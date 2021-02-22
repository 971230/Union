/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.SPDataSubmit_3;

/**
 * @author ZX
 * SPBuySubmit.java
 * 2015-1-17
 */
public class SPBuySubmit_3 extends AbsHttpRequest<SPDataSubmit_3, String>{

	public SPBuySubmit_3(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean pack(SPDataSubmit_3 data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("base", data.getBase()));
		this.body.add(new BasicNameValuePair("TRADE_ID", data.getTRADE_ID()));
		this.body.add(new BasicNameValuePair("netCode", data.getNetCode()));
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
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.dealtradefee.DealTradeFee/archiveMphoneNo/1";
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
