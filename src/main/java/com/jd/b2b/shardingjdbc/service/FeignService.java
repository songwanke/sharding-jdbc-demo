package com.jd.b2b.shardingjdbc.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.service.ApiListing;

/**
 * @author songwanke
 * @date 2018/10/23
 */
@FeignClient(name = "github-client",url = "https://api.github.com",fallbackFactory = FeignFallBackHandler.class)
public interface FeignService {

    @RequestMapping(value = "/search/repositories",method = RequestMethod.GET)
    String searchRepo(@RequestParam("q") String queryStr);
}
