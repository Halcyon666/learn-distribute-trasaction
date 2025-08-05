package com.jobs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_stock")
public class Stock {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String goodsId;

    private Integer count;
}
