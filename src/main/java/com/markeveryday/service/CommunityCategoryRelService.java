package com.markeveryday.service;

import com.markeveryday.model.Category;
import com.markeveryday.model.CommunityCategoryRel;

import java.util.List;


/**
 * @author liming
 */
public interface CommunityCategoryRelService {


    /**
     * 保存一个分类,如果已经存在则更新
     */
    void save(CommunityCategoryRel communityCategoryRel);


    CommunityCategoryRel getByCommunityId(Long communityId);

    List<CommunityCategoryRel> getByCategoryId(Long communityId);

    void deleteByCommunityId(Long communityId);

    void deleteByCategoryId(Long categoryId);
}
