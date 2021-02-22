package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;

public class CreateCustomPage1Request extends AbsHttpRequest<ParameterData,Boolean> {
	
	public CreateCustomPage1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(ParameterData data) {
		this.body.add(new BasicNameValuePair("random",data.getRandom()));
		this.body.add(new BasicNameValuePair("staffId",data.getStaffId()));
		this.body.add(new BasicNameValuePair("departId",data.getDepartId()));
		this.body.add(new BasicNameValuePair("subSysCode","custserv"));
		this.body.add(new BasicNameValuePair("eparchyCode",data.getEparchyCode()));
		return true;
	}

	@Override
	protected Boolean unpack(PageHttpResponse response) {
		try{
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
//			logger.info(respXml);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
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
