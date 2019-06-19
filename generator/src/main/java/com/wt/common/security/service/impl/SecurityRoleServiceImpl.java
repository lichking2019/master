package com.wt.common.security.service.impl;

import com.wt.common.security.dao.SecurityRoleMapper;
import com.wt.common.security.domain.SecurityRole;
import com.wt.common.security.service.SecurityRoleService;
import com.wt.master.core.base.support.ServiceSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SecurityRoleServiceImpl extends ServiceSupport<SecurityRole, SecurityRoleMapper> implements SecurityRoleService {

    @Autowired
    private SecurityRoleMapper securityRoleMapper;


    @Override
    protected SecurityRoleMapper getMapper() {
        return securityRoleMapper;
    }

    @Override
    protected Class<SecurityRole> getEntityType() {
        return SecurityRole.class;
    }


}
