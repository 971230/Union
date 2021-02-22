package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.order.req.OrderQueueCardActionLogReq;
import zte.net.ecsord.utils.PCWriteCardTools;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;

import consts.ConstsCore;

/**
 * 开户写卡连续失败与最大阀值监控任务
 * @author duan.shaochu
 *
 */
public class CardQueueFailMonitorTimer {
	private static Logger logger = Logger.getLogger(CardQueueFailMonitorTimer.class);
	@Resource
	private ICoQueueManager coQueueManager;
	
	private static String sql = "select c.queue_no,c.queue_desc,c.phones,case when CONTI_OPEN_FAILED_NUM > c.OPEN_MAX_FAILED_NUM "
			+ " then '0' else '1' end fail_type from ES_QUEUE_MANAGER c where c.source_from = ?"
			+ " and (CONTI_OPEN_FAILED_NUM > c.OPEN_MAX_FAILED_NUM or "
			+ " CONTI_CARD_FAILED_NUM > CARD_MAX_FAILED_NUM) and c.QUEUE_SWITCH = 0";

	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List list = support.queryForList(sql, ManagerUtils.getSourceFrom());
		if(null != list && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Map m = (Map)list.get(i);
				String fail_type = m.get("fail_type").toString();
				String queue_no = m.get("queue_no").toString();
				String queue_desc = m.get("queue_desc").toString();
				String phones = m.get("phones").toString();
				String deal_reason = "开户连续失败数量大于阀值,关闭开关";
				if(StringUtils.equals("1", fail_type)){
					deal_reason = "写卡连续失败数量大于阀值,关闭开关";
				}
				//关闭队列开关
				PCWriteCardTools.modifyQueueSwitch(queue_no,deal_reason,"1");
				
				//sendSmsByFail(queue_no, queue_desc, phones);
				sendSms(queue_no, queue_desc, phones);

				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_07);
				logReq.setAction_desc(deal_reason + ",定时任务");
				logReq.setQueue_code(queue_no);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
			}
		}
	}
	
	/**
	 * 浙江发送短信
	 * @param queue_code
	 * @param queue_desc
	 * @param phones
	 */
	public void sendSms(String queue_code , String queue_desc , String phones) {
		try {
			if (StringUtils.isEmpty(phones)) {
				return;
			}
			String[] phoneList = phones.split("\\|");
			if (phoneList.length <= 0) {
				return;
			}
			for (String phone : phoneList) {
				SmsTempleteRequest smsTemp = new SmsTempleteRequest();
				smsTemp.setCode("WRITE_CARD_QUEUE");//短信模板，WRITE_CARD_QUEUE  ,配置在表es_sms_templete
				smsTemp.setAccNbr(phone);//接收号码
				Map<String, String> params = new HashMap<String, String>();
				params.put("queue_code", queue_code);
				params.put("queue_desc", queue_desc);
				smsTemp.setParams(params);
				ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				SmsTempleteResponse smsTempResp = zteclient.execute(smsTemp, SmsTempleteResponse.class);
				if(smsTempResp!=null&&ConstsCore.ERROR_SUCC.equals(smsTempResp.getError_code())){
					 logger.info("写卡队列告警短信发送到指定手机用户成功!");
				}else{
					 logger.info("写卡队列告警短信发送到指定手机用户失败!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 广东发送短信
	 * @param queue_code
	 * @param queue_desc
	 * @param phones
	 */
	public void sendSmsByFail(String queue_code , String queue_desc , String phones) {
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String template = cacheUtil.getConfigInfo("WRITE_CARD_QUEUE_TEMPLATE");
			if (!StringUtils.isEmpty(template)) {
				template = template.replaceAll("\\{queue_code\\}", queue_code);
				template = template.replaceAll("\\{queue_desc\\}", queue_desc);
			}
			
			if (StringUtils.isEmpty(phones)) {
				return;
			}
			String[] phoneList = phones.split("\\|");
			if (phoneList.length <= 0) {
				return;
			}
			for (String phone : phoneList) {
				JSONObject json = new JSONObject();
				json.put("phone", phone);
				json.put("content", template);
				json.put("source_system", "10011");
				json.put("priority_level", "1");
				json.put("repeat_time", "9");
				json.put("send_gate_type", "1");
				
				// 添加到定时任务队列
				CoQueue queBak = new CoQueue();
				queBak.setService_code("CO_EXCEPTION_SMS_ZB");			//service_code改为老系统
				queBak.setCo_id("");
				queBak.setCo_name("写卡机队列自动关闭短信通知");
				queBak.setObject_id(queue_code);
				queBak.setObject_type("DINGDAN");
				queBak.setContents(json.toString());
				queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
				coQueueManager.add(queBak);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
