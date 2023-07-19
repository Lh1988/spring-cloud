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
@TableName("data_crawler_data")
public class CrawlerData extends BaseEntity {
    @Schema(title= "项目ID")
    @Excel(name="项目ID", width = 60)
    private String projectId;
    @Schema(title= "表名")
    @Excel(name="表名", width = 60)
    private String tableName;
    @Schema(title= "字段名")
    @Excel(name="字段名", width = 60)
    private String fieldName;
    @Schema(title= "数据类型",description = "0:array,1:object,2:Table,3:List,4:Div,5:constant")
    @Excel(name="数据类型", width = 60)
    private Integer dataType;
    @Schema(title= "数据定位")
    @Excel(name="数据定位", width = 60)
    private String valuePosition;
    @Schema(title= "数组定位")
    @Excel(name="数组定位", width = 60)
    private String arrayPosition;
    @Schema(title= "数值类型",description = "0:String,1:Number")
    @Excel(name="数值类型", width = 60)
    private String valueType;
    @Schema(title= "数值序号")
    @Excel(name="数值序号", width = 60)
    private Integer valueIndex;
}
