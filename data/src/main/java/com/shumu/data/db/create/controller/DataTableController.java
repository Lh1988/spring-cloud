package com.shumu.data.db.create.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.util.StringUtil;
import com.shumu.common.security.util.JwtUtil;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.entity.DataCreateIndex;
import com.shumu.data.db.create.entity.DataCreateTable;
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
@Tag(name = "表信息操作")
@RestController
@RequestMapping("/data/db/create/table")
public class DataTableController extends BaseController<DataCreateTable, IDataTableService> {
    @Autowired
    private IDataTableService dataTableService;
    @Autowired
    private IDataFieldService dataFieldService;
    @Autowired
    private IDataIndexService dataIndexService;

    @Operation(summary = "获取数据库表信息")
    @GetMapping("/tables")
    public BaseResponse<List<DataCreateTable>> getDbTables(){
        List<DataCreateTable> dbTables = dataTableService.getDbTables();
        BaseResponse<List<DataCreateTable>> result = new BaseResponse<>();
        if(null==dbTables){
           result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setResult(dbTables);
        }
        return result;        
    }

    @Operation(summary = "获取数据库表信息")
    @PostMapping("/addDbTable")
    public BaseResponse<?> addDbTableInfo(@RequestBody DataCreateTable table, HttpServletRequest req) {
        if(null == table){
          return BaseResponse.error("不能为空");
        }
        table.setCreateTime(LocalDateTime.now());
        String token = JwtUtil.resolveToken(req);
        String username = JwtUtil.getUsername(token);
        table.setCreateBy(username);
        boolean success = dataTableService.save(table);
        if(success){
            String id = table.getId();
            List<DataCreateIndex> indexes = dataIndexService.getDbIndexes(table.getTableName(),table.getDatabaseName());
            List<DataCreateField> fields = dataFieldService.getDbFields(table.getTableName(),table.getDatabaseName());
            if(null!=indexes){
               indexes.forEach(item->{
                item.setTableId(id);
                item.setCreateBy(username);
                item.setCreateTime(LocalDateTime.now());
            });
               dataIndexService.saveBatch(indexes);
            }
            if(null!=fields){
                fields.forEach(item->{
                  item.setTableId(id);
                  item.setCreateBy(username);
                  item.setCreateTime(LocalDateTime.now());
                });
                dataFieldService.saveBatch(fields);
             }
            return BaseResponse.ok("添加成功");
        }
        return BaseResponse.error("添加失败");
    }

    @Operation(summary = "判断表名存在")
    @GetMapping("/isExist")
    public BaseResponse<Boolean> isTableNameExist(@RequestParam("tableName") String tableName) {
        BaseResponse<Boolean> result = new BaseResponse<>();
        result.setSuccess(false);
        List<String> names = new ArrayList<>();
        List<DataCreateTable> tables = dataTableService.list();
        if(StringUtil.isNotEmpty(tableName) && null!=tables && !tables.isEmpty()){
            tables.forEach(item->names.add(item.getTableName()));
            if (names.contains(tableName)) {
                result.setResult(true);
            } else {
                result.setResult(false);
            }
        }
        return result;
    }

    @Operation(summary = "删除表")
    @DeleteMapping("/delete")
    @Override
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        boolean success = dataTableService.removeById(id);
        if (success) {
            Map<String,Object> map = new HashMap<>(8);
            map.put("table_id", id);
            dataFieldService.removeByMap(map);
            dataIndexService.removeByMap(map);
            return BaseResponse.ok("删除成功");
        } else {
            return BaseResponse.error("删除失败");
        }
    }
    @Operation(summary = "批量删除表")
    @DeleteMapping("/deleteBatch")
    @Override
    public BaseResponse<?> deleteBatch(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            boolean success = dataTableService.removeById(id);
            if (success) {
                Map<String,Object> map = new HashMap<>(8);
                map.put("table_id", id);
                dataFieldService.removeByMap(map);
                dataIndexService.removeByMap(map);
            } else {
                return BaseResponse.error("删除失败");
            }
        }
        return BaseResponse.ok("批量删除成功");
    }
    @Operation(summary = "更新表状态")
    @GetMapping("/change")
    public BaseResponse<?> change(@RequestParam(name = "id") String id,@RequestParam(name = "status") Integer status) {
        if(null!=id && !"".equals(id)){
            DataCreateTable table = new DataCreateTable();
            table.setId(id);
            table.setStatus(status);
            try {
                dataTableService.updateById(table);
                return BaseResponse.ok("更新成功");
            } catch (Exception e) {
                return BaseResponse.ok("更新失败");
            }
           
        }
        return BaseResponse.ok("参数错误");
    }

    
}
