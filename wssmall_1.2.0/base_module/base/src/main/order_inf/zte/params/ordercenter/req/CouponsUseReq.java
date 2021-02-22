package zte.params.ordercenter.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.ordercenter.resp.CouponsUseResp;

public class CouponsUseReq extends ZteRequest<CouponsUseResp> {
	
	@ZteSoftCommentAnnotationParam(name="优惠券编码",type="String",isNecessary="Y",desc="优惠券编码")
	private String coupon_code;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.center.coupon.use";
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

}
