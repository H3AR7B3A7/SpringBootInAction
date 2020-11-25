# Spring Boot
## Preface
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

### Setup
To start off we created a project with the initializr including these dependencies:  
- Web
- Thymeleaf
- Jpa  
- H2 (we include H2, but switch out for MySQL later)

## Configuration
What we need to understand about **Spring conditional configuration:** Most of the Spring default configuration beans only get created when we don't provide any of our own. We can write our own custom conditions
in Spring by implementing the Condition interface. There are also some standard annotations to choose from like: **@ConditionalOnBean**, **@ConditionalOnMissingBean**, ...  
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
  - Property sources specified by **@PropertySource**
  - Default properties
  
Application.properties (or application.yml) files can reside in these locations, also with descending precedence:
- Externally, in a /config subdirectory
- Externally, in the directory where we run the application
- Internally, in the 'resources root' in a /config subdirectory
- Internally, in the 'resources root' (Provided by Spring)
 
A good rule is to use the generated file provided by Spring unless you want them to be overridden, or when you don't want to publish some sensitive information to GitHub through .gitignore.  

*Although using .yml can save us some work writing out property paths and can hold multiple profiles, I do not recommend using them, so they will not be covered in this document.*

### Externalizing configuration
To externalize the configuration of our 'amazon associate id' we add this dependency:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    
This way we can keep our configurations in one place and easily change them later when necessary. 
We set the value in the controller by using the annotation **@ConfigurationProperties(prefix="amazon")** 
and providing a setter:

    public void setAssociateId(String associateId) {this.associateId = associateId;}

Now we can easily see/change the value in application.properties with:

    amazon.associateId=someAssociateID
    
### Capturing configuration in a bean
Instead of annotating the controller and having the setter in there, we could just create a bean for that and then wire in the bean. 
This way we can isolate the configuration related to Amazon from the controller.

## Configuring with profiles
We can specify environments with annotation in our configuration classes with **@Profile("production")**. 
This way we can choose a profile in applications.properties by specifying the property: 

    spring.profiles.active=production

### Profile specific properties files
We can have profile specific properties files by using naming them using the pattern:  
**application-{profile}.properties**  
For instance, we can have different logging levels for different environments:  

    logging.level.root=DEBUG / WARN / INFO 
    
In IntelliJ we can specify the active profiles under *'Edit configurations...'*.  
We can still have a default properties file to hold properties that aren't profile specific when we specified active profiles.

## Configuring SSL/TLS certificate with keystore
Command line example:

    keytool -genkeypair -alias spring-https -keyalg RSA -keysize 2048 -keystore spring-https.jks -validity 365

Converting jks to PKCS12 format:

    keytool -importkeystore -srckeystore spring-https.jks -destkeystore spring-https.jks -deststoretype pkcs12
    
Service will now be served at https://localhost:8443/
While serving locally the browser will complain it is 'not safe', but that's nothing to worry about.

## Logging
Logback is the standard provided by spring-boot-starter-logging. Other options are 'Log4j' or 'Log4j2'. We could exclude Logback and provide dependencies for them instead, 
but there is usually no need to do so.  
For full control over logging we could add a 'logback.xml'.
[Documentation](http://logback.qos.ch/documentation.html)

## Custom error handling
The default error handler that’s auto-configured by Spring Boot looks for a view
named *'error'*. If it can’t find one, it uses its default whitelabel error view.

This means we can just add a file *error.html* to easily create a custom error page.
On this error page we can refer to the status, url path and the generated error with:  

    ${status}, ${path} and ${error}

## Security
We add Spring security by simply adding the following dependency:

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder which required plain text passwords. 
In Spring Security 5, the default is DelegatingPasswordEncoder, which required Password Storage Format.  

We can still use the old way by adding a prefix to our password like this:

    insert into Reader (username, password, fullname) values ('test', '{noop}test', 'test');

Later when creating users with a form we will want to use a User Builder with default password encoder.

Check [here](https://github.com/H3AR7B3A7/SpringCourses/tree/master/security) for a more advanced example of security.
There we saw how to generate CSRF-tokens, use password encoders, create custom authorities and more...

## Testing Spring Boot applications
*I will be documenting testing mainly using JUnit 5 Jupiter. For reference, I will add the JUnit 4 variants of annotations 
between square brackets. Be aware they can't always just be switched out, so you might have to check some documentation on their use.*

Because Spring already provides us with the dependency *spring-boot-starter-test* our project already includes a lot of dependencies for testing purposes:
- **JUnit:** The de-facto standard for unit testing Java applications
- **Spring (Boot) Test:** Utilities and integration test support for Spring (Boot) applications
- **AssertJ:** A fluent assertion library
- **Hamcrest:** A library of matcher objects (also known as constraints or predicates)
- **Mockito:** A Java mocking framework
- **JSONassert:** An assertion library for JSON
- **JsonPath:** XPath for JSON

### Mock MVC
To configure MockMVC we need a WebApplicationContext. We can wire this in and configure MockMVC ourselves like this:

    @Autowired
    private WebApplicationContext context;
        
    private MockMvc mockMvc;
    
    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    
We can also accomplish this with an annotation **@AutoConfigureMockMvc**

When we configure MockMVC ourselves however, we can choose to only setup the controllers we need:
- standaloneSetup()
- webAppContextSetup()

### Testing with security
Because we make use of Spring Security we need the following dependency:

    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>

Now we can simply annotate our test with **@WithUserDetails(value = "test")** to authenticate as the user named: *test*.
There is also a parameter *userDetailsServiceBeanName* with a default value of "myUserDetailsService" hidden in this annotation.  

We have to use **@WithUserDetails** in our application because we use our custom *'Reader'* class for authentication, 
but when we use the default User class for authentication **@WithMockUser** is the easy option.

    @Test
    @WithMockUser(username="someName",
                  password="optional",
                  roles="READER")
    public void someTest() throws Exception {...}

### Integration Test Environment
With the **@SpringBootTest** annotation, Spring Boot provides a convenient way to start up an application context to be used in a test.  

We can use the H2 database we used at the beginning of the project when we are running integration setting the scope to 'test':

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>


To enable Spring support for integration testing we annotate the class with **@ExtendWith(SpringExtension.class)**  
[ **@RunWith(SpringJUnit4ClassRunner.class)** or shortened **@RunWith(SpringRunner.class)** ]  

As of Spring Boot 2.1, we no longer need to load the SpringExtension because it's included as a meta annotation in the Spring Boot test annotations like **@DataJpaTest**, **@WebMvcTest**, and **@SpringBootTest**.  

I will be diving deeper into testing Spring Boot applications in one of my next projects...