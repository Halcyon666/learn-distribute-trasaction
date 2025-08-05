package com.jobs.controller;

import com.jobs.pojo.Order;
import com.jobs.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/order")
@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(@RequestBody Order order) {
        try {
            Long orderId = orderService.createOrder(order);
            log.info("controller下单成功");
            return ResponseEntity.ok(orderId);
        } catch (Exception ex) {
            log.error("controller下单失败：{}", ex.getMessage(), ex);
            return ResponseEntity.status(500).body(0L);
        }
    }
}
