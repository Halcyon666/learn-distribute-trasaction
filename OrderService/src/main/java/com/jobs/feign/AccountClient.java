package com.jobs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/minus/{uid}/{money}")
    String minus(@PathVariable("uid") String uid, @PathVariable("money") Integer money);
}
