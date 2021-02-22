package com.ztesoft.net.mall.core.workflow.util;

import java.util.ArrayList;
import java.util.List;

public class ModVO {
	private String tableName  ;//表名[必须]
	private Object obj   ;//变更对象[必须]
	
	
	
	private String action ;//A M D
	private List<String> changedFields ;//变更字段,仅对修改起作用
	
	
	public void addField(String field){
		if(changedFields  == null )
			 changedFields = new ArrayList<String>() ;
		this.changedFields.add(field) ;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getChangedFields() {
		return changedFields;
	}
	public void setChangedFields(List<String> changedFields) {
		this.changedFields = changedFields;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
