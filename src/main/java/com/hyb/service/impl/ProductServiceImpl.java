package com.hyb.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyb.dao.CategoryMapper;
import com.hyb.dao.IndexProductMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.Category;
import com.hyb.pojo.IndexProduct;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.Products;
import com.hyb.pojo.ProductsExample;
import com.hyb.pojo.ProductsExample.Criteria;
import com.hyb.service.ProductService;
import com.mysql.jdbc.StringUtils;

/**
 * 处理商品的服务
 * 
 * @author Sean
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private IndexProductMapper indexProductMapper;

	/**
	 * 获取首页所需的最新商品
	 */
	@Override
	public List<IndexProduct> getNewestProduct(int num) {
		List<IndexProduct> products = indexProductMapper
				.selectNewestProduct(num);
		return products;
	}

	/**
	 * 获取首页所需的热门商品
	 */
	@Override
	public List<IndexProduct> getHotProduct(int num) {
		List<IndexProduct> hotProducts = indexProductMapper
				.selectHotestProduct(num);
		return hotProducts;
	}

	/**
	 * 分页分类获取商品
	 */
	@Override
	public PageBean<Products> getProductByPage(int currPage, String cid,
			int pageSize) {
		// 开启分页查询
		PageHelper.startPage(currPage, pageSize);
		// 封装查询条件
		ProductsExample example = new ProductsExample();
		Criteria criteria = example.createCriteria();
		criteria.andCidEqualTo(cid);
		criteria.andPflagEqualTo(1);
		// 执行查询
		List<Products> list = productMapper.selectByExampleWithBLOBs(example);
		// 返回结果
		if (list != null && list.size() > 0) {
			PageInfo<Products> pageInfo = new PageInfo<Products>(list);
			// 返回封装结果
			PageBean<Products> pageBean = new PageBean<Products>();
			pageBean.setCurrPage(currPage);
			pageBean.setList(list);
			pageBean.setPageSize(pageSize);
			pageBean.setTotalCount(pageInfo.getTotal());
			return pageBean;
		}

		return null;
	}

	/**
	 * 通过商品的id获取商品
	 */
	@Override
	public Products getProductById(String pid) {
		// 封装条件
		ProductsExample example = new ProductsExample();
		example.createCriteria().andPidEqualTo(pid);
		List<Products> list = productMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {

			Products product = list.get(0);
			// 获取商品的分类信息
			Category category = categoryMapper.selectByPrimaryKey(product
					.getCid());
			product.setCategory(category);
			return product;
		}

		return null;
	}

	/**
	 * 通过商品ids获取商品
	 */
	@Override
	public List<Products> getProductByIds(String ids) {

		// 获取商品id集合
		String[] split = ids.split("-");
		List<String> values = Arrays.asList(split);

		// 查询
		// List<Products> list = productMapper.selectByExample(example);
		List<Products> list = new ArrayList<Products>();
		for (String pid : values) {
			Products product = productMapper.selectByPrimaryKey(pid);
			list.add(product);
		}

		return list;
	}

	/**
	 * 商品搜索
	 */
	@Override
	public PageBean<Products> searchProduct(String productName, String price,
			String order, int currPage, int pageSize) {
		PageHelper.startPage(currPage, pageSize);
		ProductsExample example = new ProductsExample();
		Criteria criteria = example.createCriteria();
		// 设置价格区间
		if (price != null) {
			if (price.contains("-")) {
				String[] split = price.split("-");
				criteria.andShopPriceGreaterThanOrEqualTo(Double
						.parseDouble(split[0]));
				criteria.andShopPriceLessThanOrEqualTo(Double
						.parseDouble(split[1]));
			} else {
				criteria.andShopPriceGreaterThanOrEqualTo(Double
						.parseDouble(price));
			}
		}
		// 设置价格排序
		if (order == null || order.equals("up")) {
			example.setOrderByClause("shop_price ASC");
		} else {
			example.setOrderByClause("shop_price DESC");
		}

		// 设置商品名
		if (!StringUtils.isNullOrEmpty(productName)) {
			criteria.andPnameLike("%" + productName + "%");
		}

		// 执行查询
		List<Products> list = productMapper.selectByExampleWithBLOBs(example);
		// 返回结果
		if (list != null && list.size() > 0) {
			PageInfo<Products> pageInfo = new PageInfo<Products>(list);
			// 返回封装结果
			PageBean<Products> pageBean = new PageBean<Products>();
			pageBean.setCurrPage(currPage);
			pageBean.setList(list);
			pageBean.setPageSize(pageSize);
			pageBean.setTotalCount(pageInfo.getTotal());
			return pageBean;
		}
		return null;
	}

}
