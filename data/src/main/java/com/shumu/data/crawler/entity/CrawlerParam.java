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
@Schema(title= "爬虫参数")
@TableName("data_crawler_param")
public class CrawlerParam extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "参数名称")
    @Excel(name="参数名称", width = 60)
    private String paramName;
    @Schema(title= "参数标题")
    @Excel(name="参数标题", width = 60)
    private String paramTitle;
    @Schema(title= "参数类型",description = "0:Text,1:Number")
    @Excel(name="参数类型", width = 60)
    private Integer paramType;
    @Schema(title= "参数默认值")
    @Excel(name="参数默认值", width = 60)
    private String paramDefault;
    @Schema(title= "参数序号")
    @Excel(name="参数序号", width = 60)
    private Integer paramIndex;
}
