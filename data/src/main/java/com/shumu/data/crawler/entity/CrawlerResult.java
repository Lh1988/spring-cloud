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
@Schema(title= "爬虫结果")
@TableName("data_crawler_result")
public class CrawlerResult extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "参数")
    @Excel(name="参数", width = 60)
    private String paramJson;
    @Schema(title= "结果")
    @Excel(name="结果", width = 60)
    private String resultJson;
}
