package com.wt.master.core.page;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页表格结果抽象
 *
 * @author lichking2019@aliyun.com
 * @date Jun 18, 2019 at 9:28:35 AM
 */
@Data
@Accessors(chain = true)
public class QueryResult<T> {

    /**
     * 总记录数
     */
    private long recordsTotal;

    /**
     * 数据
     */
    private List<T> data;

}
