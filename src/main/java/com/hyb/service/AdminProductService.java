package com.hyb.service;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.IndexProduct;
import com.hyb.pojo.Products;

public interface AdminProductService {

	//添加商品
	boolean addProduct(Products product);
	//分页分类查询商品
	EasyUIDataGridResult getProductByPage(int page,int rows,String categoryId);
	//修改商品的信息
	boolean updateProduct(Products product);
	//批量删除商品
	boolean deleteProduct(String ids);
	//批量上架商品
	boolean putOrDownProduct(String ids,String operator);
	//添加首页商品
	boolean addIndexProduct(IndexProduct product);
	//分页获取首页商品
	EasyUIDataGridResult getIndexProduct(int currPage,int pageSize);
	//上下架首页商品
	boolean putOrDownIndexProduct(String ids,String operator);
}
