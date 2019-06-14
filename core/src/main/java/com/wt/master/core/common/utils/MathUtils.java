package com.wt.master.core.common.utils;

import java.text.DecimalFormat;

/**
 * 数学公式工具
 *
 * @author lichking2019@aliyun.com
 * @date May 24, 2019 at 5:12:25 PM
 */
public class MathUtils {
    private DecimalFormat df = new DecimalFormat("##.00");

    /**
     * 转换小数点后两位
     * @param value 值
     * @return
     */
    public static Double doubleFormatDotTwo(Double value) {
        Double result;
        if (value != null) {
            result = Double.parseDouble(String.format("%.2f", value));
        } else {
            String.format("%.2f", "0.0");
            result = Double.parseDouble(String.format("%.2f", "0.0"));
        }
        return result;
    }
}
