package com.markeveryday.admin.controller;

import com.markeveryday.bean.CommunityCategoryBean;
import com.markeveryday.model.Account;
import com.markeveryday.model.Book;
import com.markeveryday.model.Category;
import com.markeveryday.model.Community;
import com.markeveryday.model.CommunityCategoryRel;
import com.markeveryday.model.Enterprise;
import com.markeveryday.model.Group;
import com.markeveryday.model.GroupBookRel;
import com.markeveryday.security.LoginHelper;
import com.markeveryday.service.AccountService;
import com.markeveryday.service.BookService;
import com.markeveryday.service.CategoryService;
import com.markeveryday.service.CommunityCategoryRelService;
import com.markeveryday.service.CommunityService;
import com.markeveryday.service.EnterpriseService;
import com.markeveryday.service.GroupBookRelService;
import com.markeveryday.service.GroupService;
import com.markeveryday.service.RoleService;
import com.markeveryday.service.UserService;
import com.markeveryday.utils.Constants;
import com.markeveryday.utils.QiNiuUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 管理controller
 *
 * @author liming
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupBookRelService groupBookRelService;
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
    public String saveCommunity(@RequestParam Long communityId,
                                @RequestParam String name,
                                @RequestParam String description,
                                @RequestParam String slogan,
                                @RequestParam Long categoryId,
                                @RequestParam(required = false) MultipartFile file) {


        Community community;

        if (communityId != null && communityId == 0) {
            community = new Community();
            community.setCreateTime(new Date());
        } else {
            community = communityService.findById(communityId);
            if (community == null) {
                throw new IllegalStateException("Community not found in database, communityId:" + communityId);
            }
        }
        community.setName(name);
        community.setSlogan(slogan);
        community.setDeleteStatus(false);
        community.setDescription(description);
        community.setModTime(new Date());

        if (StringUtils.isEmpty(community.getImage())) {
            if (file != null) {
                String imageUrl = QiNiuUtil.upload(file);
                if (StringUtils.isNotEmpty(imageUrl)) {
                    community.setImage(imageUrl);
                } else {
                    community.setImage("DEFAULT");
                }
            } else {
                community.setImage("DEFAULT");
            }

        }

        Category category = categoryService.findById(categoryId);
        if (category == null) {
            throw new IllegalStateException("Category not found in database, categoryId:" + categoryId);
        }

        CommunityCategoryBean ccBean = new CommunityCategoryBean();
        ccBean.setCommunity(community);
        ccBean.setCategory(category);

        return saveCommunity(ccBean);
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


    private String saveCommunity(CommunityCategoryBean communityCategoryBean) {

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


    @RequestMapping(value = "/group/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveGroup(@RequestBody Group group) {

        if (group != null && group.getEnterpriseId() == null) {
            Account account =
                    accountService.findByUsername(LoginHelper.getLoginUser().getUsername());
            if (account != null) {
                Enterprise currentLoginEnterprise =
                        enterpriseService.findByAccountId(account.getId());
                if (currentLoginEnterprise != null) {
                    group.setEnterpriseId(currentLoginEnterprise.getId());
                    group.setCreator(LoginHelper.getLoginUser().getUsername());
                }
            }
        }
        groupService.saveGroup(group);
        return Constants.RESPONSE_SUCCESS;


    }


    @RequestMapping(value = "/group/delete/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteGroup(@PathVariable Long groupId) {

        if (groupId == null) {
            return Constants.RESPONSE_SUCCESS;
        }
        groupService.deleteGroup(groupId);
        return Constants.RESPONSE_SUCCESS;


    }


    @RequestMapping(value = "/group/book/delete/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteGroupBookRel(@PathVariable Long groupId) {

        if (groupId == null) {
            return Constants.RESPONSE_SUCCESS;
        }
        groupBookRelService.deleteByGroupId(groupId);
        return Constants.RESPONSE_SUCCESS;
    }


    @RequestMapping(value = "/group/book/save/", method = RequestMethod.POST)
    @ResponseBody
    public String saveGroupBookRel(@RequestBody GroupBookRel rel) {

        groupBookRelService.save(rel);
        return Constants.RESPONSE_SUCCESS;
    }

}
