package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.PreOrderResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

/**
 * 
 * @author chenlijun
 *
 */
public class PreOrderRequest extends ZteRequest<PreOrderResponse>{

	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="N",desc="订单ID")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="N",desc="订单类型")
	private String order_type;
	
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="N",desc="订单状态")
	private String state;
	
	@ZteSoftCommentAnnotationParam(name="联系号码",type="String",isNecessary="N",desc="联系号码")
	private String phone_no;
	
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="开始时间")
	private String start_time;
	
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="结束时间")
	private String end_time;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(订单列表查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "preOrder";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	
}
