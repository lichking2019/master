package com.wt.master.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源路由
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:31:49 PM
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
