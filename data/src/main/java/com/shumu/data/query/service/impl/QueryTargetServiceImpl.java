package com.shumu.data.query.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.query.entity.DataQueryTarget;
import com.shumu.data.query.mapper.QueryTargetMapper;
import com.shumu.data.query.service.IQueryTargetService;

/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
@Service
public class QueryTargetServiceImpl extends ServiceImpl<QueryTargetMapper, DataQueryTarget> implements IQueryTargetService {
    
}
