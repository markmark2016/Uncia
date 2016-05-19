package com.markeveryday.service;

import com.markeveryday.model.Community;

import java.util.List;


/**
 * @author liming
 */
public interface CommunityService {


    Community findById(Long id);


    /**
     * 保存一个社群,如果已经存在则更新
     */
    void save(Community community);


    /**
     * 获得所有社群
     */
    List<Community> getAllCommunities();

    void deleteById(Long communityId);
}
