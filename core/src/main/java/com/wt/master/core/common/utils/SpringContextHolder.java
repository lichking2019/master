package com.wt.master.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    public static void main(String[] args){
        List<String> a = new ArrayList<>();
        a.add("dddd");
        a.add("eee");
        System.out.println(a.toArray());
    }
}
