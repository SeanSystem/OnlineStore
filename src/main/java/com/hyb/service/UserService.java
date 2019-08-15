package com.hyb.service;

import com.hyb.pojo.User;

public interface UserService {
	
	//检测用户名是否已经存在
	boolean checkUsername(String username);
	//新增用户
	boolean addUser(User user);
	//用户登录验证
	User checkUser(User user);
}
