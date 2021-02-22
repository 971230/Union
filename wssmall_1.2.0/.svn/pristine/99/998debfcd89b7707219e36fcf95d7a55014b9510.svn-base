/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.SPData;


/**
 * @author ZX
 * @since SPBuyInfo.java
 * @version 2015-1-16
 */
public class SPBuy extends AbsHttpRequest<SPData, String> {
	private static Logger logger = Logger.getLogger(SPBuy.class);
	public SPBuy(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean pack(SPData data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("Base", data.getBase()));
		this.body.add(new BasicNameValuePair("Ext", data.getExt()));
		this.body.add(new BasicNameValuePair("globalPageName", data.getGlobalPageName()));
		return true;
	}

	@Override
	protected String unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
    		logger.info("========= SP订购，模拟【确定】==========");
    		logger.info(respXml);
			return respXml;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv?service=swallow/personalserv.platformtrade.SpTrade/submitMobTrade/1";
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
