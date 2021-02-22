package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_7Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String> {

	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";

	public MobileProductPage2_7Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		this.body.add(new BasicNameValuePair("cancelTag", "false"));
		return false;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		this.body.add(new BasicNameValuePair("tradeId", String.valueOf(client.getBusiMap().get("tradeId"))));
		this.body.add(new BasicNameValuePair("tradeTypeCode", "0120"));
		this.body.add(new BasicNameValuePair("TRADE_ID_MORE_STR", String.valueOf(client.getBusiMap().get("tradeId"))));
		this.body.add(new BasicNameValuePair("SERIAL_NUMBER_STR", String.valueOf(super.client.getBusiMap().get("SERIAL_NUMBER"))+","));
		this.body.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "120,"));
		this.body.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
		this.body.add(new BasicNameValuePair("strNetType", "0050"));
		this.body.add(new BasicNameValuePair("globalPageName", globalPageName));

		return response.getResponse();
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?swallow/personalserv.dealtradefee.DealTradeFee/dealAfterOrderSubmit/1";
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
