package params.cart.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;
import params.cart.resp.CartListResp;

public class CartListReq extends ZteRequest<CartListResp> {

	@ZteSoftCommentAnnotationParam(name="会员编号",type="String",isNecessary="Y",desc="会员编号")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="商家用户ID",type="String",isNecessary="Y",desc="商家用户ID")
	private String staff_no;

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
		return "zte.cartService.cart.list";
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
	
}
