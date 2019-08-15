package com.hyb.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.OrderitemMapper;
import com.hyb.dao.OrdersMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.Cart;
import com.hyb.pojo.Cartitem;
import com.hyb.pojo.Orderitem;
import com.hyb.pojo.OrderitemExample;
import com.hyb.pojo.Orders;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.PageVo;
import com.hyb.pojo.Products;
import com.hyb.service.OrderService;
import com.hyb.utils.UUIDUtils;
/**
 * 处理订单服务
 * @author Sean
 *
 */
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrdersMapper orderMapper;
	@Autowired
	private OrderitemMapper orderitemMapper;
	@Autowired
	private ProductsMapper productMapper;
	
	/**
	 * 生成订单
	 */
	@Override
	public Orders makeOrder(Cart cart) {
		
		//生成订单
		Orders order = new Orders();
		String oid = UUIDUtils.getId();
		order.setOid(oid);
		order.setState(0); //设置订单状态 0表示未付款  1表示已付款  2表示已发货  3表示已完成
		order.setTotalprice(cart.getTotalprice());
		order.setUid(cart.getUid());
		order.setOrdertime(new Date());
		//保存订单
		orderMapper.insertSelective(order);
		//生成订单项
		Collection<Cartitem> items = cart.getItems();
		List<Orderitem> orderitems = order.getOrderitems();
		for (Cartitem cartitem : items) {
			//生成订单项
			Orderitem item = new Orderitem();
			item.setOid(oid);
			item.setCount(cartitem.getCount());
			String itemid = UUIDUtils.getId();
			item.setItemid(itemid);
			item.setPid(cartitem.getPid());
			item.setProduct(cartitem.getProduct());
			item.setSubtotal(cartitem.getSubtotal());
			//保存订单项
			orderitemMapper.insertSelective(item);
			//添加订单项
			orderitems.add(item);
			
		}
		
		return order;
	}

	/**
	 * 分页获取订单信息
	 */
	@Override
	public PageBean<Orders> getOrderByPage(PageVo vo) {
		
		//获取分页订单信息
		List<Orders> orders = orderMapper.getOrderByPage(vo);
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
	 * 根据订单的id获取订单信息
	 */
	@Override
	public Orders getOrderById(String oid) {
		//获取订单
		Orders order = orderMapper.selectByPrimaryKey(oid);
		//获取订单的订单项
		OrderitemExample example = new OrderitemExample();
		example.createCriteria().andOidEqualTo(order.getOid());
		List<Orderitem> orderitems = orderitemMapper.selectByExample(example);
		//获取订单项的商品
		for (Orderitem orderitem : orderitems) {
			Products product = productMapper.selectByPrimaryKey(orderitem.getPid());
			orderitem.setProduct(product);
		}
		//设置订单项
		order.setOrderitems(orderitems);
		return order;
	}

	/**
	 * 修改支付后的订单信息
	 */
	@Override
	public boolean payOrder(String oid,String did) {
		Orders order = orderMapper.selectByPrimaryKey(oid);
		if(order == null) {
			return false;
		}
		OrderitemExample example = new OrderitemExample();
		example.createCriteria().andOidEqualTo(oid);
		List<Orderitem> list = orderitemMapper.selectByExample(example);
		//修改商品的库存
		for(Orderitem item : list){
			Products product = productMapper.selectByPrimaryKey(item.getPid());
			product.setCount(product.getCount() - item.getCount());
			productMapper.updateByPrimaryKeySelective(product);
		}
		//补充支付后的订单信息
		order.setDid(did);
		order.setState(1);
		int update = orderMapper.updateByPrimaryKeySelective(order);
		return update > 0;
	}

	/**
	 * 修改订单的状态
	 */
	@Override
	public void updateOrderStat(String oid, int state) {
		//订单信息
		Orders order = new Orders();
		order.setOid(oid);
		order.setState(state);
		//修改
		orderMapper.updateByPrimaryKeySelective(order);
	}

	/**
	 * 确认收货
	 */
	@Override
	public boolean confirmOrder(String oid, int status) {
		Orders order = new Orders();
		order.setOid(oid);
		order.setState(status);
		int update = orderMapper.updateByPrimaryKeySelective(order);
		return update > 0;
	}

}
