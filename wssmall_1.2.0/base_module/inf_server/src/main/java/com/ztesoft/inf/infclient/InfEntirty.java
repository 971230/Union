package com.ztesoft.inf.infclient;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import rule.params.coqueue.req.CoQueueRuleReq;
import zte.params.brand.req.BrandListAllReq;
import zte.params.brand.req.BrandModelListAllReq;
import zte.params.brand.resp.BrandListResp;
import zte.params.brand.resp.BrandModelListResp;
import zte.params.goods.req.GoodsInfoGetReq;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.resp.GoodsInfoGetResp;
import zte.params.goods.resp.ProductsListResp;
import zte.params.goodscats.req.CatsListByIdReq;
import zte.params.goodscats.resp.CatsListByIdResp;
import zte.params.goodstype.req.TypeListReq;
import zte.params.goodstype.resp.TypeListResp;
import zte.params.number.resp.NumberSynInfo;
import zte.params.number.resp.NumberSynInfoResp;
import zte.params.store.resp.InventoryApplyLogResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsInventoryApplyLog;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.remote.inf.IWarehouseService;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;

public class InfEntirty {
	@Resource
	private static ICacheUtil cacheUtil;
	@Resource
	IWarehouseService warehouseService;
	private static Logger logger = Logger.getLogger(InfEntirty.class);
	private static ICommClientBO commClientBO;
	private static ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	/**
	 * 商品库存同步
	 * @param response
	 * @param coQueueRuleReq
	 * @return
	 */
	public static String goodInventorySyn(InventoryApplyLogResp response, CoQueueRuleReq coQueueRuleReq){
		StringBuffer json = new StringBuffer();
		Warehouse wh=response.getWarehouse();
		GoodsInventoryApplyLog gal=response.getApplyLog();
		String result = null;
		try {
			String seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
			json.append("{\"prod_offer_inventory_req\":{");
			json.append("\"serial_no\":").append("\"").append(seq+time).append("\",");	//序列号
			json.append("\"time\":").append("\"").append(time).append("\",");	//时间
			json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			json.append("\"receive_system\":").append("\"").append("10008").append("\",");	//接收方系统标识
			json.append("\"inventory_code\":").append("\"").append(wh.getHouse_code()).append("\",");	//仓库编码
			json.append("\"inventory_name\":").append("\"").append(wh.getHouse_name()).append("\",");	//仓库名称
			json.append("\"prod_offer_code\":").append("\"").append(gal.getSku()).append("\",");	//商品编码
			String channel = getChannelCode(coQueueRuleReq);
			json.append("\"channel\":").append("\"").append(channel).append("\",");	//商城编码
			json.append("\"is_share_other\":").append("\"").append("1").append("\",");	//系统内共享
			json.append("\"is_share\":").append("\"").append(gal.getIs_share()).append("\",");	//是否共享
			json.append("\"action\":").append("\"").append(gal.getAction()).append("\",");	//动作
			json.append("\"prod_offer_num\":").append("\"").append(gal.getInventory_num()).append("\",");	//商品数量
			json.append("\"prod_offer_reason\":").append("\"").append(gal.getAction_name()).append("\"");	//商品数量变动原因
			json.append("}}");
		} catch (Exception e) {
			json = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		return json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	/**
	 * 活动信息同步
	 * @param response
	 * @param coQueueRuleReq
	 * @return
	 */
	public static String activitySyn(PromotionMapByIdResp response,CoQueueRuleReq coQueueRuleReq){
		String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
		Map pt=response.getPmt_map();
		List<Map>gl=response.getGoodsLst();
		List<Map> g=response.getGiftsLst();
		String result = null;
 			String seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
 			Date date=new Date();
 			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
 			String time=format.format(date);
 			StringBuffer json=new StringBuffer();
 			json.append("{\"activity_req\":{");
 			json.append("\"serial_no\":").append("\"").append(seq+time).append("\",");	//序列号
 			json.append("\"time\":").append("\"").append(time).append("\",");	//时间
 			json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
 			json.append("\"receive_system\":").append("\"").append("10008").append("\",");	//接收方系统标识
 			String a=coQueueRuleReq.getCoQueue().getAction_code();
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
 			json.append("\"action\":").append("\"").append(a).append("\",");	//动作
 			json.append("\"action_id\":").append("\"").append(coQueueRuleReq.getObject_id()).append("\",");  //活动标识			
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
 			if ("-1".equals(region) || province_region.equals(region)) {
				region = cacheUtil.getConfigInfo("PROVINCE_CITY_REGOIN_CODES");
			} 			
// 			String channelStr=getChannelCode(coQueueRuleReq);
			String channelStr =coQueueRuleReq.getCoQueue().getOrg_id_str();
 			String modify_time = coQueueRuleReq.getCoQueue().getCreated_date().replaceAll("[-|:| |.]", "").substring(0, 14);
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
	
	/**
	 * 获取商城的编码
	 * @param coQueueRuleReq
	 * @return
	 */
	public static String getChannelCode(CoQueueRuleReq coQueueRuleReq){
		CoQueue queue = coQueueRuleReq.getCoQueue();
		String orgId = queue.getOrg_id_str();
		StringBuffer jsonBuffer = new StringBuffer();
		String []arr = orgId.split(",");
		try {
			String sql = null;
			String orgCode = null;
			for (int i = 0; i < arr.length; i++) {
				sql = "select a.org_code from es_goods_org a where a.party_id = " + arr[i];
				orgCode = getICommClientBO().queryForString(sql);
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
	 * 号码信息同步
	 * @param response
	 * @param coQueueRuleReq
	 * @return
	 */
	public static String numSync(NumberSynInfoResp response,CoQueueRuleReq coQueueRuleReq){
		StringBuffer jsonStr = new StringBuffer();
		String seq = null;
		try {
			seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String channel = coQueueRuleReq.getCoQueue().getOrg_id_str();
			if (null == channel || "".equals(channel)) {
				//logger.info("号码同步异常.object_id[" + coQueueRuleReq.getObject_id() + "],channel为空.");
				return null;
			}else {
				channel = getChannelCode(coQueueRuleReq);
			}
			List<NumberSynInfo> ni = response.getNumberSynInfoList();
			jsonStr.append("{\"phone_no_req\":{");
			jsonStr.append("\"serial_no\":").append("\"").append(seq).append("\",");
			jsonStr.append("\"time\":").append("\"").append(df.format(new Date())).append("\",");
			jsonStr.append("\"source_system\":").append("\"10011\",");
			jsonStr.append("\"receive_system\":").append("\"10008\",");
			jsonStr.append("\"channel\":").append("\"").append(channel).append("\",");
			jsonStr.append("\"action\":").append("\"").append(coQueueRuleReq.getAction_code()).append("\",");
			jsonStr.append("\"phone_list\":[");

			for (int i = 0; i < ni.size(); i++) {
				NumberSynInfo number = ni.get(i);
				jsonStr.append("{");
				jsonStr.append("\"phone_no\":").append("\"").append(number.getNo()).append("\",");  //号码
				jsonStr.append("\"phone_city_code\":").append("\"").append(number.getRegion_id()).append("\",");  //地市编码
				jsonStr.append("\"phone_no_state\":").append("\"").append(number.getStatus()).append("\",");  //号码状态
				String phone_group_id = number.getGroup_id();
				if (null == number.getGroup_id() || "".equals(number.getGroup_id())) {
					phone_group_id = "-1";
				}
				jsonStr.append("\"phone_group_id\":").append("\"").append(phone_group_id).append("\",");  //号码分组标识
				jsonStr.append("\"phone_group\":").append("\"").append(number.getGroup_name()).append("\",");  //号码分组
				jsonStr.append("\"phone_batch\":").append("\"").append(number.getBatch_id()).append("\",");  //号码批次
				jsonStr.append("\"is_good_no\":").append("\"").append(number.getIs_lucky()).append("\",");  //是否靓号
				jsonStr.append("\"good_no_type\":").append("\"").append(number.getNo_classify()).append("\",");  //靓号类型
				jsonStr.append("\"good_no_rule\":").append("\"").append(number.getRule_id()).append("\",");  //靓号规则
				jsonStr.append("\"good_no_deposit\":").append("\"").append(number.getDeposit()).append("\",");  //靓号预存款
				jsonStr.append("\"deposit_adjust\":").append("\"").append(number.getFee_adjust()).append("\",");  //靓号减免款
				jsonStr.append("\"good_no_limit\":").append("\"").append(number.getPeriod()).append("\",");  //合约期
				jsonStr.append("\"good_no_fee\":").append("\"").append(number.getLowest()).append("\",");  //最低消费额
				jsonStr.append("\"mobile_net\":").append("\"").append(number.getNo_gen()).append("\",");  //网别
				String pay_type = number.getCharge_type();
				if (null == pay_type || "".equals(pay_type)) {
					pay_type = "";
				}else if ("1".equals(pay_type)) {
					pay_type = "PRE";
				}else if ("2".equals(pay_type)) {
					pay_type = "BAK";
				}
				jsonStr.append("\"pay_type\":").append("\"").append(pay_type).append("\"");	//付费类型
				jsonStr.append("}");
				if ( i < ni.size() -1 ){
					jsonStr.append(",");
				}
			}
			jsonStr.append("]}}");
		} catch (Exception e) {
			jsonStr = new StringBuffer();
			//logger.info("号码同步异常.object_id[" + coQueueRuleReq.getObject_id() + "]");
			logger.info(e.getMessage(), e);
		}
		//把所有null替换为空
		return jsonStr.toString().replaceAll("\"null\"", "\"\"");
	}
	
	/**
	 * 货品库存同步
	 * @param resp
	 * @param coQueueRuleReq
	 * @return
	 */
	public static String proInventorySyn(InventoryApplyLogResp resp, CoQueueRuleReq coQueueRuleReq){
		StringBuffer json = new StringBuffer();
		GoodsInventoryApplyLog gal = resp.getApplyLog();
		Warehouse wh = resp.getWarehouse();
		String result = null;
		try {
			String seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
			json.append("{\"goods_inventory_req\":{");
			json.append("\"serial_no\":").append("\"").append(seq+time).append("\",");	//序列号
			json.append("\"time\":").append("\"").append(time).append("\",");	//时间
			json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			json.append("\"receive_system\":").append("\"").append(coQueueRuleReq.getCoQueue().getOrg_id_belong()).append("\",");	//接收方系统标识
			json.append("\"inventory_code\":").append("\"").append(wh.getHouse_code()).append("\",");	//仓库编码
			json.append("\"inventory_name\":").append("\"").append(wh.getHouse_name()).append("\",");	//仓库名称
			json.append("\"sku\":").append("\"").append(gal.getSku()).append("\",");	//货品编码
			CoQueue cq = coQueueRuleReq.getCoQueue();
			String orgId = cq.getOrg_id_str();
//			String channel = getChannelCode(coQueueRuleReq);
			String channel = orgId;
			json.append("\"channel\":").append("\"").append(channel).append("\",");	//商城编码
			json.append("\"is_share_other\":").append("\"").append("1").append("\",");	//系统内共享
			json.append("\"is_share\":").append("\"").append(gal.getIs_share()).append("\",");	//是否共享
			json.append("\"action\":").append("\"").append(gal.getAction()).append("\",");	//动作
			json.append("\"prod_offer_num\":").append("\"").append(gal.getInventory_num()).append("\",");	//货品数量
			json.append("\"prod_offer_reason_ID\":").append("\"").append(gal.getAction()).append("\",");	//货品数量变动原因编码
			json.append("\"prod_offer_reason\":").append("\"").append(gal.getAction_name()).append("\"");	//货品数量变动原因
			json.append("}}");
		} catch (Exception e) {
			json = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		//把所有null替换为空
		return json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	//品牌同步
	public static String brandSyn(){
		
		BrandListAllReq req = new BrandListAllReq();
	
		   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		   BrandListResp response = client.execute(req, BrandListResp.class);
		   List<Brand> lb = response.getBrands();
		return null;
	}

	//类型同步
		public static String typeSyn(String type_id){	
			TypeListReq req = new TypeListReq();
		      req.setType_id(type_id);
			   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			   TypeListResp response = client.execute(req, TypeListResp.class);
			   List<GoodsType> lgt = response.getTypes();	
			return null;
		}
		
		//分类同步
				public static String classSyn(String type_id){
					
					CatsListByIdReq req = new CatsListByIdReq();
				      req.setCat_id(type_id);
					   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					   CatsListByIdResp response = client.execute(req, CatsListByIdResp.class);
					   List<Cat> lc = response.getCats();		
					return null;
				}

	//货品同步
				
				//货品同步
				public  static String searchProductEcs(String object_id,String action_code,String orgId){
					
					String goods_id = null;
					   logger.info("============================进入货品同步=goods_id="+goods_id);
					 // String str=" SELECT  ec.type_id  cat_idi,  ep.sku skua, eg.goods_id,  eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id "; //where eg.goods_id='201403215013731
					   
					   // String str=" SELECT   ep.sku,   eq.action_code,  eg.name,  eb.name        brand_name, ec.name        cat_name,  et.name        type_name,   em.model_name,    eg.model_code,  ep.sn, eg.state, eg.params     FROM es_goods eg  left join es_brand eb   on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec   on eg.cat_id = ec.cat_id  left join es_goods_type et  on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id    left join es_brand_model em  on eg.model_code=em.model_code  left join es_product ep   on ep.goods_id = eg.goods_id ";		   
					   
					  
					   Map tmpMap=null;
						   goods_id = object_id;
//						   goods_id  = getICommClientBO().queryForString(" SELECT A.PRODUCT_ID FROM ES_PRODUCT_CO A WHERE A.ID = "+object_id);
						   String str="SELECT ep.sku skua,eg.goods_id,ec.type_id,eq.action_code,eg.name,eb.brand_code brand_name,ec.cat_id cat_name,et.type_id type_name,em.model_name,eg.model_code,ep.sn,eg.state,eg.params   FROM es_goods eg left join es_brand eb on eg.brand_id = eb.brand_id  left join ES_GOODS_CAT ec on eg.cat_id = ec.cat_id left join es_goods_type et on eg.type_id = et.type_id left join es_co_queue eq on eg.goods_id = eq.object_id  left join es_brand_model em on eg.model_code = em.model_code  left join es_product ep on ep.goods_id = eg.goods_id  where eg.type = 'product'  and  eg.goods_id='"+goods_id+"'";
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
	/*public static String productSyn(String goods_id){
		
	ProductInfoGetReq req = new ProductInfoGetReq();
   req.setProduct_id(goods_id);
   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
  ProductInfoGetResp response = client.execute(req, ProductInfoGetResp.class);
	Goods goods = response.getProduct();
	   SqlExe getICommClientBO() = new SqlExe();
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String dtime = df.format(new Date());
	    StringBuffer jsonStr=new StringBuffer();
	    String seq=null;
		try {
			seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
		} catch (FrameException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	
		  jsonStr.append("{\"goods_info_req\":");
		  jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
		  jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");

	      jsonStr.append("\"sku\":").append("\"").append(goods.getSku()).append("\",");
		  jsonStr.append("\" source_system \":").append("\"").append("10011").append("\",");
		  jsonStr.append("\"receive_system\":").append("\"").append("10008").append("\",");
		  jsonStr.append("\"action\":").append("\"").append("A").append("\",");//调用CoQueueRuleReq获取
		  jsonStr.append("\"goods_name\":").append("\"").append(goods.getName()).append("\",");
		  jsonStr.append("\"goods_brand\":").append("\"").append(goods.getBrand_name()).append("\",");
		  jsonStr.append("\"goods_category\":").append("\"").append(goods.getCat_name()).append("\",");
		  jsonStr.append("\"goods_type\":").append("\"").append(goods.getType_name()).append("\",");
		  jsonStr.append("\"goods_class\":").append("\"").append(goods.getModel_code()).append("\",");//tmpMap.get("model_code")
		  jsonStr.append("\"goods_spec\":").append("\"").append(goods.getSn()).append("\",");
		  jsonStr.append("\"goods_state\":").append("\"").append(goods.getState()).append("\",");
		  try{
			  String strpar =  goods.getParams();
			  strpar=strpar.substring(1,strpar.lastIndexOf("]"));
			
			  paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
			  jsonStr.append("\"goods_attr\":[");
			  
				 for(int i=0;i<pl.getParamList().size();i++){
					
					 paramsenum tmp=pl.getParamList().get(i);
					 jsonStr.append("{");
					  jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
					  jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
					  jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getAttrcode()).append("\",");
					  Map tmpMapdc=null;
					  Map tmpMap_yanshe=null;						
					  if(tmp.getAttrcode().equals("DC_GOODS_COLOR")){
						  String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = 'DC_GOODS_COLOR'";
						  SqlExe getICommClientBO()dc = new SqlExe();
						   try {
							tmpMapdc=  getICommClientBO()dc.queryForMap(strdc);
						} catch (FrameException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						 
						    String dc_sql =  tmpMapdc.get("dc_sql").toString();
						    String dca = dc_sql.replaceAll("'", "");
						  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
						  logger.info("查颜色SQL------------------str_yanshe="+str_yanshe);
						  SqlExe getICommClientBO()_yanshe = new SqlExe();
						  try {
							  tmpMap_yanshe =  getICommClientBO()_yanshe.queryForMap(str_yanshe);
							} catch (FrameException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
				
					     if(tmpMap_yanshe.get("value_desc") != null){
					    	 jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\","); 
					    	 jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
					     }else{
					    	 //没改前的的逻辑
					    	 jsonStr.append("\"param_value_code\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
					    	 jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
					     }
					  
					  }else{
						  jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
						  jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
						//  jsonStr.append("\"param_value\":").append("\"").append("").append("\",");
						  logger.info("进入ELSE----------------------------");  
					  }						
					  jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\""); 
					  jsonStr.append("}");
					  if(i<pl.getParamList().size()-1)
						  jsonStr.append(",");						
			  }
				 jsonStr.append("]");
		  }catch(Exception ex){
			  
		  }
		  
			  jsonStr.append("}}");
			  return jsonStr.toString();							
	}*/
	
	//号码信息同步
	public static String numSyn(NumberSynInfoResp response,CoQueueRuleReq coQueueRuleReq){
		String str = new String();

			String seq;
			try {
				seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
			
			// 获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String dtime = df.format(new Date());
//		   NumberSynInfoReq req = new NumberSynInfoReq();
//		     req.setId(id);
//		   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
//		   NumberSynInfoResp response = client.execute(req, NumberSynInfoResp.class);
		   List<NumberSynInfo> ni = response.getNumberSynInfoList();
			
				StringBuffer jsonStr = new StringBuffer();
				jsonStr.append("{\"phone_no_req\":{");
				jsonStr.append("\"serial_no\":").append("\"").append("" + seq + "")
						.append("\",");
				jsonStr.append("\"time\":").append("\"").append("" + dtime + "")
						.append("\",");
				jsonStr.append("\"source_system\":").append("\"").append("10011")
						.append("\",");
				jsonStr.append("\"receive_system\":").append("\"").append("10008")
						.append("\",");
				
				
				   String tmpStr=getChannel(coQueueRuleReq);
				
				jsonStr.append("\"channel\":").append("\"").append(tmpStr)//调用API
						.append("\",");
				jsonStr.append("\"action\":").append("\"").append(coQueueRuleReq.getAction_code()).append("\",");
				jsonStr.append("\"phone_list\":").append("[");
				for (int i = 0; i < ni.size(); i++) {
					NumberSynInfo map = ni.get(i);
					jsonStr.append("{");
					jsonStr.append("\"phone_no\":").append("\"")
							.append(map.getNo()).append("\",");
					jsonStr.append("\"phone_city_code\":").append("\"")
							.append( map.getRegion_id() )
							.append("\",");
					jsonStr.append("\"phone_no_state\":").append("\"")
							.append(map.getStatus())
							.append("\",");
					
					//map.getGroup_id()==null?jsonStr.append("\"phone_group_id\":").append("\"").append("-1").append("\","):jsonStr.append("\"phone_group_id\":").append("\"").append(map.getGroup_id()).append("\",");
					if(map.getGroup_id()==null)
						jsonStr.append("\"phone_group_id\":").append("\"").append("-1").append("\",");
					else
					jsonStr.append("\"phone_group_id\":").append("\"").append(map.getGroup_id()).append("\",");
					
					
					jsonStr.append("\"phone_group\":").append("\"").append(map.getGroup_name())
							.append("\",");
					jsonStr.append("\"phone_batch\":").append("\"")
							.append( map.getBatch_id()).append("\",");
					jsonStr.append("\"is_good_no\":").append("\"")
							.append( map.getIs_lucky()).append("\",");	
				jsonStr.append("\"good_no_type\":").append("\"")
					       .append(map.getNo_classify()).append("\",");	
					jsonStr.append("\"good_no_rule\":").append("\"")
							.append(map.getRule_id())
							.append("\",");
					jsonStr.append("\"good_no_deposit\":").append("\"")
							.append("" + map.getDeposit()+ "")
							.append("\",");
					jsonStr.append("\"deposit_adjust\":").append("\"").append(map.getFee_adjust()).append("\",");
					jsonStr.append("\"good_no_limit\":").append("\"")
							.append(map.getPeriod())
							.append("\",");
					jsonStr.append("\"good_no_fee\":").append("\"")
							.append( map.getLowest()).append("\",");
					jsonStr.append("\"mobile_net\":").append("\"")
					.append( map.getNo_gen()).append("\",");
					
					 if("1".equalsIgnoreCase(map.getCharge_type())){
							jsonStr.append("\"pay_type\":").append("\"")
							.append( "PRE").append("\"");
					 }else if("2".equalsIgnoreCase(map.getCharge_type())){
						jsonStr.append("\"pay_type\":").append("\"")
						.append( "BAK").append("\"");
					 }else{
						jsonStr.append("\"pay_type\":").append("\"")
						.append( map.getCharge_type()).append("\"");
					 }
					
//					jsonStr.append("\"mobile_net\":").append("\"")
//							.append(map.get("mobile_net")").append("\"");
					jsonStr.append("}");
					if (i<ni.size()-1){
						jsonStr.append(",");
					}
				}
				jsonStr.append("]");
				jsonStr.append("}}");
				str= jsonStr.toString();
			} catch (Exception  e) {
			}		
		return str;
	}
	
	//型号同步
		public String modelSyn(String modelCode) {
			
			
			BrandModelListAllReq req = new BrandModelListAllReq();
		     req.setModel_code(modelCode);
		   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		   BrandModelListResp response = client.execute(req, BrandModelListResp.class);
		   List bml=response.getBrandModelLst();
			
			String str = new String();
				// 获取序列
				String seq = getICommClientBO()
						.queryForString("select seq_goods.nextVal from dual"); 
				// 获取系统当前时间
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
				String dtime = df.format(new Date());
				Map m = getICommClientBO()
						.queryForMap("select ee.model_code class_code,ee.model_name class_name,ee.brand_code from es_brand_model ee where ee.model_code='"
								+ modelCode+"'");

				str = "{" + "\"class_req\":" + "{\"serial_no\":\"" + seq + "\","
						+ "\"time\":\"" + dtime + "\"," + "\"source_system \":\""
						+ "10011" + "\"," + "\"receive_system \":\"" + "10008"
						+ "\"," + "\"class_code\":\"" + m.get("class_code") + "\","
						+ " \"action\":\"" + "A" + "\"," + "\"class_name\":\""
						+ m.get("class_name") + "\"," + "\"brand_code\":\""
						+ m.get("brand_code") + "\"," + "\"class_state\":\"" + "0"
						+ "\"" + "" + "}" + "}";
			return str;
		}

  //商品信息同步
		public static String goodSyn(String goods_id){
						
			GoodsInfoGetReq req = new GoodsInfoGetReq();
			   req.setGoods_id(goods_id);
			   ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			   GoodsInfoGetResp response = client.execute(req, GoodsInfoGetResp.class);
				Goods goods = response.getGoods();
				
				//ProductsListReq ?ProductsListResp
				ProductsListReq pr=new ProductsListReq();
				pr.setGoods_id(goods_id);
				ProductsListResp plr=client.execute(pr, ProductsListResp.class);
				List<Goods> p=plr.getProducts();
			String result = null;
					//Map map = getICommClientBO().queryForMap("select a.sn as prod_offer_code,a.name as prod_offer_name,d.name as prod_offer_type,b.name as prod_offer_brand,c.model_name as terminal_model,a.state as prod_offer_state,a.price as prod_offer_price,a.weight as prod_offer_heavy,f.tag_name as prod_offer_package,a.sku as sku,a.store as goods_num,a.params from es_goods a left join es_brand b on b.brand_id = a.brand_id left join es_brand_model c on a.model_code = c.model_code left join es_goods_cat d on a.cat_id=d.cat_id left join es_tag_rel e on e.rel_id=a.goods_id left join es_tags f on e.tag_id =f.tag_id where a.type = 'goods' and a.goods_id = '"+goods_id+"'");
					String seq = getICommClientBO().queryForString("select seq_goods.nextVal from dual");
				    Date date=new Date();
				    DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
				    String time=format.format(date);
				    StringBuffer json=new StringBuffer();
				      json.append("{\"prod_offer_req\":");
					//  json.append("{\"tip\":").append("\"").append("商品信息同步").append("\",");
					  json.append("\"serial_no\":").append("\"").append(seq+time).append("\",");
					  json.append("\"time\":").append("\"").append(time).append("\",");
					  json.append("\"source_system\":").append("\"").append("10011").append("\",");
					  json.append("\"receive_system\":").append("\"").append("10008").append("\",");
					  		
					  json.append("\"action\":").append("\"").append("A").append("\",");
					  
					  json.append("\"prod_offer_code\":").append("\"").append(goods.getSku()).append("\",");
					  json.append("\"prod_offer_name\":").append("\"").append(goods.getName()).append("\",");
					  
					  json.append("\"channel\":").append("\"").append("").append("\",");
					  
					  
					  json.append("\"prod_offer_type\":").append("\"").append(goods.getCat_id()).append("\",");
					  json.append("\"prod_offer_brand\":").append("\"").append(goods.getBrand_name()).append("\",");
					  json.append("\"terminal_model\":").append("\"").append(goods.getModel_code()).append("\",");
					  json.append("\"prod_offer_state\":").append("\"").append(goods.getState()).append("\",");
					  json.append("\"prod_offer_price\":").append("\"").append(goods.getPrice()).append("\",");
					  json.append("\"prod_offer_heavy\":").append("\"").append(goods.getWeight()).append("\",");
					  
					  
					  
					  json.append("\"prod_offer_package\":").append("\"").append("").append("\",");//尚需个API
				  json.append("\"prod_offer_ele\":[");
				  
				  for(int i=0;i<p.size();i++){
					  Goods g=p.get(i);
				  json.append("{\"sku\":").append("\"").append(g.getSku()).append("\",");
				  json.append("\"goods_num\":").append("\"").append(g.getAll_count()).append("\"");
				  json.append("}"); 
				  if(i<p.size()-1)
					  json.append(",");
				  }
				  json.append("]");
					  
					  try{
						  String strpar =  goods.getParams();
						  strpar=strpar.substring(1,strpar.lastIndexOf("]"));
						
						  paramsL pl=JsonUtil.fromJson(strpar, paramsL.class);
						  json.append("\"prod_offer_param\":[");
						  
							 for(int i=0;i<pl.getParamList().size();i++){
								
								 paramsenum tmp=pl.getParamList().get(i);
								 json.append("{");
								  json.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
								  json.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
								  json.append("\"param_value_code\":").append("\"").append(tmp.getAttrcode()).append("\",");
								  Map tmpMapdc=null;
								  Map tmpMap_yanshe=null;
								  if(tmp.getAttrcode().equals("DC_GOODS_COLOR")){
									  String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = 'DC_GOODS_COLOR'";
										tmpMapdc=  getICommClientBO().queryForMap(strdc);
									
									    String dc_sql =  tmpMapdc.get("dc_sql").toString();
									 
									    String dca = dc_sql.replaceAll("'", "");
								
									  String str_yanshe="select value_desc from ("+dca+")T where T.VALUE ="+tmp.getValue().toString();
									
										  tmpMap_yanshe =  getICommClientBO().queryForMap(str_yanshe);
								
								  json.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
								  }else{
									  json.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
									
								  }
								  json.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\"");
								  json.append("}");
								  if(i<pl.getParamList().size()-1)
									  json.append(",");
							
						  }
							 json.append("]");
					  }catch(Exception ex){
						  
					  }
					
					//  json.append("\"prod_offer_attr\":[");
					  //json.append("]");待定
					
						  json.append("}}"); 
					  logger.info(json);
			return result;
			
			
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
			
			public static String getChannel(CoQueueRuleReq coQueueRuleReq){
				 CoQueue cq=coQueueRuleReq.getCoQueue();
				  String orgId=cq.getOrg_id_str();

				  String tmpStr=new String();
				  try{
				  if(orgId != null && !"".equalsIgnoreCase(orgId)){
					  String[] orgIdStr=orgId.split(",");
					  if(orgIdStr.length > 0){
						  for(int i=0;i<orgIdStr.length;i++){
							  String sqlStr="select a.org_code from es_goods_org a where a.party_id="+orgIdStr[i];
							  String orgCode=getICommClientBO().queryForString(sqlStr);
//							  if(i<orgIdStr.length){
//								  tmpStr=tmpStr+","+orgCode;
//							  }
//							  else tmpStr+=orgCode;
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
				  }catch(Exception e){
					  
				  }
				  return tmpStr;
			}
			
			public static void main(String args[]){
				//logger.info(InfEntirty.productSyn("692014032724617"));
				InfEntirty.brandSyn();
			}

}
