package com.shumu.data.database.service;

import java.util.List;

import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.entity.DataTableInfo;
import com.shumu.data.database.model.TableModel;
/**
* @description: database
* @author: Li
* @date: 2023-01-31
*/
public interface IDatabaseService {
     /**
     * create
     * @param table
     * @return
     */
    public boolean createTableByInfo(TableModel table);
    /**
     * create
     * @param table
     * @return
     */
    public boolean createTableByReferenced(TableModel table);
    /**
     * create
     * @param table
     * @return
     */
    public boolean createView(TableModel table);
    /**
     * change
     * @param table
     * @return
     */
    public boolean changeTable(TableModel table);
    /**
     * getDbTableInfo
     * @param table
     * @param database
     * @return
     */
    public DataTableInfo getDbTableInfo(String table,String database);
    /**
     * getDbTableFields
     * @param table
     * @param database
     * @return
     */
    public List<DataTableField> getDbTableFields(String table,String database);
    /**
     * getDbTableIndexes
     * @param table
     * @param database
     * @return
     */
    public List<DataTableIndex> getDbTableIndexes(String table, String database);
    /**
     * dorpDbTable
     * @param table
     * @param database
     * @return
     */
    public boolean dorpDbTable(String table,String database);
    /**
     * dorpDbView
     * @param table
     * @param database
     * @return
     */
    public boolean dorpDbView(String table,String database);
    /**
     * getTableModel
     * @param tableId
     * @return
     */
    public TableModel getTableModel(String tableId);
    /**
     * getDatabaseNames
     * @return
     */
    public List<String> getDatabaseNames();
}
