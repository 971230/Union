package com.ztesoft.mall.core.action.backend;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.plugin.field.GoodsField;
import com.ztesoft.net.mall.core.plugin.field.GoodsFieldPluginBundle;
import com.ztesoft.net.mall.core.service.IGoodsFieldManager;

import java.util.List;

public class GoodsFieldAction extends WWAction {
	private IGoodsFieldManager goodsFieldManager;
	private GoodsFieldPluginBundle goodsFieldPluginBundle;
	
	
	private String typeid;
	private GoodsField goodsField;
	private List fieldPluginList;
	private String fieldid;
	private boolean isEdit;
	private Integer[] ids;
	private Integer[] sorts;

	private List<GoodsField> fieldList;
	
	public String list(){
		this.fieldList =this.goodsFieldManager.list(typeid);
		return "list";
	}
	
	public String add() {
		this.isEdit = false;
		fieldPluginList = this.goodsFieldPluginBundle.getPlugins();
		return Action.INPUT;
	}

	public String edit() {
		this.isEdit = true;
		goodsField = this.goodsFieldManager.get(fieldid);
		fieldPluginList = this.goodsFieldPluginBundle.getPlugins();		
		return Action.INPUT;
	}
	
	

	public String saveAdd() {
		try{
 
			
			fieldid  = this.goodsFieldManager.add(goodsField);
			this.json ="{result:1,fieldid:"+fieldid+",message:'字段添加成功'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e);
			this.json="{result:0,message:'字段添加出错'}";
			
		}
		return WWAction.JSON_MESSAGE;
	}

	public String saveEdit() {
		try{
			 this.goodsFieldManager.edit(goodsField);
			this.json ="{result:1,message:'字段修改成功'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e);
			this.json="{result:0,message:'字段修改出错'}";
			
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String delete(){
		try{
			goodsFieldManager.delete(fieldid);
			this.json ="{result:1,message:'字段删除成功'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e);
			this.json="{result:0,message:'字段删除出错'}";
			
		}
		return WWAction.JSON_MESSAGE;
	}	

	
	public String disInputHtml(){
		fieldList= this.goodsFieldManager.list(typeid);
		for(GoodsField field:fieldList){
			String html =this.goodsFieldPluginBundle.onDisplay(field, null);
			field.setInputHtml(html);
		}
		return "input_html";
	}
	
 
	
	
	public IGoodsFieldManager getGoodsFieldManager() {
		return goodsFieldManager;
	}

	public void setGoodsFieldManager(IGoodsFieldManager goodsFieldManager) {
		this.goodsFieldManager = goodsFieldManager;
	}

 


	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public GoodsField getGoodsField() {
		return goodsField;
	}

	public void setGoodsField(GoodsField goodsField) {
		this.goodsField = goodsField;
	}

	public List getFieldPluginList() {
		return fieldPluginList;
	}

	public void setFieldPluginList(List fieldPluginList) {
		this.fieldPluginList = fieldPluginList;
	}

	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Integer[] getSorts() {
		return sorts;
	}

	public void setSorts(Integer[] sorts) {
		this.sorts = sorts;
	}

	public GoodsFieldPluginBundle getGoodsFieldPluginBundle() {
		return goodsFieldPluginBundle;
	}

	public void setGoodsFieldPluginBundle(
			GoodsFieldPluginBundle goodsFieldPluginBundle) {
		this.goodsFieldPluginBundle = goodsFieldPluginBundle;
	}

	public List<GoodsField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<GoodsField> fieldList) {
		this.fieldList = fieldList;
	}

}
