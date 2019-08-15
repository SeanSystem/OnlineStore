package com.hyb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hyb.pojo.IndexProduct;
import com.hyb.service.ProductService;

/* 
 * 处理和商城首页有关的请求
 * @author Sean
 *
 */

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private ProductService productService;
	@Value("${INDEX_NEWEST_PRODUCT_SIZE}")
	private int INDEX_NEWEST_PRODUCT_SIZE;
	@Value("${INDEX_HOT_PRODUCT_SIZE}")
	private int INDEX_HOT_PRODUCT_SIZE;
	/**
	 * 跳转到商城首页
	 * @return
	 */
	@RequestMapping("/indexUI")
	public ModelAndView index(){
		
		//获热门商品
		List<IndexProduct> hotProduct = productService.getHotProduct(INDEX_HOT_PRODUCT_SIZE);
		//获取最新商品
		List<IndexProduct> newestProduct = productService.getNewestProduct(INDEX_NEWEST_PRODUCT_SIZE);
	
		//包装返回的数据
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("nList",newestProduct);
		modelAndView.addObject("hList",hotProduct);
		modelAndView.setViewName("/jsp/index");
		
		return modelAndView;
	}
	
}
