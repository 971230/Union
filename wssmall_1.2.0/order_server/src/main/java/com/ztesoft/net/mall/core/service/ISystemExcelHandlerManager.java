package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.TempConfig;
import com.ztesoft.net.model.TempInst;


/**
 * 扩展属性导入导出处理类
 * @author hu.yi
 * @date 2013.9.26
 */
public interface ISystemExcelHandlerManager {
	
	
	/****************************** 模板相关业务 start ******************************************/
	
	/**
	 * 根据id获取扩展属性实体list
	 * @param attr_spec_id
	 * @return
	 */
	public List<AttrDef> getAttrById(String attr_spec_id);
	
	/**
	 * 根据id获取模板实例list
	 * @param temp_inst_id
	 * @return
	 */
	public List<TempInst> getTempById(String temp_inst_id);
	
	/**
	 * 列出模板配置的所有模板
	 * @return
	 */
	public List<TempConfig> getTempConfig();
	
	/**
	 * 根据id获取模板配置
	 * @param temp_id
	 * @return
	 */
	public TempConfig getTempConfigById(String temp_id);
	
	/**
	 * 保存模板数据
	 * @param tempConfig
	 * @param attr_spec_data
	 * @param temp_source_from
	 * @param temp_inst_id
	 */
	public void saveTemp(TempConfig tempConfig,String attr_spec_data,String temp_source_from,String temp_inst_id);
	
	/**
	 * 获取模板实例
	 * @param temp_inst_id
	 * @param source_from
	 * @return
	 */
	public TempInst getTempInstByArgs(String temp_inst_id,String source_from);
	
	/****************************** 模板相关业务 end ******************************************/
	
	
	
	
	
	
	/****************************** 入库相关业务 start ******************************************/
	/**
	 * 导入订单
	 * @param inList
	 * @param orderType 订单类型
	 * @param flowType	流程类型
	 * @return BatchResult
	 */
	public BatchResult importOrder(List<Map<String, Object>> inList,List<Map<String, Object>> prefixList,String orderType,String flowType);
	
	
	/**
	 * 扩展属性插入 单独抽出方法
	 * @param prefixList
	 */
//	public void insertPrefixEle(List<Map<String, Object>> prefixList);
	
	
	
	/****************************** 入库相关业务 end ******************************************/
	
}
