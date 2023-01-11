package com.shumu.common.office.excel.export.view;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.export.util.ExportUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;
/**
* @Description: 导出流
* @Author: Li
* @Date: 2022-01-11
* @LastEditTime: 2022-01-11
* @LastEditors: Li
*/
public class ExportXlsxStreamingView<T> extends AbstractXlsxStreamingView {
    @SuppressWarnings("all")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String excelName = model.get(ExcelConstant.EXCEL_FILE_NAME).toString() + ExcelConstant.EXCEL_FILE_XLSX;
        ExportUtil.setResponse(response, excelName, request.getHeader("User-Agent"));
        ExportParam excelParam = (ExportParam) model.get(ExcelConstant.EXPORT_EXCEL_PARAM);
        QueryWrapper<T> queryWrapper =(QueryWrapper<T>) model.get(ExcelConstant.EXPORT_QUERY_WRAPPER);
        IService<T> service = (IService<T>) model.get(ExcelConstant.EXPORT_SERVICE);
        ExportUtil.createExcel(workbook, excelParam, queryWrapper,service);

    }

}
