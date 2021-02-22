package com.ztesoft.net.sqls;


/**
 * 订单数据归档sql
 * @author jun
 *
 */
public class Sqls_Archives extends Sqls {

	public Sqls_Archives(){
		
	}
	
	//插入语句
	public String getINS_ORDER_EXT_HIS(){//update columnNames 12.29
		String columnNames="ORDER_CITY_CODE,PAY_TYPE,FLOW_STATUS,SIMPLE_ORDER_ID,LOCK_USER_NAME,FLOW_TRACE_ID,SOURCE_FROM,IS_ACCOUNT,IS_SHIP," +
				"ORDER_ID,REFUND_TRACE_INST_ID,EXCEPTION_TYPE,REFUND_DEAL_TYPE,REFUND_STATUS,REFUND_TYPE,SEND_ZB,ESS_CANCEL_STATUS,BSS_CANCEL_STATUS," +
				"IS_SEND_WMS,WMS_STATUS,FLOW_TRACE_INST_ID,FLOW_TRACE_NAME,FLOW_INST_ID,FLOW_ID,LOCK_STATUS,LOCK_USER_ID,LOCK_TIME,PENDING_REASON," +
				"ORDER_MODEL,COL7,COL6,COL5,COL4,COL3,COL2,SHIPPING_QUICK,ABNORMAL_STATUS,ORDER_CHANNEL,ORDER_FROM,TID_CATEGORY,IS_ADV_SALE," +
				"TID_TIME,WM_ISRESERVATION_FROM,OUT_TID,PLAT_TYPE,CHANNEL_MARK,ZB_INF_ID,IS_SHOW,SPREAD_CHANNEL,MATERIAL_RETRIEVE,ERROR_COMMENTS," +
				"WRITE_CARD_RESULT,WRITE_CARD_STATUS,ORDER_ORIGIN,OLD_FLOW_ID,AGENT_CODE,VISIBLE_STATUS,ZB_STATUS,QUICK_SHIP_COMPANY_CODE," +
				"QUICK_SHIP_STATUS,ABNORMAL_TYPE,O_SHIP_STATUS,PACK_TYPE,MODEL_NAME,MODEL_CODE,PROD_CAT_ID,PAY_MOTHED,LAST_DEAL_TIME,EXCEPTION_DESC," +
				"refund_time,bss_reset_time,ess_reset_time";
		return " insert into es_order_ext_his  ("+columnNames +" ) select "+columnNames +"  from es_order_ext a where a.order_id=? ";
	}
	public String getINS_ORDER_ITEMS_EXT_HIS(){//update columnNames 12.23
		String columnNames="INVOICE_GROUP_CONTENT,INVENTORY_NAME,COL7,COL6,COL5,COL4,COL3,COL2,ITEM_ID,ORDER_ID,ESS_ORDER_ID,INVOICE_NO,PRINT_USER,PRINT_TIME," +
				"PRINT_FLAG,BSS_PRE_DESC,ESS_PRE_DESC,ESS_PRE_STATUS,BSS_PRE_STATUS,BSS_STATUS,ACCOUNT_STATUS,ACCOUNT_VALI,ACCOUNT_TIME,COLLECTION,IS_BILL," +
				"DISCOUNTID,PHONE_DEPOSIT,SUPERIORS_BANKCODE,IS_CHANGE,PHONE_NUM,GOODS_NUM,SOURCE_FROM,RESERVE8,INVOICE_PRINT_TYPE,GOODS_CAT_ID,BRAND_NUMBER," +
				"IS_CUSTOMIZED,GOODS_TYPE,BSS_ACCOUNT_TIME,ACTIVE_NO";
		return "insert into es_order_items_ext_his   ("+columnNames +" ) select "+columnNames +"  from es_order_items_ext a where a.order_id=? ";
	}
	public String getINS_ORDER_ITEMS_PROD_HIS(){
		String columnNames=" ORDER_ID,ITEM_ID,ITEM_PROD_INST_ID,ITEM_SPEC_GOODS_ID,PROD_SPEC_GOODS_ID,CREATE_TIME,"
				+"OPER_ID,PROD_SPEC_TYPE_CODE,STATUS,SOURCE_FROM,NAME ";
		return "insert into es_order_items_prod_his     ("+columnNames +" ) select "+columnNames +"  from es_order_items_prod a where a.order_id=?   ";
	}
	public String getINS_ORDER_ITEMS_PROD_EXT_HIS(){//update columnNames 12.23
		String columnNames="ICCID,READID,IS_IPHONE_PLAN,PHONE_NUM,UPDATE_TIME,CREATE_TIME,SOURCE_FROM,COL7,COL6,COL5,COL4,COL3,COL2,ITEM_PROD_INST_ID," +
				"ITEM_ID,ORDER_ID,CERTI_TYPE,CERT_FAILURE_TIME,CERT_ADDRESS,CERT_CARD_NATION,CERT_CARD_BIRTH,CERT_CARD_SEX,CERT_CARD_NUM,CERT_CARD_NAME," +
				"VICE_ACC_NBR,WHITE_CART_TYPE,FIRST_PAYMENT,TERMINAL_NUM,IS_OLD,OLD_REMD_USER,IS_LOVES_PHONE,SIM_CARD_SKU";
		
		return " insert into es_order_items_prod_ext_his    ("+columnNames +" ) select "+columnNames +"  from es_order_items_prod_ext a where a.order_id=? ";
	}
	
	public String getINS_ES_ORDER_EXTVTL_HIS(){//update 1.09
		String columnNames=" CHANNEL_TYPE,AGENT_CODE_DLS,AGENT_NAME,AGENT_CITY,AGENT_DISTRICT,ITEM_ID,ITEM_PROD_INST_ID," +
				"PAYMENT_ID,MEMBER_ID,ACCEPTANCEFORM,BSS_OPERATOR_NAME,INVOICE_TYPE,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8," +
				"COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17,COL18,COL19,COL20,SOCIETY_NAME,BILL_MAIL_REC,ZBPACKAGES," +
				"LOVES_PHONE_NUM,READID,PLAT_DISTRIBUTOR_CODE,BANK_USER,PAYPROVIDERID,RESERVE0,GUARANTOR_CERTI_TYPE,FILE_RETURN_TYPE," +
				"PROD_AUDIT_STATUS,SHIP_AREA,COUPONBATCHID,CHOOSE_ACTIVE,ADJUSTMENT_CREDIT,SIMID,IS_WRITE_CARD,PAYCHANNELID,SR_STATUS," +
				"HIGHER_LEAGUE,SIM_TYPE,SELL_PRICE,ICCID,PAYCHANNELNAME,FAMLIY_NUM,ORDER_PROVINC_CODE,ISGIFTS,DISCOUNTINFOS,GIFTINFOS,ACTIVITY_LIST," +
				"OSS_OPERATOR,FUND_TYPE,ORDERACCTYPE,CUSTOMERTYPE,RESERVE1,RECOMMENDED_CODE,BSS_REFUND_STATUS,NET_TYPE,RESERVE6,GROUP_NAME,RELIEFPRES_FLAG," +
				"CARD_TYPE,COUPONNAME,SPECIFICATIO_NNAME,ACTIVE_SORT_NAME,PROPACDESC,REGIST_TYPE,ORDER_DISFEE,SHIPPING_COMPANY_NAME,STATIONNO,GUARANTOR," +
				"DEVELOPMENT_CODE,SYN_ORD_ZB,BUYER_MESSAGE,ORG_ID,DISFEE_SELECT,CUST_SERVICE_NAME,COLLECTION_FREE,PACKAGE_SALE,RECYLE_DESC,PAYFINTIME,DISCOUNTVALUE," +
				"OUT_PLAN_ID_BSS,PASSWORD,SPECIFICATION_CODE,FEEINFO,RECOMMENDED_NAME,IS_GROUP_CONTRACT,FACE_VALUE,LEAGUE_NAME,BUYER_ID,BANK_CODE,NET_REGION,IS_LIANG," +
				"WCFPACKAGES,TOTEID,BAIDU_ID,SALES_MANAGER,OUT_PACKAGE_ID,ORDER_ORIGFEE,DEVELOPMENT_NAME,SYN_ORD_WMS,ORDER_JOIN_CATEGORY,PHONE_OWNER_NAME,VICECARD_NO," +
				"RESERVE5,IS_SEND_GOODS,BILL_MAIL_ADDR,TB_STATUS,VOUCHERS_MONEY,RECOMMENDED_PHONE,WMS_REFUND_STATUS,FREEZE_FREE,SF_STATUS,BANK_ACCOUNT,CREDIT_CLASS,SOURCE_TYPE," +
				"ALIPAY_ID,ESS_STATUS,RESERVE7,ORDER_DEAL_TYPE,OUT_PLAN_ID_ESS,IS_EASY_ACCOUNT,SOCIETY_PRICE,ATIVE_TYPE,ND_STATUS,PAYPROVIDERNAME,CARD_SPECIES,BILL_MAIL_CONTENT," +
				"BUY_MODE_NAME,NEW_SHOP_STATUS,IS_DIY_PLAN,PLAN_TITLE,SCRIPT_SEQ,ORDER_ID,CREATE_TIME,SOURCE_FROM,ACTIVE_SORT,BILL_MAIL_TYPE,IS_UPLOAD,FREEZE_TRAN_NO,BILL_MAIL_POST_CODE," +
				"BUY_MODE,LIANG_CODE,IS_PHYSICS,ORDERACCCODE,BSS_ORDER_TYPE,BSS_OPERATOR,PRO_BRAND,BUYER_NAME,BUSI_CREDENCE_CODE,GUARANTOR_CERTI_NO,BIRTHDAY,SHIP_BATCH,RESERVE2,VOUCHERS_CODE," +
				"BU_PRICE,LIANG_PRICE,SEX,PRO_REALFEE,GUARANTOR_INFO,DISFEE_TYPE,GROUP_CODE,PLATFORM_STATUS_NAME,SELLER_MESSAGE,SPECIAL_BUSI_TYPE,FIRST_FEE_TYPE_NAME,PLATFORM_STATUS,GOODSNAME," +
				"BRAND_NAME,DISCOUNTRANGE,SERVICE_REMARKS,MERGE_STATUS,BILL_TYPE,DISCOUNTNAME,PAYPLATFORMORDERID,RESERVE4,ZB_REFUND_STATUS," +
				"SUPPLY_ID,sys_code ";
		
		return " insert into es_order_extvtl_his  ("+columnNames +" ) select  "+columnNames +"  from es_order_extvtl v where v.order_id=?   ";
	}
	public String getINS_ORDER_LOG_HIS(){
		String columnNames=" LOG_ID,ORDER_ID,OP_ID,OP_NAME,OP_TIME,MESSAGE,ACTION,SOURCE_FROM ";
		
		return " insert into es_order_log_his     ("+columnNames +" ) select "+columnNames +"  from es_order_log a where a.order_id=?   ";
	}
	
	public String getINS_ORDER_OUTER_HIS(){
		return " insert into es_order_outer_his select * from es_order_outer where order_id  = ? ";
	}
	public String getINS_ORDER_OUTER_ATTR_INST_HIS(){
		return " insert into es_outer_attr_inst_his select * from es_outer_attr_inst where order_id  = ? ";
	}
	
	public String getINS_ORDER_EXCEPTION_COLLECT_HIS(){
		String columnNames=" EXCEPTION_ID,ORDER_ID,CREATE_DATE,COMMENTS,SOURCE_FROM,FLOW_ID,FLOW_TRACE_ID ";
		
		return " insert into es_order_exception_collect_his   ("+columnNames +" ) select "+columnNames +"  from es_order_exception_collect a  where a.order_id=? ";
	}
	public String getINS_ORDER_REL_HIS(){
		String columnNames=" A_ORDER_ID,Z_ORDER_ID,REL_TYPE,CREATE_DATE,Z_TABLE_NAME,STATE,COMMENTS,STATE_DATE,SOURCE_FROM,N_ORDER_ID ";
		return " insert into es_order_rel_his     ("+columnNames +" ) select "+columnNames +"  from es_order_rel a where a.a_order_id=? and a.z_order_id = ?  ";
	}
	public String getINS_ORDER_COMMENTS_HIS (){
		String columnNames=" ORDER_ID,COMMENTS,FLAG,OPET_NAME,OPER_ID,OPER_TIME,SEQU,SOURCE_FROM ";
		
		return " insert into es_order_comments_his    ("+columnNames +" ) select "+columnNames +"  from es_order_comments a where a.order_id=? ";
	}
	public String getINS_ORDER_TREE_HIS(){
		String columnNames=" ORDER_ID,ES_ORDER,ES_ORDER_EXT,ES_ORDER_ITEMS,ES_ATTR_INST,ES_DELIVERY,ES_PAYMENT_LOGS,CREATE_TIME, " +
				" UPDATE_TIME,SOURCE_FROM,ES_ORDER_ITEMS_EXT,COMMENTS,ES_ATTR_PACKAGE,ES_ATTR_FEE_INFO,ES_ATTR_GIFT_INFO,ES_ATTR_DISCOUNT_INFO ";
		return " insert into  es_order_trees_his ("+columnNames +" ) select "+columnNames +"  from es_order_trees a where a.order_id=? ";
	}
	public String getINS_ORDER_HIS(){
		String columnNames=
		"ORDER_ID,SN,MEMBER_ID,STATUS,PAY_STATUS,SHIP_STATUS,SHIPPING_ID,SHIPPING_TYPE,SHIPPING_AREA, "
		+" PAYMENT_ID,PAYMENT_NAME,PAYMENT_TYPE,PAYMONEY,CREATE_TIME,SHIP_NAME,SHIP_ADDR,SHIP_ZIP, "
		+" SHIP_EMAIL,SHIP_MOBILE,SHIP_TEL,SHIP_DAY,SHIP_TIME,IS_PROTECT,PROTECT_PRICE,GOODS_AMOUNT, "
		+" SHIPPING_AMOUNT,ORDER_AMOUNT,WEIGHT,GOODS_NUM,GAINEDPOINT,CONSUMEPOINT,DISABLED,DISCOUNT, "
		+" IMPORTED,PIMPORTED,COMPLETE_TIME,BANK_ID,GOODS,REMARK,USERID,SOURCE_FROM,ACCEPT_TIME, "
		+" TRANSACTION_ID,ORDER_TYPE,COL1,COL2,COL3,COL4,COL5,TYPE_CODE,ACCEPT_STATUS,GOODS_ID, "
		+" PAY_TIME,LAN_ID,DEAL_MESSAGE,BILL_FLAG,ACC_NBR,BATCH_ID,INVOICE_TYPE,INVOICE_TITLE, "
		+" INVOICE_TITLE_DESC,INVOICE_CONTENT,LAST_UPDATE,CREATE_TYPE,SOURCE_SHOP_NAME, "
		+" ORDER_ADSCRIPTION_ID,PAY_TYPE,CONFIRM_STATUS,CONFIRM_TIME,CONFIRM_GROUP_ID, "
		+" CONFIRM_USER_ID,SHIP_ASSIGN_TIME,SHIP_GROUP_ID,SHIP_USER_ID,ACCEPT_ASSIGN_TIME, "
		+" ACCEPT_GROUP_ID,ACCEPT_USER_ID,PAY_ASSIGN_TIME,PAY_GROUP_ID,PAY_USER_ID,TAXES, "
		+" ORDER_DISCOUNT,ORDER_COUPON,ORDER_RECORD_STATUS,QUERY_GROUP_ID,QUERY_USER_ID, "
		+" DLY_ADDRESS_ID,SEVICE_TYPE,SPREAD_ID,COL6,COL7,COL8,COL9,COL10,SERVICE_TYPE, "
		+" SERVICE_ID,SERVICE_CODE,APP_KEY ";
		
		return " insert into es_order_his ("+columnNames +" ) select "+columnNames +"  from es_order a where a.order_id=? ";
	}
	public String getINS_ORDER_ITEMS_HIS(){
		String columnNames=" ITEM_ID ,ORDER_ID,GOODS_ID,SPEC_ID,NUM,SHIP_NUM,NAME,PRICE,GAINEDPOINT,STATE,ADDON,LV_ID,COUPON_PRICE,"
				+" UNIT,SOURCE_FROM,ITEM_TYPE,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,PRODUCT_ID ";
		return " insert into es_order_items_his   ( "
				+columnNames
				+" )  select "
				+columnNames
				+" from es_order_items a where a.order_id=? ";
	}
	public String getINS_ATTR_INST_HIS(){
		String columnNames="ATTR_INST_ID,ORDER_ID,ORDER_ITEM_ID,INST_ID,ATTR_SPEC_ID,FIELD_ATTR_ID,FIELD_NAME,FILED_DESC,"
				+" FIELD_VALUE,FIELD_VALUE_DESC,MODIFY_FIELD_VALUE,SEQU,SEC_FIELD_VALUE,SEC_FILED_VALUE_DESC,COL1,COL2,COL3,CREATE_DATE,SOURCE_FROM ";
		
		return " insert into es_attr_inst_his  ("+columnNames +" ) select "+columnNames +"  from es_attr_inst a where a.order_id=? ";
	}
	public String getINS_DELIVERY_HIS(){//update columnNames 12.25
		String columnNames=" COL5,COL4,COL3,COL2,COL1,HOUSE_ID,SOURCE_FROM,BATCH_ID,SHIP_STATUS,WEIGHT,PRINT_STATUS,SHIP_NUM,USERID," +
				"REMARK,REASON,CREATE_TIME,OP_NAME,SHIP_EMAIL,SHIP_MOBILE,SHIP_TEL,SHIP_ZIP,SHIP_ADDR,PROVINCE,CITY,REGION,REGION_ID," +
				"CITY_ID,PROVINCE_ID,SHIP_NAME,LOGI_NO,LOGI_NAME,LOGI_ID,PROTECT_PRICE,IS_PROTECT,SHIP_TYPE,MONEY,MEMBER_ID,ORDER_ID," +
				"TYPE,DELIVERY_ID,COL10,COL9,COL8,COL7,COL6,DELIVERY_TYPE,USER_RECIEVE_TIME,LOGI_RECEIVER_PHONE,LOGI_RECEIVER,POST_FEE," +
				"OUT_HOUSE_ID,SHIPPING_TIME,N_SHIPPING_AMOUNT,SHIPPING_COMPANY";
		return " insert into es_delivery_his  ("+columnNames +" ) select "+columnNames +"   from es_delivery a where a.order_id=? ";
	}
	
	public String getINS_DELIVERY_ITEM_HIS(){//update columnNames 12.25
		String columnNames=" COL10,COL9,COL8,COL7,COL6,COL5,COL4,COL3,COL2,COL1,SOURCE_FROM," +
				"ITEMTYPE,NUM,NAME,SN,PRODUCT_ID,GOODS_ID,DELIVERY_ID,ITEM_ID,ORDER_ID";
		
		return " insert into es_delivery_item_his  ("+columnNames +" ) select "+columnNames +" from es_delivery_item a "+
				" where a.order_id=?  ";
	}
	public String getINS_PAYMENT_LOGS_HIS(){
		String columnNames="PAYMENT_ID,ORDER_ID,MEMBER_ID,ACCOUNT,BANK_ID,PAY_USER,MONEY,PAY_COST,PAY_TYPE,PAY_METHOD,OP_ID," +
				" TYPE,STATUS,CREATE_TIME,REMARK,USERID,TRANSACTION_ID,STATUS_TIME,PAYTYPE,PAY_TIME,RETURNED_ACCOUNT," +
				" RETURNED_TYPE,RETURNED_KIND,SOURCE_FROM,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10 ";
		
		return " insert into es_payment_logs_his   ("+columnNames +" ) select "+columnNames +" from es_payment_logs a where a.order_id=? ";
	}
	
	public String getINS_RULE_EXE_LOG_HIS(){
		return "insert into es_rule_exe_log_his(log_id, rule_id, create_time, exe_result, result_msg, source_from, plan_id, "+
			" obj_id, children_error, children_info, engine_path, flow_inst_id, fact_class) select t.log_id, t.rule_id, t.create_time, t.exe_result, t.result_msg, t.source_from, "+
			" t.plan_id, t.obj_id, t.children_error, t.children_info, t.engine_path, t.flow_inst_id, t.fact_class from es_rule_exe_log t where t.obj_id=?";
	}
	
	public String getINS_ES_ORDER_ITEMS_APT_PRINTS_HIS(){
		
		return " insert into es_order_items_apt_prints_his select * from es_order_items_apt_prints a where a.order_id=? ";
	}
    public String getINS_ES_ORDER_ITEMS_INV_PRINTS_HIS(){
		
		return " insert into es_order_items_inv_prints_his select * from es_order_items_inv_prints a where a.order_id=? ";
	}	
    public String getINS_ES_ATTR_DISCOUNT_INFO_HIS(){
		
		return " insert into es_attr_discount_info_his select * from es_attr_discount_info a where a.order_id=? ";
	}
    public String getINS_ES_ATTR_PACKAGE_HIS(){
		return " insert into es_attr_package_his select * from es_attr_package a where a.order_id=? ";
	}
    
    public String getINS_ES_ATTR_GIFT_PARAM_HIS(){
		
  		return " insert into es_attr_gift_param_his select * from es_attr_gift_param a where a.order_id=? ";
  	}
    public String getINS_ES_ATTR_GIFT_INFO_HIS(){
  		return " insert into es_attr_gift_info_his select * from es_attr_gift_info a where a.order_id=? ";
  	}	
    
    public String getINS_ES_ATTR_FEE_INFO_HIS(){
  		return " insert into es_attr_fee_info_his select * from es_attr_fee_info a where a.order_id=? ";
  	}	
    public String getINS_ES_ORDER_ITEMS_AGENT_MONEY_HIS(){//add 1.09
    	String columnNames="COL2,COL3,COL4,COL5,COL6,COL7,ITEM_ID,ORDER_ID,GOODS_ID,O_ORDER_ID,COL8,COL9,COL10,COL11,C_ORDER_ID";
    	return " insert into es_order_items_agent_money_his   ("+columnNames +" ) select "+columnNames +" from es_order_items_agent_money a where a.order_id=? ";
  	}	
    
    
    //删除语句
	public String getDELETE_ES_ORDER_ITEMS_APT_PRINTS(){
		return "delete from es_order_items_apt_prints t where t.order_id=?";
	}
	
	public String getDELETE_ES_ORDER_ITEMS_INV_PRINTS(){
		return "delete from es_order_items_inv_prints t where t.order_id=?";
	}
	
	public String getDEL_ES_ATTR_DISCOUNT_INFO(){
		return " delete from  es_attr_discount_info a  where a.order_id=? ";
	}
	
	public String getDEL_ES_ATTR_PACKAGE(){
		return " delete from es_attr_package a  where a.order_id=? ";
	}
	public String getDEL_ES_ATTR_GIFT_PARAM(){
		return " delete from es_attr_gift_param a  where a.order_id=? ";
	}
	
	public String getDEL_ES_ATTR_GIFT_INFO(){
		return " delete from es_attr_gift_info a  where a.order_id=? ";
	}
	
	public String getDEL_ES_ATTR_FEE_INFO(){
		return " delete from es_attr_fee_info a  where a.order_id=? ";
	}
		
	public String getDELETE_RULE_EXE_LOG(){
		return "delete from es_rule_exe_log t where t.obj_id=?";
	}
	
	//删除语句
	
	public String getDEL_ORDER_TREE(){
		return " delete from es_order_trees a  where a.order_id=? ";
	}
	
	public String getDEL_ORDER_OUTER(){
		return " delete from  es_order_outer a  where a.order_id=? ";
	}
	
	public String getDEL_ORDER_OUTER_ATTR_INST(){
		return " delete from es_outer_attr_inst a  where a.order_id=? ";
	}
	
	public String getDEL_ORDER_OUTER_REL(){
		return " delete from es_order_rel a  where a.a_order_id=?  and z_order_id = ? ";
	}
	
	public String getDEL_ORDER_EXT(){
		return " delete from es_order_ext a  where a.order_id=? ";
	}
	public String getDEL_ORDER(){
		return " delete from es_order a where a.order_id=? ";
	}
	public String getDEL_ORDER_ITEMS(){
		return " delete from es_order_items a where a.order_id=? ";
	}
	public String getDEL_ORDER_ITEMS_EXT(){
		return " delete from  es_order_items_ext a where a.order_id=? ";
	}
	public String getDEL_ORDER_ITEMS_PROD(){
		return " delete from es_order_items_prod a where a.order_id=? ";
	}
	public String getDEL_ORDER_ITEMS_PROD_EXT(){
		return " delete from es_order_items_prod_ext a where a.order_id=? ";
	}
	public String getDEL_ATTR_INST(){
		return " delete from es_attr_inst a where a.order_id=? ";
	}
	public String getDEL_DELIVERY(){
		return " delete from es_delivery a where a.order_id=? ";
	}
	public String getDEL_DELIVERY_ITEM(){
		return " delete from es_delivery_item a where a.order_id=?  ";
	}
	public String getDEL_PAYMENT_LOGS(){
		return " delete from es_payment_logs a where a.order_id=? ";
	}
	public String getDEL_ORDER_LOG(){
		return " delete from es_order_log a where a.order_id=? ";
	}
	public String getDEL_ORDER_EXCEPTION_COLLECT(){
		return " delete from es_order_exception_collect a where a.order_id=? ";
	}
	
	public String getDEL_ES_ORDER_EXTVTL_HIS(){
		return " delete from es_order_extvtl a where a.order_id=? ";
	}
	
	public String getDEL_ORDER_REL(){
		return " delete from es_order_rel a where a.a_order_id=? ";
	}
	public String getDEL_ORDER_COMMENTS(){
		return " delete from es_order_comments a where a.order_id=? ";
	}
	public String getDEL_ORDER_ITEMS_AGENT_MONEY(){
		return " delete from es_order_items_agent_money a where a.order_id=? ";
	}

   //更新状态
	public String getUP_ORDER_HIS(){
		return " update  es_order_his t set t.status=? where t.order_id=? ";
	}
}
