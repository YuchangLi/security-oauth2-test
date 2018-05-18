/**
 * @Title: UserServiceImpl.java
 * @Package com.liyc.security.service
 * @author liyuchang
 * @date 2018年5月4日
 */
package com.liyc.security.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import com.liyc.security.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.thymeleaf.util.StringUtils;


/**
 * @ClassName: UserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liyuchang
 * @date 2018年5月4日
 */
@Service("userService")
public class UserServiceImpl implements UserDetailsManager {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  /**
   * uri匹配工具
   */
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  /*
   * (non-Javadoc)
   * 
   * @param username
   * 
   * @return
   * 
   * @throws UsernameNotFoundException
   * 
   * @see
   * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.
   * String)
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO load from db
    User user = new User();
    user.setId(1l);
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode("123456"));
    user.setEmail("lyc@qq.com");
    UserDetails authentication = new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("USER", "ROOT", "ROLE"));
    return authentication;
  }

  /**
   * @Title: hasPermission
   * @Description: 当前登录用户是否具有访问该URL的权限
   * @param request
   * @param authentication
   * @return boolean
   * @throws
   */
  public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    logger.info("【RbacServiceImpl】  --hasPermission={}", authentication.getPrincipal());
    Object principal = authentication.getPrincipal();

    boolean hasPermission = false;
    // 有可能是匿名的anonymous
    if (principal instanceof org.springframework.security.core.userdetails.User) {
      // admin永远放回true
      if (StringUtils.equals("admin", ((org.springframework.security.core.userdetails.User) principal).getUsername())) {
        hasPermission = true;
      } else {
        // 读取用户所拥有权限所有的URL 在这里全部返回true
        @SuppressWarnings("serial")
        Set<String> urls = new HashSet<String>() {{
          add("/admin/detail");
          if(((org.springframework.security.core.userdetails.User) principal).getUsername().equals("lyc")){
            add("/admin/list");
          }
        }};

        for (String url : urls) {
          if (antPathMatcher.match(url, request.getRequestURI())) {
            hasPermission = true;
            break;
          }
        }
      }
    }
    return hasPermission;
  }

  /*
   * (non-Javadoc)
   * 
   * @param user
   * 
   * @see
   * org.springframework.security.provisioning.UserDetailsManager#createUser(org.springframework.
   * security.core.userdetails.UserDetails)
   */
  @Override
  public void createUser(UserDetails user) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @param user
   * 
   * @see
   * org.springframework.security.provisioning.UserDetailsManager#updateUser(org.springframework.
   * security.core.userdetails.UserDetails)
   */
  @Override
  public void updateUser(UserDetails user) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @param username
   * 
   * @see org.springframework.security.provisioning.UserDetailsManager#deleteUser(java.lang.String)
   */
  @Override
  public void deleteUser(String username) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @param oldPassword
   * 
   * @param newPassword
   * 
   * @see
   * org.springframework.security.provisioning.UserDetailsManager#changePassword(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void changePassword(String oldPassword, String newPassword) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @param username
   * 
   * @return
   * 
   * @see org.springframework.security.provisioning.UserDetailsManager#userExists(java.lang.String)
   */
  @Override
  public boolean userExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }

}
