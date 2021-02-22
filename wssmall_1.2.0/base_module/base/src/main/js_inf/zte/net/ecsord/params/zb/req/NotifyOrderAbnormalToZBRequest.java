package zte.net.ecsord.params.zb.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class NotifyOrderAbnormalToZBRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="异常通知类型",type="String",isNecessary="Y",desc="NoticeType：异常通知类型【标记异常、恢复异常】，业务组件传入")
	private String NoticeType;
	
	@ZteSoftCommentAnnotationParam(name="异常类型",type="String",isNecessary="Y",desc="ExceptionType：异常类型，业务组件传入")
	private String ExceptionType;
	
	@ZteSoftCommentAnnotationParam(name="异常编码",type="String",isNecessary="Y",desc="ExceptionCode：异常编码")
	private String ExceptionCode;
	
	@ZteSoftCommentAnnotationParam(name="异常描述",type="String",isNecessary="Y",desc="ExceptionDesc：异常描述，业务组件传入")
	private String ExceptionDesc;
	
	@ZteSoftCommentAnnotationParam(name="变更时间",type="String",isNecessary="Y",desc="StateChgTime：变更时间")
	private String StateChgTime;

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

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, 
						AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getNoticeType() {
		
		return  NoticeType;
		
	}

	public void setNoticeType(String noticeType) {
		NoticeType = noticeType;
	}

	public String getExceptionType() {
		return ExceptionType;
	}

	public void setExceptionType(String exceptionType) {
		ExceptionType = exceptionType;
	}

	public String getExceptionCode() {
		ExceptionCode = "";
		return ExceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		ExceptionCode = exceptionCode;
	}

	public String getExceptionDesc() {
		
	    return ExceptionDesc;
		
	}

	public void setExceptionDesc(String exceptionDesc) {
		ExceptionDesc = exceptionDesc;
	}

	public String getStateChgTime() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StateChgTime = dtf.format(new Date());
		return StateChgTime;
	}

	public void setStateChgTime(String stateChgTime) {
		StateChgTime = stateChgTime;
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
		return "com.zte.unicomService.zb.NotifyOrderAbnormalToZB";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	
}
