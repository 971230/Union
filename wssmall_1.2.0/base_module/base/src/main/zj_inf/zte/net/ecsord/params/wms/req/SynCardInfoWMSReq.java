package zte.net.ecsord.params.wms.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.wms.resp.SynCardInfoWMSResp;
import zte.net.ecsord.params.wms.vo.SynCardInfoOrderInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 接收WMS写卡机编号
 * 入参对象
 */
public class SynCardInfoWMSReq extends ZteRequest<SynCardInfoWMSResp>{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name="接入Id",type="String",isNecessary="Y",desc="接入Id(订单系统提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="请求类型,固定值：at_syn_readId")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name="订单信息",type="SynCardInfoOrderInfoVo",isNecessary="Y",desc="订单信息")
	private SynCardInfoOrderInfoVo orderInfo;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.wms.synchronousCardInfoWMS";
	}

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
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public SynCardInfoOrderInfoVo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(SynCardInfoOrderInfoVo orderInfo) {
		this.orderInfo = orderInfo;
	}
}
