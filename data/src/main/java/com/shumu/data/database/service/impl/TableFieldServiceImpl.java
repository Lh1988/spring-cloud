package com.shumu.data.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.mapper.TableFieldMapper;
import com.shumu.data.database.service.ITableFieldService;
/**
* @description: field
* @author: Li
* @date: 2023-01-30
*/
@Service
public class TableFieldServiceImpl extends ServiceImpl<TableFieldMapper,DataTableField> implements ITableFieldService {
    @Autowired
    private TableFieldMapper tableFieldMapper;
    @Override
    public List<DataTableField> getFieldsOfTable(String tableId) {
        try {
            List<DataTableField> fields = tableFieldMapper.getFieldsOfTable(tableId);
            return fields;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeAllFieldOfTable(String tableId) {
        try {
            tableFieldMapper.removeAllFieldOfTable(tableId);
            return true;
        } catch (Exception e) {
            return false;
        }        
    }
    
}
