/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liyc.security.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Joe Grandja
 */
@Controller
public class MainController {

  @Value("${spring.application.name}")
  private String appName;
  
	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/user/index")
	public String userIndex() {
		return "user/index";
	}

	@RequestMapping("/user/info")
	@ResponseBody
	public Object userInfo(Principal user) {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return ((Authentication)user).getPrincipal()+" "+appName;
	}

//	@CrossOrigin(origins = "http://client2.liyuchang.cn:9081")
	@RequestMapping("/common/info")
	@ResponseBody
	public Object common(Principal user) {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return "common "+appName;
	}

	@RequestMapping("/admin/detail")
	@ResponseBody
	public Object adminDetail(Principal user) {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return "detail";
	}

	/**
	 * @PostAuthorize("returnObject.name == authentication.name")
        Person findOne(Integer id);
        
        //当有多个对象是使用filterTarget进行标注
      @PreFilter(filterTarget="ids", value="filterObject%2==0")
       public void delete(List<Integer> ids, List<String> usernames) {
  
       }

      @PreAuthorize("hasRole('ADMIN')")
      @PostFilter("filterObject.name == authentication.name")
      List<Person> findAll();
	 */
	@RequestMapping("/admin/list")
	@ResponseBody
	public Object adminList(Principal user) {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return "list";
	}

	@RequestMapping("/info/admin")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Object infoAdmin() {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return "infoAdmin";
	}

	@RequestMapping("/info/role")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE')")
	public Object infoRole() {
//	  return ((UsernamePasswordAuthenticationToken)user).getPrincipal();
	  return "infoRole";
	}

	
	@RequestMapping("/login")
	public String login() {
	  return "login";
	}
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
