package params.cart.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import params.ZteResponse;

import java.util.List;

public class CartTotalResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="价格信息",type="String",isNecessary="Y",desc="价格信息",hasChild=true)
	private OrderPrice cartPrice;
	@ZteSoftCommentAnnotationParam(name="优惠券信息",type="String",isNecessary="N",desc="优惠券信息",hasChild=true)
	private Coupons coupon;
	@ZteSoftCommentAnnotationParam(name="优惠活动规则",type="String",isNecessary="N",desc="优惠活动规则",hasChild=true)
	private List<Promotion> ordePmtList;
	public OrderPrice getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(OrderPrice cartPrice) {
		this.cartPrice = cartPrice;
	}
	public Coupons getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupons coupon) {
		this.coupon = coupon;
	}
	public List<Promotion> getOrdePmtList() {
		return ordePmtList;
	}
	public void setOrdePmtList(List<Promotion> ordePmtList) {
		this.ordePmtList = ordePmtList;
	}
	
}
