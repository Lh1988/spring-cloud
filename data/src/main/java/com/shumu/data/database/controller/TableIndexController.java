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
import com.shumu.data.database.entity.DataTableIndex;
import com.shumu.data.database.service.ITableIndexService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
/**
* @description: 
* @author: Li
* @date: 2023-02-08
*/
@Slf4j
@Tag(name = "索引操作")
@RestController
@RequestMapping("/data/db/index")
public class TableIndexController extends BaseController<DataTableIndex, ITableIndexService> {
    @Autowired
    private ITableIndexService tableIndexService;
    @Override
    @DeleteMapping("/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        DataTableIndex index = new DataTableIndex();
        index.setId(id);
        index.setStatus(3);
        try {
            tableIndexService.updateById(index);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        }    
    }

    @Override
    @DeleteMapping("/deleteBatch")
    public BaseResponse<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        String[] list = ids.split(",");
        List<DataTableIndex> indexs = new ArrayList<>();
        for (String id : list) {
            DataTableIndex index = new DataTableIndex();
            index.setId(id);
            index.setStatus(3);
            indexs.add(index);
        }
        try {
            tableIndexService.updateBatchById(indexs);
            log.info("成功删除索引信息："+ids);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        } 
    }
    @GetMapping("/order")
    public BaseResponse<?> orderIndex(@RequestParam(name = "ids") String ids) {
        String[] list = ids.split(",");
        int ind = Integer.parseInt(list[0]);
        List<DataTableIndex> indexs = new ArrayList<>();
        for (int i = 1; i < list.length; i++) {
            String id = list[i];
            DataTableIndex index = new DataTableIndex();
            index.setId(id);
            index.setIndexNum(ind+i);
            indexs.add(index);
        }
        try {
            tableIndexService.updateBatchById(indexs);
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            return BaseResponse.error("删除失败");
        } 
    }
}
