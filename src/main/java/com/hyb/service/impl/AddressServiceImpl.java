package com.hyb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyb.dao.DeliveryAddressMapper;
import com.hyb.pojo.DeliveryAddress;
import com.hyb.pojo.DeliveryAddressExample;
import com.hyb.pojo.User;
import com.hyb.service.AddressService;
import com.hyb.utils.UUIDUtils;
/**
 * 处理收货地址的服务
 * @author Sean
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private DeliveryAddressMapper addressMapper;
	
	/**
	 * 添加收货地址
	 */
	@Override
	public DeliveryAddress addDeliveryAddress(DeliveryAddress address,User user) {
		//设置地址的id
		String id = UUIDUtils.getId();
		address.setId(id);
		//设置地址的所有者用户id
		address.setUid(user.getUid());
		addressMapper.insertSelective(address);
		//返回收货地址信息
		return address;
	}

	/**
	 * 获取用户的收货地址
	 */
	@Override
	public List<DeliveryAddress> getDeliveryAddress(User user) {
		//封装查询条件
		DeliveryAddressExample example = new DeliveryAddressExample();
		example.createCriteria().andUidEqualTo(user.getUid());
		List<DeliveryAddress> list = addressMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			
			return list;
		}
		
		return null;
	}

}
