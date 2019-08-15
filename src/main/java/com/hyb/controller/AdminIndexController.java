package com.hyb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 负责后台页面跳转
 * @author Administrator
 *
 */
@Controller
public class AdminIndexController {

	/**
	 * 跳转到登陆界面
	 * @return
	 */
	/*@RequestMapping("/loginUI")
	public String login(){
		
		return "/admin/login";
	}*/
	
	/**
	 * 跳转到后台登陆页面
	 * @return
	 */
	@RequestMapping("/admin")
	public String login(){
		
		return "/admin/login";
	}
	/**
	 * 跳转到后台首页
	 * @return
	 */
	@RequestMapping("/admin/index")
	public String index(){
		return "/admin/index";
	}
	@RequestMapping("/admin/{page}")
	public String forward(@PathVariable("page") String page){
		
		return "/admin/"+page;
	}
}

	
