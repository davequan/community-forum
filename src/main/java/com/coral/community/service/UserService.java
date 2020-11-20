package com.coral.community.service;

import com.coral.community.dao.UserMapper;
import com.coral.community.entity.User;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;  // generate static html to receive content
    @Autowired
    private UserMapper userMapper;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;


    public User findUserByID(int id){
        return userMapper.selectById(id);
    }

    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        // deal with null
        if(user ==null){
            throw new IllegalArgumentException("parameter cant be null");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","username cant be null");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","password cant be null");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","email cant be null");
            return map;
        }
        // verify username
        User u = userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("usernameMsg","account exist");
            return  map;
        }
        // verify the mail
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("emailMsg","email is registered");
            return map;
        }
        //register user
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0); // normal user
        user.setStatus(0);//unactivated
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        //activate email
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain+contextPath+"/activation"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"ActivationAccount",content);
        return map;
    }




}
