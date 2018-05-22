package com.liyc.security.resource;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityTestSsoResource2Application {

  @Value("${spring.application.name}")
  private String app;
  @Autowired
  private OAuth2RestOperations oAuth2RestTemplate;

  public static void main(String[] args) {
    SpringApplication.run(SecurityTestSsoResource2Application.class, args);
  }

  @GetMapping("/api/{id}")
  public String get(@PathVariable("id") String id) {
    System.out.println("id=" + id);
    return "hello " +id+ " from " + app;
  }

  @GetMapping("/hello")
  public String hello() {
    return "hello from + " + app;
  }
  
  @RequestMapping("/api/resource1")
  String resource1() {
      String result = oAuth2RestTemplate.getForObject("http://localhost:8085/api/5", String.class);
//      String result = oAuth2RestTemplate.getForObject(messagesUrl + "/hello", String.class);
      return result;
  }
  
  @GetMapping("/user")
  public Authentication user(Authentication user) {
    return user;
  }
  
  @GetMapping("/principal")
  public Object principal(Principal user) {
    System.out.println((Authentication)user);
    return (Authentication)user;
  }

  @Bean
  OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details){
      return new OAuth2RestTemplate(details,oAuth2ClientContext);
  }
}
