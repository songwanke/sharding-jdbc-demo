package com.jd.b2b.shardingjdbc.web.config;

import com.google.common.base.Preconditions;
import com.jd.b2b.shardingjdbc.web.config.sharding.SpringBootShardingRuleConfigurationProperties;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.exception.ShardingJdbcException;
import io.shardingjdbc.core.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songwanke
 * @date 2018/1/24
 */
//@Configuration
//@EnableConfigurationProperties({SpringBootShardingRuleConfigurationProperties.class,DruidConfig.class})
public class ShardingjdbcConfig implements EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SpringBootShardingRuleConfigurationProperties shardingProperties;

//    @Autowired
//    private DruidConfig druidConfig;

    private final Map<String, DataSource> dataSourceMap = new HashMap<>();

//    @Bean(name = "druidDataSource")
    @Bean
    public DataSource dataSource() throws SQLException {

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingProperties.getShardingRuleConfiguration(),
                shardingProperties.getConfigMap(), shardingProperties.getProps());
    }

    /**
     * druid DataSource 配置
     * @return DataSource
     */
//    public DataSource druidDataSource(final String dataSourceName){
//        DruidDataSource datasource = new DruidDataSource();
//
//        datasource.setUrl(String.format(druidConfig.getDbUrl(),dataSourceName));
//        datasource.setUsername(druidConfig.getUsername());
//        datasource.setPassword(druidConfig.getPassword());
//        datasource.setDriverClassName(druidConfig.getDriverClassName());
//        datasource.setInitialSize(druidConfig.getInitialSize());
//        datasource.setMinIdle(druidConfig.getMinIdle());
//        datasource.setMaxActive(druidConfig.getMaxActive());
//        datasource.setMaxWait(druidConfig.getMaxWait());
//        datasource.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
//        datasource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
//        datasource.setValidationQuery(druidConfig.getValidationQuery());
//        datasource.setTestWhileIdle(druidConfig.getTestWhileIdle());
//        datasource.setTestOnBorrow(druidConfig.getTestOnBorrow());
//        datasource.setTestOnReturn(druidConfig.getTestOnReturn());
//        datasource.setPoolPreparedStatements(druidConfig.getPoolPreparedStatements());
//        try {
//            datasource.setFilters(druidConfig.getFilters());
//            /*druidConfig.getPwdDecryptProperties().setProperty("config.decrypt.key",druidConfig.getPublicKey());
//            datasource.setConnectProperties(druidConfig.getPwdDecryptProperties());*/
//        } catch (SQLException e) {
//            logger.error("druid configuration initialization filter", e);
//        }
//        return datasource;
//    }

    @Override
    public void setEnvironment(Environment environment) {
        setDataSourceMap(environment);
    }

    private void setDataSourceMap(final Environment environment) {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "sharding.jdbc.datasource.");
        String dataSources = propertyResolver.getProperty("names");
        for (String each : dataSources.split(",")) {
            try {
                Map<String, Object> dataSourceProps = propertyResolver.getSubProperties(each + ".");
                Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
               DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
//                dataSourceMap.put(each, druidDataSource(each));
                dataSourceMap.put(each, dataSource);
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingJdbcException("Can't find datasource type!", ex);
            }
        }
    }
}
