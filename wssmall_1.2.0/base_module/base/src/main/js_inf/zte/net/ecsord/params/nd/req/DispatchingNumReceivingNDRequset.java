package zte.net.ecsord.params.nd.req;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.OrderResp;
import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busiopen.account.resp.OrderAKeyActResp;
import zte.net.ecsord.params.nd.resp.DispatchingNumReceivingNDResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.mq.client.common.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 派工号接收
 * 
 */
public class DispatchingNumReceivingNDRequset extends ZteRequest<DispatchingNumReceivingNDResponse> {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="activeNo：访问流水")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	

	@ZteSoftCommentAnnotationParam(name="处理机构",type="String",isNecessary="Y",desc="dealAgent：对应登陆人员所在物流公司")
	private String dealAgent;

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wl_account_write_card")
	private String reqType;	

	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="reqTime：格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;

	@ZteSoftCommentAnnotationParam(name="派工号工号",type="String",isNecessary="Y",desc="operCode：派工号工号")
	private String operCode;

	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="origOrderId：外部订单号")
	private String origOrderId;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="orderId：订单编号")
	private String orderId;
	
	public String getActiveNo() {
		
		//return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
		return activeNo;
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

	public String getDealAgent() {
		return dealAgent;
	}

	public void setDealAgent(String dealAgent) {
		this.dealAgent = dealAgent;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {

		//Date now = new Date();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//reqTime=dateFormat.format(now);
		return reqTime;
	
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}
	
	

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
		return "com.zte.unicomService.nd.dispatchingNumReceiving";
	}

	
	public static void main(String[] args){
		
		
		
		 
		
		DispatchingNumReceivingNDRequset resp = new DispatchingNumReceivingNDRequset();
		resp.setActiveNo("1111111");
		resp.setReqId("wssmall_ecsord");
		resp.setDealAgent("nd");
		resp.setOperCode("gonghao");
		resp.setOrigOrderId("12242");
		resp.setOrderId("22344");
	
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(resp);
			logger.info("json:"+json);//json:{"resp_code":"0000","resp_msg":"success"}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
