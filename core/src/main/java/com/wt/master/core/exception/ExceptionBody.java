package com.wt.master.core.exception;

import com.wt.master.core.reflect.ReflectUtil;
import lombok.Data;

/**
 * 自定义异常体
 *
 * @author lichking2019@aliyun.com
 * @date Apr 26, 2019 at 3:09:36 PM
 */
@Data
public class ExceptionBody {
    public static final String MESSAGE = "message";
    /**
     * 异常代码
     */
    private String errorCode;
    /**
     * 异常信息
     */
    private String errorMessage;

    /**
     * 构造函数
     *
     * @param errorEnum 异常枚举
     * @param params    拼接异常信息的参数
     */
    public ExceptionBody(Enum errorEnum, Object... params) {
        this.errorCode = errorEnum.name();
        this.errorMessage = String.format(ReflectUtil.getValueOfProperty(errorEnum, MESSAGE).toString(), params);
    }

}
