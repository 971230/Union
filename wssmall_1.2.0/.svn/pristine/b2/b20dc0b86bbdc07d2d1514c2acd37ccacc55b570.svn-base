package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_5Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String>{
	private static Logger logger = Logger.getLogger(MobileProductPage2_5Request.class);
	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";


	public MobileProductPage2_5Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		this.body.add(new BasicNameValuePair("cancelTag", "false"));
		return false;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		this.body.add(new BasicNameValuePair("CASH", "0.00"));
		String base = "{\"prepayTag\": \"1\", \"tradeTypeCode\": \"0120\", \"strisneedprint\": \"1\", \"serialNumber\": \""+String.valueOf(super.client.getBusiMap().get("SERIAL_NUMBER"))+"\", \"tradeReceiptInfo\": \"[{\"RECEIPT_INFO5\":\"\",\"RECEIPT_INFO2\":\"\",\"RECEIPT_INFO1\":\"\",\"RECEIPT_INFO4\":\"\",\"RECEIPT_INFO3\":\"\"}]\", \"netTypeCode\": \"0050\"}";
		this.body.add(new BasicNameValuePair("base", base));
		this.body.add(new BasicNameValuePair("TRADE_ID", String.valueOf(client.getBusiMap().get("tradeId"))));
		this.body.add(new BasicNameValuePair("globalPageName", globalPageName));
		
		String respXml = response.getResponse();
		logger.info("respXml"+respXml);
		return "";
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?swallow/personalserv.dealtradefee.DealTradeFee/resourceMphoneNo/1";
	}

	@Override
	public String getReferer() {
		String jsonStr = "{\"SUBSCRIBE_ID\": \"" + client.getBusiMap().get("tradeId") + "\", \"TRADE_ID\": \"" + client.getBusiMap().get("tradeId")
				+ "\", \"PROVINCE_ORDER_ID\": \"" + client.getBusiMap().get("tradeId") + "\"}";
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=init&TRADE_TYPE_CODE=tradeType&param=" + jsonStr
				+ "&fee=&noBack=&staffId=" + client.getParam().getStaffId() + "&departId=" + client.getParam().getDepartId() + "&subSysCode=custserv&eparchyCode="
				+ client.getParam().getEparchyCode();
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
