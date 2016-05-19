package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.CommunityDao;
import com.markeveryday.model.Community;
import com.markeveryday.service.CommunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Override
    public Community findById(Long id) {
        if (id == null) {
            return null;
        }
        return communityDao.findById(id);
    }

    /**
     * 保存一个社群,如果已经存在则更新
     */
    @Override
    public void save(Community community) {
        Assert.notNull(community, "Community to save can't be null");
        if (community.getCreateTime() == null) {
            community.setCreateTime(new Date());
        }
        community.setModTime(new Date());
        communityDao.saveOrUpdate(community);

    }

    /**
     * 获得所有社群
     */
    @Override
    public List<Community> getAllCommunities() {
        return communityDao.findByProperties(ConditionFactory.and("deleteStatus", false));
    }

    @Override
    public void deleteById(Long communityId) {
        Community community = findById(communityId);
        if (community == null) {
            return;
        }
        community.setDeleteStatus(true);
        community.setModTime(new Date());
        communityDao.saveOrUpdate(community);
    }
}
