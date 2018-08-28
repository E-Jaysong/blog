package com.pubutech.blog.framework.datasource;

import com.pubutech.blog.framework.holder.DatabaseContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Order(0)
public class DataSourceAspect {

    @Pointcut("execution(* com.pubutech.blog.business.service.Impl.*.*(..))")
    public void aspect() {

    }

    @Before("aspect()")
    public void before(JoinPoint point) {
        String className = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        log.info("切入点: {} 类中 {} 方法 args {}" , className , method ,args);

        if(method .startsWith("add")
                || method.startsWith("create")
                || method.startsWith("save")
                || method.startsWith("edit")
                || method.startsWith("update")
                || method.startsWith("delete")
                || method.startsWith("add")
                || method.startsWith("remove")){

            log.info("切换到: master");
            DatabaseContextHolder.setCustomerType("master");
        }else{
            log.info("切换到: slave");
            DatabaseContextHolder.setCustomerType("slave");
        }
    }

    @After("aspect()")
    public void after(JoinPoint point) {
        log.info("清理掉当前设置的数据源，让默认的数据源不受影响");
        DatabaseContextHolder.clearCustomerType();
    }
}
