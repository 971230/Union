package com.ztesoft.net.mall.core.action.backend;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.service.IPaymentManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付配置action
 * @author kingapex
 *2010-4-13下午05:58:35
 */
public class PayCfgAction extends WWAction {
	private IPaymentManager paymentManager ;
	private List list;
	private List pluginList;
	private String paymentId;
	private String pluginId;
	private String[] id; //删除用
	private String name; 
	private String type;
	private String biref;
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		list  = this.paymentManager.list();
		return "list";
	}
	
	
	/**
	 * 到添加页 
	 * @return
	 */
	public String add(){	
		this.pluginList = this.paymentManager.listAvailablePlugins();
		return Action.INPUT;
	}
	
	public String getPluginHtml(){
		try{
			this.json = this.paymentManager.getPluginInstallHtml(pluginId, paymentId);
		}catch(RuntimeException e){
			this.json = e.getMessage();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 到修改页面
	 * @return
	 */
	public String edit(){
		this.pluginList = this.paymentManager.listAvailablePlugins();
		PayCfg cfg  = this.paymentManager.get(paymentId);
		this.name= cfg.getName();
		this.type= cfg.getType();
		this.biref= cfg.getBiref();
		return Action.INPUT;
	}
	
	
	/**
	 * 保存添加
	 * @return
	 */
	public String saveAdd(){
		try{
			HttpServletRequest  request = this.getRequest();
			Enumeration<String> names = request.getParameterNames();
			Map<String,String> params = new HashMap<String, String>();
			while(names.hasMoreElements()){
				String name= names.nextElement();
				
				if("name".equals(name)) continue;
				if("type".equals(name)) continue;
				if("biref".equals(name)) continue;
				if("paymentId".equals(name)) continue;
				if("submit".equals(name)) continue;
				String value  = request.getParameter(name);
				params.put(name, value);
			}
			
			this.paymentManager.add(name, type, biref, params);
			this.msgs.add("支付方式添加成功");
			this.urls.put("支付方式列表", "payCfg!list.do");
		}catch(RuntimeException e){
			this.msgs.add(e.getMessage());
		} 		
		return WWAction.MESSAGE;
	}
	
	
	public String save(){
		
		if(paymentId==null || "".equals(paymentId)){
			return this.saveAdd();
		}else{
			return this.saveEdit();
		}
		
	}
	
	
	/**
	 * 保存修改 
	 * @return
	 */
	public String saveEdit(){
		try{
			HttpServletRequest  request = this.getRequest();
			Enumeration<String> names = request.getParameterNames();
			Map<String,String> params = new HashMap<String, String>();
			while(names.hasMoreElements()){
				String name= names.nextElement();
				
				if("name".equals(name)) continue;
				if("type".equals(name)) continue;
				if("biref".equals(name)) continue;
				if("paymentId".equals(name)) continue;
				if("submit".equals(name)) continue;
				String value  = request.getParameter(name);
				params.put(name, value);
			}
			
			this.paymentManager.edit(paymentId,name, biref, params);
			this.msgs.add("支付方式修改成功");
			this.urls.put("支付方式列表", "payCfg!list.do");
		}catch(RuntimeException e){
			this.msgs.add(e.getMessage());
		} 		
		return WWAction.MESSAGE;
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		try{
			this.paymentManager.delete(id);
			this.json="{result:0,message:'支付方式删除成功'}";
		}catch(RuntimeException e){
			this.json="{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}
 
	public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBiref() {
		return biref;
	}

	public void setBiref(String biref) {
		this.biref = biref;
	}


	public List getPluginList() {
		return pluginList;
	}


	public void setPluginList(List pluginList) {
		this.pluginList = pluginList;
	}


	public String getPluginId() {
		return pluginId;
	}


	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	
	
	
}
