package com.coral.community.dao;

import com.coral.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset,int limit);
    int selectCountByEntity(int entityType,int entityId);

    int insertComment(Comment comment);
    Comment selectCommentById(int id);
}
