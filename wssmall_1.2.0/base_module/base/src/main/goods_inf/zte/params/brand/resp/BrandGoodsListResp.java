package zte.params.brand.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteResponse;

public class BrandGoodsListResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="N",desc="根据品牌ID查询的商品列表")
	private List goodsList;

	public List getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List goodsList) {
		this.goodsList = goodsList;
	}

}
