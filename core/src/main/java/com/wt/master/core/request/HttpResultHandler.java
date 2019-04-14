package com.wt.master.core.request;

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


    public enum ErrorCode{
        SUCCESS("0","SUCCESS")
        ,ERROR("1","ERROR");
        private String code;
        private String message;

        ErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
