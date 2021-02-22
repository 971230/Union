package zte.net.ecsord.params.nd.req;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.nd.resp.OrderDealSuccessNDResponse;
import zte.net.ecsord.params.nd.vo.GoodInfo;
import zte.net.ecsord.params.nd.vo.OrderInfo;
import zte.net.ecsord.params.nd.vo.PostInfo;
import zte.net.ecsord.params.nd.vo.StatuOrderInfo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 南都将订单完成信息通知给订单系统
 * 
 */
public class OrderDealSuccessNDRequset extends ZteRequest<OrderDealSuccessNDResponse> {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="activeNo：访问流水")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wl_order_deal_suces")
	private String reqType;	

	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="reqTime：格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;

	@ZteSoftCommentAnnotationParam(name="订单信息",type="String",isNecessary="Y",desc="orderInfo：订单信息")
	private List<OrderInfo> orderInfo;		
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
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
		//Date now = new Date();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//reqTime=dateFormat.format(now);
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public List<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
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
		return "com.zte.unicomService.nd.orderDealSuccess";
	}

	 public static void main(String[] args){
	    	//生成模拟报文
		 OrderDealSuccessNDRequset req=new OrderDealSuccessNDRequset();
	    	req.setActiveNo("111111111");
	    	req.setReqId("nd");
	    	req.setReqType("wl_order_deal_suces");
	    	req.setReqTime("2015-01-05 17:16:33");
	    	  List<OrderInfo> list=new ArrayList<OrderInfo>();
		    	  OrderInfo orderInfo=new OrderInfo();
			      orderInfo.setOrigOrderId("wms20143333333");
			      orderInfo.setReissueInfo("这是补寄品内容");
			      List<GoodInfo> goodInfoList=new ArrayList<GoodInfo>();
			      GoodInfo goodInfo=new GoodInfo();
			         goodInfo.setPackageId("12");
			         goodInfo.setSeriesNumSw("1122");
			         goodInfo.setSeriesNumCard("223333");
			         goodInfo.setMobileTel("100010");
			         goodInfo.setBssAccountTime("2015-01-05 17:16:36");
			         goodInfo.setBssAccount("333333");
			         goodInfo.setBssReceivablesAmounts("33.0");
			         goodInfo.setBssFastenerAmounts("353");
			         goodInfo.setCouponCardFee("110002.09");
			       goodInfoList.add(goodInfo);
			      
			       List<PostInfo> postInfoList=new ArrayList<PostInfo>();
			       PostInfo postInfo=new PostInfo();
			          postInfo.setPostId("220202020");
			          postInfo.setCarryPerson("快递员1");
			          postInfo.setCarryTime("2015-01-05 17:16:58");
			          postInfo.setPostNo("88888");
			          postInfo.setProtectFee("0.003");
			          postInfo.setCarryFee("5.0");
			          postInfo.setCarryPersonTel("133998888");
			          postInfo.setReceiptTime("2015-01-05 17:17:55");
			       
			       postInfoList.add(postInfo);
			      orderInfo.setGoodInfo(goodInfoList);
			      orderInfo.setPostInfo(postInfoList);
			      
		       list.add(orderInfo);
		    req.setOrderInfo(list);
			ObjectMapper mapper = new ObjectMapper();
			try {
				String json = mapper.writeValueAsString(req);
				//logger.info("json:"+json);//
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
