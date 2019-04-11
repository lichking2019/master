package com.wt.master.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//只能用于类使用
@Target(ElementType.TYPE)
//保留到运行阶段
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 实体对应的表名
     * @return
     */
    String tableName();
}
