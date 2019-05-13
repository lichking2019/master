package com.wt.master.generator;

import com.wt.master.generator.builder.MySqlTableBuilder;
import com.wt.master.generator.builder.TableBuilder;
import com.wt.master.generator.model.Table;
import com.wt.master.generator.util.TableInfoUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
     * @param tableName 表名
     * @param item      选项
     */
    public static void create(String tableName, GenerateItem... item) {
        Assert.isTrue(StringUtils.isEmpty(tableName), "表名不能为空");
    }

    /**
     * 获取表的信息
     *
     * @return
     */
    private Table getTableInfo(String tableName) {
        //获取列的信息
        List<Table.Column> tableColumnList = TableInfoUtils.getTableColumnList(tableName);
        TableBuilder tableBuilder = new MySqlTableBuilder();
        tableBuilder.buildColumnList(tableColumnList);
        // TODO: 2019-05-12
        //        tableBuilder.buildComment();
        tableBuilder.buildTableName(tableName);
        return tableBuilder.build();
    }


    public enum GenerateItem {
        controller, service, mapper
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
