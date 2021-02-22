package com.zte.cbss.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = 5788448411192393614L;
	
	private int page = 1;
	
	private int total;
	
	private int pageSize;
	
	private int maxPage;
	
	private List<T> items;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPage() {
		if(pageSize == 0)
			return 1;
		maxPage = (total + pageSize - 1) / pageSize;
		if (maxPage <= 0) {
			maxPage = 1;
		}
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public List<T> getItems() {
		if(items == null) {
			items = new ArrayList<T>();
		}
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return "Pagination [page=" + page + ", total=" + total + ", pageSize="
				+ pageSize + ", maxPage=" + maxPage + ", items=" + items + "]";
	}

}
