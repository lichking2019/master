package com.wt.master.core.base.support;

import com.mongodb.BasicDBObject;
import com.wt.master.core.helper.QueryHelper;
import com.wt.master.core.reflect.ReflectUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public List<Map> findEntitiesByBson(String bsonSql){
        BasicDBObject bson = new BasicDBObject();
        bson.put("$eval", bsonSql);
        Document tResult = mongoTemplate.getDb().runCommand(bson);
        Document retval = (Document) tResult.get("retval");
        List<Map> dataList = (ArrayList) retval.get("_batch");
        return dataList;
    }

    /**
     * 使用mongo原生语句查询结果
     * @param bsonSql mongo原生sql
     * @return
     */
    public List<T> findEntitiesByBson(String bsonSql,Class<T> type){
        List<T> result = new ArrayList<>();
        List<Map> dataList = findEntitiesByBson(bsonSql);

        List<Field> allField = ReflectUtil.getAllField(type);
        for (Map data : dataList) {
            try {
                T entity = (T)type.newInstance();
                for (Field declaredField : allField) {
                    data.forEach((k,v)->{
                        declaredField.setAccessible(true);
                        if(declaredField.getName().equals(k)){
                            try {
                                declaredField.set(entity,v);
                            } catch (IllegalAccessException e) {
                                // TODO: 2019-05-21 异常封装
                                throw new RuntimeException("反射异常",e);
                            }
                        }
                    });
                }
                result.add(entity);
            } catch (Exception e) {
                // TODO: 2019-05-21 异常封装
                throw new RuntimeException("反射出错",e);
            }
        }
        return result;
    }


//    public static final String SELECT = "select";
//    public static final String ID = "id";

//    /**
//     * 读取mongo xml文件中的sql语句
//     *
//     * @param configFilePath xml位置
//     * @param id             对应的bson语句标识
//     * @param params         参数
//     * @return
//     */
//    public static String readMongoSql(String configFilePath, String id, String... params) {
//        String result = "";
//        if (StringUtils.isBlank(configFilePath) || StringUtils.isBlank(id)) {
//            // TODO: 2019-05-21 异常封装
//            throw new RuntimeException("传入参数非法");
//        }
//
//        ClassPathResource resource = new ClassPathResource("classpath*:"+configFilePath);
//        Assert.isTrue(resource.exists(), "mongo的bson文件不存在");
//        org.jdom.Document document = null;
//        try {
//            org.jdom.Document build = new SAXBuilder().build(resource.getFile());
//            document = build;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("读取mongo 语句的xml文件报错,文件路径：" + configFilePath,e);
//        }
//        Element root = document.getRootElement();
//        List<Element> sql = root.getChildren(SELECT);
//
//        if (CollectionUtils.isEmpty(sql)) {
//            throw new RuntimeException("mongo的bson配置文件格式不对");
//        }
//        Element target = null;
//        for (Element element : sql) {
//            if (id.equals(element.getAttributeValue(ID))) {
//                target = element;
//            }
//        }
//        result = target.getText();
//        if (params.length > 0) {
//            result = String.format(result, params);
//        }
//        return result;
//    }
}
