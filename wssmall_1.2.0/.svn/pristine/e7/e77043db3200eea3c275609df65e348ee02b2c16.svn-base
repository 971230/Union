package zte.params.cart.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import params.ZteRequest;
import zte.params.cart.resp.CartDeleteResp;

public class CartDeleteReq  extends ZteRequest<CartDeleteResp>{

	@ZteSoftCommentAnnotationParam(name="是否清空购物车",type="Array",isNecessary="Y",desc="是否清空购物车")
	private boolean clean;
	@ZteSoftCommentAnnotationParam(name="购物车编号",type="Array",isNecessary="Y",desc="购物车编号")
	private String [] cart_ids;
	public String[] getCart_ids() {
		return cart_ids;
	}
	public void setCart_ids(String[] cart_ids) {
		this.cart_ids = cart_ids;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.cartService.cart.delete";
	}
	public boolean isClean() {
		return clean;
	}
	public void setClean(boolean clean) {
		this.clean = clean;
	}

}
