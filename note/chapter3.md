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
* 业务层
  * 处理添加评论的业务：先增加评论、再更新帖子的评论数量。
* 表现层
  * 处理添加评论数据的请求。
  * 设置添加评论的表单。

## 7. 私信列表

* 私信列表
  * 查询当前用户的会话列表，每个会话只显示一条最新的私信。
  * 支持分页显示。
* 私信详情
  * 查询某个会话所包含的私信。
  * 支持分页显示。

## 8. 发送私信

* 发送私信
  * 采用异步的方式发送私信。
  * 发送成功后刷新私信列表。
* 设置已读
  * 访问私信详情时，将显示的私信设置为已读状态。

## 9. 统一处理异常

* @ControllerAdvice
  * 用于修饰类，表示该类是Controller的全局配置类。
  * 在此类中，可以对Controller进行如下三种全局配置：异常处理方案、绑定数据方案、绑定参数方案。
* @ExceptionHandler
  * 用于修饰方法，该方法会在Controller出现异常后被调用，用于处理捕获到的异常。
* @ModelAttribute
  * 用于修饰方法，该方法会在Controller方法执行前被调用，用于为Model对象绑定参数。
* @DataBinder
  * 用于修饰方法，该方法会在Controller方法执行前被调用，用于绑定参数的转换器。

## 10. 统一记录日志

**需求**：

* 帖子模块
* 评论模块
* 消息模块

**AOP的概念**：

* Aspect Oriented Programing，即面向方面（切面）编程。
* AOP是一种编程思想，是对OOP的补充，可以进一步提高编程的效率。

<img src="img\20191121213702.png" style="zoom: 67%;" />

**AOP的术语**：

<img src="img\20191121214201.png" style="zoom:60%;" />

**AOP的实现**

* AspectJ
  * AspectJ是语言级的实现，它扩展了Java语言，定义了AOP语法。
  * AspectJ在编译期织入代码，它有一个专门的编译器，用来生成遵守Java字节码规范的class文件。
* Spring AOP
  * Spring AOP使用纯Java实现，它不需要专门的编译过程，也不需要特殊的类装载器。
  * Spring AOP在运行时通过代理的方式织入代码，只支持方法类型的连接点。
  * Spring支持对AspectJ的集成。

**Spring AOP**

* JDK动态代理
  * Java提供的动态代理技术，可以在运行时创建接口的代理实例。
  * Spring AOP默认采用此种方式，在接口的代理实例中织入代码。
* CGLib动态代理
  * 采用底层的字节码技术，在运行时创建子类代理实例。
  * 当目标对象不存在接口时，Spring AOP会采用此种方式，在子类实例中织入代码。

 