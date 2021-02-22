package com.ztesoft.net.consts;

/**
 * 订单状态
 * 
 * @author apexking
 * 
 */
public interface OrderStatus {

	
	//订单状态
	
	public static final int ORDER_CHANGED=-7;//已换货
	
	public static final int ORDER_CHANGE_REFUSE=-6;//换货被拒绝
	public static final int ORDER_RETURN_REFUSE=-5;//退货被拒绝
	
	public static final int ORDER_CHANGE_APPLY=-4;//申请换货	
	public static final int ORDER_RETURN_APPLY=-3; // 申请退货
	
	public static final int ORDER_CANCEL_SHIP = -2; // 退货

	public static final int ORDER_CANCEL_PAY = -1; // 退款
	
	public static final int ORDER_RECORD_STATUS_0 = 0;//正常
	public static final int ORDER_RECORD_STATUS_1 = 1;//暂停
	
	public static final int ORDER_COLLECT = 0; // 待审核
	public static final int ORDER_WAI_CONFIRM = -50;//待确认
	public static final int ORDER_COLLECT_REFUSE = 1; // 待审核审核不通过
	public static final int ORDER_NOT_PAY = 2; // 未付款、审核通过---
	public static final int ORDER_PAY = 3; // 已支付、待受理---
	public static final int ORDER_ACCEPT = 4; // 已受理待发货---
	public static final int ORDER_SHIP = 5; // 已发货待确认---
	public static final int ORDER_CONFIRM_SHIP = 6; // 确认收货----
	public static final int ORDER_COMPLETE = 7; // 已完成----
	public static final int ORDER_CANCELLATION = 8; // 作废
	public static final int ORDER_ACCEPT_WITHDRAW = -9; // 撤单
	public static final int ORDER_CONFIRM_CANCEL= -10; // 取消订单
	
	public static final int ORDER_ACCEPT_FAIL = -11; // 受理失败
	public static final int ORDER_EXCEPTION_99 = 99;//订单异常
	
	public static final int RETURNED_APPLY = 20;//退货申请
	public static final int RETURNED_APPLY_PASS = 21;//退货申请审核通过
	public static final int EXCHANGE_APPLY = 22;//换货申请
	public static final int EXCHANGE_APPLY_PASS = 23;//换货申请通过
	
	public static final String ORDER_EXCEPTION = "99";	//订单异常
	
	/**
	 * 0未打印、1已打印、（订单页面发货状态为等待配货）、2已校验、3已发货、4部分发货
	 */
	public static final int DELIVERY_SHIP_STATUS_0 = 0;
	public static final int DELIVERY_SHIP_STATUS_1 = 1;
	public static final int DELIVERY_SHIP_STATUS_2 = 2;
	public static final int DELIVERY_SHIP_STATUS_3 = 3;
	public static final int DELIVERY_SHIP_STATUS_4 = 4;
	public static final int DELIVERY_SHIP_STATUS_NOT_CONFIRM = -1;
	
	public static final String ACTION_CODE_PAY = "pay";
	public static final String ACTION_CODE_CONFIRM = "confirm";
	public static final String ACTION_CODE_ACCEPT = "accept";
	public static final String ACTION_CODE_DELIVERY = "delivery";
	public static final String ACTION_CODE_CREATE_ORDER = "createorder";
	
	public static final String ACCEPT_STATUS_YES = "yes";
	public static final String ACCEPT_STATUS_NO = "no";
	
	/**
	 * 打印状态(1备货单/2清单/3快递单已打印) 0未打印
	 */
	public static final int DELIVERY_PRINT_STATUS_1 = 1;
	public static final int DELIVERY_PRINT_STATUS_2 = 2;
	public static final int DELIVERY_PRINT_STATUS_3 = 3;
	public static final int DELIVERY_PRINT_STATUS_0 = 0;
	/**
	 * 展示菜单列表:
	 * 
	 * 全部订单
	 * 待支付
	 * 待审核
	 * 待受理
	 * 待发货
	 * 待确认发货
	 * 订单取消
	 * 订单撤单
	 * 
	 */
	//订单审核状态
	public static final String ORDER_AUDIT_STATE_0= "0";  //待审核
	public static final String ORDER_AUDIT_STATE_1= "1"; //一级分销商审核不通过
	public static final String ORDER_AUDIT_STATE_2= "2";//一级分销商审核通过
	public static final String ORDER_AUDIT_STATE_3= "3";//电信员工审核不通过
	public static final String ORDER_AUDIT_STATE_4= "4";//电信员工审核通过
	
	//订单类型 1订购、2退费、3换货、4退货 5订单与订单的退换关系 {订购关系为分销订单与淘宝订单的关系，退费}
	public static final String ORDER_TYPE_1= "1";//
	public static final String ORDER_TYPE_2= "2";//
	public static final String ORDER_TYPE_3= "3";//
	public static final String ORDER_TYPE_4= "4";//
	public static final String ORDER_TYPE_5= "5";
	
	/**
	 * 退货退费换货申请状态
	 */
	public static final String ORDER_APPLY_STATUS_0 = "0"; //未审核
	public static final String ORDER_APPLY_STATUS_1 = "1"; //已接受申请
	public static final String ORDER_APPLY_STATUS_2 = "2"; //已拒绝
	public static final String ORDER_APPLY_STATUS_3 = "3"; //已退货
	public static final String ORDER_APPLY_STATUS_4 = "4"; //已确认退货
	public static final String ORDER_APPLY_STATUS_5 = "5"; //已退费
	public static final String ORDER_APPLY_STATUS_6 = "6"; //已确认退费
	public static final String ORDER_APPLY_STATUS_7 = "7"; //已换货
	public static final String ORDER_APPLY_STATUS_8 = "8"; //已确认换货
	/**
	 * 处理状态 退货退费换货
	 */
	public static final String ORDER_APPLY_REL_STATE_0 = "0";//待处理
	public static final String ORDER_APPLY_REL_STATE_1 = "1";//已处理
	/**
	 * 退款方式（1退款至账户余额、2原支付方式返回、3退款至银行卡）
	 */
	public static final String REFUND_TYPE_1 = "1";
	public static final String REFUND_TYPE_2 = "2";
	public static final String REFUND_TYPE_3 = "3";
	 
	
	//付款状态
	public static final int PAY_NO= 0;  //未支付
	public static final int PAY_YES= 1;//已支付
	public static final int PAY_CANCEL =2; //已经退款
	public static final int PAY_PARTIAL_REFUND = 3; //部分退款
	public static final int PAY_PARTIAL_PAYED =4 ;//部分付款
	public static final int PAY_PARTIAL_REFUNDING =5 ;//退款申请中
	
	
	//受理状态
	public static final int ACCEPT_STATUSN_1= -1;//待确认
	public static final int ACCEPT_STATUS_0= 0; //0未受理，1申请审核通过，2审核 不通过，3受理成功，4受理失败
	public static final int ACCEPT_STATUS_1 =1; //受理申请审核不通过
	public static final int ACCEPT_STATUS_2 = 2; //2审核通过
	public static final int ACCEPT_STATUS_3 =3 ;//受理成功
	public static final int ACCEPT_STATUS_4 =4 ;//受理失败
	public static final int ACCEPT_STATUS_5 =5 ;//处理中
	public static final int ACCEPT_STATUSN_11= -11;//部分待确认
	public static final int ACCEPT_STATUS_11 =11 ;//部分申请
	public static final int ACCEPT_STATUS_33 =33 ;//部分受理
	
	
	//货运状态
	public static final int SHIP_PRINT= -1;  //	0待打印
	public static final int SHIP_PRINTING= -2;  //	-2正在打印
	public static final int SHIP_PRINTED= -3;  //	-2已打印
	public static final int SHIP_NO= 0;  //	0待发货
	public static final int SHIP_YES= 1;//	1已发货
	public static final int SHIP_CANCEL= 2;//	2.已退货
	public static final int SHIP_PARTIAL_SHIPED= 4; //	4 部分发货
	public static final int SHIP_PARTIAL_CANCEL= 3;// 3 部分退货
	public static final int SHIP_PARTIAL_CHANGE= 5;// 5部分换货	
	public static final int SHIP_CHANED= 6;// 6已换货	
	
	
	public static final String SHIP_ACTION_SHIP= "SHIP"; // 发货
	public static final String SHIP_ACTION_CONFIRM_SHIP = "CONFIRM_SHIP"; // 确认收货
	public static final String SHIP_ACTION_RETURNED = "RETURNED"; // 退货
	public static final String SHIP_ACTION_CHANGE = "CHANGE"; // 换货
	
	public static final String PAYMENT_ACTION_PAYED = "PAYED"; // 支付
	public static final String PAYMENT_ACTION_REFUND = "REFUND"; // 退款

	public static final String DEAL_FLAG_11 = "11"; // 已提交银行，未收到银行支付结果,12:可疑交易
	public static final String DEAL_FLAG_12 = "12"; // 可疑交易
	public static final String DEAL_FLAG_1 = "1"; // 银行支付不成功
	public static final String DEAL_FLAG_2 = "2"; //支付成功，待送计费销帐
	public static final String DEAL_FLAG_3 = "3"; //支付成功，计费销帐失败
	public static final String DEAL_FLAG_4 = "4"; //支付成功，计费销帐成功 A:支付成功，不需后续处理
	
	
	public static final String PAY_PAYMENT_CFG_2= "2";//在线支付
	public static final String PAY_PAYMENT_CFG_3= "3";//货单付款
	public static final String PAY_PAYMENT_CFG_4= "4";//预存款支付
	public static final String PAY_PAYMENT_CFG_5= "5";//免支付
	
	public static final int ITEM_TYPE_0 = 0; // 普通订单
	public static final int ITEM_TYPE_1 = 1; // 捆绑订单
	public static final int ITEM_TYPE_2 = 2; // 赠品订单
	public static final int ITEM_TYPE_3 = 3; // 团购订单
	public static final int ITEM_TYPE_4 = 4; // 限时秒杀订单
	
	
	public static final String SOURCE_FROM_AGENT_ONE = "00A"; // 一级分销商
	public static final String SOURCE_FROM_AGENT_TOW = "00B"; // 二级分销商
	public static final String SOURCE_FROM_TAOBAO = "00C"; // 淘宝
	public static final String SOURCE_FROM_FJ = "FJ"; // 福建
	public static final String SOURCE_FROM_WT = "WT"; // 网厅
	public static final String SOURCE_FROM_HN = "HN"; // 湖南
	
	
	public static final String AUDIT_STATE_WAIT = "0"; // 待审核
	public static final String AUDIT_STATE_YES= "1"; // 审核通过
	public static final String AUDIT_STATE_NO = "2"; // 审核不通过
	
	
	public static final int PAY_TYPE_1= 1; //1收款
	public static final int PAY_TYPE_2 =2;//2退款
	public static final int PAY_STATUS_0 = 0; //0待支付
	public static final int PAY_STATUS_1 = 1; //1成功
	public static final int PAY_STATUS_2 =2 ;//2进行中
	public static final int PAY_STATUS_NOT_CONFIRM =-1 ;//-1待确认
	
	public static final String AUDIT_TYPE_00A= "00A"; //合约机审核
	public static final String AUDIT_TYPE_00B ="00B";//退费审核
	
	
	//按钮中文名
	public static final String BUTTON_NAME_CLOUD_ACCEPT = "云卡调拨"; 
	public static final String BUTTON_NAME_CUST_ACCEPT = "资料返档"; 
	public static final String BUTTON_NAME_CARD_ACCEPT = "充值卡调拨"; 
	public static final String BUTTON_NAME_CONTRACT_APPLY = "合约机申请"; 
	public static final String BUTTON_NAME_CONTRACT_AUDIT = "合约机审核"; 
	public static final String BUTTON_NAME_CONTRACT_ACCEPT = "合约机受理"; 
	
	public static final String BUTTON_NAME_CONTRACT_TANSFER = "合约机号码调拨"; 
	
	public static final String BUTTON_NAME_PAY = "支付"; 
	public static final String BUTTON_NAME_CARD_PAY = "预存金支付";
	
	public static final String BUTTON_NAME_REFUND_APPLY= "退费申请";
	public static final String BUTTON_NAME_REFUND_AUDIT= "退费审核";
	public static final String BUTTON_NAME_REFUND= "退费处理"; 
	public static final String BUTTON_NAME_AUDIT = "审核"; 
	public static final String BUTTON_NAME_SHIPPING = "发货"; 
	public static final String BUTTON_NAME_GET_SHIPPING = "确认收货"; 
	public static final String BUTTON_NAME_RETURNED_SHIPPING= "退货"; 
	public static final String BUTTON_NAME_CHANGE_SHIPPING = "换货";
	public static final String BUTTON_NAME_FINISHED = "完成";
	public static final String BUTTON_NAME_INVALID = "作废";
	public static final String BUTTON_NAME_CANCEL = "取消";
	public static final String BUTTON_NAME_DRAWBACK = "撤单";
	
	
	
	
	
	
	//按钮英文名
	public static final String BUTTON_CLOUD_ACCEPT = "BUTTON_CLOUD_ACCEPT"; 
	public static final String BUTTON_CUST_ACCEPT = "BUTTON_CUST_ACCEPT"; 
	public static final String BUTTON_CARD_ACCEPT = "BUTTON_CARD_ACCEPT"; 
	public static final String BUTTON_CONTRACT_APPLY = "BUTTON_CONTRACT_APPLY"; 
	public static final String BUTTON_CONTRACT_AUDIT = "BUTTON_CONTRACT_AUDIT"; 
	public static final String BUTTON_CONTRACT_ACCEPT = "BUTTON_CONTRACT_ACCEPT"; 
	public static final String BUTTON_PAY = "BUTTON_PAY"; 
	public static final String BUTTON_REFUND= "BUTTON_REFUND"; 
	public static final String BUTTON_REFUND_AUDIT= "BUTTON_REFUND_AUDIT"; 
	public static final String BUTTON_CARD_PAY= "BUTTON_CARD_PAY"; 
	
	
	
	public static final String BUTTON_AUDIT = "BUTTON_AUDIT"; 
	public static final String BUTTON_SHIPPING = "BUTTON_SHIPPING"; 
	public static final String BUTTON_GET_SHIPPING = "BUTTON_GET_SHIPPING"; 
	public static final String BUTTON_RETURNED_SHIPPING= "BUTTON_RETURNED_SHIPPING"; 
	public static final String BUTTON_CHANGE_SHIPPING = "BUTTON_CHANGE_SHIPPING";
	public static final String BUTTON_FINISHED = "BUTTON_FINISHED";
	public static final String BUTTON_INVALID = "BUTTON_INVALID";
	public static final String BUTTON_CANCEL = "BUTTON_CANCEL";
	public static final String BUTTON_DRAWBACK = "BUTTON_DRAWBACK";
	public static final String BUTTON_REFUND_APPLY = "BUTTON_REFUND_APPLY";
	
	
	public static final String BUTTON_CONTRACT_TANSFER = "BUTTON_CONTRACT_TANSFER";
	
	
	
	
	
	//业务受理动作类 
	public static final String ACCEPT_ACTION_WITHDRAWS= "WITHDRAWS"; // 撤单 
	public static final String ACCEPT_ACTION_CANCEL= "CANCEL"; // 取消订单
	public static final String ACCEPT_ACTION_ORDER= "ORDER"; //订购
	public static final String ACCEPT_ACTION_REFUND = "REFUND";//退费
	public static final String ACCEPT_ACTION_CANCEL_SHIPPING = "CANCEL_SHIPPING";//退货
	public static final String ACCEPT_ACTION_CHANGE_SHIPPING = "CHANGE_SHIPPING";//换货
	
	
	public static final String Z_TABLE_NAME_ES_ORDER = "ES_ORDER";//订单表
	public static final String Z_TABLE_NAME_ES_ORDER_OTHER = "ES_ORDER_OUTER";//外系统订单表
	
	
	public static final String ORDER_REL_STATE_0 = "0";//待处理
	public static final String ORDER_REL_STATE_1 = "1";//已处理
	
	
	/**
	 * 大众版订单处理
	 */
	public static final String BUTTON_CUST_ACCEPT_C = "BUTTON_CUST_ACCEPT_C";
	public static final String BUTTON_SHIPPING_C = "BUTTON_SHIPPING_C"; 
	public static final String BUTTON_GET_SHIPPING_C = "BUTTON_GET_SHIPPING_C"; 
	public static final String BUTTON_FINISHED_C = "BUTTON_FINISHED_C";
	//退货
	public static final String BUTTON_RETURNED_SHIPPING_C= "BUTTON_RETURNED_SHIPPING_C"; 
	//退费
	public static final String BUTTON_REFUND_C= "BUTTON_REFUND_C"; 
	
	
	public static final String SUB_ATTR_SPEC_TYPE_ACCEPT = "accept";//受理类型
	public static final String SUB_ATTR_SPEC_TYPE_DELIVERY = "delivery";//受理类型
	
	
	//确认状态
	/*待确认-1
	已拆分完2
	部分拆分3
	取消-10
	余单撤销-9
	失败订单-11
	异常订单-12
	作废8*/
	public static final int ORDER_CONFIRM_STATUS_NOT = -1;
	public static final int ORDER_CONFIRM_STATUS_2 = 2;
	public static final int ORDER_CONFIRM_STATUS_3 = 3;
	public static final int ORDER_CONFIRM_STATUS_CANCEL_ALL = -10;
	public static final int ORDER_CONFIRM_STATUS_CANCEL_PART = -11;
	public static final int ORDER_CONFIRM_STATUS_EXCEPTION = -12;
	public static final int ORDER_CONFIRM_STATUS_8 = 8;
	
	
	//彩购单状态  
	//1平台获取、2手工新建、3批量导入、4售货自建 、5采购入订单,采购订单订单写入改造,6采购出订单,采购订单订单写入改造 7其它出库
	public static final String WP_CREATE_TYPE_1 = "1";
	public static final String WP_CREATE_TYPE_2 = "2";
	public static final String WP_CREATE_TYPE_3 = "3";
	public static final String WP_CREATE_TYPE_4 = "4";
	public static final String WP_CREATE_TYPE_5 = "5";
	public static final String WP_CREATE_TYPE_6 = "6";
	public static final String WP_CREATE_TYPE_7 = "7";
	//采购状态 (已新建0、已完成1)
	public static final String WP_PRU_STATUS_0 = "0";
	public static final String WP_PRU_STATUS_1 = "1";
	//出入库类型0采购入库、1直接入库、2调拨入库
	public static final String WP_STORE_ACTION_TYPE_0 = "0";
	public static final String WP_STORE_ACTION_TYPE_1 = "1";
	public static final String WP_STORE_ACTION_TYPE_2 = "2";
	//审核状态0未审核、1审核通过、2审核不通过
	public static final String WP_AUDIT_STATUS_0 = "0";
	public static final String WP_AUDIT_STATUS_1 = "1";
	public static final String WP_AUDIT_STATUS_2 = "2";
	
	//1退款至账户余额 2原支付方式返回 3退款至银行卡
	public static final String APLY_RETURN_TYPE_1 = "1";
	public static final String APLY_RETURN_TYPE_2 = "2";
	public static final String APLY_RETURN_TYPE_3 = "3";
	public static final String SERVICE_CODE_ORDER="3";
	
	//物流子单类型
	public static final Integer DELIVERY_ITEM_TYPE_0 = 0;//正常
	public static final Integer DELIVERY_ITEM_TYPE_3 = 3;//补发
	public static final Integer DELIVERY_ITEM_TYPE_4 = 4;//重发
	//0正常发货、1补寄、2重发
	public static final String DELIVERY_TYPE_0 = "0";//
	public static final String DELIVERY_TYPE_1 = "1";//
	public static final String DELIVERY_TYPE_2 = "2";//
	
	public static final int DELIVERY_SHIP_SATUS_NOT=-1;
	public static final int DELIVERY_SHIP_SATUS_0=0;
	public static final int DELIVERY_SHIP_SATUS_1=1;
}
