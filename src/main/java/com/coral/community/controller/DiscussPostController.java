package com.coral.community.controller;


import com.coral.community.entity.*;
import com.coral.community.event.EventProducer;
import com.coral.community.service.CommentService;
import com.coral.community.service.DiscussPostService;
import com.coral.community.service.LikeService;
import com.coral.community.service.UserService;
import com.coral.community.util.CommunityConstant;
import com.coral.community.util.CommunityUtil;
import com.coral.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private EventProducer eventProducer;

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

        // after add discusspost,fire new event
        Event event = new Event();
        event.setTopic(TOPIC_PUBLISH)
                .setUserId(user.getId())
                .setEntityType(ENTITY_TYPE_POST)
                .setEntityId(discussPost.getId());
        eventProducer.fireEvent(event);
        // if error occurs , deal with error unifired
        return CommunityUtil.getJSONString(0,"Post Successfully!");
    }


    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId , Model model, Page page){
        // post
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPost);
        // author
        User user = userService.findUserByID(discussPost.getUserId());
        model.addAttribute("user",user);
        //like number
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeCount",likeCount);
        //like status
        int likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_POST,discussPostId);
        model.addAttribute("likeStatus",likeStatus);

        //comment
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(discussPost.getCommentCount());
        List<Comment> commentList = commentService.findCommentsByEntity(
                ENTITY_TYPE_POST, discussPost.getId(), page.getOffset(), page.getLimit());
        // comment: comment for the discusspost
        // reply: comment for the comment upon
        //comment VO list
        List<Map<String,Object>> commentViewObjectList = new ArrayList<>();
        if(commentList != null){
            for(Comment comment : commentList){
                // one comment VO
                Map<String,Object> commentViewOject = new HashMap<>();
                // discuss comment
                commentViewOject.put("comment",comment);
                //discuss comment user
                commentViewOject.put("user",userService.findUserByID(comment.getUserId()));

                //like number
                 likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,comment.getId());
                commentViewOject.put("likeCount",likeCount);
                //like status
                likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,comment.getId());
                commentViewOject.put("likeStatus",likeStatus);
                // discuss comment's r
                // reply
                List<Comment> replyList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(),
                        0,Integer.MAX_VALUE);
                //replaY VO list
                List<Map<String,Object>> replyVOlist = new ArrayList<>();
                if(replyList != null){
                    for(Comment reply : replyList){
                        Map<String,Object> replyVO = new HashMap<>();
                        // REPLY
                        replyVO.put("reply",reply);
                        //author
                        replyVO.put("user",userService.findUserByID(reply.getUserId()));


                        //target ID whether is o or 1
                        User target = reply.getTargetId() == 0 ? null : userService.findUserByID(reply.getTargetId());
                        replyVO.put("target",target);
                        //like number
                        likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT,reply.getId());
                        replyVO.put("likeCount",likeCount);
                        //like status
                        likeStatus =hostHolder.getUser()==null?0: likeService.findEntityLikeStatus(hostHolder.getUser().getId(),ENTITY_TYPE_COMMENT,reply.getId());
                        replyVO.put("likeStatus",likeStatus);

                        replyVOlist.add(replyVO);
                    }
                }
                commentViewOject.put("replys",replyVOlist);
                // reply count
                int replycount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentViewOject.put("replyCount",replycount);
                commentViewObjectList.add(commentViewOject);
            }
        }
        model.addAttribute("comments",commentViewObjectList);
        return "/site/discuss-detail";
    }




}
