package com.wt.master.core.base.support;

import com.wt.master.core.base.BaseDao;
import com.wt.master.core.helper.MapperSqlHelper;
import com.wt.master.core.helper.QueryHelper;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Mybatis持久层基类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:26:17 PM
 */
public interface MapperSupport<T> extends BaseDao<T> {
    String ENTITY = "entity";
    String ENTITIES = "entities";
    String ID = "id";
    String ENTITYTYPE = "entityType";
    String PARAM = "param";
    String QUERY_HELPER = "queryHelper";

    /**
     * 查询所有实体
     *
     * @return 实体集合
     */
    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll")
    List<T> findAll(@Param(ENTITY) T entity);

    /**
     * 查询所有实体
     *
     * @param queryHelper 查询辅助类
     * @param enrityType  实体类型
     * @return
     */
    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll_custom")
    List<Map<String, Object>> findallCustom(@Param(QUERY_HELPER) QueryHelper queryHelper, @Param(PARAM) Map<String, Object> param, @Param(ENTITYTYPE) Class<T> enrityType);

    /**
     * 添加实体
     *
     * @param securityUser 实体信息
     */
    @Override
    @InsertProvider(type = MapperSqlHelper.class, method = "add")
    void add(@Param(ENTITY) T entity);

    /**
     * 批量添加实体
     *
     * @param securityUserList 实体信息集合
     */
    @Override
    @InsertProvider(type = MapperSqlHelper.class, method = "addBatch")
    void addBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 删除实体
     *
     * @param id 实体ID
     */
    @Override
    @DeleteProvider(type = MapperSqlHelper.class, method = "delete")
    void delete(@Param(ID) Serializable id, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 更新实体
     *
     * @param securityUser 实体信息
     */
    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "update")
    void update(@Param(ENTITY) T entity);

    /**
     * 批量更新实体
     *
     * @param securityUserList 实体信息
     */
    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "updateBatch")
    void updateBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 根据实体ID 查询实体
     *
     * @param userId 实体ID
     * @return
     */
    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findById")
    T findById(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);

    /**
     * 逻辑删除
     *
     * @param userId 实体ID
     */
    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "logicDelete")
    int logicDelete(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);


}
