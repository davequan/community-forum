package com.coral.community.controller;

import com.coral.community.annotation.LoginRequired;
import com.coral.community.entity.User;
import com.coral.community.service.LikeService;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @RequestMapping(path ="/like" ,method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType,int entityId){
        User user = hostHolder.getUser();
        //like
        likeService.like(user.getId(),entityType,entityId);
        //number
        long count = likeService.findEntityLikeCount(entityType,entityId);
        //status
        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);
        Map<String,Object> map  = new HashMap<>();
        map.put("likeCount",count);
        map.put("likeStatus",likeStatus);
        return CommunityUtil.getJSONString(0,null,map);

    }
}
