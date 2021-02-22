package zte.net.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.req.ZbQueryOrderDetailReq;
import params.resp.ZbQueryOrderDetailResp;


import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.autoprocess.base.model.ZBLoginParam;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.utils.CrawlerSetting;

import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.ICustomerOrderInquiryService;
import zte.net.service.IStandardizationService;

public class CustomerOrderInquiryService implements ICustomerOrderInquiryService {
	private static Logger logger = Logger.getLogger(CustomerOrderInquiryService.class);
	private IStandardizationService standardizationService;
	
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
	
	public static void main(String[] args) {
		queryOrderDetail();
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
				 logger.info("=========要解析的文件"+filename+"不存在!");
			}
		return sb.toString();
	}
	
	public static ZbQueryOrderDetailResp queryOrderDetail() {
		//ZBSystemClient client =init();
		String stdResult = "";
		ZbQueryOrderDetailResp resp = new ZbQueryOrderDetailResp();
		resp.setError_code("1");
		//客服订单查询链接
		
		/*String detailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL);
		
		// 创建参数队列
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", orderId));//订单ID
		detailparams.add(new BasicNameValuePair("orderType", "1"));
		*/
		String detailOrder = null;
		try {
			detailOrder = getFileToString("订单详细信息6389458195");//client.post(detailparams, detailUrl);
		} catch (Exception e) {
			stdResult = "进入客服订单详情异常："+e.getMessage();
			e.printStackTrace();
		}
		//爬虫客服订单查询操作记录
		if(StringUtils.isNotBlank(detailOrder)){//爬虫获取的订单详情数据不为空
		    //getFile("allocation",oldFileName,orderId);
			Document detaildoc = Jsoup.parse(detailOrder);//解析详情数据
			
			Element stepAreaDiv = detaildoc.getElementsByClass("stepAreaDiv").get(0);
			String stepOnArea = "";//当前环节
			String stepOnAreaDate = "";//环节流转时间
			Elements stepArea = stepAreaDiv.getElementsByClass("stepOnArea");
			Elements stepCompleteArea = stepAreaDiv.getElementsByClass("stepCompleteArea");
			if(stepArea.size() != 0){//如果订单没走完则stepOnArea是当前停留环节
				stepOnArea = stepArea.get(0).getElementsByClass("stepAction").text();
				stepOnAreaDate = stepArea.get(0).getElementsByClass("stepAction").text();
				logger.info(stepOnArea);
			}else {//如果没有stepOnArea则说明该订单已经进行资料归档了
				stepOnArea = stepCompleteArea.get(stepCompleteArea.size()-1).getElementsByClass("stepAction").text();
				stepOnAreaDate = stepCompleteArea.get(stepCompleteArea.size()-1).getElementsByClass("stepDate").text();
			}
			resp.setStepOnArea(stepOnArea);
			resp.setStepOnAreaDate(stepOnAreaDate);
			
			Elements operationRecords = detaildoc.getElementsByClass("operationRecord");
			if(null != operationRecords && operationRecords.size() >0){
				List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				for (Element element : operationRecords) {
					Elements a= element.getElementsByClass("operationRecordTitle");
					Elements b= element.getElementsByClass("operationRecordBottom");
					if(element.getElementsByClass("operationRecordTitle").size() ==0){
						String operationTime = element.getElementsByClass("operationTime").get(0).text();
						String operationType = element.getElementsByClass("operationType").text();
						String operationInfo = element.getElementsByClass("operationInfo").get(0).text();
						String operator = element.getElementsByClass("operator").get(0).text();
						Map<String, String> map = new HashMap<String, String>();
						map.put("operationTime", operationTime);
						map.put("operationType", operationType);
						map.put("operationInfo", operationInfo);
						map.put("operator", operator);
						list.add(map);
					}
				}
				resp.setOperationRecord(list);
				resp.setError_code("0");
			}
			
		}
		return resp;
	}

	@Override
	public ZbQueryOrderDetailResp queryOrderDetail(ZbQueryOrderDetailReq req) {
		ZBSystemClient client =init();
		String stdResult = "";
		ZbQueryOrderDetailResp resp = new ZbQueryOrderDetailResp();
		resp.setError_code("1");
		//客服订单查询链接
		
		String detailUrl = CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.CUSTOMER_QUERY_ZB_ORDER_DETAIL_URL);
		
		// 创建参数队列
		List<NameValuePair> detailparams = new ArrayList<NameValuePair>();
		detailparams.add(new BasicNameValuePair("orderId", req.getOrderId()));//订单ID
		detailparams.add(new BasicNameValuePair("orderType", "1"));
		
		String detailOrder = null;
		try {
			detailOrder = client.post(detailparams, detailUrl);
		} catch (Exception e) {
			stdResult = "进入客服订单详情异常："+e.getMessage();
			e.printStackTrace();
		}
		//爬虫客服订单查询操作记录
		if(StringUtils.isNotBlank(detailOrder)){//爬虫获取的订单详情数据不为空	
			Document detaildoc = Jsoup.parse(detailOrder);//解析订单路由信息
			
			Element stepAreaDiv = detaildoc.getElementsByClass("stepAreaDiv").get(0);
			String stepOnArea = "";//当前环节
			String stepOnAreaDate = "";//环节流转时间
			String zbLastModifyTime = "";//最后操作时间
			String cancellationStatus = "";//退单状态
			String dealDesc = "";//退单原因
			Elements stepArea = stepAreaDiv.getElementsByClass("stepOnArea");
			Elements stepCompleteArea = stepAreaDiv.getElementsByClass("stepCompleteArea");
			if(stepArea.size() != 0){//如果订单没走完则stepOnArea是当前停留环节
				stepOnArea = stepArea.get(0).getElementsByClass("stepAction").text();
				stepOnAreaDate = stepArea.get(0).getElementsByClass("stepAction").text();
				logger.info(stepOnArea);
			}else {//如果没有stepOnArea则说明该订单已经归档完成了
				stepOnArea = "归档完成";
				//stepOnArea = stepCompleteArea.get(stepCompleteArea.size()-1).getElementsByClass("stepAction").text();
				//stepOnAreaDate = stepCompleteArea.get(stepCompleteArea.size()-1).getElementsByClass("stepDate").text();
			}
			resp.setStepOnArea(stepOnArea);
			resp.setStepOnAreaDate(stepOnAreaDate);
			
			Elements operationRecords = detaildoc.getElementsByClass("operationRecord");
			if(null != operationRecords && operationRecords.size() >0){
				List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				for(int i=1;i<list.size();i++){
					Element element = operationRecords.get(i);
					if(element.getElementsByClass("operationRecordTitle").size() ==0 ){
						String operationTime = element.getElementsByClass("operationTime").get(0).text();
						String operationType = element.getElementsByClass("operationType").text();
						String operationInfo = element.getElementsByClass("operationInfo").get(0).text();
						String operator = element.getElementsByClass("operator").get(0).text();
						Map<String, String> map = new HashMap<String, String>();
						map.put("operationTime", operationTime);
						map.put("operationType", operationType);
						map.put("operationInfo", operationInfo);
						map.put("operator", operator);
						list.add(map);
						
						if(i== list.size()-1){//最后一条记录，保存最后操作时间
							zbLastModifyTime = operationTime;
						}
						
						if(!StringUtils.isEmpty(operationInfo) && operationInfo.indexOf("申请退单") != -1){
							cancellationStatus = "申请退单";
							dealDesc = operationInfo.substring(operationInfo.indexOf("【"));
						}else if("退款申请".equals(operationType)){
							cancellationStatus = "退款申请";
						}else if("退单通过".equals(operationType)){
							cancellationStatus = "退单通过";
						}else if("退款通过".equals(operationType)){
							cancellationStatus = "退款通过";
						}
					}
					
				}
				resp.setZbLastModifyTime(zbLastModifyTime);
				resp.setOperationRecord(list);
				resp.setCancellationStatus(cancellationStatus);
				resp.setDealDesc(dealDesc);
				resp.setError_code("0");
			}
		}
		return resp;
	}

}