package com.wt.master.generator.util;

import com.wt.master.core.base.BaseEntity;
import com.wt.master.generator.model.Table;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 列工具
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 11:40:40 AM
 */
public class TableColumnUtils {

    /**
     * 转换数据对象为表对象
     * @param columnInfo
     * @return
     */
    public static Table convertToColumn(List<Map<String,Object>> columnInfo, Map tableInfo){
        Table table = new Table();
        table.setTableName(tableInfo.get("table_name").toString());
        table.setComment(tableInfo.get("table_comment")==null?"":tableInfo.get("table_comment").toString());
        ArrayList<Table.Column> columns = new ArrayList<>();
        for (Map map : columnInfo) {
            Table.Column column = new Table().new Column();
            column.setName(map.get("column_name").toString());
            column.setComment(map.get("column_comment").toString());
            column.setType(Table.DataType.valueOf(map.get("data_type").toString()));
            column.setColumn_key(map.get("column_key").toString());
            columns.add(column);
        }
        table.setColumnList(columns);
        return table;
    }

    public static List<Table.Column> removeUnuseColumn(List<Table.Column> columnList){
        Iterator<Table.Column> iterator = columnList.iterator();
        Field[] superColumns = getSuperColumns();
        while (iterator.hasNext()){
            Table.Column next = iterator.next();
            for (Field superColumn : superColumns) {
                if(next.getName().equals(superColumn.getName())){
                    iterator.remove();
                }
            }
        }
        return columnList;
    }

    private static Field[] getSuperColumns(){
        Field[] declaredFields = BaseEntity.class.getDeclaredFields();
        return declaredFields;
    }
}
