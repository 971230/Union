package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.OrderQueryParams;

public interface IOrdIntentManager {

	/**
	 * 新增意向单操作记录
	 * 
	 * @return
	 */
	public String addIntentRecord(Map<String, Object> map);

	/**
	 * 意向单客户信息
	 * 
	 * @return
	 */
	public Map<String, Object> getAcceptDetail(String order_id);

	/**
	 * 意向单处理保存
	 * 
	 * @return
	 */
	public Map<String, Object> saveIntent(Map<String, Object> map);
	/**
	 * 意向单处理保存
	 * 
	 * @return
	 */
	//public Map<String, Object> updateIntentContact(Map<String, Object> map);

	/**
	 * 商品预定回填接口
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult orderReceiveBack(String order_id);

	/**
	 * 订单撤单，意向单结束，工单处理失败
	 * 
	 * @param order_id
	 * @return
	 */
	public void cancelIntent(String order_id);

	/**
	 * 意向单回收
	 * 
	 * @param order_id
	 */
	public String intentBack(String order_id);

	/**
	 * 是否省中台工号
	 * 
	 * @param order_id
	 * @return
	 */
	public String isSZT(String order_id);

	/**
	 * 根据订单编号查询意向单信息
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List qryOrderIntentByOrderId(String order_id) throws Exception;

	/**
	 * 订单信息回填接口
	 * 
	 * @param order_id
	 * @return
	 */
	public BusiDealResult orderInfoBackfill(String order_id) throws Exception;

	/**
	 * 短信模版页面
	 * 
	 * @param order_id
	 * @return
	 */
	public Page smsTemplateFormQuery(OrderQueryParams params, int pageNo, int pageSize);
	/**
	 * 选择短信模版查询
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page selectSMSFormQuery(OrderQueryParams params, int pageNo, int pageSize);

	public List<Map<String, Object>> getSmsStatusList();

	public List<Map<String, Object>> getSmsLevelList();

	/**
	 * 新增短信模版
	 * 
	 * @param order_id
	 */
	public String addSMS(String sms_template) throws Exception;
	
	public String banSMS(String sms_id) throws Exception;
	public String pickSMS(String sms_id) throws Exception;
	public String deleteSMS(String sms_id) throws Exception;
	
	/**
	 * 修改短信模版
	 * 
	 * @param order_id
	 */
	public String editSMS(Map<String, Object> map) throws Exception;
	
	/**
	 * 发送短信内容
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String sendSMS(Map<String, Object> map) throws Exception;

	/**
	 * 新增短信模版操作记录
	 * 
	 * @return
	 */
	public void addSmsLogs(Map<String, Object> map) throws Exception;

	/**
	 * 短信模版信息
	 * 
	 * @return
	 */
	public Map<String, Object> getSMSDetail(String sms_id) throws Exception;

	/**
	 * 短信操作记录
	 * 
	 * @param sms_id
	 * @return
	 */
	public List<Map<String, Object>> getSmsLogs(String sms_id) throws Exception;
	
	/**
	 * 短信发送记录
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getSmsSendLogs(String order_id) throws Exception;
	
	/**
	 * 获取订单客户联系号码
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public String getOrderShipTel(String order_id) throws Exception;
	
	/**
	 * 是否为联通号码
	 * @param ship_tel
	 * @return
	 * @throws Exception
	 */
	public Boolean isUnicom(String ship_tel) throws Exception;
	
	public String openSmsSend() throws Exception;
	public String colseSmsSend() throws Exception;
	
	public String beeActionHandle(String order_id) throws Exception;
	/**
	 * 短信异步发送手动执行
	 * @param sms_id
	 * @return
	 * @throws Exception
	 */
	public String smsSendHandle(String sms_id) throws Exception;
	/**
	 * 短信发送定时任务执行
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String smsSendTimer(Map<String, String> map) throws Exception;
	
	
	
}
