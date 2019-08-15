package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyb.pojo.Admin;
import com.hyb.service.AdminService;
import com.mysql.jdbc.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理后台管理员请求类
 * 
 * @author Sean
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 管理员登陆
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/login")
	@ResponseBody
	public void login(Admin admin,HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		PrintWriter out = response.getWriter();
		if(StringUtils.isNullOrEmpty(admin.getUsername()) || 
				StringUtils.isNullOrEmpty(admin.getPassword())){
			out.write("false");
		}
		Admin adminUser = adminService.login(admin);
		request.getSession().setAttribute("admin",adminUser);
		if(adminUser != null) {
			out.write("ok");
		}
	}

	/**
	 * 添加管理员
	 * @param admin
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	public void addUser(Admin admin, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		if (StringUtils.isNullOrEmpty(admin.getUsername())
				|| StringUtils.isNullOrEmpty(admin.getPassword())) {
			out.write("false");
		}
		boolean ok = adminService.addUser(admin);
		if (ok) {
			out.write("ok");
		}else {
			out.write("false");
		}
	}
	/**
	 * 获取所有管理员信息
	 * @return
	 */
	@RequestMapping("/getUsers")
	@ResponseBody
	public List<Admin> getUsers(){
		List<Admin> users = adminService.getUsers();
		return users;
	}
	/**
	 * 获取管理员权限
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getAuthority")
	@ResponseBody
	public void getAuthority(Integer id,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String authority = adminService.getAuthority(id);
		out.write(authority);
	}
	/**
	 * 添加权限
	 * @param admin
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addAuthority")
	@ResponseBody
	public void addAuthority(Admin admin,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		if(StringUtils.isNullOrEmpty(admin.getId()+"") ||
				StringUtils.isNullOrEmpty(admin.getAuthority())){
			out.write("false");
		}
		boolean ok = adminService.addAuthority(admin);
		if(ok) {
			out.write("ok");
		}else {
			out.write("false");
		}
	}
	/**
	 * 删除管理员
	 * @throws IOException 
	 */
	@RequestMapping("/deleteAdmin")
	@ResponseBody
	public void deleteAdmin(Integer id,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		if(id == null) {
			out.write("false");
		}
		boolean ok = adminService.deleteAdmin(id);
		if(ok) {
			out.write("ok");
		}else {
			out.write("false");
		}
	}
}
