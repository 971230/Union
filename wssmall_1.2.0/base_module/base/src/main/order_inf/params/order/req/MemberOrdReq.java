package params.order.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class MemberOrdReq extends ZteRequest {
	private String member_id;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "zte.orderService.order.listorder.member";
	}
}