package com.shumu.common.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

/**
* @Description: 
* @Author: Li
* @Date: 2022-02-16
* @LastEditTime: 2022-02-16
* @LastEditors: Li
*/
public class ValidateCodeUtil {
    public static final String CODE_KEY = "CODE_KEY";

    public static String createCaptcha(int width, int height, String key){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(105, 35);
        return lineCaptcha.getImageBase64Data();
    }

}
