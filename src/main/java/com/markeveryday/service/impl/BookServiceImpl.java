package com.markeveryday.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.markeveryday.commons.db.ConditionAndSet;
import com.markeveryday.commons.db.ConditionFactory;
import com.markeveryday.dao.BookDao;
import com.markeveryday.model.Book;
import com.markeveryday.service.BookService;

/**
 * Book service impl.
 *
 * @author liming
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    /**
     * 保存或者更新一本书
     */
    @Transactional()
    @Override
    public void saveBook(Book book) {
        Assert.notNull(book, "待保存的book不能为null.");
        book.setModTime(new Date());
        bookDao.saveOrUpdate(book);
    }

    /**
     * 根据Id查找对应的book
     *
     * @param id book id
     * @return book or null.
     */
    @Override
    public Book findById(Long id) {
        Assert.notNull(id, "id 不能为null");
        return bookDao.findById(id);
    }

    /**
     * 查找所有图书
     */
    @Override
    public List<Book> findAll() {
        return bookDao.findByProperties(ConditionFactory.and("deleteStatus", false));
    }

    /**
     * 删除指定的book
     */
    @Override
    public void deleteBook(Book book) {
        book.setDeleteStatus(true);
        book.setModTime(new Date());
        saveBook(book);
    }
}
