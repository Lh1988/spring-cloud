package com.shumu.data.query.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.data.query.entity.DataQueryTable;
import com.shumu.data.query.service.IQueryTableService;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-06-01
*/
@Tag(name = "查询指标")
@RestController
@RequestMapping("/data/query/table")
public class QueryTableController extends BaseController<DataQueryTable, IQueryTableService> {
    
}
