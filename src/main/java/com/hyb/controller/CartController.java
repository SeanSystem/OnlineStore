package com.hyb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyb.pojo.Cart;
import com.hyb.pojo.Cartitem;
import com.hyb.pojo.Products;
import com.hyb.pojo.User;
import com.hyb.service.CartService;
import com.hyb.service.ProductService;
import com.hyb.utils.UUIDUtils;

/**
 * 处理购物车请求
 * 
 * @author Sean
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	/**
	 * 跳转到购物车界面
	 * 
	 * @return
	 */
	@RequestMapping("/cartUI")
	public String cartUI(HttpServletRequest request) {
		// 判断用户是否登陆
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "您还未登陆，请先登陆~~");
			return "/jsp/msg";
		}
		// 获取用户购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			getCart(request);
		}

		return "/jsp/cart";
	}

	/**
	 * 添加商品到购物车中
	 * 
	 * @param pid
	 * @param count
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addToCart")
	public String addToCart(String pid, int count, HttpServletRequest request,
			HttpServletResponse response) {

		// 判断用户是否登陆
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "您还未登陆，请先登陆~~");
			return "/jsp/msg";
		}

		// 获取购买的商品信息
		Products product = productService.getProductById(pid);
		if (count > product.getCount()) {
			request.setAttribute("msg","商品得库存不足，请减少商品购买数量！");
			return "/jsp/msg";
		}
		// 创建购物项
		Cartitem cartItem = new Cartitem(product, count);
		cartItem.setPid(pid);
		String cid = UUIDUtils.getId();
		cartItem.setCid(cid);
		cartItem.setUid(user.getUid());
		// 获取购物车
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		// 保存购物车信息
		cartService.saveCart(cart, pid);
		// 重定向到购物车界面
		return "redirect:cartUI";

	}

	/**
	 * 修改购物车中商品数量
	 * 
	 * @param pid
	 * @param count
	 * @throws IOException
	 */
	@RequestMapping("/updateProductCount")
	public void updateProductCount(String pid, int count,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 获取购物车
		Cart cart = getCart(request);
		cart.updateItem(pid, count);
		// 更新数据库数据
		cartService.saveCart(cart, pid);
		// 获取更新后的数据返回
		Double totalPrice = cart.getTotalprice();
		Double subtotal = cart.getMap().get(pid).getSubtotal();
		Integer totalcount = cart.getTotalcount();
		String result = "" + totalPrice + "," + subtotal + "," + totalcount;
		// 返回修改后的商品小计和购物车总金额
		response.getWriter().write(result);
	}

	/**
	 * 清除购物项
	 * 
	 * @param pid
	 */
	@RequestMapping("/removeCartitem")
	public String removeCartitem(String pid, HttpServletRequest request) {

		Cart cart = getCart(request);
		cart.removeFromCart(pid);
		// 更新数据库中的数据
		cartService.deleteCartitem(cart, pid);

		return "redirect:cartUI";
	}

	/**
	 * 清空购物车
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping("/clearCart")
	public String clearCart(String pid, HttpServletRequest request) {

		Cart cart = getCart(request);
		cart.clearCart();
		// 更新数据库中的数据
		cartService.clearCart(cart);
		return "redirect:cartUI";
	}

	/**
	 * 获取购物车中的商品数量
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/getCartNum")
	public void getCartNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 获取当前用户
		User user = (User) request.getSession().getAttribute("user");
		// 获取结果
		int cartNum = cartService.getCartNum(user);
		// 响应结果
		response.getWriter().write(cartNum + "");

	}

	public Cart getCart(HttpServletRequest request) {

		// 从session中获取购物车对象
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");

		// 如果cart为空
		if (cart == null) {

			// 查看用户的购物车中是否含有购物项 ，如果有返回购物车，并将购物车加入到session中
			cart = cartService.getCart(user);
			cart.setUid(user.getUid());
			request.getSession().setAttribute("cart", cart);
		}

		return cart;
	}

}
