package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
public class Utils01JdkClient {
    public static void main(String[] args) throws Exception {
        f1();
        f2();
        f3();
    }
    /**
     * 1.使用HttpURLConnection调用服务端的POST请求
     * 服务端入参注解: @RequestBody
     */
    public static void f1() throws Exception {
        // 1.请求URL
        String postUrl = "http://127.0.0.1:8081/comm/f1";
        // 2.请求参数JSON格式
        Map<String, String> map = new HashMap<>();
        map.put("userName", "HangZhou20220718");
        map.put("tradeName", "Vue进阶教程");
        String json = JSON.toJSONString(map);
        // 3.创建连接与设置连接参数
        URL urlObj = new URL(postUrl);
        HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Charset", "UTF-8");
        // POST请求且JSON数据,必须设置
        httpConn.setRequestProperty("Content-Type", "application/json");
        // 打开输出流,默认是false
        httpConn.setDoOutput(true);
        // 打开输入流,默认是true,可省略
        httpConn.setDoInput(true);
        // 4.从HttpURLConnection获取输出流和写数据
        OutputStream oStream = httpConn.getOutputStream();
        oStream.write(json.getBytes());
        oStream.flush();
        // 5.发起http调用(getInputStream触发http请求)
        if (httpConn.getResponseCode() != 200) {
            throw new Exception("调用服务端异常.");
        }
        // 6.从HttpURLConnection获取输入流和读数据
        BufferedReader br = new BufferedReader(
                new InputStreamReader(httpConn.getInputStream()));
        String resultData = br.readLine();
        System.out.println("从服务端返回结果: " + resultData);
        // 7.关闭HttpURLConnection连接
        httpConn.disconnect();
    }
    /**
     * 2.使用HttpURLConnection调用服务端的GET请求
     * 服务端入参注解: @RequestParam
     */
    public static void f2() throws Exception {
        // 1.请求URL与组装请求参数
        String getUrl = "http://127.0.0.1:8081/comm/f2";
        String obj = "Vue进阶教程";
        String para = "?obj=" + URLEncoder.encode(obj, "UTF-8");
        getUrl = getUrl + para;
        // 2.创建连接与设置连接参数
        URL urlObj = new URL(getUrl);
        HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("Charset", "UTF-8");
        // 3.发起http调用(getInputStream触发http请求)
        if (httpConn.getResponseCode() != 200) {
            throw new Exception("调用服务端异常.");
        }
        // 4.从HttpURLConnection获取输入流和读数据
        BufferedReader br = new BufferedReader(
                new InputStreamReader(httpConn.getInputStream()));
        String resultData = br.readLine();
        System.out.println("从服务端返回结果: " + resultData);
        // 5.关闭HttpURLConnection连接
        httpConn.disconnect();
    }
    /**
     * 3.使用HttpURLConnection调用服务端的GET请求
     * 服务端入参注解: @PathVariable
     */
    public static void f3() throws Exception {
        // 1.请求URL与组装请求参数
        String getUrl = "http://127.0.0.1:8081/comm/f3/";
        String obj = "Vue进阶教程";
        obj = URLEncoder.encode(obj, "UTF-8");
        getUrl = getUrl + obj;
        URL urlObj = new URL(getUrl);
        // 2.创建连接与设置连接参数
        HttpURLConnection httpConn = (HttpURLConnection) urlObj.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("charset", "UTF-8");
        // 3.发起http调用(getInputStream触发http请求)
        if (httpConn.getResponseCode() != 200) {
            throw new Exception("调用服务端异常.");
        }
        // 4.从HttpURLConnection获取输入流和读数据
        BufferedReader br = new BufferedReader(
                new InputStreamReader(httpConn.getInputStream()));
        String resultData = br.readLine();
        System.out.println("从服务端返回结果: " + resultData);
        // 5.关闭HttpURLConnection连接
        httpConn.disconnect();
    }
}


