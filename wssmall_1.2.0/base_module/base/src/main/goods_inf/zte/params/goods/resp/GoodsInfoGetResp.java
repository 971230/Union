package zte.params.goods.resp;

import java.util.List;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;

public class GoodsInfoGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品信息",type="String",isNecessary="Y",desc="goods：商品基本信息，商品参数。")
	public Goods goods;
	
	@ZteSoftCommentAnnotationParam(name="商品信息",type="String",isNecessary="Y",desc="goods：商品基本信息，商品属性数据。")
	List<Attribute> attrs;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public List<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}
	
}
