package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ProxyGoodsDeleteReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="N",desc="userid：分销商ID。")
	private String userid;
	@ZteSoftCommentAnnotationParam(name="代理的商品ID",type="String",isNecessary="N",desc="p_goods_id：代理的商品ID。")
	private String p_goods_id;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getP_goods_id() {
		return p_goods_id;
	}

	public void setP_goods_id(String p_goods_id) {
		this.p_goods_id = p_goods_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(userid == null || "".equals(userid)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "userid：分销商不允许为空"));
		if(p_goods_id == null || "".equals(p_goods_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "p_goods_id：代理的商品ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.proxy.delete";
	}

}
