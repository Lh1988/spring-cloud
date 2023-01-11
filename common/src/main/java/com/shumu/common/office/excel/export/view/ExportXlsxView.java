package com.shumu.common.office.excel.export.view;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.export.util.ExportUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 * @param <T>
 * @Description:
 * @Author: Li
 * @Date: 2022-01-09
 * @LastEditTime: 2022-01-09
 * @LastEditors: Li
 */
public class ExportXlsxView<T> extends AbstractXlsxView {
    @SuppressWarnings("all")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String excelName = model.get(ExcelConstant.EXCEL_FILE_NAME).toString() + ExcelConstant.EXCEL_FILE_XLSX;
        ExportUtil.setResponse(response, excelName, request.getHeader("User-Agent"));
        ExportParam excelParam = (ExportParam) model.get(ExcelConstant.EXPORT_EXCEL_PARAM);
        ExportUtil.createExcel(workbook, excelParam, (List<T>) model.get(ExcelConstant.EXPORT_EXCEL_DATA));
    }
}
