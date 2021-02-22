/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.SPDataSubmit_4;

/**
 * @author ZX
 * SPBuySubmit.java
 * 2015-1-17
 */
public class SPBuySubmit_4 extends AbsHttpRequest<SPDataSubmit_4, String>{

	public SPBuySubmit_4(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean pack(SPDataSubmit_4 data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("tradeId", data.getTradeId()));
		this.body.add(new BasicNameValuePair("tradeTypeCode", data.getTradeTypeCode()));
		this.body.add(new BasicNameValuePair("TRADE_ID_MORE_STR", data.getTRADE_ID_MORE_STR()));
		this.body.add(new BasicNameValuePair("SERIAL_NUMBER_STR", data.getSERIAL_NUMBER_STR()));
		this.body.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", data.getTRADE_TYPE_CODE_STR()));
		this.body.add(new BasicNameValuePair("NET_TYPE_CODE_STR", data.getNET_TYPE_CODE_STR()));
		this.body.add(new BasicNameValuePair("strNetType", data.getStrNetType()));
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
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.dealtradefee.DealTradeFee/dealAfterOrderSubmit/1";
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
