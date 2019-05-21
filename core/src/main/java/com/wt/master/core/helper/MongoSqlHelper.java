package com.wt.master.core.helper;


import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 读取xml文件中定义的mongo原生语句
 */
public class MongoSqlHelper {

    public static final String SELECT = "select";
    public static final String ID = "id";

    /**
     * 读取mongo xml文件中的sql语句
     *
     * @param configFilePath xml位置
     * @param id             对应的bson语句标识
     * @param params         参数
     * @return
     */
    public static String readMongoSql(String configFilePath, String id, String... params) {
        String result = "";
        if (StringUtils.isBlank(configFilePath) || StringUtils.isBlank(id)) {
            // TODO: 2019-05-21 异常封装
            throw new RuntimeException("传入参数非法");
        }

        ClassPathResource resource = new ClassPathResource(configFilePath);
        Assert.isTrue(resource.exists(), "mongo的bson文件不存在");
        Document document = null;
        try {
            Document build = new SAXBuilder().build(resource.getFile());
            document = build;
        } catch (Exception e) {
            throw new RuntimeException("读取mongo 语句的xml文件报错,文件路径：" + configFilePath);
        }
        Element root = document.getRootElement();
        List<Element> sql = root.getChildren(SELECT);

        if (CollectionUtils.isEmpty(sql)) {
            throw new RuntimeException("mongo的bson配置文件格式不对");
        }
        Element target = null;
        for (Element element : sql) {
            if (id.equals(element.getAttributeValue(ID))) {
                target = element;
            }
        }
        result = target.getText();
        if (params.length > 0) {
            result = String.format(result, params);
        }
        return result;
    }
}
