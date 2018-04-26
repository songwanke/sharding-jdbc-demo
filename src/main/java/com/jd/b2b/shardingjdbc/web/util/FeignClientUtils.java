package com.jd.b2b.shardingjdbc.web.util;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * @author zhangjianbin
 */
public class FeignClientUtils {

    /**
     * 获取feign 客户端
     *
     * @param client
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T getClient(Class<T> client, String url) {
        return Feign.builder()
                .logger(new Slf4jLogger(client))
                .logLevel(feign.Logger.Level.FULL)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(client, url);
    }

}
