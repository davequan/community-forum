package com.coral.community.service;

import com.coral.community.dao.LoginTicketMapper;
import com.coral.community.dao.UserMapper;
import com.coral.community.entity.LoginTicket;
import com.coral.community.entity.User;
import com.coral.community.util.CommunityConstant;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.MailClient;
import com.coral.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements CommunityConstant {
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
//    @Autowired
//    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    public User findUserByID(int id){

//        return userMapper.selectById(id);
        User user = getCache(id);
        if(user == null){
             user = initCache(id);
        }
        return user;
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
        String url = domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"ActivationAccount",content);
        return map;
    }
    // activate method
    public int activation(int UserId, String code){
         User user = userMapper.selectById(UserId);
         if(user.getStatus() ==1 ){
             return  ACTIVATION_REPEAT;
         }else if(user.getActivationCode().equals(code)){
             userMapper.updateStatus(UserId,1);
             clearCache(UserId);
             return  ACTIVATION_SUCCESS;
         }else{
             return ACTIVATION_FAILURE;
         }
    }

    public Map<String ,Object> login(String username,String password,int expiredSeconds){
        Map<String,Object> map = new HashMap<>();
        // null value
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","username cant be null");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","password cant be null");
            return map;
        }
        //verify the account
        User user = userMapper.selectByName(username);
        if(user ==null){
            map.put("usernameMsg","account doesnt exist");
            return map;
        }
        // verify the status
        if(user.getStatus() ==0){
            map.put("usernameMsg","account is not activated ");
            return map;
        }
        // verify the password
        password = CommunityUtil.md5(password + user.getSalt());
        if(!user.getPassword().equals(password)){
            map.put("passwordMsg","password is not correct");
            return map;
        }
        //login successfully,generate login ticket
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);  // validate
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));
//        loginTicketMapper.insertLoginTicket(loginTicket);
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket);


        map.put("ticket",loginTicket.getTicket());
        return map;
    }
    public void logout(String ticket){
//        loginTicketMapper.updateStatus(loginTicket,1);  // not validate
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket)redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey,loginTicket);
    }
    public LoginTicket findLoginTicket(String ticket){
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket)redisTemplate.opsForValue().get(redisKey);
    }
    public int updateHeader(int userId,String headerUrl){
//        return userMapper.updateHeader(userId,headerUrl);
        int rows = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return rows;
    }
    public Map<String ,Object> updatePassword(int userId,String oldpassword,String newpassword,String repassword){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(oldpassword)){
           map.put("olderror","oldPassword cant be null");
            return map;
        }
        if(StringUtils.isBlank(newpassword)){
            map.put("newerror","newPassword cant be null");
            return map;
        }
        if(StringUtils.isBlank(repassword)){
            map.put("reerror","confirm password cant be null");
            return map;
        }
        if(!repassword.equals(newpassword)){
            map.put("reerror","confirm password is not the same");
            return map;
        }
        User user = userMapper.selectById(userId);
        String enryptold = CommunityUtil.md5(oldpassword + user.getSalt());
        if(!user.getPassword().equals(enryptold)){
            map.put("olderror","password is wrong");
        }
        if(oldpassword.equals(newpassword)){
            map.put("newerror","new password cant be same as old password");
            return map;
        }
        String newPasswordEncrypt = CommunityUtil.md5(newpassword+user.getSalt());
        int num = userMapper.updatePassword(userId,newPasswordEncrypt);
        return map;
    }
    public  User findUserByName(String username){
        return userMapper.selectByName(username);
    }

    /*
    * 1。优先redis中取值
    * 2。取不到时初始化缓存数据
    * 3。数据变更时 清除缓存数据
    * */
    // 1.
    private  User getCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User)redisTemplate.opsForValue().get(redisKey);
    }
    //2
    private User initCache(int userId){
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey,user,3600, TimeUnit.SECONDS);
        return user;
    }
    //3
    private void clearCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }


}
