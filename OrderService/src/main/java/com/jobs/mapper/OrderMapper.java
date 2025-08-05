package com.jobs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jobs.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
