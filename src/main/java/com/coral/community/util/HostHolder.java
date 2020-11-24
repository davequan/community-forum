package com.coral.community.util;

import com.coral.community.entity.User;
import org.springframework.stereotype.Component;

/*
* hold user information ,used to replace session object
* */
@Component
public class HostHolder {
     private  ThreadLocal<User> users = new ThreadLocal<>();
     public void setUser(User user){
          users.set(user);
     }
     public User getUser(){
          return  users.get();
     }
     public void clear(){
          users.remove();
     }
}
