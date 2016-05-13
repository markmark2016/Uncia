package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.commons.db.AbstractBaseDao;
import com.markeveryday.dao.MessageDao;
import com.markeveryday.model.Message;

/**
 * Message DAO 实现
 *
 * @author liming
 */
@Repository
public class MessageDaoImpl extends AbstractBaseDao<Message> implements MessageDao {
}
