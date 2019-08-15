package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hyb.pojo.Category;
import com.hyb.pojo.EasyUITreeData;
import com.hyb.service.AdminCategoryService;
/**
 * 处理后台商品分类管理请求
 * @author Sean
 *
 */
@Controller
//@RequestMapping("/admin/adminCategory")
@RequestMapping("/adminCategory")
public class AdminCategoryController {
	
	@Autowired
	private AdminCategoryService adminCategoryService;
	
	/**
	 * 添加商品分类
	 * @param category
	 * @throws IOException 
	 */
	@RequestMapping("/addCategory")
	public void addCategory(Category category,HttpServletResponse response) throws IOException{
		
		adminCategoryService.addCategory(category);
		PrintWriter out = response.getWriter();
		out.write("ok");
	}
	
	/**
	 * 修改商品分类名
	 * @throws IOException 
	 */
	@RequestMapping("/updateCategory")
	public void updateCategory(String id,String name,HttpServletResponse response) throws IOException{
		
		Category category = new Category();
		category.setCid(id);
		category.setCname(name);
		adminCategoryService.updateCategory(category);
		
		PrintWriter out = response.getWriter();
		out.write("ok");
	}
	
	/**
	 * 删除商品分类名
	 * @throws IOException 
	 */
	@RequestMapping("/deleteCategory")
	public void deleteCategory(String id,HttpServletResponse response) throws IOException{
		
		adminCategoryService.deleteCategory(id);

		PrintWriter out = response.getWriter();
		out.write("ok");
	}
	
	/**
	 * 获取商品分类信息
	 * @return
	 */
	@RequestMapping("/getCategorys")
	@ResponseBody
	public List<EasyUITreeData> getCategorys(){
		
		List<EasyUITreeData> list = adminCategoryService.getCategorys();
		
		return list;
	}
	
}
