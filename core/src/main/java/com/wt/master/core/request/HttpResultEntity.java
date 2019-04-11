package com.wt.master.core.request;

/**
 * 请求结果实体
 */
public class HttpResultEntity<T> {
    /**
     * 错误代码
     */
    private String errorCode;
    /**
     * 消息
     */
    private String message;

    /**
     * 返回的内容
     */
    private Object result;

    public HttpResultEntity(String errorCode, String message, Object result) {
        this.errorCode = errorCode;
        this.message = message;
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
