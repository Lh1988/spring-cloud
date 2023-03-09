package com.shumu.data.map.entity;

import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 地图地址
* @author: Li
* @date: 2023-03-09
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "企业地图地址")
public class CompanyMapAddress extends BaseEntity {
    @Schema(title= "地图类型(0高德1百度)")
    @Excel(name="地图类型", width = 60)
    private Integer mapType;
    @Schema(title= "企业名称")
    @Excel(name="企业名称", width = 60)
    private String companyName;
    @Schema(title= "企业代码")
    @Excel(name="企业代码", width = 60)
    private String companyCode;
    @Schema(title= "批次代码")
    @Excel(name="批次代码", width = 60)
    private String batchCode;
    @Schema(title= "是否准确地址")
    @Excel(name="是否准确地址", width = 60)
    private Boolean isExactAddress;
    @Schema(title= "国家")
    @Excel(name="国家", width = 60)
    private String countryName;
    @Schema(title= "省份名")
    @Excel(name="省份名", width = 60)
    private String provinceName;
    @Schema(title= "城市名")
    @Excel(name="城市名", width = 60)
    private String cityName;
    @Schema(title= "城市编码")
    @Excel(name="城市编码", width = 60)
    private String cityCode;
    @Schema(title= "区县名")
    @Excel(name="区县名", width = 60)
    private String districtName;
    @Schema(title= "街道名")
    @Excel(name="街道名", width = 60)
    private String streetName;
    @Schema(title= "门牌")
    @Excel(name="门牌", width = 60)
    private String number;
    @Schema(title= "区域编码")
    @Excel(name="区域编码", width = 60)
    private String addressCode;
    @Schema(title= "坐标点")
    @Excel(name="坐标点", width = 60)
    private String location;
    @Schema(title= "级别")
    @Excel(name="级别", width = 60)
    private String level;
    @Schema(title= "区划代码")
    @Excel(name="区划代码", width = 60)
    private String zoneCode;
}
