package com.shumu.common.office.excel.constant;

/**
 * @author Li
 * @since 12:34 2021/3/12 0012
 */
public interface ExcelConstant {
    String EXCEL_FILE_NAME = "FILE_NAME";
    String EXCEL_FILE_XLS = ".xls";
    String EXCEL_FILE_XLSX = ".xlsx";

    String FIRE_FOX = "firefox";

    int EXCEL_TYPE_HSSF =0;
    int EXCEL_TYPE_XSSF =1;
    String EXPORT_EXCEL_PARAM = "EXCEL_PARAM";
    String EXPORT_EXCEL_DATA = "EXCEL_DATA";
  
    int HSSF_MAX_ROW = 65536;
    int XSSF_MAX_ROW = 1048576;

    String EXPORT_QUERY_WRAPPER ="EXPORT_QUERY_WRAPPER";
    String EXPORT_SERVICE ="EXPORT_SERVICE";
}
