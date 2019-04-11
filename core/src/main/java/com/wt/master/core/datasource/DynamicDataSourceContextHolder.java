package com.wt.master.core.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: bigdata
 * @Package: com.aptech.bigdata.analyze.service.common.datasource
 * @Description:
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-04-01 18:51
 * @Version: v1.0
 */
@Slf4j
public class DynamicDataSourceContextHolder {
    /**
     * 存放当前线程使用的数据源类型信息
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 存放数据源id
     */
    public static List<String> dataSourceIds = new ArrayList<String>();

    /**
     * 设置数据源
     * @param dataSourceType
     */
    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSourceType() {
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断当前数据源是否存在
     * @param dataSourceId
     * @return
     */
    public static boolean isContainsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
