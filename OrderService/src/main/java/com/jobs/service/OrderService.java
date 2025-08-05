package com.jobs.service;

import com.jobs.feign.AccountClient;
import com.jobs.feign.StockClient;
import com.jobs.mapper.OrderMapper;
import com.jobs.pojo.Order;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final AccountClient accountClient;
    private final StockClient stockClient;

    @GlobalTransactional
    public Long createOrder(Order order) {
        //创建订单
        orderMapper.insert(order);
        String xid = RootContext.getXID();
        log.info("创建订单 xid: {}", xid);
        //减钱
        accountClient.minus(order.getUserId(), order.getMoney());
        //减库存
        stockClient.minus(order.getGoodsId(), order.getCount());
        //返回订单号
        return order.getId();
    }
}
