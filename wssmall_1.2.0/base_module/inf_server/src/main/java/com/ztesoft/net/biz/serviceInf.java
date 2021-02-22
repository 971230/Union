package com.ztesoft.net.biz;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class serviceInf {
	private static Logger logger = Logger.getLogger(serviceInf.class);
	private static ICommClientBO commClientBO;
	private static ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	//货品同步
	public  static String searchProductEcs(String object_id,String action_code,String orgId){
		
		String goods_id = null;
		   logger.info("============================进入货品同步=goods_id="+goods_id);
		 // String str=" SELECT  ec.type_id  cat_idi,  ep.sku skua, eg.goods_id,  eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id "; //where eg.goods_id='201403215013731
		   
		   // String str=" SELECT   ep.sku,   eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id ";		   
		   
		  
		   Map tmpMap=null;
			   goods_id = object_id;
//			   goods_id  = getICommClientBO().queryForString(" SELECT A.PRODUCT_ID FROM ES_PRODUCT_CO A WHERE A.ID = "+object_id);
			   String str="SELECT * from v_product ep where  ep.product_id ='"+goods_id+"'";
			   logger.info("============================货品同步=str="+str);
			tmpMap=  getICommClientBO().queryForMap(str);
		    logger.info("============================货品同步=tmpMap="+tmpMap);
		    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String dtime = df.format(new Date());
		    StringBuffer jsonStr=new StringBuffer();
		    String seq=null;
				seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			   logger.info("==================YYYYYYYYYY==========货品同步=sku="+tmpMap.get("skua"));
			  jsonStr.append("{\"goods_info_req\":");
			 // jsonStr.append("\"tip\":").append("\"").append("货品同步场景").append("\",");
			  jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
			  jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");

		      jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");
			  jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");
			  jsonStr.append("\"receive_system\":").append("\"").append("10008").append("\",");
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
			  if(tmpMap.get("sn") == null){
				  jsonStr.append("\"goods_spec\":").append("\"").append("").append("\",");
			  }else{
				  jsonStr.append("\"goods_spec\":").append("\"").append(tmpMap.get("sn")).append("\",");
			  }
			  //String tmp_state=tmpMap.get("state")==null?"":tmpMap.get("state").toString();
			  logger.info("==============AAAAA==============货品同步=state="+tmpMap.get("state"));
			  if(tmpMap.get("state") == null){ 
				  jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			  }else{
				  jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			  }		      
		      logger.info("==========PPPPPPP======================货品同步strpar="+tmpMap.get("params"));	      
			//  String strpar=(String) tmpMap.get("params");	
		      
		      jsonStr.append("\"goods_attr\":[");
			  try{
				  String strpar = ClobToString((Clob) tmpMap.get("params"));
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
								tmpMapdc=  getICommClientBO().queryForMap(strdc);
							   logger.info("RRRRRRRRRRRRRRRRRRRR======================strdc="+strdc); 
							   logger.info("OOOOOOOOOO======================tmpMapdc="+tmpMapdc); 
							    String dc_sql =  tmpMapdc.get("dc_sql").toString();
							  //String dca =   dc_sql.substring(dc_sql.indexOf("'")+1,dc_sql.lastIndexOf("'"));
							    String dca = dc_sql.replaceAll("'", "");
							    logger.info("KKKKKKKKKKKKKKKKKKK======================dc_sql="+dca); 
							  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
							  logger.info("查颜色SQL------------------str_yanshe="+str_yanshe);
								  tmpMap_yanshe =  getICommClientBO().queryForMap(str_yanshe);
							  logger.info("颜色三属性=======GGGGGGGGG========================="+tmpMap_yanshe.get("value_desc"));
						  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
						  jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
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
	
	//货品同步
	public  static String searchProductWMS(String object_id,String action_code,String orgId){
		
		String goods_id = null;
		   logger.info("============================进入货品同步=goods_id="+goods_id);
		 // String str=" SELECT  ec.type_id  cat_idi,  ep.sku skua, eg.goods_id,  eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id "; //where eg.goods_id='201403215013731
		   
		   // String str=" SELECT   ep.sku,   eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id ";		   
		   
		  
		   Map tmpMap=null;
			   goods_id = object_id;
//			   goods_id  = getICommClientBO().queryForString(" SELECT A.PRODUCT_ID FROM ES_PRODUCT_CO A WHERE A.ID = "+object_id);
			   String str="SELECT ep.sku skua,eg.goods_id,fun_wms_goods_category(ec.type_id) type_id,eq.action_code,eg.name,eb.brand_code brand_name,ec.cat_id cat_name,et.type_id type_name,em.model_name,eg.model_code,ep.sn,eg.state,eg.params   FROM es_goods eg left join es_brand eb on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec on eg.cat_id = ec.cat_id left join es_goods_type et on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id  left join es_brand_model em on eg.model_code = em.model_code  left join es_product ep on ep.goods_id = eg.goods_id  where eg.type = 'product'  and  ep.product_id='"+goods_id+"'";
			   logger.info("============================货品同步=str="+str);
			tmpMap=  getICommClientBO().queryForMap(str);
		    logger.info("============================货品同步=tmpMap="+tmpMap);
		    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String dtime = df.format(new Date());
		    StringBuffer jsonStr=new StringBuffer();
		    String seq=getICommClientBO().queryForString("select seq_goods.nextVal from dual");
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
				  String strpar = ClobToString((Clob) tmpMap.get("params"));
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
								tmpMapdc=  getICommClientBO().queryForMap(strdc);
							   logger.info("RRRRRRRRRRRRRRRRRRRR======================strdc="+strdc); 
							   logger.info("OOOOOOOOOO======================tmpMapdc="+tmpMapdc); 
							    String dc_sql =  tmpMapdc.get("dc_sql").toString();
							  //String dca =   dc_sql.substring(dc_sql.indexOf("'")+1,dc_sql.lastIndexOf("'"));
							    String dca = dc_sql.replaceAll("'", "");
							    logger.info("KKKKKKKKKKKKKKKKKKK======================dc_sql="+dca); 
							  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
							  logger.info("查颜色SQL------------------str_yanshe="+str_yanshe);
								  tmpMap_yanshe =  getICommClientBO().queryForMap(str_yanshe);
							  logger.info("颜色三属性=======GGGGGGGGG========================="+tmpMap_yanshe.get("value_desc"));
						  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
						  jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
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
//	public  static String searchProductEcs(String object_id,String action_code,String orgId){
//		
//		String goods_id = null;
//		   logger.info("============================进入货品同步=goods_id="+goods_id);
//		 // String str=" SELECT  ec.type_id  cat_idi,  ep.sku skua, eg.goods_id,  eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id "; //where eg.goods_id='201403215013731
//		   
//		   // String str=" SELECT   ep.sku,   eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id ";		   
//		   SqlExe getICommClientBO() = new SqlExe();
//		   
//		  
//		   Map tmpMap=null;
//		   try {
//			   goods_id = object_id;
////			   goods_id  = getICommClientBO().queryForString(" SELECT A.PRODUCT_ID FROM ES_PRODUCT_CO A WHERE A.ID = "+object_id);
//			   String str="SELECT ep.sku skua,eg.goods_id,ec.type_id,eq.action_code,eg.name,eb.brand_code brand_name,ec.cat_id cat_name,et.type_id type_name,em.model_name,eg.model_code,ep.sn,eg.state,eg.params   FROM es_goods eg left join es_brand eb on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec on eg.cat_id = ec.cat_id left join es_goods_type et on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id  left join es_brand_model em on eg.model_code = em.model_code  left join es_product ep on ep.goods_id = eg.goods_id  where eg.type = 'product'  and  eg.goods_id='"+goods_id+"'";
//			   logger.info("============================货品同步=str="+str);
//			tmpMap=  getICommClientBO().queryForMap(str);
//		} catch (FrameException e) {
//			
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		    logger.info("============================货品同步=tmpMap="+tmpMap);
//		    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//			String dtime = df.format(new Date());
//		    StringBuffer jsonStr=new StringBuffer();
//		    String seq=null;
//			try {
//				seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
//			} catch (FrameException e) {
//				
//				e.printStackTrace();
//			} catch (SQLException e) {
//			
//				e.printStackTrace();
//			}
//			   logger.info("==================YYYYYYYYYY==========货品同步=sku="+tmpMap.get("skua"));
//			  jsonStr.append("{\"goods_info_req\":");
//			 // jsonStr.append("\"tip\":").append("\"").append("货品同步场景").append("\",");
//			  jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
//			  jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
//
//		      jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");
//			  jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");
//			  jsonStr.append("\"receive_system\":").append("\"").append("10008").append("\",");
//			  jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");
//			 //tmpMap.get("name")
//				jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");
//			  jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");
//			  
//			  
//			//  String tmp_cat=tmpMap.get("cat_idi")==null?"":tmpMap.get("cat_idi").toString();
//			  jsonStr.append("\"goods_category\":").append("\"").append(tmpMap.get("type_id")).append("\",");
//			  logger.info("77777777777778999999999999999999---------------------------------goods_category="+tmpMap.get("type_id"));
//			  String tmp_ty=tmpMap.get("type_name")==null?"":tmpMap.get("type_name").toString();//"预付费套餐"
//			  jsonStr.append("\"goods_type\":").append("\"").append(tmp_ty).append("\",");
//			  
//			  String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
//			  jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");//tmpMap.get("model_code")
//			  //String tmp_sn=tmpMap.get("sn")==null?"":tmpMap.get("sn").toString();
//			  if(tmpMap.get("sn") == null){
//				  jsonStr.append("\"goods_spec\":").append("\"").append("").append("\",");
//			  }else{
//				  jsonStr.append("\"goods_spec\":").append("\"").append(tmpMap.get("sn")).append("\",");
//			  }
//			  //String tmp_state=tmpMap.get("state")==null?"":tmpMap.get("state").toString();
//			  logger.info("==============AAAAA==============货品同步=state="+tmpMap.get("state"));
//			  if(tmpMap.get("state") == null){ 
//				  jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
//			  }else{
//				  jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
//			  }		      
//		      logger.info("==========PPPPPPP======================货品同步strpar="+tmpMap.get("params"));	      
//			//  String strpar=(String) tmpMap.get("params");	
//		      
//		      jsonStr.append("\"goods_attr\":[");
//			  try{
//				  String strpar = ClobToString((Clob) tmpMap.get("params"));
//				  strpar=strpar.substring(1,strpar.lastIndexOf("]"));
//				  logger.info("==========GGGGGGGGGG======================货品同步strpar="+strpar);	  
//				  logger.info("=======HHHHHHHHH=========================货品同步strpar="+strpar);
//				  paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
//				 
//				 /* if(pl.getParamList().size()>0){
//					 for(int i=0;i<pl.getParamList().size();i++){						
//						 paramsenum tmp=pl.getParamList().get(i);
//						 jsonStr.append("{");
//						  jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
//						  jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
//						  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");						  
//						 // jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\","); 						  
//						  Map tmpMapdc=null;
//						  Map tmpMap_yanshe=null;
//						 // attrdefvalue   tmp.getAttrcode()
//						  logger.info("WWWWWWWWWWWWWWWW======================"+tmp.getAttrcode());
//						  if(tmp.getAttrcode().equals("DC_GOODS_COLOR")){
//							  String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = 'DC_GOODS_COLOR'";
//							  SqlExe getICommClientBO()dc = new SqlExe();
//							   try {
//								tmpMapdc=  getICommClientBO()dc.queryForMap(strdc);
//							} catch (FrameException e) {
//								e.printStackTrace();
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//							   logger.info("RRRRRRRRRRRRRRRRRRRR======================strdc="+strdc); 
//							   logger.info("OOOOOOOOOO======================tmpMapdc="+tmpMapdc); 
//							    String dc_sql =  tmpMapdc.get("dc_sql").toString();
//							  //String dca =   dc_sql.substring(dc_sql.indexOf("'")+1,dc_sql.lastIndexOf("'"));
//							    String dca = dc_sql.replaceAll("'", "");
//							    logger.info("KKKKKKKKKKKKKKKKKKK======================dc_sql="+dca); 
//							  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
//							  logger.info("查颜色SQL------------------str_yanshe="+str_yanshe);
//							  SqlExe getICommClientBO()_yanshe = new SqlExe();
//							  try {
//								  tmpMap_yanshe =  getICommClientBO()_yanshe.queryForMap(str_yanshe);
//								} catch (FrameException e) {
//									e.printStackTrace();
//								} catch (SQLException e) {
//									e.printStackTrace();
//								}
//							  logger.info("颜色三属性=======GGGGGGGGG========================="+tmpMap_yanshe.get("value_desc"));
//						  jsonStr.append("\"param_value_code\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
//						  }else{
//							  jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
//							  logger.info("进入ELSE----------------------------");  
//						  }
//						  
//						  //jsonStr.append("\"sku_attr\":").append("\"").append("1").append("\""); 
//						  jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\""); 
//						  jsonStr.append("}");
//						  if(i<pl.getParamList().size()-1)
//							  jsonStr.append(",");
//					
//				      }
//				  }*/
//				  
//				  if(pl.getParamList().size()>0){
//						 for(int i=0;i<pl.getParamList().size();i++){						
//							 paramsenum tmp=pl.getParamList().get(i);
//							 jsonStr.append("{");
//							  jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
//							  jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
//							  				  
//							 // jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\","); 						  
//							  Map tmpMapdc=null;
//							  Map tmpMap_yanshe=null;
//							  if(tmp.getAttrcode() != null && !"".equalsIgnoreCase(tmp.getAttrcode())){
////								  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");		
//								  String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode()+"'";
//								  SqlExe getICommClientBO()dc = new SqlExe();
//								   try {
//									tmpMapdc=  getICommClientBO()dc.queryForMap(strdc);
//								} catch (FrameException e) {
//									e.printStackTrace();
//								} catch (SQLException e) {
//									e.printStackTrace();
//								}
//								    String dc_sql =  tmpMapdc.get("dc_sql").toString();
//								  //String dca =   dc_sql.substring(dc_sql.indexOf("'")+1,dc_sql.lastIndexOf("'"));
//								    String dca = dc_sql.replaceAll("'", "");
//								  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ='"+tmp.getValue().toString().trim()+"'";
//								  SqlExe getICommClientBO()_yanshe = new SqlExe();
//								  try {
//									  tmpMap_yanshe =  getICommClientBO()_yanshe.queryForMap(str_yanshe);
//									} catch (FrameException e) {
//										e.printStackTrace();
//									} catch (SQLException e) {
//										e.printStackTrace();
//									}
//							  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
//							  jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");		
//							  }else{
//								  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");		
//								  jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
//							  }
//							  
//							  jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\""); 
//							  jsonStr.append("}");
//							  if(i<pl.getParamList().size()-1)
//								  jsonStr.append(",");
//						
//					      }
//					  }
//					
//			  }catch(Exception ex){  
//			  }
//			  jsonStr.append("]");
//				  jsonStr.append("}}");
//				  logger.info("================BBBBB============货品同步=jsonStr="+jsonStr); 
//				  return jsonStr.toString();
//					
//	}
	//WMS货品库存同步
	public static  String  wmsprosyn(){
		String sqlStr="SELECT ei.product_id,nvl(ei.sku,ei.product_id) sku, ew.house_code,el.change_num,el.change_reason,eal.order_id FROM es_goods_inventory ei left join es_warehouse ew on ei.house_id = ew.house_id left join es_goods_inventory_log el on ei.product_id=el.product_id left join es_goods_inventory_apply_log eal on ei.product_id=eal.obj_id";
		Map tmpMap=null;
			tmpMap=getICommClientBO().queryForMap(sqlStr);
		String seq=null;
			seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String dtime = df.format(new Date());
		StringBuffer jsonStr=new StringBuffer();
		
		
		jsonStr.append("{\"inventory_info_req\":{");
		jsonStr.append("\"tip\":").append("\"").append("WMS货品库存同步场景").append("\",");
		jsonStr.append("\"serial_no\":").append("\"").append(seq).append("\",");
		jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
		jsonStr.append("\"source_system \":").append("\"").append("10010").append("\",");
		jsonStr.append("\"receive_system\":").append("\"").append("10011").append("\",");
		jsonStr.append("\"sku\":").append("\"").append(tmpMap.get("sku")).append("\",");
		jsonStr.append("\"inventory_code\":").append("\"").append("house_code").append("\",");
		jsonStr.append("\"inventory_num\":").append("\"").append("change_num").append("\",");
		jsonStr.append("\"change_reason\":").append("\"").append("change_reason").append("\",");
		jsonStr.append("\"inventory_order\":").append("\"").append("20140219173800001").append("\"");
		jsonStr.append("}}");
		
		return jsonStr.toString();
		
	}
	//WMS仓库信息同步
	public static  String  wmsgInfosyn(String houseId){
		String sqlStr=" SELECT ew.house_code,ew.house_name,ew.remarks,ew.status,ew.address,ew.scope_code FROM es_warehouse ew where house_id='"+houseId+"'";

		Map tmpMap=null;
			tmpMap=getICommClientBO().queryForMap(sqlStr);
		String seq=null;
			seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String dtime = df.format(new Date());

		
		StringBuffer jsonStr=new StringBuffer();
		jsonStr.append("{\"inventory_req\":{");
		jsonStr.append("\"tip\":").append("\"").append("WMS仓库信息同步场景").append("\",");
		jsonStr.append("\"serial_no\":").append("\"").append(seq).append("\",");
		jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
		jsonStr.append("\"source_system \":").append("\"").append("10010").append("\",");
		jsonStr.append("\"receive_system\":").append("\"").append("10011").append("\",");
		jsonStr.append("\"action\":").append("\"").append("A").append("\",");
		jsonStr.append("\"inventory_code\":").append("\"").append(tmpMap.get("house_code")).append("\",");
		jsonStr.append("\"inventory_name\":").append("\"").append(tmpMap.get("house_name")).append("\",");
		
		String tmp_des=tmpMap.get("remarks")==null?"":tmpMap.get("remarks").toString();
		jsonStr.append("\"inventory_desc\":").append("\"").append(tmp_des).append("\",");
		
		String tmp_state=tmpMap.get("status")==null?"":tmpMap.get("status").toString();
		jsonStr.append("\"inventory_state\":").append("\"").append(tmp_state).append("\",");
		
		String tmp_addr=tmpMap.get("address")==null?"":tmpMap.get("address").toString();
		jsonStr.append("\"inventory_addr\":").append("\"").append(tmp_addr).append("\",");
		
		String tmp_scope=tmpMap.get("scope_code")==null?"":tmpMap.get("scope_code").toString();
		jsonStr.append("\"scope\":").append("\"").append(tmp_scope).append("\"");
		jsonStr.append("}}");
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
	public static void getPamfromjson(String str){
		str=str.substring(1,str.lastIndexOf("]"));
		paramsL pl=JsonUtil.fromJson(str, paramsL.class);
		 for(int i=0;i<pl.getParamList().size();i++){
			 logger.info(pl.getParamList().get(i).getEname());
		 }
	}
}
