package com.ztesoft.net.attr.hander;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;


public class AcceptanceFormHandler extends BaseSupport implements IAttrHandler{

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
			return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) 
	{
		
		
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		//String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		//受理单
		StringBuffer jsonStr=new StringBuffer();
		AttrInstLoadResp attrInstLoadResp=new AttrInstLoadResp();
    	
        /*mainContentOne        
        1、  套餐名称：《套餐名称》，详见业务协议
        2、  《按合约数据文档中的产品描述。》*/
    	String sms_send_num = "";
		String answering_free = "";
		String call_times = "";
		String flow = "";
		String visual_phone = "";
		String other_business ="";
		String out_flow = "";
    	String pay_type = "";
    	//基本通信服务及附加业务名称及描述
    	String ywmcms = "";
    	if(StringUtils.isEmpty(goods_id))
    		return attrInstLoadResp;
    	try {
			List<Map> list =  this.baseDaoSupport.queryForList("select a.params from v_product_params a where a.a_goods_id = ? and rownum < 2", goods_id);
			if(list != null && list.size() > 0){
				String strpar = list.get(0).get("params").toString();
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
			
		}
    	 catch (Exception e) 
    	{
			logger.info(e.getMessage(), e);
		}
    	
    	String mainContentOne_1 = "";
    	String mainContentOne_2 = "";
    	String taocan_name=(String) orderAttrValues.get("goods_name");
    	if(taocan_name !=null&&!"".equalsIgnoreCase(taocan_name))
    	{
    		mainContentOne_1 ="1、  套餐名称："+ taocan_name+"，详见业务协议";
    	}
    	
    	else 
    	{
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
    	jsonStr.append("\"contactAddr\":").append("\"").append(getValue(orderAttrValues.get(""))).append("\",");
    	/*mainContentTwo
    	 * 基本通信服务及附加业务名称及描述：《国内通话（这个指的是号卡），国内上网卡，本地上网卡，国际上网卡，合约机，裸终端》。<br/>
    	 * 可选业务包名称及描述：《可选活动名称》<br/>
    	 * 号码信息：您选择的号码18502510173，具体规则详见业务协议 。<br/>
    	 * 活动信息：《俊伟同步的BSS数据中的活动的名称》。<br/>*/
//    	String is_group = "";
    	
    	String type_id  = orderAttrValues.get(SpecConsts.TYPE_ID).toString();
    	
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
		append("可选业务包名称及描述：").append("可选活动名").append("\\r").append("号码信息：您选择的号码").append(getValue(orderAttrValues.get(AttrConsts.PHONE_NUM))).
		append("，具体规则详见业务协议").append("\",");
//		append("，具体规则详见业务协议").append("<br/>").append("活动信息：").append("").append("\",");
		jsonStr.append("");
		jsonStr.append("\" \":").append("\"").append("").append("\",");
    	jsonStr.append("\"agentPaperType\":").append("\"").append("").append("\",");//代理人证件类型，来源不清楚  空
    	jsonStr.append("\"acctAddr\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.SHIP_ADDR))).append("\",");//cust_addr
    	if(getValue(orderAttrValues.get(AttrConsts.IS_OLD)).equals(EcsOrderConsts.IS_OLD_0))
    	{
    		jsonStr.append("\"userType\":").append("\"").append(EcsOrderConsts.USERTYPE_NEW).append("\",");
    	}
    	
    	else
    	{
    		jsonStr.append("\"userType\":").append("\"").append(EcsOrderConsts.USERTYPE_OLD).append("\",");
    	}
    	
    	jsonStr.append("\"paperExpr\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.CERT_FAILURE_TIME))).append("\",");//证件有效期   传空值
    	jsonStr.append("\"custName\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.PHONE_OWNER_NAME))).append("\",");//用户姓名   cust_name
    	jsonStr.append("\"acctName\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.PHONE_OWNER_NAME))).append("\",");//用户姓名   cust_name
    	jsonStr.append("\"agentPhone\":").append("\"").append("").append("\",");//代理人电话号码，来源不清楚  空
    	jsonStr.append("\"custType\":").append("\"").append(orderAttrValues.get(AttrConsts.CUSTOMER_TYPE)).append("\",");//客户类型  字典值有（个人或集团）  要商城传，接口要改
       
    	//付费方式  payment_type  跟字典值做对应关系，字典值不清楚
    	String payment_type = orderAttrValues.get(AttrConsts.PAY_METHOD).toString();
    	String payment_name = querySqlResult("select c.field_desc from es_mall_config c where c.field_name = 'payment_type' and c.field_value = '"+payment_type+"'");
		if (StringUtils.isEmpty(payment_name)) {
			payment_name = payment_type;
		}
		jsonStr.append("\"payMethod\":").append("\"").append(payment_name).append("\",");
		jsonStr.append("\"emailAddr\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.SHIP_EMAIL))).append("\",");
		jsonStr.append("\"contactMan\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.SHIP_NAME))).append("\",");
		jsonStr.append("\"contactAddr\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.SHIP_ADDR))).append("\",");
		jsonStr.append("\"paperAddr\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.CERT_ADDRESS))).append("\",");    //证件地址  certi_addr	    	
    	jsonStr.append("\"paperType\":").append("\"").append(CommonDataFactory.getInstance().getDcPublicDataByPkey(StypeConsts.CERTI_TYPE, getValue(orderAttrValues.get(AttrConsts.CERTI_TYPE)))).append("\",");  //证件类型  certi_type
    	jsonStr.append("\"staffInfo\":").append("\"").append("").append("\","); //受理人及工号  传空值                                
    	jsonStr.append("\"contactPhone\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.REVEIVER_MOBILE))).append("\",");//客户联系电话  ship_phone
    	jsonStr.append("\"bankAcctName\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.BANK_ACCOUNT))).append("\",");//银行帐号      bank_cust_code
    	jsonStr.append("\"bankName\":").append("\"").append("").append("\",");//银行名称      bank_name
    	jsonStr.append("\"bankAcct\":").append("\"").append("").append("\",");//该字段没有用到，来源不清楚   空
    	jsonStr.append("\"paperNo\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.CERT_CARD_NUM))).append("\",");//证件号码  certi_num
    	jsonStr.append("\"bankCode\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.BANK_CODE))).append("\",");//银行编码  bank_code
    	jsonStr.append("\"agentPaperNo\":").append("\"").append("").append("\",");//代理人证件号，来源不清楚  空
    	jsonStr.append("\"agentName\":").append("\"").append("").append("\",");//代理人姓名，来源不清楚  空	
    	jsonStr.append("\"userNo\":").append("\"").append(getValue(orderAttrValues.get(AttrConsts.PHONE_NUM))).append("\",");
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
    	
    	String receipt_code = "";
    	if("20000".equalsIgnoreCase(type_id)){//号卡
    		if("预付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    		}else if("后付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("postsub_t").append("\",");
    		}else{
        		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
        	}
    		receipt_code="presub_t";
    	}else if("20002".equalsIgnoreCase(type_id)){//合约机
    		if("预付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    		}else if ("后付费".equalsIgnoreCase(pay_type)){
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("postsub_t").append("\",");
    		}else{
        		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
        	}
    		receipt_code="presub_t";
    	}else if ("20001".equalsIgnoreCase(type_id)&&"后付费".equalsIgnoreCase(pay_type)){//上网卡
    		if("后付费".equalsIgnoreCase(pay_type)){
    			if("农行商城".equalsIgnoreCase(order_from)){ //是否集团用户  农行商城过来的单子为集团用户，其他为非集团客户
					jsonStr.append("\"AcceptanceTp\":").append("\"").append("wlpost_g_t").append("\",");
				}else{
					jsonStr.append("\"AcceptanceTp\":").append("\"").append("wlpost_p_t").append("\",");
				}
    			receipt_code="wlpost_p_t";
    		}else {
    			jsonStr.append("\"AcceptanceTp\":").append("\"").append("precard_t").append("\",");
    			receipt_code="precard_t";
			}
    	}else{
    		jsonStr.append("\"AcceptanceTp\":").append("\"").append("presub_t").append("\",");
    		receipt_code="presub_t";
    	}
    	//保留
    	jsonStr.append("\"Para\":");
    	jsonStr.append("{\"ParaID\":").append("\"").append("").append("\",");
    	jsonStr.append("\"ParaValue\":").append("\"").append("").append("\"");
    	
    	jsonStr.append("}");
    	jsonStr.append("}");
    	
    	attrInstLoadResp.setField_value(jsonStr.toString());
    	
    	 OrderItemsAptPrintsBusiRequest orderItemAptPrt = new OrderItemsAptPrintsBusiRequest();
    	 OrderItemBusiRequest  orderItemBusiRequest =new  OrderItemBusiRequest();
    	 List<OrderItemBusiRequest> 	orderItemBusiRequests= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
    	 for(OrderItemBusiRequest orderItemBusiRequestsInfo:orderItemBusiRequests)
    	 {

       	    	 orderItemAptPrt.setOrder_id(order_id);
           	     orderItemAptPrt.setAcceptance_id(this.baseDaoSupport.getSequences("SEQ_ES_ORDER_ITEMS_APT_PRINTS")); //手动设置主键值
           	     orderItemAptPrt.setItem_id(orderItemBusiRequestsInfo.getItem_id());
       	    	 orderItemAptPrt.setStatus(EcsOrderConsts.IS_PRINT_0);
       	    	 List<String> errors = parseStr(jsonStr.toString());
           	     for(int i = 0; i < errors.size(); i++){
           	    	 if(i == 0)
           	    		 orderItemAptPrt.setAcceptance_html(errors.get(i));
           	    	 else if(i == 1)
           	    		 orderItemAptPrt.setAcceptance_html_2(errors.get(i));
           	    	 else if(i == 2)
           	    		 orderItemAptPrt.setAcceptance_html_3(errors.get(i));
           	     }
          	     orderItemAptPrt.setReceipt_mode("0");
          	     orderItemAptPrt.setReceipt_code(receipt_code);
          	     orderItemAptPrt.setCreate_time(Consts.SYSDATE);
          	     orderItemAptPrt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
           	     orderItemAptPrt.setDb_action(ConstsCore.DB_ACTION_INSERT);
           	     orderItemAptPrt.store();

        	 
        	 
    	 }
    	 
    	 attrInstLoadResp.setField_value("");
    	 
    	return attrInstLoadResp;
		
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
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
	
	@SuppressWarnings("rawtypes")
	private List parseStr(String errorStr) {
		List<String> errorsList = new ArrayList<String>();
		parseCicleStr(errorsList, errorStr);
		return errorsList;
	}

	private void parseCicleStr(List<String> errorsList, String errorStr) {
		int splitPoint = 3500;
		while (!StringUtil.isEmpty(errorStr) && (errorStr.length() >splitPoint)) {
			String error_info = errorStr.substring(0, splitPoint).toString();
			errorsList.add(error_info);
			errorStr = errorStr.substring(splitPoint);
			parseCicleStr(errorsList, errorStr);
		}
		errorsList.add(errorStr);
	}

}
