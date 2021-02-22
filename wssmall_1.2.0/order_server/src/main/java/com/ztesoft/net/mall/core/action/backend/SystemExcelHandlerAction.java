package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import net.sf.json.JSONArray;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.ISystemExcelHandlerManager;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.TempConfig;


/**
 * excel可扩展导入导出处理类
 * @author hu.yi
 * @date 2013.9.26
 */
public class SystemExcelHandlerAction extends WWAction{

	private String temp_id;				//模板配置表id
	private String attr_spec_data;		//属性扩展数据，jsonArray格式的string
	private String temp_source_from;	//模板来源，标志淘宝京东等商户
	private String temp_inst_id;		//模板实例id，即商品或订单等id
	private String attr_spec_id;		//扩展属性id，也是商品或订单等id
	
	private ISystemExcelHandlerManager systemExcelHandlerManager;
	
	
	/**
	 * 保存模板
	 * @return
	 */
	public String saveTemp(){
		
		TempConfig tempConfig = systemExcelHandlerManager.getTempConfigById(temp_id);
		
		try {
			systemExcelHandlerManager.saveTemp(tempConfig,attr_spec_data,temp_source_from,temp_inst_id);
			this.json = "{result:1,message:模板保存成功}";
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"模板保存失败：" + e.getMessage() + "\"}";
		}
		
		
		return WWAction.JSON_MESSAGE;
	}


	/**
	 * 查询模板配置所有数据
	 * @return
	 */
	public String listTempConf(){
		
		List<TempConfig> list = systemExcelHandlerManager.getTempConfig();
		this.webpage = new Page();
		this.webpage.setParam(0, list.size(), list.size(), list);
		
		return "list_temp_config";
	}
	
	
	/**
	 * 查询扩展属性
	 * @return
	 */
	public String getAttrDefInfo(){
		
		List<AttrDef> list = systemExcelHandlerManager.getAttrById(attr_spec_id);
		JSONArray json = JSONArray.fromObject(list);
		this.json = "{attr_def_list:"+json+"}";
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	public String getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}

	public String getAttr_spec_data() {
		return attr_spec_data;
	}

	public void setAttr_spec_data(String attr_spec_data) {
		this.attr_spec_data = attr_spec_data;
	}

	public String getTemp_source_from() {
		return temp_source_from;
	}

	public void setTemp_source_from(String temp_source_from) {
		this.temp_source_from = temp_source_from;
	}

	public String getTemp_inst_id() {
		return temp_inst_id;
	}

	public void setTemp_inst_id(String temp_inst_id) {
		this.temp_inst_id = temp_inst_id;
	}

	public String getAttr_spec_id() {
		return attr_spec_id;
	}

	public void setAttr_spec_id(String attr_spec_id) {
		this.attr_spec_id = attr_spec_id;
	}

	

	public ISystemExcelHandlerManager getSystemExcelHandlerManager() {
		return systemExcelHandlerManager;
	}

	public void setSystemExcelHandlerManager(
			ISystemExcelHandlerManager systemExcelHandlerManager) {
		this.systemExcelHandlerManager = systemExcelHandlerManager;
	}
	
	
}