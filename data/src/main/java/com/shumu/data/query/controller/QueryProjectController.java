package com.shumu.data.query.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.data.query.entity.DataQueryConnect;
import com.shumu.data.query.entity.DataQueryProject;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.entity.DataQueryTarget;
import com.shumu.data.query.model.QuerySaveModel;
import com.shumu.data.query.service.IQueryProjectService;
import com.shumu.data.query.service.IQuerySaveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-06-01
*/
@Tag(name = "查询指标")
@RestController
@RequestMapping("/data/query/project")
public class QueryProjectController extends BaseController<DataQueryProject, IQueryProjectService> {
    @Autowired
    private IQueryProjectService queryProjectService;
    @Autowired
    private IQuerySaveService querySaveService;

    private static final String SELECT_STRING = "SELECT";
    private static final String SYS_STRING = "sys_";

    @Operation(summary = "获取项目指标")
    @GetMapping("/targets")
    public BaseResponse<List<DataQueryTarget>> targets(@RequestParam("projectId") String projectId) {
        BaseResponse<List<DataQueryTarget>> response = new BaseResponse<>();
        try {
            List<DataQueryTarget> list = queryProjectService.getTarget4Project(projectId);
            response.setSuccess(true);
            response.setResult(list);
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return response; 
    }

    @Operation(summary = "获取项目表")
    @GetMapping("/tables")
    public BaseResponse<List<DataQueryTable>> tables(@RequestParam("projectId") String projectId) {
        BaseResponse<List<DataQueryTable>> response = new BaseResponse<>();
        try {
            List<DataQueryTable> list = queryProjectService.getTable4Project(projectId);
            response.setSuccess(true);
            response.setResult(list);
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return response; 
    }

    @Operation(summary = "获取项目表关联")
    @GetMapping("/connects")
    public BaseResponse<List<DataQueryConnect>> connects(@RequestParam("projectId") String projectId) {
        BaseResponse<List<DataQueryConnect>> response = new BaseResponse<>();
        try {
            List<DataQueryConnect> list = queryProjectService.getConnect4Project(projectId);
            response.setSuccess(true);
            response.setResult(list);
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return response; 
    }
    @Operation(summary = "查询数据")
    @GetMapping("/query")
    public BaseResponse<List<Map<String,Object>>> query(@RequestParam("sql") String sql) {
        BaseResponse<List<Map<String,Object>>> response = new BaseResponse<>();
        if(!sql.startsWith(SELECT_STRING)){
           response.setSuccess(false);
           response.setMessage("只能为查询语句");
           return response;
        }
        if(sql.indexOf(SYS_STRING)>0){
            response.setSuccess(false);
            response.setMessage("可能涉及系统表");
            return response;
         }
        try {
            List<Map<String,Object>> list = queryProjectService.queryData(sql);
            response.setSuccess(true);
            response.setResult(list);
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return response; 
    }
    @Operation(summary = "保存查询")
    @PutMapping("/addQuery")
    public BaseResponse<?> addQuery(QuerySaveModel model) {
        try {
            querySaveService.addQuerySave(model);
        } catch (Exception e) {
            return BaseResponse.error("添加失败");
        }
        return BaseResponse.error("添加成功");
    }
    @Operation(summary = "保存查询")
    @PostMapping("/updateQuery")
    public BaseResponse<?> updateQuery(QuerySaveModel model) {
        try {
            querySaveService.updateQuerySave(model);
        } catch (Exception e) {
            return BaseResponse.error("添加失败");
        }
        return BaseResponse.error("添加成功");
    }
    @Operation(summary = "查询数据")
    @GetMapping("/getQuery")
    public BaseResponse<QuerySaveModel> getQuery(String id) {
        BaseResponse<QuerySaveModel> response = new BaseResponse<>();
        QuerySaveModel model = querySaveService.getQuerySaveModel(id);
        response.setSuccess(true);
        response.setResult(model);
        return response;
    }
}
