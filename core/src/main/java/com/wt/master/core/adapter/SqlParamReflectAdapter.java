package com.wt.master.core.adapter;

import com.wt.master.core.annotation.Id;
import com.wt.master.core.annotation.Table;

import static com.wt.master.core.reflect.ReflectUtil.*;

import com.wt.master.core.exception.BaseErrorException;
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
        /**
         * 字段名称
         */
        private String fieldName;
        /**
         * 字段值
         */
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
        if (param.size() == 0) {
            throw new BaseErrorException(String.format("实体%s未配置@Id注解！", entity.getClass().getName()));
        }
        param.forEach((k, v) -> {
            fieldDto.setFieldName(k);
            fieldDto.setFieldValue(v);
        });
        return fieldDto;
    }

    /**
     * 获取类型的主键名称
     *
     * @param entityType 实体类型
     * @return 主键名称
     */
    public static String getPrimaryKeyName(Class entityType) {
        Field idField = getClassPropertyUnderAnnotation(entityType, Id.class);
        Id idAnnotation = getFieldAnnotation(idField, Id.class);
        return idAnnotation.value();
    }

    /**
     * 获取实体主键的值
     *
     * @param entity
     * @return
     */
    public static Object getPrimaryKeyValue(Object entity) {
        FieldDto entityIdField = getEntityIdField(entity);
        return entityIdField.getFieldValue();
    }

    /**
     * 获取类型对应的数据库表名
     *
     * @param type 类型
     * @return
     */
    public static String getTableName(Class type) {
        Table classAnnotation = getClassAnnotation(type, Table.class);
        return classAnnotation.tableName();
    }

    /**
     * 设置实体主键的值
     *
     * @param entity 实体
     * @param value  值
     */
    public static void setPrimeryKey(Object entity, String value) {
        Field idField = getClassPropertyUnderAnnotation(entity.getClass(), Id.class);
        try {
            idField.setAccessible(true);
            idField.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new BaseErrorException("设置主键值异常!");
        }
    }
}
