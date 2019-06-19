package com.wt.master.core.common.utils;

import com.wt.master.core.exception.BaseErrorException;

import java.util.List;

/**
 * 字符串工具
 *
 * @author lichking2019@aliyun.com
 * @date May 23, 2019 at 3:39:41 PM
 */
public class StringUtils {
    /**
     * 将字符串列表，转换为逗号分割的字符串，每个部分带有双引号
     *
     * @return
     */
    public static String convertToDivisionByComma(List<String> data) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append("\"");
            result.append(data.get(i));
            result.append("\"");
        }
        return result.toString();
    }

    /**
     * 将字符串列表，转换为逗号分割的字符串，每个部分不带双引号
     *
     * @param data
     * @return
     */
    public static String convertToDivisionByCommaNoQuotation(List<String> data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(data.get(i));
        }
        return result.toString();
    }

    /**
     * 首字母小写
     * @param s 目标字符串
     * @return
     */
    public static String toLowerCaseFirstOne(String s){
        if(org.apache.commons.lang3.StringUtils.isBlank(s)){
            throw new BaseErrorException("待转换的字符串不能为空！");
        }
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    /**
     * 首字母大写
     * @param s 目标字符串
     * @return
     */
    public static String toUpperCaseFirstOne(String s){
        if(org.apache.commons.lang3.StringUtils.isBlank(s)){
            throw new BaseErrorException("待转换的字符串不能为空！");
        }
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static void main(String[] args) {
        String name = "";
        String name1 = "Ceshi";
        System.out.println(toUpperCaseFirstOne(name));
        System.out.println(toLowerCaseFirstOne(name1));
    }
}
