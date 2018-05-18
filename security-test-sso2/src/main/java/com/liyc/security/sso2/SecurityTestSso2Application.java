package com.liyc.security.sso2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SsoClient1Application
 * @Description: sso client
 * @author liyuchang
 * @date 2018年5月18日
 */
@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SecurityTestSso2Application {

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;


//    @Value("${messages.url:http://sso-resource:8085}/resource")
    @Value("${messages.url:http://sso-resource:8085}")
    String messagesUrl;

    @Value("${spring.application.name}")
    String appName;

    public static void main(String[] args) {
        SpringApplication.run(SecurityTestSso2Application.class, args);
    }
    
    @GetMapping("/user")
    public Authentication user(Authentication user) {
      return user;
    }

    @GetMapping("/hello")
    public String hello() {
//      String result = oAuth2RestTemplate.getForObject("http://sso-tmall:8084/hello", String.class);
      return "hello from "+appName;
  }

    @RequestMapping("/api")
    String home(Model model) {
        String result = oAuth2RestTemplate.getForObject(messagesUrl + "/api/2", String.class);
//        String result = oAuth2RestTemplate.getForObject(messagesUrl + "/hello", String.class);
        return result;
    }

    @Bean
    OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details){
        return new OAuth2RestTemplate(details,oAuth2ClientContext);
    }
}
