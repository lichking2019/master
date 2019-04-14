package com.wt.master.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注实体对应的数据库表名
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:20:49 PM
 */
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
