package com.hyb.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.Products;
import com.hyb.service.ProductService;
import com.hyb.utils.CookieUtils;

/**
 * 处理商品请求
 * @author Sean
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	@Value("${CATEGORY_PRODUCT_SIZE}")
	private int CATEGORY_PRODUCT_SIZE;
	@Value("${VISIT_SIZE}")
	private int VISIT_SIZE;
	@Value("${VISIT_HISTORY_MAXAGE}")
	private int VISIT_HISTORY_MAXAGE;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 分类分页获取商品
	 * @return
	 */
	@RequestMapping("/getProductByPage")
	public ModelAndView getProductByPage(int currPage,String cid){
		
		//获取数据
		PageBean<Products> pageBean = productService.getProductByPage(currPage, cid, CATEGORY_PRODUCT_SIZE);
		//封装数据返回
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bean",pageBean);
		modelAndView.addObject("cid",cid);
		modelAndView.setViewName("/jsp/product_list");
		return modelAndView;
		
	}
	
	/**
	 * 商品查询
	 * @param prductName
	 * @param price
	 * @param order
	 * @param currPage
	 * @return
	 */
	@RequestMapping("/searchProduct")
	public ModelAndView searchProduct(String productName,String price,String order,int currPage){
		PageBean<Products> pageBean = productService.searchProduct(productName, price, order, currPage, CATEGORY_PRODUCT_SIZE);
		//封装数据返回
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("bean",pageBean);
		modelAndView.addObject("productName",productName);
		modelAndView.addObject("price", price);
		modelAndView.addObject("order", order);
		modelAndView.setViewName("/jsp/product_search");
		return modelAndView;
	}
	
	/**
	 * 通过商品的id获取商品
	 * @return
	 */
	@RequestMapping("/getProductById")
	public ModelAndView getProductById(String pid,
			HttpServletRequest request,HttpServletResponse response){
		
		Products product = productService.getProductById(pid);
		//封装返回数据
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product",product);
		modelAndView.setViewName("/jsp/product_info");
		//将商品id存入浏览历史中
		addProductHistory(request, response, pid);
		
		return modelAndView;
		
	}
	
	
	/**
	 * 获取浏览记录的商品信息
	 * @param ids
	 * @return
	 */
	@RequestMapping("/getProductHistory")
	@ResponseBody
	public List<Products> getProductHistory(String ids){
		
		List<Products> list = productService.getProductByIds(ids);
		return list;
	}
	
	/**
	 * 清除商品的浏览记录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/clearProductHistory")
	public void clearProductHistory(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		Cookie[] cookies = request.getCookies();
		
		Cookie cookie = null;
		
		if(cookies !=null ){
			cookie = CookieUtils.getCookie("history", cookies);
		}
		
		if(cookie != null){
			
			CookieUtils.clearCookie(cookie, request, response);
			response.getWriter().write("ok");
		}
		
	}
	
	/**
	 * 将商品id添加到浏览历史中
	 * @param request
	 * @param response
	 * @param pid
	 */
	public void addProductHistory(HttpServletRequest request,HttpServletResponse response,String pid){
		
		//将该商品的信息加入存放浏览记录的cookie中,规定cookie的形式为：pid-pid-pid
				String ids = "";
				Cookie[] cookies = request.getCookies();
				Cookie cookie = CookieUtils.getCookie("history", cookies);
				if(cookie == null){
					ids = pid;
					
				}else{
					
					String value = cookie.getValue();
					//将cookie转成list集合
					String[] split = value.split("-");
					List<String> alist =  Arrays.asList(split);
					LinkedList<String> list = new LinkedList<String>(alist);
					//判断cookie中是否已经存在该商品
					if(list.contains(pid)){
						list.remove(pid);
					}else{
						
						if(list.size()>=VISIT_SIZE){
							list.removeLast();
						}
						
					}
					
					list.addFirst(pid);
					
					StringBuffer buff = new StringBuffer();
					for (String str : list) {
						buff.append(str+"-");
					}
					
					ids = buff.substring(0, buff.length()-1);
				}
				
				//将cookie写回
				cookie = new Cookie("history",ids);
				cookie.setPath(request.getContextPath()+"/");
				cookie.setMaxAge(VISIT_HISTORY_MAXAGE);
				response.addCookie(cookie);
	}
	
	
	
}
