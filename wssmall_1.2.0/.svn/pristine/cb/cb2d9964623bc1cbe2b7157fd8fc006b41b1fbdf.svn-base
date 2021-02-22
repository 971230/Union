package zte.params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class GoodsTypeListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="N",desc="类型：商品-goods，货品-product。")
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodsType.typelist";
	}

}
