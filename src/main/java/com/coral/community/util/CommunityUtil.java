package com.coral.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {
    //generate random String

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
        // replace - with black space

    }
    //md5 encrypt the password
    //1.only encrypt cant decrypt
    //hello -> abc123def456
    //hello + 3e4a8  --->abc123def456abc
     public static  String md5(String key){
        if(StringUtils.isBlank(key)){      // blank,null,space all consider as blank
             return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
     }

     public  static  String getJSONString(int code, String msg, Map<String,Object> map){
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("code",code);
         jsonObject.put("msg",msg);
         if(map != null){
             for(String key :map.keySet()){
                 jsonObject.put(key,map.get(key));
             }
         }
         return jsonObject.toString();
     }
    public static  String getJSONString(int code,String msg){
        return getJSONString(code,msg,null);
    }
    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }


}
