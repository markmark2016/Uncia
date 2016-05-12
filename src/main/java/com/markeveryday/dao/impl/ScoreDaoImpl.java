package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.ScoreDao;
import com.markeveryday.model.Score;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Score DAO 实现
 *
 * @author liming
 */
@Repository
public class ScoreDaoImpl extends AbstractBaseDao<Score> implements ScoreDao {
}
