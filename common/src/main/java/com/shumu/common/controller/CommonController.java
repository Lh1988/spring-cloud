package com.shumu.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.constant.CommonConstant;
import com.shumu.common.constant.TextConstant;
import com.shumu.common.util.FileUtil;
import com.shumu.common.util.StringUtil;
import com.shumu.common.util.UploadUtil;

import lombok.extern.slf4j.Slf4j;
/**
* @description: 
* @author: Li
* @date: 2022-12-11
*/
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value(value = "${shumu.path.upload}")
    private String uploadPath;
    /**
     * 本地：local minio：minio 阿里：alioss
     */
    @Value(value="${shumu.uploadType}")
    private String uploadType;

	@PostMapping(value = "/upload")
	public BaseResponse<?> upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		if(null==file || file.isEmpty()){
			return BaseResponse.error("文件上传失败");
		}
		String bizPath = request.getParameter("biz");
		if(null==bizPath ){
			bizPath = "";
		}
		if(bizPath.contains(TextConstant.D_DOT_SLASH) || bizPath.contains(TextConstant.D_DOT_BACKSLASH)){
			return BaseResponse.error("上传目录bizPath格式非法");
		}
		String savePath = "";
		if(CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)){
			savePath = uploadLocal(file,bizPath);
		}else{
			savePath = UploadUtil.upload(file, bizPath, uploadType);
		}

		if(StringUtil.isNotEmpty(savePath)){
			return BaseResponse.ok(savePath,"上传成功！");
		}else{
			return BaseResponse.error("上传失败！");
		}
	}
	/**
     * 本地文件上传
     * @param mf 文件
     * @param bizPath  自定义路径
     * @return
     */
    private String uploadLocal(MultipartFile mf,String bizPath){
        try {
            String ctxPath = uploadPath;
            String fileName = null;
            File file = new File(ctxPath + File.separator + bizPath + File.separator );
            if (!file.exists()) {
                file.mkdirs();
            }
            // 获取文件名
            String orgName = mf.getOriginalFilename();
            orgName = FileUtil.getFileName(orgName);
            if(orgName.indexOf(TextConstant.DOT)!=-1){
                fileName = orgName.substring(0, orgName.lastIndexOf(TextConstant.DOT)) + "_" + System.currentTimeMillis() + orgName.substring(orgName.lastIndexOf("."));
            }else{
                fileName = orgName+ "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = null;
            if(StringUtil.isNotEmpty(bizPath)){
                dbpath = bizPath + File.separator + fileName;
            }else{
                dbpath = fileName;
            }
            if (dbpath.contains(TextConstant.BACKSLASH)) {
                dbpath = dbpath.replace(TextConstant.BACKSLASH, TextConstant.SLASH);
            }
            return dbpath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
    /**
     * 预览图片&下载文件
     * 请求地址：http://localhost:8080/common/static/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/**")
    public void view(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if(StringUtil.isEmpty(imgPath) || imgPath==TextConstant.NULL_STRING){
            return;
        }
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace(TextConstant.D_DOT, "").replace(TextConstant.D_DOT_SLASH,"");
            if (imgPath.endsWith(TextConstant.COMMA)) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String filePath = uploadPath + File.separator + imgPath;
            File file = new File(filePath);
            if(!file.exists()){
                response.setStatus(404);
                throw new RuntimeException("文件["+imgPath+"]不存在..");
            }
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            log.error("预览文件失败" + e.getMessage());
            response.setStatus(404);
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }
	/**
     *  把指定URL后的字符串全部截断当成参数
     *  这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
     * @param request
     * @return
     */
    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
    
}
