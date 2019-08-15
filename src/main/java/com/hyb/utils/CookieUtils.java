package com.hyb.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 处理cookie的工具类
 * @author Sean
 *
 */
public class CookieUtils {
	
	/**
	 * 获取指定名称的cookie
	 * @param name cookie名称
	 * @param cookies 
	 * @return
	 */
	public static Cookie getCookie(String name,Cookie[] cookies){
		
		for (Cookie cookie : cookies) {
			
			if(cookie.getName().equals(name)){
				
				return cookie;
			}
		}
		return null;
	}

	
	/**
	 * 清除指定名称的cookie
	 * @param name cookie名称
	 * @param request 
	 * @param response
	 */
	public static void clearCookie(String name,HttpServletRequest request,HttpServletResponse response){
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = CookieUtils.getCookie(name, cookies);
		if(cookie!=null){
			cookie.setPath(request.getContextPath()+"/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 删除指定cookie
	 * @param cookie
	 * @param request
	 * @param response
	 */
	public static void clearCookie(Cookie cookie,HttpServletRequest request,HttpServletResponse response){
		
		if(cookie != null){
			cookie.setPath(request.getContextPath()+"/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);	
		}
	}
	
	/**
	 * 设置cookie
	 * @param cookie
	 * @param expiry
	 * @param request
	 * @param response
	 */
	public static void setCookie(Cookie cookie,int expiry,HttpServletRequest request,HttpServletResponse response){
		
		cookie.setPath(request.getContextPath()+"/");
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}
	
}
