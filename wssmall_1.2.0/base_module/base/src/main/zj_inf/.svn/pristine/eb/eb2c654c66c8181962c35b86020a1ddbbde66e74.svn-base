package zte.net.ecsord.params.bss.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class LocalGoodsStatusSynchronizationReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "local.goods.status.synchronization";
	}
	
	@ZteSoftCommentAnnotationParam(name="内部订单编号",type="String",isNecessary="Y",desc="内部订单编号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="订单号")
	private String ORDER_CODE;
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="Y",desc="订单状态")
	private String ORDER_STATUS;
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="Y",desc="接口编码")
	private String ACTION_ID;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getORDER_CODE() {
		ORDER_CODE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);//外部单号
		if(ORDER_CODE == null)
			ORDER_CODE = "";
		return ORDER_CODE;
	}

	public void setORDER_CODE(String oRDER_CODE) {
		ORDER_CODE = oRDER_CODE;
	}

	public String getORDER_STATUS() {
		OrderBusiRequest orderBusi = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderBusiRequest();//;.getOrderExtBusiRequest();
		if(null == orderBusi){
			return "";
		}
		String trace = orderBusi.getFlow_trace_id();//订单环节
		
		if(EcsOrderConsts.DIC_ORDER_NODE_B == trace){//审核
			ORDER_STATUS = "10";
		}if(EcsOrderConsts.DIC_ORDER_NODE_F == trace){//质检稽核
			ORDER_STATUS = "03";
		} else if(EcsOrderConsts.DIC_ORDER_NODE_H == trace){//发货
			ORDER_STATUS = "04";
		} else if(EcsOrderConsts.DIC_ORDER_NODE_J == trace){//资料归档
			ORDER_STATUS = "05";
		}else {//其他状态
			ORDER_STATUS = "";
		}

		return ORDER_STATUS;
	}

	public void setORDER_STATUS(String oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}

	public String getACTION_ID() {//固定为M10001
		ACTION_ID = "M10001";
		return ACTION_ID;
	}

	public void setACTION_ID(String aCTION_ID) {
		ACTION_ID = aCTION_ID;
	}
	
	

}
