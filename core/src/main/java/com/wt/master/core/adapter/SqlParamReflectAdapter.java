package com.wt.master.core.adapter;

import com.wt.master.core.annotation.Id;
import com.wt.master.core.annotation.Table;

import static com.wt.master.core.reflect.ReflectUtil.*;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 反射适配，供拼装Sql的服务使用
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:19:39 PM
 */
public class SqlParamReflectAdapter {
    /**
     * 字段传输对象
     */
    static class FieldDto {
        private String fieldName;
        private Object fieldValue;

        public FieldDto() {
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Object getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(Object fieldValue) {
            this.fieldValue = fieldValue;
        }
    }

    /**
     * 获取实体的主键属性
     *
     * @param entity 实体
     * @return
     */
    public static FieldDto getEntityIdField(Object entity) {
        FieldDto fieldDto = new FieldDto();
        Map<String, Object> param = getParameterValueOnAnnotation(entity, Id.class);
        Assert.isTrue(param.size() == 1, String.format("类：%s 配置了多个@Id注解，或未配置@Id注解", entity.getClass().getSimpleName()));
        param.forEach((k, v) -> {
            fieldDto.setFieldName(k);
            fieldDto.setFieldValue(v);
        });
        return fieldDto;
    }

    /**
     * 获取主键名称
     *
     * @param entityType 实体类型
     * @return 主键名称
     */
    public static String getPrimaryKeyName(Class entityType) {
        Field idField = getClassPropertyUnderAnnotation(entityType, Id.class);
        Id idAnnotation = getFieldAnnotation(idField, Id.class);
        return idAnnotation.value();
    }

    public static Object getPrimaryKeyValue(Object entity) {
        Field idField = getClassPropertyUnderAnnotation(entity.getClass(), Id.class);
        try {
            idField.setAccessible(true);
            return idField.get(entity);
        } catch (IllegalAccessException e) {
            // TODO: 2019-04-25 考虑异常封装
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取实体对应的数据库表名
     *
     * @param entityClass 实体类型
     * @return
     */
    public static String getTableName(Class entity) {
        Table classAnnotation = getClassAnnotation(entity, Table.class);
        return classAnnotation.tableName();
    }

    /**
     * 设置实体主键的值
     *
     * @param entity
     */
    public static void setPrimeryKey(Object entity, String value) {
        Field idField = getClassPropertyUnderAnnotation(entity.getClass(), Id.class);
        try {
            idField.setAccessible(true);
            idField.set(entity, value);
        } catch (IllegalAccessException e) {
            // TODO: 2019-04-26 考虑异常封装
            e.printStackTrace();
        }
    }
}
