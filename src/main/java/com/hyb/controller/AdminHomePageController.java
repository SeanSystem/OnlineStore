package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.IndexProduct;
import com.hyb.service.AdminProductService;
/**
 * 处理后台管理商城首页请求
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminHomePageController {
	
	@Autowired
	private AdminProductService adminProductService;
	/**
	 * 首页商品添加
	 * @param pid
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/addProduct")
	@ResponseBody
	public void addProduct(IndexProduct product,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		if(product.getPid() == null){
			out.write("false");
		}
		boolean ok = adminProductService.addIndexProduct(product);
		if(ok) {
			out.write("ok");
		}else {
			out.write("false");
		}
	}
	
	/**
	 * 分页获取首页商品信息
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getProduct")
	@ResponseBody
	public EasyUIDataGridResult getProduct(int page,int rows){
		return adminProductService.getIndexProduct(page, rows);
	}
	
	/**
	 * 批量上架或下架首页商品
	 * @param ids
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/putOrdownProduct")
	public void putOrdownProduct(String ids,String operator,HttpServletResponse response) throws IOException{
		boolean isOk = adminProductService.putOrDownIndexProduct(ids, operator);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}	
	}
	
}
