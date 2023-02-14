package com.shumu.data.db.create.mapper;

import com.shumu.data.db.create.model.CreateTableModel;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
public interface CreateTableMapper {
    /**
     * createTable
     * @param table
     */
    public void createTable(CreateTableModel table);
    /**
     * alterTable
     * @param table
     */
    public void alterTable(CreateTableModel table);
    /**
     * getTableModel
     * @param id
     * @return
     */
    public CreateTableModel getTableModel(String id);
    /**
     * drop table
     * @param tableName
     */
    public void dropTable(String tableName);
}
