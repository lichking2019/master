package com.wt.master.core.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 */
@Data
public class BaseEntity implements Serializable ,Cloneable{
    /**
     * 创建人ID
     */
    private Integer founderId;
    /**
     * 创建人名称
     */
    private String founderName;
    /**
     * 创建时间
     */
    private Date createDateTime;
    /**
     * 修改人ID
     */
    private Integer modifierId;
    /**
     * 修改人
     */
    private String modifierName;
    /**
     * 修改时间
     */
    private Date modifierDateTime;
    /**
     * 删除标识
     */
    private Boolean deleteFlag;
}
