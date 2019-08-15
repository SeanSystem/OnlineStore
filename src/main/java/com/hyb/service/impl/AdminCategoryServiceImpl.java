package com.hyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.CategoryMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.Category;
import com.hyb.pojo.CategoryExample;
import com.hyb.pojo.EasyUITreeData;
import com.hyb.pojo.Products;
import com.hyb.pojo.ProductsExample;
import com.hyb.service.AdminCategoryService;
import com.hyb.utils.UUIDUtils;

/**
 * 处理商品的分类服务
 * @author Sean
 *
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private ProductsMapper productMapper;
	
	/**
	 * 添加商品分类
	 */
	@Override
	public void addCategory(Category category) {
		
		//设置分类的id
		String cid = UUIDUtils.getId();
		category.setCid(cid);
		categoryMapper.insert(category);
	}

	/**
	 * 获取商品分类信息
	 */
	@Override
	public List<EasyUITreeData> getCategorys() {
		
		CategoryExample example = new CategoryExample();
		List<Category> list = categoryMapper.selectByExample(example);
		List<EasyUITreeData> categorys = new ArrayList<EasyUITreeData>();
		if(list!=null && list.size()>0){
			for (Category category : list) {
				EasyUITreeData node = new EasyUITreeData();
				node.setId(category.getCid());
				node.setText(category.getCname());
				node.setState("open");
				categorys.add(node);
			}
		}
		return categorys;
	}

	/**
	 * 修改分类名
	 */
	@Override
	public void updateCategory(Category category) {
		
		categoryMapper.updateByPrimaryKeySelective(category);
	}

	/**
	 * 删除商品分类名
	 */
	@Override
	public void deleteCategory(String id) {
		
		//将该分类名下所有的商品分类置为空
		Products product = new Products();
		product.setCid("");
		ProductsExample example = new ProductsExample();
		example.createCriteria().andCidEqualTo(id);
		productMapper.updateByExampleSelective(product, example);
		//删除该分类
		categoryMapper.deleteByPrimaryKey(id);
		
	}

}
