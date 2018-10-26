package com.jd.b2b.shardingjdbc.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author songwanke
 * @date 2018/10/23
 */
@Component
@Slf4j
public class FeignFallBackHandler implements FallbackFactory<FeignService>{
    @Override
    public FeignService create(Throwable throwable) {
        log.info("baocuol------------------");

        return queryStr ->
                "456";
    }

//    @Override
//    public String searchRepo(String queryStr) {
//        return "123";
//    }
}
