package com.zte.cbss.autoprocess.request;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;

public class DealGroupRelationRequest extends AbsHttpRequest<ParameterData,Boolean>{

	public DealGroupRelationRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(ParameterData data) {
		try{
			this.body.add(new BasicNameValuePair("listener","init"));
			this.body.add(new BasicNameValuePair("staffId",data.getStaffId()));
			this.body.add(new BasicNameValuePair("departId",data.getDepartId()));
			this.body.add(new BasicNameValuePair("subSysCode","custserv"));
			this.body.add(new BasicNameValuePair("eparchyCode",data.getEparchyCode()));
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
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
		return "https://gd.cbss.10010.com/custserv?service=page/groupserv.dealgrouprelation.DealGroupRelation";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/personalserv.createuser.CreateUser");
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.createuser.CreateUser"+param;
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
