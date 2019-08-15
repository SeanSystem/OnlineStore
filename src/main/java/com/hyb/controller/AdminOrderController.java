package com.hyb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hyb.pojo.EasyUIDataGridResult;
import com.hyb.pojo.EasyUITreeData;
import com.hyb.pojo.Orders;
import com.hyb.pojo.PageVo;
import com.hyb.service.AdminOrderService;
/**
 * 处理后台订单请求
 * @author Sean
 *
 */
@Controller
//@RequestMapping("/admin/adminOrder")
@RequestMapping("/adminOrder")
public class AdminOrderController {
	
	@Autowired
	private AdminOrderService adminOrderService;
	@Value("${ORDER_LIST_SIZE}")
	private int ORDER_LIST_SIZE;
	@Value("${ORDER_WAIT_RECEIVE}")
	private int ORDER_WAIT_RECEIVE;
	@Value("${ORDER_WAIT_DELIVER}")
	private int ORDER_WAIT_DELIVER;
	
	
	/**
	 * 根据订单状态分页获取订单信息
	 * @param state
	 * @param currPage
	 * @return
	 */
	@RequestMapping("/orderInfo/{oid}")
	public ModelAndView getOrderById(@PathVariable String oid){
			
		Orders order = adminOrderService.getOrderById(oid);
		// 封装响应数据
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("order", order);
		modelAndView.setViewName("/admin/order-info");
		
		return modelAndView;
	}
	
	/**
	 * 分页获取订单信息
	 * @param page
	 * @param rows
	 * @param orderState
	 * @return
	 */
	@RequestMapping("/getOrderList")
	@ResponseBody
	public EasyUIDataGridResult getOrderList(int page, int rows,int orderState){
		// 设值分页查询条件
		PageVo vo = new PageVo();
		vo.setCurrPage(page-1);
		vo.setPageSize(rows);
		vo.setState(orderState);
		EasyUIDataGridResult dataGridResult = adminOrderService.getOrderList(vo);
		
		return dataGridResult;
	}
	/**
	 * 确认发货
	 * @param oid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/sendOrder")
	public void sendOrder(String oid,HttpServletResponse response) throws IOException{
		
		Orders order = new Orders();
		order.setOid(oid);
		order.setState(ORDER_WAIT_RECEIVE);
		//更新订单状态
		boolean isOk = adminOrderService.updateOrderState(order);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.write("ok");
		}else{
			out.write("error");
		}
	}
	
	/**
	 * 获取所有的订单状态
	 * @return
	 */
	@RequestMapping("/getOrderStates")
	@ResponseBody
	public List<EasyUITreeData> getOrderStates(){
		
		List<EasyUITreeData> orderStates = adminOrderService.getOrderStates();
		return orderStates;
	}
	
	
	
}
