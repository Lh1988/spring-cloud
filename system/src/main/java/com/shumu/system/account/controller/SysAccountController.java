package com.shumu.system.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.security.service.IPasswordEncoderService;
import com.shumu.system.account.entity.SysAccount;
import com.shumu.system.account.service.ISysAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
* @description: 账户操作
* @author: Li
* @date: 2023-01-04
*/
@RestController
@RequestMapping("/sys/account")
@Tag(name ="账户操作")
public class SysAccountController  extends BaseController<SysAccount,ISysAccountService> {
    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private IPasswordEncoderService passwordEncoderService;

    @Operation(summary = "解除锁定")
    @PostMapping("/unlock")
    public BaseResponse<?> unlockById(@RequestBody String id) {
        if(null==id || "".equals(id)){
            return BaseResponse.error("ID为空");
        }
        try {
            SysAccount sysAccount = new SysAccount();
            sysAccount.setId(id);
            sysAccount.setLoginErrorCount(0);
            sysAccount.setStatus(1);
            sysAccountService.updateById(sysAccount);
        } catch (Exception e) {
            return BaseResponse.error("解锁失败");
        }
        return BaseResponse.ok("解除锁定");
    }


    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public BaseResponse<?> resetPassword(@RequestBody String param) {
        String id = JSONObject.parseObject(param).getString("id");
        if(null==id || "".equals(id)){
            return BaseResponse.error("ID为空");
        }
        try {
            SysAccount sysAccount = new SysAccount();
            sysAccount.setId(id);
            //之后yml里定义通用重置
            String password = "#12Ab%";
            sysAccount.setAccountCredential(passwordEncoderService.delegatingPasswordEncoder().encode(password));
            sysAccount.setStatus(1);
            sysAccountService.updateById(sysAccount);
        } catch (Exception e) {
            return BaseResponse.error("解锁失败");
        }
        return BaseResponse.ok("解除锁定");
    }
    
}
