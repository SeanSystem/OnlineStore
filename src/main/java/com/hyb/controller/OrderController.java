package com.hyb.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hyb.pojo.Cart;
import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.Orders;
import com.hyb.pojo.PageBean;
import com.hyb.pojo.PageVo;
import com.hyb.pojo.User;
import com.hyb.service.AddressService;
import com.hyb.service.CartService;
import com.hyb.service.OrderService;

/**
 * 处理订单请求
 * 
 * @author Sean
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	@Value("${ORDER_LIST_SIZE}")
	private int ORDER_LIST_SIZE;
	@Value("${ORDER_COMPLETE}")
	private int ORDER_COMPLETE;
	@Autowired
	private AddressService addressService;

	/**
	 * 生成订单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/makeOrder")
	public ModelAndView makeOrder(HttpServletRequest request) {
		//获取当前用户
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView modelAndView = new ModelAndView();
		if(user == null){
			modelAndView.addObject("msg","您还未登陆,请先登陆~~");
			modelAndView.setViewName("/jsp/msg");
			return modelAndView;
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 生成订单
		Orders order = orderService.makeOrder(cart);
		// 清空购物车
		cart.clearCart();
		cartService.clearCart(cart);
		//获取收货地址
		List<DeliveryAddress> ads = addressService.getDeliveryAddress(user);
		//封装响应数据
		modelAndView.addObject("order", order);
		modelAndView.addObject("ads",ads);
		modelAndView.setViewName("/jsp/order_info");

		return modelAndView;
	}

	/**
	 * 获取所有的订单信息
	 * 
	 * @param currPage
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrderByPage")
	public ModelAndView getOrderByPage(Integer currPage,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		// 获取当前用户信息
		User user = (User) request.getSession().getAttribute("user");
		if(user ==null){
			modelAndView.addObject("msg","您还未登陆,请先登陆~~");
			modelAndView.setViewName("/jsp/msg");
			return modelAndView;
		}
		if (currPage == null) {
			currPage = 1;
		}
		// 设值分页查询条件
		PageVo vo = new PageVo();
		vo.setCurrPage((currPage - 1) * ORDER_LIST_SIZE);
		vo.setPageSize(ORDER_LIST_SIZE);
		
		vo.setUser(user);
		PageBean<Orders> page = orderService.getOrderByPage(vo);
		// 封装响应数据
		modelAndView.addObject("pb", page);
		modelAndView.setViewName("/jsp/order_list");

		return modelAndView;
	}

	/**
	 * 通过订单id获取订单
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping("/getOrderById")
	public ModelAndView getOrderById(String oid, HttpServletRequest request) {

		// 获取订单
		Orders order = orderService.getOrderById(oid);
		// 获取用户的收货地址信息
		User user = (User) request.getSession().getAttribute("user");
		List<DeliveryAddress> ads = addressService.getDeliveryAddress(user);
		// 封装响数据
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("order", order);
		modelAndView.addObject("ads", ads);
		modelAndView.setViewName("/jsp/order_info");

		return modelAndView;
	}
	/**
	 * 跳过第三方支付 支付
	 * @param oid
	 * @param did
	 * @return
	 */
	@RequestMapping("/pay")
	public String payOrder(String oid, String did,Double totalPrice,HttpServletRequest request){
		// 修改订单的状态
		boolean payed = orderService.payOrder(oid, did);
		if(payed){
			request.setAttribute("msg", "您的订单号为:" + oid + ",金额为:"
					+totalPrice  + "已经支付成功,等待发货~~");

		}else {
			request.setAttribute("msg", "支付失败，请稍后再试！");
		}
		return "/jsp/msg";
	}
	
	/**
	 * 确认收货
	 * @param oid
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmOrder")
	public String confirmOrder(String oid, HttpServletRequest request){
		boolean ok = orderService.confirmOrder(oid, ORDER_COMPLETE);
		if(!ok){
			request.setAttribute("msg","确认收货失败，请稍后再试！");
			return "/jsp/msg";
		}
		return "redirect:/order/getOrderByPage";
	}
	/**
	 * 支付订单
	 * 
	 * @param oid
	 * @param did
	 * @param pd_FrpId
	 * @throws Exception
	 */
	/*@RequestMapping("/pay")
	public void pay(String oid, String did, String pd_FrpId,
			HttpServletResponse response) throws Exception {

		// 修改订单的状态
		orderService.payOrder(oid, did);

		// 组织发送支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString(
				"p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01"; //订单金额
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString(
				"responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		// 发送给第三方
		StringBuffer sb = new StringBuffer(
				"https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		response.sendRedirect(sb.toString());
	}
*/
	/**
	 * 支付回调方法
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/callback")
	public String callback(PayResultData data,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 身份校验 --- 判断是不是支付公司通知你
		
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(data.getHmac(), data.getP1_MerId(), data.getR0_Cmd(),
				data.getR1_Code(), data.getR2_TrxId(),data.getR3_Amt(),data.getR4_Cur(),data.getR5_Pid(),data.getR6_Order(),data.getR7_Uid(),
				data.getR8_MP(),data.getR9_BType(),keyValue);
		if (isValid) {
			// 响应数据有效
			if (data.getR9_BType().equals("1")) {
				// 浏览器重定向
				//System.out.println("111");
				request.setAttribute("msg", "您的订单号为:" + data.getR6_Order() + ",金额为:"
						+ data.getR3_Amt() + "已经支付成功,等待发货~~");

			} else if (data.getR9_BType().equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}

			// 修改订单状态 
			orderService.updateOrderStat(data.getR6_Order(),1); 

		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}

		return "/jsp/msg";
	}
*/
}
