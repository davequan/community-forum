package com.coral.community;

import com.coral.community.dao.DiscussPostMapper;
import com.coral.community.dao.UserMapper;
import com.coral.community.entity.DiscussPost;
import com.coral.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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


}
