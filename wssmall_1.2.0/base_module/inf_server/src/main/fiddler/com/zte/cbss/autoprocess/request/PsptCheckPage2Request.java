package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.PsptCheckData;

/**
 * 证件检查页面请求
 * @author 张浩
 * @version 1.0.0
 */
public class PsptCheckPage2Request extends AbsHttpRequest<PsptCheckData,PsptCheckData>{
	
	private PsptCheckData data;

	public PsptCheckPage2Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(PsptCheckData data) {
		this.data = data;
		this.body.add(new BasicNameValuePair("listener","init"));
		this.body.add(new BasicNameValuePair("checkType",data.getCheckType()));
		this.body.add(new BasicNameValuePair("pspId",data.getPsptId()));
		this.body.add(new BasicNameValuePair("custName",data.getCustomName()));
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
//			logger.info(respXml);
			return this.data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=page/popupdialog.PersonCardReaderSX";
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
