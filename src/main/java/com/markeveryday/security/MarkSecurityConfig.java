package com.markeveryday.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.markeveryday.bean.AccountRole;

/**
 * MarkMark 安全配置
 *
 * @author liming
 */
@Configuration
@EnableWebSecurity
public class MarkSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MarkUserDetailsService userDetailsService;

    /**
     * 基于数据库的用户认证
     *
     * @param auth
     *
     * @throws Exception
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }

    /**
     * URL权限控制
     *
     * @param http
     *
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/admin/**").hasRole(AccountRole.ADMIN.name())
                .antMatchers("/app/**").hasRole(AccountRole.COMMON_USER.name())
                .antMatchers("/api/**").hasRole(AccountRole.API_CALL.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

}
