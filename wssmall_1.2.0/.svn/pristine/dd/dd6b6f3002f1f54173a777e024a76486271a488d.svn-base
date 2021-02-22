package zte.params.supplier.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class SupplierGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商家名称",type="String",isNecessary="N",desc="name：商家名称")
	private String company_name;
	
	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.supplierService.supplier.get";
	}

}
