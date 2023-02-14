package com.shumu.data.db.update.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.data.db.update.entity.DataUpdateTable;
import com.shumu.data.db.update.service.IDataUpdateService;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-02-14
*/
@Tag(name = "数据更新操作")
@RestController
@RequestMapping("/data/db/update")
public class DataUpdateController extends BaseController<DataUpdateTable, IDataUpdateService> {
    
}
