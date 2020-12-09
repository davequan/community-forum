package com.coral.community.controller;

import com.coral.community.annotation.LoginRequired;
import com.coral.community.entity.User;
import com.coral.community.service.FollowService;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowerController {
    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String follow(int entityType,int entityId ){
        User user = hostHolder.getUser();
        followService.follow(user.getId(),entityType,entityId);
        return CommunityUtil.getJSONString(0,"followed!");
    }
    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String unfollow(int entityType,int entityId ){
        User user = hostHolder.getUser();
        followService.unfollow(user.getId(),entityType,entityId);
        return CommunityUtil.getJSONString(0,"unfollowed!");
    }

}
