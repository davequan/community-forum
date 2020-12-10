# Spring Boot,Develop core community functions

## 1. Filter Banned work

* Prefix Tree
  * Name：Trie Tree
  * Features：High search efficiency and high memory consumption
  * Application：String search, word frequency statistics, string sorting etc.
* Banned Work Filter
  * Define prefix tree
  * According to banned words, initialize the prefix tree
  * Write a method to filter sensitive words

## 2.Publishing Post 

* AJAX
  * Asynchronous JavaScript and XML
  * Asynchronous JavaScript and XML are not a new technology, but a new term.
  * Using AJAX, web pages can present incremental updates on the page without having to refresh the entire page.
  * Although X stands for XML, JSON is currently used more commonly than XML.
  * https://developer.mozilla.org/zh-CN/docs/Web/Guide/AJAX
* Demo
  * Use jQuery to send AJAX requests.
* Practise
  * Use AJAX request， function of publishing posts.

## 3. DiscussPost Detail

* DiscussPostMapper
* DiscussPostService
* DiscussPostController
* index.html
  * Add a link to the detail page in the post title
* discuss-detail.html
  * Process the access path of static resources
  * Reuse the header  and footer  of index.html
  * Display title, author, publication time, post body, etc.

## 4. Transaction management

**review**

* Whats Transaction
  * A transaction is a logical execution unit composed of a sequence of N-step database operations. This series of operations are either fully executed or completely abandoned.
* Transaction characteristics（ACID）
  * Atomicity：A transaction is the smallest executable in an application that cannot be subdivided.
  * Consistency：As a result of transaction execution, the data must change from a consistent state to another consistent state.
  * Isolation：The execution of each transaction does not interfere with each other, and the internal operations of any transaction are isolated from other transactions.
  * Durability：Once the transaction is committed, any changes made to the data must be recorded in permanent storage.

**Transaction isolation**

* Common concurrency exception
  * Dirty read, non-repeatable read, phantom read，Missing updates
* Common isolation levels
  * Read Uncommitted：Read uncommitted data
  * Read Committed：Read Committed data
  * Repeatable Read：Repeatable reading
  * Serializable：Serialization

**First Type Lost Updates**：rollback of a certain transaction resulted in the loss of the updated data of another transaction.

**Second Type Lost Updates**：commit of a certain transaction results in the loss of the updated data of another transaction.

**Dirty Read**：A certain transaction reads uncommitted data of another transaction.

**Non-repeatable read**：In a certain transaction, the results of reading the same data before and after are inconsistent.

**Phantom reading**：For a certain transaction, the number of rows found before and after the same table is inconsistent.

**Transaction isolation level**：




**Spring Transaction Management**

* Declarative transaction
  * Declare the transaction characteristics of a method through XML configuration
  * Through annotations, declare the transaction characteristics of a method.
* Programmatic transaction
  * Manage transactions through TransactionTemplate and perform database operations through it.

## 5. Show Comments

* Data Layer
  * Query a page of comment data based on the entity.
  * Query the number of comments based on the entity.
* Logic Layer
  * Handle the business of query and comment.
  * Handle the business of querying the number of comments.
* View Layer
  * When displaying post detail data, all comment data of the post will be displayed at the same time.

## 6. Add Comment

* Data Layer
  * Add comment data.
  * Modify the number of comments on the post.
* Logic Layer
  * Deal with the business of adding comments: add comments first, and then update the number of comments on the post.
* View Layer
  * Process requests to add comment data.
  * Set up a form for adding comments.

## 7. Private message list

* Private message list
  * Query the current user's conversation list, each conversation only displays the latest private message.
  * Support paging display.
* Private message details
  * Query the private messages contained in a session
  * Support paging display.

## 8. Send Message

* Send Message
  * Use AJAX to send private message
  * Refresh the private message list after sending successfully.
* Set read
  * When accessing the private message details, set the displayed private message to read status.

## 9. Unified exception handling

* @ControllerAdvice
  * Used to decorate the class, indicating that the class is the global configuration class of the Controller.
  * In this class, the following three global configurations can be performed on the Controller: exception handling scheme, data binding scheme, and parameter binding scheme.
* @ExceptionHandler
  
* @ModelAttribute
 
* @DataBinder
  

## 10. Unified logging

**demand**：

* Post module
* Comment module
* Message module

**Concept of AOP**：

* Aspect Oriented Programing 
* AOPis a programming idea and a supplement to OOP, which can further improve the efficiency of programming.

**Implementation of AOP**
**Spring AOP**

* JDK dynamic proxy
  * The dynamic proxy technology provided by Java can create proxy instances of the interface at runtime.
  * Spring AOP uses this method by default to weave code in the proxy instance of the interface.
* CGLib dynamic agent
  * Use the underlying bytecode technology to create subclass proxy instances at runtime.
  * When the target object does not have an interface, Spring AOP will use this method to weave code in the subclass instance.

 