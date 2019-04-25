package com.wt.master.generator.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 表操作类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 23, 2019 at 11:36:30 PM
 */
public class TableHandler {

    public static final String JDBC_TEMPLATE = "jdbcTemplate";

    public static List<Column> getTableColumnList(String tableName){
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JDBC_TEMPLATE);
        RowMapper<Column> rowMapper = new BeanPropertyRowMapper<>(Column.class);

        List<Column> columns = jdbcTemplate.query(getTableStructureSql(tableName),rowMapper);
        return columns;
    }

    private static String getTableStructureSql(String tableName){
        StringBuilder sql = new StringBuilder();
        sql.append("select column_name, data_type,column_comment ");
        sql.append("from information_schema.columns ");
        sql.append("where table_name = '"+tableName+"'");
        return sql.toString();
    }



    /**
     * 列
     */
    class Column{
        private String column_name;
        private String data_type;
        private String column_comment;

        public String getColumn_name() {
            return column_name;
        }

        public void setColumn_name(String column_name) {
            this.column_name = column_name;
        }

        public String getData_type() {
            return data_type;
        }

        public void setData_type(String data_type) {
            this.data_type = data_type;
        }

        public String getColumn_comment() {
            return column_comment;
        }

        public void setColumn_comment(String column_comment) {
            this.column_comment = column_comment;
        }
    }

    public enum DataType{
        varchar("String"),datetime("Date"),init("Integer");
        private String javaType;

        DataType(String javaType) {
            this.javaType = javaType;
        }
    }
}
