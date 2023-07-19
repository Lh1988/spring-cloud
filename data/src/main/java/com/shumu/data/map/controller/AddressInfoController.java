package com.shumu.data.map.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.data.map.entity.AddressInfo;
import com.shumu.data.map.service.IAddressInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 
* @author: Li
* @date: 2023-03-31
*/
@Tag(name = "企业地图地址操作")
@RestController
@RequestMapping("/data/map/info")
public class AddressInfoController extends BaseController<AddressInfo, IAddressInfoService> {
    @Autowired
    private IAddressInfoService addressInfoService;

    @Operation(summary = "插入数据")
    @PostMapping(value = "/addBatch")
    public BaseResponse<?> batchAdd(@RequestBody Map<String,List<AddressInfo>> map) {
        List<AddressInfo> addresses = map.get("addresses");
        try {
            addressInfoService.saveBatch(addresses);
            return BaseResponse.ok("添加成功!");
        } catch (Exception e) {
            return BaseResponse.error("添加失败!");
        }
    }
}
