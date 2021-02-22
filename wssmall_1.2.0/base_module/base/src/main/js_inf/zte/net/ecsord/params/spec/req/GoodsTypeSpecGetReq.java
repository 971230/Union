package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsTypeSpecGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品货品type_id",type="String",isNecessary="Y",desc="商品货品type_id")
	private String type_id;
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="Y",desc="类型：商品goods、货品product")
	private String type;
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void check() throws ApiRuleException {
		if(type_id==null || "".equals(type_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "type_id不允许为空"));
		if(type==null || "".equals(type)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "type不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodsType.get";
	}

}
