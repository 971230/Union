package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsPlanRelReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="a_goods_id：计划关联套餐 终端商品ID 不能为空。")
	private String a_goods_id ;
	public String getA_goods_id() {
		return a_goods_id;
	}

	public void setA_goods_id(String a_goods_id) {
		this.a_goods_id = a_goods_id;
	}

	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="z_goods_id：计划关联套餐 计划商品ID 不能为空。")
	private String z_goods_id;
	public String getZ_goods_id() {
		return z_goods_id;
	}

	public void setZ_goods_id(String z_goods_id) {
		this.z_goods_id = z_goods_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(a_goods_id==null || "".equals(a_goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "终端商品ID不允许为空"));
		if(z_goods_id==null || "".equals(z_goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "计划商品ID不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.plan.rel.get";
	}

}
