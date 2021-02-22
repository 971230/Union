package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_3Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String> {
	private static Logger logger = Logger.getLogger(MobileProductPage2_3Request.class);
	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";

	public MobileProductPage2_3Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		this.body.add(new BasicNameValuePair("globalPageName",globalPageName));
		return true;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
			//现金支票支付方式不能一起使用
			logger.info("现金支票支付方式不能一起使用"+respXml);
			return respXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?swallow/personalserv.dealtradefee.DealTradeFee/getPayCodeMutex/1";
	}

	@Override
	public String getReferer() {
		String jsonStr = "{\"SUBSCRIBE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"TRADE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"PROVINCE_ORDER_ID\": \""+client.getBusiMap().get("tradeId")+"\"}";
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param="+jsonStr+"&fee=&noBack=&staffId="+client.getParam().getStaffId()+"&departId="+client.getParam().getDepartId()+"&subSysCode=custserv&eparchyCode="+client.getParam().getEparchyCode();

	}

	@Override
	public boolean isXMLHttpRequest() {
		return false;
	}

	@Override
	public boolean isPost() {
		return false;
	}

}
