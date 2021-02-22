package zte.params.tags.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class TagListReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.tag.list";
	}

}
