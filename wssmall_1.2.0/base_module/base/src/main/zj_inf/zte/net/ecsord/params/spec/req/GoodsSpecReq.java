package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.params.req.ZteInstQueryRequest;

public class GoodsSpecReq extends ZteRequest<ZteResponse>  {
	@ZteSoftCommentAnnotationParam(name="商品货品goods_id",type="String",isNecessary="Y",desc="商品货品goods_id")
	private String goods_id;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goods_id==null || "".equals(goods_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "GOODS_ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.goodsService.spec.get";
	}

}
