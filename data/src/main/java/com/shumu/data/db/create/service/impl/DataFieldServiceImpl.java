package com.shumu.data.db.create.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.mapper.DataFieldMapper;
import com.shumu.data.db.create.service.IDataFieldService;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Service
public class DataFieldServiceImpl extends ServiceImpl<DataFieldMapper,DataCreateField> implements IDataFieldService {
    @Autowired
    private DataFieldMapper dataFieldMapper;
    @Override
    public List<DataCreateField> getDbFields(String table, String database) {
        try {
            List<DataCreateField> list = dataFieldMapper.getDbFields(table, database);
            String afterField = "";
            for (DataCreateField field : list) {
                field.setFieldAfter(afterField);
                field.setOldName(field.getFieldName());
                afterField = field.getFieldName();
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }
}
