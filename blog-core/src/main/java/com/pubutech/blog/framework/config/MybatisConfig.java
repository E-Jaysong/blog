package com.pubutech.blog.framework.config;

import com.pubutech.blog.framework.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Properties;

@Configuration
@MapperScan("com.pubutech.blog.persistence.mapper")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    @Qualifier("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);

        Properties sqlSessionFactoryProperties = new Properties();
        sqlSessionFactoryProperties.setProperty("cacheEnabled","false");
        fb.setConfigurationProperties(sqlSessionFactoryProperties);
        return fb.getObject();
    }

    @Bean
    @Qualifier("transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
