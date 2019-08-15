package com.hyb.service;


import com.hyb.pojo.Cart;
import com.hyb.pojo.Orders;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.PageVo;

public interface OrderService {

	//生成订单
	Orders makeOrder(Cart cart);
	//分页获取订单
	PageBean<Orders> getOrderByPage(PageVo vo);
	//通过订单号获取订单信息
	Orders getOrderById(String oid);
	//订单支付
	boolean payOrder(String oid,String did);
	//修改订单的状态
	void updateOrderStat(String oid,int state);
	//确认收货
	boolean confirmOrder(String oid, int status);
}
