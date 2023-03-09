
package com.shumu.common.base.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shumu.common.base.entity.BaseEntity;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.export.util.ExportUtil;
import com.shumu.common.office.excel.export.view.ExportXlsView;
import com.shumu.common.office.excel.export.view.ExportXlsxView;
import com.shumu.common.office.excel.imports.param.ImportParam;
import com.shumu.common.office.excel.imports.util.ImportUtil;
import com.shumu.common.office.excel.param.ColumnParam;
import com.shumu.common.office.excel.util.ParamUtil;
import com.shumu.common.query.util.QueryGenerator;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: BaseController
 * @Author: Li
 * @Date: 2021-12-29
 * @LastEditTime: 2021-12-29
 * @LastEditors: Li
 */
@Slf4j
public class BaseController<T extends BaseEntity, S extends IService<T>> {

    protected String name = "";
    protected String description = "";

    @Autowired
    private S service;

    protected Class<T> getEntityClass() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) trueType;
    }

    /**
     * 获取对应实体类的page类型列表数据，前端分页表
     * 
     * @param object   实体类对象
     * @param pageNo   页码
     * @param pageSize 页面行数
     * @param req      前端请求对象
     * @return BaseResponse<IPage<T>>
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Operation(summary = "获取分页列表数据")
    @GetMapping(value = "/pages")
    @PreAuthorize("hasAuthority()")
    public BaseResponse<IPage<T>> queryPageList(T object,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, req.getParameterMap());
        Page<T> page = new Page<>(pageNo, pageSize);
        IPage<T> pageList = service.page(page, queryWrapper);
        BaseResponse<IPage<T>> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setMessage("数据查询成功!");
        result.setResult(pageList);
        return result;
    }
 /**
     * 根据查询条件获取全部查询结果列表
     * 
     * @param entity 查询用实体类对象
     * @param req    查询参数
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Operation(summary = "根据查询条件获取全部查询结果列表")
    @GetMapping(value = "/list")
    public BaseResponse<List<T>> queryEntityList(T entity, HttpServletRequest req)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BaseResponse<List<T>> result = new BaseResponse<>();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(entity, req.getParameterMap());
        List<T> list = service.list(queryWrapper);
        result.setSuccess(true);
        result.setMessage("查询成功!");
        result.setResult(list);
        return result;
    }
    /**
     * 增
     * 
     * @param object 实体类对象
     * @return BaseResponse<?>
     */
    @Operation(summary = "插入数据")
    @PostMapping(value = "/add")
    public BaseResponse<?> add(@RequestBody T object, HttpServletRequest req) {
        try {
            service.save(object);
            return BaseResponse.ok("添加成功!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.error("添加失败!");
        }
    }

    /**
     * 删（通过id删除）
     * 
     * @param id id
     * @return BaseResponse<?>
     */
    @Operation(summary = "删除数据")
    @DeleteMapping(value = "/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        try {
            service.removeById(id);
            return BaseResponse.ok("删除成功!");
        } catch (Exception e) {
            return BaseResponse.error("删除失败!");
        }
    }

    /**
     * 批量删除
     * 
     * @param ids ids
     * @return BaseResponse<?>
     */
    @Operation(summary = "批量删除数据")
    @DeleteMapping(value = "/deleteBatch")
    public BaseResponse<?> deleteBatch(@RequestParam(value = "ids") String ids) {
        if (ids == null || "".equals(ids.trim())) {
            return BaseResponse.error("参数不识别!");
        } else {
            service.removeByIds(Arrays.asList(ids.split(",")));
            return BaseResponse.ok("删除成功!");
        }
    }

    /**
     * 改
     * 
     * @param object 实体类对象
     * @return
     */
    @Operation(summary = "更新数据")
    @PutMapping(value = "/edit")
    public BaseResponse<?> edit(@RequestBody T object) {
        object.setUpdateTime(LocalDateTime.now());
        try {
            service.updateById(object);
            return BaseResponse.ok("修改成功!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.error("修改失败!");
        }
    }

    /**
     * 查（通过id查询）
     * 
     * @param id id
     * @return BaseResponse<T>
     */
    @Operation(summary = "查询单个数据")
    @GetMapping(value = "/queryById")
    public BaseResponse<T> queryById(@RequestParam(name = "id") String id) {
        T object = service.getById(id);
        BaseResponse<T> result = new BaseResponse<>();
        if (object == null) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("未找到对应实体!");
        } else {
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("查询成功!");
            result.setResult(object);
        }
        return result;
    }

    /**
     * 通过ids批量查询
     * 
     * @param id id
     * @return BaseResponse<T>
     */
    @Operation(summary = "通过ids查询")
    @GetMapping(value = "/queryByIds")
    public BaseResponse<List<T>> queryByIds(@RequestParam(name = "ids") String ids) {
        BaseResponse<List<T>> result = new BaseResponse<>();
        List<T> objectList = new ArrayList<>();
        if (ids != null && !"".equals(ids)) {
            String[] ida = ids.split(",");
            for (String id : ida) {
                T object = service.getById(id);
                if (object != null) {
                    objectList.add(object);
                }
            }
        }
        if (objectList.size() == 0) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("未找到对应实体!");
        } else {
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("查询成功!");
            result.setResult(objectList);
        }
        return result;
    }

    @Operation(summary = "导出xls")
    @GetMapping("/export/xls")
    public ModelAndView exportXls(T object, HttpServletRequest request)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /* 1.组装查询条件 */
        Map<String, String[]> parameterMap = request.getParameterMap();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, parameterMap);
        /* 2.获取数据库数据：实体类型 */
        List<T> list = service.list(queryWrapper);
        /* 3.配置导出Excel的参数 */
        /* 3.1.创建Excel的ModelAndView：xls格式 */
        ModelAndView mv = new ModelAndView(new ExportXlsView<T>());
        /* 3.2.导出文件名 */
        mv.addObject(ExcelConstant.EXCEL_FILE_NAME, "导出数据");
        /* 3.3.配置导出文件参数 */
        ExportParam exportParam = ExportUtil.getDefaultEntityExportParam(object);
        /* 5.向mv添加参数 */
        mv.addObject(ExcelConstant.EXPORT_EXCEL_PARAM, exportParam);
        mv.addObject(ExcelConstant.EXPORT_EXCEL_DATA, list);
        return mv;
    }

    @Operation(summary = "导出xlsx")
    @GetMapping("/export/xlsx")
    public ModelAndView exportXlsx(T object, HttpServletRequest request)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /* 1.组装查询条件 */
        Map<String, String[]> parameterMap = request.getParameterMap();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, parameterMap);
        /* 2.获取数据库数据：实体类型 */
        List<T> list = service.list(queryWrapper);
        /* 3.配置导出Excel的参数 */
        /* 3.1.创建Excel的ModelAndView：xlsx格式 */
        ModelAndView mv = new ModelAndView(new ExportXlsxView<T>());
        /* 3.2.导出文件名 */
        mv.addObject(ExcelConstant.EXCEL_FILE_NAME, "导出数据");
        /* 3.3.配置导出文件参数 */
        ExportParam exportParam = ExportUtil.getDefaultEntityExportParam(object);
        /* 5.向mv添加参数 */
        mv.addObject(ExcelConstant.EXPORT_EXCEL_PARAM, exportParam);
        mv.addObject(ExcelConstant.EXPORT_EXCEL_DATA, list);
        return mv;
    }

    @Operation(summary = "导入")
    @PostMapping(value = "/import")
    public BaseResponse<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        Class<T> clazz = getEntityClass();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            ImportParam param = new ImportParam();
            param.setTitleRows(2);
            param.setHeadRows(1);
            List<ColumnParam> columnParams = ParamUtil.getColumnParamList(clazz);
            param.setColumnParams(columnParams);
            try {
                List<T> list = ImportUtil.importExcel(file.getInputStream(), param, clazz);
                // 批量插入数据
                long start = System.currentTimeMillis();
                service.saveBatch(list);
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                return BaseResponse.ok("文件导入成功!数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
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

}
