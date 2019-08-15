package com.hyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.DeliveryAddressMapper;
import com.hyb.dao.OrderStateMapper;
import com.hyb.dao.OrderitemMapper;
import com.hyb.dao.OrdersMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.EasyUITreeData;
import com.hyb.pojo.OrderState;
import com.hyb.pojo.OrderStateExample;
import com.hyb.pojo.Orderitem;
import com.hyb.pojo.OrderitemExample;
import com.hyb.pojo.Orders;
import com.hyb.pojo.OrdersExample;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.PageVo;
import com.hyb.pojo.Products;
import com.hyb.service.AdminOrderService;

/**
 * 后台订单服务类
 * @author Administrator
 *
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private OrderitemMapper orderitemMapper;
	@Autowired
	private ProductsMapper productMapper;
	@Autowired
	private DeliveryAddressMapper deliveryAddressMapper;
	@Autowired
	private OrderStateMapper orderStateMapper;
	/**
	 * 根据订单的状态分页获取订单信息
	 */
	@Override
	public PageBean<Orders> getOrderByState(PageVo vo) {
		
		//获取分页订单信息
		List<Orders> orders = orderMapper.getOrderByState(vo);
		
		for (Orders order : orders) {
			//获取订单的订单项信息
			OrderitemExample example = new OrderitemExample();
			example.createCriteria().andOidEqualTo(order.getOid());
			List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
			//添加订单项信息
			order.setOrderitems(orderitems);
			//获取订单项的商品
			for (Orderitem orderitem : orderitems) {
				Products product = productMapper.selectByPrimaryKey(orderitem.getPid());
				//添加商品信息
				orderitem.setProduct(product);
			
			}
			//获取订单的收货地址
			DeliveryAddress address = deliveryAddressMapper.selectByPrimaryKey(order.getDid());
			if(address != null){
				StringBuilder sb = new StringBuilder();
				sb.append("收货人姓名：  "+address.getDname()+"  ");
				sb.append("电话：  "+address.getDtelphone()+"  ");
				sb.append("地址：  "+address.getDaddress());
				order.setDid(sb.toString());
			}
		}
		
		//封装分页数据
		PageBean<Orders> page = new PageBean<Orders>();
		int cpage = (vo.getCurrPage()/vo.getPageSize())+1;
		page.setCurrPage(cpage);
		page.setList(orders);
		page.setPageSize(vo.getPageSize());
		//获取订单的总数
		long orderCount = orderMapper.getOrderCount(vo);
		page.setTotalCount(orderCount);
		
		return page;
	}

	/**
	 * 更新订单的状态
	 */
	@Override
	public boolean updateOrderState(Orders order) {
		
		OrdersExample example = new OrdersExample();
		example.createCriteria().andOidEqualTo(order.getOid());
		int result = orderMapper.updateByExampleSelective(order, example);
		
		if(result <= 0){
			return false;
		}
		
		return true;
	}

	/**
	 * 获取订单状态
	 */
	@Override
	public List<EasyUITreeData> getOrderStates() {
		
		OrderStateExample example = new OrderStateExample();
		List<OrderState> list = orderStateMapper.selectByExample(example);
		List<EasyUITreeData> treeDatas = new ArrayList<EasyUITreeData>();
		if(list != null && list.size()>0){
			for (OrderState orderState : list) {
				EasyUITreeData node = new EasyUITreeData();
				node.setId(orderState.getSid());
				node.setText(orderState.getSname());
				node.setState("open");
				treeDatas.add(node);
			}
		}
		return treeDatas;
	}

	/**
	 * 分页获取订单（简要信息）
	 */
	@Override
	public EasyUIDataGridResult getOrderList(PageVo vo) {
		
		List<Orders> orderList = orderMapper.getOrderList(vo);
		long orderCount = orderMapper.getAllOrderCount(vo);
		EasyUIDataGridResult dataGridResult = new EasyUIDataGridResult();
		dataGridResult.setRows(orderList);
		dataGridResult.setTotal(orderCount);
		
		return dataGridResult;
	}

	/**
	 * 根据订单号获取订单详情
	 * 
	 */
	@Override
	public Orders getOrderById(String oid) {
		
		Orders order = orderMapper.selectByPrimaryKey(oid);
		OrderitemExample example = new OrderitemExample();
		example.createCriteria().andOidEqualTo(order.getOid());
		List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
		order.setOrderitems(orderitems);
		for (Orderitem orderitem : orderitems) {
			Products product = productMapper.selectByPrimaryKey(orderitem.getPid());
			//添加商品信息
			orderitem.setProduct(product);
		}
		//获取订单的收货地址
		DeliveryAddress address = deliveryAddressMapper.selectByPrimaryKey(order.getDid());
		if(address !=null){
			StringBuilder sb = new StringBuilder();
			sb.append("收货人姓名：  "+address.getDname()+"  ");
			sb.append("电话：  "+address.getDtelphone()+"  ");
			sb.append("地址：  "+address.getDaddress());
			order.setDid(sb.toString());
		}
		
		return order;
	}
	
	
	@Override
	public PageBean<Orders> getOrderByState(Integer oid) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
