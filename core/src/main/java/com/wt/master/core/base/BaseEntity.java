package com.wt.master.core.base;

import com.wt.master.core.annotation.Ttransparent;
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
    @Ttransparent
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
    private Date modifyDateTime;
    /**
     * 删除标识
     */
    @Ttransparent
    private Boolean deleteFlag;
}
