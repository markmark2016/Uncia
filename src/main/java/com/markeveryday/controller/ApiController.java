package com.markeveryday.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.markeveryday.bean.AccountRole;
import com.markeveryday.bean.CommunityCategoryBean;
import com.markeveryday.bean.EnterpriseGroupUser;
import com.markeveryday.bean.GroupUser;
import com.markeveryday.model.Account;
import com.markeveryday.model.Book;
import com.markeveryday.model.Category;
import com.markeveryday.model.Community;
import com.markeveryday.model.CommunityCategoryRel;
import com.markeveryday.model.Enterprise;
import com.markeveryday.model.Group;
import com.markeveryday.model.GroupBookRel;
import com.markeveryday.model.Role;
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
import com.markeveryday.utils.EnvUtil;
import com.markeveryday.utils.HttpClientUtil;
import com.markeveryday.utils.JsonHelpler;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

/**
 * API controller
 *
 * @author liming
 */
@Controller
@RequestMapping("/api")
public class ApiController {


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


    /**
     * 获取当前登录的企业账号下的所有用户信息
     */
    @RequestMapping(value = "/enterprise/users", method = RequestMethod.GET)
    @ResponseBody
    public List<com.markeveryday.model.User> getUsersByEnterprise() {

        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return Collections.emptyList();
        }
        Long accountId = enterprise.getAccountId();
        List<Role> roles = roleService.getRolesByAccountId(accountId);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        for (Role r : roles) {
            if (AccountRole.ROLE_SUPER_ADMIN.equals(r.getRole())) {
                return userService.getAllUsers();
            }
        }
        return userService.findByEnterpriseId(enterprise.getId());

    }

    /**
     * 获取当前登录的企业账号下的组信息
     */
    @RequestMapping(value = "/enterprise/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> getGroupsByEnterprise() {
        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return Collections.emptyList();
        }
        return groupService.findByEnterpriseId(enterprise.getId());

    }

    /**
     * 获取当前登录的企业信息
     */
    @RequestMapping(value = "/enterprise", method = RequestMethod.GET)
    @ResponseBody
    public Enterprise getLoginEnterprise() {
        User user = LoginHelper.getLoginUser();
        if (user == null) {
            return null;
        }
        String enterpriseAdminUsername = user.getUsername();
        Account account = accountService.findByUsername(enterpriseAdminUsername);
        if (account != null) {
            return enterpriseService.findByAccountId(account.getId());
        }
        return null;
    }

    /**
     * 获取当前登录的企业的用户信息,包括组信息,企业信息
     *
     * @see #getUsersByEnterprise()
     */
    //TODO 改为批量查询
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public EnterpriseGroupUser getUsers() {
        Enterprise enterprise = getLoginEnterprise();
        if (enterprise == null) {
            return null;
        }
        List<Group> groups = groupService.findByEnterpriseId(enterprise.getId());
        List<GroupUser> groupUsers = new ArrayList<>();
        for (Group group : groups) {
            List<com.markeveryday.model.User> users =
                    userService.findByGroupId(group.getId());
            GroupUser gu = new GroupUser();
            gu.setGroup(group);
            gu.setUsers(users);
            groupUsers.add(gu);
        }
        EnterpriseGroupUser egu = new EnterpriseGroupUser();
        egu.setEnterprise(enterprise);
        egu.setGroupUsers(groupUsers);
        return egu;
    }

    /**
     * 按照昵称搜索用户
     *
     * @see #getUsersByEnterprise()
     */

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    @ResponseBody
    public List<com.markeveryday.model.User> searchUsers(@RequestParam String query) {

        List<com.markeveryday.model.User> users = getUsersByEnterprise();
        List<com.markeveryday.model.User> resultUsers = new ArrayList<>();
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        for (com.markeveryday.model.User u : users) {
            String nickName = u.getNickname();
            if (StringUtils.isNotEmpty(nickName)) {
                if (StringUtils.contains(nickName, query)) {
                    resultUsers.add(u);
                }
            }
        }
        return resultUsers;
    }


    /**
     * 获取所有图书列表
     *
     * @return 所有图书列表或者空
     */
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.findAll();
    }


    /**
     * 通过调用豆瓣api获取查询图书信息
     *
     * @param query  查询字符
     * @param limits 记录条目限制
     * @return 图书列表
     */
    @RequestMapping(value = "/douban/books/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> searchBookUsingDoubanApi(@RequestParam String query, @RequestParam Integer limits) {

        List<Book> books = new ArrayList<>();

        if (StringUtils.isEmpty(query)) {
            return Collections.EMPTY_LIST;
        }

        limits = limits == null || limits <= 0 ? 10 : limits;

        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("count", limits.toString());

        //String url = EnvUtil.getConfigValueByKey("douban.books.search.api");
        String url = Constants.DOUBAN_BOOK_SEARCH_API;
        if (StringUtils.isEmpty(url)) {
            throw new IllegalStateException("豆瓣图书查询api未设置");
        }

        String response = HttpClientUtil.get(url, params);
        if (StringUtils.isEmpty(response)) {
            return Collections.EMPTY_LIST;
        }

        JsonNode booksNode = JsonHelpler.getJsonTree(response).get("books");
        if (booksNode != null) {

            Iterator<JsonNode> iterator = booksNode.elements();
            while (iterator.hasNext()) {
                JsonNode book = iterator.next();
                String title = book.get("title").asText();
                String imageUrl = book.get("image").asText();
                String summary = book.get("summary").asText();

                ArrayNode authorsNode = (ArrayNode) book.get("author");

                StringBuilder stringBuilder = new StringBuilder();
                Iterator<JsonNode> authorsIterator = authorsNode.elements();
                while (authorsIterator.hasNext()) {
                    stringBuilder.append(StringUtils.strip(authorsIterator.next().toString(), "\""));
                    stringBuilder.append(" ");
                }

                if (StringUtils.isEmpty(title) || StringUtils.isEmpty(imageUrl) || StringUtils.isEmpty(summary)) {
                    continue;
                }
                Book markBook = new Book();
                markBook.setName(title);
                markBook.setSummary(summary);
                markBook.setDeleteStatus(false);
                markBook.setCoverImage(imageUrl);
                markBook.setCreateTime(new Date());
                markBook.setModTime(new Date());
                markBook.setAuthor(stringBuilder.toString());
                books.add(markBook);
            }
            return books;
        }

        return Collections.EMPTY_LIST;
    }

    @RequestMapping(value = "/communities", method = RequestMethod.GET)
    @ResponseBody
    public List<CommunityCategoryBean> getCommunities() {

        List<Community> communities = communityService.getAllCommunities();
        if (CollectionUtils.isEmpty(communities)) {
            return null;
        }

        List<CommunityCategoryBean> communityCategoryBeens = new ArrayList<>();
        for (Community c : communities) {
            CommunityCategoryRel rel = communityCategoryRelService.getByCommunityId(c.getId());
            if (rel != null) {
                Category category = categoryService.findById(rel.getCategoryId());
                if (category != null) {
                    communityCategoryBeens.add(new CommunityCategoryBean(c, category));
                }
            }

        }
        return communityCategoryBeens;
    }


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }



    @RequestMapping(value = "/group/book/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public GroupBookRel getGroupBookRel(@PathVariable Long groupId) {
        return groupBookRelService.findByGroupId(groupId);

    }


}
