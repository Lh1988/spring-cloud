package com.shumu.data.database.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.entity.DataTableInfo;
import com.shumu.data.database.mapper.DatabaseMapper;
import com.shumu.data.database.model.TableModel;
import com.shumu.data.database.service.IDatabaseService;

/**
 * @description:
 * @author: Li
 * @date: 2023-01-31
 */
@Service
public class DatabaseServiceImpl implements IDatabaseService {
    @Autowired
    private DatabaseMapper databaseMapper;

    @Override
    public boolean createTableByInfo(TableModel table) {
        try {
            databaseMapper.createTableByInfo(table);
            return true;
        } catch (Exception e) {
            return false;           
        }
    }

    @Override
    public boolean createTableByReferenced(TableModel table) {
        try {
            databaseMapper.createTableByReferenced(table);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean changeTable(TableModel table) {
        try {
            databaseMapper.changeTable(table);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public DataTableInfo getDbTableInfo(String table, String database) {
        try {
            return databaseMapper.getDbTableInfo(table, database);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataTableField> getDbTableFields(String table, String database) {
        try {
            return databaseMapper.getDbTableFields(table, database);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DataTableIndex> getDbTableIndexes(String table, String database) {
        try {
            return databaseMapper.getDbTableIndexes(table, database);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean dorpDbTable(String table, String database) {
        try {
            databaseMapper.dropDbTable(table, database);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean dorpDbView(String table, String database) {
        try {
            databaseMapper.dropDbView(table, database);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public TableModel getTableModel(String tableId) {
        return databaseMapper.getTableModel(tableId);
    }

    @Override
    public List<String> getDatabaseNames() {
        return databaseMapper.getDatabaseNames();
    }

    @Override
    public boolean createView(TableModel table) {
        try {
            databaseMapper.createTableView(table);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
