package zte.net.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.req.ZbOrderDistributeReq;
import params.req.ZbOrderVerifyReq;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.IOrderVerifySectionService;

import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;

import consts.ConstsCore;

public class OrderVerifySectionService implements IOrderVerifySectionService{
	private static Logger logger = Logger.getLogger(OrderVerifySectionService.class);
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
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
			}
			 client = ClientPool.get(username);//获取总商登录客户端对象
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【总部系统-登陆系统异常】");
		}
		return client;
	}

	@Override
	public ZbOrderVerifyResp orderVerify(ZbOrderVerifyReq verifyReq) {
		// TODO Auto-generated method stub
		ZbOrderVerifyResp resp = new ZbOrderVerifyResp();
		try{
			ZBSystemClient client =init();
			if(null==client){
				resp.setError_code(ConstsCore.ERROR_BUSI_MID);
				resp.setError_msg("未登陆总商系统，请先登录！");
				return resp;
			}
			String orderId = verifyReq.getOrderId();
			String orderNo = verifyReq.getOrderNo();
			//审单操作按钮链接
			String reviewBtnUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_URL)+orderId+"?orderId="+orderId;
			String reviewBtnResult = client.post(new ArrayList<NameValuePair>(),reviewBtnUrl);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("deliverTypeCode", "");
			paramMap.put("templateId", "");
			paramMap.put("isGroupCust", "");
			paramMap.put("exception", "");//获取总商返回的异常信息
			paramMap.put("isValidatedUser", "");
			paramMap.put("realNameShowPic", "");
			paramMap.put("isRealNameSucc", "");
			paramMap.put("orgPostInfo", "");//获取订单配送信息
			String isOffLine="";
			
			if(StringUtil.isEmpty(reviewBtnResult) || reviewBtnResult.indexOf("value=\"审核通过\"") == -1){
				logger.info("=====["+orderId+"]审核锁定失败");
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("审核锁定失败");
				return resp;
			}else{
				ZBSystemClient.getJsoupScriptContent(reviewBtnResult, paramMap);//通过Jsoup获取html中的js变量值
			}
			//订单审核校验
			Map<String,String> exceptionMap =new HashMap<String,String>();
			if(StringUtils.isNotBlank(paramMap.get("exception"))){
				String exception= paramMap.get("exception").toString();
				exception = exception.replace(":", ":\'").replace(",", "\',").replace("}", "\'}").replace("}\',{", "},{");
				logger.info("订单"+orderNo+"审核总商返回审核结果"+exception);
				JSONArray array=	JSONArray.fromObject(exception) ;
				for(int i=0;i<array.size();i++){
					JSONObject json =(JSONObject) array.get(i);
					String briefly = new String(json.getString("briefly").getBytes());
					String desc = new String(json.getString("desc").getBytes());
					String msg=briefly.equals(desc)?briefly:briefly+desc;
					if ("AUTO_VERIFY_POST".equals(json.get("typeCode")) && ("002".equals(json.get("typeValue")) && "2003".equals(json.get("vrstTipType"))) || ("004".equals(json.get("typeValue")) && "2009".equals(json.get("vrstTipType")))) {
						exceptionMap.put("postBlackError",  exceptionMap.get("postBlackError")==null?msg:exceptionMap.get("postBlackError")+msg);
	                    //配送信息错误
	                } else if ("AUTO_VERIFY_POST".equals(json.get("typeCode"))) {
	                	exceptionMap.put("postInfoError", exceptionMap.get("postInfoError")==null?msg:exceptionMap.get("postInfoError")+msg);
	                    //入网信息
	                } else if ("AUTO_VERIFY_NETIN".equals(json.get("typeCode"))) {
	                	exceptionMap.put("netInError", exceptionMap.get("netInError")==null?msg:exceptionMap.get("netInError")+msg);
	                    //商品信息
	                } else if ("AUTO_VERIFY_GOODS".equals(json.get("typeCode"))) {
	                	exceptionMap.put("goodsError",  exceptionMap.get("goodsError")==null?msg:exceptionMap.get("goodsError")+msg);
	                    //证件照
	                } else if ("AUTO_VERIFY_UPLOAD".equals(json.get("typeCode")) && "0".equals(paramMap.get("isValidatedUser"))) {
	                    if(!"1".equals(paramMap.get("realNameShowPic")) && !"1".equals(paramMap.get("isRealNameSucc"))){
	                    	exceptionMap.put("netInError", exceptionMap.get("netInError")==null?msg:exceptionMap.get("netInError")+msg);
	                    }
	                }
				}
			}
			if(StringUtils.isNotBlank(exceptionMap.get("postInfoError"))){
				resp.setError_code(ConstsCore.ERROR_FAIL);				resp.setExceptionMap(exceptionMap);
				return resp;
			}
			 //拦截自提订单
			if(StringUtils.isNotBlank(exceptionMap.get("netInError"))){
				isOffLine="0";
				/*  在界面上未找到goodsList-idfy-updated-tip
				  var errorMsg = $.trim($("#goodsList-idfy-updated-tip span").text());
			      if(errorMsg == "未上传证件照") {
			        $.alert("未上传证件照，审核无法通过");
			        return;
			      }
			      if(errorMsg == "证件照未校验") {
			        $.alert("证件照未校验，审核无法通过");
			        return;
			      }
			      if(errorMsg == "证件照校验未通过") {
			        $.alert("证件校验未通过，审核无法通过");
			        return;
			      } 
			        */
				JSONObject postInfo = JSONObject.fromObject(paramMap.get("orgPostInfo")); 
				if(null!=postInfo){
					String deliverTypeCode = postInfo.get("deliverTypeCode")==null ?"":postInfo.get("deliverTypeCode").toString();
					Document doc = Jsoup.parse(reviewBtnResult);
					String wfId = doc.getElementById("wfIdSpan").text();
					
					if("10".equals(deliverTypeCode)||"206".equals(wfId)){
						if(doc.select(".error-psptcontent").attr("style").indexOf("block")>0&&doc.select(".error-psptaddr").attr("style").indexOf("block")>0){
							exceptionMap.put("netInAddrError", "入网资料地址存在错误");
							resp.setError_code(ConstsCore.ERROR_FAIL);
							resp.setExceptionMap(exceptionMap);
							return resp;
						}
						isOffLine="1";
					}
					//不拦截营业厅线下审核订单 国政通信息报错 （王卡营业厅自提订单，上门激活订单）
			        if(!("10".equals(deliverTypeCode)|| "206".equals(wfId))){
			        	exceptionMap.put("netInError", "入网信息存在错误");
			        	resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setExceptionMap(exceptionMap);
						return resp;
			        }
				}
			}
			if(!exceptionMap.isEmpty()){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setExceptionMap(exceptionMap);
				return resp;
			}
			
			//订单审核通过按钮
			String orderReviewUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_AUDIT_ZB_ORDER_PASS_URL)+orderId;
			
			// 创建参数队列
    		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    		formparams.add(new BasicNameValuePair("orderId", orderId));
    		formparams.add(new BasicNameValuePair("delvTypeCode", paramMap.get("deliverTypeCode")));
    		formparams.add(new BasicNameValuePair("templateId", paramMap.get("templateId")));
    		formparams.add(new BasicNameValuePair("custRemark", "云订单审核"));
    		formparams.add(new BasicNameValuePair("isGroupCust", paramMap.get("isGroupCust")));
    		formparams.add(new BasicNameValuePair("isOffLine", isOffLine));
			if(client!=null){
				String responseStr = client.post(formparams, orderReviewUrl);//执行订单审核操作
				if(StringUtils.isNotBlank(responseStr) && CrawlerUtils.isJson(responseStr)){
					JSONObject respJSON = JSONObject.fromObject(responseStr);
					if (responseStr == null || respJSON.getBoolean("success") != true) {
						logger.info("=====["+orderId+"]订单审核失败========");
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg(respJSON.toString());;
					}else{
						logger.info("=====["+orderId+"]订单审核成功========");
						resp.setError_code(ConstsCore.ERROR_SUCC);
					}
				}else{
					logger.info("=====["+orderId+"]订单审核失败========审核结果"+responseStr);					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("订单审核失败");
				}
//				
//				
//				if(CrawlerSetting.ORDER_REVIEW_COUNT > 0 || CrawlerSetting.ORDER_REVIEW_COUNT == -1){
//					String responseStr = client.post(formparams, orderReviewUrl);//执行订单审核操作
//					logger.info("==========审核操作按钮返回内容："+responseStr);
//					if("N".equals(CrawlerSetting.IS_OUT_ID_CHECK)){//如果不通过导入订单号来做审核，而是审核所有订单，则不要做订单审核状态更新
//						//返回内容不为空并且是json格式
//						if(StringUtils.isNotBlank(responseStr) && CrawlerUtils.isJson(responseStr)){
//							JSONObject respJSON = JSONObject.fromObject(responseStr);
//							if (responseStr == null || respJSON.getBoolean("success") != true) {
//								logger.info("=====["+orderId+"]订单审核失败========");
//							}else{
//								logger.info("=====["+orderId+"]订单审核成功========");
//								if(CrawlerSetting.ORDER_REVIEW_COUNT > 0){
//									CrawlerSetting.ORDER_REVIEW_COUNT --;//记录处理数量
//								}
//							}
//						}
//					}else{//按导入订单号来处理，则需更新对应订单号的审核状态
//						ZbAuditStatusUpdateReq req = new ZbAuditStatusUpdateReq();
//						req.setOut_tid(orderNo);
//						req.setZb_id(orderId);
//						req.setAudit_type("auto");
//						//返回内容不为空并且是json格式
//						if(StringUtils.isNotBlank(responseStr) && CrawlerUtils.isJson(responseStr)){
//							JSONObject respJSON = JSONObject.fromObject(responseStr);
//							if (responseStr == null || respJSON.getBoolean("success") != true) {
//								//String errorMsg = respJSON.getString("errorMsg");//失败原因
//						        logger.info("=====["+orderId+"]订单审核失败=======");
//						        //审核失败(后续调用API做状态更新)
//						        req.setAudit_status("2");//2 审核失败
//						    }else {
//						        logger.info("=====["+orderId+"]订单审核成功=======");
//						        //审核成功(后续调用API做状态更新)
//						        req.setAudit_status("1");//1 审核成功
//						        if(CrawlerSetting.ORDER_REVIEW_COUNT > 0){
//									CrawlerSetting.ORDER_REVIEW_COUNT --;//记录处理数量
//								}
//						    }
//						}else{
//							 logger.info("=====["+orderId+"]订单审核失败，审核返回内容为空或者返回内容不是json格式");
//							 req.setAudit_status("2");//2 审核失败
//						}
//						ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
//						ZbAuditStatusUpdateResp updateResp = zteclient.execute(req, ZbAuditStatusUpdateResp.class);
//						logger.info("=====订单批量审核回填API："+updateResp.getError_code());
//					}
//				}else {//写入配置文件，记录处理完毕
//					if(CrawlerSetting.ORDER_REVIEW_STATUS == false){//未写入
//						CrawlerSetting.ORDER_REVIEW_STATUS = true;//标记写入
//						Map<String,String> map = new HashMap<String, String>();
//						map.put("order_review.count", String.valueOf(CrawlerSetting.ORDER_REVIEW_COUNT));
//						CrawlerSetting.updateProperties(map);
//					}
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单审核异常"+e.getMessage());
		}
		return resp;
	}

	@Override
	public ZbOrderDistributeResp orderDistribute(ZbOrderDistributeReq req) {
		// TODO Auto-generated method stub
		ZbOrderDistributeResp resp= new ZbOrderDistributeResp();
		String responseStr="";
		try{
			String orderNo = req.getOrderNo();
			String orderId = req.getOrderId();
			ZBSystemClient client =init();
			if(null==client){
				resp.setError_code(ConstsCore.ERROR_BUSI_MID);
				resp.setError_msg("未登陆总商系统，请先登录！");
				return resp;
			}
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("selProvinceCode",LocalCrawlerUtil.ZB_PROVINCE_CODE));
    		formparams.add(new BasicNameValuePair("pageSize", "100"));
    		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize", "100"));
    		formparams.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize", "1000"));
    		formparams.add(new BasicNameValuePair("page.webPager.currentPage", "1"));
    		formparams.add(new BasicNameValuePair("orderNo", orderNo));
    		logger.info("[查询总商订单分配页面返回报文开始]==="+orderNo+"==================================================================================================================================");
    		responseStr = client.post(formparams, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.QUERY_ALLOCATION_ZB_ORDER_URL));
    		logger.info("[查询总商订单分配页面返回报文结束]==="+orderNo+"==================================================================================================================================");

	        Document doc =  Jsoup.parse(responseStr);
	        
	        Elements elements = doc.select("table[class=singleOrder]");
			if(elements!=null && elements.size()>0){
				logger.info("================解析文件获取的订单号大小："+elements.size());
				String orderIdList = "";
				String custIdList = "";
				String custIpList = "";
				String cityCodes = "";
				String provinceCodes = "";
				for(Element element:elements){
					 String custId = "";//买家标识 
					 String custIp = "";//客户IP
					 String cityCode = "";
					 String provinceCode = "";
					 
					 Elements classTypeElements = element.select("a[href].shortBlueBtn");
					 if(classTypeElements!=null && classTypeElements.size()>0){//分配按钮为蓝色可以点击

						 Element checkElement = element.getElementById("checkbox");
						 if(checkElement!=null){
							 orderId = checkElement.val();
						 }
						 
						 if(StringUtils.isNotBlank(orderId)){
							 Element custIdElement = element.getElementById("custId_"+orderId);
							 if(custIdElement!=null){
								 custId = custIdElement.val();
							 }
							 Element custIpElement = element.getElementById("custIp_"+orderId);
							 if(custIpElement!=null){
								 custIp = custIpElement.val();
							 }
							 
							 Element cityCodeElement = element.getElementById("cityCode_"+orderId);
							 if(cityCodeElement!=null){
								 cityCode = cityCodeElement.val();
							 }
							 Element provinceCodeElement = element.getElementById("provinceCode_"+orderId);
							 if(provinceCodeElement!=null){
								 provinceCode = provinceCodeElement.val();
							 }
						 }
						 orderIdList = orderIdList+orderId+",";
						 custIdList = custIdList+custId+",";
						 custIpList = custIpList+custIp+",";
						 cityCodes = cityCodes+cityCode+",";
						 provinceCodes = provinceCodes+provinceCode+",";
					}
				}
//				//==============================审核前需先获取员工列表=====================
				String batchStaffUrl = "http://admin.mall.10010.com/Odm/OrderAllocation/queryNewBatchAllocationStaffs";
				//String staffUrl = "http://admin.mall.10010.com/Odm/OrderAllocation/queryNewBatchAllocationStaffs";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String staffTypeId ="";
				Element staffTypeIdElement =  doc.getElementById("staffTypeId");
				if(staffTypeIdElement!=null){
					staffTypeId = staffTypeIdElement.val();
				}
				String staffCityId = "";
				Element staffCityIdElement =  doc.getElementById("staffCityId");
				if(staffCityIdElement!=null){
					staffCityId = staffCityIdElement.val();
				}
				String staffAreaId = "";
				Element staffAreaIdElement =  doc.getElementById("staffAreaId");
				if(staffAreaIdElement!=null){
					staffCityId = staffAreaIdElement.val();
				}
				String staffGroupId = "";
				Element staffGroupIdElement =  doc.getElementById("staffGroupId");
				if(staffGroupIdElement!=null){
					staffGroupId = staffGroupIdElement.val();
				}
				String goSearch = "";
				Element goSearchElement =  doc.getElementById("goSearch");
				if(goSearchElement!=null){
					goSearch = goSearchElement.val();
				}
				
				params.add(new BasicNameValuePair("staffTypeID", staffTypeId));
				params.add(new BasicNameValuePair("staffProvinceID", staffTypeId));
				params.add(new BasicNameValuePair("staffCityID", staffCityId));
				params.add(new BasicNameValuePair("staffCountyID", staffAreaId));
				params.add(new BasicNameValuePair("staffGroupID", staffGroupId));
				params.add(new BasicNameValuePair("cityCodes", cityCodes));
				params.add(new BasicNameValuePair("provinceCodes", provinceCodes));
				params.add(new BasicNameValuePair("goSearch", goSearch));
				params.add(new BasicNameValuePair("orderIDList", orderIdList));

				//==============================获取员工列表结束，继续执行批量审核=====================
				logger.info("[订单批量分配第一步返回报文开始]==="+orderNo+"==================================================================================================================================");
				responseStr = client.post(params, batchStaffUrl);
		        logger.info("[订单批量分配第一步返回报文结束]==="+orderNo+"==================================================================================================================================");

				JSONObject jsonObj = JSONObject.fromObject(responseStr);//结果不做处理
				if(StringUtils.isNotEmpty(orderIdList)){
					
					String allocationUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.BATCH_ALLOCATION_ZB_ORDER_URL);// post json
					formparams = new ArrayList<NameValuePair>();
					formparams.add(new BasicNameValuePair("staffCode", LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE));//固定分配工号
					formparams.add(new BasicNameValuePair("staffID", LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_ID));//工号id
					formparams.add(new BasicNameValuePair("staffName", LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_NAME));//工号名称
					
					formparams.add(new BasicNameValuePair("orderID", ""));//单个分配传orderID
					formparams.add(new BasicNameValuePair("orderIDList", orderIdList));//批量分配传orderIDList
					formparams.add(new BasicNameValuePair("custIDList", custIdList));
					formparams.add(new BasicNameValuePair("custIPList", custIpList));
					try {
						logger.info("[订单分配返回报文开始]==="+orderNo+"==================================================================================================================================");
					    responseStr = client.post(formparams, allocationUrl);
				        logger.info("[订单分配查询返回报文开始]==="+orderNo+"==================================================================================================================================");

						JSONObject respJson = JSONObject.fromObject(responseStr);
						JSONObject respInfo = respJson.getJSONObject("respInfo");
						int respCode = respInfo.getInt("RespCode");
						if(respCode==0){
							logger.info("=====["+orderIdList+"]订单分配失败"+responseStr);
							resp.setError_code(ConstsCore.ERROR_FAIL);
							resp.setError_msg("订单分配失败");
						}else if(respCode == -1){
							logger.info("=====["+orderIdList+"]订单分配失败，订单状态已变更"+respCode);
							resp.setError_code(ConstsCore.ERROR_FAIL);
							resp.setError_msg("订单分配失败，订单状态已变更");
						}else{
							logger.info("=====["+orderIdList+"]订单分配成功信息："+respInfo);
							resp.setError_code(ConstsCore.ERROR_SUCC);
						}
					} catch (Exception e){
						e.printStackTrace();
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg("订单分配失败");
					}
				}else{
					resp.setError_code(ConstsCore.ERROR_BUSI_MID);
					resp.setError_msg("分配按钮为灰色，请稍后再试");
				}
				
			}else{
				resp.setError_code(ConstsCore.ERROR_BUSI_MID);
				resp.setError_msg("在分配界面未查询到订单");
			}
		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("订单分配查询失败");
			logger.info("订单分配异常"+responseStr);
			e.printStackTrace();
			
		}
		return resp;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		//String str ="\u60A8\u8F93\u5165\u7684\u8EAB\u4EFD\u8BC1\u53F7\u7801\u4E0D\u5B58\u5728\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165";
		String str =new String("[{briefly:\u672A\u4E0A\u4F20\u8BC1\u4EF6\u7167,desc:\u672A\u4E0A\u4F20\u8BC1\u4EF6\u7167,orderId:null,typeCode:AUTO_VERIFY_UPLOAD,typeValue:401,vrstBriefly:null,vrstDesc:null,vrstId:null,vrstState:null,vrstTipType:1030,vrstTypeCode:null,vrstTypeValue:null},{briefly:\u672A\u4E0A\u4F20\u8BC1\u4EF6\u7167,desc:\u672A\u4E0A\u4F20\u8BC1\u4EF6\u7167,orderId:null,typeCode:AUTO_VERIFY_UPLOAD,typeValue:401,vrstBriefly:null,vrstDesc:null,vrstId:null,vrstState:null,vrstTipType:1030,vrstTypeCode:null,vrstTypeValue:null}]".getBytes(),"utf-8");
		str = str.replace(":", ":\'").replace(",", "\',").replace("}", "\'}").replace("}\',{", "},{");
		logger.info(str);
		logger.info(JSONArray.fromObject(str));
	}
}
