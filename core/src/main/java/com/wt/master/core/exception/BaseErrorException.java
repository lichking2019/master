package com.wt.master.core.exception;

/**
 * 系统异常，非捕获异常。通常是业务无法处理的异常
 *
 * @author lichking2019@aliyun.com
 * @date Apr 26, 2019 at 2:54:01 PM
 */
public class BaseErrorException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public BaseErrorException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 异常信息
     * @param cause   异常内容
     */
    public BaseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
