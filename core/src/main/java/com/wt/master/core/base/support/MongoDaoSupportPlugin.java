package com.wt.master.core.base.support;

import com.wt.master.core.helper.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.wt.master.core.adapter.SqlParamReflectAdapter.getPrimaryKeyValue;
import static com.wt.master.core.reflect.ReflectUtil.getPropertyAndValue;

/**
 * Mongo 持久层抽象
 *
 * @author lichking2019@aliyun.com
 * @date Apr 25, 2019 at 12:43:26 PM
 */
@Component
public class MongoDaoSupportPlugin<T> {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 实体属性及值
     */
    private Map<String, Object> paramMap;

    private void init(T entity) {
        paramMap = getPropertyAndValue(entity);
    }

    public List<T> findAll(T entity, Class<T> z) {
        init(entity);
        AtomicReference<Query> query = new AtomicReference<>(new Query());
        paramMap.forEach((k, v) -> {
            if (v != null) {
                query.set(new Query(Criteria.where(k).is(v)));
            }
        });
        return mongoTemplate.find(query.get(), z);
    }


    public List<Map<String, Object>> findallCustom(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType) {
        // TODO: 2019-04-28 异常封装
        throw new RuntimeException("mongo不支持关系型查询");
    }


    public List<T> findAllEntityCustom(QueryHelper queryHelper, Map<String, Object> param, Class<T> enrityType) {
        // TODO: 2019-04-28 异常封装 
        throw new RuntimeException("mongo不支持关系型查询");
    }


    public void add(T entity) {
        mongoTemplate.save(entity);
    }


    public void addBatch(List<T> entityList, Class<T> entityType) {
        entityList.forEach((entity) -> {
            mongoTemplate.save(entity);
        });
    }


    public void delete(Serializable id, Class<T> entityType) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, entityType);
    }


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
        mongoTemplate.updateMulti(query, update, entity.getClass());
    }


    public void updateBatch(List<T> entityList, Class<T> entityType) {
        // TODO: 2019-04-25 下个版本实现 
    }


    public T findById(Serializable entityId, Class<T> entityType) {
        Query query = new Query(Criteria.where("id").is(entityId));
        return mongoTemplate.findOne(query, entityType);
    }


    public int logicDelete(Serializable entityId, Class<T> entityType) {
        // TODO: 2019-04-25 下个版本实现
        return 0;
    }

}
