package com.shumu.common.security.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.shumu.common.constant.CommonConstant;
import com.shumu.common.security.authority.CommonGrantedAuthority;
import com.shumu.common.security.model.UserDetailsModel;
import com.shumu.common.security.model.UserModel;
import com.shumu.common.security.service.ICommonUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: Li
 * @Date: 2022-01-18
 * @LastEditTime: 2022-01-18
 * @LastEditors: Li
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ICommonUserService commonUserService;
    @Value("${shumu.timeout.account}")
    private long accountTimeout;
    @Value("${shumu.timeout.credential}")
    private long credentialTimeout;
    @Value("${shumu.locked.time}")
    private long lockedTime;
    @Value("${shumu.locked.count}")
    private int errorCount;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = commonUserService.getUserByName(username);
        UserDetailsModel userDetails = new UserDetailsModel();
        userDetails.setUsername(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        userDetails.setAuthorities(authorities);
        log.info(username);
        if (user != null) {
            userDetails.setPassword(user.getPassword());
            userDetails.setId(user.getId());
            userDetails.setUserId(user.getUserId());
            /* 判断账号是否启用 */
            boolean enabled = true;
            if (user.getStatus() == 0) {
                enabled = false;
            }
            userDetails.setEnabled(enabled);
            /* 判断账号是否过期 */
            boolean accountNonExpired = true;
            if (accountTimeout > 0) {
                long last = user.getLastLoginTime().toEpochSecond(ZoneOffset.of("+8"));
                long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                if (now - last > accountTimeout) {
                    accountNonExpired = false;
                }
            }
            userDetails.setAccountNonExpired(accountNonExpired);
            /* 判断密码/凭证是否过期 */
            boolean credentialsNonExpired = true;
            if (credentialTimeout > 0) {
                long update = user.getUpdateCredentialTime().toEpochSecond(ZoneOffset.of("+8"));
                long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                if (now - update > credentialTimeout) {
                    credentialsNonExpired = false;
                }
            }
            userDetails.setCredentialsNonExpired(credentialsNonExpired);
            /* 判断用户是否被锁定 */
            boolean accountNonLocked = true;
            if (user.getStatus() == CommonConstant.STATUS_LOCKED) {
                accountNonLocked = false;
            } else {
                if (lockedTime > 0 && errorCount > 0) { 
                    long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                    long last = user.getLastErrorTime().toEpochSecond(ZoneOffset.of("+8"));
                    int count = user.getLoginErrorCount();
                    if(count >= errorCount && now - last > lockedTime){
                        accountNonLocked = false;
                    }
                }
            }
            userDetails.setAccountNonLocked(accountNonLocked);
            /* 活动的账户获取其角色与权限*/
            if (!accountNonLocked || !credentialsNonExpired || !accountNonExpired || !enabled) {
                String userId = user.getUserId();
                if (userId != null) {    
                    List<String> roles = commonUserService.getRoles(userId);
                    List<String> permissions = commonUserService.getPermissions(userId);
                    // 添加角色
                    if (roles != null && roles.size() > 0) {
                        for (String role : roles) {
                            GrantedAuthority grantedAuthority = new CommonGrantedAuthority(role);
                            authorities.add(grantedAuthority);
                        }
                    }
                    // 添加权限
                    if (permissions != null && permissions.size() > 0) {
                        for (String permission : permissions) {
                            GrantedAuthority grantedAuthority = new CommonGrantedAuthority(permission);
                            authorities.add(grantedAuthority);
                        }
                    }
                }
            }
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }

}
