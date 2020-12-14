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

## 6. Followee List,Follower List

* Service layer
  * Query the people followed by a user, support paging.
  * Query the fans of a user, support paging.
* View Layer
  * Handle the request of "query followers" and "query fans".
  * Write templates for "Query Followers" and "Query Fans".

## 7. Optimize login module

* Use Redis to store verification codes
  * he verification code requires frequent access and refresh, which requires high performance
  * The verification code does not need to be stored forever, usually it will become invalid after a short period of time.
  * In distributed deployment, there is a problem of Session sharing.
* Use Redis to store login ticket
  * Each time a request is processed, the user's login credentials must be queried, and the frequency of access is very high.
* Use Redis to cache user information
  * Each time a request is processed, user information must be queried based on credentials, and the frequency of access is very high.







