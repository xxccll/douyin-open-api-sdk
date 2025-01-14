package vip.gadfly.tiktok.core.http.impl;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vip.gadfly.tiktok.core.util.json.FastJsonSerializer;
import vip.gadfly.tiktok.core.util.json.JsonSerializer;
import vip.gadfly.tiktok.core.util.json.TiktokOpenJsonBuilder;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author Clevo
 * @date 2020/7/12
 */
@Slf4j
public class RestTemplateTtOpHttpClient extends AbstractTtOpHttpClient {

    private final RestTemplate restTemplate;

    public RestTemplateTtOpHttpClient() {
        super(TiktokOpenJsonBuilder.instance());
        this.restTemplate = new RestTemplate();
    }

    @SuppressWarnings("deprecation")
    public RestTemplateTtOpHttpClient(JsonSerializer jsonSerializer) {
        super(jsonSerializer);
        this.restTemplate = new RestTemplate();
        if (jsonSerializer instanceof FastJsonSerializer) {
            this.restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        }
    }

    public RestTemplateTtOpHttpClient(RestTemplate restTemplate) {
        super(TiktokOpenJsonBuilder.instance());
        this.restTemplate = restTemplate;
    }

    public RestTemplateTtOpHttpClient(RestTemplate restTemplate, JsonSerializer jsonSerializer) {
        super(jsonSerializer);
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T doGet(String url, Class<T> t) {
        return doGetWithHeaders(url, null, t);
    }

    @Override
    <T> T doGetWithHeaders(String url, Multimap<String, String> headers, Class<T> t) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                Collection<String> headerValues = headers.get(headerName);
                for (String headerValue : headerValues) {
                    httpHeaders.add(headerName, headerValue);
                }
            }
        }
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, Collections.emptyMap());
        return this.getJsonSerializer().parseResponse(result.getBody(), t);
    }

    @Override
    public <T> T doPost(String url, Object request, Class<T> t) {
//        String result = restTemplate.postForObject(url, request, String.class);
//        return this.getJsonSerializer().parseResponse(result, t);
        return this.doPostWithHeaders(url, LinkedListMultimap.create(), request, t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T doPostWithHeaders(String url, Multimap<String, String> headers, Object request, Class<T> t) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String headerName : headers.keySet()) {
            Collection<String> headerValues = headers.get(headerName);
            for (String headerValue : headerValues) {
                httpHeaders.add(headerName, headerValue);
            }
        }
        Object httpEntity;
        if (httpHeaders.getContentType() == null) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpEntity = new HttpEntity<>(getJsonSerializer().toJson(request), httpHeaders);
        } else {
            MultiValueMap<String, Object> param = (MultiValueMap<String, Object>) handlerRequestParam(request);
            httpEntity = new HttpEntity<>(param, httpHeaders);
        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        return this.getJsonSerializer().parseResponse(responseEntity.getBody(), t);
    }

    @Override
    protected Object handlerRequestParam(Object requestParams) {
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
        Field[] fields = requestParams.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(requestParams);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value instanceof File) {
                value = new FileSystemResource((File) value);
            }
            String aliasName = getJsonSerializer().getFieldAliasName(field);
            paramsMap.add(aliasName, value);
        }
        return paramsMap;
    }
}
