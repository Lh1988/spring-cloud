package com.shumu.data.database.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.data.database.entity.DataTableField;
import com.shumu.data.database.service.ITableFieldService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
/**
* @description: 
* @author: Li
* @date: 2023-02-01
*/
@Slf4j
@Tag(name = "字段操作")
@RestController
@RequestMapping("/data/db/field")
public class TableFieldController extends BaseController<DataTableField, ITableFieldService> {
    @Autowired
    private ITableFieldService tableFieldService;
    @Override
    @DeleteMapping("/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        DataTableField field = new DataTableField();
        field.setId(id);
        field.setStatus(3);
        try {
            tableFieldService.updateById(field);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        }    
    }

    @Override
    @DeleteMapping("/deleteBatch")
    public BaseResponse<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        String[] list = ids.split(",");
        List<DataTableField> fields = new ArrayList<>();
        for (String id : list) {
            DataTableField field = new DataTableField();
            field.setId(id);
            field.setStatus(3);
            fields.add(field);
        }
        try {
            tableFieldService.updateBatchById(fields);
            log.info("删除成功");
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        } 
    }
    @GetMapping("/order")
    public BaseResponse<?> orderField(@RequestParam(name = "ids") String ids) {
        String[] list = ids.split(",");
        int index = Integer.parseInt(list[0]);
        List<DataTableField> fields = new ArrayList<>();
        for (int i = 1; i < list.length; i++) {
            String id = list[i];
            DataTableField field = new DataTableField();
            field.setId(id);
            field.setFieldOrder(index+i);
            fields.add(field);
        }
        try {
            tableFieldService.updateBatchById(fields);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        } 
    }
    @GetMapping("/fields")
    public BaseResponse<?> getTableFields(@RequestParam(name = "tableId") String tableId) {
        List<DataTableField> list=tableFieldService.getFieldsOfTable(tableId);
        return BaseResponse.ok(list);
    }
}
