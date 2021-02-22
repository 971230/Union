package com.ztesoft.net.biz;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
//import com.ztesoft.inf.test.GoodsInfo;
//import com.ztesoft.inf.vo.GoodsVo;
//import com.ztesoft.inf.vo.paramList;
//import com.ztesoft.inf.vo.paramsL;
//import com.ztesoft.inf.vo.paramsenum;
//import com.ztesoft.inf.vo.GoodsVo;
//import com.ztesoft.inf.vo.paramList;

/*
1：商品信息同步
2：货品库存同步接口  -------先不用管-
3：活动信息同步接口
4：分类同步接口
5：品牌同步接口============不用管 已同步完------
*/

public class SendsSyn {
	private static Logger logger = Logger.getLogger(SendsSyn.class);
	private ICommClientBO commClientBO;
	private ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
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

	// 商品信息同步-------------------------6

	// 商品信息同步-------------------------6
	public String searchGoodsEcs(String object_id,String action_code,String orgId) {
//		String result = null;
		StringBuffer json = new StringBuffer();
		String goods_id = object_id;
//			goods_id = getICommClientBO().queryForString(" SELECT A.GOODS_ID FROM ES_GOODS_CO A WHERE A.ID = "+object_id);
			Map map = getICommClientBO()
					.queryForMap("select ep.sku as skua,  a.cat_id as cat_ida, d.type_id as type_aid, a.sku as prod_offer_code,  a.name as prod_offer_name, d.cat_id as prod_offer_type, b.brand_code as prod_offer_brand,  c.model_code as terminal_model, a.state as prod_offer_state, a.price as prod_offer_price, a.weight as prod_offer_heavy, f.tag_id as prod_offer_package, a.sku as sku, a.store as goods_num, a.params  from es_goods a left join es_brand b on b.brand_id = a.brand_id left join es_brand_model c on a.model_code = c.model_code left join es_goods_cat d on a.cat_id = d.cat_id left join es_tag_rel e on e.rel_id = a.goods_id left join es_tags f on e.tag_id = f.tag_id  left join es_product ep on ep.goods_id = a.goods_id where a.type = 'goods' and a.goods_id = '"
							+ goods_id + "'");
		//.queryForMap("select   a.cat_id as cat_ida,  d.type_id as type_aid,  a.sn as prod_offer_code,a.name as prod_offer_name,d.name as prod_offer_type,b.name as prod_offer_brand,c.model_name as terminal_model,a.state as prod_offer_state,a.price as prod_offer_price,a.weight as prod_offer_heavy,f.tag_name as prod_offer_package,a.sku as sku,a.store as goods_num,a.params from es_goods a left join es_brand b on b.brand_id = a.brand_id left join es_brand_model c on a.model_code = c.model_code left join es_goods_cat d on a.cat_id=d.cat_id left join es_tag_rel e on e.rel_id=a.goods_id left join es_tags f on e.tag_id =f.tag_id where a.type = 'goods' and a.goods_id = '201403215013731'");
			String seq = getICommClientBO()
					.queryForString("select seq_goods.nextVal from dual");

			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(date);
		
			json.append("{\"prod_offer_req\":");
			json.append("{\"tip\":").append("\"").append("商品信息同步")
					.append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time)
					.append("\",");
			json.append("\"time\":").append("\"").append(time).append("\",");
			//json.append("\"channel\":").append("\"").append("").append("\",");
			json.append("\"source_system\":").append("\"").append("10011")
					.append("\",");

			json.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
//			json.append("\"channel\":").append("\"").append("WCS")//WCS,WWA
//			.append("\",");
			  String tmpStr=new String();
			  if(orgId != null && !"".equalsIgnoreCase(orgId)){
				  String[] orgIdStr=orgId.split(",");
				  if(orgIdStr.length > 0){
					  for(int i=0;i<orgIdStr.length;i++){
						  String sqlStr="select a.org_code from es_goods_org a where a.party_id="+orgIdStr[i];
						  String orgCode=getICommClientBO().queryForString(sqlStr);
//						  if(i<orgIdStr.length){
//							  tmpStr=tmpStr+","+orgCode;
//						  }
//						  else tmpStr+=orgCode;
						  if(tmpStr == null || "".equalsIgnoreCase(tmpStr)){
							  tmpStr = orgCode;
						  }else{
							  tmpStr = tmpStr + ","+ orgCode;
						  }
					  }  
				  }else{
					  String sqlStr="select a.org_code from es_goods_org a where a.party_id="+orgId;
					  String orgCode=getICommClientBO().queryForString(sqlStr);
					  tmpStr = orgCode;
				  }
				  
			  }
			json.append("\"channel\":").append("\"").append(tmpStr)//WCS,WWA
				.append("\",");
			
			json.append("\"prod_offer_code\":").append("\"")
					.append(map.get("prod_offer_code")).append("\",");
			json.append("\"action\":").append("\"").append(action_code).append("\",");
			
			json.append("\"prod_offer_name\":").append("\"")
					.append(map.get("prod_offer_name")).append("\",");
	
			json.append("\"prod_offer_type\":").append("\"")
					.append(map.get("cat_ida")).append("\",");

			json.append("\"prod_offer_category\":").append("\"")
			.append(map.get("type_aid")).append("\",");
			json.append("\"prod_offer_brand\":").append("\"")
					.append(map.get("prod_offer_brand")).append("\",");
			json.append("\"terminal_model\":").append("\"")
					.append(map.get("terminal_model")).append("\",");
			String prod_offer_state = null;
	
			if(map.get("prod_offer_state") == null){
				prod_offer_state="0";
			}else{
				prod_offer_state="1";
			}
			json.append("\"prod_offer_state\":").append("\"")
					.append(prod_offer_state).append("\",");
			json.append("\"prod_offer_price\":").append("\"")
					.append(map.get("prod_offer_price")).append("\",");
			json.append("\"prod_offer_heavy\":").append("\"")
					.append(map.get("prod_offer_heavy")).append("\",");
			json.append("\"prod_offer_package\":").append("\"")
					.append(map.get("prod_offer_heavy")).append("\",");
			
			json.append("\"prod_offer_ele\":[");
			try {
	
			
			//map.get("skua")
			String sqlStr= "SELECT c.sku,sum(1) record_num FROM es_goods_rel b left join es_goods a on a.goods_id = b.a_goods_id left join es_product c on c.product_id = b.product_id where a.goods_id = "+goods_id+ " group by c.sku";

			logger.info(sqlStr);
			List list_sku = getICommClientBO().queryForList(sqlStr);

            if(list_sku.toString() != "[]" ){
	////////////////////////////
            	for(int i = 0; i < list_sku.size();i++){    

        		Map m = (Map) list_sku.get(i);
        		json.append("{\"sku\":").append("\"").append(m.get("sku")).append("\",");
        		
        		if(  map.get("goods_num") == null ){
    				json.append("\"goods_num\":").append("\"")
    				.append(1).append("\"");	
    			}else{
    				json.append("\"goods_num\":").append("\"")
    				.append(m.get("record_num")).append("\"");	
    			}
        		if (i < list_sku.size() - 1){
					json.append("},");	
				}else{
					json.append("}");
				};
        	}

            	
///////////////////////////
        	/*for(int i = 0; i < list_sku.size();i++){    

        		Map m = (Map) list_sku.get(i);
        		json.append("{\"sku\":").append("\"").append(m.get("sku")).append("\",");
        		logger.info("-FOR----里面SKU----"+m.get("sku").toString());
        		//ss.searchGoodsEcs(m.get("goods_id").toString());
        		if(  map.get("goods_num") == null ){
    				json.append("\"goods_num\":").append("\"")
    				.append(1).append("\"");	
    			}else{
    				json.append("\"goods_num\":").append("\"")
    				.append(1).append("\"");	
    			}
        		if (i < list_sku.size() - 1){
					json.append("},");	
				}else{
					json.append("}");
				};
        	}*/	
        }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
            
			json.append("],");
		
			json.append("\"prod_offer_param\":[");
			
			/////
			String mon_return="0";
            String mobile_price="0";
            String order_return="0";
            String deposit_fee="0";
            String  mon_give="0";
            String all_give="0";
			
			///////
			try {
				if(map.get("params") != null&&!"[]".equalsIgnoreCase(map.get("params").toString())){
//			String strpar2 = ClobToString((Clob) map.get("params"));
					String strpar2 = map.get("params").toString();
			strpar2 = strpar2.substring(1, strpar2.lastIndexOf("]"));
			paramsL p2=JsonUtil.fromJson(strpar2, paramsL.class);
			//paramList p2 = JsonUtil.fromJson(strpar2, paramList.class);

			
				
				if (p2.getParamList().size() > 0) {
			
					for (int i = 0; i < p2.getParamList().size(); i++) {
						
						paramsenum tmp=p2.getParamList().get(i);
						
						String ename=tmp.getEname();
						if("mon_return".equalsIgnoreCase(ename)){
							mon_return=tmp.getValue()==null?"0":tmp.getValue();
						}else if("mobile_price".equalsIgnoreCase(ename)){
							mobile_price=tmp.getValue()==null?"0":tmp.getValue();
						}else if("order_return".equalsIgnoreCase(ename)){
							order_return=tmp.getValue()==null?"0":tmp.getValue();
						}else if("deposit_fee".equalsIgnoreCase(ename)){
							deposit_fee=tmp.getValue()==null?"0":tmp.getValue();
						}else if("mon_give".equalsIgnoreCase(ename)){
							mon_give=tmp.getValue()==null?"0":tmp.getValue();
						}else if("all_give".equalsIgnoreCase(ename)){
							all_give=tmp.getValue()==null?"0":tmp.getValue();
						}
						//GoodsVo tmp = p2.getParamList().get(i);
			
						//json.append("\"" + tmp.getEname() + "\":");
						//json.append("\"" + tmp.getValue() + "\"");
						json.append("{\"param_code\":").append("\"").append(tmp.getEname())
						.append("\",");
				                    json.append("\"param_name\":").append("\"").append(tmp.getName())
						.append("\",");
				                    json.append("\"param_value_code\":").append("\"").append("")
						.append("\",");
				                    json.append("\"param_value\":").append("\"").append(tmp.getValue())
						.append("\"");
				                    /*json.append("\"sku_attr\":").append("\"").append("1")
									.append("\"");*/
				                    if (i < p2.getParamList().size() - 1){
				    					json.append("},");	
				    				}else{
				    					json.append("}");
				    				};         
					}
					}
					
			}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			json.append("],");
			json.append("\"prod_offer_attr\":");
		
				json.append("{");
				json.append("\"prod_offer_sku\":").append("\"").append("0").append("\",");
				json.append("\"mon_give\":").append("\"").append(mon_give).append("\",");
				json.append("\"all_give\":").append("\"").append(all_give).append("\",");
				json.append("\"mobile_price\":").append("\"").append(mobile_price).append("\",");
				json.append("\"order_return\":").append("\"").append(order_return).append("\",");
				json.append("\"deposit_fee\":").append("\"").append(deposit_fee).append("\",");
				json.append("\"mon_return\":").append("\"").append(mon_return).append("\"");
				
				json.append("}");
			//}
			json.append("}}");
		return json.toString();
	}

	// 货品库存同步接口  -------先不用管-------------------------7
	//public String ProductSyn(String house_id, String url) {
		public String ProductSyn(String house_id) {
		String result = null;
		logger.info("===========AAAAAAAAAA===========house_id="+house_id);
			Map map = getICommClientBO()
					.queryForMap("select a.house_code as house_code,a.house_name as house_name,b.sku as sku,e.org_id_str as channel,d.attr_inline_type as is_share_other,c.inventory_num as prod_offer_num,c.change_reason as prod_offer_reason from es_warehouse a left join es_goods_inventory b on b.house_id = a.house_id left join es_goods_inventory_log c on c.house_id = a.house_id left join es_virtual_warehouse d on d.house_id=a.house_id left join es_goods_inventory_apply_log e on e.house_id=a.house_id where a.house_id='"
							+ house_id + "'");
			String seq = getICommClientBO()
					.queryForString("select seq_goods.nextVal from dual");
			logger.info("==BBB====================house_id="+house_id);
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
					.append(map.get("is_share_other")).append("\",");
			json.append("\"action\":").append("\"").append("A").append("\",");
			json.append("\"prod_offer_num\":").append("\"")
					.append(map.get("prod_offer_num")).append("\",");
			json.append("\"prod_offer_reason_ID\":").append("\"").append("")
					.append("\",");
			json.append("\"prod_offer_reason\":").append("\"")
					.append(map.get("prod_offer_reason")).append("\"");
			json.append("}}");
			logger.info("货品库存同步接口");
		return result;
	}

	// 活动信息同步接口-------------------------8
	public String ActivitySyn(String id) {
		String result = null;
			Map map = getICommClientBO()
					.queryForMap("select * from es_promotion_activity a ,es_promotion b where a.id='"
							+ id + "'");
			String seq = getICommClientBO()
					.queryForString("select seq_goods.nextVal from dual");
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(date);
			StringBuffer json = new StringBuffer();
			json.append("{\"goods_inventory_req\":");
			json.append("{\"tip\":").append("\"").append("活动信息同步")
					.append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time)
					.append("\",");
			json.append("\"time\":").append("\"").append(time).append("\",");
			json.append("\"source_system\":").append("\"").append("10011")
					.append("\",");
			json.append("\"receive_system\":").append("\"").append("10008")
					.append("\",");
			json.append("\"action\":").append("\"").append("A").append("\",");
			json.append("\"activity_code\":").append("\"").append("")
					.append("\",");
			json.append("\"activity_name\":").append("\"")
					.append(map.get("name")).append("\",");
			json.append("\"activity_type\":").append("\"")
					.append(map.get("enable")).append("\",");
			json.append("\"activity_desc\":").append("\"")
					.append(map.get("brief")).append("\",");
			json.append("\"activity_cond\":").append("\"").append("")
					.append("\",");
			json.append("\"discount_type\":").append("\"").append("")
					.append("\",");
			json.append("\"discount_content\":").append("\"").append("")
					.append("\",");
			json.append("\"activity_offer\":").append("\"").append("")
					.append("\",");
			json.append("\"channel\":").append("\"").append("").append("\",");
			json.append("\"eff_time\":").append("\"").append("").append("\",");
			json.append("\"exp_time\":").append("\"").append("").append("\",");
			json.append("\"activity_attr\":[");
			json.append("{\"param_type\":").append("\"").append("")
					.append("\",");
			json.append("\"param_name\":").append("\"").append("")
					.append("\",");
			json.append("\"param_value\":").append("\"").append("")
					.append("\"");
			json.append("}]");
			json.append("}}");
			logger.info("活动信息同步接口======================json="+json);
		logger.info("活动信息同步接口======================result="+result);
		return result;
	}

	// 分类同步接口-------------------------9
	public String ClassifySyn(String cat_id) {
		String result = null;
			Map map = getICommClientBO()
					.queryForMap("  SELECT  a.NAME cat_name,a.cat_id cat_code,a.type_id,a.parent_id parents_code,'0' type_state,b.params,A.TYPE type_i from es_goods_cat a LEFT JOIN es_goods_type b ON b.type_id = a.cat_id where a.cat_id="
							+ cat_id + " ");
			/*lj .queryForMap("  SELECT  a.NAME cat_name,a.cat_id cat_code,a.type_id,a.parent_id parents_code,'0' type_state,b.params,A.TYPE type_i from es_goods_cat a LEFT JOIN es_goods_type b ON b.type_id = a.cat_id where a.cat_id="
					+ cat_id + " ");*/
					//.queryForMap("  SELECT  a.NAME cat_name,a.cat_id cat_code,a.type_id,a.parent_id parents_code,'0' type_state,b.params   from es_goods_cat a LEFT JOIN es_goods_type b ON b.type_id = a.cat_id where a.cat_id="
						//	+ cat_id + " ");
			String seq = getICommClientBO()
					.queryForString("select seq_goods.nextVal from dual");
			logger.info("分类同步接口(ClassifySyn)-------------String--seq="+seq);
			logger.info("分类同步接口(ClassifySyn)-------------Map--map="+map.toString());
			String TYPE ="";
			if(map.get("type_i").equals("product") ){
				TYPE= "1";
			}else{
				TYPE="0";
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
			json.append("\"type_code\":").append("\"").append("").append(map.get("cat_code")).append("\",");
			json.append("\"action\":").append("\"").append("A").append("\",");
			json.append("\"type_name\":").append("\"")
					.append(map.get("cat_name")).append("\",");
			json.append("\"type_class\":").append("\"")
					.append(TYPE).append("\",");
			json.append("\"type_parents_code\":").append("\"")
					.append(map.get("parents_code")).append("\",");
			json.append("\"type_state\":").append("\"").append("0").append("\",");

			json.append("\"brand_list\":").append("[");
			try {
				List l_brand = getICommClientBO()
						.queryForList(" select b.brand_code,b.NAME from es_type_brand a JOIN es_brand b ON b.brand_id = a.brand_id AND b.disabled = 0 WHERE a.type_id ="
								+ map.get("cat_code").toString());
				
				if (l_brand.size() > 0) {
					
					for (int i = 0; i < l_brand.size(); i++) {	
						Map m = (Map) l_brand.get(i);
						json.append("{");
						json.append("\"brand_code\":").append("\"" + getValue(m.get("brand_code"))
								+ "\",");
						json.append("\"brand_name\":").append("\"" + getValue(m.get("name")) + "\"");
						if (i < l_brand.size() - 1){
							json.append("},");	
						}else{
							json.append("}");
						}
					}
					
				}		
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			json.append("],");

			json.append("\"param_list\":").append("[");

			try {
				String strpar = ClobToString((Clob) map.get("params"));
				if (strpar != null && !"".equalsIgnoreCase(strpar)) {
					strpar = strpar.substring(1, strpar.lastIndexOf("]"));
					paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
					if(pl.getParamList().size()>0){
					 for(int i=0;i<pl.getParamList().size();i++){
							
						 paramsenum tmp=pl.getParamList().get(i);
						 json.append("{");
						 json.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						  json.append("\"param_name\":").append("\"").append(tmp.getName()).append("\"");
						  json.append("}");
						  if(i<pl.getParamList().size()-1)
							  json.append(",");
					
				  }
					}
					/*paramList pl = JsonUtil.fromJson(strpar, paramList.class);
					logger.info("heo");
					if (pl.getParamsList().size() > 0) {
						logger.info("hello");
						for (int i = 0; i < pl.getParamsList().size(); i++) {
							GoodsVo tmp = pl.getParamsList().get(i);
							System.err.println(tmp.toString());
							json.append("{");
							
							json.append("\"param_code\":").append("\"" + tmp.getEname() + "\",");
							json.append("\"param_name\":").append("\"" + tmp.getName() + "\"");
							if (i < pl.getParamsList().size() - 1){
								json.append("},");
							}else{
								json.append("}");
							}	
						}
					
					}*/
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			json.append("]");

			json.append("}}");
			result = json.toString();			
		logger.info("分类同步接口(ClassifySyn)---------------result="+result);
		return result;
	}

	// 品牌同步接口============不用管 已同步完-------------------------10
	public String BrandSyn(String brand_id) {
		String result = null;
			Map map = getICommClientBO()
					.queryForMap("select * from es_brand a where a.brand_id='"
							+ brand_id + "'");
			String seq = getICommClientBO()
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
			json.append("\"action\":").append("\"").append("A").append("\",");
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
		return result;
	}

	public String getValue(Object o) {
		String result = "";
		if (o != null)
			return o.toString();
		return result;
	}
}
