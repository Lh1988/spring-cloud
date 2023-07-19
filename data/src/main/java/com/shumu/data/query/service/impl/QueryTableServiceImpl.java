package com.shumu.data.query.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.mapper.QueryTableMapper;
import com.shumu.data.query.service.IQueryTableService;

/**
* @description: 
* @author: Li
* @date: 2023-05-31
*/
@Service
public class QueryTableServiceImpl extends ServiceImpl<QueryTableMapper, DataQueryTable> implements IQueryTableService {
    
}
