package com.ztesoft.net.attr.hander;

import java.io.Serializable;
import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.AttrDef;

public class AttrSwitchParams extends ZteRequest implements Serializable {

	private String order_id;//订单ID
	private String inst_id; //数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
	private String goods_id; //商品ID 只有子订单或货品单才有值
	private String pro_goods_id; //货品ID 只有货品单才有值
	private String order_from; //订单来源
	private String value; //原始值
	private AttrDef attrDef; //属性定议配置信息
	private Map orderAttrValues;//订单所有属性值
	private String hander_class;
	
	private String goods_pro_id;//商品product_id
	private String product_pro_id;//货品product_id
	
	private Map<String, Object> objMap;//业务对象集合
	private Object busiRequest; //业务对象
	private String mapping_type; //映射类型in-外系统转成本系统的，out--本系统转换成外系统
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getInst_id() {
		return inst_id;
	}
	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getPro_goods_id() {
		return pro_goods_id;
	}
	public void setPro_goods_id(String pro_goods_id) {
		this.pro_goods_id = pro_goods_id;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public AttrDef getAttrDef() {
		return attrDef;
	}
	public void setAttrDef(AttrDef attrDef) {
		this.attrDef = attrDef;
	}
	public Map getOrderAttrValues() {
		return orderAttrValues;
	}
	public void setOrderAttrValues(Map orderAttrValues) {
		this.orderAttrValues = orderAttrValues;
	}
	public String getGoods_pro_id() {
		return goods_pro_id;
	}
	public void setGoods_pro_id(String goods_pro_id) {
		this.goods_pro_id = goods_pro_id;
	}
	public String getProduct_pro_id() {
		return product_pro_id;
	}
	public void setProduct_pro_id(String product_pro_id) {
		this.product_pro_id = product_pro_id;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.attrswitchparams";
	}
	public String getHander_class() {
		return hander_class;
	}
	public void setHander_class(String hander_class) {
		this.hander_class = hander_class;
	}
	public Map<String, Object> getObjMap() {
		return objMap;
	}
	public void setObjMap(Map<String, Object> objMap) {
		this.objMap = objMap;
	}
	public Object getBusiRequest() {
		return busiRequest;
	}
	public void setBusiRequest(Object busiRequest) {
		this.busiRequest = busiRequest;
	}
	public String getMapping_type() {
		return mapping_type;
	}
	public void setMapping_type(String mapping_type) {
		this.mapping_type = mapping_type;
	}
	
}
