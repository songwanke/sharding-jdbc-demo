package com.jd.b2b.shardingjdbc.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jd.b2b.shardingjdbc.domain.Order;
import com.jd.b2b.shardingjdbc.web.util.IniFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author songwanke
 * @date 2018/4/9
 */
@RestController
@RequestMapping("/ini/test")
@Slf4j
public class ReadIniFileController {

    @RequestMapping(value = "/readPlatform",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public List<String> readPlatform() throws IOException{
        List<String> init = IniFileUtils.initPlatform("interceptor.ini");
        return init;
    }

    @RequestMapping(value = "/readPlatformId",method = {RequestMethod.POST},produces = "application/json;charset=UTF-8")
    public List<Long> readPlatformId(String sectionName) throws IOException{
        List<Long> platformIdList = IniFileUtils.initPlatformIdV2("interceptor.ini",sectionName);
        return platformIdList;
    }
}
