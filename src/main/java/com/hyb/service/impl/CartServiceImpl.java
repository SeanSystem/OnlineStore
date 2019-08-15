package com.hyb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.CartMapper;
import com.hyb.dao.CartitemMapper;
import com.hyb.dao.ProductsMapper;
import com.hyb.pojo.Cart;
import com.hyb.pojo.Cartitem;
import com.hyb.pojo.CartitemExample;
import com.hyb.pojo.CartitemExample.Criteria;
import com.hyb.pojo.Products;
import com.hyb.pojo.User;
import com.hyb.service.CartService;

/**
 * 处理购物车的服务
 * @author Sean
 *
 */
@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private CartitemMapper cartitemMapper;
	@Autowired
	private ProductsMapper productMapper;
	
	/**
	 * 获取购物车
	 */
	@Override
	public Cart getCart(User user) {
		
		//获取购物车中的购物项
		CartitemExample example = new CartitemExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(user.getUid());
		List<Cartitem> cartItems = cartitemMapper.selectByExample(example);
		//获取购物车
		Cart cart = cartMapper.selectByPrimaryKey(user.getUid());
		//如果购物项不为空，将购物项加入到购物车中
		for (Cartitem cartitem : cartItems) {
			Map<String, Cartitem> map = cart.getMap();
			//获取购物项的商品
			Products product = productMapper.selectByPrimaryKey(cartitem.getPid());
			cartitem.setProduct(product);
			map.put(cartitem.getPid(), cartitem);
		}
		
		return cart;
	}

	@Override
	public void saveCart(Cart cart,String pid) {
		//更新购物车
		cartMapper.updateByPrimaryKeySelective(cart);
		//如果是添加了新商品，添加购物项,否则更新
		Cartitem cartitem = cart.getMap().get(pid);
		if(cart.isAdd()){
			cartitemMapper.insertSelective(cartitem);
		}else{
			cartitemMapper.updateByPrimaryKeySelective(cartitem);
		}
			
	}

	/**
	 * 删除购物项
	 */
	@Override
	public void deleteCartitem(Cart cart, String pid) {

		//更新购物车
		cartMapper.updateByPrimaryKeySelective(cart);
		//封装条件
		CartitemExample example = new CartitemExample();
		Criteria criteria = example.createCriteria();
		criteria.andPidEqualTo(pid);
		criteria.andUidEqualTo(cart.getUid());
		//删除购物项
		cartitemMapper.deleteByExample(example);
	}

	/**
	 * 清空购物车
	 */
	@Override
	public void clearCart(Cart cart) {

		//更新购物车
		cartMapper.updateByPrimaryKeySelective(cart);
		
		//清空购物车中的购物项
		CartitemExample example = new CartitemExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(cart.getUid());
		cartitemMapper.deleteByExample(example);
	}

	/**
	 * 获取购物车中购物项的数量
	 */
	@Override
	public int getCartNum(User user) {
		if(user!=null){
			//查询购物车
			Cart cart = cartMapper.selectByPrimaryKey(user.getUid());
			int count = cart.getTotalcount();
			return count;
		}else{
			
			return 0;
		}
		
	}

	
}
