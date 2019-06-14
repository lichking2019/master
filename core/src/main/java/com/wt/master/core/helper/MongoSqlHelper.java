package com.wt.master.core.helper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;

/**
 * 读取xml文件中定义的mongo原生语句
 */
@Slf4j
public class MongoSqlHelper {

    private static final String SELECT = "select";
    private static final String ID = "id";

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
        Document document = null;
        try {
            ClassPathResource resource = new ClassPathResource(configFilePath);
            InputStream inputStream = resource.getInputStream();
            Document build = new SAXBuilder().build(inputStream);
            document = build;
        } catch (Exception e) {
            throw new RuntimeException("读取mongo 语句的xml文件报错,文件路径：" + configFilePath, e);
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
        // TODO: 2019-05-23 提示信息有待改善
        Assert.isTrue(target!=null,"未找到对应的bson，请检查代码");

        result = target.getText();
        if (params.length > 0) {
            result = String.format(result, params);
        }
        log.info("=====>生成的mongobson:{}",result);
        return result;
    }
}
