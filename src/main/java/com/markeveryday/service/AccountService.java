package com.markeveryday.service;

import com.markeveryday.model.Account;

/**
 * 账户Service
 *
 * @author liming
 */
public interface AccountService {

    /**
     * 根据账户名获取账户信息
     *
     * @param username
     *
     * @return
     */
    Account findByUsername(String username);

}
