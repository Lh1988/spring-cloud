package com.shumu.data.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.database.entity.DataTableInfo;
import com.shumu.data.database.mapper.TableInfoMapper;
import com.shumu.data.database.service.ITableInfoService;
/**
* @description: table info
* @author: Li
* @date: 2023-01-30
*/
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper,DataTableInfo> implements ITableInfoService{
    @Autowired
    private TableInfoMapper tableInfoMapper;
    @Override
    public List<String> getTableNames() {
        try {
            List<String> names = tableInfoMapper.getTableNames();
            return names;
        } catch (Exception e) {
            return null;
        } 
    }
    
}
