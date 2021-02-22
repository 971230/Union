package zte.net.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.BindCustInfo2OrderReq;
import params.req.ManualModifyBuyerInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.OpenAccountDetailReq;
import params.req.OpenAccountSubmitOrderReq;
import params.req.ReAllotOrderReq;
import params.resp.BindCustInfo2OrderResp;
import params.resp.CrawlerResp;
import params.resp.ManualModifyBuyerInfoResp;
import params.resp.OpenAccountDetailResp;
import params.resp.OpenAccountSubmitOrderResp;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.IOrderOpenAccountSectionService;

import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.autoprocess.base.exception.BusinessException;
import com.ztesoft.autoprocess.base.exception.SystemException;
import com.ztesoft.autoprocess.base.model.ZbOpenType;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;

import consts.ConstsCore;

public class OrderOpenAccountSectionService implements IOrderOpenAccountSectionService{
	private static Logger logger = Logger.getLogger(OrderOpenAccountSectionService.class);
	private ZBSystemClient init(){
		ZBSystemClient client=null;
		try {
			String username = "";
			if(ZBSystemClient.clientLogin!=null){
				if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getUsername())){
					username = ZBSystemClient.clientLogin.getLoginParam().getUsername();
					//logger.info("=================分配获取登录的用户名："+username);
				}else{
					username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
				}
			}else{
				username =LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
			}
			 client = ClientPool.get(username);//获取总商登录客户端对象
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【总部系统-登陆出错】");
		}
		return client;
	}
	/**
	 * 判断总商是自动开户还是手动开户  
	 * @param orderId
	 * @return  auto自动开户 manual手动开户,null在开户界面未查询到订单
	 * @throws BusinessException 
	 * @throws SystemException 
	 */
	@Override
	public ZbOpenType zbOpenType(ZBSystemClient client,String orderId,String orderNo) throws Exception{
		ZbOpenType zbOpenType = new ZbOpenType();
		List params = new ArrayList();
		params.add(new BasicNameValuePair("orderId",orderId));
		
		String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);//查询自动开户详情
		String result =client.post(params, url);
		if(StringUtils.indexOf(result, "当前订单["+orderNo+"]已经进入开户处理")<=0){
			url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL);//查询手动开户详情
			result =client.post(params, url);
			if(StringUtils.indexOf(result, "当前订单["+orderNo+"]已经进入开户处理")>0){
				zbOpenType.setOpenType("manual");
				zbOpenType.setOpenDetailHtml(result);
			}
		}else{
			zbOpenType.setOpenType("auto");
			zbOpenType.setOpenDetailHtml(result);
		}
		
		return zbOpenType;
	}
	/**
	 * 进入总部开户详情页面
	 * @param req
	 * @return
	 */
	@Override
	public CrawlerResp reAllotOrder(ReAllotOrderReq req){
		CrawlerResp resp = new CrawlerResp();
		ZBSystemClient client =init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
		params.add(new BasicNameValuePair("page.webPager.action","refresh"));
		params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
		params.add(new BasicNameValuePair("pageSize","5"));
		try {
			
			String listHtml = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.REALLOT_ORDER_QUERY_URL));//查询二次分配订单
			if(StringUtils.indexOf(listHtml, "订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("二次分配次菜单未查询到此订单，无需二次分配");
				return  resp;
			}
			String orderId = req.getOrderId();
			params = new ArrayList<NameValuePair>();
			Document doc = Jsoup.parse(listHtml);
			if(doc!=null){
				String custId = null;
				Element el1 = doc.getElementById("custId_"+orderId);
				if(el1!=null){
					custId = el1.val();
				}
				String custIp = null;
				Element el2 = doc.getElementById("custIp_"+orderId);
				if(el2!=null){
					custIp = el2.val();
				}
				String orderState = null;
				Element el3 = doc.getElementById("orderState_"+orderId);
				if(el3!=null){
					orderState = el3.val();
				}
				String staffID = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_ID;
				String staffName = "云订单系统";
				String staffCode = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;
				
				params.add(new BasicNameValuePair("staffID",staffID));
				params.add(new BasicNameValuePair("staffCode",staffCode));
				params.add(new BasicNameValuePair("staffName",staffName));
				params.add(new BasicNameValuePair("orderID",orderId));
				params.add(new BasicNameValuePair("orderIDList",""));
				params.add(new BasicNameValuePair("custIDList",custId));
				params.add(new BasicNameValuePair("custIPList",custIp));
				params.add(new BasicNameValuePair("orderState",orderState));
				
				String responseStr = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.REALLOT_ORDER_URL));

				JSONObject respJson = JSONObject.fromObject(responseStr);
				respJson = respJson.getJSONObject("respInfo");
				/*if(respJson.containsKey("OperatorType") && "handover".equals(respJson.getString("OperatorType"))){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单二次分配失败");
					return resp;
				}*/
				if(respJson.containsKey("RespCode") && "0".equals(respJson.getString("RespCode"))){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单二次分配失败");
					return resp;
				}
				else if(respJson.containsKey("RespCode") && "-1".equals(respJson.getString("RespCode"))){
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("订单已二次分配");
					return resp;
				}
				else{
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("订单二次分配成功");
					return resp;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单二次分配时异常"+e.getMessage());
		}
		return resp;
	}
	
	/**
	 * 进入总部开户详情页面
	 * @param req
	 * @return
	 */
	@Override
	public OpenAccountDetailResp getOpenAccountDetail(OpenAccountDetailReq req){
		OpenAccountDetailResp resp = new OpenAccountDetailResp();
		ZBSystemClient client =init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		List params = new ArrayList();
		params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
		params.add(new BasicNameValuePair("page.webPager.action","refresh"));
		params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
		params.add(new BasicNameValuePair("pageSize","5"));
//		params.add(new BasicNameValuePair("kingCardType","A000170V000002"));
//		params.add(new BasicNameValuePair("userTag","2"));
//		params.add(new BasicNameValuePair("orderByTime","0"));
//		params.add(new BasicNameValuePair("delayOrderTag","1"));
		try {
			
			String result1 =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL_LIST));//查询开户列表
			if(StringUtils.indexOf(result1, "订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户列表未查询到订单");
				return  resp;
			}
			params = new ArrayList();
			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
			String  result =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL_CHECK_STATE));//订单状态检查

			JSONObject respJson = JSONObject.fromObject(result);
			respJson = respJson.getJSONObject("retInfo");
			String state=respJson.getString("ORDER_STATE");
			if ( !"C0".equals(state) &&!"C1".equals(state) &&!"CA".equals(state)){
				logger.info("订单"+req.getOrderNo()+"总商状态检查返回"+result);
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单状态异常");
				return resp;
			}
			//判断是否能够自动开户
			Document doc = Jsoup.parse(result1) ;
			Elements elements=doc.select("a.allotBtn[orderId="+req.getOrderId()+"]");
			if(elements==null||elements.size()<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单开户列表未查询到订单");
				return resp;
			}
			Element elment =elements.first();
			String userTag=elment.attr("userTag");
			String orderProvince=elment.attr("orderProvince");
			String isZFKNewFlag=elment.attr("isZFKNewFlag");
			String isCardChangeFlag=elment.attr("isCardChangeFlag");
			String tmplId=elment.attr("tmplId");
			String g4OperFlag=elment.attr("g4OperFlag");
			String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);//订单自动开户详情
			resp.setZb_open_type("0");
			if("2".equals(userTag)&&!"34".equals(orderProvince)&&!"51".equals(orderProvince)
					&&!"81".equals(orderProvince)&&!"11".equals(orderProvince)&&!"1".equals(isCardChangeFlag)){
				logger.info("老用户优惠购机订单，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";//手动开户详情
				resp.setZb_open_type("1");
			}else if("60000014".equals(tmplId)){
				logger.info("2G号码类订单，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}else if("60000018".equals(tmplId)){
				logger.info("自备机入网类订单，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}else if("60000026".equals(tmplId)){
				logger.info("号卡通用模版，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}else if("60000019".equals(tmplId)&&"0".equals(g4OperFlag)){
				logger.info("4G号卡共享套餐订单，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}else if("60000023".equals(tmplId)&&!"1".equals(isZFKNewFlag)){
				logger.info("4G号卡共享套餐订单，不支持自动开户。订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}else if("60000020".equals(tmplId)){
				logger.info("智慧沃家订单，不支持自动开户订单："+req.getOrderNo());
				url="http://admin.mall.10010.com/Odm/ManualAccount/toDetail";
				resp.setZb_open_type("1");
			}
			result =client.post(params, url);//订单自动开户详情
			if(StringUtils.indexOf(result, "当前订单["+req.getOrderNo()+"]已经进入开户处理")>0){
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("查询订单开户详情成功");
			}else{//如果自动开户里面查询不到，则查询手动开户
				result =client.post(params, "http://admin.mall.10010.com/Odm/ManualAccount/toDetail");//订单手动开户详情
				if(StringUtils.indexOf(result, "当前订单["+req.getOrderNo()+"]已经进入开户处理")>0){
					resp.setZb_open_type("1");
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("查询订单手动开户详情成功");
				}else{
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("开户详情未查询到订单");
					logger.info("订单"+req.getOrderNo()+"开户详情查询返回======"+result);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("查询订单开户详情时异常");
		}
		return resp;
	}
	/**
	 * 修改开户界面商品信息
	 * @param req
	 * @return
	 */
	@Override
	public ZteResponse modifyOpenAccountGoods(ModifyOpenAccountGoodsReq req){
			ZteResponse resp = new ZteResponse();
			ZBSystemClient client =init();
			if(null==client){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("未登陆总商系统，请先登录!");
				return  resp;
			}
			List params = new ArrayList();
			String result; 
			try {
				//步骤一查询开户详情
				params.add(new BasicNameValuePair("orderId",req.getOrderId()));
				String url = "";
//获取地址自动开户和手动开户的接口地址不一样  add by mo.chencheng 2017-04-06
				if("1".equals(req.getIsManual())){//手动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL);
				}else{//自动
					url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_DETAIL);
				}
				
				result =client.post(params, url);//订单开户详情
				if(StringUtils.indexOf(result, "当前订单["+req.getOrderNo()+"]已经进入开户处理")<=0){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("开户详情界面未查询到订单");
					return resp;
				}
				
				
//				//步骤二解析界面内容
				Map<String, String> tempGoodsInfo = new HashMap<String, String>();
				parseGoodsInfo(result, tempGoodsInfo);
				Document doc = Jsoup.parse(result) ;
				tempGoodsInfo.put("orderId", req.getOrderId());
				tempGoodsInfo.put("shareFlag", "1");
				Elements elments =doc.select(".numList li input[prenum]");
				List numberArray =new ArrayList();
				List cardtypeArray =new ArrayList();
				if(null!=elments&&elments.size()>0){
					for(int i=0;i<elments.size();i++){
						String prenum =elments.get(i).attr("prenum");
						numberArray.add(prenum);
						String cardtype =doc.select("#"+prenum+" option[selected]").val();
						cardtypeArray.add(cardtype);
					}
				}		
				tempGoodsInfo.put("numberArray", JsonUtil.toJson(numberArray));
				tempGoodsInfo.put("cardtypeArray", JsonUtil.toJson(cardtypeArray));
//				tempGoodsInfo.put("numberArrayCopy", JsonUtil.toJson(numberArray));
//				tempGoodsInfo.put("cardtypeArrayCopy", JsonUtil.toJson(cardtypeArray));
				
				
//				tempGoodsInfo.put("directArr", JsonUtil.toJson(new ArrayList()));//定向包
//				tempGoodsInfo.put("idleArr", JsonUtil.toJson(new ArrayList()));//闲时包
//				tempGoodsInfo.put("halfYearFlow", "null");//半年包
				
				boolean netFlagBoolean = false;// 入网资料是否变更
				if(StringUtils.isNotBlank(req.getIdCard())||StringUtils.isNotBlank(req.getCertType())){
					tempGoodsInfo.put("certFlag", "1");
				}else{
					tempGoodsInfo.put("certFlag", "0");
				}
				
				if(StringUtils.isNotBlank(req.getIdCard())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("psptNo", req.getIdCard());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					//解析界面
					String idCard=doc.select("#idCard").val(); 
					tempGoodsInfo.put("psptNo", idCard);
				}
				
				if(StringUtils.isNotBlank(req.getCustName())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("custName", req.getCustName());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String custName=doc.select("#custName").val(); 
					tempGoodsInfo.put("custName", custName);
				}
				
				if(StringUtils.isNotBlank(req.getCertType())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("psptType", req.getCertType());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String certType=doc.select("#certType").val(); 
					tempGoodsInfo.put("certType", certType);
				}
				
				if(StringUtils.isNotBlank(req.getCertName())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("psptName", req.getCertName());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String psptName =doc.select("#certType option[selected]").text();
					tempGoodsInfo.put("psptName", psptName);
				}
				if(StringUtils.isNotBlank(req.getCertAddr())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("psptAddr", req.getCertAddr());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String psptAddr=doc.select("#psptAddr").val(); 
					tempGoodsInfo.put("psptAddr", psptAddr);
				}
				if(StringUtils.isNotBlank(req.getReferrerName())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("recommendName", req.getReferrerName());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String ftReferrerName=doc.select("#ftReferrerName").val(); 
					tempGoodsInfo.put("recommendName", ftReferrerName);
				}
				if(StringUtils.isNotBlank(req.getReferrerPhone())){
					netFlagBoolean=netFlagBoolean||true;
					tempGoodsInfo.put("recommendPhone", req.getReferrerPhone());
				}else{
					netFlagBoolean=netFlagBoolean||false;
					String ftReferrerPhone=doc.select("#ftReferrerPhone").val(); 
					tempGoodsInfo.put("netFlagBoolean", ftReferrerPhone);
				}
				tempGoodsInfo.put("netFlag", netFlagBoolean?"1":"0");
				
				if(StringUtils.isNotBlank(req.getDevelopmentCode())||StringUtils.isNotBlank(req.getDevelopmentName())){
					tempGoodsInfo.put("recommendFlag", "1");
					tempGoodsInfo.put("refErrerCode", req.getDevelopmentCode());
					tempGoodsInfo.put("refErrerName", req.getDevelopmentName());
					tempGoodsInfo.put("recomDepartId", req.getDevelopmentDepartId());
					tempGoodsInfo.put("channelName", req.getChannelName());
					tempGoodsInfo.put("recommendNumber", req.getRecommendNumber());
				}else{
					tempGoodsInfo.put("recommendFlag", "0");
					String refErrerCode=doc.select("#refErrerCode").val(); 
					tempGoodsInfo.put("refErrerCode", refErrerCode);
					String recomDepartId=doc.select("#recomDepartId").val(); 
					tempGoodsInfo.put("recomDepartId", recomDepartId);
				}
				tempGoodsInfo.put("attrFlag", "0");
				//暂不考虑如下信息变更
				tempGoodsInfo.put("colorFlag", "0");//终端颜色是否变更
				tempGoodsInfo.put("monthFlag", "0");//首月资费方式是否变更
				tempGoodsInfo.put("numCardTypeFlag", "0");//号卡类卡类型是否变更
				tempGoodsInfo.put("phoneTypeFlag", "0");//老用户便捷换卡手机类型是否变更
				tempGoodsInfo.put("flowPackageFlag", "0");//4G号卡流量包是否变更
				tempGoodsInfo.put("locflowPackageFlag", "0");//4G号本地卡流量包是否变更
				tempGoodsInfo.put("shrflowPackageFlag", "0");//4G号共享卡流量包是否变更
				tempGoodsInfo.put("voicePackageFlag", "0");//4G号卡语音包是否变更
				tempGoodsInfo.put("locvoicePackageFlag", "0");//4G号卡本地语音包是否变更
				tempGoodsInfo.put("shrvoicePackageFlag", "0");//4G号卡共享语音包是否变更
				tempGoodsInfo.put("shortMsgPackageFlag", "0");//4G号卡短彩包是否变更
				tempGoodsInfo.put("locshortMsgPackageFlag", "0");//4G号卡本地短彩包是否变更
				tempGoodsInfo.put("shrshortMsgPackageFlag", "0");//4G号卡共享短彩包是否变更
				tempGoodsInfo.put("addServicePackageFlag", "0");//4G号卡增值业务包是否变更
				tempGoodsInfo.put("locaddServicePackageFlag", "0");//4G号卡本地增值业务包是否变更
				tempGoodsInfo.put("shraddServicePackageFlag", "0");//4G号卡共享增值业务包是否变更
				tempGoodsInfo.put("numFlag", "0");//号码
				tempGoodsInfo.put("optionalPkgFlag", "0");//
				tempGoodsInfo.put("CERT_CHECK_RSP_CODE", "200");
				tempGoodsInfo.put("CERT_CHECK_RSP_DETAIL", "身份证校验成功");
				tempGoodsInfo.put("optProductFlag", "0");//产品包
//				if(tempGoodsInfo.get("attrFlag").equals("1")||tempGoodsInfo.get("netFlag").equals("1")
//						||tempGoodsInfo.get("recommendFlag").equals("1")||tempGoodsInfo.get("optProductFlag").equals("1")
//						||tempGoodsInfo.get("flowPackageFlag").equals("1")||tempGoodsInfo.get("voicePackageFlag").equals("1")
//						||tempGoodsInfo.get("shortMsgPackageFlag").equals("1")||tempGoodsInfo.get("addServicePackageFlag").equals("1")
//						||tempGoodsInfo.get("shareFlag").equals("1")||tempGoodsInfo.get("halfYearFlowFlag").equals("1")){
//					// String param = JsonUtil.toJson(tempGoodsInfo);
					  
					//步骤三提交修改
					 
					//result =client.jsonPost(param, ZBOrderUrlConsts.OPEN_ACCOUNT_GOODS_MODIFY);
					//tempGoodsInfo = test();
					Iterator<Map.Entry<String, String>> it= tempGoodsInfo.entrySet().iterator();
					List<NameValuePair> list = new ArrayList<NameValuePair>();
					while(it.hasNext()){
						Map.Entry<String, String> tmp=it.next();
						list.add(new BasicNameValuePair(tmp.getKey(), tmp.getValue()));
					}
					
					//获取地址自动开户和手动开户的接口地址不一样  add by mo.chencheng 2017-04-06
//					if("1".equals(req.getIsManual())){//手动
//						url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_GOODS_MODIFY);
//					}else{//自动
//						url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_GOODS_MODIFY);
//					}
					if("1".equals(req.getIsManual())){//手动
						url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_GOODS_MODIFY);
					}else{//自动
						url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.OPEN_ACCOUNT_GOODS_MODIFY);
					}
					result= client.post(list, url);
					
					logger.info("订单"+req.getOrderNo()+"修改开户界面商品信息结果"+result); 
					JSONObject respJSON = JSONObject.fromObject(result);
					if(respJSON!=null && respJSON.containsKey("RespCode")){
						String respCode = respJSON.getString("RespCode");
						if(StringUtils.isNotBlank(respCode) && "0000".equals(respCode)){
							resp.setError_code(ConstsCore.ERROR_SUCC);
							resp.setError_msg("修改开户界面商品信息成功");
						}else{
							resp.setError_code(ConstsCore.ERROR_FAIL);
							resp.setError_msg("修改开户界面商品信息失败");
						}
					}
//				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("修改商品信息失败："+e.getMessage());
			} 
			return resp;
	}
	
	private void parseGoodsInfo(String context,Map map ){
		map.put("orderId","");
		map.put("goodsInstId","");
		map.put("tmplId","");
		map.put("goodsId","");
		map.put("merchantId","");
		map.put("custId","");
		map.put("proKeyMode","");
		map.put("numProcId","");
		map.put("psptNo","");
		map.put("psptAddr","");
		map.put("refErrerCode","");
		map.put("refErrerName",""); 
		map.put("recomDepartId","");
		map.put("channelName",""); 
		map.put("referrerMode","");
		map.put("defaultReferrerCode","");
		map.put("defaultReferrerName","");
		map.put("defaultChannelName","");
		map.put("defaultReferrerNumber","");
		map.put("defaultReferrerDepartId","");
		map.put("custName","");
		map.put("psptType","");
		map.put("recommendName","");
		map.put("recommendPhone","");
		map.put("psptAddrDisabled","");
		map.put("colorCode","");
		map.put("colorName","");
		map.put("oldColorCode","");
		map.put("monthChargeCode","");
		map.put("monthChargeName","");
		map.put("numCardTypeCode","");
		map.put("numCardTypeName","");
		map.put("PhoneCode","");
		map.put("phoneName","");
		map.put("flowPackageCode","");
		map.put("flowPackageName","");
		map.put("voicePackageCode","");
		map.put("voicePackageName","");
		map.put("shortMsgPackageCode","");
		map.put("shortMsgPackageName","");
		map.put("addServicePackageCode","");
		map.put("addServicePackageName","");
		map.put("numCode","");
		map.put("oldNum","");
		map.put("optionalPkgCode","");
		map.put("optionalPkgDesc","");
		map.put("payState","");
		map.put("numGroup","");
		map.put("shareFlag","");
		map.put("numberArray","");
		map.put("cardtypeArray","");
		map.put("numberArrayCopy[]","");
		map.put("directArr","");
		map.put("idleArr","");
		map.put("halfYearFlow","");
		map.put("netFlag","");
		map.put("recommendFlag","");
		map.put("attrFlag","");
		map.put("colorFlag","");
		map.put("monthFlag","");
		map.put("numCardTypeFlag","");
		map.put("phoneTypeFlag","");
		map.put("flowPackageFlag","");
		map.put("locflowPackageFlag","");
		map.put("shrflowPackageFlag","");
		map.put("voicePackageFlag","");
		map.put("locvoicePackageFlag","");
		map.put("shrvoicePackageFlag","");
		map.put("shortMsgPackageFlag","");
		map.put("locshortMsgPackageFlag","");
		map.put("shrshortMsgPackageFlag","");
		map.put("addServicePackageFlag","");
		map.put("locaddServicePackageFlag","");
		map.put("shraddServicePackageFlag","");
		map.put("numFlag","");
		map.put("optionalPkgFlag","");
		map.put("CERT_CHECK_RSP_CODE","200");
		map.put("CERT_CHECK_RSP_DETAIL","身份证校验成功");
		map.put("optProductFlag","0");
		Document doc = Jsoup.parse(context);
		if(doc!=null){
			Elements e = doc.getElementsByTag("script");
			if(e!=null && e.size()>0){
				/*用來封裝要保存的参数*/
				for (Element element : e) {
					/*取得JS变量数组*/
					String[] data = element.data().toString().split("goodsInfo.");
					/*取得单个JS变量*/
					for(String variable : data){
						/*过滤variable为空的数据*/
						if(variable.contains("=")){
							String[]  kvp = variable.split("=");
							String[]  val = kvp[1].split(";");
							/*取得JS变量存入map*/
							String key = kvp[0].trim();
							if(StringUtils.isNotBlank(key)){
								if(map.containsKey(key)) {
									//去除单双引号
									String value = val[0].trim();
									//获取值是否是json格式
									if(StringUtils.isNotBlank(value) && !CrawlerUtils.isJson(value)){
										value = value.replaceAll("'", "").replace("\"", "");
									}
									map.put(key, value);
								}
							}
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
//		File file = new File("E","\\SVN\\ZTEsoft\\doc\\浙江\\爬虫抓取信息\\自动开户详情\\订单处理详情.html");
//		Map<String, String> tempGoodsInfo = new HashMap<String, String>();
//		logger.info(tempGoodsInfo);
		
		String str="{\"retInfo\":{\"retFlag\":true}}";
		if(str.indexOf("\"retFlag\":true")>0){
			logger.info("===========");
		}else{
			logger.info("---------"+str.indexOf("retFlag:true"));
		}
	}
	public Map test(){
		Map map= new HashMap();
				map.put("orderId","3517012049057591");
				map.put("goodsInstId","3517012036071698");
				map.put("tmplId","60000019");
				map.put("goodsId","361405139289");
				map.put("merchantId","3600000");
				map.put("custId","111111155787841");
				map.put("proKeyMode","1");
				map.put("numProcId","111111155787841");
				map.put("psptNo","37152219850914053X");
				//map.put("psptAddr","杭州市江干区清泰小区４６幢２单元４０１室");
				//map.put("refErrerCode","3601605755");
				//map.put("refErrerName","www.10010.com"); 
				//map.put("recomDepartId","36a9577");
				//map.put("channelName","www.10010.com"); 
				map.put("referrerMode","1");
//				map.put("defaultReferrerCode","");
//				map.put("defaultReferrerName","");
//				map.put("defaultChannelName","");
//				map.put("defaultReferrerNumber","");
//				map.put("defaultReferrerDepartId","");
				map.put("custName","任全康");
				map.put("psptType","02");
				map.put("recommendName","");
				map.put("recommendPhone","");
				map.put("psptAddrDisabled","");
				map.put("colorCode","");
				map.put("colorName","");
				map.put("oldColorCode","");
				map.put("monthChargeCode","A000011V000002");
				map.put("monthChargeName","套餐减半");
				map.put("numCardTypeCode","A000065V000003");
				map.put("numCardTypeName","nano卡");
				map.put("PhoneCode","");
				map.put("phoneName","");
				map.put("flowPackageCode","5501000");
				map.put("flowPackageName","10元包100MB");
				map.put("voicePackageCode","");
				map.put("voicePackageName","");
				map.put("shortMsgPackageCode","");
				map.put("shortMsgPackageName","");
				map.put("addServicePackageCode","");
				map.put("addServicePackageName","");
				map.put("numCode","18668226369");
				map.put("oldNum","18668226369");
				map.put("optionalPkgCode","");
				map.put("optionalPkgDesc","无");
				map.put("payState","1");
				map.put("numGroup","");
				map.put("shareFlag","1");
				map.put("numberArray","[18668226369,null]");
				map.put("cardtypeArray","[null,null]");
				map.put("numberArrayCopy[]","18668226369");
				map.put("directArr","[]");
				map.put("idleArr","[]");
				map.put("halfYearFlow","null");
				map.put("netFlag","0");
				map.put("recommendFlag","0");
				map.put("attrFlag","0");
				map.put("colorFlag","0");
				map.put("monthFlag","0");
				map.put("numCardTypeFlag","0");
				map.put("phoneTypeFlag","0");
				map.put("flowPackageFlag","0");
				map.put("locflowPackageFlag","0");
				map.put("shrflowPackageFlag","0");
				map.put("voicePackageFlag","0");
				map.put("locvoicePackageFlag","0");
				map.put("shrvoicePackageFlag","0");
				map.put("shortMsgPackageFlag","0");
				map.put("locshortMsgPackageFlag","0");
				map.put("shrshortMsgPackageFlag","0");
				map.put("addServicePackageFlag","0");
				map.put("locaddServicePackageFlag","0");
				map.put("shraddServicePackageFlag","0");
				map.put("numFlag","0");
				map.put("optionalPkgFlag","0");
				map.put("CERT_CHECK_RSP_CODE","200");
				map.put("CERT_CHECK_RSP_DETAIL","身份证校验成功");
				map.put("optProductFlag","0");
				return map;
	}

	@Override
	public BindCustInfo2OrderResp bindCustInfo2Order(BindCustInfo2OrderReq req) {
		BindCustInfo2OrderResp resp = new BindCustInfo2OrderResp();
		ZBSystemClient client =init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		try {
			/**订单在手工开户列表是否存在*/
			String result = this.queryManualOpenAccountListInf(req.getOrderNo());
			if(StringUtils.indexOf(result, "订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户列表未查询到订单");
				return  resp;
			}
			/**检查订单状态是否正常*/
			resp = (BindCustInfo2OrderResp) this.checkManualOpenAccountState(req.getOrderId(), resp);
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				return resp;
			}
			/**订单是否存在*/
			String pageHtml = this.findManualOpenAccountDetailInf(req.getOrderId());
//			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
//			String pageHtml =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL));//订单开户详情
			if(StringUtils.indexOf(pageHtml, "当前订单["+req.getOrderNo()+"]已经进入开户处理")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户详情界面未查询到订单");
				return resp;
			}
			//客户信息与订单绑定
			String url = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_BIND_CUSTINFO_TO_ORDER) + "/" + req.getOrderId();
			result = client.get(url);
			JSONObject respJSON = JSONObject.fromObject(result);
			
			if(respJSON!=null && respJSON.containsKey("success")){
				String respCode = respJSON.getString("success");
				if(StringUtils.isNotBlank(respCode) && "true".equals(respCode)){
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("客户信息和订单绑定成功");
				}else{
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("客户信息和订单绑定失败");
				}
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("客户信息和订单绑定失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("客户信息和订单信息绑定失败："+e.getMessage());
		} 
		return resp;
	}

	@Override
	public OpenAccountSubmitOrderResp openAccountSubmitOrder(
			OpenAccountSubmitOrderReq req) {
		OpenAccountSubmitOrderResp resp = new OpenAccountSubmitOrderResp();
		ZBSystemClient client =init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}

		try {
			/**订单在手工开户列表是否存在*/
			String result = this.queryManualOpenAccountListInf(req.getOrderNo());
			if(StringUtils.indexOf(result, "订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户列表未查询到订单");
				return  resp;
			}
			/**检查订单状态是否正常*/
			resp = (OpenAccountSubmitOrderResp) this.checkManualOpenAccountState(req.getOrderId(), resp);
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				return resp;
			}
			
			/**订单是否存在*/
			String pageHtml = this.findManualOpenAccountDetailInf(req.getOrderId());
//			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
//			String pageHtml =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL));//订单开户详情
			if(StringUtils.indexOf(pageHtml, "当前订单["+req.getOrderNo()+"]已经进入开户处理")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户详情界面未查询到订单");
				return resp;
			}
			/**解析订单手工提交所需要的参数*/
			Map<String, String> pageParams = new HashMap<String, String>();
			analysizeOrderDetail(pageHtml, pageParams);
			/**封装请求参数*/
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("orderId", req.getOrderId()));
			postParams.add(new BasicNameValuePair("tmplId", pageParams.get("tmplId")));
			postParams.add(new BasicNameValuePair("goodsInstId", pageParams.get("goodsInstId")));
			postParams.add(new BasicNameValuePair("certType", pageParams.get("certType")));
			postParams.add(new BasicNameValuePair("certNum", pageParams.get("certNum")));
			postParams.add(new BasicNameValuePair("manualImeiCode", pageParams.get("manualImeiCode")));
			postParams.add(new BasicNameValuePair("manualNetCardNum", pageParams.get("manualNetCardNum")));
			postParams.add(new BasicNameValuePair("manualUsimCardNum", req.getManualUsimCardNum()));
			postParams.add(new BasicNameValuePair("manualOrderNo", req.getManualOrderNo()));
			postParams.add(new BasicNameValuePair("goodsType", pageParams.get("goodsType")));
			postParams.add(new BasicNameValuePair("isCardChange", pageParams.get("isCardChange")));
			/**发起请求*/
			result= client.post(postParams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_SUBMIT_OIRDER));
			/**解析结果*/
			if(result.indexOf("\"retFlag\":true")>0){
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("订单补录提交成功");
			}else{
				JSONObject respJSON = JSONObject.fromObject(result);
				if(respJSON!=null && respJSON.containsKey("RespCode")){
					String respCode = respJSON.getString("RespCode");
					if(StringUtils.isNotBlank(respCode) && "1".equals(respCode)){
						resp.setError_code(ConstsCore.ERROR_SUCC);
						resp.setError_msg("订单补录提交成功");
					}else{
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg("订单补录提交失败");
					}
				}else{
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单补录提交失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单开户信息补录失败："+e.getMessage());
		} 
		return resp;
	}
	/**
	 * 检查手工开户状态
	 * @param orderId
	 * @param resp
	 * @return
	 * @throws BusinessException 
	 * @throws SystemException 
	 */
	private ZteResponse checkManualOpenAccountState(String orderId, ZteResponse resp) throws SystemException, BusinessException{
		String result = this.manualOpenAccountCheckStateInf(orderId);
		JSONObject respJson = JSONObject.fromObject(result);
		respJson = respJson.getJSONObject("retInfo");
		String state=respJson.getString("ORDER_STATE");
		if ( !"C0".equals(state) &&!"C1".equals(state) &&!"CA".equals(state)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单状态异常");
		}else{
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("订单状态正常");
		}
		return resp;
	}
	
	private String findManualOpenAccountDetailInf(String orderId) throws SystemException, BusinessException{
		ZBSystemClient client =init();
		List params = new ArrayList();
		params.add(new BasicNameValuePair("orderId",orderId));
		String pageHtml = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL));
		
		return pageHtml;
	}
	
	/**
	 * 订单开户手工提交，从界面中获取所需要的参数
	 * @param pageHtml
	 * @param params
	 * @return
	 */
	private Map<String, String> analysizeOrderDetail(String pageHtml, Map<String, String> params){
		/**从js中获取所需的参数*/
		params.put("isCardChange", "");//是否是老用户便捷换卡订单 1:是  0:不是
		ZBSystemClient.getJsoupScriptContent(pageHtml, params);//通过Jsoup获取html中的js变量值
		/**从dom树中获取所需要的参数*/
		Document doc = Jsoup.parse(pageHtml);
		if(null != doc.getElementById("hiddenTmplID")){//商品模板ID
			params.put("tmplID", doc.getElementById("hiddenTmplID").val());
		}
		if(null != doc.getElementById("goodInstId")){//商品实例ID
			params.put("goodsInstId", doc.getElementById("goodInstId").val());
		}
		if(null != doc.getElementById("certType")){//证件类型
			params.put("certType", doc.getElementById("certType").val());
		}
		if(null != doc.getElementById("idCard")){//证件号码
			params.put("certNum", doc.getElementById("idCard").val());
		}
		if(null != doc.getElementById("manualImeiCode")){//终端串号
			params.put("manualImeiCode", doc.getElementById("idCard").val());
		}
		if(null != doc.getElementById("manualNetCardNum")){//上网卡号码，手机号码
			params.put("manualNetCardNum", doc.getElementById("manualNetCardNum").val());
		}
		if(null != doc.getElementById("hiddenGoodsType")){//开户商品类型(2G/3G/4G)
			params.put("goodsType", doc.getElementById("hiddenGoodsType").val());
		}
		return params;
	}

	@Override
	public ManualModifyBuyerInfoResp manualModifyBuyerInfo(
			ManualModifyBuyerInfoReq req) {
		ManualModifyBuyerInfoResp resp = new ManualModifyBuyerInfoResp();
		ZBSystemClient client =init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}

		try {
			/**订单在手工开户列表是否存在*/
			String result = this.queryManualOpenAccountListInf(req.getOrderNo());
			if(StringUtils.indexOf(result, "订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户列表未查询到订单");
				return  resp;
			}
			/**检查订单状态是否正常*/
			resp = (ManualModifyBuyerInfoResp) this.checkManualOpenAccountState(req.getOrderId(), resp);
			if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
				return resp;
			}
			
			/**订单是否存在*/
			String pageHtml = this.findManualOpenAccountDetailInf(req.getOrderId());
//			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
//			String pageHtml =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL));//订单开户详情
			if(StringUtils.indexOf(pageHtml, "当前订单["+req.getOrderNo()+"]已经进入开户处理")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("开户详情界面未查询到订单");
				return resp;
			}
			/**封装请求参数*/
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("orderId", req.getOrderId()));
			postParams.add(new BasicNameValuePair("receiverName", req.getReceiverName()));
			postParams.add(new BasicNameValuePair("mobilePhone", req.getMobilePhone()));
			postParams.add(new BasicNameValuePair("custRemark", req.getCustRemark()));
			/**发起请求*/
			result= client.post(postParams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_BUYER_INFO_MODIFY));
			/**解析结果*/
			JSONObject respJSON = JSONObject.fromObject(result);
			respJSON = respJSON.getJSONObject("respInfo");
			if(respJSON!=null && respJSON.containsKey("RespCode")){
				String respCode = respJSON.getString("RespCode");
				if(!"0".equals(respCode)){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单买家信息修改失败");
				}else{
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("订单买家信息修改成功");
				}
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单买家信息修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单买家信息修改失败："+e.getMessage());
		} 
		return resp;
	}
	
	/**
	 * 查询订单在手动开户列表中的页面信息(接口调用)
	 * @param orderNo
	 * @return
	 * @throws BusinessException 
	 * @throws SystemException 
	 */
	private String queryManualOpenAccountListInf(String orderNo) throws SystemException, BusinessException{
		List params = new ArrayList();
		params.add(new BasicNameValuePair("orderNo",orderNo));
		params.add(new BasicNameValuePair("page.webPager.action","refresh"));
		params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
		params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
		params.add(new BasicNameValuePair("pageSize","5"));
//		params.add(new BasicNameValuePair("kingCardType","A000170V000002"));
//		params.add(new BasicNameValuePair("userTag","2"));
//		params.add(new BasicNameValuePair("orderByTime","0"));
//		params.add(new BasicNameValuePair("delayOrderTag","1"));
		ZBSystemClient client =init();
		String result =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL_LIST));//查询开户列表
		
		return result;
	}
	
	/**
	 * 查询手工开户信息状态（接口调用）
	 * @param orderId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	private String manualOpenAccountCheckStateInf(String orderId) throws SystemException, BusinessException{
		ZBSystemClient client =init();
		List params = new ArrayList();
		params.add(new BasicNameValuePair("orderId",orderId));
		String result =client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.MANUAL_OPEN_ACCOUNT_DETAIL_CHECK_STATE));//订单状态检查
		
		return result;
	}
}
