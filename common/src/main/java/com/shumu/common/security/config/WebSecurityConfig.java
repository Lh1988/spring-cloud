package com.shumu.common.security.config;

import com.shumu.common.security.filter.AccountPasswordAuthenticationFilter;
import com.shumu.common.security.filter.ValidateCodeAuthenticationFilter;
import com.shumu.common.security.handler.LoginFailureHandler;
import com.shumu.common.security.handler.LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-01-28
 * @LastEditTime: 2022-01-28
 * @LastEditors: Li
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/**","/login")
                .permitAll()
                .and()
                .csrf().disable()
                .addFilterBefore(validateCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .usernameParameter("account")
                        .loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                        .defaultSuccessUrl("/login/loginSuccess"));
        
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public ValidateCodeAuthenticationFilter validateCodeFilter() {
        return new ValidateCodeAuthenticationFilter();
    }

    @Bean
    public AccountPasswordAuthenticationFilter accountPasswordFilter() throws Exception {
        AccountPasswordAuthenticationFilter filter = new AccountPasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }
}
