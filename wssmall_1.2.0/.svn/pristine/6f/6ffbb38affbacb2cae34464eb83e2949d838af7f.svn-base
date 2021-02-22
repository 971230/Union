package zte.params.goodstype.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class TypeListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="类型ID",type="String",isNecessary="Y",desc="type_id：类型ID，如果是-1，返回全部类型。")
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
		return "com.goodsService.types.list";
	}

}
