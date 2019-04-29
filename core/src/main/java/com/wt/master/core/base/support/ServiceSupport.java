package com.wt.master.core.base.support;

import com.wt.master.core.adapter.SqlParamReflectAdapter;
import com.wt.master.core.base.BaseDao;
import com.wt.master.core.base.BaseEntity;
import com.wt.master.core.base.BaseService;
import com.wt.master.core.common.utils.UUIDUtils;
import com.wt.master.core.helper.QueryHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * service基类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:26:40 PM
 */

public abstract class ServiceSupport<T, M extends BaseDao<T>> implements BaseService<T> {
    /**
     * 获取Mapper由具体的实现类实现
     *
     * @return
     */
    protected abstract M getMapper();

    protected abstract Class<T> getEntityType();

    @Override
    public List<T> findAll(){
        try {
            T entity = getEntityType().newInstance();
            return getMapper().findAll(entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /**
     * 查询所有实体信息
     *
     * @return 实体集合
     */
    @Override
    public List<T> findAll(T entity) {
        return getMapper().findAll(entity);
    }

    /**
     * 根据查询条件，查询所有的实体信息
     * @param queryHelper sql辅助类
     * @return
     */
    @Override
    public List<Map<String,Object>> findAll(QueryHelper queryHelper, Map<String,Object> param){
        return getMapper().findallCustom(queryHelper,param,getEntityType());
    }

    /**
     * 条件查询所有实体
     * @param queryHelper
     * @param param
     * @return
     */
    public List<T> findAllEntityCustom(QueryHelper queryHelper, Map<String,Object> param){
        return getMapper().findAllEntityCustom(queryHelper,param,getEntityType());
    }


    /**
     * 添加单个实体
     *
     * @param entity 实体信息
     */
    @Override
    public void add(T entity) {
        initEntityBaseInfo(entity);
        getMapper().add(entity);
    }

    /**
     * 批量初始化实体的基础信息
     */
    private void initEntityBaseInfoBatch(List<T> entityList){
        for (int i = 0; i < entityList.size(); i++) {
            initEntityBaseInfo(entityList.get(i));
        }
    }

    /**
     * 初始化实体的基础信息
     */
    private void initEntityBaseInfo(T entity){
        //设置主键
        SqlParamReflectAdapter.setPrimeryKey(entity, UUIDUtils.getUUID32());
        //设置创建时间、创建人信息
        ((BaseEntity)entity).setCreateDateTime(new Date());
        ((BaseEntity)entity).setFounderId("-1");
        ((BaseEntity)entity).setFounderName("系统创建");
    }

    /**
     * 物理删除实体
     *
     * @param id 实体ID
     * @param entityType 实体类型
     */

    @Override
    public void delete(Serializable id) {
        getMapper().delete(id,getEntityType());
    }

    /**
     * 更新实体
     *
     * @param entity 实体信息
     */
    @Override
    public void update(T entity) {
        getMapper().update(entity);
    }

    /**
     * 根据实体ID，查询实体信息
     *
     * @param entityId 实体ID
     * @return
     */
    @Override
    public T findById(Serializable entityId) {
        return getMapper().findById(entityId,getEntityType());
    }

    /**
     * 逻辑删除实体
     *
     * @param entityId
     * @return 删除的实体ID
     */
    @Override
    public int logicDelete(Serializable entityId) {
        return getMapper().logicDelete(entityId,getEntityType());
    }

    /**
     * 批量添加实体
     *
     * @param entityList 实体信息集合
     */
    @Override
    public void addBatch(List<T> entityList) {
        initEntityBaseInfoBatch(entityList);
        getMapper().addBatch(entityList,getEntityType());
    }

    /**
     * 批量更新实体
     *
     * @param entityList 实体信息集合
     */
    @Override
    public void updateBatch(List<T> entityList) {
        getMapper().updateBatch(entityList,getEntityType());
    }
}
