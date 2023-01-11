package com.shumu.common.security.service.impl;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shumu.common.security.service.IPasswordEncoderService;
/**
* @description: 
* @author: Li
* @date: 2023-01-09
*/
@Service
public class PasswordEncoderServiceImpl implements IPasswordEncoderService{

    @Override
    public PasswordEncoder delegatingPasswordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
}
