package com.shumu.data.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.mapper.TableIndexMapper;
import com.shumu.data.database.service.ITableIndexService;
/**
* @description: index
* @author: Li
* @date: 2023-01-30
*/
@Service
public class TableIndexServiceImpl  extends ServiceImpl<TableIndexMapper,DataTableIndex> implements ITableIndexService {
    @Autowired
    private TableIndexMapper tableIndexMapper;
    @Override
    public List<DataTableIndex> getIndexesOfTable(String tableId) {
        try {
            List<DataTableIndex> indexes= tableIndexMapper.getIndexesOfTable(tableId);
            return indexes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeAllIndexOfTable(String tableId) {
        try {
            tableIndexMapper.removeAllIndexOfTable(tableId);
            return true;
        } catch (Exception e) {
            return false;
        }        
    }
    
}
