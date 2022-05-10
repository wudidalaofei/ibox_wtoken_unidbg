package com.spider.unidbgserver.controller;

import com.alibaba.fastjson.JSON;


import com.worker.Ibox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/test")
public class SignController {

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @Autowired(required = false)

    HashMap<String,Ibox> iboxMap = new HashMap<String,Ibox>();
    @RequestMapping(value = "ibox", method = {RequestMethod.GET, RequestMethod.POST})
    public String ibox(@RequestParam("data") String data,@RequestParam("key") String key) {
        synchronized (this) {
            if(key == null){
                key = getRandomString(16);
            }
            Ibox temp = iboxMap.get(key);
            if(temp!=null){
                Map<String, String> result = temp.worker(data,false);
                result.put("key",key);
                result.put("ad","q2625112940");
                String jsonString = JSON.toJSONString(result);
                return jsonString;
            }else{
                Ibox ibox = new Ibox();
                Map<String, String> result = ibox.worker(data,true);
                String jsonString = JSON.toJSONString(result);
                iboxMap.put(key,ibox);
                result.put("key",key);
                result.put("ad","q2625112940");
                return jsonString;
            }

        }

    }

}