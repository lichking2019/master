package com.wt.master.core.config;

import com.wt.master.core.aop.DynamicDattaSourceInterceptor;
import com.wt.master.core.datasource.DynamicDataSourceRegister;
import com.wt.master.core.property.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 动态数据源配置文件
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:30:18 PM
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@Import({DynamicDataSourceRegister.class})
@ConditionalOnProperty(prefix = "slave", value = "enable", matchIfMissing = false)
@Slf4j
public class DynamicDataSourceConfiguration {
    @Autowired
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Bean
    @ConditionalOnMissingBean(DynamicDattaSourceInterceptor.class)
    public DynamicDattaSourceInterceptor dynamicDattaSourceInterceptor() {
        log.info("配置文件中slave.enable=true,将多数据源拦截器加入到spring容器中");
        return new DynamicDattaSourceInterceptor();
    }
}
