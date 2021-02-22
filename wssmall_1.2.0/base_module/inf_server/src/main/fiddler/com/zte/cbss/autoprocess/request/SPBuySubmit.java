/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.SPDataSubmit;

/**
 * @author ZX
 * SPBuySubmit.java
 * 2015-1-17
 */
public class SPBuySubmit extends AbsHttpRequest<SPDataSubmit, String>{

	public SPBuySubmit(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean pack(SPDataSubmit data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("cancelTag", String.valueOf(data.isCancelTag())));
		this.body.add(new BasicNameValuePair("funcType", data.getFuncType()));
		this.body.add(new BasicNameValuePair("dataType", data.getDataType()));
		this.body.add(new BasicNameValuePair("tradeMain", data.getTradeMain()));
		this.body.add(new BasicNameValuePair("fees", data.getFees()));
		this.body.add(new BasicNameValuePair("unChargedfees", data.getUnChargedfees()));
		this.body.add(new BasicNameValuePair("feePayMoney", data.getFeePayMoney()));
		this.body.add(new BasicNameValuePair("feeCheck", data.getFeeCheck()));
		this.body.add(new BasicNameValuePair("feePos", data.getFeePos()));
		this.body.add(new BasicNameValuePair("base", data.getBase()));
		this.body.add(new BasicNameValuePair("CASH", data.getCASH()));
		this.body.add(new BasicNameValuePair("SEND_TYPE", data.getSEND_TYPE()));
		this.body.add(new BasicNameValuePair("TRADE_ID", data.getTRADE_ID()));
		this.body.add(new BasicNameValuePair("TRADE_ID_MORE_STR", data.getTRADE_ID_MORE_STR()));
		this.body.add(new BasicNameValuePair("SERIAL_NUMBER_STR", data.getSERIAL_NUMBER_STR()));
		this.body.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", data.getTRADE_TYPE_CODE_STR()));
		this.body.add(new BasicNameValuePair("NET_TYPE_CODE_STR", data.getNET_TYPE_CODE_STR()));
		this.body.add(new BasicNameValuePair("DEBUTY_CODE", data.getDEBUTY_CODE()));
		this.body.add(new BasicNameValuePair("IS_NEED_WRITE_CARD", String.valueOf(data.isIS_NEED_WRITE_CARD())));
		this.body.add(new BasicNameValuePair("WRAP_TRADE_TYPE", data.getWRAP_TRADE_TYPE()));
		this.body.add(new BasicNameValuePair("CUR_TRADE_IDS", data.getCUR_TRADE_IDS()));
		this.body.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES", data.getCUR_TRADE_TYPE_CODES()));
		this.body.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS", data.getCUR_SERIAL_NUMBERS()));
		this.body.add(new BasicNameValuePair("CUR_NET_TYPE_CODES", data.getCUR_NET_TYPE_CODES()));
		this.body.add(new BasicNameValuePair("isAfterFee", data.getIsAfterFee()));
		this.body.add(new BasicNameValuePair("globalPageName", data.getGlobalPageName()));
		return true;
	}
	
	@Override
	protected String unpack(PageHttpResponse response) {
		// TODO Auto-generated method stub
		String respXml = response.getResponse();
//		logger.info(respXml);
		return respXml;
	}
	
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1";
	}

	@Override
	public String getReferer() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv";
	}

	@Override
	public boolean isXMLHttpRequest() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isPost() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
