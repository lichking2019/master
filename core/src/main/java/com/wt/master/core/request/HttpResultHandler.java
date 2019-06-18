package com.wt.master.core.request;

import com.wt.master.core.exception.ExceptionBody;

/**
 * 返回结果处理类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:34:24 PM
 */
public class HttpResultHandler {

    /**
     * 返回处理结果
     * @param errorCode 错误代码
     * @param result 结果
     * @return
     */
    public static HttpResultEntity getResultEntity(ErrorCode errorCode,Object result){
        return new HttpResultEntity(errorCode.code,errorCode.message,result);
    }

    /**
     * 根据异常对象，构建返回结果
     * @param exceptionBody
     * @return
     */
    public static HttpResultEntity getResultEntity(ExceptionBody exceptionBody){
        return new HttpResultEntity(exceptionBody.getErrorCode(),exceptionBody.getErrorMessage(),null);
    }

    public enum ErrorCode{
        SUCCESS("0","SUCCESS")
        ,ERROR("1","网络异常，请联系管理员");
        private String code;
        private String message;

        ErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
