package com.pubutech.blog.business.annotation;

import java.lang.annotation.*;

/**
 * 日志切面注解
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String remark() default "";

    String operType() default "0";

}
