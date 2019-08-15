package com.hyb.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hyb.pojo.Category;
import com.hyb.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/getCategorys")
	@ResponseBody
	public List<Category> getCategorys(){
		
		List<Category> list = categoryService.getCategorys();
		if(list!=null && list.size()>0){
			
			return list;
		}
		return null;
	}
	
}
