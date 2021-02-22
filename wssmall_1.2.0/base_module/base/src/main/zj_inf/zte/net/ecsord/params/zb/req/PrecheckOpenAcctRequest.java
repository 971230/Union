package zte.net.ecsord.params.zb.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class PrecheckOpenAcctRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="OperatorID：操作员ID")
	private String OperatorID;
	
	@ZteSoftCommentAnnotationParam(name="订单编码",type="String",isNecessary="Y",desc="OrderId：订单编码")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="操作类型",type="String",isNecessary="Y",desc="OperType：操作类型")
	private String OperType;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="Para：保留字段")
	private String Para;
	

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

	public String getOperatorID() {
		OperatorID = CommonDataFactory.getInstance().getOperatorCode(notNeedReqStrOrderId);
		if(StringUtils.isEmpty(OperatorID)){
			
//			OperatorID = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_id();
			
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			List<OrderLockBusiRequest> orderLockRequest=orderTree.getOrderLockBusiRequests();
			OrderLockBusiRequest orderLockBusiRequest=null;
			for(int i=0;i<orderLockRequest.size();i++){
				if(orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())){
					orderLockBusiRequest=orderLockRequest.get(i);
				}
			}
			OperatorID=orderLockBusiRequest.getLock_user_id();
		}
		return OperatorID;
	}

	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getOperType() {
		OperType = "01";
		return OperType;
	}

	public void setOperType(String operType) {
		OperType = operType;
	}

	public String getPara() {
		return "";
	}

	public void setPara(String para) {
		Para = para;
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

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.PrecheckOpenAcct";
	}

}
