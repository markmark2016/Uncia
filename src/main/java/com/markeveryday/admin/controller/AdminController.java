package com.markeveryday.admin.controller;

import com.markeveryday.bean.CommunityCategoryBean;
import com.markeveryday.model.Book;
import com.markeveryday.model.Category;
import com.markeveryday.model.Community;
import com.markeveryday.model.CommunityCategoryRel;
import com.markeveryday.service.AccountService;
import com.markeveryday.service.BookService;
import com.markeveryday.service.CategoryService;
import com.markeveryday.service.CommunityCategoryRelService;
import com.markeveryday.service.CommunityService;
import com.markeveryday.service.EnterpriseService;
import com.markeveryday.service.GroupService;
import com.markeveryday.service.RoleService;
import com.markeveryday.service.UserService;
import com.markeveryday.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import javax.ws.rs.Path;

/**
 * 管理controller
 *
 * @author liming
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private BookService bookService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityCategoryRelService communityCategoryRelService;
    @Autowired
    private CategoryService categoryService;

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


    @RequestMapping(value = "/community/delete/{communityId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCommunity(@PathVariable Long communityId) {
        if (communityId == null) {
            return Constants.RESPONSE_FAILED;
        }
        communityService.deleteById(communityId);
        communityCategoryRelService.deleteByCommunityId(communityId);

        return Constants.RESPONSE_SUCCESS;
    }


    @RequestMapping(value = "/community/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveCommunity(@RequestBody CommunityCategoryBean communityCategoryBean) {

        Community community = communityCategoryBean.getCommunity();
        communityService.save(community);

        Category category = communityCategoryBean.getCategory();
        if (category == null) {
            throw new IllegalStateException("Category not set for:" + communityCategoryBean);
        }

        CommunityCategoryRel rel = communityCategoryRelService.getByCommunityId(community.getId());
        if (rel != null) {
            rel.setCategoryId(category.getId());
            rel.setModTime(new Date());
        } else {
            rel = new CommunityCategoryRel();
            rel.setCommunityId(community.getId());
            rel.setCategoryId(category.getId());
            rel.setCreateTime(new Date());
            rel.setModTime(new Date());
            rel.setDeleteStatus(false);
        }
        communityCategoryRelService.save(rel);

        return Constants.RESPONSE_SUCCESS;
    }


    @RequestMapping(value = "/category/save", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Constants.RESPONSE_SUCCESS;
    }

    @RequestMapping(value = "/category/delete/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);

        communityCategoryRelService.deleteByCategoryId(categoryId);

        return Constants.RESPONSE_SUCCESS;
    }


}
