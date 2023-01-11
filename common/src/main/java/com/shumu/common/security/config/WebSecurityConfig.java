package com.shumu.common.security.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import com.shumu.common.security.filter.JwtAuthenticationFilter;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-01-28
 * @LastEditTime: 2022-01-28
 * @LastEditors: Li
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,
                                "/actuator/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/favicon.ico",
                                "/error",
                                "/v3/api-docs/**",
                                "/common/static/**",
                                "/login",
                                "/login/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/login/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), AuthorizationFilter.class)
                .sessionManagement().disable()
                .csrf().disable();
        return http.build();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        return jwtAuthenticationFilter;
    }

    @Bean
    public AuthorizationEventPublisher authorizationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new SpringAuthorizationEventPublisher(applicationEventPublisher);
    }

}