package com.wt.master.core.datasource;

import java.lang.annotation.*;

/**
 * @ProjectName: bigdata
 * @Package: com.aptech.bigdata.analyze.service.common.datasource
 * @Description:
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-04-01 19:16
 * @Version: v1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String name();
}
