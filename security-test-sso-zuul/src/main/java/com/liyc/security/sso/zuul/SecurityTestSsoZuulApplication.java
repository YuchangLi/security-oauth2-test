package com.liyc.security.sso.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
//@EnableOAuth2Sso
@EnableZuulProxy
public class SecurityTestSsoZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityTestSsoZuulApplication.class, args);
	}
	
}
