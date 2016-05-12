package com.markeveryday.dao.impl;

import org.springframework.stereotype.Repository;

import com.markeveryday.dao.BookDao;
import com.markeveryday.model.Book;
import com.markeveryday.commons.db.AbstractBaseDao;

/**
 * Book DAO 实现
 *
 * @author liming
 */
@Repository
public class BookDaoImpl extends AbstractBaseDao<Book> implements BookDao {
}
