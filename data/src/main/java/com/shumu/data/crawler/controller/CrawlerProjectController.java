package com.shumu.data.crawler.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.data.crawler.entity.CrawlerProject;
import com.shumu.data.crawler.service.ICrawlerProjectService;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-05-05
*/
@Tag(name = "爬虫数据")
@RestController
@RequestMapping("/data/crawler/project")
public class CrawlerProjectController extends BaseController<CrawlerProject, ICrawlerProjectService> {
    
}
