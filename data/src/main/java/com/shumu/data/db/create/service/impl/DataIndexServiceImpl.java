package com.shumu.data.db.create.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.db.create.entity.DataCreateIndex;
import com.shumu.data.db.create.mapper.DataIndexMapper;
import com.shumu.data.db.create.service.IDataIndexService;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Service
public class DataIndexServiceImpl extends ServiceImpl<DataIndexMapper,DataCreateIndex> implements IDataIndexService {
    private DataIndexMapper dataIndexMapper;
    @Override
    public List<DataCreateIndex> getDbIndexes(String table, String database) {
        try {
            List<DataCreateIndex> list = dataIndexMapper.getDbIndexes(table, database);
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
