package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.CartUpdateResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class CartUpdateReq extends ZteRequest<CartUpdateResp> {

	public static final String ACTION_NUM = "num";
	public static final String ACTION_CHECKED_FLAG = "checked_flag";
	
	@ZteSoftCommentAnnotationParam(name="购物车ID",type="String",isNecessary="Y",desc="购物车ID(批量修改，用“，”分割)")
	private String cart_id;
	@ZteSoftCommentAnnotationParam(name="商品数量",type="String",isNecessary="N",desc="商品数量")
	private int num;
	@ZteSoftCommentAnnotationParam(name="是否选中0否1是",type="String",isNecessary="N",desc="是否选中0否1是")
	private int checked_flag = 0;
	@ZteSoftCommentAnnotationParam(name="操作类型 num修改数量 checked_flag修改是否选中",type="String",isNecessary="N",desc="操作类型 num修改数量 checked_flag修改是否选中")
	private String action = ACTION_NUM;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.cartService.cart.updatenum";
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getChecked_flag() {
		return checked_flag;
	}

	public void setChecked_flag(int checked_flag) {
		this.checked_flag = checked_flag;
	}

}
