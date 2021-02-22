package com.ztesoft.inf.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.dao.DBCon;
import com.ztesoft.inf.framework.dao.SqlExeNew;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.util.SendInfoUtil;

public class RunTest {

	private static Logger logger = Logger.getLogger(RunTest.class);
	
	/**
	 * 型号同步
	 * @param modelCode
	 * @param action_code(A:新增，M:修改)
	 * @return
	 */
	public static String modelSyn(String modelCode , String action_code) {
		DBCon sqlExec = new DBCon();
		String str = new String();
		try {
			// 获取序列
			String seq = sqlExec
					.queryForString("select seq_goods.nextVal from dual");
			// 获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String dtime = df.format(new Date());
			Map m = sqlExec
					.queryForMap("select ee.model_code class_code,ee.model_name class_name,ee.brand_code from es_brand_model ee where ee.model_code='"
							+ modelCode+"'");

			str = "{" + "\"class_req\":" + "{\"serial_no\":\"" + seq + "\","
					+ "\"time\":\"" + dtime + "\"," + "\"source_system\":\""
					+ "10011" + "\"," + "\"receive_system\":\"" + "10008"
					+ "\"," + "\"class_code\":\"" + m.get("class_code") + "\","
					+ " \"action\":\"" + action_code + "\"," + "\"class_name\":\""
					+ m.get("class_name") + "\"," + "\"brand_code\":\""
					+ m.get("brand_code") + "\"," + "\"class_state\":\"" + "0"
					+ "\"" + "" + "}" + "}";

			// logger.info(str);
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}


	/**
	 * 分类同步
	 * @param cat_id
	 * @param action_code(A:新增，M:修改)
	 * @return
	 */
	public static String ClassifySyn(String cat_id , String action_code) {
		String result = null;
		DBCon sqlExec = new DBCon();
		try {
			Map map = sqlExec
					.queryForMap("  SELECT  a.NAME cat_name,a.cat_id cat_code,a.type_id,a.parent_id parents_code,'0' type_state,b.params,A.TYPE type_i from es_goods_cat a LEFT JOIN es_goods_type b ON b.type_id = a.cat_id where a.cat_id="
							+ cat_id + " ");
			String seq = sqlExec
					.queryForString("select seq_goods.nextVal from dual");
			String TYPE = "";
			if (map.get("type_i").equals("product")) {
				TYPE = "1";
			} else {
				TYPE = "0";
			}
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(date);
			StringBuffer json = new StringBuffer();
			json.append("{\"type_req\":");
			json.append("{\"tip\":").append("\"").append("分类信息同步")
					.append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time)
					.append("\",");
			json.append("\"time\":").append("\"").append(time).append("\",");
			json.append("\"source_system\":").append("\"").append("10011")
					.append("\",");
			json.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
			json.append("\"type_code\":").append("\"").append("")
					.append(map.get("cat_code")).append("\",");
			json.append("\"action\":").append("\"").append(action_code).append("\",");
			json.append("\"type_name\":").append("\"")
					.append(map.get("cat_name")).append("\",");
			json.append("\"type_class\":").append("\"").append(TYPE)
					.append("\",");
			json.append("\"type_parents_code\":").append("\"")
					.append(map.get("parents_code")).append("\",");
			json.append("\"type_state\":").append("\"").append("0")
					.append("\",");

			json.append("\"brand_list\":").append("[");
			try {
				List l_brand = sqlExec
						.queryForList(" select b.brand_code,b.NAME from es_type_brand a JOIN es_brand b ON b.brand_id = a.brand_id AND b.disabled = 0 WHERE a.type_id ="
								+ map.get("cat_code").toString());

				if (l_brand.size() > 0) {

					for (int i = 0; i < l_brand.size(); i++) {
						Map m = (Map) l_brand.get(i);
						json.append("{");
						json.append("\"brand_code\":").append(
								"\"" + getValue(m.get("brand_code")) + "\",");
						json.append("\"brand_name\":").append(
								"\"" + getValue(m.get("name")) + "\"");
						if (i < l_brand.size() - 1) {
							json.append("},");
						} else {
							json.append("}");
						}
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			json.append("],");
           
			json.append("\"param_list\":").append("[");
            if(map.get("params")!=null&&!"[]".equalsIgnoreCase(map.get("params").toString()))
            {
			try {
				String strpar = map.get("params").toString();
				if (strpar != null && !"".equalsIgnoreCase(strpar)) {
					strpar = strpar.substring(1, strpar.lastIndexOf("]"));
					paramsL pl = JsonUtil.fromJson(strpar, paramsL.class);
					if (pl.getParamList().size() > 0) {
						for (int i = 0; i < pl.getParamList().size(); i++) {

							paramsenum tmp = pl.getParamList().get(i);
							json.append("{");
							json.append("\"param_code\":").append("\"")
									.append(tmp.getEname()).append("\",");
							json.append("\"param_name\":").append("\"")
									.append(tmp.getName()).append("\"");
							json.append("}");
							if (i < pl.getParamList().size() - 1)
								json.append(",");

						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
            }
			json.append("]");

			json.append("}}");
			result = json.toString();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 品牌同步
	 * @param brand_id(品牌ID)
	 * @param action_code(A:新增,M:修改)
	 * @return
	 */
	public static String BrandSyn(String brand_id , String action_code) {
		String result = null;
		DBCon sqlExec = new DBCon();
		try {
			Map map = sqlExec
					.queryForMap("select * from es_brand a where a.brand_id='"
							+ brand_id + "'");
			String seq = sqlExec
					.queryForString("select seq_goods.nextVal from dual");
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(date);
			StringBuffer json = new StringBuffer();
			json.append("{\"brand_req\":");
			json.append("{\"tip\":").append("\"").append("品牌信息同步")
					.append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time)
					.append("\",");
			json.append("\"time\":").append("\"").append(time).append("\",");
			json.append("\"source_system\":").append("\"").append("10011")
					.append("\",");
			json.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
			json.append("\"brand_code\":").append("\"")
					.append(map.get("brand_code")).append("\",");
			json.append("\"action\":").append("\"").append(action_code).append("\",");
			json.append("\"brand_name\":").append("\"").append(map.get("name"))
					.append("\",");
			json.append("\"type_code\":").append("\"").append("").append("\",");
			json.append("\"brand_class\":").append("\"").append("")
					.append("\",");
			json.append("\"brand_state\":").append("\"")
					.append(map.get("disabled")).append("\",");
			json.append("\"logo_addr\":").append("\"").append(map.get("logo"))
					.append("\"");
			json.append("}}");
			logger.info("品牌同步接口");
			result = json.toString();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 货品同步
	 * @param object_id(货品的product_id)
	 * @param action_code(操作类型)
	 * @param orgId(接收系统，如：10008)
	 * @return
	 */
	public  static String searchProductEcs(String object_id,String action_code,String orgId){
		
		String goods_id = object_id;
		Map tmpMap = null;
		logger.info("============================进入货品同步=goods_id=" + goods_id);
		String sql = "SELECT * from v_product ep where  ep.product_id ='"+goods_id+"'";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer jsonStr=new StringBuffer();
		String seq = "";
		String dc_sql = "";
		String dc_sql_value = "";
		
		try {
			DBCon sqlExec = new DBCon();
			List<Map> list = sqlExec.queryForList(sql);
			if (null != list && list.size() > 0) {
				tmpMap = list.get(0);
			}else {
				logger.info("没有找到货品[" + goods_id + "]");
			}
			//序列号
			seq = sqlExec.queryForString("select seq_goods.nextval from dual");

			jsonStr.append("{\"goods_info_req\":");
			jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");	//序列号
			jsonStr.append("\"time\":").append("\"").append(df.format(new Date())).append("\",");	//时间
			jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");	//货品编码
			jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			jsonStr.append("\"receive_system\":").append("\"").append(orgId).append("\",");	//接收方系统标识
			jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");	//动作
			jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");	//货品名称
			jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");	//货品品牌
			jsonStr.append("\"goods_category\":").append("\"").append(tmpMap.get("type_id")).append("\",");	//货品分类
			jsonStr.append("\"goods_type\":").append("\"").append(tmpMap.get("cat_name")).append("\",");	//货品类型
			String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
			jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");	//货品型号
			//货品规格
			if (null == tmpMap.get("sn")) {
				jsonStr.append("\"goods_spec\":").append("\"").append("").append("\",");
			}else {
				jsonStr.append("\"goods_spec\":").append("\"").append(tmpMap.get("sn")).append("\",");
			}
			//货品状态
			if (null == tmpMap.get("state")) {
				jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			}else {
				jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			}
			//货品属性
			jsonStr.append("\"goods_attr\":[");
			
			String strpar = tmpMap.get("params").toString();
			strpar=strpar.substring(1,strpar.lastIndexOf("]"));			
			paramsL pl = null;
			try {
				pl = JsonUtil.fromJson(strpar, paramsL.class);
				
				if (null != pl && pl.getParamList().size() > 0) {
					for (int i = 0; i < pl.getParamList().size(); i++) {
						paramsenum tmp=pl.getParamList().get(i);
						jsonStr.append("{");
						jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
						
						if (null != tmp.getAttrcode() && !"".equals(tmp.getAttrcode())) {
							dc_sql = sqlExec.queryForString("select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode() +"'");
							dc_sql_value = sqlExec.queryForString("select value_desc from (select 'ECS' source_from, e.* from ("+dc_sql+") e)T where T.VALUE ='"+tmp.getValue().toString()+"' and rownum < 2");
							jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
							jsonStr.append("\"param_value\":").append("\"").append(dc_sql_value).append("\",");
						}else {
							jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
							jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
						}
						jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\"");
						jsonStr.append("}");
						if(i < pl.getParamList().size() - 1){
							jsonStr.append(",");
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
			jsonStr.append("]");
			jsonStr.append("}}");
			
		} catch (Exception e) {
			logger.info("error json:==========" + jsonStr.toString());
			jsonStr = new StringBuffer();
			logger.info(e.getMessage());
		}
		return jsonStr.toString();
					
	}
	// CLON转换String
	public static String ClobToString(Clob cb) {
		try {
			// 以 java.io.Reader 对象形式（或字符流形式）
			// 检索此 Cb 对象指定的 CLOB 值 --Clob的转换
			Reader inStreamDoc = cb.getCharacterStream();
			// 取得cb的长度
			char[] tempDoc = new char[(int) cb.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) { 
		try {
			String url = "";
			String json = "";
			String result = "";
			//分类
			url = "http://10.123.98.57:7001/integrationweb/integration/basClass.sync";
			json = ClassifySyn("10051","M");
			
			//型号
//			url = "http://10.123.98.57:7001/integrationweb/integration/basModel.sync";
//			json = modelSyn("红米Note","A");
			
			//品牌
//			url = "http://10.123.99.18:9002/integrationweb/integration/basBrand.sync";
//			json = BrandSyn("111","A");
			
			//货品
//			url = "http://10.123.177.212:9002/integrationweb/integration/pmProd.sync";
//			json = searchProductEcs("160200947260000726", "A", "100312");
			
			//活动同步
//			url = "http://10.123.98.57:7001/integrationweb/integration/actExt.sync";
//			json = activitySyn("160451528572508818","A","10034");
			
			//商品同步
//			url = "http://10.123.177.212:9002/integrationweb/integration/pmGoods.sync";
//			json = searchGoodsEcs("151871528250000152", "A", "10034", "10008");
			
			//输出请求、响应报文
			logger.info(json);
			result = SendInfoUtil.postHttpReq(json,url);
			logger.info(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
	}
	
	/**
	 * 商品同步
	 * @param object_id  商品goods_id
	 * @param action_code	操作类型(A:新增，M:修改)
	 * @param orgId	商城编码(如：10034)
	 * @param orgIdBelong	接收系统(如：10008)
	 * @return
	 */
	public static String searchGoodsEcs(String object_id,String action_code,String orgId,String orgIdBelong) {
		StringBuffer json = new StringBuffer();
		String goods_id = object_id;
		try {
			DBCon sqlExec = new DBCon();
			Map map = sqlExec.queryForMap("select * from es_gdlt_v_goods a  where  a.goods_id = '"+ goods_id + "'");
			String seq = sqlExec.queryForString("select seq_goods.nextVal from dual");
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
			json.append("{\"prod_offer_req\":");
			json.append("{\"tip\":").append("\"").append("商品信息同步").append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time).append("\",");	//序列号
			json.append("\"time\":").append("\"").append(time).append("\",");	//时间
			json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			json.append("\"receive_system\":").append("\"").append(orgIdBelong).append("\",");	//接收方系统标识			
			json.append("\"channel\":").append("\"").append(orgId).append("\",");	//商城编码
			json.append("\"prod_offer_code\":").append("\"").append(map.get("prod_offer_code")).append("\",");	//商品编码
			json.append("\"action\":").append("\"").append(action_code).append("\",");	//动作
			json.append("\"prod_offer_name\":").append("\"").append(map.get("prod_offer_name")).append("\",");	//商品名称
			json.append("\"prod_offer_type\":").append("\"").append(map.get("cat_ida")).append("\",");	//商品分类
			json.append("\"prod_offer_category\":").append("\"").append(map.get("type_aid")).append("\",");
			json.append("\"prod_offer_brand\":").append("\"").append(map.get("prod_offer_brand")).append("\",");	//商品品牌
			json.append("\"terminal_model\":").append("\"").append(map.get("terminal_model")).append("\",");	//机型
			Object prod_offer_state = map.get("prod_offer_state");
			if (null == prod_offer_state || "".equals(prod_offer_state.toString())) {
				prod_offer_state = "0";
			}
			json.append("\"prod_offer_state\":").append("\"").append(prod_offer_state.toString()).append("\",");	//商品状态
			json.append("\"prod_offer_price\":").append("\"").append(map.get("prod_offer_price")).append("\",");	//商品价格
			json.append("\"prod_offer_heavy\":").append("\"").append(map.get("prod_offer_heavy")).append("\",");	//商品重量（kg）
			json.append("\"prod_offer_package\":").append("\"").append(map.get("prod_offer_heavy")).append("\",");	//归属商品包
			
			//商品构成
			json.append("\"prod_offer_ele\":[");
			try {
				String sqlStr= "SELECT a.sku,sum(1) record_num FROM ES_GDLT_V_GOODS_REL a where a.goods_id = "+goods_id+ " group by a.sku";
				List list_sku = sqlExec.queryForList(sqlStr);
				if(list_sku.toString() != "[]" ){
					for(int i = 0; i < list_sku.size();i++){  
						Map m = (Map) list_sku.get(i);
						json.append("{\"sku\":").append("\"").append(m.get("sku")).append("\",");	//货品sku
						Object goods_num = map.get("goods_num");
						if (null == goods_num) {
							goods_num = "1";
						}else {
							goods_num = m.get("record_num");
						}
						json.append("\"goods_num\":").append("\"").append(goods_num).append("\"");	//货品数量
						if (i < list_sku.size() - 1){
							json.append("},");
						}else {
							json.append("}");
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}			
			json.append("],");
			
			//商品参数
			json.append("\"prod_offer_param\":[");
			String mon_return = "0";
			String mobile_price = "0";
			String order_return = "0";
			String deposit_fee = "0";
			String mon_give = "0";
			String all_give = "0";
			try {
				if(null != map.get("params") && !"[]".equalsIgnoreCase(map.get("params").toString())){
					String strpar2 = map.get("params").toString();
					strpar2 = strpar2.substring(1, strpar2.lastIndexOf("]"));
					paramsL p2=JsonUtil.fromJson(strpar2, paramsL.class);
					if (p2.getParamList().size() > 0) {
						for (int i = 0; i < p2.getParamList().size(); i++) {
							paramsenum tmp=p2.getParamList().get(i);
							String ename=tmp.getEname();
							if("mon_return".equalsIgnoreCase(ename)){
								mon_return = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("mobile_price".equalsIgnoreCase(ename)){
								mobile_price = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("order_return".equalsIgnoreCase(ename)){
								order_return = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("deposit_fee".equalsIgnoreCase(ename)){
								deposit_fee = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("mon_give".equalsIgnoreCase(ename)){
								mon_give = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("all_give".equalsIgnoreCase(ename)){
								all_give = isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}
							json.append("{\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
							json.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
							json.append("\"param_value_code\":").append("\"").append("").append("\",");
							json.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\"");
							if (i < p2.getParamList().size() - 1){
								json.append("},");
							}else {
								json.append("}");
							}
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			//mon_give不一定有值,无值时取mon_return
			if (null == mon_give || "".equals(mon_give) || "0".equals(mon_give) && null != mon_return) {
				mon_give = mon_return;
			}
			json.append("],");
			json.append("\"prod_offer_attr\":");	//合约属性
			json.append("{");
			json.append("\"prod_offer_sku\":").append("\"").append("0").append("\",");	//套餐sku
			json.append("\"mon_give\":").append("\"").append(mon_give).append("\",");	//月送费金额
			json.append("\"all_give\":").append("\"").append(all_give).append("\",");	//协议期总送费金额
			json.append("\"mobile_price\":").append("\"").append(mobile_price).append("\",");	//手机款
			json.append("\"order_return\":").append("\"").append(order_return).append("\",");	//首月返还
			json.append("\"deposit_fee\":").append("\"").append(deposit_fee).append("\",");	//预存款
			json.append("\"mon_return\":").append("\"").append(mon_return).append("\"");	//分月返还
			json.append("}}}");
			
		} catch (Exception e) {
			json = new StringBuffer();
			logger.info("商品["+goods_id+"]同步失败.");
			logger.info(e.getMessage(), e);
		}
		return json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	//货品同步
	public  static String searchProductWMS(String object_id,String action_code,String orgId){
		
		String goods_id = null;
//		   logger.info("============================进入货品同步=goods_id="+goods_id);
		 // String str=" SELECT  ec.type_id  cat_idi,  ep.sku skua, eg.goods_id,  eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id "; //where eg.goods_id='201403215013731
		   
		   // String str=" SELECT   ep.sku,   eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id ";		   
		   DBCon sqlExec = new DBCon();
		   
		  
		   Map tmpMap=null;
		   try {
			   goods_id = object_id;
//			   goods_id  = sqlExec.queryForString(" SELECT A.PRODUCT_ID FROM ES_PRODUCT_CO A WHERE A.ID = "+object_id);
			   String str="SELECT ep.sku skua,eg.goods_id,fun_wms_goods_category(ec.type_id) type_id,eq.action_code,eg.name,eb.brand_code brand_name,ec.cat_id cat_name,et.type_id type_name,em.model_name,eg.model_code,ep.sn,eg.state,eg.params   FROM es_goods eg left join es_brand eb on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec on eg.cat_id = ec.cat_id left join es_goods_type et on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id  left join es_brand_model em on eg.model_code = em.model_code  left join es_product ep on ep.goods_id = eg.goods_id  where eg.type = 'product'  and  eg.goods_id='"+goods_id+"'";
			   logger.info("============================货品同步=str="+str);
			tmpMap=  sqlExec.queryForMap(str);
		} catch (FrameException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		    logger.info("============================货品同步=tmpMap="+tmpMap);
		    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String dtime = df.format(new Date());
		    StringBuffer jsonStr=new StringBuffer();
		    String seq=null;
			try {
				seq = sqlExec.queryForString("select seq_goods.nextVal from dual");
			} catch (FrameException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			   logger.info("==================YYYYYYYYYY==========货品同步=sku="+tmpMap.get("skua"));
			  jsonStr.append("{\"goods_info_req\":");
			 // jsonStr.append("\"tip\":").append("\"").append("货品同步场景").append("\",");
			  jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
			  jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");

		      jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");
//			  jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");
//			  jsonStr.append("\"receive_system\":").append("\"").append("10008").append("\",");
			  jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");
			 //tmpMap.get("name")
				jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");
			  jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");
			  
			  
			//  String tmp_cat=tmpMap.get("cat_idi")==null?"":tmpMap.get("cat_idi").toString();
			  jsonStr.append("\"goods_category\":").append("\"").append(tmpMap.get("type_id")).append("\",");
			  logger.info("77777777777778999999999999999999---------------------------------goods_category="+tmpMap.get("type_id"));
			  String tmp_ty=tmpMap.get("type_name")==null?"":tmpMap.get("type_name").toString();//"预付费套餐"
			  jsonStr.append("\"goods_type\":").append("\"").append(tmp_ty).append("\",");
			  
			  String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
			  jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");//tmpMap.get("model_code")
			  //String tmp_sn=tmpMap.get("sn")==null?"":tmpMap.get("sn").toString();
//			  if(tmpMap.get("sn") == null){
//				  jsonStr.append("\"goods_spec\":").append("\"").append("").append("\",");
//			  }else{
//				  jsonStr.append("\"goods_spec\":").append("\"").append(tmpMap.get("sn")).append("\",");
//			  }
//			  //String tmp_state=tmpMap.get("state")==null?"":tmpMap.get("state").toString();
//			  logger.info("==============AAAAA==============货品同步=state="+tmpMap.get("state"));
			  if(tmpMap.get("state") == null){ 
				  jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			  }else{
				  jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			  }		      
		      logger.info("==========PPPPPPP======================货品同步strpar="+tmpMap.get("params"));	      
			//  String strpar=(String) tmpMap.get("params");	
		      
		      jsonStr.append("\"goods_attr\":[");
			  try{
//				  String strpar = ClobToString((Clob) tmpMap.get("params"));
				  String strpar =  tmpMap.get("params").toString();
				  strpar=strpar.substring(1,strpar.lastIndexOf("]"));
				  logger.info("==========GGGGGGGGGG======================货品同步strpar="+strpar);	  
				  logger.info("=======HHHHHHHHH=========================货品同步strpar="+strpar);
				  paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
				 
				  if(pl.getParamList().size()>0){
					 for(int i=0;i<pl.getParamList().size();i++){						
						 paramsenum tmp=pl.getParamList().get(i);
						 jsonStr.append("{");
						  jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						  jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
						  						  
						 // jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\","); 						  
						  Map tmpMapdc=null;
						  Map tmpMap_yanshe=null;
						 // attrdefvalue   tmp.getAttrcode()
						  logger.info("WWWWWWWWWWWWWWWW======================"+tmp.getAttrcode());
						  if(tmp.getAttrcode() != null && !"".equalsIgnoreCase(tmp.getAttrcode())){
							  String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode() +"'";
							  DBCon sqlExecdc = new DBCon();
							   try {
								tmpMapdc=  sqlExecdc.queryForMap(strdc);
							} catch (FrameException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							   logger.info("RRRRRRRRRRRRRRRRRRRR======================strdc="+strdc); 
							   logger.info("OOOOOOOOOO======================tmpMapdc="+tmpMapdc); 
							    String dc_sql =  tmpMapdc.get("dc_sql").toString();
							  //String dca =   dc_sql.substring(dc_sql.indexOf("'")+1,dc_sql.lastIndexOf("'"));
							    String dca = dc_sql.replaceAll("'", "");
							    logger.info("KKKKKKKKKKKKKKKKKKK======================dc_sql="+dca); 
							    if(tmp.getValue()!= null && !"".equalsIgnoreCase(tmp.getValue()) && dc_sql!= null && !"".equalsIgnoreCase(dc_sql) ){
							    	String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
									  logger.info("查颜色SQL------------------str_yanshe="+str_yanshe);
									  DBCon sqlExec_yanshe = new DBCon();
									  try {
										  tmpMap_yanshe =  sqlExec_yanshe.queryForMap(str_yanshe);
										} catch (FrameException e) {
											e.printStackTrace();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									  logger.info("颜色三属性=======GGGGGGGGG========================="+tmpMap_yanshe.get("value_desc"));
								  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
								  jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
							    }else{
							    	 jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
									  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
							    }
							  
						  }else{
							  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
							  jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
							  logger.info("进入ELSE----------------------------");  
						  }
						  
						  //jsonStr.append("\"sku_attr\":").append("\"").append("1").append("\""); 
						  jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\""); 
						  jsonStr.append("}");
						  if(i<pl.getParamList().size()-1)
							  jsonStr.append(",");
					
				      }
				  }
					
			  }catch(Exception ex){  
			  }
			  jsonStr.append("]");
				  jsonStr.append("}}");
				  logger.info("================BBBBB============货品同步=jsonStr="+jsonStr); 
				  return jsonStr.toString();
					
	}
    public String BufferedReaderDemo(String path) throws IOException {
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            sb.append(temp+" ");
            temp=br.readLine();
        }
        return sb.toString();
    }
    

/** 

     * wsdl接口远程调用方法 

     * @param url  接口地址 

     * @param OperationName //需要调用的方法 

     * @param xml //报文 

     * @return 返回的报文 

     */ 

    public static String getWSDLCall(String url,String xml){ 

        String result = ""; 

        try { 

        	Service service = new Service();  
			Call call2 = (Call) service.createCall();  
			 call2.setTargetEndpointAddress(new java.net.URL(  
					 url));  
			 call2.setUseSOAPAction(true);  
			 call2.setReturnType(new QName("http://www.w3.org/2001/XMLSchema",  
			         "string"));  
			 // 第二种设置返回值类型为String的方法  
			 call2.setOperationName(new QName("http://tempuri.org/", "ReqSKU"));  
			 call2.setSOAPActionURI("http://tempuri.org/ReqSKU");  
			 call2.addParameter(new QName("http://tempuri.org/", "strJson"),// 这里的name对应参数名称  
			         XMLType.XSD_STRING, ParameterMode.IN);  
			 String retVal2 = (String) call2  
			         .invoke(new Object[] { xml});  
			 logger.info(retVal2);  

        } catch (Exception e) { 

            // TODO: handle exception 

            e.printStackTrace(); 

        } 

        return result; 

    } 
    
	// 货品库存同步接口  -------先不用管-------------------------7
	//public String ProductSyn(String house_id, String url) {
		public static String ProductSyn(String log_id) {
		String result = null;
		DBCon sqlExec = new DBCon();
		logger.info("===========AAAAAAAAAA===========house_id="+log_id);
		try {
			Map map = sqlExec
					.queryForMap("select distinct c.house_code,c.house_name,a.is_share,a.sku,a.org_id_str channel,d.attr_inline_type is_share_other, a.inventory_num prod_offer_num,e.change_reason prod_offer_reason from es_goods_inventory_apply_log a join es_goods_inventory b on b.house_id = a.house_id left join es_warehouse c on c.house_id = a.house_id left join es_virtual_warehouse d on d.house_id = a.house_id left join es_goods_inventory_log e on e.log_id = a.log_id where a.log_id ='"
							+ log_id + "'");
			String seq = sqlExec
					.queryForString("select seq_goods.nextVal from dual");
			logger.info("==BBB====================house_id="+log_id);
			logger.info("======================map="+map.toString());
			logger.info("==================================seq="+seq);
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(date);
			StringBuffer json = new StringBuffer();
			json.append("{\"goods_inventory_req\":");
			json.append("{\"tip\":").append("\"").append("货品库存同步")
					.append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time)
					.append("\",");
			json.append("\"time\":").append("\"").append(time).append("\",");
			json.append("\"source_system\":").append("\"").append("10011")
					.append("\",");
			json.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
			json.append("\"inventory_code\":").append("\"")
					.append(map.get("house_code")).append("\",");
			json.append("\"inventory_name\":").append("\"")
					.append(map.get("house_name")).append("\",");
			json.append("\"sku\":").append("\"").append(map.get("sku"))
					.append("\",");
			json.append("\"channel\":").append("\"").append(map.get("channel"))
					.append("\",");
			json.append("\"is_share_other\":").append("\"")
					.append(getValue(map.get("is_share_other"))).append("\",");
			json.append("\"is_share\":").append("\"")
			.append(map.get("is_share")).append("\",");
			json.append("\"action\":").append("\"").append("1").append("\",");
			json.append("\"prod_offer_num\":").append("\"")
					.append(map.get("prod_offer_num")).append("\",");
			json.append("\"prod_offer_reason_ID\":").append("\"").append("1")
					.append("\",");
			json.append("\"prod_offer_reason\":").append("\"")
					.append("1").append("\"");
			json.append("}}");
			result =json.toString(); 
			logger.info("货品库存同步接口");
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
    
	public static String getValue(Object o) {
		String result = "";
		if (o != null)
			return o.toString();
		return result;
	}
	
	/**
	 * 获取商城的编码
	 * @param coQueueRuleReq
	 * @return
	 */
	public String getChannelCode(String orgId){
		StringBuffer jsonBuffer = new StringBuffer();
		String []arr = orgId.split(",");
		try {
			String sql = null;
			String orgCode = null;
			DBCon sqlExec = new DBCon();
			for (int i = 0; i < arr.length; i++) {
				SqlExeNew sqlexe = new SqlExeNew();
				sql = "select a.org_code from es_goods_org a where a.party_id = " + arr[i];
//				orgCode = sqlexe.queryForString(sql);本机测试走不通，暂时保留
				orgCode = sqlExec.queryForString(sql);
				if (null == jsonBuffer || "".equals(jsonBuffer.toString())) {
					jsonBuffer.append(orgCode);
				}else {
					jsonBuffer.append(",").append(orgCode);
				}
			}
		} catch (Exception e) {
			jsonBuffer = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		return jsonBuffer.toString().replaceAll("\"null\"", "\"\"");
	}
	
	/**
	 * 判断字符串是否为空，不为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(null == str || "".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断字符串是否为空，为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Map getPromotionMap(String activity_id) throws FrameException, SQLException {
		DBCon sqlexe = new DBCon();
		Map pmtMap = new HashMap();
		List goodsLst = new ArrayList();
		List giftsLst = new ArrayList();
		List paramLst = new ArrayList();
		String pmt_id = "-111111";  //活动规则标识
		String pmt_type = "";  //活动规则类型
		String source_from = "ECS";
		logger.info("getPromotionMap activity_id = " + activity_id);
		
		//获取活动集活动规则的基本信息，包括：活动标识、编码、名称、开始时间、结束时间、活动描述、活动类型、活动规则
		String sql = "select b.pmt_id, a.pmt_code, a.name as pmt_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = '"+1443+"'"
				+ " and source_from= 'ECS') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution"
				+ " from es_promotion_activity a"
				+ " inner join es_promotion b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = 'ECS'"
				+ " and a.id = '"+activity_id+"'"
				+ " union"
				+ " select b.pmt_id, a.pmt_code, a.name as pmt_name, a.begin_time, a.end_time, "
				+ " a.region, a.relief_no_class, a.modify_eff_time, "
				+ " b.order_money_from as act_condition, b.order_money_to, b.pmt_type,"
				+ " (select pkey from es_dc_public where pkey = b.pmt_type and stype = '"+1443+"'"
				+ " and source_from= 'ECS') as pmt_type_name,"
				+ " b.pmt_describe, b.pmt_solution"
				+ " from es_promotion_activity_log a"
				+ " inner join es_promotion_log b on a.id = b.pmta_id"
				+ " where 1 = 1"
				+ " and a.source_from = b.source_from"
				+ " and a.source_from = 'ECS'"
				+ " and a.id = '"+activity_id+"'"; 
		List<Map> pmtBaseLst = sqlexe.queryForList(sql);
		
		if (pmtBaseLst == null || pmtBaseLst.size() == 0) {
			logger.info("没有找到对应的活动信息  activity_id = " + activity_id);
		}

		//获取参与该活动的商品列表开始
		int pmtBaseSize = pmtBaseLst.size();
		logger.info("获取参与该活动的商品列表开始   getPromotionMap pmtBaseSize = " + pmtBaseSize);
		
		for (Map tmpMap : pmtBaseLst) {
			
			pmt_type = (String)tmpMap.get("pmt_type");  //活动的规则类型
			pmt_id = (String)tmpMap.get("pmt_id");  //规则标识			

			String sql_goods = "select a.pmt_id, b.goods_id, b.sku, b.name as goods_name, "
					+ " c.type_id as goods_type_id, c.name as goods_type_name"
					+ " from es_pmt_goods a"
					+ " inner join es_goods b on a.goods_id = b.goods_id"
					+ " inner join es_goods_type c on b.type_id = c.type_id"
					+ " where 1 = 1"
					+ " and a.source_from = b.source_from"
					+ " and a.source_from = 'ECS'"
					+ " and a.pmt_id = '"+pmt_id+"'"
					+ " union "
					+ " select a.pmt_id, b.goods_id, b.sku, b.name as goods_name, "
					+ " c.type_id as goods_type_id, c.name as goods_type_name"
					+ " from es_pmt_goods_log a"
					+ " inner join es_goods b on a.goods_id = b.goods_id"
					+ " inner join es_goods_type c on b.type_id = c.type_id"
					+ " where 1 = 1"
					+ " and a.source_from = b.source_from"
					+ " and a.source_from = 'ECS'"
					+ " and a.pmt_id = '"+pmt_id+"'";   //获取适应该活动规则的商品列表或赠品列表
			
			logger.info("getPromotionMap pmt_type = " + pmt_type);
			logger.info("getPromotionMap pmt_id = " + pmt_id);
			
			//如果某活动有两个活动规则，其中有赠品的，则获取赠品列表
			if (Consts.PMT_TYPE_GIFT.equalsIgnoreCase(pmt_type)) {
				giftsLst = sqlexe.queryForList(sql_goods);
				continue;
			}
			
			//设置活动基本信息
			pmtMap.putAll(tmpMap);
			
			//如果是满赠就放入赠品列表
			if (Consts.PMT_TYPE_MANZENG.equalsIgnoreCase(pmt_type)) {
				giftsLst = sqlexe.queryForList(sql_goods);
			} else {
				goodsLst = sqlexe.queryForList(sql_goods);
			}
		}
		giftsLst = new ArrayList();
		pmtMap.put("goods_list", goodsLst);
		pmtMap.put("gifts_list", giftsLst);
		logger.info("getPromotionMap pmtMap = " + pmtMap);
		
		return pmtMap;
	}
	
	/**
	 * 活动信息同步
	 * @param response
	 * @param coQueueRuleReq
	 * @return
	 * @throws SQLException 
	 * @throws FrameException 
	 */
	public static String activitySyn(String activity_id , String active_code , String org_id_str) throws FrameException, SQLException{
		Map pt=getPromotionMap(activity_id);
		List<Map>gl=(List)pt.get("goods_list");
		List<Map> g=(List)pt.get("gifts_list");
		DBCon sqlexe = new DBCon();
		String result = null;
		String seq = sqlexe.queryForString("select seq_goods.nextVal from dual");
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date);
		StringBuffer json=new StringBuffer();
		json.append("{\"activity_req\":{");
		json.append("\"serial_no\":").append("\"").append(seq+time).append("\",");	//序列号
		json.append("\"time\":").append("\"").append(time).append("\",");	//时间
		json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
		json.append("\"receive_system\":").append("\"").append("10008").append("\",");	//接收方系统标识
		String activity_type = pt.get("pmt_type_name").toString(); 			
		if ("004".equals(activity_type)) {
			activity_type = "0";
		}else if ("003".equals(activity_type)) {
			activity_type = "1";
		}else if ("008".equals(activity_type)) {
			activity_type = "2";
		}else if ("009".equals(activity_type)) {
			activity_type = "3";
		}else if ("006".equals(activity_type)) {
			activity_type = "4";
		}else if ("007".equals(activity_type)) {
			activity_type = "5";
		}else if ("011".equals(activity_type)) {
			activity_type = "6";
		}else if ("012".equals(activity_type) || "010".equals(activity_type)) {
			activity_type = "7";
		}else if ("013".equals(activity_type)) {
			activity_type = "8";
		}else if ("014".equals(activity_type)) {
			activity_type = "9";
		}
		json.append("\"action\":").append("\"").append(active_code).append("\",");	//动作
		json.append("\"action_id\":").append("\"").append(activity_id).append("\",");  //活动标识			
		json.append("\"activity_code\":").append("\"").append(pt.get("pmt_code")).append("\",");  //活动编码
		json.append("\"activity_name\":").append("\"").append(pt.get("pmt_name")).append("\",");  //活动名称
		json.append("\"activity_type\":").append("\"").append(activity_type).append("\",");  //活动类型
		json.append("\"activity_desc\":").append("\"").append(pt.get("pmt_describe")).append("\",");  //活动内容
		json.append("\"activity_cond\":").append("\"").append(pt.get("act_condition")).append("\",");  //活动条件
		String at=new String();
		String ps=(String) pt.get("pmt_type"); 			
		if ( "0".equals(activity_type) || "4".equals(activity_type)) {
			at = "1";
		}else if ("2".equals(activity_type) || "3".equals(activity_type) || "5".equals(activity_type) || "7".equals(activity_type) ||
 				 "8".equals(activity_type) || "9".equals(activity_type) || "1".equals(activity_type) ) {
			at = "2";
		}else if ("6".equals(activity_type)) {
			at = "3";
		}else {
			at = "0";
		}
		json.append("\"discount_type\":").append("\"").append(at).append("\",");//根据活动类型进行判断  优惠类型
		json.append("\"discount_content\":").append("\"").append(pt.get("pmt_solution")).append("\",");  //优惠内容
		json.append("\"good_no_type\":").append("\"").append(pt.get("relief_no_class")).append("\",");  //靓号类型

		String gn=new String();
		for(int i=0;i<gl.size();i++){
			gn+=gl.get(i).get("sku");
			if(i<gl.size()-1)
			gn+=",";
		}
		json.append("\"activity_offer\":").append("\"").append(gn).append("\",");	//活动商品
		
		String region = pt.get("region") == null ? "" : pt.get("region").toString();
		if ("-1".equals(region) || "330000".equals(region)) {
			region = "310000,315000,325000,314000,313000,312000,321000,324000,316000,318000,323000";
		} 			
// 			String channelStr=getChannelCode(coQueueRuleReq);
		String channelStr =org_id_str;
		String modify_time = dateToStr(new Date());
		json.append("\"channel\":").append("\"").append(channelStr).append("\",");//活动商城
		json.append("\"city\":").append("\"").append(region).append("\",");	//活动地市
		json.append("\"eff_time\":").append("\"").append(pt.get("begin_time").toString().replaceAll("[-|:| |.]", "").substring(0, 14)).append("\",");	//生效时间
		json.append("\"exp_time\":").append("\"").append(pt.get("end_time").toString().replaceAll("[-|:| |.]", "").substring(0, 14)).append("\",");	//失效时间
		json.append("\"modify_time\":").append("\"").append(modify_time).append("\",");	//修改时间
		json.append("\"modify_eff_time\":").append("\"").append(pt.get("modify_eff_time").toString().replaceAll("[-|:| |.]", "").substring(0, 14)).append("\",");	//修改生效时间
		json.append("\"activity_attr\":[");	//活动属性
		for(int i=0;i<g.size();i++){
 			Map tmp=g.get(i);
 			String goods_type_id = tmp.get("goods_type_id").toString();
 			if ("10007".equals(goods_type_id)) {
 				goods_type_id = "0";
			}else if ("10010".equals(goods_type_id)) {
				goods_type_id = "2";
			}else {
				goods_type_id = "1";
			}
 			json.append("{\"param_type\":").append("\"").append(goods_type_id).append("\",");	//属性类型
 			json.append("\"param_name\":").append("\"").append(tmp.get("goods_name")).append("\",");	//属性名称
 			json.append("\"param_value\":").append("\"").append(tmp.get("sku")).append("\"");	//属性取值
 			json.append("}");
 			if(i<g.size()-1)
 			json.append(",");
		}
		json.append("]}}");
		result=json.toString().replaceAll("\"null\"", "\"\"");;
 		return result;		
	}
	
	public static String[] listParseStringArray(List list){
		String []arr = new String[list.size()];
		int i = 0;
		for(Object o : list.toArray()){
			arr[i++] = o.toString();
		}
		return arr;
	}
	
	public static String dateToStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
}