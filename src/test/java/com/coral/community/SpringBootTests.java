package com.coral.community;

import com.coral.community.entity.DiscussPost;
import com.coral.community.service.DiscussPostService;
import com.coral.community.service.MessageService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SpringBootTests {
    @Autowired
    private DiscussPostService discussPostService;
    private  DiscussPost post;
    @BeforeClass
    public static void beforeClass(){
        System.out.println("before class");
    }
    @AfterClass
    public static void afterClass(){
        System.out.println("after class");
    }
    @Before
    public void before(){
        System.out.println("before");
        //initialize data
    }
    @After
    public void after(){
        System.out.println("after");
    // delete initilization data
    }
    @Test
    public  void test1(){
        System.out.println("test1");
    }
    @Test
    public void test2(){
        System.out.println("test2");
    }

}
