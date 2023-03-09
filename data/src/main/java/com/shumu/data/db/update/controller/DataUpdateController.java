package com.shumu.data.db.update.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.mybatis.spring.SqlSessionTemplate;

import com.shumu.common.base.controller.BaseController;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.export.util.ExportUtil;
import com.shumu.common.office.excel.export.view.ExportXlsView;
import com.shumu.common.office.excel.imports.param.ImportParam;
import com.shumu.common.office.excel.imports.util.ImportUtil;
import com.shumu.common.office.excel.param.ColumnParam;
import com.shumu.common.util.StringUtil;
import com.shumu.data.db.create.entity.DataCreateField;
import com.shumu.data.db.create.service.IDataFieldService;
import com.shumu.data.db.create.service.IDataTableService;
import com.shumu.data.db.update.entity.DataUpdateTable;
import com.shumu.data.db.update.generator.CommonIdGenerator;
import com.shumu.data.db.update.service.IDataUpdateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Li
 * @date: 2023-02-14
 */
@Tag(name = "数据更新操作")
@RestController
@RequestMapping("/data/db/update")
public class DataUpdateController extends BaseController<DataUpdateTable, IDataUpdateService> {
    @Autowired
    private IDataTableService dataTableService;
    @Autowired
    private IDataFieldService dataFieldService;
    @Autowired
    private IDataUpdateService dataUpdateService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate; 
    @Operation(summary = "关联")
    @PostMapping(value = "/referenced")
    public BaseResponse<?> updateByReferenced(@RequestBody Map<String,Object> map) {
        if(null==map.get("id")){
            return BaseResponse.error("参数id不能问空!");
        }
        DataUpdateTable update = dataUpdateService.getById(map.get("id").toString());
        if(null==update){
            return BaseResponse.error("无对应数据表更新信息!");
        }
        String table = update.getTableName();
        if(StringUtil.isNotEmpty(update.getDatabaseName())){
            table = update.getDatabaseName()+"."+update.getTableName();
        }
        String sql = "INSERT INTO "+ table;
        String fields = update.getUpdateFields();
        if(StringUtil.isNotEmpty(fields)){
           sql += " ("+ fields +") ";
        }
        String select = update.getSelectSql();
        if(StringUtil.isEmpty(select)){
           return BaseResponse.error("SQL语句不全!");
        }
        if(!select.startsWith("select") && !select.startsWith("SELECT")){
            select = "SELECT " + select;
        }
        String from = update.getFromSql();
        if(StringUtil.isEmpty(from)){
            return BaseResponse.error("SQL语句不全!");
         }
        if(!from.startsWith("from") && !from.startsWith("FROM")){
            from = "FROM " + from;
        }
        sql += " "+ select + " " + from;
        String where = update.getWhereSql();
        if(StringUtil.isNotEmpty(where)){
            if(!where.startsWith("where") && !where.startsWith("WHERE")){
                where = "WHERE " + where;
            }
            sql += " "+ where;
        }
        String other = update.getOtherSql();
        if(StringUtil.isNotEmpty(other)){
            sql += " "+ other;
        }
        sql += " "+ select + " " + from + " " + where  + " " + other;
        Map<String,Object> param = new HashMap<>(8);
        for (String key : map.keySet()) {
            String sKey = "${"+key+"}";
            if(sql.indexOf(sKey)>0){
                sql = sql.replace(sKey, map.get(key).toString());
            }
            String vKey = "#{"+key+"}";
            if(sql.indexOf(vKey)>0){
                param.put(key, map.get(key));
            }
        }
        try {
            sqlSessionTemplate.insert(sql, param);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.error("更新失败：" + e.getMessage());
        }
        return BaseResponse.ok("更新成功！");
    }
    @Operation(summary = "导入")
    @PostMapping(value = "/excel")
    public BaseResponse<?> updateByExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            if(null==file){
                return BaseResponse.error("文件上传失败!");
            }
            String fileName = file.getOriginalFilename();
            String updateId = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("."));
            if(StringUtil.isEmpty(updateId)){
                return BaseResponse.error("文件上传错误!");
            }
            DataUpdateTable update = dataUpdateService.getById(updateId);
            if(null==update){
                return BaseResponse.error("模板名称与数据表不对应!");
            }
            ImportParam importParam = getImportParam(update);
            if(null==importParam){
                return BaseResponse.error("数据表不存在!");
            }
            String databaseId = sqlSessionTemplate.getConfiguration().getDatabaseId();
            CommonIdGenerator idGenerator = new CommonIdGenerator(databaseId,update.getPrimaryKeyGenerator(),update.getPrimaryKeySequence());
            String databaseName = update.getDatabaseName();
            String tableName = update.getTableName();
            String primaryKey = update.getPrimaryKey();
            String[] flagFields = null;
            if (StringUtil.isNotEmpty(update.getUpdateFlagField())) {
                flagFields = update.getUpdateFlagField().split(",");
            }
            try {                
                List<Map> list = ImportUtil.importExcel(file.getInputStream(), importParam, Map.class);
                List<Map<String, Object>> insert = new ArrayList<>();
                List<Map<String, Object>> idUpdate = new ArrayList<>();
                List<Map<String, Object>> fieldUpdate = new ArrayList<>();
                for (Map<String, Object> map : list) {
                    if (null == map.get(primaryKey)) {
                        if (null != flagFields) {
                            boolean isNull = false;
                            for (String field : flagFields) {
                                if (null == map.get(field)) {
                                    isNull = true;
                                    break;
                                }
                            }
                            if (!isNull) {
                                fieldUpdate.add(map);
                                continue;
                            }
                        }
                        map.put(primaryKey, idGenerator.getId());
                        insert.add(map);
                    } else {
                        idUpdate.add(map);
                    }
                }
                if (StringUtil.isNotEmpty(databaseName)) {
                    tableName = databaseName + "." + tableName;
                }
                if (!fieldUpdate.isEmpty() && null != flagFields) {
                    for (Map<String, Object> map : fieldUpdate) {
                        List<Map<String, Object>> flagList = new ArrayList<>();
                        for (String field : flagFields) {
                            Map<String, Object> flagMap = new HashMap<>(8);
                            flagMap.put("key", field);
                            flagMap.put("value", map.get(field));
                            flagList.add(flagMap);
                        }
                        List<String> ids = dataUpdateService.getPrimaryKey(tableName, primaryKey, flagList);
                        if (null != ids && 1 == ids.size()) {
                            String id = ids.get(0);
                            map.put(primaryKey, id);
                            idUpdate.add(map);
                        } else {
                            map.put(primaryKey, idGenerator.getId());
                            insert.add(map);
                        }
                    }

                }
                List<String> fieldNames = new ArrayList<>();
                List<ColumnParam> columnParams = importParam.getColumnParams();
                for (ColumnParam columnParam : columnParams) {
                    fieldNames.add(columnParam.getKey());
                }
                long size = 0;
                if (!insert.isEmpty()) {
                    dataUpdateService.batchInsertData(tableName, fieldNames, insert);
                    size+=insert.size();
                }
                long err = 0;
                if (!idUpdate.isEmpty()) {
                    for (Map<String, Object> map : idUpdate) {
                      try {
                        dataUpdateService.updateDataById(tableName, primaryKey, fieldNames, map);
                        size++;
                      } catch (Exception e) {
                        err++;
                      }  
                    }
                }
                update.setLastUpdateTime(LocalDateTime.now());
                update.setLastUpdateResult("导入excel数据"+size+"条，更新失败"+err+"条");
                dataUpdateService.updateById(update);
                return BaseResponse.ok("文件导入成功!数据行数:" + +size+"条");
            } catch (Exception e) {
                return BaseResponse.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return BaseResponse.error("文件导入失败!");
    }

    private ImportParam getImportParam(DataUpdateTable update){
        String databaseName = update.getDatabaseName();
        String tableName = update.getTableName();
        List<DataCreateField> fields = dataFieldService.getDbFields(tableName, databaseName);
        if (null == fields || fields.isEmpty()) {
            return null;
        }
        ImportParam importParam = new ImportParam();
        List<ColumnParam> columnParams = new ArrayList<>();
        
        for (DataCreateField field : fields) {
            ColumnParam columnParam = new ColumnParam();
            columnParam.setKey(field.getFieldName());
            columnParam.setTitle(field.getFieldComment());
            columnParam.setOrder(field.getOrderNum());
            columnParam.setType(getJavaType(field.getFieldType()));
            columnParams.add(columnParam);
        }
        importParam.setColumnParams(columnParams);
        importParam.setTitleRows(2);
        importParam.setHeadRows(1);
        return importParam;
    }
    private String getJavaType(int type){
       String name = "String";
       switch (type) {
        case 0:
            name = "Double";
            break;
        case 1:
            name = "Integer";
            break;
        case 2:
            name = "BigDecimal";
            break;
        case 3:
            name = "String";
            break;
        case 4:
            name = "Integer";
            break;
        case 5:
            name = "LocalDateTime";
            break;
        case 6:
            name = "LocalDate";
            break;
        case 7:
            name = "Time";
            break;
        case 8:
            name = "String";
            break;
        default:
            break;
       }
       return name;
    }
    @Operation(summary = "导出导数模板")
    @GetMapping("/template/{id}")
    public ModelAndView exportXls(@PathVariable("id") String id, HttpServletRequest request)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DataUpdateTable update = dataUpdateService.getById(id);
        String databaseName = update.getDatabaseName();
        String tableName = update.getTableName();
        String tableTitle = update.getTableTitle();
        List<DataCreateField> fields = dataFieldService.getDbFields(tableName, databaseName);
        List<ColumnParam> columnParams = new ArrayList<>();
        for (DataCreateField field : fields) {
            ColumnParam columnParam = new ColumnParam();
            columnParam.setKey(field.getFieldName());
            int width = null!=field.getFieldLength()?field.getFieldLength():0;
            width = width>15?width:15;
            width = width>75?75:width;
            columnParam.setWidth(width);
            columnParam.setTitle(field.getFieldComment());
            columnParam.setOrder(field.getOrderNum());
            columnParams.add(columnParam);
        }
        ExportParam exportParam = ExportUtil.getDefaultMapExportParam(columnParams);
        exportParam.setFileName(tableTitle);
        ModelAndView mv = new ModelAndView(new ExportXlsView<Map<String, Object>>());
        mv.addObject(ExcelConstant.EXCEL_FILE_NAME, "导出数据");
        mv.addObject(ExcelConstant.EXPORT_EXCEL_PARAM, exportParam);
        mv.addObject(ExcelConstant.EXPORT_EXCEL_DATA, null);
        return mv;
    }

}
