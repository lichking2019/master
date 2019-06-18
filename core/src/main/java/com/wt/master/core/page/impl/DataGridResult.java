package com.wt.master.core.page.impl;

import com.wt.master.core.page.QueryResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * easyUI的DataGrid列表组件结果封装
 *
 * @author lichking2019@aliyun.com
 * @date Jun 18, 2019 at 9:36:23 AM
 */
@Data
@Accessors(chain = true)
public class DataGridResult extends QueryResult {

    private List getRows(){
        return super.getData();
    }

    private long getTotal(){
        return super.getRecordsTotal();
    }
}
