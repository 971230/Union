package zte.net.ecsord.params.zb.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.AccountInfo;

public class NotifyOpenAccountGDRequest extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;

	@ZteSoftCommentAnnotationParam(name = "开户节点", type = "List", isNecessary = "Y", desc = "AccountInfo：开户节点")
	private List<AccountInfo> AccountInfo;

	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public List<AccountInfo> getAccountInfo() {
		return AccountInfo;
	}

	public void setAccountInfo(List<AccountInfo> accountInfo) {
		AccountInfo = accountInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.NotifyOpenAccountGD";
	}

}