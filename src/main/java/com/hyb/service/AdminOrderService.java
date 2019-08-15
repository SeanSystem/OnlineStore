package com.hyb.service;

import java.util.List;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.EasyUITreeData;
import com.hyb.pojo.Orders;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.PageVo;

public interface AdminOrderService {

	/**
	 * 根据订单详情
	 * @param oid
	 * @return
	 */
	public PageBean<Orders> getOrderByState(Integer oid);
	
	/**
	 * 修改订单的状态
	 * @param order
	 * 
	 */
	public boolean updateOrderState(Orders order);
	
	/**
	 * 获取所有的订单状态
	 * @return
	 */
	public List<EasyUITreeData> getOrderStates();
	
	/**
	 * 根据订单状态分页获取要订单（简要信息）
	 * @param vo
	 * @return
	 */
	public EasyUIDataGridResult getOrderList(PageVo vo);
	
	/**
	 * 根据订单号获取订单详情
	 * @param oid
	 * @return
	 */
	public Orders getOrderById(String oid);

	PageBean<Orders> getOrderByState(PageVo vo);

}
