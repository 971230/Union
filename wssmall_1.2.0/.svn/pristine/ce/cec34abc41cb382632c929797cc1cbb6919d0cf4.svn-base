package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.MemberReturnedOrderListResp;

public class MemberReturnedOrderListReq extends
		ZteRequest<MemberReturnedOrderListResp> {

	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="Y",desc="会员ID")
	private String menmber_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.returneds";
	}

	public String getMenmber_id() {
		return menmber_id;
	}

	public void setMenmber_id(String menmber_id) {
		this.menmber_id = menmber_id;
	}

}
