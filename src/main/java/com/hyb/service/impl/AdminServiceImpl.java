package com.hyb.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.AdminMapper;
import com.hyb.pojo.Admin;
import com.hyb.pojo.AdminExample;
import com.hyb.pojo.AdminExample.Criteria;
import com.hyb.service.AdminService;
import com.hyb.utils.MD5Utils;
import com.mysql.jdbc.StringUtils;
/**
 * 处理后台管理员的业务类
 * @author Sean
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	/**
	 * 添加管理员账号
	 */
	@Override
	public boolean addUser(Admin admin) {
		String md5Password = MD5Utils.md5(admin.getPassword()).replace("-", "");
		admin.setPassword(md5Password);
		admin.setCreateTime(new Date());
		admin.setStatus(1);
		int insert = adminMapper.insert(admin);
		return insert > 0;
	}
	/**
	 * 获取所有管理员信息
	 */
	@Override
	public List<Admin> getUsers() {
		AdminExample example = new AdminExample();
		example.createCriteria().andStatusEqualTo(1);
		List<Admin> list = adminMapper.selectByExample(example);
		return list;
	}
	/**
	 * 获取管理员权限
	 */
	@Override
	public String getAuthority(Integer id) {
		Admin admin = adminMapper.selectByPrimaryKey(id);
		if(admin != null && !StringUtils.isNullOrEmpty(admin.getAuthority())){
			return admin.getAuthority();
		}
		return "";
	}
	/**
	 * 添加管理员权限
	 */
	@Override
	public boolean addAuthority(Admin admin) {
		int update = adminMapper.updateByPrimaryKeySelective(admin);
		return update > 0;
	}
	/**
	 * 管理员登录
	 */
	@Override
	public Admin login(Admin admin) {
		AdminExample example =  new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(admin.getUsername());
		String md5Password = MD5Utils.md5(admin.getPassword()).replace("-", "");
		criteria.andPasswordEqualTo(md5Password);
		List<Admin> list = adminMapper.selectByExample(example);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	/**
	 * 删除管理员
	 */
	@Override
	public boolean deleteAdmin(Integer id) {
		Admin record = new Admin();
		record.setId(id);
		record.setStatus(-1);
		int delete = adminMapper.updateByPrimaryKeySelective(record);
		return delete > 0;
	}

	
}
