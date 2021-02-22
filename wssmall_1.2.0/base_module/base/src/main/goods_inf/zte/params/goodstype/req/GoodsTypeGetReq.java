package zte.params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsTypeGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品类型ID",type="String",isNecessary="Y",desc="商品类型ID，获取商品类型信息")
	private String type_id;
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String typeId) {
		type_id = typeId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(type_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"type_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "zte.goodsService.goodsType.get";
	}

}
