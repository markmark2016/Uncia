package com.markeveryday.service.impl;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.CommunityCategoryRelDao;
import com.markeveryday.model.CommunityCategoryRel;
import com.markeveryday.service.CommunityCategoryRelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author liming
 */
@Service
public class CommunityCategoryRelServiceImpl implements CommunityCategoryRelService {


    @Autowired
    private CommunityCategoryRelDao communityCategoryRelDao;


    /**
     * 保存一个分类,如果已经存在则更新
     */
    @Override
    public void save(CommunityCategoryRel communityCategoryRel) {
        Assert.notNull(communityCategoryRel, "CommunityCategoryRel to save can't be null");
        if (communityCategoryRel.getCreateTime() == null) {
            communityCategoryRel.setCreateTime(new Date());
        }
        communityCategoryRel.setModTime(new Date());
        communityCategoryRelDao.saveOrUpdate(communityCategoryRel);

    }

    @Override
    public CommunityCategoryRel getByCommunityId(Long communityId) {
        if (communityId == null) {
            return null;
        }
        ConditionAndSet andSet = (ConditionAndSet) ConditionFactory.and("communityId", communityId);
        andSet.put("deleteStatus", false);
        List<CommunityCategoryRel> rels = communityCategoryRelDao.findByProperties(andSet);
        if (!CollectionUtils.isEmpty(rels)) {
            return rels.get(0);
        }
        return null;
    }

    @Override
    public List<CommunityCategoryRel> getByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        ConditionAndSet andSet = (ConditionAndSet) ConditionFactory.and("categoryId", categoryId);
        andSet.put("deleteStatus", false);
        return communityCategoryRelDao.findByProperties(andSet);
    }


    @Override
    public void deleteByCommunityId(Long communityId) {
        CommunityCategoryRel rel = getByCommunityId(communityId);
        if (rel == null) {
            return;
        }
        rel.setDeleteStatus(true);
        rel.setModTime(new Date());
        communityCategoryRelDao.saveOrUpdate(rel);
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        List<CommunityCategoryRel> rels = getByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(rels)) {
            return;
        }
        for (CommunityCategoryRel rel : rels) {
            rel.setDeleteStatus(true);
            rel.setModTime(new Date());
        }
        communityCategoryRelDao.saveOrUpdateAll(rels);
    }
}
