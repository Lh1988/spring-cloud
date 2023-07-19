package com.shumu.data.crawler.crawler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.alibaba.fastjson2.JSONObject;

import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: Li
 * @date: 2023-05-16
 */
public class CommonClient {
  private String base;
  private String path;
  private String method;
  private Map<String, String> headers;
  private Map<String, Object> params;

  private RequestHeadersUriSpec<?> requestHeadersUriSpec;
  private RequestBodyUriSpec requestBodyUriSpec;
  private ResponseSpec response;

  private static final String LEFT_BRACE = "{", RIGHT_BRACE="}",QUESTION_MARK="?",AND_MARK="?";

  public CommonClient(String base,String path, String method, Map<String, String> headers, Map<String, Object> params) {
    this.base = base;
    this.path = path;
    this.method = method;
    this.headers = headers;
    this.params = params;
  }

  public void build(){
    WebClient webClient = WebClient.builder().baseUrl(base).build();
    setMethod(webClient);
    setPath();
    if(null != requestHeadersUriSpec){
      response = requestHeadersUriSpec.uri(path,params).headers((httpHeaders) -> setHeaders(httpHeaders)).retrieve();
    }
    if(null != requestBodyUriSpec) {
      response = requestBodyUriSpec.uri(path).headers((httpHeaders) -> setHeaders(httpHeaders)).body(BodyInserters.fromValue(JSONObject.parseObject("{\"searchKey\":\"宽腾医疗\",\"pageIndex\":1,\"pageSize\":20}"))).retrieve();
    }
  }
  private void setHeaders(HttpHeaders httpHeaders){
    if(null!=headers &&!headers.isEmpty()){
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        httpHeaders.set(entry.getKey(), entry.getValue());
      }
    }
  }
  private void setMethod(WebClient webClient){
    switch (method.toLowerCase()) {
      case "get":
        requestHeadersUriSpec = webClient.get();
        break;
      case "delete":
        requestHeadersUriSpec = webClient.delete();
        break;
      case "head":
        requestHeadersUriSpec = webClient.head();
        break;
      case "options":
        requestHeadersUriSpec = webClient.options();
        break;
      case "post":
        requestBodyUriSpec = webClient.post();
        break;
      case "put":
        requestBodyUriSpec = webClient.put();
        break;
      case "patch":
        requestBodyUriSpec = webClient.patch();
        break;
      default:
        break;
    }
  }
  private void setPath(){
    Map<String, Object> map = new HashMap<>(16);
    if (null == path) {
       path = "";
    }
    if (!"".equals(path) && path.contains(LEFT_BRACE) && path.contains(RIGHT_BRACE)) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        if (!path.contains("{" + entry.getKey() + "}")) {
          map.put(entry.getKey(), entry.getValue());
        } else {
          path.replace(entry.getKey(), entry.getValue().toString());
        }
      }
      if (!map.isEmpty()) {
        if (null != requestHeadersUriSpec) {
          if (!path.contains(QUESTION_MARK)) {
            path = path + QUESTION_MARK;
          }
          if (!path.endsWith(QUESTION_MARK) && !path.endsWith(AND_MARK)) {
            path = path + AND_MARK;
          }
          for (String key : map.keySet()) {
            if (null != map.get(key) && !"".equals(map.get(key))) {
              path += key + "=" + map.get(key).toString() + AND_MARK;
            }
          }
        }
        this.params = map;        
      }
    }
  }

  public JSONObject getJsonResult() {
    Mono<String> mono = response.bodyToMono(String.class);
    return JSONObject.parse(mono.block());
  }

  public String getStringResult() {
    Mono<String> mono = response.bodyToMono(String.class);
    return mono.block();
  }
}
