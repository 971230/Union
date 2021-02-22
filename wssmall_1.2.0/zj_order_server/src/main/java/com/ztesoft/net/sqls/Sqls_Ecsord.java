package com.ztesoft.net.sqls;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


public class Sqls_Ecsord extends Sqls {

	public Sqls_Ecsord(){
		
	}
	
	//
	public String getORDER_CO_QUEUE_BAK_LIST(){
		return "select a.co_id,a.service_code,a.object_type,a.created_date,a.failure_desc,a.batch_id,a.status,a.status_date,b.old_sec_order_id ext_order_id,d.order_id,d.create_time "+
				" from es_co_queue a left join es_order_outer b on a.object_id=b.order_id and a.source_from=b.source_from "+
				" left join es_order_rel c on b.order_id=c.a_order_id and b.source_from=c.source_from "+
				" left join es_order d on c.z_order_id=d.order_id and c.source_from=d.source_from "+
				" where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	public String getORDER_CO_QUEUE_BAK_LIST1(){
		return "select a.co_id,a.service_code,a.object_type,a.created_date,a.failure_desc,a.batch_id,a.status,a.status_date,b.old_sec_order_id ext_order_id,d.order_id,d.create_time "+
				" from es_co_queue_bak a left join es_order_outer b on a.object_id=b.order_id and a.source_from=b.source_from "+
				" left join es_order_rel c on b.order_id=c.a_order_id and b.source_from=c.source_from "+
				" left join es_order d on c.z_order_id=d.order_id and c.source_from=d.source_from "+
				" where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getORDER_CO_QUEUE_LIST(){
		return "select c.co_id,c.service_code,c.object_type,c.status,c.status_date,a.old_sec_order_id ext_order_id from es_order_outer a,es_co_queue c "+
				"where a.order_id=c.object_id and a.source_from=c.source_from and c.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getORDER_SYN_EXE_TMPL_LIST(){
		return "select * from es_outer_execute_tmpl where status=0 and run_status<>'00E' and source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	
	public String getOUTER_EXECUTE_TMPL_STOP(){
		return "update es_outer_execute_tmpl set run_status=? where status=0 ";
	}
	
	public String getOUTER_EXECUTE_TMPL_START(){
		return "update es_outer_execute_tmpl set run_status=?,data_end_time=sysdate+5/1440 where status=0 ";
	}
	
	public String getCO_QUEUE_MOVE_LOG(){
		return "select log_id,to_char(move_time,'yyyy/mm/dd hh24:mi:ss') move_time from es_co_queue_move_log where source_from='"+ManagerUtils.getSourceFrom()+"' and log_id=? ";
	}
	
	public String getCO_QUEUE_GET(){
		return "select * from #table_name where source_from='"+ManagerUtils.getSourceFrom()+"' and co_id=? ";
	}
	
	public String getDELIVERY_PRINTS_SELECT_BY_ID(){
		return "select a.*  from es_delivery_prints a  where  a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.delvery_print_id =? ";
	}
	public String getINSURE_VALUE_BY_ORDERID(){
		return "select a.insur_value,a.is_insur  from es_delivery_prints a  where  a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.order_id =? ";
	}
	public String getUPDATE_ES_DELIVERY_ITEM_BY_DEID(){
		return "update es_delivery_item a  set a.col1=? where a.delivery_id=? and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	public String getUPDATE_ES_DELIVERY_ITEM_BY_ID(){
		return "update es_delivery_item a  set a.col1=? where a.item_id=? and a.source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	public String getINSERT_INFORMATION_ES_CO_QUEUE(){
		return "insert into es_co_queue    select * from es_co_queue_bak a where a.co_id =?";
	}
	public String getSELECT_INFORMATION_ES_CO_QUEUE(){
		return "select * from es_co_queue where source_from='"+ManagerUtils.getSourceFrom()+"' and co_id=? ";
	}
	
	public String getSELECT_ES_ORDER_WARNING(){
		return "select a.* from es_order_warning a where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}

	public String getPREMIUM_GOODS_GET(){
		return "select count(*) from es_premium_goods where goods_id= ? ";
	}
	
	public String getOrderItemAgentMoneys(){
		return "select a.* from es_order_items_agent_money a where ((a.col3>=trunc(sysdate-1) and a.col3<trunc(sysdate)) or (a.col5>=trunc(sysdate-1) and a.col5<trunc(sysdate))) and a.source_from='"+ManagerUtils.getSourceFrom()+"' "+
			" union all "+
			" select a.* from es_order_items_agent_money_his a where ((a.col3>=trunc(sysdate-1) and a.col3<trunc(sysdate)) or (a.col5>=trunc(sysdate-1) and a.col5<trunc(sysdate))) and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCheckAccConfigBySystemID(){
		return "select * from es_checkacct_config t where t.system_id=? and t.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	public String getCheckAccConfigBySystemName(){
		return "select * from es_checkacct_config t where t.SYSTEM_NAME=? and t.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getPayAssureList(){
		return "select order_id from es_order_ext_his a where(a.ARCHIVES_TIME>=trunc(sysdate-1) and a.ARCHIVES_TIME<trunc(sysdate)) and a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getCO_QUEUE_BAK_BY_OUT_ID(){
		return "select a.* from es_co_queue_bak a where " + 
			" exists(select a.*,a.rowid from es_order_outer t where t.order_id = a.object_id " +
			"and t.old_sec_order_id = ? " + ManagerUtils.apSFromCond("t") + ")" + ManagerUtils.apSFromCond("a");
	}
	
	public String getORDER_COUNT_BY_LOGI_NO(){
		return "select sum(logi_no_count) from " +
				"(select count(1) logi_no_count from es_delivery t where t.logi_no=? and t.order_id<>?" +
				" union all select count(1) logi_no_count from es_delivery_his t where t.logi_no=? and t.order_id<> ?)";
	}
	//暂时用不到了
//	public String getINVOICE_NUM_BY_ORDER_ID(){
//		return "select t.invoice_num from es_order_items_inv_prints t where t.order_id=? and t.print_flag=? and rownum<?";
//	}
//	
//	public String getINVOICE_NUM_BY_ORDER_ID_HIS(){
//		return "select t.invoice_num from es_order_items_inv_prints_his t where t.order_id=? and t.print_flag=? and rownum<?";
//	}
	/**
	 * 物流报表
	 */
	public String getORDER_DISTIBUTION_REPORT_HEAD_LIST(){
		return "select rownum sequence_id,out_order_id,status,opname,h_end_time,goodsname,specificatio_nname,terminal_num,tid_time,"
				+ "lock_user_name,lock_time,is_old,phone_owner_name,phone_num,ship_name,ship_tel,logi_no,receipt_no,ship_addr,"
				+ "shipping_company,pay_method,fee,ship_batch,goods_shipper,goods_send_time,net_certi,accept_agree,"
				+ "liang_agree,receive_num,j_op_id,archive_time";
	}
	
	public String getORDER_DISTIBUTION_REPORT_LIST(){
		return "select distinct eoe.out_tid out_order_id,"
				+ "(select t.pname from es_dc_public_ext t where t.stype='DIC_ORDER_STATUS' and t.pkey=eo.status) status"
				//+ ",(select t2.realname from ES_ADMINUSER t2 where t2.userid=eost.h_op_id) opname"
				+ ",(select t2.realname from ES_ADMINUSER t2 where t2.userid= decode(eo.status,'73',eost.h_op_id,'8',eost.h_op_id,'82',eost.h_op_id,'83',eost.h_op_id,'91',eost.h_op_id,'92',eost.h_op_id)) opname"
				+ ",eost.h_end_time"
				+ ",eoev.goodsname,"
				+ "eoev.specificatio_nname,eoipe.terminal_num,eoe.tid_time,(select t2.realname from ES_ADMINUSER t2 where t2.userid=eost.f_op_id) lock_user_name,eost.f_end_time lock_time,"
				+ "decode(eoipe.is_old,'0','新用户','1','老用户') is_old,eoev.phone_owner_name,eoie.phone_num,ed.ship_name,"
				+ "ed.ship_tel,ed.logi_no,ed.receipt_no,ed.ship_addr,eo.source_from,"
				+ "decode(ed.shipping_company,'EMS','EMS','EMS0001','EMS','EMS0002','EMS国内','ND0001','南都','NULL_VAL','空值','SF-FYZQYF','顺丰集中生产','SF0001','顺丰','SF0002','顺丰-国内','WSFW','WSFW','ZT0002','自提','ZY0001','自有配送-联通','ZY0002','自有配送-联通','SF0003','顺丰-国内','SF-HS','顺丰-华盛','ND0002','南都-省内',"
				+ "'EMS0003','EMS省内')shipping_company,"
				+ "edp_pay.pname pay_method,"
				+ "'' fee,eoev.ship_batch,'' goods_shipper,ed.shipping_time goods_send_time,"
				+ "edp_cer.pname net_certi,eoe.accept_agree,eoe.liang_agree,eoe.receive_num,"
				+ "(select t2.realname from ES_ADMINUSER t2 where t2.userid=eost.j_op_id) j_op_id,"
				+ "eost.j_end_time archive_time "
				+ " from es_order eo "
				+ "left join es_order_ext eoe on eoe.order_id = eo.order_id  "
				+ "left join es_order_extvtl eoev on eoev.order_id = eo.order_id  "
				+ "left join es_order_items_ext eoie on eoie.order_id = eo.order_id  "
				+ "left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
				+ "left join es_payment_logs epl on epl.order_id = eo.order_id  "
				+ "left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
				+ "left join es_delivery ed on ed.order_id = eo.order_id and ed.delivery_type='0' "
				+ "left join es_order_lock eolock on eolock.order_id = eo.order_id  "
				+ "left join es_dc_public_ext edp_pay on edp_pay.pkey=epl.pay_type and edp_pay.stype='pay_type'  "
				+ "left join es_dc_public_ext edp_cer on edp_cer.pkey=eoipe.certi_type and edp_cer.stype='certi_type'  "
				+ "left join es_ORDER_STATS_TACHE eost on eost.order_id = eo.order_id  "
				+ "where eo.source_from='"+ManagerUtils.getSourceFrom()+"' and eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	
	
	/**
	 * 订单领取报表查询头字段
	 * 
	 */
	
	public String  getORDER_RECEIVE_REPORT_HEAD_LIST(){
		return "select order_id,order_from_str,order_city,order_goodsname,order_status,order_receive,lock_user_name,phone_num,"
				+ "plan_title,tid_time,pay_status,pay_type,order_amount,paymoney,pack_type,brand_name,specificatio_nname,"
				+ "terminal_num,logi_no";
	}
	
	
	public String  getORDER_RECEIVE_REPORT_LIST(){
		return "select distinct eoe.out_tid order_id,edp_sf.pname order_from_str,er_li.local_name order_city,eoev.goodsname order_goodsname,"
				+ "(select t.pname from es_dc_public_ext t where t.stype='DIC_ORDER_STATUS' and t.pkey=eo.status) order_status,"
				+ " (select t2.realname from ES_ADMINUSER t2 where t2.userid=eol.lock_user_id) lock_user_name,decode(eol.lock_user_name,'','未领取','已领取')  order_receive,eoie.phone_num,eoev.plan_title,eoe.tid_time,"
				+ "decode (eo.pay_status,'1','已支付','0','未支付') pay_status ,edp_pm.pname pay_type,eo.order_amount,eo.paymoney,"
				+ "decode(eoe.pack_type,'01','裸机','02','上网卡','03','上网卡','04','号卡','06','合约机','08','配件') pack_type,"
				+ "eoev.brand_name,eoev.specificatio_nname,eoipe.terminal_num,ed.logi_no "
				+ " from es_order eo  left join es_order_ext eoe on eoe.order_id = eo.order_id   "
				+ " left join es_order_extvtl eoev on eoev.order_id = eo.order_id  "
				+ " left join es_order_items_ext eoie on eoie.order_id = eo.order_id   "
				+ " left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
				+ " left join es_payment_logs epl on epl.order_id = eo.order_id  "
				+ " left join es_delivery ed on ed.order_id = eo.order_id and ed.delivery_type='0' "
			//	+ " left join es_dc_public_ext edp_sf on edp_sf.pkey=eoev.source_type and edp_sf.stype='ZbOrderSource' "
				+ " left join es_dc_public_ext edp_sf on edp_sf.pkey=eoe.order_from and edp_sf.stype='source_from' "
				+ " left join es_dc_public_ext edp_pm on edp_pm.pkey = eoe.pay_type and edp_pm.stype='pay_type'  "
				+ " left join es_order_lock eol on eol.order_id=eo.order_id and eoe.flow_trace_id=eol.tache_code " //订单当前环节和锁定单的环节匹配
				+ " left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
				+ " where eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	
	/**
	 * 库管日报查询字段
	 * @return
	 */
	public String  getWAREHOUSE_DAILY_REPORT_HEAD_LIST(){
		
		return "select create_time2,tid_time,receive_num,order_id,order_status,order_from_str,"
				+ "phone_owner_name,phone_num,order_city,specificatio_nname,pack_type,terminal_num,receiver_name";
	}

	/**
	 * 库管日报查询内容
	 * @return
	 */
	
	public String getWAREHOUSE_DAILY_REPORT_LIST(){
		 
		//String abc =this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE"); //获得省份代码
		
		return "select distinct eost.h_begin_time create_time2,eoe.out_tid order_id,eoe.tid_time,eoe.receive_num,"
		    		+ "(select t.pname from es_dc_public_ext t where t.stype='DIC_ORDER_STATUS' and t.pkey=eo.status) order_status,"
		    		+ "edp_sf.pname order_from_str,eoev.phone_owner_name,eoie.phone_num,er_li.local_name order_city,"
		    		+ "decode(eoe.pack_type,'01','裸机','02','上网卡','03','上网卡','04','号卡','06','合约机','08','配件') pack_type,"
		    		+ "eoev.specificatio_nname,eoipe.terminal_num,(select t5.realname from ES_ADMINUSER t5 where t5.userid=eost.h_op_id) receiver_name "
		    		+ "from es_order eo "
		    		+ "left join es_order_ext eoe on eoe.order_id = eo.order_id "
		    		+ "left join es_order_extvtl eoev on eoev.order_id = eo.order_id  "
		    		+ "left join es_order_items eoi on eoi.order_id = eo.order_id "
		    		+ "left join es_order_items_ext eoie on eoie.order_id = eo.order_id "
		    		+ "left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
		    		+ "left join es_dc_public_ext edp_sf on edp_sf.pkey = eoe.order_from and edp_sf.stype='source_from' "
		    		+ " left join es_order_lock eol on eol.order_id=eo.order_id "
		    		+ " left join es_order_ext eoe2 on eoe2.order_id=eol.order_id and eoe2.flow_trace_id=eol.tache_code"
		    		+ " left join es_ORDER_STATS_TACHE eost on eost.order_id = eo.order_id  "
		    		+ " left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
		    		+ " where eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
	}
	
	/**
	 * 退款订单报表头字段1/2
	 * @return
	 */
	public String  getORDER_REFUND_HEAD_LIST(){
		
		return "select order_from,out_tid,status,bss_account_time,refund_time,order_city,goods_type,GoodsName,phone_num,paymoney,busi_money,brand_name,specificatio_nname,terminal_color,terminal_num,is_old,phone_owner_name,ship_tel ";
	}
	
	
	/**
	 * 退款订单报表体 2/2
	 * @return
	 */
	public String  getORDER_REFUND_LIST(){
		
		return "select eoe.tid_time,edp_sf.pname order_from,eoe.out_tid,"
				+ "(select t.pname from es_dc_public_ext t where t.stype='DIC_ORDER_STATUS' and t.pkey=eo.status)status,"
				+ "eoie.bss_account_time,eoe.refund_time,er_li.local_name  order_city,edp_goods.pname goods_type,"
				+ "eoevtl.GoodsName,eoie.phone_num,eo.paymoney,eo.busi_money,eoevtl.brand_name,"
				+ "eoevtl.specificatio_nname,'' terminal_color,eoipe.terminal_num,decode(eoipe.is_old,'0','新用户','1','老用户') is_old,"
				+ "eoevtl.phone_owner_name,ed.ship_tel "
				+ "from es_order eo "
				+ "left join es_order_ext eoe on eoe.order_id = eo.order_id "
				+ "left join es_order_extvtl eoevtl on eoevtl.order_id = eo.order_id "
				+ "left join es_delivery ed on ed.order_id = eo.order_id "
				+ "left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
				+ "left join es_order_items_ext eoie on eoie.order_id = eo.order_id "
				+ " left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
				+ "left join es_dc_public_ext edp_goods on edp_goods.pkey=eoie.goods_type and edp_goods.stype='goods_type' "
				+ "left join es_dc_public_ext edp_sf on edp_sf.pkey=eoevtl.source_type and edp_sf.stype='ZbOrderSource' "
				+ "where eo.source_from = 'ECS' "
				+ "and eoe.refund_deal_type='02' ";
	}
	
	public String getORDER_MONI_TOTAL_LIST(){
		return " select out_tid ,order_id ,tid_time ,create_time ,order_from_str ,order_city ,current_flow_name , "+
				" pack_type ,goodsname ,wms_refund_status,plan_title ,first_payment ,phone_num ,order_mode_str , "+
				" pay_type,pay_method ,pay_status ,to_char(pay_time,'yyyy-mm-dd hh24:mi:ss') pay_time,service_remarks ,shipping_quick , "+
				" shipping_type ,spread_channel ,development_code ,development_name ,payplatformorderid , "+
				" goods_num ,terminal_num ,iccid ,brand_name ,model_code ,ative_type ,phone_owner_name , "+
				" certi_type ,cert_card_num ,cert_address ,invoice_title ,recommended_name ,recommended_phone , "+
				" shipping_company_name ,logi_no ,ship_name ,ship_tel ,buyer_message ,seller_message , "+
				" auto_exception_type ,auto_exception_desc ,exception_type ,exception_desc ,invoice_num ,paymoney ,bss_account_time  ,receipt_status ,sign_status ," +
				" exception_type1 ,exception_desc1 ,exception_type2 ,exception_desc2 ,wm_isreservation_from ,related_order_id";
	}
	
	
	
	
	
	public String getORDER_MONI_LIST(){
		return " select distinct eo.create_time,eoe.tid_time,eoe.out_tid,eo.order_id,edp_sf.pname order_from_str,er_li.local_name order_city,edp_cfl.pname current_flow_name, "+
				" eoev.goodsname,decode(eoev.wms_refund_status,'13','退单申请通知WMS','15','退单完成通知WMS','20','WMS回库确认') wms_refund_status," +
				" decode(eoe.pack_type,'01','裸机','02','上网卡','03','上网卡','04','号卡','06','合约机','08','配件') pack_type,eoev.plan_title,edp_fp.pname first_payment, "+
				" eoie.phone_num,decode(eoe.order_model,'01','自动化生产','02','人工集中生产','03','现场生产','04','物流生产','05','独立生产') order_mode_str, "+
				" case when eoe.abnormal_type='3' and eoe.abnormal_status='1' then edp_aex.pname end auto_exception_type, "+
				" case when eoe.abnormal_type='3' and eoe.abnormal_status='1' then eoe.exception_desc end auto_exception_desc, "+
				" case when eoe.abnormal_type='1' and eoe.abnormal_status='1' then edp_ex.pname end exception_type, "+
				" case when eoe.abnormal_type='1' and eoe.abnormal_status='1' then eoe.exception_desc end exception_desc, "+
				" eoev.service_remarks,decode(eoe.shipping_quick,'1','闪电送','非闪电送') shipping_quick,edp_st.pname shipping_type,edp_pm.pname pay_type," +
				" (select t.pname from es_dc_public_ext t where t.stype='pay_way' and t.pkey=epl.pay_method) pay_method, "+
				" eoe.spread_channel,eoev.development_name,eoev.development_code,decode(eo.pay_status,'1','已支付','0','未支付') pay_status,eo.pay_time, "+
				" eoev.payplatformorderid,'1' goods_num,eoipe.terminal_num,nvl(eoev.iccid,eoipe.iccid) iccid,eoev.brand_name,eg.model_code,edp_at.pname ative_type, "+
				" eoev.phone_owner_name,edp_ct.pname certi_type,eoipe.cert_card_num,eoipe.cert_address,eoie.reserve8 invoice_title,eoev.recommended_name,eoev.recommended_phone, "+
				" eoev.shipping_company_name,ed.logi_no,eo.ship_name,ed.ship_addr,ed.ship_tel,eoev.buyer_message,eoev.seller_message,  "+
				" eoiip.invoice_num,eo.paymoney,eoie.bss_account_time,decode(ed.receipt_status,'1','已签收','0','未签收') receipt_status,decode(ed.sign_status,'1','已签收','0','未签收') sign_status, "+
				" decode(eoel1.exception_type,'00','欠费问题','01','号码问题','02','合约期问题','03','信息问题','04','无法接通','05','套餐问题','06','系统问题','07','缺货','08','无转兑包','09','转兑包互斥','10','黑名单问题'," +
				" '13','无操作指引','14','证件问题','15','融合业务','99','其他异常','NoGoods','缺货异常','OpenEr','开户异常','BSSEr','业务办理异常','CHECKEr','稽核异常') exception_type1," +
				" eoel1.exception_desc exception_desc1, decode(eoel2.exception_type,'00','欠费问题','01','号码问题','02','合约期问题','03','信息问题','04','无法接通','05','套餐问题','06','系统问题','07','缺货','08'," +
				" '无转兑包','09','转兑包互斥','10','黑名单问题','13','无操作指引','14','证件问题','15','融合业务','99','其他异常','NoGoods','缺货异常','OpenEr','开户异常','BSSEr','业务办理异常','CHECKEr','稽核异常') exception_type2," +
				" eoel2.exception_desc exception_desc2,edp_isr.pname wm_isreservation_from,eoe.related_order_id "+
				" from es_order eo "+
				" left join es_order_ext eoe on eoe.order_id = eo.order_id "+
				" left join es_order_extvtl eoev on eoev.order_id = eo.order_id "+
				" left join es_order_items eoi on eoi.order_id = eo.order_id "+
				" left join es_order_items_ext eoie on eoie.order_id = eo.order_id "+
				" left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "+
				" left join es_delivery ed on ed.order_id = eo.order_id and ed.delivery_type='0' "+
				" left join es_payment_logs epl on epl.order_id = eo.order_id "+
				" left join es_goods eg on eg.goods_id = eoi.goods_id "+
				" left join es_dc_public_ext edp_sf on edp_sf.pkey = eoe.order_from and edp_sf.stype='source_from' "+
				" left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"+
				" left join es_order_items_inv_prints eoiip on eoiip.order_id = eo.order_id and eoiip.print_flag='1' "+//发票相关
				" left join es_dc_public_ext edp_cfl on edp_cfl.pkey = eoe.flow_trace_id and edp_cfl.stype='DIC_ORDER_NODE' "+
				" left join es_dc_public_ext edp_st on edp_st.pkey = eo.shipping_type and edp_st.stype='shipping_type' "+
				" left join es_dc_public_ext edp_pm on edp_pm.pkey = eoe.pay_type and edp_pm.stype='pay_type' "+
				" left join es_dc_public_ext edp_fp on edp_fp.pkey = eoipe.first_payment and edp_fp.stype='offer_eff_type' "+
				" left join es_dc_public_ext edp_at on edp_at.pkey = eoev.ative_type and edp_at.stype='DICT_ACCOUNT_4G_TYPE' "+
				" left join es_dc_public_ext edp_ct on edp_ct.pkey = eoipe.certi_type and edp_ct.stype='certi_type' "+
				" left join es_dc_public_ext edp_aex on edp_aex.pkey = eoe.exception_type and edp_aex.stype='EXCEPTION_TYPE' "+
				" left join es_dc_public_ext edp_ex on edp_ct.pkey = eoe.exception_type and edp_ct.stype='DIC_ORDER_EXCEPTION_TYPE' "+
				" left join es_dc_public_ext edp_isr on edp_isr.pkey = eoe.wm_isreservation_from and edp_isr.stype='DIC_BESPOKE_FLAG' "+
				" left join ( SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY order_id ORDER BY mark_time DESC) LEV,   r.*  "+
                " FROM es_order_exception_logs r where is_deal='0' and abnormal_type='1') WHERE LEV = 1 ) eoel1 on eoel1.order_id = eo.order_id "+
				" left join ( SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY order_id ORDER BY mark_time DESC) LEV,   r.*  "+
                " FROM es_order_exception_logs r where is_deal='0' and abnormal_type='3') WHERE LEV = 1 ) eoel2 on eoel2.order_id = eo.order_id "+
				" where eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
	}
	
	public String getORDER_MONI_HIS_LIST(){
		return " select distinct eo.create_time,eoe.tid_time,eoe.out_tid,eo.order_id,edp_sf.pname order_from_str,er_li.local_name order_city,edp_cfl.pname current_flow_name, "+
				" eoev.goodsname,decode(eoev.wms_refund_status,'13','退单申请通知WMS','15','退单完成通知WMS','20','WMS回库确认') wms_refund_status," +
				" decode(eoe.pack_type,'01','裸机','02','上网卡','03','上网卡','04','号卡','06','合约机','08','配件') pack_type,eoev.plan_title,edp_fp.pname first_payment, "+
				" eoie.phone_num,decode(eoe.order_model,'01','自动化生产','02','人工集中生产','03','现场生产','04','物流生产','05','独立生产') order_mode_str, "+
				" case when eoe.abnormal_type='3' and eoe.abnormal_status='1' then edp_aex.pname end auto_exception_type, "+
				" case when eoe.abnormal_type='3' and eoe.abnormal_status='1' then eoe.exception_desc end auto_exception_desc, "+
				" case when eoe.abnormal_type='1' and eoe.abnormal_status='1' then edp_ex.pname end exception_type, "+
				" case when eoe.abnormal_type='1' and eoe.abnormal_status='1' then eoe.exception_desc end exception_desc, "+
				" eoev.service_remarks,decode(eoe.shipping_quick,'1','闪电送','非闪电送') shipping_quick,edp_st.pname shipping_type,edp_pm.pname pay_type," +
				" (select t.pname from es_dc_public_ext t where t.stype='pay_way' and t.pkey=epl.pay_method) pay_method, "+
				" eoe.spread_channel,eoev.development_name,eoev.development_code,decode(eo.pay_status,'1','已支付','0','未支付') pay_status,eo.pay_time, "+
				" eoev.payplatformorderid,'1' goods_num,eoipe.terminal_num,nvl(eoev.iccid,eoipe.iccid) iccid,eoev.brand_name,eg.model_code,edp_at.pname ative_type, "+
				" eoev.phone_owner_name,edp_ct.pname certi_type,eoipe.cert_card_num,eoipe.cert_address,eoie.reserve8 invoice_title,eoev.recommended_name,eoev.recommended_phone, "+
				" eoev.shipping_company_name,ed.logi_no,eo.ship_name,ed.ship_addr,ed.ship_tel,eoev.buyer_message,eoev.seller_message,  "+
				" eoiip.invoice_num,eo.paymoney,eoie.bss_account_time,decode(ed.receipt_status,'1','已签收','0','未签收') receipt_status,decode(ed.sign_status,'1','已签收','0','未签收') sign_status, "+
				" decode(eoel1.exception_type,'00','欠费问题','01','号码问题','02','合约期问题','03','信息问题','04','无法接通','05','套餐问题','06','系统问题','07','缺货','08','无转兑包','09','转兑包互斥','10','黑名单问题'," +
				" '13','无操作指引','14','证件问题','15','融合业务','99','其他异常','NoGoods','缺货异常','OpenEr','开户异常','BSSEr','业务办理异常','CHECKEr','稽核异常') exception_type1," +
				" eoel1.exception_desc exception_desc1, decode(eoel2.exception_type,'00','欠费问题','01','号码问题','02','合约期问题','03','信息问题','04','无法接通','05','套餐问题','06','系统问题','07','缺货','08'," +
				" '无转兑包','09','转兑包互斥','10','黑名单问题','13','无操作指引','14','证件问题','15','融合业务','99','其他异常','NoGoods','缺货异常','OpenEr','开户异常','BSSEr','业务办理异常','CHECKEr','稽核异常') exception_type2," +
				" eoel2.exception_desc exception_desc2,edp_isr.pname wm_isreservation_from,eoe.related_order_id "+
				" from es_order_his eo "+
				" left join es_order_ext_his eoe on eoe.order_id = eo.order_id "+
				" left join es_order_extvtl_his eoev on eoev.order_id = eo.order_id "+
				" left join es_order_items_his eoi on eoi.order_id = eo.order_id "+
				" left join es_order_items_ext_his eoie on eoie.order_id = eo.order_id "+
				" left join es_order_items_prod_ext_his eoipe on eoipe.order_id = eo.order_id "+
				" left join es_delivery_his ed on ed.order_id = eo.order_id and ed.delivery_type='0'"+
				" left join es_payment_logs_his epl on epl.order_id = eo.order_id "+
				" left join es_goods eg on eg.goods_id = eoi.goods_id "+
				" left join es_dc_public_ext edp_sf on edp_sf.pkey = eoe.order_from and edp_sf.stype='source_from' "+
				" left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"' "+
				" left join es_order_items_inv_prints_his eoiip on eoiip.order_id = eo.order_id and eoiip.print_flag='1' "+//发票相关
				" left join es_dc_public_ext edp_cfl on edp_cfl.pkey = eoe.flow_trace_id and edp_cfl.stype='DIC_ORDER_NODE' "+
				" left join es_dc_public_ext edp_st on edp_st.pkey = eo.shipping_type and edp_st.stype='shipping_type' "+
				" left join es_dc_public_ext edp_pm on edp_pm.pkey = eoe.pay_type and edp_pm.stype='pay_type' "+
				" left join es_dc_public_ext edp_fp on edp_fp.pkey = eoipe.first_payment and edp_fp.stype='offer_eff_type' "+
				" left join es_dc_public_ext edp_at on edp_at.pkey = eoev.ative_type and edp_at.stype='DICT_ACCOUNT_4G_TYPE' "+
				" left join es_dc_public_ext edp_ct on edp_ct.pkey = eoipe.certi_type and edp_ct.stype='certi_type' "+
				" left join es_dc_public_ext edp_aex on edp_aex.pkey = eoe.exception_type and edp_aex.stype='EXCEPTION_TYPE' "+
				" left join es_dc_public_ext edp_ex on edp_ct.pkey = eoe.exception_type and edp_ct.stype='DIC_ORDER_EXCEPTION_TYPE' "+
				" left join es_dc_public_ext edp_isr on edp_isr.pkey = eoe.wm_isreservation_from and edp_isr.stype='DIC_BESPOKE_FLAG' "+
				" left join ( SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY order_id ORDER BY mark_time DESC) LEV,   r.*  "+
                " FROM es_order_exception_logs_his r where is_deal='0' and abnormal_type='1') WHERE LEV = 1 ) eoel1 on eoel1.order_id = eo.order_id  "+
				" left join ( SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY order_id ORDER BY mark_time DESC) LEV,   r.*  "+
                " FROM es_order_exception_logs_his r where is_deal='0' and abnormal_type='3') WHERE LEV = 1 ) eoel2 on eoel2.order_id = eo.order_id "+
				" where eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	
	public String getMonth_account() {
		return "select t.account from Es_month_account t where t.order_model = ? and t.account_owner = ? ";
	}
	
	public String getORDER_RELEASE_LIST(){
		return "select t.*,username.realname from es_order_release_record t left join es_adminuser username on t.deal_username=username.username where t.source_from = '"+ManagerUtils.getSourceFrom()+"' ";
	}
	
	public String getRELEASE_RECORD_SELECT_BY_ID(){
		return "select * from es_order_release_record t where t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.release_id = ? ";
	}
	
	public String getADMINUSER_BY_USERNAME(){
		return "select * from es_adminuser t where t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.username = ? and rownum<2";
	}
	
	public String getADMINUSER_COUNT_BY_USERNAME(){
		return "select count(1) from es_adminuser t where t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.username = ? and rownum<2";
	}

	public String getORDER_ACTION(){
		return "select t.*,a.pname action_name from es_order_action_log t left join es_dc_public_ext a on a.stype='order_action' and t.action=a.pkey where t.source_from='"+ManagerUtils.getSourceFrom()+"'";
	}	
	
	public String getSWITCH_BY_PC_QUEUE(){
		return "select t.queue_switch from es_queue_manager t where t.queue_no=? and t.source_from='"+ManagerUtils.getSourceFrom()+"' and rownum<2";
	}

	public String getINSERT_ORDER_TO_PC_QUEUE(){
		return "insert into es_queue_write_card(queue_code,order_id,create_time,open_account_status,open_account_time,write_card_status,write_card_time,exception_type,exception_msg,queue_status,source_from,out_tid) values(?,?,sysdate,'0',null,'0',null,'','','0','"+ManagerUtils.getSourceFrom()+"',?)";
	}	
	
	
	public String getLOGIS_TRANSFER_LIST_TOTAL(){
		return "select op_id,create_time,out_tid,order_id,pname,name,logi_no";
	}	
	
	public String getLOGIS_TRANSFER_LIST(){
		return "select tc.op_id,tc.create_time,tb.out_tid,tc.order_id,tf.name pname,td.name,ta.logi_no from ES_DELIVERY ta , es_order_ext tb , ES_ORDER_HANDLE_LOGS tc , es_order_items td, es_order_items_ext te, es_goods_cat tf where tb.source_from = '"+ManagerUtils.getSourceFrom()+"' and ta.order_id = tb.order_id and ta.order_id=tc.order_id and ta.order_id=td.order_id and ta.order_id=te.order_id and te.goods_type=tf.cat_id and ta.type='1' and tb.abnormal_status<>'1' and tc.flow_trace_id='H'";
	}
	
	public String getLOGIS_TRANSFER_LIST_HIS(){
		return "select tc.op_id,tc.create_time,tb.out_tid,tc.order_id,tf.name pname,td.name,ta.logi_no from ES_DELIVERY_his ta , es_order_ext_his tb , ES_ORDER_HANDLE_LOGS_his tc , es_order_items_his td, es_order_items_ext_his te, es_goods_cat tf where tb.source_from = '"+ManagerUtils.getSourceFrom()+"' and ta.order_id = tb.order_id and ta.order_id=tc.order_id and ta.order_id=td.order_id and ta.order_id=te.order_id and te.goods_type=tf.cat_id and ta.type='1' and tb.abnormal_status<>'1' and tc.flow_trace_id='H'";
	}	
	
	public String getQUERY_LOGI_CONF(){
		return "select a.is_need_receipt,a.is_protect_price,a.deliver_info from es_dc_print_template a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.goods_cat_id = ? and a.shipping_company = ? ";
	}	
	
	public String getQUERY_PRINTER_NAME(){
		return "select a.printer_name from es_dc_printer a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.user_id = ? and a.print_btn_ename = ? ";
	}

	public String getAUTO(){
		return "select a.codea from es_dc_public_ext a where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.stype='DIC_OPER_MODE' and a.pkey = ? ";
	}

	public String getSELECT_ORDER_PC_QUEUE(){
		return "select c.write_machine_code from es_order_wms_key_info c where c.order_id = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"'";
	}	
	
	public String getSELECT_ES_ORDER_AUDIT(){
		return "select a.* from es_order_warning a where a.source_from='"+ManagerUtils.getSourceFrom()+"' ";
	}
	/**
	 * 营业日报
	 * @return
	 */
	public String getORDER_BUSINESS_REPORT_HEAD_LIST(){
		
		return "select card_write_time,tid_time,order_operator,order_type,order_plat_type,order_from_str"
				+ ",out_order_id,goods_type2,goodsname,plan_title"//,pack_type,goods_num
				+ ",is_old,first_payment,contract_month,order_city,phone_num,phone_owner_name"
				+ ",certi_type,cert_card_num,ship_name,ship_tel,ship_addr,pay_type,order_model,ship_type"
				+ ",shipping_company,logi_no,terminal_num,invoice_no,cellphone_type,discountname"
				+ ",buyer_message,paymoney,busi_money,pickup_self_address,outlet_receiver"
				+ ",outlet_contact_number,outlet_pickup_selft ";
		
	}
    public String getORDER_BUSINESS_REPORT_LIST(){
    	return "select distinct eost.x_end_time card_write_time,eoe.tid_time,edp_sf.pname order_from_str,edp_sf2.pname order_plat_type,eo.order_id,"
    			+ "eoe.out_tid out_order_id,"
    			+ "edp_ordt.pname order_type,"
    			//+ "edp_pro.pname pack_type,"
    			+ "eoev.goodsname,eoev.plan_title,"
    			+ "edp_goods.pname goods_type2,"
    			//+ "decode(eoe.pack_type,'01','裸机','02','上网卡','03','上网卡','04','号卡','06','合约机','08','配件') pack_type,"
    			//+ "eoie.goods_num,"
    			+ "decode(eoipe.is_old,'0','新用户','1','老用户')is_old,"
    			+ "decode(eoipe.first_payment,'HALF','半月生效','ALLM','当月生效')first_payment,"
    			+ "eoipe.contract_month,er_li.local_name order_city,eoie.phone_num,eoev.phone_owner_name,"
    			+ "edp_cer.pname certi_type,"
    			+ "eoipe.cert_card_num,ed.ship_name,ed.ship_tel,ed.ship_addr,"
    			+ "edp_pay.pname pay_type,"
    			+ "decode(ed.shipping_company,'EMS','EMS','EMS0001','EMS','EMS0002','EMS国内','ND0001','南都','NULL_VAL','空值','SF-FYZQYF',"
    			+ "'顺丰集中生产','SF0001','顺丰','SF0002','顺丰-国内','WSFW','WSFW','ZT0002','自提','ZY0001','自有配送-联通','ZY0002','自有配送-联通',"
    			+ "'SF0003','顺丰-国内','SF-HS','顺丰-华盛','ND0002','南都-省内','EMS0003','EMS省内')shipping_company,"//物流
    			+ "ed.ship_type,ed.logi_no,"
    			+ "eoipe.terminal_num,eoie.invoice_no,eoev.discountname,eoev.buyer_message,eo.paymoney,eo.busi_money,eo.status,"
    			+ "(select t5.realname from ES_ADMINUSER t5 where t5.userid=eost.d_op_id) order_operator,"
    			+ "decode(eoe.order_model,'02','自动','05','手工','06','手工')order_model,"
    			+ "eoev.specificatio_nname cellphone_type,"
    			+ "'' pickup_self_address,"// 暂无数据字段	   
    			+ "'' outlet_receiver,"// 暂无数据字段	
    			+ "'' outlet_contact_number,"// 暂无数据字段	
    			+ "'' outlet_pickup_selft "// 暂无数据字段	
    			+ "from es_order eo "
    			+ "left join es_order_ext eoe on eoe.order_id = eo.order_id "
    			+ "left join es_order_extvtl eoev on eoev.order_id = eo.order_id  "
    			+ "left join es_order_items_ext eoie on eoie.order_id = eo.order_id  "
    			+ "left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id "
    			+ "left join es_payment_logs epl on epl.order_id = eo.order_id  "
    			+ "left join es_delivery ed on ed.order_id = eo.order_id and ed.delivery_type='0' "
    			+ "left join es_dc_public_ext edp_sf on edp_sf.pkey=eoe.order_from and edp_sf.stype='source_from'  "
    			+ "left join es_dc_public_ext edp_sf2 on edp_sf2.pkey=eoe.plat_type and edp_sf2.stype='plat_type'"
    			
    			+ "left join es_dc_public_ext edp_pro on edp_pro.pkey=eoe.pack_type and edp_pro.stype='goods_type'  "
    			+ "left join es_dc_public_ext edp_pay on edp_pay.pkey=epl.pay_type and edp_pay.stype='pay_type'  "
    			+ "left join es_dc_public_ext edp_ordt on edp_ordt.pkey=eo.order_type and edp_ordt.stype='DIC_ORDER_NEW_TYPE'  "
    			+ "left join es_order_items_prod eoip on eoip.order_id = eo.order_id "
    			+ "left join es_dc_public_ext edp_cer on edp_cer.pkey=eoipe.certi_type and edp_cer.stype='certi_type'  "
    			+ "left join es_dc_public_ext edp_goods on edp_goods.pkey=eoie.goods_type and edp_goods.stype='goods_type' "
    			+" left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
    			+ "left join es_order_lock eolock on eolock.order_id = eo.order_id "
				+ " left join es_ORDER_STATS_TACHE eost on eost.order_id = eo.order_id  "
    			+ " where eo.source_from = '"+ManagerUtils.getSourceFrom()+"' and eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
    }
	
    
    
	public String getORDER_HIS_HEAD_LIST(){
		
		return "select out_order_id,order_src,order_type,goods_type,goods_name,order_status,is_take,subs_svc_number,main_prod_name,"
				+ "area_id,deal_time,pay_type,pay_status,delivery_type,order_amount,termial_model,buyer,buyer_phone,data_src,to_address,to_contactor,order_remark ";
		
	}
	
	public String getORDER_HIS_LIST(){
		
		return "select oo.out_order_id,"
    			+ "edp_src.pname order_src,"
    			+ "edp_type.pname order_type,"
    			+ "decode(oo.goods_type,'101','号码','102','手机','104','配件','103','上网卡','105','流量充值卡','106','存费送费','99','其他') goods_type,"
				+ "oo.goods_name,"
    			+ "edp_status.pname order_status,"
    			+ "decode(oo.is_take,'0','未领取','1','已领取') is_take,"
				+ "oo.subs_svc_number,"
				+ "oo.main_prod_name,"
    			+ "decode(oo.area_id,'A','杭州','B','温州','C','衢州','D','湖州','E','嘉兴','F','绍兴','G','台州','H','丽水','I','金华','J','舟山','K','宁波') area_id,"
				+ "oo.deal_time,"
    			+ "decode(oo.pay_type,'101','在线支付','102','货到付款','104','后向支付','103','刷卡') pay_type,"
    			+ "decode(oo.pay_status,'11','未支付','13','已支付','14','申请退款','15','退款成功','16','退款失败','17','线下退款','12','部分支付') pay_status,"
    			+ "decode(oo.delivery_type,'101','快递','102','来联通自提','104','现场提货','103','不需要配送') delivery_type,"
				+ "oo.order_amount,"
				+ "da.p_desc termial_model,"
				+ "oo.buyer,"
				+ "oo.buyer_phone,"
    			+ "decode(oo.data_src,'1002','总部商城','1005','惠生基金','1001','本地天猫','​1004','集客二维码','1006','拍拍网','1007','线下订单','1008','沃盟微店','1003','zj186','1009','点信宝','1010','天猫分销','1011','手拉手') data_src,"
				+ "exp.to_address,"
				+ "exp.to_contactor, "
				+ "oo_ext.order_remark "
				+ "from es_uni_order oo "
    			+ "left join es_dc_public_ext edp_status on edp_status.pkey=oo.order_status and edp_status.stype='uni_order_status'  "
    			+ "left join es_dc_public_ext edp_type on edp_type.pkey=oo.order_type and edp_type.stype='uni_order_type'  "
    			+ "left join es_dc_public_ext edp_src on edp_src.pkey=oo.order_src and edp_src.stype='uni_order_src'  "
    			+ "left join es_uni_order_express exp on exp.order_id = oo.order_id  "
    			+ "left join es_SYS_PARAM_DETAIL da on da.p_value=oo.termial_model and da.p_key='UNI_TERMINAL_MODEL'  "
    			+ "left join es_uni_order_ext oo_ext on oo_ext.order_id=oo.order_id ";

	}
	/**
	 * 宽带电商化报表头
	 * @return
	 */
	public String getORDER_BROADBAND_COMMERCE(){
		return "select order_city, order_county, order_from, order_id, out_tid,receipt_time, "
				+ "ship_name, ship_tel, ship_addr,goods_id, goodsname, office_id,office_name, "
				+ "deal_operator_num,  deal_operator_name, development_point_code, development_point_name,"
				+ "development_code, development_name, status, out_office_id, out_office_name, operator_id, "
				+ "operator_name,pro_realfee, wxsg_status, complete_time, dispatch_time";
		
	}
	
	
	/**
	 * 实收款稽核报表查询字段(该功能17年1月取消，已经被整合到财务稽核处)
	 * @return
	 */
	public String getORD_ACTUAL_REVENUE_AUDIT_REPORT_HEAD_LIST(){
		return "select rownum seq_num,d_begin_time,city,phone_num,order_from,"
				+ "out_tid,payprovidername,pay_time,refund_time,payplatformorderid,"
				+ "paytype,account_paymoney,paymoney,paid_in_difference,busi_money,"
				+ "ess_money,discountrange,logi_no,audit_receive_money_status,"
				+ "audit_comments ";
		
	}

	
	/**
	 * 实收款稽核报表内容部分(该功能17年1月取消，已经被整合到财务稽核处)
	 * @return
	 */
	
	public String getORD_ACTUAL_REVENUE_AUDIT_REPORT_REPORT_LIST(){
		
		return "select distinct eost.d_begin_time,er_li.local_name city,eoie.phone_num,edp_sf.pname order_from,eoe.out_tid,"
				+ "eoevtl.payprovidername,eo.pay_time,eoe.refund_time,"
				+ "eoevtl.payplatformorderid,edp_pm.pname paytype,eo.paymoney,"
				+ "eo.busi_money,eo.ess_money,eoevtl.discountrange,"
				+ "ed.logi_no,eoe.audit_receive_money_status,"
				+ "eo.order_id, "
				+ "'' account_paymoney,"
				+ "'' paid_in_difference,"
				+ "'' audit_comments"
				+ " from es_order eo "
				+ "left join es_order_ext eoe on eoe.order_id=eo.order_id "
				+ "left join es_order_extvtl eoevtl on eoevtl.order_id=eo.order_id "
				+ "left join es_order_items_ext eoie on eoie.order_id=eo.order_id "
				+ "left join es_delivery ed on ed.order_id=eo.order_id "
				+ "left join es_order_stats_tache eost on eost.order_id=eo.order_id "
				+ "left join es_payment_logs epl on epl.order_id=eo.order_id "
				+ " left join es_dc_public_ext edp_pm on edp_pm.pkey = eoe.pay_type and edp_pm.stype='pay_type' "
				+ "left join es_dc_public_ext edp_sf on edp_sf.pkey = eoe.order_from and edp_sf.stype='source_from'"
				+ "left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
				+ " where eo.source_from = '"+ManagerUtils.getSourceFrom()+"' and eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	
	/*
	 * 订单数据查询报表
	 * 头
	 */
	
	public String getORDER_DATA_SEARCH_TOTAL_LIST(){
		return "select  out_tid,tid_time,order_collect_time,package_acp_date,"
				+ " time_difference,order_from,order_origin,spread_channel,development_name,"
				+ " development_code,handle_name,handle_time,f_op_id,f_end_time,shipping_time,logi_no,"
				+ " terminal_num,refund_desc,lan_code,goods_type,goodsName,invoice_no,plan_title,"
				+ " privilege_combo,phone_num,zb_fuka_info,terminal,prod_cat_id,contract_month,"
				+ " pro_realfee,pay_status,paytype,status,account_status,is_old,ship_name,"
				+ " reference_phone,contact_addr,carry_person_mobile,audit_remark,service_remarks,"
				+ " devlopname,sending_type,prize,special_combo,phone_owner_name";
	}
	/**
	 * 订单数据查询报表
     * 头
	 */
	public String getORDER_DATA_SEARCH_TOTAL_LIST_NEW(){
        return "select  out_tid,tid_time,order_collect_time,sign_status,route_acceptime,"
                + " time_difference,order_from,order_origin,spread_channel,development_name,"
                + " development_code,handle_name,handle_time,f_op_id,f_end_time,ship_end_time,logi_no,"
                + " terminal_num,refund_desc,lan_code,goods_type,goodsName,invoice_no,plan_title,"
                + " privilege_combo,phone_num,zb_fuka_info,terminal,prod_cat_id,contract_month,"
                + " pro_realfee,pay_status,paytype,status,account_status,is_old,ship_name,"
                + " reference_phone,ship_addr,carry_person_mobile,audit_remark,service_remarks,"
                + " devlopname,sending_type,prize,special_combo,phone_owner_name,mainnumber,active_flag,active_time";
    }
	
	/*
	 * 订单数据查询报表
	 * 查询体
	 */
	
	public String getORDER_DATA_LIST(){
		return "select distinct eoe.out_tid,eoe.tid_time,"
				+ "nvl2(eol.lock_user_id,eol.lock_time,'') order_collect_time,"
				+ "'' package_acp_date,"
				+ "'' time_difference,"
				+ "edp_datasrc.pname order_from,edp_datasrc.pname order_origin,eoe.spread_channel,eoevtl.development_name,eoevtl.development_code,"
				+ "decode(eoe.flow_trace_id,"
				+ "'B',(select tu1.realname from ES_ADMINUSER tu1 where tu1.userid=eost.b_op_id),"
				+ "'C',(select tu2.realname from ES_ADMINUSER tu2 where tu2.userid=eost.c_op_id),"
				+ "'D',(select tu3.realname from ES_ADMINUSER tu3 where tu3.userid=eost.d_op_id),"
				+ "'F',(select tu4.realname from ES_ADMINUSER tu4 where tu4.userid=eost.f_op_id),"
				+ "'H',(select tu5.realname from ES_ADMINUSER tu5 where tu5.userid=eost.h_op_id),"
				+ "'J',(select tu6.realname from ES_ADMINUSER tu6 where tu6.userid=eost.j_op_id),"
				+ "'X',(select tu7.realname from ES_ADMINUSER tu7 where tu7.userid=eost.x_op_id),"
				+ "'Y',(select tu8.realname from ES_ADMINUSER tu8 where tu8.userid=eost.y_op_id),"
				+ "'L',(select tu9.realname from ES_ADMINUSER tu9 where tu9.userid=eost.l_op_id)) handle_name,"
				+ "decode(eoe.flow_trace_id,"
				+ "'B',b_end_time,"
				+ "'C',c_end_time,"
				+ "'D',d_end_time,"
				+ "'F',f_end_time,"
				+ "'H',h_end_time,"
				+ "'J',j_end_time,"
				+ "'X',x_end_time,"
				+ "'Y',y_end_time,"
				+ "'L',l_end_time) handle_time,"
				+ "(select tu1.realname from ES_ADMINUSER tu1 where tu1.userid=eost.f_op_id) f_op_id,"
				+ "eost.f_end_time,ed.shipping_time,ed.logi_no,eoipe.terminal_num,eoe.refund_desc,"
				+ "er_li.local_name lan_code,"
				+ "edp_goodstype.pname goods_type,"
				+ "eoevtl.goodsName,eoie.invoice_no,eoevtl.plan_title,"
				+ "'' privilege_combo,eoie.phone_num,"
				+ "'' zb_fuka_info,"
				+ "'' terminal,eoe.prod_cat_id,eoipe.contract_month,eoevtl.pro_realfee,"
				+ "decode(eo.pay_status,'1','已支付','0','未支付') pay_status,"
				+ "edp_paytype.pname paytype,"
				+ "edp_status.pname status,"
				+ "decode(eoie.account_status,'','','1','是') account_status,"
				+ "eoipe.is_old,ed.ship_name,ed.ship_mobile reference_phone,eoe.contact_addr,"
				+ "'test' carry_person_mobile,"
				+ "eoe.audit_remark,eoevtl.service_remarks,eoevtl.development_name devlopname,"
				+ "'' sending_type,"
				+ "'' prize,"
				+ "'' special_combo,"
				+ "eoevtl.phone_owner_name "
				+ " from es_order eo "
				+ " left join es_order_ext eoe on eoe.order_id=eo.order_id "
				+ " left join es_order_extvtl eoevtl on eoevtl.order_id=eo.order_id "
				+ " left join es_delivery ed on ed.order_id=eo.order_id "
				+ " left join es_order_items_ext eoie on eoie.order_id=eo.order_id "
				+ " left join es_order_items_prod_ext eoipe on eoipe.order_id=eo.order_id "
				+ " left join es_order_stats_tache eost on eost.order_id=eo.order_id "
				//+ " left join es_dc_public_ext edp_orderfrom on edp_orderfrom.pkey = eoevtl.source_type and edp_orderfrom.stype='source_from' "
				+ " left join es_dc_public_ext edp_status on edp_status.pkey = eo.status and edp_status.stype='DIC_ORDER_STATUS' "
				+ " left join es_dc_public_ext edp_datasrc on edp_datasrc.pkey = eoe.order_from  and edp_datasrc.stype='source_from' "
				+ " left join es_dc_public_ext edp_goodstype on edp_goodstype.pkey = eoie.goods_type  and edp_goodstype.stype='goods_type' "
				+ " left join es_dc_public_ext edp_paytype on edp_paytype.pkey = eoe.pay_type and edp_paytype.stype='pay_type' "
				//+ " left join es_dc_public_ext edp_lan on edp_lan.pkey = eoe.order_city_code and edp_lan.stype='DC_MODE_REGION' "
				+ " left join es_regions er_li on er_li.region_id = eoe.order_city_code and er_li.p_region_id='"+this.cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"'"
				+ " left join es_payment_logs epl on epl.order_id=eo.order_id "
				+ " left join es_order_lock eol on eol.order_id=eo.order_id and eoe.flow_trace_id=eol.tache_code " //订单当前环节和锁定单的环节匹配"
				+ " where eo.source_from = 'ECS' and eoe.tid_time >= to_date('2015-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss')";
	}
	public String getORDER_DATA_LIST_NEW(){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    StringBuilder sbBuilder = new StringBuilder();
	    sbBuilder.append("select  distinct eoe.out_tid, eoe.tid_time, nvl2(eol.lock_user_id, eol.lock_time, '') order_collect_time,");
	    sbBuilder.append("                decode(nvl(ed.sign_status, 0), '1', '已签收', '未签收') as sign_status,  ");                                                                             
	    sbBuilder.append("              eee.route_acceptime as route_acceptime,'' time_difference, ");
	    sbBuilder.append("              (select pname from es_dc_public_ext edp_datasrc where  edp_datasrc.pkey = eoe.order_from and edp_datasrc.stype = 'source_from') as order_from, ");
	    sbBuilder.append("              (select pname from es_dc_public_ext edp_datasrc where  edp_datasrc.pkey = eoe.order_from and edp_datasrc.stype = 'source_from') as order_origin, ");
	    sbBuilder.append("              eoe.spread_channel,  ");
	    sbBuilder.append("              eoevtl.develop_point_code_new as development_code,  ");
	    sbBuilder.append("              eoevtl.development_point_name as development_name, ");
	    sbBuilder.append("              decode(eoe.flow_trace_id, 'B', "); 
	    sbBuilder.append("                     (select tu1.realname from ES_ADMINUSER tu1 where tu1.userid = eost.b_op_id), ");
	    sbBuilder.append("                     'C', ");
	    sbBuilder.append("                     (select tu2.realname from ES_ADMINUSER tu2 where tu2.userid = eost.c_op_id), ");
	    sbBuilder.append("                     'D', ");
	    sbBuilder.append("                     (select tu3.realname from ES_ADMINUSER tu3 where tu3.userid = eost.d_op_id), ");
	    sbBuilder.append("                     'F', ");
	    sbBuilder.append("                     (select tu4.realname  from ES_ADMINUSER tu4 where tu4.userid = eost.f_op_id), ");
	    sbBuilder.append("                     'H', ");
	    sbBuilder.append("                     (select tu5.realname from ES_ADMINUSER tu5 where tu5.userid = eost.h_op_id), ");
	    sbBuilder.append("                     'J', ");
	    sbBuilder.append("                     (select tu6.realname from ES_ADMINUSER tu6 where tu6.userid = eost.j_op_id), ");
	    sbBuilder.append("                     'X', ");
	    sbBuilder.append("                     (select tu7.realname from ES_ADMINUSER tu7 where tu7.userid = eost.x_op_id), ");
	    sbBuilder.append("                     'Y', ");
	    sbBuilder.append("                     (select tu8.realname  from ES_ADMINUSER tu8 where tu8.userid = eost.y_op_id), ");
	    sbBuilder.append("                     'L', ");
	    sbBuilder.append("                     (select tu9.realname  from ES_ADMINUSER tu9 where tu9.userid = eost.l_op_id)) handle_name,  ");
	    sbBuilder.append("              decode(eoe.flow_trace_id, 'B',  eost.b_end_time, ");
	    sbBuilder.append("                     'C', eost.c_end_time, ");
	    sbBuilder.append("                     'D',  eost.d_end_time, 'F', eost.f_end_time, ");
	    sbBuilder.append("                     'H',  eost.h_end_time, 'J', eost.j_end_time,  ");
	    sbBuilder.append("                 'X', eost.x_end_time,'Y', eost.y_end_time, ");
	    sbBuilder.append("                     'L', eost.l_end_time) handle_time,  ");
	    sbBuilder.append("              (select tu1.realname from ES_ADMINUSER tu1 where tu1.userid = eost.f_op_id) f_op_id, eost.f_end_time, to_char(eos.h_end_time, 'yyyy/mm/dd HH24:mi:ss') as ship_end_time,");
	    sbBuilder.append("              ed.logi_no,  eoipe.terminal_num,  eoe.refund_desc, er_li.local_name lan_code, ");
	    sbBuilder.append("              (select pname from es_dc_public_ext edp_goodstype where edp_goodstype.pkey = eoie.goods_type and edp_goodstype.stype = 'goods_type' ) as goods_type, ");
	    sbBuilder.append("              eoevtl.goodsName,  eoie.invoice_no,    eoevtl.plan_title,  ");
	    sbBuilder.append("              '' privilege_combo,  eoie.phone_num,  '' zb_fuka_info,   '' terminal,  ");
	    sbBuilder.append("              eoe.prod_cat_id,  eoipe.contract_month,   eoevtl.pro_realfee,  ");
	    sbBuilder.append("              decode(eo.pay_status, '1', '已支付', '0', '未支付') pay_status,  ");
	    sbBuilder.append("              (select pname from es_dc_public_ext edp_paytype where edp_paytype.pkey = eoe.pay_type  and edp_paytype.stype = 'pay_type') as paytype, ");
	    sbBuilder.append("              (select pname from es_dc_public_ext dep_status where dep_status.pkey = eo.status and dep_status.stype = 'DIC_ORDER_STATUS') as status, ");
	    sbBuilder.append("              decode(eoie.account_status, '', '', '1', '是') account_status,   eoipe.is_old,  ");
	    sbBuilder.append("              ed.ship_name,   ed.ship_mobile as reference_phone, ed.ship_addr as ship_addr, ");
	    sbBuilder.append("              'test' carry_person_mobile,    eoe.audit_remark,   eoevtl.service_remarks,  ");
	    sbBuilder.append("              eoevtl.development_name devlopname,'' sending_type,'' prize,'' special_combo,eoevtl.phone_owner_name , eoevtl.mainnumber as mainnumber, ");
	    sbBuilder.append("                 decode(eori.active_flag, '2', '已激活', '3','已激活', '未激活') as active_flag,");
	    sbBuilder.append("                  case when eori.active_flag in ('2', '3') then  to_char(eos.j_begin_time, 'yyyy/mm/dd HH24:mi:ss') else  null end as active_time  ");
	    sbBuilder.append("  from es_order eo  ");
	    sbBuilder.append("left join es_order_ext eoe on eoe.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_order_extvtl eoevtl on eoevtl.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_delivery ed  on ed.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_order_items_ext eoie  on eoie.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_order_items_prod_ext eoipe on eoipe.order_id = eo.order_id ");
	    sbBuilder.append("left join es_order_stats_tache eost on eost.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_regions er_li on er_li.region_id = eoe.order_city_code  ");
	    sbBuilder.append(" and er_li.p_region_id = '").append(cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")).append("'  ");
	    sbBuilder.append("left join es_payment_logs epl on epl.order_id = eo.order_id  ");
	    sbBuilder.append("left join es_order_lock eol on eol.order_id = eo.order_id  ");
	    sbBuilder.append(" and eoe.flow_trace_id = eol.tache_code   ");
	    sbBuilder.append("left join es_order_realname_info eori on eori.order_id = eo.order_id  ");
	    sbBuilder.append("left join (select eorl.order_id, max(eorl.route_acceptime) route_acceptime  ");
	    sbBuilder.append("             from es_order_route_log eorl  ");
	    sbBuilder.append("             left join es_order eot  on eorl.order_id = eot.order_id  ");
	    sbBuilder.append("            where eorl.op_code = '10' group by eorl.order_id) eee  ");
	    sbBuilder.append("  on eee.order_id = eo.order_id  ");
	    sbBuilder.append("  left join (select ee.order_id,  ");
	    sbBuilder.append("                  max(ee.h_end_time) h_end_time,  ");
	    sbBuilder.append("                  max(ee.j_begin_time) j_begin_time  ");
	    sbBuilder.append("             from es_order_stats_tache ee   ");
	    sbBuilder.append("             left join es_order eot  on ee.order_id = eot.order_id group by ee.order_id) eos  ");
	    sbBuilder.append("  on eos.order_id = eo.order_id  ");
	    sbBuilder.append("  where eo.source_from = 'ECS' and eoe.tid_time >= to_date('2016-03-23 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
	    return sbBuilder.toString();
    }
	public String getINSERT_ORDER_TO_OPEN_ACCOUNT(){
		return "insert into es_queue_open_account(order_id,queue_code,create_time,status,out_tid) values(?,?,sysdate,'0',?)";
	}
	
	public String getUPDATE_ORDER_OPEN_ACCOUNT_STATUS(){
		return "update es_queue_open_account set status=?,modify_time =sysdate where order_id =? ";
	}
}
