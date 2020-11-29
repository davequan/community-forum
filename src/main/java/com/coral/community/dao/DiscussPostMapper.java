package com.coral.community.dao;

import com.coral.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@Mapper
public interface DiscussPostMapper {
                                            // start line number  limit total lines
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);
     //@param used to rename for the parameter
    //if only one parameter,and used as dynamic paramter.have to use @param
    int selectDiscussPostRows(@Param("userId") int userId);
    int insertDiscussPost(DiscussPost discussPost);
    DiscussPost selectDiscussPostById(int id);
}
