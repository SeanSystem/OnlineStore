package com.hyb.service;

import com.hyb.pojo.Cart;
import com.hyb.pojo.User;

public interface CartService {

	//获取购物车
	Cart getCart(User user);
	//保存/更新购物车
	void saveCart(Cart cart,String pid);
	//删除购物项
	void deleteCartitem(Cart cart,String pid);
	//清空购物车
	void clearCart(Cart cart);
	//获取购物车中商品的数量
	int getCartNum(User user);
	
}
