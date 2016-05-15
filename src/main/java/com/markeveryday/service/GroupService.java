package com.markeveryday.service;

import com.markeveryday.model.Group;

import java.util.List;

/**
 * @author liming
 */
public interface GroupService {

    /**
     * 根据企业id获取企业下的组信息
     *
     * @param enterpriseId 企业id
     * @return 组列表或者null
     */
    List<Group> findByEnterpriseId(Long enterpriseId);




}
