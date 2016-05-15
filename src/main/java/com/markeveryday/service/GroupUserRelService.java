package com.markeveryday.service;

import com.markeveryday.model.GroupUserRel;

import java.util.List;

/**
 * @author liming
 */
public interface GroupUserRelService {


    /**
     * 根据组id获取用户列表
     */
    List<GroupUserRel> getUserRelsByGroupId(Long groupId);


}
