package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GroupBuyGetReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="团购ID",type="String",isNecessary="Y",desc="group_id：团购ID")
	private String group_id;
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(group_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"group_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.groupbuy.get";
	}

}
