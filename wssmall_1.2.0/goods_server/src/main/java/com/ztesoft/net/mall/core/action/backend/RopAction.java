package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.SimulatorDDGJReq;
import zte.net.ecsord.params.base.resp.SimulatorDDGJResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleExeLogDelResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.req.TbTianjiTestReq;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.orderctn.resp.TbTianjiTestResp;

import com.taobao.api.domain.Feature;
import com.taobao.api.domain.OrderMessage;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.domain.Receiver;
import com.taobao.api.domain.SubPurchaseOrder;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 模拟外部调用订单系统的接口 用外部调用的过来的报文，反向调用订单系统
 */
public class RopAction extends WWAction {

	private String jsonStr;

	private String bussType;

	private String url = "http://localhost:8080/router";

	private String appKey = "wssmall_ecsord", secret = "123456";

	public String ROPTest() {
		if (null == jsonStr || jsonStr.equals("这是测试ROP报文")) {
			return "ropTest";
		} else {
			json = "{'result':0,'message':'模拟请求成功！'}";
			
			if(null != bussType && bussType.equals("ddgj")){//模拟订单归集 SimulatorDDGJReq
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
//				try {
//					jsonStr = new String(jsonStr.getBytes("iso8859-1"),"utf-8");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				
				request.setReqType("ddgj");
				request.setReqStr(jsonStr);
				request.setSevletPath(url);
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = "{'result':0,'message':'模拟请求成功["+resp.getOrder_id()+"]！'}";
			}else if(null != bussType && bussType.equals("ddgj_zb")){//模拟订单归集
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				
//				logger.info(jsonStr);
//				try {
////					logger.info(new String(jsonStr.getBytes("iso8859-1"),"utf-8"));
//					jsonStr = new String(jsonStr.getBytes("iso8859-1"),"GBK");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				request.setReqStr(jsonStr);
				request.setReqType("ddgj_zb");
				request.setSevletPath(url);
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = "{'result':0,'message':'模拟请求成功["+resp.getOrder_id()+"]！'}";
			}else if (null != bussType && bussType.equals("ddtd")){//模拟订单退单
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				request.setReqStr(jsonStr);
				request.setReqType("ddtd");
				request.setSevletPath(url);
				request.setFormat("xml");
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = "{'result':0,'message':'模拟请求成功["+resp.getOrder_id()+"]！'}";
			}else if(null != bussType && (bussType.equals("wl_oper_to_order")||bussType.equals("wl_syn_status")||bussType.equals("wl_order_deal_sucess"))){//模拟南都HttpPost请求
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				request.setReqStr(jsonStr);
				request.setReqType("httpPost");
				url=url+"?reqId=nan-fang-chuan-mei&reqType="+bussType;
				request.setSevletPath(url);
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = resp.getResult_str();
			}else if(null != bussType && ("sf_artificial_select".equals(bussType)||"wl_result_to_order".equals(bussType))){//模拟顺丰 xml请求参数 HttpPost请求
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				request.setFormat("xml");
				request.setReqStr(jsonStr);
				request.setReqType("httpPost");
				url=url+"?reqId=sf-express&reqType="+bussType;
				request.setSevletPath(url);
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = resp.getResult_str();
				
			}else if(null != bussType && (bussType.equals("wyg_syn_order_status"))){//模拟沃云购Http请求
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				SimulatorDDGJReq request = new SimulatorDDGJReq();
				request.setReqStr(jsonStr);
				request.setReqType("httpPost");
				url=url+"?reqId=xsc-wyg-valid&reqType="+bussType;
				request.setSevletPath(url);
				SimulatorDDGJResp resp = client.execute(request, SimulatorDDGJResp.class);
				json = resp.getResult_str();
			}
			else if(null != bussType && bussType.equals("ddtd_tmfx")){
				/*logger.info("淘宝分销订单[]抓取到本地环境");
				PurchaseOrder purchaseOrder = createPurchaseOrder(Long.valueOf("111"));
				String inJson = JsonUtil.toJson(purchaseOrder);
				OrderCtnReq orderCtnReq  = new OrderCtnReq();
				orderCtnReq.setOutServiceCode("create_order_taoBaoFenxiaoOrderStandard");
				orderCtnReq.setReqMsgStr(inJson);
				orderCtnReq.setParams(null);
				orderCtnReq.setVersion("1.0");
				orderCtnReq.setFormat("json");
				final String out_order_id = searchValue(jsonStr, "fenxiaoid");
				orderCtnReq.setOutOrderId(out_order_id);
				Map<String, Object> dyn_field = new HashMap<String, Object>();
				dyn_field.put("order_from", "10001");
				dyn_field.put("end_time", "2016-10-11 11:33:24");//传递抓单时间
				orderCtnReq.setDyn_field(dyn_field);
				long begin = System.currentTimeMillis();
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				OrderCtnResp resp = client.execute(orderCtnReq, OrderCtnResp.class);
				long end = System.currentTimeMillis();
				logger.info("[TaobaoFenxiaoCtnService] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));*/
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				TbTianjiTestReq req = new TbTianjiTestReq();
				
				TbTianjiTestResp resp = client.execute(req, TbTianjiTestResp.class);
				logger.info("[TaobaoFenxiaoCtnService] 执行标准化总时间为:[] 返回对象: "+ IJSONUtil.beanToJson(resp));
			} else if(null != bussType && bussType.equals("ddtd_tmtj")){
				OrderCtnReq orderCtnReq  = new OrderCtnReq();
				orderCtnReq.setOutServiceCode("create_order_taoBaoTianjiOrderStandard");
				orderCtnReq.setReqMsgStr(jsonStr);
				orderCtnReq.setParams(null);
				orderCtnReq.setVersion("1.0");
				orderCtnReq.setFormat("json");
				final String out_order_id = searchValue(jsonStr, "tb_order_no");
				orderCtnReq.setOutOrderId(out_order_id);
				Map<String, Object> dyn_field = new HashMap<String, Object>();
				dyn_field.put("order_from", "10012");
				dyn_field.put("end_time", "2016-10-20 13:38:09");//传递抓单时间
				orderCtnReq.setDyn_field(dyn_field);
				long begin = System.currentTimeMillis();
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
				OrderCtnResp resp = client.execute(orderCtnReq, OrderCtnResp.class);
				long end = System.currentTimeMillis();
				logger.info("[TaobaoFenxiaoCtnService] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
			}else{//模拟第三方系统请求返回报文 
				Class<?> clazz = null;
				try {
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
					clazz = Class.forName(bussType);
					ZteRequest request = (ZteRequest) CommonTools.jsonToBean(jsonStr, clazz);
					ZteResponse resp = client.execute(request, ZteResponse.class);
                     
					/*ObjectMapper mapper = new ObjectMapper();
					try {
						json= mapper.writeValueAsString(resp);
					} catch (JsonGenerationException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}				
			}
			return JSON_MESSAGE;
		}

	}
	
	public PurchaseOrder createPurchaseOrder(long out_tid){
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setSupplierFrom("taobao");
		purchaseOrder.setSupplierUsername("zhangsan");
		purchaseOrder.setCreated(new Date());
		purchaseOrder.setAlipayNo("");
		purchaseOrder.setTotalFee("200.07");
		purchaseOrder.setPostFee("10.00");
		purchaseOrder.setDistributorPayment("210.07");
		purchaseOrder.setSnapshotUrl("www.vancl.com");
		purchaseOrder.setPayTime(new Date());
		purchaseOrder.setConsignTime(new Date());
		purchaseOrder.setModified(new Date());
		purchaseOrder.setSupplierMemo("供应商信息描述");
		purchaseOrder.setFenxiaoId(2387648327L); //外部订单号
		purchaseOrder.setPayType("ALIPAY_SURETY");
		purchaseOrder.setTradeType("AGENT");
		purchaseOrder.setDistributorFrom("taobao");
		purchaseOrder.setId(345235L);
		purchaseOrder.setStatus("TRADE_FINISHED");
		purchaseOrder.setBuyerNick("zhangsan");
		purchaseOrder.setMemo("留言");
		purchaseOrder.setTcOrderId(199879341L);
		
		Receiver receiver = new Receiver();
		receiver.setName("zhangsan");
		receiver.setPhone("057188155188");
		receiver.setMobilePhone("1384567895");
		receiver.setAddress("浙江省杭州市西湖区华星路99号");
		receiver.setDistrict("西湖区");
		receiver.setCity("浙江");
		receiver.setCardId("111112222233333444");
		purchaseOrder.setReceiver(receiver);
		
		purchaseOrder.setShipping("FAST");  //配送方式
		purchaseOrder.setLogisticsCompanyName("EMS");   //物流公司
		purchaseOrder.setLogisticsId("123123");
		purchaseOrder.setIsvCustomKey(new ArrayList<String>());
		purchaseOrder.setIsvCustomValue(new ArrayList<String>());
		purchaseOrder.setEndTime(new Date());
		purchaseOrder.setSupplierFlag(1L);
		purchaseOrder.setBuyerPayment("210.07");
		
		OrderMessage orderMessage = new OrderMessage();
		orderMessage.setMessageTime(new Date());
		orderMessage.setMessageTitle("分销商留言");
		orderMessage.setMessageContent("留言内容");
		orderMessage.setPicUrl("11111");
		List<OrderMessage> list = new ArrayList<OrderMessage>();
		list.add(orderMessage);
		purchaseOrder.setOrderMessages(list);
		
		SubPurchaseOrder subPurchaseOrder = new SubPurchaseOrder();
		subPurchaseOrder.setItemOuterId("555");
		subPurchaseOrder.setSkuId(5555L);
		subPurchaseOrder.setSkuOuterId("69201407240077931"); //商品ID、SN
		subPurchaseOrder.setSkuProperties("颜色:红色");
		subPurchaseOrder.setSnapshotUrl("www.vancl.com");
		subPurchaseOrder.setCreated(new Date());
		subPurchaseOrder.setStatus("TRADE_FINISHED");
		subPurchaseOrder.setRefundFee("0");
		subPurchaseOrder.setId(5552L);
		subPurchaseOrder.setFenxiaoId(1000L);
		subPurchaseOrder.setSkuId(555L);
		subPurchaseOrder.setTcOrderId(112233L);
		subPurchaseOrder.setItemId(555L);
		subPurchaseOrder.setOrder200Status("TRADE_FINISHED");
		subPurchaseOrder.setAuctionPrice("100.10");
		subPurchaseOrder.setNum(50L);
		subPurchaseOrder.setTitle("中兴V889M 黑色社会机66元套餐C50月购机送费"); //商品名称
		subPurchaseOrder.setPrice("100.10");
		subPurchaseOrder.setTotalFee("200.07");
		subPurchaseOrder.setDistributorPayment("210.07");
		subPurchaseOrder.setBuyerPayment("210.07");
		subPurchaseOrder.setBillFee("200.07");
		subPurchaseOrder.setScItemId(100000000L);
		subPurchaseOrder.setTcPreferentialType("聚划算");
		subPurchaseOrder.setTcDiscountFee(10L);
		subPurchaseOrder.setTcAdjustFee(10L);
		subPurchaseOrder.setDiscountFee("11.11");
		subPurchaseOrder.setPromotionType("1");
		subPurchaseOrder.setFeatures(new ArrayList<Feature>());
		List<SubPurchaseOrder> list2 = new ArrayList<SubPurchaseOrder>();
		list2.add(subPurchaseOrder);
		purchaseOrder.setSubPurchaseOrders(list2);
		purchaseOrder.setBuyerTaobaoId("12214235");
		
		List<Feature> features = new ArrayList<Feature>();
		//发票抬头
		Feature feature = new Feature();
		feature.setAttrKey("orderNovice");
		feature.setAttrValue("zhangsan");
		features.add(feature);
		purchaseOrder.setFeatures(features);
		return purchaseOrder;
	}
	
	/**
	 * 获取json字符串中某一个key的value
	 * @param json 
	 * @param key 键
	 * @return
	 */
	public static String searchValue(String json, String key) {
		json = strReplaceBlank(json);
		String regex = "\"" +key + "\":(.*?),";
		Matcher matcher=Pattern.compile(regex).matcher(json);
		String value = "";
		
		while(matcher.find())
		{
			value = matcher.group(1);
		}
		// 去掉双引号("")
		value = value.trim().replaceAll("\"(\\w+)\"", "$1"); 
		return value;
	}
	
	/**
	 * 替换空格、回车、换行、制表位为空字符
	 * @param str
	 * @return
	 */
	public static String strReplaceBlank(String str){
		Pattern pattern = Pattern.compile("\r|\t|\n");
		Matcher m = pattern.matcher(str);
		return m.replaceAll("");
	}
	
	public String ROPZhuDongTest() {

		//String order_id="HZ201501209016998508";//  ZBWO201412153579996500		;ZQ201501222083998612; 	CZ201412167584997083;ZBWO201412183305997380; 	
		/*
		NotifyOrderInfoSFRequset req = new NotifyOrderInfoSFRequset();
		req.setOrderid(order_id);
		NotifyOrderInfoSFResponse infResp = client.execute(req, NotifyOrderInfoSFResponse.class);
		logger.info("应答编码:"+infResp.getError_code()+",筛单结果:");*/
		
		//调用同步信息到物流公司规则
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String order_id="GZ201502109190998659";
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.SF_STATUS},new String[]{EcsOrderConsts.SDS_STATUS_01});
		//删除规则执行日志
		RuleExeLogDelReq delLogReq = new RuleExeLogDelReq();
		delLogReq.setObj_id(order_id);
		delLogReq.setRule_id(EcsOrderConsts.ORDER_SYN_TO_WL);
		client.execute(delLogReq, RuleExeLogDelResp.class);
		
		//执行规则

		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(EcsOrderConsts.ORDER_SYN_TO_WL);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		req.setFact(fact);
		RuleTreeExeResp rsp = client.execute(req, RuleTreeExeResp.class);
		if(ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){//没调用业务组件也算失败
			logger.info("同步成功："+rsp.getError_msg());
		}else{
			logger.info("同步失败："+rsp.getError_msg());
		}
		return order_id;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
