package com.wt.master.core.base.support;

import com.wt.master.core.adapter.SqlParamReflectAdapter;
import com.wt.master.core.base.BaseDao;
import com.wt.master.core.base.BaseEntity;
import com.wt.master.core.base.BaseService;
import com.wt.master.core.common.utils.UUIDUtils;
import com.wt.master.core.exception.BaseErrorException;
import com.wt.master.core.helper.QueryHelper;
import com.wt.master.core.page.PageHelperFactory;
import com.wt.master.core.page.QueryResult;
import com.wt.master.core.page.impl.DataTableResult;
import com.wt.master.core.property.SystemConfigProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
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
    public List<T> findAll() {
        try {
            T entity = getEntityType().newInstance();
            return getMapper().findAll(entity);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BaseErrorException("反射创建实例失败", e);
        }
    }

    @Override
    public List<T> findAll(T entity) {
        return getMapper().findAll(entity);
    }

    @Override
    public List<Map<String, Object>> findAll(QueryHelper queryHelper, Map<String, Object> param) {
        return getMapper().findAllCustom(queryHelper, param, getEntityType());
    }

    @Override
    public QueryResult<T> getPaggingData(QueryHelper queryHelper, Map<String, Object> param) {
        QueryResult queryResult = queryHelper.getQueryResult();
        List<T> paggingData = getMapper().getPaggingData(queryHelper, param, getEntityType());
        long count = getMapper().selectCount(queryHelper,param,getEntityType());
        queryResult.setData(paggingData);
        queryResult.setRecordsTotal(count);
        return queryResult;
    }

    @Override
    public List<T> findAllEntityCustom(QueryHelper queryHelper, Map<String, Object> param) {
        return getMapper().findAll(queryHelper, param, getEntityType());
    }

    @Override
    public void add(T entity) {
        initEntityBaseInfo(entity);
        getMapper().add(entity);
    }

    /**
     * 批量初始化实体的基础信息
     */
    private void initEntityBaseInfoBatch(List<T> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            initEntityBaseInfo(entityList.get(i));
        }
    }

    /**
     * 初始化实体的基础信息
     */
    private void initEntityBaseInfo(T entity) {
        //设置主键
        SqlParamReflectAdapter.setPrimeryKey(entity, UUIDUtils.getUUID32());
        //设置创建时间、创建人信息
        ((BaseEntity) entity).setCreateDateTime(new Date());
        ((BaseEntity) entity).setFounderId("-1");
        ((BaseEntity) entity).setFounderName("系统创建");
    }

    @Override
    public void delete(Serializable id) {
        getMapper().delete(id, getEntityType());
    }

    @Override
    public void deleteByCondition(T entity) {
        getMapper().deleteByCondition(entity, getEntityType());
    }

    @Override
    public void update(T entity) {
        getMapper().update(entity);
    }

    @Override
    public T findById(Serializable entityId) {
        return getMapper().findById(entityId, getEntityType());
    }

    @Override
    public int logicDelete(Serializable entityId) {
        return getMapper().logicDelete(entityId, getEntityType());
    }

    @Override
    public void addBatch(List<T> entityList) {
        initEntityBaseInfoBatch(entityList);
        getMapper().addBatch(entityList, getEntityType());
    }

    @Override
    public void updateBatch(List<T> entityList) {
        getMapper().updateBatch(entityList, getEntityType());
    }

    /**
     * 创建结果对象
     * @return
     */
    protected QueryResult newQueryResult(){
        if(StringUtils.isBlank((String)SystemConfigProperties.getInstance().getTable().get("type"))){
            return new DataTableResult();
        }
        return PageHelperFactory.create().createQueryResult();
    }
}
