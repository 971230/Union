package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_2Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String> {
	private static Logger logger = Logger.getLogger(MobileProductPage2_2Request.class);
	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";

	public MobileProductPage2_2Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		this.body.add(new BasicNameValuePair("SERIAL_NUMBER",""));
		this.body.add(new BasicNameValuePair("NET_TYPE_CODE",""));
		this.body.add(new BasicNameValuePair("TRADE_TYPE_CODE","undefined"));
		this.body.add(new BasicNameValuePair("judge","1"));
		this.body.add(new BasicNameValuePair("globalPageName",globalPageName));
		return true;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
			logger.info("|160|133|139|131|100|381|71"+respXml);
			//|160|133|139|131|100|381|71
			return respXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
//		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee";
//		String jsonStr = "{\"SUBSCRIBE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"TRADE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"PROVINCE_ORDER_ID\": \""+client.getBusiMap().get("tradeId")+"\"}";
//		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param="+jsonStr+"&fee=&noBack=&staffId="+client.getParam().getStaffId()+"&departId="+client.getParam().getDepartId()+"&subSysCode=custserv&eparchyCode="+client.getParam().getEparchyCode();
		return "https://gd.cbss.10010.com/custserv?swallow/common.UtilityPage/needChkCust/1";
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
