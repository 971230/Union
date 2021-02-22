package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsStoreGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品库存",type="int",isNecessary="Y",desc="stores：商品库存查询结果。")
	private int stores;
	public int getStores() {
		return stores;
	}
	public void setStores(int stores) {
		this.stores = stores;
	}
}
