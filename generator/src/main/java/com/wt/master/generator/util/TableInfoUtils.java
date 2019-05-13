package com.wt.master.generator.util;

import com.wt.master.generator.model.Table;
import com.wt.master.generator.util.SpringContextUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

    public static List<Table.Column> getTableColumnList(String tableName){
        JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextUtils.getBean(JDBC_TEMPLATE);
        RowMapper<Map> rowMapper = new BeanPropertyRowMapper<>(Map.class);

        List<Map> columns = jdbcTemplate.query(getTableStructureSql(tableName),rowMapper);
        return TableColumnUtils.convertToColumn(columns);
    }

    /**
     * 获取查询表字段属性的SQL
     * @param tableName 表名
     * @return
     */
    private static String getTableStructureSql(String tableName){
        StringBuilder sql = new StringBuilder();
        sql.append("select column_name, data_type,column_comment ");
        sql.append("from information_schema.columns ");
        sql.append("where table_name = '"+tableName+"'");
        return sql.toString();
    }


}
