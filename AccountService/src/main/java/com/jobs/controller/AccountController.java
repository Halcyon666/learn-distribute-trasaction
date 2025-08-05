package com.jobs.controller;

import com.jobs.service.AccountTccService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountTccService accountTccService;

    @GetMapping("/minus/{uid}/{money}")
    public ResponseEntity<String> minusMoney(@PathVariable("uid") String uid,
                                             @PathVariable("money") Integer money) {
        accountTccService.minusMoney(uid, money);
        return ResponseEntity.ok("减钱成功");
    }
}
