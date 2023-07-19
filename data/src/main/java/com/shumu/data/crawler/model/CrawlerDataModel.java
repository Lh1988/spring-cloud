package com.shumu.data.crawler.model;

import lombok.Data;
/**
* @description: 
* @author: Li
* @date: 2023-05-16
*/
@Data
public class CrawlerDataModel {
    private String table;
    private Integer type;
    private String key;
    private String[] fields;
    private String[] keys;
}
