package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.List;

/**
 * 订单实体
 * @author wui
 */
public class OrderHis implements java.io.Serializable {

	private String order_id;

	private String sn;

	private String member_id;

	private Integer status;

	private Integer pay_status;

	private Integer ship_status;
	
	//状态显示字串
	private String shipStatus;
	private String payStatus;
	private String orderStatus;
	private String acceptStatus;
	
	
	//收货地区id三级省市的最后一级
	private Long regionid; 
	private String shipping_id;

	private String shipping_type;

	private String shipping_area;
	
	private String userid; //用户id
	
	private String source_from;

	private String goods;

	private String create_time;

	private String ship_name;

	private String ship_addr;

	private String ship_zip;

	private String ship_email;

	private String ship_mobile;

	private String ship_tel;

	private String ship_day;

	private String ship_time;

	private Integer is_protect;

	private Double protect_price;

	private Double goods_amount;

	private Double shipping_amount;
	private Double discount; //优惠金额
	private Double order_amount;

	private Double weight;
	
	private Double paymoney;

	private String remark;
	
	private Integer disabled;
	
	private Integer payment_id;
	private String payment_name;
	private String payment_type;
	private Integer goods_num;
	private int gainedpoint;
	private int consumepoint;
	private String bank_id;
	private String order_type; //订单类型 1订购、2退费、3换货、4退货
	
	private String transaction_id;//网银支付流水号
	
	private String type_code; //订单业务编码
	
	private Integer accept_status =0; // 受理状态
	
	
	private String goods_id; // 商品id 冗余方便统计
	private String lan_id; // 订单归属本地网
	
	private String deal_message;
	
	//订单的价格，非数据库字段
	private OrderPrice orderprice;
	private String pay_time;
	
	private String goods_name;
	
	private String user_name;
	
	private String orderType;
	
	private String oper_btns;
	
	private String lan_name;
	private String audit_state;
	private String acc_nbr;
	private String bill_flag;
	
	private String batch_id;
	
	
	//发票信息
	private Integer invoice_title;//发票抬头
	private Integer invoice_type;//发票类型
	private String invoice_title_desc;//发票抬头描述
	private Integer invoice_content;//发票内容
	
	private String last_update;
	private Integer create_type;
	private String source_shop_name;
	private String order_adscription_id;
	private Integer pay_type;
	private Integer confirm_status;
	private String confirm_time;
	private String confirm_group_id;
	private String confirm_user_id;
	private String ship_assign_time;
	private String ship_group_id;
	private String ship_user_id;
	private String accept_assign_time;
	private String accept_group_id;
	private String accept_user_id;
	private String query_group_id;
	private String query_user_id;
	private String pay_assign_time;
	private String pay_group_id;
	private String pay_user_id;
	private Double taxes;
	private Float order_discount;
	private Double order_coupon;
	private Integer order_record_status;
	
	private String dly_address_id;
	
	
	private List<OrderItem> orderItemList;
	
	private String canPay;
	private String canShip;
	
	private String uname;//购买人
	
	@NotDbField
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	@NotDbField
	public String getCanPay() {
		return canPay;
	}
	public void setCanPay(String canPay) {
		this.canPay = canPay;
	}
	@NotDbField
	public String getCanShip() {
		return canShip;
	}
	public void setCanShip(String canShip) {
		this.canShip = canShip;
	}
	public String getDly_address_id() {
		return dly_address_id;
	}
	public void setDly_address_id(String dly_address_id) {
		this.dly_address_id = dly_address_id;
	}
	public String getLast_update() {
		return last_update;
	}
	@NotDbField
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	public Integer getCreate_type() {
		return create_type;
	}

	public void setCreate_type(Integer create_type) {
		this.create_type = create_type;
	}

	public String getSource_shop_name() {
		return source_shop_name;
	}

	public void setSource_shop_name(String source_shop_name) {
		this.source_shop_name = source_shop_name;
	}

	public String getOrder_adscription_id() {
		return order_adscription_id;
	}

	public void setOrder_adscription_id(String order_adscription_id) {
		this.order_adscription_id = order_adscription_id;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getConfirm_status() {
		return confirm_status;
	}

	public void setConfirm_status(Integer confirm_status) {
		this.confirm_status = confirm_status;
	}

	public String getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(String confirm_time) {
		this.confirm_time = confirm_time;
	}

	public String getConfirm_group_id() {
		return confirm_group_id;
	}

	public void setConfirm_group_id(String confirm_group_id) {
		this.confirm_group_id = confirm_group_id;
	}


	public String getShip_assign_time() {
		return ship_assign_time;
	}

	public void setShip_assign_time(String ship_assign_time) {
		this.ship_assign_time = ship_assign_time;
	}

	public String getShip_group_id() {
		return ship_group_id;
	}

	public void setShip_group_id(String ship_group_id) {
		this.ship_group_id = ship_group_id;
	}


	public String getAccept_assign_time() {
		return accept_assign_time;
	}

	public void setAccept_assign_time(String accept_assign_time) {
		this.accept_assign_time = accept_assign_time;
	}

	public String getAccept_group_id() {
		return accept_group_id;
	}

	public void setAccept_group_id(String accept_group_id) {
		this.accept_group_id = accept_group_id;
	}


	public String getPay_assign_time() {
		return pay_assign_time;
	}

	public void setPay_assign_time(String pay_assign_time) {
		this.pay_assign_time = pay_assign_time;
	}

	public String getPay_group_id() {
		return pay_group_id;
	}

	public void setPay_group_id(String pay_group_id) {
		this.pay_group_id = pay_group_id;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Float getOrder_discount() {
		return order_discount;
	}

	public void setOrder_discount(Float order_discount) {
		this.order_discount = order_discount;
	}

	public Double getOrder_coupon() {
		return order_coupon;
	}

	public String getConfirm_user_id() {
		return confirm_user_id;
	}
	public void setConfirm_user_id(String confirm_user_id) {
		this.confirm_user_id = confirm_user_id;
	}
	public String getShip_user_id() {
		return ship_user_id;
	}
	public void setShip_user_id(String ship_user_id) {
		this.ship_user_id = ship_user_id;
	}
	public String getAccept_user_id() {
		return accept_user_id;
	}
	public void setAccept_user_id(String accept_user_id) {
		this.accept_user_id = accept_user_id;
	}
	public String getPay_user_id() {
		return pay_user_id;
	}
	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}
	public void setOrder_coupon(Double order_coupon) {
		this.order_coupon = order_coupon;
	}

	public Integer getOrder_record_status() {
		return order_record_status;
	}

	public void setOrder_record_status(Integer order_record_status) {
		this.order_record_status = order_record_status;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getBill_flag() {
		return bill_flag;
	}

	public void setBill_flag(String bill_flag) {
		this.bill_flag = bill_flag;
	}

	public Integer getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(Integer goodsNum) {
		goods_num = goodsNum;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Double getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(Double goods_amount) {
		this.goods_amount = goods_amount;
	}

	public Integer getIs_protect() {
		is_protect =is_protect==null?0:is_protect;
		return is_protect;
	}

	public void setIs_protect(Integer is_protect) {
		this.is_protect = is_protect;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Double getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(Double order_amount) {
		this.order_amount = order_amount;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Integer getPay_status() {
		return pay_status;
	}

	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getShip_day() {
		return ship_day;
	}

	public void setShip_day(String ship_day) {
		this.ship_day = ship_day;
	}

	public String getShip_email() {
		return ship_email;
	}

	public void setShip_email(String ship_email) {
		this.ship_email = ship_email;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public Integer getShip_status() {
		return ship_status;
	}

	public void setShip_status(Integer ship_status) {
		this.ship_status = ship_status;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_time() {
		return ship_time;
	}

	public void setShip_time(String ship_time) {
		this.ship_time = ship_time;
	}

	public String getShip_zip() {
		return ship_zip;
	}

	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}

	public Double getShipping_amount() {
		return shipping_amount;
	}

	public void setShipping_amount(Double shipping_amount) {
		this.shipping_amount = shipping_amount;
	}

	public String getShipping_area() {
		return shipping_area;
	}

	public void setShipping_area(String shipping_area) {
		this.shipping_area = shipping_area;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getProtect_price() {
		return protect_price;
	}

	public void setProtect_price(Double protect_price) {
		this.protect_price = protect_price;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}

	public String getPayment_name() {
		return payment_name;
	}

	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	public Double getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}

	public int getGainedpoint() {
		return gainedpoint;
	}

	public void setGainedpoint(int gainedpoint) {
		this.gainedpoint = gainedpoint;
	}

	public int getConsumepoint() {
		return consumepoint;
	}

	public void setConsumepoint(int consumepoint) {
		this.consumepoint = consumepoint;
	}

	@NotDbField
	public Long getRegionid() {
		return regionid;
	}

	public void setRegionid(Long regionid) {
		this.regionid = regionid;
	}

	@NotDbField
	public String getShipStatus() {
		if(ship_status ==null || ship_status==0)
			return "未发货";
		if(ship_status==0)shipStatus="未发货";
		if(ship_status==1)shipStatus="已发货";
		if(ship_status==2)shipStatus="已退货";
		if(ship_status==3)shipStatus="部分退货";
		if(ship_status==4)shipStatus="部分发货";
		
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) 
	{
		this.shipStatus = shipStatus;
	}
	
	@NotDbField
	public String getPayStatus() {
		
		if(Consts.PAYMENT_ID_AUTO.equals(payment_id))
			return "免支付";
		
		if(pay_status ==null || pay_status==0)
			return "未支付";
		
		if(pay_status==0)payStatus="未支付";
		if(pay_status==1)payStatus="支付成功";
		if(pay_status==2)payStatus="已退款";
		if(pay_status==3)payStatus="部分退款";
		if(pay_status==4)payStatus="部分支付";
		
		return payStatus;
	}

	@NotDbField
	public String getAcceptStatus() {
		
		if(accept_status == null || accept_status==0)
			return "未受理";
		if(accept_status==1)acceptStatus="受理审核被拒";
		else if(accept_status==2)acceptStatus="受理审核通过";
		else if(accept_status==3)acceptStatus="受理成功";
		else  if(accept_status==4)acceptStatus="受理失败";
		return acceptStatus;
	}
	@NotDbField
	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}
	
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	
	
//	
//	public static final int ORDER_COLLECT = 0; // 待审核
//	public static final int ORDER_COLLECT_REFUSE = 1; // 待审核审核不通过
//	public static final int ORDER_NOT_PAY = 2; // 未付款、审核通过
//	public static final int ORDER_PAY = 3; // 已支付、待受理
//	public static final int ORDER_ACCEPT = 4; // 已受理待发货
//	public static final int ORDER_SHIP = 5; // 已发货待确认
//	public static final int ORDER_CONFIRM_SHIP = 6; // 确认收货
//	public static final int ORDER_COMPLETE = 7; // 已完成
//	public static final int ORDER_CANCELLATION = 8; // 作废
//	public static final int ORDER_ACCEPT_WITHDRAW = -9; // 撤单
//	public static final int ORDER_CONFIRM_CANCEL= -10; // 取消订单
//	
//	public static final int ORDER_ACCEPT_FAIL = -11; // 受理失败
	
	
	@NotDbField
	public String getOrderStatus() {
		
		if(status ==null || status==0)
			return "待上级审核";
		if(status==-9)orderStatus="撤单";
		if(status==-10)orderStatus="订单已取消";
		if(status==-11)orderStatus="受理失败";
		
		if(status==-7)orderStatus="已换货";
		if(status==-6)orderStatus="换货被拒绝";
		if(status==-5)orderStatus="退货被拒绝";
		if(status==-4)orderStatus="申请换货";
		if(status==-3)orderStatus="申请退货";
		if(status==-2)orderStatus="退货";
		if(status==-1)orderStatus="退款";
		
		if(status==0)orderStatus="待上级审核";
		if(status==1)orderStatus="审核不通过";
		//if(status==2)orderStatus="已审核待支付";
		if(status==2)orderStatus="待支付";
		//if(status==3)orderStatus="待处理";
		if(status==3)orderStatus="已支付待备货";
		if(status==4)orderStatus="已处理待发货";
		if(status==5)orderStatus="已发货待确认";
		if(status==6)orderStatus="已收货确认";
		if(status==7)orderStatus="订单已完成";
		if(status==8)orderStatus="作废";
		if(status==OrderStatus.ORDER_CONFIRM_CANCEL)orderStatus="已取消";
		
		
		if (OrderBuilder.CLOUD_KEY.equals(getType_code())) { // 云卡
			if (status == 3) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
						orderStatus ="待调拨云卡";
				} else {
						orderStatus ="待资料返档";
				}
			}
			
			if (status == 4) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="已调拨待发货";
				}
			}
		}

		if (OrderBuilder.CONTRACT_KEY.equals(getType_code())) { // 合约机
			
			if (status == 3) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="待调拨号码";
				} else {
					
					
//					public static final String ORDER_AUDIT_STATE_0= "0";  //待审核
//					public static final String ORDER_AUDIT_STATE_1= "1"; //一级分销商审核不通过
//					public static final String ORDER_AUDIT_STATE_2= "2";//一级分销商审核通过
//					public static final String ORDER_AUDIT_STATE_3= "3";//电信员工审核不通过
//					public static final String ORDER_AUDIT_STATE_4= "4";//电信员工审核通过
					
					
					if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner()){
						if(OrderStatus.ORDER_AUDIT_STATE_0.equals(audit_state+"")){
							orderStatus ="合约受理审核中";
						}else if(OrderStatus.ORDER_AUDIT_STATE_1.equals(audit_state+"")){
							orderStatus ="合约受理审核通过";
						}else if(OrderStatus.ORDER_AUDIT_STATE_2.equals(audit_state+"")){
							if(ManagerUtils.isFirstPartner())
								orderStatus ="合约受理审核中";
							else if(ManagerUtils.isSecondPartner())
								orderStatus ="合约受理审核中";
						}else if(OrderStatus.ORDER_AUDIT_STATE_3.equals(audit_state+"")){
							orderStatus ="合约受理审核通过";
						}else if(OrderStatus.ORDER_AUDIT_STATE_4.equals(audit_state+"")){
							orderStatus ="合约受理审核通过";
						}
						
					}else{
						orderStatus ="已支付待审核";
					}
				}
			}
			
			if (status == 4) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="已调拨待发货";
				}
			}
			
		}

		if (OrderBuilder.CARD_KEY.equals(getType_code())) { // 充值卡
			if (status == 3) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="待调拨充值卡";
				} else {
					orderStatus ="已支付待充值";
				}
			}
			
			if (status == 4) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="已调拨待发货";
				}
			}
		}
		if (OrderBuilder.RECHARGE_CARD_KEY.equals(getType_code())) { // 流量卡
			
			if (status == 3) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="待调拨流量卡";
				} else {
					orderStatus ="已支付待充值";
				}
			}
			
			if (status == 4) {
				if (!OrderStatus.SOURCE_FROM_TAOBAO.equals(getSource_from())) {
					orderStatus ="已调拨待发货";
				}
			}
		}
		
		
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String paymentType) {
		payment_type = paymentType;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@NotDbField
	public OrderPrice getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(OrderPrice orderprice) {
		this.orderprice = orderprice;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public Integer getAccept_status() {
		return accept_status;
	}

	public void setAccept_status(Integer accept_status) {
		this.accept_status = accept_status;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getDeal_message() {
		return deal_message;
	}

	public void setDeal_message(String deal_message) {
		this.deal_message = deal_message;
	}

	
	@NotDbField
	public String getGoods_name() {
		return goods_name;
	}
	
	@NotDbField
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	@NotDbField
	public String getUser_name() {
		return user_name;
	}
	@NotDbField
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@NotDbField
	public String getOrderType() {
		if(StringUtil.isEmpty(order_type))
			return "订购";
		if(order_type.equals(OrderStatus.ORDER_TYPE_1))
			orderType="订购";
		else if(order_type.equals(OrderStatus.ORDER_TYPE_2))
			orderType="退订";
		return orderType;
	}
	
	@NotDbField
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	@NotDbField
	public String getOper_btns() {
		return oper_btns;
	}
	@NotDbField
	public void setOper_btns(String oper_btns) {
		this.oper_btns = oper_btns;
	}
	
	@NotDbField
	public String getLan_name() {
		if(StringUtil.isEmpty(lan_name))
			lan_name= "全省";
		return lan_name;
	}
	
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}

	@NotDbField
	public String getAudit_state() {
		return audit_state;
	}
	@NotDbField
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}



	public String getInvoice_title_desc() {
		return invoice_title_desc;
	}

	public void setInvoice_title_desc(String invoice_title_desc) {
		this.invoice_title_desc = invoice_title_desc;
	}

	public Integer getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(Integer invoice_title) {
		this.invoice_title = invoice_title;
	}

	public Integer getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}

	public Integer getInvoice_content() {
		return invoice_content;
	}

	public void setInvoice_content(Integer invoice_content) {
		this.invoice_content = invoice_content;
	}
	public String getQuery_group_id() {
		return query_group_id;
	}
	public void setQuery_group_id(String query_group_id) {
		this.query_group_id = query_group_id;
	}
	public String getQuery_user_id() {
		return query_user_id;
	}
	public void setQuery_user_id(String query_user_id) {
		this.query_user_id = query_user_id;
	}
	
}