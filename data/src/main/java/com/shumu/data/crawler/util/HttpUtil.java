package com.shumu.data.crawler.util;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
/**
* @description: 
* @author: Li
* @date: 2023-07-12
*/
public class HttpUtil {
    public static String getApiData(String base, String url, HttpMethod method,MediaType media, Map<String, String> headers, Map<String, Object> params) {
        WebClient webClient = WebClient.builder().baseUrl(base).defaultHeaders((httpHeaders) -> {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }).build();
        Mono<String> mono = null;
        if(null != params){
            if(method.equals(HttpMethod.GET)){
                mono = webClient.get().uri(url, params).retrieve().bodyToMono(String.class);
            } else if(method.equals(HttpMethod.DELETE)){
                mono = webClient.delete().uri(url, params).retrieve().bodyToMono(String.class);
            } else {                
                if (media.includes(MediaType.APPLICATION_JSON)) {
                    LinkedMultiValueMap<String,Object> map = new LinkedMultiValueMap<String,Object>();
                    mono = webClient.method(method).uri(url).contentType(media).body(BodyInserters.fromMultipartData(map)).retrieve().bodyToMono(String.class);
                } else if(media.includes(MediaType.MULTIPART_FORM_DATA)){
                    mono = webClient.method(method).uri(url, params).contentType(media).retrieve().bodyToMono(String.class);
                }
            }
        } else {
            mono = webClient.method(method).uri(url).contentType(media).retrieve().bodyToMono(String.class);
        }
        if(mono != null){
            return mono.block();
        }else{
            return null;
        }
    }
}
