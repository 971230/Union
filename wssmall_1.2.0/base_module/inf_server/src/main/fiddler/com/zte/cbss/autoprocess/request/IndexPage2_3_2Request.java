package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.PageData;

public class IndexPage2_3_2Request extends AbsHttpRequest<PageData,Boolean> {

	PageData data;
	
	public IndexPage2_3_2Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PageData data) {
		this.data = data;
		this.body.add(new BasicNameValuePair("listener",""));
		this.body.add(new BasicNameValuePair("staffId",data.getStaffId()));
		this.body.add(new BasicNameValuePair("departId",data.getDepartId()));
		this.body.add(new BasicNameValuePair("subSysCode","custserv"));
		this.body.add(new BasicNameValuePair("eparchyCode",data.getEparchyCode()));
		return true;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		try{
			this.data.setReferer(response.getUrl());
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
//     		logger.info("");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		String BSS_CUSTSERV_JSESSIONID = this.client.getCookie("BSS_CUSTSERV_JSESSIONID");
		return "https://gd.cbss.10010.com/custserv;BSS_CUSTSERV_JSESSIONID="+BSS_CUSTSERV_JSESSIONID+"?service=page/pub.chkcust.MainChkCust";
	}

	@Override
	public String getReferer() {
		return this.data.getReferer();
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
