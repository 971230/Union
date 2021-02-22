package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import params.order.resp.AcceptResp;
import rule.params.accept.req.AcceptRuleReq;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.TempInst;
import com.ztesoft.net.model.TplAttr;



public interface ITplInstManager {
	
	public List<TplAttr> getAttrsByCol(Map inMap);
	public AttrDef getAttrDef(String field_attr_id);
	public TempInst getTempInstByTempInstId(String temp_inst_id);
	public List<AttrInst> getOuterAttrInst(String order_id);
	public TempInst getTempInstByGoodsIdAndSource(String goods_id,String source_from);
	
	/**
	 * 保存属性受理数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@SuppressWarnings("unchecked")
	public void saveTableInst(Order order,OrderItem orderItem,Map<String, List<AttrInst>> tableInstAttrs,Map<String, String> tableInstFkeys);
	
	public void saveEmptyTableInst(Order order,OrderItem orderItem,String table_name,Map<String, String> tableInstFkeys);
	/**
	 * 保存属性实例数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	public void saveAttrInst(OrderItem orderItem,List<AttrInst> propAttrInsts,Map<String,String> tableInstFkeys) ;

	
	/**
	 * 保存受理数据
	 * @param acceptRuleReq
	 * @return
	 * @throws ApiBusiException
	 */
	public AcceptResp saveTplInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException;
	
	
	/**e
	 * 分组处理
	 * @param inMap
	 * @param temp_inst_id
	 * @param tableInstAttrs
	 * @param attrInstAttr
	 */
	public void groupAttrInsts(List<AttrInst> oAttrInsts,Map<String ,List<AttrInst>> tableAttrInsts,List<AttrInst> pAttrInsts,String sub_attr_spec_type);
	
	
	
	/**
	 * 页面装载属性数据
	 * @param req
	 * @return
	 */
	public AttrInstLoadResp pageLoadAttr(AttrInstLoadReq req);
	
//	/**
//	 * 页面保存属性实例数据
//	 * @param req
//	 * @return
//	 */
//	public AttrInstLoadResp pageUpdateAttr(AttrInstLoadReq req);
	
}
