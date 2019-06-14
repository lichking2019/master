package com.wt.master.core.common.utils;

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
     * @return
     */
    public static String convertToDivisionByComma(List<String> data){
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
     * @param data
     * @return
     */
    public static String convertToDivisionByCommaNoQuotation(List<String> data){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(data.get(i));
        }
        return result.toString();
    }
}
