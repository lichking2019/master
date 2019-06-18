package com.wt.master.core.page;

import com.wt.master.core.common.utils.SpringContextHolder;
import com.wt.master.core.page.impl.DataTablePageHelper;
import com.wt.master.core.page.impl.DataGridPageHelper;
import com.wt.master.core.property.SystemConfigProperties;

import static com.wt.master.core.page.PageHelper.DataTable;
import static com.wt.master.core.page.PageHelper.EasyUI;


/**
 * 分页服务工厂
 *
 * @author lichking2019@aliyun.com
 * @date Jun 18, 2019 at 10:19:09 AM
 */
public class PageHelperFactory {

    private static PageHelper pageHelper = new DataTablePageHelper();

    /**
     * 工厂方法
     *
     * @return
     */
    public static PageHelper create() {
        SystemConfigProperties systemConfigProperties = (SystemConfigProperties) SpringContextHolder.getBean(
                "systemConfigProperties");
        switch ((String) systemConfigProperties.getTable().get("type")) {
            case DataTable:
                pageHelper = new DataTablePageHelper();
                break;
            case EasyUI:
                pageHelper = new DataGridPageHelper();
                break;
            default:
                pageHelper = new DataGridPageHelper();
        }
        return pageHelper;
    }

}
