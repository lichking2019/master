package com.wt.master.core.page.impl;

import com.wt.master.core.helper.QueryHelper;
import com.wt.master.core.page.PageHelper;
import com.wt.master.core.page.QueryResult;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * dataTable分页
 *
 * @author lichking2019@aliyun.com
 * @date Jun 14, 2019 at 4:11:19 PM
 */
public class DataTablePageHelper implements PageHelper {

    @Override
    public void setPageInfo(QueryHelper queryHelper, HttpServletRequest request) {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        queryHelper.setStart(Long.parseLong(start));
        queryHelper.setLength(Long.parseLong(length));
    }

    @Override
    public QueryResult createQueryResult() {
        return new DataTableResult();
    }
}
