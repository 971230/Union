package com.ztesoft.net.mall.core.action.backend;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import vo.Rule;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.ServiceObjOffer;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.ISysRuleManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

/**
 * 商品规则统一入口
 * @author hu.yi
 * @date 2014.02.19
 */
public class SysRuleAction extends WWAction{

	
	private Rule rule;
	private ISysRuleManager sysRuleManager;
	private List<Map<String, Object>> ruleTypeList;
	private String rule_id;
	private String service_id;
	private IDcPublicInfoManager dcPublicInfoManager;
	private ServiceObjOffer serviceOffer;
	private String is_select;	//标志是进入规则选择页还是列表页的标志位
	private List<Rule> ruleList;	//已关联的规则列表
	


	/**
	 * 规则列表
	 * @return
	 */
	public String listRule(){
		
		if(rule == null){
			rule = new Rule();
		}
		qryRuleType();
		this.webpage = this.sysRuleManager.listRule(rule, this.getPage(), this.getPageSize());
		
		return "sys_rule_list";
	}
	
	
	/**
	 * 进入规则添加页面
	 * @return
	 */
	public String addRule(){
		qryRuleType();
		return "sys_rule_add";
	}
	
	/**
	 * 删除规则
	 * @return
	 */
	public String delRule(){
		try {
			this.rule.setDisabled("1");
			this.sysRuleManager.saveRule(this.rule);
			
			this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 进入规则修改页面
	 * @return
	 */
	public String editRule(){
		this.rule = this.sysRuleManager.qryRule(rule_id);
		qryRuleType();
		return "sys_rule_add";
	}
	
	
	/**
	 * 保存规则
	 * @return
	 */
	public String save(){
		try {
			int count  = 0;
			
			if(StringUtils.isEmpty(rule.getRule_id())){
				count = this.sysRuleManager.qryRuleCount(this.rule);
			}
			
			if(count > 0){
				this.json = "{result:0,message:'此类型下的规则编码已存在，请重新填写规则编码！'}";
			}else{
				this.sysRuleManager.saveRule(this.rule);
				this.json = "{result:1,message:'操作成功'}";
			}
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 查询规则类型
	 */
	private void qryRuleType(){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    this.ruleTypeList = dcPublicCache.getList("2381");
	}
	
	
	
	
	/**
	 * 服务列表
	 * @return
	 */
	public String listServer(){
		
		if(serviceOffer == null){
			serviceOffer = new ServiceObjOffer();
		}
		
		this.webpage = this.sysRuleManager.listServer(serviceOffer,this.getPage(),this.getPageSize());
		
		return "server_offer_list";
	}
	
	
	/**
	 * 新增
	 * @return
	 */
	public String addServer(){
		return "server_offer_add";
	}
	
	
	/**
	 * 编辑
	 * @return
	 */
	public String editServer(){
		this.serviceOffer = this.sysRuleManager.qryServer(service_id);
		return "server_offer_add";
	}
	
	
	/**
	 * 保存
	 * @return
	 */
	public String saveServer(){
		try {
			Integer count = this.sysRuleManager.qryServerCount(this.serviceOffer);
			if(count > 0){
				this.json = "{result:0,message:'此编码已存在，请重新输入您的服务编码'}";
			}else{
				this.sysRuleManager.saveServer(this.serviceOffer);
				this.json = "{result:1,message:'操作成功'}";
			}
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 删除业务
	 * @return
	 */
	public String delServer(){
		try {
			this.serviceOffer.setDisplay_flag("1");
			this.sysRuleManager.saveServer(serviceOffer);
			
			this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 跳转到关联规则界面
	 * @return
	 */
	public String linkRule(){
		
		this.serviceOffer = this.sysRuleManager.qryServer(service_id);
		
		this.ruleList = this.sysRuleManager.qryRuleList(service_id);
		
		
		return "link_rule_dialog";
	}
	
	/**
	 * 保存关联规则
	 * @return
	 */
	public String saveLinkRule(){
		
		try {
			this.sysRuleManager.saveLinkRule(rule_id,service_id);
			
			this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	
	
	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public ISysRuleManager getSysRuleManager() {
		return sysRuleManager;
	}

	public void setSysRuleManager(ISysRuleManager sysRuleManager) {
		this.sysRuleManager = sysRuleManager;
	}

	public List<Map<String, Object>> getRuleTypeList() {
		return ruleTypeList;
	}

	public void setRuleTypeList(List<Map<String, Object>> ruleTypeList) {
		this.ruleTypeList = ruleTypeList;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	
	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public ServiceObjOffer getServiceOffer() {
		return serviceOffer;
	}

	public void setServiceOffer(ServiceObjOffer serviceOffer) {
		this.serviceOffer = serviceOffer;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getIs_select() {
		return is_select;
	}

	public void setIs_select(String is_select) {
		this.is_select = is_select;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}
}
