package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;

public class NumberSpecReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="order_id：根据订单ID查询号码，查询号码的规格信息并返回。")
	private String dn_no;

	public String getDn_no() {
		return dn_no;
	}

	public void setDn_no(String dn_no) {
		this.dn_no = dn_no;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.number.spec";
	}
	
	

}
