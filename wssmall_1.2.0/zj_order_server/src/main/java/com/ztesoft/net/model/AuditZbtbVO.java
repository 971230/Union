package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * @Description 总部订单详情（字段还需要增减）   es_order_audit_zb_details
 * @author zhangJun
 * @date 2016年10月1日
 */
public class AuditZbtbVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	             
	  private String   zbtb_id ;            
	  private String   province  ;        
	  private String   city     ;         
	  private String   goods_type  ;      
	  private String  goods_name ;       
	  private String   fee_plan   ;       
	  private String   tb_order_id ;      
	  private String   store_order_id  ;  
	  private String   id_number     ;    
	  private String   phone_number   ;   
	  private String   order_create_date ;
	  private Float   order_fee       ;  
	  private String   receipt_date    ;  
	  private Float   money         ;    
	  private Float  brokerage_fee  ;   
	  private Float  expand_fee    ;    
	  private Float  card_fee     ;     
	  private Float  fine_fee    ;      
	  private Float  receive_money   ;  
	  private String  source_from   ;    
	  private String  batch_number ;     
	  private String  create_date   ;    
	  private String  create_user  ;
	  
	  
	  
	  private Float  paymoney  ;   
		private String  order_id ;
		
		
	public Float getPaymoney() {
			return paymoney;
		}
		public void setPaymoney(Float paymoney) {
			this.paymoney = paymoney;
		}
		public String getOrder_id() {
			return order_id;
		}
		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}
	public String getZbtb_id() {
		return zbtb_id;
	}
	public void setZbtb_id(String zbtb_id) {
		this.zbtb_id = zbtb_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getFee_plan() {
		return fee_plan;
	}
	public void setFee_plan(String fee_plan) {
		this.fee_plan = fee_plan;
	}
	public String getTb_order_id() {
		return tb_order_id;
	}
	public void setTb_order_id(String tb_order_id) {
		this.tb_order_id = tb_order_id;
	}
	public String getStore_order_id() {
		return store_order_id;
	}
	public void setStore_order_id(String store_order_id) {
		this.store_order_id = store_order_id;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getOrder_create_date() {
		return order_create_date;
	}
	public void setOrder_create_date(String order_create_date) {
		this.order_create_date = order_create_date;
	}
	public Float getOrder_fee() {
		return order_fee;
	}
	public void setOrder_fee(Float order_fee) {
		this.order_fee = order_fee;
	}
	public String getReceipt_date() {
		return receipt_date;
	}
	public void setReceipt_date(String receipt_date) {
		this.receipt_date = receipt_date;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public Float getBrokerage_fee() {
		return brokerage_fee;
	}
	public void setBrokerage_fee(Float brokerage_fee) {
		this.brokerage_fee = brokerage_fee;
	}
	public Float getExpand_fee() {
		return expand_fee;
	}
	public void setExpand_fee(Float expand_fee) {
		this.expand_fee = expand_fee;
	}
	public Float getCard_fee() {
		return card_fee;
	}
	public void setCard_fee(Float card_fee) {
		this.card_fee = card_fee;
	}
	public Float getFine_fee() {
		return fine_fee;
	}
	public void setFine_fee(Float fine_fee) {
		this.fine_fee = fine_fee;
	}
	public Float getReceive_money() {
		return receive_money;
	}
	public void setReceive_money(Float receive_money) {
		this.receive_money = receive_money;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}   
	  
	  
}
