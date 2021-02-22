package com.ztesoft.net.biz.lhyinf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class LhyInfoSyn {
	private static Logger logger = Logger.getLogger(LhyInfoSyn.class);
	private  ICommClientBO commClientBO;
	private  ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	public String modelSyn(String modelCode) {
		String str = new String();
			// 获取序列
			String seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			// 获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String dtime = df.format(new Date());
			Map m = getICommClientBO()
					.queryForMap("select ee.model_code class_code,ee.model_name class_name,ee.brand_code from es_brand_model ee where ee.model_code="
							+ modelCode);

			str = "{" + "\"class_req\":" + "{\"serial_no\":\"" + seq + "\","
					+ "\"time\":\"" + dtime + "\"," + "\"source_system \":\""
					+ "10011" + "\"," + "\"receive_system \":\"" + "10008"
					+ "\"," + "\"class_code\":\"" + m.get("class_code") + "\","
					+ " \"action\":\"" + "M" + "\"," + "\"class_name\":\""
					+ m.get("class_name") + "\"," + "\"brand_code\":\""
					+ m.get("brand_code") + "\"," + "\"class_state\":\"" + "0"
					+ "\"" + "" + "}" + "}";

		return str;
	}

	public String phoneSyn(String objId) {

		String str = new String();

			// 获取序列
			String seq = getICommClientBO()
					.queryForString("select seq_goods.nextVal from dual");
			// 获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String dtime = df.format(new Date());
			List m = getICommClientBO()
					.queryForList("select aa.dn_no phone_no,aa.area_code phone_city_code,aa.status phone_no_state,aa.group_id phone_group_id,"
							+ "aa.batch_id phone_batch,aa.is_lucky is_good_no,aa.no_classify good_no_type,aa.rule_id good_no_rule,"
							+ "aa.deposit good_no_deposit,aa.period good_no_limit,aa.lowest good_no_fee,aa.NO_GEN mobile_net from es_no aa where aa.dn_no in (select es.no from es_no_co es where es.id = "
							+ objId + ")");
			logger.info(m.size());
			StringBuffer jsonStr = new StringBuffer();
			jsonStr.append("{\"phone_no_req\":{");
			jsonStr.append("\"serial_no\":").append("\"").append("" + seq + "")
					.append("\",");
			jsonStr.append("\"time\":").append("\"").append("" + dtime + "")
					.append("\",");
			jsonStr.append("\"source_system \":").append("\"").append("10010")
					.append("\",");
			jsonStr.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
			jsonStr.append("\"channel\":").append("\"").append("134,135")
					.append("\",");
			jsonStr.append("\"phone_list\":").append("[");
			for (int i = 0; i < m.size(); i++) {
				Map map = (Map) m.get(i);
				jsonStr.append("{");
				jsonStr.append("\"phone_no\":").append("\"")
						.append("" + map.get("phone_no") + "").append("\",");
				jsonStr.append("\"phone_city_code\":").append("\"")
						.append("" + map.get("phone_city_code") + "")
						.append("\",");
				jsonStr.append("\"phone_no_state\":").append("\"")
						.append("" + map.get("phone_no_state") + "")
						.append("\",");
				jsonStr.append("\"phone_group_id\":").append("\"")
						.append("" + map.get("phone_group_id") + "")
						.append("\",");
				jsonStr.append("\"phone_group\":").append("\"").append("分组名称")
						.append("\",");
				jsonStr.append("\"phone_batch\":").append("\"")
						.append("" + map.get("phone_batch") + "").append("\",");
				jsonStr.append("\"is_good_no\":").append("\"")
						.append("" + map.get("is_good_no") + "").append("\",");
				jsonStr.append("\"good_no_rule\":").append("\"")
						.append("" + map.get("good_no_rule") + "")
						.append("\",");
				jsonStr.append("\"good_no_deposit\":").append("\"")
						.append("" + map.get("good_no_deposit") + "")
						.append("\",");
				jsonStr.append("\"good_n o_limit\":").append("\"")
						.append("" + map.get("good_no_limit") + "")
						.append("\",");
				jsonStr.append("\"good_no_fee\":").append("\"")
						.append("" + map.get("good_no_fee") + "").append("\",");
				jsonStr.append("\"mobile_net\":").append("\"")
						.append("" + map.get("mobile_net") + "").append("\"");
				jsonStr.append("}");
				if (i < m.size() - 1) {
					jsonStr.append(",");
				}
			}
			jsonStr.append("]");
			jsonStr.append("}}");
			str = jsonStr.toString();

			logger.info(str);
		return str;

	}
}
