package com.wt.master.generator.handler;

import com.wt.master.core.common.utils.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * controller层生成器
 *
 * @author lichking2019@aliyun.com
 * @date May 11, 2019 at 11:46:31 PM
 */
public class ControllerGeneratorHandler extends BaseGenerator {
    @Override
    public BaseGenerator generate(String tableName, String moduleName, String moduleNameCN, String packagePath) {
        Template t = super.getTemplate("controller.java.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("moduleName", StringUtils.toUpperCaseFirstOne(moduleName));
        velocityContext.put("moduleNameCN", moduleNameCN);
        velocityContext.put("packagePath", packagePath);
        velocityContext.put("lowerModuleName", StringUtils.toLowerCaseFirstOne(moduleName));
        super.merge(t, velocityContext, getFilePath(packagePath, moduleName));
        return this;
    }

    @Override
    public String getFilePath(String packagePath, String moduleName) {
        String filePath = org.apache.commons.lang3.StringUtils.join(super.convertPackagePathToFilePath(packagePath),
                "/controller/",moduleName,"Controller.java");
        return getRootPath()+filePath.toString();
    }
}
