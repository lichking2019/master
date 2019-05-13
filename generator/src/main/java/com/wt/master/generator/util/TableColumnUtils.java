package com.wt.master.generator.util;

import com.wt.master.generator.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列工具
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 11:40:40 AM
 */
public class TableColumnUtils {

    /**
     * Map转换为列对象列表
     * @param columnInfo
     * @return
     */
    public static List<Table.Column> convertToColumn(List<Map> columnInfo){
        ArrayList<Table.Column> columns = new ArrayList<>();
        for (Map map : columnInfo) {
            Table.Column column = new Table().new Column();
            column.setName(map.get("column_name").toString());
            column.setComment(map.get("column_comment").toString());
            column.setType(Table.DataType.valueOf(map.get("data_type").toString()));
        }
        return columns;
    }
}
