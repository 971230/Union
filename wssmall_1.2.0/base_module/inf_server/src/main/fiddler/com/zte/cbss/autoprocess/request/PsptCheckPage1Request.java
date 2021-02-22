package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.PsptCheckData;

public class PsptCheckPage1Request extends AbsHttpRequest<PsptCheckData,PsptCheckData> {
	private static Logger logger = Logger.getLogger(PsptCheckPage1Request.class);
	private PsptCheckData data;
	
	public PsptCheckPage1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PsptCheckData data) {
		this.data = data;
		this.body.add(new BasicNameValuePair("random",data.getRandom()));
		this.body.add(new BasicNameValuePair("staffId",data.getStaffId()));
		this.body.add(new BasicNameValuePair("departId",data.getDepartId()));
		this.body.add(new BasicNameValuePair("subSysCode",data.getSubSysCode()));
		this.body.add(new BasicNameValuePair("eparchyCode",data.getEparchyCode()));
		return true;
	}

	@Override
	protected PsptCheckData unpack(PageHttpResponse response) {
		try{
			this.data.setReferer(response.getUrl());
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
			logger.info("============ 系统校验开始   ============");
			return this.data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=page/component.Agent";
	}

	@Override
	public String getReferer() {
		return null;
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
