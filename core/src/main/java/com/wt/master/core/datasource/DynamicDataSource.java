package com.wt.master.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ProjectName: bigdata
 * @Package: com.aptech.bigdata.analyze.service.common.datasource
 * @Description: 数据源路由
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-04-01 18:51
 * @Version: v1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
