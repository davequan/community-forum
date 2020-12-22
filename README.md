# Forum Community Project

This project is a community platform based on SpringBoot, which implements mailbox registration,
Verification code login, post, comment, private message, thumb up, follow, statistics website visit times and other functions,uses
Mybatis,Redis to interacted with database and Kafka to build system notifications，Use Elasticsearch to build a full-text search function. At the same time realize the functions of generating pdf files and uploading cloud servers.

## Project Note

[1.Develop Community-forum HomePage](./note/chapter1.md) 

[2.develop community login module](./note/chapter2.md)

[3.Develop core community functions](./note/chapter3.md) 

[4.Redis，high-performance storage](./note/chapter4.md) 

[5.Kafka，construct asynchronous messaging system](./note/chapter5.md)

[6.Elasticsearch，distributed search engine](./note/chapter6.md) 

[7.Project deploy](Documents/community/note/chapter7.md)

## Project Summary

* Spring Boot
* **Spring**
* Spring MVC、Spring Mybatis、**Spring Security**
* Authority
  * Registration, login, logout, status, settings, authorization
  * Spring Email、**Interceptor**
* Core@Banned word@Transaction
  * Homepage, posts, comments, private messages, exceptions, logs
  * Advice、**AOP**、**Transaction**
* Performance@data structure
  * Like, follow, statistics, cache
  * **Redis**
* Notify@mode
  * system notification
  * Kafka
* Search @Index
  * research all
  * Elasticsearch



