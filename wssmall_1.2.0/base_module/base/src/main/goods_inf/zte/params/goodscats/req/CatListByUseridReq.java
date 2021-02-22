package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CatListByUseridReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="user_id：分销商ID")
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(user_id == null || "".equals(user_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"user_id：分销商ID不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.cat.listByUserid";
	}

}
