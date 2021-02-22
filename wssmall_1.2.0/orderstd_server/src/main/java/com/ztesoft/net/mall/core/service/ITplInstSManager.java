package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.AttrInst;



public interface ITplInstSManager {
	
	/**
	 * 保存属性受理数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@SuppressWarnings("unchecked")
	public void saveTableInst(Order order,OrderItem orderItem,Map<String, List<AttrInst>> tableInstAttrs,Map<String, String> tableInstFkeys);
	/**
	 * 保存属性实例数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	public void saveAttrInst(OrderItem orderItem,List<AttrInst> propAttrInsts,Map<String,String> tableInstFkeys) ;

	
	/**e
	 * 分组处理
	 * @param inMap
	 * @param temp_inst_id
	 * @param tableInstAttrs
	 * @param attrInstAttr
	 */
	public void groupAttrInsts(List<AttrInst> oAttrInsts,Map<String ,List<AttrInst>> tableAttrInsts,List<AttrInst> pAttrInsts,String sub_attr_spec_type);
	public List<AttrInst> getOuterAttrInst(String order_id);
	
}
