package com.wt.master.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * spring容器工具类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:28:59 PM
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanType){
        return applicationContext.getBean(beanType);
    }
    public static void main(String[] args){
        List<String> a = new ArrayList<>();
        System.out.println(a.toArray());
    }
}
