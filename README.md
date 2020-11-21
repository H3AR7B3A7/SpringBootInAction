# Spring Boot
After exploring [servlets and Jsp](https://github.com/H3AR7B3A7/ServletsAndJsp) in a Maven web application 
and [Spring MVC](https://github.com/H3AR7B3A7/SpringMVC) in a custom Maven build with spring mvc dependencies, 
let us go back to a Spring Boot application set up with the initializr.  
With a higher appreciation of what SpringBoot and the initializr do in the background for us, 
we will have a closer look at different aspects of this rather simple application. 

In the [web store](https://github.com/H3AR7B3A7/TjEnterprise-pet-store) we made before we didn't pay attention to all the possibilities because that was not in our interest then. 
This is a good example of Spring keeping us from having to write a lot of plumbing code and providing adequate defaults. For instance when it comes to security though, Spring doesn't 
really have any business deciding everything for us.  
In [spring courses](https://github.com/H3AR7B3A7/SpringCourses) and 'SpringServletStack' for [XML](https://github.com/H3AR7B3A7/SpringServletStackXml) and 
[JavaConfig](https://github.com/H3AR7B3A7/SpringServletStackCode) we already saw some concepts of configuration and security. We will explore them further in this application.

## Configuration
What we need to understand about **Spring conditional configuration:** Most of the Spring default configuration beans only get created when we don't provide any of our own. We can write our own custom conditions
in Spring by implementing the Condition interface. There are also some standard annotations to choose from like: @ConditionalOnBean, @ConditionalOnMissingBean, ...  
Check out some more examples [here](https://iamninad.com/conditional-bean-creation-in-spring-boot/).
- **Overriding auto-configuration:** Because of these conditionals we can create our own configuration and Spring will automatically not create the default configuration beans.
- **Configuration with properties:** We can also modify default behaviour of auto-configuration to some extent with properties. We can do this in different ways with descending precedence in:
  - Command-line arguments
  - JNDI attributes from java:comp/env
  - JVM system properties
  - Operating system env variables
  - Randomly generated values dor properties prefixed with random.*  
  (referenced when setting other properties, such as ${random.long})
  - An application.properties (or application.yml) file outside the application
  - An application.properties (or application.yml) packaged inside of the application
  - Property sources specified by @PropertySource
  - Default properties
  
Application.properties (or application.yml) files can reside in these locations, also with descending precedence:
- Externally, in a /config subdirectory
- Externally, in the directory where we run the application
- Internally, in the 'resources root' in a /config subdirectory
- Internally, in the 'resources root' (Provided by Spring)
 
A good rule is to use the generated file provided by Spring unless you want them to be overridden, or when you don't want to publish some sensitive information to GitHub through .gitignore.

