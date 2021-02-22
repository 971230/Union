package com.ztesoft.net.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.phw.eop.api.ApiException;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import org.phw.eop.api.EopReq;
import org.phw.eop.api.EopRsp;
import org.phw.eop.api.internal.util.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.exception.HttpTimeOutException;


public class ByJtKdBo implements IAction{
	private static Logger logger = Logger.getLogger(ByJtKdBo.class);
	private static PreparedStatement ps = null;
	static Connection conn;
	@Override
	public int perform(DynamicDict aDynamicDict) throws FrameException {
		// TODO Auto-generated method stub
		return 0;
	}
public static void searchHb () throws Exception {
		
		/*String url = "http://132.151.42.121:8006/api/rest";//测试地址，生产地址见下面
	    //String url = "http://eop.mall.10010.com/phw-eop";//测试地址，生产地址见下面
		String appcode = "E50C485B5E2F40A6BE0D65D9829A0626"; //联通商城提供
		String signKey = "ae0aiCqyH9MBsDJthGBAEp8uQj8R8Hj2twLANixIGA4SWsFNqNcnnRizG2eV+PWOiAkS01c9xIXGBgz3Q/0AlA==";//联通商城提供
		String eopaction="broadband.message.get"; //宽带消息获取private static final String HMAC = "mxDQfaSvJ2pEPk8mMxD76/TzlKXYGweqiDxaeBXE53SrhhmP61p47PwqCFFt8bT5qSNJijBYMhVEMTOCxb0zBw==";

		EopClient client = new EopClient(url, appcode, signKey);
		client.setSignAlgorithm("HMAC");
		EopReq eopReq = new EopReq(eopaction);
		Map reqMap = new HashMap();
		reqMap.put("provinceCode", "18");
		reqMap.put("timestamp", System.currentTimeMillis());
		eopReq.put("ReqJson", reqMap);
		EopRsp eopRsp = null;
		try {
			  String method = "eop.user.broadband.getmessage";
		      String version = "2.0";
		      String timestamp = DateUtil.getTime5();
		      String access_token = "Y2IxYmM5ZThhMzMwNjJkMzUxMDJmZjMyZmNiYzUzODg=";
		      String app_id = "81190";
		      String sign_method = "md5";
		      String secret = "3f0f5398dcabd4c9";
		      String charset = "utf-8";
		      eopRsp = client.execute(eopReq,method,version,timestamp,access_token,app_id,sign_method,secret,charset);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map ret = eopRsp.getResult();
		String  rret=ret.get("RespBody").toString();
		 logger.info("Map集合大小为："+ret);
		 logger.info("+++++++++++++："+ret.get("RespBody"));*/
		
		 JSONObject baseReq = new JSONObject();
		 JSONObject req = new JSONObject();
		 JSONObject reqObj = new JSONObject();
		 JSONObject head = new JSONObject();
		 JSONObject body = new JSONObject();
		 ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		 String provinceCode = "36";//cacheUtil.getConfigInfo("KDYYD_PROVINCECODE");
		 String appcode = cacheUtil.getConfigInfo("KDYYD_APP_CODE");/*"BA3CC5E3241A42349D2B2173309E6873";*/
		 String url = cacheUtil.getConfigInfo("KDYYD_URL");/*"http://132.151.42.121:8006/api/rest";*/ //"http://132.38.2.88:80/zop/broadband/bespeak/syncState/v1";
		 String HMAC = cacheUtil.getConfigInfo("KDYYD_HMAC");/*"GilDLPDb5BYqMbayMiZGTUoBfp0GQCZqwY9jVmOs52ToDttpaoQY7A3xP0o23jgzvxYrNHwhZfnv6QemLsownw==";*/  //"mxDQfaSvJ2pEPk8mMxD76/TzlKXYGweqiDxaeBXE53SrhhmP61p47PwqCFFt8bT5qSNJijBYMhVEMTOCxb0zBw==";
		 String AES = cacheUtil.getConfigInfo("KDYYD_AES");/*"qEnKWukeREV6Or4EDWU4dw==";*/  //"lmFhZj5nPJQ8iQLigPjPwg==";
		 String KDYYD_PARAMETER = cacheUtil.getConfigInfo("KDYYD_GET_PARAMETER"); 
		 JSONObject PARAMETER = JSONObject.fromObject(KDYYD_PARAMETER);
		 String timestamp = DateUtil.getTime6();
		 String uuid = String.valueOf(UUID.randomUUID());
		 body.put("provinceCode", provinceCode);
		 head.put("timestamp", timestamp);
		 head.put("uuid", uuid);
		 head.put("sign", makeSign(appcode,timestamp,uuid,HMAC));
		
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
			/*String method = "zop.user.order.syncState";
		      String version = "2.0";
		      String access_token = "Y2IxYmM5ZThhMzMwNjJkMzUxMDJmZjMyZmNiYzUzODg=";
		      String app_id = "81190";
		      String sign_method = "md5";
		      String secret = "3f0f5398dcabd4c9";
		      String charset = "utf-8";*/
			String sign = getSign(busiContent, secret, sign_method, charset);
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
	        logger.info(resp.toString());
	        JSONObject result = resp.getJSONObject("result");
	        logger.info(result.toString());
	        String  rspCode = result.get("rspCode").toString();
	        if("0000".equals(rspCode)){
	        	String sstr = result.get("body").toString();
	        	//System.out.println(sstr);
	        	parseJSONWithJSONObject(sstr);
	        }
		 
		 //Map map = json2map(respStr);
		 //saveKDDD(ret,ret,ret);
		// Map ss =(Map) ret.get("RespBody");
		 //logger.info("Map集合大小为："+ss.get("speed"));
		 //ss.get("speed");

	}
private static void parseJSONWithJSONObject(String JsonData)
{
  try
  {
	  //System.out.println(JsonData);
    JSONArray jsonArray = JSONArray.fromObject(JsonData);
    int count = 0;
    List<String> arr=new ArrayList<String>();
   // List arr=new ArrayList();
    for (int i = 0; i < jsonArray.size(); ++i) {
      JSONObject jsonobject = jsonArray.getJSONObject(i);

      String cityCode = jsonobject.getString("cityCode");
      logger.info("地市ID--***=======" + cityCode);
      //if (cityCode.equals("188")) {
    	 // ++count;
         // if (count <= 50) {
          saveKDDD(jsonobject);
          String bespeakId = String.valueOf(jsonobject.getString("id"));
          logger.info("订单id=======" + bespeakId);
          arr.add(bespeakId);
         // String bespeakId = String.valueOf(jsonObject.getInt("bespeakId"));
 		  // logger.info("订单id======="+bespeakId);
         // ByJtKdServiceDel del = new ByJtKdServiceDel();
         // del.execute(bespeakId);
         // }
    // }

    }
    String str2=arr.toString();
    String str3=str2.substring(1, str2.length()-1);
   // String str4=str3.substring(0, str3.length()-1);
    logger.info("订单id=======***********" + str3);
    ByJtKdServiceDel del = new ByJtKdServiceDel();
    if(!StringUtils.isEmpty(str3)){
    	del.execute(str3);
    }
    
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
}

public static void saveKDDD(JSONObject jsonObject)
  throws FrameException
{
  try
  {
    String id = "";//消息id，作为消息唯一标识
    if(jsonObject.has("id")){
    	id = jsonObject.getString("id");
    }
    String bespeakId = "";//订单ID
    if(jsonObject.has("bespeakId")){
    	bespeakId = jsonObject.getString("bespeakId");
    }
    String time = "";//订单创建时间，yyyy-mm-dd HH24:mi:ss
    if(jsonObject.has("time")){
    	time = jsonObject.getString("time");
    }
    String comeFrom = "";//web-网厅；mobile-手厅；weixin-联能微厅；jd-京东；tmall-天猫；alipay-支付宝；wkzs-王卡助手；msg-码上购；wkkd-商城统一王卡宽带预约服务；
    if(jsonObject.has("comeFrom")){
    	comeFrom = jsonObject.getString("comeFrom");
    }
    String thirdNo = "";//第三方预约ID
    if(jsonObject.has("thirdNo")){
    	thirdNo = jsonObject.getString("thirdNo");
    }
    String thirdTime = "";//第三方下单时间，格式：YYYY-MM-DD HH24:mi:ss
    if(jsonObject.has("thirdTime")){
    	thirdTime = jsonObject.getString("thirdTime");
    }
    String provinceCode = "";//省份编码
    if(jsonObject.has("provinceCode")){
    	provinceCode = jsonObject.getString("provinceCode");
    }
    String provinceName = "";//省份名称
    if(jsonObject.has("provinceName")){
    	provinceName = jsonObject.getString("provinceName");
    }
    String cityCode = "";//地市编码
    if(jsonObject.has("cityCode")){
    	cityCode = jsonObject.getString("cityCode");
    }
    String cityName = "";//地市名称
    if(jsonObject.has("cityName")){
    	cityName = jsonObject.getString("cityName");
    }
    String districtCode="";//区县编码
    String districtName="";//区县名称
    if(jsonObject.has("districtCode")){
        districtCode = jsonObject.getString("districtCode");
    }
    if(jsonObject.has("districtName")){
    	districtName = jsonObject.getString("districtName");
    }
    String userName = "";//用户名
    if(jsonObject.has("userName")){
    	userName = jsonObject.getString("userName");
    }
    String userPhone = "";//手机号码
    if(jsonObject.has("userPhone")){
    	userPhone = jsonObject.getString("userPhone");
    }
    String certNo = "";//身份证号
    if(jsonObject.has("certNo")){
    	certNo = jsonObject.getString("certNo");
    }
    String userAddress = "";//装机地址
    if(jsonObject.has("userAddress")){
    	userAddress = jsonObject.getString("userAddress");
    }
    String productName = "";//产品名称
    if(jsonObject.has("productName")){
    	productName = jsonObject.getString("productName");
    }
    String broadbandId = "";//产品编码
    if(jsonObject.has("broadbandId")){
    	broadbandId = jsonObject.getString("broadbandId");
    }
    String price="";//价格 单位：离
    if(jsonObject.has("price")){
    	price = jsonObject.getString("price");
    }
    String speed="";//速率 eg. 6M
    if(jsonObject.has("speed")){
    	speed = jsonObject.getString("speed");
    }
    String tariffType="";//资费方式 eg.包一年
    if(jsonObject.has("tariffType")){
    	tariffType = jsonObject.getString("tariffType");
    }
    String appointment = "";//预约安装时间 格式：yyyy-mm-dd HH24:mi:ss
    if (jsonObject.has("appointment")) {
    	appointment = jsonObject.getString("appointment");
    }
    String appoActivity = "";//预约活动ID
    if (jsonObject.has("appoActivity")) {
    	appoActivity = jsonObject.getString("appoActivity");
    }
    String actPhone = "";//优惠活动手机号
    if (jsonObject.has("actPhone")) {
    	actPhone = jsonObject.getString("actPhone");
    }
    String devProvinceCode = "";//发展人省份编码
    if (jsonObject.has("devProvinceCode")) {
    	devProvinceCode = jsonObject.getString("devProvinceCode");
    }
    String devCityCode = "";//发展人地市编码
    if (jsonObject.has("devCityCode")) {
    	devCityCode = jsonObject.getString("devCityCode");
    }
    String devId = "";//发展人编码
    if (jsonObject.has("devId")) {
    	devId = jsonObject.getString("devId");
    }
    String devName = "";//发展人名称
    if (jsonObject.has("devName")) {
    	devName = jsonObject.getString("devName");
    }
    String channelId = "";//渠道ID
    if (jsonObject.has("channelId")) {
    	channelId = jsonObject.getString("channelId");
    }
    String devDepartCode = "";//发展人部门编码
    if (jsonObject.has("devDepartCode")) {
    	devDepartCode = jsonObject.getString("devDepartCode");
    }
    String devPhoneNo = "";//发展人电话
    if (jsonObject.has("devPhoneNo")) {
    	devPhoneNo = jsonObject.getString("devPhoneNo");
    }
    String relatedCardNum = "";//下单时关联的王卡号码 (目前只针对于 wkkd 渠道)
    if (jsonObject.has("relatedCardNum")) {
    	relatedCardNum = jsonObject.getString("relatedCardNum");
    }
    String preNum = "";//宽带融合业务下单时关联的号码
    if (jsonObject.has("preNum")) {
    	preNum = jsonObject.getString("preNum");
    }
    String hotSpotId = "";//热点ID
    if (jsonObject.has("hotSpotId")) {
    	hotSpotId = jsonObject.getString("hotSpotId");
    }
    String sql = " insert into es_kdyyd_order   (id,bespeakid,time,comefrom,thirdno,thirdtime,provincecode,provincename,citycode,cityname,districtcode,districtname,username,userphone,certno,useraddress,productname,broadbandid,price,speed,tarifftype,appointment,appoactivity,actphone,devprovincecode,devcitycode,devid,devname,channelid,devdepartcode,devphoneno,relatedcardnum,prenum,hotspotid,del_status,deal_status,deal_num,source_from,syn_mode) "
    		   + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'0','WCL','0','ECS',?) ";

    logger.info("订单入库========" + sql);

    if (conn == null) {
      conn = DBUtil.getConnection();
    }
    ps = conn.prepareStatement(sql);
    ps.setString(1, id);
    ps.setString(2, bespeakId);
    ps.setString(3, time);
    ps.setString(4, comeFrom);
    ps.setString(5, thirdNo);
    ps.setString(6, thirdTime);
    ps.setString(7, provinceCode);
    ps.setString(8, provinceName);
    ps.setString(9, cityCode);
    ps.setString(10, cityName);
    ps.setString(11, districtCode);
    ps.setString(12, districtName);
    ps.setString(13, userName);
    ps.setString(14, userPhone);
    ps.setString(15, certNo);
    ps.setString(16, userAddress);
    ps.setString(17, productName);
    ps.setString(18, broadbandId);
    ps.setString(19, price);
    ps.setString(20, speed);
    ps.setString(21, tariffType);
    ps.setString(22, appointment);
    ps.setString(23, appoActivity);
    ps.setString(24, actPhone);
    ps.setString(25, devProvinceCode);
    ps.setString(26, devCityCode);
    ps.setString(27, devId);
    ps.setString(28, devName);
    ps.setString(29, channelId);
    ps.setString(30, devDepartCode);
    ps.setString(31, devPhoneNo);
    ps.setString(32, relatedCardNum);
    ps.setString(33, preNum);
    ps.setString(34, hotSpotId);
    ps.setString(35, "zop");
    
   // logger.info(ps.toString());
    ps.executeUpdate();
  }
  catch (Exception ee)
  {
    ee.printStackTrace();
    logger.info(ee.getStackTrace());
  }
  finally
  {
    try
    {
      if (ps != null)
        ps.close();

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}

static String getSign(String busiContent,String secret,String sign_method, String charset) throws Exception{
	String sign = "";
	logger.info("busiContent: "+busiContent);
	logger.info("secret: "+secret);
	logger.info("charset: "+charset);
	//如果是MD5的加密方式
	if ("MD5".equalsIgnoreCase(sign_method)) {
		if (StringUtils.isEmpty(charset)) {
			sign = MD5Util.MD5(secret + busiContent + secret);
		} else {
			sign = MD5Util.MD5(secret + busiContent + secret, charset);
		}
	}
	logger.info("sign: "+sign);
	return sign;//密钥;
}

public static String makeSign (String appCode,String timestamp,String uuid,String HMAC) throws Exception {
    StringBuffer sb = new StringBuffer();
    //appCode+head节点（除sign节点,字母升序）+hmac密钥
    sb.append("appCode").append(appCode)
            .append("timestamp").append(timestamp)
            .append("uuid").append(uuid)
            .append(HMAC);
    return SecurityTool.sign(HMAC,sb.toString());
}

public static void main(String[] args)
{
  try {
	searchHb();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
}