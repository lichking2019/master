package com.wt.master.generator.util;

import com.wt.master.generator.model.Table;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表操作类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 23, 2019 at 11:36:30 PM
 */
public class TableInfoUtils {

    public static final String JDBC_TEMPLATE = "jdbcTemplate";

    public static Table getTableColumnList(String tableName) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextUtils.getBean(JDBC_TEMPLATE);

        List<Map<String,Object>> tableInfo = jdbcTemplate.queryForList(getTableStructureSql(tableName));
        if (CollectionUtils.isEmpty(tableInfo)) {
            throw new RuntimeException("表：" + tableName + "不存在" );
        }

        List<Map<String,Object>> columns = jdbcTemplate.queryForList(getColumnStructureSql(tableName));

        return TableColumnUtils.convertToColumn(columns, tableInfo.get(0));
    }

    /**
     * 获取查询表字段属性的SQL
     *
     * @param tableName 表名
     * @return
     */
    private static String getColumnStructureSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select column_name, data_type,column_comment,column_key " );
        sql.append("from information_schema.columns " );
        sql.append("where table_name = '" + tableName + "'" );
        return sql.toString();
    }

    /**
     * 获取表的信息
     * @param tableName
     * @return
     */
    private static String getTableStructureSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name,table_comment " );
        sql.append("from information_schema.tables " );
        sql.append("where table_name= '" + tableName + "'" );
        return sql.toString();
    }


}
