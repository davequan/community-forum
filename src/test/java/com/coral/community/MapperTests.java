package com.coral.community;

import com.coral.community.dao.*;
import com.coral.community.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
    @Autowired
    private CommentMapper  commentMapper;
    @Autowired
    private MessageMapper messageMapper;

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
        List<DiscussPost> list =discussPostMapper.selectDiscussPosts(0,3,10);
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
    @Test
    public void testSelectComments(){
        List<Comment> comments = commentMapper.selectCommentsByEntity(1, 275, 0, 10);
        System.out.println(comments);
    }
    @Test
    public void testSelectCommentCounts(){
        int i = commentMapper.selectCountByEntity(1, 228);
        System.out.println(i);
    }
    @Test
    public void insertComment(){
        Comment comment = new Comment();
        comment.setUserId(127);
        comment.setContent("test1");
        comment.setCreateTime(new Date());
        comment.setEntityId(275);
        comment.setEntityType(2);
        comment.setStatus(0);
        comment.setTargetId(128);
        int i = commentMapper.insertComment(comment);
        System.out.println(1);
    }
    @Test
    public void testSelectLetters(){
        List<Message> li = messageMapper.selectConversations(111, 0, 10);
        for (Message message: li){
            System.out.println(message);
        }
        int countConversion = messageMapper.selectConversationCount(111);
        System.out.println(countConversion);

    }
    @Test
    public void testSelectMessagesCount(){
        int count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);
    }
    @Test
    public void testSelectMessage(){
        List<Message> messages = messageMapper.selectLetters("111_112", 0, 10);
        for(Message message:messages){
            System.out.println(message);
        }

    }

    @Test
    public void testUnread(){
        int count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }

    @Test
    public  void testInsetMessage(){
        Message message = new Message();
        message.setContent("111");
        message.setConversationId("111_111");
        message.setCreateTime(new Date());
        message.setFromId(000);
        message.setToId(000);
        message.setStatus(0);
        int i = messageMapper.insertMessage(message);
    }

    @Test
    public void testUpdateStatusMessage(){
        List<Integer> ids = new LinkedList<>();
        ids.add(39);

        int i = messageMapper.updateStatus(ids, 0);
    }
}
