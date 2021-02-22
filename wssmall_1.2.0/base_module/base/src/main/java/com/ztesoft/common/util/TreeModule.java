package com.ztesoft.common.util;

import java.io.Serializable;
import java.util.ArrayList;

public class TreeModule implements Serializable{
	private Object data ;
	private ArrayList children ;
	public TreeModule(){
		this.children = new ArrayList();
	}
	public ArrayList getChildren() {
		return children;
	}
	public void setChildren(ArrayList children) {
		this.children = children;
	}
	@Override
	public String toString(){
		return this.data.toString();
	}
	public Object getData() {
		return data;
	}
	public void setData(Object mmMenuVO) {
		this.data = mmMenuVO;
	}
}
