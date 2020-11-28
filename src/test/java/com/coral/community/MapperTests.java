package com.coral.community;

import com.coral.community.dao.DiscussPostMapper;
import com.coral.community.dao.LoginTicketMapper;
import com.coral.community.dao.UserMapper;
import com.coral.community.entity.DiscussPost;
import com.coral.community.entity.LoginTicket;
import com.coral.community.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(11);
        System.out.println(user);
        user = userMapper.selectByName("JIAHAO11");
        System.out.println(user);
        user = userMapper.selectByEmail("JIAHAO11@sina.com");
        System.out.println(user);
    }

    @Test
    public  void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@email.com");
        user.setHeaderUrl("www.google.com");
        user.setCreateTime(new Date());
        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }
    @Test
    public void deleteUser(){
        int rows =userMapper.updateStatus(150,1);
        System.out.println(rows);
        rows = userMapper.updateHeader(150,"www.google2.com");
        System.out.println(rows);
        rows = userMapper.updatePassword(150,"hello");
        System.out.println(rows);

    }
    @Test
    public void testSelectPosts(){
        List<DiscussPost> list =discussPostMapper.selectDiscussPosts(0,0,10);
        for(DiscussPost post: list){
            System.out.println(post);
        }
        int rows = discussPostMapper.selectDiscussPostRows(102);
        System.out.println(rows);
    }
    @Test
    public void testInsertLogin(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStatus(0);
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }
    @Test
    public void testSelectByTicket(){
        LoginTicket loginTicket =loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }
    @Test
    public  void testUpdateTicketStatus(){
        int a =loginTicketMapper.updateStatus("abc",1);
        Assert.assertEquals(1,a);
    }
    @Test
    public void testInserpost(){
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(155);
        discussPost.setTitle("test");
        discussPost.setContent("testpost");
        discussPost.setStatus(0);
        discussPost.setType(1);

        discussPost.setCreateTime(new Date());
        discussPost.setCommentCount(5);
        discussPost.setScore(2);
        int i = discussPostMapper.insertDiscussPost(discussPost);
        System.out.println(i);
    }
    @Test
    public void testSelectDiscussPostbyId(){
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(109);
        System.out.println(discussPost);
    }

}
