# Elasticsearch,Distributed search engine

## 1. Elasticsearch Intro
- A distributed, Restful style search engine.
  - Support the retrieval of various types of data.
  - Fast search speed, real-time search service can be provided.
  - It is easy to expand horizontally and can process PB-level massive data per second.

### Elasticsearch terms

- Index (corresponding to the database), type (corresponding to the table), document (one row in the table), field (one column).                                                                                                                                                                                                                                                                                                                                                                                                                                 
- Cluster (servers are grouped together), node (each server in the cluster), shard (division of the index), copy (backup of the shard).


The data searched by ES must be transferred to ES, which is a database from a certain perspective.

### Elasticsearch function

* Install and modify configuration files
  * elasticsearch.yml file，modify cluster.name，path.data，path.logs
  * Configure environment variables in Java
* Install postman (submit html data to ES) to simulate web client
* Start ES: open bin/elasticsearch.bat
  * View cluster health status：curl -X GET "localhost:9200/_cat/health?v"
  * View node：curl -X GET "localhost:9200/_cat/nodes?v"
  * View index：curl -X GET "localhost:9200/_cat/indices?v"
  * Create index：curl -X PUT "localhost:9200/test"
  * Delete index：curl -X DELETE "localhost:9200/test"

* Use postman to query

  * submit data，PUT localhost:9200/test/_doc/1选择Body,raw,JSON

  * search，GET localhost:9200/test/_search?q=title(/content):xxx

  * ES segmented the keywords when searching

  * Construct complex search conditions through the request body

## 2. Spring integrate Elasticsearch

* Add depenency
  * spring-boot-starter-data-elasticsearch
*  configure Elasticsearch
  * cluster-name、cluster-nodes
  * Both Redis and ES use Netty at the bottom level, and there is a startup conflict. Solution: Add an initialization method to the CommunityApplication class for configuration.
* Spring Data Elasticsearch
  * ElasticsearchTemplate（CRUD method integrated with ES）
  * ElasticsearchRepository（Interface, the bottom layer is ElasticsearchTemplate, which is more convenient to use）

## 3. Develop community search function

### Search Service

- Save the post to the Elasticsearch server.
  - Use annotations to configure the post entity class DiscussPost
  - Get data from Mybatis and save
  - Create the DiscussPostRepository class in the dao layer and inherit the ElasticsearchRepository interface, which integrates the CRUD method
- Delete the post from the Elasticsearch server.
- Search for posts from Elasticsearch servers.
  - ES can add tags to the searched words to achieve highlighting
  - Query using elasticTemplate.queryForPage()

### Post event

- When posting a post, submit the post to the Elasticsearch server asynchronously.
  -Create a new ElasticsearchService class, define CRUD and search methods.
  -Define and trigger the posting event (Event, eventProducer.fireEvent(event)) when posting in the DiscussPostController class
  -When adding a comment, submit the post to the Elasticsearch server asynchronously.
- Add a method in the consumption component to consume post publishing events.
  - Add a method to consume posting events in the EventConsumer class
  - Query posts in the event and save them to ES server

### show result

- Handle the search request in the controller and display the search result on HTML.
  - Create a new SearchController class to handle search requests
  - At this time, it is a GET request, and the input of the keyword (search?keyword=xxx)
  - Modify index.html, form submission path, text box name="keyword"
  - Modify in search.html, traverse to get the post.

