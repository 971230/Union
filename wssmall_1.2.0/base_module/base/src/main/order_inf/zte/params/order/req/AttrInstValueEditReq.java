package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.AttrInstValueEditResp;

public class AttrInstValueEditReq extends ZteRequest<AttrInstValueEditResp> {
	
	@ZteSoftCommentAnnotationParam(name="属性实例数据ID",type="String",isNecessary="Y",desc="属性实例数据ID")
	private String attr_inst_id;
	@ZteSoftCommentAnnotationParam(name="修改值",type="String",isNecessary="Y",desc="修改值")
	private String value;
	@ZteSoftCommentAnnotationParam(name="字段名",type="String",isNecessary="Y",desc="字段名")
	private String field_name;
	
	@ZteSoftCommentAnnotationParam(name="是否需要更新属性",type="String",isNecessary="Y",desc="是否需要更新属性")
	private String need_update_attr;
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号，必填")
	private String order_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.attr.value.edit";
	}

	public String getAttr_inst_id() {
		return attr_inst_id;
	}

	public void setAttr_inst_id(String attr_inst_id) {
		this.attr_inst_id = attr_inst_id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getNeed_update_attr() {
		return need_update_attr;
	}

	public void setNeed_update_attr(String need_update_attr) {
		this.need_update_attr = need_update_attr;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
