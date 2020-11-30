package com.coral.community.service;

import com.coral.community.dao.CommentMapper;
import com.coral.community.entity.Comment;
import com.coral.community.util.CommunityConstant;
import com.coral.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService implements CommunityConstant {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private DiscussPostService discussPostService;
    public List<Comment> findCommentsByEntity(int entityType, int entityId,int offset ,int limit){
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }
    public int findCommentCount(int entityType, int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public  int addComment(Comment comment){
        if(comment ==null){
            throw new IllegalArgumentException("parameter cant be null");
        }
        // add comment
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));  //过滤标签
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);
        // update number,  post's comment not reply
        if(comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
             discussPostService.updateCommentCount(comment.getEntityId(), count);
        }
        return rows;
    }
}
