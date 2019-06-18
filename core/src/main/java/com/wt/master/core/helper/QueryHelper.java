package com.wt.master.core.helper;

import com.wt.master.core.common.utils.RequestContextUtils;
import com.wt.master.core.page.PageHelper;
import com.wt.master.core.page.PageHelperFactory;
import com.wt.master.core.page.QueryResult;
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
    /**
     * 查询的列
     */
    private List<String> queryColumnList = new ArrayList<String>();
    /**
     * 是否去重
     */
    private Boolean distinct = false;
    /**
     * 第一条数据的起始位置
     */
    private long start;
    /**
     * 每页显示的条数
     */
    private long length;

    /**
     * 分页结果集
     */
    private QueryResult queryResult;


    public QueryHelper() {
        BEGIN();
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStart() {
        return this.start;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLength() {
        return this.length;
    }

    public QueryResult getQueryResult(){
        return this.queryResult;
    }

    /**
     * 分页初始化，设置分页参数
     *
     * @return
     */
    public QueryHelper pageInit() {
        PageHelper pageHelper = PageHelperFactory.create();
        pageHelper.setPageInfo(this, RequestContextUtils.getHttpServletRequest());
        this.queryResult = pageHelper.createQueryResult();
        return this;
    }


    /**
     * 左连接
     *
     * @param queryColumn 连接目标表要查询出来的类
     * @param joinSql     连接SQL
     * @return
     */
    public QueryHelper LEFT_JOIN(String queryColumn, String joinSql) {
        if (StringUtils.isNotBlank(queryColumn)) {
            queryColumnList.add(queryColumn);
        }
        LEFT_OUTER_JOIN(joinSql);
        return this;
    }

    /**
     * 内连接
     *
     * @param queryColumn
     * @param joinSql
     * @return
     */
    public QueryHelper JOIN(String queryColumn, String joinSql) {
        queryColumnList.add(queryColumn);
        INNER_JOIN(joinSql);
        return this;
    }

    /**
     * 右连接
     *
     * @param queryColumn
     * @param joinSql
     * @return
     */
    public QueryHelper RIGHT_JOIN(String queryColumn, String joinSql) {
        queryColumnList.add(queryColumn);
        RIGHT_OUTER_JOIN(joinSql);
        return this;
    }

    public QueryHelper WHERE(String column, CompareType compareType, String value) {
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.WHERE(StringUtils.join(column, " ", compareType.symbol, " ",
                value));
        return this;
    }

    public QueryHelper ORDER_BY(String column) {
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.ORDER_BY(column);
        return this;
    }


    public QueryHelper GROUP_BY(String column) {
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.GROUP_BY(column);
        return this;
    }

    public QueryHelper HAVING(String condition) {
        com.wt.master.core.builder.sqlbuilder.SqlBuilder.HAVING(condition);
        return this;
    }

    public QueryHelper DISTINCT() {
        distinct = true;
        return this;
    }


    /**
     * 返回SQL
     *
     * @return
     */
    public String getSQL(String table) {
        BEGIN();
        createPreSQL(table);
        return SQL();
    }

    /**
     * 分页查询
     *
     * @param table
     * @return
     */
    private String getSQLOfPagging(String table) {
        BEGIN();
        createPreSQL(table);
        String paggingSQL = SQL() + createLimit();
        return paggingSQL;
    }

    /**
     * 创建limit语句
     *
     * @return
     */
    private String createLimit() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" limit");
        stringBuilder.append(" ");
        stringBuilder.append(this.start);
        stringBuilder.append(",");
        stringBuilder.append(this.length);
        return stringBuilder.toString();
    }

    private void createPreSQL(String table) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("t.*");
        for (String selectColumn : this.queryColumnList) {
            selectSql.append(",");
            selectSql.append(selectColumn);
        }

        if (distinct) {
            SELECT_DISTINCT(selectSql.toString());
        } else {
            SELECT(selectSql.toString());
        }
        FROM(StringUtils.join(table, " t"));
    }


    public enum CompareType {
        EQUAL("等于", "="), GT("大于", "&gt;"), LT("小于", "&lt;"), GTE("大于等于", "&gt;="), LTE("小于等于", "&lt;="), NOT_EQUAL(
                "不等于", "&lt;&gt;"), LIKE("like", "like");
        private String name;
        private String symbol;

        CompareType(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

}
