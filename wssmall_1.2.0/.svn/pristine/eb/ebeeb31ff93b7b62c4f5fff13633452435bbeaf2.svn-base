package com.ztesoft.inf.test;


public class InfTest {
	
//	
//	public  static String searchProductEcs(String goods_id){
//		
//		/*ProductInfoGetReq req = new ProductInfoGetReq();
//		req.setProduct_id(goods_id);
//		
//		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
//		ProductInfoGetResp response = client.execute(req, ProductInfoGetResp.class);
//		Goods goods = response.getProduct();*/
//		
//		
//		/*GoodsInfoGetReq req = new GoodsInfoGetReq();
//		req.setGoods_id((goods_id));
//		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
//		GoodsInfoGetResp response = client.execute(req, GoodsInfoGetResp.class);
//		Goods goods = response.getGoods();
//
//			 StringBuffer jsonStr=new StringBuffer();
//	
//			  jsonStr.append("{\"goods_info_req\":");
//			  jsonStr.append("{\"serial_no\":").append("此处为序列号").append(",");
//			  jsonStr.append("\"time\":").append("此处为时间").append(",");
//
//		      jsonStr.append("\"sku\":").append(goods.getSku()).append(",");
//			  jsonStr.append("\" source_system \":").append("     ").append(",");
//			  jsonStr.append("\"receive_system\":").append("     ").append(",");
//			  jsonStr.append("\"action\":").append("A ").append(",");
//			 
//				jsonStr.append("\"goods_name\":").append("\"").append(goods.getName()).append("\",");
//			  jsonStr.append("\"goods_brand\":").append("\"").append(goods.getBrand_name()).append("\",");
//			  jsonStr.append("\"goods_category\":").append("\"").append(goods.getCat_name()).append("\",");
//			  jsonStr.append("\"goods_type\":").append("\"").append(goods.getGoods_type()).append("\",");
//			  jsonStr.append("\"goods_class\":").append("\"").append(goods.getModel()).append("\",");
//			  jsonStr.append("\"goods_state\":").append("\"").append(goods.getState()).append("\",");
//			  
//		     jsonStr.append("\"goods_attr\":[");
//			  String str=goods.getParams();
//			//  str=str.substring(1,str.lastIndexOf("]"));
//				/*paramsL pl=JsonUtil.fromJson(str, paramsL.class);
//				logger.info(pl.getParamList().size());
//				 for(int i=0;i<pl.getParamList().size();i++){
//					
//					 paramsenum tmp=pl.getParamList().get(i);
//					 jsonStr.append("{");
//					 jsonStr.append("\"param_code\":").append(tmp.getEname()).append(",");
//					  jsonStr.append("\"param_name\":").append(tmp.getName()).append(",");
//					  jsonStr.append("\"param_value_code\":").append(tmp.getAttrcode()).append(",");
//					  jsonStr.append("\"param_value\":").append(tmp.getValue()).append(",");
//					  jsonStr.append("\"sku_attr\":").append(tmp.attrvaltype);
//					  jsonStr.append("}");
//					  if(1<pl.getParamList().size()-1)
//						  jsonStr.append(",");
//				 }
//					  
//				  jsonStr.append("]}}");
//					
//				return jsonStr.toString();
//				*/
//		
//		   String str=" SELECT eg.name,eg.model_code,eg.sku,eg.state,eg.params, eb.name brand_name, ec.name cat_name,et.name type_name,eg.specs FROM es_goods eg  left join es_brand eb  on eg.brand_id = eb.brand_id left join ES_GOODS_CAT ec on eg.cat_id = ec.cat_id  left join es_goods_type et on eg.type_id=et.type_id";
//		   SqlExe sqlExec = new SqlExe();
//		   Map tmpMap=null;
//		   try {
//			tmpMap=  sqlExec.queryForMap(str);
//		} catch (FrameException e) {
//			
//			e.printStackTrace();
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		   SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//			String dtime = df.format(new Date());
//		   StringBuffer jsonStr=new StringBuffer();
//		   String seq=null;
//			try {
//				seq = sqlExec.queryForString("select seq_goods.nextVal from dual");
//			} catch (FrameException e) {
//				
//				e.printStackTrace();
//			} catch (SQLException e) {
//			
//				e.printStackTrace();
//			}
//			  jsonStr.append("{\"goods_info_req\":");
//			  jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
//			  jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
//
//		      jsonStr.append("\"sku\":").append("\"").append(tmpMap.get("sku")).append("\",");
//			  jsonStr.append("\" source_system \":").append("\"").append("     ").append("\",");
//			  jsonStr.append("\"receive_system\":").append("\"").append("     ").append("\",");
//			  jsonStr.append("\"action\":").append("\"").append("A ").append("\",");
//			 
//				jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");
//			  jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");
//			  jsonStr.append("\"goods_category\":").append("\"").append(tmpMap.get("cat_name")).append("\",");
//			  jsonStr.append("\"goods_type\":").append("\"").append(tmpMap.get("type_name")).append("\",");
//			  jsonStr.append("\"goods_class\":").append("\"").append(tmpMap.get("model_code")).append("\",");
//			  jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
//			  
//		     jsonStr.append("\"goods_attr\":[");
//			  String strpar=tmpMap.get("params").toString();
//			  strpar=strpar.substring(1,strpar.lastIndexOf("]"));
//				paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
//				 for(int i=0;i<pl.getParamList().size();i++){
//					
//					 paramsenum tmp=pl.getParamList().get(i);
//					 jsonStr.append("{");
//					 jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
//					  jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
//					  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getAttrcode()).append("\",");
//					  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
//					  jsonStr.append("\"sku_attr\":").append("\"").append(tmp.attrvaltype).append("\"");
//					  jsonStr.append("}");
//					  if(i<pl.getParamList().size()-1)
//						  jsonStr.append(",");
//				 }
//			  
//				  jsonStr.append("]}}");
//				  return jsonStr.toString();
//					
//		   
//	}
//	
//	public static  void wmsprosyn(){
//		String sqlStr="SELECT ei.product_id,nvl(ei.sku,ei.product_id) sku, ew.house_code,el.change_num,el.change_reason,eal.order_id FROM es_goods_inventory ei left join es_warehouse ew on ei.house_id = ew.house_id left join es_goods_inventory_log el on ei.product_id=el.product_id left join es_goods_inventory_apply_log eal on ei.product_id=eal.obj_id";
//		SqlExe sqlExec = new SqlExe();
//		Map tmpMap=null;
//		try {
//			tmpMap=sqlExec.queryForMap(sqlStr);
//		} catch (FrameException e) {			
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		String seq=null;
//		try {
//			seq = sqlExec.queryForString("select seq_goods.nextVal from dual");
//		} catch (FrameException e) {
//			
//			e.printStackTrace();
//		} catch (SQLException e) {
//		
//			e.printStackTrace();
//		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//		String dtime = df.format(new Date());
//		StringBuffer jsonStr=new StringBuffer();
//		jsonStr.append("{\"inventory_info_req\":{");
//		jsonStr.append("\"serial_no\":").append("\"").append(seq).append("\",");
//		jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
//		jsonStr.append("\"source_system \":").append("\"").append("").append("\",");
//		jsonStr.append("\"receive_system\":").append("\"").append("").append("\",");
//		jsonStr.append("\"sku\":").append("\"").append(tmpMap.get("sku")).append("\",");
//		jsonStr.append("\"inventory_code\":").append("\"").append(tmpMap.get("house_code")).append("\",");
//		jsonStr.append("\"inventory_num\":").append("\"").append(tmpMap.get("change_num")).append("\",");
//		jsonStr.append("\"change_reason\":").append("\"").append(tmpMap.get("change_reason")).append("\",");
//		jsonStr.append("\"inventory_order\":").append("\"").append(tmpMap.get("order_id")).append("\"");
//		jsonStr.append("}}");
//		
//		logger.info(jsonStr);
//		
//	}
//	public static  void wmsgInfosyn(){
//		String sqlStr="SELECT ew.house_code,ew.house_name,ew.status,ew.address,ew.scope_code,ew.remarks FROM es_warehouse ew ";
//
//		SqlExe sqlExec = new SqlExe();
//		Map tmpMap=null;
//		try {
//			tmpMap=sqlExec.queryForMap(sqlStr);
//		} catch (FrameException e) {		
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		String seq=null;
//		try {
//			seq = sqlExec.queryForString("select seq_goods.nextVal from dual");
//		} catch (FrameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//		String dtime = df.format(new Date());
//
//		
//		StringBuffer jsonStr=new StringBuffer();
//		jsonStr.append("{\"inventory_req\":{");
//		jsonStr.append("\"serial_no\":").append("\"").append(seq).append("\",");
//		jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
//		jsonStr.append("\"source_system \":").append("\"").append("").append("\",");
//		jsonStr.append("\"receive_system\":").append("\"").append("").append("\",");
//		jsonStr.append("\"action \":").append("\"").append("").append("\",");
//		jsonStr.append("\"inventory_code\":").append("\"").append(tmpMap.get("house_code")).append("\",");
//		jsonStr.append("\"inventory _name\":").append("\"").append(tmpMap.get("house_name")).append("\",");
//		jsonStr.append("\"inventory_desc\":").append("\"").append(tmpMap.get("remarks")).append("\",");
//		jsonStr.append("\"inventory_state\":").append("\"").append(tmpMap.get("status")).append("\",");
//		jsonStr.append("\"inventory_addr\":").append("\"").append(tmpMap.get("address")).append("\",");
//		jsonStr.append("\"scope \":").append("\"").append(tmpMap.get("scope_code")).append("\"");
//		jsonStr.append("}}");
//		
//		logger.info(jsonStr);
//		
//	}
//	public static void getPamfromjson(String str){
////		str=str.substring(1,str.lastIndexOf("]"));
////		paramsL pl=JsonUtil.fromJson(str, paramsL.class);
////		 for(int i=0;i<pl.getParamList().size();i++){
////			 logger.info(pl.getParamList().get(i).getEname());
////		 }
//	}
//
// public static void main(String[] args){
//
////
////logger.info(searchProductEcs("2014032710440"));
////	 
////wmsgInfosyn();
////
////wmsprosyn();
//	 
// }
}
