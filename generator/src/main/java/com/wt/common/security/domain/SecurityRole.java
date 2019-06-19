package com.wt.common.security.domain;

import com.wt.master.core.annotation.Id;
import com.wt.master.core.annotation.Table;
import com.wt.master.core.annotation.Transparent;
import com.wt.master.core.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色管理实体
 *
 * @author
 * @date
 */
@Data
@Table(tableName = "SecurityRoleT")
@Accessors(chain = true)
public class SecurityRole extends BaseEntity {
    /**
     * 角色Id
     */
    @Id(value = "roleId")
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
}
