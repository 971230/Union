package com.ztesoft.net.consts;

/**
 * 订流程静态数据
 * @作者 MoChunrun
 * @创建日期 2014-6-10 
 * @版本 V 1.0
 */
public class OrderFlowConst {

	//不能撤消
	public static final int CAN_NOT_RIB = 99;
	public static final int SUCCESS = 0;//成功
	public static final int FAIL = 1;//失败
	public static final int CANT_NOT_LOCK = 88;//已经锁定或者当前环节为空，不能抢单
	
	public static final int STRUTS_1 = 1;//通过
	public static final int STRUTS_2 = 2;//不通过
	public static final int STRUTS_3 = 3;//不需要处理
	public static final int STRUTS_4 = 4;//撤单
	public static final int STRUTS_5 = 5;//抢单
	public static final int STRUTS_6 = 6;//分派
	
	/**
	 * 类型编码goods商品、goodstype商品类型、common通用流程
	 */
	public static final String FLOW_TYPE_GOODS = "goods";
	public static final String FLOW_TYPE_GOODS_TYPE = "goodstype";
	public static final String FLOW_TYPE_COMMON = "common";
	
	/**
	 * 流程类型 buy购买 returned 退货 refund退款
	 */
	public static final String FLOW_SERVICE_TYPE_BUY = "buy";
	public static final String FLOW_SERVICE_TYPE_RETURNED = "returned";
	public static final String FLOW_SERVICE_TYPE_REFUND = "refund";
	public static final String FLOW_SERVICE_TYPE_EXCHANGE = "exchange";
	
	/**
	 * 流程类型audit审核、confirm确认、package商品备货、pay支付、delivery发货、
	 * received确认收货、finish完成订单、returned退货、refund退费、exchange换货
	 * openAccount开户、rob抢单
	 */
	public static final String FLOW_DEF_TYPE_ROB = "rob";
	public static final String FLOW_DEF_TYPE_AUDIT = "audit";
	public static final String FLOW_DEF_TYPE_CONFIRM = "confirm";
	public static final String FLOW_DEF_TYPE_PACKAGE = "package";
	public static final String FLOW_DEF_TYPE_PAY = "pay";
	public static final String FLOW_DEF_TYPE_DELIVERY = "delivery";
	public static final String FLOW_DEF_TYPE_RECEIVED = "received";
	public static final String FLOW_DEF_TYPE_FINISH = "finish";
	public static final String FLOW_DEF_TYPE_RETURNED = "returned";
	public static final String FLOW_DEF_TYPE_REFUND = "refund";
	public static final String FLOW_DEF_TYPE_EXCHANGE = "exchange";
	public static final String FLOW_DEF_TYPE_OPEN_ACCOUNT = "openAccount";
	
	/**
	 * 订单分派 dispatch
	 */
	public static final String ORDER_TODO_LIST_DISPATCH = "dispatch";
	
	
	public static String getFlowDefType(int order_status,String service_type){
		if(FLOW_SERVICE_TYPE_BUY.equals(service_type)){
			if(OrderStatus.ORDER_COLLECT==order_status){
				return FLOW_DEF_TYPE_AUDIT;
			}else if(OrderStatus.ORDER_WAI_CONFIRM==order_status){
				return FLOW_DEF_TYPE_CONFIRM;
			}else if(OrderStatus.ORDER_NOT_PAY==order_status){
				return FLOW_DEF_TYPE_PAY;
			}else if(OrderStatus.ORDER_PAY==order_status){
				return FLOW_DEF_TYPE_PACKAGE;
			}else if(OrderStatus.ORDER_ACCEPT==order_status){
				return FLOW_DEF_TYPE_DELIVERY;
			}else if(OrderStatus.ORDER_SHIP==order_status){
				return FLOW_DEF_TYPE_RECEIVED;
			}else if(OrderStatus.ORDER_CONFIRM_SHIP==order_status){
				return FLOW_DEF_TYPE_FINISH;
			}
		}else if(FLOW_SERVICE_TYPE_RETURNED.equals(service_type)){
			if(7==order_status){
				return FLOW_DEF_TYPE_RETURNED;
			}else{
				return FLOW_DEF_TYPE_REFUND;
			}
		}else if(FLOW_SERVICE_TYPE_EXCHANGE.equals(service_type)){
			if(7==order_status){
				return FLOW_DEF_TYPE_EXCHANGE;
			}else{
				return FLOW_DEF_TYPE_REFUND;
			}
		}
		//还没有定退货、换货、开户、退款
		return null;
	}
	
	public static int getOrderStatus(String flow_code){
		if(FLOW_DEF_TYPE_AUDIT.equals(flow_code)){
			return OrderStatus.ORDER_COLLECT;
		}else if(FLOW_DEF_TYPE_PAY.equals(flow_code)){
			return OrderStatus.ORDER_NOT_PAY;
		}else if(FLOW_DEF_TYPE_PACKAGE.equals(flow_code)){
			return OrderStatus.ORDER_PAY;
		}else if(FLOW_DEF_TYPE_DELIVERY.equals(flow_code)){
			return OrderStatus.ORDER_ACCEPT;
		}else if(FLOW_DEF_TYPE_RECEIVED.equals(flow_code)){
			return OrderStatus.ORDER_SHIP;
		}else if(FLOW_DEF_TYPE_FINISH.equals(flow_code)){
			return OrderStatus.ORDER_CONFIRM_SHIP;
		}
		//还没有定退货、换货、开户、退款
		return -9999;
	}
	
	public static String getCommonAcceptUserId(){
		return "1";
	}
	
}
