package com.shumu.data.db.create.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.data.db.create.entity.DataCreateIndex;
import com.shumu.data.db.create.service.IDataIndexService;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
* @description: 
* @author: Li
* @date: 2023-02-09
*/
@Tag(name = "索引信息操作")
@RestController
@RequestMapping("/data/db/create/index")
public class DataIndexController extends BaseController<DataCreateIndex, IDataIndexService> {
    @Autowired
    private IDataIndexService dataIndexService;
    @Override
    @DeleteMapping("/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        DataCreateIndex index = new DataCreateIndex();
        index.setId(id);
        index.setStatus(3);
        try {
            dataIndexService.updateById(index);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        }    
    }
}
