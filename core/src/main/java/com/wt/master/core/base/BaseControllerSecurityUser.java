package com.wt.master.core.base;

import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;

public class BaseControllerSecurityUser {
    /**
     * 返回成功的结果，不需要有内容
     * @return
     */
    protected HttpResultEntity getSuccessResult(){
        return getSuccessResult(null);
    }

    /**
     * 返回处理成功的结果
     * @param result 结果内容
     * @return
     */
    protected HttpResultEntity getSuccessResult(Object result){
        return HttpResultHandler.getResultEntity(HttpResultHandler.ErrorCode.SUCCESS,result);
    }
}
