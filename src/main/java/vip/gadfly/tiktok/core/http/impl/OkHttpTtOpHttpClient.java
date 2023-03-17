package vip.gadfly.tiktok.core.http.impl;

import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import vip.gadfly.tiktok.core.util.json.JsonSerializer;
import vip.gadfly.tiktok.core.util.json.TiktokOpenJsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * https://square.github.io/okhttp/
 *
 * @author yangyidian
 * @date 2020/08/03
 **/
@Slf4j
public class OkHttpTtOpHttpClient extends AbstractTtOpHttpClient {

    public static final MediaType JSON = MediaType.parse("application/json");

    public OkHttpClient client;

    public OkHttpTtOpHttpClient() {
        super(TiktokOpenJsonBuilder.instance());
        this.client = new OkHttpClient();
    }

    public OkHttpTtOpHttpClient(JsonSerializer jsonSerializer) {
        super(jsonSerializer);
        this.client = new OkHttpClient();
    }

    public OkHttpTtOpHttpClient(OkHttpClient client) {
        super(TiktokOpenJsonBuilder.instance());
        this.client = client;
    }

    public OkHttpTtOpHttpClient(OkHttpClient client, JsonSerializer jsonSerializer) {
        super(jsonSerializer);
        this.client = client;
    }

    @Override
    public <T> T doGet(String url, Class<T> clazz) {
        return doGetWithHeaders(url, null, clazz);
    }

    @Override
    <T> T doGetWithHeaders(String url, Multimap<String, String> headers, Class<T> t) {
        Map<String, String> headersMap;
        if (headers != null) {
            headersMap = multimapHeaders2MapHeaders(headers);
        } else {
            headersMap = Collections.emptyMap();
        }
        Headers.Builder headersBuilder = Headers.of().newBuilder();
        for (String headerName : headersMap.keySet()) {
            String headerValue = headersMap.get(headerName);
            headersBuilder.add(headerName, headerValue);
        }
        Request request = new Request.Builder()
                .headers(headersBuilder.build())
                .url(url)
                .build();
        return doRequest(request, t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T doPostWithHeaders(String url, Multimap<String, String> headers, Object requestParam, Class<T> clazz) {
        Map<String, String> headersMap = multimapHeaders2MapHeaders(headers);
        Headers.Builder headersBuilder = Headers.of().newBuilder();
        for (String headerName : headersMap.keySet()) {
            String headerValue = headersMap.get(headerName);
            headersBuilder.add(headerName, headerValue);
        }
        String contentType = headersBuilder.get("Content-Type");
        if (contentType == null) {
            contentType = JSON.toString();
        }
        RequestBody body = null;
        while (body == null) {
            Map<String, Object> paramsMap = (Map<String, Object>) handlerRequestParam(requestParam);
            if (contentType.contains("multipart/form-data")) {
                MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
                requestBodyBuilder.setType(MultipartBody.FORM);
                for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                    if (entry.getValue() instanceof File) {
                        File file = (File) entry.getValue();
                        requestBodyBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(file, null));
                    } else if (entry.getValue() != null) {
                        requestBodyBuilder.addFormDataPart(entry.getKey(), (String) entry.getValue());
                    }
                }
                body = requestBodyBuilder.build();
                break;
            }
            if (contentType.contains("application/x-www-form-urlencoded")) {
                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                    if (entry.getValue() != null) {
                        builder.addEncoded(entry.getKey(), entry.getValue().toString());
                    }
                }
                body = builder.build();
                break;
            }
            String requestJson = getJsonSerializer().toJson(requestParam);
            body = RequestBody.Companion.create(requestJson.getBytes(StandardCharsets.UTF_8), JSON);
        }

        Request request = new Request.Builder()
                .headers(headersBuilder.build())
                .url(url)
                .post(body)
                .build();

        return doRequest(request, clazz);
    }

    @Override
    public <T> T doPost(String url, Object requestParam, Class<T> clazz) {
        String requestJson = getJsonSerializer().toJson(requestParam);
        log.trace("json:{}, {}", requestParam, requestJson);
        RequestBody body = RequestBody.Companion.create(requestJson.getBytes(StandardCharsets.UTF_8), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        log.trace("request json:{} content-type:{}", requestJson, request.headers().get("Content-Type"));
        return doRequest(request, clazz);
    }

    private <T> T doRequest(Request request, Class<T> clazz) {
        try (Response result = client.newCall(request).execute()) {
            log.trace("{}, {}", request, result);
            if (clazz == byte[].class) {
                return (T) result.body().bytes();
            } else {
                String resultString = result.body().string();
                return getJsonSerializer().parseResponse(resultString, clazz);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
