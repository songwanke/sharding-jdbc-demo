package com.jd.b2b.shardingjdbc.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author songwanke
 * @date 2018/1/30
 */
@ApiIgnore
@Controller
public class ApiController {
    /**
     * swagger地址跳转
     * @return swagger 地址
     */
    @RequestMapping("/api")
    public String redisApiHome() {
        return "redirect:swagger-ui.html";
    }

}
