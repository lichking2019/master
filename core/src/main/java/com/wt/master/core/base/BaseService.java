package com.wt.master.core.base;

import com.wt.master.core.helper.QueryHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * service 抽象，定义通用的接口
 * @param <T> 服务对应的实体类型
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:28:03 PM
 */
public interface BaseService<T> {

    /**
     * 查询所有实体信息
     * @return 实体集合
     */
    public List<T> findAll(T entity);

    /**
     * 查询所有的实体信息
     * @param queryHelper sql辅助类
     * @return
     */
    public List<Map<String,Object>> findAll(QueryHelper queryHelper, Map<String,Object> param);

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
