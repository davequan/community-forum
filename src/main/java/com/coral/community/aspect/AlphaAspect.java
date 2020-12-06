package com.coral.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {
    @Pointcut("execution(* com.coral.community.service.*.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void before(){
        System.out.println("before");
    }
    @After("pointCut()")
    public void afrer(){
        System.out.println("after");
    }
    @AfterReturning("pointCut()")
    public void afrerReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint)throws  Throwable{
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }


}
