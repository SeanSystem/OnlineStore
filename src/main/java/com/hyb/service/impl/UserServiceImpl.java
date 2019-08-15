package com.hyb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.CartMapper;
import com.hyb.dao.UserMapper;
import com.hyb.pojo.Cart;
import com.hyb.pojo.User;
import com.hyb.pojo.UserExample;
import com.hyb.pojo.UserExample.Criteria;
import com.hyb.service.UserService;
import com.hyb.utils.MD5Utils;
import com.hyb.utils.UUIDUtils;

/**
 * 处理用户的业务类
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	/**
	 * 检测数据库中是否存在该用户名
	 */
	@Override
	public boolean checkUsername(String username) {
		
		//封装查询条件
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> list = userMapper.selectByExample(example);
		
		if(list!=null && list.size()>0){
			return true;
		}
		
		return false;
	}

	/**
	 * 添加新用户
	 */
	@Override
	public boolean addUser(User user) {
		
		//检测用户信息中的用户和密码的合法性
		String username = user.getUsername();
		String password = user.getPassword();
		if(username == null || username.trim().equals("")
				|| password == null || password.trim().equals("")){
			return false;
		}
		//生成用户ID
		String uid = UUIDUtils.getId();
		user.setUid(uid);
		
		//对用户密码进行MD5加密
		String md5Password = MD5Utils.md5(password);
		user.setPassword(md5Password);
		
		//新增用户
		int result1 = userMapper.insert(user);
		
		//初始化用户的购物车
		Cart cart = new Cart();
		cart.setUid(uid);
		cart.setTotalprice(0.0);
		cart.setTotalcount(0);
		int result2 = cartMapper.insert(cart);
		
		if(result1 == 1 && result2 == 1){
			
			return true;
		}
		
		return false;
	}

	@Override
	public User checkUser(User user) {
		
		//检查用户名和密码的有效性
		String username = user.getUsername();
		String password = user.getPassword();
		if(username == null || username.trim().equals("")
				|| password == null || password.trim().equals("")){
			
			return null;
		}
		//对用户密码加密
		password = MD5Utils.md5(password);

		//验证登录
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			User u = list.get(0);
			return u;
		}
		
		return null;
	}

	
}
