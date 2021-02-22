package com.zte.cbss.autoprocess.request;

import java.lang.reflect.Field;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.LoginData;

public class LoginRequest extends AbsHttpRequest<LoginData,Boolean>{
	private static Logger logger = Logger.getLogger(LoginRequest.class);
	public LoginRequest(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(LoginData data) {
		try{
			/*this.body.add(new BasicNameValuePair("LOGIN_PROVINCE_REDIRECT_URL",data.getLOGIN_PROVINCE_REDIRECT_URL()));
			this.body.add(new BasicNameValuePair("AUTH_TYPE",data.getAUTH_TYPE()));
			this.body.add(new BasicNameValuePair("STAFF_ID",data.getSTAFF_ID()));
			this.body.add(new BasicNameValuePair("LOGIN_PASSWORD",data.getLOGIN_PASSWORD()));
			this.body.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE",data.getLOGIN_PROVINCE_CODE()));*/
			
			Field[] fields = LoginData.class.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				Object obj = fields[i].get(data);
				if(obj != null){
					this.body.add(new BasicNameValuePair(fields[i].getName(),obj.toString()));
				}else{
					this.body.add(new BasicNameValuePair(fields[i].getName(),""));
				}
			}
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
			logger.info("============ CBSS开始登陆  ============");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getReferer() {
		return "https://cbss.10010.com/essframe";
	}

	@Override
	public boolean isXMLHttpRequest() {
		return false;
	}

	@Override
	public boolean isPost() {
		return true;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin";
	}

}
