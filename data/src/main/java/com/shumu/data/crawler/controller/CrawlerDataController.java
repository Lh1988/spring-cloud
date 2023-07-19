package com.shumu.data.crawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.data.crawler.entity.CrawlerData;
import com.shumu.data.crawler.service.ICrawlerDataService;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-05-05
*/
@Tag(name = "爬虫参数")
@RestController
@RequestMapping("/data/crawler/data")
public class CrawlerDataController extends BaseController<CrawlerData, ICrawlerDataService> {
    
}
