package com.zte.cbss.autoprocess.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.model.data.CreateCustomPageData;

public class CreateCustomPage2Request extends AbsHttpRequest<CreateCustomPageData,CreateCustomPageData> {
	
	CreateCustomPageData data;
	
	public CreateCustomPage2Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(CreateCustomPageData data) {
		this.data = data;
		ParameterData param = data.getParam();
		this.body.add(new BasicNameValuePair("listener","initMobTrade"));
		this.body.add(new BasicNameValuePair("PSPT_TYPE_CODE",param.getBill().getIdTypeCode()));
		this.body.add(new BasicNameValuePair("PSPT_ID",param.getBill().getPsptId()));
		this.body.add(new BasicNameValuePair("CUST_NAME",""));
		this.body.add(new BasicNameValuePair("PSPT_END_DATE",""));
		this.body.add(new BasicNameValuePair("PSPT_ADD",""));
		this.body.add(new BasicNameValuePair("SEX",""));
		this.body.add(new BasicNameValuePair("BIRTHDAY",""));
		this.body.add(new BasicNameValuePair("FOLK_CODE",""));
		this.body.add(new BasicNameValuePair("TAG","1"));
		this.body.add(new BasicNameValuePair("staffId",param.getStaffId()));
		this.body.add(new BasicNameValuePair("departId",param.getDepartId()));
		this.body.add(new BasicNameValuePair("subSysCode","CSM"));
		this.body.add(new BasicNameValuePair("eparchyCode",param.getEparchyCode()));
		this.body.add(new BasicNameValuePair("random",param.getRandom()));
		return true;
	}

	@Override
	protected CreateCustomPageData unpack(PageHttpResponse response) {
		try{
//			logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
			String respXml = response.getResponse();
			//logger.info(respXml);
			
			Pattern pat =  Pattern.compile("id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^\"]*)\"/>");
			Matcher mat = pat.matcher(respXml);
			if(mat.find()){
				this.data.setBaseStr(mat.group(1));
			}
			pat = Pattern.compile("&quot;CUST_ID&quot;:&quot;([^&]*)&quot;");
			mat = pat.matcher(respXml);
			if(mat.find()){
				this.data.setCUST_ID(mat.group(1));
			}
			return this.data;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/custserv?service=page/personalserv.createcusttrade.CreateCust";
	}

	@Override
	public String getReferer() {
		String param = this.client.getRelererParam("page/component.Agent");
		return "https://gd.cbss.10010.com/custserv?service=page/component.Agent"+param;
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
