package com.ztesoft.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import services.ServiceBase;
import zte.params.order.resp.OrderTaobaoSyncResp;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.model.OrderItemAddAccept;
import com.ztesoft.net.sqls.SF;

public class OrderGetManager extends ServiceBase {
	private ICommClientBO commClientBO;
	private ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	public String taobaoOrderSync(String orderId,String toSysName,String serial_no) {
		List<String> params = new ArrayList<String>();
		OrderTaobaoSyncResp resp = new OrderTaobaoSyncResp();
		String orderSql = SF.orderSql("OrderListForOut");
		String itemSql = SF.orderSql("OrderItmeForOut");
		String acceptSql = SF.orderSql("OrderAcceptForOut");
		List<Order> orderlist = new ArrayList<Order>();

		orderSql += " and  t.order_id = '" + orderId+"'";
		Page webpage = baseDaoSupport.queryForPage(orderSql, 1, 100,
				Order.class, params.toArray(new String[] {}));
		orderlist = webpage.getResult();
		for (int i = 0; i < orderlist.size(); i++) {
			Order order = orderlist.get(i);
			String order_id = order.getOrder_id();
			if (StringUtils.isNotEmpty(order_id)) {
				List<OrderItem> itemList = baseDaoSupport.queryForList(itemSql,
						OrderItem.class, new String[] { order_id });
				List<OrderItemAddAccept> acceptList = baseDaoSupport
						.queryForList(acceptSql, OrderItemAddAccept.class,
								new String[] { order_id });
				order.setOrderItemList(itemList);
				order.setOrderItemAcceptList(acceptList);
			}
		}

		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setOrderSyncList(orderlist);

		return taobaoOrdertoJson(resp, toSysName,serial_no);
	}
	
	public static String ClobToString(Clob cb){
		 try    
	        {    
	            Reader inStreamDoc = cb.getCharacterStream();    
	            char[] tempDoc = new char[(int) cb.length()];    
	            inStreamDoc.read(tempDoc);    
	            inStreamDoc.close();    
	            return new String(tempDoc);    
	        }    
	        catch (IOException e)    
	        {    
	            e.printStackTrace();    
	        }    
	        catch (SQLException es)    
	        {    
	            es.printStackTrace();    
	        }    
	        return null;    
	}
	
	public String getOrderListForOut(){return "select * from es_order t where t.app_key='taobao' ";}

	public String getOrderItmeForOut(){return "select * from es_order_items i where i.order_id=?";}
	
	public String getOrderAcceptForOut(){return "select * from es_outer_accept a where a.order_id=?";}
	
	
	public String taobaoOrderGet(String orderId,String toSysName,String serial_no) {
		List<String> params = new ArrayList<String>();
		OrderTaobaoSyncResp resp = new OrderTaobaoSyncResp();
		String orderSql = "select * from es_order t where t.app_key='taobao' ";
		List<Order> orderlist = new ArrayList<Order>();

		orderSql += " and  t.order_id = '" + orderId+"'";


			List orders = getICommClientBO().queryForList(orderSql);
			for (int i = 0; i < orders.size(); i++) {
				Map m =(Map) orders.get(i);
				Order order = new Order();
				order.setOrder_id(getValue(m.get("order_id")));
				order.setSn(getValue(m.get("sn")));
				order.setMember_id(getValue(m.get("member_id")));
				if(getValue(m.get("status")) != null  && !"".equalsIgnoreCase(getValue(m.get("status")))){
					order.setStatus(Integer.parseInt(getValue(m.get("status"))));;
				}
				if(getValue(m.get("pay_status")) != null  && !"".equalsIgnoreCase(getValue(m.get("pay_status")))){
					order.setPay_status(Integer.parseInt(getValue(m.get("pay_status"))));;
				}
				if(getValue(m.get("ship_status")) != null  && !"".equalsIgnoreCase(getValue(m.get("ship_status")))){
					order.setShip_status(Integer.parseInt(getValue(m.get("ship_status"))));;
				}
				order.setShipStatus(getValue(m.get("shipStatus")));
				order.setPayStatus(getValue(m.get("payStatus")));
				order.setOrderStatus(getValue(m.get("orderStatus")));
				order.setAcceptStatus(getValue(m.get("acceptStatus")));
				if(getValue(m.get("regionid")) != null  && !"".equalsIgnoreCase(getValue(m.get("regionid")))){
					order.setRegionid(new Long(getValue(m.get("regionid"))));;
				}
				order.setShipping_id(getValue(m.get("shipping_id")));
				order.setShipping_type(getValue(m.get("shipping_type")));
				order.setShipping_area(getValue(m.get("shipping_area")));
				order.setUserid(getValue(m.get("userid")));
				order.setSource_from(getValue(m.get("source_from")));
				order.setGoods(getValue(m.get("goods")));
				order.setCreate_time(getValue(m.get("create_time")));
				order.setShip_name(getValue(m.get("ship_name")));
				order.setShip_addr(getValue(m.get("ship_addr")));
				order.setShip_zip(getValue(m.get("ship_zip")));
				order.setShip_email(getValue(m.get("ship_email")));
				order.setShip_mobile(getValue(m.get("ship_mobile")));
				order.setShip_tel(getValue(m.get("ship_tel")));
				order.setShip_day(getValue(m.get("ship_day")));
				order.setShip_time(getValue(m.get("ship_time")));
				
				if(getValue(m.get("is_protect")) != null  && !"".equalsIgnoreCase(getValue(m.get("is_protect")))){
					order.setIs_protect(Integer.parseInt(getValue(m.get("is_protect"))));;
				}
				if(getValue(m.get("protect_price")) != null  && !"".equalsIgnoreCase(getValue(m.get("protect_price")))){
					order.setProtect_price(Double.parseDouble(getValue(m.get("protect_price"))));;
				}
				if(getValue(m.get("goods_amount")) != null  && !"".equalsIgnoreCase(getValue(m.get("goods_amount")))){
					order.setGoods_amount(Double.parseDouble(getValue(m.get("goods_amount"))));;
				}
				if(getValue(m.get("shipping_amount")) != null  && !"".equalsIgnoreCase(getValue(m.get("shipping_amount")))){
					order.setShipping_amount(Double.parseDouble(getValue(m.get("shipping_amount"))));;
				}
				if(getValue(m.get("discount")) != null  && !"".equalsIgnoreCase(getValue(m.get("discount")))){
					order.setDiscount(Double.parseDouble(getValue(m.get("discount"))));;
				}
				if(getValue(m.get("order_amount")) != null  && !"".equalsIgnoreCase(getValue(m.get("order_amount")))){
					order.setOrder_amount(Double.parseDouble(getValue(m.get("order_amount"))));;
				}
				if(getValue(m.get("weight")) != null  && !"".equalsIgnoreCase(getValue(m.get("weight")))){
					order.setWeight(Double.parseDouble(getValue(m.get("weight"))));;
				}
				if(getValue(m.get("paymoney")) != null  && !"".equalsIgnoreCase(getValue(m.get("paymoney")))){
					order.setPaymoney(Double.parseDouble(getValue(m.get("paymoney"))));;
				}
				order.setRemark(getValue(m.get("remark")));
				if(getValue(m.get("disabled")) != null  && !"".equalsIgnoreCase(getValue(m.get("disabled")))){
					order.setDisabled(Integer.parseInt(getValue(m.get("disabled"))));;
				}
				if(getValue(m.get("payment_id")) != null  && !"".equalsIgnoreCase(getValue(m.get("payment_id")))){
					order.setPayment_id(Integer.parseInt(getValue(m.get("payment_id"))));;
				}
				order.setPayment_name(getValue(m.get("payment_name")));
				order.setPayment_type(getValue(m.get("payment_type")));
				if(getValue(m.get("goods_num")) != null  && !"".equalsIgnoreCase(getValue(m.get("goods_num")))){
					order.setGoods_num(Integer.parseInt(getValue(m.get("goods_num"))));;
				}
				if(getValue(m.get("gainedpoint")) != null  && !"".equalsIgnoreCase(getValue(m.get("gainedpoint")))){
					order.setGainedpoint(Integer.parseInt(getValue(m.get("gainedpoint"))));;
				}
				if(getValue(m.get("consumepoint")) != null  && !"".equalsIgnoreCase(getValue(m.get("consumepoint")))){
					order.setConsumepoint(Integer.parseInt(getValue(m.get("consumepoint"))));;
				}
				order.setBank_id(getValue(m.get("bank_id")));
				order.setOrder_type(getValue(m.get("order_type")));
				order.setTransaction_id(getValue(m.get("transaction_id")));
				order.setType_code(getValue(m.get("type_code")));
				if(getValue(m.get("accept_status")) != null  && !"".equalsIgnoreCase(getValue(m.get("accept_status")))){
					order.setAccept_status(Integer.parseInt(getValue(m.get("accept_status"))));;
				}
				order.setGoods_id(getValue(m.get("goods_id")));
				order.setLan_id(getValue(m.get("lan_id")));
				order.setDeal_message(getValue(m.get("deal_message")));
				order.setOrderprice(null);
				order.setPay_time(getValue(m.get("pay_time")));
				order.setGoods_name(getValue(m.get("goods_name")));
				order.setUser_name(getValue(m.get("user_name")));
				order.setOrderType(getValue(m.get("orderType")));
				order.setOper_btns(getValue(m.get("oper_btns")));
				order.setLan_name(getValue(m.get("lan_name")));
				order.setAudit_state(getValue(m.get("audit_state")));
				order.setAcc_nbr(getValue(m.get("acc_nbr")));
				order.setBill_flag(getValue(m.get("bill_flag")));
				order.setBatch_id(getValue(m.get("batch_id")));
//				order.setInvoice_title(getValue(m.get("invoice_title")));
				if(getValue(m.get("invoice_type")) != null  && !"".equalsIgnoreCase(getValue(m.get("invoice_type")))){
					order.setInvoice_type(Integer.parseInt(getValue(m.get("invoice_type"))));;
				}
				order.setInvoice_title_desc(getValue(m.get("invoice_title_desc")));
//				order.setInvoice_content(getValue(m.get("invoice_content")));
				order.setLast_update(getValue(m.get("last_update")));
				if(getValue(m.get("create_type")) != null  && !"".equalsIgnoreCase(getValue(m.get("create_type")))){
					order.setCreate_type(Integer.parseInt(getValue(m.get("create_type"))));;
				}
				order.setSource_shop_name(getValue(m.get("source_shop_name")));
				order.setOrder_adscription_id(getValue(m.get("order_adscription_id")));
				if(getValue(m.get("pay_type")) != null  && !"".equalsIgnoreCase(getValue(m.get("pay_type")))){
					order.setPay_type(Integer.parseInt(getValue(m.get("pay_type"))));;
				}
				if(getValue(m.get("confirm_status")) != null  && !"".equalsIgnoreCase(getValue(m.get("confirm_status")))){
					order.setConfirm_status(Integer.parseInt(getValue(m.get("confirm_status"))));;
				}
				order.setConfirm_time(getValue(m.get("confirm_time")));
				order.setConfirm_group_id(getValue(m.get("confirm_group_id")));
				order.setConfirm_user_id(getValue(m.get("confirm_user_id")));
				order.setShip_assign_time(getValue(m.get("ship_assign_time")));
				order.setShip_group_id(getValue(m.get("ship_group_id")));
				order.setShip_user_id(getValue(m.get("ship_user_id")));
				order.setAccept_assign_time(getValue(m.get("accept_assign_time")));
				order.setAccept_group_id(getValue(m.get("accept_group_id")));
				order.setAccept_user_id(getValue(m.get("accept_user_id")));
				order.setQuery_group_id(getValue(m.get("query_group_id")));
				order.setQuery_user_id(getValue(m.get("query_user_id")));
				order.setPay_assign_time(getValue(m.get("pay_assign_time")));
				order.setPay_group_id(getValue(m.get("pay_group_id")));
				order.setPay_user_id(getValue(m.get("pay_user_id")));
				if(getValue(m.get("taxes")) != null  && !"".equalsIgnoreCase(getValue(m.get("taxes")))){
					order.setTaxes(Double.parseDouble(getValue(m.get("taxes"))));;
				}
				if(getValue(m.get("order_discount")) != null  && !"".equalsIgnoreCase(getValue(m.get("order_discount")))){
					order.setOrder_discount(Float.parseFloat(getValue(m.get("order_discount"))));;
				}
				if(getValue(m.get("order_coupon")) != null  && !"".equalsIgnoreCase(getValue(m.get("order_coupon")))){
					order.setOrder_coupon(Double.parseDouble(getValue(m.get("order_coupon"))));;
				}
				if(getValue(m.get("order_record_status")) != null  && !"".equalsIgnoreCase(getValue(m.get("order_record_status")))){
					order.setOrder_record_status(Integer.parseInt(getValue(m.get("order_record_status"))));;
				}
				order.setOrderItemList(null);
				order.setCanPay(getValue(m.get("canpay")));
				order.setCanShip(getValue(m.get("canship")));
				order.setUname(getValue(m.get("uname")));
				order.setSpread_id(getValue(m.get("spread_id")));
				order.setService_type(getValue(m.get("service_type")));
				order.setService_id(getValue(m.get("service_id")));
				order.setCreaterOrder(getValue(m.get("createrOrder")));
				order.setService_code(getValue(m.get("service_code")));
				order.setApp_key(getValue(m.get("app_key")));
				order.setOrderItemAcceptList(null);
				orderlist.add(order);
			}
			
			for (int i = 0; i < orderlist.size(); i++) {
				Order order = orderlist.get(i);
				String order_id = order.getOrder_id();
				if (StringUtils.isNotEmpty(order_id)) {
					List items = getICommClientBO().queryForList("select * from es_order_items i where i.order_id= '"+order_id+"' ");
					List<OrderItem> itemList = new ArrayList<OrderItem>();
					for (int j = 0; j < items.size(); j++){
						OrderItem item = new OrderItem();
						Map m =(Map) items.get(j);
						item.setItem_id(getValue(m.get("item_id")));
						item.setOrder_id(getValue(m.get("order_id")));
						item.setGoods_id(getValue(m.get("goods_id")));
						item.setSpec_id(getValue(m.get("spec_id")));
						if(getValue(m.get("num")) != null  && !"".equalsIgnoreCase(getValue(m.get("num")))){
							item.setNum(Integer.parseInt(getValue(m.get("num"))));;
						}
						if(getValue(m.get("ship_num")) != null  && !"".equalsIgnoreCase(getValue(m.get("ship_num")))){
							item.setShip_num(Integer.parseInt(getValue(m.get("ship_num"))));;
						}
						item.setName(getValue(m.get("name")));
						item.setSn(getValue(m.get("sn")));
						if(getValue(m.get("store")) != null  && !"".equalsIgnoreCase(getValue(m.get("store")))){
							item.setStore(Integer.parseInt(getValue(m.get("store"))));;
						}
						item.setAddon(getValue(m.get("addon")));
						item.setType_id(getValue(m.get("type_id")));
						item.setHave_stock(getValue(m.get("have_stock")));
						item.setType_code(getValue(m.get("type_code")));
						if(getValue(m.get("price")) != null  && !"".equalsIgnoreCase(getValue(m.get("price")))){
							item.setPrice(Double.parseDouble(getValue(m.get("price"))));;
						}
						if(getValue(m.get("gainedpoint")) != null  && !"".equalsIgnoreCase(getValue(m.get("gainedpoint")))){
							item.setGainedpoint(Integer.parseInt(getValue(m.get("gainedpoint"))));;
						}
						item.setOrder_outer_id(getValue(m.get("order_outer_id")));
						item.setSource_from(getValue(m.get("source_from")));
						item.setBack_num(getValue(m.get("back_num")));
						item.setOrder_apply_id(getValue(m.get("order_apply_id")));
						item.setLv_id(getValue(m.get("lv_id")));
						if(getValue(m.get("coupon_price")) != null  && !"".equalsIgnoreCase(getValue(m.get("coupon_price")))){
							item.setCoupon_price(Double.parseDouble(getValue(m.get("coupon_price"))));;
						}
						item.setUnit(getValue(m.get("unit")));
						item.setAction_code(getValue(m.get("action_code")));
						item.setActionRuleList(null);
						item.setStatus(getValue(m.get("status")));
						if(getValue(m.get("item_type")) != null  && !"".equalsIgnoreCase(getValue(m.get("item_type")))){
							item.setItem_type(Integer.parseInt(getValue(m.get("item_type"))));;
						}
						item.setProduct_id(getValue(m.get("product_id")));
						itemList.add(item);
					}
					List accepts = getICommClientBO().queryForList("select * from es_taobao_accept a where a.order_id= '"+order_id+"' ");
					
					List<OrderItemAddAccept> acceptList = new ArrayList<OrderItemAddAccept>();
					for (int j = 0; j < accepts.size(); j++){
						OrderItemAddAccept accept = new OrderItemAddAccept();
						Map m =(Map) accepts.get(j);
						accept.setItem_id(getValue(m.get("item_id")));
						accept.setOrder_id(getValue(m.get("order_id")));
						accept.setGoods_id(getValue(m.get("goods_id")));
						accept.setSpec_id(getValue(m.get("spec_id")));
						accept.setNum(getValue(m.get("num")));
						accept.setShip_num(getValue(m.get("ship_num")));
						accept.setName(getValue(m.get("name")));
						accept.setPrice(getValue(m.get("price")));
						accept.setGainedpoint(getValue(m.get("gainedpoint")));
						accept.setState(getValue(m.get("state")));
						accept.setAddon(getValue(m.get("addon")));
						accept.setLv_id(getValue(m.get("lv_id")));
						accept.setCoupon_price(getValue(m.get("coupon_price")));
						accept.setUnit(getValue(m.get("unit")));
						accept.setSource_from(getValue(m.get("source_from")));
						accept.setItem_type(getValue(m.get("item_type")));
						accept.setProduct_id(getValue(m.get("product_id")));
						accept.setPro_detail_code(getValue(m.get("pro_detail_code")));
						accept.setPro_name(getValue(m.get("pro_name")));
						accept.setPro_type(getValue(m.get("pro_type")));
						accept.setIsgifts(getValue(m.get("isgifts")));
						if(getValue(m.get("goods_num")) != null  && !"".equalsIgnoreCase(getValue(m.get("goods_num")))){
							accept.setGoods_num(Integer.parseInt(getValue(m.get("goods_num"))));;
						}
						if(getValue(m.get("sell_price")) != null  && !"".equalsIgnoreCase(getValue(m.get("sell_price")))){
							accept.setSell_price(Double.parseDouble(getValue(m.get("sell_price"))));;
						}
						if(getValue(m.get("pro_origfee")) != null  && !"".equalsIgnoreCase(getValue(m.get("pro_origfee")))){
							accept.setPro_origfee(Double.parseDouble(getValue(m.get("pro_origfee"))));;
						}
						accept.setBrand_number(getValue(m.get("brand_number")));
						accept.setBrand_name(getValue(m.get("brand_name")));
						accept.setColor_code(getValue(m.get("color_code")));
						accept.setColor_name(getValue(m.get("color_name")));
						accept.setSpecification_code(getValue(m.get("specification_code")));
						accept.setSpecificatio_nname(getValue(m.get("specificatio_nname")));
						accept.setModel_code(getValue(m.get("model_code")));
						accept.setModel_name(getValue(m.get("model_name")));
						accept.setTerminal_num(getValue(m.get("terminal_num")));
						accept.setPhone_num(getValue(m.get("phone_num")));
						accept.setPhone_owner_name(getValue(m.get("phone_owner_name")));
						accept.setCerti_type(getValue(m.get("certi_type")));
						accept.setCerti_number(getValue(m.get("certi_number")));
						accept.setCert_failure_time(getValue(m.get("cert_failure_time")));
						accept.setCert_address(getValue(m.get("cert_address")));
						accept.setWhite_cart_type(getValue(m.get("white_cart_type")));
						accept.setFirst_payment(getValue(m.get("first_payment")));
						accept.setCollection(getValue(m.get("collection")));
						accept.setOut_package_id(getValue(m.get("out_package_id")));
						accept.setPlan_title(getValue(m.get("plan_title")));
						accept.setOut_plan_id_ess(getValue(m.get("out_plan_id_ess")));
						accept.setOut_plan_id_bss(getValue(m.get("out_plan_id_bss")));
						accept.setReliefpres_flag(getValue(m.get("reliefpres_flag")));
						accept.setSimid(getValue(m.get("simid")));
						accept.setProduct_net(getValue(m.get("product_net")));
						accept.setAct_protper(getValue(m.get("act_protper")));
						if(getValue(m.get("phone_deposit")) != null  && !"".equalsIgnoreCase(getValue(m.get("phone_deposit")))){
							accept.setPhone_deposit(Double.parseDouble(getValue(m.get("phone_deposit"))));;
						}
						accept.setFamliy_num(getValue(m.get("famliy_num")));
						accept.setAdjustment_credit(getValue(m.get("adjustment_credit")));
						accept.setSuperiors_bankcode(getValue(m.get("superiors_bankcode")));
						accept.setBank_code(getValue(m.get("bank_code")));
						accept.setBank_account(getValue(m.get("bank_account")));
						accept.setBank_user(getValue(m.get("bank_user")));
						accept.setIs_iphone_plan(getValue(m.get("is_iphone_plan")));
						accept.setIs_loves_phone(getValue(m.get("is_loves_phone")));
						accept.setLoves_phone_num(getValue(m.get("loves_phone_num")));
						accept.setIs_liang(getValue(m.get("is_liang")));
						if(getValue(m.get("liang_price")) != null  && !"".equalsIgnoreCase(getValue(m.get("liang_price")))){
							accept.setLiang_price(Double.parseDouble(getValue(m.get("liang_price"))));;
						}
						accept.setLiang_code(getValue(m.get("liang_code")));
						accept.setIs_stock_phone(getValue(m.get("is_stock_phone")));
						accept.setAtive_type(getValue(m.get("ative_type")));
						accept.setDisfee_select(getValue(m.get("disfee_select")));
						accept.setSociety_name(getValue(m.get("society_name")));
						if(getValue(m.get("society_price")) != null  && !"".equalsIgnoreCase(getValue(m.get("society_price")))){
							accept.setSociety_price(Double.parseDouble(getValue(m.get("society_price"))));;
						}
						accept.setOrder_channel(getValue(m.get("order_channel")));
						accept.setOrder_from(getValue(m.get("order_from")));
						accept.setOrder_type(getValue(m.get("order_type")));
						accept.setAbnormal_status(getValue(m.get("abnormal_status")));
						accept.setMerge_status(getValue(m.get("merge_status")));
						accept.setDelivery_status(getValue(m.get("delivery_status")));
						accept.setPlatform_status(getValue(m.get("platform_status")));
						accept.setPlat_type(getValue(m.get("plat_type")));
						accept.setTid_time(getValue(m.get("tid_time")));
						accept.setTid_time(getValue(m.get("pay_time")));
						accept.setService_remarks(getValue(m.get("service_remarks")));
						accept.setBuyer_id(getValue(m.get("buyer_id")));
						accept.setBuyer_name(getValue(m.get("buyer_name")));
						accept.setProvince(getValue(m.get("province")));
						accept.setCity(getValue(m.get("city")));
						accept.setDistrict(getValue(m.get("district")));
						accept.setProvinc_code(getValue(m.get("provinc_code")));
						accept.setCity_code(getValue(m.get("city_code")));
						accept.setArea_code(getValue(m.get("area_code")));
						accept.setSending_type(getValue(m.get("sending_type")));
						accept.setIs_bill(getValue(m.get("is_bill")));
						accept.setInvoice_print_type(getValue(m.get("invoice_print_type")));
						accept.setRecommended_name(getValue(m.get("recommended_name")));
						accept.setRecommended_code(getValue(m.get("recommended_code")));
						accept.setRecommended_phone(getValue(m.get("recommended_phone")));
						accept.setDevelopment_code(getValue(m.get("development_code")));
						accept.setDevelopment_name(getValue(m.get("development_name")));
						accept.setFile_return_type(getValue(m.get("file_return_type")));
						accept.setTid_category(getValue(m.get("tid_category")));
						accept.setCollection_free(getValue(m.get("collection_free")));
						accept.setVouchers_money(getValue(m.get("vouchers_money")));
						accept.setVouchers_code(getValue(m.get("vouchers_code")));
						accept.setDisfee_type(getValue(m.get("disfee_type")));
						accept.setPromotion_area(getValue(m.get("promotion_area")));
						accept.setOne_agents_id(getValue(m.get("one_agents_id")));
						accept.setTwo_agents_id(getValue(m.get("two_agents_id")));
						accept.setWm_order_from(getValue(m.get("wm_order_from")));
						accept.setWt_paipai_order_id(getValue(m.get("wt_paipai_order_id")));
						accept.setCouponname(getValue(m.get("couponname")));
						accept.setCouponbatchid(getValue(m.get("couponbatchid")));
						accept.setOrderacctype(getValue(m.get("orderacctype")));
						accept.setOrderacccode(getValue(m.get("orderacccode")));
						accept.setPaytype(getValue(m.get("paytype")));
						accept.setPayfintime(getValue(m.get("payfintime")));
						accept.setPayplatformorderid(getValue(m.get("payplatformorderid")));
						accept.setPayproviderid(getValue(m.get("payproviderid")));
						accept.setPayprovidername(getValue(m.get("payprovidername")));
						accept.setPaychannelid(getValue(m.get("paychannelid")));
						accept.setPaychannelname(getValue(m.get("paychannelname")));
						accept.setDiscountname(getValue(m.get("discountname")));
						accept.setDiscountid(getValue(m.get("discountid")));
						accept.setDiscountrange(getValue(m.get("discountrange")));
						accept.setBss_order_type(getValue(m.get("bss_order_type")));
						accept.setWm_isreservation_from(getValue(m.get("wm_isreservation_from")));
						accept.setOut_tid(getValue(m.get("out_tid")));
						accept.setOrder_provinc_code(getValue(m.get("order_provinc_code")));
						accept.setOrder_city_code(getValue(m.get("order_city_code")));
						accept.setOrder_disfee(getValue(m.get("order_disfee")));
						accept.setIs_adv_sale(getValue(m.get("is_adv_sale")));
						accept.setPlat_distributor_code(getValue(m.get("plat_distributor_code")));
						accept.setAlipay_id(getValue(m.get("alipay_id")));
						accept.setPay_mothed(getValue(m.get("pay_mothed")));
						accept.setOrder_realfee(getValue(m.get("order_realfee")));
						accept.setDiscountvalue(getValue(m.get("discountvalue")));
						accept.setCert_card_num(getValue(m.get("cert_card_num")));
						acceptList.add(accept);
					}
					if(itemList != null && itemList.size() > 0)
					order.setOrderItemList(itemList);
					if(acceptList != null && acceptList.size() > 0)
					order.setOrderItemAcceptList(acceptList);
				}
				
			
			}
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setOrderSyncList(orderlist);
		return taobaoOrdertoJson(resp, toSysName,serial_no);

	}
	//货品
	String getskuList(String orderId){
		StringBuffer skustr = new StringBuffer();
		try {
			List m1 = getICommClientBO()
					.queryForList("select * from es_goods g where g.goods_id in (select a.z_goods_id from es_goods_rel a where a.a_goods_id in (select i.goods_id from es_order_items i where i.order_id = "+orderId+"))");
			skustr.append("\"sku_list\":[");
			for(int i=0;i<m1.size();i++){
			Map map = (Map) m1.get(i);
			String sku = ClobToString((Clob)map.get("params"));
			skustr.append("{");
			String skuid = map.get("sku")==null?"null":map.get("sku").toString();
			String skuname = map.get("name").toString();
			String goodstype = map.get("type_id").toString();
			String isvirtual = "";
			String isgift = "";
			int skunum = m1.size();
			skustr.append("\"sku_id\":\""+skuid+"\",");
			skustr.append("\"goods_name\":\""+skuname+"\",");
			skustr.append("\"goods_type\":\""+goodstype+"\",");
			skustr.append("\"is_virtual\":\""+isvirtual+"\",");
			skustr.append("\"is_gift\":\""+isgift+"\",");
			skustr.append("\"sku_num\":\""+skunum+"\",");
			sku = sku.substring(1,sku.lastIndexOf("]"));
			skustr.append("\"sku_param\":[");
			JSONObject a1=(JSONObject) JSON.parse(sku);
			JSONArray aa1= (JSONArray) a1.get("paramList");
			
			for(int s=0;s<aa1.length();s++){
				JSONObject jsonobj = (JSONObject) aa1.get(s);
			String ename = jsonobj.getString("ename");
				String name = jsonobj.getString("name");
				String attrcode = jsonobj.getString("attrcode");
				String value = jsonobj.getString("value");
				skustr.append("{");
				skustr.append("\"param_code\":\""+ename+"\",");
				skustr.append("\"param_name\":\""+name+"\",");
				skustr.append("\"param_value_code\":\""+attrcode+"\",");
				skustr.append("\"param_value\":\""+value+"\"");
				skustr.append("}");
				if(s!=aa1.length()-1){
					skustr.append(",");
				}
			}
			
			
			skustr.append("]");
			skustr.append("}");
			if(i!=m1.size()-1){
				skustr.append(",");
			}
			}
			skustr.append("]");
			logger.info(skustr);
		//	logger.info(sku);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return skustr.toString();
		}
		
	
	//商品参数
	String getofferparam(String orderId){ 
      
		Map	offerMap = new HashMap();
		offerMap = getICommClientBO()
					.queryForMap("select a.params from es_goods a where a.goods_id in (select i.goods_id from es_order_items i where i.order_id = "+orderId+")");
		String mm = ClobToString((Clob)offerMap.get("params"));
		mm = mm.substring(1,mm.lastIndexOf("]"));
		
		StringBuffer osserString = new StringBuffer();
		osserString.append("\"offer_param\":[");
		try {
			JSONObject a=(JSONObject) JSON.parse(mm);
			JSONArray aa= (JSONArray) a.get("paramList");
			for(int s=0;s<aa.length();s++){
				JSONObject jsonobj = (JSONObject) aa.get(s);
				String ename = jsonobj.getString("ename");
				String name = jsonobj.getString("name");
				String attrcode = jsonobj.getString("attrcode");
				String value = jsonobj.getString("value");
				osserString.append("{");
				osserString.append("\"param_code\":\""+ename+"\",");
				osserString.append("\"param_name\":\""+name+"\",");
				osserString.append("\"param_value_code\":\""+attrcode+"\",");
				osserString.append("\"param_value\":\""+value+"\"");
				osserString.append("}");
				if(s!=aa.length()-1){
					osserString.append(",");
				}
			}
		}catch(Exception e){}
			osserString.append("]");
		
		return osserString.toString();
	}
	String taobaoOrdertoJson(OrderTaobaoSyncResp resp ,String toSysName,String serial_no){
		
		
		
		List<Order> orders = resp.getOrderSyncList();
		Order o = orders.get(0);
		String result = "";
    	SimpleDateFormat sdf_good =   new SimpleDateFormat( "yyyyMMddHHmmss" );
    	Date Current_date = new Date(System.currentTimeMillis());//获取系统当前时间
    	String req_date = sdf_good.format(Current_date);
    	String fromSysName = "ECS";
    	OrderItemAddAccept accept = new OrderItemAddAccept();
    	try{
    		accept = o.getOrderItemAcceptList().get(0);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
		String  offerStr=getofferparam(o.getOrder_id());
		String skuStr = getskuList(o.getOrder_id());
    	
    	String order_create_date = accept.getTid_time().replace("-", "").replace(":", "").trim();
    	String order_pay_date = accept.getPay_time().replace("-", "").replace(":", "").trim();
    	//----------------------------------------------------------------------------------
    	StringBuffer jsonStr=new StringBuffer();
    	jsonStr.append("{\"serial_no\":").append("\"").append(serial_no).append("\",");
    	jsonStr.append("\"time\":").append("\"").append(req_date).append("\",");
    	jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");
    	jsonStr.append("\"receive_system\":").append("\"").append("10009").append("\",");    	
    	jsonStr.append("\"source_from_system\":").append("\"").append(accept.getPlat_type()).append("\",");
    	jsonStr.append("\"order_city\":").append("\"").append(accept.getOrder_city_code()).append("\",");
    	jsonStr.append("\"channel_type\":").append("\"").append(accept.getPaytype()).append("\",");
        jsonStr.append("{\"channel_id\":").append("\"").append(accept.getPaychannelid()).append("\",");
    	jsonStr.append("\"chanel_name\":").append("\"").append(accept.getPaychannelname()).append("\",");
    	jsonStr.append("\"development_code\":").append("\"").append(accept.getDevelopment_code()).append("\",");
    	jsonStr.append("\"reference_phone\":").append("\"").append(accept.getRecommended_phone()).append("\",");
    	jsonStr.append("\"create_time\":").append("\"").append(order_create_date).append("\",");
    	jsonStr.append("\"pay_time\":").append("\"").append(order_pay_date).append("\",");
    	jsonStr.append("\"pay_type\":").append("\"").append(accept.getPaytype()).append("\",");
    	
    	
    	jsonStr.append("{\"payment_type\":").append("\"").append(accept.getPay_mothed()).append("\",");
    	jsonStr.append("\"payment_status\":").append("\"").append(o.getPayStatus()).append("\",");
    	jsonStr.append("\"payment_serial_no\":").append("\"").append("").append("\",");
    	jsonStr.append("\"payment_code\":").append("\"").append("").append("\",");    	
    	jsonStr.append("\"payment_name\":").append("\"").append("").append("\",");
    	jsonStr.append("\"payment_channel_code\":").append("\"").append(accept.getPaychannelid()).append("\",");
    	jsonStr.append("\"payment_channel_name\":").append("\"").append(accept.getPaychannelname()).append("\",");
        jsonStr.append("{\"order_amount\":").append("\"").append(o.getOrder_amount()).append("\",");
    	jsonStr.append("\"order_disacount\":").append("\"").append(accept.getDiscountvalue()).append("\",");
    	jsonStr.append("\"pay_money\":").append("\"").append(accept.getOrder_realfee()).append("\",");
    	jsonStr.append("\"o_shipping_amount\":").append("\"").append("").append("\",");
    	jsonStr.append("\"n_shipping_amount\":").append("\"").append("").append("\",");
    	jsonStr.append("\"shipping_company\":").append("\"").append("").append("\",");
    	jsonStr.append("\"shipping_company_name\":").append("\"").append(o.getShip_name()).append("\",");
    	
    	jsonStr.append("{\"shipping_type\":").append("\"").append(accept.getSending_type()).append("\",");
    	jsonStr.append("\"shipping_time\":").append("\"").append(o.getShip_time()).append("\",");
    	jsonStr.append("\"ship_name\":").append("\"").append(o.getShip_name()).append("\",");
    	jsonStr.append("\"ship_province\":").append("\"").append(accept.getProvinc_code()).append("\",");    	
    	jsonStr.append("\"ship_city\":").append("\"").append(accept.getOrder_city_code()).append("\",");
    	jsonStr.append("\"ship_country\":").append("\"").append("").append("\",");
    	jsonStr.append("\"ship_area\":").append("\"").append(accept.getArea_code()).append("\",");
        jsonStr.append("{\"ship_addr\":").append("\"").append(o.getShip_addr()).append("\",");
    	jsonStr.append("\"postcode\":").append("\"").append(o.getShip_zip()).append("\",");
    	jsonStr.append("\"ship_tel\":").append("\"").append(o.getShip_tel()).append("\",");
    	jsonStr.append("\"ship_phone\":").append("\"").append(o.getShip_tel()).append("\",");
    	jsonStr.append("\"ship_email\":").append("\"").append(o.getShip_tel()).append("\",");
    	jsonStr.append("\"cust_phone_no\":").append("\"").append("").append("\",");
    	jsonStr.append("\"cust_mobile_no\":").append("\"").append(o.getShip_mobile()).append("\",");
    	
    	
    	jsonStr.append("{\"cust_name\":").append("\"").append(accept.getBuyer_name()).append("\",");
    	jsonStr.append("\"cust_addr\":").append("\"").append(o.getShip_addr()).append("\",");
    	jsonStr.append("\"buyer_message\":").append("\"").append(o.getRemark()).append("\",");
    	jsonStr.append("\"seller_message\":").append("\"").append(accept.getService_remarks()).append("\",");    	
    	jsonStr.append("\"order_comment\":").append("\"").append(o.getRemark()).append("\",");
    	jsonStr.append("\"group_code\":").append("\"").append(accept.getArea_code()).append("\",");
    	jsonStr.append("\"group_name\":").append("\"").append("").append("\",");
        jsonStr.append("{\"industry_type\":").append("\"").append("").append("\",");
    	jsonStr.append("\"Industry_sub_type\":").append("\"").append("").append("\",");
//    	jsonStr.append("\"baidu_account\":").append("\"").append(accept.getBaidu_id()).append("\",");
//    	jsonStr.append("\"baidu_no\":").append("\"").append(accept.getFreeze_tran_no()).append("\",");
//    	jsonStr.append("\"baidu_money\":").append("\"").append(accept.getFreeze_free()).append("\",");
    	
    	jsonStr.append("\"order_list[\":");
        List<OrderItem> orderItemList = o.getOrderItemList();
    	if(orderItemList!=null && orderItemList.size()>0){
    		for(int i = 0 ; i <orderItemList.size() ; i++ ){
    			OrderItem oi = orderItemList.get(i);
    			double offer_disacount_price = oi.getPrice()-oi.getCoupon_price();
    	    	jsonStr.append("\"prod_offer_code\":").append("\"").append(oi.getGoods_id()).append("\",");
    	    	jsonStr.append("\"prod_offer_name\":").append("\"").append(accept.getPro_name()).append("\",");
    	    	jsonStr.append("\"prod_offer_type\":").append("\"").append(oi.getType_code()).append("\",");
    	    	jsonStr.append("\"offer_price\":").append("\"").append(oi.getPrice()).append("\",");
    	    	
    	    	jsonStr.append("\"offer_disacount_price\":").append("\"").append(offer_disacount_price).append("\",");
    	    	jsonStr.append("\"offer_coupon_price\":").append("\"").append(oi.getCoupon_price()).append("\",");
    	    	jsonStr.append("\"prod_offer_num\":").append("\"").append(oi.getNum()).append("\",");
    	    	jsonStr.append("\"invoice_type\":").append("\"").append(o.getInvoice_type()).append("\",");
    	    	
    	    	jsonStr.append("\"invoice_print_type\":").append("\"").append(accept.getInvoice_print_type()).append("\",");
    	    	jsonStr.append("\"invoice_title\":").append("\"").append(o.getInvoice_title()).append("\",");
    	    	jsonStr.append("\"invoice_content\":").append("\"").append(o.getInvoice_content()).append("\",");
    	    	jsonStr.append("\"inventory_code\":").append("\"").append("").append("\",");
    	    	
    	    	jsonStr.append("\"inventory_name\":").append("\"").append("").append("\",");
    	    	jsonStr.append("\"offer_comment\":").append("\"").append("").append("\",");
    	    	
    	    	//-------------
    	    	
    	    	jsonStr.append("\"activity_list\":").append("\"").append("[]").append("\",");
    	    	//-------
    	    	
    	    	//--------------------
    	    	
    	    	jsonStr.append(offerStr);
    	    	//--------------------
    	    	
    	    	
    	    	jsonStr.append("\"package_sale\":").append("\"").append("0").append("\","); //新加字段
    	    	jsonStr.append("\"choose_active\":").append("\"").append("0").append("\",");//新加字段
    	    	jsonStr.append("\"is_self\":").append("\"").append("0").append("\",");//新加字段
    	    	
    	    	//-----------------------------
    	    	jsonStr.append(skuStr);
    			//-----------------------------
    			
    			
    	    	
    	    
    		}
    		result = result +"]";
    	}
    	result = result +"}}";
    	return result.trim();
   
    	
    	
    	
    	//-----------------------------------------------------------------------------------
    	
	}
	
	public String orderStateSync(String orderId,String toSysName,String serial_no) {
		String result = "";
		
		String fromSysName = "ECS";
	    	SimpleDateFormat sdf_good =   new SimpleDateFormat( "yyyyMMddHHmmss" );
	    	Date Current_date = new Date(System.currentTimeMillis());//获取系统当前时间
	    	String req_date = sdf_good.format(Current_date);
			Map m = getICommClientBO().queryForMap("SELECT A.ORDER_ID, A.STATUS, B.SHIP_NAME, B.DELIVERY_ID, A.DEAL_MESSAGE FROM ES_ORDER A JOIN ES_DELIVERY B ON B.ORDER_ID = A.ORDER_ID WHERE A.ORDER_ID = '"+orderId+"'");
			result = result + "  {                                                                         " + 
					"    \"order_state_req\":                                                    " +
					"    {                                                                       " +
					"  \"serial_no\":\""+serial_no+"\",                                     " +
					"      \"time\":\""+req_date+"\",                                            " +
					"  \" source_system \":\""+fromSysName+"\",                                            " +
					"      \" receive_system \":\""+toSysName+"\",                                       " +
					"      \" order_no\":\""+orderId+"\",                                " +
					"      \" order_state\":\""+getValue(m.get("STATUS"))+"\",                                             " +
					"      \" express_company_code\":\""+getValue(m.get("SHIP_NAME"))+"\",                                   " +
					"      \" express_id \":\""+getValue(m.get("DELIVERY_ID"))+"\",                                      " +
					"      \"order_desc\":\""+getValue(m.get("DEAL_MESSAGE"))+"\"     " +
					"    }                                                                       " +
					"  }                                                                         " ;
		
		return result.trim();
	}
	
	public String getValue(Object o){
		String result = "";
		if(o != null) return o.toString();
		return result;
	}
	
	
	
}
