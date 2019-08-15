package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hyb.pojo.User;
import com.hyb.service.UserService;
import com.hyb.utils.CookieUtils;

/**
 * 处理与用户有关的请求
 * 
 * @author Sean
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 跳转到注册界面
	 * 
	 * @return
	 */
	@RequestMapping("/registerUI")
	public String toRegisterUI() {

		return "/jsp/register";
	}

	/**
	 * 跳转到登录界面
	 * 
	 * @return
	 */
	@RequestMapping("/loginUI")
	public String toLoginUI() {
		return "/jsp/login";
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@RequestMapping("/register")
	public ModelAndView register(User user) {

		boolean result = userService.addUser(user);
		ModelAndView modelAndView = new ModelAndView();

		if (result == true) {
			modelAndView.addObject("msg", "恭喜您注册成功，快去登录吧~~");
		} else {
			modelAndView.addObject("msg", "注册失败！用户信息中存在不合法的数据，请重新注册！！");
		}
		modelAndView.setViewName("/jsp/msg");

		return modelAndView;
	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/checkCode")
	public void checkCode(String randomStr, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 获取session中的验证码
		String code = (String) request.getSession().getAttribute("code");

		// 响应比较结果
		PrintWriter out = response.getWriter();
		if (code != null && code.equalsIgnoreCase(randomStr)) {
			out.write("true");
		} else {
			out.write("false");
		}
	}

	/**
	 * 检测数据库中是否存在该用户
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/checkUsername")
	public void checkUsername(String username, HttpServletResponse response)
			throws IOException {

		boolean isExe = userService.checkUsername(username);
		PrintWriter out = response.getWriter();
		if (isExe) {
			out.write("false");
		} else {
			out.write("true");
		}

	}

	/**
	 * 用户登录验证
	 * 
	 * @param user
	 * @param autologin
	 * @param remeber
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void login(User user, boolean autologin, boolean remeber,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 登录验证
		User u = userService.checkUser(user);
		PrintWriter out = response.getWriter();
		// 如果登录成功
		if (u != null) {
			// 如果用户登录勾选了记住用户名
			if (remeber) {
				Cookie cookie = new Cookie("username", URLEncoder.encode(
						u.getUsername(), "utf-8"));
				CookieUtils.setCookie(cookie, 3600 * 24, request, response);
			}
			// 如果用户勾选了自动登录
			if (autologin) {
				Cookie cookie2 = new Cookie("autologin", URLEncoder.encode(
						user.getUsername() + "-" + user.getPassword(), "utf-8"));
				CookieUtils.setCookie(cookie2, 3600 * 24, request, response);
			} else {
				CookieUtils.clearCookie("autologin", request, response);
			}
			// 将用户信息放入session中
			request.getSession().setAttribute("user", u);
			out.write("true");
		} else {
			out.write("false");
		}

	}

	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request,
			HttpServletResponse response) {

		// 清除session中的用户
		request.getSession().removeAttribute("user");
		// 清除用户的自动登录cookie
		CookieUtils.clearCookie("autologin", request, response);
		// 重定向到登录界面
		return "redirect:/user/loginUI";
	}
}
