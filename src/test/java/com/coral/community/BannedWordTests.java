package com.coral.community;

import com.coral.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class BannedWordTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public  void testSensitiveFilter(){
        String text = "its not allow drugabc";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
