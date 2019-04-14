package com.wt.master.core.base;

import java.io.Serializable;
import java.util.List;

/**
 * 服务基类，提供通用的方法
 * @param <T> 服务对应的实体类型
 */
public interface BaseService<T> {

    /**
     * 查询所有实体信息
     * @return 实体集合
     */
    public List<T> findAll(T entity);

    /**
     * 添加单个实体
     * @param entity 实体信息
     */
    public void add(T entity);

    /**
     * 物理删除实体
     * @param id 实体ID
     * @param entityType 实体类型
     */
    public void delete(Serializable id);

    /**
     * 更新实体
     * @param entity 实体信息
     */
    public void update(T entity);

    /**
     * 根据实体ID，查询实体信息
     * @param entityId 实体ID
     * @return
     */
    public T findById(Serializable entityId);

    /**
     * 逻辑删除实体
     * @param entityId
     * @return 删除的实体ID
     */
    public int logicDelete(Serializable entityId);

    /**
     * 批量添加实体
     * @param entityList 实体信息集合
     */
    public void addBatch(List<T> entityList);

    /**
     * 批量更新实体
     * @param entityList 实体信息集合
     */
    public void updateBatch(List<T> entityList);
}
