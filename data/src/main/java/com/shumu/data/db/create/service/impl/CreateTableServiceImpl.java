package com.shumu.data.db.create.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shumu.data.db.create.mapper.CreateTableMapper;
import com.shumu.data.db.create.model.CreateTableModel;
import com.shumu.data.db.create.service.ICreateTableService;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Service
public class CreateTableServiceImpl implements ICreateTableService{
    @Autowired
    private CreateTableMapper createTableMapper;
    @Override
    public void createTable(CreateTableModel table) {
        createTableMapper.createTable(table);        
    }

    @Override
    public void alterTable(CreateTableModel table) {
        createTableMapper.alterTable(table);
    }

    @Override
    public CreateTableModel getTableModel(String id) {
        try {
            CreateTableModel table = createTableMapper.getTableModel(id); 
            return  table;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void dropTable(String tableName) {
        createTableMapper.dropTable(tableName);        
    }
    
}
