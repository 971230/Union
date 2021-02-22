package com.ztesoft.net.mall.consts;

/**
 * 
 * @Package com.ztesoft.net.mall.consts
 * @Description: 订单归集常量类
 * @author zhouqiangang
 * @date 2015年11月18日 下午5:19:14
 */
public interface CrawlerConsts {
	
	/**
	 * 服务版本
	 */
	public static final String VERSION = "1.0";

	/**
	 * 消费类型-dubbo调用
	 */
	public static final String RPC_TYPE_DUBBO = "D";

	/**
	 * 消费类型-mq
	 */
	public static final String RPC_TYPE_MQ = "M";

	/**
	 * ES_OPEN_SERVICE_CFG接口动作 - 新增
	 */
	public static final String SERVICE_ACTION_CODE_ADD = "A";

	/**
	 * ES_OPEN_SERVICE_CFG接口动作 - 修改
	 */
	public static final String SERVICE_ACTION_CODE_UPDATE = "M";

	/**
	 * ES_OPEN_SERVICE_CFG接口动作 - 删除
	 */
	public static final String SERVICE_ACTION_CODE_DELETE = "D";

	/**
	 * ES_OPEN_SERVICE_CFG接口动作 - 查询
	 */
	public static final String SERVICE_ACTION_CODE_QUERY = "Q";

	/**
	 * 业务对象基础类型初始值 - Integer
	 */
	public static final Integer INTEGER_CONSTS = 0;

	/**
	 * 消息队列状态 -未发送
	 */
	public static final String CO_QUEUE_STATUS_WFS = "WFS";

	/**
	 * 消息队列状态 -发送中
	 */
	public static final String CO_QUEUE_STATUS_FSZ = "FSZ";

	/**
	 * 消息队列状态 -响应成功
	 */
	public static final String CO_QUEUE_STATUS_XYCG = "XYCG";

	/**
	 * 消息队列状态 -响应失败
	 */
	public static final String CO_QUEUE_STATUS_XYSB = "XYSB";

	/**
	 * 消息队列状态 -订单挂起（特殊处理）
	 */
	public static final String CO_QUEUE_STATUS_DDGQ = "DDGQ";

	/**
	 * 处理状态 0:待处理
	 */
	public static final String CO_QUEUE_WORK_STATE_0 = "0";

	/**
	 * 处理状态 1:已处理
	 */
	public static final String CO_QUEUE_WORK_STATE_1 = "1";

	/**
	 * 订单队列业务数据类型 添加
	 */
	public static final String CO_QUEUE_BUSI_TYPE_ADD = "ADD";

	/**
	 * 订单队列业务数据类型 拆单
	 */
	public static final String CO_QUEUE_BUSI_TYPE_SPLIT = "SPLIT";

	/**
	 * 订单标准化ApiMethodName - 标准化
	 */
	public static final String SERVICE_CODE_ORDERSTANDING = "zte.net.orderstd.orderStanding";

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
	 * 订单标准化ApiMethodName - 模版转换标准化
	 */
	public static final String SERVICE_CODE_TEMPLATESORDERSTANDARD = "zte.net.orderstd.templatesOrderStandard";

	/**
	 * 订单标准化ApiMethodName - 新商城订单标准化
	 */
	public static final String SERVICE_CODE_HSMALLORDERSTANDARD = "zte.net.orderstd.HSMallOrderStandard";
	
	/**
	 * 订单标准化outService - 标准化
	 */
	public static final String OUT_SERVICE_CODE_ORDERSTANDING = "create_order_orderStanding";

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
	public static final String OUT_SERVICE_CODE_TAOBAOFXORDERSTANDARD = "create_order_taoBaoFenxiaoOrderStandard";
	
	/**
	 * 订单标准化outService - 模版转换标准化
	 */
	public static final String OUT_SERVICE_CODE_TEMPLATESORDERSTANDARD = "create_order_templatesOrderStandard";

	/**
	 * 订单标准化outService - 华盛订单标准化
	 */
	public static final String OUT_SERVICE_CODE_HSMALLORDERSTANDARD = "create_order_HSMallOrderStandard";
	
	/**
	 * 订单标准化outService - 浙江本地商城标准化
	 */
	public static final String OUT_SERVICE_CODE_ZJLOCALMALLORDERSTANDARD = "create_order_ZJLocalMallOrderStandard";


	/*********************************** http接口协议静态数据-开始 *****************************************/
	/**
	 * http接口协议静态数据-成功
	 */
	public static final String HTTP_RESP_CODE_SUCC = "0000";
	/**
	 * http接口协议静态数据-超时失败，具体返回由代码确定
	 */
	public static final String HTTP_RESP_CODE_TIMEOUT = "5000";
	/**
	 * http接口协议静态数据-系统异常
	 */
	public static final String HTTP_RESP_CODE_FAIL = "1000";
	/**
	 * http接口协议静态数据-查询成功,但是未返回数据
	 */
	public static final String HTTP_RESP_CODE_QUERY_NO_DATA = "000x";
	/**
	 * http接口协议静态数据-成功
	 */
	public static final String HTTP_RESP_RESULT_SUCC = "0";
	/**
	 * http接口协议静态数据-超时失败，具体返回由代码确定
	 */
	public static final String HTTP_RESP_RESULT_TIMEOUT = "-2";
	/**
	 * http接口协议静态数据-系统异常
	 */
	public static final String HTTP_RESP_RESULT_FAIL = "-1";
	/**
	 * http接口协议静态数据-成功
	 */
	public static final String HTTP_RESP_MSG_SUCC = "成功";
	/**
	 * http接口协议静态数据-超时失败
	 */
	public static final String HTTP_RESP_MSG_TIMEOUT = "超时失败";
	/**
	 * http接口协议静态数据-系统异常
	 */
	public static final String HTTP_RESP_MSG_FAIL = "系统异常";
	/*********************************** http接口协议静态数据-结束 *****************************************/
	
	/**
	 * BASE-YES--1
	 */
	public static final String BASE_YES_FLAG_1 = "1";//
	
	/**
	 * BASE-NO--0
	 */
	public static final String BASE_NO_FLAG_0 = "0";//否
	
	/**
	 * 服务编码解析路径配置es_config_inf
	 */
	public static final String SERVICE_CODE_PATH = "SERVICE_CODE_PATH";   
	
	/**
	 * 订单重复性校验NAMESPACE
	 */
	public static final int ORDER_DUPLICATE_CHECK_NAMESPACE = 5000;
	
	/**
	 * 订单重复性校验NAMESPACE缓存保存时间  25天
	 */
	public static final int ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT = 25*24*60*60;
	
	/**
	 * 模板类型 - 转入
	 */
	public static final String ORDER_TEMPLATE_TYPE_IN = "in";				
	/**
	 * 模板类型 - 转出
	 */
	public static final String ORDER_TEMPLATE_TYPE_OUT = "out";       
	/**
	 * 模板类型 - 转入|转出
	 */
	public static final String ORDER_TEMPLATE_TYPE_IN_OUT = "in#out";	
	
	/**
	 * 模板类型 - 转出|转入
	 */
	public static final String ORDER_TEMPLATE_TYPE_OUT_IN = "out#in";		
	
	/**
	 * 新商城调用dubbo方式校验值
	 */
	public static final String NEW_MALL_SOURCE_FROM_WYG = "100312";
	
	/**
	 * 2.0新商城报文shipping_type
	 */
	public static final String NEW_MALL_SHIPPING_TYPE_2 = "XJ";
	
	/**
	 * 模版转换-头部编码
	 */
	public static final String TEMPLATE_CONVERT_HEAD_CODE = "zte.service.orderqueue.orderQueueHeadIn";
	
	/**
	 * 模版转换-头部版本
	 */
	public static final String TEMPLATE_CONVERT_CODE_VERSION = "100";
	
	/**
	 * 模版转换rop调用地址
	 */
	public static final String  TEMPLATE_REMOTE_ROP_ADDRESS = "TEMPLATE_REMOTE_ROP_ADDRESS";
	
	/**
	 * 订单队列表报文类型 - xml
	 */
	public static final String ORDER_QUEUE_MSG_TYPE_XML = "xml";
	
	/**
	 * 订单队列表报文类型 - json
	 */
	public static final String ORDER_QUEUE_MSG_TYPE_JSON = "json";
	
	/**
	 * oracle当前时间
	 */
	public static final String DB_ORACLE_CURRENT_TIMER = "sysdate";
	
	/**
	 * dubbo调用校验系统开关
	 */
	public static final String DUBBO_CHECK_SYS = "DUBBO_CHECK_SYS";
	
	/**
	 * dubbo调用校验系统-是
	 */
	public static final String DUBBO_CHECK_SYS_YES = "yes";
	
	/**
	 * mq调用校验系统开关
	 */
	public static final String MQ_CHECK_SYS = "MQ_CHECK_SYS";
	
	/**
	 * mq调用校验系统-是
	 */
	public static final String MQ_CHECK_SYS_YES = "yes";
	
	
	/** 队列异常写异常单系统开关 */
	public static final String INSERT_QUEUE_TO_EXP = "INSERT_QUEUE_TO_EXP";
	
	/** 队列异常写异常单系统开关 -是 */
	public static final String INSERT_QUEUE_TO_EXP_YES = "yes";
	
	/** 订单标准化方式 */
	public static final String ORDER_STD_OLD_SYS_CF_ID = "order_std_old_sys"; // 标准化方式配置cf_id
	public static final String ORDER_STD_OLD_SYS_YES = "yes"; // 旧系统标准化
	 
	
	/** 旧系统订单标准化地址配置 */
	public static final String SERVER_IP_ADDR_NEWMALLORDER = "SERVER_IP_ADDR_NEWMALLORDER"; // 新商城
	public static final String SERVER_IP_ADDR_CENTERMALLORDER = "SERVER_IP_ADDR_CENTERMALLORDER"; // 总商
	
	
	/**
	 * es_abgray_ord_env_rel_log.opcode
	 */
	public static final String ABGRAY_ORD_ENV_REL_LOG_OPCODE = "OrderQueueLog";
	
	public static final String HS_SOURCE_FROM_B2C = "10061";	//B2C订单
	public static final String HS_SOURCE_FROM_B2B = "10062";	//B2B订单
	
	public static final String SIGN_STATUS_0 = "0";//物流状态：未签收
	public static final String SIGN_STATUS_1 = "1";//物流状态：已签收	
	
	public static final String QUERY_ORDER = "queryOrder";//订单查询
	public static final String MY_ORDER = "myOrder";//我的订单

}
