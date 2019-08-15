package com.hyb.service;

import java.util.List;

import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.User;

public interface AddressService {

	//添加收货地址
	DeliveryAddress addDeliveryAddress(DeliveryAddress address,User user);
	//获取用户的收货地址
	List<DeliveryAddress> getDeliveryAddress(User user);
}
