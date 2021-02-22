package com.ztesoft.net.template.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.ztesoft.lucence.AbsLucence;
import com.ztesoft.lucence.LucenceConfig;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;

 
public class LucenceAttrTableAction extends WWAction {

	 
	private static final long serialVersionUID = 1L;
	private String keyWord;
	private String keyName;
	private String type;
	private String id;
	private AbsLucence lucenAttrTableService;
	
	public String searchPage(){
		LucenceConfig config = new LucenceConfig();
		config.setIs_new(1);
		try {
			lucenAttrTableService.perform(config);
			json = "{'result':0,'message':'刷新归属字段成功！'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'刷新归属字段失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	public String search(){
		Map<String,String> params = new HashMap<String,String>();
		params.put(keyName, keyWord);
		//params.put("type", type);
		try {
			Page page = lucenAttrTableService.search(params);
			List list = page.getResult();
			json = JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			json = "[]";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public AbsLucence getLucenAttrTableService() {
		return lucenAttrTableService;
	}

	public void setLucenAttrTableService(AbsLucence lucenAttrTableService) {
		this.lucenAttrTableService = lucenAttrTableService;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
