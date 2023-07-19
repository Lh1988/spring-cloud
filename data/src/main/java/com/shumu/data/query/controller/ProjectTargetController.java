package com.shumu.data.query.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.query.util.QueryGenerator;
import com.shumu.data.query.entity.DataProjectTarget;
import com.shumu.data.query.service.IProjectTargetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
/**
* @description: 
* @author: Li
* @date: 2023-06-15
*/
@Tag(name = "查询指标")
@RestController
@RequestMapping("/data/query/pt")
public class ProjectTargetController {
    @Autowired
    private IProjectTargetService projectTargetService;
    @Operation(summary = "根据查询条件获取全部查询结果列表")
    @GetMapping(value = "/list")
    public BaseResponse<List<DataProjectTarget>> queryEntityList(DataProjectTarget entity, HttpServletRequest req)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        BaseResponse<List<DataProjectTarget>> result = new BaseResponse<>();
        QueryWrapper<DataProjectTarget> queryWrapper = QueryGenerator.initQueryWrapper(entity, req.getParameterMap());
        List<DataProjectTarget> list = projectTargetService.list(queryWrapper);
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
    public BaseResponse<?> add(@RequestBody DataProjectTarget object, HttpServletRequest req) {
        try {
            projectTargetService.save(object);
            return BaseResponse.ok("添加成功!");
        } catch (Exception e) {
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
            projectTargetService.removeById(id);
            return BaseResponse.ok("删除成功!");
        } catch (Exception e) {
            return BaseResponse.error("删除失败!");
        }
    }

}
