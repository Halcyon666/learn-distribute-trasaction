package com.jobs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("stock-service")
public interface StockClient {

    @GetMapping("/stock/minus/{gid}/{count}")
    String minus(@PathVariable("gid") String gid, @PathVariable("count") Integer count);
}
