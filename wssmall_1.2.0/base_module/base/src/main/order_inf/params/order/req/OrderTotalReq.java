package params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;
import params.ZteRequest;

public class OrderTotalReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="区、县ID",type="String",isNecessary="Y",desc="区、县ID")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name="配送方式ID",type="String",isNecessary="Y",desc="配送方式ID")
	private String dlyType_id;
	private String isProtected;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="Y",desc="会员ID")
	private String member_id;
	private Member member;
	@ZteSoftCommentAnnotationParam(name="优惠券编码",type="String",isNecessary="Y",desc="优惠券编码")
	private String coupon_code;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getIsProtected() {
		return isProtected;
	}

	public void setIsProtected(String isProtected) {
		this.isProtected = isProtected;
	}

	public String getDlyType_id() {
		return dlyType_id;
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

	public void setDlyType_id(String dlyType_id) {
		this.dlyType_id = dlyType_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "zte.orderService.order.goodsitem.totalprice";
	}
}
