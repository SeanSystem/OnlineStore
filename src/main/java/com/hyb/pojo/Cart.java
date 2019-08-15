package com.hyb.pojo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	
    private String uid;
    
    private User user;

    private Integer totalcount;

    private Double totalprice;
    
    private boolean isAdd; //标志是否是添加购物项
    
    //存放购物项的map集合
  	private Map<String,Cartitem> map = new LinkedHashMap<String,Cartitem>();

  	/**
	 * 获取所有的购物项
	 * @return
	 */
	public Collection<Cartitem> getItems(){
		Collection<Cartitem> values = map.values();
		return values;
	}
	
	/**
	 * 添加到购物车
	 * @param item
	 */
	public void add2Cart(Cartitem item){
		
		//获取购买的商品id
		String pid = item.getPid();
		
		//判断购物车中是否已经存在
		if(map.containsKey(pid)){
			//购物车中存在则获取原来商品的购买数量加上现在的购买数量
			Cartitem oItem = map.get(pid);
			oItem.setCount(oItem.getCount()+item.getCount());	
			this.isAdd = false;
		}else{
			//不存在
			map.put(pid, item);
			this.isAdd = true;
		}
		
		//修改购物中的总金额
		this.totalprice+=item.getSubtotal();
		//修改购物车中商品的总数量
		this.totalcount+=item.getCount();
	}
	
	/**
	 * 更改购物车中商品的数量
	 * @param pid 商品编号
	 */
	public void updateItem(String pid,int count) {
		
		//获取购物项
		Cartitem cartItem = map.get(pid);
		//减去原来的商品小计
		this.totalprice = this.totalprice-cartItem.getSubtotal();
		this.totalcount = this.totalcount-cartItem.getCount();
		//设置新的商品数量
		cartItem.setCount(count);
		//加上现在的商品小计
		this.totalprice += cartItem.getSubtotal();
		this.totalcount += cartItem.getCount();
		//设置为更新购物车数据操作
		this.isAdd = false;
	}
	
	/**
	 * 从购物车中移除购物项
	 * @param item
	 */
	public void removeFromCart(String pid){
		//通过商品的id移除map中的购物项
		Cartitem oitem = map.remove(pid);
		
		//修改总金额
		this.totalprice-=oitem.getSubtotal();
		this.totalcount-=oitem.getCount();
		
	}
	
	/**
	 * 清空购物车
	 */
	public void clearCart(){
		
		//清空map集合
		map.clear();
		
		//修改总金额
		this.totalprice = 0.0;
		this.totalcount = 0;
	}
	
	
    public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public Map<String, Cartitem> getMap() {
		return map;
	}

	public void setMap(Map<String, Cartitem> map) {
		this.map = map;
	}

	public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}