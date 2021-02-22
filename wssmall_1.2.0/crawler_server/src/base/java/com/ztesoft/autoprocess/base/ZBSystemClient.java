package com.ztesoft.autoprocess.base;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.BackOrderLayerReq;
import params.req.CallOutOperationReq;
import params.req.CallRefundReq;
import params.req.CardSubmitInfoReq;
import params.req.CrawlerAccountInfoReq;
import params.req.CrawlerFreeDesc;
import params.req.CrawlerUpdateGoodsInfoReq;
import params.req.CrawlerUpdateHandleNumReq;
import params.req.CrawlerUpdatePostInfoReq;
import params.req.CrawlerWriteCardSucReq;
import params.req.GetCardDatasReq;
import params.req.OpenAccountDetailReq;
import params.req.OrderSubmitReq;
import params.req.RejectLayerReq;
import params.req.SingleApplicationReq;
import params.req.SubmitOrderReq;
import params.req.ZbAuditOrderQueryReq;
import params.req.ZbAuditSuccOrderQueryReq;
import params.resp.CrawlerFeeInfo;
import params.resp.CrawlerResp;
import params.resp.GetCardDatasResp;
import params.resp.OpenAccountDetailResp;
import params.resp.OperationRecordResp;
import params.resp.OrderSubmitResp;
import params.resp.ZbAuditOrderQueryResp;
import params.resp.ZbAuditSuccOrderQueryResp;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.ecsord.params.bss.req.RecordInfLogReq;
import zte.net.ecsord.params.sf.vo.Route;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.service.impl.OrderOpenAccountSectionService;
import zte.params.goods.req.StdGoodsGetReq;
import zte.params.goods.resp.StdGoodsGetResp;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.autoprocess.base.detector.impl.LoginResultDetector;
import com.ztesoft.autoprocess.base.detector.impl.LoginStatusDetector;
import com.ztesoft.autoprocess.base.detector.zb.ZBLoginResultDetector;
import com.ztesoft.autoprocess.base.detector.zb.ZBLoginStatusDetector;
import com.ztesoft.autoprocess.base.exception.BusinessException;
import com.ztesoft.autoprocess.base.exception.SystemException;
import com.ztesoft.autoprocess.base.model.ZBLoginParam;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.consts.CrawlerConsts;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.timer.AutoLoginZBTask;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;

import consts.ConstsCore;

/**
 * 总部系统登录客户端类，模拟浏览器向订单系统发起登录请求
 * 
 * @author lzg
 * 
 */
public class ZBSystemClient {

	/** 日志记录器 */
	private static final Log log = LogFactory.getLog(ZBSystemClient.class);
	/** 当前实例的名称*/
	private String name;
	/** 登录参数实体 */
	private static ZBLoginParam loginParam;
	/** 登录结果验证器 */
	private LoginResultDetector loginResultValidator;
	/** 是否在线检测器 */
	private LoginStatusDetector loginStatusDetector;
	/** 是否在线*/
	public boolean isOnline=false;
    
    /** 创建默认的httpClient实例 */
    private static String OTHER_SYSTEM = "ZB";

    /** 尝试连接次数 */
    private static short TRY_TO_CONN_NUM = 3;
	
	/** 创建默认的httpClient实例 */
	private CloseableHttpClient httpclient;
	
	/** cookieValue全局变量 */
	public static String cookieValue = "";//失效之后获取的cookie
	public static String cookieValue1 = "";//登录获取的cookie
	public static CookieStore cookieStore = null;
	public static int i =0;
	
	public static ZBSystemClient clientLogin;
	
	/**缓存信息**/
	static INetCache cache;
	private static int NAMESPACE = 433;
	private static String HB_ZB_ORDER_ID = "HB_ZB_ORDER_ID_";
	static {
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	/** 私有构造方法 */
	private ZBSystemClient() {
		
	}
	
	/**
	 * 订单系统输入验证码后点击登录按钮发起登录总商操作
	 * @param username
	 * @param password
	 * @param validateCode
	 * @throws Exception
	 */
	public static String zbClientLogin(String username,String password,String validateCode) throws Exception{
		String loginCookie = "";
		if(clientLogin==null){
			clientLogin = ZBSystemClient.getInstance(username,password,"");
		}
		if(StringUtils.isNotBlank(validateCode)){
			loginParam.setValidateCode(validateCode);
			//第二步登录
			if(clientLogin.doLogin()){//登录成功
				//第三步登录后还需要进入该页面初始化一次，不然不能正常操作
				//String url= ZBOrderUrlConsts.INIT_ZB_MANAGER_URL;
		        String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.INIT_ZB_MANAGER_URL);
				
				clientLogin.doEnterUrl(url);
				//第四步获取cookie
				cookieValue1 = clientLogin.doEnterUrlReturnCookie(url);
				AutoLoginZBTask.autoProcess();//初始化自动登录
				loginCookie = cookieValue1;
			}else{
				log.info("===========通过工号"+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"登录总商失败===========");
			}
		}
		log.info("===========通过前端调用登录方法后返回的cookie："+cookieValue1);
		log.info("===========通过前端调用登录方法后返回的cookie："+cookieValue1);
		return loginCookie;
	}
	
	/**
	 * 获取登录总商工号，获取不到默认工号，用于从自动登录连接池中获取连接对象
	 * @return
	 */
	public static String getLoginZbName(){
		String username = "";
		if(ZBSystemClient.clientLogin!=null){
			if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getUsername())){
				username = ZBSystemClient.clientLogin.getLoginParam().getUsername();
			}else{
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
			}
		}else{
			username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
		}
		return username;
	}

	public static void setCookieStore(HttpResponse httpResponse) {
	    log.info("----setCookieStore");
	    cookieStore = new BasicCookieStore();
	    // JSESSIONID
	    String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
	    String JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
	    log.info("==============setCookieStore  JSESSIONID:" + JSESSIONID);
	    // 新建一个Cookie
	    String path = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_QUERY_ZB_ORDER_URL);
		
	    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
	    cookie.setVersion(0);
	    cookie.setDomain(CrawlerSetting.CRAWLER_ADDRESS);
	    cookie.setPath(path);
	    // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
	    // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
	    // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
	    // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
	    cookieStore.addCookie(cookie);
	  }
	
	public static String getFileToString(String filename){
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;  
			String url = ZBLoginParam.getParseFileUrl()+filename;
			File input = new File("D:/tmp/"+filename+".html");
			if(input.exists()){
				try { 
		            reader = new BufferedReader(new FileReader(input));  
		            String tempString = null;  
		            int line = 1;  
		            while ((tempString = reader.readLine()) != null) {  
		            	sb.append(tempString);
		            }  
		            reader.close();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        } finally {  
		            if (reader != null) {  
		                try {  
		                    reader.close();  
		                } catch (IOException e1) {  
		                }  
		            }  
		        }  
			}else{
				 log.info("=========要解析的文件"+filename+"不存在!");
			}
		return sb.toString();
	}

	
	/**
	 * 订单领取
	 * @param client 登录客户端
	 * @param orderId 订单id
	 * @return 领取结果
	 */
	public static boolean orderReceive(ZBSystemClient client, String orderId){
		//订单领取接口
		String receiveUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_RECEIPT);
		
		JSONObject receiveParams = new JSONObject();
		receiveParams.put("orderId", orderId);//领取Id
		receiveParams.put("claimType", "01");//固定01currClaimType = 01
		String responseStr = "";
		boolean result = false;
		try {
			responseStr = client.jsonPost(receiveParams.toString(), receiveUrl);
		} catch (SystemException e) {
			log.info("=====["+orderId+"]订单领取失败");
			e.printStackTrace();
			result = false;
		} catch (BusinessException e) {
			result = false;
			log.info("=====["+orderId+"]订单领取失败");
			e.printStackTrace();
			result = false;
		}
		
	    if (responseStr != null && responseStr.indexOf("<title>商城、商户登录</title>") != -1) {
            result = true;
        }
		
	    return result;
	}
	
	/**
	 * 查询自动开户状态数据
	 * @param client
	 * @param orderNo
	 * @return
	 */
	public static List<String> queryOrderProcess(ZBSystemClient client,String orderNo){
		String orderProcessUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.QUERY_AUTOMATIC_ACCOUNT_LIST);
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if(StringUtils.isNotBlank(orderNo)){
			formparams.add(new BasicNameValuePair("orderNo",orderNo));
		}
		formparams.add(new BasicNameValuePair("pageSize", "200"));
		formparams.add(new BasicNameValuePair("page.webPager.action", "refresh"));
		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "200"));
		formparams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
		formparams.add(new BasicNameValuePair("orderByTime", "0"));
		List<String> result = new ArrayList<String>();
		try {
			String responseStr = client.post(formparams, orderProcessUrl);
			log.info("[查询自动开户数据返回报文：]："+responseStr);
			if(!StringUtil.isEmpty(responseStr)){
				Document doc = Jsoup.parse(responseStr);
				Elements orderElement = doc.select("div[class=tableBody]");
				if(orderElement != null && orderElement.size() > 0){
					for (Element element : orderElement) {
						String orderIdStr = element.select("p[class=lineHeight24]").get(0).text().trim();
						if(!StringUtil.isEmpty(orderIdStr) && orderIdStr.indexOf("订单编号：") != -1){
							String[] oa = orderIdStr.split("：");
							if(oa != null && oa.length == 2){
								result.add(oa[1]);
								log.info("=====[自动开户队列单号：]"+oa[1]);
							}
						}
					}
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 订单外呼操作
	 * @param client 总商登录客户端
	 * @param orderId 订单id
	 */
	public static CrawlerResp  callOutOperation(ZBSystemClient client, CallOutOperationReq req){
		//外呼操作
		String callOutOrderQuery = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_OUT_ORDER_QUERY);
		
		String callOutOperationUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_OUT_CHECK_ORDER_STATE);
		
		String callOutOperationUrl2 = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_OUT_PROCESSING_DETAILS);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String responseStr = "";
		CrawlerResp resp = new CrawlerResp();
		try{
			params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
			params.add(new BasicNameValuePair("page.webPager.action","refresh"));
			params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
			params.add(new BasicNameValuePair("pageSize","5"));
			
			responseStr = client.post(params, callOutOrderQuery);//查询外呼确认订单
			if(StringUtils.indexOf(responseStr, "订单编号："+req.getOrderNo()+"")<=0){
				//总商外呼确认菜单查不到订单，则认为已经外呼确认
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("总商外呼确认环节查询不到该订单");
				return resp;
			}
			
			params = new ArrayList<NameValuePair>();
			responseStr = "";
			
			params.add(new BasicNameValuePair("orderId", req.getOrderId()));//固定分配工号
			responseStr = client.post(params, callOutOperationUrl);
			JSONObject respJson = JSONObject.fromObject(responseStr);
			String ORDER_STATE = respJson.getJSONObject("retInfo").getString("ORDER_STATE");
			log.info("=====["+req.getOrderNo()+"]订单外呼操作修改订单状态链接返回"+ORDER_STATE); 
			if( "C0".equals(ORDER_STATE) && 
					"C1".equals(ORDER_STATE) && "CA".equals(ORDER_STATE)){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单外呼操作失败，订单状态异常："+ORDER_STATE);
					return resp;
			}else {
				log.info("=====["+req.getOrderNo()+"]订单外呼操作成功");
				responseStr = "";
				responseStr = client.post(params, callOutOperationUrl2);
				if(StringUtils.isNotBlank(responseStr) && responseStr.indexOf("<title>订单处理详情</title>") != -1){
					return client.callOutConfirm(client, req.getOrderId(), responseStr);
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单外呼操作异常："+e.getMessage());
			log.info("=====[订单外呼确认操作]入参："+params.toString()+"; 返回："+responseStr);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单外呼操作异常："+e.getMessage());
			log.info("=====[订单外呼确认操作]入参："+params.toString()+"; 返回："+responseStr);
		} catch (JSONException e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单外呼操作总商返回JSON结果错误："+responseStr);
			log.info("=====[订单外呼确认操作]入参："+params.toString()+"; 返回："+responseStr);
		}
		return resp;
		
	}
	
	/**
	 * 获取写卡卡数据
	 * @param client
	 * @param req
	 */
	public GetCardDatasResp getCardDatas(ZBSystemClient client, GetCardDatasReq req){
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.GET_CALL_CARD_QRY_DATA);
		
		GetCardDatasResp resp = new GetCardDatasResp();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ICCID", req.getIccid()));
		params.add(new BasicNameValuePair("orderId", req.getOrderId()));
		params.add(new BasicNameValuePair("AllotFlag", req.getAllotFlag()));
		params.add(new BasicNameValuePair("isCardChange", req.getIsCardChange()));
		params.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));
		params.add(new BasicNameValuePair("preNum", req.getPreNum()));
		String responseStr = "";
		try{
			responseStr = client.post(params, url);
			JSONObject respJson = JSONObject.fromObject(responseStr);
			JSONObject respInfo = respJson.getJSONObject("respInfo");
			if("0001".equals(respInfo.getString("RespCode"))){
				log.info("=====获取写卡数据，卡归属渠道"+respInfo.getString("channelId")+"，需调拨至您所在的渠道“"+respInfo.getString("myChannelId")+"才能继续，确认惊喜调拨操作？（默认：Yes）");
				req.setAllotFlag("0");
				return getCardDatas(client,req);
			}else if ("5555".equals(respInfo.getString("RespCode")) && !"0000".equals(respInfo.getString("RespCode"))){
				resp.setError_code("-1");
				resp.setError_msg(respInfo.getString("RespCode"));
				log.info("=====获取写卡数据失败");
				return resp;
			}
			
			resp.setImsi(respInfo.getString("IMSI"));
			resp.setNumId( respInfo.getString("NumID"));
			resp.setScriptSeq(respInfo.getString("ScriptSeq"));
			resp.setIccid(respInfo.getString("ICCID"));
			resp.setError_code("0");
			return resp;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e){
			e.printStackTrace();
			log.info("=====["+req.getOrderId()+"]获取写卡数据失败");
			log.info("=====["+req.getOrderId()+"]获取写卡数据总商返回JSON结果错误："+responseStr);
		}
		
		resp.setError_code("-1");
		resp.setError_msg(responseStr);
		return resp;
	
	}
	/**
	 * 写卡完成，在提交写卡之前执行
	 * @param req
	 * @return
	 */
	public ZteResponse writeCardSuc(ZBSystemClient client,CrawlerWriteCardSucReq req) {
		ZteResponse response = new ZteResponse();
		String url ="http://admin.mall.10010.com/Odm/OrderProcess/callWCardResult";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ICCID", req.getIccid()));
		params.add(new BasicNameValuePair("IMSI", req.getImsi()));
		params.add(new BasicNameValuePair("ReasonID", "0"));
		params.add(new BasicNameValuePair("ErrorComments", "写卡成功"));
		params.add(new BasicNameValuePair("orderId", req.getOrderId()));
		params.add(new BasicNameValuePair("isCardChange", req.getIsCardChange()));
		params.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));
		params.add(new BasicNameValuePair("preNum", req.getPreNum()));
		String result="";
		try {
			result = client.post(params, url);
			log.info("订单"+req.getOrderNo()+"执行写卡完毕返回"+result);
			response.setError_code(ConstsCore.ERROR_SUCC);
			response.setError_msg("执行成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setError_code(ConstsCore.ERROR_FAIL);
			response.setError_msg("执行写卡完毕异常");
			e.printStackTrace();
		}
		return response;
	}
	/**
	 * 提交写卡
	 * @throws Exception 
	 */
	public ZteResponse callCardSubmit(ZBSystemClient client, CardSubmitInfoReq req) throws Exception{
		ZteResponse response = new ZteResponse();
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_CARD_SUBMIT);
		
		String openDetailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);

		String responseStr = "";
		try {
			//通过爬虫开户详情页获取固定参数
    		List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
    		formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//总商订单ID长订单号
			String openDetail = client.post(formparams, openDetailUrl);
			if(StringUtils.isBlank(openDetail) || openDetail.indexOf("当前订单["+req.getOrderNo()+"]已经进入开户处理") == -1 ){
				response.setError_code("-1");//0为成功非0失败
				response.setError_msg("爬虫开户详情页未获取到订单信息");
				return response;
			}
//			CrawlerAccountInfoReq creq = null;
//			if(StringUtils.isNotBlank(openDetail)){
//				creq = getZbOpenDetail(openDetail);
//			}
			CrawlerAccountInfoReq cai = getZbOpenDetail(openDetail);
			req.setGoodInstId(cai.getGoodInstId());
			req.setTmplId(cai.getTmplId());
			//==============================================================
			JSONObject jsonParams = new JSONObject();
			jsonParams.put("orderId", req.getOrderId());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderId", req.getOrderId()));
			
			if("1".equals(req.getIsCardChange())){//老用户便捷换卡开户不走卡数据同步接口“1”：是 “0”：不是
				if("1".equals(req.getIsOldCard())){//是否成卡
					response.setError_code("-1");
					response.setError_msg("成卡不需要写卡");
					return response;
				}
				
				String accountSubmit4CardChgUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ACCOUNT_SUBMIT4_CARD_CHG_URL);
				
				responseStr = client.jsonPost(jsonParams.toString(), accountSubmit4CardChgUrl);
				JSONObject respJSON = JSONObject.fromObject(responseStr);
				if("0000".equals(respJSON.getJSONObject("respInfo").getString("respCode"))){
					response.setError_code("0");
					response.setError_msg("老用户便捷换卡提交成功");
				}else{
					log.info("=====["+req.getOrderId()+"]提交写卡失败，老用户便捷换卡："+respJSON.getJSONObject("respInfo").getString("respDesc"));
					response.setError_code("-1");
					response.setError_msg(respJSON.getJSONObject("respInfo").getString("respDesc"));
				}
				
				return response;
			}
		
			if("1".equals(req.getIsOldCard()) && "10000011".equals(req.getTmplId()) || "10000012".equals(req.getTmplId())){
				response.setError_code("-1");
				response.setError_msg("goodsTMPLId为：10000011,10000012 无法提交写卡");
				return response;
			}
			params.add(new BasicNameValuePair("invoiceNo", req.getInvoiceNo()));
			params.add(new BasicNameValuePair("goodInstId", req.getGoodInstId()));
			params.add(new BasicNameValuePair("invoiceNo", req.getInvoiceNo()));
			params.add(new BasicNameValuePair("simID", req.getSimID()));
//			jsonParams.put("invoiceNo", req.getInvoiceNo());
//			jsonParams.put("goodInstId", req.getGoodInstId());
//			jsonParams.put("invoiceNo", req.getInvoiceNo());
//			jsonParams.put("simID", req.getSimID());
			
			if("10000011".equals(req.getTmplId()) || "10000012".equals(req.getTmplId())){
				params.add(new BasicNameValuePair("tmplId", req.getTmplId()));
//				jsonParams.put("tmplId", req.getTmplId());
			}
			params.add(new BasicNameValuePair("numID", req.getNumId()));
			params.add(new BasicNameValuePair("usimFee", StringUtils.isEmpty(req.getUsimFee())?"0":""+Double.parseDouble(req.getUsimFee())*1000));
			params.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));
			params.add(new BasicNameValuePair("preNum", req.getPreNum()));
//			jsonParams.put("numID", req.getNumId());
//			jsonParams.put("usimFee", StringUtils.isEmpty(req.getUsimFee())?"0":Double.parseDouble(req.getUsimFee())*1000);
//			jsonParams.put("isZFKNewOrder", req.getIsZFKNewOrder());
//			jsonParams.put("preNum", req.getPreNum());
		
			responseStr = client.post(params, url);
			JSONObject respJson = JSONObject.fromObject(responseStr);
			JSONObject resp = respJson.getJSONObject("respInfo");//{"respInfo":{"ErrDesc":"[AOP卡数据同步提交接口]返回：-流水号170205000508217357{\"detail\":\"无此资源信息\",\"code\":\"9999\"}","ErrCode":"9999"}}
			if(resp.containsKey("ErrCode")&&!StringUtil.equals("0000",resp.getString("ErrCode"))){
				log.info("=====["+req.getOrderId()+"]提交写卡失败,卡数据同步失败："+(resp.containsKey("ErrorMsg")?resp.getString("ErrorMsg"):resp.getString("ErrDesc")));
				response.setError_code("-1");
				response.setError_msg((resp.containsKey("ErrorMsg")?resp.getString("ErrorMsg"):resp.getString("ErrDesc")));
				return response;
			}
			
			if(resp.containsKey("ErrCode")&&"0000".equals(resp.getString("ErrCode"))){
				response.setError_code("0");
			} else if(resp.containsKey("InvoiceFlag") && "0".equals(resp.getString("InvoiceFlag"))){
				log.info("=====["+req.getOrderId()+"]提交写卡失败,卡数据同步失败："+resp.getString("ErrDesc"));
				response.setError_code("-1");
				response.setError_msg(resp.getString("ErrDesc"));
			} else if(resp.containsKey("InvoiceFlag") && "0007".equals(resp.getString("InvoiceFlag"))){
				log.info("=====["+req.getOrderId()+"]提交写卡失败,提交写卡失败,卡数据同步失败："+resp.getString("ErrDesc"));
				response.setError_code("-1");
				response.setError_msg(resp.getString("ErrDesc"));
			} else {
				log.info("=====["+req.getOrderId()+"]提交写卡失败,卡数据同步失败："+resp.getString("ErrDesc"));
				response.setError_code("-1");
				response.setError_msg(resp.getString("ErrDesc"));
			}
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError_msg(e.getLocalizedMessage());
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError_msg(e.getLocalizedMessage());
		}
		return response;
	}
	
	/**
	 * 爬虫获取开户详情页面信息
	 * @param openDetail
	 * @return
	 */
	private static CrawlerAccountInfoReq getZbOpenDetail(String openDetail){
		CrawlerAccountInfoReq req = new CrawlerAccountInfoReq();
		if(StringUtils.isNotBlank(openDetail)){
			Document doc = Jsoup.parse(openDetail);
			String tmplId = "";
			String merchantId = "";
			String accountFlag = "";
			String goodInstId = "";
			String userTag = "";
			String incomeMoney = "";
			String isZFKNewOrder = "";
			String isNorthSixOrder = "";
			String isCombineOrder = "";
			String ismanual4Combine = "";
			String manualOrderNo = "";
			if(doc!=null){
				Element tmplIdElement = doc.getElementById("goodsTMPLId");
				if(tmplIdElement!=null){//防止标签不存在
					tmplId = tmplIdElement.val();
					req.setTmplId(tmplId);
				}
				Element merchantIdElement = doc.getElementById("merchantId");
				if(merchantIdElement!=null){
					merchantId = merchantIdElement.val();
					req.setMerchantId(merchantId);
				}
				Element accountFlagElement = doc.getElementById("accountFlag");
				if(accountFlagElement!=null){
					accountFlag = accountFlagElement.val();
					req.setAccountFlag(accountFlag);
				}else{
					req.setAccountFlag("0");//默认为0
				}
				Element goodInstIdElement = doc.getElementById("goodInstId");
				if(goodInstIdElement!=null){
					goodInstId = goodInstIdElement.val();
					req.setGoodInstId(goodInstId);
				}
				Element userTagElement = doc.getElementById("userTag");
				if(userTagElement!=null){
					userTag = userTagElement.val();
					req.setUserTag(userTag);
				}else{
					req.setUserTag("1");//默认为1
				}
				Elements incomeMoneyElement = doc.getElementsByAttributeValue("name", "incomeMoney");
				if(incomeMoneyElement!=null && incomeMoneyElement.size()>0){
					incomeMoney = incomeMoneyElement.val();
					req.setIncomeMoney(incomeMoney);
				}
				Elements isZFKNewOrderElement = doc.getElementsByAttributeValue("name", "isZFKNewOrder");
				if(isZFKNewOrderElement!=null && isZFKNewOrderElement.size()>0){
					isZFKNewOrder = isZFKNewOrderElement.val();
					req.setIsZFKNewOrder(isZFKNewOrder);
				}else{
					req.setIsZFKNewOrder("0");//默认为0
				}
			}
		}
		return req;
	}
	
	/**
	 * 订单提交按钮（开户提交，预开户）
	 * @param client
	 * @param req
	 */
	public OrderSubmitResp callSubmit(ZBSystemClient client, OrderSubmitReq req){
		OrderSubmitResp response = new OrderSubmitResp();
		
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_SUBMISSION);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		JSONObject jsonParams = new JSONObject();
		params.add(new BasicNameValuePair("orderId", req.getOrderId()));
		params.add(new BasicNameValuePair("userTag", req.getUserTag()));
		params.add(new BasicNameValuePair("invoiceNo", req.getInvoiceNo()));
		params.add(new BasicNameValuePair("isOldCard", req.getIsOldCard()));
		params.add(new BasicNameValuePair("simNo", req.getSimNo()));
		
		CrawlerFreeDesc feeDesc = req.getFreeDesc();
		if(!StringUtil.isEmpty(feeDesc.getUsimFee())){
			params.add(new BasicNameValuePair("origTotalFee", ""+Double.valueOf(feeDesc.getTotleFee())*1000));
			params.add(new BasicNameValuePair("origTotalFee", ""+Double.valueOf(feeDesc.getTotleFee())*1000));
			jsonParams.put("usimRealFee", Double.valueOf(feeDesc.getUsimFee())*1000);
			jsonParams.put("origTotalFee", Double.valueOf(feeDesc.getTotleFee())*1000);
		}else{
			params.add(new BasicNameValuePair("origTotalFee", ""+Double.valueOf(feeDesc.getTotleFee())*1000));
		}
		if(!StringUtil.isEmpty(feeDesc.getOtherFee())){
			params.add(new BasicNameValuePair("otherOrigFee", ""+Double.valueOf(feeDesc.getOtherFee())*1000));
			params.add(new BasicNameValuePair("otherRealFee", ""+Double.valueOf(feeDesc.getOtherFee())*1000));
		}
		params.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));
		params.add(new BasicNameValuePair("preNum", req.getPreNum()));
		
		if(null != feeDesc.getCrawlerFeeInfo() && 0 < feeDesc.getCrawlerFeeInfo().size()){
			JSONArray arr = new JSONArray();
			params.add(new BasicNameValuePair("feeInfo", arr.toString()));
			List<CrawlerFeeInfo> list = feeDesc.getCrawlerFeeInfo();
			for(int i=0;i<list.size();i++){
				CrawlerFeeInfo feeinfo = list.get(i);
				JSONObject item = new JSONObject();
				item.put("FeeID", feeinfo.getFeeID());
				item.put("FeeCategory", feeinfo.getFeeCategory());
				item.put("FeeDes", feeinfo.getFeeDes());
				item.put("OrigFee", Double.parseDouble(feeinfo.getOrigFee())*1000);
				item.put("MaxRelief", Double.parseDouble(feeinfo.getMaxRelief())*1000);
				item.put("ReliefFee", Double.parseDouble(feeinfo.getReliefFee())*1000);
				item.put("RealFee", Double.parseDouble(feeinfo.getRealFee())*1000);
				item.put("ReliefResult", feeinfo.getReliefResult());
				arr.add(item);
			}
		}
		String responseStr = "";
		try {
			
			String openDetailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);
			
			//通过爬虫开户详情页获取固定参数
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
			formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//总商订单ID长订单号
			String openDetail = client.post(formparams, openDetailUrl);
			if(!StringUtils.isBlank(openDetail) || openDetail.indexOf("当前订单["+req.getOrderNo()+"]已经进入开户处理") != -1 ){
				//CrawlerAccountInfoReq creq = null;
				//creq = getZbOpenDetail(openDetail);
				CrawlerAccountInfoReq cai = getZbOpenDetail(openDetail);
				req.setGoodInstId(cai.getGoodInstId());
				req.setTmplId(cai.getTmplId());
			}
			params.add(new BasicNameValuePair("tmplId", req.getTmplId()));
			params.add(new BasicNameValuePair("goodInstId", req.getGoodInstId()));
			
			responseStr = client.post(params, url);
			JSONObject respJSON = JSONObject.fromObject(responseStr);
			JSONObject resp = respJSON.getJSONObject("respInfo");
			if(resp.containsKey("resp.ErrorMsg")&&!StringUtil.isEmpty(resp.getString("resp.ErrorMsg"))){
				log.info("=====["+req.getOrderId()+"]订单提交失败："+resp.getString("ErrorMsg"));
				response.setError_code("-1");
				response.setError_msg(resp.getString("ErrorMsg"));
				return response;
			}
			
			if(resp.containsKey("ErrCode") && "0000".equals(resp.getString("ErrCode"))){
				response.setError_code("0");
				response.setError_msg("提交成功");
				response.setEssOrderId(resp.getString("ESS_ORDER_ID"));//开户流水
			}else if(resp.containsKey("InvoiceFlag") && "0".equals(resp.getString("InvoiceFlag"))){
				response.setError_code("-1");
				response.setError_msg(resp.getString("ErrDesc"));
			}else {
				response.setError_code("-1");
				response.setError_msg(resp.toString());
			}
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("订单"+req.getOrderNo()+"操作[订单提交按钮]返回报文异常"+responseStr);
			response.setError_code("-1");
			response.setError_msg("总商[订单提交按钮]操作异常");
		}
		return response;
	}
	
	public ZteResponse singleApplication(ZBSystemClient client, SingleApplicationReq req){
		ZteResponse response = new ZteResponse();
		
		//订单审核退单申请链接
		String cancelConfirmUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_REVIEW_APPLICATION_WITHDRAWAL)+req.getOrderId();//"http://admin.mall.10010.com/Acodm/ArtificialDetail/requstBack/"+req.getOrderId();
		
		//订单外呼申请退单链接
		String callOutChargebackUr = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_OUT_CHARGE_BACK_OPERATOR);//"http://admin.mall.10010.com/Odm/OrderConfirm/chargebackOperator";
		
		//自动开户退单申请链接
		String chargebackUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.AUTOMATIC_ACCOUNT_APPLICATION_WITHDRAWAL);
		
		//发货退单申请
		String cancelApplyUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.DELIVER_GOODS_APPLICATION_WITHDRAWAL);//"http://admin.mall.10010.com/Odm/OrderDelivery/cancelApply";
	
		//请求参数
		JSONObject jsonParams = new JSONObject();
		String responseStr = "";
		try {
			if("orderReview".equals(req.getLink())){//订单审核申请退单
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("reasonCode", req.getReasonCode()));
				param.add(new BasicNameValuePair("reasonDesc", req.getReasonDesc()));
				param.add(new BasicNameValuePair("cancelRemark", req.getCancelRemark()));
				param.add(new BasicNameValuePair("orderId", req.getOrderId()));
					
				responseStr = client.post(param, cancelConfirmUrl);//{"success":true}
				JSONObject jsonObj = JSONObject.fromObject(responseStr);
				if(!StringUtil.isEmpty(jsonObj.getString("success"))){
					response.setError_code("0");//申请成功
				}else{
					response.setError_code("-1");
					response.setError_msg("总商系统异常，请稍后再试");
					log.info("=====[订单审核]申请退单出错，返回报文："+responseStr);
				}
    			
			}else if("automatic".equals(req.getLink())){//自动开户申请退单
				OrderOpenAccountSectionService sectionService = new OrderOpenAccountSectionService();
				OpenAccountDetailReq detailReq = new OpenAccountDetailReq();
				detailReq.setOrderId(req.getOrderId());
				detailReq.setOrderNo(req.getOrderNo());
				OpenAccountDetailResp resp = sectionService.getOpenAccountDetail(detailReq);
				if(!ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
					response.setError_code("-1");
					response.setError_msg(resp.getError_msg());
					log.info("=====[自动开户]申请退单出错，返回报文："+responseStr);
				}else{
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("reasonCode", req.getReasonCode()));
					param.add(new BasicNameValuePair("chargebackRemark", req.getCancelRemark()));
					param.add(new BasicNameValuePair("orderId", req.getOrderId()));
					
					responseStr = client.post(param, chargebackUrl);
					JSONObject jsonObj = JSONObject.fromObject(responseStr);
					JSONObject respInfo = jsonObj.getJSONObject("respInfo");
					if("0".equals(respInfo.getString("RespCode"))){
						response.setError_code("0");
					}else{
						response.setError_code("-1");
						response.setError_msg(respInfo.getString("RespDesc"));
						log.info("=====[自动开户]申请退单出错，返回报文："+responseStr);
					}
				}
			}else if("deliverGoods".equals(req.getLink())){//发货申请退单
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("reasonCode", req.getReasonCode()));
				param.add(new BasicNameValuePair("cancelRemark", req.getCancelRemark()));
				param.add(new BasicNameValuePair("orderId", req.getOrderId()));
				
				responseStr = client.post(param, cancelApplyUrl);//{"respInfo":{"RespCode":"0","RespDesc":"退单申请成功！"}}
				JSONObject jsonObj = JSONObject.fromObject(responseStr);
				JSONObject respInfo = jsonObj.getJSONObject("respInfo");
				if("0".equals(respInfo.getString("RespCode"))){
					response.setError_code("0");
				}else{
					response.setError_code("-1");
					response.setError_msg(respInfo.getString("RespDesc"));
					log.info("=====[发货]申请退单出错，返回报文："+responseStr);
				}
			}else if("callOutCharge".equals(req.getLink())){
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("reasonCode", req.getReasonCode()));
				param.add(new BasicNameValuePair("cancelRemark", req.getCancelRemark()));
				param.add(new BasicNameValuePair("orderId", req.getOrderId()));
				
				responseStr = client.post(param, callOutChargebackUr);//{"respInfo":{"RespCode":"0","RespDesc":"退单成功！"}}
				JSONObject jsonObj = JSONObject.fromObject(responseStr);
				JSONObject respInfo = jsonObj.getJSONObject("respInfo");
				if("0".equals(respInfo.getString("RespCode"))){
					response.setError_code("0");
				}else{
					response.setError_code("-1");
					response.setError_msg(respInfo.getString("RespDesc"));
					log.info("=====[外呼确认]申请退单出错，返回报文："+responseStr);
				}
			} else {
				response.setError_code("-1");
				response.setError_msg("=====订单["+req.getOrderNo()+"],申请环节["+req.getLink()+"]该环节没有退单申请操作，请确认申请环节是否正确！");
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	

	/**
	 * 退单审核--》退单
	 * @param client
	 * @return
	 */
	public ZteResponse backOrderLayer(ZBSystemClient client, BackOrderLayerReq req){
		ZteResponse response = new ZteResponse();
		
		String queryUrl= "http://admin.mall.10010.com/Odm/OrderCancelJudge/query";
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("isQueryFlag", "0"));
		param.add(new BasicNameValuePair("checkBroadband", "0"));
		param.add(new BasicNameValuePair("pageSize", "1"));
		param.add(new BasicNameValuePair("page.webPager.action", "refresh"));
		param.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
		param.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "1"));
		param.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
		param.add(new BasicNameValuePair("orderNo", req.getOrderNo()));
		
		String str="";
		try {
			str = client.post(param, queryUrl);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isEmpty(str)){
			response.setError_code("-1");
			response.setError_msg("查询退单审核列表返回为空");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]查询退单列表返回为空");
			return response;
		}
		Document doc = Jsoup.parse(str);
		Element pageTable = doc.getElementById("pageTable");
		if(pageTable == null){
			response.setError_code("-1");
			response.setError_msg("查询不到订单信息");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]退单列表不存在该订单");
			return response;
		}
		
		Elements tableBodys = pageTable.select(".tableBody");
		if(tableBodys == null || tableBodys.size() != 1){
			response.setError_code("-1");
			response.setError_msg("查询不到订单");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]退单列表解析不到.tableBody");
			return response;
		}
		
		Elements backOrderEjects = tableBodys.get(0).select(".backOrderEject");
		if(backOrderEjects == null && backOrderEjects.size() == 1){
			response.setError_code("-1");
			response.setError_msg("查询不到订单");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]退单列表解析不到.backOrderEject");
			return response;
		}
		Element backOrderEject = backOrderEjects.get(0);
		String orderId = backOrderEject.attr("orderId");
		String orderFrom = backOrderEject.attr("orderFrom");
		String payState = backOrderEject.attr("payState");
		String incomeMoney = backOrderEject.attr("incomeMoney");
		String processState = backOrderEject.attr("processState");
		String showRefund = backOrderEject.attr("showRefund");
		String delayTag = backOrderEject.attr("delayTag");
		String payType = backOrderEject.attr("payType");
		String isNoDeliveryGuarantee = backOrderEject.attr("isNoDeliveryGuarantee");

		String orderDelayTag = req.getOrderDelayTag();//退款1  不退款2
		boolean isRefundApply = false;
		if(!StringUtil.isEmpty(delayTag) && "1".equals(delayTag) && !StringUtil.isEmpty(payType) && "02".equals(payType)){//可以退款
			
		}else{//订单要申请退款
			if("0".equals(showRefund) && !"0".equals(isNoDeliveryGuarantee) && "1".equals(orderDelayTag)){//不能申请退款
				response.setError_code("-1");
				response.setError_msg("该订单不支持退款");
				log.info("=====["+req.getOrderNo()+"-退单审核-退单]该订单不支持退款");
				return response;
			}else{
				isRefundApply = true;
				if(("1".equals(payState) && Double.valueOf(incomeMoney) > 0) || "0".equals(isNoDeliveryGuarantee)){
					if(!StringUtil.isEmpty(processState)){//宽带订单不能发起申请退款，直接退单，在调宽带撤单接口通过后直接调统一支付退款
						isRefundApply = false;//禁用申请退款-可退单
					}else if("0".equals(isNoDeliveryGuarantee)){
						if("A".equals(payState) || "D".equals(payState)){// A-退款申请、D-退款失败
							isRefundApply = false;//禁用申请退款-可退单
						}
						if("2".equals(payState) || "B".equals(payState)){//2-已退款、B-线下退款
							isRefundApply = false;//已经申请退款了，不需要申请退款，可以退单
						}
					}else{//不允许退单，可申请退款
						
					}
				}else if("A".equals(payState) || "D".equals(payState)){// A-退款申请、D-退款失败
					isRefundApply = false;//禁用申请退款-可退单
				}else{// 0-未支付、2-已退款、B-线下退款
					isRefundApply = false;//禁用申请退款-可退单
				}
			}
		}
		String refundApplyUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.REFUND_APPLY_OPERATION);//"http://admin.mall.10010.com/Odm/OrderCancelJudge/refundApply";//全额退款申请
		List<NameValuePair> refundApplyParam = new ArrayList<NameValuePair>();
		refundApplyParam.add(new BasicNameValuePair("orderId", orderId));
		if(isRefundApply == true && "1".equals(orderDelayTag)){
			String responseStr;
			try {
				responseStr = client.post(refundApplyParam, refundApplyUrl);//{"UPDATE_SUCCESS":true}
				if(StringUtil.isEmpty(responseStr)){
					response.setError_code("-1");
					response.setError_msg("全额退款申请返回为空");
					log.info("=====["+req.getOrderNo()+"-退单审核-退单]全额退款申请返回为空");
					return response;
				}
				JSONObject json = JSONObject.fromObject(responseStr);
				if(json.getBoolean("UPDATE_SUCCESS") == false){
					response.setError_code("-1");
					response.setError_msg("全额退款申请失败");
					log.info("=====["+req.getOrderNo()+"-退单审核-退单]全额退款申请失败");
					return response;
				}
				payState = "A";
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BACK_REVIEW_BACK_OPERATION);
	
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", orderId));
		detailparams.add(new BasicNameValuePair("backReasonDesc", req.getBackReasonDesc()));
		detailparams.add(new BasicNameValuePair("orderDelayTag", orderDelayTag));
		
		String responseStr = "";
		
		try {
			responseStr = client.post(detailparams, url);//{"UPDATE_SUCCESS":true}
			JSONObject jsonObj = JSONObject.fromObject(responseStr);
			if(jsonObj.getBoolean("UPDATE_SUCCESS")){
				if(jsonObj.containsKey("AUTO_REFUND")){//自动申请退款了
					response.setError_code("0");
					response.setError_msg("退单成功，请到退款审核中进行退款操作！");
				}else{
					response.setError_code("0");
					response.setError_msg("退单成功");
				}
			}else {
				response.setError_code("-1");
				response.setError_msg("退单失败，失败原因："+jsonObj.getString("ErrorMsg"));
				log.info("=====["+req.getOrderNo()+"-退单审核-退单]-退单失败，返回报文："+responseStr);
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("退单失败，系统异常");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]-退单异常，返回报文："+responseStr);
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("退单失败，系统异常");
			log.info("=====["+req.getOrderNo()+"-退单审核-退单]-退单异常，返回报文："+responseStr);
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * 退单审核--》驳回
	 * @param client
	 * @return
	 */
	public ZteResponse rejectLayer(ZBSystemClient client, RejectLayerReq req){
		ZteResponse response = new ZteResponse();
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BACK_REVIEW_REJECT_OPERATION);
		
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", req.getOrderId()));
		detailparams.add(new BasicNameValuePair("rejectReasonDesc", req.getRejectReasonDesc()));
	
		String responseStr = "";
		try {
			responseStr = client.post(detailparams, url);
			JSONObject jsonObj = JSONObject.fromObject(responseStr);
			if(jsonObj.getBoolean("UPDATE_SUCCESS")){
				response.setError_code("-1");
				response.setError_msg("驳回失败："+jsonObj.getString("ErrorMsg"));
				log.info("=====["+req.getOrderId()+"-退单审核-驳回]驳回失败，返回报文："+responseStr);
			}else {
				response.setError_code("0");
				response.setError_msg("");
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("驳回失败，系统异常");
			log.info("=====["+req.getOrderId()+"-退单审核-驳回]驳回异常，返回报文："+responseStr);
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("驳回失败，系统异常");
			log.info("=====["+req.getOrderId()+"-退单审核-驳回]驳回异常，返回报文："+responseStr);
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * 退款审核--》退款
	 * @param client
	 * @return
	 */
	public static ZteResponse callRefund(ZBSystemClient client, CallRefundReq req){
		ZteResponse response = new ZteResponse();
		
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.REFUND_REVIEW_REFUND_OPERATION);
		
		String refuadListUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.QUWEY_REFUND_REVIEW_LIST);
		
		List<NameValuePair> refuadParams = new ArrayList<NameValuePair>();//创建参数队列w
		refuadParams.add(new BasicNameValuePair("checkBroadband", "0"));
		refuadParams.add(new BasicNameValuePair("orderNo", req.getOrderNo()));
		refuadParams.add(new BasicNameValuePair("page.webPager.action", "refresh"));
		refuadParams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
		refuadParams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "1"));
		refuadParams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
		refuadParams.add(new BasicNameValuePair("pageSize", "1"));
		
		
		String responseStr = "";
		String offlineRefund = "";
		String orderfrom = "";
		try {
			String detailHtml = client.post(refuadParams, refuadListUrl);
			if(!StringUtil.isEmpty(detailHtml)){
				Document doc = Jsoup.parse(detailHtml);
				offlineRefund = doc.select("#offlineRefund").val(); 
				orderfrom = doc.select(".shortBlueBtn").attr("orderfrom");
			}
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
			formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));
			formparams.add(new BasicNameValuePair("offlineRefund", offlineRefund));
			formparams.add(new BasicNameValuePair("orderfrom", orderfrom));
			
			responseStr = client.post(formparams, url);
			JSONObject jsonObj = JSONObject.fromObject(responseStr);//{"VALIDATE_SUCCESS":true,"REFUND_SUCCESS":true,"orderId":"6617033025903826","UPDATE_SUCCESS":true}
			if(!jsonObj.getBoolean("VALIDATE_SUCCESS")){
				response.setError_code("-1");
				response.setError_msg(jsonObj.getString("ErrorMsg"));
				log.info("=====["+req.getOrderId()+"-退款审核-退款]退款失败，返回报文："+responseStr);
			}else{
				if(jsonObj.getBoolean("UPDATE_SUCCESS") && jsonObj.getBoolean("REFUND_SUCCESS")){//退款成功
					response.setError_code("0");
				}else{
					response.setError_code("-1");
					response.setError_msg(jsonObj.getString("ErrorMsg"));
					log.info("=====["+req.getOrderId()+"-退款审核-退款]退款失败，返回报文："+responseStr);
				}
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("退款失败，系统异常");
			log.info("=====["+req.getOrderId()+"-退款审核-退款]退款异常，返回报文："+responseStr);
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			response.setError_code("-1");
			response.setError_msg("退款失败，系统异常");
			log.info("=====["+req.getOrderId()+"-退款审核-退款]退款异常，返回报文："+responseStr);
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * 处理完毕，确认提交订单
	 * @param client
	 */
	public ZteResponse submitOrder(ZBSystemClient client, SubmitOrderReq req){
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.FINISHED_PROCESSING_CONFIRM_SUBMISSION_OF_ORDERS);
		
		ZteResponse response = new ZteResponse();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orderId", req.getOrderId()));
		params.add(new BasicNameValuePair("certType", req.getCertType()));
		params.add(new BasicNameValuePair("certNum", req.getCertNum()));
		params.add(new BasicNameValuePair("goodsType", req.getGoodsType()));
		
		String responseStr = "";
		
		try {
			String openDetailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);
			
			//通过爬虫开户详情页获取固定参数
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//总商订单ID长订单号
			String openDetail = client.post(formparams, openDetailUrl);
			if(!StringUtils.isBlank(openDetail) || openDetail.indexOf("当前订单["+req.getOrderNo()+"]已经进入开户处理") != -1 ){
				Map<String, String> inputmap = new HashMap<String, String>();
				inputmap.put("isZFKNewOrder", "");
				inputmap.put("ismanual4zfk", "");
				inputmap.put("isCombineOrder", "");
				inputmap.put("ismanual4Combine", "");
				getJsoupScriptContent(openDetail, inputmap);
				req.setIsZFKNewOrder(inputmap.get("isZFKNewOrder"));
				req.setIsmanual4Combine(inputmap.get("ismanual4Combine"));
				req.setIsmanual4zfkv(inputmap.get("ismanual4zfk"));
				req.setIsCombineOrder(inputmap.get("isCombineOrder"));

				CrawlerAccountInfoReq cai = getZbOpenDetail(openDetail);
				req.setGoodsInstId(cai.getGoodInstId());
				req.setTmplId(cai.getTmplId());
			}
			params.add(new BasicNameValuePair("tmplId", req.getTmplId()));//总商订单ID长订单号
			params.add(new BasicNameValuePair("goodsInstId", req.getGoodsInstId()));//总商订单ID长订单号
			
			 /**手动开户补录信息start**/
			if(("1".equals(req.getIsZFKNewOrder()) && "1".equals(req.getIsmanual4zfkv())) || ("1".equals(req.getIsCombineOrder()) && "1".equals(req.getIsmanual4Combine()))){
				params.add(new BasicNameValuePair("isZFKNewOrder", req.getIsZFKNewOrder()));//总商订单ID长订单号
				params.add(new BasicNameValuePair("zfkNumList", req.getZfkNumList()));//总商订单ID长订单号
			}else {
				params.add(new BasicNameValuePair("manualImeiCode", req.getManualImeiCode()));//终端串号
				params.add(new BasicNameValuePair("manualNetCardNum", req.getManualNetCardNum()));//上网卡号码、手机号码
				params.add(new BasicNameValuePair("manualUsimCardNum", req.getManualUsimCardNum()));//USIM卡号
				params.add(new BasicNameValuePair("manualOrderNo", req.getManualOrderNo()));//开户单号(BSS/ESS/CBSS)
			}
			/**手动开户补录信息end**/
			
			responseStr = client.post(params, url);
			JSONObject respJson = JSONObject.fromObject(responseStr);
			JSONObject respInfo = respJson.getJSONObject("retInfo");
			if(respInfo.containsKey("RespCode") && "1".equals(respInfo.getString("RespCode"))){
				response.setError_code("-1");
				response.setError_msg("开户确认提交失败，订单状态已变更，请刷新订单列表");
				log.info("=====["+req.getOrderId()+"-自动开户-确认提交订单]开户确认提交失败，订单状态已变更请刷新订单列表，返回报文："+responseStr);
				return response;
			}
			if(respInfo.containsKey("retFlag") && respInfo.getBoolean("retFlag")){
				response.setError_code("0");
				return response;
			}
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError_msg(e.getLocalizedMessage());
			log.info("=====["+req.getOrderId()+"-自动开户-确认提交订单]开户确认提交异常，返回报文："+responseStr);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setError_msg(e.getLocalizedMessage());
			log.info("=====["+req.getOrderId()+"-自动开户-确认提交订单]开户确认提交异常，返回报文："+responseStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("=====["+req.getOrderId()+"-自动开户-确认提交订单]开户确认提交异常，返回报文："+responseStr);
		}
		response.setError_code("-1");
		return response;
	}
	
	
	/**
	 * 外呼确认操作
	 * @param client 总商登录客户端
	 * @param orderId 订单id
	 */
	public CrawlerResp callOutConfirm(ZBSystemClient client, String orderId,String detailHtml){
		CrawlerResp resp = new CrawlerResp();
		Document doc = Jsoup.parse(detailHtml);
		//String orderId = doc.select("#orderId").val();
		String tmplId = doc.select("#goodsTMPLId").val();
		String goodsInstId = doc.select("#goodInstId").val();
		String goodsType = doc.select("#hiddenGoodsType").val();
		
		String certNum = doc.select("#idCard").val();
		String certType = certNum.length() ==15?"01":"02";
		String manualImeiCode = doc.select("#manualImeiCode").val();
		String manualNetCardNum = doc.select("#manualNetCardNum").val();
		String manualOrderNo = doc.select("#manualOrderNo").val();
		String manualUsimCardNum = doc.select("#manualUsimCardNum").val();
		
		//外呼确认
		String callOutConfirmUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CALL_OUT_CONFIRM_OPERATION);
		
		JSONObject jsonParams = new JSONObject(); 
		
		//通过爬虫开户详情页获取固定参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();//创建参数队列
		formparams.add(new BasicNameValuePair("orderId", orderId));
		formparams.add(new BasicNameValuePair("tmplId", tmplId));
		formparams.add(new BasicNameValuePair("goodsInstId", goodsInstId));
		formparams.add(new BasicNameValuePair("certType", certType));
		formparams.add(new BasicNameValuePair("certNum", certNum));
		formparams.add(new BasicNameValuePair("manualImeiCode", manualImeiCode));//终端串号
		formparams.add(new BasicNameValuePair("manualNetCardNum", manualNetCardNum));//上网卡号码、手机号码
		formparams.add(new BasicNameValuePair("manualUsimCardNum", manualUsimCardNum));//USIM卡号
		formparams.add(new BasicNameValuePair("manualOrderNo", manualOrderNo));//开户单号(BSS/ESS/CBSS)
		formparams.add(new BasicNameValuePair("goodsType", goodsType));//开户商品类型(2G/3G/4G)
		
		/*jsonParams.put("orderId", orderId);
		jsonParams.put("tmplId", tmplId);
		jsonParams.put("goodsInstId", goodsInstId);
		jsonParams.put("goodsType", goodsType);
		jsonParams.put("certType", certType);
		jsonParams.put("certNum", certNum);
		jsonParams.put("manualImeiCode", manualImeiCode);//终端串号
		jsonParams.put("manualNetCardNum", manualNetCardNum);//上网卡号码、手机号码
		jsonParams.put("manualUsimCardNum", manualUsimCardNum);//USIM卡号
		jsonParams.put("manualOrderNo", manualOrderNo);//开户单号(BSS/ESS/CBSS)
		jsonParams.put("goodsType", goodsType);//开户商品类型(2G/3G/4G)*/
		String responseStr = "";
		try{
			//responseStr = client.jsonPost(jsonParams.toString(), callOutConfirmUrl);
			responseStr = client.post(formparams, callOutConfirmUrl);
			
			JSONObject respJson = JSONObject.fromObject(responseStr);
			if(respJson.containsKey("retInfo")){
				JSONObject retInfo = respJson.getJSONObject("retInfo");
				if(retInfo.containsKey("RespCode")){
					String RespCode = retInfo.getString("RespCode");
					if("1".equals(RespCode)){
						log.info("=====["+orderId+"-外呼确认]订单外呼确认失败，订单状态已变更，返回报文："+responseStr);
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg("订单外呼确认失败，订单状态已变更："+responseStr);
						return resp;
					}
				}
				if(retInfo.containsKey("retFlag")){
					boolean retFlag = retInfo.getBoolean("retFlag");
					if(retFlag){
						resp.setError_code(ConstsCore.ERROR_SUCC);
						resp.setError_msg("订单外呼确认成功");
					}else{
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg("订单外呼确认失败"+retInfo);
						log.info("=====["+orderId+"-外呼确认]订单外呼确认失败，返回报文："+responseStr);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单外呼确认异常"+e.getMessage());
			log.info("=====["+orderId+"-外呼确认]订单外呼确认异常，返回报文："+responseStr);
		} 
		return resp;
	}
	
	/**
	 * 抓取订单详情信息
	 * @param client
	 * @param orderMap
	 * @param oldFileName
	 */
	public static String parseDetailFileOrderCtn(ZBSystemClient client,Map<String, Object> orderMap,Map<String, Object> commonMap,String oldFileName,String threadName){
		String stdResult = "";
		String orderId = orderMap.get("OrderNum").toString();
		String orderNo = orderMap.get("OrderId").toString();
		ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		//客服订单查询页面详情按钮链接
    	//String detailUrl = ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL;
		String detailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL);
    	
    	// 创建参数队列
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", orderId));//订单ID
		detailparams.add(new BasicNameValuePair("orderType", "1"));
		
		String detailOrder = null;
		try {
			detailOrder = client.post(detailparams, detailUrl);
		} catch (Exception e) {
			stdResult = "进入客服订单详情异常："+e.getMessage();
			e.printStackTrace();
		}
		//爬虫客服订单查询页面详情数据
		if(StringUtils.isNotBlank(detailOrder)){//爬虫获取的订单详情数据不为空
		    //getFile("allocation",oldFileName,orderId);
			Document detaildoc = Jsoup.parse(detailOrder);//解析详情数据
			//&nbsp;空格特殊处理
			String spaceStr = Jsoup.parse("&nbsp;").text();
			if(detaildoc!=null){
				Map<String, Object> goodsInfoMap = new HashMap<String, Object>(); //商品信息节点
			 	Map<String, Object> goodsAttInfoMap = new HashMap<String, Object>();  //商品附属信息节点
			 	Map<String, Object> activityInfoMap = new HashMap<String, Object>(); //活动信息节点
			 	Map<String, Object> resourcesInfoMap = new HashMap<String, Object>(); //终端信息节点
			 	//==================身份证信息====================
			 	getCertInfo(detaildoc,orderMap,goodsAttInfoMap);
			 	//=============订单收货配送信息===============
				getDeliveryInfo(detaildoc,orderMap,zteclient,spaceStr);
				//=================发票信息=================
				getInvoiceInfo(detaildoc,orderMap);
				//===============优惠信息====================
				//getDiscountInfo(detaildoc,orderMap);
				//================赠品信息==================
				//getGiftInfo(detaildoc, orderMap);
				//================加盟商盟信息============
				//getLeagueInfo(detaildoc, orderMap);
				//==========下单时间商品数量信息===============
				String ifSendPhotos = "0";//照片同步
				String goodsNum = "1";//订单中商品的总数量(需计算用户购买商品的数量)
				String orderTime = "";//下单时间
				Elements orderTimeElements = detaildoc.select("li[class=operationTime]");
				if(orderTimeElements!=null && orderTimeElements.size()>0){
					Element orderTimeElement = orderTimeElements.get(1);
					if(orderTimeElement!=null){	
						orderTime = orderTimeElement.text().trim();//下单时间，YYYY-MM-DD HH24:MI:SS
					}
				}
				//==============订单费用信息=====================
				getOrderFeeInfo(detaildoc, orderMap);
				//================支付信息===================
				getPayInfo(detaildoc,orderMap,orderTime,spaceStr);
				//===========useCustInfo(责任人使用人信息 爬虫不到相关信息)=============
				//getUseCustInfo(detaildoc, orderMap);
				//============订单数据===========================
				orderMap.put("GoodsNum", goodsNum);
				orderMap.put("IfSendPhotos",ifSendPhotos);
				orderMap.put("OrderTime", orderTime);
				//================获取es_std_goods_config商品配置信息===============
				getGoodsInfo(detaildoc, goodsInfoMap,goodsAttInfoMap,activityInfoMap,resourcesInfoMap, zteclient);
				//===================商品费用明细(爬虫取不到)==================
				//getGoodsFeeInfo(detaildoc, goodsInfoMap);
				//===================商品属性信息=========
				getGoodsAttInfo(detaildoc, goodsAttInfoMap, orderMap, commonMap);
				
				//ZHKL:号卡 ZHYL:号卡合约 ZZDL:终端合约 ZSWK:上网卡 ZLZD:裸终端ZPJL:配件 ZYWL:业务变更
				String goodsType = (String)goodsAttInfoMap.get("GoodsType");
				 
				//上网卡特殊处理
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equals(goodsType)){
					Elements packageNameElements = detaildoc.getElementsContainingOwnText("包名称：").parents().select("dd");
					if(packageNameElements!=null && packageNameElements.size()>0){
						String packageName = packageNameElements.get(0).text().trim();
						goodsAttInfoMap.put("ProPacCode", "");
						goodsAttInfoMap.put("ProPacDesc", packageName);
					}
				}
				Elements orderCityElements = detaildoc.getElementsContainingOwnText("归属地：").parents().select("dd");
				if(orderCityElements!=null && orderCityElements.size()>0){
					String orderCityStr = orderCityElements.get(0).text().trim();
					String orderCity = "";
					if(StringUtils.isNotBlank(orderCityStr)){
						orderCity = LocalCrawlerUtil.AREAIDBACK.get(orderCityStr);//订单地市
					}
					if(StringUtils.isBlank((String)orderMap.get("OrderCity"))){//地市为空
						orderMap.remove("OrderCity");
						orderMap.put("OrderCity", orderCity);
					}
				}
				//===============合约计划信息================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //商品类型为：号卡合约和终端合约的增加合约计划信息
					getActivityInfo(detaildoc, goodsInfoMap, goodsAttInfoMap,activityInfoMap);
				}
				//================终端信息====================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZLZD.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //商品类型为：终端合约和裸终端的增加终端信息
					String isCustomized = "TRUE";//是否为定制机(爬虫取不到数据)
					goodsAttInfoMap.put("IsCustomized", isCustomized);//(必填)
					getResourcesInfo(detaildoc, goodsInfoMap, goodsAttInfoMap, resourcesInfoMap);
				}
				//===========号码信息===========================
				if(StringUtils.isNotBlank(goodsType) && !EcsOrderConsts.ZB_GOODS_TYPE_ZLZD.equals(goodsType)){
					//不是裸机的都增加号码信息
					getPhoneInfo(detaildoc, goodsAttInfoMap, orderMap, commonMap);
				}
				//===========可选包信息============================
				if(StringUtils.isNotBlank(goodsType) && EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equals(goodsType)
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equals(goodsType) 
						 || EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equals(goodsType)){
					 //总部号卡、号卡合约、终端合约增加可选包信息（目前暂不添加）
					 //getMallPackageInfo(detaildoc, goodsAttInfoMap);
				}
				
				List<Map<String, Object>> goodsAttInfosMap = new ArrayList<Map<String, Object>>();
				goodsAttInfosMap.add(goodsAttInfoMap);
	
				goodsInfoMap.put("GoodsAttInfo", goodsAttInfosMap);//商品添加商品属性
				
				List<Map<String, Object>> goodsInfosMap = new ArrayList<Map<String, Object>>();
				goodsInfosMap.add(goodsInfoMap);
				 
				orderMap.put("GoodsInfo", goodsInfosMap);//订单添加商品信息
				 
				if(orderMap!=null && orderMap.size()>0){//买家昵称为空则取证件名称
					if(StringUtils.isBlank((String)orderMap.get("RegisterName"))){
						orderMap.remove("RegisterName");
						orderMap.put("RegisterName",orderMap.get("ConsiName"));
					}
				}
	
				String reqJosn = "";
				if(orderMap!=null && orderMap.size()>0){
					JSONObject json = JSONObject.fromObject(orderMap);//处理报文
					reqJosn = json.toString();
				}
				 
				log.info("=====封装报文所需的json字符串："+reqJosn);
				if(StringUtils.isNotBlank(reqJosn)){//组装报文不为空则调用.总商标准化收单接口
					boolean success = getOrderCtn(reqJosn,threadName);
					if(!success){
						stdResult = "调用收单接口失败";
					}else{//锁单
						//log.info("=====["+orderId+"]审核锁定成功");
						String reviewBtnUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL)+orderId+"?orderId="+orderId;
						
						String reviewBtnResult;
						try {
							reviewBtnResult = client.post(new ArrayList<NameValuePair>(),reviewBtnUrl);
					
							Map<String,String> paramMap = new HashMap<String, String>();
							paramMap.put("deliverTypeCode", "");
							paramMap.put("templateId", "");
							paramMap.put("isGroupCust", "");
							if(StringUtil.isEmpty(reviewBtnResult) || reviewBtnResult.indexOf("value=\"审核通过\"") == -1){
								log.info("=====["+orderId+"]审核锁定失败");
							}else{
								log.info("=====["+orderId+"]审核锁定成功");
							}
						} catch (SystemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else{
					stdResult = "组装报文为空";
				}
			}
			else{
				stdResult = "使用Jsoup转换客服订单详情页失败";
			}
		}else{
			stdResult = "爬虫获取的获取订单详情数据为空！";
			log.info("======爬虫客服订单页面获取的获取订单详情数据为空！");
		}
		return stdResult;
	}
	/**
	 * 爬虫获取商品属性信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param goodsInfoMap
	 * @param orderMap
	 */
	private static void getGoodsAttInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap,Map<String, Object> orderMap,Map<String, Object> commonMap){
		 String saleMode = "";//销售方式
		 String appType = "";//应用类别，当加入具体集团时为必填 0：行业应用 	1：非行业应用
		 String checkType = "01";//认证类型 cbss系统订单必传 01：本地认证	02：公安认证	03：二代证读卡器
		 String customerType = EcsOrderConsts.CUSTOMER_CUST_TYPE_GRKH;//客户类型  个人客户 集团 客户
		 String groupId = "";//集团ID
		 String certLoseTime = "2050-01-01 00:00:00";//证件失效时间
		 String cardType = "NM";//卡类型  默认为普卡
		 Elements cardTypeElements = detaildoc.getElementsContainingOwnText("卡类型：").parents().select("dd");
		 if(cardTypeElements!=null && cardTypeElements.size()>0){
			 Element cardTypeElement = cardTypeElements.get(0);
			 if(cardTypeElement!=null){
				 cardType = LocalCrawlerUtil.CARDTYPEBACK.get(cardTypeElement.text().trim());//卡类型
				 if(StringUtils.isBlank(cardType) || cardType == null || "null".equals(cardType)){
					 cardType = "NM";
				 }
			 }
		 }
		 String productName = "";
		 //Elements productNameElements = detaildoc.select("li[class=commodityComposition]").select("dd");
		 Elements productNameElements = detaildoc.getElementsContainingOwnText("套餐：").parents().select("dd");
		 if(productNameElements!=null && productNameElements.size()>0){
			 productName = productNameElements.get(0).text().trim();//产品名称
		 }
		 String productType = "MAIN";//产品类型 MAIN 4G基本套餐 DIY DIY套餐
		 String sex = "";//性别，固定长度1位， M：男， F：女
		 String simId = ""; //SIM卡号
		 String subAppType = "";//应用子类型
		 
		 goodsAttInfoMap.put("AppType", appType);
		 goodsAttInfoMap.put("CardType", cardType);
		 goodsAttInfoMap.put("CertLoseTime", certLoseTime);
		 goodsAttInfoMap.put("CertNum", orderMap.get("ConsiCertNum"));
		 goodsAttInfoMap.put("CertType", orderMap.get("ConsiCertType"));
		 goodsAttInfoMap.put("CheckType", checkType);
		 goodsAttInfoMap.put("CustomerName", orderMap.get("ConsiName"));
		 goodsAttInfoMap.put("CustomerType", customerType);
		 goodsAttInfoMap.put("GroupId", groupId);
		 goodsAttInfoMap.put("ProductName", productName);
		 goodsAttInfoMap.put("ProductNet", commonMap.get("numNet"));
		 goodsAttInfoMap.put("ProductType", productType);
		 goodsAttInfoMap.put("SaleMode", saleMode);
		 goodsAttInfoMap.put("Sex", sex);
		 goodsAttInfoMap.put("SimId", simId);
		 goodsAttInfoMap.put("SubAppType", subAppType);
		 goodsAttInfoMap.put("UserType", commonMap.get("userType"));
		 //=============获取发展人和推荐人信息
		 getDevelopRefereeInfo(detaildoc, goodsAttInfoMap);
	}
	
	/**
	 * 爬虫获取证件信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getCertInfo(Document detaildoc,Map<String, Object> orderMap,Map<String, Object> goodsAttInfoMap){
		String consiCertNum = "";//证件号码
		 Elements consiCertNumElements = detaildoc.select("span[class=idCardShow]");
		 if(consiCertNumElements!=null && consiCertNumElements.size()>0){
			 consiCertNum = consiCertNumElements.text().trim();
		 }
		 String consiCertType = "";//证件类型
		 Elements consiCertElements = detaildoc.select("em[class=c_999]");
		 if(consiCertElements!=null){
			 String consiCertStr = consiCertElements.text().trim();
			 if(StringUtils.isNotBlank(consiCertStr)){
				 consiCertType = LocalCrawlerUtil.CUSTCARDTYPEBACK.get(consiCertStr.replace("(", "").replace(")", ""));
			 }
		 }
		 String consiName = "";//证件名称
		 Elements consiNameElements = detaildoc.select("label[class=receiverNameShow]");
		 if(consiNameElements!=null && consiNameElements.size()>0){
			 consiName = consiNameElements.text().trim();
		 }
		 String consiPhone = "";//联系电话
		 Elements consiPhoneElements = detaildoc.select("p:contains(电话：)");
		 if(consiPhoneElements!=null && consiPhoneElements.size()>0){
			 String phoneStr = consiPhoneElements.text().trim();
			 if(StringUtils.isNotBlank(phoneStr)){
				 consiPhone = phoneStr.substring(phoneStr.indexOf("：")+1, phoneStr.length());
			 }
		 }
		 String consiEmail = "";//联系邮箱
		 Elements consiEmailElements = detaildoc.select(":containsOwn(联系邮箱：)");
		 if(consiEmailElements!=null && consiEmailElements.size()>0){
			 String consiEmailStr = consiEmailElements.text().trim();
			 if(StringUtils.isNotBlank(consiEmailStr)){
				 consiEmail = consiEmailStr.substring(consiEmailStr.indexOf("：")+1, consiEmailStr.length());
			 }
		 }
		 String consiCertAddress = "";//证件地址
		 Elements certAddressElements = detaildoc.select("span[class=addressName]");
		 if(certAddressElements!=null && certAddressElements.size()>0){
			 consiCertAddress = certAddressElements.text().trim();
		 }
		 orderMap.put("ConsiCertNum", consiCertNum);
		 orderMap.put("ConsiCertType", consiCertType);
		 orderMap.put("ConsiName", consiName);
		 orderMap.put("ConsiPhone", consiPhone);
		 //==========传递参数用=========
		 goodsAttInfoMap.put("CertAddr", consiCertAddress);
	}
	/**
	 * 爬虫获取配送信息
	 * @param detaildoc
	 * @param orderMap
	 * @param zteclient
	 * @param spaceStr
	 */
	private static void getDeliveryInfo(Document detaildoc,Map<String, Object> orderMap,ZteClient zteclient,String spaceStr){
		 String consiGoodsProv = "";//收货省
		 String consiGoodsCity = "";//收货地市
		 String consiGoodsDist = "";//收货地区
		 String consiPostAddress = "";//收货地址
		 Elements postAddressElements = detaildoc.select(":containsOwn(收货地址：)");//div[class=inforPosition]:containsOwn(收货地址：)
		 if(postAddressElements!=null && postAddressElements.size()>0){
			 String consiGoodsStr = postAddressElements.select("label").text().trim();
			 if(StringUtils.isNotBlank(consiGoodsStr)){
				 if(StringUtils.isNotBlank(consiGoodsStr)){
					 String[] consiGoodsArray = consiGoodsStr.split("，");
					 consiGoodsProv = LocalCrawlerUtil.AREAIDBACK.get(consiGoodsArray[0].trim());
					 consiGoodsCity = LocalCrawlerUtil.AREAIDBACK.get(consiGoodsArray[1].trim());
					 consiGoodsDist = consiGoodsArray[2].trim();
					 if(StringUtils.isNotBlank(consiGoodsDist)){
						 RegionsGetReq req = new RegionsGetReq();
						 req.setRegion_cond_type(RegionsGetReq.NAME_COND_TYPE);
						 req.setLocal_name(consiGoodsDist);
						 RegionsGetResp resp = zteclient.execute(req, RegionsGetResp.class);
						 if(resp!=null && resp.getRegions()!=null){
							 consiGoodsDist = resp.getRegions().getRegion_id();
						 }
					 }
					 //收货地址获取整个地址不做截取（跟老板确认的）
					 consiPostAddress = consiGoodsArray[3];
				 }
			 }
		 }

		 String deliMode = "KD";//配送方式 默认快递
		 String deliTimeMode = "NOLIMIT";//配送时间类型  默认不限时间送货
		 Elements deliModeElements = detaildoc.select(":containsOwn(配送方式：)").select("label");
		 if(deliModeElements!=null && deliModeElements.size()>0){
			 Element deliModeElement = deliModeElements.get(0);
			 if(deliModeElement!=null){
				 String deliModeStr = deliModeElement.text().trim();
				 if(StringUtils.isNotBlank(deliModeStr)){
					 String deliModeStr1 = deliModeStr.split(" ")[0].trim();
					 if(StringUtils.isNotBlank(deliModeStr1) && deliModeStr1.indexOf("自提")!=-1){
						 deliMode = "ZT";
					 }else{
						 deliMode = LocalCrawlerUtil.DELIVERYTYPE.get(deliModeStr1);//配送方式
					 }
				 }
			 }
			 if(deliModeElements.size()>1){//存在第二个lable标签
				 Element deliTimeModeElement = deliModeElements.get(1);
				 if(deliTimeModeElement!=null){
					 String deliTimeModeStr = deliTimeModeElement.text();
					 if(StringUtils.isNotBlank(deliTimeModeStr)){
						 deliTimeMode = LocalCrawlerUtil.DELIVERYTIMETYPE.get(deliTimeModeStr.trim());//配送时间类型
					 }
				 }
			 }
		 }
		 if(deliMode==null || "null".equals(deliMode) || StringUtils.isBlank(deliMode)){
			 deliMode = "KD";
		 }
		 if(deliTimeMode==null || "null".equals(deliTimeMode) || StringUtils.isBlank(deliTimeMode)){
			 deliTimeMode = "NOLIMIT";
		 }
		 orderMap.put("ConsiGoodsCity", consiGoodsCity);
		 orderMap.put("ConsiGoodsDist", consiGoodsDist);
		 orderMap.put("ConsiGoodsProv", consiGoodsProv);
		 orderMap.put("ConsiPostAddress", consiPostAddress);
		 orderMap.put("deliMode", deliMode);
		 orderMap.put("deliTimeMode", deliTimeMode);
	}
	/**
	 * 爬虫获取发票信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getInvoiceInfo(Document detaildoc,Map<String, Object> orderMap){
		String invoiceDesc = "";//发票内容
		 String invoiceTitle = "";//发票抬头
		 String invoiceType = ""; //发票类型 NOW 即时发票  MONTH 月结发票
		 Elements invoiceDescElements = detaildoc.select(":containsOwn(发票内容：)");
		 if(invoiceDescElements!=null && invoiceDescElements.size()>0){
			 String invoiceDescStr = invoiceDescElements.text();
			 if(StringUtils.isNotBlank(invoiceDescStr) && invoiceDescStr.indexOf("：")!=-1){
				 invoiceDesc = invoiceDescStr.substring(invoiceDescStr.indexOf("：")+1, invoiceDescStr.length()).trim();
				 if(StringUtils.isNotBlank(invoiceDesc) && "明细".equals(invoiceDesc)){
					 invoiceDesc = "MX";
					 invoiceType = "NOW";//默认为即时发票
					 
				 }
			 }//没有发票内容就不设置发票抬头
			 Elements invoiceTitleElements = detaildoc.select(":containsOwn(发票抬头：)");
			 if(invoiceTitleElements!=null && invoiceTitleElements.size()>0){
				 String invoiceTitleStr = invoiceTitleElements.text().trim();
				 if(StringUtils.isNotBlank(invoiceTitleStr)){
					 invoiceTitle = invoiceTitleStr.substring(invoiceTitleStr.indexOf("：")+1, invoiceTitleStr.length()).trim();
				 }else{
					 invoiceTitle = orderMap.get("ConsiName").toString();//发票抬头默认为收货人
				 }
			 }else{
				 invoiceTitle = orderMap.get("ConsiName").toString();//发票抬头默认为收货人
			 }
		 }
		 orderMap.put("InvoiceDesc", invoiceDesc);
		 orderMap.put("InvoiceTitle", invoiceTitle);
		 orderMap.put("InvoiceType", invoiceType);
	}
	/**
	 * 爬虫获取优惠信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getDiscountInfo(Document detaildoc,Map<String, Object> orderMap){
		 String discountName = "";
		 Elements discountNameElements = detaildoc.select("li[class=commodityName]").select(":containsOwn(活动：)").parents().select("dd");
		 if(discountNameElements!=null && discountNameElements.size()>0){
			 discountName = discountNameElements.get(0).text().trim();
			 List<Map<String,String>> discountInfos = new ArrayList<Map<String,String>>();
			 Map<String,String> discountInfoMap = new HashMap<String, String>();
			 discountInfoMap.put("DiscountID", "");
			 discountInfoMap.put("DiscountName", discountName);
			 discountInfoMap.put("DiscountType", "");
			 discountInfoMap.put("DiscountValue", "");
			 discountInfos.add(discountInfoMap);
			 orderMap.put("DiscountInfo", discountInfos);
		 }
	}
	/**
	 * 爬虫获取赠品信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getGiftInfo(Document detaildoc,Map<String, Object> orderMap){
		 String giftName = "";
		 Elements giftNameElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(赠品：)").parents().select("dd");
		 if(giftNameElements!=null && giftNameElements.size()>0){
			 giftName = giftNameElements.get(0).text().trim();
			 List<Map<String,String>> giftInfos = new ArrayList<Map<String,String>>();
			 Map<String,String> giftInfoMap = new HashMap<String, String>();
			 giftInfoMap.put("GiftDesc", "");
			 giftInfoMap.put("GiftID", "");
			 giftInfoMap.put("GiftName", giftName);
			 giftInfoMap.put("GiftNum", "1");
			 giftInfoMap.put("GiftValue", "0");
			 giftInfos.add(giftInfoMap);
			 orderMap.put("GiftInfo", giftInfos);
		 }
	}
	/**
	 * 爬虫获取联盟信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getLeagueInfo(Document detaildoc,Map<String, Object> orderMap){
		 Map<String,String> leagueInfoMap = new HashMap<String, String>();
		 leagueInfoMap.put("HigherLeagueId", "");
		 leagueInfoMap.put("HigherLeagueName", "");
		 leagueInfoMap.put("LeagueId", "");
		 leagueInfoMap.put("LeagueName", "");
		 orderMap.put("LeagueInfo", leagueInfoMap);
	}
	/**
	 * 爬虫获取订单费用信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getOrderFeeInfo(Document detaildoc,Map<String, Object> orderMap){
		 String orderOrigFee = "0";//订单应收总价，单位为厘
		 double moneyOrigFee = 0.0;
		 String orderReliefFee = "0";//订单减免金额，单位为厘
		 double moneyReliefFee = 0.0;
		 String origPostFee = "0";//应收邮寄费用，单位为厘
		 double moneyPostFee = 0.0;
		 Elements orderReliefFeeElements1 = detaildoc.select("li[class=totalAmount]").select("span");
		 Elements orderOrigFeeElements = detaildoc.select(":containsOwn(商品金额总计：)").select("span");
		 if(orderOrigFeeElements!=null && orderOrigFeeElements.size()>0){
			 String orderOrigFeeStr = orderOrigFeeElements.text().trim();
			 if(StringUtils.isNotBlank(orderOrigFeeStr)){
				 moneyOrigFee = Double.valueOf(orderOrigFeeStr.replace("￥", "").trim());
				 orderOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrigFee);//元转厘 订单应收总价，单位为厘
			 }
		 }else{
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String orderOrigFeeStr = orderReliefFeeElements1.get(0).text().trim();
				 if(StringUtils.isNotBlank(orderOrigFeeStr)){
					 moneyOrigFee = Double.valueOf(orderOrigFeeStr.replace("￥", "").trim());
					 orderOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrigFee);//元转厘 订单应收总价，单位为厘
				 }
			 }
		 }
		 Elements orderReliefFeeElements = detaildoc.select(":containsOwn(减免费用：)").select("span");
		 if(orderReliefFeeElements!=null && orderReliefFeeElements.size()>0){
			 String reliefFeeStr = orderReliefFeeElements.text().trim();
			 if(StringUtils.isNotBlank(reliefFeeStr)){
				 moneyReliefFee = Double.valueOf(reliefFeeStr.replace("￥", "").trim());
				 orderReliefFee = CrawlerUtils.parseMoneyToLi(moneyReliefFee);//订单减免金额，单位为厘 
			 }
		 }else{//搜索不到减免费用文字则通过另一种做匹配
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String reliefFeeStr = orderReliefFeeElements1.get(1).text().trim();
				 if(StringUtils.isNotBlank(reliefFeeStr)){
					 moneyReliefFee = Double.valueOf(reliefFeeStr.replace("￥", "").trim());
					 orderReliefFee = CrawlerUtils.parseMoneyToLi(moneyReliefFee);//订单减免金额，单位为厘 
				 }
			 }
		 }
		 double moneyRealFee = moneyOrigFee - moneyReliefFee;//商品金额总计 - 减免费用 = 订单实收总价（不包括运费）
		 String orderRealFee = CrawlerUtils.parseMoneyToLi(moneyRealFee);//订单实收总价，单位为厘
		 Elements origPostFeeElements = detaildoc.select(":containsOwn(运费：)").select("span");
		 if(origPostFeeElements!=null && origPostFeeElements.size()>0){
			 String origPostFeeStr = origPostFeeElements.text().trim();
			 moneyPostFee = Double.valueOf(origPostFeeStr.replace("￥", "").trim());
			 origPostFee = CrawlerUtils.parseMoneyToLi(moneyPostFee);//应收邮寄费用，单位为厘
		 }else{
			 if(orderReliefFeeElements1!=null && orderReliefFeeElements1.size()>0){
				 String origPostFeeStr = orderReliefFeeElements1.get(2).text().trim();
				 moneyPostFee = Double.valueOf(origPostFeeStr.replace("￥", "").trim());
				 origPostFee = CrawlerUtils.parseMoneyToLi(moneyPostFee);//应收邮寄费用，单位为厘
			 }
		 }
		 String realPostFee = origPostFee;//实收邮寄费用，单位为厘
		 orderMap.put("OrderOrigFee", Double.valueOf(orderOrigFee));
		 orderMap.put("OrderRealFee", Double.valueOf(orderRealFee));
		 orderMap.put("OrderReliefFee", Double.valueOf(orderReliefFee));
		 orderMap.put("OrigPostFee", Double.valueOf(origPostFee));
		 orderMap.put("RealPostFee", Double.valueOf(realPostFee));
	}
	/**
	 * 爬虫获取支付信息
	 * @param detaildoc
	 * @param orderMap
	 * @param orderTime
	 * @param spaceStr
	 */
	private static void getPayInfo(Document detaildoc,Map<String, Object> orderMap,String orderTime,String spaceStr){
		 String payFinTime = orderTime;//支付完成时间，YYYY-MM-DD HH24:MI:SS 货到付款的没有支付完成时间
		 String payTypeStr = "";
		 String payType = "HDFK";//默认货到付款
		 String payWay = "XJZF";//默认现金支付
		 String payChannelId = "";//支付渠道编码
		 String payChannelName = "";//支付渠道名称
		 String payProviderId = "";//支付机构编码
		 String payProviderName = "";//支付机构名称
		 Elements payTypeElements = detaildoc.select(":containsOwn(支付方式：)").select("label");
		 if(payTypeElements!=null && payTypeElements.size()>0){
			 Element payTypeElement = payTypeElements.get(0);
			 if(payTypeElement!=null){
				 payTypeStr = payTypeElement.text().trim();
				 if(StringUtils.isNotBlank(payTypeStr)){
					 String[] payTypeArray = payTypeStr.split("\\(");
					 payType = LocalCrawlerUtil.PAYTYPEBACK.get(payTypeArray[0].trim());//支付类型
					 payWay = LocalCrawlerUtil.PAYWAY.get(payTypeArray[1].replace(")", "").trim());//支付方式
				 }
			 }
			 if(payTypeElements.size()>1){//存在支付信息标签
				 Element payProviderElement = payTypeElements.get(1);
				 if(payProviderElement!=null){
					 String payStr = payProviderElement.text();
					 if(StringUtils.isNotBlank(payStr)){
						 String payProviderStr = payStr.substring(payStr.indexOf("：")+1, payStr.length()).replace(spaceStr, "").trim();
						 if(StringUtils.isNotBlank(payProviderStr) && payProviderStr.indexOf(" ")!=-1){
							 payChannelName = payProviderStr.substring(0, payProviderStr.indexOf(" ")).replace(spaceStr, "").trim();
							 if(StringUtils.isNotBlank(payChannelName)){
								 payChannelId = LocalCrawlerUtil.PAY_CHANNEL.get(payChannelName);//通过支付渠道名称获取支付渠道编码
								 if(payChannelId==null || "null".equals(payChannelId) || StringUtils.isBlank(payChannelId)){
									 payChannelId = "";
									 payChannelName = "";
								 }
							 }
							 //取最后空格之间的内容
							 payProviderName = payProviderStr.substring(payProviderStr.lastIndexOf(" "),payProviderStr.length()).replace(spaceStr, "").trim();
							 if(StringUtils.isNotBlank(payProviderName)){
								 payProviderId = LocalCrawlerUtil.PAY_PROVIDER.get(payProviderName.trim());//通过支付机构名称获取支付机构编码
								 if(payProviderId==null || "null".equals(payProviderId) || StringUtils.isBlank(payProviderId)){
									 payProviderId = "";
									 payProviderName = "";
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 if(payType==null || "null".equals(payType) || StringUtils.isBlank(payType)){
			 payType = "HDFK";
		 }
		 if(payWay==null || "null".equals(payWay ) || StringUtils.isBlank(payWay)){
			 payWay = "XJZF";
		 }
		 Map<String, String> payInfoMap = new HashMap<String, String>();
		 payInfoMap.put("PayFinTime", payFinTime);
		 payInfoMap.put("PayPlatFormOrderId", "");
		 payInfoMap.put("PayChannelId", payChannelId);
		 payInfoMap.put("PayChannelName", payChannelName);
		 payInfoMap.put("PayProviderId", payProviderId);
		 payInfoMap.put("PayProviderName", payProviderName);
		 payInfoMap.put("PayType", payType);
		 payInfoMap.put("PayWay", payWay);
		 orderMap.put("PayInfo", payInfoMap);
	}
	/**
	 * 爬虫获取责任人信息
	 * @param detaildoc
	 * @param orderMap
	 */
	private static void getUseCustInfo(Document detaildoc,Map<String, Object> orderMap){
		Map<String, String> useCustInfoMap = new HashMap<String, String>();
		orderMap.put("useCustInfo", useCustInfoMap); 
	}
	/**
	 * 爬虫获取商品费用明细
	 * @param detaildoc
	 * @param goodsInfoMap
	 */
	private static void getGoodsFeeInfo(Document detaildoc,Map<String, Object> goodsInfoMap){
		 String feeDes = "";//收费项描述
		 String feeID = "";//收费项编码
		 String origFee = "";//应收金额，单位为厘
		 String realFee = "";//实收金额，单位为厘
		 String reliefFee = "";//减免金额，单位为厘
		 List<Map<String, String>> feeInfo = new ArrayList<Map<String, String>>();
		 Map<String, String> feeMap = new HashMap<String, String>();
		 feeMap.put("FeeDes", feeDes);
		 feeMap.put("FeeID", feeID);
		 feeMap.put("OrigFee", origFee);
		 feeMap.put("RealFee", realFee);
		 feeMap.put("ReliefFee", reliefFee);
		 feeInfo.add(feeMap);
		 goodsInfoMap.put("FeeInfo", feeInfo);
	}
	/**
	 * 爬虫获取商品信息
	 * @param detaildoc
	 * @param goodsInfoMap
	 * @param zteclient
	 */
	private static void getGoodsInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap,Map<String, Object> activityInfoMap,Map<String, Object> resourcesInfoMap, ZteClient zteclient){
		 String goodsCode = "";//页面没有该参数值，只能通过商品名称去获取商品编码
		 String goodsName = "";//商品名称
		 String productName = "";//产品名称
		 String productCode = "";//产品编码
		 //ZHKL:号卡 ZHYL:号卡合约 ZZDL:终端合约 ZSWK:上网卡 ZLZD:裸终端ZPJL:配件 ZYWL:业务变更
		 String goodsType = EcsOrderConsts.ZB_GOODS_TYPE_ZHKL;//商品类型 (根据什么条件来判断商品类型)
		 String laterActFlag = "0";//是否后激活订单（0：普通订单 1：后激活订单）
		 String numType = "1";//主副标识（1：主号；2：副号）
		 String productBrand = "4GPH";//产品品牌 3GPH 3G手机卡 3GWK 3G上网卡 3GWB 3G上网本 2GPH 2G手机卡 4GPH 4G手机卡
		 String reliefPresFlag = EcsOrderConsts.RELIEFPRES_FLAG_NO;//减免预存标记
		 String serType = "BAK";//付费类型 后付费：BAK，预付费：PRE
		 String activityCode = "";//合约编码
		 String actProtPer = "";//合约期
		 String activityType = "";//合约类型
		 String resourcesTypeId = "";//终端机型编码
		 String resourcesBrand = "";//终端品牌编码
		 String resourcesModel = "";//终端型号编码
		 String resourcesColor = "";//终端颜色编码
		 
		 Elements goodsNameElements = detaildoc.select("ul[class=listInfor]").select("li[class=commodityName]").select("dd");
		 Elements productNameElements = detaildoc.getElementsContainingOwnText("套餐：").parents().select("dd");
		 if(productNameElements!=null && productNameElements.size()>0){
			 productName = productNameElements.get(0).text().trim();//产品名称
		 }
		 if(goodsNameElements!=null && goodsNameElements.size()>0){
			 goodsName = goodsNameElements.get(0).text().trim();
			 log.info("=========================爬虫获取的商品名称为："+goodsName);
			 log.info("=========================爬虫获取的产品名称为："+productName);
			 if(StringUtils.isNotBlank(goodsName)){
				 StdGoodsGetReq req = new StdGoodsGetReq();
				 //【滴滴王卡】滴滴小王卡-58元套餐
				 req.setGoodsName(goodsName);
				 req.setTitleName(productName);
				 //req.setGoodsName("三星G3502粉24月购机送费4G全国套餐76元");
				 StdGoodsGetResp resp = zteclient.execute(req, StdGoodsGetResp.class);
				 if(resp!=null){
					 goodsCode = resp.getpCode();
					 productCode = resp.getSn();
					 goodsType = resp.getGoodsType();
					 laterActFlag = resp.getLaterActFlag();
					 numType = resp.getNumType();
					 productBrand = resp.getProductBrand();
					 reliefPresFlag = resp.getReliefPresFlag();
					 serType = resp.getSerType();
					 actProtPer = resp.getContractMonth();
					 activityType = resp.getActivityType();
					 resourcesBrand = resp.getBrandCode();
					 resourcesModel = resp.getModelCode();
					 resourcesColor = resp.getColorCode();
					 
					 if("ZHKL".equalsIgnoreCase(goodsType)){
						 //号卡
						 activityCode = resp.getpCode(); 
					 }else if ( "ZHYL".equalsIgnoreCase(goodsType)) {
						 //号卡合约
						 activityCode =  resp.getpCode(); 
						 //actProtPer = resp.getSn();
					 }else if ("ZZDL".equalsIgnoreCase(goodsType)) {
						 //总部终端合约类
						 activityCode =  resp.getpCode(); 
						 //actProtPer = resp.getSn();
						 resourcesTypeId = resp.getSn();
					 }else if ("ZSWK".equalsIgnoreCase(goodsType)) {
						 //总部上网卡商品
					 }else if ("ZPJL".equalsIgnoreCase(goodsType)) {
						//总部配件商品
					 }else if ("ZLZD".equalsIgnoreCase(goodsType)) {
						//总部裸终端商品
						 resourcesTypeId = resp.getSn();
					 }else {
						 activityCode =  resp.getpCode(); 
						 resourcesTypeId = resp.getSn();
					 }
				 }
			 }else{
				 log.info("=====爬虫抓取订单详情解析商品名称为空，解析报文："+detaildoc.toString());
			 }
		 }
		 String goodsOrigFee = "";//商品应收，单位为厘
		 String goodsRealFee = "";//商品实收，单位为厘
		 Elements goodsOrigElements = detaildoc.select("ul[class=listInfor]").select("li[class=settlement]");
		 if(goodsOrigElements!=null && goodsOrigElements.size()>0){
			 String goodsOrigStr = goodsOrigElements.text().trim();
			 if(StringUtils.isNotBlank(goodsOrigStr)){
				 double moneyOrig = Double.valueOf(goodsOrigStr.replace("￥", "").trim());
				 goodsOrigFee =  CrawlerUtils.parseMoneyToLi(moneyOrig);//元转厘
				 goodsRealFee = goodsOrigFee;//*1000 单位为厘
			 }
		 }
		 goodsInfoMap.put("GoodsCode", goodsCode);
		 goodsInfoMap.put("GoodsName", goodsName);
		 goodsInfoMap.put("GoodsOrigFee", goodsOrigFee);
		 goodsInfoMap.put("GoodsRealFee", goodsRealFee);
		 goodsInfoMap.put("GoodsReliefRes", "");//商品减免原因
		 //商品附属信息节点
		 goodsAttInfoMap.put("ProductCode", productCode);
		 goodsAttInfoMap.put("GoodsType", goodsType);
		 goodsAttInfoMap.put("LaterActFlag", laterActFlag);
		 goodsAttInfoMap.put("NumType", numType);
		 goodsAttInfoMap.put("ProductBrand", productBrand);
		 goodsAttInfoMap.put("ReliefPresFlag", reliefPresFlag);
		 goodsAttInfoMap.put("SerType", serType);
		 //合约计划配置信息
		 activityInfoMap.put("ActivityCode", activityCode);
		 activityInfoMap.put("ActProtPer", actProtPer);
		 activityInfoMap.put("ActivityType", activityType);
		 //终端配置信息
		 resourcesInfoMap.put("ResourcesTypeId", resourcesTypeId);
		 resourcesInfoMap.put("ResourcesBrand", resourcesBrand);
		 resourcesInfoMap.put("ResourcesModel", resourcesModel);
		 resourcesInfoMap.put("ResourcesColor", resourcesColor);
	}
	/**
	 * 爬虫获取发展人推荐人信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getDevelopRefereeInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap){
		 String developCode = "";
		 String developDepId = "";//发展人ID
		 String developName = "";
		 String realnameType = "1";//实名认证类型：1：未实名认证 2：已实名认证
		 String refereeName = "";//推荐人名称
		 String refereeNum = "";//推荐人号码（联系方式）
		 //&nbsp;空格特殊处理
		 String spaceStr = Jsoup.parse("&nbsp;").text();
		 Elements developElements = detaildoc.getElementsContainingOwnText("发展人：").parents().select("dd");
		 if(developElements!=null && developElements.size()>0){
			 String developCodeStr = developElements.get(0).text().replace(spaceStr, "").trim();
			 if(StringUtils.isNotBlank(developCodeStr)){
				 developCode = developCodeStr.substring(developCodeStr.indexOf("（")+1, developCodeStr.length()-1).replace(spaceStr, "").trim();
				 developName = developCodeStr.substring(0, developCodeStr.indexOf("（")).replace(spaceStr, "").trim();//发展人名称
			 }
		 }
		 String firstMonBillMode = "";
		 Elements firstMonElements = detaildoc.getElementsContainingOwnText("首月：").parents().select("dd");
		 if(firstMonElements!=null && firstMonElements.size()>0){
			 Element firstMonElement = firstMonElements.get(0);
			 if(firstMonElement!=null){
				 firstMonBillMode = LocalCrawlerUtil.FIRST_FEE_TYPE.get(firstMonElement.text().replace(spaceStr, "").trim());//首月资费方式 FIRST_FEE_TYPE_ALLM
				 if(firstMonBillMode==null || "null".equals(firstMonBillMode) || StringUtils.isBlank(firstMonBillMode)){
					 firstMonBillMode = "";
				 }
			 }
		 }
		 Elements refereeElements = detaildoc.getElementsContainingOwnText("推荐人：").parents().select("dd");
		 if(refereeElements!=null && refereeElements.size()>0){
			 String refereeStr = refereeElements.get(0).text().replace(spaceStr, "").trim();
			 if(StringUtils.isNotBlank(refereeStr)){
				 refereeName = refereeStr.substring(0, refereeStr.indexOf("(")).trim();//发展人名称
				 refereeNum = refereeStr.substring(refereeStr.indexOf("(")+1, refereeStr.length()-1).trim();
			 }
		 }
		 goodsAttInfoMap.put("DevelopCode", developCode);
		 goodsAttInfoMap.put("DevelopDepId", developDepId);
		 goodsAttInfoMap.put("DevelopName", developName);
		 goodsAttInfoMap.put("FirstMonBillMode", firstMonBillMode);
		 goodsAttInfoMap.put("RealnameType", realnameType);
		 goodsAttInfoMap.put("RefereeName", refereeName);
		 goodsAttInfoMap.put("RefereeNum", refereeNum);
	}
	/**
	 * 爬虫获取合约计划信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param goodsName
	 */
	private static void getActivityInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap, Map<String, Object> activityInfoMap){
		 List<Map<String, Object>> activityInfoMapList = new ArrayList<Map<String, Object>>();
		 //Map<String, Object> activityMap = new HashMap<String, Object>();
		 String goodsName = (String)goodsInfoMap.get("GoodsName");
		 String activityName = "";//合约名称(必填)
		 String activityType = "";//合约类型(必填)
		 String actProtPer = "";//合约期限(必填)
		 
		 Elements activityTypeElements = detaildoc.getElementsContainingOwnText("合约：").parents().select("dd");
		 if(activityTypeElements!=null && activityTypeElements.size()>0){
			 String activityTypeStr = activityTypeElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(activityTypeStr) && activityTypeStr.indexOf("/")!=-1){
				 activityType = LocalCrawlerUtil.ACTIVITY_TYPE.get(activityTypeStr.substring(0, activityTypeStr.indexOf("/")));
			 }else{
				 activityType = LocalCrawlerUtil.ACTIVITY_TYPE.get(activityTypeStr);
			 }
		 }
		 Elements activityElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(活动：)").parents().select("dd");
		 if(activityElements!=null && activityElements.size()>0){
		     activityName = activityElements.get(0).text().trim();
		     if(StringUtils.isNotBlank(activityName) && "无".equals(activityName)){
		    	 activityName = "";
		     }
		 }
		 Elements actProtPerElements = detaildoc.getElementsContainingOwnText("期限：").parents().select("dd");
		 if(actProtPerElements!=null && actProtPerElements.size()>0){
			 String actProtPerStr = actProtPerElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(actProtPerStr)){
				 actProtPer = actProtPerStr.substring(0, 2);
			 }else{
				 if(StringUtils.isNotBlank(goodsName) && goodsName.indexOf("【")!=-1 && goodsName.indexOf("】")!=-1){
					 String gname = goodsName.substring(goodsName.indexOf("【")+1, goodsName.indexOf("】"));
					 //actProtPer = CrawlerUtils.getNumbers(gname);
					 if(StringUtils.isNotBlank(gname)){
						 actProtPer = gname.substring(0, 2);
						 if(!CrawlerUtils.isDigit(actProtPer)){//获取的不是数字则转换为数字
							 if("两年".equals(actProtPer)){
								 actProtPer = "24";
							 }else if("三年".equals(actProtPer)){
								 actProtPer = "36";
							 }else{
								 actProtPer = "";
							 }
						 }
					 }
				 }
			 }
		 }else{
			 if(StringUtils.isNotBlank(goodsName) && goodsName.indexOf("【")!=-1 && goodsName.indexOf("】")!=-1){
				 String gname = goodsName.substring(goodsName.indexOf("【")+1, goodsName.indexOf("】"));
				 //actProtPer = CrawlerUtils.getNumbers(gname);
				 if(StringUtils.isNotBlank(gname)){
					 actProtPer = gname.substring(0, 2);
					 if(!CrawlerUtils.isDigit(actProtPer)){//获取的不是数字则转换为数字
						 if("两年".equals(actProtPer)){
							 actProtPer = "24";
						 }else if("三年".equals(actProtPer)){
							 actProtPer = "36";
						 }else{
							 actProtPer = "";
						 }
					 }
				 }
			 }
		 }
		 //如果爬虫抓取到的合约期不为空，则使用爬虫抓取到的
		 if(!StringUtils.isEmpty(actProtPer)){
			 activityInfoMap.put("ActProtPer", actProtPer);
		 }
		//如果爬虫抓取到的活动类型不为空，则使用爬虫抓取到的
		 if(!StringUtils.isEmpty(activityType)){
			 activityInfoMap.put("ActivityType", activityType);
		 }
		 activityInfoMap.put("ActivityName", activityName);
		 
		 //合约下自选包
		 String elementName = "";//元素名称 
		 String elementType = "";//元素类型
		 String elementCode = "";//元素编码
		 String packageCode = "";//包编码
		 String packageName = "";//包名称
		 Elements elementElements = detaildoc.getElementsContainingOwnText("业务：").parents().select("dd");
		 if(elementElements!=null && elementElements.size()>0){
			 elementName = elementElements.get(0).text().trim();
			 elementType = "D";//S 服务 D 资费  A 礼品  K 礼品包 X 合作伙伴产品 
			 if(CrawlerUtils.isDigit(elementName)){//如果获取的业务是数字则为元素编码反之为元素名称
				 elementCode = elementName;
				 elementName = "";
			 }
		 }
		 List<Map<String, String>> activityPackageInfoMap = new ArrayList<Map<String, String>>();
		 Map<String, String> activityPackageMap = new HashMap<String, String>();
		 activityPackageMap.put("ElementCode", elementCode);
		 activityPackageMap.put("ElementName", elementName);
		 activityPackageMap.put("ElementType", elementType);
		 activityPackageMap.put("PackageCode", packageCode);
		 activityPackageMap.put("PackageName", packageName);
		 activityPackageInfoMap.add(activityPackageMap);
		 //activityMap.put("Package", activityPackageInfoMap);目前暂不添加可选包
		 
		 activityInfoMapList.add(activityInfoMap);
		 goodsAttInfoMap.put("ActivityInfo",activityInfoMapList);
	}
	/**
	 * 爬虫获取终端信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getResourcesInfo(Document detaildoc,Map<String, Object> goodsInfoMap,Map<String, Object> goodsAttInfoMap, Map<String, Object> resourcesInfoMap){
		 String resources = "";
		 String resourcesBrand = "";//终端品牌编码(必填)
		 String resourcesModel = "";//终端型号编码(必填)
		 String resourcesColor = "";//终端颜色编码(必填)
		 String resourcesTypeId = "";//终端机型编码(必填)
		 String resourcesCode = "";//终端串号
		 String resourcesFittings = "";//终端随机配件
		 Elements resourcesElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(手机：)").parents().select("dd");
		 if(resourcesElements!=null && resourcesElements.size()>0){
			 resources = resourcesElements.get(0).text().trim();
			 if(StringUtils.isNotBlank(resources) && resources.indexOf(" ")!=-1){
				 String[] resourcesStr = resources.split(" ");
				 if(resourcesStr!=null && resourcesStr.length==2){//一个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1];
				 }else if (resourcesStr!=null && resourcesStr.length==3){//两个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1];
					 resourcesColor = resourcesStr[2];
				 }else if (resourcesStr!=null && resourcesStr.length==4){//三个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1]+" "+resourcesStr[2];
					 resourcesColor = resourcesStr[3];
				 }else if (resourcesStr!=null && resourcesStr.length==5){//四个空格
					 resourcesBrand = resourcesStr[0];
					 resourcesModel = resourcesStr[1]+" "+resourcesStr[2]+" "+resourcesStr[3];
					 resourcesColor = resourcesStr[4];
				 }else{
					 
				 }
			 }
		 }
		 if(!StringUtils.isEmpty(resourcesBrand)){
			 resourcesInfoMap.put("ResourcesBrand", resourcesBrand);
		 }
		 if(!StringUtils.isEmpty(resourcesModel)){
			 resourcesInfoMap.put("ResourcesModel", resourcesModel);
		 }
		 if(!StringUtils.isEmpty(resourcesColor)){
			 resourcesInfoMap.put("ResourcesColor", resourcesColor);
		 }
		 resourcesInfoMap.put("ResourcesCode", resourcesCode);
		 resourcesInfoMap.put("ResourcesFittings", resourcesFittings);

		 goodsAttInfoMap.put("ResourcesInfo", resourcesInfoMap);
	}
	/**
	 * 爬虫获取手机号码信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 * @param orderMap
	 */
	private static void getPhoneInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap,Map<String, Object> orderMap,Map<String, Object> commonMap){
		 String numNet = commonMap.get("numNet").toString();//号码网别
		 String occupiedTime = "";//号码状态标识时间
		 String operatorState = "2";//操作状态
		 String proKey = orderMap.get("CustomerId").toString();//资源预占关键字(发现报文里面很多都是用了客户ID)
		 String proKeyMode = "1";//号码资源预占关键字类型 0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号

		 String phoneNum = "";//号码
		 Elements phoneNumElements = detaildoc.getElementsContainingOwnText("号码：").parents().select("dd");
		 if(phoneNumElements!=null && phoneNumElements.size()>0){
			 phoneNum = phoneNumElements.get(0).text().trim();//产品名称
			 phoneNum = phoneNum.substring(0,11);
		 }
		 
		 //NiceInfo(靓号信息)
		 String advancePay = "0";//预存话费金额
		 String cancelTagChe = "0";//考核期是否销户 0:允许 1：不允许
		 String cancelTagPro = "0";//协议期是否销户  0:允许 1：不允许
		 String changeTagChe = "0";//考核期是否过户  0:允许 1：不允许
		 String changeTagPro = "0";//协议期是否过户  0:允许 1：不允许
		 String classId = "9";//号码等级 1：一级靓号 2：二级靓号 3：三级靓号 4：四级靓号 5：五级靓号 6：六级靓号 9：普通号码
		 Map<String, Object> niceInfoMap = new HashMap<String, Object>();
		 niceInfoMap.put("AdvancePay", advancePay);
		 niceInfoMap.put("CancelTagChe", Integer.valueOf(cancelTagChe));
		 niceInfoMap.put("CancelTagPro", Integer.valueOf(cancelTagPro));
		 niceInfoMap.put("ChangeTagChe", Integer.valueOf(changeTagChe));
		 niceInfoMap.put("ChangeTagPro", Integer.valueOf(changeTagPro));
		 niceInfoMap.put("ClassId", Integer.valueOf(classId));
		 
		 Map<String, Object> phoneInfoMap = new HashMap<String, Object>();
		 phoneInfoMap.put("NumNet", "未知");//生产报文是“未知”，默认“未知”
		 phoneInfoMap.put("OccupiedTime", occupiedTime);
		 phoneInfoMap.put("OperatorState", operatorState);
		 phoneInfoMap.put("PhoneNum", phoneNum);
		 phoneInfoMap.put("ProKey", proKey);
		 phoneInfoMap.put("ProKeyMode", proKeyMode);
		 phoneInfoMap.put("NiceInfo", niceInfoMap);//爬虫抓取不到靓号信息，暂时取默认
		 
		 goodsAttInfoMap.put("PhoneInfo", phoneInfoMap);
	}
	/**
	 * 爬虫获取可选包信息
	 * @param detaildoc
	 * @param goodsAttInfoMap
	 */
	private static void getMallPackageInfo(Document detaildoc,Map<String, Object> goodsAttInfoMap){
		 //Package 可选包信息
		 String melementCode = "";//元素编码
		 String melementName = "";//元素名称 
		 String melementType = "";//元素类型
		 String packageCode = "";//可选包编码
		 String packageName = "";//可选包名称
		 
		 List<Map<String, String>> mallPackageListMap = new ArrayList<Map<String, String>>();
		
		 Elements packageNameElements = detaildoc.getElementsContainingOwnText("包名称：").parents().select("dd");
		 if(packageNameElements!=null && packageNameElements.size()>0){
			 Map<String, String> mallPackagesMap = new HashMap<String, String>();
			 packageName = packageNameElements.get(0).text().trim();
			 mallPackagesMap.put("ElementCode", melementCode);
			 mallPackagesMap.put("ElementName", melementName);
			 mallPackagesMap.put("ElementType", melementType);
			 mallPackagesMap.put("PackageCode", packageCode);
			 mallPackagesMap.put("PackageName", packageName);
			 
			 mallPackageListMap.add(mallPackagesMap);
		 }else{
			 Elements mallPackageElements = detaildoc.select("li[class=commodityComposition]").select("dl");
			 //Elements mallPackageElements = detaildoc.select("li[class=commodityComposition]").select(":containsOwn(流量包)");
			 String packageNameAll = "流量包,语音包,短/彩信,增值包,来显包,可选包";//4G组合套餐
			 if(mallPackageElements!=null && mallPackageElements.size()>0){
				 for(Element pelement:mallPackageElements){
					 Elements pelements = pelement.select("dt");
					 if(pelements!=null && pelements.size()>0){
						 packageName = pelements.text().trim();
						 packageName = packageName.replace("：", "");
						 if(StringUtils.isNotBlank(packageName) && packageNameAll.indexOf(packageName)!=-1){
							 Elements eelements = pelement.select("dd");
							 if(eelements!=null && eelements.size()>0){
								 melementName = eelements.text().trim();
								 if(StringUtils.isNotBlank(melementName) && !"无".equals(melementName)){
									 Map<String, String> mallPackagesMap = new HashMap<String, String>();
									 mallPackagesMap.put("ElementCode", melementCode);
									 mallPackagesMap.put("ElementName", melementName);
									 mallPackagesMap.put("ElementType", melementType);
									 mallPackagesMap.put("PackageCode", packageCode);
									 mallPackagesMap.put("PackageName", packageName);
									 
									 mallPackageListMap.add(mallPackagesMap);
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 goodsAttInfoMap.put("Package", mallPackageListMap);
	}
	
	/**
	 * 指定单号查询分配状态
	 * @param client 
	 * @param orderNo 总商订单编号
	 */
	public static int queryOrderAllocationStatusByOrderNo(ZBSystemClient client,String orderNo){
		int status = 1;//1不可分配，0可分配，2订单不存在
		try {
        	//订单分配页面查询按钮链接
        	//String oldUrl = ZBOrderUrlConsts.QUERY_ALLOCATION_ZB_ORDER_URL;
    		String oldUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.QUERY_ALLOCATION_ZB_ORDER_URL);
    		
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("selProvinceCode",LocalCrawlerUtil.ZB_PROVINCE_CODE));
    		formparams.add(new BasicNameValuePair("pageSize", "1"));
    		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "1"));
    		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
    		formparams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
    		formparams.add(new BasicNameValuePair("orderNo", orderNo));
    		if(client!=null){
    			String oldOrder = client.post(formparams, oldUrl);//查询分配信息
    			log.info(orderNo+"[订单分配查询分配状态返回报文：]"+oldOrder);
	        	if(StringUtils.isNotBlank(oldOrder)){//查到订单信息再做分配操作
	    			Document doc =  Jsoup.parse(oldOrder);
	    			if(doc!=null){
	    				Elements elements = doc.select("table[class=singleOrder]");
	    				if(elements!=null && elements.size()>0){
	    					for(Element element:elements){
	    						 String orderId = "";//订单ID(长订单ID) 
	    						 Elements classTypeElements = element.select("a[href].shortBlueBtn");
	    						 
	    						 Element checkElement = element.getElementById("checkbox");
    							 if(checkElement!=null){
    								 orderId = checkElement.val();
    							 }
	    						 if(classTypeElements!=null && classTypeElements.size()>0 && StringUtils.isNotBlank(orderId)){//分配按钮为蓝色可以点击
	    							status = 0;
	    							log.info("=====["+orderNo+"-订单分配]订单可分配");
	    						 }else{//分配按钮为灰色不可以点击
	    							 log.info("=====["+orderNo+"-订单分配]订单不可分配");
	    						 }
	    					}
	    				}else{
	    					status = 2;
	    					log.info("=====["+orderNo+"-订单分配]没有查到订单信息");
	    				}
	    			}
            	}else{
					log.info("=====["+orderNo+"-订单分配]没有查到订单信息");
            	}
    		}else{
    			log.info("=====["+orderNo+"-订单分配]自动登录客户端对象为空，请检查自动登录的cookie是否已失效!");
    		}
		} catch (Exception e) {
			status = 2;
			e.printStackTrace();
			log.info("[总部系统-查询分配状态]任务出错", e);
			log.info("=====["+orderNo+"-订单分配]查询订单分配状态异常");
		}
		return status;
	}
	
	/**
	 * 爬虫抓取订单审核页面新用户信息标准化
	 * @param client
	 * @param threadName 线程名称
	 * @throws Exception
	 */
	public static void parseOrderVerifyOrderCtn(ZBSystemClient client,String threadName) throws Exception{
		if (client != null) {
        	String ordersNoStr = "";//通过API获取订单编号的字符串
        	List<String> ordersList = new ArrayList<String>();
        	ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        	if("Y".equals(CrawlerSetting.IS_OUT_ID_CHECK)){
        		ZbAuditOrderQueryReq req = new ZbAuditOrderQueryReq();
            	ZbAuditOrderQueryResp resp = zteclient.execute(req, ZbAuditOrderQueryResp.class);
            	if(resp!=null && !"-1".equals(resp.getError_code()) && resp.getOrders()!=null && resp.getOrders().size()>0){
            		for(int j=0;j<resp.getOrders().size();j++){
            			JSONObject json = JSONObject.fromObject(resp.getOrders().get(j).toString());//读取返回内容
            			String orderNo = json.getString("out_tid");
            			ordersNoStr = ordersNoStr + orderNo+",";
            			ordersList.add(orderNo);
            		}
            	}
        		log.info("=====[新用户标准化]获取导入的订单编码："+ordersNoStr);
        	}
        	//订单审核页面点击查询按钮链接
        	Date d=new Date();   
        	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        	String beginDate = df.format(new Date(d.getTime() - (long)7 * 24 * 60 * 60 * 1000));
        	String endDate = df.format(d);
	        	
			//String newUrl = ZBOrderUrlConsts.BATCH_QUERY_ZB_ORDER_URL;
    		String newUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_QUERY_ZB_ORDER_URL);
    		
    		if("Y".equals(CrawlerSetting.IS_OUT_ID_CHECK)){
    			for (String noStr : ordersList) {
    	        	String json = "{\"currPage\":1,\"pageSize\":\"100\",\"template\":\"order/artificialverify/list/artificialverifytable\",\"orderFrom\":\"\",\"orderType\":\"\",\"userTag\":\"1\",\"payType\":\"\",\"appArea\":\"\",\"netType\":\"\",\"businessType\":\"\",\"turnoverTimeTab\":\"\",\"startTime\":\""+beginDate+"\",\"endTime\":\""+endDate+"\",\"orderNo\":\""+noStr+"\",\"custName\":\"\",\"psptNo\":\"\",\"mobilePhone\":\"\"}";
    	        	
    	        	String newOrder = client.jsonPost(json, newUrl);
    	        	log.info("======[线程："+threadName+"-订单审核查询返回报文开始]");
    	        	log.info(newOrder);
    	        	log.info("======[线程："+threadName+"-订单审核查询返回报文开始]");
    	        	if(StringUtils.isNotBlank(newOrder)){
    	        		parseOldNewOrderCtn(client,null,newOrder,ordersNoStr,zteclient,threadName);//解析新户订单编号和订单ID执行审核操作
    	        	}else{
    	        		log.info("=====爬虫订单审核页面的订单信息返回为空");
    	        	}
				}
    		}else{
	        	//String json = "{\"currPage\":1,\"pageSize\":\"100\",\"template\":\"order/artificialverify/list/artificialverifytable\",\"orderFrom\":\"\",\"orderType\":\"\",\"userTag\":\"1\",\"payType\":\"\",\"appArea\":\"\",\"netType\":\"\",\"businessType\":\"\",\"turnoverTimeTab\":\"\",\"startTime\":\""+beginDate+"\",\"endTime\":\""+endDate+"\",\"orderNo\":\""+noStr+"\",\"custName\":\"\",\"psptNo\":\"\",\"mobilePhone\":\"\"}";

    			Map<String, String> params = CrawlerSetting.ORDER_REVIEW_MAP.get(threadName);
    			String pageSize = params.get("pageSize");
    			if(StringUtils.isEmpty(pageSize) || !CrawlerSetting.isNumeric(pageSize)){
    				pageSize = "10";
    			}
    			String orderType = params.get("orderType");
    			if(StringUtils.isEmpty(orderType)){
    				orderType = "";
    			}
    			String userTag = params.get("userTag");
    			if(StringUtils.isEmpty(userTag)){
    				userTag = "";
    			}
    			String businessType = params.get("businessType");
    			if(StringUtils.isEmpty(businessType)){
    				businessType = "";
    			}
    			String psptNo = params.get("psptNo");
    			if(StringUtils.isEmpty(psptNo)){
    				psptNo = "";
    			}
    			String mobilePhone = params.get("mobilePhone");
    			if(StringUtils.isEmpty(mobilePhone)){
    				mobilePhone = "";
    			}
    			String orderFrom = params.get("orderFrom");
    			if(StringUtils.isEmpty(orderFrom)){
    				orderFrom = "";
    			}
    			String payType = params.get("payType");
    			if(StringUtil.isEmpty(payType)){
    				payType = "";
    			}
    			String appArea = params.get("appArea");
    			if(StringUtil.isEmpty("appArea")){
    				appArea = "";
    			}
    			String netType = params.get("netType");
    			if(StringUtil.isEmpty(netType)){
    				netType = "";
    			}
    			String turnoverTimeTab = params.get("turnoverTimeTab");
    			if(StringUtil.isEmpty(turnoverTimeTab)){
    				turnoverTimeTab = "";
    			}
    			String orderNo = params.get("orderNo");
    			if(StringUtil.isEmpty(orderNo)){
    				orderNo = "";
    			}
    			String custName = params.get("custName");
    			if(StringUtil.isEmpty(custName)){
    				custName = "";
    			}
    			/*JSONObject jsonObj = new JSONObject();
	        	jsonObj.put("currPage", 1);
	        	jsonObj.put("pageSize", pageSize);
	        	jsonObj.put("template", "order/artificialverify/list/artificialverifytable");
	        	jsonObj.put("orderFrom", orderFrom);
	        	jsonObj.put("orderType", orderType);
	        	jsonObj.put("userTag", userTag);
	        	jsonObj.put("payType", payType);
	        	jsonObj.put("appArea", appArea);
	        	jsonObj.put("netType", netType);
	        	jsonObj.put("businessType", businessType);
	        	jsonObj.put("turnoverTimeTab", turnoverTimeTab);
	        	jsonObj.put("startTime", beginDate);
	        	jsonObj.put("endTime", endDate);
	        	jsonObj.put("orderNo", orderNo);
	        	jsonObj.put("custName", custName);
	        	jsonObj.put("psptNo", psptNo);
	        	jsonObj.put("mobilePhone", mobilePhone);
	        	
	        	String newOrder = client.jsonPost(jsonObj.toString(), newUrl);*/
    			//==========================================查询我的订单开始================================
        		String newOrderUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_QUERY_ZB_MY_ORDER_URL);
        		
    	    	//String json = "{\"currPage\":1,\"pageSize\":\"100\",\"template\":\"order/artificialverify/list/artificialverifytable\",\"orderFrom\":\"\",\"orderType\":\"\",\"userTag\":\"1\",\"payType\":\"\",\"appArea\":\"\",\"netType\":\"\",\"businessType\":\"\",\"turnoverTimeTab\":\"\",\"startTime\":\""+beginDate+"\",\"endTime\":\""+endDate+"\",\"orderNo\":\""+noStr+"\",\"custName\":\"\",\"psptNo\":\"\",\"mobilePhone\":\"\"}";
    	    	//订单审核页面点击查询按钮链接
    			
    	    	JSONObject jsonObj = new JSONObject();
    	    	jsonObj.put("currPage", "1");
    	    	jsonObj.put("fastOrderNo", orderNo);
    	    	jsonObj.put("pageSize", "1");
    	    	jsonObj.put("template", "order/artificialverify/list/verifymyordertable");

    	    	String newOrder = client.jsonPost(jsonObj.toString(), newOrderUrl);
    			//===========================================查询我的审核订单结束===============================
    	    	
	        	if(StringUtils.isNotBlank(newOrder)){
	        		parseOldNewOrderCtn(client,null,newOrder,ordersNoStr,zteclient,threadName);//解析新户订单编号和订单ID执行审核操作
	        	}else{
	        		log.info("======[线程："+threadName+"]爬虫订单审核页面的订单信息返回为空");
	        	}
    		}
        } else {
            log.info("======[线程："+threadName+"]当前cookie已经失效!");
        }
	}

	/**
	 * 解析订单审核页面的订单信息标准化入库
	 * @param client 登录客户端
	 * @param oldFileName 返回结果文件
	 * @param resultJson 返回结果
	 * @param ordersNoStr 匹配订单编号,例："111111111,222222222"
	 * @return 审核结果，只审核在导入订单中匹配到的订单
	 */
	public static void parseOldNewOrderCtn(ZBSystemClient client,String oldFileName,String resultJson,String ordersNoStr,ZteClient zteclient,String threadName){
		try {
			//String str = getFileToString(oldFileName); CrawlerUtils.isJson(resultJson) 是否为json格式
			if(client!=null && StringUtils.isNotBlank(resultJson) && CrawlerUtils.isJson(resultJson)){
				JSONObject json = JSONObject.fromObject(resultJson);//读取返回内容
				//获取订单列表
				JSONArray jsonArr = json.getJSONArray("pages");
				//获取订单基本信息
				for (int i=0;i<jsonArr.size();i++) {
					JSONObject orderJson = jsonArr.getJSONObject(i);
					String orderId = orderJson.getString("oId");
					String orderNo = orderJson.getString("orderNo");
					
					//做解析之前先拿到总商订单号做重复单校验
					try{
						String zbOrderId = (String)cache.get(NAMESPACE, HB_ZB_ORDER_ID + orderNo);
						if(StringUtil.isEmpty(zbOrderId))
							continue;//订单存在，不做处理
					}catch(Exception e){
						e.printStackTrace();
					}
					
					 String activeNo = SpecUtils.getActiveNo(true);//订单流水号
					 String orderAccCode = EcsOrderConsts.CRAWLER_TYPE_MALL;//订单接入编码(默认爬虫来源)
					 String orderAccType = EcsOrderConsts.CRAWLER_TYPE_MALL;//订单接入类型(默认爬虫来源)
					 
					 String orderSource = "EMALL";//订单来源 默认为网上商城
					 String orderSourceStr = orderJson.getString("orderFrom");//订单来源字符串  这个有些特别
					 if(StringUtils.isNotBlank(orderSourceStr)){
						 orderSource = LocalCrawlerUtil.ORDERSRC.get(orderSourceStr);
						 if(orderSource==null || "null".equals(orderSource) || StringUtils.isBlank(orderSource)){
							 orderSource = "MOBILE";
						 }
					 }
					 String numNet = "4G";//号码网别
					 String userType = "NEW";//入网类型 NEW 新用户，OLD 老用户
					 String userTypeStr = orderJson.getString("userTag");//入网类型字符串
					
					 if(StringUtils.isNotBlank(userTypeStr)){
						 userType = LocalCrawlerUtil.USER_TYPE.get(userTypeStr);
					 }
					 //String phoneNum = "";//订购号码
					 String orderCity = "";//订单地市
					 String orderCityStr = orderJson.getString("cityName");//订单地市字符串
					 
					 if(StringUtils.isNotBlank(orderCityStr)){
						 orderCity = LocalCrawlerUtil.AREAIDBACK.get(orderCityStr);//订单地市
					 }
					 String orderOperType = "01";//是否闪电送 01 非闪电送 02 闪电送
					 String orderProvince = orderJson.getString("provinceCode");//订单省份
					 String preSale = "1";//预售标识
					 log.info("================解析文件获取的订单编号："+orderNo+"\t"+orderId+"\t"+orderSourceStr+"\t"+numNet+"\t"+orderAccType+"\t"+orderCity+"\t"+userType);
					 Map<String, Object> orderMap = new HashMap<String, Object>();
					 orderMap.put("ActiveNo", activeNo);//流水号
					 orderMap.put("ClientIP", "");//卖家ip --留空
					 orderMap.put("CustomerId", "9015091867693272");//卖家标识 --留空
					 orderMap.put("OrderSource", orderSource);//订单来源 --默认
					 orderMap.put("RegisterName", "");//卖家昵称 --留空
					 orderMap.put("OrderAccCode", orderAccCode);//订单接入类型 --默认
					 orderMap.put("OrderAccType", orderAccType);//订单接入类型 --默认
					 orderMap.put("OrderProvince", orderProvince);//订单省份 --默认
					 orderMap.put("OrderCity", orderCity);//订单地市 --从详情取
					 orderMap.put("OrderId", orderNo);//订单id --列表取
					 orderMap.put("OrderOperType",orderOperType);//是否闪电送
					 orderMap.put("PreSale",preSale);//预售标识 --定值
					 orderMap.put("OrderNum", orderId);//总商长订单号 --订单查询取
					 //以下参数是做传递数据
					 Map<String, Object> commonMap = new HashMap<String, Object>();
					 commonMap.put("numNet", numNet);
					 commonMap.put("userType", userType);
					 //commonMap.put("phoneNum", phoneNum);
					 
					 String stdResult = parseDetailFileOrderCtn(client,orderMap,commonMap,oldFileName,threadName);
					 log.info("======[线程："+threadName+"]新用户标准化入库返回结果："+stdResult);
				}
			}else{
				log.info("======[线程："+threadName+"]解析订单审核返回的内容为空或不为json格式");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("======[线程："+threadName+"]解析订单审核文件异常，返回报文："+resultJson);
		}
	}
	
	/**
	 * 解析订单分配页面老用户信息标准化入库
	 * @param client
	 * @param oldFileName
	 */
	public static void parseOldOrderCtn(ZBSystemClient client,String oldFileName,String ordersNoStr,String threadName){
		try {
			Document doc =  Jsoup.parse(oldFileName);//getFile("allocation","",oldFileName);
			//&nbsp;空格特殊处理
			String spaceStr = Jsoup.parse("&nbsp;").text();
			
			if(doc!=null){
				
				Elements elements = doc.select("table[class=singleOrder]");
				if(elements!=null && elements.size()>0){
					//int count = CrawlerSetting.ORDER_ALLOCATION_COUNT;//用于防止批量分配实际分配数量大于可分配数量问题
					for(Element element:elements){
						 String orderNo = "";//订单编号(短订单ID)
						 String orderId = "";//订单ID(长订单ID)
						 String orderAccTypeStr ="";//接入类型字符串
						 String custId = "9015091867693272";//买家标识 
						 String custIp = "";//客户IP
						 String cityCode = "";
						 String provinceCode = "";

							 String activeNo = SpecUtils.getActiveNo(true);//订单流水号
							 Elements orderNoElements = element.select("p:contains(订单编号：)");
							 if(orderNoElements!=null && orderNoElements.size()>0){
								 orderNo = orderNoElements.text().trim();
								 if(StringUtils.isNotBlank(orderNo) && orderNo.indexOf("：")!=-1){
									 orderNo = orderNo.substring(orderNo.indexOf("：")+1, orderNo.length());
								 }
							 }
							 Elements orderAccTypeElements = element.select("p:contains(推广渠道：)");
							 if(orderAccTypeElements!=null && orderAccTypeElements.size()>0){
								 orderAccTypeStr = orderAccTypeElements.text().trim();
								 if(StringUtils.isNotBlank(orderAccTypeStr) && orderAccTypeStr.indexOf("：")!=-1){
									 orderAccTypeStr = orderAccTypeStr.substring(orderAccTypeStr.indexOf("：")+1, orderAccTypeStr.length());
								 }
							 }
							 String orderAccCode = EcsOrderConsts.CRAWLER_TYPE_MALL;//订单接入编码(默认爬虫来源)
							 String orderAccType = EcsOrderConsts.CRAWLER_TYPE_MALL;//订单接入类型(默认爬虫来源)

							 Element checkElement = element.getElementById("checkbox");
							 if(checkElement!=null){
								 orderId = checkElement.val();
							 }
							 
							 String orderSource = "EMALL";//订单来源 默认为网上商城
							 String orderSourceStr = "";//订单来源字符串
							 Elements sourceElements = element.select("dd[class=nowrap]").select("label");//这个有些特别
							 if(sourceElements!=null && sourceElements.size()>0){
								 orderSourceStr = sourceElements.text().trim();
							 }
							 if(StringUtils.isNotBlank(orderSourceStr)){
								 orderSource = LocalCrawlerUtil.ORDERSRC.get(orderSourceStr);
								 if(orderSource==null || "null".equals(orderSource) || StringUtils.isBlank(orderSource)){
									 orderSource = "MOBILE";
								 }
							 }
							 String numNet = "4G";//号码网别
							 Elements numNetElements = element.select("td[class=third]").select("p");
							 if(numNetElements!=null && numNetElements.size()>0){
								 String numNetStr = numNetElements.get(1).text().trim();
								 if(numNetStr.indexOf("号码")!=-1){
									 numNet = numNetStr.substring(0, numNetStr.indexOf("号码"));
								 }
							 }
							 String clientIp = "127.0.0.1";//买家登录IP
							 String registerName = "";//买家昵称
							 Elements clientIpElements = element.select("td[class=fourth]").select("p");
							 if(clientIpElements!=null && clientIpElements.size()>0){
								 String registerNameStr = clientIpElements.get(0).text().replace(spaceStr, "").trim();
								 registerName = registerNameStr.substring(0, registerNameStr.indexOf("(")).trim();
								 String clientIpStr = clientIpElements.get(1).text().trim();
								 clientIp = clientIpStr.substring(0, clientIpStr.indexOf("(")).trim();
							 }
							 String userType = "NEW";//入网类型 NEW 新用户，OLD 老用户
							 String userTypeStr = "";//入网类型字符串
							 Elements userTypeElements = element.getElementsByClass("iconOlduser");		 
							 if(userTypeElements!=null && userTypeElements.size()>0){
								 userTypeStr = userTypeElements.text().trim();
							 }
							 if(StringUtils.isNotBlank(userTypeStr)){
								 userType = LocalCrawlerUtil.USER_TYPE.get(userTypeStr);
							 }
							 String phoneNum = "";//订购号码
							 String orderCity = "";//订单地市
							 String orderCityStr = "";//订单地市字符串
							 Elements numListWElements = element.getElementsByClass("numListW").select("li");
							 if(numListWElements!=null && numListWElements.size()>0){
								 String phoneNumStr = numListWElements.text().trim();
								 if(StringUtils.isNotBlank(phoneNumStr) && phoneNumStr.indexOf("(")!=-1 && phoneNumStr.indexOf(")")!=-1){
									 phoneNum = phoneNumStr.substring(0, phoneNumStr.indexOf("(")).trim();//订购号码
									 orderCityStr = phoneNumStr.substring(phoneNumStr.indexOf("(")+1,phoneNumStr.indexOf(")")).trim();
								 }else{
									 phoneNum = phoneNumStr;
								 }
							 }
							 if(StringUtils.isNotBlank(orderCityStr)){
								 orderCity = LocalCrawlerUtil.AREAIDBACK.get(orderCityStr);//订单地市
							 }
							 String orderOperType = "01";//是否闪电送 01 非闪电送 02 闪电送
							 String orderProvince = LocalCrawlerUtil.ZB_PROVINCE_CODE;//订单省份
							 String preSale = "1";//预售标识
							 log.info("======[线程："+threadName+"]解析文件获取的订单编号："+orderNo+"\t"+orderId+"\t"+orderSourceStr+"\t"+numNet+"\t"+clientIp+"\t"+orderAccType+phoneNum+"\t"+orderCity+"\t"+userType+"\t"+registerName);
							 Map<String, Object> orderMap = new HashMap<String, Object>();
							 orderMap.put("ActiveNo", activeNo);//流水号
							 orderMap.put("ClientIP", clientIp);//卖家ip --订单分配补录
							 orderMap.put("CustomerId", custId);//卖家标识 --订单分配补录
							 orderMap.put("OrderSource", orderSource);//订单来源 --默认
							 orderMap.put("RegisterName", registerName);//卖家昵称 --订单分配补录
							 orderMap.put("OrderAccCode", orderAccCode);//订单接入类型 --默认
							 orderMap.put("OrderAccType", orderAccType);//订单接入类型 --默认
							 orderMap.put("OrderProvince", orderProvince);//订单省份 --默认
							 orderMap.put("OrderCity", orderCity);//订单地市 --从详情取
							 orderMap.put("OrderId", orderNo);//订单id --列表取
							 orderMap.put("OrderOperType",orderOperType);//是否闪电送
							 orderMap.put("PreSale",preSale);//预售标识 --定值
							 orderMap.put("OrderNum", orderId);//总商长订单号 --订单查询取
							 //以下参数是做传递数据
							 Map<String, Object> commonMap = new HashMap<String, Object>();
							 commonMap.put("numNet", numNet);
							 commonMap.put("userType", userType);
							 commonMap.put("phoneNum", phoneNum);
							 
							 if(StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(orderId)){

								 String stdResult = parseDetailFileOrderCtn(client,orderMap,commonMap,oldFileName,threadName);

								 log.info("======[线程："+threadName+"]老用户标准化入库返回结果："+stdResult);
								
							 }else{
								 log.info("======[线程："+threadName+"]抓取到的订单信息订单号为空");
							 }
					}
					
				}else{
					log.info("======[线程："+threadName+"]没有抓取到订单信息");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("======[线程："+threadName+"]解析订单文件出错");
		}
	}
	
	/**
	 * 抓取订单分配页面老用户的订单信息
	 * @param client
	 * @param threadName 线程名称
	 */
	public static void getOrderAllocationInfoOrderCtn(ZBSystemClient client,String threadName){
		try {
			//==========================（在订单分配页面抓取订单信息）开始===================================
        	//订单分配页面查询按钮链接
        	//String oldUrl = ZBOrderUrlConsts.QUERY_ALLOCATION_ZB_ORDER_URL;
    		String oldUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.QUERY_ALLOCATION_ZB_ORDER_URL);
    		
        	Date d=new Date();   
        	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
        	String maxTime = df.format(d);
    		
        	ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        	List<String> ordersList = new ArrayList<String>();
        	String ordersNoStr = "";//通过API获取订单编号的字符串
        	if("Y".equals(CrawlerSetting.IS_OUT_ID_CHECK)){
        		ZbAuditSuccOrderQueryReq req = new ZbAuditSuccOrderQueryReq();
            	ZbAuditSuccOrderQueryResp resp = zteclient.execute(req, ZbAuditSuccOrderQueryResp.class);
            	if(resp!=null && !"-1".equals(resp.getError_code()) && resp.getOrders()!=null && resp.getOrders().size()>0){
            		for(int j=0;j<resp.getOrders().size();j++){
            			JSONObject json = JSONObject.fromObject(resp.getOrders().get(j).toString());//读取返回内容
            			String noStr = json.getString("out_tid");
            			ordersNoStr = ordersNoStr + noStr +",";
            			ordersList.add(noStr);
            		}
            	}
        		log.info("======[线程："+threadName+"]获取导入的订单编码："+ordersNoStr);
        	}
        	
    		if("Y".equals(CrawlerSetting.IS_OUT_ID_CHECK)){
	    		for (String orderNo : ordersList) {
	    			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	    			
	    			formparams.add(new BasicNameValuePair("selProvinceCode",LocalCrawlerUtil.ZB_PROVINCE_CODE));
	        		formparams.add(new BasicNameValuePair("pageSize", "100"));
	        		formparams.add(new BasicNameValuePair("page.webPager.action", "refresh"));
	        		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "100"));
	        		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
	        		formparams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
	        		formparams.add(new BasicNameValuePair("orderNo", orderNo));
	        		formparams.add(new BasicNameValuePair("userTag", "2"));
	        		if(client!=null){
	        			String oldOrder = client.post(formparams, oldUrl);//爬虫老用户订单信息
	        			
	                	if(StringUtils.isNotBlank(oldOrder)){//爬虫到的内容不为空把内容生成到文件里面
	                		parseOldOrderCtn(client, oldOrder, ordersNoStr,threadName);//解析老用户订单标准化入库
	                	}else{
	                		log.info("======[线程："+threadName+"]在订单分配页面没有爬虫到订单信息");
	                	}
	        		}else{
	        			log.info("======[线程："+threadName+"]在订单分配抓取订单时自动登录客户端对象为空，请检查自动登录的cookie是否已失效!");
	        		}
				}
    		}else{
    			List<NameValuePair> formparams = new ArrayList<NameValuePair>();

    			formparams.add(new BasicNameValuePair("selProvinceCode",LocalCrawlerUtil.ZB_PROVINCE_CODE));
    			formparams.add(new BasicNameValuePair("page.webPager.action", "refresh"));
        		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
        		formparams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
        		formparams.add(new BasicNameValuePair("userTag", "2"));
        		
        		//String threadName = Thread.currentThread().getName();
        		Map<String, String> params = CrawlerSetting.ORDER_ALLOCATION_MAP.get(threadName);
        		Set<String> keys = params.keySet();
        		for (String str : keys) {
					if(!StringUtil.isEmpty(params.get(str))){
		        		formparams.add(new BasicNameValuePair(str, params.get(str)));
					}
				}
        		
        		if(params.containsKey("pageSize") == false){
        			formparams.add(new BasicNameValuePair("pageSize", "10"));
        			formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "10"));
        		}else{
        			formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", params.get("pageSize")));
        		}
        		if(params.containsKey("maxTime") == false){
        			formparams.add(new BasicNameValuePair("maxTime", maxTime));
        		}
        		
        		if(client!=null){
        			String oldOrder = client.post(formparams, oldUrl);//爬虫老用户订单信息
					
                	if(StringUtils.isNotBlank(oldOrder)){//爬虫到的内容不为空把内容生成到文件里面
                		parseOldOrderCtn(client, oldOrder, ordersNoStr,threadName);//解析老用户订单文件获取订单编号和订单ID
                	}else{
                		log.info("======[线程："+threadName+"]在订单分配页面没有爬虫到订单信息");
                	}
        		}else{
        			log.info("======[线程："+threadName+"]在订单分配抓取订单时自动登录客户端对象为空，请检查自动登录的cookie是否已失效!");
        		}
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[总部系统-抓取订单分配页面订单信息]任务出错", e);
		}
	}

	
	/**
	 * 修改总商商品清单信息
	 * @param client
	 * @param req
	 * @return
	 */
	public static ZteResponse updateGoodsInfo(ZBSystemClient client, CrawlerUpdateGoodsInfoReq req){
		ZteResponse resp = new ZteResponse();
		String responseStr = "";
		try {
			//String newUrl = ZBOrderUrlConsts.BATCH_QUERY_ZB_MY_ORDER_URL;
    		String newUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_QUERY_ZB_MY_ORDER_URL);
    		
	    	//String json = "{\"currPage\":1,\"pageSize\":\"100\",\"template\":\"order/artificialverify/list/artificialverifytable\",\"orderFrom\":\"\",\"orderType\":\"\",\"userTag\":\"1\",\"payType\":\"\",\"appArea\":\"\",\"netType\":\"\",\"businessType\":\"\",\"turnoverTimeTab\":\"\",\"startTime\":\""+beginDate+"\",\"endTime\":\""+endDate+"\",\"orderNo\":\""+noStr+"\",\"custName\":\"\",\"psptNo\":\"\",\"mobilePhone\":\"\"}";
	    	//订单审核页面点击查询按钮链接
			
	    	JSONObject jsonObj = new JSONObject();
	    	jsonObj.put("currPage", "1");
	    	jsonObj.put("fastOrderNo", req.getOrderNo());
	    	jsonObj.put("pageSize", "1");
	    	jsonObj.put("template", "order/artificialverify/list/verifymyordertable");

	    	String newOrder = client.jsonPost(jsonObj.toString(), newUrl);
			
	    	if(client!=null && StringUtils.isNotBlank(newOrder) && CrawlerUtils.isJson(newOrder)){
				JSONObject json = JSONObject.fromObject(newOrder);//读取返回内容
				//获取订单列表
				JSONArray jsonArr = json.getJSONArray("pages");
				if(jsonArr==null||jsonArr.size()<=0){
					resp.setError_code("-1");
					resp.setError_msg("总商系统订单审核环节未查询到订单");
					return resp;
				}
				//获取订单基本信息
				for (int i=0;i<jsonArr.size();i++) {
					JSONObject orderJson = jsonArr.getJSONObject(i);
					String orderId = orderJson.getString("oId");
					String orderNo = orderJson.getString("orderNo");
					
					
					//审单操作按钮链接
					//String reviewBtnUrl = ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL+orderId+"?orderId="+orderId;
		    		String reviewBtnUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL)+orderId+"?orderId="+orderId;
		    		
 					String reviewBtnResult = client.post(new ArrayList<NameValuePair>(),reviewBtnUrl);
					Map<String,String> paramMap = new HashMap<String, String>();
					paramMap.put("deliverTypeCode", "");
					paramMap.put("templateId", ""); 
					paramMap.put("isGroupCust", "");
					paramMap.put("goodsInsId", "");
					paramMap.put("numcardTypes", "");
					paramMap.put("wholeCountryFlowPackage", "");
					if(StringUtil.isEmpty(reviewBtnResult) || reviewBtnResult.indexOf("value=\"审核通过\"") == -1){
						log.info("=====["+orderId+"]审核锁定失败");
					}else{
						getJsoupScriptContent(reviewBtnResult, paramMap);//通过Jsoup获取html中的js变量值
						
						Document doc = Jsoup.parse(reviewBtnResult);
						
						String wholeCountryFlowPackageCode = null;
						String wholeCountryFlowPackageText = null;
						Element wholeCountryFlowPackageElements = doc.getElementById("selectFlowPackageCharge");//流量包信息
						if(wholeCountryFlowPackageElements!=null){
							Elements options = wholeCountryFlowPackageElements.getElementsByTag("option");
							for(int index=0;index<options.size();index++){
								String attr = options.get(index).attr("selected");
								if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
									wholeCountryFlowPackageCode = options.get(index).attr("value");
									wholeCountryFlowPackageText = options.get(index).text();
								}
							}
						 }
						
						JSONArray opPkgs = null;
						JSONArray newopPkgs = null;
						Elements selectOpPkg = doc.select(".pkg-content a[class*='selectCurr']");
						 if(selectOpPkg!=null && selectOpPkg.size()>0){
							 opPkgs = new JSONArray();
							 newopPkgs = new JSONArray();
							 for (Element element : selectOpPkg) {
								 JSONObject opPkg = new JSONObject();
								 opPkg.put("orderId", orderId);
								 opPkg.put("goodsInstId", paramMap.get("goodsInstId"));
								 opPkg.put("productId", element.attr("productId"));
								 opPkg.put("productName", element.attr("productName"));
								 opPkg.put("packageId", element.attr("packageId"));
								 opPkg.put("packageName", element.attr("packageName"));
								 opPkg.put("emId", element.attr("emId"));
								 opPkg.put("emType", element.attr("emType"));
								 opPkg.put("emName", element.attr("emName"));
								 opPkg.put("packageType", element.attr("packageType"));
								 opPkgs.add(opPkg);
								 
								 JSONObject newopPkg = new JSONObject();
								 newopPkg.put("PACKAGE_ID", element.attr("packageId"));
								 newopPkg.put("EM_ID", element.attr("emId"));
								 newopPkgs.add(newopPkg);
							 }
						 }
						 
						 //可选包
						 JSONObject pkgAttr = new JSONObject();
						 String newCode = null;
						 String newName = null;
						 Element pkgAttrElement =  doc.getElementById("optionalPkg");//获取下拉框值
						 if(pkgAttrElement!=null){
							Elements options = pkgAttrElement.getElementsByTag("option");
							for(int index=0;index<options.size();index++){
								String attr = options.get(index).attr("selected");
								if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
									newCode = options.get(index).attr("value");
									newName = options.get(index).text();
									if(StringUtil.isEmpty(newCode)){
										newCode = null;
										newName = null;
									}
								}
							}
						 }
						 pkgAttr.put("code", newCode);
						 pkgAttr.put("text", newName);
						 
						 //卡型
						 JSONObject cardTypes = new JSONObject();
						 String cardType = null;
						 String typeName = null;
						 Element cardTypesElement =  doc.getElementById("cardTypeEdit");//获取下拉框值
						 if(cardTypesElement !=null){
							Elements options = cardTypesElement.getElementsByTag("option");
							for(int index=0;index<options.size();index++){
								String attr = options.get(index).attr("selected");
								if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
									cardType = options.get(index).attr("value");
									typeName = options.get(index).text();
								}
							}
						 }
						 cardTypes.put("cardType", cardType);
						 cardTypes.put("typeName", typeName);
						 
						 JSONObject numcardTypesObj = new JSONObject();
						 String preNum = req.getPreNum();
						 String field = "";
						 Element fieldElement = doc.getElementById("cardType_"+preNum);
						 if(fieldElement != null){
							 field = fieldElement.text();
						 }
						 numcardTypesObj.put("preNum", preNum);
						 numcardTypesObj.put("field", field);
						 numcardTypesObj.put("code", null);
						 numcardTypesObj.put("text", null);
						 
						 //卡型
						JSONArray simCardTypes = new JSONArray();
						JSONArray numcardTypes = new JSONArray();
						numcardTypes.add(numcardTypesObj);
						 for (int j = 0; j < numcardTypes.size(); j++) {
							 JSONObject data = numcardTypes.getJSONObject(j);
							 Element newCardType =  doc.getElementById("cardTypeEdit_"+preNum);
							 if(newCardType != null){
								 Elements options = newCardType.getElementsByTag("option");
								 String val = "";
								 String text = "";
									for(int index=0;index<options.size();index++){
										String attr = options.get(index).attr("selected");
										if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
											val = options.get(index).attr("value");
											text = options.get(index).text();
										}
									}
								 
								 if(data.getString("code") != val){
									JSONObject item = new JSONObject();
									item.put("preNum", data.getString("preNum"));
									item.put("cardType", val);
									item.put("typeName", text);
								 }
							 }
						}
						if(simCardTypes.size() == 0){
							simCardTypes = null;
						}
						if(numcardTypes.size() == 0){
							numcardTypes = null;
						}
						 cardTypes.put("cardType", cardType);
						 cardTypes.put("typeName", typeName);
						 
						 //姓名
						 //String name = doc.getElementById("goodsEdit-idfy-name").val();
						 //证件类型
						 String certType = "";
						 Element certTypeElement = doc.getElementById("goodsEdit-idfy-psptype");
						 if(certTypeElement !=null){
								Elements options = certTypeElement.getElementsByTag("option");
								for(int index=0;index<options.size();index++){
									String attr = options.get(index).attr("selected");
									if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
										certType = options.get(index).attr("value");
									}
								}
							 }
						 //证件号码
						 //String certNum = doc.getElementById("goodsEdit-idfy-psptno").val();
						 //证件地址
						 String certAddr = doc.getElementById("goodsEdit-idfy-psptaddr").val();

						// String url = ZBOrderUrlConsts.ORDER_ARTIFICIAL_UPDATE_GOODS_INFO;
			    		//String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_UPDATE_GOODS_INFO) + orderId+"?orderId="+orderId;
						 String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_UPDATE_GOODS_INFO) + orderId;
				    		
			    		String goodsInsId = paramMap.get("goodsInsId");
			    		long insId = 0;
			    		if(!StringUtil.isEmpty(goodsInsId) && CrawlerSetting.isNumeric(goodsInsId)){
			    			insId = Long.valueOf(goodsInsId);
			    		}
			    		
			    		Map<String, Object> jsonParams = new HashMap<String, Object>();
			    		jsonParams.put("orderId", orderId);
			    		jsonParams.put("goodsInsId", insId);
			    		jsonParams.put("opPkg", opPkgs);
			    		jsonParams.put("optionalPkgAttr", newCode);
			    		jsonParams.put("optionalPkgAttrDesc", newName);
			    		jsonParams.put("firstMonthFee", req.getFirstMonthFee());
			    		jsonParams.put("firstMonthFeeName", req.getFirstMonthFeeName());
//			    		jsonParams.put("firstMonthFee", null);
//			    		jsonParams.put("firstMonthFeeName", null);
			    		jsonParams.put("cardType", cardType);
//			    		jsonParams.put("cardType", null);
			    		jsonParams.put("cardTypes", simCardTypes);
			    		jsonParams.put("psptNo", req.getPsptNo());
			    		jsonParams.put("psptType", certType);
			    		jsonParams.put("psptAddr", req.getCertAddr());
			    		jsonParams.put("custName", req.getCustName());
			    		jsonParams.put("templateId", paramMap.get("templateId"));
			    		jsonParams.put("isGroupCust", paramMap.get("isGroupCust"));
			    		JSONObject test = JSONObject.fromObject(jsonParams);
			    		responseStr = client.jsonPost(test.toString(), url);
						if(StringUtil.isEmpty(responseStr)){
							resp.setError_code("-1");
							resp.setError_msg("总商修改商品清单返回为空");
							log.info("=====["+req.getOrderNo()+"-订单审核-修改商品清单信息]修改返回报文为空");
							return resp;
						}
						JSONObject responseJson = JSONObject.fromObject(responseStr);
					
						if(responseJson.containsKey("isOk") == true && responseJson.getBoolean("isOk") == true){
							resp.setError_code(ConstsCore.ERROR_SUCC);
						}
						return resp;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.setError_code("-1");
			resp.setError_msg("系统异常："+e.getLocalizedMessage());
			log.info("=====["+req.getOrderNo()+"-订单审核-修改商品清单信息]修改失败，返回报文："+responseStr);
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 修改总商配送信息
	 * @param client
	 * @param req
	 * @return
	 */
	public static ZteResponse updatePostInfo(ZBSystemClient client, CrawlerUpdatePostInfoReq req){
		ZteResponse resp = new ZteResponse();
		
		//String newUrl = ZBOrderUrlConsts.BATCH_QUERY_ZB_MY_ORDER_URL;
		String newUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_QUERY_ZB_MY_ORDER_URL);
		String responseStr = "";
    	//String json = "{\"currPage\":1,\"pageSize\":\"100\",\"template\":\"order/artificialverify/list/artificialverifytable\",\"orderFrom\":\"\",\"orderType\":\"\",\"userTag\":\"1\",\"payType\":\"\",\"appArea\":\"\",\"netType\":\"\",\"businessType\":\"\",\"turnoverTimeTab\":\"\",\"startTime\":\""+beginDate+"\",\"endTime\":\""+endDate+"\",\"orderNo\":\""+noStr+"\",\"custName\":\"\",\"psptNo\":\"\",\"mobilePhone\":\"\"}";
    	//订单审核页面点击查询按钮链接
		
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("currPage", "1");
    	jsonObj.put("fastOrderNo", req.getOrderNo());
    	jsonObj.put("pageSize", "1");
    	jsonObj.put("template", "order/artificialverify/list/verifymyordertable");

    	String newOrder;
		try {
			newOrder = client.jsonPost(jsonObj.toString(), newUrl);
			if(client!=null && StringUtils.isNotBlank(newOrder) && CrawlerUtils.isJson(newOrder)){
				JSONObject json = JSONObject.fromObject(newOrder);//读取返回内容
				//获取订单列表
				JSONArray jsonArr = json.getJSONArray("pages");
				if(jsonArr==null||jsonArr.size()<=0){
					resp.setError_code("-1");
					resp.setError_msg("总商系统订单审核环节未查询到订单");
					return resp;
				}
				//获取订单基本信息
				for (int i=0;i<jsonArr.size();i++) {
					JSONObject orderJson = jsonArr.getJSONObject(i);
					String orderId = orderJson.getString("oId");
					String orderNo = orderJson.getString("orderNo");

					//审单操作按钮链接
					//String reviewBtnUrl = ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL+orderId+"?orderId="+orderId;
					String reviewBtnUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL)+orderId+"?orderId="+orderId;
					
					String reviewBtnResult = client.post(new ArrayList<NameValuePair>(),reviewBtnUrl);
					Map<String,String> paramMap = new HashMap<String, String>();
					paramMap.put("payTypeCode ", "");
					if(StringUtil.isEmpty(reviewBtnResult) || reviewBtnResult.indexOf("value=\"审核通过\"") == -1){

						log.info("=====["+req.getOrderNo()+"-订单审核-修改配送信息]进入详情失败，返回报文："+reviewBtnResult);
					}else{
						getJsoupScriptContent(reviewBtnResult, paramMap);//通过Jsoup获取html中的js变量值
					}
					
					JSONObject postEditChecks = new JSONObject();
					Document doc = Jsoup.parse(reviewBtnResult);
					String name = "";
					Element nameElement = doc.getElementById("postEdit_name");
					if(nameElement != null){
						name = nameElement.val();
					}
					String phone = "";
					Element phoneElement = doc.getElementById("postEdit_mobile");
					if(phoneElement != null){
						phone = phoneElement.val();
					}
					
					String province = "";
					Element provinceElement = doc.getElementById("postEdit_province");//select
					if(provinceElement != null){
						Elements options = provinceElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								province = options.val();
							}
						}
					}
					
					String city = "";
					Element cityElement = doc.getElementById("postEdit_city");//select
					if(cityElement != null){
						Elements options = cityElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								city = options.val();
							}
						}
					}
					String district = "";
					Element districtElement = doc.getElementById("postEdit_district");//select
					if(districtElement != null){
						Elements options = districtElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								district = options.val();
							}
						}
					}
					String addr = "";
					Element addrElement = doc.getElementById("postEdit_addr");
					if(addrElement != null) {
						addr = addrElement.val();
					}
					
					String payWay = "";
					Element payWayElement = doc.getElementById("postEdit_payWay");//select
					if(payWayElement != null) {
						Elements options = payWayElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								payWay = options.val();
							}
						}
					}
					String delvDate = "";
					Element delvDateElement = doc.getElementById("postEdit_dlvDate");//select
					if(delvDateElement != null){
						Elements options = delvDateElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								delvDate = options.val();
							}
						}
					}
					
					String wishLgts = "";
					Element wishLgtsElement = doc.getElementById("postEdit_wishLgtsCode");//select
					if(wishLgtsElement != null){
						Elements options = wishLgtsElement.getElementsByTag("option");
						for(int index=0;index<options.size();index++){
							String attr = options.get(index).attr("selected");
							if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
								wishLgts = options.val();
							}
						}
					}
					String selfFetch = "";
					Elements selfFetchElements = doc.select("chooseSelfFectchDiv[type='radio']");//radio
					if(selfFetchElements != null){
						for (Element element : selfFetchElements) {
							if(element.attr("checked") == "checked"){
								selfFetch = element.val();
							}
						}
					}
					String businessHallSelfFetch = "";
					Element businessHallSelfFetchElement = doc.getElementById("businessHallSelfFetch");
					if(businessHallSelfFetchElement != null){
						businessHallSelfFetch = businessHallSelfFetchElement.val();
					}
					String fixDate = "";
					Element fixDateElement = doc.getElementById("postEdit_dlvDateValue");
					if(fixDateElement != null){
						fixDate = fixDateElement.val();
					}
					String delvFee = "";
					Element delvFeeElement = doc.getElementById("deliveryFee");
					if(delvFeeElement != null){
						delvFee = delvFeeElement.val();
					}
					String linkman = "";
					Element linkmanElement = doc.getElementById("linkman");
					if(linkmanElement != null){
						linkman = linkmanElement.text().trim();
					}
					String contactPhone = "";
					Element contactPhoneElement =  doc.getElementById("contactPhone");
					if(contactPhoneElement != null){
						contactPhone = contactPhoneElement.text();
					}
					
					String selfFetchId = null;
					Elements selfFetchIdElements = doc.select("input[type='radio'][name='selfFetchAddr']");//radio
					if(selfFetchIdElements != null){
						for (Element element : selfFetchIdElements) {
							if(element.attr("checked") == "checked"){
								selfFetchId = element.val();
							}
						}
					}
					
					String businessHallSelfFetchId = "";
					Elements businessHallSelfFetchIdElements = doc.select("input[type='radio'][name='kingCardBusinessHallSelfFetchAddr']");
					if(businessHallSelfFetchIdElements != null){
						for (Element element : businessHallSelfFetchIdElements) {
							if(element.attr("checked") == "checked"){
								businessHallSelfFetchId = element.val();
							}
						}
					}
					
					postEditChecks.put("name", name);
					postEditChecks.put("phone", phone);
					postEditChecks.put("province", province);
					postEditChecks.put("city", city);
					postEditChecks.put("district", district);
					postEditChecks.put("addr", addr);
					postEditChecks.put("payWay", payWay);
					postEditChecks.put("delvDate", delvDate);
					postEditChecks.put("wishLgts", wishLgts);
					postEditChecks.put("selfFetch", selfFetch);
					postEditChecks.put("businessHallSelfFetch", businessHallSelfFetch);
					postEditChecks.put("fixDate", fixDate);
					postEditChecks.put("delvFee", delvFee);
					
					JSONObject updateInfoData = new JSONObject();
					JSONObject postInfo = new JSONObject();
					postInfo.put("receiverName", req.getReceiverName());
					postInfo.put("mobilePhone", req.getMobilePhone());
					postInfo.put("deliverTypeCode", req.getDeliverTypeCode());
					postInfo.put("dispatchName", req.getDispatchName());
					
					if("联通自提".equals(postInfo.getString("dispatchName"))){
						postInfo.put("selfFetchFlag", "0");
					}else if("联通营业厅自提".equals(postInfo.getString("dispatchName"))){
						postInfo.put("selfFetchFlag", "1");
						postInfo.put("linkman", linkman);
						postInfo.put("contactPhone", doc.select(".item-op #mobile_phone").text().trim());
					}

					if("10".equals(postInfo.getString("deliverTypeCode"))){
						postInfo.put("proviceCode", req.getProviceCode());
						postInfo.put("cityCode", req.getCityCode());
						postInfo.put("districtCode", req.getDistrictCode());
						postInfo.put("selfFetchFlag", "1");
						postInfo.put("linkman", linkman);
						postInfo.put("contactPhone", contactPhone);
						postInfo.put("selfFetchId", selfFetchId);
						postInfo.put("businessHallSelfFetchId", businessHallSelfFetchId);

					}
					if(!"03".equals(req.getDeliverTypeCode()) && !"10".equals(req.getDeliverTypeCode())){

						postInfo.put("proviceCode", req.getProviceCode());
						postInfo.put("cityCode", req.getCityCode());
						postInfo.put("districtCode", req.getDistrictCode());
						postInfo.put("postAddr", req.getPostAddr());
						postInfo.put("wishLgtsCode", req.getWishLgtsCode());
						postInfo.put("deliverDateType", req.getDeliverDateType());
						
						
						if("04".equals(req.getDeliverDateType())){
							postInfo.put("fixDelvTime", req.getFixDelvTime());
						}
					}
					
					if("02".equals(paramMap.get("payTypeCode"))){//货到付款
						postInfo.put("payWay", payWay);
						postInfo.put("selfFetchId", selfFetch);
					}
					
					//String url = ZBOrderUrlConsts.ORDER_ARTIFICIAL_UPDATE_POST_INFO;
					String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_UPDATE_POST_INFO)+orderId+"?orderId="+orderId;
					
					// 创建参数队列
					//List<NameValuePair> formparams = new ArrayList<NameValuePair>();
					//formparams.add(new BasicNameValuePair("postInfo", postInfo.toString()));
					JSONObject params = new JSONObject();
					params.put("postInfo", postInfo);
					url = url + orderId;
					responseStr = client.jsonPost(params.toString(), url);//post(formparams, url);
					JSONObject responseJson = JSONObject.fromObject(responseStr);
					String phoneBlk = responseJson.getString("phoneBlk");
					String addrBlk = responseJson.getString("addrBlk");
					if(StringUtil.isEmpty(responseJson.getString("exceptions")) || !"false".equals(phoneBlk) || !"false".equals(addrBlk)){
						resp.setError_code("-1");
						resp.setError_msg(responseStr);
						log.info("=====["+req.getOrderNo()+"-订单审核-修改配送信息]进入详情失败，返回报文："+responseStr);
					}else{
						resp.setError_code("0");
					}
					
					return resp;
				
				}
			}
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg(e1.getLocalizedMessage());
			log.info("=====["+req.getOrderNo()+"-订单审核-修改配送信息]进入详情失败，返回报文："+responseStr);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			resp.setError_msg(e1.getLocalizedMessage());
			log.info("=====["+req.getOrderNo()+"-订单审核-修改配送信息]进入详情失败，返回报文："+responseStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_msg(e.getLocalizedMessage());
			log.info("=====["+req.getOrderNo()+"-订单审核-修改配送信息]进入详情失败，返回报文："+responseStr);
		}
		
		return resp;
	}
	
	/**
	 * 调用总商标准化接口地址
	 * @param inJson
	 */
	private synchronized static boolean getOrderCtn(String inJson,String threadName){
		boolean success = false;
		try {
			String outJson = "";//接口返回报文
			String orderId;//外部订单号
			String activeNo = "";// 序列号
			activeNo = CrawlerUtils.searchValue(inJson, "ActiveNo");
			orderId = CrawlerUtils.searchValue(inJson, "OrderId");
			
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

			Map<String, String> params = CrawlerSetting.HANDLE_NUM_MAP.get(threadName);
			String str = params.get("HANDLE_NUM");
			String status = params.get("UPDATE_STATUS");
			int handleNum = StringUtil.isEmpty(str)?-1:Integer.valueOf(str);
			if(handleNum == 0){//设置了处理条数，如果处理条数等于0则不做收单
				if("0".equals(status)){
	        		String server_ip = StringUtil.getLocalIpAddress();
	        		String server_port = StringUtil.getContextPort();
	        		CrawlerUpdateHandleNumReq req = new CrawlerUpdateHandleNumReq();
	        		req.setIp(server_ip);
	        		req.setPort(server_port);
	        		req.setHandleNum(0);
	        		ZteResponse resp = client.execute(req, ZteResponse.class);
	        		if("0".equals(resp.getError_code())){
	        			params.put("UPDATE_STATUS", "1");
	        		}
				}
				return success;
			}
			OrderCtnReq req = new OrderCtnReq();
			req.setOutServiceCode(CrawlerConsts.OUT_SERVICE_CODE_CENTERMALLORDERSTANDARD);
			req.setVersion(CrawlerConsts.VERSION);
			req.setReqMsgStr(inJson);
			req.setFormat(CrawlerConsts.ORDER_QUEUE_MSG_TYPE_JSON);
			req.setOutOrderId(orderId);
			long begin = System.currentTimeMillis();
			
			OrderCtnResp resp = client.execute(req, OrderCtnResp.class);
			long end = System.currentTimeMillis();
			log.info("=====[OrderCrawlerTimer] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
			if (null != resp) {
				String rpc_type = resp.getRpc_type();
				if (CrawlerConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
					String error_code = resp.getError_code();
					if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
						if(handleNum > 0){
							handleNum--;
							params.put("HANDLE_NUM", String.valueOf(handleNum));
							CrawlerSetting.HANDLE_NUM_MAP.put(threadName, params);
							
						}
						success = true;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
					} else {
						success = false;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
					}
				} else if (CrawlerConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
					String error_code = resp.getError_code();
					if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
						if(handleNum > 0){
							handleNum--;
							params.put("HANDLE_NUM", String.valueOf(handleNum));
							CrawlerSetting.HANDLE_NUM_MAP.put(threadName, params);
						}
						success = true;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
					} else {
						success = false;
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
					}
				} else {
					success = false;
					outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
				}
			} else {
				success = false;
				outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败\"}}";
			}
			
			if(success)
				cache.set(NAMESPACE, HB_ZB_ORDER_ID+orderId,orderId);//把标准化成功的订单号设置到缓存系统中
			
			log.info("======[线程："+threadName+"]调用总商标准化订单接口返回结果：============"+outJson);
			log.info("======[线程："+threadName+"]调用总商标准化订单接口返回结果：============"+outJson);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return success;
	}
	
	/**
	 * 查询总商城物流信息
	 * @param client
	 * @param orderId
	 * @return
	 */
	public static OperationRecordResp queryZBLogisticsInformation(ZBSystemClient client, String orderId){
		OperationRecordResp resp = new OperationRecordResp();
		ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		//客服订单查询页面详情按钮链接
    	String detailUrl = ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL;
    	// 创建参数队列
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", orderId));//订单ID
		detailparams.add(new BasicNameValuePair("orderType", "1"));
		
		String detailOrder = null; //= getFileToString("2647353045");
		try {
			detailOrder = client.post(detailparams, detailUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//爬虫客服订单查询页面详情数据
		if(StringUtils.isEmpty(detailOrder)){//爬虫获取的订单详情数据不为空
			resp.setError_code("1");
			resp.setError_msg("订单["+orderId+"]在客服订单详情数据为空");
			return resp;
		}
		Document doc = Jsoup.parse(detailOrder);
		String orderStatus = CrawlerConsts.SIGN_STATUS_0;
		List<Route> list = new ArrayList<Route>();
		
		Elements ligiNoElements = doc.select("div.inforPosition").select(".width179");

		for (Element element : ligiNoElements) {
			String str = element.text();
			if(!StringUtils.isEmpty(str) && str.indexOf("运单号码：") != -1){
				 resp.setLogi_no(str.substring(5));;
			}
		}
		
		Elements operationRecord = doc.select(".operationRecord");
		if(operationRecord != null && operationRecord.size() > 0){
			for (Element element : operationRecord) {
				Elements operationType = element.select(".operationType");
				if(operationType == null || operationType.size() != 1){
					continue;
				}
				String type = operationType.get(0).text();
				if((!StringUtil.isEmpty(type) && "发货完成".equals(type.trim())) || "物流在途".equals(type)){
					String operationTime = "";
					String operationInfo = "";
					String operator = "";
					
					Elements operationTimeElements = element.select(".operationTime");
					if(operationTimeElements != null || operationTimeElements.size() == 1){
						operationTime = operationTimeElements.get(0).text();
					}
					
					Elements operationInfoElements = element.select(".operationInfo");
					if(operationInfoElements != null || operationInfoElements.size() == 1){
						operationInfo = operationInfoElements.get(0).text();
						
						if("签收成功".equals(operationInfo)){
							orderStatus = CrawlerConsts.SIGN_STATUS_1;
						}
					}
					
					Elements operatorElements = element.select(".operator");
					if(operatorElements != null || operatorElements.size() == 1){
						operator = operatorElements.get(0).text();
					}
					Route item = new Route();
					
					item.setAccept_time(operationTime);
					item.setRemark(type);
					item.setAccept_address(operationInfo);
					item.setOpcode(operator);
					list.add(item);
				}
			}
		}
		
		resp.setError_code("0");
		resp.setOperationRecordList(list);
		resp.setSign_status(orderStatus);
		return resp;
	}
	
	/**
	 * 通过Jsoup解析html获取下拉框选中值
	 * @param doc
	 * @param tagName
	 * @return
	 */
	private static String getSelectValue(Document doc,String tagName){
		String optionValue = "";
		if(doc!=null && StringUtils.isNotBlank(tagName)){
			Element staffGroupIdElement =  doc.getElementById(tagName);//获取下拉框值
			if(staffGroupIdElement!=null){
				Elements options = staffGroupIdElement.getElementsByTag("option");
				for(int i=0;i<options.size();i++){
					String attr = options.get(i).attr("selected");
					if(StringUtils.isNotBlank(attr) && "selected".equals(attr)){//选中值
						optionValue = options.get(i).attr("value");
					}
				}
			}
		}
		return optionValue;
	}
	
	/**
	 * 通过Jsoup获取html中的js变量值
	 * @param htmlContent
	 * @param inputmap
	 * @return
	 */
	public static void getJsoupScriptContent(String htmlContent,Map<String, String> inputmap){
		if(StringUtils.isNotBlank(htmlContent)){
			Document doc = Jsoup.parse(htmlContent,CrawlerSetting.CRAWLER_ADDRESS);
			if(doc!=null){
				Elements e = doc.getElementsByTag("script");
				if(e!=null && e.size()>0){
					/*用來封裝要保存的参数*/
					for (Element element : e) {
						/*取得JS变量数组*/
						String[] data = element.data().toString().split("var");
						/*取得单个JS变量*/
						for(String variable : data){
							/*过滤variable为空的数据*/
							if(variable.contains("=")){
								String[]  kvp = variable.split("=");
								String[]  val = kvp[1].split(";");
								/*取得JS变量存入map*/
								String key = kvp[0].trim();
								if(StringUtils.isNotBlank(key)){
									if(inputmap.containsKey(key)) {
										//去除单双引号
										String value = val[0].trim();
										//获取值是否是json格式
										if(StringUtils.isNotBlank(value) && !CrawlerUtils.isJson(value)){
											value = value.replaceAll("'", "").replace("\"", "");
										}
										inputmap.put(key, value);
									}
								}
							}
						}
					}
				}
			}
		}
		//return inputmap;
	}

	 /**
     * @Description: 通过cookie获取对象
     * @author: lzg
     * @param name
     * @param password
     * @param validateCode
     * @param cookies
     * @return
     * @throws Exception 
     */
    public static ZBSystemClient getInstanceByCookieTest(String name,
            String password, String validateCode, String cookies) {
        // client实例
    	CloseableHttpClient client = ZBSystemClient.craeteHttpClientInstance();
    	//HttpClient client = new DefaultHttpClient();
//        ClientConnectionManager mgr = client.getConnectionManager();  
//        HttpParams params = client.getParams();  
//        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);
        
        /** 下面代码是初始化已有cookie的client对象 */
        //String url = ZBOrderUrlConsts.INIT_ZB_MANAGER_URL;
        String url = "http://admin.mall.10010.com/ManagerIndex/init";
		
        String outUrl = "http://admin.mall.10010.com/ManagerLogin/init";
		
        String responseStr = null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Referer", outUrl);
            httpget.addHeader("Host", "admin.mall.10010.com");
            httpget.addHeader("Connection", "Keep-Alive");
            httpget.addHeader("Cookie", cookies);
            // 执行get请求.
            HttpResponse response = client.execute(httpget);
            if(response!=null){
            	// 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseStr = EntityUtils.toString(entity);
                }
                if(response.getFirstHeader("Set-Cookie")!=null){
                	log.info("=============调用过cookie=============");
	                ZBSystemClient.cookieValue = response.getFirstHeader("Set-Cookie").getValue();
                }
            }
        } catch (Exception e) {
            log.info("User[" + name + "] clear user session: 访问url出错[" + url + "]", e);
            return null;
        }
        /** 上面代码是初始化已有cookie的client对象 */
        
        if (responseStr == null
                || responseStr.indexOf("<title>商城后台主页</title>") == -1) {
        	//log.info("User[" + name + "] clear user session:"+responseStr);
            return null;
        }
        
        // 请求参数实体
        ZBLoginParam loginParam = new ZBLoginParam();
        loginParam.setUsername(name);
        loginParam.setPassword(password);
        loginParam.setValidateCode(validateCode);

        // 登录结果检测器
        LoginResultDetector loginResultValidator = ZBLoginResultDetector.getInstance();

        // 在线状态检测器
        LoginStatusDetector loginStatusDetector =  ZBLoginStatusDetector.getInstance();

        // 创建实例并初始化必要属性
        ZBSystemClient zbSystemClient = new ZBSystemClient();
        zbSystemClient.setOnline(true);
        zbSystemClient.setHttpclient(client);
        zbSystemClient.setLoginParam(loginParam);
        zbSystemClient.setLoginResultValidator(loginResultValidator);
        zbSystemClient.setLoginStatusDetector(loginStatusDetector);
        zbSystemClient.setName(name);
        return zbSystemClient;
    }
	
	public static void main(String[] args) {
		
		
		String username = "ZJ0003";
		String password = "Hlw2016";
		String cookieValue = "AdminStaff=qITWpHb/vJa5Bx9l1aQmaZIDzUlNpBjLfcXINni36LoJ7O2euPbkgzc9sNpjhHBBdhVDB4ZK7KXbMNIh0rBORH6gF+3X/Rrxk8cd5lwDNdPpISuwBcguUbpQgmgYXNBEkJganfNkwos/KCr/rNFPvcgIA0p3jdVEHBE0vbgDdwbo7Ysm4WZwKobl9eovtBV2wLAuHeAj9gy3avArynh9ngRA537TJo/8KsDYkAd9TDcJN9ZX+6lRpK50Nv0tl8f4NCdukeKG7M540H4uI+fvQVmp/AXi3s8rn1pUtFO1uY+B4rlQj6FhAOy5/Uj1hS45Iu9n8dkTizbqrWGgaXQgbdql9PdruNZNBno/6ja6EZN7Q35Fta7cut0nRy2wu+PMQBh5Qo29ZaJYy2A5Ad2eOAVkkf9afZM4mcUj3xatWdd4VJevd7QDCOnFSYovCWa82IAENGpgp7SpqZO8M/esoBVd3aSEEljECs4n9gYpwe2D64EpmvYl7s4i4rDgNFoe4nw65m0tgbPe7VmYGYjjBfLRk6uo2WqjSXPxUN3PaIO55RJJS/1ps9IyqoqOmNSVqz/fsKoGPB1o/BKX3V9U7h+HuNMS82cPHd1jX8NJRpDTcAL/4tXrQbeW6T4+vVNTdZXa6vC8aXa10xY1KHgAHzZ66tFPLIEZpcfcCmrvyz+kkTjBxqzymrd1XIKrcr6KWoVBGiSBQsTjXSevzIoW8ruh4WJZJftmhTjuaw6PnCeaMRwFpUYud2A+vkVcI+m2aGHFHMIWDy/CevdzEftVynBSGmX+hPLAApeIx7AGUfqilTt3k9j63nkHiLFLACslgKEeHJxo3+5L9IHdCQjtPW4UjoMOm9ZvQuhccfF5vPwAfWRCU6NZfGPMwevYamd/lg/rWvw7TGgdiIisZWbfDEGejUAoLo2+cvkdpLSfbMTqEWjHOo1auIuKnXmjS/VlnDyA3GcOSSjXTdCvZPGQlVdA4bDuwzmi684LYEQmgSJlGV8zaZyclTXWdwxUMV8i5m443XnbecUtyw4ugU4BpPPNTYWgk6ItuVP91SsL29Cm+3isL2GcMPSlDCe8Za2JZFAkh7Aeh8jOjvWV+ryU/D7YaaN3gppavx3MTZcLBegnv2HOKUkx978Q+l/Tnq7fEVjjQ9xMvQOi5WKZJuDsWrHmjI1w3s0oV1AyBZGixTraRu8jR00jrLNhB/J/UXIXji3GX7Vp64pax5Ja4dx/IGKEceGrFA8/m39g0B0/WPjJu2w0muNfTI5o6YrD2/M4dagyrOjC5UFdPayRm3WOq0yAR/om+++yyIqhRsl7eBvRBb/HdpWL+wfasrxKalQ0VH+e8LyAv+Bn9Pg24G3T1vPhPZaYMzFzonzXSVo1TrtjqDBXFa0GLuK0KVEPxzG8TpOWfYHldapOM+Nb9fPmjuQ8l9Izp2r6LTFHOZB10yCEO0/Dmh75N5LJU6HFNejCNsHnAxmVrRZFNHErRR12Fj+/6NbtNYfgUbHFevGegHHHkHqPQksghg1WlCU+8fha8XcNA+IG8K73/ljUfNDcC0RfSPdZS4qdebB/JFLFjpix4xsHeaKzmjhhSAX3nD5Whhr7DmYYSsR+UiscOqm8h8DipMCO5IF0MwDp0zaBQf0QyYga7E8VxEhv8hDrKgT/R2TgBPN/jfbJ0NYDv+lNmO03rBq8gd6rfw8vu6cjdsFiRYAWDlUEOnzUwNroq+cc37ndV1bO7MU=; domain=10010.com; expires=Tuesday, 11-Apr-2017 03:10:06 GMT; path=/";
		ZBSystemClient client = ZBSystemClient.getInstanceByCookieTest(username,password,"",cookieValue);
        if (client != null) {
           log.info("用户["+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"]的登陆成功!");  
           SingleApplicationReq req = new SingleApplicationReq();
           req.setReasonCode("0402");//退单编码
           req.setReasonDesc("客户申请退单");//退单描述
           req.setCancelRemark("退单原因：同意退单");//退单备注
           req.setOrderId("9817041051148638");//总商id
           req.setOrderNo("5696819023");
           req.setLink("orderReview");
           client.singleApplication(client, req);
          /* CallRefundReq req = new CallRefundReq();
           req.setOrderId("6617033025903826");
           req.setOrderNo("4302292886");
           client.callRefund(client, req);*/
           
          /* BackOrderLayerReq req = new BackOrderLayerReq();
           req.setOrderId("4302292886");
           req.setBackReasonDesc("客户申请退款");
           req.setOrderDelayTag("1");
   			client.backOrderLayer(client, req);*/
           
           
            /**CrawlerUpdateGoodsInfoReq req = new CrawlerUpdateGoodsInfoReq();
            req.setOrderNo("3490625203");
            req.setCustName("林格智");
            req.setFirstMonthFee("A000011V000002");
            req.setFirstMonthFeeName("半月");
            req.setPsptType("02");
            req.setPsptNo("445281199210044076");
            req.setPreNum("13208028735");
            
            client.updateGoodsInfo(client, req);*/
            
           /* CrawlerUpdatePostInfoReq postInfo = new CrawlerUpdatePostInfoReq();
            postInfo.setOrderNo("6841058309");
            postInfo.setReceiverName("测试人员");
            postInfo.setMobilePhone("13317353056");
            postInfo.setDeliverTypeCode("01");
            postInfo.setDispatchName("第三方物流");
            postInfo.setProviceCode("330000");
            postInfo.setCityCode("330100");
            postInfo.setDistrictCode("330108");
            postInfo.setWishLgtsCode("1001");
            postInfo.setPostAddr("（测试单，如误处理请退单）1");
            postInfo.setDeliverDateType("01");
            
            client.updatePostInfo(client, postInfo);*/
            
        } 
	}
	
	/**
	 * 通过Jsoup解析爬虫文件
	 * @param filename
	 * @return
	 */
	public static Document getFile(String parentDir,String childDir,String filename){
		Document doc = null;
		try {
			String url = ZBLoginParam.getParseFileUrl();
			if(StringUtils.isNotBlank(parentDir)){
				url = url+parentDir+"/";
			}
			if(StringUtils.isNotBlank(childDir)){
				url = url+childDir+"/";
			}
			url = url + filename+".html";
			//String url = ZBLoginParam.parseFileUrl+filename+".html";
			File input = new File(url);
			if(input.exists()){
				doc = Jsoup.parse(input, "UTF-8", CrawlerSetting.CRAWLER_ADDRESS);
			}else{
				 log.info("=========要解析的文件"+filename+"不存在!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 把爬虫内容生成html文件
	 * @param line
	 * @param filename
	 * @return
	 */
	private static File content2File(String parentDir,String childDir,String line,String filename){
		File f = null;
		try{
			if(StringUtils.isNotBlank(line)){
				String url = ZBLoginParam.getParseFileUrl();
				if(StringUtils.isNotBlank(parentDir)){
					url = url+parentDir+"/";
				}
				if(StringUtils.isNotBlank(childDir)){
					url = url+childDir+"/";
				}
				url = url + filename+".html";
				f=new File(url);//向指定文本框内写入
				if (!f.exists() && !f.isDirectory()) {
			        f.getParentFile().mkdirs();
			    }
				if (f.exists()) {  
		            f.delete();  
		        }  
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f, true),"UTF-8");
				osw.write(line);
//				FileWriter fw=new FileWriter(f);
//				fw.write(line);
//				fw.close();
				osw.close();
			}else{
				log.info("========要生成文件的内容为空不能生成文件");
			}
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  return f;
	}

	/**
	 * 登录成功后访问总商主页
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doEnterUrl(String url) throws Exception {
		String responseStr = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseStr = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			log.info("访问url出错[" + url + "]", e);
			throw e;
		}
		
		return responseStr;
	}

	/**
	 * 登录成功后获取总商cookie
	 * @param url
	 * @return
	 * @throws Exception
	 */
    public String doEnterUrlReturnCookie(String url) throws Exception {
        String cookies = "";
        String setCookie = "";
        String JSESSIONID = "";
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = null;
            // 执行get请求.
            if(httpclient!=null){
            	 RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();//设置请求和传输超时时间
            	 httpget.setConfig(requestConfig);
            	 response = httpclient.execute(httpget);
            	 setCookie = response.getFirstHeader("Set-Cookie").getValue();
                 //log.info("=============setCookie:" + setCookie);
                 JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
                 //log.info("=============JSESSIONID:" + JSESSIONID);
                 Header[] headers = response.getHeaders("Set-Cookie");
                 for (int i = 0; i < headers.length; i++) {
                     if (i == 0) {
                         cookies += headers[i].getValue();
                     } else {
                         cookies += "; " +headers[i].getValue();
                     }
                 }
            }
            //log.info("=============cookies:" + cookies);
        } catch (Exception e) {
            log.info("访问url出错[" + url + "]", e);
            throw e;
        }
        log.info("=============调用过cookie=============");
        return setCookie;
    }

	/**
	 * 调用发送验证码的接口，让总部系统将短信发送到对应手机上
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static boolean doSendValidate(String username,String password,String sendType) throws Exception {
		boolean isSend = false;
		String responseStr = null;
		try {
			//第一步构建对象，初始化基本信息
			if(StringUtils.isBlank(username)){
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认值
			}
			if(StringUtils.isBlank(password)){
				password = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_PWD;//默认值
			}
			clientLogin = ZBSystemClient.getInstance(username,password,"");
			String url = ZBLoginParam.getValidateUrl()+"?staffCode="+username+"&sendType="+sendType;
			if(StringUtils.isNotBlank(url)){
				responseStr = clientLogin.get(url);
				log.info("=====发送验证码返回报文："+responseStr);
				if(responseStr!=null && StringUtils.isNotBlank(responseStr)){
					isSend = true;
				}else{
					isSend = false;
				}
			}
		} catch (Exception e) {
			log.info("发送验证码出错[账号:" +username+ "]", e);
			isSend = false;
		}
		return isSend;
	}
	
	/**
	 * 调用发送验证码的接口，让总部系统将短信发送到对应手机上,并且手工从console输入验证码，用于调试
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String doSendValidateAndInput() throws Exception {
		String responseStr = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(ZBLoginParam.getValidateUrl()+"?staffCode="+loginParam.getUsername()+"&sendType=phone");
			// 执行get请求.
			HttpResponse response = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseStr = EntityUtils.toString(entity);
				log.info("发送验证码返回："+responseStr);
			}
		} catch (Exception e) {
			log.info("发送验证码出错[账号:" + loginParam.getUsername() + "]", e);
			throw e;
		}

		InputStreamReader is = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(is);
		log.info("请输入验证码：");
		String name = br.readLine();
		log.info(name);
		
		loginParam.setValidateCode(name);
		return name;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static ZBSystemClient getInstance(String name, String password,String validateCode) {
        // client实例
        //HttpClient client = new DefaultHttpClient();
		CloseableHttpClient client = ZBSystemClient.craeteHttpClientInstance();
		// 请求参数实体
		ZBLoginParam loginParam = new ZBLoginParam();
		loginParam.setUsername(name);
		loginParam.setPassword(password);
		if(StringUtils.isNotBlank(validateCode)){
			loginParam.setValidateCode(validateCode);//设置验证码
		}
		

		// 登录结果检测器
		LoginResultDetector loginResultValidator = ZBLoginResultDetector.getInstance();

		// 在线状态检测器
		LoginStatusDetector loginStatusDetector =  ZBLoginStatusDetector.getInstance();

		// 创建实例并初始化必要属性
		ZBSystemClient zbSystemClient = new ZBSystemClient();
        zbSystemClient.setHttpclient(client);
		zbSystemClient.setLoginParam(loginParam);
		zbSystemClient.setLoginResultValidator(loginResultValidator);
		zbSystemClient.setLoginStatusDetector(loginStatusDetector);
		zbSystemClient.setName(name);

		return zbSystemClient;
	}

	/**
	 * 进行登录
	 * 
	 * @return boolean 是否登录成功
	 * @throws Exception
	 */
	public boolean doLogin() throws Exception {
		String responseStr = null;
		// 创建httppost
		HttpPost httppost = new HttpPost(ZBLoginParam.getLoginUrl());
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("staffCode", loginParam.getUsername()));
		formparams.add(new BasicNameValuePair("passWd", loginParam.getPassword()));
		formparams.add(new BasicNameValuePair("safetyCode", loginParam.getValidateCode()));			
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseStr = EntityUtils.toString(entity, "UTF-8");
				log.info("=====登录返回报文："+responseStr);
			}
		} catch (Exception e) {
			log.info("登录出错[账号:" + loginParam.getUsername() + "] ", e);
			throw e;
		}

		return loginResultValidator.isLogin(responseStr);
	}

	/**
	 * 释放连接
	 */
	public void shutdown() {
		log.info("===========执行httpclient释放连接=========");
		log.info("===========执行httpclient释放连接=========");
		httpclient.getConnectionManager().shutdown();
	}

	/** =====================非登录等相关操作使用===================start */
    /**
     * @Description: 通过总部用户名获取ZBSystemClient对象
     * @author: shusx
     * @param name
     * @return
     * @throws Exception 
     */
    public static ZBSystemClient getInstanceByOperName(String name)
            throws Exception {
        return new ZBSystemClient();
    }

    /**
     * @Description: 获取所有总部用户的ZBSystemClient对象(在线的用户)
     * @author: shusx
     * @date: 2013-10-14 上午11:38:40
     * 
     * @return
     * @throws Exception 
     */
    public static List<ZBSystemClient> getInstanceList()throws Exception {
        return new ArrayList<ZBSystemClient>();
    }

    /**
     * @Description: 通过cookie获取对象
     * @author: lzg
     * @param name
     * @param password
     * @param validateCode
     * @param cookies
     * @return
     * @throws Exception 
     */
    public static ZBSystemClient getInstanceByCookie(String name,
            String password, String validateCode, String cookies) {
        // client实例
    	CloseableHttpClient client = ZBSystemClient.craeteHttpClientInstance();
    	//HttpClient client = new DefaultHttpClient();
//        ClientConnectionManager mgr = client.getConnectionManager();  
//        HttpParams params = client.getParams();  
//        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);
        
        /** 下面代码是初始化已有cookie的client对象 */
        //String url = ZBOrderUrlConsts.INIT_ZB_MANAGER_URL;
        String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.INIT_ZB_MANAGER_URL);
		
        String outUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.LOGIN_ZB_OUT_URL);
		
        String responseStr = null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Referer", outUrl);
            httpget.addHeader("Host", "admin.mall.10010.com");
            httpget.addHeader("Connection", "Keep-Alive");
            httpget.addHeader("Cookie", cookies);
            // 执行get请求.
            HttpResponse response = client.execute(httpget);
            if(response!=null){
            	// 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseStr = EntityUtils.toString(entity);
                }
                if(response.getFirstHeader("Set-Cookie")!=null){
                	log.info("=============调用过cookie=============");
	                ZBSystemClient.cookieValue = response.getFirstHeader("Set-Cookie").getValue();
                }
            }
        } catch (Exception e) {
            log.info("User[" + name + "] clear user session: 访问url出错[" + url + "]", e);
            return null;
        }
        /** 上面代码是初始化已有cookie的client对象 */
        
        if (responseStr == null
                || responseStr.indexOf("<title>商城后台主页</title>") == -1) {
        	//log.info("User[" + name + "] clear user session:"+responseStr);
            return null;
        }
        
        // 请求参数实体
        ZBLoginParam loginParam = new ZBLoginParam();
        loginParam.setUsername(name);
        loginParam.setPassword(password);
        loginParam.setValidateCode(validateCode);

        // 登录结果检测器
        LoginResultDetector loginResultValidator = ZBLoginResultDetector.getInstance();

        // 在线状态检测器
        LoginStatusDetector loginStatusDetector =  ZBLoginStatusDetector.getInstance();

        // 创建实例并初始化必要属性
        ZBSystemClient zbSystemClient = new ZBSystemClient();
        zbSystemClient.setOnline(true);
        zbSystemClient.setHttpclient(client);
        zbSystemClient.setLoginParam(loginParam);
        zbSystemClient.setLoginResultValidator(loginResultValidator);
        zbSystemClient.setLoginStatusDetector(loginStatusDetector);
        zbSystemClient.setName(name);
        return zbSystemClient;
    }
    
    /**
     * 将指定的参数以post形式发送到指定的url
     * @param params 参数
     * @param url url
     * @return 结果字符串
     * @throws Exception 
     */
    public synchronized String post(List<NameValuePair> params, String url)
            throws SystemException, BusinessException {
    	log.info("爬虫模拟调用信息，\nURL："+url+"\n出参报文："+params.toString());
        String responseStr = null;
        // 创建httppost
        
        if(StringUtil.isEmpty(url)){
        	throw new SystemException(null,"请求地址为空，请在爬虫配置管理中增加目标操作地址。");
        }
        
        HttpPost httppost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;
        String req_time=null;
        try { 
        	req_time=DateUtil.getTime2();
            uefEntity = new UrlEncodedFormEntity(params, "UTF-8");

            httppost.setEntity(uefEntity);
            CloseableHttpResponse response=null;
            if(httpclient!=null){
            	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();//设置请求和传输超时时间
            	httppost.setConfig(requestConfig);
            	response = httpclient.execute(httppost);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        if (responseStr != null
                && (responseStr.indexOf("<html><head><title>302 Moved Temporarily</title></head>") != -1
                || responseStr.indexOf("<title>商城、商户登录</title>") != -1)) {
            throw new SystemException(null, "用户[" + this.getLoginParam().getUsername()
                    + "]连接不存在,或者连接已经断开!请重新登陆!");
        }
       // log.info("爬虫模拟调用信息，\nURL："+url+"\n返回报文："+responseStr);
        //记录日志
        /*RecordInfLogReq req=new RecordInfLogReq();
        req.setReq_time(req_time);
        req.setEp_address(url);
        if(params!=null){
        	 req.setReq_xml(params.toString());
        	 for (NameValuePair nameValuePair : params) {
        		 if(("orderId").equals( nameValuePair.getName())){
        			 req.setOrder_id(nameValuePair.getValue());
        		 };
     		}
        }
        this.recordInfLog(req);*/
        return responseStr;
    }
    
    /**
     * 将指定的参数以json形式发送到指定的url
     * @param data 参数
     * @param url url
     * @return 结果字符串
     * @throws Exception 
     */
    public synchronized String jsonPost(String data, String url)
            throws SystemException, BusinessException {
        String responseStr = null;
        // 创建httppost
        
        if(StringUtil.isEmpty(url)){
        	throw new SystemException(null,"请求地址为空，请在爬虫配置管理中增加目标操作地址。");
        }
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response=null;
        String req_time=null;
        try {
        	req_time=DateUtil.getTime2();
        	StringEntity uefEntity = new StringEntity(data,ContentType.APPLICATION_JSON);// 构造请求数据
            httppost.setEntity(uefEntity);
            if(httpclient!=null){
            	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();//设置请求和传输超时时间
            	httppost.setConfig(requestConfig);
            	response = httpclient.execute(httppost);
            	HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseStr = EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				//response.getEntity().getContent().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       //记录日志
       /* RecordInfLogReq req=new RecordInfLogReq();
        req.setReq_time(req_time);
        req.setEp_address(url);
        req.setReq_xml(data);
        this.recordInfLog(req);*/
        
        return responseStr;
    }
    
    public synchronized String postJson(String url,String data){
		String responseStr = null;
		
		try {
	        if(StringUtil.isEmpty(url)){
	        	throw new SystemException(null,"请求地址为空，请在爬虫配置管理中增加目标操作地址。");
	        }
			
            URL urlcon = new URL(url);  
            //HttpURLConnection connection = (HttpURLConnection) urlcon.openConnection();  
            HttpURLConnection connection;
            if("Y".equals(CrawlerSetting.IS_PROXY)) {//如果走代理
            	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(CrawlerSetting.PROXY_HOST, CrawlerSetting.PROXY_PORT));
                connection = (HttpURLConnection) urlcon.openConnection(proxy); 
            }else {
            	connection = (HttpURLConnection) urlcon.openConnection(); 
            }

            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setRequestMethod("POST");  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);   
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");         
            connection.connect();  
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
            out.write(data.getBytes("UTF-8"));//这样可以处理中文乱码问题  
            out.flush();  
            out.close(); 
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
            String lines;  
            StringBuffer sb = new StringBuffer("");  
            while ((lines = reader.readLine()) != null) {  
                lines = new String(lines.getBytes(), "UTF-8");  
                sb.append(lines);  
            }  
            
            responseStr = sb.toString();
            
            reader.close();  
            // 断开连接  
            connection.disconnect();
            
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return responseStr;
	}
    
    
    
    
    /**
     * 通过get方式访问一个指定的路径，返回该路径的报文
     * @param url 远程地址
     * @return 报文信息
     * @throws SystemException
     * @throws BusinessException
     */
    public synchronized String get(String url) throws SystemException,
            BusinessException {
        String responseStr = null;
        // 创建httpget.
        HttpGet httpget = new HttpGet(url);
        // 执行get请求.
        CloseableHttpResponse response = null;
        try {
        	if(httpclient!=null){
            	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();//设置请求和传输超时时间
            	httpget.setConfig(requestConfig);
            	response = httpclient.execute(httpget);
        	}
        } catch (ClientProtocolException e) {
            log.info("协议格式有误：",e);
            throw new SystemException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                responseStr = EntityUtils.toString(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (responseStr != null
                && (responseStr.indexOf("<html><head><title>302 Moved Temporarily</title></head>") != -1
                || responseStr.indexOf("<title>商城、商户登录</title>") != -1)) {
            throw new SystemException(null, "用户[" + this.getLoginParam().getOperName()
                    + "]连接不存在,或者连接已经断开!请重新登陆!");
        }
        
        return responseStr;
    }
    
	/**
	 * 通过jsoup获取Document
	 * @param url
	 * @return
	 */
	public static Document getDocument(String url){
	     try {
	            return Jsoup.connect(url).get();
	     } catch (IOException e) {
	             e.printStackTrace();
	     }
	     return null;
	}
	
	/**
	 * Jsoup解析文件
	 * @param fileName
	 * @return
	 */
	private static Map<String,String> getOrderId(String fileName){
		Map<String,String> orderMap = new HashMap<String,String>();
		try {
			String url = ZBLoginParam.getParseFileUrl()+fileName;
			File input = new File(url);
			Document doc = Jsoup.parse(input, "UTF-8", CrawlerSetting.CRAWLER_ADDRESS);
			//div[class=tableBody]table[class=singleOrder]td[class=second]
			Elements elements = doc.select("table[class=singleOrder]");
			log.info("================解析文件获取的订单号大小："+elements.size());
			for(Element element:elements){
				 Elements elements1 = element.select("p");
				 if(elements1!=null && elements1.size()>0 && StringUtils.isNotBlank(elements1.get(0).text())){
					 String orderStr = elements1.get(0).text().trim();
					 int beginIndex = orderStr.indexOf("：");
					 log.info("================解析文件获取的订单号beginIndex："+beginIndex);
					 String orderId = orderStr.substring(beginIndex+1, orderStr.length());
					 log.info("================解析文件获取的订单号："+orderId);
				 }
				 //String  ss = element.select("td").select("p").get(0).text();
			}
			
			Elements elements2 = doc.select("p:contains(订单编号)");
			for(int i=0;i<elements2.size();i++){
				String orderStr = elements2.get(i).text();
				String[] orderSplit = orderStr.split("：");
				String orderId = orderSplit[1].trim();
				log.info("================解析文件获取的订单号11："+orderId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMap;
	}
    /** =====================非登录等相关操作使用===================end */

	private void recordInfLog(RecordInfLogReq reqs){
		final RecordInfLogReq req=reqs;
		try {
			req.setRsp_time(DateUtil.getTime2());
            //orderId
			String order_id=null;
			String req_xml=req.getReq_xml();
			if(!StringUtil.isEmpty(req_xml)&&StringUtil.isEmpty(req.getOrder_id())){
				int star=req_xml.indexOf("\"orderId\":\"");
				req_xml=req_xml.substring(star+11,req_xml.length());
				int end=req_xml.indexOf("\"");
				order_id=req_xml.substring(0,end);
				req.setOrder_id(order_id);
			}
			Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					client.execute(req, ZteResponse.class);}
			});
			thread.start();
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ZBLoginParam getLoginParam() {
		return loginParam;
	}

	public void setLoginParam(ZBLoginParam loginParam) {
		ZBSystemClient.loginParam = loginParam;
	}

	public LoginResultDetector getLoginResultValidator() {
		return loginResultValidator;
	}

	public void setLoginResultValidator(LoginResultDetector loginResultValidator) {
		this.loginResultValidator = loginResultValidator;
	}

	public LoginStatusDetector getLoginStatusDetector() {
		return loginStatusDetector;
	}

	public void setLoginStatusDetector(LoginStatusDetector loginStatusDetector) {
		this.loginStatusDetector = loginStatusDetector;
	}

	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}

	public static CloseableHttpClient craeteHttpClientInstance(){
		if("Y".equals(CrawlerSetting.IS_PROXY)) {//如果走代理
			HttpHost proxy = new HttpHost(CrawlerSetting.PROXY_HOST,CrawlerSetting.PROXY_PORT);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			return HttpClients.custom().setRoutePlanner(routePlanner).build();
		}else{
			return HttpClients.createDefault();
		}
	}
	
	public void setHttpclient(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
}
