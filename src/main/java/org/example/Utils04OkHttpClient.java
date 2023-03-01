package org.example;

import okhttp3.*;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.alibaba.fastjson.JSON;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Utils04OkHttpClient {
    public static void main(String[] args) throws Exception {
        f1();
        f2();
        f3();

        OkHttpClient.syncPost("http://127.0.0.1:8081/httpByteTest","我是客户端");
    }
    /**
     *  1.使用okhttp调用服务端的POST请求
     *    服务端入参注解: @RequestBody
     * */
    public static void f1() throws Exception {
        // 1.请求URL
        String postUrl = "http://127.0.0.1:8081/comm/f1";
        // 2.请求参数
        Map<String, String> map = new HashMap<>();
        map.put("userName", "HangZhou20220718");
        map.put("tradeName", "Vue进阶教程");
        String json = JSON.toJSONString(map);
        // 3.创建连接与设置连接参数
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        RequestBody requestBody = RequestBody.Companion.create(json, mediaType);
        Request request = new Request.Builder().url(postUrl).post(requestBody).build();
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // 4.发起请求与接收返回值
        //Response response = okHttpClient.newCall(request).execute();

        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder().build();
        Response response = okHttpClient.newCall(request).execute();

        String resultData = response.body().string();
        System.out.println("从服务端返回结果: " + resultData);
    }
    /**
     *  2.使用okhttp调用服务端的GET请求
     *    服务端入参注解: @RequestParam
     * */
    public static void f2() throws Exception {
        // 1.请求URL与组装请求参数
        String getUrl = "http://127.0.0.1:8081/comm/f2";
        String obj = "Vue进阶教程";
        String para = "?obj=" + URLEncoder.encode(obj, "UTF-8");
        getUrl = getUrl + para;
        // 2.创建连接与设置连接参数
        Request request = new Request.Builder().url(getUrl).build();
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // 3.发起请求与接收返回值
        //Response response = okHttpClient.newCall(request).execute();

        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder().build();
        Response response = okHttpClient.newCall(request).execute();

        String resultData = response.body().string();
        System.out.println("从服务端返回结果: " + resultData);
    }
    /**
     *  3.使用okhttp调用服务端的GET请求
     *    服务端入参注解: @PathVariable
     * */
    public static void f3() throws Exception {
        // 1.请求URL与组装请求参数
        String getUrl = "http://127.0.0.1:8081/comm/f3/";
        String obj = "Vue进阶教程";
        obj = URLEncoder.encode(obj, "UTF-8");
        getUrl = getUrl + obj;
        // 2.创建连接与设置连接参数
        Request request = new Request.Builder().url(getUrl).build();
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // 3.发起请求与接收返回值
        //Response response = okHttpClient.newCall(request).execute();

        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder().build();
        Response response = okHttpClient.newCall(request).execute();

        String resultData = response.body().string();
        System.out.println("从服务端返回结果: " + resultData);
    }
}

