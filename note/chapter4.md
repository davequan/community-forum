# Redis，high-performance storage solution

## 1. Redis Introduction
* Redis
It is a NoSQL database based on key-value pairs, and its values support multiple data structures:
  strings、hashes、lists、sets、sorted sets etc.
* Redis:all data is stored in memory, so its read and write performance is amazing.
  At the same time, Redis can also save the data in memory to the hard disk in the form of snapshots or logs to ensure data security.
* Typical application scenarios of Redis include: cache, leaderboard, counter, social network, message queue, etc.

## 2. Spring integrates Redis

* 引入依赖
  * spring-boot-starter-data-redis
* 配置Redis
  * 配置数据库参数
  * 编写配置类，构造RedisTemplate
* 访问Redis
  * redisTemplate.opsForValue()
  * redisTemplate.opsForHash()
  * redisTemplate.opsForList()
  * redisTemplate.opsForSet()
  * redisTemplate.opsForZSet()

## 3. Like

* Like
  * Support to like posts and comments.
  * Like the first time and cancel the like the second time.
* Number of like in Homepage
  * Count the number of likes for posts.
* Number of like in discuss Detail
  * Count the number of likes.
  * Show like status.

## 4. Like User received

* Reconstruct like function
  * Use the user as the key to record the number of likes
  * increment(key)，decrement(key)
* Develop personal profile page
  * Use the user as the key to query the number of likes

## 5. follow,unfollow

* Demand
  * Develop follow,unfollow function
  * Count the number of followers and fans of users.
* Follow
  * If A follows B, then A is B's Follower (fan), and B is A's Followee (target).
  * The follow target can be users, posts, topics, etc. These targets are abstracted into entities when they are implemented.

## 6. 关注列表、粉丝列表

* 业务层
  * 查询某个用户关注的人，支持分页。
  * 查询某个用户的粉丝，支持分页。
* 表现层
  * 处理“查询关注的人”、“查询粉丝”请求。
  * 编写“查询关注的人”、“查询粉丝”模板。

## 7. 优化登录模块

* 使用Redis存储验证码
  * 验证码需要频繁的访问与刷新，对性能要求较高。
  * 验证码不需永久保存，通常在很短的时间后就会失效。
  * 分布式部署时，存在Session共享的问题。
* 使用Redis存储登录凭证
  * 处理每次请求时，都要查询用户的登录凭证，访问的频率非常高。
* 使用Redis缓存用户信息
  * 处理每次请求时，都要根据凭证查询用户信息，访问的频率非常高。







