package com.wt.master.generator.builder;

import com.wt.master.generator.model.Table;

import java.util.List;

/**
 * 表建造者抽象
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 11:09:53 AM
 */
public abstract class TableBuilder {
    public abstract Table build();

    /**
     * 构建表名
     * @param tableName
     */
    public abstract void buildTableName(String tableName);

    /**
     * 构建表注释
     * @param comment
     */
    public abstract void buildComment(String comment);

    /**
     * 构建列信息
     * @param columnList
     */
    public abstract void buildColumnList(List<Table.Column> columnList);

}
