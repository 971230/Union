package zte.params.brand.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BrandListByTypeReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="类型ID",type="String",isNecessary="Y",desc="类型id： 不能为空。")
	private String type_id;
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(type_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"type_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.brand.listbytype";
	}

}
