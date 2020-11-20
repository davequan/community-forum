package com.coral.community.service;

import com.coral.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@Service

public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    public AlphaService(){

        //System.out.println("instantiate AlphaService");
    }

    @PreDestroy
    public void destory(){
        //System.out.println("destory AlphaService");
    }
    @PostConstruct
    public void init(){
        //System.out.println("init AlphaService");
    }

    public String find(){
       return alphaDao.select();
    }
}
