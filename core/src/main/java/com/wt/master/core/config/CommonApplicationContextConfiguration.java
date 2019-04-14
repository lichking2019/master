package com.wt.master.core.config;

//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wt.master.core.common.utils.SpringPropertyUtils;
import com.wt.master.core.datasource.DynamicDataSourceRegister;
import com.wt.master.core.property.DataPoolDruidProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Spring 上下文加载Bean配置类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:30:00 PM
 */
//声明这是一个配置类
@Configuration
//扫描本模块bean
@ComponentScan(basePackages = {"com.wt.**.*"})
public class CommonApplicationContextConfiguration {

    public static final String CONFIG_KYLIN_PROPERTIES = "config/kylin.properties";
    public static final String CONFIG_THRIFT_SERVER_PROPERTIES = "config/thriftServer.properties";
    public static final String CONFIG_SYSTEM_INIT_PARAMETER_PROPERTIES = "config/systemInitParameter.properties";

    /**
     * 资源文件工具类
     *
     * @return
     */
    @Bean
    public static SpringPropertyUtils springPropertyUtils() {
        SpringPropertyUtils springPropertyUtils = new SpringPropertyUtils();
        Resource kylinConfig = new ClassPathResource(CONFIG_KYLIN_PROPERTIES);
        Resource thriftServerList = new ClassPathResource(CONFIG_THRIFT_SERVER_PROPERTIES);
        Resource systemInitParameter = new ClassPathResource(CONFIG_SYSTEM_INIT_PARAMETER_PROPERTIES);
        springPropertyUtils.setLocations(kylinConfig,thriftServerList,systemInitParameter);
        return springPropertyUtils;
    }


    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(DataPoolDruidProperties druidPoolConfig) {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid" +
                "/*");
        //添加初始化参数：initParams
        //白名单
        servletRegistrationBean.addInitParameter("allow", druidPoolConfig.getAllow());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", druidPoolConfig.getDeny());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", druidPoolConfig.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidPoolConfig.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", druidPoolConfig.getResetEnable());
        return servletRegistrationBean;
    }

    /**
     * druid 连接池过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(DataPoolDruidProperties druidPoolConfig) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns(druidPoolConfig.getUrlPatterns());
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", druidPoolConfig.getExclusions());
        return filterRegistrationBean;
    }

    /**
     * 使用jedis作为redis的客户端
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    /**
     * Spring提供的redis操作模板
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
