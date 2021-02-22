package zte.params.goods.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;

public class ProductsListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="货品列表",type="List",isNecessary="N",desc="products：商品下的货品列表。")
	private List<Goods> products;

	public List<Goods> getProducts() {
		return products;
	}

	public void setProducts(List<Goods> products) {
		this.products = products;
	}
}
