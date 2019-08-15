package com.hyb.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hyb.pojo.User;
import com.hyb.service.UserService;
import com.hyb.utils.CookieUtils;

public class AutoLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		
		//获取请求路径
		String path = request.getRequestURL().toString();
		
		//判断session中是否包含用户信息
		User user = (User) request.getSession().getAttribute("user");
		
		//如果session中没有用户信息,即用户未登录
		if(user == null){
			
			//如果不是登录和注册地址，执行自动登录
			if(!path.contains("login") && !path.contains("register")){
				
				autoLogin(request);
			}
			
		}
		
		return true;
	}

	public void autoLogin(HttpServletRequest request){
		//根据名称获取cookie
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if(cookies!=null){
			cookie = CookieUtils.getCookie("autologin", cookies);
		}
		if (cookie != null) {
			
			//获取cookie中的用户名和密码
			String str = cookie.getValue();
			try {
				str = URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String username = str.split("-")[0];
			String password = str.split("-")[1];
			User u1 = new User();
			u1.setUsername(username);
			u1.setPassword(password);
			
			//调用UserService中的方法实习自动登陆
			User u = null;
			
			try {
				u = userService.checkUser(u1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (u != null) {
				request.getSession().setAttribute("user", u);	
			}

		}
	}
}
