package zte.net.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.ZbBackfillLogisticsReq;
import params.req.ZbOrderDeliveryCodeQueryReq;
import params.req.ZbOrderDeliveryReq;
import params.req.ZbOrderPrintReq;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.IOrderDeliverySectionService;
import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.utils.CrawlerSetting;

import consts.ConstsCore;
/**
 * 总部发货环节处理流程
 * @author ricky
 *
 */
public class OrderDeliverySectionService implements IOrderDeliverySectionService{
	private static Logger logger = Logger.getLogger(OrderDeliverySectionService.class);
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
	/**
	 * 总部订单发货
	 * @param req
	 * @return
	 */
	@Override
	public ZteResponse zbOrderDelivery(ZbOrderDeliveryReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		ZBSystemClient client= init(); 
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		try{
			//步骤一查询发货环节订单
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
			params.add(new BasicNameValuePair("page.webPager.action","refresh"));
			params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
			params.add(new BasicNameValuePair("pageSize","5"));
			String result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_QUERY));
			if(result.indexOf("订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("发货处理列表未查询到订单");
				return  resp;
			}
			//步骤二 进入发货详情界面
			//先解析列表界面的orderType字段
			Document doc = Jsoup.parse(result);
			Elements element =doc.select("#orderDetailForm input[name=orderType]");
			String orderType =element.val();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
			params.add(new BasicNameValuePair("orderType",orderType));
			result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_DETAIL));
			if(result.indexOf(req.getOrderNo())<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("发货详情未查询到订单");
				return  resp;
			}
			Map<String ,Object> map = new HashMap<String,Object>();
			params = new ArrayList<NameValuePair>();
			if("2".equals(req.getLogisticType())){//第三方物流
				params.add(new BasicNameValuePair("companyId", req.getCompanyId()));
				params.add(new BasicNameValuePair("companyName", req.getCompanyName()));
				params.add(new BasicNameValuePair("delivery_type_code", "01"));// 物流方式为快递
				params.add(new BasicNameValuePair("lgtsOrder", req.getLgtsOrder()));
				params.add(new BasicNameValuePair("lgtsRemark", req.getLgtsRemark()));
				if("1".equals(req.getIsNeedLgtsRtn())){
					params.add(new BasicNameValuePair("lgtsRtnOrder",  req.getLgtsRtnOrder()));
					params.add(new BasicNameValuePair("lgtsRtnRemark",  req.getLgtsRtnOrderDesc()));
				}else{
					params.add(new BasicNameValuePair("lgtsRtnOrder",  ""));
					params.add(new BasicNameValuePair("lgtsRtnRemark",  ""));
				}
				params.add(new BasicNameValuePair("billType",  req.getBillType()));
			}else if("3".equals(req.getLogisticType())){//客户自提
				params.add(new BasicNameValuePair("selffetchId",  ""));
				params.add(new BasicNameValuePair("selffetchName", ""));
				params.add(new BasicNameValuePair("delivery_type_code",  "03"));
				params.add(new BasicNameValuePair("src_delivery_type_code", "03" ));//总商用于后序判断，默认不改变
			}else if("1".equals(req.getLogisticType())){//自行配送
				params.add(new BasicNameValuePair("staffId",  req.getStaffId()));
				params.add(new BasicNameValuePair("delivery_type_code", "01"));// 物流方式为快递
			}
			params.add(new BasicNameValuePair("orderId", req.getOrderId()));
			params.add(new BasicNameValuePair("logisticType", req.getLogisticType()));
			params.add(new BasicNameValuePair("callPlatform", "1"));// 该标示是否忽略第三方发货通知接口调用失败 1 ：不忽略 空值 不忽略
			params.add(new BasicNameValuePair("iccid",  req.getIccid()));
			params.add(new BasicNameValuePair("resourceCode",  req.getResourceCode()));
			
			String param = JsonUtil.toJson(map);
			result = client.post(params,CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY));
			
			logger.info("订单"+req.getOrderNo()+"发货结果："+result);
			JSONObject obj = JSONObject.fromObject(result);
			if(StringUtils.isBlank(obj.getString("CHECK_SUCCESS")) && !obj.getBoolean("CHECK_SUCCESS")){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单发货失败，"+result);
			}else{
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("订单发货成功");
			}
		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("总部订单发货异常!");
			return  resp;
		}
		return resp;
	}
	/**
	 * 总部订单打印接口
	 * @param req
	 * @return
	 */
	@Override
	public ZteResponse zbOrderPrint(ZbOrderPrintReq req) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public static void main(String[] args) {
		File input = new File("E:/SVN/ZTEsoft/doc/浙江/爬虫抓取信息/发货处理/发货处理.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8", "http://admin.mall.10010.com");
			Elements element =doc.select("#orderDetailForm input[name=orderType]");
			logger.info(element.val()+"=========");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 总部订单回填物流单
	 */
	@Override
	public ZteResponse zbBackfillLogistics(ZbBackfillLogisticsReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		try {
			ZBSystemClient client = init();
			if(null==client){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("未登陆总商系统，请先登录!");
				return  resp;
			}
			//步骤一查询发货环节订单
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
			params.add(new BasicNameValuePair("page.webPager.action","refresh"));
			params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1000"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
			params.add(new BasicNameValuePair("pageSize","5"));
			String result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_QUERY));
			if(result.indexOf("订单编号："+req.getOrderNo()+"")<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("发货处理列表未查询到订单");
				return  resp;
			}
			//步骤二 进入发货详情界面
			//先解析列表界面的orderType字段
			Document doc = Jsoup.parse(result);
			Elements element =doc.select("#orderDetailForm input[name=orderType]");
			String orderType =element.val();
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
			params.add(new BasicNameValuePair("orderType",orderType));
			result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_DETAIL));
			if(result.indexOf(req.getOrderNo())<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("发货详情未查询到订单");
				return  resp;
			}
			
			//回填发货/退货地址
			params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("addrType","A"));
			params.add(new BasicNameValuePair("provinceCode",req.getProvinceCode()));
			params.add(new BasicNameValuePair("cityCode",req.getCityCode()));
			params.add(new BasicNameValuePair("districtCode",req.getDistrictCode()));
			params.add(new BasicNameValuePair("address",req.getAddress()));
			params.add(new BasicNameValuePair("contact",req.getContact()));
			params.add(new BasicNameValuePair("telphone",req.getTelphone()));
			
			result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_SEND_ADDS));
			JSONObject obj = JSONObject.fromObject(result);
			Boolean updateResult = obj.getBoolean("UPDATE_SUCCESS");
			if(!updateResult){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("修改发货/退货地址不成功");
				return  resp;
			}
			
			//步骤三回填并提交物流单
			params = new ArrayList<NameValuePair>();
//	        subLgtsOrderParams.chinaUnicomSelfFetchAddrId = addressId;//联通营业厅自提选择自提点
			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
			params.add(new BasicNameValuePair("logisticType",req.getLogisticType()));
			params.add(new BasicNameValuePair("billType",req.getBillType()));
			params.add(new BasicNameValuePair("companyId",req.getCompanyId()));
			params.add(new BasicNameValuePair("companyName",req.getCompanyName()));
			if("1".equals(req.getIsNeedLgtsRtn())){//需要回单
				if(StringUtils.isBlank(req.getLgtsRtnOrder())){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("回单单号不能为空");
					return  resp;
				}
				params.add(new BasicNameValuePair("lgtsRtnOrderDesc",req.getLgtsRtnOrderDesc()));
				params.add(new BasicNameValuePair("lgtsRtnOrder",req.getLgtsRtnOrder()));
			}
			params.add(new BasicNameValuePair("isNeedLgtsRtn",req.getIsNeedLgtsRtn()));
			if("elecBill".equals(req.getBillType())){
				params.add(new BasicNameValuePair("insureFlag",req.getInsureFlag()));//0:不保价  1：保价
				params.add(new BasicNameValuePair("insureMoney",req.getInsureMoney()));
			}else{
				if(StringUtils.isBlank(req.getLgtsOrder())){
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("物流单号不能为空");
					return  resp;
				}
				params.add(new BasicNameValuePair("lgtsOrder",req.getLgtsOrder()));
			}
			params.add(new BasicNameValuePair("lgtsRemark",req.getLgtsRemark()));
			if("1".equals(req.getLogisticType())){//1：自行配送，2：第三方配送， 3：客户自提  
				params.add(new BasicNameValuePair("deliver_type_code","05"));
			}else if("2".equals(req.getLogisticType())){
				params.add(new BasicNameValuePair("deliver_type_code","01"));
			}else if("3".equals(req.getLogisticType())){
				params.add(new BasicNameValuePair("deliver_type_code","03"));
			}
			params.add(new BasicNameValuePair("self_Type","0"));
			params.add(new BasicNameValuePair("chinaUnicomSelfFetch","0"));
			
			result = client.post(params,CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_SEND_LOGISTIC));
			logger.info("提交订单"+req.getOrderNo()+"物流单号结果："+result);
			 obj = JSONObject.fromObject(result);
			String respCode = obj.getString("RESP_CODE");
			if("SUCCESS".equalsIgnoreCase(respCode)||"WAIT".equalsIgnoreCase(respCode)){
				resp.setError_code(ConstsCore.ERROR_SUCC);
				resp.setError_msg("提交物流单号成功");
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("物流单号提交失败");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("总部订单回填物流单异常");
		} 
		return resp;
	}
	
	/**
	 * add by duan.jingliang
	 * 总部订单获取电子物流单号接口
	 * @param req
	 * @return
	 */
	@Override
	public ZteResponse zbOrderQueryDeliveryNum(ZbOrderDeliveryCodeQueryReq req) {
		ZteResponse resp = new ZteResponse();
		try {
			ZBSystemClient client = init();
			if (null==client) {
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("未登陆总商系统，请先登录!");
				return  resp;
			}
			if (null == req || null == req.getOrderId()) {
				throw new Exception ("请求参数错误");
			}
			//步骤一查询电子物流单号
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("chinaUnicomSelfFetchAddrId",""));
			params.add(new BasicNameValuePair("orderId",req.getOrderId()));
			params.add(new BasicNameValuePair("logisticType","2"));
			params.add(new BasicNameValuePair("billType","elecBill"));
			params.add(new BasicNameValuePair("companyId","1002"));
			params.add(new BasicNameValuePair("companyName","顺丰速运"));
			params.add(new BasicNameValuePair("isNeedLgtsRtn","0"));
			params.add(new BasicNameValuePair("lgtsRemark",""));
			params.add(new BasicNameValuePair("insureFlag","0"));
			params.add(new BasicNameValuePair("insureMoney","0"));
			params.add(new BasicNameValuePair("deliver_type_code","01"));
			params.add(new BasicNameValuePair("self_Type","0"));
			params.add(new BasicNameValuePair("chinaUnicomSelfFetch","0"));
			
			String result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_DELIVERY_CODE_QUERY));
			logger.info("总商获取电子物流单号接口返回: " + result);
			
			//步骤二解析状态
			if (null != result && !"".equals(result)) {
				JSONObject retObj = JSONObject.fromObject(result);
				String deliveryNum = retObj.getString("LGTS_ORDER");
				String respCode = retObj.getString("RESP_CODE");
				if ("SUCCESS".equals(respCode)) {
					logger.info("获取物流单号成功!");
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("获取物流单号成功");
					resp.setBody(deliveryNum);
				} else {
					resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("获取物流单号失败");
				}
			} else {
				throw new Exception ("接口返回空");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("获取物流单号接口异常," + e.getMessage());
		}
		return resp;
	}
}
