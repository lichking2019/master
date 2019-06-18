package com.wt.master.core.aop;

import com.wt.master.core.exception.BaseAccidentException;
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
 * controller层拦截器
 * 当后台处理发生异常的时候，封装异常对象返回给前台
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:25:03 PM
 */
@Aspect
@Order(-1)
@Component
@Slf4j
public class ControllerLogInterceptor {

    /**
     * 拦截所有发送到controller的请求
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public HttpResultEntity controllerLog(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = className + "$" + method.getName();
        Object[] args = proceedingJoinPoint.getArgs();
        try {
            HttpResultEntity result = (HttpResultEntity) proceedingJoinPoint.proceed();
            return result;
        } catch (Throwable throwable) {
            log.error("系统异常，方法名：{}，异常信息：{}", methodName, throwable);
            if (throwable instanceof BaseAccidentException) {
                BaseAccidentException exception = (BaseAccidentException) throwable;
                return HttpResultHandler.getResultEntity(exception.getExceptionBody());
            }
            return HttpResultHandler.getResultEntity(HttpResultHandler.ErrorCode.ERROR, null);
        }
    }
}
