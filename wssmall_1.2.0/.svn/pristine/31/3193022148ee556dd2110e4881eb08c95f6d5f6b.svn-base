package zte.net.ecsord.params.nd.req;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.nd.resp.NotifyOrderInfoNDResponse;
import zte.net.ecsord.params.nd.resp.NotifyOrderStatuNDResponse;
import zte.net.ecsord.params.nd.vo.StatuOrderInfo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 订单状态通知（南都通知到订单系统）
 * 
 */
public class NotifyOrderStatuNDRequset extends ZteRequest<NotifyOrderStatuNDResponse> {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="activeNo：访问流水")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wl_syn_status")
	private String reqType;	

	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="reqTime：格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;


	@ZteSoftCommentAnnotationParam(name="订单信息",type="String",isNecessary="Y",desc="orderInfo：订单信息")
	private List<StatuOrderInfo> orderInfo; 
	
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

	

	public List<StatuOrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<StatuOrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.nd.notifyOrderStatu";
	}
    public static void main(String[] args){
    	//生成模拟报文
    	NotifyOrderStatuNDRequset req=new NotifyOrderStatuNDRequset();
    	req.setActiveNo("111111111");
    	req.setReqId("nd");
    	req.setReqType("wl_syn_status");
    	req.setReqTime("2015-01-05 17:16:33");
	    	List<StatuOrderInfo> list=new ArrayList<StatuOrderInfo>();
	    	StatuOrderInfo orderInfo=new StatuOrderInfo();
	    	orderInfo.setOrigOrderId("wms20143333333");
	    	orderInfo.setStatus("02");
	    	orderInfo.setStatusName("成功接收");
	    	orderInfo.setDesc("成功描述");
	    	orderInfo.setUpdateTime("2015-01-05 17:16:34");
	    	list.add(orderInfo);
	    req.setOrderInfo(list);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(req);
			logger.info("json:"+json);//
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
