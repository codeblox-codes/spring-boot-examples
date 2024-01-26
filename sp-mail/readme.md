# Spring Boot Email Sending

Sending emails in Spring Boot involves the use of the `spring-boot-starter-mail` dependency.

## Dependency

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
````

## SMTP SERVER
We now need to configure a SMTP SERVER. We can use for example SMTP4DEV available on dockerhub.
We can use a `docker.compose.yml` file:

```yml
version: '3'

services:
smtp4dev:
container_name: SMTP4DEV
image: rnwood/smtp4dev:v3
restart: always
ports:
# Change the number before : to the port the web interface should be accessible on
- '5000:80'
# Change the number before : to the port the SMTP server should be accessible on
- '25:25'
```

## Application Properties

Configure the Mail Server properties in your` application.properties` file:

```properties
spring.mail.host=localhost
spring.mail.port=25
```

## Email Validation
Ensure the provided email is valid using the following regex:

`^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$`

You can use the following Java code for email validation:

```java
private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

@Override
public Boolean isEmailValid(String email){
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
}
```

## Sending Emails

To send emails using JavaMailSender, perform the email validation check:

```java
@Override
public void sendMail(MailDTO mailDTO){
    if (!isEmailValid(mailDTO.email())) {
        throw new RuntimeException("The provided email is not valid");
    }
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setSubject(mailDTO.subject());
    mailMessage.setTo(mailDTO.email());
    mailMessage.setText(mailDTO.message());
    javaMailSender.send(mailMessage);
}
```
