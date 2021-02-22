package zte.net.ecsord.params.base.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import zte.net.ecsord.params.base.resp.HuaShengGoodsResp;

public class HuaShengGoodsReq extends ZteRequest<HuaShengGoodsResp> {
	
	private String matnr;

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.ecsord.huaShengGoodsReq";
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

}
