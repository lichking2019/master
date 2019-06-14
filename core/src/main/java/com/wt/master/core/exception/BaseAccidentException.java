package com.wt.master.core.exception;

import lombok.Data;

/**
 * 业务异常基类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 26, 2019 at 2:54:39 PM
 */
@Data
public class BaseAccidentException extends Exception{
    /**
     * 异常代码
     */
    private String errorCode;
    /**
     * 异常体
     */
    private ExceptionBody exceptionBody;

    /**
     * 构造异常
     * @param exceptionBody 异常体
     * @param cause 异常原因
     */
    public BaseAccidentException(ExceptionBody exceptionBody,Throwable cause){
        super(exceptionBody.getErrorMessage(),cause);
        this.exceptionBody = exceptionBody;
        this.errorCode = exceptionBody.getErrorCode();
    }

    /**
     * 构造异常
     * @param exceptionBody 异常体
     */
    public BaseAccidentException(ExceptionBody exceptionBody){
        super(exceptionBody.getErrorMessage());
        this.exceptionBody = exceptionBody;
        this.errorCode = exceptionBody.getErrorCode();
    }
}
