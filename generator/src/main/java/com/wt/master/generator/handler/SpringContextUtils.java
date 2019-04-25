package com.wt.master.generator.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring上下文工具
 *
 * @author lichking2019@aliyun.com
 * @date Apr 23, 2019 at 11:45:41 PM
 */
public class SpringContextUtils {
    public static final String CLASSPATH_CONFIG_APPLICATION_CONTEXT_XML = "classpath:config/applicationContext.xml";

    /**
     * 获取bean
     * @param beanName bean名称
     * @return
     */
    public static Object getBean(String beanName){
        ApplicationContext ctx=new ClassPathXmlApplicationContext(CLASSPATH_CONFIG_APPLICATION_CONTEXT_XML);
        return ctx.getBean(beanName);
    }
}
