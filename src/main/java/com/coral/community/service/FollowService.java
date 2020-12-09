package com.coral.community.service;

import com.coral.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private RedisTemplate redisTemplate;
    public void follow(int userid, int entityType,int entityId){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userid,entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
                redisOperations.multi();
                redisOperations.opsForZSet().add(followeeKey,entityId,System.currentTimeMillis());
                redisOperations.opsForZSet().add(followerKey,userid,System.currentTimeMillis());
                return redisOperations.exec();
            }
        });
    }
    public void unfollow(int userid, int entityType,int entityId){
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userid,entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
                redisOperations.multi();
                redisOperations.opsForZSet().remove(followeeKey,entityId);
                redisOperations.opsForZSet().remove(followerKey,userid);
                return redisOperations.exec();
            }
        });
    }
    //查询关注的实体的数量
    public  long findFolloweeCount(int userId, int entityType){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }
    //查询某个实体的粉丝的数量
    public long findFollowerCount(int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType,entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }
    //查询当前用户是否已关注该实体
    public boolean hasFollowed(int userId , int entityType,int entityId){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId,entityType);
        return redisTemplate.opsForZSet().score(followeeKey, entityId) != null;
    }

}
