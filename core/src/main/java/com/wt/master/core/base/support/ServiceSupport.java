package com.wt.master.core.base.support;

import java.io.Serializable;
import java.util.List;

public abstract class ServiceSupport<T, M extends MapperSupport<T>> {
    /**
     * 获取Mapper由具体的实现类实现
     *
     * @return
     */
    protected abstract M getMapper();

    protected abstract Class<T> getEntityType();

    /**
     * 查询所有实体信息
     *
     * @return 实体集合
     */
    public List<T> findAll(T entity) {
        return getMapper().findAll(entity);
    }

    /**
     * 添加单个实体
     *
     * @param entity 实体信息
     */
    public void add(T entity) {
        getMapper().add(entity);
    }

    /**
     * 物理删除实体
     *
     * @param id 实体ID
     * @param entityType 实体类型
     */
    public void delete(Serializable id) {
        getMapper().delete(id,getEntityType());
    }

    /**
     * 更新实体
     *
     * @param entity 实体信息
     */
    public void update(T entity) {
        getMapper().update(entity);
    }

    /**
     * 根据实体ID，查询实体信息
     *
     * @param entityId 实体ID
     * @return
     */
    public T findById(Serializable entityId) {
        return getMapper().findById(entityId,getEntityType());
    }

    /**
     * 逻辑删除实体
     *
     * @param entityId
     * @return 删除的实体ID
     */
    public int logicDelete(Serializable entityId) {
        return getMapper().logicDelete(entityId,getEntityType());
    }

    /**
     * 批量添加实体
     *
     * @param entityList 实体信息集合
     */
    public void addBatch(List<T> entityList) {
        getMapper().addBatch(entityList,getEntityType());
    }

    /**
     * 批量更新实体
     *
     * @param entityList 实体信息集合
     */
    public void updateBatch(List<T> entityList) {
        getMapper().updateBatch(entityList,getEntityType());
    }
}
