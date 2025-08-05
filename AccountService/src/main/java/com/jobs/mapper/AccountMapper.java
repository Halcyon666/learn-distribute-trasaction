package com.jobs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    //减钱
    @Update("update tb_account set money = money - ${money} where user_id = #{uid}")
    int minusMoney(@Param("uid") String uid, @Param("money") int money);

    //加钱
    @Update("update tb_account set money = money + ${money} where user_id = #{uid}")
    int addMoney(@Param("uid") String uid, @Param("money") int money);
}
