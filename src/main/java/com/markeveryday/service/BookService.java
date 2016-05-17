package com.markeveryday.service;

import com.markeveryday.model.Book;

import java.util.List;

/**
 * Book Service
 *
 * @author liming
 */
public interface BookService {

    /**
     * 保存或者更新一本书
     *
     * @param book
     */
    void saveBook(Book book);

    /**
     * 根据Id查找对应的book
     *
     * @param id book id
     *
     * @return book or null.
     */
    Book findById(Long id);


    /**
     * 查找所有图书
     * @return
     */
    List<Book> findAll();


    /**
     * 删除指定的book
     *
     * @param book
     */
    void deleteBook(Book book);

    /**
     * 根据id删除book
     *
     * @param id
     */
    default void deleteById(Long id) {
        deleteBook(findById(id));
    }

}
