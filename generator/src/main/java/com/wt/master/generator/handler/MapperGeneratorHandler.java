package com.wt.master.generator.handler;

import com.wt.master.core.common.utils.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * 持久层生成器
 *
 * @author lichking2019@aliyun.com
 * @date May 11, 2019 at 11:47:39 PM
 */
public class MapperGeneratorHandler extends BaseGenerator{
    @Override
    public BaseGenerator generate(String tableName, String moduleName, String moduleNameCN, String packagePath) {
        Template t = super.getTemplate("dao.java.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("moduleName", StringUtils.toUpperCaseFirstOne(moduleName));
        velocityContext.put("moduleNameCN", moduleNameCN);
        velocityContext.put("packagePath", packagePath);
        super.merge(t, velocityContext, getFilePath(packagePath, moduleName));
        return this;
    }

    @Override
    public String getFilePath(String packagePath,String moduleName) {
        String filePath = org.apache.commons.lang3.StringUtils.join(super.convertPackagePathToFilePath(packagePath),
                "/dao/",moduleName,"Mapper.java");
        return getRootPath()+filePath.toString();
    }
}
