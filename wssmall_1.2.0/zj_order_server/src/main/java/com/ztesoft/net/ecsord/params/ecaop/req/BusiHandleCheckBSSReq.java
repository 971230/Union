package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class BusiHandleCheckBSSReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="联通服务号码",type="String",isNecessary="Y",desc="service_num:联通服务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="office_id:操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name="操作员",type="String",isNecessary="Y",desc="operator_id：操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name="活动列表",type="String",isNecessary="Y",desc="scheme_list：多个以空格分割")
	private String scheme_list;
	@ZteSoftCommentAnnotationParam(name="产品列表",type="String",isNecessary="Y",desc="product_list：多个以空格分割")
	private String product_list;
	
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getOffice_id() {
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getScheme_list() {
		return scheme_list;
	}
	public void setScheme_list(String scheme_list) {
		this.scheme_list = scheme_list;
	}
	public String getProduct_list() {
		return product_list;
	}
	public void setProduct_list(String product_list) {
		this.product_list = product_list;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.busiHandleCheckBSS";
	}

}
