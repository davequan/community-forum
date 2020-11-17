package com.coral.community.service;

import com.coral.community.dao.UserMapper;
import com.coral.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public User findUserByID(int id){
        return userMapper.selectById(id);
    }
}
