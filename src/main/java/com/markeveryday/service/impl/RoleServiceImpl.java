package com.markeveryday.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.RoleDao;
import com.markeveryday.model.Role;
import com.markeveryday.service.RoleService;

/**
 * 用户角色服务实现
 *
 * @author liming
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 根据账户id获取角色信息
     *
     * @param accountId
     *
     * @return
     */
    @Override
    public List<Role> getRolesByAccountId(Long accountId) {
        Assert.notNull(accountId, "accountId to find roles can't be null.");
        List<Role> roles =
                roleDao.findByProperties(ConditionFactory.and("accountId", accountId));
        if (!CollectionUtils.isEmpty(roles)) {
            return roles;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 根据账户名获取角色信息
     *
     * @param accountName
     *
     * @return
     */
    @Override
    public List<Role> getRolesByAccount(String accountName) {
        return null;
    }
}
