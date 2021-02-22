package zte.net.ecsord.params.wms.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyStatusToWMSOrderInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 通知订单状态到WMS系统 入参对象
 */
public class NotifyOrderStatusToWMSReq extends
		ZteRequest<NotifyOrderStatusToWMSResp> {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name = "接入Id", type = "String", isNecessary = "Y", desc = "接入Id(由订单系统提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name = "请求类型", type = "String", isNecessary = "Y", desc = "请求类型,固定值：at_syn_status")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name = "请求时间", type = "String", isNecessary = "Y", desc = "请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name = "订单信息", type = "List<NotifyStatusToWMSOrderInfoVo>", isNecessary = "Y", desc = "订单信息")
	private List<NotifyStatusToWMSOrderInfoVo> orderInfo;
	@ZteSoftCommentAnnotationParam(name = "订单Id", type = "String", isNecessary = "Y", desc = "订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "通知状态", type = "String", isNecessary = "Y", desc = "通知状态")
	private String notNeedReqStrWms_status;

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

		return "com.zte.unicomService.wms.notifyOrderStatusToWMS";
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
		return InfConst.WMS_AT_SYN_STATUS;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		reqTime = dateFormat.format(now);
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public List<NotifyStatusToWMSOrderInfoVo> getOrderInfo() {
		orderInfo = new ArrayList<NotifyStatusToWMSOrderInfoVo>();
		List<NotifyStatusToWMSOrderInfoVo> NotifyStatusToWMSOrderInfoVos = new ArrayList<NotifyStatusToWMSOrderInfoVo>();
		NotifyStatusToWMSOrderInfoVo notifyStatusToWMSOrderInfoVo = new NotifyStatusToWMSOrderInfoVo();
		notifyStatusToWMSOrderInfoVo.setOrderId(orderId);
		notifyStatusToWMSOrderInfoVo.setDesc("");
		notifyStatusToWMSOrderInfoVo.setStatus(notNeedReqStrWms_status);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		notifyStatusToWMSOrderInfoVo.setStatusUpdateTime(dateFormat.format(new Date()));
		notifyStatusToWMSOrderInfoVo.setUserDef1(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.LOGI_NO));//物流单号，稽核完成【通知WMS发货】送给WMS
		notifyStatusToWMSOrderInfoVo.setUserDef2("");
		NotifyStatusToWMSOrderInfoVos.add(notifyStatusToWMSOrderInfoVo);
		orderInfo.add(notifyStatusToWMSOrderInfoVo);
		return orderInfo;
	}

	public void setOrderInfo(List<NotifyStatusToWMSOrderInfoVo> orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getNotNeedReqStrWms_status() {
		return notNeedReqStrWms_status;
	}

	public void setNotNeedReqStrWms_status(String notNeedReqStrWms_status) {
		this.notNeedReqStrWms_status = notNeedReqStrWms_status;
	}
	
}
