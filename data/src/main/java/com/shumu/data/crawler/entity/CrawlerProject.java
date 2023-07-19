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
* @date: 2023-04-19
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "爬虫工程")
@TableName("data_crawler_project")
public class CrawlerProject extends BaseEntity {
    @Schema(title= "项目名称")
    @Excel(name="项目名称", width = 60)
    private String projectName;
    @Schema(title= "项目标题")
    @Excel(name="项目标题", width = 60)
    private String projectTitle;
    @Schema(title= "项目类型",description = "0:Api,1:Html,2:Text")
    @Excel(name="项目类型", width = 60)
    private Integer projectType;
    @Schema(title= "基础地址")
    @Excel(name="基础地址", width = 60)
    private String originUrl;
    @Schema(title= "接口方法")
    @Excel(name="接口方法", width = 60)
    private String projectMethod;
    @Schema(title= "项目路径")
    @Excel(name="项目路径", width = 60)
    private String projectPath;
    @Schema(title= "是否分页数据")
    @Excel(name="是否分页数据", width = 60)
    private Boolean isPagination;
    @Schema(title= "总数字段")
    @Excel(name="总数字段", width = 60)
    private String totalField;
    @Schema(title= "页码参数")
    @Excel(name="页码参数", width = 60)
    private String indexParam;
    @Schema(title= "条数参数")
    @Excel(name="条数参数", width = 60)
    private String sizeParam;
    @Schema(title= "文本选取（正则表达式）")
    @Excel(name="文本选取", width = 60)
    private String regexPattern;
}
