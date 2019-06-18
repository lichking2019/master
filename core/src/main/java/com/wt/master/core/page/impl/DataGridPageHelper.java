package com.wt.master.core.page.impl;

import com.wt.master.core.helper.QueryHelper;
import com.wt.master.core.page.PageHelper;
import com.wt.master.core.page.QueryResult;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * easayUI列表
 *
 * @author lichking2019@aliyun.com
 * @date Jun 14, 2019 at 4:30:23 PM
 */
public class DataGridPageHelper implements PageHelper {

    @Override
    public void setPageInfo(QueryHelper queryHelper, HttpServletRequest httpRequest) {

    }

    @Override
    public QueryResult createQueryResult() {
        return new DataGridResult();
    }
}
