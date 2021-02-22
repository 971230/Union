package zte.net.ecsord.params.attr.req;
//package zte.net.ecsord.params.attr.req;
//
//import params.ZteRequest;
//import zte.net.ecsord.params.attr.resp.AttrInstUpdateResp;
//
//import com.ztesoft.api.ApiRuleException;
//import com.ztesoft.net.framework.database.NotDbField;
//
///**
// * 属性更新实体
// * @作者 wui
// * @创建日期 2014-9-29 
// * @版本 V 1.0
// */
//public class AttrInstUpdateReq extends ZteRequest<AttrInstUpdateResp> {
//	String field_name ;
//	String field_value;
//	String order_id;
//	public String getField_name() {
//		return field_name;
//	}
//	public void setField_name(String field_name) {
//		this.field_name = field_name;
//	}
//	public String getField_value() {
//		return field_value;
//	}
//	public void setField_value(String field_value) {
//		this.field_value = field_value;
//	}
//	@Override
//	@NotDbField
//	public void check() throws ApiRuleException {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	@NotDbField
//	public String getApiMethodName() {
//		return "zte.net.ecsord.params.attr.req.attrInstUpdateReq";
//	}
//	public String getOrder_id() {
//		return order_id;
//	}
//	public void setOrder_id(String order_id) {
//		this.order_id = order_id;
//	}
//	
//}
