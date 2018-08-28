package com.pubutech.blog.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.pubutech.blog.framework.datasource.DynamicDataSource;
import com.pubutech.blog.framework.property.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    private DataSource createDataSource(String driverClassName , String jdbcUrl , String userName, String password){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
            dataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Qualifier("dataSource")
    @Primary
    public DynamicDataSource dataSource(){

        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource master = createDataSource(dataSourceProperties.getDriverClassName(),
                dataSourceProperties.getMasterUrl(),
                dataSourceProperties.getUserName(),
                dataSourceProperties.getPassword());
        targetDataSources.put("master", master);
        targetDataSources.put("slave", createDataSource(dataSourceProperties.getDriverClassName(),
                dataSourceProperties.getSlaveUrl(),
                dataSourceProperties.getUserName(),
                dataSourceProperties.getPassword()));

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(master);// 默认的datasource设置为ldkMasterDataSource
        return dataSource;
    }

}
