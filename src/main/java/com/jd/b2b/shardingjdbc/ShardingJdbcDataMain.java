package com.jd.b2b.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songwanke
 * @date 2018/1/23
 */
@MapperScan(basePackages = "com.jd.b2b.shardingjdbc.mapper")
@SpringBootApplication
public class ShardingJdbcDataMain{

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDataMain.class,args);
    }
}
