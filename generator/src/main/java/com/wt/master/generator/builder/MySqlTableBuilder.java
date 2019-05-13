package com.wt.master.generator.builder;

import com.wt.master.generator.model.Table;

import java.util.List;

/**
 * mysql表建造者
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 11:14:18 AM
 */
public class MySqlTableBuilder extends TableBuilder{
    private Table table;

    @Override
    public Table build() {
        return table;
    }

    @Override
    public void buildTableName(String tableName) {
        table.setTableName(tableName);
    }

    @Override
    public void buildComment(String comment) {
        table.setComment(comment);
    }

    @Override
    public void buildColumnList(List<Table.Column> columnList) {
        table.setColumnList(columnList);
    }
}
