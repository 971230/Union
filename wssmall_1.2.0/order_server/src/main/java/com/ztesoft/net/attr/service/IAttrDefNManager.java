package com.ztesoft.net.attr.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.AttrTable;

public interface IAttrDefNManager {

	/**
	 * 查询订单属性配置信息
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param space_type 属性类型
	 * @param attr_space_id -999表示通用的属性数据 
	 * @return
	 */
	public List<AttrDef> queryAttrDef(String space_type,String sub_space_type,String attr_space_id);
	
	public List<AttrTable> queryAttrTable(String space_type,String sub_space_type,String attr_space_id);
	
	/**
	 * 插入横表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param attrs
	 * @param values
	 */
	public List<AttrInstBusiRequest> insertAttrTable(String attr_space_type,String attr_sub_space_type, Map values,Map extMap,String order_id,String inst_id,String goods_id,String pro_goods_id,String goods_pro_id,String product_pro_id,int index)throws Exception;
	
	public void insertAttrInst(AttrInst inst);
	
	public void batchInsertAttrInst(List<AttrInstBusiRequest> instList);
	

	/**
	 * 查询attrdef
	 * @作者 MoChunrun
	 * @创建日期 2014-9-29 
	 * @param field_attr_id
	 * @return
	 */
	public AttrDef getAttrDef(String field_attr_id);
	
	public String getAttrInstValue(String order_id, String field_name);
	/**
	 * 重置属性缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-10-27
	 */
	public void resetAttrCache();
	
}
