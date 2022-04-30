package com.shumu.common.office.excel.param;

import lombok.Data;

/**
 * 单元格样式参数
 * @author Li
 * @since 17:21 2021/8/6
 */
@Data
public class CellStyleParam {
    /**行高*/
    private Short height;
    /**列宽*/
    private Short width;
    /**单元格背景色*/
    private Short color;
    /**字体*/
    private String fontName;
    /**字号*/
    private Short fontHeight;
    /**字色*/
    private Short fontColor;
    /**斜体*/
    private Boolean italic;
    /**加粗*/
    private Boolean bold;
    /**下划线*/
    private Byte underline;
    /**换行*/
    private Boolean warp;
    /**数字格式*/
    private Short dataFormat;
    /**水平对齐*/
    private Short align;
    /**垂直对齐*/
    private Short vertical;
    /**边框样式*/
    private Short leftBorderStyle;
    private Short topBorderStyle;
    private Short rightBorderStyle;
    private Short bottomBorderStyle;
    /**边框颜色*/
    private Short leftBorderColor;
    private Short topBorderColor;
    private Short rightBorderColor;
    private Short bottomBorderColor;    
}
