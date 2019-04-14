package com.wt.master.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略注解，在框架进行SQL语句拼装的时候，忽略带有这个注解的字段
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:24:46 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transparent {
    String value() default "";
}
