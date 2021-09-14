package vip.gadfly.tiktok.core;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

public class OkHttp3 {
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");
    private static final Logger log = LoggerFactory.getLogger(OkHttp3.class);
    public static OkHttpClient client = new OkHttpClient();

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        client.readTimeoutMillis();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * https post json 请求方法
     *
     * @param url     请求地址
     * @param content 请求内容json
     * @return string json
     */
    public static String okHttpsPostJson(String url, String content) {

        client = new OkHttpClient.Builder()
                .sslSocketFactory(getUnsafeOkHttpClient(), getTrustManager())
                .hostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                }).build();
        return okHttpPost(url, content, "application/json; charset=utf-8", null);
    }

    /**
     * http post json 请求方法
     *
     * @param url     请求地址
     * @param content 请求内容json
     * @return json
     */
    public static String okHttpPostJson(String url, String content) {
        return okHttpPost(url, content, "application/json; charset=utf-8", null);
    }

    /**
     * http post json 请求方法
     *
     * @param url            请求地址
     * @param content        json数据
     * @param connectTimeout 超时时间
     * @return json
     */
    public static String okHttpsPostJson(String url, String content,
                                         long connectTimeout) {
        client = new OkHttpClient.Builder().connectTimeout(connectTimeout,
                TimeUnit.SECONDS).build();
        return okHttpPost(url, content, "application/json; charset=utf-8", null);
    }

    /**
     * http post json 请求方法
     *
     * @param url     请求地址
     * @param content json数据
     * @return json
     */
    public static ResponseBody okHttpPostJsonResponseBody(String url,
                                                          String content) {
        return okHttpPostResponseBody(url, content,
                "application/json; charset=utf-8", null);
    }

    /**
     * http post 请求方法
     *
     * @param url 请求地址
     * @return string
     */
    public static String okHttpPost(String url, String content,
                                    String mediaType, Headers headers) {
        if (url == null || url.length() <= 0) {
            return "";
        }
        String result = null;
        try {
            Request request;
            // 是否有请求头信息
            if (headers == null) {
                request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse(mediaType),
                                content)).build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .post(RequestBody.create(MediaType.parse(mediaType),
                                content)).build();
            }
            log.debug("request  : " + request + " body: " + content);
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return "{\"errcode\": \"3333" + response.code()
                               + "\",\"errmsg\": \"http 请求失败   " + response
                               + "\"}";
            }
            result = response.body().string();
            log.debug("response :" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * http post 请求方法
     *
     * @param url 请求地址
     * @return string
     */
    public static ResponseBody okHttpPostResponseBody(String url,
                                                      String content, String mediaType, Headers headers) {
        if (url == null || url.length() <= 0) {
            return null;
        }
        ResponseBody result = null;
        try {
            Request request;
            // 是否有请求头信息
            if (headers == null) {
                request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse(mediaType),
                                content)).build();
            } else {
                request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .post(RequestBody.create(MediaType.parse(mediaType),
                                content)).build();
            }
            log.debug("request  : " + request + " body: " + content);
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            result = response.body();
            log.debug("response :" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * http get 请求方法
     *
     * @param url     请求地址
     * @param headers 请求头
     * @return string
     */
    public static String okHttpGet(String url, Headers headers) {
        if (url == null || url.length() <= 0) {
            return "";
        }
        String result = null;
        try {
            Request request;
            // 是否有请求头信息
            if (headers == null) {
                request = new Request.Builder().url(url).get().build();
            } else {
                request = new Request.Builder().url(url).headers(headers).get()
                        .build();
            }
            log.debug("request  : " + request);
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return "";
            }
            result = response.body().string();
            log.debug("response :" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String okHttpPost(String url, File file, String mediaType) {
        if (url == null || url.length() <= 0) {
            return "";
        }
        if (file == null || file.length() <= 0) {
            return "";
        }

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse(mediaType), file))
                .build();

        log.debug("request  : " + request + " body: " + file);
        String result = null;
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            result = response.body().toString();
            log.debug("response :" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static X509TrustManager getTrustManager() {
        return new X509TrustManager() {

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
            }


            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
            }


            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
    }

    public static SSLSocketFactory getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }


                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }


                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            return sslContext
                    .getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        okHttpGet(
                "http://apis.baidu.com/datatiny/cardinfo/cardinfo?cardnum=5187102112341234",
                new Headers.Builder().add("apikey",
                        "953eb9b1f583df6d427ec16e280f2550").build());
    }

}
