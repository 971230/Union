package com.ztesoft.net.mall.core.plugin.search;


import java.io.Serializable;

/**
 * 选器实体
 * @author kingapex
 *
 */
public class SearchSelector implements Serializable{
	
	private String name;
	private String url;
	private boolean isSelected;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
