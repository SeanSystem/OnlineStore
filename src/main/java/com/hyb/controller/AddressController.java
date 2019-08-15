package com.hyb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.User;
import com.hyb.service.AddressService;
/**
 * 处理收货地址的请求
 * @author Sean
 *
 */
@Controller
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping("/addAddress")
	@ResponseBody
	public DeliveryAddress addAddress(DeliveryAddress address,HttpServletRequest request){
		//获取当前用户
		User user = (User) request.getSession().getAttribute("user");
		DeliveryAddress ad = addressService.addDeliveryAddress(address, user);
		//以json字符串返回添加的地址信息
		return ad;
	}
	
}
