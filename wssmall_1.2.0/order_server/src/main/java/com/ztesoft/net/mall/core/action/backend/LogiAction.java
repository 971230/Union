package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.ILogiManager;

public class LogiAction extends WWAction {
	
	private ILogiManager logiManager;
	private String name;
	private String cid;
	private String id;
	private String order;
	private Logi logi;
	
	public String add_logi(){
		return "add_logi";
	}
	
	public String edit_logi(){
		this.logi = this.logiManager.getLogiById(cid);
		return "edit_logi";
	}
	
	public String list_logi(){
		this.webpage = this.logiManager.pageLogi(order, this.getPage(), this.getPageSize());
		return "list_logi";
	}
	
	public String delete(){
		try {
			this.logiManager.delete(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	public String saveAdd(){
		logiManager.saveAdd(name);
		this.msgs.add("添加成功");
		this.urls.put("物流公司列表","logi!list_logi.do");
		return WWAction.MESSAGE;
	}
	public String saveEdit(){
		this.logiManager.saveEdit(cid, name);
		this.msgs.add("修改成功");
		this.urls.put("物流公司列表","logi!list_logi.do");
		return WWAction.MESSAGE;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	public ILogiManager getLogiManager() {
		return logiManager;
	}

	public void setLogiManager(ILogiManager logiManager) {
		this.logiManager = logiManager;
	}

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Logi getLogi() {
		return logi;
	}

	public void setLogi(Logi logi) {
		this.logi = logi;
	}

 
	
	
}
