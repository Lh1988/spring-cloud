package com.shumu.data.map.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.office.excel.annotation.Excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @description: 地址详情
* @author: Li
* @date: 2023-03-31
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Schema(title= "地址详情")
@TableName("data_map_info")
public class AddressInfo extends BaseEntity {
    @Schema(title= "地址名称")
    @Excel(name="地址名称", width = 60)
    private String addressName;
    @Schema(title= "地址标识")
    @Excel(name="地址标识", width = 60)
    private String addressId;
    @Schema(title= "坐标点")
    @Excel(name="坐标点", width = 60)
    private String location;
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
    @Schema(title= "乡镇/街道")
    @Excel(name="乡镇/街道", width = 60)
    private String townshipName;
    @Schema(title= "区划代码")
    @Excel(name="区划代码", width = 60)
    private String zoneCode;
    @Schema(title= "结构化地址")
    @Excel(name="结构化地址", width = 60)
    private String formatAddress;
    @Schema(title= "社区名称")
    @Excel(name="社区名称", width = 60)
    private String neighborhoodName;
    @Schema(title= "社区类型")
    @Excel(name="社区类型", width = 60)
    private String neighborhoodType;
    @Schema(title= "建筑名称")
    @Excel(name="建筑名称", width = 60)
    private String buildingName;
    @Schema(title= "建筑类型")
    @Excel(name="建筑类型", width = 60)
    private String buildingType;
}
