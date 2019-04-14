package com.wt.master.core.aop;

import com.wt.master.core.datasource.DynamicDataSourceContextHolder;
import com.wt.master.core.annotation.TargetDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: bigdata
 * @Package: com.aptech.bigdata.analyze.service.common.datasource
 * @Description: 拦截带有@TargetDataSource注解的方法，切换数据源
 * @Author: lichking2018@aliyun.com
 * @CreateDate: 2019-04-01 19:17
 * @Version: v1.0
 */
@Aspect
@Order(-1)
//@Component 改为动态注入，不是所有情况都加载这个拦截器，在DynamicDataSourceConfiguration中进行容器注入
@Slf4j
public class DynamicDattaSourceInterceptor {
    /**
     * 改变数据源
     * @param joinPoint
     * @param targetDataSource
     */
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String dbid = targetDataSource.name();
        if (!DynamicDataSourceContextHolder.isContainsDataSource(dbid)) {
            //joinPoint.getSignature() ：获取连接点的方法签名对象
            log.error("数据源 " + dbid + " 不存在使用默认的数据源 -> " + joinPoint.getSignature());
        } else {
            log.debug("使用数据源：" + dbid);
            DynamicDataSourceContextHolder.setDataSourceType(dbid);
        }
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        log.debug("清除数据源 " + targetDataSource.name() + " !");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }
}
