package com.wt.master.core.base.support;

import com.wt.master.core.helper.MapperSqlHelper;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper基类
 *
 * @param <T>
 */
public interface MapperSupport<T> {
    String ENTITY = "entity";
    String ENTITIES = "entities";
    String ID = "id";
    String ENTITYTYPE = "entityType";

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
    @InsertProvider(type = MapperSqlHelper.class, method = "addBatch")
    void addBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 删除实体
     *
     * @param id 实体ID
     */
    @DeleteProvider(type = MapperSqlHelper.class, method = "delete")
    void delete(@Param(ID) Serializable id, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 更新实体
     *
     * @param securityUser 实体信息
     */
    @UpdateProvider(type = MapperSqlHelper.class, method = "update")
    void update(@Param(ENTITY) T entity);

    /**
     * 批量更新实体
     *
     * @param securityUserList 实体信息
     */
    @UpdateProvider(type = MapperSqlHelper.class, method = "updateBatch")
    void updateBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 根据实体ID 查询实体
     *
     * @param userId 实体ID
     * @return
     */
    // 【知识点】，Mybatis返回的查询结果不一定都是集合，如果要是使用单个对象接收，那么Mybatis会智能组装成单一对象，而不用List.get(0)
    @SelectProvider(type = MapperSqlHelper.class, method = "findById")
    T findById(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 逻辑删除
     *
     * @param userId 实体ID
     */
    @UpdateProvider(type = MapperSqlHelper.class, method = "logicDelete")
    int logicDelete(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);
}
