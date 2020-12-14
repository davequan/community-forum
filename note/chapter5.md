# Kafka，Build an asynchronous message system

## 1. Blocking queue

* BlockingQueue
  * Solve the problem of thread communication.
  * Blocking method：put、take。

* Producer consumer model
  * Producer: The thread that produces the data.
  * Consumer: the thread that uses the data.

* Implementation Class
  * ArrayBlockingQueue
  * LinkedBlockingQueue
  * PriorityBlockingQueue、SynchronousQueue、DelayQueue etc.

## 2. Kafka Introduction

* Kafka
  * Kafka is a distributed streaming media platform.
  * Application: Message system, log collection, user behavior tracking, streaming processing.
* Kafka Features
  * High throughput, message persistence, high reliability, and high scalability.
* Kafka terms
  * Broker、Zookeeper
  * Topic、Partition、Offset
  * Leader Replica 、Follower Replica

## 3. Spring integrated with Kafka

* dependencies
  * spring-kafka
* Configure Kafka
  * Configure server、consumer
* 访问Kafka
  * 生产者
    kafkaTemplate.send(topic, data);
  * 消费者
    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {}

## 4. 发送系统通知

* 触发事件
  * 评论后，发布通知
  * 点赞后，发布通知
  * 关注后，发布通知
* 处理事件
  * 封装事件对象
  * 开发事件的生产者
  * 开发事件的消费者

## 5. 显示系统通知

* 通知列表
  * 显示评论、点赞、关注三种类型的通知
* 通知详情
  * 分页显示某一类主题所包含的通知
* 未读消息
  * 在页面头部显示所有的未读消息数量