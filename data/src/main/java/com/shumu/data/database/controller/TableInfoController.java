package com.shumu.data.database.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.util.StringUtil;
import com.shumu.data.database.constant.DatabaseConstant;
import com.shumu.data.database.entity.DataTableInfo;
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
 * @date: 2023-01-30
 */
@Slf4j
@Tag(name = "数据表操作")
@RestController
@RequestMapping("/data/db/table")
public class TableInfoController extends BaseController<DataTableInfo, ITableInfoService> {
    @Autowired
    private ITableInfoService tableInfoService;
    @Autowired
    private ITableFieldService tableFieldService;
    @Autowired
    private ITableIndexService tableIndexService;
    @Autowired
    private IDatabaseService databaseService;
    @Operation(summary = "判断表名存在")
    @GetMapping("/isExist")
    public BaseResponse<Boolean> isTableNameExist(@RequestParam("tableName") String tableName) {
        BaseResponse<Boolean> result = new BaseResponse<>();
        result.setSuccess(true);
        List<String> names = tableInfoService.getTableNames();
        if (null != names && !names.isEmpty() && names.contains(tableName)) {
            result.setResult(true);
        } else {
            result.setResult(false);
        }
        return result;
    }
    @Operation(summary = "删除表")
    @DeleteMapping("/delete")
    @Override
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        DataTableInfo tableInfo = tableInfoService.getById(id);
        if (null != tableInfo) {
            boolean success = true;
            if (0 != tableInfo.getStatus()) {
                String database = tableInfo.getDatabaseName();
                String table = tableInfo.getTableName();
                if (DatabaseConstant.TABLE_INFO_UPDATED == tableInfo.getStatus()) {
                    table = tableInfo.getOldName();
                }
                success = databaseService.dorpDbTable(table, database);
                if (!success) {
                    return BaseResponse.error("删除数据库表：" + table + "失败");
                }
            }
            if (success) {
                tableFieldService.removeAllFieldOfTable(id);
                tableIndexService.removeAllIndexOfTable(id);
            }
        }
        return super.delete(id);
    }
    @Operation(summary = "批量删除表")
    @DeleteMapping("/deleteBatch")
    @Override
    public BaseResponse<?> deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            DataTableInfo tableInfo = tableInfoService.getById(id);
            if (null != tableInfo) {
                boolean success = true;
                if (0 != tableInfo.getStatus()) {
                    String database = tableInfo.getDatabaseName();
                    String table = tableInfo.getTableName();
                    if (2 == tableInfo.getStatus()) {
                        table = tableInfo.getOldName();
                    }
                    success = databaseService.dorpDbTable(table, database);
                    if (!success) {
                        return BaseResponse.error("删除数据库表：" + table + "失败");
                    }
                }
                if (success) {
                    tableFieldService.removeAllFieldOfTable(id);
                    tableIndexService.removeAllIndexOfTable(id);
                    tableInfoService.removeById(id);
                }
            }
        }
        return BaseResponse.ok("批量删除成功");
    }
    @Operation(summary = "移除对应数据库表")
    @DeleteMapping("/remove")
    public BaseResponse<?> removeTable(@RequestParam("id") String id,@RequestParam("table") String table, @RequestParam("database") String database) {
        boolean success = databaseService.dorpDbTable(table, database);
        if (success) {
            if(StringUtil.isNotEmpty(id)){
                DataTableInfo tableInfo = new DataTableInfo();
                tableInfo.setId(id);
                tableInfo.setStatus(DatabaseConstant.TABLE_INFO_CREATED);
                tableInfoService.updateById(tableInfo);
            }
            log.info("删除数据库表：" + table);
            return BaseResponse.ok("删除数据库表：" + table + "成功");
        } else {
            return BaseResponse.error("删除数据库表：" + table + "失败");
        }
    }
    @Operation(summary = "更新表状态")
    @GetMapping("/change")
    public BaseResponse<?> change(@RequestParam(name = "id") String id) {
        if(null!=id && !"".equals(id)){
            DataTableInfo table = new DataTableInfo();
            table.setId(id);
            table.setStatus(2);
            try {
                tableInfoService.updateById(table);
                return BaseResponse.ok("更新成功");
            } catch (Exception e) {
                return BaseResponse.ok("更新失败");
            }
           
        }
        return BaseResponse.ok("参数错误");
    }


}
