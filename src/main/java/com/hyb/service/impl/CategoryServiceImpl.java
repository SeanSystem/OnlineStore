package com.hyb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.CategoryMapper;
import com.hyb.pojo.Category;
import com.hyb.pojo.CategoryExample;
import com.hyb.service.CategoryService;
/**
 * 处理商品分类信息的服务
 * @author Sean
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	/**
	 * 获取所有的商品分类信息
	 */
	@Override
	public List<Category> getCategorys() {
		
		CategoryExample example = new CategoryExample();
		List<Category> list = categoryMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}

}
