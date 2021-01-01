package org.winter.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wang
 * @Description controller.method annotation
 * @Date 2020/12/31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    // HTTP请求方法
    String method() default "GET";

    // 请求路径
    String value() default "/";
}
