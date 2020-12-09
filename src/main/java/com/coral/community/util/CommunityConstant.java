package com.coral.community.util;


public interface CommunityConstant {
    /*
    *  activate success
    * */
    int ACTIVATION_SUCCESS =0 ;

    /*
    * repeat
    * */
    int ACTIVATION_REPEAT=1;
    /*
    * fail
    * */
    int ACTIVATION_FAILURE =2;

    /*
    * default saving time
    * */
    int DEFAULT_EXPIRED_SECONDS = 3600*12;
    /*
    * remember saving time
    * */
    int REMEMBER_EXPIRED_SECONDS=3600*24*100;

    /*
    *  entity type: post
    * */
    int ENTITY_TYPE_POST = 1;

    /*
    * entity type :comment
    * */
    int ENTITY_TYPE_COMMENT= 2;
    /*
     * entity type :user
     * */
    int ENTITY_TYPE_USER = 3;

}
