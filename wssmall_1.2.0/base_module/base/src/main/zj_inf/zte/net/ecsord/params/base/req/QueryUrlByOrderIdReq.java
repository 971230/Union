package zte.net.ecsord.params.base.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.model.AttrDef;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.base.resp.AttrInstLoadListResp;

public class QueryUrlByOrderIdReq extends ZteRequest<AttrInstLoadListResp>{
	private String order_id;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id)){CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空！"));}
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.QueryUrlByOrderIdReq";
	}
}
