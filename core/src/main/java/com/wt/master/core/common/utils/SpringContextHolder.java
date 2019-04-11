package com.wt.master.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: bigdata
 * @Package: com.wt.project.common.utils
 * @Description:
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-03-29 15:32
 * @Version: v1.0
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

}
