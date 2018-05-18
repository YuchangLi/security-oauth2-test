/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.liyc.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
// 开启方法级别注解的配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsService userService;

  // @formatter:off
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // super.configure(http);
    http
      .formLogin()
        .loginPage("/login")
//        .successForwardUrl("/user/index")
        .failureUrl("/login-error").permitAll()
      .and()
      .sessionManagement()
        .invalidSessionUrl("/login")
      .and()
        .csrf()
          .disable()
        .cors() // by default uses a Bean by the name of corsConfigurationSource
      .and()  
      .authorizeRequests()
        .antMatchers("/css/**", "/favicon.ico", "/index", "/common/**")
          .permitAll()
        .antMatchers("/user/**")
          .access("hasRole('USER') or hasAnyAuthority('ROOT')")
        .antMatchers("/admin/**")
          .access("@userService.hasPermission(request,authentication)")
        .anyRequest().authenticated()
//        .and()
//        .logout().logoutUrl("/logout")
    ;
  }
  // @formatter:on

  /*
   * (non-Javadoc)
   * @param auth
   * @throws Exception
   * @see
   * org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#
   * configure(org.springframework.security.config.annotation.authentication.builders.
   * AuthenticationManagerBuilder)
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
//      .and() 会覆盖yam里配置的user
//      .inMemoryAuthentication().withUser("user2").password("password2").roles("USER")
      .userDetailsService(userService) // 替换默认InMemoryUserDetailsManager
      .passwordEncoder(passwordEncoder()) // 替换默认PlaintextPasswordEncoder
      ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  // Remove the ROLE_ prefix
  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
      return new GrantedAuthorityDefaults("");
  }
  
  /**
   * CORS处理，另外方法：https://spring.io/guides/gs/rest-service-cors/
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("https://example.com", "*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
