package com.wt.master.core.config;

import com.wt.master.core.datasource.DynamicDataSourceRegister;
import com.wt.master.core.property.DynamicDataSourceProperties;
import com.wt.master.core.property.SystemConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * 跨域配置
 *
 * @author lichking2019@aliyun.com
 * @date Apr 29, 2019 at 2:16:52 PM
 */
@Configuration
@ConditionalOnProperty(prefix = "com.wt.framework.config.crossDomain", value = "enable")
@Slf4j
public class CrossDomainConfiguration {
    public CrossDomainConfiguration() {
        log.info(">>>>>[系统配置]，发现应用进行了跨域配置[{}]，进行跨域配置...", "com.wt.framework.config.crossDomain.enable=true");
    }

    private CorsConfiguration buildConfig(SystemConfigProperties systemConfigProperties) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1允许任何域名使用
        corsConfiguration.addAllowedOrigin(systemConfigProperties.getCrossDomain().get("allowedOrgin").toString());
        // 2允许任何头
        corsConfiguration.addAllowedHeader(systemConfigProperties.getCrossDomain().get("allowedHeader").toString());
        // 3允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod(systemConfigProperties.getCrossDomain().get("allowedMethod").toString());
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(SystemConfigProperties systemConfigProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig(systemConfigProperties));
        return new CorsFilter(source);
    }
}
