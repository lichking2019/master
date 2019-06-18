package com.wt.master.core.property;

import com.wt.master.core.common.utils.SpringContextHolder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 系统配置
 *
 * @author lichking2019@aliyun.com
 * @date Apr 29, 2019 at 2:26:37 PM
 */
@Data
public class SystemConfigProperties {
    private Map<String, Object> cache;
    private Map<String, Object> swagger;
    private Map<String, Object> crossDomain;
    private Map<String, Object> dao;
    private Map<String, Object> table;

    /**
     * 获取容器中的实例
     * @return
     */
    public static SystemConfigProperties getInstance() {
        return (SystemConfigProperties) SpringContextHolder.getBean("systemConfigProperties");
    }


}
