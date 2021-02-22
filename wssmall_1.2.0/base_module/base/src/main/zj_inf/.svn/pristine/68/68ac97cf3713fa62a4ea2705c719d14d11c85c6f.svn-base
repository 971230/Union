package zte.net.ecsord.params.wms.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.wms.resp.NotifyWriteCardResultWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyWriteCardResultGoodInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyWriteCardResultOrderInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 写卡完成通知WMS 入参对象
 */
public class NotifyWriteCardResultWMSReq extends
		ZteRequest<NotifyWriteCardResultWMSResp> {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name = "接入Id", type = "String", isNecessary = "Y", desc = "接入Id(由WMS提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name = "请求类型", type = "String", isNecessary = "Y", desc = "请求类型,固定值：at_syn_card")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name = "请求时间", type = "String", isNecessary = "Y", desc = "请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name = "订单信息", type = "List<NotifyWriteCardResultOrderInfoVo>", isNecessary = "Y", desc = "订单信息")
	private List<NotifyWriteCardResultOrderInfoVo> orderInfo;
	@ZteSoftCommentAnnotationParam(name = "订单Id", type = "String", isNecessary = "Y", desc = "订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "状态", type = "String", isNecessary = "Y", desc = "状态")
	private String notNeedReqStrStatus;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {

		return "com.zte.unicomService.wms.notifyWriteCardResultWMS";
	}

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(
				AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		reqId = "";
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return InfConst.WMS_AT_SYN_CARD;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		reqTime = dtf.format(new Date());
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public List<NotifyWriteCardResultOrderInfoVo> getOrderInfo() {
		orderInfo = new ArrayList<NotifyWriteCardResultOrderInfoVo>();

		NotifyWriteCardResultOrderInfoVo notifyWriteCardResultOrderInfoVo = new NotifyWriteCardResultOrderInfoVo();
		notifyWriteCardResultOrderInfoVo.setOrderId(orderId);

		List<NotifyWriteCardResultGoodInfoVo> notifyWriteCardResultGoodInfoVos = new ArrayList<NotifyWriteCardResultGoodInfoVo>();
		
		//取订单中的套餐货品
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.GOODS_TYPE);
		List<OrderItemBusiRequest> items = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests();
		OrderItemBusiRequest item = items.get(0);
		List<OrderItemProdBusiRequest> prodBusis = item.getOrderItemProdBusiRequests();
		if(prodBusis != null && prodBusis.size() > 0){
			for(OrderItemProdBusiRequest prodBusi : prodBusis){
				if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusi.getProd_spec_type_code())){//送套餐货品
					NotifyWriteCardResultGoodInfoVo notifyWriteCardResultGoodInfoVo = new NotifyWriteCardResultGoodInfoVo();
					notifyWriteCardResultGoodInfoVo.setOrderId(orderId);
					notifyWriteCardResultGoodInfoVo.setOrderProductId(prodBusi.getProd_spec_goods_id());
					notifyWriteCardResultGoodInfoVo.setPackageId(prodBusi.getItem_spec_goods_id());
					notifyWriteCardResultGoodInfoVo.setMobileTel(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ICCID));
					notifyWriteCardResultGoodInfoVo.setStatus(this.notNeedReqStrStatus);
					notifyWriteCardResultGoodInfoVo.setDescr("");
					notifyWriteCardResultGoodInfoVo.setUserDef1("");
					notifyWriteCardResultGoodInfoVo.setUserDef2("");
					notifyWriteCardResultGoodInfoVos.add(notifyWriteCardResultGoodInfoVo);
				}
			}
		}
		
		notifyWriteCardResultOrderInfoVo
				.setGoodInfo(notifyWriteCardResultGoodInfoVos);
		orderInfo.add(notifyWriteCardResultOrderInfoVo);
		return orderInfo;
	}

	public void setOrderInfo(List<NotifyWriteCardResultOrderInfoVo> orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getNotNeedReqStrStatus() {
		return notNeedReqStrStatus;
	}

	public void setNotNeedReqStrStatus(String notNeedReqStrStatus) {
		this.notNeedReqStrStatus = notNeedReqStrStatus;
	}
}
