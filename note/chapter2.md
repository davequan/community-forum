# Spring Boot,develop community login module

## 1. send email activation 

* mail setting
  * Enable client SMTP service

* Spring Email
  * import jar package
  * Email parameter configuration
  * Use JavaMailSender to send mail
* template engine
  * Use Thymeleaf to send HTML email

## 2. Develop registration functio

* Visit the registration page
  * Click the link in the top area to open the registration page.
* Submit registration data
  * Submit data via form
  * Server verifies whether the account already exists, and the mailbox is registered.
  * Server sends an activation email.
* Activate registered account
  * Click the link in the email to access the activation service on the server.

## 3. Session management

* Basic property of HTTP
  * HTTP is simple
  * HTTP is extensible
  * HTTP is stateless
* Cookie
  * data sent by the server to the browser and saved on the browser side.
  * Next time the browser accesses the server, it will automatically carry the block of data and send it to the server.
* Session
  * It is a JavaEE standard used to record client information on the server side.
  * Data stored on the server is more secure, but it will also increase the memory pressure on the server.

## 4. Generate verification code

* Kaptcha
  * import jar package
  * Write Kaptcha configuration class
  * Generate random characters, generate pictures

## 5. Develop login and logout functions

* Visit the login page
  * Click on the link in the top area to open the login page
* Log in
  * Verify account, password, and verification code.
  * When successful, the login credentials are generated and issued to the client.
  * When it fails, jump back to the login page.
* Log out
  * Modify the login credentials to an invalid state.
  * Redirect to Home Page

## 6. Show login information

* Interceptor Demo
  * Define Interceptor，implement HandlerInterceptor
  * Define interception path
* Interceptor application
  * Query the logged-in user at the beginning of the request
  * Hold user data in this request
  * Display user data on template view
  * Clean up user data at the end of the request

## 7. Account Setting

* Upload File
  * Request：must be "post"
  * form：enctype=“multipart/form-data”
  * Spring MVC：Process uploaded files through MultipartFile
* Development steps
  * Visit account settings page
  * Upload avatar
  * Get avatar

## 8. Check login status

* Use interceptor
  * Mark custom annotations before the method
  * Intercept all requests and only process methods with this annotation
* Custom annotation
  * Commonly used meta annotations：
    @Target、@Retention、@Document、@Inherited
* how to read annotation：
  Method.getDeclaredAnnotations ()
  Method.getAnnotation (Class<T> annotationClass)











