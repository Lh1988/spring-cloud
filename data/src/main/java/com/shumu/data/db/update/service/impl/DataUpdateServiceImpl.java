package com.shumu.data.db.update.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.update.entity.DataUpdateTable;
import com.shumu.data.db.update.mapper.DataUpdateMapper;
import com.shumu.data.db.update.service.IDataUpdateService;
/**
* @description: DataUpdate
* @author: Li
* @date: 2023-02-14
*/
@Service
public class DataUpdateServiceImpl extends ServiceImpl<DataUpdateMapper,DataUpdateTable> implements IDataUpdateService{
    
}
