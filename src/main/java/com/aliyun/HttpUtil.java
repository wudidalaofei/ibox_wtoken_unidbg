package com.aliyun;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

public class HttpUtil {
    public static String send(String request_url,String phone,String wtoken) throws IOException, JSONException {
        //请求url
        String serverURL = request_url;
        URL url = new URL(serverURL);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //header参数connection.setRequestProperty("键","值");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("wtoken", wtoken);
        connection.setRequestProperty("language", "zh-CN");
        connection.setRequestProperty("ib-app-version", "1.1.3");
        connection.setRequestProperty("ib-app-version", "1.1.3");
        connection.setRequestProperty("ib-platform-type", "android");
        connection.setRequestProperty("user-agent","iBoxApp");
        connection.setRequestProperty("ib-trans-id","e4bca849-45e0-4af0-bd80-b938820cd7c4_android");
        connection.setRequestProperty(" ib-device-id","00000000-7aa0-6e07-ffff-ffffef05ac4a_ad");



        connection.connect();
        OutputStreamWriter writer = new     OutputStreamWriter(connection.getOutputStream(),"UTF-8");
        //获取当前时间戳
        long time = new Date().getTime();
        JSONArray jsonArray = new JSONArray();
        JSONObject parm1 = new JSONObject();
        parm1.put("phoneNumber",phone);

        writer.write(parm1.toString());
        writer.flush();
        StringBuffer sbf = new StringBuffer();
        String strRead = null;
        // 返回结果-字节输入流转换成字符输入流，控制台输出字符
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        connection.disconnect();
        String results = sbf.toString();
//        System.out.println(results);
        return results;
    }


    public static String Login(String request_url,String phone,String code,String wtoken) throws IOException, JSONException {
        //请求url
        String serverURL = request_url;
        URL url = new URL(serverURL);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //header参数connection.setRequestProperty("键","值");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("wtoken", wtoken);
        connection.setRequestProperty("language", "zh-CN");
        connection.setRequestProperty("ib-app-version", "1.1.3");
        connection.setRequestProperty("ib-app-version", "1.1.3");
        connection.setRequestProperty("ib-platform-type", "android");
        connection.setRequestProperty("user-agent","iBoxApp");
        connection.setRequestProperty("ib-trans-id","e4bca849-45e0-4af0-bd80-b938820cd7c4_android");
        connection.setRequestProperty(" ib-device-id","00000000-7aa0-6e07-ffff-ffffef05ac4a_ad");



        connection.connect();
        OutputStreamWriter writer = new     OutputStreamWriter(connection.getOutputStream(),"UTF-8");
        //获取当前时间戳
        long time = new Date().getTime();
        JSONArray jsonArray = new JSONArray();
        JSONObject parm1 = new JSONObject();
        parm1.put("phoneNumber",phone);
        parm1.put("code",code);

        writer.write(parm1.toString());
        writer.flush();
        StringBuffer sbf = new StringBuffer();
        String strRead = null;
        // 返回结果-字节输入流转换成字符输入流，控制台输出字符
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        connection.disconnect();
        String results = sbf.toString();
//        System.out.println(results);
        return results;
    }
}
