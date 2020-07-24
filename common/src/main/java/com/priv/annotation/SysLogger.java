package com.priv.annotation;

import java.lang.annotation.*;


/**
 * @author fenghen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogger {
    String value() default "";
}
