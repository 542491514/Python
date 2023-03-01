package org.example;



import okhttp3.*;
//import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class OkHttpClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private volatile static okhttp3.OkHttpClient client;

    private static final int MAX_IDLE_CONNECTION = 10;

    private static final long KEEP_ALIVE_DURATION = 10;

    private static final long CONNECT_TIMEOUT = 10;

    private static final long READ_TIMEOUT = 10;

    //private static final Logger log = (Logger) LoggerFactory.getLogger(OkHttpClient.class);
    /**
     * 单例模式(双重检查模式) 获取类实例
     *
     * @return client
     */
    private static okhttp3.OkHttpClient getInstance() {
        if (client == null) {
            synchronized (okhttp3.OkHttpClient.class) {
                if (client == null) {
                    client = new okhttp3.OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTION, KEEP_ALIVE_DURATION,
                                    TimeUnit.MINUTES))
                            .build();
                }
            }
        }
        return client;
    }

    public static String syncPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = OkHttpClient.getInstance().newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                //log.info("syncPost response = {}, responseBody= {}", response, result);
                System.out.println("syncGet response = {},responseBody= {}  "+result);
                return result;
            }
            String result = response.body().string();
            //log.info("syncPost response = {}, responseBody= {}", response, result);
            System.out.println("syncGet response = {},responseBody= {}  "+result);
            throw new IOException("三方接口返回http状态码为" + response.code());
        } catch (Exception e) {
            //log.error("syncPost() url:{} have a ecxeption {}", url, e);
            System.out.println("syncPost() url:{}  "+url);
            throw new RuntimeException("syncPost() have a ecxeption {}" + e.getMessage());
        }
    }

    public static String syncGet(String url, Map<String, Object> headParamsMap) throws IOException {
        Request request;
        final Request.Builder builder = new Request.Builder().url(url);
        try {
            if (!CollectionUtils.isEmpty(headParamsMap)) {
                final Iterator<Map.Entry<String, Object>> iterator = headParamsMap.entrySet()
                        .iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<String, Object> entry = iterator.next();
                    builder.addHeader(entry.getKey(), (String) entry.getValue());
                }
            }
            request = builder.build();
            Response response = OkHttpClient.getInstance().newCall(request).execute();
            String result = response.body().string();
            //log.info("syncGet response = {},responseBody= {}", response, result);
            System.out.println("syncGet response = {},responseBody= {}  "+result);
            if (!response.isSuccessful()) {
                throw new IOException("三方接口返回http状态码为" + response.code());
            }
            return result;
        } catch (Exception e) {
            //log.error("remote interface url:{} have a ecxeption {}", url, e);
            System.out.println("remote interface url:{} have a ecxeption {}  "+url);
            throw new RuntimeException("三方接口返回异常");
        }
    }

}
