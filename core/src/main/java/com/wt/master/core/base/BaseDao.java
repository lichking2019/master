package com.wt.master.core.base;

import com.wt.master.core.helper.QueryHelper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 持久层抽象，制定公共的协议
 *
 * @author lichking2019@aliyun.com
 * @date Apr 25, 2019 at 12:42:00 PM
 */
public interface BaseDao<T> {

    /**
     * 查询所有实体
     *
     * @param entity 实体
     * @return
     */
    List<T> findAll(T entity);

    /**
     * 自定义查询
     *
     * @param queryHelper 查询器
     * @param param       参数
     * @param enrityType  实体类型
     * @return
     */
    List<Map<String, Object>> findAllCustom(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType);

    /**
     * 自定义查询，所有实体
     *
     * @param queryHelper 查询器
     * @param param       入参
     * @param enrityType  实体类型
     * @return
     */
    List<T> findAll(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType);

    /**
     * count查询
     *
     * @param queryHelper 查询器
     * @param param       查询参数
     * @param enrityType  实体类型
     * @return
     */
    long selectCount(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType);

    /**
     * 分页查询
     *
     * @param queryHelper 查询器
     * @param param       参数
     * @param entityType  实体类型
     * @return
     */
    List<T> getPaggingData(QueryHelper queryHelper, Map<String, Object> param, Class<T> entityType);

    /**
     * 添加实体
     *
     * @param entity 实体信息
     */
    void add(T entity);

    /**
     * 批量添加实体
     *
     * @param entityList 实体集合
     * @param entityType 实体类型
     */
    void addBatch(List<T> entityList, Class<T> entityType);

    /**
     * 删除实体
     *
     * @param id         实体ID
     * @param entityType 实体类型
     */
    void delete(Serializable id, Class<T> entityType);

    /**
     * 条件删除实体
     *
     * @param entity     实体
     * @param entityType 实体类型
     */
    void deleteByCondition(T entity, Class<T> entityType);

    /**
     * 更新实体
     *
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 批量更新
     *
     * @param entityList 实体集合
     * @param entityType 实体类型
     */
    void updateBatch(List<T> entityList, Class<T> entityType);

    /**
     * 根据实体ID 查询实体
     *
     * @param entityId   实体ID
     * @param entityType 实体类型
     * @return
     */
    T findById(Serializable entityId, Class<T> entityType);

    /**
     * 逻辑删除
     *
     * @param entityId   实体ID
     * @param entityType 实体类型
     * @return
     */
    int logicDelete(Serializable entityId, Class<T> entityType);
}
