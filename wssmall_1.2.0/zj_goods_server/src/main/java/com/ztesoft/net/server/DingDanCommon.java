package com.ztesoft.net.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderExtInfoGetReq;
import zte.params.order.resp.OrderExtInfoGetResp;
import zte.params.order.resp.OrderTaobaoSyncResp;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderItemAddAccept;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallActivity_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallGift_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallWCFPackage;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallDiscountInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallFeeInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGiftInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallLeagueInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallPackage;
import com.ztesoft.remote.inf.IActivityService;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;

public class DingDanCommon extends BaseSupport{

	private IActivityService activityService;
	public DingDanCommon(IDaoSupport baseDaoSupport , IActivityService activityService){
		this.baseDaoSupport = baseDaoSupport;
		this.activityService = activityService;
	}
	//卡类型
	private String sim_type = "白卡";
	//是否靓号
	private String is_goodno = "0";
	//靓号预存
	private String good_no_fee = "0";
	//靓号低消
	private String good_no_low = "0";
	//订单来源
	private String order_from = "";
	private SimpleDateFormat simpleFormat = new SimpleDateFormat( "yyyyMMddHHmmss" );
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//套餐名
	private String taocan_name = "";
	//上网卡时长
	private String online_time = "";
	//基本通信服务及附加业务名称及描述
	private String ywmcms = "";
	//号码信息
	private List accnbrList = null;
	//获取本地扩展表信息
	private Map localOrderAttr = null;
	//货品费用
	private String sku_fee = "";
	
	//扩展信息字段
	//配送时间
	private String shipping_time = "";
	//买家标识
	private String uid = "";
	//电子邮件
	private String ship_email = "";
	//实收运费
	private String n_shipping_amount = "";
	//物流公司编码
	private String shipping_company = "";
	//地址商圈
	private String ship_area = "";
	//卖家留言
	private String seller_message = "";
	//发票内容
	private String invoice_group_content = "";
	//是否已办理完成
	private String is_deal = "";
	//仓库名称
	private String inventory_name = "";
	//可选活动
	private String choose_active = "";
	//推荐人手机
	private String reference_phone = "";
	//客户类型
	private String customerType = "";
	//总部费用明细
	private String feeInfo = "";
	//是否闪电送
	private String shipping_quick = "";
	//总部可选择包信息
	private String zbPackage = "";
	//新商城可选包
	private String mallPackage = "";
	//是否变更套餐
	private String is_change = "";
	private String package_sale = "0";
	private String propack_code = "";
	private String propack_desc = "";
	private String is_self = "0";
	private String is_group_contract = "0";
	private String fund_type;	//基金类型
	//总部优惠信息
	private String discountInfos = "";
	//总部赠品信息
	private String giftInfos = "";
	private String group_code = "";
	private String group_name = "";
	private String industry_type = "";
	private String industry_sub_type = "";
	private String goodsNameExt = "";
	private String order_points = "";
	private String points_user = "";
	private String offer_point = "";
	private String service_remarks = "";	//淘宝备注信息
	
	//商品type_id
	private String goods_type_id = "";
	//商品是否人工生产
	private String is_manual_type="";
	private String agent_code="";
	private String agent_name="";
	private String agent_city="";
	private String agent_district="";
	private String channel_type="";
	
	//预留字段
	private String baidu_begin_time = "";
	private String baidu_end_time = "";
	private String invoice_title = "";
	private String inventory_code = "";
	private String activity_list = "";
	private String gift_list = "";
	private String prod_offer_type = "";
	private String goods_cat_id = "";
	private String zbpackage_list = "";
	private String mallpackage_list = "";
	private String ad_service = "";
	private String djyck = "0";
	private String ssyh ="";
	private String prod_offer_code = "";
	private String channel_id = "";
	private String channel_mark = "";
	private String spread_channel = "";
	private String chanel_name = "";
	private String is_to4g = "";
	private String accNbr_sku = null;	//号码的sku
	private String netcard_type = null;	//上网卡的号码的sku
	
	private String pmt_code = null;	//活动编码
	private String pmtCodeList = null; //活动编码对应的优惠信息
	private String tb_activity_list = null;	//淘宝优惠信息
	
	private String source_type = null;	//订单来源
	private String regist_type = null;	//订单接入类别
	private String league_info = null;	//联盟信息
	
	//支撑沃云购2.0
	private String bss_operator = null;	//BSS工号
	private String bss_operator_name = null;	//BSS工号
	private String oss_operator = null;	//订单支撑系统工号
	private String terminal_num = null;//终端串号
	
	private String offer_price = null;  //商品价格
	private String mobile_price = null; //终端价格
	private String deposit_fee = null;  //合约费用
	private String new_type_id = null;  //淘宝用的上网卡type_id;
	private String new_cat_id = null;	//淘宝用的上网卡cat_id;
	private String new_prod_brand =null;//淘宝用的上网卡prod_brand;
	private String sys_code=null; 		//标志新老系统标识
	


	/**
	 * 获取扩展信息
	 * @param o
	 */
	private void getOrderExtInfo(Order o){

//		扩展信息
		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
		OrderExtInfoGetReq extReq = new OrderExtInfoGetReq();
		extReq.setOrder_id(o.getOrder_id());
		OrderExtInfoGetResp extResp = orderServices.getOrderExtInfo(extReq);
		
		Map<String,String> mapExt = extResp.getExtMap();
		shipping_time = MallUtils.getValues(mapExt.get("shipping_time"));
		uid = MallUtils.getValues(mapExt.get("uid"));
		ship_email = MallUtils.getValues(mapExt.get("ship_email"));
		n_shipping_amount = MallUtils.getValues(mapExt.get("n_shipping_amount"));
		if (MallUtils.isEmpty(n_shipping_amount)) {
			n_shipping_amount = "0";
		}else {
			n_shipping_amount = MallUtils.parseMoneyToLi(Double.parseDouble(n_shipping_amount));
		}
		shipping_company = MallUtils.getValues(mapExt.get("shipping_company"));
		ship_area = MallUtils.getValues(mapExt.get("ship_area"));
		seller_message = MallUtils.getValues(mapExt.get("seller_message"));
		invoice_group_content = MallUtils.getValues(mapExt.get("invoice_group_content"));
		is_deal = MallUtils.getValues(mapExt.get("is_deal"));
		inventory_name = MallUtils.getValues(mapExt.get("inventory_name"));
		choose_active = MallUtils.getValues(mapExt.get("choose_active"));
		terminal_num = MallUtils.getValues(mapExt.get("terminal_num"));//终端串号
		is_change = MallUtils.getValues(mapExt.get("is_change"));
		reference_phone = MallUtils.getValues(mapExt.get("reference_phone"));
		customerType = MallUtils.getValues(mapExt.get("CustomerType"));
		shipping_quick = MallUtils.getValues(mapExt.get("shipping_quick"));
		package_sale = MallUtils.getValues(mapExt.get("package_sale"));
		propack_desc = MallUtils.getValues(mapExt.get("ProPacDesc"));
		propack_code = MallUtils.getValues(mapExt.get("ProPacCode"));
		goodsNameExt = MallUtils.getValues(mapExt.get("GoodsName"));
		fund_type = MallUtils.getValues(mapExt.get("fund_type"));
		offer_point = MallUtils.getValues(mapExt.get("offer_point"));
		order_points = MallUtils.getValues(mapExt.get("order_points"));
		points_user = MallUtils.getValues(mapExt.get("points_user"));
		group_code = MallUtils.getValues(mapExt.get("group_code"));
		group_name = MallUtils.getValues(mapExt.get("group_name"));
		industry_type = MallUtils.getValues(mapExt.get("industry_type"));
		industry_sub_type = MallUtils.getValues(mapExt.get("industry_sub_type"));
		spread_channel = MallUtils.getValues(mapExt.get("spread_channel"));
		regist_type = MallUtils.getValues(mapExt.get("regist_type"));

		bss_operator = MallUtils.getValues(mapExt.get("bss_operator"));
		bss_operator_name = MallUtils.getValues(mapExt.get("bss_operator_name"));
		agent_code=MallUtils.getValues(mapExt.get("agent_code"));
		agent_name=MallUtils.getValues(mapExt.get("agent_name"));
		agent_city=MallUtils.getValues(mapExt.get("agent_city"));
		agent_district=MallUtils.getValues(mapExt.get("agent_district"));
		channel_type=MallUtils.getValues(mapExt.get("channel_type"));
		oss_operator = MallUtils.getValues(mapExt.get("oss_operator"));
		
		
		if(MallUtils.isEmpty(regist_type)){
			regist_type = "SELF";
		}
		source_type = MallUtils.getValues(mapExt.get("source_type"));
		if(MallUtils.isEmpty(source_type)){
			source_type = "GDMALL";
		}
		String league_info_tmp = MallUtils.getValues(mapExt.get("LeagueInfo"));
		if(MallUtils.isEmpty(league_info_tmp)){
			league_info = "\"league_info\":{\"league_id\":\"\",\"league_name\":\"\",\"higher_league_id\":\"\",\"higher_league_name\":\"\"},";
		}else {
			league_info = "";
			StringBuffer stringBuffer = new StringBuffer();
			JSONArray jsonArray = JSONArray.fromObject(league_info_tmp);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				CenterMallLeagueInfo leagueInfo = (CenterMallLeagueInfo)JSONObject.toBean(jsonObject,CenterMallLeagueInfo.class);
				stringBuffer.append("\"league_info\":{");
				stringBuffer.append("\"league_id\":\"").append(leagueInfo.getLeagueId()).append("\",");
				stringBuffer.append("\"league_name\":\"").append(leagueInfo.getLeagueName()).append("\",");
				stringBuffer.append("\"higher_league_id\":\"").append(leagueInfo.getHigherLeagueId()).append("\",");
				stringBuffer.append("\"higher_league_name\":\"").append(leagueInfo.getHigherLeagueName()).append("\"");
				stringBuffer.append("},");
			}
			league_info = stringBuffer.toString();
		}
		
//		总部费用明细
		feeInfo = MallUtils.getValues(mapExt.get("feeinfo"));		
//		获取sku_fee json参数列表
		if (MallUtils.isEmpty(feeInfo)) {
			sku_fee = "\"sku_fee\":[]";
		} else {
			sku_fee = getSkuFee(feeInfo);
		}
		//总部可选包
		zbPackage = MallUtils.getValues(mapExt.get("zbpackages"));
		if (MallUtils.isEmpty(zbPackage)) {
			zbpackage_list = "\"Package\":[],";
		}else {
			zbpackage_list = getZBPackageInfo(zbPackage);
		}
		//新商城可选包
		mallPackage = MallUtils.getValues(mapExt.get("wcfPackages"));
		if (MallUtils.isEmpty(mallPackage)) {
			mallpackage_list = "\"Package\":[],";
		}else {
			mallpackage_list = getNewMallPackageInfo(mallPackage);
		}
		
		pmtCodeList = getPmtAactivityList(pmt_code);
		//商城优惠活动信息
		String t_activity_list = MallUtils.getValues(mapExt.get("activity_list"));
		if (MallUtils.isEmpty(t_activity_list)) {
			activity_list = "\"activity_list\":[],";
		}else {
			activity_list = getActiveList(t_activity_list);
		}
		//总部优惠信息
		String t_discountInfos = MallUtils.getValues(mapExt.get("discountInfos"));
		if (MallUtils.isEmpty(t_discountInfos)) {
			discountInfos = "\"activity_list\":[],";
		}else {
			discountInfos = getZBActiveList(t_discountInfos);
		}
		//淘宝的优惠信息
		if(MallUtils.isNotEmpty(pmtCodeList)){
			tb_activity_list = "\"activity_list\":["+pmtCodeList+"],";
		}else{
			tb_activity_list = "\"activity_list\":[],";
		}
		
		//总部赠品信息
		String t_giftInfos = MallUtils.getValues(mapExt.get("giftInfos"));
		if (MallUtils.isNotEmpty(t_giftInfos)) {
			if(checkFieldValueExists("zbgiftisold", "1")){
				giftInfos = getZBGiftInfo_Old(t_giftInfos);
			}else {
				giftInfos = getZBGiftInfo(t_giftInfos);
			}
		}
	}
	
	/**
	 * 获取sku_fee信息,总部的费用明细存放在扩展信息表中
	 * @param str
	 * @return
	 */
	private String getSkuFee(String str){
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
		JSONArray jsonArray = JSONArray.fromObject(str);
		for (int i = 0; i < jsonArray.size(); i++) {
			sku_feeStr.append("{");
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			CenterMallFeeInfo feeInfo = (CenterMallFeeInfo)JSONObject.toBean(jsonObject,CenterMallFeeInfo.class);
			sku_feeStr.append("\"fee_item_code\":\""+feeInfo.getFeeID()+"\",");
			sku_feeStr.append("\"fee_item_name\":\""+feeInfo.getFeeDes()+"\",");
			sku_feeStr.append("\"o_fee_num\":\""+ (Integer.parseInt(feeInfo.getOrigFee()) * 1000)+"\",");
			sku_feeStr.append("\"disacount_fee\":\""+ (Integer.parseInt(feeInfo.getReliefFee()) * 1000)+"\",");
			sku_feeStr.append("\"disacount_reason\":\""+feeInfo.getReliefResult()+"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ (Integer.parseInt(feeInfo.getRealFee()) * 1000)+"\"");
			sku_feeStr.append("}");
			if (i != jsonArray.size() - 1) {
				sku_feeStr.append(",");
			}
		}
		sku_feeStr.append("]");
		return sku_feeStr.toString();
	}
	
	/**
	 * 获取送订单系统的可选包信息
	 * @param zbPackage
	 * @return
	 */
	private String getZBPackageInfo(String zbPackage){
		StringBuffer packageStr = new StringBuffer();
		packageStr.append("\"Package\":[");
		JSONArray jsonArray = JSONArray.fromObject(zbPackage);
		for (int i = 0; i < jsonArray.size(); i++) {
			packageStr.append("{");
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			CenterMallPackage centerPackage = (CenterMallPackage)JSONObject.toBean(jsonObject,CenterMallPackage.class);
			packageStr.append("\"PackageCode\":\""+centerPackage.getPackageCode()+"\",");
			packageStr.append("\"PackageName\":\""+centerPackage.getPackageName()+"\",");
			packageStr.append("\"ProductCode\":\""+ centerPackage.getPackageCode() +"\",");
			packageStr.append("\"ElementCode\":\""+ centerPackage.getElementCode() +"\",");
			packageStr.append("\"ElementType\":\""+ centerPackage.getElementType() +"\",");
			packageStr.append("\"ElementName\":\""+ centerPackage.getElementName() +"\",");
			packageStr.append("\"OperType\":\"E\",");
			packageStr.append("\"ChageType\":\"O\",");
			packageStr.append("\"BizType\":\"FWYH\"");
			packageStr.append("}");
			if (i != jsonArray.size() - 1) {
				packageStr.append(",");
			}
		}
		packageStr.append("],");
		return packageStr.toString();
	}
	
	/**
	 * 获取新商城的可选包信息
	 * @param mallPackage
	 * @return
	 */
	private String getNewMallPackageInfo(String mallPackage){
		StringBuffer packageStr = new StringBuffer();
		packageStr.append("\"Package\":[");
		JSONArray jsonArray = JSONArray.fromObject(mallPackage);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			MallWCFPackage wcfPackage = (MallWCFPackage)JSONObject.toBean(jsonObject,MallWCFPackage.class);
			
			paramsL params = getProductParams(wcfPackage.getPackageCode());
			if (null != params && params.getParamList().size() > 0) {
				String bss_code = "";
				String package_code = "";
				String package_element_code = "";
				String package_name="";
				for (int j = 0; j < params.getParamList().size(); j++) {
					paramsenum tmp_param = params.getParamList().get(j);
					if ("bss_code".equalsIgnoreCase(tmp_param.getEname()) && "BSS编码".equalsIgnoreCase(tmp_param.getName())) {
						bss_code = tmp_param.getValue();
					}
					if ("package_code".equalsIgnoreCase(tmp_param.getEname()) && "可选包编码".equalsIgnoreCase(tmp_param.getName())) {
						package_code = tmp_param.getValue();
					}
					if ("package_name".equalsIgnoreCase(tmp_param.getEname()) && "可选包名称".equalsIgnoreCase(tmp_param.getName())) {
						package_name = tmp_param.getValue().trim();
					}
					if ("package_element_code".equalsIgnoreCase(tmp_param.getEname()) && "可选元素编码".equalsIgnoreCase(tmp_param.getName())) {
						package_element_code = tmp_param.getValue();
					}
				}
				packageStr.append("{");
				packageStr.append("\"PackageCode\":\""+ package_code +"\",");
				packageStr.append("\"PackageName\":\""+ package_name +"\",");
				packageStr.append("\"ProductCode\":\""+ package_code +"\",");
				packageStr.append("\"ElementCode\":\""+ package_element_code +"\",");
				packageStr.append("\"ElementType\":\"D\",");
				packageStr.append("\"ElementName\":\""+ params.getName() +"\",");
				packageStr.append("\"OperType\":\"E\",");
				packageStr.append("\"ChageType\":\"O\",");
				packageStr.append("\"BizType\":\"FWYH\"");
				packageStr.append("}");
				if (i != jsonArray.size() - 1) {
					packageStr.append(",");
				}
			}
		}
		packageStr.append("],");
		return packageStr.toString();
	}
	
	/**
	 * 获取送订单系统的优惠活动数据
	 * @param active_list
	 * @return
	 */
	private String getActiveList(String active_list){
		StringBuffer activeStr = new StringBuffer();
		activeStr.append("\"activity_list\":[");
		String activity_desc = "";
		String disacount_num = "0";
		try {
			JSONArray jsonArray = JSONArray.fromObject(active_list);
			for (int i = 0; i < jsonArray.size(); i++) {
				MallActivity_List activity = null;
				try {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					Map<String, Class> classMap = new HashMap<String, Class>();
					classMap.put("gift_list", MallGift_List.class);
					activity = (MallActivity_List)JSONObject.toBean(jsonObject, MallActivity_List.class, classMap);
					
					//获取赠品信息
					if (null != activity.getGift_list() && activity.getGift_list().size() > 0) {
						getGiftInfo(activity.getGift_list());
					}
					//根据activity_code(优惠编码)获取id(优惠ID)
					String activity_id = querySqlResult("select id from es_promotion_activity c where c.pmt_code = '"+ activity.getActivity_code() +"'");
					//获取活动同步信息
					PromotionMapByIdReq request = new PromotionMapByIdReq();
					request.setActivity_id(activity_id);
					PromotionMapByIdResp resp = activityService.getPromotionMap(request);
					
					Map pmt_map = resp.getPmt_map();
					if (null != pmt_map && pmt_map.size() > 0) {
						activeStr.append("{");
						activeStr.append("\"activity_code\":\""+ pmt_map.get("pmt_code") +"\",");
						activeStr.append("\"activity_name\":\""+ pmt_map.get("pmt_code") +"\",");
						activity_desc = pmt_map.get("pmt_describe").toString();
						if (MallUtils.isEmpty(activity_desc)) {
							activity_desc = pmt_map.get("pmt_name").toString();
						}
						disacount_num = pmt_map.get("pmt_solution").toString();
						try {
							disacount_num = MallUtils.parseMoneyToLi(Double.parseDouble(disacount_num));
						} catch (Exception e) {
							disacount_num = "0";
							logger.info("disacount_num["+disacount_num+"]转换为厘失败");
						}
						activeStr.append("\"activity_desc\":\""+ activity_desc +"\",");					
						activeStr.append("\"activity_type\":\"3\",");
						activeStr.append("\"disacount_range\":\"\",");
						activeStr.append("\"disacount_num\":\""+disacount_num+"\",");
						activeStr.append("\"disacount_unit\":\"02\"");
						activeStr.append("}");
						if (i != jsonArray.size() - 1) {
							activeStr.append(",");
						}
					}
				} catch (Exception e) {
					logger.info("获取优惠信息失败pmt_code["+activity.getActivity_code()+"]");
					logger.info(e.getMessage(),e);
				}					
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		activeStr.append("],");
		return activeStr.toString();
	}
	
	/**
	 * 获取商品的参数
	 * @param sku
	 * @return
	 */
	private paramsL getProductParams(String sku){
		paramsL params = null;
		String sql = "select name,params from es_goods c where sku = ?";
		try {
			Map map = this.baseDaoSupport.queryForMap(sql, sku);
			if (null != map && map.size() > 0) {
				String name = map.get("name").toString();
				String paramsValue = map.get("params").toString();
				params = JsonUtil.fromJson(paramsValue.substring(1,paramsValue.lastIndexOf("]")), paramsL.class);
				params.setName(name);
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return params;
	}
	
	/**
	 * 获取受理单节点信息
	 * @param jsonStr
	 * @param o
	 * @param accept
	 */
	private void getAccountValue(StringBuffer jsonStr ,Order o ,OrderItemAddAccept accept , String goods_id){
		//受理单
    	jsonStr.append("\"AccountInfo\":");
    	jsonStr.append("{\"AcceptanceForm\":");
        /*mainContentOne        
        1、  套餐名称：《套餐名称》，详见业务协议
        2、  《按合约数据文档中的产品描述。》*/
    	String sms_send_num = "";
		String answering_free = "";
		String call_times = "";
		String flow = "";
		String visual_phone = "";
		String other_business = "";
		String out_flow = "";
    	String pay_type = "";
    	try {
			Map map = this.baseDaoSupport.queryForMap("select a.params from v_product_params a where a.a_goods_id = ? and rownum < 2", goods_id);
			if(map != null){
				String strpar = map.get("params").toString();
				strpar = strpar.substring(1,strpar.lastIndexOf("]"));
				JSONObject a1=JSONObject.fromObject(strpar);
				JSONArray aa1= a1.getJSONArray("paramList");
				
				for(int s=0;s<aa1.size();s++){
					JSONObject jsonobj = (JSONObject) aa1.get(s);
					String attrcode = jsonobj.getString("attrcode");
					String value = jsonobj.getString("value");
					if(attrcode != null && !"".equalsIgnoreCase(attrcode)){
						String dc_sql = querySqlResult("select DC_SQL from es_dc_sql  a where  a.dc_name = '"+attrcode +"' and rownum < 2");
						String dc_sql_value = querySqlResult("select value_desc from (select 'ECS' source_from, e.* from ("+dc_sql+") e)T where T.VALUE ='"+value+"' and rownum < 2");
						value = dc_sql_value;
					}
				  if(value != null &&!"".equalsIgnoreCase(value)){
		    	    	if("sms_send_num".equalsIgnoreCase(attrcode)){//国内短信发送条数
							 sms_send_num = "国内短信发送条数" + value+",";
						}else if("answering_free".equalsIgnoreCase(attrcode)){//接听免费
							 answering_free = value+"接听免费,";
						}else if("call_times".equalsIgnoreCase(attrcode)){//国内语音拨打分钟数
							 call_times = "国内语音拨打"+value+"分钟,";
						}else if("flow".equalsIgnoreCase(attrcode)){//国内流量
							 flow = "国内流量"+value+",";
						}else if("visual_phone".equalsIgnoreCase(attrcode)){//可视电话
							 visual_phone = "可视电话"+value+"分钟,";
						}else if("other_business".equalsIgnoreCase(attrcode)){//国内流量
							 other_business = "赠送增值业务"+value+",";
						}else if("out_flow".equalsIgnoreCase(attrcode)){//国内流量
							 out_flow = "套餐外流量费用"+value+"元/分钟";
						}else if("pay_type".equalsIgnoreCase(attrcode)){//国内流量
							if( "BAK".equalsIgnoreCase(value)){
								pay_type = "后付费";
							}else{
								pay_type = "预付费";
							}
						}
    	            }
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
    	
    	String mainContentOne_1 = "";
    	String mainContentOne_2 = "";
    	
    	if(taocan_name !=null&&!"".equalsIgnoreCase(taocan_name)){
    		mainContentOne_1 ="1、  套餐名称："+ taocan_name+"，详见业务协议";
    	}else {
    		mainContentOne_1 = "无套餐信息";
    	}
    	if("".equalsIgnoreCase(sms_send_num)&&"".equalsIgnoreCase(answering_free)&&"".equalsIgnoreCase(call_times)
    			&&"".equalsIgnoreCase(flow)&&"".equalsIgnoreCase(visual_phone)&&"".equalsIgnoreCase(other_business)&&"".equalsIgnoreCase(out_flow))
    	{
    		mainContentOne_2= sms_send_num+answering_free+call_times+flow+visual_phone+other_business+out_flow;
    	}else{
    		mainContentOne_2 = "2、"+sms_send_num+answering_free+call_times+flow+visual_phone+other_business+out_flow;
    	}
      	jsonStr.append("{\"mainContentOne\":").append("\"").append(mainContentOne_1).append(mainContentOne_2).append("\","); 
    	jsonStr.append("\"AcceptanceMode\":").append("\"").append("0").append("\",");
    	jsonStr.append("\"contactAddr\":").append("\"").append(getValue(o.getShip_addr())).append("\",");
    	/*mainContentTwo
    	 * 基本通信服务及附加业务名称及描述：《国内通话（这个指的是号卡），国内上网卡，本地上网卡，国际上网卡，合约机，裸终端》。<br/>
    	 * 可选业务包名称及描述：《可选活动名称》<br/>
    	 * 号码信息：您选择的号码18502510173，具体规则详见业务协议 。<br/>
    	 * 活动信息：《俊伟同步的BSS数据中的活动的名称》。<br/>*/
//    	String is_group = "";
    	
    	String type_id  = querySqlResult("select a.type_id from es_goods a where a.goods_id = '"+o.getGoods_id()+"'");
    	
    	if("20000".equalsIgnoreCase(type_id)){  //号卡
    		ywmcms = "国内通话";
    	}else if("20001".equalsIgnoreCase(type_id)){   //上网卡
    		;
    	}else if("20002".equalsIgnoreCase(type_id)){   //合约机
    		ywmcms = "合约机";
    	}else if("20003".equalsIgnoreCase(type_id)){   //裸机
    		ywmcms = "裸终端";
    	}
    	
		jsonStr.append("\"mainContentTwo\":").append("\"").append("基本通信服务及附加业务名称及描述：").append(ywmcms).append("\\r").
		append("可选业务包名称及描述：").append("可选活动名").append("\\r").append("号码信息：您选择的号码").append(getValue(accept.getPhone_num())).
		append("，具体规则详见业务协议").append("\",");
//		append("，具体规则详见业务协议").append("<br/>").append("活动信息：").append("").append("\",");
    	
    	
    	jsonStr.append("\"agentPaperType\":").append("\"").append("").append("\",");//代理人证件类型，来源不清楚  空
    	jsonStr.append("\"acctAddr\":").append("\"").append(getValue(o.getShip_addr())).append("\",");//cust_addr
    	jsonStr.append("\"userType\":").append("\"").append("").append("\",");//prod_offer_type  跟字典值做对应关系，字典值不清楚        空
    	jsonStr.append("\"paperExpr\":").append("\"").append("").append("\",");//证件有效期   传空值
    	jsonStr.append("\"acctName\":").append("\"").append(getValue(accept.getPhone_owner_name())).append("\",");//用户姓名   cust_name
    	jsonStr.append("\"agentPhone\":").append("\"").append("").append("\",");//代理人电话号码，来源不清楚  空
    	jsonStr.append("\"custType\":").append("\"").append(customerType).append("\",");//客户类型  字典值有（个人或集团）  要商城传，接口要改
    	
    	//付费方式  payment_type  跟字典值做对应关系，字典值不清楚
    	String payment_type = accept.getPay_mothed();
    	String payment_name = querySqlResult("select c.field_desc from es_mall_config c where c.field_name = 'payment_type' and c.field_value = '"+payment_type+"'");
		if (MallUtils.isEmpty(payment_name)) {
			payment_name = payment_type;
		}
		jsonStr.append("\"payMethod\":").append("\"").append(payment_name).append("\",");
		
    	jsonStr.append("\"paperAddr\":").append("\"").append(getValue(accept.getCert_address())).append("\",");    //证件地址  certi_addr	    	
    	jsonStr.append("\"paperType\":").append("\"").append(getValue(accept.getCerti_type())).append("\",");  //证件类型  certi_type
    	jsonStr.append("\"staffInfo\":").append("\"").append("").append("\","); //受理人及工号  传空值                                
    	jsonStr.append("\"contactPhone\":").append("\"").append(getValue(o.getShip_mobile())).append("\",");//客户联系电话  ship_phone
    	jsonStr.append("\"bankAcctName\":").append("\"").append(getValue(accept.getBank_account())).append("\",");//银行帐号      bank_cust_code
    	jsonStr.append("\"bankName\":").append("\"").append("").append("\",");//银行名称      bank_name
    	jsonStr.append("\"bankAcct\":").append("\"").append("").append("\",");//该字段没有用到，来源不清楚   空
    	jsonStr.append("\"paperNo\":").append("\"").append(getValue(accept.getCerti_number())).append("\",");//证件号码  certi_num
    	jsonStr.append("\"bankCode\":").append("\"").append(getValue(accept.getBank_code())).append("\",");//银行编码  bank_code
    	jsonStr.append("\"agentPaperNo\":").append("\"").append("").append("\",");//代理人证件号，来源不清楚  空
    	jsonStr.append("\"agentName\":").append("\"").append("").append("\",");//代理人姓名，来源不清楚  空	
    	jsonStr.append("\"userNo\":").append("\"").append(getValue(accept.getPhone_num())).append("\"");
    	jsonStr.append("},");
    	jsonStr.append("\"AcceptanceMode\":").append("\"").append("0").append("\",");//默认“打套”，送“0”
    	
    	/*"RECEIPT_CODE  "	打印规则 	
    	presub_t	号码是预付费的合约机和号卡
    	postsub_t	号码是后付费的合约机和号卡
    	wlpost_p_t	非集团用户的后付费无线上网卡
    	wlpost_g_t	集团用户的后付费无线上网卡
    	precard_t	预付费无线上网卡
    	
    	合约机：号码+合约计划+手机+套餐
    	号卡：号码+合约计划（可选）+套餐
    	上网卡：套餐+上网卡硬件（可选）
    	裸机：手机*/
    	
    	
    	if("20000".equalsIgnoreCase(type_id)){//号卡
    		if("预付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    		}else if("后付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("postsub_t").append("\",");
    		}else{
        		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
        	}
    	}else if("20002".equalsIgnoreCase(type_id)){//合约机
    		if("预付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    		}else if ("后付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("postsub_t").append("\",");
    		}else{
        		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
        	}
    	}else if ("20001".equalsIgnoreCase(type_id)&&"后付费".equalsIgnoreCase(pay_type)){//上网卡
    		if("后付费".equalsIgnoreCase(pay_type)){
    			if("农行商城".equalsIgnoreCase(o.getSource_from())){ //是否集团用户  农行商城过来的单子为集团用户，其他为非集团客户
					jsonStr.append("\"AcceptanceTp\":").append("\"").append("wlpost_g_t").append("\",");
				}else{
					jsonStr.append("\"AcceptanceTp\":").append("\"").append("wlpost_p_t").append("\",");
				}
    		}else {
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("precard_t").append("\",");
			}
    	}else{
    		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    	}
    	//保留
    	jsonStr.append("\"Para\":");
    	jsonStr.append("{\"ParaID\":").append("\"").append("").append("\",");
    	jsonStr.append("\"ParaValue\":").append("\"").append("").append("\"");
    	
    	jsonStr.append("}");
    	jsonStr.append("},");
	}
	
	/**
	 * 执行查询SQL，查单列，单行
	 * @param sql
	 * @return
	 */
	private String querySqlResult(String sql){
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
	 * 获取变量的值
	 * @param o
	 * @return
	 */
	private String getValue(Object o){
		String result = "";
		if(o != null && !"null".equalsIgnoreCase(o.toString())) return o.toString();
		return result;
	}
	
	/**
	 * 获取预留字段
	 * @param o
	 * @throws Exception 
	 */
	private void getReserveInfo(Order o) throws Exception{
		try {
			Map m = this.baseDaoSupport.queryForMap(" select a.reserve0,a.reserve1,a.reserve2,a.reserve3,a.reserve4,a.reserve5,a.reserve6,a.reserve7,a.reserve8,a.reserve9,a.house_id,a.out_house_id from es_outer_accept a WHERE a.order_id = ? AND ROWNUM < 2 ", o.getOrder_id());
			if(m != null){
				if (MallUtils.isNotEmpty(m.get("reserve5").toString())) {
					baidu_begin_time = m.get("reserve5").toString().replaceAll("[-|:| |.]", "");
				}
				if (MallUtils.isNotEmpty(m.get("reserve6").toString())) {
					baidu_end_time = m.get("reserve6").toString().replaceAll("[-|:| |.]", "");
				}
				if (MallUtils.isNotEmpty(m.get("reserve8").toString())) {
					invoice_title = m.get("reserve8").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve7").toString())) {
					ssyh = m.get("reserve7").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve3").toString())) {
					channel_id = m.get("reserve3").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve2").toString())) {
					channel_mark = m.get("reserve2").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve4").toString())) {
					chanel_name = m.get("reserve4").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve1").toString())) {
					is_to4g = m.get("reserve1").toString();
				}
				if (MallUtils.isNotEmpty(m.get("out_house_id").toString())) {
					inventory_code = m.get("out_house_id").toString();
				}
				if (MallUtils.isNotEmpty(m.get("reserve9").toString())) {
					pmt_code = m.get("reserve9").toString();	//活动编码
					if(pmt_code.indexOf(",") != -1){
						logger.info("订单["+o.getOrderItemAcceptList().get(0).getOut_tid()+"]活动编码值不正确,请检查！");
						throw new Exception("pmt_code error");
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			if("pmt_code error".equals(e.getMessage())){
				throw new Exception("pmt_code error");
			}
		}
	}
	
	/**
	 * 转换证件类型
	 * @param certi_type
	 * @return
	 */
	private String parseCertiType(String certi_type){
		if("1".equalsIgnoreCase(certi_type)){
    		return "SFZ18";
    	}else if("2".equalsIgnoreCase(certi_type)){
    		return "SFZ15";
    	}else if("3".equalsIgnoreCase(certi_type)){
    		return "HZB";
    	}else if("4".equalsIgnoreCase(certi_type)){
    		return "JUZ";
    	}else if("6".equalsIgnoreCase(certi_type)){
    		return "HKB";
    	}else if("7".equalsIgnoreCase(certi_type)){
    		return "JGZ";
    	}else if("10".equalsIgnoreCase(certi_type)){
    		return "GOT";
    	}else if("11".equalsIgnoreCase(certi_type)){
    		return "TWT";
    	}else {
			return certi_type;
		}
	}
	
	/**
	 * 转换发票打印方式
	 * @param invoice_print_type
	 * @return
	 */
	private String parseInvoicePrintType(String invoice_print_type){
		if("分月打印".equalsIgnoreCase(invoice_print_type)){
			return "2";
    	}else if("不打印发票".equalsIgnoreCase(invoice_print_type)){
    		return "3";
    	}else if("一次性打印".equalsIgnoreCase(invoice_print_type)){
    		return "1";
    	}else if("一次性发票".equalsIgnoreCase(invoice_print_type)){
    		return "1";
    	}else{
    		return invoice_print_type;
    	}
	}
	
	/**
	 * 获取号码节点信息
	 * @param skustr
	 * @param o
	 * @param accept
	 * @param acc_nbr
	 */
	private void getAccnbrParam(StringBuffer skustr ,OrderItemAddAccept accept,String acc_nbr){
		String acc_nbr_bz = "";
		String nbr_net_type = accept.getProduct_net();
		String net_region = "";
		String is_old = localOrderAttr.get("is_old").toString();
		String bill_type = localOrderAttr.get("bill_type").toString();
		String password = localOrderAttr.get("password").toString();
		String family_no = accept.getFamliy_num();
		String if_love_no = accept.getIs_loves_phone();
		String love_no = accept.getLoves_phone_num();
		String sex = localOrderAttr.get("sex").toString();
		String birthday = localOrderAttr.get("birthday").toString();
		String contact_addr = localOrderAttr.get("contact_addr").toString();
		String parents_bank_code = localOrderAttr.get("parents_bank_code").toString();
		String bank_code = localOrderAttr.get("bank_code").toString();
		String bank_name = localOrderAttr.get("bank_name").toString();
		String bank_cust_code = localOrderAttr.get("bank_cust_code").toString();
		String bank_cust_name = localOrderAttr.get("bank_cust_name").toString();
		String credit_class = localOrderAttr.get("credit_class").toString();
		String credit_adjust = localOrderAttr.get("credit_adjust").toString();
		String guarantor_info = localOrderAttr.get("guarantor_info").toString();
		String guarantor_certi_type = localOrderAttr.get("guarantor_certi_type").toString();
		String guarantor_certi_no = localOrderAttr.get("guarantor_certi_no").toString();
		String bill_mail_content = localOrderAttr.get("bill_mail_content").toString();
		String bill_mail_rec = localOrderAttr.get("bill_mail_rec").toString();
		String bill_mail_addr = localOrderAttr.get("bill_mail_addr").toString();
		String bill_mail_post_code = localOrderAttr.get("bill_mail_post_code").toString();
		String vicecard_no = localOrderAttr.get("vicecard_no").toString();
		String guarantor = localOrderAttr.get("guarantor").toString();
		String bill_mail_type = localOrderAttr.get("bill_mail_type").toString();
		String sub_no = localOrderAttr.get("reserve0").toString();  //共享子号
		//合约机默认白卡
		if (checkFieldValueExists("isbaika", goods_type_id)) {
			sim_type = "白卡";
		}
		//VIP商城默认成卡
		if ( checkFieldValueExists("ischengka", order_from) ) {
			sim_type = "成卡";
		}
		//老用户默认成卡
		//“副卡号码”不为空时，根据商品的“是否成品卡”属性来判定sim_type的值。
		if ("1".equals(is_old) && MallUtils.isEmpty(vicecard_no)) {
			sim_type = "成卡";
		}else if("1".equals(is_old) && MallUtils.isNotEmpty(vicecard_no)){
			sim_type = "白卡";
		}
		//如果有副卡号码,则把acc_nbr与vicecard_no反转过来,订单系统要求(陈奥)
		//if(MallUtils.isNotEmpty(vicecard_no)){
		//	String tmp = vicecard_no;
		//	vicecard_no = acc_nbr;
		//	acc_nbr = tmp;
		//}
		
		//如果商城有值,以商城的为准
		String sim_type_tmp = localOrderAttr.get("sim_type").toString();
		if (MallUtils.isNotEmpty(sim_type_tmp)) {
			sim_type = sim_type_tmp;
		}
		
		String card_type = accept.getWhite_cart_type();
		if (MallUtils.isEmpty(card_type)) {
			card_type = "none";
		}
		
		if (MallUtils.isEmpty(guarantor)) {
			guarantor = "无";
		}
		if (MallUtils.isEmpty(bill_mail_type)) {
			bill_mail_type = "00";
		}
		if (MallUtils.isEmpty(bill_type)) {
			bill_type = "10";
		}
		//获取号码节点的sku
		if ("20002".equals(goods_type_id) && MallUtils.isNotEmpty(accept.getSpecification_code())) {
			//合约机
			accNbr_sku = querySqlResult("select c.machine_code from es_brand_model c where model_code = '"+ accept.getSpecification_code() +"'");
		}else if ("20000".equals(goods_type_id) && "白卡".equals(sim_type)) {
			//号卡、号卡合约  白卡
			//获取card_type对应的货品名称
			String productName = querySqlResult("select c.reserve0 from es_mall_config c where c.field_name = 'bai_card_sku' and c.field_value = '"+card_type+"'");
			accNbr_sku = querySqlResult("select c.skua from v_product c where name = '"+productName+"'");
		}else if ("20000".equals(goods_type_id) && "成卡".equals(sim_type)) {
			//号卡、号卡合约  成卡
			//获取card_type对应的货品名称
			String productName = querySqlResult("select c.reserve0 from es_mall_config c where c.field_name = 'cheng_card_sku' and c.field_value = '"+card_type+"'");
			accNbr_sku = querySqlResult("select c.skua from v_product c where name = '"+productName+"'");
		}else if ("20001".equals(goods_type_id)) {
			//上网卡
			accNbr_sku = netcard_type;
		}
		if (MallUtils.isEmpty(accNbr_sku)) {
			accNbr_sku = "243667223";
		}

		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
		if(isTbOrder/*order_from.equals("10001")*/&&(prod_offer_type).equals("20000")&&StringUtils.isEmpty(acc_nbr)){
			acc_nbr="no_No";
		}
		
		//02-判断号码是否在新商品系统存在
		if(acc_nbr != null && !"".equalsIgnoreCase(acc_nbr)){   //有号码的号卡、合约机、上网卡				
			if(null != accnbrList && accnbrList.size() > 0){   //号码校验成功   该号码在新商品系统存在
			  Map accnbrMap = (Map)accnbrList.get(0);
			  //总部订单这三个传0
			  if ("10003".equals(order_from)) {
				  is_goodno = "0";
				  good_no_fee = "0";
				  good_no_low = "0";
			  }else if(checkFieldValueExists("istcgoodnofee", order_from)) {
				  //靓号费用透传的商城
				  is_goodno = accept.getIs_liang();
				  initGoodsNoFee();
				  
			  }else {
				  is_goodno = accnbrMap.get("is_lucky").toString();
				  if ("1".equals(is_goodno)) {
					  good_no_fee = MallUtils.parseMoneyToLi(Double.parseDouble(accnbrMap.get("deposit").toString()));
					  good_no_low = MallUtils.parseMoneyToLi(Double.parseDouble(accnbrMap.get("lowest").toString()));
				  }
			  }
			  acc_nbr_bz = "号码校验成功";
			  net_region = accnbrMap.get("region_id").toString();
			}else{
				//号码校验失败      该号码在新商品系统不存在
				acc_nbr_bz = "该号码在新商品系统不存在";
				is_goodno = accept.getIs_liang();
				if(StringUtils.isEmpty(is_goodno)){
					is_goodno="0";
				}
				initGoodsNoFee();
			}				
		}else{
			acc_nbr_bz = "号码不存在";
			acc_nbr = "none";
		}
		
		//243667223表示号码
		skustr.append(",{");
		skustr.append("\"sku_id\":\""+accNbr_sku+"\",");
		skustr.append("\"goods_name\":\"号码\",");
		skustr.append("\"goods_type\":\"10011\",");
		skustr.append("\"goods_category\":\"10011\",");
		skustr.append("\"goods_desc\":\"号码\",");
		skustr.append("\"is_virtual\":\"1\",");
		skustr.append("\"is_gift\":\"0\",");
		skustr.append("\"sku_num\":\"1\",");
		skustr.append("\"sku_param\":[");
		skustr.append("{\"param_code\":\"acc_nbr_bz\",\"param_name\":\"号码校验失败备注\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+acc_nbr_bz+"\"}");
		skustr.append(",{\"param_code\":\"acc_nbr\",\"param_name\":\"号码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+acc_nbr+"\"}");
		skustr.append(",{\"param_code\":\"nbr_net_type\",\"param_name\":\"网别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+nbr_net_type+"\"}");
		skustr.append(",{\"param_code\":\"net_region\",\"param_name\":\"入网地区\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+net_region+"\"}");
		skustr.append(",{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+is_goodno+"\"}");
		skustr.append(",{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+good_no_fee+"\"}");
		skustr.append(",{\"param_code\":\"good_no_low\",\"param_name\":\"靓号低消\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+good_no_low+"\"}");
		skustr.append(",{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+is_old+"\"}");
		skustr.append(",{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_type+"\"}");
		skustr.append(",{\"param_code\":\"card_type\",\"param_name\":\"卡类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+card_type+"\"}");
		skustr.append(",{\"param_code\":\"sim_type\",\"param_name\":\"成卡白卡\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+sim_type+"\"}");
		skustr.append(",{\"param_code\":\"password\",\"param_name\":\"老用户密码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+password+"\"}");
		skustr.append(",{\"param_code\":\"vicecard_no\",\"param_name\":\"副卡号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+vicecard_no+"\"}");
		skustr.append(",{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+family_no+"\"}" );
		skustr.append(",{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+if_love_no+"\"}");
		skustr.append(",{\"param_code\":\"love_no\",\"param_name\":\"情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+love_no+"\"}");
		skustr.append(",{\"param_code\":\"sex\",\"param_name\":\"性别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+sex+"\"}");
		skustr.append(",{\"param_code\":\"birthday\",\"param_name\":\"出生日期\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+birthday+"\"}");
		skustr.append(",{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+contact_addr+"\"}");
		skustr.append(",{\"param_code\":\"parents_bank_code\",\"param_name\":\"上级银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+parents_bank_code+"\"}");
		skustr.append(",{\"param_code\":\"bank_code\",\"param_name\":\"银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bank_code+"\"}");
		skustr.append(",{\"param_code\":\"bank_name\",\"param_name\":\"银行名称\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bank_name+"\"}");
		skustr.append(",{\"param_code\":\"bank_cust_code\",\"param_name\":\"银行账号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bank_cust_code+"\"}");
		skustr.append(",{\"param_code\":\"bank_cust_name\",\"param_name\":\"银行账户名\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bank_cust_name+"\"}");
		skustr.append(",{\"param_code\":\"credit_class\",\"param_name\":\"信用等级\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+credit_class+"\"}");
		skustr.append(",{\"param_code\":\"credit_adjust\",\"param_name\":\"信用度调整\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+credit_adjust+"\"}");
		skustr.append(",{\"param_code\":\"guarantor\",\"param_name\":\"担保人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+guarantor+"\"}");
		skustr.append(",{\"param_code\":\"guarantor_info\",\"param_name\":\"担保信息参数\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+guarantor_info+"\"}");
		skustr.append(",{\"param_code\":\"guarantor_certi_type\",\"param_name\":\"被担保人证件类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+guarantor_certi_type+"\"}");
		skustr.append(",{\"param_code\":\"guarantor_certi_no\",\"param_name\":\"被担保人证件号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+guarantor_certi_no+"\"}");
		skustr.append(",{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_mail_type+"\"}");
		skustr.append(",{\"param_code\":\"bill_mail_content\",\"param_name\":\"账单寄送内容\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_mail_content+"\"}");
		skustr.append(",{\"param_code\":\"bill_mail_rec\",\"param_name\":\"账单收件人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_mail_rec+"\"}");
		skustr.append(",{\"param_code\":\"bill_mail_addr\",\"param_name\":\"账单寄送地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_mail_addr+"\"}");
		skustr.append(",{\"param_code\":\"bill_mail_post_code\",\"param_name\":\"账单寄送邮编\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+bill_mail_post_code+"\"}");
		skustr.append(",{\"param_code\":\"sub_no\",\"param_name\":\"共享子号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\""+sub_no+"\"}");
		skustr.append("],");
		skustr.append(sku_fee);
		skustr.append("}");	
	}
	
	/**
	 * 获取商品参数信息
	 * @param orderId
	 * @return
	 */
	private String getOfferParam(String goods_id){ 
      
		Map	offerMap = new HashMap();
		StringBuffer osserString = new StringBuffer();
		try {
			offerMap = this.baseDaoSupport.queryForMap("select a.params,type_id from es_goods a where a.goods_id = '"+goods_id+"'");
			String mm = offerMap.get("params").toString();
			goods_type_id = offerMap.get("type_id").toString();
			mm = mm.substring(1,mm.lastIndexOf("]"));		
			
			osserString.append("\"offer_param\":[");
			JSONObject a=JSONObject.fromObject(mm);
			JSONArray aa= a.getJSONArray("paramList");
			String mon_return = "";
			for(int s=0;s<aa.size();s++){
				JSONObject jsonobj = (JSONObject) aa.get(s);
				String ename = jsonobj.getString("ename");
				String name = jsonobj.getString("name");
				String attrcode = jsonobj.getString("attrcode");
				String value_1 = jsonobj.getString("value");

				//上网卡号码的sku
				if("IS_GROUP".equalsIgnoreCase(ename)){
					ename = "is_group";
				}else if ("netcard_type".equalsIgnoreCase(ename)) {
					netcard_type = value_1;
				}
				
				if("is_manual".equalsIgnoreCase(ename)){
					is_manual_type=value_1;
				}
				
				if("mobile_price".equalsIgnoreCase(ename)){
					mobile_price=value_1;
				}
				if("deposit_fee".equalsIgnoreCase(ename)){
					deposit_fee=value_1;
				}
				if("new_type_id".equalsIgnoreCase(ename)){
					new_type_id=value_1;
				}
				if("new_cat_id".equalsIgnoreCase(ename)){
					new_cat_id=value_1;
				}
				if("new_prod_brand".equalsIgnoreCase(ename)){
					new_prod_brand=value_1;
				}

				String attrvaltype = jsonobj.getString("attrvaltype");
				String value_2 = "";
//				attrvaltype = null == attrcode ? "0" :"".equals(attrcode) ? "0" :"1";
				osserString.append("{");
				
				if(attrcode != null && !"".equalsIgnoreCase(attrcode)){
					String dc_sql = querySqlResult("select DC_SQL from es_dc_sql  a where  a.dc_name = '"+attrcode +"' and rownum < 2");
					String dc_sql_value = querySqlResult("select value_desc from (select 'ECS' source_from, e.* from ("+dc_sql+") e)T where T.VALUE ='"+value_1+"' and rownum < 2");
					value_2 = dc_sql_value;
					if ("是".equals(value_2)) {
						value_2 = "1";
					}else if ("否".equals(value_2)) {
						value_2 = "0";
					}
				}
				
				osserString.append("\"param_code\":\""+ename+"\",");
				osserString.append("\"param_name\":\""+name+"\",");
				osserString.append("\"has_value_code\":\""+attrvaltype+"\",");
				String param_value_code = value_1;
				param_value_code = querySqlResult("select a.param_code from inf_param a where a.param_name = '"+value_1+"'");
				
				if("mobile_price".equalsIgnoreCase(ename)||"deposit_fee".equalsIgnoreCase(ename)||
						"order_return".equalsIgnoreCase(ename)||"mon_return".equalsIgnoreCase(ename)||
						"all_give".equalsIgnoreCase(ename)||"mon_give".equalsIgnoreCase(ename)){
						if(null == value_1||"".equalsIgnoreCase(value_1)){
							value_1 = "0";
						}
						if(null == value_2||"".equalsIgnoreCase(value_2)){
							value_2 = "0";
						}
						value_1 = MallUtils.parseMoneyToLi(Double.parseDouble(value_1));
						value_2 = MallUtils.parseMoneyToLi(Double.parseDouble(value_2));
						if ("mon_return".equalsIgnoreCase(ename)) {
							mon_return =  value_1;
						}
					}
				
				if ("1".equals(attrvaltype)) {
					//上网卡是否全国卡本身逻辑 --- 0：是  1：否
					if("is_group".equalsIgnoreCase(ename)){
						if(!"".equalsIgnoreCase(value_2)&&null != value_2&&"1".equalsIgnoreCase(value_2) ){
							osserString.append("\"param_value_code\":\"1\",");
						}else if(!"".equalsIgnoreCase(value_2)&&null != value_2&&"0".equalsIgnoreCase(value_2)){
							osserString.append("\"param_value_code\":\"0\",");
						}	
					}else{
						osserString.append("\"param_value_code\":\""+value_1+"\",");
					}
					osserString.append("\"param_value\":\""+value_2+"\"");
				}else {
					osserString.append("\"param_value_code\":\""+param_value_code+"\",");
					osserString.append("\"param_value\":\""+value_1+"\"");
				}
				
				osserString.append("}");
				if(s!=aa.size()-1){
					osserString.append(",");
				}
			}
			if (osserString.indexOf("mon_give") == -1 && MallUtils.isNotEmpty(mon_return)) {
				osserString.append(",{");
				osserString.append("\"param_code\":\"mon_give\",");
				osserString.append("\"param_name\":\"月送费金额\",");
				osserString.append("\"has_value_code\":\"0\",");
				osserString.append("\"param_value_code\":\"\",");
				osserString.append("\"param_value\":\""+mon_return+"\"");
				osserString.append("}");
			}
			osserString.append("]");
		} catch (Exception e1) {
//			osserString.append("]");
			logger.info("获取商品[:" + goods_id + "]商品参数失败.");
			logger.info(e1.getMessage(), e1);
		} 
		if ("\"offer_param\":[".equals(osserString.toString())||"".equalsIgnoreCase(osserString.toString())) {
			osserString = new StringBuffer();
			osserString.append("\"offer_param\":[]");
		}
		
		return osserString.toString();
	}
	
	/**
	 * 根据es_order、es_outer_accept、es_order_items生成自动化接口的json信息
	 * @param resp
	 * @param toSysName
	 * @param serial_no
	 * @return
	 * @throws Exception
	 */
	public String orderToStandardJsonValue(OrderTaobaoSyncResp resp ,String toSysName,String serial_no){
    	StringBuffer jsonStr = new StringBuffer();
		List<Order> orders = resp.getOrderSyncList();
		//es_order
		Order order = orders.get(0);
		//es_order_items
		List<OrderItem> itemList = order.getOrderItemList();
		//es_outer_accept
		List<OrderItemAddAccept> acceptList = order.getOrderItemAcceptList();
		
		OrderItemAddAccept accept = null;
		OrderItem item = null;
    	String ship_addr = "";
		//淘宝一订单多商品名称
		String [] arrGoodsName = null;
		//商品名称
	    String prod_offer_name = null; 
		
		try {
			for (int i = 0; i < itemList.size(); i++) {
				accept = acceptList.get(i);
				item = itemList.get(i);
				//有号码则获取号码信息
				if (MallUtils.isNotEmpty(accept.getPhone_num())) {
					accnbrList = this.baseDaoSupport.queryForList("select * from es_gdlt_v_no a WHERE a.dn_no = ? AND ROWNUM < 2", accept.getPhone_num());
				}
				
				//扩展信息只查询一次
				if (i == 0) {
					localOrderAttr = this.baseDaoSupport.queryForMap("select distinct * from es_local_order_attr g where g.order_id = '"+order.getOrder_id()+"'");
			    	//获取预留字段信息
					getReserveInfo(order);					
					//获取扩展信息
			    	getOrderExtInfo(order);	
				}
				
				//淘宝备注信息
				service_remarks = accept.getService_remarks();
		    	//商品编号
				prod_offer_code = item.getGoods_id();
				//商品类型 大类
				prod_offer_type = querySqlResult("select a.type_id from es_goods a WHERE a.goods_id = '"+item.getGoods_id()+"'");				
				//商品小类
				goods_cat_id = accept.getPro_type();
				String prod_offer_code_2 = querySqlResult("select a.CRM_OFFER_ID from es_goods a where a.goods_id = '"+prod_offer_code+"'");
				if (MallUtils.isNotEmpty(prod_offer_code_2)) {
					prod_offer_code = prod_offer_code_2;
				}   	
				order_from = accept.getOrder_from();
		    	String source = accept.getPlat_type();
		    	//总部商城的单
		    	if( checkFieldValueExists("iszbmall", source) ){
		    		source = "10003";
		    		order_from = "10003";
		    		accept.setOrder_channel(source);
		    	}				
				//新商城(沃云购)的费用明细
				//多交预存款  
		    	if(checkFieldValueExists("istcdjyck", order_from)){
		    		if (!checkFieldValueExists("catid_is4g", goods_cat_id)) {
			    		sku_fee = getNewMallSkuFee(order,accept);
					}else {
						offer_price =  MallUtils.parseMoneyToLi(accept.getSell_price());
					}
		    	}else {
		    		offer_price =  MallUtils.parseMoneyToLi(accept.getSell_price());
				}
    	    	
    	    	
		    	//获取上网卡[es_gdlt_v_goods_swk]参数信息
				getSWKInfo(item.getGoods_id());
				//获取商品参数信息
				String offerStr = getOfferParam(item.getGoods_id());

				boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
				if(isTbOrder/*order_from.equals("10001")*/&&prod_offer_type.equals("20001")){
					//淘宝上网卡改号卡
					if(!StringUtils.isEmpty(new_type_id)){
						prod_offer_type=new_type_id;
					}
					if(!StringUtils.isEmpty(new_cat_id)){
						goods_cat_id=new_cat_id;
					}
				}

				
    	    	//非总部商城费用明细
    	    	if(!StringUtils.equals(order_from, "10003")){
    		    	if(checkFieldValueExists("istcgoodnofee", order_from)) {
        				//靓号费用透传的商城
        				good_no_fee = localOrderAttr.get("good_no_fee").toString();
        	    	}else if(accept.getPhone_num()!=null&&queryDepositByAccnbr(accept.getPhone_num())!=null){
        	    		//非总部不透传且有靓号费信息的
        	    		good_no_fee=queryDepositByAccnbr(accept.getPhone_num());
        	    	}
    		    	if(StringUtils.isEmpty(good_no_fee))
    		    	{
    		    		good_no_fee="0";
    		    	}
//        	    	if(checkFieldValueExists("catid_is4g", goods_cat_id)&&!"10003".equals(order_from)){
//	    	    		double goodNoFee=Double.parseDouble(good_no_fee);
//	    				double djyckFee=order.getOrder_amount()-goodNoFee;
//	    				if(djyckFee>0){
//	    					int fee =(int)djyckFee*1000;//转换为厘单位
//	        				sku_fee = set4gDjyck(fee);
//	    				}
//        	    	}
    	    		
    	    		if(StringUtils.equals(prod_offer_type, "20002")){
    	    			//合约机
    	    			sku_fee=setNotzbHyjFee(order,accept,good_no_fee);
    	    		}
    	    		else if(StringUtils.equals(prod_offer_type, "20000")){
    	    			//号卡
    	    			sku_fee=setNotzbHkFee(order,accept,good_no_fee);
    	    		}
    	    		else if(StringUtils.equals(prod_offer_type, "20003")){
    	    			//裸机
    	    			sku_fee=setNotzbLjFee(order,accept,good_no_fee);
    	    		}
    	    		
    	    		if(!good_no_fee.equals("0")){
    	    			good_no_fee=MallUtils.parseMoneyToLi(Double.parseDouble(good_no_fee));
    	    		}
    	    		
    	    	}
    	    	
    	    	//获取货品参数信息
				String skuStr = getSkuListParam(item.getGoods_id() , accept);
				
				//单号卡送总部合约
				if(prod_offer_type.equals("20000")&&isSingalNoCard(item.getGoods_id())){
					is_group_contract = "1";
				}
		    	//支付时间
		    	String order_pay_date = "";
		    	if(MallUtils.isNotEmpty(accept.getPay_time())){
		        	order_pay_date = accept.getPay_time().replaceAll("[-|:| |.]", "");
		    	}				
				//获取订单类别  Z0:联通合约机		Z1:3G号卡		Z2:定制机裸机 等
		    	String tid_category = accept.getTid_category();
				if(MallUtils.isEmpty(tid_category)){
					tid_category = querySqlResult("select a.tid_category from inf_tid_category a WHERE a.cat_id = '"+accept.getPro_type()+"'");
				}
				String order_city = accept.getOrder_city_code();
				Map cityMap = queryMapMallConfig("other_ship_city", order_city);
				//非广东省的地市做处理
				if (null != cityMap && cityMap.size() > 0) {
					order_city = cityMap.get("field_desc").toString();
				}
				//生产中心明确裸机订单归属都是云浮(445300)
				if ("20003".equals(goods_type_id)) {
					order_city = "445300";
				}
		    	if(isTbOrder/*"10001".equals(order_from)*/){
		    		ship_addr = accept.getProvince() + " " + accept.getCity() + " " + accept.getDistrict() + " " + order.getShip_addr();
		    		package_sale = "0";
		    		arrGoodsName = goodsNameExt.split("# #d#");
    		    	prod_offer_name = arrGoodsName[i];
				}else{
					ship_addr = order.getShip_addr();
					prod_offer_name = goodsNameExt;
				}
				
				if (i == 0) {
					//开始拼接json数据
			    	jsonStr.append("{\"standard_order_req\":{\"serial_no\":").append("\"").append(serial_no).append("\",");
			    	jsonStr.append("\"time\":").append("\"").append(simpleFormat.format(new Date())).append("\",");
			    	jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");
			    	jsonStr.append("\"receive_system\":").append("\"").append("10009").append("\",");    
			    	jsonStr.append("\"order_id\":").append("\"").append(accept.getOut_tid()).append("\",");			    	
			    	jsonStr.append("\"source_from_system\":").append("\"").append(getValue(source)).append("\",");
			    	jsonStr.append("\"source_from\":").append("\"").append(order_from).append("\",");
			    	sys_code=findSysCode(accept.getOut_tid());
			    	jsonStr.append("\"sys_code\":").append("\"").append(sys_code).append("\",");
			    	String sql = "";
			    	//总部订单透传,本地source_type默认GDMALL,regist_type默认SELF
			    	//String sql = "select c.field_value from es_mall_config c where c.field_name = 'source_type' and c.field_desc = '"+order_from+"' and rownum < 2";
			    	//String source_type = querySqlResult(sql);
			    	sql = "select c.field_value from es_mall_config c where c.field_name = 'source_type' and c.field_desc = '"+order_from+"' and rownum < 2";
			    	String source_type_tmp = querySqlResult(sql);
			    	if(MallUtils.isNotEmpty(source_type_tmp)){
			    		source_type = source_type_tmp;
			    	}
			    	jsonStr.append("\"source_type\":").append("\"").append(source_type).append("\",");
			    	jsonStr.append("\"regist_type\":").append("\"").append(regist_type).append("\",");
			    	jsonStr.append("\"order_city\":").append("\"").append(order_city).append("\",");
			    	jsonStr.append("\"channel_mark\":").append("\"").append(channel_mark).append("\",");
			    	jsonStr.append("\"spread_channel\":").append("\"").append(spread_channel).append("\",");
			    	jsonStr.append("\"channel_id\":").append("\"").append(channel_id).append("\",");
			    	jsonStr.append("\"chanel_name\":").append("\"").append(chanel_name).append("\",");
			    	jsonStr.append("\"bss_operator\":").append("\"").append(bss_operator).append("\",");
			    	jsonStr.append("\"bss_operator_name\":").append("\"").append(bss_operator_name).append("\",");
			    	jsonStr.append("\"agent_code\":").append("\"").append(agent_code).append("\",");
			    	jsonStr.append("\"agent_name\":").append("\"").append(agent_name).append("\",");
			    	jsonStr.append("\"agent_city\":").append("\"").append(agent_city).append("\",");
			    	jsonStr.append("\"agent_district\":").append("\"").append(agent_district).append("\",");
			    	jsonStr.append("\"channel_type\":").append("\"").append(channel_type).append("\",");
			    	jsonStr.append("\"oss_operator\":").append("\"").append(oss_operator).append("\",");
			    	//订单归属地市
			    	//判断发展人编码是否为空，为空则根据订单来源和订单归属地址从es_development_code(来自现有商城发展人信息表.xlsx)表中关联
			    	String development_code = accept.getDevelopment_code();
			    	if(MallUtils.isEmpty(development_code) || "-1".equals(development_code)){
			    		development_code = querySqlResult("select development_code from es_development_code where area_code = '"+order_city+"' and source_from_Id = '"+order_from+"' and rownum < 2");
			    	}    	
			    	String development_name = accept.getDevelopment_name();
			    	if ( ( MallUtils.isEmpty(development_name) || "-1".equals(development_name) ) 
			    			&& MallUtils.isNotEmpty(development_code)) {
			    		development_name = querySqlResult("select c.field_desc from es_mall_config c where c.field_name = 'development_name' and c.field_value = '"+development_code+"' and rownum < 2");
			        	if (MallUtils.isEmpty(development_name)) {
			        		development_name = development_code;
			        		//发展人名称为空时需要调总部接口查询，此类订单在队列表中挂起
			        		//return "development_name_error";
			    		}
			    	}
			    	jsonStr.append("\"development_code\":").append("\"").append(development_code).append("\",");
			    	jsonStr.append("\"development_name\":").append("\"").append(development_name).append("\",");    	
			    	jsonStr.append("\"reference_phone\":").append("\"").append(reference_phone).append("\",");
			    	jsonStr.append("\"reference_name\":").append("\"").append(getValue(accept.getRecommended_name())).append("\",");    	
			    	jsonStr.append("\"create_time\":").append("\"").append(accept.getTid_time().replaceAll("[-|:| |.]", "")).append("\",");
			    	jsonStr.append("\"pay_time\":").append("\"").append(getValue(order_pay_date)).append("\",");    	
			    	jsonStr.append("\"pay_type\":").append("\"").append(accept.getPaytype()).append("\",");
			    	//支付方式
			    	String payment_type = accept.getPay_mothed();
			    	String paytype = accept.getPaytype();			
					String payment_status = "未支付";
			    	if ("ZFB".equalsIgnoreCase(payment_type)) {
			    		payment_type = "QEZF";
			    		paytype = "ZXZF";
					}  
					jsonStr.append("\"payment_type\":").append("\"").append(payment_type).append("\",");		
					if (checkFieldValueExists("ispayed", paytype)) {
						payment_status = "已支付";
					}
					if ("XCZF".equalsIgnoreCase(paytype)) {
						payment_status = "未定支付方式";
					}
			    	jsonStr.append("\"payment_status\":").append("\"").append(payment_status).append("\",");    	
			    	jsonStr.append("\"payment_serial_no\":").append("\"").append(accept.getPayplatformorderid()).append("\",");
			    	jsonStr.append("\"payment_code\":").append("\"").append(accept.getPayproviderid()).append("\",");    	
			    	jsonStr.append("\"payment_name\":").append("\"").append(accept.getPayprovidername()).append("\",");    	
			    	jsonStr.append("\"payment_channel_code\":").append("\"").append(accept.getPaychannelid()).append("\",");
			    	jsonStr.append("\"payment_channel_name\":").append("\"").append(accept.getPaychannelname()).append("\","); 
			        jsonStr.append("\"order_amount\":").append("\"").append(MallUtils.parseMoneyToLi(order.getOrder_amount())).append("\",");
			    	jsonStr.append("\"order_disacount\":").append("\"").append(MallUtils.parseMoneyToLi(Double.parseDouble(accept.getDiscountvalue()))).append("\",");
			    	jsonStr.append("\"pay_money\":").append("\"").append(MallUtils.parseMoneyToLi(Double.parseDouble(accept.getOrder_realfee()))).append("\",");    	
			    	String o_shipping_amount = order.getShipping_amount().toString();
			    	if (MallUtils.isNotEmpty(o_shipping_amount)) {
			    		o_shipping_amount = MallUtils.parseMoneyToLi(Double.parseDouble(o_shipping_amount));
					}else {
						o_shipping_amount = "0";
					}
			    	jsonStr.append("\"o_shipping_amount\":").append("\"").append(o_shipping_amount).append("\",");
			    	jsonStr.append("\"n_shipping_amount\":").append("\"").append(n_shipping_amount).append("\",");
			    	jsonStr.append("\"order_points\":").append("\"").append(order_points).append("\",");
			    	jsonStr.append("\"points_user\":").append("\"").append(points_user).append("\",");
			    	jsonStr.append("\"shipping_company\":").append("\"").append(shipping_company).append("\",");
			    	//物流公司名
			    	String shipping_company_name = "";
			    	if (MallUtils.isNotEmpty(shipping_company)) {
				    	sql = "select c.field_content_desc from es_mall_config c where c.field_name = 'shipping_company' and c.field_value = '"+shipping_company+"' and rownum < 2";
			    		shipping_company_name = querySqlResult(sql);
					}
			    	jsonStr.append("\"shipping_company_name\":").append("\"").append(shipping_company_name).append("\",");
			    	jsonStr.append("\"shipping_quick\":").append("\"").append(shipping_quick).append("\",");			    	
			    	sql = "select c.reserve0 from es_mall_config c where c.field_name = 'shipping_type' and c.field_value = '"+accept.getSending_type()+"' and rownum < 2";
			    	jsonStr.append("\"shipping_type\":").append("\"").append(querySqlResult(sql)).append("\",");
			    	jsonStr.append("\"shipping_time\":").append("\"").append(shipping_time).append("\",");
			    	jsonStr.append("\"ship_name\":").append("\"").append(getValue(order.getShip_name())).append("\",");    	
			    	jsonStr.append("\"ship_province\":").append("\"").append(getValue(accept.getProvinc_code())).append("\",");    	
			    	jsonStr.append("\"ship_city\":").append("\"").append(accept.getCity_code()).append("\",");
			    	jsonStr.append("\"ship_country\":").append("\"").append(getValue(accept.getArea_code())).append("\",");
			    	jsonStr.append("\"ship_area\":").append("\"").append(ship_area).append("\",");
					jsonStr.append("\"ship_addr\":\""+ship_addr+"\",");
			    	jsonStr.append("\"postcode\":").append("\"").append(getValue(order.getShip_zip())).append("\",");
			    	jsonStr.append("\"ship_tel\":").append("\"").append(getValue(order.getShip_tel())).append("\",");
			    	jsonStr.append("\"ship_phone\":").append("\"").append(getValue(order.getShip_mobile())).append("\",");
			    	jsonStr.append("\"ship_email\":").append("\"").append(ship_email).append("\",");
			    	jsonStr.append("\"cust_phone_no\":").append("\"").append(getValue(order.getShip_tel())).append("\",");
			    	jsonStr.append("\"cust_mobile_no\":").append("\"").append(getValue(order.getShip_mobile())).append("\",");
			    	jsonStr.append("\"cust_name\":").append("\"").append(getValue(accept.getPhone_owner_name())).append("\",");
			    	jsonStr.append("\"cust_addr\":").append("\"").append(order.getShip_addr()).append("\",");
			    	jsonStr.append("\"uid\":").append("\"").append(uid).append("\",");
			    	jsonStr.append("\"uname\":").append("\"").append(getValue(accept.getBuyer_id())).append("\",");    	
			    	jsonStr.append("\"buyer_message\":").append("\"").append(getValue(order.getRemark()).replace(",", " ").replace("\r", "").replace("\n", "")).append("\",");
			    	jsonStr.append("\"seller_message\":").append("\"").append(seller_message).append("\",");    	
			    	jsonStr.append("\"order_comment\":").append("\"").append(getValue(accept.getService_remarks()).replace(",", " ").replace("\r", "").replace("\n", "")).append("\",");
			    	
			    	//是否人工生产
			    	jsonStr.append("\"is_manual_operation\":").append("\"").append(is_manual_type).append("\",");  
			    	
			    	//集客必填字段
			    	jsonStr.append("\"group_code\":").append("\"").append(group_code).append("\",");
			    	jsonStr.append("\"group_name\":").append("\"").append(group_name).append("\",");
			        jsonStr.append("\"industry_type\":").append("\"").append(industry_type).append("\",");
			    	jsonStr.append("\"industry_sub_type\":").append("\"").append(industry_sub_type).append("\",");
			    	
			    	jsonStr.append("\"baidu_account\":").append("\"").append(getValue(accept.getBaidu_id())).append("\",");
			    	jsonStr.append("\"baidu_no\":").append("\"").append(getValue(accept.getFreeze_tran_no())).append("\",");
			    	String baidu_money = accept.getFreeze_free();
			    	if (MallUtils.isNotEmpty(baidu_money)) {
			    		baidu_money = MallUtils.parseMoneyToLi(Double.parseDouble(baidu_money));
					}
			    	jsonStr.append("\"baidu_money\":").append("\"").append(baidu_money).append("\",");
			    	jsonStr.append("\"baidu_begin_time\":").append("\"").append(baidu_begin_time).append("\",");
			    	jsonStr.append("\"baidu_end_time\":").append("\"").append(baidu_end_time).append("\",");
			    	jsonStr.append("\"fund_type\":").append("\"").append(fund_type).append("\",");
			    	
			    	//回收方式
			    	jsonStr.append("\"retrieve_type\":").append("\"").append("").append("\",");
			    	jsonStr.append("\"retrieve_content\":").append("\"").append("").append("\",");
			    	
			    	//发票信息
			    	String invoice_type = order.getInvoice_type().toString();
			    	if ("0".equals(invoice_type)) {
			    		invoice_type = "";
					}
			    	String invoice_print_type = parseInvoicePrintType(accept.getInvoice_print_type());
			    	jsonStr.append("\"invoice_type\":").append("\"").append(invoice_type).append("\",");
			    	jsonStr.append("\"invoice_print_type\":").append("\"").append(getValue(invoice_print_type)).append("\",");
			    	jsonStr.append("\"invoice_title\":").append("\"").append(getValue(invoice_title)).append("\",");
			    	jsonStr.append("\"invoice_content\":").append("\"").append(order.getInvoice_title_desc()).append("\",");
			    	jsonStr.append("\"invoice_group_content\":").append("\"").append(invoice_group_content).append("\",");			    	
			    	jsonStr.append("\"is_upload\":").append("\"").append("").append("\",");
			    	jsonStr.append("\"order_type\":").append("\"").append(order.getOrder_type()).append("\",");			    	
			    	jsonStr.append("\"tid_category\":").append("\"").append(tid_category).append("\",");
			    	jsonStr.append("\"is_to4g\":").append("\"").append(is_to4g).append("\",");
			    	jsonStr.append("\"is_deal\":").append("\"").append(is_deal).append("\",");
			    	jsonStr.append("\"ssyh\":").append("\"").append(ssyh).append("\",");
			    	jsonStr.append("\"mall_order_id\":").append("\"").append(accept.getOrder_id()).append("\",");
			    	//联盟信息
			    	jsonStr.append(league_info);
			    	//获取order_list节点信息
			    	jsonStr.append("\"order_list\":[");
				}
				//good_id的长度超过12位，取后12位
    		    if(prod_offer_code.length() > 12){
    		    	prod_offer_code = prod_offer_code.substring(prod_offer_code.length()-12,prod_offer_code.length());
    		    }
    		    if (MallUtils.isEmpty(prod_offer_name)) {
    		    	prod_offer_name = accept.getPro_name();
				}
    		    jsonStr.append("{\"prod_offer_code\":").append("\"").append(prod_offer_code).append("\",");    	    	
    	    	jsonStr.append("\"prod_offer_name\":").append("\"").append(prod_offer_name).append("\",");
    	    	jsonStr.append("\"prod_offer_type\":").append("\"").append(getValue(prod_offer_type)).append("\",");		    	    	
    	    	String prod_brand = localOrderAttr.get("pro_brand").toString();
    	    	if (MallUtils.isEmpty(prod_brand)) {
        	    	if ("20000".equals(prod_offer_type)) {
        	    		if(accnbrList != null && accnbrList.size() > 0){
        	    			Map accnbrMap = (Map)accnbrList.get(0);
        	    			String no_gen = accnbrMap.get("no_gen").toString();
		    	    		if("2G".equalsIgnoreCase(no_gen)){
		    	    			prod_brand = "2GPH";
		    	    		}else if("3G".equalsIgnoreCase(no_gen)){
		    	    			prod_brand = "3GPH";
		    	    		}else if("4G".equalsIgnoreCase(no_gen)){
		    	    			prod_brand = "4GPH";
		    	    		}
        	    		}
    				}
				}
    	    	if (MallUtils.isEmpty(prod_brand)) {
    	    		prod_brand = querySqlResult("select field_desc from es_mall_config where field_name = 'prod_brand' and field_value = '"+accept.getPro_type()+"'");
				}
    	    	if(isTbOrder/*order_from.equals("10001")*/&&prod_offer_type.equals("20000")&&!StringUtils.isEmpty(new_prod_brand)){
    	    		prod_brand=new_prod_brand;//淘宝上网卡
    	    	}
    	    	jsonStr.append("\"prod_brand\":").append("\"").append(prod_brand).append("\",");	    	    	
    	    	//是否总部合约
    	    	jsonStr.append("\"is_group_contract\":").append("\"").append(is_group_contract).append("\",");	    	    	
    			//商品金额
    			String offer_price_tmp = MallUtils.parseMoneyToLi(accept.getSell_price());
    			//实收金额
    			String offer_coupon_price = MallUtils.parseMoneyToLi(accept.getPro_origfee());
    			//优惠金额
    			Integer offer_disacount_price = Integer.parseInt(offer_price_tmp) - Integer.parseInt(offer_coupon_price);	    			
    	    	jsonStr.append("\"offer_price\":").append("\"").append(offer_price).append("\",");
    	    	jsonStr.append("\"offer_disacount_price\":").append("\"").append(offer_disacount_price).append("\",");
    	    	jsonStr.append("\"offer_coupon_price\":").append("\"").append(offer_coupon_price).append("\",");
    	    	jsonStr.append("\"offer_point\":").append("\"").append(offer_point).append("\",");
    	    	jsonStr.append("\"prod_offer_num\":").append("\"").append(item.getNum()).append("\",");
    	    	if (MallUtils.isEmpty(inventory_code)) {
    	    		inventory_code = "-1";
				}
    	    	jsonStr.append("\"inventory_code\":").append("\"").append(inventory_code).append("\",");
    	    	if (MallUtils.isEmpty(inventory_name)) {
    	    		inventory_name = "-1";
				}
    	    	jsonStr.append("\"inventory_name\":").append("\"").append(inventory_name).append("\",");
    	    	jsonStr.append("\"offer_comment\":").append("\"").append("").append("\",");		    	    	
    	    	//证件类型
    	    	String certi_type = parseCertiType(accept.getCerti_type());
    	    	String certi_address = accept.getCert_address();
    	    	if (MallUtils.isEmpty(certi_address)) {
    	    		certi_address = ship_addr;
				}
    	    	String certi_num = accept.getCerti_number();
    	    	if (null != certi_num && "-1".equals(certi_num)) {
					certi_num = "";
				}
    	    	jsonStr.append("\"certi_type\":").append("\"").append(certi_type).append("\",");
    	    	jsonStr.append("\"certi_num\":").append("\"").append(certi_num).append("\",");
    	    	jsonStr.append("\"certi_address\":").append("\"").append(certi_address).append("\",");
    	    	jsonStr.append("\"cust_name\":").append("\"").append(getValue(accept.getPhone_owner_name())).append("\",");
		        if (MallUtils.isEmpty(customerType)) {
			        if ( checkFieldValueExists("isjtkh", order_from) ) {
			        	customerType = "JTKH";
					}else {
			        	customerType = "GRKH";
					}
				}
    	    	jsonStr.append("\"cust_type\":").append("\"").append(customerType).append("\",");
    	    	//证件有效期
		        String times = "";  
		        try {
					times = simpleFormat.format(dateFormat.parse(accept.getCert_failure_time()));
				} catch (ParseException e) {
					logger.info(e.getMessage(), e);
					times  = "2049123111159";
				}
    	    	jsonStr.append("\"certi_valid_date\":").append("\"").append(times).append("\",");    	    	
    	    	jsonStr.append("\"reduce_flag\":").append("\"").append(accept.getReliefpres_flag()).append("\",");	    	    	   	    	
    	    	//商品参数  offer_param
    	    	jsonStr.append(offerStr).append(",");
    	    	jsonStr.append("\"package_sale\":").append("\"").append(package_sale).append("\",");
    	    	jsonStr.append("\"choose_active\":").append("\"").append(choose_active).append("\",");
		    	jsonStr.append("\"terminal_num\":").append("\"").append(terminal_num).append("\",");
    	    	jsonStr.append("\"is_self\":").append("\"").append(is_self).append("\",");	    	    	
    	    	//可选包   Package(对象)、优惠信息
    	    	if ("10003".equals(order_from)) {
    	    		//总部
        	    	jsonStr.append(zbpackage_list);//可选包
        	    	jsonStr.append(discountInfos);//优惠
				}else if(isTbOrder/*"10001".equals(order_from)*/){
					//淘宝
					String tbPackage = "";
					if(checkFieldValueExists("catid_is4g", accept.getPro_type())){
						tbPackage = getTaoBaoPackage(item.getGoods_id(),item.getOrder_id());
					}
					if(MallUtils.isEmpty(tbPackage)){
						tbPackage = "\"Package\":[],";
					}
        	    	jsonStr.append(tbPackage);//可选包
        	    	jsonStr.append(tb_activity_list);//优惠
				}else{
					//新商城
        	    	jsonStr.append(mallpackage_list);//可选包
        	    	//优惠信息
        	    	jsonStr.append(activity_list); //优惠
				}
    	    	//增值业务   ad_service（对象）
    	    	if(MallUtils.isNotEmpty(ad_service)){
    	    		jsonStr.append("\"ad_service\":").append("").append(ad_service).append(",");
    	    	}else{
    	    		jsonStr.append("\"ad_service\":").append("").append("[]").append(",");
    	    	}
    	    	
    	    	jsonStr.append("\"djyck\":").append("\"").append(djyck).append("\",");
    	    	jsonStr.append("\"online_time\":").append("\"").append(online_time).append("\",");
    	    	//获取受理单信息
    	    	getAccountValue(jsonStr,order,accept,item.getGoods_id());	    	    	
    	    	//货品信息   sku_list
    	    	jsonStr.append(skuStr);	    	    	
    	    	jsonStr.append(",\"propack_code\":").append("\"").append(propack_code).append("\",");
    	    	jsonStr.append("\"propack_desc\":").append("\"").append(propack_desc).append("\"");	    			
    	    	jsonStr.append("}");
    	    	if (i != acceptList.size() - 1) {
					jsonStr.append(",");
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
    	jsonStr.append("]}}");    	
    	//替换字符
    	String rtnStr = jsonStr.toString();
		//把所有"null"替换为""
    	//入库数据中可能存在双引号,转换成json时会有问题,替换错误的双引号
		rtnStr = rtnStr.replaceAll("\"null\"", "\"\"");
		//把所有:""替换为#@d!d@#
		rtnStr = rtnStr.replaceAll(":\"\"", "#@d!d@#");
		//把所有""替换为"
		rtnStr = rtnStr.replaceAll("\"\"", "\"");
		//所#@d!d@#替换回:""
		rtnStr = rtnStr.replaceAll("#@d!d@#", ":\"\"");
		return rtnStr;
	}

	private String findSysCode(String out_tid) {
		// TODO 获取新老订单系统标准
		String code="";
		String sql="select t.sys_code from es_co_queue_bak t, es_order_outer o where t.source_from = o.source_from " +
				"and t.object_id = o.order_id and t.service_code = 'CO_GUIJI' and o.old_sec_order_id = ? and o.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		code=this.baseDaoSupport.queryForString(sql,out_tid);
		return code;
	}

	private String queryDepositByAccnbr(String accnbr) {
		// TODO 获取存在号码的信息
		String depfee=this.baseDaoSupport.queryForString("select deposit from es_gdlt_v_no a WHERE a.dn_no = ? and rownum<2",accnbr);
		return depfee;
	}

	/**
	 * 获取上网卡信息[上网卡时长、卡类型]
	 * @param o
	 */
	private void getSWKInfo(String goods_id){
		try {
			Map swk_m = this.baseDaoSupport.queryForMap("select a.params from es_goods a where a.goods_id = '"+goods_id+"'");
			if(swk_m != null){
				String online_time_s = swk_m.get("params").toString();
				online_time_s = online_time_s.substring(1,online_time_s.lastIndexOf("]"));
				JSONObject a1=JSONObject.fromObject(online_time_s);
				JSONArray aa1= a1.getJSONArray("paramList");
				
				for(int s=0;s<aa1.size();s++){
					JSONObject jsonobj = (JSONObject) aa1.get(s);
					String ename = jsonobj.getString("ename");
					String value = jsonobj.getString("value");
					String attrcode = jsonobj.getString("attrcode");
					if("IS_SET".equalsIgnoreCase(ename)){
						if("1".equalsIgnoreCase(value)){
							sim_type = "成卡";
						}else if("0".equalsIgnoreCase(value)){
							sim_type = "白卡";
						}
					}
					if("CARD_TIME".equalsIgnoreCase(attrcode)){
						if("1".equalsIgnoreCase(value)){
							online_time = "03";
						}else if("2".equalsIgnoreCase(value)){
							online_time = "02";
						}else if("3".equalsIgnoreCase(value)){
							online_time = "01";
						}else if("4".equalsIgnoreCase(value)){
							online_time = "04";
						}else if("5".equalsIgnoreCase(value)){
							online_time = "05";
						}else {
							online_time = "0" + value;
						}
					}
					if("IS_GROUP".equalsIgnoreCase(attrcode)){
						if("1".equalsIgnoreCase(value)){
							ywmcms = "国内上网卡";
						}else if("0".equalsIgnoreCase(value)){
							ywmcms = "本地上网卡";
						}
					}
				}
			 }					
	    } catch (Exception e) {
		  logger.info("获取商品[:" + goods_id + "]的参数信息失败.");
	    }
	}
	
	/**
	 * 获取赠品信息
	 * @param gifts
	 */
	private void getGiftInfo(List<MallGift_List> gifts){
		StringBuffer giftBuffer = new StringBuffer();
		for (int j = 0; j < gifts.size(); j++) {
			boolean isGift = false;
			MallGift_List mallGift_List = gifts.get(j);
			try {
				String sql = "select c.* from es_goods c where sku = ?";
				String type_id = "";
				String cat_id = "";
				Map goodsMap = this.baseDaoSupport.queryForMap(sql, mallGift_List.getGift_id());
				if (null != goodsMap && goodsMap.size() > 0) {
					Map giftMap = new HashMap();
					giftMap.put("type_id", goodsMap.get("type_id").toString());
					giftMap.put("cat_id", goodsMap.get("cat_id").toString());
					giftMap.put("sku_id", mallGift_List.getGift_id());
					giftMap.put("name", goodsMap.get("name"));
					giftMap.put("sku_num", mallGift_List.getGift_num());
					giftMap.put("params", goodsMap.get("params").toString());
					giftMap.put("is_process", mallGift_List.getIs_process());
					giftMap.put("process_type", mallGift_List.getProcess_type());
					giftMap.put("process_desc", "");
					giftMap.put("is_gift", "1");
					giftMap.put("giftvalue", "0");

					giftBuffer.append(getGiftJson(giftMap));
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
		}
		gift_list = giftBuffer.toString();
	}
	
	/**
	 * 获取总部商城的优惠信息
	 * @param t_discountInfos
	 */
	private String getZBActiveList(String t_discountInfos){
		StringBuffer zbBuffer = new StringBuffer();
		try {
			JSONArray jsonArray = JSONArray.fromObject(t_discountInfos);
			zbBuffer.append("\"activity_list\":[");
			String activity_type = "";
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				CenterMallDiscountInfo discountInfo = (CenterMallDiscountInfo)JSONObject.toBean(jsonObject, CenterMallDiscountInfo.class);
				/*if ("1".equals(discountInfo.getDiscountType()) || "2".equals(discountInfo.getDiscountType())) {
					activity_type = "03";
				}else if ("3".equals(discountInfo.getDiscountType())) {
					activity_type = "02";
				}*/
				activity_type = discountInfo.getDiscountType();
				String disacount_num = discountInfo.getDiscountValue();
				if (MallUtils.isNotEmpty(disacount_num)) {
					disacount_num = MallUtils.parseMoneyToLi(Double.parseDouble(disacount_num));
				}
				zbBuffer.append("{");
				zbBuffer.append("\"activity_code\":\""+ discountInfo.getDiscountID() +"\",");
				zbBuffer.append("\"activity_name\":\""+ discountInfo.getDiscountName() +"\",");
				zbBuffer.append("\"activity_desc\":\""+ discountInfo.getDiscountName() +"\",");
				zbBuffer.append("\"activity_type\":\""+ activity_type +"\",");
				zbBuffer.append("\"disacount_range\":\"\",");
				zbBuffer.append("\"disacount_num\":\""+ disacount_num +"\",");
				zbBuffer.append("\"disacount_unit\":\"02\"");
				zbBuffer.append("}");
				if (i != jsonArray.size() - 1) {
					zbBuffer.append(",");
				}
			}
		} catch (Exception e) {
			logger.info("获取总部优惠活动失败." + t_discountInfos);
			logger.info(e.getMessage(), e);
		}
		zbBuffer.append("],");
		return zbBuffer.toString();
	}
	
	/**
	 * 总部赠品信息
	 * @param t_giftInfos
	 * @return
	 */
	private String getZBGiftInfo_Old(String t_giftInfos){
		StringBuffer zbBuffer = new StringBuffer();
		try {
			JSONArray jsonArray = JSONArray.fromObject(t_giftInfos);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				CenterMallGiftInfo giftInfo = (CenterMallGiftInfo)JSONObject.toBean(jsonObject, CenterMallGiftInfo.class);
				String gift_id = giftInfo.getGiftID();
				String gift_type = giftInfo.getGiftType();
				String gift_value = giftInfo.getGiftValue();
				String gift_name = giftInfo.getGiftName();
				String goods_category = "";
				String goods_type = "10007";
				if (MallUtils.isEmpty(gift_value)) {
					gift_value = "0";
				}else {
					gift_value = MallUtils.parseMoneyToLi(Double.parseDouble(gift_value));
				}
				if (MallUtils.isEmpty(gift_id)) {
					gift_id = "2222222222222";
				}
				if (MallUtils.isEmpty(gift_type)) {
					if (null != giftInfo.getGiftDesc() && giftInfo.getGiftDesc().indexOf("卡") != -1) {
						gift_type = "01";
					}else{
						gift_type = "02";
					}
				}
				if ("01".equals(gift_type)) {
					goods_category = "690903000";
				}else {
					goods_category = "690902000";
				}
				//ur单:197952
				//总部订单下发时，对其中的礼品将礼品名称截取掉前缀“总部-”后，找到商品系统中同名的礼品货品sku，传送给订单系统。
				//如果匹配不到货品，则透传总部的sku
				String sku_id = null;
				if (null != gift_name && !"".equals(gift_name)) {
					//根据货品名查找sku
					String sql = "select * from v_product c where name = '总部-"+gift_name+"'";
					Map lpMap = null;
					try {
						lpMap = baseDaoSupport.queryForMap(sql, null);
					} catch (Exception e) {
					}
					if (null != lpMap && lpMap.size() > 0) {
						sku_id = lpMap.get("skua").toString();	//sku
						goods_type = lpMap.get("type_id").toString();	//货品大类
						goods_category = lpMap.get("cat_name").toString();	//货品小类
					}
				}
				//陈奥要求修改gift_id为sku_id
				gift_id = sku_id;
				
				//货品信息
				zbBuffer.append(",{");
				zbBuffer.append("\"sku_id\":").append("\"").append(sku_id).append("\",");
				zbBuffer.append("\"goods_name\":").append("\"").append(giftInfo.getGiftName()).append("\",");
				zbBuffer.append("\"goods_type\":").append("\"").append(goods_type).append("\",");
				
				zbBuffer.append("\"goods_category\":").append("\"").append(goods_category).append("\",");
				zbBuffer.append("\"goods_desc\":").append("\"").append(giftInfo.getGiftName()).append("\",");
				if (checkFieldValueExists("isvirtualgift", goods_category)) {
					zbBuffer.append("\"is_virtual\":").append("\"1\",");
				}else {
					zbBuffer.append("\"is_virtual\":").append("\"0\",");
				}
				zbBuffer.append("\"is_gift\":").append("\"1\",");
				zbBuffer.append("\"sku_num\":").append("\"").append(giftInfo.getGiftNum()).append("\",");
				zbBuffer.append("\"sku_param\":").append("[");
				//赠品类型
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_type\",");
				zbBuffer.append("\"param_name\":\"赠品类型\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+gift_type+"\"");
				zbBuffer.append("},");
				//赠品编码
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_id\",");
				zbBuffer.append("\"param_name\":\"赠品编码\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+gift_id+"\"");
				zbBuffer.append("},");
				//赠品名称
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_name\",");
				zbBuffer.append("\"param_name\":\"赠品名称\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+gift_name+"\"");
				zbBuffer.append("},");
				//赠品面值
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_value\",");
				zbBuffer.append("\"param_name\":\"赠品面值\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+gift_value+"\"");
				zbBuffer.append("},");
				//赠品面值单位
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_unit\",");
				zbBuffer.append("\"param_name\":\"赠品面值单位\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\"01\"");
				zbBuffer.append("},");
				//赠品数量
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_num\",");
				zbBuffer.append("\"param_name\":\"赠品数量\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftNum()+"\"");
				zbBuffer.append("},");
				//赠品描述
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_desc\",");
				zbBuffer.append("\"param_name\":\"赠品描述\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftDesc()+"\"");
				zbBuffer.append("},");
				//赠品品牌
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_brand\",");
				zbBuffer.append("\"param_name\":\"赠品品牌\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftBrand()+"\"");
				zbBuffer.append("},");
				//赠品型号
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_model\",");
				zbBuffer.append("\"param_name\":\"赠品型号\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftModel()+"\"");
				zbBuffer.append("},");
				//赠品颜色
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_color\",");
				zbBuffer.append("\"param_name\":\"赠品颜色\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftColor()+"\"");
				zbBuffer.append("},");
				//赠品机型
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"gift_sku\",");
				zbBuffer.append("\"param_name\":\"赠品机型\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\""+giftInfo.getGiftTypeId()+"\"");
				zbBuffer.append("},");
				//是否需要加工
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"is_process\",");
				zbBuffer.append("\"param_name\":\"是否需要加工\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\"0\"");
				zbBuffer.append("},");
				//加工类型
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"process_type\",");
				zbBuffer.append("\"param_name\":\"加工类型\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\"\"");
				zbBuffer.append("},");
				//加工内容
				zbBuffer.append("{");
				zbBuffer.append("\"param_code\":\"process_desc\",");
				zbBuffer.append("\"param_name\":\"加工内容\",");
				zbBuffer.append("\"has_value_code\":\"0\",");
				zbBuffer.append("\"param_value_code\":\"\",");
				zbBuffer.append("\"param_value\":\"\"");
				zbBuffer.append("}");
				zbBuffer.append("],\"sku_fee\":[]");
				zbBuffer.append("}");
			}
		} catch (Exception e) {
			logger.info("获取总部赠品信息失败." + t_giftInfos);
			logger.info(e.getMessage(), e);
		}
		return zbBuffer.toString();
	}
	
	/**
	 * 总部赠品信息
	 * @param t_giftInfos
	 * @return
	 */
	private String getZBGiftInfo(String t_giftInfos){
		StringBuffer zbBuffer = new StringBuffer();
		try {
			JSONArray jsonArray = JSONArray.fromObject(t_giftInfos);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				CenterMallGiftInfo giftInfo = (CenterMallGiftInfo)JSONObject.toBean(jsonObject, CenterMallGiftInfo.class);
				String gift_name = giftInfo.getGiftName();
				String gift_type = giftInfo.getGiftType();
				String goods_category = "";
				String goods_type = "10007";
				String sku_id = null;
				String params = null;
				if ("01".equals(gift_type)) {
					goods_category = "690903000";
				}else {
					goods_category = "690902000";
				}
				//ur单:197952
				//总部订单下发时，对其中的礼品将礼品名称截取掉前缀“总部-”后，找到商品系统中同名的礼品货品sku，传送给订单系统。
				//如果匹配不到货品，则透传总部的sku
				if (null != gift_name && !"".equals(gift_name)) {
					//根据货品名查找sku
					String sql = "select * from v_product c where name = '总部-"+gift_name+"'";
					Map lpMap = null;
					try {
						lpMap = baseDaoSupport.queryForMap(sql, null);
					} catch (Exception e) {
					}
					if (null != lpMap && lpMap.size() > 0) {
						sku_id = lpMap.get("skua").toString();	//sku
						goods_type = lpMap.get("type_id").toString();	//货品大类
						goods_category = lpMap.get("cat_name").toString();	//货品小类
						params = lpMap.get("params").toString();
					}
				}else {
					sku_id = "";
				}
				Map giftMap = new HashMap();
				giftMap.put("type_id", goods_type);
				giftMap.put("cat_id", goods_category);
				giftMap.put("sku_id", sku_id);
				giftMap.put("name", gift_name);
				giftMap.put("sku_num", giftInfo.getGiftNum());
				giftMap.put("params", params);
				giftMap.put("is_process", "0");
				giftMap.put("process_type", "");
				giftMap.put("process_desc", "");
				giftMap.put("is_gift", "1");				
				giftMap.put("giftbrand", giftInfo.getGiftBrand());
				giftMap.put("giftmodel", giftInfo.getGiftModel());
				giftMap.put("giftcolor", giftInfo.getGiftColor());
				giftMap.put("gifttypeid", giftInfo.getGiftTypeId());
				giftMap.put("giftdesc", giftInfo.getGiftDesc());
				giftMap.put("giftvalue", giftInfo.getGiftValue());
				
				zbBuffer.append(getGiftJson(giftMap));
			}
		} catch (Exception e) {
			logger.info("获取总部赠品信息失败." + t_giftInfos);
			logger.info(e.getMessage(), e);
		}
		return zbBuffer.toString();
	}
	
	/**
	 * 检查值是否存在
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	private boolean checkFieldValueExists(String fieldName , String fieldValue){
		boolean flag = false;
		try {
			String sql = "select * from es_mall_config where field_name = ? and field_value = ?";
			List l = baseDaoSupport.queryForList(sql, fieldName,fieldValue);
			if (null != l && l.size() > 0) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			logger.info("check fieldName[" + fieldName + "],fieldValue["+fieldValue+"] error");
		}
		return flag;
	}
	
	/**
	 * 获取货品信息
	 * @param orderId
	 * @param acc_nbr
	 * @param o
	 * @return
	 * @throws Exception
	 */
	private String getSkuListParam(String goods_id , OrderItemAddAccept accept){
		StringBuffer skustr = new StringBuffer();
		try {
			boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
			String skuSql = "";
			List m1 = null;
	    	try{
				if (checkFieldValueExists("catid_is4g", accept.getPro_type())) {
					skuSql = "select distinct * from es_goods g where g.goods_id in (select a.z_goods_id from es_goods_rel a where a.a_goods_id = '"+goods_id+"' and cat_id in (select c.field_value from es_mall_config c where c.field_name = 'is4gdiytaocan' and c.source_from = '"+ManagerUtils.getSourceFrom()+"'))";
				}else {
					skuSql = "select distinct * from es_goods g where g.goods_id in (select a.z_goods_id from es_goods_rel a where a.a_goods_id = '"+goods_id+"')";
				}
	    		m1 = this.baseDaoSupport.queryForList(skuSql);
	    	}catch(Exception ex){
	    		logger.info(ex.getMessage(), ex);
	    	}
			skustr.append("\"sku_list\":[");
			for(int i=0;i<m1.size();i++){
				try {
					Map map = (Map) m1.get(i);
					String sku = map.get("params").toString();
					//获取SKU
					skustr.append("{");
					String skuid = querySqlResult("select ep.sku from es_product ep where  ep.goods_id = '"+map.get("goods_id").toString()+"'");
					
					//获取机型编码
					String sn = querySqlResult("select ep.sn from es_product ep where  ep.goods_id = '"+map.get("goods_id").toString()+"'");
					//套餐名
					String skuname = map.get("name").toString();
					
					String goodstype = map.get("type_id").toString();
					String type_id =  map.get("type_id").toString();
					String cat_id =  map.get("cat_id").toString();
					skustr.append("\"sku_id\":\""+skuid+"\",");
					skustr.append("\"goods_name\":\""+skuname+"\",");
					skustr.append("\"goods_type\":\""+goodstype+"\",");
					skustr.append("\"goods_category\":\""+cat_id+"\",");
					skustr.append("\"goods_desc\":\""+skuname+"\",");
					
					//礼品有实物和附加产品
					String type_id_tmp = type_id;
					if (checkFieldValueExists("islipin", cat_id)) {
						type_id = "10007";
					}else if (checkFieldValueExists("isfjcp", cat_id)){
						type_id = "10009";
					}
					//10000:手机  10003:上网卡硬件  10006:配件
					//690902000:应用产品
					if( checkFieldValueExists("is_virtual", type_id) ){
						skustr.append("\"is_virtual\":\"1\",");
					}else{
						skustr.append("\"is_virtual\":\"0\",");
					}
					
					//10007:礼品
					if(checkFieldValueExists("islipin", type_id)){
						skustr.append("\"is_gift\":\"1\",");
						skustr.append("\"sku_num\":\"0\",");
					}else{
						skustr.append("\"is_gift\":\"0\",");
						skustr.append("\"sku_num\":\"1\",");
					}
					
					sku = sku.substring(1,sku.lastIndexOf("]"));
					skustr.append("\"sku_param\":[");
					JSONObject a1=JSONObject.fromObject(sku);
					JSONArray aa1= a1.getJSONArray("paramList");
					
					
					//10002:套餐
					if(checkFieldValueExists("istaocan", goodstype)){
						taocan_name = skuname;
						String has_value_code = skuname == null ? "0" :"".equals(skuname) ? "0" :"1";
						String param_value = "";
						skustr.append("{");
						skustr.append("\"param_code\":\"offer_name\",");
						skustr.append("\"param_name\":\"套餐名称\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+skuname+"\",");
						skustr.append("\"param_value\":\""+skuname+"\"");
						skustr.append("},");
						skustr.append("{");
						
						has_value_code = accept.getFirst_payment() == null ? "0" :"".equals(accept.getFirst_payment()) ? "0" :"1";
						//商城未传时取默认值空，勇军要求
						param_value = MallUtils.isEmpty(accept.getFirst_payment()) ? "" : getValue(accept.getFirst_payment());
						skustr.append("\"param_code\":\"offer_eff_type\",");
						skustr.append("\"param_name\":\"生效方式\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+param_value+"\",");
						skustr.append("\"param_value\":\""+param_value+"\"");
						skustr.append("},");
						skustr.append("{");

						
						skustr.append("\"param_code\":\"wo_offer_eff_type\",");
						skustr.append("\"param_name\":\"微信沃包生效方式\",");
						skustr.append("\"has_value_code\":\"0\",");
						skustr.append("\"param_value_code\":\"\",");
						skustr.append("\"param_value\":\"ALLM\"");
						skustr.append("},");
						skustr.append("{");
						skustr.append("\"param_code\":\"offer_change\",");
						skustr.append("\"param_name\":\"套餐是否变更\",");
						skustr.append("\"has_value_code\":\"0\",");
						skustr.append("\"param_value_code\":\"\",");
						skustr.append("\"param_value\":\""+is_change+"\"");
						skustr.append("},");
						//690107000： 4G自由组合(DIY)套餐     690108000 ： 基本套餐
				    	 if(checkFieldValueExists("is4gdiytaocan", cat_id)){
				    		 skustr.append("{");
								skustr.append("\"param_code\":\"4g_type\",");
								skustr.append("\"param_name\":\"4G套餐类型\",");
								skustr.append("\"has_value_code\":\"0\",");
								skustr.append("\"param_value_code\":\"\",");
								skustr.append("\"param_value\":\"DIY\"");
								skustr.append("},");
				    	 }else if(checkFieldValueExists("is3g4gyth", cat_id)){
				    		 skustr.append("{");
								skustr.append("\"param_code\":\"4g_type\",");
								skustr.append("\"param_name\":\"4G套餐类型\",");
								skustr.append("\"has_value_code\":\"0\",");
								skustr.append("\"param_value_code\":\"\",");
								skustr.append("\"param_value\":\"MAIN\"");
								skustr.append("},");
				    	 }
						
						//69010300:IPHONE专用套餐
						if( checkFieldValueExists("is_phone", cat_id) ){
							skustr.append("{");
							skustr.append("\"param_code\":\"is_iphone\",");
							skustr.append("\"param_name\":\"是否iphone套餐\",");
							skustr.append("\"has_value_code\":\"0\",");;
							skustr.append("\"param_value_code\":\"\",");
							skustr.append("\"param_value\":\"1\"");
							skustr.append("}");
						}else{
							skustr.append("{");
							skustr.append("\"param_code\":\"is_iphone\",");
							skustr.append("\"param_name\":\"是否iphone套餐\",");
							skustr.append("\"has_value_code\":\"0\",");
							skustr.append("\"param_value_code\":\"\",");
							skustr.append("\"param_value\":\"0\"");
							skustr.append("}");
						}
						
						if(aa1.size() > 0){
							skustr.append(",");
						}
					}
					
					//10000:手机
					if(checkFieldValueExists("is_mobile", goodstype)){
						//690001000:社会机
						if( checkFieldValueExists("shehuiji", accept.getPro_type()) ){
							skustr.append("{");
							skustr.append("\"param_code\":\"customization\",");
							skustr.append("\"param_name\":\"是否定制机\",");
							skustr.append("\"has_value_code\":\"0\",");
							skustr.append("\"param_value_code\":\"\",");
							skustr.append("\"param_value\":\"0\"");
							skustr.append("}");
						}else{
							skustr.append("{");
							skustr.append("\"param_code\":\"customization\",");
							skustr.append("\"param_name\":\"是否定制机\",");
							skustr.append("\"has_value_code\":\"0\",");
							skustr.append("\"param_value_code\":\"\",");
							skustr.append("\"param_value\":\"1\"");
							skustr.append("}");
						}
						String has_value_code = accept.getSpecification_code() == null ? "0" :"".equals(accept.getSpecification_code()) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"model\",");
						skustr.append("\"param_name\":\"型号\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+accept.getSpecification_code()+"\",");
						skustr.append("\"param_value\":\""+accept.getSpecification_code()+"\"");
						skustr.append("}");
						has_value_code = sn == null ? "0" :"".equals(sn) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"sku\",");
						skustr.append("\"param_name\":\"机型编码\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+sn+"\",");
						skustr.append("\"param_value\":\""+sn+"\"");
						skustr.append("}");

						has_value_code = accept.getBrand_number() == null ? "0" :"".equals(accept.getBrand_name()) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"brand\",");
						skustr.append("\"param_name\":\"品牌\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+accept.getBrand_number()+"\",");
						skustr.append("\"param_value\":\""+accept.getBrand_name()+"\"");
						skustr.append("}");
						
						//获取机型串号
						String imei = querySqlResult("select terminal_no from GD_ES_TERMINAL  where  sn = '"+sn+"'");						
						has_value_code = imei == null ? "0" :"".equals(accept.getBrand_name()) ? "0" :"1";						
						skustr.append(",{");
						skustr.append("\"param_code\":\"imei\",");
						skustr.append("\"param_name\":\"机型串号\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+imei+"\",");
						skustr.append("\"param_value\":\""+imei+"\"");
						skustr.append("}");
						
						
						if(aa1.size() > 0){
							skustr.append(",");
						}	
					}
					
					
					//10001:合约计划
					if(checkFieldValueExists("ishyjh", goodstype)){
						//获取合约编码
						String sql = "select distinct a.p_code from es_goods_package_for_10003 a where a.goods_id =  '"+goods_id+"' and p_code is not null and sn is not null";
						String package_code = querySqlResult(sql);						
						if (MallUtils.isEmpty(package_code)) {
							sql = "select distinct c.p_code from es_goods_package c where c.goods_id = '"+goods_id+"' and c.source_from = '"+ManagerUtils.getSourceFrom()+"' and p_code is not null and sn is not null and not exists (select 1 from ES_GOODS_PACKAGE_FOR_10003 d where d.goods_id = c.goods_id and d.p_code = c.p_code and d.sn = c.sn and d.source_from = 'ECS' and d.source_from = c.source_from)";
							package_code = querySqlResult(sql);
						}
						//判断是否总部合约
						if (MallUtils.isEmpty(package_code)) {
							if ("10003".equals(order_from)) {
					    		is_group_contract = "1";
							}else {
					    		is_group_contract = "0";
					    		package_code = "666666666666";
							}
						}else {
				    		is_group_contract = "1";
						}
						String has_value_code = package_code == null ? "0" :"".equals(package_code) ? "0" :"1";
						skustr.append("{");
						skustr.append("\"param_code\":\"package_code\",");
						skustr.append("\"param_name\":\"合约编码\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+package_code+"\",");
						skustr.append("\"param_value\":\""+package_code+"\"");
						skustr.append("}");
						has_value_code = skuname == null ? "0" :"".equals(skuname) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"package_name\",");
						skustr.append("\"param_name\":\"合约名称\",");
						skustr.append("\"has_value_code\":\"0\",");
						skustr.append("\"param_value_code\":\""+skuname+"\",");
						skustr.append("\"param_value\":\""+skuname+"\"");
						skustr.append("}");
						
						if(aa1.size() > 0){
							skustr.append(",");
						}	
					}
					
					//获取是否总部合约
					if(checkFieldValueExists("iszbhy", goodstype) && "0".equals(is_group_contract)){
						String sql = "select distinct a.p_code from es_goods_package_for_10003 a where a.goods_id =  '"+goods_id+"' and p_code is not null and sn is not null";
						String tm_package_code = querySqlResult(sql);						
						if (MallUtils.isEmpty(tm_package_code)) {
							sql = "select distinct c.p_code from es_goods_package c where c.goods_id = '"+goods_id+"' and c.source_from = '"+ManagerUtils.getSourceFrom()+"' and p_code is not null and sn is not null and not exists (select 1 from ES_GOODS_PACKAGE_FOR_10003 d where d.goods_id = c.goods_id and d.p_code = c.p_code and d.sn = c.sn and d.source_from = '"+ManagerUtils.getSourceFrom()+"' and d.source_from = c.source_from)";
							tm_package_code = querySqlResult(sql);
						}
						//判断是否总部合约
						if (MallUtils.isEmpty(tm_package_code)) {
							is_group_contract = "0";
						}else {
				    		is_group_contract = "1";
						}
					}
					
					
					//10003:上网卡硬件 10006:配件
					if(checkFieldValueExists("isswkyj", goodstype) || checkFieldValueExists("ispj", goodstype)){
						String has_value_code = accept.getBrand_name() == null ? "0" :"".equals(accept.getBrand_name()) ? "0" :"1";
						skustr.append("{");
						skustr.append("\"param_code\":\"brand\",");
						skustr.append("\"param_name\":\"品牌\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+accept.getBrand_number()+"\",");
						skustr.append("\"param_value\":\""+accept.getBrand_name()+"\"");
						skustr.append("}");
						
						has_value_code = accept.getSpecification_code() == null ? "0" :"".equals(accept.getSpecification_code()) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"model\",");
						skustr.append("\"param_name\":\"型号\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+accept.getSpecification_code()+"\",");
						skustr.append("\"param_value\":\""+accept.getSpecification_code()+"\"");
						skustr.append("}");
						
						has_value_code = sn == null ? "0" :"".equals(sn) ? "0" :"1";
						skustr.append(",{");
						skustr.append("\"param_code\":\"sku\",");
						skustr.append("\"param_name\":\"机型\",");
						skustr.append("\"has_value_code\":\""+has_value_code+"\",");
						skustr.append("\"param_value_code\":\""+sn+"\",");
						skustr.append("\"param_value\":\""+sn+"\"");
						skustr.append("}");
						if(aa1.size() > 0){
							skustr.append(",");
						}	
					}
					
//					获取货品的参数信息，如手机的size，颜色等
					for(int s=0;s<aa1.size();s++){
						JSONObject jsonobj = (JSONObject) aa1.get(s);
//						{"attrcode":"DC_GOODS_COLOR","attrdefvalue":"","attrtype":"goodsparam","attrvaltype":"1","ename":"color","name":"颜色","value":"9811072805104169"}
						String ename = jsonobj.getString("ename");
						String name = jsonobj.getString("name");
						String attrcode = jsonobj.getString("attrcode");
						String value_1 = jsonobj.getString("value");
						String attrvaltype = jsonobj.getString("attrvaltype");
						String value_2 = "";
						
//						Map tmpMapdc=null;
//						String tmpMap_yanshe=null;
						skustr.append("{");
						if(attrcode != null && !"".equalsIgnoreCase(attrcode)){
							String dc_sql = querySqlResult("select DC_SQL from es_dc_sql  a where  a.dc_name = '"+attrcode +"' and rownum < 2");
							String dc_sql_value = querySqlResult("select value_desc from (select 'ECS' source_from, e.* from ("+dc_sql+") e)T where T.VALUE ='"+value_1+"' and rownum < 2");
							value_2 = dc_sql_value;
						}
						skustr.append("\"param_code\":\""+ename+"\",");
						skustr.append("\"param_name\":\""+name+"\",");
						skustr.append("\"has_value_code\":\""+attrvaltype+"\",");
						String param_value_code = value_1;
						param_value_code = querySqlResult("select a.param_code from inf_param a where a.param_name = '"+value_1+"'");
						
						
						if ("1".equals(attrvaltype)) {
							skustr.append("\"param_value_code\":\""+value_1+"\",");
							skustr.append("\"param_value\":\""+value_2+"\"");
						}else {
							skustr.append("\"param_value_code\":\""+param_value_code+"\",");
							skustr.append("\"param_value\":\""+value_1+"\"");
						}
						skustr.append("}");
						if(s!=aa1.size()-1){
							skustr.append(",");
						}
					}
					if(i != m1.size() - 1){
						skustr.append("],");
						skustr.append("\"sku_fee\":[]");
						skustr.append("},");
					}else {
						//如果是最后一品，并且没有号码节点的,需要把货品费用添加到最后一个节点上
						if ( ! checkFieldValueExists("ishavemsisdn", prod_offer_type) ) {
							skustr.append("],");
							skustr.append(sku_fee);
							skustr.append("}");
						}else {
							skustr.append("],");
							skustr.append("\"sku_fee\":[]");
							skustr.append("}");
						}
						//总部订单通过活动编码找到转兑包和附加产品(总部订单中不会有转况包和附加产品信息)
						String addProductStr = null;
						if(MallUtils.isNotEmpty(pmt_code) && 
								(order_from.equals("10003") || isTbOrder/*order_from.equals("10001")*/) ){
							addProductStr = getProductInfo(pmt_code,order_from);
							if(MallUtils.isNotEmpty(addProductStr)){
								skustr.append(addProductStr);
							}
						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
					skustr.append("],");
					skustr.append("\"sku_fee\":[]");
					skustr.append("}");
				}
			
			}
			//20000:号卡 20001:上网卡  20002:合约机   必须有号码节点   “附加产品”类的礼品，号码节点必传
			if(checkFieldValueExists("ishavemsisdn", prod_offer_type) 
					|| ("20008".equals(prod_offer_type) && "690901000".equals(goods_cat_id))){
				getAccnbrParam(skustr,accept,accept.getPhone_num());
			}
			
			//礼品、转兑包类、附加产品
			if ("10003".equals(order_from)) {
				skustr.append(giftInfos);	//总部
			}else if(isTbOrder/*"10001".equals(order_from)*/){
				if(MallUtils.isNotEmpty(service_remarks)){
					skustr.append(getTaoBaoGift(service_remarks));	//淘宝
				}
			}else {
				skustr.append(gift_list);	//新商城
			}
			
			skustr.append("]");
		} catch (Exception e) {
			skustr.append("]");
			logger.info("获取货品信息失败.");
			logger.info(e.getMessage(), e);
		}
		return skustr.toString();
	}
	
	private boolean isSingalNoCard(String goods_id) {
		// TODO Auto-generated method stub
		String sql="select count(*) from es_goods_package where goods_id =? ";
		int count=this.baseDaoSupport.queryForInt(sql, goods_id);
		if(count>0){
			return false;
		}
		return true;
	}

	/**
	 * 获取配置数据
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Map queryMapMallConfig(String fieldName , String fieldValue){
		Map map = null;
		String sql = null;
		try {
			sql = "select * from es_mall_config where field_name = ? and field_value = ?";
			map = baseDaoSupport.queryForMap(sql, fieldName,fieldValue);
		} catch (Exception e) {
			map = null;
			logger.info("执行sql["+sql+"]失败.");
			logger.info("check fieldName[" + fieldName + "],fieldValue["+fieldValue+"] error");
		}
		return map;
	}
	
	/**
	 * 根据活动编码(pmt_code)查找商品信息
	 * @param pmtCode
	 * @return
	 */
	public List queryGoodsByPmtCode(String pmtCode){
		List list = null;
		String sql = "select d.goods_id, c.pmt_code, e.pmt_id, e.pmta_id, g.name, " +
				" g.type_id, g.cat_id, g.sku, g.params, g.type from es_pmt_goods d, " +
				" es_promotion_activity c, es_promotion e, es_goods g where " +
				" c.pmt_code = ? and c.id = e.pmta_id and d.pmt_id = e.pmt_id " +
				" and g.goods_id = d.goods_id and g.source_from = ? and g.type = 'product' " +
				" and g.source_from = e.source_from and g.source_from = c.source_from and " +
				" g.source_from = d.source_from";
		try{
			list = baseDaoSupport.queryForList(sql, pmtCode,ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("获取活动编码["+pmtCode+"]的商品信息失败");
			logger.info(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * 根据pmtCode获取商品侧自行添加的货品信息
	 * @param pmtCode
	 * @param order_from
	 * @return
	 */
	public String getProductInfo(String pmtCode , String order_from){
		StringBuffer stringBuffer = new StringBuffer();
		List list_product = queryGoodsByPmtCode(pmtCode);
		Map map = null;
		String type_id = null;
		String cat_id = null;
		if(null != list_product && list_product.size() > 0){
			for (int i = 0; i < list_product.size(); i++) {
				map = (Map)list_product.get(i);
				type_id = map.get("type_id").toString();
				cat_id = map.get("cat_id").toString();
				//对于总部商城订单,商品只添加转兑包货品
				if(!checkFieldValueExists("iszbaddproduct", type_id) 
						&& "10003".equals(order_from)){
					continue;
				}
				boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
				//淘宝订单通过商品关联礼品
				if(!checkFieldValueExists("istbaddproduct", type_id) 
						&& isTbOrder/*"10001".equals(order_from)*/){
					continue;
				}
				stringBuffer.append(",{");
				stringBuffer.append("\"sku_id\":").append("\"").append(map.get("sku")).append("\",");
				stringBuffer.append("\"goods_name\":").append("\"").append(map.get("name")).append("\",");
				stringBuffer.append("\"goods_type\":").append("\"").append(type_id).append("\",");
				stringBuffer.append("\"goods_category\":").append("\"").append(cat_id).append("\",");
				stringBuffer.append("\"goods_desc\":").append("\"").append(map.get("name")).append("\",");
				if (checkFieldValueExists("isvirtualgift", cat_id)) {
					stringBuffer.append("\"is_virtual\":").append("\"1\",");
				}else {
					stringBuffer.append("\"is_virtual\":").append("\"0\",");
				}
				stringBuffer.append("\"is_gift\":").append("\"1\",");
				stringBuffer.append("\"sku_num\":").append("\"").append("1").append("\",");
				stringBuffer.append("\"sku_param\":").append("[");
				
				//获取参数信息
				String params_list = map.get("params").toString();
				paramsL pl = null;
				try {
					params_list = params_list.substring(1,params_list.lastIndexOf("]"));
					pl = JsonUtil.fromJson(params_list, paramsL.class);
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
				}
				if (null != pl) {
					for (int j = 0; j < pl.getParamList().size(); j++) {
						paramsenum ps = pl.getParamList().get(j);
						String ename = ps.getEname();
						String param_value = ps.getValue();
						if("ADJUST_NUM".equalsIgnoreCase(ename)){
							//单位为厘
							try {
								param_value = MallUtils.parseMoneyToLi(Double.parseDouble(param_value));
							} catch (Exception e) {
								logger.info(e.getMessage(), e);
							}
						}
						stringBuffer.append("{");
						stringBuffer.append("\"param_code\":\""+ename+"\",");
						stringBuffer.append("\"param_name\":\""+ps.getName()+"\",");
						stringBuffer.append("\"has_value_code\":\"0\",");
						stringBuffer.append("\"param_value_code\":\"\",");
						stringBuffer.append("\"param_value\":\""+param_value+"\"");
						stringBuffer.append("}");
						if (j != pl.getParamList().size() - 1) {
							stringBuffer.append(",");
						}
					}
				}
				//礼品类的
				boolean isGift = false;
				if ( checkFieldValueExists("islipin", cat_id) ) {
					isGift = true;
					String gift_type = "690903000".equals(cat_id) ? "01" : "690904000".equals(cat_id) ? "03" : "02";
					//赠品类型
					stringBuffer.append(",{");
					stringBuffer.append("\"param_code\":\"gift_type\",");
					stringBuffer.append("\"param_name\":\"赠品类型\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\""+gift_type+"\"");
					stringBuffer.append("},");
					//赠品编码
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"gift_id\",");
					stringBuffer.append("\"param_name\":\"赠品编码\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\""+map.get("sku")+"\"");
					stringBuffer.append("},");
					//赠品名称
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"gift_name\",");
					stringBuffer.append("\"param_name\":\"赠品名称\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\""+map.get("name")+"\"");
					stringBuffer.append("},");
					//赠品数量
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"gift_num\",");
					stringBuffer.append("\"param_name\":\"赠品数量\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\"1\"");
					stringBuffer.append("},");
					//赠品面值单位
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"gift_unit\",");
					stringBuffer.append("\"param_name\":\"赠品面值单位\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\"01\"");
					stringBuffer.append("},");
					//是否需要加工
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"is_process\",");
					stringBuffer.append("\"param_name\":\"是否需要加工\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\"0\"");
					stringBuffer.append("},");
					//加工类型
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"process_type\",");
					stringBuffer.append("\"param_name\":\"加工类型\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\"\"");
					stringBuffer.append("},");
					//加工内容
					stringBuffer.append("{");
					stringBuffer.append("\"param_code\":\"process_desc\",");
					stringBuffer.append("\"param_name\":\"加工内容\",");
					stringBuffer.append("\"has_value_code\":\"0\",");
					stringBuffer.append("\"param_value_code\":\"\",");
					stringBuffer.append("\"param_value\":\"\"}");
				}
				stringBuffer.append("],\"sku_fee\":[]");
				stringBuffer.append("}");
				
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 获取淘宝的礼品信息
	 * @param service_remarks
	 * @return
	 */
	public String getTaoBaoGift(String service_remarks){
		StringBuffer stringBuffer = new StringBuffer();
		String []remarks = service_remarks.replaceAll("^##", "").split(",");
		if (null != remarks && remarks.length > 0) {
			for (String str : remarks) {
				if(null != str){
					String []remarkInfo = str.split("=");
					if(null != remarkInfo && remarkInfo.length == 2){
						if("赠送礼品".equals(remarkInfo[0]) 
								&& MallUtils.isNotEmpty(remarkInfo[1])){
							String []giftInfo = remarkInfo[1].split("\\|");
							for (int i = 0 ; i < giftInfo.length ; i++) {
								String giftName = giftInfo[i];
								String sql = "select * from v_product c where name = '总部-"+giftName+"'";
								Map lpMap = null;
								//按总部名称查
								try {
									lpMap = baseDaoSupport.queryForMap(sql, null);
								} catch (Exception e) {
								}
								//如果总部查不到按淘宝名称查
								if (null == lpMap) {
									sql = "select * from v_product c where name = '淘宝-"+giftName+"'";
									try {
										lpMap = baseDaoSupport.queryForMap(sql, null);
									} catch (Exception e) {
									}
								}
								if (null != lpMap && lpMap.size() > 0) {
									Map giftMap = new HashMap();
									giftMap.put("type_id", lpMap.get("type_id").toString());
									giftMap.put("cat_id", lpMap.get("cat_name").toString());
									giftMap.put("sku_id", lpMap.get("skua").toString());
									giftMap.put("name", giftName);
									giftMap.put("sku_num", "1");
									giftMap.put("params", lpMap.get("params").toString());
									giftMap.put("is_process", "0");
									giftMap.put("process_type", "");
									giftMap.put("process_desc", "");
									giftMap.put("is_gift", "1");
									giftMap.put("giftvalue", "0");
									stringBuffer.append(getGiftJson(giftMap));
								}
							}
						}
					}
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 根据map对象生成送订单系统的赠品信息
	 * @param map
	 * @return
	 */
	public String getGiftJson(Map map){
		StringBuffer stringBuffer = new StringBuffer();
		String type_id = map.get("type_id").toString();
		String cat_id = map.get("cat_id").toString();
		String sku_id = map.get("sku_id").toString();
		String name = map.get("name").toString();
		String sku_num = map.get("sku_num").toString();
		String is_gift = map.get("is_gift").toString();
		String is_process = map.get("is_process").toString();
		String process_type = map.get("process_type").toString();
		String process_desc = map.get("process_desc").toString();
		String giftvalue = map.get("giftvalue").toString();
		String params_list = map.get("params").toString();
		
		String giftbrand = null == map.get("giftbrand") ? "" : map.get("giftbrand").toString();
		String giftmodel = null == map.get("giftmodel") ? "" : map.get("giftmodel").toString();
		String giftcolor = null == map.get("giftcolor") ? "" : map.get("giftcolor").toString();
		String gifttypeid = null == map.get("gifttypeid") ? "" : map.get("gifttypeid").toString();
		String giftdesc = null == map.get("giftdesc") ? "" : map.get("giftdesc").toString();

		
		if (checkFieldValueExists("isyewubao", cat_id)) {
			type_id = "10009";
		}
		
		stringBuffer.append(",{");
		stringBuffer.append("\"sku_id\":").append("\"").append(sku_id).append("\",");
		stringBuffer.append("\"goods_name\":").append("\"").append(name).append("\",");
		stringBuffer.append("\"goods_type\":").append("\"").append(type_id).append("\",");
		stringBuffer.append("\"goods_category\":").append("\"").append(cat_id).append("\",");
		stringBuffer.append("\"goods_desc\":").append("\"").append(name).append("\",");
		if (checkFieldValueExists("isvirtualgift", cat_id)) {
			stringBuffer.append("\"is_virtual\":").append("\"1\",");
		}else {
			stringBuffer.append("\"is_virtual\":").append("\"0\",");
		}
		stringBuffer.append("\"is_gift\":").append("\"").append(is_gift).append("\",");
		stringBuffer.append("\"sku_num\":").append("\"").append(sku_num).append("\",");
		stringBuffer.append("\"sku_param\":").append("[");
		
		boolean isGift = false;
		//礼品类的
		if ( checkFieldValueExists("islipin", cat_id) ) {
			isGift = true;
			String gift_type = "690903000".equals(cat_id) ? "01" : "690904000".equals(cat_id) ? "03" : "02";
			//赠品类型
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_type\",");
			stringBuffer.append("\"param_name\":\"赠品类型\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+gift_type+"\"");
			stringBuffer.append("},");
			//赠品描述
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_desc\",");
			stringBuffer.append("\"param_name\":\"赠品类型\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+giftdesc+"\"");
			stringBuffer.append("},");
			//赠品编码
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_id\",");
			stringBuffer.append("\"param_name\":\"赠品编码\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+sku_id+"\"");
			stringBuffer.append("},");
			//赠品名称
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_name\",");
			stringBuffer.append("\"param_name\":\"赠品名称\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+name+"\"");
			stringBuffer.append("},");			
			//赠品品牌
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_brand\",");
			stringBuffer.append("\"param_name\":\"赠品品牌\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+giftbrand+"\"");
			stringBuffer.append("},");
			//赠品型号
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_model\",");
			stringBuffer.append("\"param_name\":\"赠品型号\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+giftmodel+"\"");
			stringBuffer.append("},");
			//赠品颜色
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_color\",");
			stringBuffer.append("\"param_name\":\"赠品颜色\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+giftcolor+"\"");
			stringBuffer.append("},");
			//赠品机型
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_sku\",");
			stringBuffer.append("\"param_name\":\"赠品机型\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+gifttypeid+"\"");
			stringBuffer.append("},");
			//赠品数量
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_num\",");
			stringBuffer.append("\"param_name\":\"赠品数量\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+sku_num+"\"");
			stringBuffer.append("},");
			//赠品面值单位
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"gift_unit\",");
			stringBuffer.append("\"param_name\":\"赠品面值单位\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\"01\"");
			stringBuffer.append("},");
			//是否需要加工
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"is_process\",");
			stringBuffer.append("\"param_name\":\"是否需要加工\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+is_process+"\"");
			stringBuffer.append("},");
			//加工类型
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"process_type\",");
			stringBuffer.append("\"param_name\":\"加工类型\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+process_type+"\"");
			stringBuffer.append("},");
			//加工内容
			stringBuffer.append("{");
			stringBuffer.append("\"param_code\":\"process_desc\",");
			stringBuffer.append("\"param_name\":\"加工内容\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+process_desc+"\"}");
		}
		//获取参数信息
		paramsL pl = null;
		try {
			params_list = params_list.substring(1,params_list.lastIndexOf("]"));
			pl = JsonUtil.fromJson(params_list, paramsL.class);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if (null != pl) {
			if (isGift) {
				stringBuffer.append(",");
			}
			for (int i = 0; i < pl.getParamList().size(); i++) {
				paramsenum ps = pl.getParamList().get(i);
				String ename = ps.getEname();
				String param_value = ps.getValue();
				//单位转换为厘
				if ("gift_value".equals(ename) || "ADJUST_NUM".equalsIgnoreCase(ename)) {
					try {
						param_value = MallUtils.parseMoneyToLi(Double.parseDouble(param_value));
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
					}
				}
				stringBuffer.append("{");
				stringBuffer.append("\"param_code\":\""+ps.getEname()+"\",");
				stringBuffer.append("\"param_name\":\""+ps.getName()+"\",");
				stringBuffer.append("\"has_value_code\":\"0\",");
				stringBuffer.append("\"param_value_code\":\"\",");
				stringBuffer.append("\"param_value\":\""+param_value+"\"");
				stringBuffer.append("}");
				if (i != pl.getParamList().size() - 1) {
					stringBuffer.append(",");
				}
			}
			
		}
		if (stringBuffer.indexOf("gift_value") == -1 && isGift) {
			//赠品面值
			stringBuffer.append(",{");
			stringBuffer.append("\"param_code\":\"gift_value\",");
			stringBuffer.append("\"param_name\":\"赠品面值\",");
			stringBuffer.append("\"has_value_code\":\"0\",");
			stringBuffer.append("\"param_value_code\":\"\",");
			stringBuffer.append("\"param_value\":\""+giftvalue+"\"");
			stringBuffer.append("}");
		}
		stringBuffer.append("],\"sku_fee\":[]");
		stringBuffer.append("}");
		
		return stringBuffer.toString();
	}
	
	/**
	 * 根据活动编码获取相应的优惠信息(淘宝及总部)
	 * @param pmt_code
	 * @return
	 */
	private String getPmtAactivityList(String pmt_code){
		if(MallUtils.isEmpty(pmt_code)){
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		try{
			//根据activity_code(优惠编码)获取id(优惠ID)
			String activity_id = querySqlResult("select id from es_promotion_activity c where c.pmt_code = '"+ pmt_code +"'");
			//获取活动同步信息
			PromotionMapByIdReq request = new PromotionMapByIdReq();
			request.setActivity_id(activity_id);
			PromotionMapByIdResp resp = activityService.getPromotionMap(request);
			
			Map pmt_map = resp.getPmt_map();
			if (null != pmt_map && pmt_map.size() > 0) {
				stringBuffer.append("{");
				stringBuffer.append("\"activity_code\":\""+ pmt_map.get("pmt_code") +"\",");
				stringBuffer.append("\"activity_name\":\""+ pmt_map.get("pmt_code") +"\",");
				String activity_desc = pmt_map.get("pmt_describe").toString();
				if (MallUtils.isEmpty(activity_desc)) {
					activity_desc = pmt_map.get("pmt_name").toString();
				}
				String disacount_num = pmt_map.get("pmt_solution").toString();
				try {
					disacount_num = MallUtils.parseMoneyToLi(Double.parseDouble(disacount_num));
				} catch (Exception e) {
					disacount_num = "0";
					logger.info("disacount_num["+disacount_num+"]转换为厘失败");
				}
				stringBuffer.append("\"activity_desc\":\""+ activity_desc +"\",");					
				stringBuffer.append("\"activity_type\":\"3\",");
				stringBuffer.append("\"disacount_range\":\"\",");
				stringBuffer.append("\"disacount_num\":\""+disacount_num+"\",");
				stringBuffer.append("\"disacount_unit\":\"02\"");
				stringBuffer.append("}");
			}
		}catch(Exception e){
			logger.info(e.getMessage(), e);
			stringBuffer = new StringBuffer();
		}
		return stringBuffer.toString();
	}

	
	/**
	 * 获取淘宝的可选包信息
	 * @param goods_id
	 * @param order_id
	 * @return
	 */
	public String getTaoBaoPackage(String goods_id , String order_id){
		StringBuffer stringBuffer = new StringBuffer();
		String skuSql = "select distinct * from es_goods g where g.goods_id in (select a.z_goods_id from es_goods_rel a where a.a_goods_id = '"+goods_id+"') and g.cat_id != '690107000'";
		List m1 = this.baseDaoSupport.queryForList(skuSql);
		try{
			stringBuffer.append("\"Package\":[");
			if(null != m1){
				for(int i=0;i<m1.size();i++){
					Map map = (Map) m1.get(i);
					String name = map.get("name").toString();
					String paramsValue = map.get("params").toString();
					paramsL  params = JsonUtil.fromJson(paramsValue.substring(1,paramsValue.lastIndexOf("]")), paramsL.class);
					params.setName(name);
					if (null != params && params.getParamList().size() > 0) {
						String bss_code = "";
						String package_code = "";
						String package_element_code = "";
						String package_name = "";
						for (int j = 0; j < params.getParamList().size(); j++) {
							paramsenum tmp_param = params.getParamList().get(j);
							if ("bss_code".equalsIgnoreCase(tmp_param.getEname()) && "BSS编码".equalsIgnoreCase(tmp_param.getName())) {
								bss_code = tmp_param.getValue();
							}
							if ("package_code".equalsIgnoreCase(tmp_param.getEname()) && "可选包编码".equalsIgnoreCase(tmp_param.getName())) {
								package_code = tmp_param.getValue();
							}
							if ("package_name".equalsIgnoreCase(tmp_param.getEname()) && "可选包名称".equalsIgnoreCase(tmp_param.getName())) {
								package_name = tmp_param.getValue().trim();
							}
							if ("package_element_code".equalsIgnoreCase(tmp_param.getEname()) && "可选元素编码".equalsIgnoreCase(tmp_param.getName())) {
								package_element_code = tmp_param.getValue();
							}
						}
						stringBuffer.append("{");
						stringBuffer.append("\"PackageCode\":\""+ package_code +"\",");
						stringBuffer.append("\"PackageName\":\""+ package_name +"\",");
						stringBuffer.append("\"ProductCode\":\""+ package_code +"\",");
						stringBuffer.append("\"ElementCode\":\""+ package_element_code +"\",");
						stringBuffer.append("\"ElementType\":\"D\",");
						stringBuffer.append("\"ElementName\":\""+ params.getName() +"\",");
						stringBuffer.append("\"OperType\":\"E\",");
						stringBuffer.append("\"ChageType\":\"O\",");
						stringBuffer.append("\"BizType\":\"FWYH\"");
						stringBuffer.append("}");
						if (i != m1.size() - 1) {
							stringBuffer.append(",");
						}
					}
				}
			}
			stringBuffer.append("],");
		}catch(Exception e){
			stringBuffer = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 获取透传商城的靓号费用
	 */
	public void initGoodsNoFee(){
		if(checkFieldValueExists("istcgoodnofee", order_from)) {
			//靓号费用透传的商城
			good_no_fee = localOrderAttr.get("good_no_fee").toString();
			good_no_low = localOrderAttr.get("good_no_low").toString();
			if (MallUtils.isEmpty(good_no_fee)) {
				good_no_fee = "0";
			}else {
				good_no_fee = MallUtils.parseMoneyToLi(Double.parseDouble(good_no_fee));
			}
			if (MallUtils.isEmpty(good_no_low)) {
				good_no_low = "0";
			}else {
				good_no_low = MallUtils.parseMoneyToLi(Double.parseDouble(good_no_low));
			}
		}
	}
	
	/**
	 * 获取新商城的sku_fee
	 * @param o
	 * @param accept
	 * @return
	 */
	public String getNewMallSkuFee(Order o , OrderItemAddAccept accept){
		//商品价格(商城侧的价格)
		Double orderAmount = o.getOrder_amount();
		//靓号预存
		String goodNoFee = localOrderAttr.get("good_no_fee").toString();
		Double goodFee = 0d;
		if(MallUtils.isNotEmpty(goodNoFee)){
			//goodFee = Double.parseDouble(MallUtils.parseMoney(new Integer(goodNoFee)));
			goodFee = Double.parseDouble(goodNoFee);
		}
		//商品系统中价格
		String priceStr = querySqlResult("select c.price from es_goods c where goods_id = '"+o.getGoods_id()+"'");
		Double price = 0d;
		if (MallUtils.isNotEmpty(priceStr)) {
			price = Double.parseDouble(priceStr);
		}
		//沃云购传过来的商品金额大于商品系统的商品金额，则使用商品系统的商品价格作为商品价格，多出来的部分当作多交预存款处理。
		//生成多交预存款费用项的信息：收费项编码为“4001”、收费项描述为“多交预存款费用”、实收金额为交预存款的金额、应收金额也为为交预存款的金额、减免金额为0。
		//商品节点的多交预存款也需要填入该信息。
		if(orderAmount > price + goodFee){
			offer_price = MallUtils.parseMoneyToLi(price);
		}else {
			offer_price = MallUtils.parseMoneyToLi(accept.getSell_price());
		}
		djyck = MallUtils.parseMoneyToLi(orderAmount - price - goodFee);
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
		if(!"0".equals(djyck)){
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"4001\",");
			sku_feeStr.append("\"fee_item_name\":\"多交预存款费用\",");
			sku_feeStr.append("\"o_fee_num\":\""+ djyck +"\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ djyck +"\"");
			sku_feeStr.append("}");
		}
		sku_feeStr.append("]");
		return sku_feeStr.toString();
	}
	
	private String set4gDjyck(int fee) {
		// TODO 4G自由组合多交预存款
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
		if(!"0".equals(fee)){
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"4001\",");
			sku_feeStr.append("\"fee_item_name\":\"多交预存款费用\",");
			sku_feeStr.append("\"o_fee_num\":\""+ fee +"\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ fee +"\"");
			sku_feeStr.append("}");
		}
		sku_feeStr.append("]");
		return sku_feeStr.toString();
	}
	
	private String setNotzbHyjFee(Order order, OrderItemAddAccept accept,String good_no_fee) {
		// TODO 设置非总部商城的合约机费用
		String zdfy  ="";
		String lhyck ="";
		String hyfy  ="";
		if(mobile_price==null||mobile_price.equals("")||mobile_price.equals("0")){
			mobile_price="0";
			zdfy="0";
		}else{
			zdfy=MallUtils.parseMoneyToLi(Double.parseDouble(mobile_price));
		}
		
		if(good_no_fee.equals("0")){
			lhyck="0";
		}else{
			lhyck=MallUtils.parseMoneyToLi(Double.parseDouble(good_no_fee));
		}
		
		if(deposit_fee==null||deposit_fee.equals("")||deposit_fee.equals("0")){
			deposit_fee="0";
			hyfy="0";
		}else{
			hyfy=MallUtils.parseMoneyToLi(Double.parseDouble(deposit_fee));
		}
		
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
			//USIM费用
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"1001\",");
			sku_feeStr.append("\"fee_item_name\":\"USIM卡费用\",");
			sku_feeStr.append("\"o_fee_num\":\"0\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\"0\"");
			sku_feeStr.append("},");
			//终端费用
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"1002\",");
			sku_feeStr.append("\"fee_item_name\":\"终端费用\",");
			sku_feeStr.append("\"o_fee_num\":\""+ zdfy +"\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ zdfy +"\"");
			sku_feeStr.append("},");
			//靓号预存款
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"2001\",");
			sku_feeStr.append("\"fee_item_name\":\"靓号预存款\",");
			sku_feeStr.append("\"o_fee_num\":\""+ lhyck +"\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ lhyck +"\"");
			sku_feeStr.append("},");
			//合约费用
			sku_feeStr.append("{");
			sku_feeStr.append("\"fee_item_code\":\"2002\",");
			sku_feeStr.append("\"fee_item_name\":\"合约费用\",");
			sku_feeStr.append("\"o_fee_num\":\""+ hyfy +"\",");
			sku_feeStr.append("\"disacount_fee\":\"0\",");
			sku_feeStr.append("\"disacount_reason\":\"\",");
			sku_feeStr.append("\"n_fee_num\":\""+ hyfy +"\"");
			sku_feeStr.append("}");
			//多交预存款,合约机是溢价
			if(!djyck.equals("0")){
				sku_feeStr.append(",{");
				sku_feeStr.append("\"fee_item_code\":\"4001\",");
				sku_feeStr.append("\"fee_item_name\":\"多交预存款费用\",");
				sku_feeStr.append("\"o_fee_num\":\""+ djyck +"\",");
				sku_feeStr.append("\"disacount_fee\":\"0\",");
				sku_feeStr.append("\"disacount_reason\":\"\",");
				sku_feeStr.append("\"n_fee_num\":\""+ djyck +"\"");
				sku_feeStr.append("}");
			}
		sku_feeStr.append("]");	
		return sku_feeStr.toString();
	}
	
	private String setNotzbHkFee(Order order, OrderItemAddAccept accept,String good_no_fee) {
		// TODO 设置非总部商城的号卡费用
		String zdfy  ="";
		String lhyck ="";
		String hyfy  ="";
		if(mobile_price==null||mobile_price.equals("")||mobile_price.equals("0")){
			mobile_price="0";
			zdfy="0";
		}else{
			zdfy=MallUtils.parseMoneyToLi(Double.parseDouble(mobile_price));
		}
		
		if(good_no_fee.equals("0")){
			lhyck="0";
		}else{
			lhyck=MallUtils.parseMoneyToLi(Double.parseDouble(good_no_fee));
		}
		
		if(deposit_fee==null||deposit_fee.equals("")||deposit_fee.equals("0")){
			deposit_fee="0";
			hyfy="0";
		}else{
			hyfy=MallUtils.parseMoneyToLi(Double.parseDouble(deposit_fee));
		}
		
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
		//USIM卡费用
		sku_feeStr.append("{");
		sku_feeStr.append("\"fee_item_code\":\"1001\",");
		sku_feeStr.append("\"fee_item_name\":\"USIM卡费用\",");
		sku_feeStr.append("\"o_fee_num\":\"0\",");
		sku_feeStr.append("\"disacount_fee\":\"0\",");
		sku_feeStr.append("\"disacount_reason\":\"\",");
		sku_feeStr.append("\"n_fee_num\":\"0\"");
		sku_feeStr.append("},");
		//靓号预存款
		sku_feeStr.append("{");
		sku_feeStr.append("\"fee_item_code\":\"2001\",");
		sku_feeStr.append("\"fee_item_name\":\"靓号预存款\",");
		sku_feeStr.append("\"o_fee_num\":\""+ lhyck +"\",");
		sku_feeStr.append("\"disacount_fee\":\"0\",");
		sku_feeStr.append("\"disacount_reason\":\"\",");
		sku_feeStr.append("\"n_fee_num\":\""+ lhyck +"\"");
		sku_feeStr.append("},");
		//合约费用
		sku_feeStr.append("{");
		sku_feeStr.append("\"fee_item_code\":\"2002\",");
		sku_feeStr.append("\"fee_item_name\":\"合约费用\",");
		sku_feeStr.append("\"o_fee_num\":\""+ hyfy +"\",");
		sku_feeStr.append("\"disacount_fee\":\"0\",");
		sku_feeStr.append("\"disacount_reason\":\"\",");
		sku_feeStr.append("\"n_fee_num\":\""+ hyfy +"\"");
		sku_feeStr.append("}");
		//号卡费用
		if(checkFieldValueExists("catid_is4g", goods_cat_id)){
			double goodNoFee=Double.parseDouble(good_no_fee);
			double djyckFee=order.getOrder_amount()-goodNoFee;
			djyck=MallUtils.parseMoneyToLi(djyckFee);
			if(!djyck.equals("0")){
				sku_feeStr.append(",{");
				sku_feeStr.append("\"fee_item_code\":\"4001\",");
				sku_feeStr.append("\"fee_item_name\":\"多交预存款费用\",");
				sku_feeStr.append("\"o_fee_num\":\""+ djyck +"\",");
				sku_feeStr.append("\"disacount_fee\":\"0\",");
				sku_feeStr.append("\"disacount_reason\":\"\",");
				sku_feeStr.append("\"n_fee_num\":\""+ djyck +"\"");
				sku_feeStr.append("}");
			}
		}else{
			if(!djyck.equals("0")){
				sku_feeStr.append(",{");
				sku_feeStr.append("\"fee_item_code\":\"4001\",");
				sku_feeStr.append("\"fee_item_name\":\"多交预存款费用\",");
				sku_feeStr.append("\"o_fee_num\":\""+ djyck +"\",");
				sku_feeStr.append("\"disacount_fee\":\"0\",");
				sku_feeStr.append("\"disacount_reason\":\"\",");
				sku_feeStr.append("\"n_fee_num\":\""+ djyck +"\"");
				sku_feeStr.append("}");
			}
		}
		sku_feeStr.append("]");	
		return sku_feeStr.toString();
	}
	
	private String setNotzbLjFee(Order order, OrderItemAddAccept accept,String good_no_fee) {
		// TODO 设置非总部裸机费用
		String zdfy  ="";
		if(offer_price==null||offer_price.equals("")||offer_price.equals("0")){
			offer_price="0";
			zdfy="0";
		}else{
			zdfy=offer_price;
		}
		StringBuffer sku_feeStr = new StringBuffer();
		sku_feeStr.append("\"sku_fee\":[");
		//终端费用
		sku_feeStr.append("{");
		sku_feeStr.append("\"fee_item_code\":\"1002\",");
		sku_feeStr.append("\"fee_item_name\":\"终端费用\",");
		sku_feeStr.append("\"o_fee_num\":\""+ zdfy +"\",");
		sku_feeStr.append("\"disacount_fee\":\"0\",");
		sku_feeStr.append("\"disacount_reason\":\"\",");
		sku_feeStr.append("\"n_fee_num\":\""+ zdfy +"\"");
		sku_feeStr.append("}");
		sku_feeStr.append("]");	
		return sku_feeStr.toString();
	}
}
