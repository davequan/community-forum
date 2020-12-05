package com.coral.community.controller.advice;

import com.coral.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    private  static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
         logger.error("server exception:" + e.getMessage());
         for(StackTraceElement  element: e.getStackTrace()){
             logger.error(element.toString());
         }
        //decide whether it is ajax
         String xRequestedWith = request.getHeader("x-requested-with");
         if("XMLHttpRequest".equals(xRequestedWith)){
             //its a ajax
             response.setContentType("application/plain;charset=utf-8");
             PrintWriter writer = response.getWriter();
             writer.write(CommunityUtil.getJSONString(1,"server exception!"));
         }else{
             response.sendRedirect(request.getContextPath()+"/error");
         }
    }



}
