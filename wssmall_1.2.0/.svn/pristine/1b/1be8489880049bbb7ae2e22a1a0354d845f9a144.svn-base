package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;

public class MobileProductPage2_4Request extends AbsHttpRequest<MobileNetworkServiceHandleReq, String> {
	private static Logger logger = Logger.getLogger(MobileProductPage2_4Request.class);

	private final String globalPageName = "personalserv.dealtradefee.DealTradeFee";

	public MobileProductPage2_4Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(MobileNetworkServiceHandleReq data) {
		this.body.add(new BasicNameValuePair("cancelTag", "false"));
		this.body.add(new BasicNameValuePair("funcType", "0"));
		this.body.add(new BasicNameValuePair("dataType", "0"));
		String tradeMain = "[{\"TRADE_ID\": \""+client.getBusiMap().get("tradeId")+"\", \"TRADE_TYPE\": \"移网产品/服务变更\", \"SERIAL_NUMBER\": \""+client.getBusiMap().get("SERIAL_NUMBER")+"\", \"TRADE_FEE\": \"0.00\", \"CUST_NAME\": \"罗振廷\", \"CUST_ID\": \""+client.getParam().getCustomInfo().getCustId()+"\", \"USER_ID\": \""+client.getParam().getUserId()+"\", \"ACCT_ID\": \""+client.getParam().getAcctId()+"\", \"NET_TYPE_CODE\": \"50\", \"TRADE_TYPE_CODE\": \"120\"}]";
		this.body.add(new BasicNameValuePair("tradeMain", tradeMain));

		this.body.add(new BasicNameValuePair("fees", "[]"));
		this.body.add(new BasicNameValuePair("unChargedfees", "[]"));
		this.body.add(new BasicNameValuePair("feePayMoney", "[]"));
		this.body.add(new BasicNameValuePair("feeCheck", "[]"));
		this.body.add(new BasicNameValuePair("feePos", "[]"));

		String base = "{\"prepayTag\": \"1\", \"tradeTypeCode\": \"0120\", \"strisneedprint\": \"1\", \"serialNumber\": \""+client.getBusiMap().get("SERIAL_NUMBER")+"\", \"tradeReceiptInfo\": \"[{\"RECEIPT_INFO5\":\"\",\"RECEIPT_INFO2\":\"\",\"RECEIPT_INFO1\":\"\",\"RECEIPT_INFO4\":\"\",\"RECEIPT_INFO3\":\"\"}]\", \"netTypeCode\": \"0050\"}";
		this.body.add(new BasicNameValuePair("feePos", base));

		this.body.add(new BasicNameValuePair("CASH", "0.00"));
		this.body.add(new BasicNameValuePair("SEND_TYPE", "0"));
		this.body.add(new BasicNameValuePair("TRADE_ID", String.valueOf(client.getBusiMap().get("tradeId"))));
		this.body.add(new BasicNameValuePair("TRADE_ID_MORE_STR", String.valueOf(client.getBusiMap().get("tradeId"))));
		this.body.add(new BasicNameValuePair("SERIAL_NUMBER_STR", client.getBusiMap().get("SERIAL_NUMBER")+","));
		this.body.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "120,"));
		this.body.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
		this.body.add(new BasicNameValuePair("DEBUTY_CODE", ""));
		this.body.add(new BasicNameValuePair("IS_NEED_WRITE_CARD", "false"));
		this.body.add(new BasicNameValuePair("WRAP_TRADE_TYPE", "tradeType"));
		this.body.add(new BasicNameValuePair("CUR_TRADE_IDS", ""));
		this.body.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES", ""));
		this.body.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS", ""));
		this.body.add(new BasicNameValuePair("CUR_NET_TYPE_CODES", ""));
		this.body.add(new BasicNameValuePair("isAfterFee", ""));
		

		this.body.add(new BasicNameValuePair("globalPageName", globalPageName));
		return true;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try {
			String respXml = response.getResponse();
			// is_need_write_crerd
			logger.info("is_need_write_crerd"+respXml);
			return respXml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1";
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
