package com.ztesoft.common.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 * 
 * 类说明: 分页信息的封装类，记录了当前页面信息和当前页的数据。
 * 
 * Copyright ? 2006 zte Co.Ltd. All rights reserved.
 * 
 * @author fjy
 * @version 1.0
 * @since 2006-3-7
 * @modified by fjy 2006-3-7
 */
public class PageModel<T> implements Serializable {
	// 总记录数
	@Expose
	private int totalCount;
	// 当前页索引
	@Expose
	private int pageIndex;
	// 总页数
	@Expose
	private int pageCount;
	// 每页的记录行数
	@Expose
	private int pageSize;
	// 当前页数据的对象列表
	@Expose
	private List<T> list;
	// 用于存放统计数据的LIST
	@Expose
	private List<T> sumInfoList;
	private Object dataEx;				// 用于存放扩展数据
	private List itemList;				// 存储二级页面列表	
	private Map<String, Object> other;	// 用于存储其他信息

	private int columns = 1;// 字段列数
	private String needPage = "T";// 是否分页 T分页，F不分页
	
	public Map<String, Object> getOther() {
		return other;
	}

	public void setOther(Map<String, Object> other) {
		this.other = other;
	}
	
	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
	
	public String getNeedPage() {
		return needPage;
	}

	public void setNeedPage(String needPage) {
		this.needPage = needPage;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * 初始化是空的list。
	 * 
	 * PageModel.java构建器
	 */
	public PageModel() {

		this.totalCount = 0;
		this.pageIndex = 1;
		this.pageCount = 1;
		this.pageSize = 10;
		this.list = new ArrayList();
		this.sumInfoList = new ArrayList();
		this.dataEx = null;

	}

	public Object getDataEx() {
		return dataEx;
	}

	public void setDataEx(Object dataEx) {
		this.dataEx = dataEx;
	}

	public List getSumInfoList() {
		return sumInfoList;
	}

	public void setSumInfoList(List sumInfoList) {
		this.sumInfoList = sumInfoList;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
