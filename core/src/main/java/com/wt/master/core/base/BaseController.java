package com.wt.master.core.base;

import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 控制器基类
 *
 * @param <T> 实体类型
 * @param <S> 服务类型
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:27:11 PM
 */
public abstract class BaseController<T, S extends BaseService<T>> {

    /**
     * 获取服务实例，有具体的实现类实现
     *
     * @return
     */
    protected abstract S getService();

    /**
     * 条件查询
     *
     * @param entity 实体
     * @return
     */
    @PutMapping(value = "/findByCondition")
    public HttpResultEntity<?> findByCondition(@RequestBody T entity) {
        return getSuccessResult(getService().findAll(entity));
    }

    /**
     * 查询所有实体
     *
     * @return
     */
    @GetMapping(value = "/findAll")
    public HttpResultEntity<?> findAll() {
        return getSuccessResult(getService().findAll());
    }

    /**
     * 添加实体
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/add")
    public HttpResultEntity<?> add(@RequestBody T entity) {
        getService().add(entity);
        return getSuccessResult(entity);
    }

    /**
     * 删除实体
     *
     * @param entityId
     * @return
     */
    @DeleteMapping(value = "/delete/{entityId}")
    public HttpResultEntity<?> delete(@PathVariable("entityId") Integer entityId) {
        getService().delete(entityId);
        return getSuccessResult();
    }

    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    @PutMapping(value = "/update")
    public HttpResultEntity<?> update(@RequestBody T entity) {
        getService().update(entity);
        return getSuccessResult();
    }

    /**
     * 根据Id查询实体
     *
     * @param entityId
     * @return
     */
    @GetMapping(value = "/findById/{entityId}")
    public HttpResultEntity<?> findById(@PathVariable("entityId") Integer entityId) {
        return getSuccessResult(getService().findById(entityId));
    }

    /**
     * 逻辑删除
     * @param entityId
     * @return
     */
    @PutMapping(value = "/logicDelete/{entityId}")
    public HttpResultEntity<?> logicDelete(@PathVariable("entityId") Serializable entityId) {
        return getSuccessResult(getService().logicDelete(entityId));
    }

    /**
     * 批量添加
     * @param entityList
     * @return
     */
    @PostMapping(value = "/addBatch")
    public HttpResultEntity<?> addBatch(@RequestBody List<T> entityList) {
        getService().addBatch(entityList);
        return getSuccessResult();
    }

    /**
     * 批量更新
     * @param entityList
     * @return
     */
    @PutMapping(value = "/updateBatch")
    public HttpResultEntity<?> updateBatch(@RequestBody List<T> entityList) {
        getService().updateBatch(entityList);
        return getSuccessResult();
    }

    /**
     * 返回成功的结果，不需要有内容
     *
     * @return
     */
    protected HttpResultEntity<?> getSuccessResult() {
        return getSuccessResult(null);
    }

    /**
     * 返回处理成功的结果
     *
     * @param result 结果内容
     * @return
     */
    protected HttpResultEntity<?> getSuccessResult(Object result) {
        return HttpResultHandler.getResultEntity(HttpResultHandler.ErrorCode.SUCCESS, result);
    }
}
