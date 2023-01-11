package com.shumu.common.base.controller;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
/**
* @description: 
* @author: Li
* @date: 2022-12-26
*/
@RestController
public class ErrorController extends BasicErrorController {
    public ErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body =this.getErrorAttributes(request,this.getErrorAttributeOptions(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body,status);
    }
    
}
