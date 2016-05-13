package com.markeveryday.service;

import java.util.List;

import com.markeveryday.model.Role;

/**
 * 用户角色Service
 *
 * @author liming
 */
public interface RoleService {

    /**
     * 根据账户id获取角色信息
     *
     * @param accountId
     *
     * @return
     */
    List<Role> getRolesByAccountId(Long accountId);

    /**
     * 根据账户名获取角色信息
     *
     * @param accountName
     *
     * @return
     */
    List<Role> getRolesByAccount(String accountName);

}
