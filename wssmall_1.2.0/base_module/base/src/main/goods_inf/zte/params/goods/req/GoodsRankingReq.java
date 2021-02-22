package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsRankingReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="类别",type="String",isNecessary="Y",desc="排行榜类别:1 同价位 2 同品牌 3 同类别")
	private String type;
	@ZteSoftCommentAnnotationParam(name="记录数",type="String",isNecessary="Y",desc="排行榜类别查询返回记录数,默认5条")
	private String count = "5";
	@ZteSoftCommentAnnotationParam(name="比较值",type="String",isNecessary="Y",desc="1 同价位传价位，单位为分 2 同品牌 传品牌id 3 同类别 传类别id")
	private String val;
	
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(type==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"排行榜类别不能为空"));
		if(val==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"排行榜比较值不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.ranking";
	}

}
