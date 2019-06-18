package com.wt.master.core.page;

import com.wt.master.core.helper.QueryHelper;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页帮助类
 *
 * @author lichking2019@aliyun.com
 * @date Jun 14, 2019 at 4:04:33 PM
 */
public interface PageHelper<T> {
    String DataTable = "datatable";
    String EasyUI = "easyUI";

    void setPageInfo(QueryHelper queryHelper, HttpServletRequest request);

    QueryResult<T> createQueryResult();
}
