package com.ztesoft.net.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import zte.net.ecsord.common.CommonDataFactory;

import com.powerise.ibss.framework.Const;
import com.tangosol.coherence.component.net.extend.message.Request;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.service.IGonghaoBindManager;
import com.ztesoft.net.service.IOrderGuijiManager;

import java.util.List;

public class gonghaoBindAction extends WWAction{
	private Map<String,String> params = new HashMap<String,String>();
	
	private String listFormActionVal;
	
	private IGonghaoBindManager gonghaoBindManager;

	private String order_gonghao;
	
	private String ess_emp_id;
	
	private String province;
	
	private String city;
	
	private String source_from;
	
	private String username;
	
	private String username_search;
	
	private String realname;

	private String phone_num;
		
	public String getUsername_search() {
		return username_search;
	}

	public void setUsername_search(String username_search) {
		this.username_search = username_search;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getEss_emp_id() {
		return ess_emp_id;
	}

	public void setEss_emp_id(String ess_emp_id) {
		this.ess_emp_id = ess_emp_id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOrder_gonghao() {
		return order_gonghao;
	}

	public void setOrder_gonghao(String order_gonghao) {
		this.order_gonghao = order_gonghao;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}
	
	public IGonghaoBindManager getGonghaoBindManager() {
		return gonghaoBindManager;
	}

	public void setGonghaoBindManager(IGonghaoBindManager gonghaoBindManager) {
		this.gonghaoBindManager = gonghaoBindManager;
	}


	public String searchAdminUser(){
		this.webpage = this.gonghaoBindManager.searchAdminUser(params, getPage(), getPageSize());
		return "adminuser_list";
	}
	
	public String unbinding_gonghao(){
		String username = this.getRequest().getParameter("username");
		Map param = new HashMap();
		param.put("username", username);
		String userid = this.gonghaoBindManager.searchUserId(param);
		String city = this.getRequest().getParameter("city");
		params.put("order_gonghao", userid);
		params.put("city", city);
		gonghaoBindManager.unbinding_gonghao(params);
		this.getRequest().setAttribute("userid", userid);	
		this.getRequest().setAttribute("username", username);
		this.webpage = this.gonghaoBindManager.searchOperator_Rel(param, this.getPage(), this.getPageSize());
		this.getRequest().setAttribute("changeNumToString", this.city_Json_String());	
		String city_json_string = this.city_Json_String();
		this.getRequest().setAttribute("changeNumToString", city_json_string);
		return "es_operator_rel_ext_list";
	}
	
	public String city_Json_String(){
		CommonDataFactory adf = new CommonDataFactory();
		StringBuilder city_json = new StringBuilder();
		city_json.append("{");
		List city = (List) adf.getDictRelationListData("city");
		for(int i=0; i<city.size(); i++){
			Map city_Map = (Map) city.get(i);
			city_json.append("\""+city_Map.get("other_field_value")+"\":\""+city_Map.get("other_field_value_desc")+"\",");
		}
		String city_json_string = city_json.toString().substring(0, city_json.toString().length()-1);
		city_json_string += "}";		
		return city_json_string;
	}
	
	public String selectProvince_City(){
		this.json = this.city_Json_String();
		return this.JSON_MESSAGE;

	}
	
	public String searchOperator_Rel(){
		username = this.getRequest().getParameter("username");
		params.put("username", username);
		String userid = this.gonghaoBindManager.searchUserId(params);
		this.getRequest().setAttribute("userid", userid);	
		this.getRequest().setAttribute("username", username);	
		this.webpage = this.gonghaoBindManager.searchOperator_Rel(params, this.getPage(), this.getPageSize());
		
		this.getRequest().setAttribute("changeNumToString", this.city_Json_String());
		
		String city_json_string = this.city_Json_String();
		this.getRequest().setAttribute("changeNumToString", city_json_string);
		
		return "es_operator_rel_ext_list";
	}
	
	public String do_binding(){
		String order_gonghao = (String) params.get("order_gonghao");
		
		if(this.gonghaoBindManager.validate_order_gonghao_city(params)){
			this.json = "{ \"result\" : \"0\" , \"message\" : \"工号已绑定\" }";
		}else{
			this.gonghaoBindManager.do_binding(params);	
			this.json = "{ \"result\" : \"1\" , \"message\" : \"绑定成功\" }";
		}
			
		return this.JSON_MESSAGE;
	}
	
	public String searchUser(){
		params.put("username_search", username_search);
		params.put("realname", realname);
		params.put("phone_num", phone_num);	
		this.webpage = this.gonghaoBindManager.searchUser(params, this.getPage(), this.getPageSize());
		return "adminuser_list";
		
	}
}
