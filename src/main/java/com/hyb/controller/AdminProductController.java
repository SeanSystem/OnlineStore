package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.Products;
import com.hyb.service.AdminProductService;

/**
 * 处理后台商品操作请求
 * @author Sean
 *
 */
@Controller
//@RequestMapping("/admin/adminProduct")
@RequestMapping("/adminProduct")
public class AdminProductController {

	@Autowired
	private AdminProductService adminProductService;
	
	/**
	 * 添加商品
	 * @param product
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/addProduct")
	public void addProduct(Products product,HttpServletResponse response) throws IOException {
		
		boolean isOk = adminProductService.addProduct(product);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}
	}
	
	/**
	 * 修改商品的信息
	 * @param product
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/updateProduct")
	public void updateProduct(Products product,HttpServletResponse response) throws IOException{
		
		boolean isOk = adminProductService.updateProduct(product);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}
	}
	
	/**
	 * 批量删除商品
	 * @param ids
	 * @throws IOException 
	 */
	@RequestMapping("/deleteProduct")
	public void deleteProduct(String ids,HttpServletResponse response) throws IOException{
		
		boolean isOk = adminProductService.deleteProduct(ids);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}	
	}
	
	/**
	 * 批量上架或下架商品
	 * @param ids
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/putOrdownProduct")
	public void putOrdownProduct(String ids,String operator,HttpServletResponse response) throws IOException{
		
		boolean isOk = adminProductService.putOrDownProduct(ids,operator);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}	
	}
	
	/**
	 * 分类分页查询商品
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/getProductList")
	@ResponseBody
	public EasyUIDataGridResult getProductByPage(int page, int rows,String categoryId){
		
		EasyUIDataGridResult dataGridResult = adminProductService.getProductByPage(page, rows, categoryId);
		
		return dataGridResult;
	}
	
}
