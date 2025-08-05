package com.jobs.controller;

import com.jobs.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/stock")
@RestController
public class StockController {

    @Resource
    private StockService stockService;

    @GetMapping("/minus/{gid}/{count}")
    public ResponseEntity<String> minusCount(@PathVariable("gid") String gid,
                                             @PathVariable("count") Integer count) {
        stockService.minusCount(gid, count);
        return ResponseEntity.ok("减库存量成功");
    }
}
