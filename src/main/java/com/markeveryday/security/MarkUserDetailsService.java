package com.markeveryday.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.markeveryday.model.Account;
import com.markeveryday.model.Role;
import com.markeveryday.service.AccountService;
import com.markeveryday.service.RoleService;

/**
 * 查询用户与对应的角色供验证使用
 *
 * @author liming
 */
@Service
public class MarkUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountService.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("Username not found! " + username);
        }
        List<Role> roles = roleService.getRolesByAccountId(account.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role r : roles) {
            authorities.add(new SimpleGrantedAuthority(r.getRole().toString()));
        }
        User user = new User(account.getUsername(), account.getPassword(), authorities);
        return user;
    }
}
