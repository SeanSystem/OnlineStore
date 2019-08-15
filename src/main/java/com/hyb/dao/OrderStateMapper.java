package com.hyb.dao;

import com.hyb.pojo.OrderState;
import com.hyb.pojo.OrderStateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStateMapper {
    int countByExample(OrderStateExample example);

    int deleteByExample(OrderStateExample example);

    int deleteByPrimaryKey(String sid);

    int insert(OrderState record);

    int insertSelective(OrderState record);

    List<OrderState> selectByExample(OrderStateExample example);

    OrderState selectByPrimaryKey(String sid);

    int updateByExampleSelective(@Param("record") OrderState record, @Param("example") OrderStateExample example);

    int updateByExample(@Param("record") OrderState record, @Param("example") OrderStateExample example);

    int updateByPrimaryKeySelective(OrderState record);

    int updateByPrimaryKey(OrderState record);
}