package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class MemberPriceByPIdReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="product_id：产品Id， 不能为空。")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="会员等级Id",type="String",isNecessary="Y",desc="member_lv_id：会员等级Id， 不能为空。")
	private String member_lv_id;
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {		
		return "zte.memberServer.memberPrice.qryPriceByPid";
	}

}
