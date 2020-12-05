package com.coral.community.dao;

import com.coral.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {
    //query current user's conversation list,only respond a newest message for each conversation
    List<Message> selectConversations(int userId,int offset,int limit);

    //query current user's conversation number
    int selectConversationCount(int userId);

    //query certain conversation that contains message list
    List<Message>selectLetters(String conversationId, int offset, int limit);

    //query certain conversation's letter number
    int selectLetterCount(String conversationId);

    //query unread letter
    int selectLetterUnreadCount(int userId,String conversationId);

    // insert message
    int insertMessage(Message message);

    int updateStatus(List<Integer> ids ,int status);


}
