package com.wt.master.generator;

import com.wt.master.generator.builder.MySqlTableBuilder;
import com.wt.master.generator.builder.TableBuilder;
import com.wt.master.generator.factory.GeneratorFactory;
import com.wt.master.generator.handler.BaseGenerator;
import com.wt.master.generator.model.Table;
import com.wt.master.generator.util.TableInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 代码生成工具
 *
 * @author lichking2019@aliyun.com
 * @date Apr 23, 2019 at 10:41:51 PM
 */
public class Generator {

    /**
     * 生成代码入口
     *
     * @param tableName    表名
     * @param moduleName   模块英文名
     * @param moduleNameCN 模块中文名
     * @param packagePath  打包路径
     * @param item         生成项目
     */
    public static void create(String tableName, String moduleName, String moduleNameCN, String packagePath,
                              GenerateItem... item) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleNameCN) || StringUtils.isBlank(packagePath)) {
            throw new IllegalArgumentException("参数非法！" );
        }

        for (GenerateItem generateItem : item) {
            BaseGenerator baseGenerator = GeneratorFactory.create(generateItem);
            baseGenerator.generate(tableName, moduleName, moduleNameCN, packagePath);
        }
    }

//    /**
//     * 获取表的信息
//     *
//     * @return
//     */
//    private Table getTableInfo(String tableName) {
//        //获取列的信息
//        Table table = TableInfoUtils.getTableColumnList(tableName);
//        TableBuilder tableBuilder = new MySqlTableBuilder();
//        tableBuilder.buildColumnList(tableColumnList);
//        // TODO: 2019-05-12
//        //        tableBuilder.buildComment();
//        tableBuilder.buildTableName(tableName);
//        return tableBuilder.build();
//    }


    public enum GenerateItem {
        controller, service, mapper, domain
    }

    //    private static JdbcTemplate getJdbcTemplate(){
    //        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
    //        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
    //        return jdbcTemplate;
    //    }

    //    /**
    //     *
    //     * @param tableName
    //     * @return
    //     */
    //    private static Object getTableInfo(String tableName){
    //        //        getJdbcTemplate().query()
    //        return null;
    //    }

}
