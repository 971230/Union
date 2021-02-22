package zte.params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CartBarPriceTotalResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品总金额",type="String",isNecessary="Y",desc="商品总金额")
	private double goodsTotal;
	@ZteSoftCommentAnnotationParam(name="是否选中",type="String",isNecessary="Y",desc="是否选中")
	private double pgkTotal;
	public double getGoodsTotal() {
		return goodsTotal;
	}
	public void setGoodsTotal(double goodsTotal) {
		this.goodsTotal = goodsTotal;
	}
	public double getPgkTotal() {
		return pgkTotal;
	}
	public void setPgkTotal(double pgkTotal) {
		this.pgkTotal = pgkTotal;
	}
	
}
