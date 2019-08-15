package com.hyb.service;

import java.util.List;

import com.hyb.pojo.Category;
import com.hyb.pojo.EasyUITreeData;

public interface AdminCategoryService {

	//新增商品分类
	void addCategory(Category category);
	//获取商品分类信息
	List<EasyUITreeData> getCategorys();
	//修改商品分类名
	void updateCategory(Category category);
	//删除商品分类名
	void deleteCategory(String id);
	
}
