package com.zte.cbss.autoprocess.request;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.PageData;

public class IndexPage2_1Request extends AbsHttpRequest<PageData,Boolean> {

	PageData data;
	
	public IndexPage2_1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PageData data) {
		this.data = data;
		return true;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		try{
			this.data.setReferer(response.getUrl());
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
//    		logger.info("======> 成功登录用户的主页面信息 <======");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/essframe?service=page/Header&LOGIN_LOG_ID=null";
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
