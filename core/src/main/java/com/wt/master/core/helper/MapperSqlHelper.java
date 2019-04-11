package com.wt.master.core.helper;

//import static org.apache.ibatis.jdbc.SelectBuilder.*;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import static com.wt.master.core.reflect.ReflectUtil.*;
import static com.wt.master.core.adapter.SqlParamReflectAdapter.*;

import com.wt.master.core.adapter.SqlParamReflectAdapter;
import com.wt.master.core.base.BaseMapper;
import org.apache.ibatis.jdbc.SqlBuilder;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * mybatis SQL 语句提供
 * 思路，通过反射获取实体属性及值，然后利用mybatis的SQLbuilder工具拼SQL，返回给MyBatis
 */
public class MapperSqlHelper {
    /**
     * 查询所有的实体数据
     *
     * @param params
     * @return
     */
    public String findAll(Map<String, Object> param) {
        //初始化，考虑多线程得因素
        BEGIN();
        Object entity = param.get(BaseMapper.ENTITY);
        //字段及值
        Map<String, Object> paramMap = getPropertyAndValue(entity);
        //获取表名
        String tableName = getTableName(entity.getClass());
        //拼SQL
        SELECT("t.*");
        FROM(tableName+" t");
        paramMap.forEach((column,value)->{
            if(value!=null){
                WHERE("t."+column+" = "+columnValueFormat(column)+"");
            }
        });
        WHERE("t.deleteFlag=false");
        return SQL();
    }

    /**
     * 插入数据
     * @param param 参数
     * @return 执行的SQL
     */
    public String add(Map<String,Object> param){
        BEGIN();
        Object entity = param.get(BaseMapper.ENTITY);
        //字段及值
        Map<String, Object> paramMap = getPropertyAndValue(entity);
        String tableName = getTableName(entity.getClass());
        //拼SQL
        INSERT_INTO(tableName);
        paramMap.forEach((column,value)->{
            if(value!=null){
                VALUES(column,columnValueFormat(column));
            }
        });
        return SQL();
    }

    private String columnValueFormat(String column){
        return StringUtils.join("#{",BaseMapper.ENTITY,".",column,"}");
    }
}
