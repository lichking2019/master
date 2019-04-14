package com.wt.master.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注实体对应的属性为数据库主键
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:20:11 PM
 */

/**
 * 标注实体主键
 */
//只能在属性上使用
@Target(ElementType.FIELD)
//保留到运行阶段
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    //必填，对应的数据库主键名称
    String value();
}
