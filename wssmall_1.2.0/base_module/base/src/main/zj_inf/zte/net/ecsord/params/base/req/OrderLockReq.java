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

public class OrderLockReq extends ZteRequest<AttrInstLoadListResp>{
	private String order_id;
	private String user_id;
	private String user_name;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id)){CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空！"));}
		if(StringUtils.isEmpty(user_id)){CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "user_id不能为空！"));}
		if(StringUtils.isEmpty(user_name)){CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "user_name不能为空！"));}
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.OrderLockReq";
	}
}
