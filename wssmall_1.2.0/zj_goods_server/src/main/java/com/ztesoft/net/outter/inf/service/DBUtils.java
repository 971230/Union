package com.ztesoft.net.outter.inf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.cache.CacheMap;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.util.CacheUtils;

public class DBUtils extends BaseSupport {
	
	private Logger logger = Logger.getLogger(DBUtils.class);
	private String sql = "";

	/**
	 * 插入数据
	 * @param tableName
	 * @return
	 */
	public boolean insertOrderData(String tableName , Map fieldsMap){
		boolean flag = false;
		try {
			
			this.baseDaoSupport.insert(tableName, fieldsMap);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.info("记录失败订单数据异常");
			logger.info(e.getMessage(), e);
		}
		return flag;
	}
	
	/**
	 * 判断订单是否存在
	 * @param order_id
	 * @return
	 */
	public boolean queryFailOrder(String order_id){
		boolean flag = false;
		try {
			sql = "select count(*) nums from es_fail_order_msg where out_tid = '"+order_id+"' and rownum < 2";
			Map orderMap = this.baseDaoSupport.queryForMap(sql, null);
			if ("1".equals(orderMap.get("nums").toString())) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			logger.info("记录失败订单数据异常");
			logger.info(e.getMessage(), e);
		}
		return flag;
	}
	
	/**
	 * 更新dealNum值
	 * @param sql
	 * @return
	 */
	public boolean updateOrderDealNum(String order_id,boolean falg){
		boolean flag = false;
		try {
			if (flag) {
				sql = "update es_fail_order_msg set reserve0 = 'successfully',lasttime=sysdate where out_tid = '"+order_id+"'";
			}else {
				sql = "update es_fail_order_msg set dealnum = dealnum + 1,lasttime=sysdate where out_tid = '"+order_id+"'";
			}
			this.baseDaoSupport.update(sql,null);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.info("更新订单的dealNum失败");
			logger.info(e.getMessage(), e);
		}
		return flag;
	}

	
	/**
	 * 查询号码在商品系统是否存在
	 * @param acc_nbr
	 * @return
	 */
	public boolean queryMsisdnExists(String acc_nbr){
		boolean flag = false;
		try {
			String sql = "select count(*) nums from es_no where dn_no = ?";
			int nums = this.baseDaoSupport.queryForInt(sql, acc_nbr);
			if (nums != 0) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 查询告警短信接收人信息
	 * @return
	 */
	public List querySmsMonitorer(){
		List smsList = new ArrayList();
		sql = "select * from es_msg_set";
		try {
			smsList = this.baseDaoSupport.queryForListByMap(sql, null);
		} catch (Exception e) {
			logger.info("获取es_msg_set信息失败.");
			logger.info(e.getMessage(), e);
		}
		return smsList;
	}
	
	/**
	 * 获取订单监控的短信接收人
	 * @return
	 */
	public List queryOrderMonitorUser(){
		List users = new ArrayList();
		List smsList = new ArrayList();
		String user[] = null;
		Map map = new HashMap();
		sql = "select * from es_msg_set where set_id = '100011111'";
		try {
			smsList = this.baseDaoSupport.queryForListByMap(sql, null);
			for (int i = 0; i < smsList.size(); i++) {
				map = (Map)smsList.get(i);
				user = map.get("mobile_no").toString().split("[,|,]");
				for (int j = 0; j < user.length; j++) {
					users.add(user[j]);
				}
			}
		} catch (Exception e) {
			logger.info("获取es_msg_set信息失败.");
			logger.info(e.getMessage(), e);
		}
		return users;
	}
	
	/**
	 * 根据serviceCode获取接口服务地址信息
	 * @param serviceCode
	 * @return
	 */
	public String queryServiceURL(String serviceCode){
		String url = "";
		sql = "SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"' and rownum < 2";
		url = baseDaoSupport.queryForString(sql);
		return url;
	}
	
	/**
	 * 查询支付渠道编码/支付渠道名称是否存在
	 * @param payment_channel_code
	 * @return
	 */
	public boolean isExistsPaymentChannelCode(String value , String type){
		boolean flag = false;
		try {
			if ("code".equalsIgnoreCase(type)) {
				sql = "select count(*) nums from es_payment_channel where payment_channel_code = ?";
			}else if ("name".equalsIgnoreCase(type)) {
				sql = "select count(*) nums from es_payment_channel where payment_channel_name = ?";
			}
			
			int nums = this.baseDaoSupport.queryForInt(sql, value);
			if (nums != 0) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 获取商品的params值
	 * @param sql
	 * @return
	 */
	public paramsL getGoodsParams(String sql){
		paramsL params = null;
		try {
			Map map = baseDaoSupport.queryForMap(sql);
			if (null != map && map.size() != 0) {
				String strpar = map.get("params").toString();
				strpar = strpar.substring(1,strpar.lastIndexOf("]"));
				params = JsonUtil.fromJson(strpar, paramsL.class);
			}
		} catch (Exception e) {
			logger.info("执行SQL["+sql+"]失败");
			logger.info(e.getMessage(), e);
		}
		return params;
	}
	
	/**
	 * 获取指定SQL的list结果集
	 * @param sql
	 * @return
	 */
	public List queryListResult(String sql){
		List orderList = null;
		try {
			//获取查询监控数据的SQL
			String dc_sql = baseDaoSupport.queryForString(sql);
			if(null == dc_sql || dc_sql.equals("")) 
				return new ArrayList();
			orderList = baseDaoSupport.queryForList(dc_sql, null);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return orderList;
	}
	
	/**
	 * 查询指定SQL的结果集
	 * @param sql
	 * @return
	 */
	public List queryListBySql(String sql){
		List orderList = null;
		try {
			orderList = baseDaoSupport.queryForList(sql, null);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return orderList;
	}
	
	
	
	/**
	 * 检查订单节点值是否正确
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public boolean checkFieldValue(String fieldName , String fieldValue){
		boolean flag = false;
		String cacheed = CacheUtils.getCache(CacheUtils.MALL_DATA_CONFIG+"YES");
		if(!"yes".equals(cacheed)){
			refreshPkConfig();
		}
		String value = CacheUtils.getCache(CacheUtils.MALL_DATA_CONFIG+fieldName+":"+fieldValue);
		String key = fieldName+":"+fieldValue;
//		Map value = cacheMallConfig.get(CacheUtils.MALL_DATA_CONFIG+fieldName+":"+fieldValue);
		if(!StringUtil.isEmpty(value) && fieldValue.equals(value))
			return true;
		else{
			//add by wui缓存失效优化 
			try {
				sql = "select * from es_mall_config where field_name = ? and field_value = ? and source_from is not null";
				List l = baseDaoSupport.queryForList(sql, fieldName,fieldValue);
				if (null != l && l.size() > 0) {
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
				logger.info("check " + fieldName + " error");
				logger.info(e.getMessage(), e);
			}
			return flag;
		}
		
//		try {
//			sql = "select * from es_mall_config where field_name = ? and field_value = ?";
//			List l = baseDaoSupport.queryForList(sql, fieldName,fieldValue);
//			if (null != l && l.size() > 0) {
//				flag = true;
//			}
//		} catch (Exception e) {
//			flag = false;
//			logger.info("check " + fieldName + " error");
//			logger.info(e.getMessage(), e);
//		}
//		return flag;
	}

	public void refreshPkConfig(){
		sql = "select * from es_mall_config where 1=1 ";
		List<Map> keyList = baseDaoSupport.queryForList(sql);
		if(keyList!=null){
			CacheMap<String,Map> cacheMallConfig = new CacheMap<String,Map>();
			for(Map m:keyList){
				String field_name = (String) m.get("field_name");
				String field_value = (String) m.get("field_value");
//				cacheMallConfig.put(field_name+":"+field_value, m);
				CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+field_name+":"+field_value,field_value);
				
			}
			CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+"YES","yes");
		}
	}
	
	
	/**
	 * 执行查询SQL，查单列，单行
	 * @param sql
	 * @return
	 */
	public String querySqlResult(String sql){
		String result = "";
		try {
			result = this.baseDaoSupport.queryForString(sql);
		} catch (Exception e) {
			logger.info("执行SQL[" + sql + "]失败.");
			logger.info(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 获取配置数据
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Map queryMapMallConfig(String fieldName , String fieldValue){
		Map map = null;
		try {
			sql = "select * from es_mall_config where field_name = ? and field_value = ?";
			map = baseDaoSupport.queryForMap(sql, fieldName,fieldValue);
		} catch (Exception e) {
			map = null;
			logger.info(e.getMessage(), e);
		}
		return map;
	}
	

	
	/**
	 * 根据sms_id修改短信发送标记
	 * @param sms_id
	 * @return
	 */
	public boolean updateSmsFlag(String sms_id){
		boolean flag = false;
		try{
			String sql = "update es_sms_send set send_flag = '1' where sms_id = '"+sms_id+"' and source_from = '"+ManagerUtils.getSourceFrom()+"'";
			baseDaoSupport.update(sql, null);
			flag = true;
		}catch(Exception e){
			flag = false;
			logger.info("修改sms_id["+sms_id+"]发送标记失败.");
			logger.info(e.getMessage(),e);
		}
		return flag;
	}
	
	/**
	 * 修改表数据
	 * @param tableName
	 * @param filedMap
	 * @param whereMap
	 * @return
	 */
	public boolean update(String tableName , Map filedMap , Map whereMap){
		boolean flag = false;
		try{
			baseDaoSupport.update(tableName, filedMap, whereMap);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
