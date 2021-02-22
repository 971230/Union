package com.ztesoft.mall.core.action.backend;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.framework.action.WWAction;


/**
 * BaseAction.java
 * @author cc
 *
 * 2013-9-28下午3:58:09
 */
public class BaseAction extends WWAction{
	protected static final String  INDEX = "index";
	protected static final String  INPUT = "input";
	protected static final String  RESULT = "result";
	protected static final String  TRUE = "true";
	protected static final String  FALSE = "false";
	protected Map<String,String> params = new HashMap<String,String>(0);
	protected String id ;
	protected String  ids;
	protected String ctx=getRequest().getContextPath();
	/**
	 * 执行返回消息对象
	 */
	protected MsgBox msg = new MsgBox();
	
	
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCtx() {
		return ctx;
	}

}
