package com.wt.master.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性文件工具类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:29:28 PM
 */
public class SpringPropertyUtils extends PropertyPlaceholderConfigurer {
    private static Map<String,String> properties = new HashMap<>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for(Object key:props.keySet()){
            properties.put(key.toString(),props.get(key).toString());
        }
    }

    public static String get(String key){
        return properties.get(key);
    }
}
