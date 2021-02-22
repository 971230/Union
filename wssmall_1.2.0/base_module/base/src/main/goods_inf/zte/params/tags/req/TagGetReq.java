package zte.params.tags.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class TagGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品标签ID",type="String",isNecessary="Y",desc="商品标签ID")
	private String tag_id;
	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tagId) {
		tag_id = tagId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.tag.get";
	}

}
