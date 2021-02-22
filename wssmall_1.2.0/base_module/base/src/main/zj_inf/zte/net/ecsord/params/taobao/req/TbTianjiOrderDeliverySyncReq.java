package zte.net.ecsord.params.taobao.req;

import java.util.List;

import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.AlibabaTianjiSupplierOrderDeliveryRequest.DistributionOrderLogisticsModel;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class TbTianjiOrderDeliverySyncReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1319250438307248398L;
	@ZteSoftCommentAnnotationParam(name="内部订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：内部订单编号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="订单物流信息",type="String",isNecessary="Y",desc="logisticsModel：订单物流信息")
	private DistributionOrderLogisticsModel logisticsModel;
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.syncTbTianjiOrderDelivery";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public DistributionOrderLogisticsModel getLogisticsModel() {
		logisticsModel = new DistributionOrderLogisticsModel();
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(notNeedReqStrOrderId, EcsOrderConsts.LOGIS_NORMAL);
		if(delivery != null){
			logisticsModel.setExpressCode(delivery.getShipping_company());//快递公司编码
			logisticsModel.setExpressName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY_NAME));
			logisticsModel.setExpressNumber(delivery.getLogi_no());
			logisticsModel.setProductSerialNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID));
			logisticsModel.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID));
			logisticsModel.setContractOrderStatus("SUCCESS");
			logisticsModel.setReason("发货成功");
		}
		return logisticsModel;
	}

	public void setLogisticsModel(DistributionOrderLogisticsModel logisticsModel) {
		this.logisticsModel = logisticsModel;
	}

	
}
