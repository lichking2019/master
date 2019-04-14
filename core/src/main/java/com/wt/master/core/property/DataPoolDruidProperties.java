package com.wt.master.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里Druid数据库连接池 配置信息。类型安全的方式记在属性文件，只支持property类型的配置文件
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:33:39 PM
 */
@Component
@ConfigurationProperties(prefix = "druid.pool")
@Data
public class DataPoolDruidProperties {

    /**
     * IP白名单
     */
    private String allow;

    /**
     * IP黑名单
     */
    private String deny;

    /**
     * 登录账号
     */
    private String loginUsername;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 是否能够重置数据
     */
    private String resetEnable;

    /**
     * 添加不需要忽略的格式信息
     */
    private String exclusions;

    /**
     * 过滤规则
     */
    private String urlPatterns;
}
