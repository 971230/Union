package params.cart.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.support.CartItem;
import params.ZteResponse;

import java.util.List;
public class CartListResp extends ZteResponse {

	//private Map<String,Map<String,List<CartItem>>> cartMap;
	@ZteSoftCommentAnnotationParam(name="优惠券信息",type="String",isNecessary="N",desc="优惠券信息")
	private Coupons coupon;
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="N",desc="商品列表")
	private List<CartItem> goodsItemList;
	@ZteSoftCommentAnnotationParam(name="赠送信息列表",type="String",isNecessary="N",desc="赠送信息列表")
	private List<CartItem> giftItemList;
	@ZteSoftCommentAnnotationParam(name="捆绑商品列表",type="String",isNecessary="N",desc="捆绑商品列表")
	private List<CartItem> pgkItemList;
	@ZteSoftCommentAnnotationParam(name="团购商品列表",type="String",isNecessary="N",desc="团购商品列表")
	private List<CartItem> groupBuyList;
	public Coupons getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupons coupon) {
		this.coupon = coupon;
	}
	public List<CartItem> getGoodsItemList() {
		return goodsItemList;
	}
	public void setGoodsItemList(List<CartItem> goodsItemList) {
		this.goodsItemList = goodsItemList;
	}
	public List<CartItem> getGiftItemList() {
		return giftItemList;
	}
	public void setGiftItemList(List<CartItem> giftItemList) {
		this.giftItemList = giftItemList;
	}
	public List<CartItem> getPgkItemList() {
		return pgkItemList;
	}
	public void setPgkItemList(List<CartItem> pgkItemList) {
		this.pgkItemList = pgkItemList;
	}
	public List<CartItem> getGroupBuyList() {
		return groupBuyList;
	}
	public void setGroupBuyList(List<CartItem> groupBuyList) {
		this.groupBuyList = groupBuyList;
	}
	
}
