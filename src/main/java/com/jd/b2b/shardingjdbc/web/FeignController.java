package com.jd.b2b.shardingjdbc.web;

import com.jd.b2b.shardingjdbc.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songwanke
 * @date 2018/10/23
 */
@RestController
public class FeignController {

     @Autowired
     private FeignService feignService;

     @GetMapping(value = "/search/github")
     public String search(@RequestParam("str") String queryStr){
         return feignService.searchRepo(queryStr);
     }
}
