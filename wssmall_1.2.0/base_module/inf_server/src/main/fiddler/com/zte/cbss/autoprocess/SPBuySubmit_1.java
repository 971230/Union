/**
 * 
 */
package com.zte.cbss.autoprocess;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.model.data.SPDataSubmit_1;

/**
 * @author ZX
 * SPBuySubmit_1.java
 * 2015-1-19
 */
public class SPBuySubmit_1 extends AbsHttpRequest<SPDataSubmit_1, String> {
	private static Logger logger = Logger.getLogger(SPBuySubmit_1.class);
	public SPBuySubmit_1(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean pack(SPDataSubmit_1 data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("globalPageName", data.getGlobalPageName()));
		return true;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		// TODO Auto-generated method stub
		String respXml = response.getResponse();
		logger.info(respXml);
		return respXml;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.dealtradefee.DealTradeFee/getPayCodeMutex/1";
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
