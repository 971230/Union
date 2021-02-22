package zte.params.goods.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class GoodsIntroReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：查询商品详情的商品ID，如201403044106001706， 不能为空。")
	private String goods_id;
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	@Override
	public void check() throws ApiRuleException {
		if(goods_id==null || "".equals(goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.detail.page.get";
	}
}
