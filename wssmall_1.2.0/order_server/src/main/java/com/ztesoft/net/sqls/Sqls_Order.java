package com.ztesoft.net.sqls;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Order extends Sqls {

	public Sqls_Order(){
		//SqlUtil.initSqls(Sqls_Order.class, this , sqls) ;}
	} 
	
	
	// 配置的SQL...
//	final String SERVICE_SQL_SELECT(){return "";}
//	final String SERVICE_SPECIAL_OFFER_SELECT_COUNT(){return "";}
//	final String SERVICE_SQL_INSERT(){return "";}
	public String getqueryTplInstSql(){return "select * from ES_TEMP_INST where TEMP_INST_ID = ?";}
	public String getqueryAttrDefSql(){return "select * from es_attr_def where field_attr_id = ?";}
	
	public String getqueryOuterAttrInstSql(){return "select * from es_outer_attr_inst where order_id = ?";}
	
	//***************** 订单管理 ********************//
	
	//工号绑定
	public String getES_ADMINUSER_LIST(){return "select username,realname,phone_num,source_from from es_adminuser where 1=1";}
	
	//工号绑定
	public String getES_OPERATOR_REL_EXT_LIST(){return "select order_gonghao,ess_emp_id,province,city,source_from from es_operator_rel_ext where 1=1";}
	
	//工号绑定
	public String getES_OPERATOR_REL_EXT_INSERT(){return "insert into es_operator_rel_ext(order_gonghao,ess_emp_id,province,city)values(?,?,?,?)";}
	
	//工号绑定
	public String getADMINUSER_USERID(){return "select userid from es_adminuser where username like ?";}
	
	//工号绑定
	public String getVALIDATE_ORDER_GONGHAO_CITY(){return "select city from es_operator_rel_ext where order_gonghao like ?";}
	
	//工号解绑
	public String getUNBINDING_GONGHAO(){return "delete from es_operator_rel_ext where order_gonghao like ? and city like ?";}
	
	public String getSERVICE_ORDER_PMT_INSERT(){return "insert into es_order_pmt(pmt_id,order_id,pmt_describe)values(?,?,?)";}
	
	public String getSERVICE_ORDER_AMOUNT_UPDATE(){return "update es_order set order_amount=? where order_id=?";}
	
	public String getSERVICE_ORDER_DISCOUNT_UPDATE(){return "update es_order set discount=discount+? where order_id=?";}
	
	public String getSERVICE_ORDER_STATUS_UPDATE(){return "update es_order set status=? where order_id=?";}
	
	public String getCHARGEBACK_APPLY_STATUS_UPDATE(){return "update es_order_batch_archive set notify_zb='1' where zb_order_id = ?";}
	
	public String getCHARGEBACK_APPLY_EXCEPTION_STATUS_UPDATE(){return "update es_order_batch_archive set notify_zb='-1' where zb_order_id = ?";}
	
	public String getCHARGEBACK_RET_STATUS_UPDATE(){return "update es_order_batch_archive set zb_return='1' where zb_order_id = ?";}
	
	public String getSERVICE_ORDER_SQL_SELECT(){return "select * from es_order t where 1=1 ";}
	
	public String getSERVICE_CARD_SELECT_BY_ORDER(){return "select * from es_card  where order_id = ? order by CREATE_DATE asc";}
	
	public String getSERVICE_ORDER_GIFT_INSERT(){return "insert into es_order_gift(order_id,gift_id,gift_name,point,num,shipnum,getmethod)values(?,?,?,?,?,?,?)";}
	
	public String getSERVICE_MEM_POINT_UPDATE(){return "update es_member set point=? where member_id=? ";}
	
	public String getSERVICE_OPTION_SQL_SELECT(){return "select p.pkey,p.pname from es_dc_public p where p.stype='8686' order by codea asc";}
	
	public String getSERVICE_SOURCE_SQL_SELECT(){return "select p.pkey,p.pname from es_dc_public p where p.stype='8022'";}
	
	public String getSERVICE_ORDER_SQL_SELECT_BY_USER(){return "select * from es_order t where query_user_id=? and t.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSERVICE_GROUP_ORDER_SQL_SELECT(){return "select * from es_order t where t.source_from = '" + ManagerUtils.getSourceFrom() + "' and query_user_id is null and query_group_id is not null and query_group_id in("+
														" select gr.group_id from es_order_group_role_rel gr where gr.userid=? and gr.source_from = '" + ManagerUtils.getSourceFrom() + "' union "+
														" select gr.group_id from es_order_group_role_rel gr,es_user_role ur where gr.source_from = ur.source_from and gr.source_from = '" + ManagerUtils.getSourceFrom() + "' and gr.role_id=ur.roleid and ur.userid=?) ";}
	
	
	public String getSERVICE_ORDER_MEMBER_SQL_SELECT(){return "select ou.*,um.uname from ( "+
														" select * from es_order t where query_user_id=? and t.source_from = '" + ManagerUtils.getSourceFrom() +"'"+
														" union all "+
														" select * from es_order t where t.source_from = '" + ManagerUtils.getSourceFrom() + "' and query_user_id is null and query_group_id is not null and query_group_id in( "+
														" select gr.group_id from es_order_group_role_rel gr where gr.userid=? and gr.source_from = '" + ManagerUtils.getSourceFrom() + "' union  "+
														" select gr.group_id from es_order_group_role_rel gr,es_user_role ur where gr.source_from = ur.source_from and ur.source_from = '" + ManagerUtils.getSourceFrom() + "' and gr.role_id=ur.roleid and ur.userid=?) "+ 
														" ) ou left join es_member um on(ou.member_id=um.member_id and um.source_from = '" + ManagerUtils.getSourceFrom() + "')"	;}
	
	public String getSERVICE_GROUP_ORDER_SQL_COUNT(){return "select count(*) from ( "+
														" select * from es_order t where query_user_id=? and t.source_from = '" + ManagerUtils.getSourceFrom() + "' "+
														" union all "+
														" select * from es_order t where t.source_from = '" + ManagerUtils.getSourceFrom() + "' and query_user_id is null and query_group_id is not null and query_group_id in( "+
														" select gr.group_id from es_order_group_role_rel gr where gr.userid=? and gr.source_from = '" + ManagerUtils.getSourceFrom() + "' union  "+
														" select gr.group_id from es_order_group_role_rel gr,es_user_role ur where gr.role_id=ur.roleid and ur.userid=? and gr.source_from = ur.source_from and ur.source_from = '" + ManagerUtils.getSourceFrom() + "') ) od "	;}
	
	public String getSERVICE_ORDER_SQL_COUNT(){return "select count(*) all_count from es_order t where 1=1 ";}
	
	public String getSERVICE_USER_ORDER_STATE_SQL_SELECT(){return "select t.*,(select lan_name from es_lan lan where lan.source_from = t.source_from and lan.lan_id = t.lan_id ) lan_name, " +
			"	(select state from es_order_audit where source_from = '" + ManagerUtils.getSourceFrom() + "' and order_id = t.order_id and audit_type = '00A' #rowlimit ) audit_state," +
			"(select uname from es_member aduser where aduser.member_id = t.member_id and aduser.source_from = t.source_from) user_name  from es_order t where t.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}

	public String getSERVICE_ORDER_SELECT_BY_ID(){return "select * from es_order where order_id=?";}

	public String getSERVICE_GROUP_SELECT_BY_ORDER (){return "select gr.* from es_order_group_role_rel gr where gr.userid=? and gr.group_id=? and gr.source_from = '" + ManagerUtils.getSourceFrom() + "' "+
														" union all "+
														" select gr.* from es_order_group_role_rel gr,es_user_role ur where gr.role_id=ur.roleid and ur.userid=? " +
														" and gr.group_id=? and gr.source_from = ur.source_from and ur.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_ORDER_SELECT_BY_SN(){return "select * from es_order where sn=?";}
	
	public String getSERVICE_GOODS_ITEMS_SELECT(){return "select items.*, g.image_default as image, p.sn as sn, (select sum(ai.num) from es_order_apply_item ai,es_order_apply oa where ai.apply_id=oa.order_apply_id and ai.item_type='4'and oa.apply_state!='2' and ai.old_order_item_id=items.item_id) as returndnum "+
														" from es_order_items items "+
														" left join es_goods g on (g.goods_id = items.goods_id  "+ManagerUtils.apSFromCond("g")+" and items.source_from=g.source_from ) "+
														" left join es_product p on (p.product_id = items.spec_id   "+ManagerUtils.apSFromCond("p")+" and items.source_from=p.source_from ) "+
														" where items.order_id = ? and items.source_from=?";}
	
	public String getSERVICE_GOODS_ITEMS_HIS_SELECT(){return "select items.*, g.image_default as image, p.sn as sn, (select sum(ai.num) from es_order_apply_item ai,es_order_apply oa where ai.apply_id=oa.order_apply_id and ai.item_type='4'and oa.apply_state!='2' and ai.old_order_item_id=items.item_id) as returndnum "+
			" from es_order_items_his items "+
			" left join es_goods g on (g.goods_id = items.goods_id  "+ManagerUtils.apSFromCond("g")+" and items.source_from=g.source_from ) "+
			" left join es_product p on (p.product_id = items.spec_id   "+ManagerUtils.apSFromCond("p")+" and items.source_from=p.source_from ) "+
			" where items.order_id = ? and items.source_from=?";}
	public String getSERVICE_COMMENTS_SELECT_BY_ORDER(){return "select * from es_comments where order_id=? and object_id=?";}
	
	public String getSERVICE_GIFT_ITEMS_SELECT(){return "select * from es_order_gift where order_id=?";}
	
	public String getSERVICE_ORDER_LOGS_SELECT(){return "select * from es_order_log where order_id=? order by op_time asc";}
	
	public String getSERVICE_ORDER_DELETE(){return "delete from es_order where 1=1 ";}
	
	public String getSERVICE_ORDER_ITEM_DELETE(){return "delete from es_order_items where 1=1 ";}
	
	public String getSERVICE_ORDER_LOG_DELETE(){return "delete from es_order_log where 1=1 ";}
	
	public String getSERVICE_PAYMENT_LOG_DELETE(){return "delete from es_payment_logs where 1=1 ";} 
	
	public String getSERVICE_DELIVERY_ITEM_DELETE(){return "delete from es_delivery_item where 1=1 ";}
	
	public String getSERVICE_DELIVERY_DELETE(){return "delete from es_delivery where 1=1 ";}
	
	public String getSERVICE_ORDER_UPDATE(){return "update es_order set disabled = ? where 1=1 ";}
	
	public String getSERVICE_ORDER_SELECT_BY_MEM_ID(){return "select * from es_order where member_id = ? order by create_time desc";}
	
	public String getSERVICE_ORDER_COUNT_BY_MEM_ID(){return "select count(0) from es_order where member_id = ?";}
	
	public String getSERVICE_MEM_PAYMONEY_SUM(){return "select sum(paymoney) from es_order where member_id = ?";}
	
	public String getSERVICE_ORDER_ITEM_BY_ORDER_ID(){return "select * from es_order_items where order_id=? and length(addon)>0";}
	
	public String getSERVICE_ORDER_STATUE_COUNT(){return "select count(0) num,status  from es_order t where disabled = 0 ";}
	
	public String getSERVICE_ORDER_EXPORT_SELECT(){return "select * from es_order where disabled=0 ";}
	
	public String getSERVICE_STAFFNO_BY_SESSION_ID(){return "select distinct g.staff_no from " + //and g.source_from = p.source_from and c.source_from = p.source_from and
			"es_goods g,es_product p,es_cart c where g.goods_id=p.goods_id and p.product_id=c.product_id " +
			"and (c.is_checked=1 or c.is_checked is null) and c.session_id=? and g.source_from = p.source_from  and g.source_from = '" + ManagerUtils.getSourceFrom() + "'  "+ManagerUtils.apSFromCond("p")+"";}

	public String getSERVICE_GOODS_SELECT_BY_STAFFNO(){return "select distinct g.goods_id from " +
			" es_goods g,es_product p,es_cart c where g.goods_id=p.goods_id and p.product_id=c.product_id and g.source_from = p.source_from  and " +
			"(c.is_checked=1 or c.is_checked is null) and g.staff_no=? and c.session_id = ? and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";} //
	//and g.source_from = p.source_from " + "and p.source_from = c.source_from and
	public String getSERVICE_ORDER_SELECT_BY_BATCH_ID(){return "select * from es_order where batch_id=?";}

	public String getSERVICE_ORDER_COUNT(){return "select count(*) as totalAmount from es_order t where 1=1 and t.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSERVICE_DELIVERY_PRICE_UPDATE(){return "update es_order o set o.shipping_amount=?,o.order_amount=o.order_amount-o.shipping_amount+? where o.order_id=?";}

	public String getSERVICE_ORDER_ITEM_BY_ITEM_ID(){return "select * from es_order_items where item_id=?";}

	public String getSERVICE_GOODS_SALES_COUNT(){return "select sum(t.ship_num) from es_order_items t where t.goods_id=? and t.lv_id=?";}
	
	public String getSERVICE_SALES_MONEY_SUM(){return "select sum(t.ship_num*gl.price) as price from " +
			"es_order_items t,es_goods_lv_price gl where t.goods_id=gl.goodsid and t.lv_id=gl.lvid and t.goods_id=? " +
			"and t.lv_id=? and t.source_from = gl.source_from and gl.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_CONFIRM_STATUE_UPDAATE(){return "update es_order t set t.confirm_status=?,t.last_update="+DBTUtil.current()+" where t.order_id=?";}
	
	public String getSERVICE_PAYMENT_LOGS_SELECT(){return "select * from es_payment_logs l where l.order_id=?";}

	public String getSERVICE_NOT_PAYMENT_LOGS_SELECT(){return "select p.* from es_payment_logs p,es_order o where " +
			"p.order_id=o.order_id and p.status="+OrderStatus.PAY_STATUS_NOT_CONFIRM  + " and p.source_from = o.source_from and o.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	public String getSERVICE_PAYMENT_LOGS_BY_TRANS_ID(){return "select * from es_payment_logs where transaction_id=?";}
	
	public String getSERVICE_SUCC_PAYMENT_LOGS_SELECT(){return "select t.*,t.rowid from es_payment_logs t where t.order_id=? and t.type=1 and t.status=1";}
	
	public String getSERVICE_ORDER_STATUE_UPDATE(){return "update es_order set status=?,pay_status=?,paymoney=paymoney-? where order_id=?";}

	//***********  order_meta *********************/
	
	public String getSERVICE_ORDER_META_SELECT(){return "select * from es_order_meta where 1=1 ";}
	
	//*********** 云卡调拨 ************************//
	
	public String getSERVICE_ACC_NBR_COUNT(){return "select count(*) from  es_phone_no where 1=1 ";}
	
	public String getSERVICE_ACC_NBR_SELECT(){return "select * from es_phone_no a where 1=1 ";}
	
	public String getSERVICE_TRANSFER_COL_SELECT(){return "select num_id, num_type, num_code, area_code, min_consume, min_month, pre_cash, cust_name, " +
								"id_card_code, state, create_date, state_date, acc_level, memo, comments, num_flag, acc_type, " +
								"to_userid, from_userid, order_id, use_type, iccid, imsi, contact_phone, contact_addr, sec_order_id " + 
								"code_head_name,choose_date,modify_date,take_date ";}
	
	public String getSERVICE_TRANSFER_COUNT(){return "select count(*) ";}
	
	public String getSERVICE_COLUMN_SELECT(){return "select distinct area_code  ";}
	
	public String getSERVICE_TRANSFER_NBR_COL_SELECT(){return "select num_id, num_type, num_code, area_code, min_consume, min_month, pre_cash, cust_name, " +
											"id_card_code, state, create_date, state_date, acc_level, memo, comments, num_flag, acc_type, " +
											"to_userid, from_userid, order_id, use_type, iccid, imsi, contact_phone, contact_addr, sec_order_id ";}

	public String getSERVICE_PHONE_NO_UPDATE_BY_STATE(){return "update es_phone_no  cf set order_id = ?,ship_state=?   where state="+Consts.CARD_INFO_STATE_0;}

	public String getSERVICE_PHONE_NO_UPDATE(){return "update es_phone_no  cf set  ";}
	
	public String getSERVICE_PHONE_NO_RESET_BY_ORD_ID(){return "update es_phone_no set ship_state ='', to_userid ='',from_userid='',first_userid='',first_orderid='',order_id ='',deal_time='' where order_id = ? and state=0";}
	
	public String getSERVICE_PHONE_NO_UPDATE_BY_ORD_ID(){return "update es_phone_no set ship_state ='"+Consts.SHIP_STATE_5+"',order_id=first_orderid  where order_id = ?  and state=0 ";}
	
	public String getSERVICE_PHONE_NO_RESET_BY_SHIP_STATE(){return "update es_phone_no set ship_state ='', to_userid ='',from_userid='',first_userid='',first_orderid='',order_id ='',deal_time='' where order_id = ? and ship_state <>? and state=0  ";}
	
	public String getSERVICE_PHONE_NO_UPDATE_BY_SHIP_STATE(){return "update es_phone_no set ship_state ='"+Consts.SHIP_STATE_5+"',order_id=''  where order_id = ? and ship_state =? and state=0 ";}
	
	public String getSERVICE_SHIP_STATE_UPDATE(){return "update es_phone_no set ship_state =? where order_id = ? ";}
	
	public String getSERVICE_LAN_ID_SELECT(){return "select l.lan_id from es_lan l where l.source_from = '" + ManagerUtils.getSourceFrom() + "' exists ("
							+ "select 1 area_code from es_phone_no t where t.source_from = l.source_from and t.area_code = l.lan_code and t.num_code between #begin and #end ) ";}

	public String getSERVICE_PHONE_NO_INSERT(){return "insert into es_phone_no (num_id,num_code," +
																	"area_code,min_consume,min_month,create_date,state,batch_id,import_userid,use_type,acc_level,pre_cash) values " +
																	"(?,? ,? ,? ,? ,? ,? ,?, ?, ? ,? ,?)";}

	public String getSERVICE_CLOUD_INFO_UPDATE(){return "update es_cloud_info  cf set order_id = ?,ship_state =?   where offer_id = ? and pay_money = ?";}
	
	public String getSERVICE_CLOUD_ACCEPT_UPDATE(){return "update es_cloud_info  cf set  from_userid = ?, to_userid = ?, #updatecol" +
		  															" ,deal_time="+DBTUtil.current()+",lan_name = (select lan_name from es_lan lan where lan.lan_id =cf.lan_id ) where  order_id = ? ";}
	
	public String getSERVICE_CLOUD_INFO_SELECT(){return "select * from es_cloud_info where 1=1 " ;}
	
	public String getSERVICE_TRANS_PRICE_SUM(){return "select sum(pay_money) from es_cloud_info where 1=1 ";}
	
	public String getSERVICE_DISTINCT_LAN_SELECT(){return "select distinct lan_id from es_cloud_info where  1=1  ";}
	
	public String getSERVICE_LAN_SELECT(){return "select lan_id from es_cloud_info where   1=1  ";}
	
	public String getSERVICE_PAYMONEY_SELECT(){return "select distinct pay_money from es_cloud_info where  phone_num between  ? and  ? ";}
	
	public String getSERVICE_ACC_NBR_INFO_SELECT(){return "select lan_id, phone_num from es_cloud_info a where a.ship_state = ? and a.order_id = ?";}
	
	public String getSERVICE_CLOUD_SELECT_BY_ACCNBR(){return "select * from es_cloud_info where phone_num = ?";}
	
	public String getSERVICE_CLOUD_SHIP_STATE_UPDATE(){return "update es_cloud_info set ship_state =? where order_id = ? ";}
	
	public String getSERVICE_CLOUD_RESET_UPDATE(){return "update es_cloud_info set ship_state ='', to_userid ='',from_userid='',first_userid='',first_orderid='',order_id ='',deal_time='' where order_id = ? and state=0";}
	
	public String getSERVICE_SHIP_STATE_FINISH_UPDATE(){return "update es_cloud_info set ship_state ='5',order_id=first_orderid  where order_id = ?  and state=0 ";}
	
	public String getSERVICE_CLOUD_RESET_BY_ORDER(){return "update es_cloud_info set ship_state ='', to_userid ='',from_userid='',first_userid='',first_orderid='',order_id ='' where order_id = ? and ship_state <>? and state=0  ";}
	
	public String getSERVICE_CLOUD_RELEASE_UPDATE(){return "update es_cloud_info set ship_state ='"+ Consts.SHIP_STATE_5+ "',order_id=''  where order_id = ? and ship_state =? and state=0 ";}
	
	public String getSERVICE_COLUD_DISABLED_UPDATE(){return "update es_cloud_info set ship_state ='', state='"
																				+ Consts.CLOUD_INFO_STATE_3
																				+ "', to_userid ='',from_userid='',first_userid='',first_orderid='',order_id ='' where order_id = ? and ship_state <>? and state=0  ";}
	
	public String getSERVICE_CLOUD_DELI_UPDATE(){return "update es_cloud_info set ship_state ='"
																				+ Consts.SHIP_STATE_5
																				+ "',order_id=''  where order_id = ? and ship_state =? and state=0 ";}
	
	public String getSERVICE_CLOUD_DELI_UPDATE_BY_ORDER(){return "update es_cloud_info set ship_state ='"+ Consts.SHIP_STATE_5 + "' where order_id = ? ";}
	
	public String getSERVICE_CLOUD_COUNT(){return "select count(*) from  es_cloud_info where 1=1 ";}
	
	public String getSERVICE_CLOUD_SELECT(){return "select cloud_id, crm_order_id, offer_name, taochan_name, "
				+ "phone_num, uim, batch_id, batch_oper_id, order_id, state,deal_time ,pay_money,"
				+ "create_date, to_userid, from_userid, lan_name, lan_id from es_cloud_info where 1 = 1 ";}
	
	public String getSERVICE_CLOUD_SELECT_BY_SHIP_STATE(){return "select * from es_cloud_info where order_id = ? and ship_state in(?,?,?,?) ";}
	
	public String getSERVICE_CLOUD_IMPORT_INSERT(){return "insert into es_cloud_info "
																				+ "(cloud_id, offer_name, taochan_name, phone_num, pay_money, uim, lan_id, offer_id, create_date, deal_time, "
																				+ " state, ship_state, batch_id, from_userid, to_userid, lan_name, username, import_userid, first_userid, "
																				+ " order_id, first_orderid, org_id, org_name, FROM_SOURCE) "  // add FROM_SOURCE column
																				+ " values "
																				+ "(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";}
	
	public String getSERVICE_CLOUD_INSERT(){return "insert into es_cloud_info (cloud_id,offer_name,taochan_name,"
																				+ "phone_num,pay_money,uim,lan_id,offer_id,create_date,state,batch_id,import_userid) values (?,? ,? ,? ,? ,? ,? ,? ,? ,? ,?,? )";}
	
	public String getSERVICE_PAY_UPDATE(){return "update es_order set status=?,pay_status=?,paymoney=paymoney+? where order_id=?";}

	public String getSERVICE_POINT_UPDATE(){return "update es_member set point=point+? where member_id=?";}
	public String getSERVICE_ADVANCE_UPDATE(){return "update es_member set advance=advance-? where member_id=?";}

	public String getSERVICE_ORDER_PAY_STATUS_UPDATE(){return "update es_order set status=?,pay_status=?,paymoney=paymoney-? where order_id=?";}

	public String getSERVICE_ADVANCE_ADD_UPDATE(){return "update es_member set advance=advance+? where member_id=?";}

	public String getSERVICE_SHIP_STATUS_UPDATE(){return "update es_order set status=?,ship_status=?,ship_time="+DBTUtil.current()+" where order_id=?";}
	public String getSERVICE_SHIP_STATUS_UPDATE_NEW(){return "update es_order set status=?,ship_status=?,ship_time="+DBTUtil.current();}

	public String getSERVICE_SHIP_NUM_UPDATE(){return "update es_order_items set ship_num=ship_num-? where order_id=? and spec_id=?";}

	public String getSERVICE_GOODS_STORE_UPDATE(){return "update es_goods set store=store+? where goods_id=?";}

	public String getSERVICE_PRODUCT_STORE_UPDATE(){return "update es_product set store=store+? where product_id=?";}

	public String getSERVICE_RETURN_SHIPNUM_UPDATE(){return "update es_order_gift set shipnum=shipnum-? where order_id=? and gift_id=?";}

	public String getSERVICE_REPERTORY_UPDATE(){return "update es_freeoffer set repertory=repertory+? where fo_id=?";}

	public String getSERVICE_SHIPNUM_ADD_UPDATE(){return "update es_order_items set ship_num=ship_num+? where order_id=? and spec_id=?";}

	public String getSERVICE_GOODS_STORE_MINUS_UPDATE(){return "update es_goods set store=store-? where goods_id=?";}

	public String getSERVICE_PRODUCT_STORE_MINUS_UPDATE(){return "update es_product set store=store-? where product_id=?";}

	public String getSERVICE_BUY_NUM_UPDATE(){return "update es_goods set buy_count=buy_count+? where goods_id=?";}

	public String getSERVICE_GIFT_SHIPNUM_UPDATE(){return "update es_order_gift set shipnum=shipnum+? where order_id=? and gift_id=?";}

	public String getSERVICE_REPERTORY_MINUS_UPDATE(){return "update es_freeoffer set repertory=repertory-? where fo_id=?";}

	public String getSERVICE_ORD_ITEM_STATE_UPDATE(){return "update es_order_items set state=? where order_id=? ";}

	public String getSERVICE_ORD_SHIP_STATUS_UPDATE(){return "update es_order set status=?, ship_status=? where order_id=?";}

	public String getSERVICE_GIFT_NOT_SHIP_SELECT(){return "select * from es_order_gift where order_id=? and shipnum<num";}

	public String getSERVICE_ORDER_NOT_SHIP_SELECT(){return "select oi.*,p.store,p.sn from es_order_items  oi " +
			"left join es_product p on oi.spec_id = p.product_id and p.source_from = oi.source_from where oi.source_from = '" + ManagerUtils.getSourceFrom() + "' and order_id=? and oi.ship_num<oi.num";}

	public String getSERVICE_GOODS_SHIP_SELECT(){return "select oi.*,p.store,p.sn from es_order_items  oi " +
			"left join es_product p on oi.spec_id = p.product_id and p.source_from = oi.source_from where oi.source_from = '" + ManagerUtils.getSourceFrom() + "' and order_id=? and ship_num>0";}

	public String getSERVICE_GIFTITEM_SHIP_SELECT(){return "select * from es_order_gift where order_id=? and shipnum!=0";}

	//public String getSERVICE_PRODUCT_SELECT(){return "select * from es_product where product_id in (select product_id from es_order_items where order_id=?)";}
	public String getSERVICE_PRODUCT_SELECT(){return "select p.* from es_product p,es_order_items i where " +
			"p.product_id=i.spec_id and i.order_id=? and p.source_from = i.source_from and i.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	//************* AREA **************//
	public String getSERVICE_AREA_DELETE(){return "delete from es_dly_area where 1=1 ";}
	
	public String getSERVICE_AREA_SELECT(){return "select * from es_dly_area ";}
	
	public String getSERVICE_AREA_UPDATE(){return "update es_dly_area set name = ? where area_id=?";}
	
	//************* CART *************//
	public String getSERVICE_CART_ITEM_COUNT(){return "select count(0) from es_cart where session_id =?";} 
	public String getSERVICE_GOODS_SELECT(){return "select g.cat_id as catid,g.goods_id,g.image_default, c.name , '' spec_id," +
			"p.specs  ,g.mktprice,g.point,g.staff_no,g.sn,g.ctn,c.member_lv_id," +
			"p.product_id,c.price,c.cart_id as cart_id,g.image_file," +
			"c.num as num,c.itemtype,c.addon,case is_checked when 0 then 0 else 1 end is_checked from es_cart c,es_product p,es_goods g "+
			"where c.itemtype=0 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=? " +
			"and c.source_from = p.source_from and p.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	
	public String getSERVICE_PGK_SELECT(){return "select g.sn,g.goods_id,g.image_default,c.name,g.mktprice,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,g.staff_no,g.sn,g.ctn,c.member_lv_id,g.image_file  from "
							+ " es_cart c, es_product p, es_goods g "+
							"where c.itemtype=1 and c.product_id=p.product_id and " +
							"p.goods_id= g.goods_id and c.session_id=? and c.source_from = p.source_from and " +
							"p.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	public String getSERVICE_GIFT_SELECT(){return "select f.fo_id as goods_id,f.list_thumb image_default,f.fo_name as name, f.price as mktprice, f.score as point,f.fo_id as product_id,f.score as price," +
								" f.limit_purchases as limitnum,c.cart_id as cart_id,c.num as num,c.itemtype,g.staff_no,g.sn,g.ctn,c.member_lv_id,g.image_file  from "
								+ " es_cart c, es_freeoffer f, es_product p, es_goods g" +
								" where c.itemtype=2 and c.product_id=f.fo_id and c.product_id=p.product_id and " +
								"p.goods_id=g.goods_id and c.session_id=? and c.source_from = f.source_from " +
								"and f.source_from = p.source_from and p.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_GROUP_BUY_SELECT(){return "select g.goods_id,g.image_default, c.name , g.mktprice,g.point,gb.groupid as product_id,c.price,c.cart_id as cart_id," +
					"c.num as num,c.itemtype,c.addon,g.staff_no,g.sn,g.ctn,c.member_lv_id,g.image_file  from "+ 
					" es_cart c, es_goods g, es_group_buy gb "+
					"where c.itemtype=3 and c.product_id = gb.groupid and gb.goodsid= g.goods_id   " +
					"and c.session_id=? and c.source_from = g.source_from and g.source_from = gb.source_from and gb.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_CART_DELETE(){return "delete from es_cart where session_id=?";}
	
	public String getSERVICE_CART_DELETE_BY_CART(){return "delete from es_cart where session_id=? and cart_id=?";}
	
	public String getSERVICE_NUM_UPDATE(){return "update es_cart set num=? where session_id =? and cart_id=?";}
	
	public String getSERVICE_CHECKED_UPDATE(){return "update es_cart set is_checked=? where session_id =?";}
	
	public String getSERVICE_CHECKED_UPDATE_BY_CART(){return "update es_cart set is_checked=? where session_id =? and cart_id=?";}
	
	public String getSERVICE_TOTAL_GOODS_COUNT(){return "select sum( c.price * c.num ) as num from es_cart c where  c.session_id=? and c.itemtype=0 ";}
	
	public String getSERVICE_TOTAL_PGK_COUNT(){return "select sum( c.price * c.num ) as num from es_cart c where c.itemtype=1  and c.session_id=? ";}
	
	public String getSERVICE_GROUP_BUY_TOTAL_COUNT(){return "select sum( c.price * c.num ) as num from es_cart c where c.itemtype="+OrderStatus.ITEM_TYPE_3+"  and c.session_id=? ";}
	
	public String getSERVICE_LIMIMT_BUY_TOTAL_COUNT(){return "select sum( c.price * c.num ) as num from es_cart c where c.itemtype="+OrderStatus.ITEM_TYPE_4+"  and c.session_id=? ";} //秒杀
	
	
	public String getSERVICE_MEM_POINT_COUNT(){return "select c.*, g.goods_id, g.point from  es_cart c, es_goods g,  es_product p " +
									"where p.product_id = c.product_id and g.goods_id = p.goods_id and c.session_id = ? and " +
									" c.source_from = g.source_from and g.source_from = p.source_from and p.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_POINT_COUNT(){return "select  sum(g.point * c.num) from es_cart c, es_product p, es_goods g "+
						"where (c.itemtype=0  or c.itemtype=1)  and c.product_id=p.product_id and p.goods_id= g.goods_id and " +
						"c.session_id=? and c.source_from = g.source_from and g.source_from = p.source_from and p.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_GOODS_WEIGHT_COUNT(){return "select sum( c.weight * c.num )  from es_cart c where c.session_id=?";}
	
	public String getSERVICE_GOODS_WEIGHT_COUNT_2(){return "select sum( c.weight * c.num )  from es_cart c, es_product p, es_goods g where " +
			"c.product_id=p.product_id and p.goods_id=g.goods_id and g.staff_no=? and c.session_id=? and" +
			" c.source_from = g.source_from and g.source_from = p.source_from and p.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_MEM_POINT_COUNT_2(){return "select c.*, g.goods_id, g.point from  es_cart c, es_goods g, es_product p " +
			"where p.product_id = c.product_id and g.goods_id = p.goods_id and c.session_id = ? and g.staff_no=? " +
			" and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_POINT_COUNT_2(){return "select  sum(g.point * c.num) from es_cart c, es_product p, es_goods g where " +
			"(c.itemtype=0  or c.itemtype=1)  and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=? and g.staff_no=?" +
			" and c.source_from = g.source_from and g.source_from = p.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_GOODS_TOTAL_COUNT (){return "select sum( c.price * c.num ) as num from es_cart c , es_product p, es_goods g " +
			"where c.product_id=p.product_id and p.goods_id=g.goods_id and g.staff_no=? and c.session_id=? and c.itemtype=0 " +
			"and c.source_from = g.source_from and g.source_from = p.source_from and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_GOODS_LIST_SELECT(){return "select g.cat_id as catid,g.goods_id,g.image_default, c.name ,  " +
			"p.specs  ,g.mktprice,g.point,g.staff_no,g.sn,g.ctn,c.spec_id,c.member_lv_id," +
			"'' agent_name," +
			"p.product_id,c.price,c.cart_id as cart_id,g.image_file," +
			"c.num as num,c.itemtype,c.addon,case is_checked when 0 then 0 else 1 end is_checked from es_cart c, es_product p, es_goods g "+
			"where c.itemtype=0 and (c.is_checked=1 or c.is_checked is null) and c.product_id=p.product_id and p.goods_id= g.goods_id and g.source_from = p.source_from and c.session_id=? and g.staff_no=? and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}

	public String getSERVICE_PGK_LIST_SELECT(){return "select g.sn,g.goods_id,g.image_default,c.name,g.mktprice,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,g.staff_no,g.sn,g.image_file  from  es_cart c, es_product p,es_goods g "+
								"where c.itemtype=1 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=? and g.staff_no=?  and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_GIFT_LIST_SELECT(){return "select f.fo_id as goods_id,f.list_thumb image_default,c.spec_id,f.fo_name as name, f.price as mktprice, f.score as point,c.product_id,f.score as price,f.limit_purchases as limitnum,c.cart_id as cart_id,c.num as num,c.itemtype,g.staff_no,g.sn,g.image_file  from "+ 
																	" es_cart c, es_freeoffer f, es_product p, es_goods g "+
																	" where c.itemtype=2 and c.spec_id=f.fo_id and c.product_id=p.product_id and g.source_from = p.source_from and p.goods_id=g.goods_id and c.session_id=? and g.staff_no=? and g.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_GROUP_BUY_LIST_SELECT(){return "select g.goods_id,g.image_default, c.name ,c.member_lv_id,c.spec_id, g.mktprice,g.point,c.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon,g.staff_no,g.sn,g.image_file  from "+ 
			" es_cart c, es_goods g, es_group_buy gb "+
			"where c.itemtype=3 and c.spec_id = gb.groupid and gb.goodsid= g.goods_id and c.session_id=? " +
			"and g.staff_no=? and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_LIMIT_LIST_SELECT(){return "select g.goods_id,g.image_default, c.name ,c.member_lv_id,c.spec_id, g.mktprice,g.point,c.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon,g.staff_no,g.sn,g.image_file  from "+ 
			" es_cart c, es_goods g, es_limitbuy_goods gb "+
			"where c.itemtype="+OrderStatus.ITEM_TYPE_4+" and c.spec_id = gb.limitbuyid and gb.goodsid= g.goods_id and c.session_id=? " +
			"and g.staff_no=? and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	
	public String getSERVICE_MORE_STAFF_NO_SELECT(){return "select count(distinct g.staff_no) as countNo from " +
			"es_cart c,es_product p,es_goods g where c.product_id=p.product_id and p.goods_id=g.goods_id and " +
			"(c.is_checked=1 or c.is_checked is null) and c.session_id=? and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	//************* 资料返档 **************//
	public String getSERVICE_CUST_RET_COUNT(){return "select count(1) from es_cust_returned";}
	
	public String getSERVICE_CUST_RET_SELECT(){return "select t.order_id,t.accept_id,t.offer_name,t.acc_nbr,t.cust_name,t.certi_type,t.certi_number,t.terminal_code,"
				+ "t.create_date,#dateformat status_date,"
				+ "t.state,t.terminal_name,t.fee,t.crm_order_id,t.comments,"
				+ "t.batch_id,t.cust_pwd,t.lan_id,t.cust_addr,t.ship_name,t.ship_tel,t.ship_addr,ea.realname "
				+ "from ES_CUST_RETURNED t left join es_adminuser ea on t.import_userid = ea.userid and ea.source_from = t.source_from where t.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	//************ 卡处理SQL ***********//
	public String getSERVICE_CARD_FAIL_SELECT(){return "select a.*,(case when b.deal_count is null then 0 else b.deal_count end) deal_count from es_order a left join " +
							"es_deal_log b on a.order_id = b.deal_order_id and b.source_from = a.source_from where a.type_code in ('RECHARGE_CARD','CARD') " +
							"and a.bill_flag = 'T' and a.accept_status  = '"+OrderStatus.ACCEPT_STATUS_4+"' and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	public String getSERVICE_DEAL_LOG_SELECT(){return "select * from es_deal_log a where a.deal_order_id = ?";}
	
	public String getSERVICE_DELIVERY_FLOW_SELECT(){return "select t.* from es_delivery_flow t where t.delivery_id = ? order by t.create_time asc";}

	public String getSERVICE_LOGI_COMPANY_SELECT(){return "select * from es_logi_company where id=? ";}

	public String getSERVICE_DELIVERY_SELECT(){return "select t.*,o.order_amount,o.goods_amount,o.shipping_amount,o.create_type,a.audit_status,a.pru_order_name from " +
			"es_delivery t,es_order o,es_warehouse_purorder a " +
			"where t.order_id=o.order_id and o.batch_id=a.z_order_id and t.ship_status=-1 and " +
			"t.source_from = o.source_from and o.source_from = a.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	public String getSERVICE_DELIVERY_COUNT(){return "select count(*) from es_delivery t,es_order o,es_warehouse_purorder a where " +
			"t.order_id=o.order_id and o.batch_id=a.z_order_id and t.ship_status=-1 and " +
			"t.source_from = o.source_from and o.source_from = a.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}

	public String getSERVICE_DELIVERY_SELECT_BY_ID(){return "select * from es_delivery where delivery_id=?";}
	
	public String getSERVICE_DELIVERY_SELECT_BY_ORDER(){return "select t.* from es_delivery t where t.order_id=?";}
	
	public String getSERVICE_DELIVERY_SELECT_BY_STATUE(){return "select * from es_delivery where order_id=? and ship_status=-1";}
	
	public String getSERVICE_DELIVERY_ITEM_SELECT(){return "select t.* from es_delivery_item t where delivery_id=? and t.source_from=?";}
	
	public String getSERVICE_DELIVERY_ITEM_GOODS_SELECT(){return "select * from es_delivery_item i,es_goods g where " +
			"i.goods_id=g.goods_id and delivery_id=? and i.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_DLY_CENTER_DELETE(){return "update es_dly_center set disabled = 'true' where 1=1 ";}
	
	public String getSERVICE_DLY_CENTER_SELECT(){return "select * from es_dly_center where disabled = 'false'";}
	
	public String getSERVICE_DLY_CENTER_SELECT_BY_ID(){return "select * from es_dly_center where dly_center_id = ?";}
	
	public String getSERVICE_DLY_ADDR_SELECT(){return "select t.* from es_dly_address t where t.region_id=?";}
	
	public String getSERVICE_DLY_TYPE_AREA_DELETE(){return "delete from es_dly_type_area where 1=1 ";}
	
	public String getSERVICE_DLY_TYPE_DELETE(){return "delete from es_dly_type where 1=1 ";}
	
	public String getSERVICE_DLY_TYPE_SELECT(){return "select * from es_dly_type where 1=1 ";}
	
	public String getSERVICE_DLY_TYPE_SELECT_ORDER(){return "select * from es_dly_type where 1=1 order by ordernum";}
	
	public String getSERVICE_DLY_TYPE_AREA_SELECT(){return "select * from es_dly_type_area where 1=1 ";}
	
	public String getSERVICE_DLY_AREA_PRICE_SELECT(){return "select a.* ,ta.price price from es_dly_area a " +
			"left join (select  * from es_dly_type_area where type_id=?)  ta  on ( a.area_id  = ta.area_id and ta.source_from = a.source_from) where a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_DLY_TYPE_SELECT_5(){return "select * from es_dly_type where type_id=5 order by ordernum";}
	
	public String getSERVICE_DLY_TYPE_DELETE_BY_ID(){return "delete from es_dly_type_area where type_id=?";}
	
	public String getSERVICE_DLY_TYPE_AREA_DEL_BY_ID(){return "delete from es_dly_type_area where type_id=?";}
	
	public String getSERVICE_GNOTIFY_SELECT(){return "select a.*, b.image_default image,b.store store, b.name name, b.price price, b.mktprice mktprice,b.cat_id cat_id from es_gnotify a " +
			"left join es_goods b on b.goods_id = a.goods_id and a.source_from = b.source_from where a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_GNOIFY_DELETE(){return "delete from es_gnotify where gnotify_id = ?";}
	
	public String getSERVICE_LOGI_COMP_DELETE(){return "delete from es_logi_company where 1=1 ";}
	
	public String getSERVICE_LOGI_COMP_DELETE_BY_ID(){return "select * from es_logi_company where id=?";}
	
	public String getSERVICE_LOGI_COMP_SELECT(){return "select * from es_logi_company";}
	
	public String getSERVICE_ORDER_ACCEPT_SELECT_BY_ID(){return "select t.*,t.rowid from ES_ORDER_ACCEPT t where t.accept_id=?";}
	
	public String getSERVICE_ORDER_ACCEPT_SELECT(){return "select t.* from ES_ORDER_ACCEPT t where 1=1 ";}
	
	public String getSERVICE_ACCEPT_STATUS_UPDATE(){return "update ES_ORDER_ACCEPT t set t.accept_status=?,t.modify_time="+DBTUtil.current()+" where t.accept_id=?";}
	
	public String getSERVICE_ACCEPT_SELECT_BY_ORDER(){return "select t.*,t.rowid from ES_ORDER_ACCEPT t where t.order_id=?";}
	
	public String getSERVICE_ACCEPT_STATUS_UPDATE_BY_ORDER(){return "update ES_ORDER_ACCEPT t set t.accept_status=?,t.modify_time="+DBTUtil.current()+" where t.order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_STATUS_UPDATE(){return "update es_order t set t.accept_status=? where t.order_id=?";}
	
	//public String getSERVICE_APPLY_SELECT(){return "select a.*,e.a_order_id as orderId,ei.name as itemName from es_order_apply a,es_order_rel e,es_order_items ei where a.order_apply_id=e.z_order_id and e.a_order_id=ei.item_id and upper(e.z_table_name)='ES_ORDER_APPLY' and a.a_order_item_id=? and a.apply_state=? and a.service_type=?";}
	//改为以下
	public String getSERVICE_APPLY_SELECT(){return "select a.*, eoa.old_order_item_id as itemId, eoa.name as itemName, eoa.num returned_num,eoa.item_type"+
			" from es_order_apply  a, es_order_rel e, es_order_apply_item eoa"+
			" where a.order_apply_id = e.z_order_id and a.order_apply_id = eoa.apply_id"+
			" and e.rel_type= "+OrderStatus.ORDER_TYPE_4+" and upper(e.z_table_name) = 'ES_ORDER_APPLY'"+
			" and e.a_order_id = ?  and a.apply_state =? and a.service_type = ?  and a.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	public String getSERVICE_APPLY_SELECT_2(){return "select * from es_order_apply a,es_order_rel orl " +
			"where a.order_apply_id=orl.z_order_id and orl.rel_type=a.service_type and orl.a_order_id=? " +
			"and a.apply_state = ? and a.service_type = ? and a.source_from = orl.source_from and orl.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_APPLY_UPDATE(){return "update es_order_apply t set t.apply_state=? where t.order_apply_id=?";}
	
	public String getSERVICE_APPLY_SELECT_BY_ID(){return "select a.*,e.a_order_id as orderId from es_order_apply a,es_order_rel e " +
			"where a.order_apply_id=e.z_order_id and upper(e.z_table_name)='ES_ORDER_APPLY' " +
			"and a.order_apply_id=? and a.source_from = e.source_from and e.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_APPLY_COUNT(){return "select count(*) from es_order_apply a,es_order_rel e where a.apply_state=? and " +
			" a.service_type=? and a.order_apply_id=e.z_order_id and e.a_order_id=? and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSERVICE_APPLY_SELECT_BY_COND(){return "select a.*,b.name good_name,c.name memeber_name from " +
			"es_order_apply  a,es_order_items b,es_member c, es_order_rel d where " +
			"b.item_id=d.a_order_id and a.order_apply_id=d.z_order_id and a.user_id=c.member_id and " +
			"a.service_type=? and a.source_from = b.source_from and b.source_from = c.source_from and " +
			"c.source_from = d.source_from and d.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_APPLY_SELECT_BY_ID_2(){return "select * from es_order_apply where order_apply_id=?";}
	
	public String getSERVICE_APPLY_SELECT_BY_ITEM(){return "select * from es_order_apply  " +
			"where order_apply_id in (select z_order_id from es_order_rel where a_order_id=? and source_from = '" + 
			ManagerUtils.getSourceFrom() + "')";}
	
	public String getSERVICE_APPLY_SELECT_BY_USER(){return "select a.*,c.name good_name,d.image_default img_url,d.goods_id goods_id from " +
				" es_order_apply a,es_order_rel b,es_order_items c,es_goods d where"+
				" b.a_order_id=c.item_id and a.order_apply_id=b.z_order_id and c.goods_id=d.goods_id  and a.service_type=? and a.user_id=? " +
				" and a.source_from = b.source_from and b.source_from = c.source_from and c.source_from = d.source_from and " +
				" d.source_from = '" + ManagerUtils.getSourceFrom() + "' and a.apply_state<"+OrderStatus.ORDER_APPLY_STATUS_2+" order by a.create_time desc";}
	
	public String getSERVICE_APPLY_COUNT_BY_USER(){return "select a.*,c.name good_name from es_order_apply a,es_order_rel b,es_order_items c where"+
			" b.a_order_id=c.item_id and a.order_apply_id=b.z_order_id and service_type=? and user_id=? " +
			" and a.source_from = b.source_from and b.source_from = c.source_from and c.source_from = '" + ManagerUtils.getSourceFrom() + "'" + 
			" and a.apply_state<"+OrderStatus.ORDER_APPLY_STATUS_2+" order by create_time desc";}
	
	public String getSERVICE_RETURNED_ITEM_SUM(){return "select sum(oa.submit_num) as returndnum from es_order_rel e,es_order_apply oa " +
			"where upper(e.z_table_name)='ES_ORDER_APPLY' "+
			" and e.z_order_id=oa.order_apply_id and oa.service_type=4 and oa.apply_state in(0,1,3) and e.a_order_id=? " +
			" and e.source_from = oa.source_from and e.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_ORDER_AUDIT_LOGS_SELECT(){return "select (select u.realname from es_adminuser u where u.userid =aud.from_userid) from_username,"
																						+"  (select u.realname from es_adminuser u where u.userid =aud.to_userid) to_username,"
																						+"  (select u.realname from es_adminuser u where u.userid =aud.to_mgr_userid) to_mgr_username,"
																						+" (select userid from es_order ord where ord.order_id =aud.order_id  ) userid, "
																						+ " aud.* from es_order_audit aud where order_id=? and aud.source_from='"+ManagerUtils.getSourceFrom()+"' order by create_date asc";}
	
	public String getSERVICE_ORDER_AUDIT_SELECT(){return "select * from es_order_audit where order_id = ? and audit_type = ?";}
	
	public String getSERVICE_COMMENTS_SELECT(){return "select t.* from es_order_comments t where t.order_id=? order by t.oper_time desc";}
																													
	public String getSERVICE_UNCOMMENTS_SELECT(){return "SELECT a.*,b.exception_name FROM es_order_exception_collect a,es_order_exception b WHERE a.exception_id=b.exception_id AND a.source_from='"+ManagerUtils.getSourceFrom()+"' AND b.source_from='"+ManagerUtils.getSourceFrom()+"' AND a.order_id=? ORDER BY a.create_date DESC";}
	
	public String getSERVICE_ORDER_MESSAGE_SELECT(){return "select t.* from es_order_message t where t.order_id=? order by t.oper_time desc";}
	
	public String getSERVICE_CONFIRM_STATUE_UPDATE(){return "update es_order set confirm_status=?,last_update="+DBTUtil.current()+" where order_id=?";}
																					
	public String getSERVICE_STATUS_UPDATE(){return "update es_order set status=?,confirm_status=?,last_update="+DBTUtil.current()+" where order_id=?";}
	
	public String getSERVICE_ORD_COMPLETE_UPDATE(){return "update es_order set complete_time="+DBTUtil.current()+" where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_TIME_UPDATE (){return "update es_order set ACCEPT_TIME="+DBTUtil.current()+" where order_id=?";}
	
	public String getSERVICE_ORDER_FINISH_UPDATE(){return "update es_order set status=?,complete_time="+DBTUtil.current()+" where order_id=?";}
	
	public String getSERVICE_GIFT_SELECT_BY_ORDER(){return "select * from es_freeoffer where fo_id in (select gift_id from es_order_gift where order_id=? ) ";}
	
	public String getSERVICE_ORD_TRAN_UPDATE(){return "update es_order set transaction_id=?,bank_id=? where order_id=?";}
	
	public String getSERVICE_ORD_PAY_UPDATE(){return "update es_order set status=?,pay_status=?,pay_time="+DBTUtil.current()+",paymoney=paymoney+? where order_id=?";}
	
	public String getSERVICE_ORD_PAY_UPDATE_NEW(){return "update es_order set status=?,pay_status=?,pay_time="+DBTUtil.current()+",paymoney=paymoney+?,transaction_id=? where order_id=?";}
	
	public String getSERVICE_PAYMENT_LOGS_UPDATE(){return "update es_payment_logs set status =?,status_time="+DBTUtil.current()+" where transaction_id = ? ";}
	
	public String getSERVICE_ORD_PAYMONEY_UPDATE(){return "update es_order set status=?,pay_status=?,pay_time="+DBTUtil.current()+",paymoney=order_amount where order_id=?";}
	
	public String getSERVICE_ORD_ITEM_SELECT(){return "select t.* from es_order_items  t where t.order_id=? and t.item_id=?";}
	
	public String getSERVICE_ITEM_SHIPNUM_MINUS_UPDATE(){return "update es_order_items set ship_num=ship_num-? where order_id=? and item_id=?";}
	
	public String getSERVICE_ITEM_SHIPNUM_UPDATE(){return "update es_order_items set ship_num=ship_num+? where order_id=? and item_id=?";}
	
	public String getSERVICE_ITEM_STATE_UPDATE(){return "update es_order_items set state=1 where order_id=? ";}
	
	public String getSERVICE_ITEM_STATE2_UPDATE(){return "update es_order_items set state=2 where order_id=? and state=1";}
	
	public String getSERVICE_ORD_ACCEPT_UPDATE(){return "update es_order set ACCEPT_TIME="+DBTUtil.current()+",status=?,accept_status = ? ,deal_message='' where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_SHIP_UPDATE(){return "update es_order set ACCEPT_TIME="+DBTUtil.current()+",ship_time="+DBTUtil.current()+",status=?,accept_status = ? ,ship_status = ?,deal_message='' where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPTING_UPDATE(){return "update es_order set ACCEPT_TIME="+DBTUtil.current()+",accept_status = ? ,deal_message='' where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_FAIL_UPDATE(){return "update es_order set ACCEPT_TIME="+DBTUtil.current()+",accept_status = ?,deal_message=? where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_THROUGH_UPDATE(){return "update es_order set status=?,accept_status = ?, accept_time="+DBTUtil.current()+"  where order_id=?";}
	
	public String getSERVICE_ORD_ACCEPT_NOT_THROUGH(){return "update es_order set status=?,accept_status = ?, accept_time="+DBTUtil.current()+"  where order_id=?";}
	
	public String getSERVICE_ORD_SHIP_AUTO_UPDATE(){return "update es_order set status=?,ship_time="+DBTUtil.current()+" where order_id=?";}
	
	public String getSERVICE_ORD_NOT_ACCEPT_SELECT(){return "select oi.*,p.store,p.sn from es_order_items  oi " +
			"left join es_product p on oi.spec_id = p.product_id and p.source_from = oi.source_from where " +
			"order_id=? and oi.ACCEPT_STATE ='0' and oi.source_from = '" + ManagerUtils.getSourceFrom() + "' and and oi.source_from = p.source_from ";}
	
	public String getSERVICE_ORD_REL_SELECT(){return "select z_order_id from es_order_rel where a_order_id = ? and rel_type = ?  and state =  ? ";}
	
	public String getSERVICE_ORD_ID_SELECT(){return "select  order_id from es_order_outer where sec_order_id = ? ";}
	
	public String getSERVICE_A_ORDER_ID_SELECT(){return "select a_order_id from es_order_rel where z_order_id = ? and rel_type = ?  ";}
	
	public String getSERVICE_ITEM_SELECT(){return "select * from es_order_items where order_id = ? and rownum<2";}
	
	public String getSERVICE_ITEM_ACTION_CODE_SELECT(){return "select i.*,gr.action_code,gr.status from es_order_items i " +
			"left join es_goods_action_rule gr on (i.goods_id=gr.goods_id and gr.disabled=0 and gr.service_code=? and i.source_from = gr.source_from) " +
			"where i.order_id=? and i.source_from ='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getSERVICE_ITEM_ACTION_RULE_SELECT(){return "select gr.* from es_goods_action_rule gr,es_order_items i " +
			"where gr.goods_id=i.goods_id and i.order_id=? and gr.disabled=0 and gr.source_from = i.source_from and i.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getOrderActionRule(){
		return "select distinct gr.action_code,gr.status from es_goods_action_rule gr,es_order_items i,es_order o "+
			" where gr.goods_id=i.goods_id and i.order_id=o.order_id and o.service_code=? and i.order_id=? and gr.disabled=0 "+
			" and gr.source_from = i.source_from and gr.source_from=o.source_from "+
			" and i.source_from=o.source_from and i.source_from =?";
	}
	
	public String getSERVICE_ORD_OUTER_SELECT(){return "select  t.*,(select name from es_goods g where g.goods_id =t.goods_id ) goods_name  from es_order_outer t where 1=1 ";}
	
	public String getSERVICE_ORD_OUTER_COUNT(){return "select count(*) from es_order_outer t where 1=1 ";}
	
	public String getSERVICE_ORD_OUTER_SELECT_BY_ID(){return "select * from es_order_outer where order_id=?";}
	
	public String getSERVICE_ORD_OUTER_SELECT_BY_BATCH(){return "select * from es_order_outer where batch_id = ? and import_state = '" + Consts.IMPORT_STATE_0 + "'";} 
	
	public String getSERVICE_ORD_OUTER_DELETE(){return "delete from es_order_outer where 1=1 ";}
	
	public String getSERVICE_ORD_SELECT_BY_STAFF(){return "select g.staff_no,g.name,g.goods_id,o.order_id,o.payment_name,o.goods_num,o.order_amount,o.create_time " +
			"from es_order o, es_goods g where o.goods_id = g.goods_id and o.pay_status="+OrderStatus.PAY_STATUS_1+" and o.ship_status="+OrderStatus.SHIP_YES+" and o.status="+OrderStatus.ORDER_COMPLETE
			+ " and o.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_STAFF_ORDER_SELECT(){return "select sum(o.order_amount) as order_amount,g.staff_no,a.realname from es_order o,es_goods g,es_adminuser a where " +
			"o.goods_id=g.goods_id and a.userid=g.staff_no and o.pay_status="+OrderStatus.PAY_STATUS_1+" and o.ship_status="+OrderStatus.SHIP_YES+
			" and o.status="+OrderStatus.ORDER_COMPLETE + " and o.source_from = g.source_from and g.source_from = a.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_COUNT(){return "select count(*) from";}
	
	public String getSERVICE_MEM_DELIVERY_SELECT(){return "select l.*, m.uname as member_name, o.sn from es_delivery l " +
			"left join es_member m on m.member_id = l.member_id and m.source_from = l.source_from " +
			"left join es_order o on o.order_id = l.order_id and o.source_from = l.source_from " +
			"where l.delivery_id = ? and l.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_PAYMENT_SELECT(){return "select l.*, m.uname as member_name from es_payment_logs l " +
			"left join es_member m on m.member_id = l.member_id and m.source_from = l.source_from" +
			"where l.payment_id = ? and l.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_MEM_PAYMENT_LOGS_SELECT(){return "select l.*, m.uname as member_name from es_payment_logs l " +
			"inner join es_member m on (m.source_from = l.source_from and m.member_id = l.member_id #joincond) where " +
			"l.source_from = '" + ManagerUtils.getSourceFrom() + "' ";}
	
	public String getSERVICE_MEM_DELIVERY_SELECT2(){return "select l.*, m.uname as member_name, o.sn,o.create_type from " +
			"es_delivery l inner join es_member m on (m.member_id = l.member_id and l.source_from = m.source_from) " +
			"inner join es_order o on ( o.source_from = l.source_from and o.order_id = l.order_id #createTypeSql #supplierSql) where " +
			"l.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	
	public String getSERVICE_DELIVERY_SELECT_BY_TYPE(){return "select * from es_delivery where order_id = ? and type = ? and ship_status!=-1";}
	
	public String getSERVICE_PAY_LOGS_SELECT(){return "select l.*,p.name as payment_name from es_payment_logs l left join es_payment_cfg p on(p.id=l.pay_method and p.source_from=l.source_from) where l.order_id = ? and l.type = ? and l.status!=-1 and l.source_from=?";}
	
	public String getSERVICE_CARD_SELECT(){return "select * from es_card_info where to_userid = ?";}
	
	public String getSERVICE_CLOUD_ACTIVE_SELECT(){return "select sum(1) all_count, sum("+DBTUtil.decode("t.state", "2", "1", "0")+") active_count, " +
					" round(sum("+DBTUtil.decode("t.state", "2", "1", "0")+")/sum(1),2)*100||'%' active_rate,  u.username, t.lan_name," +
					" t.lan_id,u.realname, #column from es_cloud_info t, es_order o, es_adminuser u ";}
	
	public String getSERVICE_CLOUD_ACTIVE_DETAIL_SELECT(){return "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
				+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state,deal_time ,t.pay_money,"
				+ " t.create_date, t.to_userid, t.from_userid , t.lan_name, u.realname, "+DBTUtil.decode("t.state", "0", "'可用'", "1", "'预占'", "2", "'已用'" , "3", "'失效'","''")+" state_name " 
				+ " from es_cloud_info t, es_order o, es_adminuser u ";}
	
	public String getSERVICE_CLOUD_ACTIVE_PARTNER_SELECT(){return "select sum(1) all_count, sum("+DBTUtil.decode("t.state", "2", "1", "0")+") active_count, " +
																		" round(sum("+DBTUtil.decode("t.state", "2", "1", "0")+")/sum(1),2) active_rate,  u.username, t.lan_name," +
																		" t.lan_id,u.realname, t.to_userid userid from es_cloud_info t, es_adminuser u #cond  group by t.to_userid,u.username,t.lan_name,t.lan_id,u.realname";}
	
	public String getSERVICE_CLOUD_PARTNER_DETAIL(){return "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
																		+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state,deal_time ,t.pay_money,"
																		+ " t.create_date, t.to_userid, t.from_userid , t.lan_name, u.realname from es_cloud_info t, es_adminuser u ";}
	
	public String getSERVICE_CLOUD_PARTNER_NET_SELECT(){return "select sum(1) all_count, sum("+DBTUtil.decode("t.state", "2", "1", "0")+") active_count, " +
																		" round(sum("+DBTUtil.decode("t.state", "2", "1", "0")+")/sum(1),2) active_rate, u.username, t.lan_name," +
																		" t.lan_id,u.realname, t.first_userid userid from es_cloud_info t, es_adminuser u #cond  group by t.first_userid,u.username,t.lan_name,t.lan_id,u.realname";}
	
	public String getSERVICE_CONTRACT_HANDLE_COUNT(){return "select sum(1) all_count,sum("+DBTUtil.decode("c.state", "1", "1", "0")+") succ_count,sum("+DBTUtil.decode("c.state", "-1", "1", "0")+") fail_count,	"
				+ " u.username,c.lan_name, u.realname , c.lan_id, c.offer_name, #column"
				+ " from es_contract_accept c , es_adminuser u #cond group by c.offer_name,u.username,#groupcol,c.lan_name,c.lan_id, u.realname";}
	
	public String getSERVICE_CONTRACT_SALER_ROOM_SELECT(){return " select c.lan_name, u.username, c.lan_id, sum(c.crm_fee) sum_crm_fee, sum(c.sec_fee) sum_sec_fee, u.realname, #column" 
																		+ " from es_contract_accept c, es_adminuser u #cond group by c.lan_name,#groupcol,u.username,c.lan_id, u.realname";}
	
	public String getSERVICE_CONTRACT_DETAIL_SELECT(){return " select  c.lan_name, c.userid, c.username, c.lan_id, c.crm_fee, c.sec_fee, "
																		+ " c.accept_id，c.cust_name, c.offer_name, c.crm_offer_id, c.acc_nbr, c.terminal_code, u.realname "
																		+ " from es_contract_accept c , es_adminuser u ";}
	
	public String getSERVICE_BUY_CARD_SELECT(){return "  select (select lan_name from es_lan l where l.lan_id = o.lan_id) lan_name, o.lan_id, "
							+ " u.username, u.realname, "
							+ " sum(o.paymoney) sum_pay,o.userid    "
							+ " from es_order o , es_adminuser u where o.source_from = u.source_from and u.source_from = '" + ManagerUtils.getSourceFrom() + "' and o.userid = u.userid "
							+ " and u.founder = #value"  // founder  过滤
							+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_TAOBAO +"'"
							+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
							+ " and o.pay_time >= "+DBTUtil.to_sql_date(":start_time", 2)
							+ " and o.pay_time <= "+DBTUtil.to_sql_date(":end_time", 2)
							+ " #cond and o.type_code = '" + OrderBuilder.CLOUD_KEY
							+ "' group by o.lan_id, o.userid, u.username, u.realname";}
	
	public String getSERVICE_BUY_CARD_SELECT_002(){return "  select (select lan_name from es_lan l where l.lan_id = o.lan_id) lan_name, o.lan_id, "
								+ " u.username, u.realname, "
								+ " sum(o.paymoney) sum_pay,o.userid    "
								+ " from es_order o , es_adminuser u where o.source_from = u.source_from and u.source_from = '" + ManagerUtils.getSourceFrom() + "' and o.userid = u.userid "
								+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
								+ " and o.pay_time >= "+DBTUtil.to_sql_date(":start_time", 2)
								+ " and o.pay_time <= "+DBTUtil.to_sql_date(":end_time", 2)
								+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "'" +
										" #cond  group by o.lan_id, o.userid, u.username, u.realname";}
	
	public String getSERVICE_BUY_CARD_DETAIL_SELECT(){return "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
							+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state,deal_time ,t.pay_money,"
							+ " t.create_date, t.to_userid, t.from_userid, t.lan_name, u.realname from es_cloud_info t, es_adminuser u " 
							+ " where t.source_from = u.source_from and u.source_from = '" + ManagerUtils.getSourceFrom() + "' " 
							+ " and t.state_date >= "+DBTUtil.to_sql_date(":start_time", 2)
							+ " and t.state_date <= "+DBTUtil.to_sql_date(":end_time", 2)
							+ " and t.lan_id in (:lan_ids) #cond  order by t.create_date desc";}
	
	public String getSERVICE_REQ_LOGS_SELECT(){return "select * from es_request_logs  where request_id = ?";}
	
	public String getSERVICE_RETURN_ORDER_DELETE(){return "delete from returns_order where id=?";}
	
	public String getSERVICE_RETURN_ORDER_SELECT(){return "select * from returns_order where id=?";}
	
	public String getSERVICE_RETURN_ORDER_SELECT2(){return "select * from returns_order order by add_time desc";}
	
	public String getSERVICE_RETURN_ORDER_MEM(){return "select * from returns_order where memberid =? ";}
	
	public String getSERVICE_RETURN_ORDER_UPDATE(){return "update returns_order set state=? where id=?";}
	
	public String getSERVICE_RETURN_ORDER_REFUSE(){return "update returns_order set state=?,refuse_reson=? where id=?";}
	
	public String getSERVICE_SALEROOM_FEE_SUM(){return "select c.lan_name,c.username,sum(c.crm_fee) crm_fee,sum(c.sec_fee) sec_fee from es_contract_accept c " +
																		"where c.state='1' #cond  group by c.userid,c.lan_name,c.username";}
	
	public String getSERVICE_SLAEROOM_FEE_COUNT(){return "select count(*) from es_contract_accept c where c.state='1' #cond";}
	
	
	public String getSERVICE_ATTR_DEF_SELECT(){return "SELECT * FROM es_attr_def WHERE attr_spec_id = ?";}
	
	public String getSERVICE_TEMP_INST_SELECT(){return "SELECT * FROM es_temp_inst WHERE 1=1 ";}
	
	public String getSERVICE_TEMP_CONFIG_SELECT(){return "SELECT * FROM es_temp_config WHERE state = ?";}
	
	public String getSERVICE_TEMP_CONFIG_SELECT_BY_ID(){return "SELECT * FROM es_temp_config WHERE temp_id = ?";}
	
	public String getSERVICE_TEMP_INST_COUNT(){return "SELECT count(1) FROM es_temp_inst WHERE temp_inst_id = ?";}
	
	public String getSERVICE_AUDIT_STATUS_UPDATE(){return "update ES_WAREHOUSE_PURORDER set AUDIT_STATUS=?,AUDIT_TIME="+DBTUtil.current()+" where Z_ORDER_ID=?";}
	
	public String getSERVICE_WH_PURORDER_SELECT(){return "select distinct a.pru_order_name,a.pru_order_id,d.delivery_id,b.company_name,a.deposit,a.pru_status,a.audit_status,a.pru_delvery_finish_time,a.create_time,c.order_id,w.house_name from " +
			"ES_WAREHOUSE_PURORDER a,es_supplier b,es_order c,es_delivery d,es_warehouse w where " +
			"a.supper_id=b.supplier_id AND a.z_order_id=c.batch_id AND d.order_id=c.order_id AND " +
			"w.house_id=a.house_id and a.source_from = b.source_from and a.source_from = c.source_from and a.source_from = d.source_from and " +
			"a.source_from = w.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont";}
	
	public String getSERVICE_WH_PURORDER_COUNT(){return "select count(distinct a.pru_order_name) from " +
			"ES_WAREHOUSE_PURORDER a,es_supplier b,es_order c,es_delivery d,es_warehouse w where " +
			"a.supper_id=b.supplier_id AND a.z_order_id=c.batch_id AND d.order_id=c.order_id AND w.house_id=a.house_id " +
			"and a.source_from = b.source_from and a.source_from = c.source_from and a.source_from = d.source_from and " +
			"a.source_from = w.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' #cont ";}
	
	public String getSERVICE_WH_P_SELECT_BY_BATCH(){return "select * from ES_WAREHOUSE_PURORDER where Z_ORDER_ID=?";}
	
	public String getSERVICE_WH_P_SELECT_BY_NAME(){return "select * from ES_WAREHOUSE_PURORDER where pru_order_name=? ";}
	
	public String getSERVICE_WH_P_SELECT_BY_ORDER(){return "select * from ES_WAREHOUSE_PURORDER where pru_order_id=?";}
	
	public String getSERVICE_ORDER_REL_UPDATE(){return "update es_order_rel set state=?,state_date="+DBTUtil.current()+" where z_order_id=? and upper(z_table_name)='ES_ORDER_APPLY'";}
	
	public String getSERVICE_DC_PUBLIC_SELECT(){return "select * from es_dc_public where stype='8012' and pkey = ? for update ";}
	
	public String getSERVICE_DC_PUBLIC8013_SELECT(){return "select * from es_dc_public where stype='8013' and pkey = ? for update ";}
	
	public String getSERVICE_SUPPLIER_SELECT(){return "select * from es_supplier t where t.userid=?";}
	
	public String getSERVICE_CART_UPDATE(){return "update es_cart set is_checked=? where session_id =? and cart_id=?";}
	
	public String getSERVICE_PRU_STATUS_UPDATE(){return "update es_warehouse_purorder t set t.pru_status=? where t.z_order_id=?";}
	
	public String getSERVICE_PAYMENT_MONEY_UPDATE(){return "update es_payment_logs set transaction_id=?,money=? where payment_id=?";}
	
	public String getSERVICE_TRANSACTION_ID_UPDATE(){return "update es_payment_logs set transaction_id=? where payment_id=?";}
	
	public String getSERVICE_PAYMONEY_UPDATE(){return "update es_order set transaction_id=?,paymoney=paymoney+? where order_id=?";}
	
	public String getSERVICE_PAYMENT_CFG_SELECT(){return "select * from es_payment_cfg where 1=1";}
	
	public String getIS_ORDER_SHIP(){return "select count(*) from es_delivery t where t.order_id=? and t.ship_status=-1";}
	
	public String getUPDATE_NOT_SHIP_ITEMS_NUM(){return "update es_order_items t set t.ship_num=t.num where t.order_id=?";}
	
	/**
	 * 同步订单历史
	 */
	//public String getSYNC_ORDER_HIS(){return "insert into es_order_his select * from es_order t where t.create_time < "+DBTUtil.to_sql_date("?", 2)+" and t.status="+OrderStatus.ORDER_COMPLETE;}
	public String getSYNC_ORDER_HIS(){return " insert into es_order_his(order_id, sn, member_id, status, pay_status, ship_status, shipping_id, "
			+" shipping_type, shipping_area, payment_id, payment_name, payment_type, paymoney,                     "
			+" create_time, ship_name, ship_addr, ship_zip, ship_email, ship_mobile, ship_tel,                     "
			+" ship_day, ship_time, is_protect, protect_price, goods_amount, shipping_amount,                      "
			+" order_amount, weight, goods_num, gainedpoint, consumepoint, disabled, discount,                     "
			+" imported, pimported, complete_time, bank_id, goods, remark, userid, source_from,                    "
			+" accept_time, transaction_id, order_type, col1, col2, col3, col4, col5,                              "
			+" type_code, accept_status, goods_id, pay_time, lan_id, deal_message, bill_flag,                      "
			+" acc_nbr, batch_id, invoice_type, invoice_title, invoice_title_desc,                                 "
			+" invoice_content, last_update, create_type, source_shop_name,                                        "
			+" order_adscription_id, pay_type, confirm_status, confirm_time, confirm_group_id,                     "
			+" confirm_user_id, ship_assign_time, ship_group_id, ship_user_id,                                     "
			+" accept_assign_time, accept_group_id, accept_user_id, pay_assign_time,                               "
			+" pay_group_id, pay_user_id, taxes, order_discount, order_coupon,                                     "
			+" order_record_status, query_group_id, query_user_id, dly_address_id, spread_id,                      "
			+" sevice_type, col6, col7, col8, col9, col10, service_type, service_id,                               "
			+" service_code, app_key) select t.order_id, t.sn, t.member_id, t.status, t.pay_status, t.ship_status, "
			+" t.shipping_id, t.shipping_type, t.shipping_area, t.payment_id, t.payment_name,                      "
			+" t.payment_type, t.paymoney, t.create_time, t.ship_name, t.ship_addr, t.ship_zip,                    "
			+" t.ship_email, t.ship_mobile, t.ship_tel, t.ship_day, t.ship_time, t.is_protect,                     "
			+" t.protect_price, t.goods_amount, t.shipping_amount, t.order_amount, t.weight,                       "
			+" t.goods_num, t.gainedpoint, t.consumepoint, t.disabled, t.discount, t.imported,                     "
			+" t.pimported, t.complete_time, t.bank_id, t.goods, t.remark, t.userid,                               "
			+" t.source_from, t.accept_time, t.transaction_id, t.order_type, t.col1, t.col2,                       "
			+" t.col3, t.col4, t.col5, t.type_code, t.accept_status, t.goods_id, t.pay_time,                       "
			+" t.lan_id, t.deal_message, t.bill_flag, t.acc_nbr, t.batch_id, t.invoice_type,                       "
			+" t.invoice_title, t.invoice_title_desc, t.invoice_content, t.last_update,                            "
			+" t.create_type, t.source_shop_name, t.order_adscription_id, t.pay_type,                              "
			+" t.confirm_status, t.confirm_time, t.confirm_group_id, t.confirm_user_id,                            "
			+" t.ship_assign_time, t.ship_group_id, t.ship_user_id, t.accept_assign_time,                          "
			+" t.accept_group_id, t.accept_user_id, t.pay_assign_time, t.pay_group_id,                             "
			+" t.pay_user_id, t.taxes, t.order_discount, t.order_coupon, t.order_record_status,                    "
			+" t.query_group_id, t.query_user_id, t.dly_address_id, t.spread_id, t.sevice_type,                    "
			+" t.col6, t.col7, t.col8, t.col9, t.col10, t.service_type, t.service_id,                              "
			+" t.service_code, t.app_key from es_order t                                                           "
			+" where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.create_time < "+DBTUtil.to_sql_date("?", 2)+" and t.status="+OrderStatus.ORDER_COMPLETE;
	}
	public String getDELETE_SYNC_ORDER(){return "delete from es_order t where t.create_time < "+DBTUtil.to_sql_date("?", 2)+" and t.status="+OrderStatus.ORDER_COMPLETE;}
	
	/**
	 * 同步子订单历史
	 */
	//public String getSYNC_ORDER_ITEMS_HIS(){return "insert into es_order_items_his select i.* from es_order_items i,es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;}
	public String getSYNC_ORDER_ITEMS_HIS(){return "insert into es_order_items_his(item_id, order_id, goods_id, spec_id, num, ship_num, name, price, gainedpoint, "
			+" state, addon, lv_id, coupon_price, unit, source_from, item_type, product_id,                                                                 "
			+" col1, col2, col3, col4, col5, col6, col7, col8, col9, col10) select i.item_id, i.order_id, i.goods_id, i.spec_id, i.num, i.ship_num, i.name, "
			+" i.price, i.gainedpoint, i.state, i.addon, i.lv_id, i.coupon_price, i.unit,                                                                   "
			+" i.source_from, i.item_type, i.product_id, i.col1, i.col2, i.col3, i.col4,                                                                    "
			+" i.col5, i.col6, i.col7, i.col8, i.col9, i.col10 from es_order_items i,es_order o                                                        "
			+" where i.source_from=o.source_from and i.source_from='"+ManagerUtils.getSourceFrom()+"' and i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;
	}
	public String getDELETE_SYNC_ORDER_ITEMS(){return "delete from es_order_items i where exists(select 1 from es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE + ")";}
	
	/**
	 * 同步订单属性表
	 */
	//public String getSYNC_ORDER_ATTR_INST_HIS(){return "insert into es_attr_inst_his select i.* from es_attr_inst i,es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;}
	public String getSYNC_ORDER_ATTR_INST_HIS(){return " insert into es_attr_inst_his(attr_inst_id, order_id, order_item_id, inst_id, attr_spec_id, field_attr_id, "
			+" field_name, filed_desc, field_value, field_value_desc, modify_field_value, sequ,                          "
			+" sec_field_value, sec_filed_value_desc, col1, col2, col3, create_date, source_from)                        "
			+" select i.attr_inst_id, i.order_id, i.order_item_id, i.inst_id, i.attr_spec_id,                            "
			+" i.field_attr_id, i.field_name, i.filed_desc, i.field_value, i.field_value_desc,                           "
			+" i.modify_field_value, i.sequ, i.sec_field_value, i.sec_filed_value_desc, i.col1,                          "
			+" i.col2, i.col3, i.create_date,i.source_from from es_attr_inst i,es_order o where i.order_id=o.order_id    "
			+" and i.source_from=o.source_from and i.source_from='"+ManagerUtils.getSourceFrom()+"' "
			+" and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;
	}
	public String getDELETE_SYNC_ORDER_ATTR_INST(){return "delete from es_attr_inst i where exists(select 1 from es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE + ")";}
	
	/**
	 * 同步订单物流表
	 */
	//public String getSYNC_ORDER_DELIVERY_HIS(){return "insert into es_delivery_his select i.* from es_delivery i,es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;}
	public String getSYNC_ORDER_DELIVERY_HIS(){return " insert into es_delivery_his(delivery_id, type, order_id, member_id, money, ship_type, is_protect,    "
			+" protect_price, logi_id, logi_name, logi_no, ship_name, province_id, city_id,                         "
			+" region_id, region, city, province, ship_addr, ship_zip, ship_tel, ship_mobile,                       "
			+" ship_email, op_name, create_time, reason, remark, userid, ship_num,                                  "
			+" print_status, weight, ship_status, batch_id, source_from, house_id, col1, col2,                      "
			+" col3, col4, col5, col6, col7, col8, col9,col10) select i.delivery_id, i.type, i.order_id,            "
			+" i.member_id, i.money, i.ship_type, i.is_protect, i.protect_price, i.logi_id, i.logi_name, i.logi_no, "
			+" i.ship_name, i.province_id, i.city_id, i.region_id, i.region, i.city, i.province,                    "
			+" i.ship_addr, i.ship_zip, i.ship_tel, i.ship_mobile, i.ship_email, i.op_name,                         "
			+" i.create_time, i.reason, i.remark, i.userid, i.ship_num, i.print_status,                             "
			+" i.weight, i.ship_status, i.batch_id, i.source_from, i.house_id, i.col1, i.col2,                      "
			+" i.col3, i.col4, i.col5, i.col6, i.col7, i.col8, i.col9, i.col10 from es_delivery i,es_order o        "
			+" where i.order_id=o.order_id and i.source_from=o.source_from  and i.source_from='"+ManagerUtils.getSourceFrom()+"' "
			+ " and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;
	}
	public String getDELETE_SYNC_ORDER_DELIVERY(){return "delete from es_delivery i where exists(select 1 from es_order o where i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE + ")";}
	
	/**
	 * 同步物流子表
	 */
	//public String getSYNC_ORDER_DELIVERY_ITEM_HIS(){return "insert into es_delivery_item_his select di.* from es_delivery_item di,es_delivery i,es_order o where di.delivery_id=i.delivery_id and i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;}
	public String getSYNC_ORDER_DELIVERY_ITEM_HIS(){return " insert into es_delivery_item_his(item_id, delivery_id, goods_id, product_id, sn, name, num, itemtype,                                                        "
			+" source_from, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10) select di.item_id, di.delivery_id, di.goods_id,                                    "
			+" di.product_id, di.sn, di.name, di.num, di.itemtype, di.source_from, di.col1, di.col2, di.col3, di.col4, di.col5,                                             "
			+" di.col6, di.col7, di.col8, di.col9, di.col10 from es_delivery_item di,es_delivery i,es_order o where di.delivery_id=i.delivery_id and i.order_id=o.order_id  "
			+" and di.source_from=i.source_from and di.source_from=o.source_from and i.source_from=o.source_from and di.source_from='"+ManagerUtils.getSourceFrom()+"' "
			+" and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;
	}
	public String getDELETE_SYNC_ORDER_DELIVERY_ITEM(){return "delete from es_delivery_item di where exists(select 1 from es_delivery i,es_order o where di.delivery_id=i.delivery_id and i.order_id=o.order_id and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE+")";}
	
	public String getSERVICE_ORDER_COMMENTS_SELECT(){return "select t.* from es_order_comments t where t.order_id=? order by t.oper_time desc";}
	public String getIS_CREATE_ORDER(){return "select count(*) from es_goods_action_rule t where t.action_code in('pay','delivery','createorder') and t.status='yes' and t.disabled='0' and t.service_code=? and t.goods_id in(:instr)";}
	
	public String getQUERY_ORDER_APPLY_ITEMS(){return "select * from es_order_apply_item t where t.apply_id=? ";}
	public String getQUERY_ORDER_APPLY_PAGE(){return "select a.*,er.a_order_id order_id,m.uname memeber_nam " +
			"from es_order_apply a,es_order_rel er,es_member m where " +
			"a.service_type=? and a.member_id=m.member_id and a.order_apply_id=er.z_order_id and " +
			"a.service_type=er.rel_type and upper(er.z_table_name)=upper('es_order_apply') " +
			"and a.source_from = er.source_from and er.source_from = m.source_from and m.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	public String getQUERY_ORDER_REL_BY_APPLY_ID(){return "select * from es_order_rel t where t.z_order_id=? and upper(t.z_table_name)=upper('es_order_apply')";}

	public String getDELETE_ORDER_APPLY_ITEMS(){return "delete from es_order_apply_item t where t.apply_id=?";}
	public String getDELETE_ORDER_APPLY_ORDER_REL(){return "delete from es_order_rel t where t.z_order_id=? and upper(t.z_table_name)=upper('es_order_apply')";}
	
	public String getCOUNT_ODER_APPLY_NUM(){return "select count(*) from es_order_apply t,es_order_rel orl where " +
			"t.order_apply_id=orl.z_order_id and orl.a_order_id=? and t.apply_state in(0,1) and t.service_type in(3,4)" +
			" and t.source_from = orl.source_from and t.source_from = '" + ManagerUtils.getSourceFrom() + "'";}
	public String getUPDATE_ORDER_APPLY_STATUS(){return "update es_order_apply t set t.apply_state=? where t.order_apply_id=?";}
	
	public String getLIST_ORDER_EXCEPTION(){return "SELECT * FROM es_order_exception WHERE source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getLIST_ORDER_EXCEPTION_COUNT(){return "SELECT count(exception_id) FROM es_order_exception WHERE source_from='"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getQRY_ORDER_EXCEPTION(){return "SELECT * FROM es_order_exception WHERE exception_id = ? AND source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getDELETE_ORDER_EXCEPTION(){return "DELETE FROM es_order_exception WHERE exception_id = ? AND source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getQUERY_GOODS_ACTION_RULE_GOODS_ID(){return "select * from es_goods_action_rule t where t.goods_id=? and t.source_from=? and t.service_code=? and t.disabled=0";}
	
	public String getQUERY_GOODS_ACTION_RULE_GOODS_IDS(){return "select distinct * from es_goods_action_rule t where t.goods_id in (:inids) and t.source_from=? and t.service_code=? and t.disabled=0";}
	
	public String getGET_OUTTER_TMPL_BY_ORDER_FROM(){return "select * from es_outer_execute_tmpl t where t.order_from=? and t.source_from=?";}
	
	public String getACCEPT_TMPL_INST_BY_PRODUCTID(){return "SELECT t.* FROM es_temp_inst t,es_product p WHERE t.goods_id=p.goods_id and p.product_id=? and p.source_from=?";}
	
	public String getLIST_OUTER_EXECUTE_TMPL(){return "select * from es_outer_execute_tmpl t where t.run_status='00A' and t.next_execute<="+DBTUtil.current();}
	public String getUPDATE_EXECUTE_RUN_STATUS(){return "update es_outer_execute_tmpl t set t.run_status=? where t.tmpl_id=?";}
	
	public String getSYNC_OUTER_ORDER(){return "select * from es_order_outer t where t.source_from=? and  t.create_time>="+DBTUtil.to_sql_date("?", 2)+" and t.create_time<"+DBTUtil.to_sql_date("?", 2);}
	
	public String getQUERY_PUBLIC_CONST_BY_SYS(){return "select t.* from ES_PUBLIC_CONSTS t where t.sys_from=? and t.outer_code=? and t.object_type=? and t.source_from=?";}


	public String getOrderListForOut(){return "select * from es_order t where t.app_key='taobao' and exists (select 1 from es_outer_accept a where a.order_id = t.order_id and a.pro_type is not null) ";}

	public String getOrderItmeForOut(){return "select * from es_order_items i where i.order_id=?";}
	
	public String getOrderAcceptForOut(){return "select * from es_outer_accept a where a.order_id=?";}
	public String getPaymentListCount(){return "select count(*) from es_payment_list where transaction_id = ?";}
	 
	public String getORDER_OUTER_BY_ORDER_ID(){
		return "SELECT b.* from es_order a, es_order_outer b, es_order_rel c " +
				"WHERE a.order_id = c.z_order_id AND b.order_id = c.a_order_id " +
				"AND c.z_table_name = 'ES_ORDER_OUTER' AND " +
				"a.source_from = '"+ManagerUtils.getSourceFrom()+"' " +
						"AND b.source_from = '"+ManagerUtils.getSourceFrom()+"' " +
								"AND c.source_from = '"+ManagerUtils.getSourceFrom()+"'" + 
						" AND a.order_id = ?";
	}
	

	public String getOrderGroupListByGroupType(){
		return "select t.* from es_order_group_def t where t.disabled='0'";
	}
	
	public String getListOrderPromotion(){
		return "select pa.name as activity_name,p.pmt_describe,p.pmts_id,p.pmt_id,op.order_id,pa.id as activity_id from es_order_pmt op,es_promotion p,es_promotion_activity pa where op.pmt_id=p.pmt_id "+
				" and p.pmta_id=pa.id and op.order_id=? and op.source_from=p.source_from and op.source_from=p.source_from and pa.source_from=p.source_from and op.source_from=?";
	}
	
	public String getListPromotionGoods(){
		return "select g.* from es_goods g,es_pmt_goods pg where g.goods_id=pg.goods_id and g.source_from=pg.source_from and pg.pmt_id=? and g.source_from=?";
	}
	
	public String getPromotionMenberLv(){
		return "select ml.* from es_pmt_member_lv pml,es_member_lv ml where pml.lv_id=ml.lv_id and pml.source_from=ml.source_from and pml.pmt_id=? and pml.source_from=?";
	}
	
	public String getQueryOrderFlow(){
		return "select * from es_order_flow t where t.type_code=? and t.object_id=? and t.service_type=?";
	}
	
	public String getQueryNextOrderFlowDef(){
		return "select * from es_order_flow_def t where t.parent_id=?";
	}
	
	public String getOrderFlowDef(){
		return "select * from es_order_flow_def t where t.flow_def_id=?";
	}
	
	public String getUpdateOrderToDoList(){
		return "update es_order_todo_list t set t.flow_status=?,t.oper_id=?,t.oper_name=?,t.oper_type=?,t.hint=?,t.flow_pass_time="+DBTUtil.current()+" where t.list_id=?";
	}
	
	public String getFlowDefByOrderStatus(){
		return "select * from es_order_flow_def t where t.flow_id=? and t.flow_type=?";
	}
	
	public String getQueryUserOrderToDoList(){
		//return "select l.* from es_order_todo_list l where l.order_id=? and l.flow_status=0 and l.source_from=? and l.flow_user_id=? and l.flow_user_id is not null";
		return "select l.* from es_order_todo_list l where l.order_id=? and l.flow_status=0 and l.source_from=? and ((l.flow_user_id=? and l.flow_user_id is not null) or "+
			"(exists(select 1 from es_order_group_role_rel t where "+
			" t.group_id=l.flow_group_id and t.source_from=l.source_from and (t.userid=? or exists(select 1 from es_user_role ur where ur.roleid=t.role_id and ur.userid=? and ur.source_from=t.source_from)))))";
	}
	
	public String getQueryUserOrderToDoList0(){
		return "select l.* from es_order_todo_list l where l.order_id=? and l.flow_status=0 and l.source_from=? and l.flow_user_id=? and l.flow_user_id is not null";
	}
	
	public String getCountOrderToDoList(){
		return "select count(*) from es_order_todo_list t where t.order_id=? and t.flow_status=0 and t.flow_def_id=?";
	}
	
	public String getListOrderFlowDefList(){
		return "select t.* from es_order_flow_def t where t.flow_id=? order by t.sort";
	}
	
	public String getNoDoListOrderFlowDefList(){
		return "select t.* from es_order_flow_def t where t.flow_id=? and not exists(select 1 from es_order_todo_list ot where ot.flow_def_id=t.flow_def_id and ot.source_from=t.source_from and ot.order_id=?) and t.source_from=? order by t.sort";
	}
	
	public String getQUERY_ORDER_GROUP_USERS(){
		return "select * from (select t.* from es_adminuser t,es_order_group_role_rel ogr where upper(t.username) like ? and t.userid=ogr.userid and ogr.group_id=? and t.source_from=ogr.source_from and t.source_from=? "+
			" union "+
			" select t.* from es_adminuser t,es_user_role ur,es_order_group_role_rel ogr where t.userid=ur.userid and ur.roleid=ogr.role_id and upper(t.username) like ? and ogr.group_id=? "+
			" and t.source_from=? and t.source_from=ur.source_from and ur.source_from=ogr.source_from and t.source_from=ogr.source_from) ee";
	}
	
	public String getSERVICE_ORDER_ACCEPT_STATUS_UPDATE(){
		return "update es_order t set t.accept_status=? where t.order_id=?";
	}
	
	public String getQueryFlowOrder(){
		return "select distinct t.*,m.uname,(select am.username from es_order_todo_list tld,es_adminuser am where tld.flow_user_id=am.userid and tld.order_id=t.order_id and tld.flow_status=0 "+
				" and tld.source_from=t.source_from and rownum=1) as todo_user_id "+
				"  from es_order t, es_order_todo_list l,es_member m "+
				" where t.order_id = l.order_id and t.member_id=m.member_id and t.disabled=0 "+
				"   and t.source_from = l.source_from and t.source_from=m.source_from "+
				"   and l.source_from=m.source_from and l.source_from = ? ";
				/*"   and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
				"       (l.flow_user_id is null and exists                        "+
				"        (select 1                                                "+
				"            from es_order_group_role_rel t                       "+
				"           where t.group_id = l.flow_group_id                    "+
				"             and t.source_from = l.source_from                   "+
				"             and (t.userid = ? or exists                       "+
				"                  (select 1                                      "+
				"                     from es_user_role ur                        "+
				"                    where ur.roleid = t.role_id                  "+
				"                      and ur.userid = ?                        "+
				"                      and ur.source_from = t.source_from)))))    ";*/
	}
	
	public String getUpdateToDoListByOrderIDAndFlowType(){
		return "update es_order_todo_list t set t.flow_status=? where t.order_id=? and t.flow_type=?";
	}
	
	public String getOrderFlowByDefId(){
		return "select f.* from es_order_flow f,es_order_flow_def odf where f.flow_id=odf.flow_id and odf.flow_def_id=? and f.source_from=odf.source_from and odf.source_from=?";
	}
	
	public String getListOrderFlowToDoList(){
		return "select t.*,fd.flow_name from es_order_todo_list t left join es_order_flow_def fd on(t.flow_def_id=fd.flow_def_id and t.source_from=fd.source_from) where t.order_id=? and t.source_from=? order by t.flow_pass_time";
	}
	
	public String getCountOrderApplyByTypeAndStatus(){
		return "select count(*) from es_order_apply t where t.service_type=? ";
	}
	
	public String getCountHasShipOrderItems(){
		return "select count(*) from es_order_items t where t.order_id=? and t.ship_num>0";
	}
	
	public String getQueryHistoryOrder(){
		return "select distinct t.*,m.uname "+
				"  from es_order_his t, es_order_todo_list l,es_member m "+
				" where t.order_id = l.order_id and t.member_id=m.member_id "+
				"   and t.source_from = l.source_from and t.source_from=m.source_from"+
				"   and l.source_from=m.source_from and l.source_from = ?  "+
				"   and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
				"       (l.flow_user_id is null and exists                        "+
				"        (select 1                                                "+
				"            from es_order_group_role_rel t                       "+
				"           where t.group_id = l.flow_group_id                    "+
				"             and t.source_from = l.source_from                   "+
				"             and (t.userid = ? or exists                       "+
				"                  (select 1                                      "+
				"                     from es_user_role ur                        "+
				"                    where ur.roleid = t.role_id                  "+
				"                      and ur.userid = ?                        "+
				"                      and ur.source_from = t.source_from)))))    ";
	}
	
	public String getHIS_SERVICE_ORDER_SELECT_BY_ID(){return "select * from es_order_his where order_id=?";}
	
	public String getHIS_SERVICE_GOODS_ITEMS_SELECT(){return "select items.*, g.image_default as image, p.sn as sn, (select sum(ai.num) from es_order_apply_item ai,es_order_apply oa where ai.apply_id=oa.order_apply_id and ai.item_type='4'and oa.apply_state!='2' and ai.old_order_item_id=items.item_id) as returndnum "+
			" from es_order_items_his items "+
			" left join es_goods g on g.goods_id = items.goods_id  "+ManagerUtils.apSFromCond("g")+" "+
			" left join es_product p on p.product_id = items.spec_id   "+ManagerUtils.apSFromCond("p")+""+
			" where items.order_id = ? and items.source_from=g.source_from and items.source_from=p.source_from and items.source_from=?";}
	
	public String getHIS_SERVICE_DELIVERY_SELECT_BY_TYPE(){return "select * from es_delivery_his where order_id = ? and type = ? and ship_status!=-1";}
	
	public String getHIS_SERVICE_PAY_LOGS_SELECT(){return "select l.*,p.name as payment_name from es_payment_logs_his l left join es_payment_cfg p on(p.id=l.pay_method and p.source_from=l.source_from) where l.order_id = ? and l.type = ? and l.status!=-1 and l.source_from=?";}
	
	public String getSYNC_PAYMENT_LOGS(){
		return " insert into es_payment_logs_his(payment_id, order_id, member_id, account, bank_id, pay_user, money, pay_cost, "
				+" pay_type, pay_method, op_id, type, status, create_time, remark, userid,                                       "
				+" transaction_id, status_time, paytype, pay_time, returned_account, returned_type,                              "
				+" returned_kind, source_from, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10)                      "
				+" select pol.payment_id, pol.order_id, pol.member_id, pol.account, pol.bank_id,                                 "
				+" pol.pay_user, pol.money, pol.pay_cost, pol.pay_type, pol.pay_method, pol.op_id,                               "
				+" pol.type, pol.status, pol.create_time, pol.remark, pol.userid,                                                "
				+" pol.transaction_id, pol.status_time, pol.paytype, pol.pay_time,                                               "
				+" pol.returned_account, pol.returned_type, pol.returned_kind, pol.source_from,                                  "
				+" pol.col1, pol.col2, pol.col3, pol.col4, pol.col5, pol.col6, pol.col7, pol.col8,                               "
				+" pol.col9, pol.col10 from es_payment_logs pol,es_order o                                                       "
				+" where pol.order_id=o.order_id and pol.source_from=o.source_from and pol.source_from='"+ManagerUtils.getSourceFrom()+"'"
				+ " and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE;
	}
	
	public String deleteSYNC_PAYMENT_LOGS(){
		return "delete from es_payment_logs t where exists(select 1 from es_order o where t.order_id=o.order_id and t.source_from=o.source_from and t.source_from='"+ManagerUtils.getSourceFrom()+"' and o.create_time < "+DBTUtil.to_sql_date("?", 2)+" and o.status="+OrderStatus.ORDER_COMPLETE+")";
	}
	
	public String getListOrderReturVisitType(){
		return "select t.* from es_order_return_visit_type t where t.kind=?";
	}
	
	public String getListOrderReturnVisit(){
		return "select t.*,vt.name as typename,tlfd.flow_name,vt2.name as method_name from es_order_return_visit t left join es_order_return_visit_type vt on(t.type_id=vt.type_id and vt.kind=0 and t.source_from=vt.source_from) "+
			" left join es_order_return_visit_type vt2 on(t.return_method=vt2.type_id and vt2.kind=1 and t.source_from=vt2.source_from)"+	
			" left join (select tl.*,fd.flow_name from es_order_todo_list tl left join es_order_flow_def fd on(tl.flow_def_id=fd.flow_def_id and tl.source_from=fd.source_from)) tlfd "+
			" on(t.todo_list_id=tlfd.list_id and t.source_from=tlfd.source_from) where t.order_id=? and t.source_from=? ";
	}
	
	public String getQueryFlowUserGroupByFlowType(){
		return "select t.* from es_order_group_def t where t.group_type=? and t.source_from=? ";
	}
	
	public String getOrderFlowByTodoListId(){
		return "select f.*,f.rowid from es_order_flow f,es_order_flow_def fd,es_order_todo_list tl "+
				" where f.flow_id=fd.flow_id and fd.flow_def_id=tl.flow_def_id and tl.list_id=? "+
				" and f.source_from=fd.source_from and fd.source_from=tl.source_from and f.source_from=?";
	}
	
	public String getOrderTodoListByFowStatus(){
		return "select * from es_order_todo_list t where t.order_id=? and t.flow_status=?";
	}
	
	public String getQueryApplyByOrderId(){
		return "select * from es_order_apply t where t.a_order_item_id=? order by t.create_time desc";
	}
	
	public String getUpdateExceptionStatus(){
		return "update es_order t set t.order_record_status=? where t.order_id=?";
	}
	
	public String getUpdateOrderDisabledStatus(){
		return "update es_order t set t.disabled=? where t.order_id=?";
	}
	
	public String getADJECT_ORDER_PRICE(){
    	return "select a.adjust_key, a.adjust_type, a.price, a.adjust_name from ES_ADJUST_PRICE a " +
    			"where a.adjust_key = ? and a.adjust_type = ? and a.source_from is not null";
    }
	
	public String getADJECT_LIST_BY_TYPE(){
		return "select a.adjust_key, a.adjust_type, a.price, a.adjust_name from ES_ADJUST_PRICE a " +
    			"where a.adjust_type = ? and a.source_from is not null";
	}
	
	public String getQUERY_ATTE_DEF_COM(){
		return "select t.*,h.handler_class from es_attr_def t left join es_attr_handler h on(h.handler_id=t.handler_id and h.source_from=t.source_from) "+
				" where t.attr_spec_type=? and t.attr_spec_id=? and t.source_from=?";
	}
	
	public String getUPDATE_ATTRINST_BY_ID(){
		return "update es_attr_inst t set t.field_value=?,t.field_value_desc=? where t.attr_inst_id=?";
	}
	
	public String getUPDATE_ATTRINST_HIS_BY_ID(){
		return "update es_attr_inst_his t set t.field_value=?,t.field_value_desc=? where t.attr_inst_id=?";
	}
	
//	public String getAttrDefByAttrInstId(){
//		return "select t.*,ai.inst_id,ai.order_id from es_attr_def t,es_attr_inst ai where t.field_attr_id=ai.field_attr_id and t.source_from=ai.source_from and ai.attr_inst_id=?";
//	}
	
	public String getQUERYGOODSTYPECODE(){
		return "select gt.* from es_goods_type gt,es_goods g where gt.source_from=g.source_from and gt.type_id=g.type_id and g.goods_id=? and g.source_from=?";
	}
	
	public String getQUERY_ATTR_TABLE(){
		return "select t.* from es_attr_table t,es_attr_def d where t.attr_def_id=d.field_attr_id and t.source_from=d.source_from and d.attr_spec_type=? and d.attr_spec_id=? and t.source_from=?";
	}
	
	public String getQUERY_ATTR_INST_TABLES(){
		//return "select t.* from es_attr_table t,es_attr_inst ai where t.attr_def_id=ai.field_attr_id and t.source_from=ai.source_from and t.source_from=? and ai.attr_inst_id=?";
		return "select * from es_attr_table where attr_def_id=? ";
	}
	
	public String getQUERY_ATTR_DEF_BY_ID(){
		return "select t.*,h.handler_class from es_attr_def t left join es_attr_handler h on(h.handler_id=t.handler_id and h.source_from=t.source_from) "+
			" where t.field_attr_id=? and t.source_from=?";
	}
	
	public String getORDER_TREE_ATTR_LIST(){
		//return "select t.* from es_attr_def t";
		return "select t.*,h.handler_class from es_attr_def t left join es_attr_handler h on(h.handler_id=t.handler_id and h.source_from=t.source_from) "+
			" where t.source_from=?  ";
	}
	
	public String getATTR_INST_GET(){
		return "select sec_field_value from es_attr_inst where order_id=? and field_name=? and source_from='"+ManagerUtils.getSourceFrom()+"'";
	}
	
	public String getQUERY_ORDER_ID(){
		return "select order_id from (select order_id, rownum rn from es_unimall_order ) where rn >= ? and rn <= ?  ";
	}
	
	public String getORDER_INFO(){
		return "select  a.*, rownum rn from es_unimall_order a where orderid=? and rownum<=1";
	}
	
	public String getDEF_CODE(){
		return "select * from es_unimall_order_def where 1=1";
	}
	
	public String getLIMIT(){
		return "select cache_interval from es_unimall_order_limit_conf where sql_def_code=?";
	}
	
	public String getORDER_TOTAL_NUM(){
		return "select count(distinct(order_id)) from (select order_id, rownum rn from es_unimall_order )";
	}
	
	public String getUNIMALL_ORDER_INSERT(){
		return "insert into ES_UNIMALL_ORDER (SQL_DEF_CODE, ORDERID, LOCKUSER, LOCKUSERNAME, DEALSORT, EXCEPTIONORDERFLAG, " +
				"AUTOORDEREXCEPTIONFLAG, ORDERCREATETIMESTR, ORDERPRI, RECOVERTYTYPE, FIRSTSAVEFLAG, SOCIETYFLAG, BESPOKEFLAG, " +
				"SENDZBFLAG, ORDERORIGIN, ORDERORGINID, FLOWNODE, POST_TYPE, CARRY_MODE, PRODUCT_TYPE, END_AGENT, LOCK_STATUS, EXT_ORDER_STATUS, " +
				"SP_PROD_TYPE, ORDER_REGION, ORDER_SON_TYPE, PACK_TYPE, ORDER_DEAL_TYPE, PACKAGE_TYPE, DEVELOP_ATTRIBUTION, BRAND_CODE, ESS_ACCOUNT_FLAG," +
				"ORDER_TYPE, OTHER_DEAL_USER, OUTER_ORDER_ID, PAY_TYPE, PET_NAME, MOBILE_TEL, HANDSET_TYPE, BUYER_NAME, SERIES_NUM, IS_NEED_BSS, FLOW_NODE_STATUS," +
				" OTHER_DEAL_STATUS, DEAL_FLAG, EXCEPTION_TYPE, PRODUCTION_PATTERN, ORDER_STATUS, AGENT_ID, LIGHT_NING_STATUS, SDS_COMP, DICT_CODE, PRODUCT_NAME, FEE," +
				" ESSINFO, BSSINFO,PRODUCTION_PATTERN_FIRST,SOURCE_FROM,LOCKTIMESTR,ORDERCREATETIME,PAYTIME)" +
				"values (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?," +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '"+ManagerUtils.getSourceFrom()+"', " +
				"to_date(?, 'yyyy-mm-dd hh24:mi:ss'), to_date(?, 'yyyy-mm-dd hh24:mi:ss'), to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
	}
	
	public String getDelOrderItem(){
		return "delete from es_order_items t where t.item_id=?";
	}
	
	public String getUpdateOrderItemShipNum(){
		return "update es_order_items t set t.ship_num=? where t.item_id=?";
	}
	
	public String getUpdateShipStatus(){
		return "update es_order t set t.ship_status=? where t.order_id=?";
	}
	
	public String getUPDATE_DELIVERY_STATUS(){
		return "update es_delivery t set t.ship_status=? where t.delivery_id=?";
	}
	
	public String getQUERY_DELIVERY_ITEMS(){
		return "select i.* from es_delivery_item i where i.order_id=? ";
	}
	
	public String getUNIMALL_ORDERID_QUERY(){
		return " orderid from ( select  distinct t.orderid,ordercreatetime,paytime,lockuser,dealsort from es_unimall_order t where exists (select 1 from  es_view_role_data_limitn vrdl_as where (vrdl_as.AGENT_CODE = t.END_AGENT or "+ 
        "vrdl_as.AGENT_CODE is null) and ((',' || vrdl_as.carry_mode like '%,' || t.carry_mode || ',%' or t.carry_mode is null) or vrdl_as.carry_mode is null) "+ 
        "and (',' || vrdl_as.lock_status like '%,' || (case when t.dealsort is null or t.dealsort = '0' then '0' when t.dealsort = '1' and t.LOCKUSER = ? then '1' "+
        "when t.dealsort = '1' and t.LOCKUSER <> ? then '2' end) || ',%' or vrdl_as.lock_status is null) "+ 
        "and (',' || vrdl_as.normal_flag like '%,' || (case when t.EXT_ORDER_STATUS = '05' then '0' else '1' end) || ',%' or vrdl_as.normal_flag is null) "+
        "and (',' || vrdl_as.sp_prod_type like '%,' || t.sp_prod_type || ',%' or vrdl_as.sp_prod_type is null) and (',' || vrdl_as.order_region like "+
        "'%,' || t.ORDER_REGION || ',%' or vrdl_as.order_region is null) and (',' || vrdl_as.bespoke_flag like '%,' || t.BESPOKEFLAG || ',%' or "+
        "vrdl_as.bespoke_flag is null) and (',' || vrdl_as.society_flag like '%,' || t.SOCIETYFLAG || ',%' or vrdl_as.society_flag is null) "+
        "and (',' || vrdl_as.order_origin like '%,' || t.ORDERORIGIN || ',%' or vrdl_as.order_origin is null or t.ORDERORIGIN is null) "+ 
        "and (',' || vrdl_as.son_order_type like '%,' || t.ORDER_SON_TYPE || ',%' or vrdl_as.son_order_type is null) and (',' || vrdl_as.flow_node like '%,' || 'B' || ',%' or "+
        "vrdl_as.flow_node is null) and ((',' || vrdl_as.product_sub_type like '%,' || t.PACK_TYPE || ',%' or t.PACK_TYPE is null) or vrdl_as.product_busi_type is null) "+
        "and (',' || vrdl_as.pay_mode like '%,' || t.PAY_TYPE || ',%' or vrdl_as.pay_mode is null) and (',' || vrdl_as.handle_type like '%,' || (case when t.ORDER_DEAL_TYPE is null then "+
        "'02' else t.ORDER_DEAL_TYPE end) || ',%' or vrdl_as.handle_type is null) and ((',' || vrdl_as.product_busi_type like '%,' || t.PACKAGE_TYPE || ',%' or t.PACKAGE_TYPE is null) or "+
        "vrdl_as.product_busi_type is null) and ((',' || vrdl_as.develop_attribute like '%,' || t.DEVELOP_ATTRIBUTION || ',%' or t.DEVELOP_ATTRIBUTION is null) or "+
        "vrdl_as.develop_attribute is null) and ((',' || vrdl_as.product_brand like '%,' || t.BRAND_CODE || ',%' or t.BRAND_CODE is null) or vrdl_as.product_brand is null) "+
//        "and vrdl_as.oper_id = 'ca' and  vrdl_as.oper_id is not null "+
        "and ((',' || vrdl_as.OPER_MODE like'%,' || t.PRODUCTION_PATTERN_FIRST || ',%' or t.PRODUCTION_PATTERN_FIRST is null) or vrdl_as.OPER_MODE is null) " +
//        "and t.source_from is not null and t.other_deal_status is null ";
		"and vrdl_as.oper_id = ? and  vrdl_as.oper_id is not null ) and (t.other_deal_status > '01' or t.other_deal_status < '00' or t.other_deal_status is null) and t.source_from is not null and t.other_deal_status is null ";
	}
	
	public String getUpdateReissueDeliveryItemId(){
		return "update es_delivery_item i set i.delivery_id=? where i.delivery_id is null and i.order_id=?";
	}
	public String getUpdateOrderOuterServiceCode(){
		return "update es_order_outer o set o.service_code=? where o.order_id = ? ";
	}
	
	public String getUpdateDItemDeliveryId(){
		return "update es_delivery_item d set d.delivery_id=? where d.item_id=?";
	}
	
	public String getDelDeliveryItem(){
		return "delete from es_delivery_item t where t.item_id=?";
	}
	public String getOUTER_ORDER_ID_NOTEQ_BATCH(){
		return "select order_id from es_order_outer t where t.old_sec_order_id=? and t.order_from=? and batch_id <> ? and rownum<2 ";
	}
	
	public String getOUTER_ORDERS_ID_NOTEQ_BATCH(){
		return "select order_id from es_order_outer t where t.old_sec_order_id=? and t.order_from=? and batch_id <> ? ";
	}
	
	
	public String getOUTER_ORDER_ID_EQ_BATCH(){
		return "select order_id from es_order_outer t where t.old_sec_order_id=? and t.order_from=? and batch_id = ? ";
	}
	
	
	public String getORDER_ID_BY_OUTERID(){
		return "select z_order_id from es_order_rel t,es_order o where t.a_order_id=?  and t.z_order_id = o.order_id and o.service_code = ?  and o.source_from ='"+ManagerUtils.getSourceFrom()+"'  ";
	}
	
	public String getORDER_OUTER_ID_BY_ORDERID(){
		return "select a_order_id from es_order_rel t where t.z_order_id=?  ";
	}
	
	
	public String getUPDATE_UNIMALL_ORDER(){
		return "select o.order_id orderid,o.LOCK_USER lockuser,t.oper_name lockUserName,to_char(o.lock_Time,'yyyy-MM-dd hh24:Mi:ss') lockTimeStr,o.DEAL_SORT dealSort,o.exception_Order_Flag exceptionOrderFlag, "+
				"o.auto_order_exception_flag autoOrderExceptionFlag ,o.order_Create_Time orderCreateTime,to_char(o.order_create_time, 'yyyy-MM-dd hh24:Mi:ss') orderCreateTimeStr,to_number(t.order_pri) orderPri, "+
				"o.recoverty_type recovertyType,t.first_save_flag firstSaveFlag,o.society_flag societyFlag,o.bespoke_flag bespokeFlag,o.Send_Zb_Flag sendZbFlag,o.order_origin orderOrigin, "+
				"o.ORDER_ORGINID orderOrginid,t.pay_datetime payTime,o.flow_node flowNode,oc.post_type ,oc.carry_mode,op.product_type,o.END_AGENT,o.lock_status,o.EXT_ORDER_STATUS, "+
				"t.sp_prod_type,o.ORDER_REGION,o.ORDER_SON_TYPE,op.PACK_TYPE,o.ORDER_DEAL_TYPE,oai.PACKAGE_TYPE,o.DEVELOP_ATTRIBUTION,op.BRAND_CODE,o.ESS_ACCOUNT_FLAG, "+
				"o.ORDER_TYPE,o.OTHER_DEAL_USER,o.outer_order_id,opay.pay_type,customer.pet_name,op.mobile_Tel,op.handset_type,op.buyer_Name, "+
				"op.series_num,o.is_need_bss,o.flow_node_status,o.other_deal_status,exc.deal_flag,exc.exception_type,o.production_pattern,o.order_status, "+
				"o.agent_id,o.light_Ning_Status,o.sds_comp,sdi_sds.dict_code,opay.PAY_FEE fee,op.product_name from orders o  "+
				"left join v_task_orders_unique t on o.order_id=t.order_id "+
				"left join order_product op on t.ORDER_ID=op.order_id "+
				"left join order_account_info oai on oai.order_id=o.order_id "+
				"left join order_carry oc on oc.order_id=o.order_id "+
				"left join order_pay opay on opay.order_id=o.order_id "+
				"left join order_customer customer on customer.order_id=o.order_id "+
				"left join order_exception_info exc on exc.order_id=o.order_id "+             
				"left join sys_dict_item sdi_sds on sdi_sds.dict_code=o.order_deal_type "+
				"left join at_account_info aai on aai.info_order_id=o.order_id "+
				"where 1=1 and o.order_id=? and rownum<=1";
	}
	
	public String getBATCHINSERTATTRINST(){
		return "insert into es_attr_inst(attr_inst_id, order_id, order_item_id, inst_id, attr_spec_id, field_attr_id, "+
				" field_name, filed_desc, field_value, field_value_desc, modify_field_value, "+
				" sec_field_value, sec_filed_value_desc, create_date, source_from ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,"+DBTUtil.current()+",?)";
	}
	
	public String getQUERYORDERHANDLERLOGS(){
		return "select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id,c.realname op_name,t.type_code,t.handler_type,t.exception_id,d.pname type_name,n.pname trace_name " +
				"from es_order_handle_logs t left join es_dc_public_ext d on(t.source_from=d.source_from and d.pkey=t.type_code and d.stype='DIC_HANDLE_TYPE_'||t.flow_trace_id) "+
				" left join es_dc_public_ext n on(t.source_from=n.source_from and t.flow_trace_id=n.pkey and n.stype='DIC_ORDER_NODE') left join es_adminuser c on t.op_id=c.userid"+
				" where t.order_id=? and t.source_from='"+ManagerUtils.getSourceFrom()+"' ";
		// and t.handler_type='DIC_HANDLE_TYPE' order by t.create_time desc
	}
	
	public String getLISTORDERROLEDATA(){
		return "select a.* from es_role_data a ";
	}
	
	public String getCOUNT_ORDER_AUTHORITY_BY_USER(){
		return "select count(1) from es_order_role ord, es_user_role ur, es_role_auth ra" + 
				" where ord.role_id = ur.roleid and ra.roleid = ord.role_id and ord.auth_id = ra.authid" + 
				" and ord.source_from = ur.source_from and ur.source_from = ra.source_from and ord.source_from = '"+ManagerUtils.getSourceFrom()+"'" + 
				" and ur.userid=? and ord.order_id = ?";
	}
	
	public String getAGENTAUTOSQL(){
		return "select count(*) from es_user_role ur,es_role_data rd,es_role_agent rag where ur.roleid=rd.role_code and rd.role_code=rag.role_code and ur.source_from=rd.source_from "+
			" and rd.source_from=rag.source_from and rag.agent_code=? and ur.userid=?";
	}

	public String getINSERTORDERROLE(){
		return "insert into es_order_role(order_id,role_id,source_from,auth_id) values(?,?,?,?) ";
	}
	
	public String getDELETEORDERROLE(){
		return "delete from es_order_role where order_id=?";
	}
	
	public String getLISTATTRDEF(){
		return "select * from es_attr_def t where t.attr_spec_id=?";
	}
	
	public String getLISTGOODSTYPE(){
		return "select * from es_goods_type t";
	}
	
	public String getQUERYATTRTABLECACHE(){
		return "select t.*,a.field_name as def_field_name, a.rel_table_name from es_attr_table t,es_attr_def a where t.attr_def_id=a.field_attr_id and t.source_from=a.source_from and t.source_from=?";
	}
	
	public String getQUERYORDERSYSCODE(){
		return "select t.sys_code from es_outer_accept oa,es_order_ext e,es_order_extvtl t where oa.out_tid=e.out_tid and e.order_id=t.order_id and oa.order_id=? and oa.source_from=e.source_from and e.source_from=t.source_from and t.source_from=? "+
				" union all "+
				" select t.sys_code from es_outer_accept oa,es_order_ext_his e,es_order_extvtl_his t where oa.out_tid=e.out_tid and e.order_id=t.order_id and oa.order_id=? and oa.source_from=e.source_from and e.source_from=t.source_from and t.source_from=?";
	}
	
	public String getHUODONG_BUY_NUM_UPDATE(){
		return "update es_promotion t set t.buy_num=buy_num + #buy_num where t.pmt_id in(select pmt_id from es_order_pmt where order_id=? ) ";
	}
	
	public String getLIST_MATCH_DICT() {
		return "SELECT T.DICT_ID, T.CATALOG_ID, T.DICT_DESC, T.DICT_NAME, T.DICT_MATCH_CONTENT, T.DICT_MATCH_TYPE, T.SOURCE_FROM FROM ES_DATA_PRASER_DICT T";
	}
	
	public String getEXC_LOGS() {
		return "select e.* from es_order_exception_logs e where e.source_from = '"+ManagerUtils.getSourceFrom()+"' and e.order_id = ? ";
	}
	
	public String getEXC_LOGS_HIS() {
		return "select e.* from es_order_exception_logs_his e where e.source_from = '"+ManagerUtils.getSourceFrom()+"' and e.order_id = ? ";
	}
	//======================ecsord_server=============================================
	
	//======================ecsord_server=============================================
	
}
