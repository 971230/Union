package zte.net.ecsord.params.taobao.req;

import java.util.List;

import com.taobao.api.request.AlibabaTianjiSupplierOrderQueryRequest.SupplierTopQueryModel;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class TbTianjiOrderInfoGetReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	private SupplierTopQueryModel model ;
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.getTbTianjiOrderInfo";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public SupplierTopQueryModel getModel() {
		model = new SupplierTopQueryModel();
		model.setOrderNo(notNeedReqStrOrderId);
		return model;
	}

	public void setModel(SupplierTopQueryModel model) {
		this.model = model;
	}

}
