package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class BroadbandGoodsQryReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 684189221541442640L;
	@ZteSoftCommentAnnotationParam(name="小区编码",type="String",isNecessary="Y",desc="communityCode：小区编码。")
	private String communityCode;
	@ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="Y",desc="type：商品类型。")
	private String type;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return apiMethodName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = StringUtil.isEmpty(apiMethodName)?this.apiMethodName:apiMethodName;
	}

	private String apiMethodName = "com.goodsService.goods.qryAdmissibleBroadbandGoods";
}
