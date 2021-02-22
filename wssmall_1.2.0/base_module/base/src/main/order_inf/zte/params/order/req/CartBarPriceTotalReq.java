package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.CartBarPriceTotalResp;

public class CartBarPriceTotalReq extends ZteRequest<CartBarPriceTotalResp> {

	@ZteSoftCommentAnnotationParam(name="是否选中",type="String",isNecessary="Y",desc="是否选中")
	private boolean countChecked;

	public boolean isCountChecked() {
		return countChecked;
	}

	public void setCountChecked(boolean countChecked) {
		this.countChecked = countChecked;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.cartService.cart.cartBarPrice";
	}
	
}
