package zte.net.ecsord.params.attr.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 属性更新实体
 * @作者 wui
 * @创建日期 2014-9-29 
 * @版本 V 1.0
 */
public class AttrInstLoadResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="字段类型",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_type;
	@ZteSoftCommentAnnotationParam(name="字典编码",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String attr_code;
	@ZteSoftCommentAnnotationParam(name="是否为空",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取 Y/N")
	private String is_null;
	@ZteSoftCommentAnnotationParam(name="是否编辑",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取 Y/N")
	private String is_edit;
	@ZteSoftCommentAnnotationParam(name="属性实例id，规则中不需要设置",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String attr_inst_id;
	@ZteSoftCommentAnnotationParam(name="字段值",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_value;
	@ZteSoftCommentAnnotationParam(name="字段值",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_value_desc;
	@ZteSoftCommentAnnotationParam(name="错误消息",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String check_message;
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="attr_code静态数据",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private List<Map<String,String>> staticDatas;//非必须
	@ZteSoftCommentAnnotationParam(name="字段名称",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_name;
	@ZteSoftCommentAnnotationParam(name="字段规格id",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_attr_id;
	@ZteSoftCommentAnnotationParam(name="字典编码",type="String",isNecessary="Y",desc="未设置值，从es_attr_def配置表读取")
	private String field_desc;
	@ZteSoftCommentAnnotationParam(name="是否可用",type="String",isNecessary="N",desc="是否可用")
	private String disabled;
	@ZteSoftCommentAnnotationParam(name="校验用正则表达式",type="String",isNecessary="N",desc="校验用正则表达式")
	private String field_attr_format;
	@ZteSoftCommentAnnotationParam(name="业务对象Map",type="Map",isNecessary="N",desc="业务对象Map")
	private Map<String, Object> objMap;
	@ZteSoftCommentAnnotationParam(name="业务对象",type="Object",isNecessary="N",desc="业务对象")
	private Object busiRequest;
	
	
	public Object getBusiRequest() {
		return busiRequest;
	}
	public void setBusiRequest(Object busiRequest) {
		this.busiRequest = busiRequest;
	}
	public Map<String, Object> getObjMap() {
		return objMap;
	}
	public void setObjMap(Map<String, Object> objMap) {
		this.objMap = objMap;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}
	public String getIs_edit() {
		return is_edit;
	}
	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}
	public String getField_value() {
		return field_value;
	}
	public void setField_value(String field_value) {
		this.field_value = field_value;
	}
	public String getCheck_message() {
		return check_message;
	}
	public void setCheck_message(String check_message) {
		this.check_message = check_message;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public List<Map<String, String>> getStaticDatas() {
		return staticDatas;
	}
	public void setStaticDatas(List<Map<String, String>> staticDatas) {
		this.staticDatas = staticDatas;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	
	public String getField_attr_id() {
		return field_attr_id;
	}
	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}
	public String getAttr_inst_id() {
		return attr_inst_id;
	}
	public void setAttr_inst_id(String attr_inst_id) {
		this.attr_inst_id = attr_inst_id;
	}
	public String getField_value_desc() {
		return field_value_desc;
	}
	public void setField_value_desc(String field_value_desc) {
		this.field_value_desc = field_value_desc;
	}
	public String getField_desc() {
		return field_desc;
	}
	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getField_attr_format() {
		return field_attr_format;
	}
	public void setField_attr_format(String field_attr_format) {
		this.field_attr_format = field_attr_format;
	}
	
}
