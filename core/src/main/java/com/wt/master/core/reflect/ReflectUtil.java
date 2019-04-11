package com.wt.master.core.reflect;

import org.springframework.util.Assert;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射工具类
 */
public class ReflectUtil {

    /**
     * 获取对象的属性当中，具备特定注解的属性的值
     * @param entity 对象
     * @param annotationType 注解
     * @return
     */
    public static Map<String,Object> getParameterValueOnAnnotation(Object entity,Class annotationType){
        Map<String,Object> result = new HashMap<>();
        Class<?> aClass = entity.getClass();
        List<Field> allField = getAllField(entity);
        for (Field field : allField) {
            if(field.isAnnotationPresent(annotationType)){
                field.setAccessible(true);
                try {
                    result.put(field.getName(),field.get(entity));
                } catch (IllegalAccessException e) {
                    // TODO: 2019-04-10 异常处理
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 获取对象的属性及值
     * @param entity 对象
     * @return key为属性名称,value为属性值
     */
    public static Map<String,Object> getPropertyAndValue(Object entity){
        Map<String,Object> result = new HashMap<>();
        List<Field> allField = getAllField(entity);
        for (Field field : allField) {
            //打开权限
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                result.put(field.getName(),value);
            } catch (IllegalAccessException e) {
                // TODO: 2019-04-10 异常处理
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取实体的所有属性
     * @param entity 实体
     * @return
     */
    public static List<Field> getAllField(Object entity){
        Class z = entity.getClass();
        List<Field> allField = new ArrayList<>();
        Field[] fields = z.getDeclaredFields();
        Collections.addAll(allField,fields);
        return getParentDeclaredFields(z.getSuperclass(),allField);
    }

//    /**
//     * 获取实体 的注解
//     * @param entityType 实体类型
//     * @param annotationType 注解类型
//     * @return
//     */
//    public static List<Map<String,Object>> getClassAnnotationAttribute(Class entityType,Class annotationType){
//        Annotation[] annotationsByType = entityType.getAnnotationsByType(annotationType);
//        List<Map<String,Object>> result = new ArrayList<>();
//        for (Annotation annotation : annotationsByType) {
//            Map<String,Object> annotationAttribute = new HashMap<>();
//            Method[] declaredMethods = annotation.getClass().getDeclaredMethods();
//            for (Method declaredMethod : declaredMethods) {
//                declaredMethod.setAccessible(true);
//                try {
//                    Object value = declaredMethod.invoke(annotation);
//                    annotationAttribute.put(declaredMethod.getName(),value);
//                } catch (IllegalAccessException e) {
//                    // TODO: 2019-04-10 异常处理
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                result.add(annotationAttribute);
//            }
//        }
//        return result;
//    }

    /**
     * 获取类的指定注解
     * @param entityType 实体的类型
     * @param annotationType 注解的类型
     * @param <T> 注解类型
     * @return
     */
    public static <T> T getClassAnnotation(Class entityType,Class<T> annotationType){
        Assert.isTrue(entityType.isAnnotationPresent(annotationType),String.format("类：%s 未配置@%s注解",entityType.getSimpleName(),annotationType.getSimpleName()));
        return (T)entityType.getAnnotation(annotationType);
    }


    /**
     * 获取所有父类的属性
     * @return
     */
    private static List<Field> getParentDeclaredFields(Class parentClass,List<Field> allFeild){
        if(Object.class.equals(parentClass)){
            return allFeild;
        }
        Field[] fields = parentClass.getDeclaredFields();
        Collections.addAll(allFeild,fields);
        return getParentDeclaredFields(parentClass.getSuperclass(),allFeild);
    }

}
