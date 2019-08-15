package com.hyb.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyb.dao.IndexProductMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.IndexProduct;
import com.hyb.pojo.IndexProductExample;
import com.hyb.pojo.Products;
import com.hyb.pojo.ProductsExample;
import com.hyb.pojo.ProductsExample.Criteria;
import com.hyb.service.AdminProductService;
import com.hyb.utils.UUIDUtils;

/**
 * 处理后台商品的服务
 * @author Sean
 *
 */
@Service
public class AdminProductServiceImpl implements AdminProductService {
	
	@Autowired
	private ProductsMapper productMapper;
	@Autowired
	private IndexProductMapper indexProductMapper;
	/**
	 * 添加商品
	 */
	@Override
	public boolean addProduct(Products product) {
		
		//设置商品的id
		String pid = UUIDUtils.getId();
		product.setPid(pid);
		//设置商品的状态 1正常 0下架
		product.setPflag(1);
		//设置商品的上架时间
		product.setPdate(new Date());
		//添加商品
		int num = productMapper.insertSelective(product);
		if(num == 1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 分页分类查询商品
	 */
	@Override
	public EasyUIDataGridResult getProductByPage(int page, int rows,
			String categoryId) {
		//设置分类
		PageHelper.startPage(page, rows);
		//封装查询条件
		ProductsExample example = new ProductsExample();
		Criteria criteria = example.createCriteria();
		criteria.andCidEqualTo(categoryId);
		//执行查询 查询中包含大文本的数据
		List<Products> list = productMapper.selectByExampleWithBLOBs(example);
		
		//封装返回对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		PageInfo<Products> pageInfo = new PageInfo<Products>(list);
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 修改商品的信息
	 */
	@Override
	public boolean updateProduct(Products product) {
		
		int result = productMapper.updateByPrimaryKeySelective(product);
		if(result == 1){
			return true;
		}else{
			return false;
		}	
	}

	/**
	 * 批量删除商品
	 */
	@Override
	public boolean deleteProduct(String ids) {
		
		//获取商品的id
		String[] split = ids.split(",");
		List<String> values = Arrays.asList(split);
		//封装条件
		ProductsExample example = new ProductsExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidIn(values);
		//批量删除
		int result = productMapper.deleteByExample(example);
		
		if(result>=1){
			return true;
		}else{
			return false;

		}
	}

	/**
	 * 批量上传或下架商品
	 */
	@Override
	public boolean putOrDownProduct(String ids,String operator) {
		
		//获取商品的id
		String[] split = ids.split(",");
		List<String> values = Arrays.asList(split);
		
		Products product = new Products();
		//判断是上架还是下架商品的操作
		if(operator.equals("put")){
			product.setPflag(1);
		}else if(operator.equals("down")){
			product.setPflag(0);
		}
		//设置条件
		ProductsExample example = new ProductsExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidIn(values);
		//执行操作
		int result = productMapper.updateByExampleSelective(product, example);
		if(result>=1){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 添加首页商品
	 */
	@Override
	public boolean addIndexProduct(IndexProduct product) {
		Products pro = productMapper.selectByPrimaryKey(product.getPid());
		if(pro == null){
			return false;
		}
		product.setCreateTime(new Date());
		product.setImage(pro.getImage());
		product.setPid(pro.getPid());
		product.setPname(pro.getPname());
		product.setPrice(pro.getShopPrice());
		product.setStatus(1);
		int insert = indexProductMapper.insert(product);
		return insert > 0;
	}

	/**
	 *分页获取首页商品信息
	 */
	@Override
	public EasyUIDataGridResult getIndexProduct(int currPage, int pageSize) {
		PageHelper.startPage(currPage, pageSize);
		IndexProductExample example = new IndexProductExample();
		List<IndexProduct> list = indexProductMapper.selectByExample(example);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		if(list != null && list.size() > 0){
			PageInfo<IndexProduct> info = new PageInfo<IndexProduct>(list);
			result.setRows(list);
			result.setTotal(info.getTotal());
		}
		return result;
	}
	/**
	 * 批量上下架首页商品
	 */
	@Override
	public boolean putOrDownIndexProduct(String ids, String operator) {
		//获取商品的id
		String[] split = ids.split(",");
		List<String> values = Arrays.asList(split);
		
		IndexProduct product = new IndexProduct();
		//判断是上架还是下架商品的操作
		if(operator.equals("put")){
			product.setStatus(1);
		}else if(operator.equals("down")){
			product.setStatus(0);
		}
		//设置条件
		IndexProductExample example = new IndexProductExample();
		example.createCriteria().andPidIn(values);
		//执行操作
		int result = indexProductMapper.updateByExampleSelective(product, example);
		return result > 0;
	}
}
