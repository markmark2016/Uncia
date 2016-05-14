package com.markeveryday.bean;

/**
 * 账户类型
 *
 * @author liming16
 */
public enum AccountRole {
    // 超级管理员
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN,
    // 企业帐号
    ROLE_ENTERPRISE,
    // 普通用户
    ROLE_USER,
    ROLE_API_CALL
}
