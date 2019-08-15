package com.hyb.dao;

import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.DeliveryAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliveryAddressMapper {
    int countByExample(DeliveryAddressExample example);

    int deleteByExample(DeliveryAddressExample example);

    int deleteByPrimaryKey(String id);

    int insert(DeliveryAddress record);

    int insertSelective(DeliveryAddress record);

    List<DeliveryAddress> selectByExample(DeliveryAddressExample example);

    DeliveryAddress selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DeliveryAddress record, @Param("example") DeliveryAddressExample example);

    int updateByExample(@Param("record") DeliveryAddress record, @Param("example") DeliveryAddressExample example);

    int updateByPrimaryKeySelective(DeliveryAddress record);

    int updateByPrimaryKey(DeliveryAddress record);
}