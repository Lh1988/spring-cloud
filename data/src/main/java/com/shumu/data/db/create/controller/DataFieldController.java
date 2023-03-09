package com.shumu.data.db.create.controller;

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
import com.shumu.common.util.StringUtil;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.service.IDataFieldService;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Tag(name = "字段信息操作")
@RestController
@RequestMapping("/data/db/create/field")
public class DataFieldController extends BaseController<DataCreateField, IDataFieldService> {
    @Autowired
    private IDataFieldService dataFieldService;
    @Override
    @DeleteMapping("/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        DataCreateField field = new DataCreateField();
        field.setId(id);
        field.setStatus(3);
        try {
            dataFieldService.updateById(field);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        }    
    }
    @Override
    @DeleteMapping("/deleteBatch")
    public BaseResponse<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        if(StringUtil.isNotEmpty(ids)){
            String[] idArr = ids.split(",", 0);
            List<DataCreateField> fields = new ArrayList<>();
            for (String id : idArr) {
                DataCreateField field = new DataCreateField();
                field.setId(id);
                field.setStatus(3);
                fields.add(field);
            }
            try {
                dataFieldService.updateBatchById(fields);
                return BaseResponse.ok("批量删除成功");
            } catch (Exception e) {
                return BaseResponse.error("批量删除失败");
            }  
        } else {
            return BaseResponse.error("批量删除失败");
        } 
    }
    @GetMapping("/order")
    public BaseResponse<?> orderField(@RequestParam(name = "ids") String ids) {
        String[] list = ids.split(",");
        int num = Integer.parseInt(list[0]);
        List<DataCreateField> fields = new ArrayList<>();
        for (int i = 1; i < list.length; i++) {
            String id = list[i];
            DataCreateField field = new DataCreateField();
            field.setId(id);
            field.setOrderNum(num+i);
            fields.add(field);
        }
        try {
            dataFieldService.updateBatchById(fields);
            return BaseResponse.ok("移动成功");
        } catch (Exception e) {
            return BaseResponse.error("移动失败");
        } 
    }
    @GetMapping("/fields")
    public BaseResponse<List<DataCreateField>> getDbFields(@RequestParam(name = "table") String table,@RequestParam(name = "database") String database) {
        List<DataCreateField> dbFields = dataFieldService.getDbFields(table, database);
        BaseResponse<List<DataCreateField>> result = new BaseResponse<>();
        if(null==dbFields){
           result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setResult(dbFields);
        }
        return result;  
    }


}
