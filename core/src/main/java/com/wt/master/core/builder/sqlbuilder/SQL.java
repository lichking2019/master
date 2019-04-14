/**
 * Copyright 2009-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wt.master.core.builder.sqlbuilder;

import org.apache.commons.collections.list.TreeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Clinton Begin
 */
public class SQL extends AbstractSQL<SQL> {
    /**
     * 单挑记录的values部分
     */
    private List<String> singleRecordValues = new ArrayList<>();

    private Map<String, List<String>> caseSQL = new TreeMap<>();


    @Override
    public SQL getSelf() {
        return this;
    }


    /**
     * 添加值
     * @param value
     * @return
     */
    public SQL SINGLERECORDVALUES(String value) {
        singleRecordValues.add(value);
        return getSelf();
    }

    /**
     * 批量插入，生成每条记录的值SQL
     * @return 每条记录的值SQL
     */
    public SQL GENERATESINGLERECORDVALUES() {
        VALUES(sqlClause("(", singleRecordValues, ")", ","));
        singleRecordValues.clear();
        return getSelf();
    }

    /**
     * 拼装where in 结构
     * @param column 字段
     * @param values 字段范围
     * @return
     */
    public SQL WHERE_IN(String column, List<String> values) {
        StringBuilder whereSQL = new StringBuilder();
        whereSQL.append(column + " IN");
        whereSQL.append(sqlClause("(", values, ")", ","));
        WHERE(whereSQL.toString());
        return getSelf();
    }

    public SQL buildCaseSQL(String column, String whenSQL) {
        if (caseSQL.containsKey(column)) {
            caseSQL.get(column).add(whenSQL);
        } else {
            caseSQL.put(column, new TreeList());
            caseSQL.get(column).add(whenSQL);
        }
        return getSelf();
    }

    public SQL buildBatchSetSQL() {
        StringBuilder caseSql = new StringBuilder();
        caseSQL.forEach((column, value) -> {
            caseSql.append(column);
            for (String whensql : value) {
                caseSql.append("\n"+whensql);
            }
            caseSql.append("\nEND,");
        });
        String setSQL = caseSql.toString();
        setSQL = setSQL.substring(0, setSQL.length()-1);
        SET(setSQL);
        caseSQL.clear();
        return getSelf();
    }


    /**
     * 拼装每一条值
     * @param prefix 前缀
     * @param parts 内容
     * @param suffix 后缀
     * @param division 分隔符
     * @return
     */
    private String sqlClause(String prefix, List<String> parts, String suffix, String division) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                stringBuilder.append(division);
            }
            stringBuilder.append(parts.get(i));
        }
        stringBuilder.append(suffix);
        return stringBuilder.toString();
    }
}