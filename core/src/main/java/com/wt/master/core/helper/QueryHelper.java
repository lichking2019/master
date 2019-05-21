package com.wt.master.core.helper;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.wt.master.core.builder.sqlbuilder.SqlBuilder.*;
/**
 * 查询帮助类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 18, 2019 at 12:32:06 PM
 */
public class QueryHelper {
    //查询的列
    private List<String> queryColumnList = new ArrayList<String>();
    private Boolean distinct = false;

    public QueryHelper() {
        BEGIN();
    }


    /**
     * 左连接
     * @param queryColumn 连接目标表要查询出来的类
     * @param joinSql 连接SQL
     * @return
     */
    public QueryHelper LEFT_JOIN(String queryColumn,String joinSql){
        if(StringUtils.isNotBlank(queryColumn)){
            queryColumnList.add(queryColumn);
        }
        LEFT_OUTER_JOIN(joinSql);
        return this;
    }

    /**
     * 内连接
     * @param queryColumn
     * @param joinSql
     * @return
     */
    public QueryHelper JOIN(String queryColumn,String joinSql){
        queryColumnList.add(queryColumn);
        INNER_JOIN(joinSql);
        return this;
    }

    /**
     * 右连接
     * @param queryColumn
     * @param joinSql
     * @return
     */
    public QueryHelper RIGHT_JOIN(String queryColumn,String joinSql){
        queryColumnList.add(queryColumn);
        RIGHT_OUTER_JOIN(joinSql);
        return this;
    }

    public QueryHelper WHERE(String column,CompareType compareType,String value){
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.WHERE(StringUtils.join(column," ",compareType.symbol," ",value));
        return this;
    }

    public QueryHelper ORDER_BY(String column){
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.ORDER_BY(column);
        return this;
    }


    public QueryHelper GROUP_BY(String column){
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.GROUP_BY(column);
        return this;
    }

    public QueryHelper HAVING(String condition){
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.HAVING(condition);
        return this;
    }

    public QueryHelper DISTINCT(){
        distinct = true;
        return this;
    }

    /**
     * 返回SQL
     * @return
     */
    public String getSQL(String table){
        StringBuilder selectSql = new StringBuilder();

        selectSql.append("t.*");
        for (String selectColumn : this.queryColumnList) {
            selectSql.append(",");
            selectSql.append(selectColumn);
        }

        if(distinct){
            SELECT_DISTINCT(selectSql.toString());
        }else{
            SELECT(selectSql.toString());
        }
        FROM(StringUtils.join(table," t"));
        return SQL();
    }


    public enum CompareType{
        EQUAL("等于","=")
        ,GT("大于","&gt;")
        ,LT("小于","&lt;")
        ,GTE("大于等于","&gt;=")
        ,LTE("小于等于","&lt;=")
        ,NOT_EQUAL("不等于","&lt;&gt;")
        ,LIKE("like","like");
        private String name;
        private String symbol;

        CompareType(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

}
