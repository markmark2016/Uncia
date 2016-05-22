package com.markeveryday.service;

import com.markeveryday.model.GroupBookRel;

/**
 * @author liming
 */
public interface GroupBookRelService {

    void save(GroupBookRel groupBookRel);

    void deleteByGroupId(Long groupId);

}
