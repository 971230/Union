package zte.params.goods.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class LimitBuyReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="秒杀id",type="String",isNecessary="Y",desc="limitbuyid：秒杀id不能为空。")
	private String limitbuyid;
	@ZteSoftCommentAnnotationParam(name="秒杀活动状态",type="int",isNecessary="N",desc="disabled：秒杀活动状态")
	private int disabled;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.limitBuy.inst";
	}

	public String getLimitbuyid() {
		return limitbuyid;
	}

	public void setLimitbuyid(String limitbuyid) {
		this.limitbuyid = limitbuyid;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
}
