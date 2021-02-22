package zte.params.order.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AdjustOrderPriceResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="调整金额",type="double",isNecessary="Y",desc="需要调整的金额")
	private double ajust_amount;

	public double getAjust_amount() {
		return ajust_amount;
	}

	public void setAjust_amount(double ajust_amount) {
		this.ajust_amount = ajust_amount;
	}	
}
