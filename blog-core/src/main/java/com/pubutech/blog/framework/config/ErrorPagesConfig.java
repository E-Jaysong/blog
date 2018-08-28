package com.pubutech.blog.framework.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorPagesConfig {
    /**
     * 自定义异常处理路径
     *
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return factory -> {
            factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html"));
            factory.addErrorPages(new ErrorPage(Throwable.class, "/error.html"));
        };
    }
}
