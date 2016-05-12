package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.MessageDao;
import com.markeveryday.model.Message;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Message DAO 实现
 *
 * @author liming
 */
@Repository
public class MessageDaoImpl extends AbstractBaseDao<Message> implements MessageDao {
}
