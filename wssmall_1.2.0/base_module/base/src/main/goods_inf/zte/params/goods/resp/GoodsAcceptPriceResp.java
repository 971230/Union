package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsAcceptPriceResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品受理价格",type="String",isNecessary="Y",desc="price：商品受理价格查询结果。")
	private String price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
}
