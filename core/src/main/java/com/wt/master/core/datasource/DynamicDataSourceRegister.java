package com.wt.master.core.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: bigdata
 * @Package: com.aptech.bigdata.analyze.service.common.datasource
 * @Description: 数据源注册，通过实现ImportBeanDefinitionRegistrar，在Spring容器启动的时候注册自定义的Bean
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-04-01 18:54
 * @Version: v1.0
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {


    /**
     * 指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
     */
    private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
    public static final String SPRING_DATASOURCE_DRIVER = "spring.datasource.driver";
    public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String DRIVER = "driver";
    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SLAVE_DATASOURCE_NAMES = "slave.datasource.names";
    public static final String SLAVE_DATASOURCE = "slave.datasource.";
    public static final String DATA_SOURCE = "dataSource";
    public static final String DEFAULT_TARGET_DATA_SOURCE = "defaultTargetDataSource";
    public static final String TARGET_DATA_SOURCES = "targetDataSources";

    /**
     * 默认数据源
     */
    private DataSource defaultDataSource;

    /**
     * 用户自定义数据源
     */
    private Map<String, DataSource> slaveDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        initDefaultDataSource(environment);
        initslaveDataSources(environment);
    }

    /**
     * 初始化主数据源
     *
     * @param env
     */
    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put(DRIVER, env.getProperty(SPRING_DATASOURCE_DRIVER));
        dsMap.put(URL, env.getProperty(SPRING_DATASOURCE_URL));
        dsMap.put(USERNAME, env.getProperty(SPRING_DATASOURCE_USERNAME));
        dsMap.put(PASSWORD, env.getProperty(SPRING_DATASOURCE_PASSWORD));
        defaultDataSource = buildDataSource(dsMap);
    }

    /**
     * 初始化从数据源
     *
     * @param env
     */
    private void initslaveDataSources(Environment env) {
        // 读取配置文件获取更多数据源
        String dsPrefixs = env.getProperty(SLAVE_DATASOURCE_NAMES);
        for (String dsPrefix : dsPrefixs.split(",")) {
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put(DRIVER, env.getProperty(SLAVE_DATASOURCE + dsPrefix + ".driver"));
            dsMap.put(URL, env.getProperty(SLAVE_DATASOURCE + dsPrefix + ".url"));
            dsMap.put(USERNAME, env.getProperty(SLAVE_DATASOURCE + dsPrefix + ".username"));
            dsMap.put(PASSWORD, env.getProperty(SLAVE_DATASOURCE + dsPrefix + ".password"));
            DataSource ds = buildDataSource(dsMap);
            slaveDataSources.put(dsPrefix, ds);
        }
    }

    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get(URL).toString();
            String username = dataSourceMap.get(USERNAME).toString();
            String password = dataSourceMap.get(PASSWORD).toString();
            // 自定义DataSource配置，工厂模式的使用
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //添加默认数据源
        targetDataSources.put(DATA_SOURCE, this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add(DATA_SOURCE);
        //添加其他数据源
        targetDataSources.putAll(slaveDataSources);
        for (String key : slaveDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue(DEFAULT_TARGET_DATA_SOURCE, defaultDataSource);
        mpv.addPropertyValue(TARGET_DATA_SOURCES, targetDataSources);
        //注册 - BeanDefinitionRegistry
        registry.registerBeanDefinition(DATA_SOURCE, beanDefinition);
        log.info("Dynamic DataSource Registry");
    }
}
