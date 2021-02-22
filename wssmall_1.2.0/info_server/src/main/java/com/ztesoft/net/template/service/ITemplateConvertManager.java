package com.ztesoft.net.template.service;

import java.util.Map;

import zte.params.template.resp.TemplateAccessConvertResp;
import zte.params.template.resp.TemplateOuterConvertResp;

import com.ztesoft.net.template.model.NodeModel;

public interface ITemplateConvertManager {
	/**
	 * 模板转入
	 * 根据业务数据、模板构建业务对象
	 * @param req
	 * @return
	 */
	public TemplateAccessConvertResp tplAccessConvert(Map<String, Object> busiMap, Map<String, NodeModel> tplMap);
	
	/**
	 * 模板转出
	 * 根据模板、实例ID输出报文
	 * @param req
	 * @return
	 */
	public TemplateOuterConvertResp tplOuterConvert(Map<String, NodeModel> tplMap, String inst_id);
	
}