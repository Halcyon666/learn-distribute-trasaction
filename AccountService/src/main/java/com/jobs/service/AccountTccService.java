package com.jobs.service;

import com.jobs.mapper.AccountFreezeMapper;
import com.jobs.mapper.AccountMapper;
import com.jobs.pojo.AccountFreeze;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//TCC 是事务是 3 中操作的首字母缩写，即 try（执行操作），confirm（确认提交），cancel（数据回滚）
@LocalTCC
@Slf4j
@Service
@AllArgsConstructor
public class AccountTccService {

    private final AccountMapper accountMapper;

    private final AccountFreezeMapper freezeMapper;

    //该注解配置了 tcc 事务的 3 个方法：
    //name 配置 try 方法
    //commitMethod 配置 confirm 方法
    //rollbackMethod 配置 cancel 方法
    @TwoPhaseBusinessAction(name = "minusMoney",
            commitMethod = "confirm", rollbackMethod = "cancel")
    public void minusMoney(
            //使用该注解指定的参数，
            //参数值可以在 confirm 方法和 cancel 方法的 BusinessActionContext 参数中获取到
            @BusinessActionContextParameter(paramName = "uid") String uid,
            @BusinessActionContextParameter(paramName = "money") int money) {
        //获取事务id
        String xid = RootContext.getXID();

        //为了防止业务悬挂，需要判断是否有冻结记录，如果有的话，就不能再执行 try 操作了
        AccountFreeze oldfreeze = freezeMapper.selectById(xid);
        if (oldfreeze != null) {
            return;
        }

        //减钱
        accountMapper.minusMoney(uid, money);
        //记录冻结的金额和事务状态
        AccountFreeze freeze = new AccountFreeze();
        freeze.setUserId(uid);
        freeze.setFreezeMoney(money);
        // 1 表示 try 状态，0 表示 cancel 状态
        freeze.setState(1);
        freeze.setXid(xid);
        freezeMapper.insert(freeze);
    }

    //事务成功提交的方法，此时需要删除冻结记录即可
    public boolean confirm(BusinessActionContext bac) {
        //获取事务id
        String xid = bac.getXid();
        //根据id删除冻结记录
        int count = freezeMapper.deleteById(xid);
        return count == 1;
    }

    //数据回滚方法，此时需要恢复金额，更改冻结记录的状态
    @SuppressWarnings("unused")
    public boolean cancel(BusinessActionContext bac) {
        //通过事务id查询冻结记录中的金额
        String xid = bac.getXid();
        AccountFreeze freeze = freezeMapper.selectById(xid);

        //如果 freeze 为 null，表示之前没有执行过 try，
        //此时需要空回滚，向 tb_account_freeze 表示添加一条 cancel 状态的记录
        if (freeze == null) {
            freeze = new AccountFreeze();

            //由于在 try 方法（也就是 minusMoney 方法）的参数 uid，
            //使用了 @BusinessActionContextParameter 注解，
            //因此这里使用 BusinessActionContext.getActionContext("uid")
            //就能够获取到 uid 传入的参数值，也就是用户id的值
            String uid = bac.getActionContext("uid").toString();
            freeze.setUserId(uid);
            freeze.setFreezeMoney(0);
            // 1 表示 try 状态，0 表示 cancel 状态
            freeze.setState(0);
            freeze.setXid(xid);
            freezeMapper.insert(freeze);
            return true;
        }

        //为了防止 cancel 方法被调用了多次，这里需要幂等性判断
        //如果获取到的冻结记录，状态本身已经是 cancel 状态，则不再进行处理
        if (freeze.getState() == 0) {
            return true;
        }

        //恢复余额
        accountMapper.addMoney(freeze.getUserId(), freeze.getFreezeMoney());
        //将冻结金额清零，状态改为 cancel
        //1 表示 try 状态，0 表示 cancel 状态
        freeze.setFreezeMoney(0);
        freeze.setState(0);
        freezeMapper.updateById(freeze);
        return true;
    }
}
