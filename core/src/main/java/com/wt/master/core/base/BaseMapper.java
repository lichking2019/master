package com.wt.master.core.base;

import com.wt.master.core.helper.MapperSqlHelper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper基类
 *
 * @param <T>
 */
public interface BaseMapper<T> {
    String ENTITY = "entity";
    String ENTITIES = "entities";
    String ID = "id";

    /**
     * 查询所有实体
     *
     * @return 实体集合
     */
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll")
    List<T> findAll(@Param(ENTITY) T entity);

    /**
     * 添加实体
     *
     * @param securityUser 实体信息
     */
    @InsertProvider(type = MapperSqlHelper.class, method = "add")
    void add(@Param(ENTITY) T entity);

    /**
     * 批量添加实体
     *
     * @param securityUserList 实体信息集合
     */
    void addBatch(@Param(ENTITIES) List<T> entityList);

    /**
     * 删除实体
     *
     * @param id 实体ID
     */
    void delete(@Param(ID) Serializable id);

    /**
     * 更新实体
     *
     * @param securityUser 实体信息
     */
    void update(@Param(ENTITY) T entity);

    /**
     * 批量更新实体
     *
     * @param securityUserList 实体信息
     */
    void updateBatch(@Param(ENTITIES) List<T> entityList);

    /**
     * 根据实体ID 查询实体
     *
     * @param userId 实体ID
     * @return
     */
    // 【知识点】，Mybatis返回的查询结果不一定都是集合，如果要是使用单个对象接收，那么Mybatis会智能组装成单一对象，而不用List.get(0)
    T findById(@Param(ID) Serializable entityId);

    /**
     * 逻辑删除
     *
     * @param userId 实体ID
     */
    int logicDelete(@Param(ID) Serializable entityId);
}
