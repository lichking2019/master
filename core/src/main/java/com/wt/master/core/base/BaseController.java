package com.wt.master.core.base;

import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 控制器基类
 * @param <T> 实体类型
 * @param <S> 服务类型
 */
public abstract class BaseController<T,S extends BaseService<T>> {

    /**
     * 获取服务实例，有具体的实现类实现
     * @return
     */
    protected abstract S getService();


    @GetMapping(value = "/findAll")
    public  HttpResultEntity findAll(@RequestBody T entity) {
        return getSuccessResult(getService().findAll(entity));
    }

    @PostMapping(value = "/add")
    public HttpResultEntity add(@RequestBody T entity) {
        getService().add(entity);
        return getSuccessResult(entity);
    }

    @DeleteMapping(value = "/delete/{entityId}")
    public HttpResultEntity delete(@PathVariable("entityId") Integer entityId) {
        getService().delete(entityId);
        return getSuccessResult();
    }

    @PutMapping(value = "/update")
    public HttpResultEntity update(@RequestBody T securityUser) {
        getService().update(securityUser);
        return getSuccessResult();
    }

    @GetMapping(value = "/findById/{entityId}")
    public HttpResultEntity findById(@PathVariable("entityId") Integer entityId) {
        return getSuccessResult(getService().findById(entityId));
    }

    @PutMapping(value = "/logicDelete/{entityId}")
    public HttpResultEntity logicDelete(@PathVariable("entityId") Serializable entityId) {
        return getSuccessResult(getService().logicDelete(entityId));
    }

    @PostMapping(value = "/addBatch")
    public HttpResultEntity addBatch(@RequestBody List<T> entityList){
        getService().addBatch(entityList);
        return getSuccessResult();
    }

    @PutMapping(value="/updateBatch")
    public HttpResultEntity updateBatch(@RequestBody List<T> entityList){
        getService().updateBatch(entityList);
        return getSuccessResult();
    }

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
