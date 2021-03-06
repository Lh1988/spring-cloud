
package com.shumu.common.base.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.office.excel.constant.ExcelConstant;
import com.shumu.common.office.excel.export.param.ExportParam;
import com.shumu.common.office.excel.export.util.ExportUtil;
import com.shumu.common.office.excel.export.view.ExportXlsView;
import com.shumu.common.office.excel.export.view.ExportXlsxView;
import com.shumu.common.office.excel.imports.param.ImportParam;
import com.shumu.common.office.excel.imports.util.ImportUtil;
import com.shumu.common.query.util.QueryGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: BaseController
 * @Author: Li
 * @Date: 2021-12-29
 * @LastEditTime: 2021-12-29
 * @LastEditors: Li
 */
@Slf4j
public class BaseController<T, S extends IService<T>> {
    
    protected String name="";
    protected String description="";

    @Autowired
    private S service;

    /**
     * ????????????????????????page????????????????????????????????????
     * 
     * @param object   ???????????????
     * @param pageNo   ??????
     * @param pageSize ????????????
     * @param req      ??????????????????
     * @return BaseResponse<IPage<T>>
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @ApiOperation(value = "????????????????????????")
    @GetMapping(value = "/pages")
    @PreAuthorize("hasAuthority()")
    public BaseResponse<IPage<T>> queryPageList(T object,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, req.getParameterMap());
        Page<T> page = new Page<>(pageNo, pageSize);
        IPage<T> pageList = service.page(page, queryWrapper);
        BaseResponse<IPage<T>> result = new BaseResponse<>();
        result.setSuccess(true);
        result.setMessage("??????????????????!");
        result.setResult(pageList);
        return result;
    }

    /**
     * ???
     * 
     * @param object ???????????????
     * @return BaseResponse<?>
     */
    @PostMapping(value = "/add")
    public BaseResponse<?> add(@RequestBody T object) {
        try {
            service.save(object);
            return BaseResponse.ok("????????????!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.error("????????????!");
        }
    }

    /**
     * ????????????id?????????
     * 
     * @param id id
     * @return BaseResponse<?>
     */
    @DeleteMapping(value = "/delete")
    public BaseResponse<?> delete(@RequestParam(name = "id") String id) {
        try {
            service.removeById(id);
            return BaseResponse.ok("????????????!");
        } catch (Exception e) {
            return BaseResponse.error("????????????!");
        }
    }

    /**
     * ????????????
     * 
     * @param ids ids
     * @return BaseResponse<?>
     */
    @DeleteMapping(value = "/deleteBatch")
    public BaseResponse<?> deleteBatch(@RequestParam(value = "ids") String ids) {
        if (ids == null || "".equals(ids.trim())) {
            return BaseResponse.error("???????????????!");
        } else {
            service.removeByIds(Arrays.asList(ids.split(",")));
            return BaseResponse.ok("????????????!");
        }
    }

    /**
     * ???
     * 
     * @param object ???????????????
     * @return
     */
    @PutMapping(value = "/edit")
    public BaseResponse<?> edit(@RequestBody T object) {
        try {
            service.updateById(object);
            return BaseResponse.ok("????????????!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.error("????????????!");
        }
    }

    /**
     * ????????????id?????????
     * 
     * @param id id
     * @return BaseResponse<T>
     */
    @GetMapping(value = "/queryById")
    public BaseResponse<T> queryById(@RequestParam(name = "id") String id) {
        T object = service.getById(id);
        BaseResponse<T> result = new BaseResponse<>();
        if (object == null) {
            result.setSuccess(false);
            result.setCode(500);
            result.setMessage("?????????????????????!");
        } else {
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("????????????!");
            result.setResult(object);
        }
        return result;
    }

    /**
     * ??????ids????????????
     * 
     * @param id id
     * @return BaseResponse<T>
     */
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
            result.setMessage("?????????????????????!");
        } else {
            result.setSuccess(true);
            result.setCode(200);
            result.setMessage("????????????!");
            result.setResult(objectList);
        }
        return result;
    }

    /**
     * ????????????????????????????????????????????????
     * @param entity ????????????????????????
     * @param req    ????????????
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @GetMapping(value = "/list")
    public BaseResponse<List<T>> queryEntityList(T entity, HttpServletRequest req)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BaseResponse<List<T>> result = new BaseResponse<>();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(entity, req.getParameterMap());
        List<T> list = service.list(queryWrapper);
        result.setSuccess(true);
        result.setMessage("????????????!");
        result.setResult(list);
        return result;
    }

    @RequestMapping("/export/xls")
    public ModelAndView exportXls(T object, HttpServletRequest request) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /*1.??????????????????*/
        Map<String,String[]> parameterMap=request.getParameterMap();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, parameterMap);
        /*2.????????????????????????????????????*/
        List<T> list = service.list(queryWrapper);
        /*3.????????????Excel?????????*/
        /*3.1.??????Excel???ModelAndView???xls??????*/
        ModelAndView mv = new ModelAndView(new ExportXlsView<T>());;
        /*3.2.???????????????*/
        mv.addObject("fileName", "????????????");
        /*3.3.????????????????????????*/
        ExportParam exportParam = ExportUtil.getDefaultEntityExportParam(object);
        /*5.???mv????????????*/
        mv.addObject(ExcelConstant.EXPORT_EXCEL_PARAM, exportParam);
        mv.addObject(ExcelConstant.EXPORT_EXCEL_DATA, list);
        return mv;
    }
    @RequestMapping("/export/xlsx")
    public ModelAndView exportXlsx(T object, HttpServletRequest request) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /*1.??????????????????*/
        Map<String,String[]> parameterMap=request.getParameterMap();
        QueryWrapper<T> queryWrapper = QueryGenerator.initQueryWrapper(object, parameterMap);
        /*2.????????????????????????????????????*/
        List<T> list = service.list(queryWrapper);
        /*3.????????????Excel?????????*/
        /*3.1.??????Excel???ModelAndView???xlsx??????*/
        ModelAndView mv = new ModelAndView(new ExportXlsxView<T>());;
        /*3.2.???????????????*/
        mv.addObject("fileName", "????????????");
        /*3.3.????????????????????????*/
        ExportParam exportParam = ExportUtil.getDefaultEntityExportParam(object);
        /*5.???mv????????????*/
        mv.addObject(ExcelConstant.EXPORT_EXCEL_PARAM, exportParam);
        mv.addObject(ExcelConstant.EXPORT_EXCEL_DATA, list);
        return mv;
    }
    @PostMapping(value = "/import")
    public BaseResponse<?>  importExcel(HttpServletRequest request, HttpServletResponse response, Class<T> clazz) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // ????????????????????????
            MultipartFile file = entity.getValue();
            ImportParam param = new ImportParam();
            param.setTitleRows(2);
            param.setHeadRows(1);
            try {
                List<T> list = ImportUtil.importExcel(file.getInputStream(), param, clazz);
                //??????????????????
                long start = System.currentTimeMillis();
                service.saveBatch(list);
                log.info("????????????" + (System.currentTimeMillis() - start) + "??????");
                return BaseResponse.ok("??????????????????!????????????:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return BaseResponse.error("??????????????????:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return BaseResponse.error("??????????????????!");
    }


}
