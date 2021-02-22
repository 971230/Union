package com.ztesoft.test.http.base;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztesoft.api.ApiException;
import com.ztesoft.api.Constants;
import com.ztesoft.api.internal.utils.RequestParametersHolder;
import com.ztesoft.api.internal.utils.WebUtils;
import com.ztesoft.api.internal.utils.ZteHashMap;
import com.ztesoft.api.internal.utils.ZteUtils;
import commons.CommonTools;

public class HttpClientTest {

	public ApplicationContext context = null;
    public Logger logger = LoggerFactory.getLogger(getClass());
    public String url="http://localhost:8083/rop/router";
    public String appKey="wssmall_fj",secret="123456"; //wssmall_fj

    @Before
    public void setUp() throws Exception {
    	  String configs[]=new String[]{"classpath:spring/*.xml,classpath:dubbo/reference/*.xml"};
          context = new ClassPathXmlApplicationContext(configs);
          context.containsBean("apiContextHolder");
    }
    

	public static String  doPost(String url,String param_json ,String method,String appKey,String appSecret,String session)throws ApiException {
		RequestParametersHolder requestHolder = new RequestParametersHolder();
		ZteHashMap appParams = new ZteHashMap();
		appParams.put("param_json", param_json);
		requestHolder.setApplicationParams(appParams);

		// 添加协议级请求参数
		ZteHashMap protocalMustParams = new ZteHashMap();
		protocalMustParams.put("method", method);
		protocalMustParams.put("version", "1.0");
		protocalMustParams.put("appKey", appKey);
		protocalMustParams.put("locale", "zh_CN");
		protocalMustParams.put("timestamp", new Date(System.currentTimeMillis()));
		
		// 因为沙箱目前只支持时间字符串，所以暂时用Date格式
		requestHolder.setProtocalMustParams(protocalMustParams);

		ZteHashMap protocalOptParams = new ZteHashMap();
		protocalOptParams.put("format", "httpjson");
		protocalOptParams.put("sign_method", "rop");
		protocalOptParams.put("sessionId", session);
		protocalOptParams.put("partner_id","top-sdk-java-20131225");
		requestHolder.setProtocalOptParams(protocalOptParams);

		// 添加签名参数
		// try {

			protocalMustParams.put("sign", ZteUtils.encryptSHA(appSecret, requestHolder));

		StringBuffer urlSb = new StringBuffer(url);
		try {
			String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(), Constants.CHARSET_UTF8);
			String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), Constants.CHARSET_UTF8);
			urlSb.append("?");
			urlSb.append(sysMustQuery);
		
			if (sysOptQuery != null & sysOptQuery.length() > 0) {
				urlSb.append("&");
				urlSb.append(sysOptQuery);
			}
			
		} catch (IOException e) {
			throw new ApiException(e);
		}
		String rsp = null;
		try {
			// 是否需要上传文件
			int connectTimeout =3000;
			int readTimeout = 15000;
			rsp = WebUtils.doPost(urlSb.toString(), appParams,Constants.CHARSET_UTF8, connectTimeout, readTimeout,new HashMap<String, String>());
		} catch (IOException e) {
			throw new ApiException(e);
		}
		Map httpReqMapNew = CommonTools.jsonToBean(rsp, Map.class);
		return rsp;
	}

}
