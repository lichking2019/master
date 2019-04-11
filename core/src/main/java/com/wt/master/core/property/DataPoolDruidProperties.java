package com.wt.master.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: product
 * @Package: com.aptech.bigdata.efficiency.common.property
 * @Description: 阿里Druid数据库连接池 配置信息
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-03-26 08:58
 * @Version: v1.0
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
