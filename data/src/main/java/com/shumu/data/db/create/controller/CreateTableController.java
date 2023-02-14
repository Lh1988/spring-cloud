package com.shumu.data.db.create.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.util.StringUtil;
import com.shumu.data.db.create.constant.CreateTableConstant;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.entity.DataCreateIndex;
import com.shumu.data.db.create.entity.DataCreateTable;
import com.shumu.data.db.create.model.CreateTableModel;
import com.shumu.data.db.create.service.ICreateTableService;
import com.shumu.data.db.create.service.IDataFieldService;
import com.shumu.data.db.create.service.IDataIndexService;
import com.shumu.data.db.create.service.IDataTableService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Tag(name = "字段信息操作")
@RestController
@RequestMapping("/data/db/create/create")
public class CreateTableController {
    @Autowired
    private ICreateTableService createTableService;
    @Autowired
    private IDataTableService dataTableService;
    @Autowired
    private IDataFieldService dataFieldService;
    @Autowired
    private IDataIndexService dataIndexService;

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
        CreateTableModel table = (CreateTableModel) result.getResult();
        /* 删除旧表 */
        boolean drop = dropOldTable(table);
        /* 创建新表 */
        try {
            createTableService.createTable(table);
            boolean update = updateAfterCreate(table);
            if (update) {
                return BaseResponse.ok("创建表" + table.getTableName() + "成功");
            } else {
                return BaseResponse.ok("创建表" + table.getTableName() + "成功,但更新信息失败");
            }
        } catch (Exception e) {
            if (drop && CreateTableConstant.TABLE_ADDED != table.getStatus()) {
                DataCreateTable tableInfo = new DataCreateTable();
                tableInfo.setId(table.getId());
                tableInfo.setStatus(CreateTableConstant.TABLE_ADDED);
                dataTableService.updateById(tableInfo);
                return BaseResponse.error("重建表失败,删除旧表");
            }
            return BaseResponse.error("创建表失败");
        }
    }

    @Operation(summary = "修改数据库表")
    @GetMapping("/alter")
    public BaseResponse<?> alterTable(@RequestParam("id") String id) {
        /* 参数检验 */
        BaseResponse<?> result = checkTable(id);
        if (!result.isSuccess()) {
            return result;
        }
        CreateTableModel table = (CreateTableModel) result.getResult();
        configTableField(table);
        try {
            createTableService.alterTable(table);
            boolean update = updateAfterCreate(table);
            if (update) {
                return BaseResponse.ok("修改表" + table.getTableName() + "成功");
            } else {
                return BaseResponse.ok("修改表" + table.getTableName() + "成功,但更新信息失败");
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Operation(summary = "删除数据库表")
    @DeleteMapping("/drop")
    public BaseResponse<?> dropTable(@RequestParam("id") String id) {
        CreateTableModel table = createTableService.getTableModel(id);
        boolean drop = dropOldTable(table); 
        if(drop){
            DataCreateTable tableInfo = new DataCreateTable();
            tableInfo.setId(table.getId());
            tableInfo.setStatus(CreateTableConstant.TABLE_CREATED);
            boolean change = dataTableService.updateById(tableInfo);
            if(change){
                return BaseResponse.ok("修改表" + table.getTableName() + "成功");
            } else {
                return BaseResponse.ok("修改表" + table.getTableName() + "成功,但更新信息失败");
            }
        } else {
            return BaseResponse.error("删除失败");  
        }
    }

    

    private BaseResponse<?> checkTable(String id) {
        /* 参数检验 */
        if (StringUtil.isEmpty(id)) {
            return BaseResponse.error("输入参数不能为空！");
        }
        CreateTableModel table = createTableService.getTableModel(id);
        if (null == table) {
            return BaseResponse.error("表格信息不存在！");
        }

        if (null == table.getFieldList() || table.getFieldList().isEmpty()) {
            return BaseResponse.error("表格字段数不能为0个！");
        }
        return BaseResponse.ok(table);
    }

    private boolean dropOldTable(CreateTableModel table) {
        String tableName = table.getTableName();
        String oldName = table.getOldName();
        if (StringUtil.isNotEmpty(oldName) && !oldName.equals(tableName)) {
            tableName = oldName;
        }
        if(StringUtil.isNotEmpty(table.getDatabaseName())){
            tableName = table.getDatabaseName()+"." +tableName;
        }
        try {
            createTableService.dropTable(tableName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void configTableField(CreateTableModel table) {
        List<DataCreateField> list = table.getFieldList();
        List<DataCreateField> changedList = new ArrayList<>();
        List<DataCreateField> addedList = new ArrayList<>();
        List<DataCreateField> dropList = new ArrayList<>();
        String afterField = "";
        for (DataCreateField item : list) {
            if (CreateTableConstant.FIELD_DELETED== item.getStatus()) {
                dropList.add(item);
            } else {
                if(CreateTableConstant.INDEX_CREATED == item.getStatus()){
                    if(!afterField.equals(item.getFieldAfter())){
                        item.setStatus(CreateTableConstant.FIELD_CHANGED);
                        item.setFieldAfter(afterField);
                        changedList.add(item);
                    }
                } else {
                    item.setFieldAfter(afterField);
                    if (CreateTableConstant.FIELD_ADDED == item.getStatus()) {                    
                        addedList.add(item);
                    } else if (CreateTableConstant.FIELD_CHANGED == item.getStatus()) {
                        changedList.add(item);
                    }
                }
                afterField = item.getFieldName(); 
            }
        }
        List<DataCreateField> fields = new ArrayList<>();
        fields.addAll(dropList);
        fields.addAll(addedList);
        fields.addAll(changedList);
        table.setFieldList(fields);
    }

    private boolean updateAfterCreate(CreateTableModel table) {
        List<DataCreateField> fields = table.getFieldList();
        List<DataCreateField> changedFields = new ArrayList<>();
        List<String> removedFields= new ArrayList<>();
        String afterField = "";
        for (DataCreateField item : fields) {
            if (CreateTableConstant.FIELD_DELETED == item.getStatus()) {
                removedFields.add(item.getId());
            } else {
                item.setOldName(item.getFieldName());
                item.setStatus(CreateTableConstant.FIELD_CREATED);
                item.setFieldAfter(afterField);
                changedFields.add(item);
                afterField = item.getFieldName();
            }
        }
        boolean removeFields = true;
        if(!removedFields.isEmpty()){
            removeFields = dataFieldService.removeBatchByIds(removedFields);
        }
        boolean updateFields = true;
        if(!changedFields.isEmpty()){
            updateFields = dataFieldService.updateBatchById(changedFields);
        }
        List<DataCreateIndex> indexes = table.getIndexList();
        List<DataCreateIndex> changedIndexes = new ArrayList<>();
        List<String> removedIndexes= new ArrayList<>();
        for (DataCreateIndex item : indexes) {
            if (CreateTableConstant.INDEX_DELETED == item.getStatus()) {
                removedIndexes.add(item.getId());
            } else if (CreateTableConstant.INDEX_CREATED != item.getStatus()) {
                item.setOldName(item.getIndexName());
                item.setStatus(CreateTableConstant.INDEX_CREATED);
                changedIndexes.add(item);
            }
        }
        boolean removeIndexes = true;
        if(!removedIndexes.isEmpty()){
            removeIndexes = dataIndexService.removeBatchByIds(removedIndexes);
        }
        boolean updateIndexes = true;
        if(!changedIndexes.isEmpty()){
            updateIndexes = dataIndexService.updateBatchById(changedIndexes);
        }
        DataCreateTable tableInfo = new DataCreateTable();
        tableInfo.setId(table.getId());
        tableInfo.setStatus(CreateTableConstant.TABLE_CREATED);
        tableInfo.setOldName(table.getTableName());
        boolean change = dataTableService.updateById(tableInfo);
        return removeFields && updateFields &&removeIndexes && updateIndexes && change;
    }
}
