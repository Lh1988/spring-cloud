package com.shumu.data.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.entity.DataTableInfo;
import com.shumu.data.database.model.TableModel;

/**
* @description: dba
* @author: Li
* @date: 2023-01-30
*/
public interface DatabaseMapper {
    /**
     * create
     * @param table
     */
    public void createTableByInfo(TableModel table);
    /**
     * create
     * @param table
     */
    public void createTableByReferenced(TableModel table);
    /**
     * create
     * @param table
     */
    public void createTableView(TableModel table);
    /**
     * change
     * @param table
     */
    public void changeTable(TableModel table);
    /**
     * getDbTableInfo
     * @param table
     * @param database
     * @return
     */
    public DataTableInfo getDbTableInfo(@Param("table") String table,@Param("database") String database);
    /**
     * getDbTableFields
     * @param table
     * @param database
     * @return
     */
    public List<DataTableField> getDbTableFields(@Param("table") String table,@Param("database") String database);
    /**
     * getDbTableIndexes
     * @param table
     * @param database
     * @return
     */
    public List<DataTableIndex> getDbTableIndexes(@Param("table") String table,@Param("database") String database);
    /**
     * dorpDbTable
     * @param table
     * @param database
     */
    public void dropDbTable(@Param("table") String table,@Param("database") String database);
    /**
     * dorpDbView
     * @param table
     * @param database
     */
    public void dropDbView(@Param("table") String table,@Param("database") String database);
    /**
     * getTableModel
     * @param tableId
     * @return
     */
    public TableModel getTableModel(@Param("tableId") String tableId);
    /**
     * getDatabaseNames
     * @return
     */
    public List<String> getDatabaseNames();
    /**
     * updateTableStatus
     * @param id
     * @param name
     */
    public void updateTableStatus(@Param("id") String id,@Param("name") String name);
    /**
     * updateFieldStatus
     * @param id
     */
    public void updateFieldStatus(@Param("id") String id);
}
