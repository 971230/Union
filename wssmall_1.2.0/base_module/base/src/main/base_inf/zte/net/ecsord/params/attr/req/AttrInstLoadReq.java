package zte.net.ecsord.params.attr.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import consts.ConstsCore;

/**
 * 属性更新实体
 * @作者 wui
 * @创建日期 2014-9-29 
 * @版本 V 1.0
 */
public class AttrInstLoadReq extends ZteRequest<AttrInstLoadResp> {
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="N",desc="订单编号")
	String order_id;
	@ZteSoftCommentAnnotationParam(name="字段名",type="String",isNecessary="N",desc="字段名")
	String field_name ;
	@ZteSoftCommentAnnotationParam(name="attr_code",type="String",isNecessary="N",desc="attr_code")
	String attr_code;
	@ZteSoftCommentAnnotationParam(name="属性动作",type="String",isNecessary="N",desc="页面装载前属性数据设置，页面属性保存前属性数据设置")
	String action_name = ConstsCore.ATTR_ACTION_lOAD ;
	@ZteSoftCommentAnnotationParam(name="传入新值",type="String",isNecessary="N",desc="传入新值")
	String new_value; //传入值
	@ZteSoftCommentAnnotationParam(name="样式",type="String",isNecessary="N",desc="样式")
	String style;
	
	@ZteSoftCommentAnnotationParam(name="textarea扩展属性",type="String",isNecessary="N",desc="textarea扩展属性")
	private String rows;
	@ZteSoftCommentAnnotationParam(name="textarea扩展属性",type="String",isNecessary="N",desc="textarea扩展属性")
	private String cols;
	@ZteSoftCommentAnnotationParam(name="文本类型",type="String",isNecessary="N",desc="文本类型")
	private String field_type;
	@ZteSoftCommentAnnotationParam(name="select扩展属性",type="String",isNecessary="N",desc="select扩展属性")
	String appen_options;
	@ZteSoftCommentAnnotationParam(name="是否可用",type="String",isNecessary="N",desc="是否可用")
	String disabled;
	
	
	@ZteSoftCommentAnnotationParam(name="调研时机",type="String",isNecessary="N",desc="调研时机")
	String action_time;
	
	AttrInstBusiRequest attrInstBusiRequest = null; //当前内存Tree对象
	
	
	OrderTreeBusiRequest orderTree =null;//非缓存数据提供性能提升
	List<AttrInstBusiRequest> attrInsts =new ArrayList<AttrInstBusiRequest>(); //非缓存数据提供性能提升
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ord.params.attr.req.AttrInstLoadReq";
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public AttrInstBusiRequest getAttrInstBusiRequest() {
		return attrInstBusiRequest;
	}
	public void setAttrInstBusiRequest(AttrInstBusiRequest attrInstBusiRequest) {
		this.attrInstBusiRequest = attrInstBusiRequest;
	}
	public String getNew_value() {
		return new_value;
	}
	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getAppen_options() {
		return appen_options;
	}
	public void setAppen_options(String appen_options) {
		this.appen_options = appen_options;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}
	@NotDbField
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getAction_time() {
		return action_time;
	}
	public void setAction_time(String action_time) {
		this.action_time = action_time;
	}
	public List<AttrInstBusiRequest> getAttrInsts() {
		return attrInsts;
	}
	public void setAttrInsts(List<AttrInstBusiRequest> attrInsts) {
		this.attrInsts = attrInsts;
	}
	
	
}