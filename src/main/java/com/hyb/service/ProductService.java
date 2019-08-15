package com.hyb.service;

import java.util.List;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.IndexProduct;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.Products;

public interface ProductService {

	// 获取首页显示的最新商品
	List<IndexProduct> getNewestProduct(int num);

	// 获取首页显示的最新商品
	List<IndexProduct> getHotProduct(int num);

	// 获取分类分页商品
	PageBean<Products> getProductByPage(int currPage, String cid, int pageSize);

	// 通过商品的id获取商品
	Products getProductById(String pid);

	// 通过商品的ids获取商品
	List<Products> getProductByIds(String ids);

	//搜索商品
	PageBean<Products> searchProduct(String productName, String price,
			String order, int currPage, int pageSize);
}
