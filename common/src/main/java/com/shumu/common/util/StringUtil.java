package com.shumu.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
* @description: string
* @author: Li
* @date: 2022-12-05
*/
public class StringUtil {

    public static boolean isEmpty(String str){
        if(null == str || "".equals(str)){
           return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str){
        if(null != str && !"".equals(str)){
           return true;
        }
        return false;
    }

    /**
     * 清除掉所有特殊字符
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String specialCharFilter(String str) throws PatternSyntaxException {
        String regEx = "[`_《》~!@#$%^&*()+=|{}':;',\\[\\]<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * java 判断字符串里是否包含中文字符
     * @param str
     * @return
     */
    public static boolean ifContainChinese(String str) {
        if(str.getBytes().length == str.length()){
            return false;
        }else{
            String regEx = "[\u4e00-\u9fa5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        }
    }
    
}
