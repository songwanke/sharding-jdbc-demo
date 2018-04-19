package com.jd.b2b.shardingjdbc;

import com.alibaba.druid.filter.stat.StatFilter;
import io.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author songwanke
 * @date 2018/1/23
 */
@MapperScan("com.jd.b2b.shardingjdbc.mapper")
@SpringBootApplication
//@ComponentScan(basePackageClasses = {SpringBootConfiguration.class,ShardingJdbcDataMain.class})
// @EnableDiscoveryClient
public class ShardingJdbcDataMain extends SpringBootServletInitializer{

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDataMain.class,args);
    }

    /**
     * 覆盖configure()，把启动类Application注册进去。
     * 外部web应用服务器构建Web Application Context的时候，会把启动类添加进去。
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ShardingJdbcDataMain.class);
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        return statFilter;
    }
}
