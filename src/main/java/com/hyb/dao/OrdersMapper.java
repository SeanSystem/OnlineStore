package com.hyb.dao;

import com.hyb.pojo.Orders;
import com.hyb.pojo.OrdersExample;
import com.hyb.pojo.PageVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {
    int countByExample(OrdersExample example);

    int deleteByExample(OrdersExample example);

    int deleteByPrimaryKey(String oid);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByExample(OrdersExample example);

    Orders selectByPrimaryKey(String oid);

    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    /**
     * 分页获取订单
     * @return
     */
    List<Orders> getOrderByPage(PageVo vo);
    
    /**
     * 通过订单的状态分页获取订单
     * @param vo
     * @return
     */
    List<Orders> getOrderByState(PageVo vo);
    
    /**
     * 通过订单的状态分页获取订单(简要信息)
     * @param vo
     * @return
     */
    List<Orders> getOrderList(PageVo vo);
    
    /**
     * 获取订单总数
     */
    long getOrderCount(PageVo vo);
    
    /**
     * 获取总订单数
     * @return
     */
    long getAllOrderCount(PageVo vo);
}