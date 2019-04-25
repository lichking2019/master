package com.wt.master.core.base.support;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import com.wt.master.core.base.BaseDao;
import com.wt.master.core.helper.QueryHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.wt.master.core.reflect.ReflectUtil.*;

import static com.wt.master.core.adapter.SqlParamReflectAdapter.*;

/**
 * Mongo 持久层抽象
 *
 * @author lichking2019@aliyun.com
 * @date Apr 25, 2019 at 12:43:26 PM
 */
public abstract class MongoDaoSupport<T> implements BaseDao<T> {
    /**
     * 实体属性及值
     */
    private Map<String, Object> paramMap;

    private void init(T entity) {
        paramMap = getPropertyAndValue(entity);
    }

    /**
     * 获取 Mongo的操作模板
     *
     * @return
     */
    protected abstract MongoTemplate getMongoTemplate();

    /**
     * 实体类型
     *
     * @return
     */
    protected abstract Class<T> getEntityType();


    @Override
    public List<T> findAll(T entity) {
        init(entity);
        AtomicReference<Query> query = new AtomicReference<>(new Query());
        paramMap.forEach((k, v) -> {
            if (v != null) {
                query.set(new Query(Criteria.where(k).is(v)));
            }
        });
        return getMongoTemplate().find(query.get(), getEntityType());
    }

    @Override
    public List<Map<String, Object>> findallCustom(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType) {
        throw new RuntimeException("mongo不支持关系型查询");
    }

    @Override
    public void add(T entity) {
        getMongoTemplate().save(entity);
    }

    @Override
    public void addBatch(List<T> entityList, Class<T> entityType) {
        entityList.forEach((entity) -> {
            getMongoTemplate().save(entity);
        });
    }

    @Override
    public void delete(Serializable id, Class<T> entityType) {
        Query query = new Query(Criteria.where("id").is(id));
        getMongoTemplate().remove(query, getEntityType());
    }

    @Override
    public void update(T entity) {
        init(entity);
        Object idValue = getPrimaryKeyValue(entity);
        Query query = new Query(Criteria.where("id").is(idValue));
        Update update = new Update();
        paramMap.forEach((column, value) -> {
            if (value != null) {
                update.set(column, value);
            }
        });
        getMongoTemplate().updateMulti(query, update, entity.getClass());
    }

    @Override
    public void updateBatch(List<T> entityList, Class<T> entityType) {
        // TODO: 2019-04-25 下个版本实现
    }

    @Override
    public T findById(Serializable entityId, Class<T> entityType) {
        Query query = new Query(Criteria.where("id").is(entityId));
        return getMongoTemplate().findOne(query, getEntityType());
    }

    @Override
    public int logicDelete(Serializable entityId, Class<T> entityType) {
        // TODO: 2019-04-25 下个版本实现
        return 0;
    }
}
