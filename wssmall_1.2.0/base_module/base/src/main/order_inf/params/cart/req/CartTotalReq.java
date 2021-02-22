package params.cart.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class CartTotalReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="Y",desc="会员ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="是否选中",type="String",isNecessary="Y",desc="是否选中")
	private boolean checkedFlag;
	@ZteSoftCommentAnnotationParam(name="会员等级ID",type="String",isNecessary="Y",desc="会员等级ID")
	private String member_lv_id;
	@ZteSoftCommentAnnotationParam(name="商家用户ID",type="String",isNecessary="N",desc="商家用户ID")
	private String staff_no;
	@ZteSoftCommentAnnotationParam(name="优惠券编码",type="String",isNecessary="N",desc="优惠券编码")
	private String coupon_code;

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public boolean isCheckedFlag() {
		return checkedFlag;
	}

	public void setCheckedFlag(boolean checkedFlag) {
		this.checkedFlag = checkedFlag;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "zte.cartService.cart.gettotalprice";
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	
}
