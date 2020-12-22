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
* Visit Kafka
  * Consumer
    kafkaTemplate.send(topic, data);
  * Producer
    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {}

## 4. Send System notifications

* Fire Event
  * Notice after comment
  * Notice after like
  * Notice after follow 
* Handle Event
  * Encapsulated event object
  * Producer of development events
  * Consumers of development events

## 5. Show system notifications

* Notification list
  * Show three types of notifications: comment, like, and follow
* Notification details
  * Pagination to display notifications contained in a certain topic
* Unread message
  * Display the number of all unread messages in the header of the page