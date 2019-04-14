package com.wt.master.core.aop;

import com.alibaba.fastjson.JSON;
import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * service层拦截，记录耗时信息，参数信息，异常信息
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:25:53 PM
 */

@Slf4j
@Aspect
@Order(2)
@Component
public class ServiceLogInterceptor {
    /**
     * 线程安全的耗时容器
     */
    private ThreadLocal<Long> timeConsuming = new ThreadLocal<>();

    /**
     * 请求拦截
     *
     * @param proceedingJoinPoint
     */
    @Around(value = "@within(org.springframework.stereotype.Service)")
    public Object serviceLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //方法开始时间戳
        timeConsuming.set(System.currentTimeMillis());
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = className + "$" + method.getName();

        Object[] args = proceedingJoinPoint.getArgs();
        log.info("\n ★{内容:[请求参数统计],方法名:[{}]" +
                ",参数信息:[{}]}★ \n", methodName, JSON.toJSONString(args));
//        try {
        Object result = proceedingJoinPoint.proceed();
        log.info("\n ★{内容:[耗时统计],方法名:[{}],耗时:[{}毫秒]}★ \n", methodName,
                System.currentTimeMillis() - timeConsuming.get());
        //防止内存泄露
        timeConsuming.remove();
        return result;
//        } catch (Throwable throwable) {
//            log.error("系统异常，方法名：{}，异常信息：{}", methodName, throwable.getMessage());
//            throwable.printStackTrace();
//        }
//        return null;
    }
}
