package zte.params.tags.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class TagGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="标签ID",type="String",isNecessary="Y",desc="标签ID，根据tag_id获取标签下关联的商品列表")
	private String tag_id;
	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tagId) {
		tag_id = tagId;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.tag.listGoods";
	}

}
