package com.wt.master.core.adapter;

import com.wt.master.core.annotation.Id;
import com.wt.master.core.annotation.Table;
import com.wt.master.core.reflect.ReflectUtil;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 反射适配，供拼装Sql的服务使用
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
        Map<String, Object> param = ReflectUtil.getParameterValueOnAnnotation(entity, Id.class);
        Assert.isTrue(param.size() ==1, String.format("类：%s 配置了多个@Id注解，或未配置@Id注解", entity.getClass().getSimpleName()));
        param.forEach((k, v) -> {
            fieldDto.setFieldName(k);
            fieldDto.setFieldValue(v);
        });
        return fieldDto;
    }

    /**
     * 获取实体对应的数据库表名
     * @param entityClass 实体类型
     * @return
     */
    public static String getTableName(Class entity){
        Table classAnnotation = ReflectUtil.getClassAnnotation(entity, Table.class);
        return classAnnotation.tableName();
    }
}
