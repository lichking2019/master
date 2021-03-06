package com.wt.master.core.exception;

import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller异常统一处理
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:32:51 PM
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandleAdvice {

    @ExceptionHandler
    public HttpResultEntity handler(HttpServletRequest req, HttpServletResponse res, Exception e){
        log.info("请求url:{},contentType:{}",req.getRequestURL(),req.getContentType());
        log.error("请求异常:{}",e);

        if(e instanceof BaseAccidentException){
            BaseAccidentException exception = (BaseAccidentException)e;
            return HttpResultHandler.getResultEntity(exception.getExceptionBody());
        }
        return HttpResultHandler.getResultEntity(HttpResultHandler.ErrorCode.ERROR,e.getMessage());
    }
}
