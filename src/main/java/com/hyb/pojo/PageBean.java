package com.hyb.pojo;

import java.util.List;

public class PageBean<T> {

	private List<T> list;
	private int currPage;
	private int pageSize;
	private long totalCount;
	private int totalPage;
	
	public PageBean(){}
	
	public PageBean(List<T> list, int currPage, int pageSize, long totalCount) {
		super();
		this.list = list;
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return (int) Math.ceil(totalCount*1.0/pageSize);
	}
	
	
	
}
