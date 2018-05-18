/**  
 * @Title: User.java
 * @Package com.liyc.security.entity
 * @author liyuchang
 * @date 2018年5月4日  
 */
package com.liyc.security.entity;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liyuchang
 * @date 2018年5月4日
 */
public class User {
  private Long id;
  private String username;
  private String password;
  private String email;
  private Date createTime;

  /**
   * @return id
   */
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  /**
   * @return email
   */
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  /**
   * @return username
   */
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  /**
   * @return password
   */
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  /**
   * @return createTime
   */
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
