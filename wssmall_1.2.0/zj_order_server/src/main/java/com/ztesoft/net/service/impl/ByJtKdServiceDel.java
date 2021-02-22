package com.ztesoft.net.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.phw.eop.api.ApiException;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import org.phw.eop.api.EopReq;
import org.phw.eop.api.EopRsp;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.DateUtil;

public class ByJtKdServiceDel{
	private static Logger logger = Logger.getLogger(ByJtKdServiceDel.class);
	Connection conn;
	  private PreparedStatement ps = null;
	  private ResultSet rs = null;
	
    public void execute(String ids) throws Exception {
    	
    	JSONObject baseReq = new JSONObject();
		 JSONObject req = new JSONObject();
		 JSONObject reqObj = new JSONObject();
		 JSONObject head = new JSONObject();
		 JSONObject body = new JSONObject();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		 //String provinceCode = "36";//cacheUtil.getConfigInfo("KDYYD_PROVINCECODE");
		 String appcode = cacheUtil.getConfigInfo("KDYYD_APP_CODE");/*"BA3CC5E3241A42349D2B2173309E6873";*/
		 String url = cacheUtil.getConfigInfo("KDYYD_URL");/*"http://132.151.42.121:8006/api/rest";*/ //"http://132.38.2.88:80/zop/broadband/bespeak/syncState/v1";
		 String HMAC = cacheUtil.getConfigInfo("KDYYD_HMAC");/*"GilDLPDb5BYqMbayMiZGTUoBfp0GQCZqwY9jVmOs52ToDttpaoQY7A3xP0o23jgzvxYrNHwhZfnv6QemLsownw==";*/  //"mxDQfaSvJ2pEPk8mMxD76/TzlKXYGweqiDxaeBXE53SrhhmP61p47PwqCFFt8bT5qSNJijBYMhVEMTOCxb0zBw==";
		 String AES = cacheUtil.getConfigInfo("KDYYD_AES");/*"qEnKWukeREV6Or4EDWU4dw==";*/  //"lmFhZj5nPJQ8iQLigPjPwg==";
		 String KDYYD_PARAMETER = cacheUtil.getConfigInfo("KDYYD_DEL_PARAMETER"); 
		 JSONObject PARAMETER = JSONObject.fromObject(KDYYD_PARAMETER);
		 String timestamp = DateUtil.getTime6();
		 String uuid = String.valueOf(UUID.randomUUID());
		 ids = ids.replace(" ", "");
		 body.put("id", ids);
		 head.put("timestamp", timestamp);
		 head.put("uuid", uuid);
		 head.put("sign", ByJtKdBo.makeSign(appcode,timestamp,uuid,HMAC));
		
		 reqObj.put("head", head);
		 reqObj.put("body", body);
		 req.put("appCode", appcode);
		 req.put("reqObj", reqObj);
		 PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			postMethod.setRequestHeader("Accept-Language", "us");
			postMethod.setRequestHeader("CONN_type", "SSL");
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
			String busiContent = JSON.toJSONString(req);
			String method = PARAMETER.getString("method");
		      String version = PARAMETER.getString("version");
		      String access_token = PARAMETER.getString("access_token");
		      String app_id = PARAMETER.getString("app_id");
		      String sign_method = PARAMETER.getString("sign_method");
		      String secret = PARAMETER.getString("secret");
		      String charset = PARAMETER.getString("charset");
			/*String method = "zop.user.yuyueData.back";
		      String version = "2.0";
		      String access_token = "Y2IxYmM5ZThhMzMwNjJkMzUxMDJmZjMyZmNiYzUzODg=";
		      String app_id = "81190";
		      String sign_method = "md5";
		      String secret = "3f0f5398dcabd4c9";
		      String charset = "utf-8";*/
			String sign = ByJtKdBo.getSign(busiContent, secret, sign_method, charset);
			//请求map
			Map reqMap = new HashMap();
			//AIP 必要参数
			baseReq.put("sign", sign);
			baseReq.put("app_id", app_id);
			baseReq.put("access_token", access_token);
			baseReq.put("method", method);
			baseReq.put("version", version);
			baseReq.put("timestamp", DateFormatUtils.formatDate("yyyyMMddHHmmss"));
			baseReq.put("format", "json");
			baseReq.put("sign_method", sign_method);
			baseReq.put("content", busiContent);
			String requestStr=JSON.toJSONString(baseReq);
	        RequestEntity entity = new StringRequestEntity(requestStr,"application/json","UTF-8");
	        PostMethod postmethod = new PostMethod(url);
	        postmethod.setRequestEntity(entity);
	        postmethod.setRequestHeader("Content-Type","application/json;charset=UTF-8");
	        new HttpClient().executeMethod(postmethod);
	        byte[] contentNew = postmethod.getResponseBody();
	        String str = new String(contentNew, "utf-8");
	        JSONObject resp = JSONObject.fromObject(str);
	        JSONObject result = resp.getJSONObject("result");
	        //System.out.println(result.toString());
	        String  rspCode = result.get("rspCode").toString();
	        if(rspCode.equals("0000")){
	        	modStatus(ids);
	        	
	        }
    }
  public void modStatus(String ids){
	  //String idarr[] =ids.split(",");
	  ids = ids.replace(" ", "");
	  ids = ids.replace(",", "','");
	  ids = "'"+ids+"'";
  	  String update_sql = " update es_kdyyd_order set del_status='1' where id in ("+ids+") ";
  	  //System.out.println(update_sql);
  	  IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
  	  baseDaoSupport.execute(update_sql, null);

  }
	
	/**
	 * 测试代码
	 * @param args
	 */
    
	public static void main(String[] args){
		/*try {
			new ByJtKdServiceDel().execute("9917102322271455");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		new ByJtKdServiceDel().modStatus("123,321");
	}



}