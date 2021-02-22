package com.zte.cbss.autoprocess.request;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.message.BasicNameValuePair;
import com.zte.cbss.autoprocess.AbsHttpRequest;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.PageHttpResponse;
import com.zte.cbss.autoprocess.model.data.IndexData;

public class IndexPage1Request extends AbsHttpRequest<IndexData,Boolean> {
	
	public IndexPage1Request(HttpLoginClient client) {
		super(client);
	}

	@Override
	protected boolean pack(IndexData data) {
		try{
			/*this.body.add(new BasicNameValuePair("service",data.getService()));
			this.body.add(new BasicNameValuePair("Form0",data.getForm0()));
			this.body.add(new BasicNameValuePair("LOGIN_TYPE",data.getLOGIN_TYPE()));
			this.body.add(new BasicNameValuePair("STAFF_ID",data.getSTAFF_ID()));
			this.body.add(new BasicNameValuePair("LOGIN_PASSWORD",data.getLOGIN_PASSWORD()));
			this.body.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE",data.getLOGIN_PROVINCE_CODE()));*/
			
			Field[] fields = IndexData.class.getDeclaredFields();
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
			
			Pattern pat = Pattern.compile("<meta id=\"pagecontext\" pagename=\"Main\" "
					+ "productMode='([^']*)' "			//1
					+ "staffId='([^']*)' "				//2
					+ "staffName='([^']*)' "			//3
					+ "deptId='([^']*)' "				//4
					+ "deptCode='([^']*)' "				//5
					+ "deptName='www.10010.com' "	
					+ "cityId='([^']*)' "				//6
					+ "cityName='([^']*)' "				//7
					+ "areaCode='([^']*)' "				//8
					+ "areaName='([^']*)' "				//9
					+ "epachyId='([^']*)' "				//10
					+ "epachyName='([^']*)' "			//11
					+ "loginEpachyId='([^']*)' "		//12	
					+ "version='([^']*)' "				//13
					+ "provinceId='([^']*)' "			//14
					+ "subSysCode='([^']*)' "			//15
					+ "contextName='essframe' "		
					+ "loginCheckCode='([^']*)' "		//16
					+ "loginProvinceId='([^']*)'  />");	//17
			
			Matcher mat = pat.matcher(respXml);
			if(mat.find()){
				this.client.getParam().setDepartId(mat.group(4));
				this.client.getParam().setEparchyCode(mat.group(10));
				this.client.getParam().setLOGIN_CHECK_CODE(mat.group(16));
			}
			
//			logger.info("=====》 开始登陆CBSS系统【post账号密码到分域登录】 《=====");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUrl() {
		return "https://gd.cbss.10010.com/essframe";
	}

	@Override
	public String getReferer() {
		return "https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin";
	}

	@Override
	public boolean isXMLHttpRequest() {
		return false;
	}

	@Override
	public boolean isPost() {
		return true;
	}

}
