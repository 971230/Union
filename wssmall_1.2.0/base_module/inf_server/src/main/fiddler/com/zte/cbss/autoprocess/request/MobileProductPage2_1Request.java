package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.CreateCustomHandler;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_1Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String> {

	private static final Logger log = LoggerFactory.getLogger(CreateCustomHandler.class);

	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";

	public MobileProductPage2_1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		try {
//			this.body.add(new BasicNameValuePair("NET_TYPE_CODE",client.getParam().getBill().getNetTypeCode()));
			this.body.add(new BasicNameValuePair("NET_TYPE_CODE","50"));
			this.body.add(new BasicNameValuePair("SERIAL_NUMBER",client.getParam().getBill().getSerialNumber()));
			this.body.add(new BasicNameValuePair("globalPageName",globalPageName));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
			log.info("预计返回:"+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><bcpCheckBcaInfoRsp resDesc=\"用户未签约！\" resCode=\"2\"/></root>");
			log.info("实际返回"+respXml);
			return respXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
		//https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param={"SUBSCRIBE_ID": "5115011909768622", "TRADE_ID": "5115011909768622", "PROVINCE_ORDER_ID": "5115011909768622"}&fee=&noBack=&staffId=HLWFS679&departId=51b12z3&subSysCode=custserv&eparchyCode=0757
//		String jsonStr = "{\"SUBSCRIBE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"TRADE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"PROVINCE_ORDER_ID\": \""+client.getBusiMap().get("tradeId")+"\"}";
//		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param="+jsonStr+"&fee=&noBack=&staffId="+client.getParam().getStaffId()+"&departId="+client.getParam().getDepartId()+"&subSysCode=custserv&eparchyCode="+client.getParam().getEparchyCode();
		return "https://gd.cbss.10010.com/custserv?swallow/personalserv.dealtradefee.DealTradeFee/bcpCheckBcaInfo/1";
		                                                  
	}

	@Override
	public String getReferer() {
//		return "https://gd.cbss.10010.com/custserv";
		String jsonStr = "{\"SUBSCRIBE_ID\": \""+client.getParam().getTradeId()+"\", \"TRADE_ID\": \""+client.getParam().getTradeId()+"\", \"PROVINCE_ORDER_ID\": \""+client.getParam().getTradeId()+"\"}";
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param="+jsonStr+"&fee=&noBack=&staffId="+client.getParam().getStaffId()+"&departId="+client.getParam().getDepartId()+"&subSysCode=custserv&eparchyCode="+client.getParam().getEparchyCode();
//		
	}

	@Override
	public boolean isXMLHttpRequest() {
		return true;
	}

	@Override
	public boolean isPost() {
		return true;
	}

}
