package com.shumu.common.security.authority;

import org.springframework.security.core.GrantedAuthority;
/**
* @Description: 
* @Author: Li
* @Date: 2022-01-27
* @LastEditTime: 2022-01-27
* @LastEditors: Li
*/
public class CommonGrantedAuthority implements GrantedAuthority{
    private String authority;
    public CommonGrantedAuthority(String authority){
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
}
