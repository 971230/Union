package com.ztesoft.net.mall.core.consts;

/**
 * 
 * @author wanpeng 2015-12-11
 *
 */
public class OrderQueueConsts {
	
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
	
	/** 队列处理标志 */
	public static final String DEAL_SUCCEED = "Succeed";
	public static final String DEAL_FAIL = "Fail";
	public static final String DEAL_EQUAL_SUCCEED = "EqualSucceed";
	
	/** 队列缓存key */
	public static final String ORDER_QUEUE_CACHE_KEY = "orderQueueCache";
	public static final String ORDER_QUEUE_FAIL_CACHE_KEY = "orderQueueFailCache";
	public static final String ORDER_QUEUE_HIS_CACHE_KEY = "orderQueueHisCache";
	public static final String INF_HEAD_CACHE_KEY = "infHeadCache";
	public static final String INF_HEAD_HIS_CACHE_KEY = "infHeadHisCache";
	public static final int CACHE_KEEP_TIME = 24*60*60; //1天
	
	/**
	 * 订单标准化ApiMethodName - 模版转换标准化
	 */
	public static final String SERVICE_CODE_TEMPLATESORDERSTANDARD = "zte.net.orderstd.templatesOrderStandard";
	
	/**
	 * 订单标准化ApiMethodName - 淘宝商城订单标准化
	 */
	public static final String SERVICE_CODE_TAOBAOMALLORDERSTANDARD = "zte.net.orderstd.taoBaoMallOrderStandard";
	
	/**
	 * 订单标准化ApiMethodName - 新商城订单标准化
	 */
	public static final String SERVICE_CODE_NEWMALLORDERSTANDARD = "zte.net.orderstd.newMallOrderStandard";
	/**
	 * 订单标准化ApiMethodName - 总部商城订单标准化
	 */
	public static final String SERVICE_CODE_CENTERMALLORDERSTANDARD = "zte.net.orderstd.centerMallOrderStandard";
	
	/** 订单标准化方式 */
	public static final String ORDER_STD_OLD_SYS_CF_ID = "order_std_old_sys"; // 标准化方式配置cf_id
	public static final String ORDER_STD_OLD_SYS_YES = "yes"; // 旧系统标准化
	
	/** 旧系统订单标准化地址配置 */
	public static final String SERVER_IP_ADDR_NEWMALLORDER = "SERVER_IP_ADDR_NEWMALLORDER"; // 新商城
	public static final String SERVER_IP_ADDR_CENTERMALLORDER = "SERVER_IP_ADDR_CENTERMALLORDER"; // 总商
	
	/** 文件系统 operation_code */
	public static final String ESEARCH_OPERATION_CODE = "OrderQueueLog";
	
	/**
	 * 队列类型-订单收集队列
	 */
	public static final String QUEUE_TYPE_CTN = "ctn";
	
	/**
	 *  异常类型-异常收集队列
	 */
	public static final String QUEUE_TYPE_EXP = "exp";
	

}
