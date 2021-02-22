package zte.net.ecsord.params.zb.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class NotifyOrderAbnormalToSystemRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="异常通知类型",type="String",isNecessary="Y",desc="NoticeType：异常通知类型")
	private String NoticeType;
	
	@ZteSoftCommentAnnotationParam(name="异常类型",type="String",isNecessary="Y",desc="ExceptionType：异常类型")
	private String ExceptionType;
	
	@ZteSoftCommentAnnotationParam(name="异常编码",type="String",isNecessary="Y",desc="ExceptionCode：异常编码")
	private String ExceptionCode;
	
	@ZteSoftCommentAnnotationParam(name="异常描述",type="String",isNecessary="Y",desc="ExceptionDesc：异常描述")
	private String ExceptionDesc;
	
	@ZteSoftCommentAnnotationParam(name="变更时间",type="String",isNecessary="Y",desc="StateChgTime：变更时间")
	private String StateChgTime;
	

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

	public String getNoticeType() {
		return NoticeType;
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
		return "com.zte.unicomService.zb.NotifyOrderAbnormalToSystem";
	}

}
