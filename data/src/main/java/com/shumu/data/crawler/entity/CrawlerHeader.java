package com.shumu.data.crawler.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 
* @author: Li
* @date: 2023-05-06
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "爬虫数据")
@TableName("data_crawler_header")
public class CrawlerHeader extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "header名")
    @Excel(name="header名", width = 60)
    private String headerName;
    @Schema(title= "header值")
    @Excel(name="header值", width = 60)
    private String headerValue;
    @Schema(title= "是否参数")
    @Excel(name="是否参数", width = 60)
    private Boolean isParam;
}
