package zte.net.ecsord.params.wms.req;


import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.wms.resp.SynchronousCheckFromWMSResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetOrdByBoxIdFromWMSReq extends ZteRequest<SynchronousCheckFromWMSResp>{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name="接入Id",type="String",isNecessary="Y",desc="接入Id(由订单系统提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="请求类型,固定值：wl_syn_auditing")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name="料箱号",type="String",isNecessary="Y",desc="")
	private String boxCode;
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return InfConst.WMS_WL_SYN_AUDITING;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(new Date());
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
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
		
		return "com.zte.unicomService.wms.getOrdByBoxIdFromWMS";
	}
}
