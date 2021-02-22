package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.outter.inf.service.DBUtils;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.server.sendsms.SmsSend;

public class OrderMonitorSmsTimer {

	private Logger logger = Logger.getLogger(OrderMonitorSmsTimer.class);
	@Resource
	DBUtils dbUtils;

	public void run() {
		//商城订单同步失败告警
		//mallOrderMonitor();
		//自动化订单同步失败告警
		//starandOrderMonitor();
		//读取告警短信并发送
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
		    return ;
		}
		
		sendMonitorSms();
	}
	
	/**
	 * 发送短信
	 */
	private synchronized void sendMonitorSms(){
		try {
			//失败订单数据
			String sql = "select DC_SQL from es_dc_sql c where dc_name = 'SMS_SEND'";
			List orderList = dbUtils.queryListResult(sql);
			if (null != orderList && orderList.size() > 0) {
				//logger.info("send monitor sms,size:" + orderList.size());
				for (int i = 0; i < orderList.size(); i++) {
					{
						Map map = (Map)orderList.get(i);
						//logger.info("send monitor sms,sms_content:" + map.get("sms_content").toString() + ",sms_user:" + map.get("sms_user").toString());
						SmsSend.sendMonitorSms(map.get("sms_user").toString(), map.get("sms_content").toString());
						
						//标记为已发送
						dbUtils.updateSmsFlag(map.get("sms_id").toString());
					}
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 商城订单同步失败监控
	 */
	private void mallOrderMonitor(){
		try {
			//失败订单数据
			String sql = "select DC_SQL from es_dc_sql c where dc_name = 'ORDER_MONITOR'";
			List orderList = dbUtils.queryListResult(sql);
			
			//告警短信接收人信息
			sql = "select DC_SQL from es_dc_sql c where dc_name = 'ORDER_RECEIVER'";
			List smsReceiver = dbUtils.queryListResult(sql);
			String tid = "";
			String order_from = "";
			String create_time = "";
			String receiver = "";
			String smsTemplate = "";
			String mobile_no = "";
			String []smsUser = null;
			
			if (null != orderList && orderList.size() > 0 && null != smsReceiver && smsReceiver.size() > 0) {
				for (int i = 0; i < orderList.size(); i++) {
					try {
						Map order = (Map)orderList.get(i);
						tid = order.get("tid").toString();
						order_from = order.get("order_from").toString();
						String order_from_2 = dbUtils.querySqlResult("select c.field_desc from es_mall_config c where c.field_name = 'source_from' and field_value = '"+order_from+"'");
						if (MallUtils.isNotEmpty(order_from_2)) {
							order_from = order_from_2;
						}
						create_time = order.get("create_time").toString();
						for (int j = 0; j < smsReceiver.size(); j++) {
							Map recMap = (Map)smsReceiver.get(j);
							mobile_no = recMap.get("mobile_no").toString();
							//receiver = recMap.get("receiver").toString();
							smsTemplate = recMap.get("msg").toString();
							smsUser = mobile_no.split("[，|,]");
							//smsTemplate = smsTemplate.replaceAll("receiver", receiver);
							smsTemplate = smsTemplate.replaceAll("order_from", order_from);
							smsTemplate = smsTemplate.replaceAll("create_time", create_time);
							smsTemplate = smsTemplate.replaceAll("tid", tid);
							for (int k = 0; k < smsUser.length; k++) {
								SmsSend.sendMonitorSms(smsUser[k], smsTemplate);
							}
						}
						
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 自动化同步失败监控
	 */
	private void starandOrderMonitor(){
		try {
			//失败订单数据
			String sql = "select DC_SQL from es_dc_sql c where dc_name = 'STARAND_MONITOR'";
			List orderList = dbUtils.queryListResult(sql);
			
			//告警短信接收人信息
			sql = "select DC_SQL from es_dc_sql c where dc_name = 'STARAND_RECEIVER'";
			List smsReceiver = dbUtils.queryListResult(sql);
			String tid = "";
			String order_from = "";
			String create_time = "";
			String receiver = "";
			String smsTemplate = "";
			String mobile_no = "";
			String []smsUser = null;
			
			if (null != orderList && orderList.size() > 0 && null != smsReceiver && smsReceiver.size() > 0) {
				for (int i = 0; i < orderList.size(); i++) {
					try {
						Map order = (Map)orderList.get(i);
						tid = order.get("tid").toString();
						order_from = order.get("order_from").toString();
						String order_from_2 = dbUtils.querySqlResult("select c.field_desc from es_mall_config c where c.field_name = 'source_from' and field_value = '"+order_from+"'");
						if (MallUtils.isNotEmpty(order_from_2)) {
							order_from = order_from_2;
						}
						create_time = order.get("create_time").toString();
						for (int j = 0; j < smsReceiver.size(); j++) {
							Map recMap = (Map)smsReceiver.get(j);
							mobile_no = recMap.get("mobile_no").toString();
							//receiver = recMap.get("receiver").toString();
							smsTemplate = recMap.get("msg").toString();
							smsUser = mobile_no.split("[，|,]");
							//smsTemplate = smsTemplate.replaceAll("receiver", receiver);
							smsTemplate = smsTemplate.replaceAll("order_from", order_from);
							smsTemplate = smsTemplate.replaceAll("create_time", create_time);
							smsTemplate = smsTemplate.replaceAll("tid", tid);
							for (int k = 0; k < smsUser.length; k++) {
								SmsSend.sendMonitorSms(smsUser[k], smsTemplate);
							}
						}
						
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
}
