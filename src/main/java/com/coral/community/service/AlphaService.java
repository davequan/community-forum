package com.coral.community.service;

import com.coral.community.dao.AlphaDao;
import com.coral.community.dao.DiscussPostMapper;
import com.coral.community.dao.UserMapper;
import com.coral.community.entity.DiscussPost;
import com.coral.community.entity.User;
import com.coral.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.logging.Logger;

@Service

public class AlphaService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

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
    //REQUIRED:support a current transaction
    //REQUIRED_NEW
    //NESTED
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object save1(){
        //add user
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5("123")+user.getSalt());
        user.setEmail("alpha@gmail.com");
        user.setHeaderUrl("http:/image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setCreateTime(new Date());
        discussPost.setTitle("hello");
        discussPost.setContent("testTransaction");
        discussPost.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(discussPost);
        Integer.valueOf("adc");
        return "ok";
    }
    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {

                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID());
                user.setPassword(CommunityUtil.md5("123")+user.getSalt());
                user.setEmail("alpha@gmail.com");
                user.setHeaderUrl("http:/image.nowcoder.com/head/999t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                DiscussPost discussPost = new DiscussPost();
                discussPost.setUserId(user.getId());
                discussPost.setCreateTime(new Date());
                discussPost.setTitle("hello");
                discussPost.setContent("testTransaction");
                discussPost.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(discussPost);
                Integer.valueOf("adc");
                return "ok";
            }
        });
    }
/*
*    add comment
* */
}
