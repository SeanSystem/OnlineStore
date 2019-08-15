package com.hyb.service;

import java.util.List;

import com.hyb.pojo.Admin;

/**
 * 处理后台管理员业务接口
 * @author Sean
 *
 */
public interface AdminService {
	/**
	 * 添加管理员账号
	 * @param admin
	 * @return
	 */
	boolean addUser(Admin admin);
	/**
	 * 获取所有管理员信息
	 * @return
	 */
	List<Admin> getUsers();
	/**
	 * 根据id获取管理员权限
	 * @param id
	 * @return
	 */
	String getAuthority(Integer id);
	/**
	 * 添加管理员权限
	 * @param admin
	 * @return
	 */
	boolean addAuthority(Admin admin);
	/**
	 * 管理员登录
	 * @param admin
	 * @return
	 */
	Admin login(Admin admin);
	/**
	 * 删除管理员
	 * @param id
	 * @return
	 */
	boolean deleteAdmin(Integer id);
}
