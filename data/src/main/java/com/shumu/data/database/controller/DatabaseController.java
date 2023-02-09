package com.shumu.data.database.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.util.StringUtil;
import com.shumu.data.database.constant.DatabaseConstant;
import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.entity.DataTableInfo;
import com.shumu.data.database.model.TableModel;
import com.shumu.data.database.service.IDatabaseService;
import com.shumu.data.database.service.ITableFieldService;
import com.shumu.data.database.service.ITableIndexService;
import com.shumu.data.database.service.ITableInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: Li
 * @date: 2023-02-06
 */
@Slf4j
@Tag(name = "数据库操作")
@RestController
@RequestMapping("/data/db/db")
public class DatabaseController {
    @Autowired
    private IDatabaseService databaseService;
    @Autowired
    private ITableInfoService tableInfoService;
    @Autowired
    private ITableFieldService tableFieldService;
    @Autowired
    private ITableIndexService tableIndexService;
    /**
     * create table
     * 
     * @param id
     * @return
     */
    @Operation(summary = "创建数据库表")
    @GetMapping("/create")
    public BaseResponse<?> createTable(@RequestParam("id") String id) {
        /* 参数检验 */
        BaseResponse<?> result = checkTable(id);
        if (!result.isSuccess()) {
            return result;
        }
        TableModel table = (TableModel) result.getResult();
        /* 删除旧表 */
        boolean drop = dropOldTable(table);
        /* 创建新表 */
        boolean success = false;
        if (DatabaseConstant.CREATE_BY_INFO == table.getTableType()) {
            success = databaseService.createTableByInfo(table);
        } else {
            if(StringUtil.isNotEmpty(table.getReferencedSql())){
                if(StringUtil.isNotEmpty(table.getParamValue())){
                    String sql = table.getReferencedSql();
                    String[] values = table.getParamValue().split(",");
                    for (int i = 0; i < values.length; i++) {
                        sql.replace("${p"+i+"}", values[i]);
                    }
                    table.setReferencedSql(sql);
                }
            }
            if (DatabaseConstant.CREATE_BY_REFERENCED == table.getTableType()) {
                success = databaseService.createTableByReferenced(table);
            } else if (DatabaseConstant.CREATE_TABLE_VIEW == table.getTableType()) {
                success = databaseService.createView(table);
            }
        }
        if (success) {
            boolean update = updateAfterCreate(table);
            if (update) {
                log.info("创建表" + table.getTableName() + "成功!");
                return BaseResponse.ok("创建表" + table.getTableName() + "成功");
            } else {
                return BaseResponse.ok("创建表" + table.getTableName() + "成功,但更新信息失败");
            }
        } else {
            if (drop && DatabaseConstant.TABLE_INFO_CREATED != table.getStatus()) {
                DataTableInfo tableInfo = new DataTableInfo();
                tableInfo.setId(table.getId());
                tableInfo.setStatus(DatabaseConstant.TABLE_INFO_CREATED);
                tableInfoService.updateById(tableInfo);
                return BaseResponse.error("重建表失败,删除旧表");
            }
            return BaseResponse.error("创建表失败");
        }
    }

    @Operation(summary = "创建数据库表")
    @GetMapping("/change")
    public BaseResponse<?> changeTable(@RequestParam("id") String id) {
        /* 参数检验 */
        BaseResponse<?> result = checkTable(id);
        if (!result.isSuccess()) {
            return result;
        }
        TableModel table = (TableModel) result.getResult();
        if (DatabaseConstant.CREATE_BY_INFO != table.getTableType()) {
            return BaseResponse.error("视图与关联表不能修改，请重新生成");
        } 
        configTableField(table);
        boolean success = databaseService.changeTable(table);
        if(success){

            boolean update = updateAfterCreate(table);
            if (update) {
                log.info("修改表" + table.getTableName() + "成功!");
                return BaseResponse.ok("修改表" + table.getTableName() + "成功");
            } else {
                return BaseResponse.ok("修改表" + table.getTableName() + "成功,但更新信息失败");
            }
        }
        return BaseResponse.error("修改表失败");
    }

    private BaseResponse<?> checkTable(String id) {
        /* 参数检验 */
        if (StringUtil.isEmpty(id)) {
            return BaseResponse.error("输入参数不能为空！");
        }
        TableModel table = databaseService.getTableModel(id);
        if (null == table) {
            return BaseResponse.error("表格信息不存在！");
        }

        if (null == table.getFieldList() || table.getFieldList().isEmpty()) {
            return BaseResponse.error("表格字段数不能为0个！");
        }
        return BaseResponse.ok(table);
    }

    private boolean dropOldTable(TableModel table) {
        String tableName = table.getTableName();
        String oldName = table.getOldName();
        if (null != oldName && !"".equals(oldName) && !oldName.equals(tableName)) {
            tableName = oldName;
        }
        boolean dropTable = databaseService.dorpDbTable(tableName, table.getDatabaseName());
        boolean dropView = databaseService.dorpDbView(tableName, table.getDatabaseName());
        return dropTable || dropView ;
    }

    private void configTableField(TableModel table) {
        List<DataTableField> list = table.getFieldList();
        List<DataTableField> changedList = new ArrayList<>();
        List<DataTableField> addedList = new ArrayList<>();
        List<DataTableField> dropList = new ArrayList<>();
        String afterField = "";
        for (DataTableField item : list) {
            if (DatabaseConstant.FIELD_INFO_DELETED == item.getStatus()) {
                dropList.add(item);
            } else {
                item.setFieldAfter(afterField);
                afterField = item.getFieldName();
                if (DatabaseConstant.FIELD_INFO_CREATED == item.getStatus()) {                    
                    addedList.add(item);
                } else if (DatabaseConstant.FIELD_INFO_UPDATED == item.getStatus()) {
                    changedList.add(item);
                } else {
                    if(!afterField.equals(item.getFieldAfter())){
                        item.setStatus(DatabaseConstant.FIELD_INFO_UPDATED);
                        changedList.add(item);
                    }
                }
            }
        }
        List<DataTableField> fields = new ArrayList<>();
        fields.addAll(dropList);
        fields.addAll(addedList);
        fields.addAll(changedList);
        table.setFieldList(fields);
    }

    private boolean updateAfterCreate(TableModel table) {
        List<DataTableField> fields = table.getFieldList();
        List<DataTableField> changedFields = new ArrayList<>();
        List<String> removedFields= new ArrayList<>();
        for (DataTableField item : fields) {
            if (DatabaseConstant.FIELD_INFO_DELETED == item.getStatus()) {
                removedFields.add(item.getId());
            } else if (DatabaseConstant.DB_TABLE_CREATED != item.getStatus()) {
                item.setOldName(item.getFieldName());
                item.setStatus(DatabaseConstant.DB_TABLE_CREATED);
                changedFields.add(item);
            }
        }
        boolean removeFields = true;
        if(!removedFields.isEmpty()){
            removeFields = tableFieldService.removeBatchByIds(removedFields);
        }
        boolean updateFields = true;
        if(!changedFields.isEmpty()){
            updateFields = tableFieldService.updateBatchById(changedFields);
        }
        List<DataTableIndex> indexes = table.getIndexList();
        List<DataTableIndex> changedIndexes = new ArrayList<>();
        List<String> removedIndexes= new ArrayList<>();
        for (DataTableIndex item : indexes) {
            if (DatabaseConstant.FIELD_INFO_DELETED == item.getStatus()) {
                removedIndexes.add(item.getId());
            } else if (DatabaseConstant.DB_TABLE_CREATED != item.getStatus()) {
                item.setOldName(item.getIndexName());
                item.setStatus(DatabaseConstant.DB_TABLE_CREATED);
                changedIndexes.add(item);
            }
        }
        boolean removeIndexes = true;
        if(!removedIndexes.isEmpty()){
            removeIndexes = tableIndexService.removeBatchByIds(removedIndexes);
        }
        boolean updateIndexes = true;
        if(!changedIndexes.isEmpty()){
            updateIndexes = tableIndexService.updateBatchById(changedIndexes);
        }
        DataTableInfo tableInfo = new DataTableInfo();
        tableInfo.setId(table.getId());
        tableInfo.setStatus(DatabaseConstant.DB_TABLE_CREATED);
        tableInfo.setOldName(table.getTableName());
        boolean change = tableInfoService.updateById(tableInfo);
        return removeFields && updateFields &&removeIndexes && updateIndexes && change;
    }
}
