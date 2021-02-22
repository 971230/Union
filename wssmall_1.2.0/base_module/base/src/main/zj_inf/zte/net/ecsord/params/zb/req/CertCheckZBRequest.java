package zte.net.ecsord.params.zb.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.statesys.Fetch;
import zte.net.ecsord.params.zb.vo.statesys.Order;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class CertCheckZBRequest extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "总部订单号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;



	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.certCheck";
	}
}
