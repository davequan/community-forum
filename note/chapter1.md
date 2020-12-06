# development community homepage

## 1. Build a development environment

### Apache Maven

* Can help us build projects and manage jar packages in projects
* Maven repository：address store dependency
  * dependency:The resource jar package that the project depends on when creating the project.
  * local repository：default ~/.m2/repository
  * remote repository：local repository,mirror repository,official repository
  
### IntelliJ IDEA

* Currently the most popular Java integrated development tool
*  Example: install, configure, create project
  * Download: http://www.jetbrains.com/idea
  * Set maven and its configuration file under the Editor of Settings.
  * Create a maven template project
  * recompile: mvn clean compile

### Spring Initializr

* The package is integrated and classified by function.

* Bootstrap tool for creating Spring Boot projects
  * https://start.spring.io
*  Example: create forum project
  * springboot has imbedded Tomcat.

### Spring Boot starting example
*  Spring Boot core function
  
* Start-up dependency, automatic configuration, endpoint monitoring
  
* Example:a simple client request configuration

  * application.properties file to configure

    ``````
    # ServerProperties
    server.port=8080         server port
    server.servlet.context-path=/community  request mapping path
    ``````

## 2. Spring beginner

### Spring bucket
* Spring Framework 
* Spring Boot
* Spring Cloud (Microservice)
* Spring Cloud Data Flow
*  website: https://spring.io

### Spring Framework
* Spring Core

* IoC、AOP  (The idea od managing objects, the objects managed by spring are called beans)

* Spring Data Access
  * Transactions、Spring MyBatis
* Web Servlet
  * Spring MVC
* Integration
  * Email、Scheduling、AMQP、Security

### Spring IoC
* Inversion of Control
  * Ideas of Object-Oriented Programming。
*  Dependency Injection
  * Method to realize the inversion of control
*  IoC Container
  * The IoC container is the key to achieving dependency injection, and is essentially a factory.
  * The premise of container-managed Beans: Provide the type of Bean and configure the relationship between the Beans through the configuration file
  * Decouple between beans

### Coding

* Boostrap to start Spring:

``````
@SpringBootApplication
public class CommunityApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}
}
Automatically scan the following beans of class
@Component @Repository @Service @Controller
``````

Test code

``````
@ContextConfiguration(classes = CommunityApplication.class)
``````


Give Bean a custom name: @Component("Name")

The initialization method @PostConstruct is called after the constructor. It is called before the object is destroyed, @PreDestroy.

@Scope() specifies singleton multiple instances

@Configuration:Configuration class to load and use third-party classes.

* Dependency injection:
  * @Autowired

## 3. Spring MVC starter

### HTTP
* HyperText Transfer Protocol
* Application layer protocol for transmitting content such as HTML
* It specifies how to communicate between the browser and the server, and the data format during communication.
* Learning Website：https://developer.mozilla.org/zh-CN


Browser server communication steps:

1. Open a TCP connection
2. An HTTP message occurred
3. Read the message information returned by the server
4. Close the connection or reuse the connection for subsequent requests


### Spring MVC

* three layer architecture
  * Presentation layer (mvc), business layer, data access layer

*  MVC
  * Model：model layer
  * View：view layer
  * Controller：controller layer

* Core Component
  * FrontController：DispatcherServlet

The browser accesses the server. The first access is the Controller layer.The Controller calls the business layer for processing. After processing, the data obtained is encapsulated in the Model and passed to the view layer.

### Thymeleaf
* temple enginer
  * Generate dynamic HTML
*  Thymeleaf
  * natural templates, which use HTML files as templates
### Coding part

Low level：

```java
@RequestMapping("/http")
public void http(HttpServletRequest request, HttpServletResponse response) {
    // get rquest data
    System.out.println(request.getMethod());
    System.out.println(request.getServletPath());
    Enumeration<String> enumeration = request.getHeaderNames();
    while (enumeration.hasMoreElements()) {
        String name = enumeration.nextElement();
        String value = request.getHeader(name);
        System.out.println(name + ": " + value);
    }
    System.out.println(request.getParameter("code"));

    // return response data
    response.setContentType("text/html;charset=utf-8");
    try (
        PrintWriter writer = response.getWriter();
    ) {
        writer.write("<h1>new website</h1>");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```


Get the variable from the path (two methods):

```java
@RequestMapping(path = "/students", method = RequestMethod.GET)
@ResponseBody
public String getStudents(
    @RequestParam(name = "current", required = false, defaultValue = "1") int current,
    @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
    System.out.println(current);
    System.out.println(limit);
    return "some students";
}

@RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
@ResponseBody
public String getStudent(@PathVariable("id") int id) {
    System.out.println(id);
    return "a student";
}
```

POST request:

``````java
@RequestMapping(path = "/student", method = RequestMethod.POST)
@ResponseBody
public String saveStudent(String name, int age) {
    System.out.println(name);
    System.out.println(age);
    return "success";
}
``````

Response to HTML data (using ModelAndView or Model):

``````java
@RequestMapping(path = "/teacher", method = RequestMethod.GET)
public ModelAndView getTeacher() {
    ModelAndView mav = new ModelAndView();
    mav.addObject("name", "zhang");
    mav.addObject("age", 30);
    mav.setViewName("/demo/view");
    return mav;
}

@RequestMapping(path = "/school", method = RequestMethod.GET)
public String getSchool(Model model) {
    model.addAttribute("name", "北京大学");
    model.addAttribute("age", 80);
    return "/demo/view";
}
``````

Response to JSON data (asynchronous request): Java object -> JSON string -> JS object, using @ResponseBody annotation

``````java
@RequestMapping(path = "/emp", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> getEmp() {
    Map<String, Object> emp = new HashMap<>();
    emp.put("name", "zhang");
    emp.put("age", 23);
    return emp;
}
..Convert to json string {"name":"Zhang ","age":"23"}
//You can also return List<Map<String, Object>>, list collection.
``````

## 5. MyBatis starter

### Install the database

* install MySQL Server
* install MySQL Workbench

### MyBatis
* Core Component
* SqlSessionFactory: The factory class used to create SqlSession.
  * SqlSession: The core component of MyBatis, used to execute SQL to the database.
  * Main configuration file: XML configuration file, which can make detailed configuration of the underlying behavior of MyBatis.
  * Mapper interface: DAO interface, habitually called Mapper in MyBatis.
  * Mapper mapper: a component used to write SQL and map SQL and entity classes. It can be implemented using XML and annotations

* Demo
  * Use MyBatis to perform CRUD operations on the user table.

* Configure database and Mybatis related in application.properties

## 6. Development Community Home
* Development Process
  * 1 request execution process
*  Step by step
    * Development community homepage, showing the first 10 posts
    * Develop a paging component, which displays all posts


## 8. Version Control
* Get to know Git
  * Git introduction
  * Git installation and configuration
* Git common commands
  * commit to local repository
  * push to remote repository

```c
# account setting
git config --list
git config --global user.name "*****"
git config --global user.email "****"
# local repository
git init
git status -s
git add *
git commit -m '...'

# push project
git remote add origin
https://git.....
git push -u origin master
# clone existing repository
git clone https://git......
```






