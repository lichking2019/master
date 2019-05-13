package com.wt.master.generator.model;

import java.util.List;

/**
 * 数据库表的模型
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 10:48:10 AM
 */
public class Table {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 注释
     */
    private String comment;

    /**
     * 包含的列
     */
    private List<Column> columnList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    /**
     * 列
     */
    public class Column{

        /**
         * 列名称
         */
        private String name;
        /**
         * 列类型
         */
        private DataType type;
        /**
         * 注释
         */
        private String comment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DataType getType() {
            return type;
        }

        public void setType(DataType type) {
            this.type = type;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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
