package com.markeveryday.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        auth.userDetailsService(userDetailsService);
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
                .antMatchers("/admin/**").hasAuthority(AccountRole.ROLE_ADMIN.name())
                .antMatchers("/app/**").hasAuthority(AccountRole.ROLE_USER.name())
                .antMatchers("/api/**").hasAuthority(AccountRole.ROLE_API_CALL.name())
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .rememberMe().rememberMeParameter("rememberMe")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/403");
    }

}
