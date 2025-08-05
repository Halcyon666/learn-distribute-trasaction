package com.jobs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_account_freeze")
public class AccountFreeze {

    @TableId(type = IdType.INPUT)
    private String xid;

    private String userId;

    private Integer freezeMoney;

    //1-try状态，0-cancel状态
    private Integer state;
}
