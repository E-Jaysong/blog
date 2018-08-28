package com.pubutech.blog.framework.property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConfigurationProperties(prefix = "datasource")
@Data
@EqualsAndHashCode(callSuper = false)
@Order(-1)
public class DataSourceProperties {

    private String type;

    private String driverClassName;

    private String masterUrl;

    private String slaveUrl;

    private String userName;

    private String password;
}
