package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.PageData;

public class IndexPage2_6Request extends AbsHttpRequest<PageData,Boolean> {

	private PageData data;
	
	public IndexPage2_6Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PageData data) {
		this.data = data;
		this.body.add(new BasicNameValuePair("listener",data.getListener()));
		this.body.add(new BasicNameValuePair("needNotify",data.getNeedNotify()));
		this.body.add(new BasicNameValuePair("staffId",data.getStaffId()));
		this.body.add(new BasicNameValuePair("departId",data.getDepartId()));
		this.body.add(new BasicNameValuePair("subSysCode","BSS"));
		this.body.add(new BasicNameValuePair("eparchyCode",data.getEparchyCode()));
		return true;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		try{
			this.data.setReferer(response.getUrl()); //赋值Referer
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
//			logger.info("======> 登录到框架页面 <======");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/essframe?service=page/component.Navigation";
	}

	@Override
	public String getReferer() {
		return "https://gd.cbss.10010.com/essframe";
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
