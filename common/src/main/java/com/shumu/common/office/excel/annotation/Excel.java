package com.shumu.common.office.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Li
 * @since 16:52 2021/3/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /*--设置表格列名称----------*/
    String name() default "";

    /*--设置表格列宽度----------*/
    short width() default 0;

    /*--设置表格列时间字符串格式--*/
    String format() default "";

    /*--设置表格列排列顺序-----*/
    int order() default 0;

    /*--表格列内容后缀----------*/
    String suffix() default "";

    /*--表格列内容前缀----------*/
    String prefix() default "";

    /*--表格列内容自动换行-------*/
    boolean isWrap() default true;

    /*--表格列内容对应替换数-----*/
    String[] replace() default {};
}
