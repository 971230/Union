package params.order.req;


import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class MemberOrderLogReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="订单Id",type="String",isNecessary="Y",desc="order_id：订单Id， 不能为空。")
	private String order_id;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.memberServer.member.queryOrderLog";
	}

}
