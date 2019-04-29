package com.wt.master.core.base;

import com.wt.master.core.annotation.Transparent;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 *
 * @author lichking2019@aliyun.com
 * @date Apr 14, 2019 at 9:27:48 PM
 */
@Data
public class BaseEntity implements Serializable ,Cloneable{
    /**
     * 创建人ID
     */
    private String founderId;
    /**
     * 创建人名称
     */
    private String founderName;
    /**
     * 创建时间
     */
    @Transparent
    private Date createDateTime;
    /**
     * 修改人ID
     */
    private String modifierId;
    /**
     * 修改人
     */
    private String modifierName;
    /**
     * 修改时间
     */
    private Date modifyDateTime;
    /**
     * 删除标识
     */
    @Transparent
    private Boolean deleteFlag;
}
