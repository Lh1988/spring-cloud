package com.shumu.data.db.create.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.entity.DataCreateIndex;
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

    @Override
    public List<DataCreateField> getDbFields(String table, String database) {
        try {
            List<DataCreateField> list = dataTableMapper.getDbFields(table, database);
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

    @Override
    public List<DataCreateIndex> getDbIndexes(String table, String database) {
        try {
            List<DataCreateIndex> list = dataTableMapper.getDbIndexes(table, database);
            if(null!=list && !list.isEmpty()){
                List<DataCreateIndex> indexes = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    DataCreateIndex index = new DataCreateIndex();
                    index.setIndexName(list.get(i).getIndexName());
                    if("PRIMARY".equals(list.get(i).getIndexName())){
                      index.setIndexType(2);
                    }else{
                      index.setIndexType(list.get(i).getIndexType()); 
                    }
                    index.setOldName(list.get(i).getOldName());
                    String field = list.get(i).getIndexField();
                    while (i+1<list.size() && list.get(i).getIndexName().equals(list.get(i+1).getIndexName())) {
                        field += "," + list.get(i+1).getIndexField();
                        i++;
                    }
                    index.setIndexField(field);
                    index.setOrderNum(indexes.size());
                    index.setStatus(1);
                    indexes.add(index);
                }
                return indexes;
            } 
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
}
