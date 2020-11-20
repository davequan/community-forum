package com.coral.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {
    //generate random String

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-"," ");
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

}
