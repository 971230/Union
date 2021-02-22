package com.ztesoft.ibss.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import params.req.OrderBeeReq;
import params.req.OrderDistributeCtnStandardReq;
import params.req.OrderPreCreateCtnStandardReq;
import params.resp.OrderBeeResp;
import params.resp.OrderDistributeCtnStandardResp;
import params.resp.OrderPreCreateCtnStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.params.goodscats.req.CatGetByIdReq;
import zte.params.goodscats.resp.CatGetResp;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.MarkTime;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

public class IntentUtil {
	private static Logger logger = Logger.getLogger(IntentUtil.class);

	/**
	 * 中间表插入数据
	 */
	public void intentMidOrderCreate(Map<Object, Object> mallMid) {
		IDaoSupport<?> baseDaoSupport = SpringContextHolder
				.getBean("baseDaoSupport");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

		mallMid.put("create_date", date);
		mallMid.put("exception_desc", "");
		mallMid.put("done_status", "0");
		mallMid.put("done_time", "");
		mallMid.put("done_num", "0");
		mallMid.put("SOURCE_FROM", "ECS");
		try {
			baseDaoSupport.insert("es_order_intent_mid", mallMid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 京东便利店校验参数是否异常
	 * 
	 * @param req
	 * @return
	 */
	public String cheakJD(Map mapReq) {
		if (mapReq.get("REQ_HEAD") == null) {
			return "REQ_HEAD为空";
		}
		if (mapReq.get("REQ_DATA") == null) {
			return "REQ_DATA为空";
		}
		Map mapReqHead = (Map) mapReq.get("REQ_HEAD");
		if (StringUtils
				.isEmpty(Const.getStrValue(mapReqHead, "TOUCH_ORDER_ID"))) {
			return "TOUCH_ORDER_ID为空";
		} else {
			IDaoSupport baseDaoSupport = SpringContextHolder
					.getBean("baseDaoSupport");
			String sql = "select * from es_order_intent where intent_order_id ='"
					+ Const.getStrValue(mapReqHead, "TOUCH_ORDER_ID") + "'";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				return "TOUCH_ORDER_ID重复";
			}
		}
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "IN_MODE_CODE"))) {
			return "IN_MODE_CODE为空";
		}
		if (StringUtils
				.isEmpty(Const.getStrValue(mapReqHead, "ORDER_PROVINCE"))) {
			return "ORDER_PROVINCE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "ORDER_TIME"))) {
			return "ORDER_TIME为空";
		}
		Map mapReqData = (Map) mapReq.get("REQ_DATA");
		if (mapReqData.get("COMM_OBJECT") != null) {
			List mapReqDataCommObject = (List) mapReqData.get("COMM_OBJECT");
			for (Object obj : mapReqDataCommObject) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "COMM_ID"))) {
					return "COMM_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "COMM_COUNT"))) {
					return "COMM_COUNT为空";
				}
			}
		} else {
			return "COMM_OBJECT为空";
		}
		if (mapReqData.get("CUST_INFO") != null) {
			Map mapReqDataCust = (Map) mapReqData.get("CUST_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust,
					"CUST_NAME"))) {
				return "CUST_NAME为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust,
					"CONTACT_PHONE"))) {
				return "CONTACT_PHONE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust,
					"POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust,
					"CERT_TYPE"))) {
				return "CERT_TYPE为空";
			}
			if (StringUtils.isEmpty(Const
					.getStrValue(mapReqDataCust, "CERT_ID"))) {
				return "CERT_ID为空";
			}
		}
		if (mapReqData.get("DEVELOPER_INFO") != null) {
			List mapReqDataDeveloper = (List) mapReqData.get("DEVELOPER_INFO");
			for (Object obj : mapReqDataDeveloper) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_TYPE"))) {
					return "DEVELOP_TYPE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"DEVELOP_EPARCHY_CODE"))) {
					return "DEVELOP_EPARCHY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"DEVELOP_STAFF_ID"))) {
					return "DEVELOP_STAFF_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_DATE"))) {
					return "DEVELOP_DATE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"DEVELOP_MANAGER_ID"))) {
					return "DEVELOP_MANAGER_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"DEVELOP_MANAGER_NAME"))) {
					return "DEVELOP_MANAGER_NAME为空";
				}
			}
		}

		return "";
	}

	/**
	 * 总部蜂行动校验参数是否异常
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String cheakBee(Map mapReq) {
		if (mapReq.get("ORDER") == null) {
			return "ORDER为空";
		}
		Map ORDER = (Map) mapReq.get("ORDER");
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "ORDER_ID"))) {
			return "ORDER_ID为空";
		} else {
			IDaoSupport baseDaoSupport = SpringContextHolder
					.getBean("baseDaoSupport");
			String sql = "select * from es_order_intent where intent_order_id ='"
					+ Const.getStrValue(ORDER, "ORDER_ID") + "'";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				return "ORDER_ID重复";
			}
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "IN_MODE_CODE"))) {
			return "IN_MODE_CODE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "CHANNEL_ID"))) {
			return "CHANNEL_ID为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "CREATE_DATE"))) {
			return "CREATE_DATE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "PAY_TAG"))) {
			return "PAY_TAG为空";
		}
		if (null != ORDER.get("ORDER_ATTR")) {
			List ORDER_ATTR = (List) ORDER.get("ORDER_ATTR");
			for (Object obj : ORDER_ATTR) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "ATTR_CODE"))) {
					return "ATTR_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "ATTR_VALUE"))) {
					return "ATTR_VALUE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"ATTR_VALUE_NAME"))) {
					return "ATTR_VALUE_NAME为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "ACTION"))) {
					return "ACTION为空";
				}
			}
		}
		if (null != ORDER.get("DEVELOPER_INFO")) {
			Map DEVELOPER_INFO = (Map) ORDER.get("DEVELOPER_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO,
					"DEVELOP_TYPE"))) {
				return "DEVELOP_TYPE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO,
					"DEVELOP_EPARCHY_CODE"))) {
				return "DEVELOP_EPARCHY_CODE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO,
					"DEVELOP_STAFF_ID"))) {
				return "DEVELOP_STAFF_ID为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO,
					"DEVELOP_DATE"))) {
				return "DEVELOP_DATE为空";
			}
		}
		if (null != ORDER.get("COMMON_INFO")) {
			List COMMON_INFO = (List) ORDER.get("COMMON_INFO");
			for (Object obj : COMMON_INFO) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEPART_ID"))) {
					return "DEPART_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "CITY_CODE"))) {
					return "CITY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "EPARCHY_CODE"))) {
					return "EPARCHY_CODE为空";
				}
				if (StringUtils
						.isEmpty(Const.getStrValue(map, "PROVINCE_CODE"))) {
					return "PROVINCE_CODE为空";
				}
			}
		}
		if (null != ORDER.get("FEEPAY_INFO")) {
			List FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");
			for (Object obj : FEEPAY_INFO) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_ID"))) {
					return "PAY_ID为空";
				}
				if (StringUtils.isEmpty(Const
						.getStrValue(map, "PAY_MONEY_CODE"))) {
					return "PAY_MONEY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "MONEY"))) {
					return "MONEY为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_CHANNEL"))) {
					return "PAY_CHANNEL为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_DATE"))) {
					return "PAY_DATE为空";
				}
			}
		}
		if (null != ORDER.get("CUST_INFO")) {
			Map CUST_INFO = (Map) ORDER.get("CUST_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CUST_NAME"))) {
				return "CUST_NAME为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "SEX"))) {
				return "SEX为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CUST_PHONE"))) {
				return "CUST_PHONE为空";
			}
			if (StringUtils.isEmpty(Const
					.getStrValue(CUST_INFO, "POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "PSPT_ID"))) {
				return "PSPT_ID为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO,
					"PSPT_TYPE_CODE"))) {
				return "PSPT_TYPE_CODE为空";
			}
		}
		if (null != ORDER.get("DELIVER_INFO")) {
			Map DELIVER_INFO = (Map) ORDER.get("DELIVER_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO,
					"POST_MOBILE_1"))) {
				return "POST_MOBILE_1为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO,
					"POST_PROVINCE"))) {
				return "POST_PROVINCE为空";
			}
			if (StringUtils.isEmpty(Const
					.getStrValue(DELIVER_INFO, "POST_CITY"))) {
				return "POST_CITY为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO,
					"POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
		}
		if (null != ORDER.get("ORDER_LINE")) {
			List ORDER_LINE = (List) ORDER.get("ORDER_LINE");
			for (Object obj : ORDER_LINE) {
				Map map = (Map) obj;
				if (StringUtils
						.isEmpty(Const.getStrValue(map, "ORDER_LINE_ID"))) {
					return "ORDER_LINE_ID为空";
				}
				if (StringUtils
						.isEmpty(Const.getStrValue(map, "SERIAL_NUMBER"))) {
					return "SERIAL_NUMBER为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"TRADE_TYPE_CODE"))) {
					return "TRADE_TYPE_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "CANCEL_TAG"))) {
					return "CANCEL_TAG为空";
				}
			}
		}
		if (null != ORDER.get("INVOICE_INFO")) {
			List INVOICE_INFO = (List) ORDER.get("INVOICE_INFO");
			for (Object obj : INVOICE_INFO) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"TICKET_TYPE_CODE"))) {
					return "TICKET_TYPE_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "TICKET_ID"))) {
					return "TICKET_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"INVOICE_CHECK_CODE"))) {
					return "INVOICE_CHECK_CODE为空";
				}
				if (StringUtils.isEmpty(Const
						.getStrValue(map, "INVOICE_HEADER"))) {
					return "INVOICE_HEADER为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"RECEIVE_MESSAGE_NUM"))) {
					return "RECEIVE_MESSAGE_NUM为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map,
						"BUYER_TAXPAYER_ID"))) {
					return "BUYER_TAXPAYER_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "FEE"))) {
					return "FEE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "IS_PRINT"))) {
					return "IS_PRINT为空";
				}
			}
		}

		return "";
	}

	/***
	 * 定时任务
	 */
	public String doneIntent(String order_id) {
		OrderDistributeCtnStandardResp resp = new OrderDistributeCtnStandardResp();
		OrderBeeResp resp_bee=new OrderBeeResp();
		OrderPreCreateCtnStandardResp resp_order_pre = new OrderPreCreateCtnStandardResp();
		String respJsonStr = "";// 返回json字符串
		String order_type = ""; // 订单类型
		String num = ""; // 处理次数
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		@SuppressWarnings("rawtypes")
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

		String sql = cacheUtil.getConfigInfo("handle_intent_min_update");
		String sqlByOrderId = cacheUtil.getConfigInfo("handle_intent_min_update_by_orderId");
		String updatesql = cacheUtil.getConfigInfo("order_intent_min_update");
		List<?> queryList = null;
		double money=0;	
		String PAY_TAG="";
		if (StringUtil.isEmpty(order_id)) {
			queryList = baseDaoSupport.queryForList(sql);
			if (queryList.size() > 0) {
				for (int i = 0; i < queryList.size(); i++) {
					Map query_map = (Map) queryList.get(i);
					order_type = (String) query_map.get("order_type");
					String requJsonStr = (String) query_map.get("req");
					requJsonStr = requJsonStr.replace("{req=", "");
					requJsonStr = requJsonStr.replace("}}", "}");
					ZteClient client=null;
					
					try {
						if (!StringUtils.isEmpty(requJsonStr)) {
							Map<?, ?> map = (Map<?, ?>) JSON.parse(requJsonStr);
							
							if (!map.isEmpty()) {
								if ("100104".equals(order_type)) {// 总部蜂行动
									String desc = cheakBee(map);
									String pay_money="";
									String pay_id="";
									Map<?, ?> ORDER = (Map<?, ?>) map.get("ORDER");
									PAY_TAG=Const.getStrValue(ORDER, "PAY_TAG");
									if (null != ORDER.get("FEEPAY_INFO")) {
										List<Map> FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");// 支付
										for (Map FEEPAY : FEEPAY_INFO) {
											pay_money = Const.getStrValue(FEEPAY, "MONEY");// 支付金额
																			}
									}
									order_id = Const.getStrValue(ORDER, "ORDER_ID");
									// 总部蜂行动处理次数
									String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
									done_num_sql = done_num_sql.replace("{order_id}",order_id);
									int done_num = baseDaoSupport.queryForInt(done_num_sql);
									done_num++;
									num = String.valueOf(done_num);
									// 总部蜂行动中间表更新订单返回状态
									updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',","");
									updatesql = updatesql.replace("t.done_num='{num}',", "");
									updatesql = updatesql.replace("{done_status}", "1");
									updatesql = updatesql.replace("{order_id}",order_id);
									// 执行更新语句
									baseDaoSupport.execute(updatesql);
									money=Double.valueOf(pay_money.toString());
									if (StringUtils.isEmpty(desc)) {
										if(money>0&&"1".equals(PAY_TAG)){
											//线上生产模式，拼接报文字段
											JSONObject makeUp=getBeeMakeupMessage(requJsonStr);
											//解析请求参数
											JSONObject requJson = JSONObject.fromObject(makeUp);
											//请求入参
											JSONObject orderInfoJson = requJson.getJSONObject("mall_req");
											String inJson = orderInfoJson.toString();
											
											if(org.apache.commons.lang.StringUtils.isBlank(inJson)){
												try {
													throw new Exception("传入订单信息为空");
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
											OrderBeeReq requ = (OrderBeeReq) JSONObject.toBean(orderInfoJson, OrderBeeReq.class);
											client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
											resp_bee = client.execute(requ,OrderBeeResp.class);
											
										}else{
											OrderDistributeCtnStandardReq requ = new OrderDistributeCtnStandardReq();
											requ.setMapReq(map);
											client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
											resp = client.execute(requ,OrderDistributeCtnStandardResp.class);
										}
									} else {
										resp.setRESP_CODE("0");
										resp.setRESP_DESC(desc);
									}
								} else {// 京东便利店
									String desc = cheakJD(map);
									Map<?, ?> REQ_HEAD = (Map<?, ?>) map.get("REQ_HEAD");
									order_id = Const.getStrValue(REQ_HEAD,"TOUCH_ORDER_ID");
									// 京东便利店处理次数
									String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
									done_num_sql = done_num_sql.replace("{order_id}",order_id);
									int done_num = baseDaoSupport.queryForInt(done_num_sql);
									done_num++;
									num = String.valueOf(done_num);
									// 总部蜂行动中间表更新订单返回状态
									updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',","");
									updatesql = updatesql.replace("t.done_num='{num}',", "");
									updatesql = updatesql.replace("{done_status}", "1");
									updatesql = updatesql.replace("{order_id}",order_id);
									// 执行更新语句
									baseDaoSupport.execute(updatesql);
									if (StringUtils.isEmpty(desc)) {
										OrderPreCreateCtnStandardReq requ = new OrderPreCreateCtnStandardReq();
										requ.setMapReq(map);
										client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
										resp_order_pre = client.execute(requ,OrderPreCreateCtnStandardResp.class);
									} else {
										resp_order_pre.setCODE("1002");
										resp_order_pre.setDESC(desc);
									}
								}
							} else {
								resp.setRESP_CODE("0");
								resp.setRESP_DESC("请求报文参数异常");
							}
						} else {
							resp.setRESP_CODE("0");
							resp.setRESP_DESC("请求报文为空");
						}
					} catch (ApiException e) {
						resp.setRESP_CODE("0");
						resp.setRESP_DESC("异常错误1："+ (e.getMessage() == null ? e.toString() : e.getMessage()));
						e.printStackTrace();
						logger.info(e, e);
					}
					updatesql = cacheUtil
							.getConfigInfo("order_intent_min_update");
					Map<String, Object> respMap = new HashMap<String, Object>();
					if ("1".equals(resp.getRESP_CODE())) {
						respMap.put("RESP_CODE", resp.getRESP_CODE());
						respMap.put("RESP_DESC", resp.getRESP_DESC());
						updatesql = updatesql.replace("{order_id}", order_id);
						updatesql = updatesql.replace("{done_status}", "2");
						updatesql = updatesql.replace("{num}", num);
						updatesql = updatesql.replace("{exception_desc}",resp.getRESP_DESC());
						// 执行更新语句
						baseDaoSupport.execute(updatesql);
						// 向中间表历史表插入数据
						String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
						copysql = copysql.replace("{order_id}", order_id);
						baseDaoSupport.execute(copysql);
						// 删除中间表数据
						String deletesql = cacheUtil.getConfigInfo("order_intnet_min_delete");
						deletesql = deletesql.replace("{order_id}", order_id);
						baseDaoSupport.execute(deletesql);
					}else if ("0".equals(resp_bee.getResp_code())) {
						respMap.put("RESP_CODE", resp_bee.getResp_code());
						respMap.put("RESP_DESC", resp_bee.getResp_msg());
						updatesql = updatesql.replace("{order_id}", order_id);
						updatesql = updatesql.replace("{done_status}", "2");
						updatesql = updatesql.replace("{num}", num);
						updatesql = updatesql.replace("{exception_desc}",resp_bee.getResp_msg());
						// 执行更新语句
						baseDaoSupport.execute(updatesql);
						// 向中间表历史表插入数据
						String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
						copysql = copysql.replace("{order_id}", order_id);
						baseDaoSupport.execute(copysql);
						// 删除中间表数据
						String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
						deletesql = deletesql.replace("{order_id}", order_id);
						baseDaoSupport.execute(deletesql);
						logger.info("总部蜂行动处理成功");
					}  else if ("0000".equals(resp_order_pre.getCODE())) {
						//respMap.put("DATA", resp_order_pre.getDATA());
						respMap.put("RESP_CODE", resp_order_pre.getCODE());
						respMap.put("RESP_DESC", resp_order_pre.getDESC());
						updatesql = updatesql.replace("{order_id}", order_id);
						updatesql = updatesql.replace("{done_status}", "2");
						updatesql = updatesql.replace("{num}", num);
						updatesql = updatesql.replace("{exception_desc}",
								resp_order_pre.getDESC());
						// 执行更新语句
						baseDaoSupport.execute(updatesql);
						// 向中间表历史表插入数据
						String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
						copysql = copysql.replace("{order_id}", order_id);
						baseDaoSupport.execute(copysql);
						// 删除中间表数据
						String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
						deletesql = deletesql.replace("{order_id}", order_id);
						baseDaoSupport.execute(deletesql);
					} else {
						updatesql = updatesql.replace("{num}", num);
						updatesql = updatesql.replace("{done_status}", "3");
						 if ("100104".equals(order_type)) {
								updatesql = updatesql.replace("{exception_desc}",resp.getRESP_DESC());
								respJsonStr=resp.getRESP_DESC();
								respMap.put("RESP_CODE", resp.getRESP_CODE());
								respMap.put("RESP_DESC", resp.getRESP_DESC());
							}else if ("100104".equals(order_type)&&money>0&&"1".equals(PAY_TAG)) {
							updatesql = updatesql.replace("{exception_desc}",resp_bee.getResp_msg());
							respJsonStr=resp_bee.getResp_msg();
							respMap.put("RESP_CODE", resp_bee.getResp_code());
							respMap.put("RESP_DESC", resp_bee.getResp_msg());
							} else{
							updatesql = updatesql.replace("{exception_desc}",resp_order_pre.getDESC());
							respJsonStr=resp_order_pre.getDESC();
							respMap.put("RESP_CODE", resp_order_pre.getCODE());
							respMap.put("RESP_DESC", resp_order_pre.getDESC());
							}
						updatesql = updatesql.replace("{order_id}", order_id);
						baseDaoSupport.execute(updatesql);
					}
					
					// 将返回数据转成json格式的字符串
					respJsonStr = JSONObject.fromObject(respMap).toString();
					// 返回接口调用结果
					logger.info("[OrderDistributeServlet]-response:"+ respJsonStr);
				}
			}
		} else {
			sqlByOrderId=sqlByOrderId.replace("{order_id}", order_id);
			queryList = baseDaoSupport.queryForList(sqlByOrderId);
			Map<?, ?> query_map = (Map<?, ?>) queryList.get(0);
			order_type = (String) query_map.get("order_type");
			String requJsonStr = (String) query_map.get("req");
			requJsonStr = requJsonStr.replace("{req=", "");
			requJsonStr = requJsonStr.replace("}}", "}");
			ZteClient client=null;

			try {
				if (!StringUtils.isEmpty(requJsonStr)) {
					Map<?, ?> map = (Map<?, ?>) JSON.parse(requJsonStr);
					if (!map.isEmpty()) {
						if ("100104".equals(order_type)) {// 总部蜂行动
							String desc = cheakBee(map);
							String pay_money="";
							String pay_id="";
							Map<?, ?> ORDER = (Map<?, ?>) map.get("ORDER");
							PAY_TAG=Const.getStrValue(ORDER, "PAY_TAG");
							if (null != ORDER.get("FEEPAY_INFO")) {
								List<Map> FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");// 支付
								for (Map FEEPAY : FEEPAY_INFO) {
									pay_money = Const.getStrValue(FEEPAY, "MONEY");// 支付金额
																	}
							}
							order_id = Const.getStrValue(ORDER, "ORDER_ID");
							// 总部蜂行动处理次数
							String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
							done_num_sql = done_num_sql.replace("{order_id}",order_id);
							int done_num = baseDaoSupport.queryForInt(done_num_sql);
							done_num++;
							num = String.valueOf(done_num);
							// 总部蜂行动中间表更新订单返回状态
							updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',","");
							updatesql = updatesql.replace("t.done_num='{num}',", "");
							updatesql = updatesql.replace("{done_status}", "1");
							updatesql = updatesql.replace("{order_id}",order_id);
							// 执行更新语句
							baseDaoSupport.execute(updatesql);
							money=Double.valueOf(pay_money.toString());
							if (StringUtils.isEmpty(desc)) {
								if(money>0&&"1".equals(PAY_TAG)){
									//线上生产模式，拼接报文字段
									JSONObject makeUp=getBeeMakeupMessage(requJsonStr);
									//解析请求参数
									JSONObject requJson = JSONObject.fromObject(makeUp);
									//请求入参
									JSONObject orderInfoJson = requJson.getJSONObject("mall_req");
									String inJson = orderInfoJson.toString();
									
									if(org.apache.commons.lang.StringUtils.isBlank(inJson)){
										try {
											throw new Exception("传入订单信息为空");
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									OrderBeeReq requ = (OrderBeeReq) JSONObject.toBean(orderInfoJson, OrderBeeReq.class);
									client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
									resp_bee = client.execute(requ,OrderBeeResp.class);
									
								}else{
									OrderDistributeCtnStandardReq requ = new OrderDistributeCtnStandardReq();
									requ.setMapReq(map);
									client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
									resp = client.execute(requ,OrderDistributeCtnStandardResp.class);
								}
							} else {
								resp.setRESP_CODE("0");
								resp.setRESP_DESC(desc);
							}
						} else {// 京东便利店
							String desc = cheakJD(map);
							Map<?, ?> REQ_HEAD = (Map<?, ?>) map.get("REQ_HEAD");
							order_id = Const.getStrValue(REQ_HEAD,"TOUCH_ORDER_ID");
							// 京东便利店处理次数
							String done_num_sql = cacheUtil.getConfigInfo("order_intent_done_num");
							done_num_sql = done_num_sql.replace("{order_id}",order_id);
							int done_num = baseDaoSupport.queryForInt(done_num_sql);
							done_num++;
							num = String.valueOf(done_num);
							// 总部蜂行动中间表更新订单返回状态
							updatesql = updatesql.replace("t.exception_desc = '{exception_desc}',","");
							updatesql = updatesql.replace("t.done_num='{num}',", "");
							updatesql = updatesql.replace("{done_status}", "1");
							updatesql = updatesql.replace("{order_id}",order_id);
							// 执行更新语句
							baseDaoSupport.execute(updatesql);
							if (StringUtils.isEmpty(desc)) {
									OrderPreCreateCtnStandardReq requ = new OrderPreCreateCtnStandardReq();
									requ.setMapReq(map);
									client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
									resp_order_pre = client.execute(requ,OrderPreCreateCtnStandardResp.class);
								} else {
								resp_order_pre.setCODE("1002");
								resp_order_pre.setDESC(desc);
							}
						}
					} else {
						resp.setRESP_CODE("0");
						resp.setRESP_DESC("请求报文参数异常");
					}
				} else {
					resp.setRESP_CODE("0");
					resp.setRESP_DESC("请求报文为空");
				}
			} catch (ApiException e) {
				resp.setRESP_CODE("0");
				resp.setRESP_DESC("异常错误1："+ (e.getMessage() == null ? e.toString() : e.getMessage()));
				e.printStackTrace();
				logger.info(e, e);
			}
			//
			updatesql = cacheUtil.getConfigInfo("order_intent_min_update");
			Map<String, Object> respMap = new HashMap<String, Object>();
			if ("1".equals(resp.getRESP_CODE())) {
				respMap.put("DATA", resp.getDATA());
				respJsonStr=resp.getRESP_DESC();
				updatesql = updatesql.replace("{order_id}", order_id);
				updatesql = updatesql.replace("{done_status}", "2");
				updatesql = updatesql.replace("{num}", num);
				updatesql = updatesql.replace("{exception_desc}",resp.getRESP_DESC());
				// 执行更新语句
				baseDaoSupport.execute(updatesql);
				// 向中间表历史表插入数据
				String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
				copysql = copysql.replace("{order_id}", order_id);
				baseDaoSupport.execute(copysql);
				// 删除中间表数据
				String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
				deletesql = deletesql.replace("{order_id}", order_id);
				baseDaoSupport.execute(deletesql);
				respMap.put("RESP_CODE", resp.getRESP_CODE());
				respMap.put("RESP_DESC", resp.getRESP_DESC());
			}else if ("0".equals(resp_bee.getResp_code())) {
				respMap.put("DATA", resp.getDATA());
				respJsonStr=resp_bee.getResp_msg();
				updatesql = updatesql.replace("{order_id}", order_id);
				updatesql = updatesql.replace("{done_status}", "2");
				updatesql = updatesql.replace("{num}", num);
				updatesql = updatesql.replace("{exception_desc}",resp_bee.getResp_msg());
				// 执行更新语句
				baseDaoSupport.execute(updatesql);
				// 向中间表历史表插入数据
				String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
				copysql = copysql.replace("{order_id}", order_id);
				baseDaoSupport.execute(copysql);
				// 删除中间表数据
				String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
				deletesql = deletesql.replace("{order_id}", order_id);
				baseDaoSupport.execute(deletesql);
				respMap.put("RESP_CODE", resp_bee.getResp_code());
				respMap.put("RESP_DESC", resp_bee.getResp_msg());
			} else if ("0000".equals(resp_order_pre.getCODE())) {
				respJsonStr=resp_order_pre.getDESC();
				respMap.put("DATA", resp_order_pre.getDATA());
				updatesql = updatesql.replace("{order_id}", order_id);
				updatesql = updatesql.replace("{done_status}", "2");
				updatesql = updatesql.replace("{num}", num);
				updatesql = updatesql.replace("{exception_desc}",resp_order_pre.getDESC());
				// 执行更新语句
				baseDaoSupport.execute(updatesql);
				// 向中间表历史表插入数据
				String copysql = cacheUtil.getConfigInfo("order_intent_min_his_copy");
				copysql = copysql.replace("{order_id}", order_id);
				baseDaoSupport.execute(copysql);
				// 删除中间表数据
				String deletesql = cacheUtil.getConfigInfo("order_intent_min_delete");
				deletesql = deletesql.replace("{order_id}", order_id);
				baseDaoSupport.execute(deletesql);
				respMap.put("RESP_CODE", resp.getRESP_CODE());
				respMap.put("RESP_DESC", resp.getRESP_DESC());
			} else {
				updatesql = updatesql.replace("{num}", num);
				updatesql = updatesql.replace("{done_status}", "3");
				 if ("100104".equals(order_type)) {
						updatesql = updatesql.replace("{exception_desc}",resp.getRESP_DESC());
						respJsonStr=resp.getRESP_DESC();
						respMap.put("RESP_CODE", resp.getRESP_CODE());
						respMap.put("RESP_DESC", resp.getRESP_DESC());
					}else if ("100104".equals(order_type)&&money>0&&"1".equals(PAY_TAG)) {
					updatesql = updatesql.replace("{exception_desc}",resp_bee.getResp_msg());
					respJsonStr=resp_bee.getResp_msg();
					respMap.put("RESP_CODE", resp_bee.getResp_code());
					respMap.put("RESP_DESC", resp_bee.getResp_msg());
					} else{
					updatesql = updatesql.replace("{exception_desc}",resp_order_pre.getDESC());
					respJsonStr=resp_order_pre.getDESC();
					respMap.put("RESP_CODE", resp_order_pre.getCODE());
					respMap.put("RESP_DESC", resp_order_pre.getDESC());
					}
				updatesql = updatesql.replace("{order_id}", order_id);
				baseDaoSupport.execute(updatesql);
			}
		//	respMap.put("RESP_CODE", resp.getRESP_CODE());
		//	respMap.put("RESP_DESC", resp.getRESP_DESC());
			// 将返回数据转成json格式的字符串
			respJsonStr = JSONObject.fromObject(respMap).toString();
			// 返回接口调用结果
			logger.info("[OrderDistributeServlet]-response:" + respJsonStr);

		}
		return respJsonStr;
	}
	
	//拼接总部蜂行动收单报文
		public JSONObject getBeeMakeupMessage(String req){
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			Map<String, Object> mall_req = new HashMap<String, Object>();
			Map<String, Object> cust_info = new HashMap<String, Object>();
			Map<String, Object> extMap = new HashMap<String, Object>();
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			String sql = "";
			Map<?, ?> req_map = (Map<?, ?>) JSON.parse(req);
			Map<?, ?> ORDER = (Map<?, ?>) req_map.get("ORDER");
			String ORDER_ID = Const.getStrValue(ORDER, "ORDER_ID");
			String CHANNEL_ID = Const.getStrValue(ORDER, "CHANNEL_ID");// 渠道编码
			String CHANNEL_TYPE = Const.getStrValue(ORDER, "CHANNEL_TYPE");// 渠道类型
			String CREATE_DATE = Const.getStrValue(ORDER, "CREATE_DATE");// 下单时间
			CREATE_DATE=CREATE_DATE.replace("-","");
			CREATE_DATE=CREATE_DATE.replace(":","");
			CREATE_DATE=CREATE_DATE.replace(" ","");
			
			mall_req.put("out_order_id", ORDER_ID);
			mall_req.put("channel_id", CHANNEL_ID);
			mall_req.put("channeltype", CHANNEL_TYPE);
			mall_req.put("source_from", "100104");
			mall_req.put("source_system", "AS");
			mall_req.put("create_time", CREATE_DATE);
			mall_req.put("is_realname", "1");

			if (null != ORDER.get("DEVELOPER_INFO")) {
				Map DEVELOPER_INFO = (Map) ORDER.get("DEVELOPER_INFO");// 发展人信息
				String DEVELOP_DEPART_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DEPART_ID");// 发展人所在部门编码
				String top_share_userid = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DEPART_ID");// 发展人所在部门编码
				String DEVELOP_STAFF_ID = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_ID");// 发展员工
				String seed_user_id = Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_ID");// 发展员工
				
				extMap.put("top_share_userid", top_share_userid);
				extMap.put("seed_user_id", seed_user_id);// 
				//100104
				mall_req.put("receive_system", "10092");//接收方标识
				mall_req.put("develop_departId", DEVELOP_DEPART_ID);// 发展点
				mall_req.put("develop_code", DEVELOP_STAFF_ID);// 发展员工
			}
			if (null != ORDER.get("COMMON_INFO")) {
				List<Map> COMMON_INFO = (List) ORDER.get("COMMON_INFO");// 公共信息
				for (Map COMMON : COMMON_INFO) {
					String DEPART_ID = Const.getStrValue(COMMON, "DEPART_ID");// 操作部门
					String STAFF_ID = Const.getStrValue(COMMON, "STAFF_ID");// 操作员工
					mall_req.put("deal_office_id", DEPART_ID);// 操作部门
					mall_req.put("deal_operator", STAFF_ID);// 操作员工
				}
				mall_req.put("order_deal_method", "1");// 订单受理方式	1--线上，2--线下
			}
			if (null != ORDER.get("FEEPAY_INFO")) {
				List<Map> FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");// 支付
				for (Map FEEPAY : FEEPAY_INFO) {
					String MONEY = Const.getStrValue(FEEPAY, "MONEY");// 支付金额
					MONEY=MONEY.substring(0,MONEY.length()-2);
					String pay_id = Const.getStrValue(FEEPAY, "PAY_ID");// 支付流水
					
					mall_req.put("serial_no", pay_id);
					mall_req.put("ess_money", MONEY);
					mall_req.put("pay_money", MONEY);
					mall_req.put("pay_method", "WOPAY");
				}
			}
			if (null != ORDER.get("CUST_INFO")) {
				Map CUST_INFO = (Map) ORDER.get("CUST_INFO");// 办理客户信息
				String CUST_TYPE = Const.getStrValue(CUST_INFO, "CUST_TYPE");// 新老客户标识,0：新增客户,1：老客户
				String CUST_NAME = Const.getStrValue(CUST_INFO, "CUST_NAME");// 客户名称
				String PSPT_ID = Const.getStrValue(CUST_INFO, "PSPT_ID");// 证件id
				String PSPT_ADDR = Const.getStrValue(CUST_INFO, "PSPT_ADDR");// 证件地址
				String CONTACT_NAME = Const.getStrValue(CUST_INFO, "CONTACT_NAME");// 联系人（不能全数字）
				mall_req.put("is_old", CUST_TYPE);
				mall_req.put("certi_type", "SFZ18");// 证件类型
				mall_req.put("cust_name", CUST_NAME);// 客户名称
				mall_req.put("certi_num", PSPT_ID);// 证件号码
				
				cust_info.put("cert_type", "11");// 联系人（不能全数字）
				cust_info.put("customer_name", CONTACT_NAME);// 联系人电话>6位
				cust_info.put("cert_addr", PSPT_ADDR);
				cust_info.put("cert_num", PSPT_ID);// 证件号码
			}
			if (null != ORDER.get("DELIVER_INFO")) {
				Map DELIVER_INFO = (Map) ORDER.get("DELIVER_INFO");// 交付信息
				String POST_NAME = Const.getStrValue(DELIVER_INFO, "POST_NAME");// 收件人姓名
				String POST_MOBILE_1 = Const.getStrValue(DELIVER_INFO, "POST_MOBILE_1");// 收件人证件联系电话1
				String POST_PROVINCE = Const.getStrValue(DELIVER_INFO, "POST_PROVINCE");// 收件省
				String POST_CITY = Const.getStrValue(DELIVER_INFO, "POST_CITY");// 收件市
				String POST_DIST = Const.getStrValue(DELIVER_INFO, "POST_DIST");// 收件区县
				String POST_ADDRESS = Const.getStrValue(DELIVER_INFO, "POST_ADDRESS");// 详细地址
				
				mall_req.put("ship_name", POST_NAME);// 联系人电话>6位
				mall_req.put("ship_phone", POST_MOBILE_1);// 联系人电话>6位
				if ("36".equals(POST_PROVINCE)) {
					mall_req.put("ship_province", "330000");// 省份编码转换
				} else {
					mall_req.put("ship_province", POST_PROVINCE);// 省份编码转换
				}
				sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_CITY_CODE'";
				list = baseDaoSupport.queryForList(sql);
				// 正式环境只保留浙江省的地市，测试数据是全的
				for (Map<String, String> map : list) {
					if (POST_CITY.equals(map.get("pkey"))) {
						POST_CITY = map.get("pname");
						break;
					}
				}
				String county_sql="select rel.field_value from es_busicty a "
						+ "left join es_dc_public_dict_relation rel on a.countyid=rel.other_field_value "
						+ "where a.hq_countyid='"+POST_DIST+"' and rel.stype='county'";
				String county=baseDaoSupport.queryForString(county_sql);
				county=county.replace("ZJ","");
				mall_req.put("ship_city", POST_CITY);// 收件市
				mall_req.put("ship_county", county);// 收件区县
				mall_req.put("district", county);// 区县
				mall_req.put("ship_addr", POST_ADDRESS);// 收货详细地址

			}
			if (null != ORDER.get("ORDER_LINE")) {
				List<Map> ORDER_LINE = (List) ORDER.get("ORDER_LINE");// 订单行信息
				for (Map LINE : ORDER_LINE) {
					String SERIAL_NUMBER = Const.getStrValue(LINE, "SERIAL_NUMBER");// 业务受理号码
					mall_req.put("acc_nbr", SERIAL_NUMBER);
					if (null != LINE.get("OFFER_INFO")) {
						List<Map> OFFER_INFO = (List) LINE.get("OFFER_INFO");// 商品节点
						for (Map OFFER : OFFER_INFO) {
							String OFFER_ID = Const.getStrValue(OFFER, "OFFER_ID");// 商品标识
							sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_GOODS'";
							list = baseDaoSupport.queryForList(sql);
							for (Map<String, String> map : list) {
								if (OFFER_ID.equals(map.get("pkey"))) {
									OFFER_ID = map.get("pname");
									break;
								}
							}
							mall_req.put("prod_offer_code", OFFER_ID);
							String OFFER_NAME = Const.getStrValue(OFFER, "OFFER_NAME");// 商品名称
							mall_req.put("prod_offer_name", OFFER_NAME);// 商品名称
						}
					}
					if (null != LINE.get("MPHONE_INFO")) {
						Map MPHONE_INFO = (Map) LINE.get("MPHONE_INFO");// 号码节点
						String NUM_CITY = Const.getStrValue(MPHONE_INFO, "NUM_CITY");// 0571
						// list = dcPublicCache.getList("DIC_FXD_CITY_CODE");
						sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_CITY_CODE'";
						list = baseDaoSupport.queryForList(sql);
						// 正式环境只保留浙江省的地市，测试数据是全的
						for (Map<String, String> map : list) {
							if (NUM_CITY.equals(map.get("pkey"))) {
								NUM_CITY = map.get("pname");
								break;
							}
						}
						mall_req.put("order_city_code", NUM_CITY);// 地市编码转换
					}
					mall_req.put("cust_info", cust_info);
					mall_req.put("extMap", extMap);
				}
			}
			Map<Object,Object> map=new HashMap<Object, Object>();
			map.put("mall_req", mall_req);
			JSONObject jsonObject = JSONObject.fromObject(map);
			return jsonObject;
		}
	
	public OrderCtnResp saveManualOrder(Map manualOrder, String rpc_type) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String essMoney = Const.getStrValue(manualOrder, "ess_money");
		String payMoney = Const.getStrValue(manualOrder, "pay_money");
		Map reqMap = new HashMap();
		String out_tid = Const.getStrValue(manualOrder, "out_order_id");
		
		out_tid = StringUtils.isEmpty(out_tid) ? "ZJ" + SequenceTools.getShort18PrimaryKey() : out_tid;
		reqMap.put("serial_no", UUID.randomUUID());
		reqMap.put("order_id", out_tid);
		reqMap.put("source_system", Const.getStrValue(manualOrder, "source_system")); // 发起方系统标识，单点集成系统传递
		reqMap.put("receive_system", "10011");
		reqMap.put("source_from_system", Const.getStrValue(manualOrder, "source_system")); // 订单来源系统（大类）
		reqMap.put("source_from", Const.getStrValue(manualOrder, "source_from")); // 订单来源（小类）
		reqMap.put("time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("development_code", "");
		reqMap.put("order_type", "1"); // 默认订购
		reqMap.put("status", "00"); // 订单状态，默认未知状态
		reqMap.put("platform_status", "已支付"); // 填各自平台的订单状态：是否已支付等
		reqMap.put("create_time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("pay_time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("ess_money", essMoney);
		reqMap.put("busi_money", essMoney);
		reqMap.put("order_heavy", "0");
		reqMap.put("order_disacount", "0");
		reqMap.put("order_comment", "");
		reqMap.put("order_amount", payMoney); // 订单总价
		
		String ship_province =Const.getStrValue(manualOrder, "ship_province");
		
		// 默认330000  
		if(org.apache.commons.lang.StringUtils.isBlank(ship_province)) {
			reqMap.put("order_provinc_code", "330000");
		}else{
			reqMap.put("order_provinc_code", Const.getStrValue(manualOrder, "ship_province"));
		}
		
		reqMap.put("order_city", Const.getStrValue(manualOrder, "order_city_code"));
		reqMap.put("order_city_code", Const.getStrValue(manualOrder, "order_city_code"));
		reqMap.put("abnormal_status", "正常");
		reqMap.put("pay_money", payMoney);
		reqMap.put("alipay_id", "");// 外部支付账号
		if (StringUtil.equals(Const.getStrValue(manualOrder, "source_from"), "10071")) {
			reqMap.put("pay_type", "XCZF");
		} else {
			reqMap.put("pay_type", "ZXZF");// 支付类型，默认在线支付
		}
		reqMap.put("payment_type", Const.getStrValue(manualOrder, "pay_method"));
		reqMap.put("channel_id", StringUtil.isEmpty(Const.getStrValue(manualOrder, "channelId")) ? "0000" : Const.getStrValue(manualOrder, "channelId")); // 渠道ID
		reqMap.put("chanel_name", "手工录入"); // 渠道名称
		/*
		 * 渠道标记： 1 传统营业厅 2 电话营销 3 非集团合作直销 4 非集团自有直销 5 集客 6 家客直销 7 宽固代理 8 社会渠道 9
		 * 社会微厅 10 网盟 11 异业联盟 12 员工渠道 13 自营渠道 14 自营微厅 15 其他
		 */
		reqMap.put("channel_mark", "13"); // 渠道标记，默认自营渠道
		reqMap.put("channel_type", Const.getStrValue(manualOrder, "channelType"));
		reqMap.put("develop_departId", Const.getStrValue(manualOrder, "develop_departId"));
		reqMap.put("bss_operator", ""); // BSS工号
		reqMap.put("bss_operator_name", ""); //
		reqMap.put("oss_operator", "GD001838"); // 订单支撑系统工号，如果不为空，订单标准化后使用该工号锁定订单
		reqMap.put("is_examine_card", "0"); // 是否需要校验证件照，默认已经手工校验
		reqMap.put("shipping_company", ""); // 物流公司编码
		reqMap.put("shipping_type", "EMS"); // 配送方式，默认平邮
		reqMap.put("shipping_time", "NOLIMIT");
		reqMap.put("ship_area", "");
		reqMap.put("ship_city", StringUtil.isEmpty(Const.getStrValue(manualOrder, "ship_city"))  ? "330100" : Const.getStrValue(manualOrder, "ship_city")); // 收货地市，订单补录走独立生产模式，默认杭州
		
		reqMap.put("ship_country", StringUtil.isEmpty(Const.getStrValue(manualOrder, "ship_county")) ? "330108" : Const.getStrValue(manualOrder, "ship_county")); // 收货区县，订单补录走独立生产模式，默认滨江区
		reqMap.put("ship_name", Const.getStrValue(manualOrder, "ship_name")); // 收货人姓名
		reqMap.put("ship_phone", Const.getStrValue(manualOrder, "ship_phone")); // 收货人电话
		reqMap.put("ship_addr", Const.getStrValue(manualOrder, "ship_addr"));
		reqMap.put("logi_no", Const.getStrValue(manualOrder, "logi_no"));
		reqMap.put("delivery_status", "已发货"); // 发货状态，默认已发货
		reqMap.put("shipping_amount", "0"); // 应收运费，默认0
		reqMap.put("n_shipping_amount", "0"); // 实收运费，默认0
		reqMap.put("shipping_quick", "02"); // 是否闪电送，默认否
		reqMap.put("name", Const.getStrValue(manualOrder, "cust_name")); // 买家名称cust_name
		reqMap.put("uname", Const.getStrValue(manualOrder, "cust_name")); // 买家昵称
		reqMap.put("uid", "510000"); // 买家标识
		
		reqMap.put("evdo_num",Const.getStrValue(manualOrder, "evdo_num"));
		//和宽带的用户种类分开存
		reqMap.put("user_kind",Const.getStrValue(manualOrder, "user_kind"));
		String is_examine_card = ZjEcsOrderConsts.IS_DEFAULT_VALUE;
		String isRealName = Const.getStrValue(manualOrder, "is_realname");
		if (ZjEcsOrderConsts.IS_DEFAULT_VALUE.equals(isRealName)) {
			is_examine_card = ZjEcsOrderConsts.NO_DEFAULT_VALUE;
		}
		reqMap.put("is_realname", isRealName);
		reqMap.put("is_examine_card", is_examine_card); // 是否需要校验身份证，如果已实名则不需要，未实名则需要校验
		reqMap.put("is_to4g", "0"); // 是否2G3G转4G，
		reqMap.put("is_deal", "1"); // 是否已办理完成
		
		reqMap.put("service_remarks", Const.getStrValue(manualOrder, "service_remarks"));//订单备注
		
		reqMap.put("buyer_message", Const.getStrValue(manualOrder, "buyer_message")); // 买家留言
		reqMap.put("iccid", Const.getStrValue(manualOrder, "iccid")); // ICCID
		reqMap.put("out_office", Const.getStrValue(manualOrder, "deal_office_id")); // 操作点
		reqMap.put("out_operator", Const.getStrValue(manualOrder, "deal_operator")); // 操作员
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_pay"))) {
			reqMap.put("is_pay", Const.getStrValue(manualOrder, "is_pay")); //
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "couponbatchid"))) {
			reqMap.put("couponbatchid", Const.getStrValue(manualOrder, "couponbatchid")); //
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_new"))) {
            reqMap.put("is_new", Const.getStrValue(manualOrder, "is_new")); //
        }
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_blankcard"))) {
            reqMap.put("is_blankcard", Const.getStrValue(manualOrder, "is_blankcard")); //
        }
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "develop_code"))) {
			reqMap.put("develop_code", Const.getStrValue(manualOrder, "develop_code"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "develop_name"))) {
			reqMap.put("develop_name", Const.getStrValue(manualOrder, "develop_name"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "group_code"))) {
			reqMap.put("group_code", Const.getStrValue(manualOrder, "group_code")); //
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "customer_type"))) {
			manualOrder.put("CustomerType", Const.getStrValue(manualOrder, "customer_type")); //
		}
		reqMap.put("user_kind",Const.getStrValue(manualOrder, "user_kind"));
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "group_name"))) {
			reqMap.put("group_name", Const.getStrValue(manualOrder, "group_name")); //
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "cust_id"))) {
			reqMap.put("cust_id", Const.getStrValue(manualOrder, "cust_id"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_flag"))) {
			reqMap.put("certify_flag", Const.getStrValue(manualOrder, "certify_flag"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_type"))) {
			reqMap.put("certify_cert_type", Const.getStrValue(manualOrder, "certify_cert_type")); //
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_num"))) {
			reqMap.put("certify_cert_num", Const.getStrValue(manualOrder, "certify_cert_num"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cust_name"))) {
			reqMap.put("certify_cust_name", Const.getStrValue(manualOrder, "certify_cust_name"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_addr"))) {
			reqMap.put("certify_cert_addr", Const.getStrValue(manualOrder, "certify_cert_addr"));
		}

         // 靓号信息
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "nice_info"))) {
			reqMap.put("nice_info", Const.getStrValue(manualOrder, "nice_info"));
		}
		
		reqMap.put("sale_mod_type", Const.getStrValue(manualOrder, "saleModType"));
		reqMap.put("marking_tag", Const.getStrValue(manualOrder, "markingTag"));
		reqMap.put("district_code", Const.getStrValue(manualOrder, "district"));
		reqMap.put("channelid", Const.getStrValue(manualOrder, "channelId"));
		String is_aop = Const.getStrValue(manualOrder, "is_aop");
		reqMap.put("is_aop", is_aop);
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "old_cust_id"))) {
			reqMap.put("old_cust_id", Const.getStrValue(manualOrder, "old_cust_id"));
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "rel_order_id"))) {
			reqMap.put("intent_order_id", Const.getStrValue(manualOrder, "rel_order_id"));
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "order_deal_method"))) {
			reqMap.put("order_deal_method", Const.getStrValue(manualOrder, "order_deal_method"));
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "flow_code"))) {
			reqMap.put("flow_code", Const.getStrValue(manualOrder, "flow_code"));
		}
		
		List orderList = new ArrayList();
		Map goodsMap = new HashMap();
		goodsMap.put("prod_offer_code", Const.getStrValue(manualOrder, "prod_offer_code")); // 商品编码
		goodsMap.put("prod_offer_name", Const.getStrValue(manualOrder, "prod_offer_name")); // 商品名称
		String type_id = null;
		String cat_id = Const.getStrValue(manualOrder, "prod_offer_type");
		CatGetByIdReq req = new CatGetByIdReq();
		req.setCat_id(cat_id);
		CatGetResp catGetResp = client.execute(req, CatGetResp.class);
		Cat cat = catGetResp.getCat();
		if (cat != null) {
			type_id = cat.getType_id();
		}
		goodsMap.put("prod_offer_type", type_id);
		goodsMap.put("offer_disacount_price", "0"); // 优惠价
		goodsMap.put("offer_coupon_price", Const.getStrValue(manualOrder, "pay_money")); // 实收价格
		goodsMap.put("prod_offer_num", "1"); // 商品数量
		goodsMap.put("prod_offer_heavy", "0"); // 商品重量
		goodsMap.put("card_type", "NM"); // 卡类型：大卡小卡纳诺卡
		goodsMap.put("certi_type", Const.getStrValue(manualOrder, "certi_type"));
		goodsMap.put("cust_type", "GRKH"); // 默认个人客户
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "customer_type"))) {
			goodsMap.put("cust_type", Const.getStrValue(manualOrder, "customer_type"));
		}
		goodsMap.put("cust_name", Const.getStrValue(manualOrder, "cust_name"));
		goodsMap.put("certi_num", Const.getStrValue(manualOrder, "certi_num"));
		goodsMap.put("certi_address", "");
		goodsMap.put("invoice_print_type", "3"); // 发票打印方式:1一次性，2分月，3不打印
		goodsMap.put("invoice_no", Const.getStrValue(manualOrder, "invoice_no")); // 发票号码
		goodsMap.put("terminal_num", Const.getStrValue(manualOrder, "terminal_num")); // 终端串号
		goodsMap.put("offer_price", payMoney); // 商品价格
		goodsMap.put("good_no_deposit", "0"); // 靓号预存款
		goodsMap.put("offer_eff_type", Const.getStrValue(manualOrder, "offer_eff_type")); //
		goodsMap.put("package_sale", "0");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (manualOrder.get("cust_info") != null && manualOrder.get("cust_info") instanceof Map) {
			Map cust_info = (Map) manualOrder.get("cust_info");
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_num"))) {
				goodsMap.put("cert_num", Const.getStrValue(cust_info, "cert_num"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_num2"))) {
                goodsMap.put("cert_num2", Const.getStrValue(cust_info, "cert_num2"));
            }
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_addr"))) {
				goodsMap.put("cert_addr", Const.getStrValue(cust_info, "cert_addr"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "customer_name"))) {
				goodsMap.put("customer_name", Const.getStrValue(cust_info, "customer_name"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_effected_date"))) {

				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMddHHmmss");
					java.util.Date tDate = dateFormator.parse(Const.getStrValue(cust_info, "cert_effected_date") + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					goodsMap.put("cert_effected_date", format.format(tDate));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_sex"))) {
				goodsMap.put("cert_sex", Const.getStrValue(cust_info, "cert_sex"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_nation"))) {
				goodsMap.put("cert_nation", Const.getStrValue(cust_info, "cert_nation"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cust_birthday"))) {
				goodsMap.put("cust_birthday", Const.getStrValue(cust_info, "cust_birthday"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_expire_date"))) {
				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMddHHmmss");
					java.util.Date tDate = dateFormator.parse(Const.getStrValue(cust_info, "cert_expire_date") + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					goodsMap.put("cert_expire_date", format.format(tDate));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_issuedat"))) {
				goodsMap.put("certi_issuer", Const.getStrValue(cust_info, "cert_issuedat"));
			}
		}
		String planTitle = Const.getStrValue(manualOrder, "plan_title");
		String oldPlanTitle = Const.getStrValue(manualOrder, "old_Out_plan_name_ess");
		String isChange = "0";
		if (!StringUtils.isEmpty(planTitle) && !StringUtils.isEmpty(oldPlanTitle) && planTitle.equals(oldPlanTitle)) {
			isChange = "1";
		}
		goodsMap.put("is_change", isChange); // 是否变更套餐，如果原套餐名称和当前办理套餐名称不一致，则认为是变更套餐
		goodsMap.put("plan_title", Const.getStrValue(manualOrder, "plan_title")); // 套餐名称
		goodsMap.put("special_busi_type", Const.getStrValue(manualOrder, "special_busi_type")); // 业务类型
		goodsMap.put("old_Out_plan_name_ess", Const.getStrValue(manualOrder, "old_plan_title")); // 老用户原套餐

		List skuParamList = new ArrayList();
		Map skuMap1 = new HashMap();
		skuMap1.put("param_code", "acc_nbr");
		skuMap1.put("param_name", "用户号码");
		skuMap1.put("param_value", Const.getStrValue(manualOrder, "acc_nbr"));
		skuParamList.add(skuMap1);

		Map skuMap2 = new HashMap();
		skuMap2.put("param_code", "is_old");
		skuMap2.put("param_name", "是否老用户");
		skuMap2.put("param_value", Const.getStrValue(manualOrder, "is_old"));
		skuParamList.add(skuMap2);

		Map skuMap3 = new HashMap();
		skuMap3.put("param_code", "if_love_no");
		skuMap3.put("param_name", "是否情侣号");
		skuMap3.put("param_value", "0"); // 默认否
		skuParamList.add(skuMap3);

		Map skuMap4 = new HashMap();
		skuMap4.put("param_code", "net_type");
		skuMap4.put("param_name", "网别");
		skuMap4.put("param_value", "4G"); // 待确认
		skuParamList.add(skuMap4);

		Map skuMap5 = new HashMap();
		skuMap5.put("param_code", "is_goodno");
		skuMap5.put("param_name", "靓号"); // 是否靓号，默认否，选号时传入
		skuMap5.put("param_value", "0");
		skuParamList.add(skuMap5);

		Map skuMap6 = new HashMap();
		skuMap6.put("param_code", "bill_type");
		skuMap6.put("param_name", "账户付费方式");
		skuMap6.put("param_value", "10"); // 默认现金
		skuParamList.add(skuMap6);

		Map skuMap7 = new HashMap();
		skuMap7.put("param_code", "card_type");
		skuMap7.put("param_name", "卡类型");
		skuMap7.put("param_value", "NM");
		skuParamList.add(skuMap7);

		Map skuMap8 = new HashMap();
		skuMap8.put("param_code", "guarantor");
		skuMap8.put("param_name", "担保人");
		skuMap8.put("param_value", "无");
		skuParamList.add(skuMap8);

		Map skuMap9 = new HashMap();
		skuMap9.put("param_code", "bill_mail_type");
		skuMap9.put("param_name", "账单寄送方式");// 账单寄送方式，00：不寄送，01：寄送，默认不寄送
		skuMap9.put("param_value", "00");
		skuParamList.add(skuMap9);

		goodsMap.put("sku_param", skuParamList);
		orderList.add(goodsMap);
		reqMap.put("order_list", orderList);

		if(manualOrder.get("extMap") instanceof Map&&null!=manualOrder.get("extMap")){
			reqMap.put("extMap", manualOrder.get("extMap"));
		}
		Map orderMap = new HashMap();
		orderMap.put("order_req", reqMap);

		String jsonStr = JsonUtil.toJson(orderMap);
		
		MarkTime mark = new MarkTime("saveManualOrder out_order_id="+out_tid);

		OrderCtnReq orderCtnReq = new OrderCtnReq();
		
		String is_update = Const.getStrValue(manualOrder, "is_update");
		
		if("1".equals(is_update)){
			//更新时，设置为更新订单数据的OutServiceCode
			orderCtnReq.setOutServiceCode("update_order_newMallOrderStandard");
		}else if (!StringUtil.isEmpty(rpc_type) && StringUtil.equals("M", rpc_type)) {
			orderCtnReq.setOutServiceCode("create_order_newMallOrderStandardM");
		} else {
			orderCtnReq.setOutServiceCode("create_order_newMallOrderStandard");
		}

		orderCtnReq.setVersion("1.0");
		orderCtnReq.setReqMsgStr(jsonStr);
		orderCtnReq.setFormat("json");
		orderCtnReq.setOutOrderId(out_tid);
		Map<String, Object> reqParamsMap = new HashMap<String, Object>();
		reqParamsMap.put("orderSource", reqMap.get("source_from"));
		orderCtnReq.setReqParamsMap(reqParamsMap);
		
		OrderCtnResp resp = client.execute(orderCtnReq, OrderCtnResp.class);
		
		mark.markEndTime();
		
		if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
			return resp;
		}
		
		resp.setBase_order_id(out_tid);
		
		OrderTreeBusiRequest orderTree = null;
		
		if("1".equals(is_update)){
			//更新的订单不会更新原有订单的外部单号，这时外部单号为后台生成的意向单的单号
			orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(Const.getStrValue(manualOrder, "rel_order_id"));
		}else{
			orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);
		}

		if (null != orderTree && !StringUtils.isEmpty(orderTree.getOrder_id())) {
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			if (StringUtil.equals(is_aop, "1")) {
				orderItemExtBusiRequest.setBssOrderId(Const.getStrValue(manualOrder, "cbss_order_id"));
			} else if (StringUtil.equals(is_aop, "2")) {
				orderItemExtBusiRequest.setBssOrderId(Const.getStrValue(manualOrder, "bss_order_id"));
			}
			orderItemExtBusiRequest.store();
		}
		return resp;
	}
}
