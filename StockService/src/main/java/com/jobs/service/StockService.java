package com.jobs.service;

import com.jobs.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StockService {

    @Resource
    private StockMapper stockMapper;

    public void minusCount(String gid, Integer count) {
        try {
            stockMapper.minusCount(gid, count);
        } catch (Exception e) {
            throw new RuntimeException("减库存失败");
        }
        log.info("减库存成功");
    }
}
