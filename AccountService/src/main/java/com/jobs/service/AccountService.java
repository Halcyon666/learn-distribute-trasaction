package com.jobs.service;

import com.jobs.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    //减钱
    public void minusMoney(String uid, int money) {
        try {
            accountMapper.minusMoney(uid, money);
        } catch (Exception e) {
            throw new RuntimeException("减钱失败");
        }
        log.info("减钱成功");
    }
}
