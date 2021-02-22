package com.zte.cbss.autoprocess;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.cbss.autoprocess.model.LoginInfo;
import com.zte.cbss.autoprocess.model.data.IndexData;
import com.zte.cbss.autoprocess.model.data.LoginData;
import com.zte.cbss.autoprocess.model.data.PageData;
import com.zte.cbss.autoprocess.request.IndexPage1Request;
import com.zte.cbss.autoprocess.request.IndexPage2_1Request;
import com.zte.cbss.autoprocess.request.IndexPage2_3Request;
import com.zte.cbss.autoprocess.request.IndexPage2_3_1Request;
import com.zte.cbss.autoprocess.request.IndexPage2_3_2Request;
import com.zte.cbss.autoprocess.request.IndexPage2_6Request;
import com.zte.cbss.autoprocess.request.IndexPage2_6_1Request;
import com.zte.cbss.autoprocess.request.LoginRequest;

public class HttpLoginClientPool {
	
	private static Logger log = LoggerFactory.getLogger(HttpLoginClientPool.class);
	
	private static HttpLoginClient client;
	
	public static HttpLoginClient add(LoginInfo info){
		try {
			client = new HttpLoginClient();
			client.getParam().setStaffId(info.getCbssAccount());
			client.getParam().setProvinceId(info.getProvinceCode());
			//访问登陆页面
			//get得cbss统一登录页面
			PageHttpResponse response = client.send("https://cbss.10010.com/essframe",null,false,false,null);
			if(response != null){
//				logger.info(response.getResponse().getFirstHeader("Set-Cookie"));
				LoginRequest loginRequest = new LoginRequest(client);
				LoginData loginData = new LoginData();
				loginData.setSTAFF_ID(info.getCbssAccount());
				loginData.setLOGIN_PASSWORD(info.getCbssPassword());
				loginData.setLOGIN_PROVINCE_CODE(info.getProvinceCode());
				//post登录信息到gd的分域的登录页，获得分域登录页信息
				if(loginRequest.send(loginData)){
					//访问首页，主要获取Cookie设置
					IndexData indexData = new IndexData();
					indexData.setSTAFF_ID(info.getCbssAccount());
					indexData.setLOGIN_PASSWORD(info.getCbssPassword());
					//post账号密码到分域登录
					new IndexPage1Request(client).send(indexData);
					PageData pageData1 = new PageData(client.getParam());
					//get成功登录用户的主页面信息
					new IndexPage2_1Request(client).send(pageData1);
					//功能页面
					PageData pageData3 = new PageData(client.getParam());
					//
					new IndexPage2_3Request(client).send(pageData3);
					//get
					new IndexPage2_3_1Request(client).send(pageData3);
					//get
					new IndexPage2_3_2Request(client).send(pageData3);
					//框架页面
					PageData pageData6 = new PageData(client.getParam());
					//get
					new IndexPage2_6Request(client).send(pageData6);
					//
					new IndexPage2_6_1Request(client).send(pageData6);
					return client;
				}
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
			log.info("Login in failed! Thread:"+Thread.currentThread().getName(), e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.info("Login in failed! Thread:"+Thread.currentThread().getName(), e);
		}
		return null;
	}
}
