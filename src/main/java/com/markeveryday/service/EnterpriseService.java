package com.markeveryday.service;

import com.markeveryday.model.Enterprise;

/**
 * 企业服务
 *
 * @author liming
 */
public interface EnterpriseService {

    /**
     * 根据账户id查找对应的企业, 一个企业可关联多个账户,但一个账户只能对应于一个企业
     *
     * @param accountId 账户id
     * @return 账户对应的企业或者null
     */
    Enterprise findByAccountId(Long accountId);

}
