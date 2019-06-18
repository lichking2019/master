package com.wt.master.core.base.support;

import com.wt.master.core.base.BaseDao;
import com.wt.master.core.base.BaseEntity;
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

    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll")
    List<T> findAll(@Param(ENTITY) T entity);

    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll_custom")
    List<T> findAll(@Param(QUERY_HELPER) QueryHelper queryHelper, @Param(PARAM) Map<String, Object> param,
                    @Param(ENTITYTYPE) Class<T> enrityType);

    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findAll_custom")
    List<Map<String, Object>> findAllCustom(@Param(QUERY_HELPER) QueryHelper queryHelper, @Param(PARAM) Map<String,
            Object> param, @Param(ENTITYTYPE) Class<T> enrityType);

    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "selectCount")
    long selectCount(@Param(QUERY_HELPER) QueryHelper queryHelper, @Param(PARAM) Map<String, Object> param,
                     @Param(ENTITYTYPE) Class<T> enrityType);


    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "getPaggingData")
    List<T> getPaggingData(@Param(QUERY_HELPER) QueryHelper queryHelper, @Param(PARAM) Map<String, Object> param,
                           @Param(ENTITYTYPE) Class<T> entityType);

    @Override
    @InsertProvider(type = MapperSqlHelper.class, method = "add")
    void add(@Param(ENTITY) T entity);

    @Override
    @InsertProvider(type = MapperSqlHelper.class, method = "addBatch")
    void addBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    @Override
    @DeleteProvider(type = MapperSqlHelper.class, method = "delete")
    void delete(@Param(ID) Serializable id, @Param(ENTITYTYPE) Class<T> entityType);

    @DeleteProvider(type = MapperSqlHelper.class, method = "deleteByCondition")
    void deleteByCondition(@Param(ENTITY) T entity, @Param(ENTITYTYPE) Class<T> entityType);

    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "update")
    void update(@Param(ENTITY) T entity);

    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "updateBatch")
    void updateBatch(@Param(ENTITIES) List<T> entityList, @Param(ENTITYTYPE) Class<T> entityType);

    @Override
    @SelectProvider(type = MapperSqlHelper.class, method = "findById")
    T findById(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);

    @Override
    @UpdateProvider(type = MapperSqlHelper.class, method = "logicDelete")
    int logicDelete(@Param(ID) Serializable entityId, @Param(ENTITYTYPE) Class<T> entityType);

}
