package com.coral.community.util;

public class RedisKeyUtil {
    private  static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE ="like:entity";
    //certain entity's like
    // like:entity:entityType:entityId->set(userId)
    public static String getEntityLikeKey(int entityType,int entityId){
        return  PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }
}
