package com.wt.master.generator.handler;

import com.wt.master.core.common.utils.StringUtils;
import com.wt.master.generator.model.Table;
import com.wt.master.generator.util.TableColumnUtils;
import com.wt.master.generator.util.TableInfoUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class DomainGeneratorHandler extends BaseGenerator {
    @Override
    public BaseGenerator generate(String tableName, String moduleName, String moduleNameCN, String packagePath) {
        //1、准备数据
        //1.1 获取所有的列，包括字段名称|备注|字段类型| 并标注主键。返回一个数组，里面包含字段对象
        Table tableInfo = TableInfoUtils.getTableColumnList(tableName);
        //2、获取Template
        Template t = super.getTemplate("domain.java.vm" );
        //3、封装Context对象
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("moduleName", StringUtils.toUpperCaseFirstOne(moduleName));
        velocityContext.put("moduleNameCN", moduleNameCN);
        velocityContext.put("packagePath", packagePath);
        velocityContext.put("tableName", tableInfo.getTableName());
        velocityContext.put("columnInfoList", TableColumnUtils.removeUnuseColumn(tableInfo.getColumnList()));
        //4、生成文件
        super.merge(t, velocityContext, getFilePath(packagePath, moduleName));
        return null;
    }

    @Override
    public String getFilePath(String packagePath, String moduleName) {
        String filePath = org.apache.commons.lang3.StringUtils.join(super.convertPackagePathToFilePath(packagePath),
                "/domain/", moduleName, ".java" );
        return getRootPath() + filePath.toString();
    }
}
