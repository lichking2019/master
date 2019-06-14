package com.wt.master.core.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
 *
 * @author lichking2019@aliyun.com
 * @date Apr 26, 2019 at 10:29:59 AM
 */
public class DateTimeUtils {
    private static Calendar calendar = null;

    public static String dateFormat(String date, String dateFormat) throws ParseException {
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        SimpleDateFormat simpleDateFormatAfter = new SimpleDateFormat(dateFormat);

        Date parse = simpleDateFormat.parse(date);
        return simpleDateFormatAfter.format(parse);

    }

    /**
     * 获取月份的最后一天
     *
     * @param yearAndMonth 月份 格式为2018-10
     * @return
     */
    public static String getMothFirstOrLastDay(String yearAndMonth, FirstOrEnd firstOrEnd, String dateFormat) {
        String[] date = getYearAndMonth(yearAndMonth);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
        int day = 0;

        switch (firstOrEnd) {
            case FIRST:
                day = calendar.getActualMinimum(Calendar.DATE);
                break;
            case LAST:
                day = calendar.getActualMaximum(Calendar.DATE);
                break;
            default:
                day = 1;
        }
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定年份的 第一天和 最后一天
     *
     * @param year 年份
     * @return
     */
    public static String getYearFirstOrLastDay(String year, FirstOrEnd firstOrEnd, String dateFormat) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        int month = 1;
        int day = 1;
        switch (firstOrEnd) {
            case FIRST:
                month = calendar.getActualMinimum(Calendar.MONTH);
                day = calendar.getActualMinimum(Calendar.DATE);
                break;
            case LAST:
                month = calendar.getActualMaximum(Calendar.MONTH);
                day = calendar.getActualMaximum(Calendar.DATE);
                break;
            default:
                month = 1;
                day = 1;
        }
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份的格式化结果，保持原有格式
     */
    public static String getOrignalMonthFormat(String yearAndMonth,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            Date parse = simpleDateFormat.parse(yearAndMonth);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(format);
            return simpleDateFormat1.format(parse);
        } catch (ParseException e) {
            // TODO: 2019-05-24 异常封装
            throw new RuntimeException("日期格式化出错");
        }
    }


    private static String[] getYearAndMonth(String yearAndMonth) {
        return yearAndMonth.split("-");
    }

    /**
     * 第一天或者最后一天的枚举
     */
    public enum FirstOrEnd {//第一天
        FIRST
        //最后一天
        , LAST}

}
