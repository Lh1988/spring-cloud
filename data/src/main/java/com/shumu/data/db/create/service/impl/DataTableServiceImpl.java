package com.shumu.data.db.create.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.create.entity.DataCreateTable;
import com.shumu.data.db.create.mapper.DataTableMapper;
import com.shumu.data.db.create.service.IDataTableService;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Service
public class DataTableServiceImpl extends ServiceImpl<DataTableMapper,DataCreateTable> implements IDataTableService {
    @Autowired
    private DataTableMapper dataTableMapper;
    @Override
    public String getCurrentDatabase() {
        try {
            return dataTableMapper.getCurrentDatabase();
        } catch (Exception e) {
            return null;
        }        
        
    }
    @Override
    public List<DataCreateTable> getDbTables() {
        try {
            return dataTableMapper.getDbTables();
        } catch (Exception e) {
            return null;
        }  
    }
    
}
