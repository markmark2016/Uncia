package com.markeveryday.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
     *
     * @param book
     */
    @Override
    public void saveBook(Book book) {
        Assert.notNull(book, "待保存的book不能为null.");
        bookDao.saveOrUpdate(book);
    }

    /**
     * 根据Id查找对应的book
     *
     * @param id book id
     *
     * @return book or null.
     */
    @Override
    public Book findById(Long id) {
        Assert.notNull(id, "id 不能为null");
        return bookDao.findById(id);
    }

    /**
     * 删除指定的book
     *
     * @param book
     */
    @Override
    public void deleteBook(Book book) {
        book.setDeleteStatus(true);
        book.setModTime(new Date());
        saveBook(book);
    }
}
