package zte.net.ecsord.common;

public class InfConst {

	/***** 接口请求类型开始 ******/
	public static final String WMS_AT_SYN_ACCOUNT = "at_syn_account"; // 订单业务完成状态通知（接收方WMS）
	public static final String WMS_WL_SYN_ORDER = "wl_syn_order"; // 订单信息同步到WMS
	public static final String WMS_AT_SYN_CARD = "at_syn_card"; // 写卡完成通知WMS
	public static final String WMS_AT_SYN_STATUS = "at_syn_status"; // 通知WMS订单状态
	public static final String WMS_WL_SYN_AUDITING = "wl_syn_auditing"; // 从WMS获取订单号
	public static final String ORDER_FLOW_SUBMMIT = "order_flow_submmit"; // 同步订单信息给老订单系统
	/***** 接口请求类型结束 ******/

	/***** 接口报文类型开始 ******/
	public static final String INF_PARAM_TYPE_XML = "xml";
	/***** 接口报文类型结束 ******/

	// 接口常量定义-应答编码
	public static final String RESP_CODE_SUCC = "0000";// 成功
	public static final String RESP_CODE_9999 = "9999";// 其他错误

	// 订单信息同步到总部接口应答编码定义
	public static final String ORDER_RESP_CODE_0001 = "0001";// 编码转换失败
	public static final String ORDER_RESP_CODE_0002 = "0002";// 商品信息异常
	public static final String ORDER_RESP_CODE_0003 = "0003";// 合约信息异常
	public static final String ORDER_RESP_CODE_0004 = "0004";// 终端机型编码不存在
	public static final String ORDER_RESP_CODE_0005 = "0005";// 号码预占、预订失败
	public static final String ORDER_RESP_CODE_0006 = "0006";// 商城入库失败
	public static final String ORDER_RESP_CODE_0007 = "0007";// 操作人信息不存在

	// 货品信息通知应答编码定义
	public static final String PROD_RESP_CODE_0001 = "0001";// 货品信息与订单不符，请核实
	public static final String PROD_RESP_CODE_0002 = "0002";// 终端预占失败

	// 开户预校验接口应答编码定义
	public static final String ACC_RESP_CODE_0001 = "0001";// 客户验证异常
	public static final String ACC_RESP_CODE_0002 = "0002";// 号码验证异常
	public static final String ACC_RESP_CODE_0003 = "0003";// 卡信息验证异常
	public static final String ACC_RESP_CODE_0004 = "0004";// 资源验证异常
	public static final String ACC_RESP_CODE_0005 = "0005";// 业务验证与费用计算异常
	public static final String ACC_RESP_CODE_0006 = "0006";// 受理渠道非电子渠道
	public static final String ACC_RESP_CODE_0007 = "0007";// 证件类型为集客证件，不允许创建新客户

	// 写卡数据查询接口应答编码定义
	public static final String WRITECARD_RESP_CODE_0001 = "0001";// 预占IMSI失败

	// 森锐退卡,卡回收请求类型
	public static final String SR_RECYCLE_CARD = "recycleCard";// 回收卡
	public static final String SR_OUT_CARD = "outCard";// 退卡
	public static final String SR_READ_CARDNO = "readCardNo";//获取ICCID
	public static final String SR_WRITECARD_DATA = "writeCard";//写卡数据
//	public static final String SR_WRITECARD_KEY = "D4C3C86EF4DE4FD3AA7872EAA746A774";//key值
	public static final String SR_WRITECARD_KEY = "DD1F5DEA01F54BEAA4BEA847A13B2190";
	

	// 订单信信息查询编码定义
	public static final String ORDER_QUERY = "order_query";// 请求类型
	public static final String RESP_CODE_FAILURE = "0001";// 应答编码-失败
	public static final String RESP_CODE_SUCCESS = "0000";// 应答编码-成功
	public static final String POST_TYPE_0 = "0";// 配送类型-正常发货
	public static final String POST_TYPE_1 = "1";// 配送类型-补发
	public static final String POST_TYPE_2 = "2";// 配送类型-重发

	// 订单信息查询结果类型
	public static final String ORDER_BASE_INFO = "1000"; // 订单基础信息
	public static final String ORDER_PAY_INFO = "1001"; // 订单支付信息
	public static final String ORDER_CARRY_INFO = "1002"; // 收获地址信息
	public static final String ORDER_POST_INFO = "1003"; // 订单配送信息
	public static final String ORDER_PACK_INFO = "1004"; // 订单商品包信息
	public static final String ORDER_GOOD_INFO = "2001"; // 货品信息
	public static final String ORDER_GIFT_INFO = "2002";// 赠品信息
	public static final String ORDER_ACCOUNT_INFO = "2003"; // 开户信息
	
	//socket接口--余额转移沟通
	public static final String SOCKET_VERSION = "10";//与socket接口协议版本号
	public static final String FEE_INFORM_SERVICE_TYPE = "700000003008";//商城收费、退款成功触发接口--服务类型
	public static final String ORDER_ACCOUNTORBUYBACK_INFORM_SERVICE_TYPE = "700000003009";//订单开户、返销完工触发接口--服务类型
	public static final String A7 = "F010ZB";//营业点 业务受理地点
	public static final String A8 = "SUPERUSR";// 营业员 业务受理人
	public static final String SOCKET_TAB = (char)0x09+""; //字段之间间隔符 
	public static final String SOCKET_END = (char)0x1a+""; //包结束标识  

}
