package com.wt.master.core.page.impl;

import com.wt.master.core.page.QueryResult;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * DataTable返回结果封装
 *
 * @author lichking2019@aliyun.com
 * @date Jun 18, 2019 at 9:34:05 AM
 */
@Data
@Accessors(chain = true)
public class DataTableResult extends QueryResult {
    /**
     * 绘制计数器，服务端接收到前台的请求后，原样返回
     */
    private int draw;

    /**
     * 过滤后的记录数
     */
    private int recordsFiltered;

}
