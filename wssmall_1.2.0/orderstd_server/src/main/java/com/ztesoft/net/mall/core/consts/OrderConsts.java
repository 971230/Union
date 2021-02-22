package com.ztesoft.net.mall.core.consts;

/**
 * 
 * 订单常量类
 * @author wanpeng 2015-12-03
 *
 */
public class OrderConsts {
	
	/** 消息队列状态*/
	public static final String CO_QUEUE_STATUS_WFS = "WFS";  //未发送
	public static final String CO_QUEUE_STATUS_FSZ = "FSZ";  //发送中
	public static final String CO_QUEUE_STATUS_XYCG = "XYCG";  //响应成功
	public static final String CO_QUEUE_STATUS_XYSB = "XYSB";  //响应失败
	 
	//ES_OPEN_SERVICE_CFG接口动作
	public static final String SERVICE_ACTION_CODE_ADD = "A";
	public static final String SERVICE_ACTION_CODE_UPDATE = "M";
	public static final String SERVICE_ACTION_CODE_DELETE = "D";
	public static final String SERVICE_ACTION_CODE_QUERY = "Q";
	 
	/** 订单标准化方式 */
	public static final String ORDER_STD_OLD_SYS_CF_ID = "order_std_old_sys"; // 标准化方式配置cf_id
	public static final String ORDER_STD_OLD_SYS_YES = "yes"; // 旧系统标准化
	 
	/** 消费类型 */
	public static final String RPC_TYPE_DUBBO = "D"; // 消费类型-dubbo调用
	public static final String RPC_TYPE_MQ = "M"; // 消费类型-mq
	
	/** 旧系统订单标准化地址配置 */
	public static final String SERVER_IP_ADDR_NEWMALLORDER = "SERVER_IP_ADDR_NEWMALLORDER"; // 新商城
	public static final String SERVER_IP_ADDR_CENTERMALLORDER = "SERVER_IP_ADDR_CENTERMALLORDER"; // 总商
	
	/** 队列异常写异常单系统开关 */
	public static final String INSERT_QUEUE_TO_EXP = "INSERT_QUEUE_TO_EXP";
	public static final String INSERT_QUEUE_TO_EXP_YES = "yes";
	
	
	/**
	 * 订单标准化outService - 新商城订单标准化
	 */
	public static final String OUT_SERVICE_CODE_NEWMALLORDERSTANDARD = "create_order_newMallOrderStandard";
	/**
	 * 订单标准化outService - 总部商城订单标准化
	 */
	public static final String OUT_SERVICE_CODE_CENTERMALLORDERSTANDARD = "create_order_centerMallOrderStandard";
	/**
	 * 订单标准化outService - 淘宝商城订单标准化
	 */
	public static final String OUT_SERVICE_CODE_TAOBAOMALLORDERSTANDARD = "create_order_taoBaoMallOrderStandard";
	/**
	 * 订单标准化outService - 淘宝商城订单标准化
	 */
	public static final String OUT_SERVICE_CODE_HSMALLORDERSTANDARD = "create_order_HSMallOrderStandard";
	
	/**
	 * 订单标准化ApiMethodName - 新商城订单标准化
	 */
	public static final String SERVICE_CODE_NEWMALLORDERSTANDARD = "zte.net.orderstd.newMallOrderStandard";
	/**
	 * 订单标准化ApiMethodName - 总部商城订单标准化
	 */
	public static final String SERVICE_CODE_CENTERMALLORDERSTANDARD = "zte.net.orderstd.centerMallOrderStandard";
	/**
	 * 订单标准化ApiMethodName - 淘宝商城订单标准化
	 */
	public static final String SERVICE_CODE_TAOBAOMALLORDERSTANDARD = "zte.net.orderstd.taoBaoMallOrderStandard";
	/**
	 * 订单标准化ApiMethodName - 淘宝商城订单标准化
	 */
	public static final String SERVICE_CODE_HSMALLORDERSTANDARD = "zte.net.orderstd.HSMallOrderStandard";
	
	/**
	 * 订单标准化ApiMethodName - 模版转换标准化
	 */
	public static final String SERVICE_CODE_TEMPLATESORDERSTANDARD = "zte.net.orderstd.templatesOrderStandard";


	/**
	 * 标准化处理的表名
	 */
	public static final String ES_ORDER_EXTVTL = "es_order_extvtl";
	public static final String ES_DELIVERY = "es_delivery";
	public static final String ES_PAYMENT_LOGS = "es_payment_logs";
	public static final String ES_ORDER = "es_order";
	public static final String ES_ORDER_EXT = "es_order_ext";
	public static final String ES_ATTR_DISCOUNT_INFO = "es_attr_discount_info";
	public static final String ES_ATTR_FEE_INFO = "es_attr_fee_info";
	public static final String ES_ATTR_PACKAGE = "es_attr_package";
	public static final String ES_ATTR_TERMINAL_EXT = "es_attr_terminal_ext";
	public static final String ES_HUASHENG_ORDER = "es_huasheng_order";
	public static final String ES_ORDER_ITEMS_APT_PRINTS = "es_order_items_apt_prints";
	public static final String ES_ORDER_PHONE_INFO = "es_order_phone_info";
	public static final String ES_ORDER_SUB_OTHER_INFO = "es_order_sub_other_info";
	public static final String ES_ORDER_SUB_PROD_INFO = "es_order_sub_prod_info";
	public static final String ES_ORDER_ZHWQ_ADSL = "es_order_zhwq_adsl";
	public static final String ES_ORDER_ITEMS_PROD_EXT = "es_order_items_prod_ext";
	public static final String ES_ATTR_GIFT_INFO = "es_attr_gift_info";
	public static final String ES_ORDER_ITEMS_EXT = "es_order_items_ext";
	public static final String ES_ORDER_REALNAME_INFO = "es_order_realname_info";
	public static final String ES_ORDER_WMS_KEY_INFO = "es_order_wms_key_info";
	public static final String ES_ORDER_INTENT_INFO = "es_order_internet_info";

	/**
	 * 标准化处理的对应处理器
	 */
	
	public static final String ORDEREXTHANDLER = "orderExtHandler";
	public static final String DELIVERYHANDLER = "deliveryHandler";
	public static final String ORDERHANDLER = "orderHandler";
	public static final String ORDERITEMSEXTHANDLER = "orderItemsExtHandler";
	public static final String ORDERITEMSPRODEXTHANDLER = "orderItemsProdExtHandler";
	public static final String PAYMENTLOGSHANDLER = "paymentLogsHandler";
	public static final String ORDEREXTVTLHANDLER = "orderExtvtlHandler";
	public static final String ATTRDISCOUNTINFOHANDLER = "attrDiscountInfoHandler";
	public static final String ATTRFEEINFOHANDLER = "attrFeeInfoHandler";
	public static final String ATTRPACKAGEHANDLER = "attrPackageHandler";
	public static final String ATTRTERMINALEXTHANDLER = "attrTerminalExtHandler";
	public static final String HUASHENGORDERHANDLER = "huashengOrderHandler";
	public static final String ORDERITEMSAPTPRINTSHANDLER = "orderItemsAptPrintsHandler";
	public static final String ORDERPHONEINFOHANDLER = "orderPhoneInfoHandler";
	public static final String ORDERSUBOTHERINFONEWHANDLER = "orderSubOtherInfoNewHandler";
	public static final String ORDERSUBPRODINFONEWHANDLER = "orderSubProdInfoNewHandler";
	public static final String ORDERZHWQADSLHANDLER = "orderZhwqAdslHandler";
	public static final String ATTRGIFTINFOHANDLER = "attrGiftInfoHandler";
	public static final String ORDERREALNAMEINFOHANDLER = "orderRealNameInfoHandler";
	public static final String ORDERWMSKEYINFOHANDLER = "orderWmsKeyInfoHandler";
	public static final String ORDERINTENTINFOHANDLE = "orderIntentInfoHandler";

}
