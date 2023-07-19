package com.shumu.data.crawler.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson2.JSONObject;
import com.shumu.common.base.response.BaseResponse;
import com.shumu.common.util.StringUtil;
import com.shumu.data.crawler.crawler.CommonClient;
import com.shumu.data.crawler.entity.CrawlerHeader;
import com.shumu.data.crawler.entity.CrawlerParam;
import com.shumu.data.crawler.entity.CrawlerProject;
import com.shumu.data.crawler.entity.CrawlerResult;
import com.shumu.data.crawler.service.ICrawlerHeaderService;
import com.shumu.data.crawler.service.ICrawlerParamService;
import com.shumu.data.crawler.service.ICrawlerProjectService;
import com.shumu.data.crawler.service.ICrawlerResultService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: Li
 * @date: 2023-05-05
 */
@Tag(name = "爬虫操作")
@RestController
@RequestMapping("/data/crawler/done")
public class CrawlerDoneController {
    @Autowired
    private ICrawlerProjectService crawlerProjectService;
    @Autowired
    private ICrawlerParamService crawlerParamService;
    @Autowired
    private ICrawlerHeaderService crawlerHeaderService;
    @Autowired
    private ICrawlerResultService crawlerResultService;

    private static final String ZERO_STRING = "0";
    private static final String ONE_STRING = "1";
    private static final int TWO_INT = 2;

    @Operation(summary = "爬取数据")
    @GetMapping("/crawler")
    public BaseResponse<?> crawler(@RequestParam(name = "id") String id, HttpServletRequest req) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        CrawlerProject crawlerProject = crawlerProjectService.getById(id);
        Map<String, String> headers = getHeaderMap(id, parameterMap);
        List<Map<String, Object>> params = getParams(id, parameterMap);

        int size = 10, index = 1;
        String pageStr = "page";
        String sizeStr = "size";
        boolean isPagination = false;
        if (crawlerProject.getIsPagination()) {
            isPagination = true;
            if (StringUtil.isNotEmpty(crawlerProject.getIndexParam())) {
                pageStr = crawlerProject.getIndexParam();
            }
            if (StringUtil.isNotEmpty(crawlerProject.getSizeParam())) {
                sizeStr = crawlerProject.getSizeParam();
            }
            if (null != parameterMap.get(sizeStr)) {
                size = Integer.parseInt(parameterMap.get(sizeStr)[0]);
            }
            if (null != parameterMap.get(pageStr)) {
                String str = parameterMap.get(sizeStr)[0];
                if (ONE_STRING.equals(str) || ZERO_STRING.equals(str)) {
                    size = Integer.parseInt(str);
                } else {
                    isPagination = false;
                }
            }
        }
        if (isPagination) {
            for (Map<String, Object> param : params) {
                List<CrawlerResult> list = getPageCrawlerResult(crawlerProject,headers,param,pageStr,sizeStr,index,size);
                crawlerResultService.saveBatch(list);
            }
        }else{
            for (Map<String, Object> param : params) {
                CrawlerResult result = getCrawlerResult(crawlerProject,headers,param);
                crawlerResultService.save(result);
            }
        }
        return BaseResponse.ok("hhh");
    }

    private List<CrawlerResult> getPageCrawlerResult(CrawlerProject crawlerProject, Map<String, String> headers,
            Map<String, Object> param, String pageString, String sizeString, int start, int size) {
        List<CrawlerResult> list = new ArrayList<CrawlerResult>();
        int p = 0, total = 1;
        while (p*size < total) {
            param.put(pageString, start + p);
            CrawlerResult result = getCrawlerResult(crawlerProject, headers, param);
            if (1 == total) {
                JSONObject obj = JSONObject.parse(result.getResultJson());
                String count = crawlerProject.getTotalField();
                String[] counts = count.split("\\.");
                if (counts.length == 1) {
                    total = obj.getIntValue(counts[0]);
                } else if (counts.length == 2) {
                    total = obj.getJSONObject(counts[0]).getIntValue(counts[1]);
                } else if (counts.length == 3) {
                    total = obj.getJSONObject(counts[0]).getJSONObject(counts[1]).getIntValue(counts[2]);
                }
            }
            list.add(result);
            p++;
        }
        return list;
    }

    private CrawlerResult getCrawlerResult(CrawlerProject crawlerProject, Map<String, String> headers,
            Map<String, Object> param) {
        CommonClient commonClient = new CommonClient(crawlerProject.getOriginUrl(), crawlerProject.getProjectPath(),
                crawlerProject.getProjectMethod(), headers, param);
        commonClient.build();
        try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String result = commonClient.getStringResult();
            if (TWO_INT == crawlerProject.getProjectType()) {
                String pa = crawlerProject.getRegexPattern();
                if (null != pa && !"".equals(pa)) {
                    Pattern pattern = Pattern.compile(pa);
                    Matcher matcher = pattern.matcher(result);
                    if (matcher.find()) {
                        result = matcher.group();
                    }
                }
            }
            CrawlerResult crawlerResult = new CrawlerResult();
            crawlerResult.setProjectId(crawlerProject.getId());
            crawlerResult.setParamJson(JSONObject.from(param).toString());
            crawlerResult.setResultJson(result);
            return crawlerResult;
        } catch (Exception e) {
            throw (e);
        }
    }

    private Map<String, String> getHeaderMap(String id, Map<String, String[]> parameterMap) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("project_id", id);
        List<CrawlerHeader> crawlerHeaders = crawlerHeaderService.listByMap(map);
        Map<String, String> headers = new HashMap<>(16);
        for (CrawlerHeader crawlerHeader : crawlerHeaders) {
            headers.put(crawlerHeader.getHeaderName(), crawlerHeader.getHeaderValue());
            if (parameterMap.containsKey(crawlerHeader.getHeaderName())) {
                headers.put(crawlerHeader.getHeaderName(), parameterMap.get(crawlerHeader.getHeaderName())[0]);
            }
        }
        return headers;
    }

    private List<Map<String, Object>> getParams(String id, Map<String, String[]> parameterMap) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("project_id", id);
        List<CrawlerParam> crawlerParams = crawlerParamService.listByMap(map);
        Map<String, String[]> paramArray = new HashMap<>(16);
        Map<String, Integer> types = new HashMap<>(16);
        int count = 1;
        for (CrawlerParam crawlerParam : crawlerParams) {
            String name = crawlerParam.getParamName();
            if (parameterMap.containsKey(name)) {
                String str = parameterMap.get(name)[0];
                types.put(name, crawlerParam.getParamType());
                if (null != str && !"".equals(str)) {
                    if (str.startsWith("array_")) {
                        String[] arr = str.substring(6).split(",");
                        if (arr.length > 0) {
                            paramArray.put(name, arr);
                            count *= arr.length;
                        }
                    } else {
                        paramArray.put(name, parameterMap.get(name));
                    }
                }
            }
        }
        List<Map<String, Object>> params = new ArrayList<>();
        int c = 0;
        while (c < count) {
            Map<String, Object> param = new HashMap<>(16);
            int r = c;
            for (String key : paramArray.keySet()) {
                String[] vals = paramArray.get(key);
                int l = vals.length;
                int m = r % l;
                String val = vals[m];
                if (1 == types.get(key)) {
                    param.put(key, Integer.parseInt(val));
                } else if (2 == types.get(key)) {
                    param.put(key, Double.parseDouble(val));
                } else {
                    param.put(key, val);
                }
                r = (r - m) / l;
            }
            c++;
            params.add(param);
        }
        return params;
    }

    @Operation(summary = "创建数据库表")
    @GetMapping("/test")
    public BaseResponse<?> test() {
        WebClient webClient = WebClient.create("https://sou.zhaopin.com");
        Mono<String> mono = webClient.get().uri("/?jl=530&kw=%E6%8A%96%E9%9F%B3&p=1").retrieve()
                .bodyToMono(String.class);
        BaseResponse<String> response = new BaseResponse<>();
        response.setSuccess(true);
        response.setResult(mono.block());
        return response;
    }
}
