package com.hyb.pojo;

public class Cartitem {
    private String cid;

    private String pid;

    private Integer count;

    private Double subtotal;

    private String uid;
    
	private Products product; //商品
	
	private User user;  //所属用户

	public Cartitem(){}
	
	public Cartitem(Products product,int count){
		
		this.product = product;
		this.count = count;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubtotal() {
        return this.count*(this.product.getShopPrice());
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}