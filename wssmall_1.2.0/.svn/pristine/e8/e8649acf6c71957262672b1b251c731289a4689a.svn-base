/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.SPDataSearch;

/**
 * @author ZX
 * SPBuySearch.java
 * 2015-1-17
 */
public class SPBuySearch extends AbsHttpRequest<SPDataSearch, Boolean>{
	private static Logger logger = Logger.getLogger(SPBuySearch.class);
	public SPBuySearch(HttpLoginClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean pack(SPDataSearch data) {
		// TODO Auto-generated method stub
		this.body.add(new BasicNameValuePair("globalPageName", data.getGlobalPageName()));
		this.body.add(new BasicNameValuePair("serialNumber", data.getSerialNumber()));
		this.body.add(new BasicNameValuePair("netTypeCode", data.getNetTypeCode()));
		this.body.add(new BasicNameValuePair("rightCode", data.getRightCode()));
		this.body.add(new BasicNameValuePair("touchId", data.getTouchId()));
		return true;
	}
	
	@Override
	protected Boolean unpack(PageHttpResponse response) {
		try{
			String respXml = response.getResponse();
    		logger.info("========== SP订购模拟【查询】==========");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv?service=swallow/pub.chkcust.MainChkCust/queryCustAuth/1";
	}

	@Override
	public String getReferer() {
		// TODO Auto-generated method stub
		return "https://gd.cbss.10010.com/custserv?service=direct/1/personalserv.platformtrade.SpTrade/$MobTrade.$Form$0";
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
