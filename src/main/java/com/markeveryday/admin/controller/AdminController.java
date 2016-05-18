package com.markeveryday.admin.controller;

import com.markeveryday.model.Book;
import com.markeveryday.service.BookService;
import com.markeveryday.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理controller
 *
 * @author liming
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String index() {
        return "admin/admin";
    }




    @RequestMapping(value = "/books/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return Constants.RESPONSE_SUCCESS;
    }

    @RequestMapping(value = "/books/delete/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteById(bookId);
        return Constants.RESPONSE_SUCCESS;
    }


}
