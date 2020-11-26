package com.coral.community.controller;

import com.coral.community.entity.User;
import com.coral.community.service.UserService;
import com.coral.community.util.CommunityConstant;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {
    private  static  final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private Producer kaptchaproducer;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public  String getRegisterPage() {
        return "/site/register";
    }




    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map = userService.register(user);
        if(map == null || map.isEmpty()){
             model.addAttribute("msg","register successfully,we have send verification email to your email,pleace activate ASAP");
             model.addAttribute("target","/index");
             return "site/operate-result";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "site/register";
        }
    }
    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public  String getLoginPage() {
        return "/site/login";
    }


    // http://localhost:8080/community/activation/101/code
    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model , @PathVariable("userId")int userId, @PathVariable("code") String code){
        int result = userService.activation(userId,code);
        if(result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","Activate successfully");
            model.addAttribute("target","/login");
        }else if (result == ACTIVATION_REPEAT){
            model.addAttribute("msg","Invalid Action,Account has been activated");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","Invalid Activation Code");
            model.addAttribute("target","/index");
        }
        return "site/operate-result";
    }

    @RequestMapping(path =  "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response , HttpSession session) throws IOException {
        // generate kaptcha
        String text = kaptchaproducer.createText();
        BufferedImage image = kaptchaproducer.createImage(text);
        //save kaptch into session
        session.setAttribute("kaptcha",text);
        //send image to browser
        response.setContentType("image/png");
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("kaptcha fail",e.getMessage());
        }
    }

    @RequestMapping(path ="/login",method = RequestMethod.POST)
    public  String login(String username,String password,String verification,boolean rememberme,
                         Model model,HttpSession session,HttpServletResponse response) {
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(verification) || !kaptcha.equalsIgnoreCase(verification)) {
            model.addAttribute("codeMsg", "verification is incorrect");
            return "/site/login";
        }
        //verify username, password
        //remember me / not remember me
        int expiredSeconds = rememberme? REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return"/site/login";
        }
    }
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/login";

    }

}
