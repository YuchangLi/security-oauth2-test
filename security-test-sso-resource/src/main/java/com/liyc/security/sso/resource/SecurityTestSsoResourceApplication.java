package com.liyc.security.sso.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SecurityTestSsoResourceApplication
 * @Description: resource server
 * @author liyuchang
 * @date 2018年5月18日
 */
@SpringBootApplication
@RestController
public class SecurityTestSsoResourceApplication {

  @Value("${spring.application.name}")
  private String app;

  public static void main(String[] args) {
    SpringApplication.run(SecurityTestSsoResourceApplication.class, args);
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
}
