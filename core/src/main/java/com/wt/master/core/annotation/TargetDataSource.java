package com.wt.master.core.annotation;

import java.lang.annotation.*;

/**
 * 标注当前方法使用的数据源名称
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:23:40 PM
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String name();
}
