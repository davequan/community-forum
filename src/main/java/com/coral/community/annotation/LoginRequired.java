package com.coral.community.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)   // used on the method
@Retention(RetentionPolicy.RUNTIME)  // used when the application starts
public @interface LoginRequired {
/*
* mark the method with annotation that can only be visited
* after login in  : /setting /upload /changepassword
* */

}
