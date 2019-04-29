package com.wt.master.generator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 代码生成工具
 *
 * @author lichking2019@aliyun.com
 * @date Apr 23, 2019 at 10:41:51 PM
 */
public class Generator {

    /**
     * 生成代码入口
     * @param tableName 表名
     * @param item 选项
     */
    public static void create(String tableName,GenerateItem... item){
        Assert.isTrue(StringUtils.isEmpty(tableName),"表名不能为空");
    }

    public enum GenerateItem{
        controller,service,mapper
    }

    private static JdbcTemplate getJdbcTemplate(){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        return jdbcTemplate;
    }

    private static Object getTableInfo(String tableName){
        //        getJdbcTemplate().query()
        return null;
    }

}
