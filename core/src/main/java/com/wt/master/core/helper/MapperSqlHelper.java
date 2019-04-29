package com.wt.master.core.helper;

//import static org.apache.ibatis.jdbc.SelectBuilder.*;

import static com.wt.master.core.builder.sqlbuilder.SqlBuilder.*;

import static com.wt.master.core.reflect.ReflectUtil.*;
import static com.wt.master.core.adapter.SqlParamReflectAdapter.*;

import com.wt.master.core.annotation.Id;
import com.wt.master.core.annotation.Transparent;
import com.wt.master.core.base.support.MapperSupport;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * mybatis SQL 语句提供
 * 思路，通过反射获取实体属性及值，然后利用mybatis的SQLbuilder工具拼SQL，返回给MyBatis
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:33:14 PM
 */
public class MapperSqlHelper {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键名
     */
    private String primaryKeyName;
    /**
     * 实体
     */
    private Object entity;
    /**
     * 实体类型
     */
    private Class entityType;
    /**
     * 实体属性及值
     */
    private Map<String, Object> paramMap;

    private void init(Map<String, Object> param) {
        if (param.containsKey(MapperSupport.ENTITY)) {
            entity = param.get(MapperSupport.ENTITY);
            paramMap = getPropertyAndValue(entity);
            tableName = getTableName(entity.getClass());
            primaryKeyName = getPrimaryKeyName(entity.getClass());
        }
        if (param.containsKey(MapperSupport.ENTITYTYPE)) {
            entityType = (Class) param.get(MapperSupport.ENTITYTYPE);
            primaryKeyName = getPrimaryKeyName(entityType);
            tableName = getTableName(entityType);
        }
    }


    /**
     * 查询所有的实体数据
     *
     * @param params
     * @return
     */
    public String findAll(Map<String, Object> param) {
        //初始化，考虑多线程得因素
        BEGIN();
        init(param);
        //拼SQL
        SELECT("t.*");
        FROM(tableName + " t");
        paramMap.forEach((column, value) -> {
            if (value != null) {
                WHERE(getEqualsSql(StringUtils.join("t.", column), StringUtils.join(MapperSupport.ENTITY, ".", column)));
            }
        });
        WHERE("t.deleteFlag=false");
        return SQL();
    }

    /**
     * 自定义查询
     * @param param
     * @return
     */
    public String findAll_custom(Map<String, Object> param){
        init(param);
        QueryHelper queryHelper = (QueryHelper)param.get(MapperSupport.QUERY_HELPER);
        return queryHelper.getSQL(tableName);
    }

    /**
     * 根据主键查找数据
     *
     * @param param 参数
     * @return 实体
     */
    public String findById(Map<String, Object> param) {
        BEGIN();
        init(param);
        SELECT("*");
        FROM(tableName);
        WHERE(getEqualsSql(primaryKeyName, MapperSupport.ID));
        WHERE("deleteFlag = false");
        return SQL();
    }

    /**
     * 插入数据
     *
     * @param param 参数
     * @return 执行的SQL
     */
    public String add(Map<String, Object> param) {
        BEGIN();
        init(param);
        //拼SQL
        INSERT_INTO(tableName);
        paramMap.forEach((column, value) -> {
            if (value != null) {
                VALUES(column, getEqualsSql(StringUtils.join(MapperSupport.ENTITY, ".", column)));
            }
        });
        return SQL();

    }

    /**
     * 批量新增
     *
     * @param param 参数
     * @return SQL
     */
    public String addBatch(Map<String, Object> param) {
        BEGIN();
        init(param);
        INSERT_INTO_BATCH(tableName);

        Class entityType = (Class) param.get(MapperSupport.ENTITYTYPE);

        List<Field> allField = getAllField(entityType);

        for (Field field : allField) {
            if (isIgnore(field)) {
                continue;
            }
            COLUMNS(field.getName());
        }

        List entityList = (List) param.get(MapperSupport.ENTITIES);
        for (int i = 0; i < entityList.size(); i++) {
            Object entity = entityList.get(i);
            List<Field> fieldList = getAllField(entity);
            String primeryKey = getPrimaryKeyName(entity.getClass());
            for (Field field : fieldList) {
                //注解及标注了忽略注解的字段不处理
                if (isIgnore(field)) {
                    continue;
                }
                VALUES_SINGLE_RECORD(getEqualsSql(StringUtils.join(MapperSupport.ENTITIES, "[", i, "]", ".", field.getName())));
            }
            GENERATESINGLERECORDVALUES();
        }

        return SQL();
    }

    /**
     * 删除单个实体
     *
     * @param param 参数
     * @return SQL语句
     */
    public String delete(Map<String, Object> param) {
        BEGIN();
        init(param);
        DELETE_FROM(tableName);
        WHERE(getEqualsSql(primaryKeyName, MapperSupport.ID));
        return SQL();
    }

    /**
     * 逻辑删除
     *
     * @param param
     * @return
     */
    public String logicDelete(Map<String, Object> param) {
        BEGIN();
        init(param);
        UPDATE(tableName);
        SET(StringUtils.join("deleteFlag=true"));
        WHERE(getEqualsSql(primaryKeyName, MapperSupport.ID));
        return SQL();
    }

    /**
     * 更新实体，条件更新
     *
     * @param param 参数
     * @return
     */
    public String update(Map<String, Object> param) {
        BEGIN();
        init(param);
        UPDATE(tableName);
        paramMap.forEach((column, value) -> {
            if (value != null) {
                SET(getEqualsSql(column, StringUtils.join(MapperSupport.ENTITY, ".", column)));
            }
        });
        WHERE(getEqualsSql(primaryKeyName, StringUtils.join(MapperSupport.ENTITY, ".", primaryKeyName)));
        return SQL();
    }

    /**
     * 批量更新
     *
     * @param param 参数
     * @return
     */
    public String updateBatch(Map<String, Object> param) {
        BEGIN();
        init(param);

        List<String> keyValues = new ArrayList<>();
        List<Field> fieldList = getAllField(entityType);
        List entityList = (List) param.get(MapperSupport.ENTITIES);
        for (int i = 0; i < entityList.size(); i++) {
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Id.class)) {
                    keyValues.add(getEqualsSql(StringUtils.join(MapperSupport.ENTITIES, "[" + i + "]" + "." + field.getName())));
                }
            }
        }

        for (Field field : fieldList) {
            String caseSQL = StringUtils.join(field.getName(), " = CASE ", primaryKeyName);
            for (int i = 0; i < entityList.size(); i++) {
                StringBuilder whenSQL = new StringBuilder();
                whenSQL.append("WHEN ");
                whenSQL.append(getEqualsSql(StringUtils.join(MapperSupport.ENTITIES, "[", i, "]", ".", primaryKeyName)) + " ");
                whenSQL.append(StringUtils.join("THEN ", getEqualsSql(StringUtils.join(MapperSupport.ENTITIES, "[", i, "]", ".", field.getName()))));
                buildCaseSQL(caseSQL, whenSQL.toString());
            }
        }

        UPDATE(tableName);
        buildBatchSetSQL();
        WHERE_IN(primaryKeyName, keyValues);
        return SQL();
    }


    private String getEqualsSql(String columnName, String value) {
        return StringUtils.join(columnName, "=#{", value, "}");
    }

    private String getEqualsSql(String value) {
        return StringUtils.join("#{", value, "}");
    }

    /**
     * 判断该字段是否需要忽略入库操作
     *
     * @param field 字段
     * @return 是否忽略
     */
    private boolean isIgnore(Field field) {
        return field.isAnnotationPresent(Transparent.class);
    }
}
