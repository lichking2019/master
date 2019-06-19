package com.wt.master.generator.handler;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 生成器抽象
 *
 * @author lichking2019@aliyun.com
 * @date May 12, 2019 at 10:44:53 AM
 */
public abstract class BaseGenerator {
    /**
     * 生成代码
     *
     * @param tableName    表名
     * @param moduleName   模块英文名
     * @param moduleNameCN 模块中文名
     * @param packagePath  包路径
     * @return
     */
    public abstract BaseGenerator generate(String tableName, String moduleName, String moduleNameCN,
                                           String packagePath);

    /**
     * 生成文件路径
     * @param packagePath
     * @return
     */
    public abstract String getFilePath(String packagePath,String moduleName);

    /**
     * 获取 模板
     *
     * @param templateName 模板文件名称
     * @return
     */
    Template getTemplate(String templateName) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath" );
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("input.encoding","utf-8");
        ve.setProperty("output.encoding","utf-8");
        ve.init();
        Template t = ve.getTemplate("/template/" + templateName);
        return t;
    }

    protected void merge(Template template, VelocityContext ctx, String path) {
        File file = new File(path);
        if(!file.exists()){
            new File(file.getParent()).mkdirs();
        }else{
            System.out.println("替换文件"+file.getAbsolutePath());
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path);
            template.merge(ctx, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * 获得根目录
     * @return
     */
    protected String getRootPath(){
        String rootPath = "";
        try {
            File file = new File(BaseGenerator.class.getResource("/").getFile());
            rootPath = file.getParent();
            rootPath = java.net.URLDecoder.decode(rootPath.substring(0, rootPath.indexOf("target") - 1), "utf-8");
            return rootPath+"/src/main/java";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootPath;
    }

    /**
     * 转换包路径为文件路径
     * @param packagePath
     * @return
     */
    protected String convertPackagePathToFilePath(String packagePath){
        StringBuilder path = new StringBuilder();
        path.append("/" );
        path.append(packagePath.replace(".", "/" ));
        path.append("/");
        return path.toString();
    }

}
