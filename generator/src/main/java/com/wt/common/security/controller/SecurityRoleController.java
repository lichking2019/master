package com.wt.common.security.controller;

import com.wt.common.security.domain.SecurityRole;
import com.wt.common.security.service.SecurityRoleService;
import com.wt.master.core.base.BaseController;
import com.wt.master.core.helper.QueryHelper;
import com.wt.master.core.request.HttpResultEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理控制器
 *
 * @author
 * @date
 */
@Api(value = "角色管理控制器", tags = "SecurityRoleController", description = "角色管理控制器" )
@RestController
@RequestMapping("/SecurityRole" )
@Slf4j
public class SecurityRoleController extends BaseController<SecurityRole, SecurityRoleService> {
    @Autowired
    private SecurityRoleService securityRoleService;

    @Override
    protected SecurityRoleService getService() {
        return securityRoleService;
    }

}
