package com.shumu.common.office.excel.imports.sax;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: sheet的sax读取
 * @Author: Li
 * @Date: 2022-01-12
 * @LastEditTime: 2022-01-12
 * @LastEditors: Li
 */
public class SheetHandler<T> extends DefaultHandler {
    /** 共享字符串表 */
    private SharedStringsTable sst;
    /** 上一次的内容 */
    private String lastContents;
    /** 当前行 */
    private int curRow = 0;
    /** 当前列 */
    private int curCol = 0;
    /** sax行读取 */
    private SaxRowRead<T> read;
    /** 单元格内数据类型 */
    private CellValueType type;
    /** 存储行记录的容器 */
    private List<SaxReadCellEntity> rowList = new ArrayList<>();

    public SheetHandler(SharedStringsTable sst, SaxRowRead<T> rowRead) {
        this.sst = sst;
        this.read = rowRead;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        lastContents = "";
        // 单元格
        if (SaxConstant.ELEMENT_CELL.equals(name)) {
            // 单元格数据类型
            String cellType = attributes.getValue(SaxConstant.ELEMENT_TYPE);
            if (SaxConstant.TYPE_STRING.equals(cellType)) {
                type = CellValueType.STRING;
                return;
            }
            // 单元格样式
            String cellStyle = attributes.getValue(SaxConstant.ELEMENT_STYLE);
            if (SaxConstant.STYLE_DATE.equals(cellStyle)) {
                type = CellValueType.DATE;
            } else if (SaxConstant.STYLE_NUMBER.equals(cellStyle)) {
                type = CellValueType.NUMBER;
            }
        } else if (SaxConstant.ELEMENT_T.equals(name)) {
            type = CellValueType.TElEMENT;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (CellValueType.STRING.equals(type)) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = sst.getItemAt(idx).getString();
            } catch (Exception ignored) {

            }
        }
        // t元素也包含字符串
        if (CellValueType.TElEMENT.equals(type)) {
            String value = lastContents.trim();
            rowList.add(curCol, new SaxReadCellEntity(CellValueType.STRING, value));
            curCol++;
            type = CellValueType.NONE;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if (SaxConstant.ELEMENT_VALUE.equals(name)) {
            String value = lastContents.trim();
            value = "".equals(value) ? " " : value;
            if (CellValueType.DATE.equals(type)) {
                LocalDateTime date = DateUtil.getLocalDateTime(Double.parseDouble(value));
                rowList.add(curCol, new SaxReadCellEntity(CellValueType.DATE, date));
            } else if (CellValueType.NUMBER.equals(type)) {
                BigDecimal bd = new BigDecimal(value);
                rowList.add(curCol, new SaxReadCellEntity(CellValueType.NUMBER, bd));
            } else if (CellValueType.STRING.equals(type)) {
                rowList.add(curCol, new SaxReadCellEntity(CellValueType.STRING, value));
            }
            curCol++;
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
        } else if (name.equals(SaxConstant.ELEMENT_ROW)) {
            read.parse(curRow, rowList);
            rowList.clear();
            curRow++;
            curCol = 0;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

}
