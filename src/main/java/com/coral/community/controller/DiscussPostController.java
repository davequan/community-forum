package com.coral.community.controller;


import com.coral.community.entity.DiscussPost;
import com.coral.community.entity.User;
import com.coral.community.service.DiscussPostService;
import com.coral.community.service.UserService;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public  String addDiscussPost(String title, String content){
        User user  = hostHolder.getUser();
        if(user == null){
            return CommunityUtil.getJSONString(403,"you havent log in!");
        }
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);
        // if error occurs , deal with error unifired
        return CommunityUtil.getJSONString(0,"Post Successfully!");
    }
    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId , Model model){
        // post
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPost);
        // user
        User user = userService.findUserByID(discussPost.getUserId());
        model.addAttribute("user",user);
        return "/site/discuss-detail";

    }

}
