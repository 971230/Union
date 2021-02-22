package zte.net.ecsord.params.zb.req;

import java.util.List;

import params.ZteRequest;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.Order;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class StateSynchronizationToSystemRequest extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "订单状态信息", type = "List", isNecessary = "Y", desc = "Orders：订单状态信息")
	private List<Order> Orders;

	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="总部状态",type="String",isNecessary="Y",desc="总部状态")
	private String notNeedReqStrzb_status;
	
	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public List<Order> getOrders() {
		return Orders;
	}

	public void setOrders(List<Order> orders) {
		Orders = orders;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	public String getNotNeedReqStrzb_status() {
		return notNeedReqStrzb_status;
	}

	public void setNotNeedReqStrzb_status(String notNeedReqStrzb_status) {
		this.notNeedReqStrzb_status = notNeedReqStrzb_status;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.StateSynchronizationToSystem";
	}

}
