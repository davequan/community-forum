package com.coral.community.controller.interceptor;

import com.coral.community.entity.LoginTicket;
import com.coral.community.entity.User;
import com.coral.community.service.UserService;
import com.coral.community.util.CookieUtil;
import com.coral.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*
 *    query the user when request starts
 *    handle the user data in certain request
 *    show user data in model and view
 *    clean up the user data when request ends
 * */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor{
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = CookieUtil.getValue(request, "ticket");
        if(ticket != null){
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            if(loginTicket != null && loginTicket.getStatus() ==0 && loginTicket.getExpired().after(new Date())){
                User user = userService.findUserByID(loginTicket.getUserId());
                // multithreading situation ->HostHolder
                hostHolder.setUser(user);
            }
        }
        return  true;
    }
    // before model engine
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
