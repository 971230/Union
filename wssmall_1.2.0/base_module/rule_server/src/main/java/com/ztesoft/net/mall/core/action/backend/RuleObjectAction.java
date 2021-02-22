package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import javax.annotation.Resource;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.RuleObjAttr;
import com.ztesoft.net.mall.core.service.IRuleConfigManager;
import com.ztesoft.net.mall.core.service.IRuleObjectManager;

public class RuleObjectAction extends WWAction{
	
	@Resource
	private IRuleConfigManager ruleConfigManager;
	
	private List<RuleObjAttr> objAttrList;
	private String obj_id;
	
	/**
	 * 查询象属性
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @return
	 */
	public String queryObjAttrs(){
		objAttrList = ruleConfigManager.qryRuleObjAttrByObjId(obj_id);
		return "query_obj_attr";
	}
	
	public String getObjPage(){
		
		return "rule_obj";
	}
	
	public String getDatePage(){
		
		return "date_page";
	}
	
	public String getConstPage(){
		
		return "const_page";
	}
	
	public String getTextPage(){
		
		return "text_page";
	}
	
	public String getSalesNumPage(){
		
		return "salesNum_page";
	}
	
	public String getGoodsPage(){
		
		this.webpage = ruleObjectManager.searchGoods(name, this.getPage(),this.getPageSize());

		return "goods_page";
	}
	
	public String getPartnerPage(){
		
		this.webpage = ruleObjectManager.searchPartner(partner_name, this.getPage(),this.getPageSize());
		
		return "partner_page";
	}
	
	public String getSalesAmountPage(){
		
		return "salesAmount_page";
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPartner_name() {
		return partner_name;
	}

	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
	}

	public IRuleObjectManager getRuleObjectManager() {
		return ruleObjectManager;
	}
	public void setRuleObjectManager(IRuleObjectManager ruleObjectManager) {
		this.ruleObjectManager = ruleObjectManager;
	}
	
	private IRuleObjectManager ruleObjectManager;
	
	private String name;
	
	private String partner_name;

	public List<RuleObjAttr> getObjAttrList() {
		return objAttrList;
	}

	public void setObjAttrList(List<RuleObjAttr> objAttrList) {
		this.objAttrList = objAttrList;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	
}
