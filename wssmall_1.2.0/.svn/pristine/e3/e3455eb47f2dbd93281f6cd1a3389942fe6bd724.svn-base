package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import commons.CommonTools;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsAcceptPriceReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：查询商品受理价格商品ID 不能为空。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="product_id：查询商品受理价格产品ID 不能为空。")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="Y",desc="spec_id：查询商品受理价格会员ID 不能为空。")
	private String lv_id;
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goods_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id不能为空"));
		if(product_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"product_id不能为空"));
		if(lv_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"lv_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.acceptPrice";
	}

}
