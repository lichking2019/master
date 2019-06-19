package com.wt.master.generator.handler;

import com.wt.master.core.common.utils.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * 服务层生成器
 *
 * @author lichking2019@aliyun.com
 * @date May 11, 2019 at 11:47:00 PM
 */
public class ServiceGeneratorHandler extends BaseGenerator{
    @Override
    public BaseGenerator generate(String tableName, String moduleName, String moduleNameCN, String packagePath) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("moduleName", StringUtils.toUpperCaseFirstOne(moduleName));
        velocityContext.put("moduleNameCN", moduleNameCN);
        velocityContext.put("packagePath", packagePath);
        velocityContext.put("lowerModuleName", StringUtils.toLowerCaseFirstOne(moduleName));
        generateService(velocityContext,packagePath,moduleName);
        generateServiceImpl(velocityContext,packagePath,moduleName);
        return this;
    }

    /**
     * 生成接口
     * @param moduleName
     * @param moduleNameCN
     * @param packagePath
     */
    private void generateService(VelocityContext velocityContext,String packagePath,String moduleName){
        Template t = super.getTemplate("service.java.vm" );
        super.merge(t, velocityContext, getFilePath(packagePath, moduleName));
    }

    /**
     * 生成实现类
     * @param moduleNameCN
     * @param packagePath
     */
    private void generateServiceImpl(VelocityContext velocityContext,String packagePath,String moduleName){
        Template t = super.getTemplate("serviceimpl.java.vm" );
        super.merge(t, velocityContext, getImplFilePath(packagePath, moduleName));
    }


    @Override
    public String getFilePath(String packagePath,String moduleName) {
        String filePath = org.apache.commons.lang3.StringUtils.join(super.convertPackagePathToFilePath(packagePath),
                "/service/",moduleName,"Service.java");
        return getRootPath()+filePath.toString();
    }

    public String getImplFilePath(String packagePath,String moduleName){
        String filePath = org.apache.commons.lang3.StringUtils.join(super.convertPackagePathToFilePath(packagePath),
                "/service/impl/",moduleName,"ServiceImpl.java");
        return getRootPath()+filePath.toString();
    }
}
