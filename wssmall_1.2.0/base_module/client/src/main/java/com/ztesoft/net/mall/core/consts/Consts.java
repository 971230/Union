package com.ztesoft.net.mall.core.consts;


/**
 *常量定义类
 * 
 * @author wui
 * 状态 0申请、1正常、2冻结、3注销,-1审核部通过
 */
public class Consts {
	
	 public final static String SYSDATE = "sysdate"; //DBTUtil.current();// 设置时间为系统时间
	 public final static String NOW = "now()";
	 public final static String PARTNER_STATE_ALL="all";//正常
	 public final static String PARTNER_STATE_APPLY="0";//申请
	 public final static String PARTNER_STATE_NORMAL="1";//正常
	 public final static String PARTNER_STATE_CONGELATION="2";//冻结
	 public final static String PARTNER_STATE_LOGOUT="3";//注销
	 public final static String PARTNER_STATE_NOTGO="-1";//审核不通过
	 public final static String PARTNER_AUDIT_ING="-2";//变更中
	 public final static String PARTNER_AUDIT_NOTGO="-3";//变更审核不通过
	 public final static Integer PARTNER_SEQ_0=0;//有效数据
	 public final static Integer PARTNER_SEQ_W1=-1;//待审数据
	 public final static Integer PARTNER_SEQ_W2=-2;//不通过数据
	 public final static Integer DEFAULT_ADDR=1;//缺省地址
	 public final static String SOURCE_FROM = "source_from";//应用来源
	 public final static String WSSMALL = "WSSMALL";
	 public final static String ES_CO_QUEUE="es_co_queue";//es_co_queue表名
	 public final static String ES_CO_QUEUE_BAK="es_co_queue_bak";//es_co_queue_bak表名
	 
	 public final static String ALARM_TASK_TYPE_1="1";//告警任务类型(对账告警1)
	 public final static String ALARM_TASK_TYPE_2="2";//告警任务类型(任务告警2)

	 public final static String SUPPLIER_RELTYPE="supplier";//关联类型(供货商)
	 public final static String SUPPLIER_STAFF_RELTYPE="supplier_staff";//关联类型(供货商员工)
	 
	 public final static String PARTNER_STATE_ONE_ALL="allo";//一级查询全部
	 public final static String PARTNER_STATE_TOW_ALL="allt";//二级查询全部
	 
	 public final static int SUPPLIER_FOUNDER = 4;//供货商角色
	 
	 //-----------------------------------工号角色-------------------------
	 public final static String CURR_FOUNDER_0="0";//电信员工
	 public final static int CURR_FOUNDER0=0;//电信员工
	 
	 public final static String CURR_FOUNDER_1="1";//超级管理员
	 public final static int CURR_FOUNDER1=1;//超级管理员
	 
	 public final static String CURR_FOUNDER_2="2";//二级
	 public final static int CURR_FOUNDER2=2;//二级
	 
	 public final static String CURR_FOUNDER_3="3";//一级
	 public final static int CURR_FOUNDER3=3;//一级
	 
	 public final static int CURR_FOUNDER4=4;//供货商
	 public final static String CURR_FOUNDER_4="4";//供货商
	 
	 public final static int CURR_FOUNDER5=5;//供货商员工
	 public final static String CURR_FOUNDER_5="5";//供货商员工
	 
	 public final static int CURR_FOUNDER10=10;//会员
	 public final static String CURR_FOUNDER_10 = "10";//会员
	 
	 public final static int CURR_FOUNDER6=6;//代销商 。福建用
	 public final static String CURR_FOUNDER_6 = "10";//代销商
	 
	 public final static int CURR_FOUNDER7 =7;//移动员工
	 public final static String CURR_FOUNDER_7="7";
	 public final static int CURR_FOUNSER8 =8;//移动分销商
	 public final static String CURR_FOUNDER_8="8";
	 
	 public final static String GROUD_USER_SUPER="super";//上级工号
	 public final static String GROUD_USER_SELF="self";//自身
	 
	 
	 
	 //-------------------------------------工号角色-------------------------
	 
	 public final static String A_0="A0";//针对超级管理员 待审核状态
	 public final static String B_0="B0";//针对一级 待审核状态
	 public final static String B_1="B1";//针对一级  审核处理中
	 public final static String B_2="B2";//针对一级  审核不通过
	 public final static String C_1="C1";//针对二级 审核处理中
	 public final static String C_2="C2";//针对二级 审核不通过
	 
	 public final static String PARNTER_APPLY_WAIT="欢迎您的注册，我们将尽快处理您的请求，请耐心等待";
	// public final static String PARNTER_OPEN="您的分销账户已开户";
	 public final static String PARNTER_FIELD_AUDIT_MESS="修改的数据有需审核的部分，我们将尽快审核，请耐心等待";
	 public final static String PARNTER_APPLY_SUCCESS="您的注册申请已经通过，我们将尽快为您申请系统工号，请耐心等待";
	 public final static String PARNTER_APPLY_FAIL="抱歉的通知您，您的注册申请被拒，您可通过江西采购平台系统重新申请注册。";
	 public final static String PARNTER_AUDIT_FIELD_SUCCESS="您的资料变更审核通过";
	 public final static String PARNTER_AUDIT_FIELD_FAIL="您的资料变更审核不通过";
	 
//	 public final static String PARTNER_SHOP_TYPE="0A";//0A淘宝商铺
	 
	 
	 public final static String user_state_0 ="0";//用户状态（禁用）
	 public final static int user_state0 =0;//用户状态（禁用）
	 public final static String user_state_1 ="1";//用户状态（开启）
	 public final static int user_state1 =1;//用户状态（开启）
	 public final static String other_state_2="2";//其他状态
	 public final static int other_state2=2;//其他状态
	 
	 public final static int reciverMessage_1 = 1;//区分查询收件箱信息
	 public final static int senderMessage_2 = 2;//区分查询发件箱信息
	
	 public final static String AUDIT_PATNER_LIST_0="0";//待审核分销商
	 public final static String FIELD_AUDIT_LIST_1="1";//待审核资料变更
	 public final static String BLOCK_LIST_2="2";//已冻结分销商

	 public final static int READ_STATE_1 = 1;//读写状态 已读
	 public final static int READ_STATE_0 = 0;//读写状态 未读
	 public final static int READ_STATE_2 = 2;//读写状态 已删除
	 public final static int READ_STATE_3 = 3;//读写状态 未删除
	 public final static int READ_STATE_4 = 4;//读写状态 已回复
	 
	 public final static String LAN_PROV="0000";//全省
	
	 public final static String LAN_PROV_EMPTY="";//全省(查询时使用)
	 
	 public final static String ACC_NBR_STATE_0="0";//可用
	 public final static String ACC_NBR_STATE_1="1";//预占(网厅预占,或者淘宝预占)
	 public final static String ACC_NBR_STATE_2="2";//已用(CRM受理成功)
	 public final static String ACC_NBR_STATE_3="3";//失效
	 
	 
	 public final static String loginFail_type = "登录失败";
	 public final static String loginSuccess_type = "登录成功";
	 
	 
	 public final static String Message_reciver_0="0";//收件人  所有的电信用户
	 public final static String Message_reciver_1="1";//所有的超级管理员
	 public final static String Message_reciver_2="2";//所有的二级分销商
	 public final static String Message_reciver_3="3";//所有的一级分销商
	 
	 
	 
	 public final static String FREE_REMARK="自动支付";//失效
	 
	 
	 //是否拥有库存
	 public final static String HAVE_STOCK_1="1";//是
	 public final static String HAVE_STOCK_0="0";//否
	 

	 
	 
	 public final static String DEF_LOGI_ID="1";//
	 
	 public final static String DEF_LOGI_NAME="EMS";//
	 
	 public final static String DEF_DLY_TYPE="6";//物流公司
	 
	 
	 
	 public final static String ACCEPT_STATE_SUCC="1";//受理成功
	 public final static String ACCEPT_STATE_FAIL="-1";//受理失败
	 public final static String ACCEPT_STATE_WAIT="0";//待处理
	 
	 
	 public final static Integer PAYMENT_ID_BANK=2;//在线支付
	 public final static Integer PAYMENT_ID_DEPOST=4;//预存金支付
	 public final static Integer PAYMENT_ID_AUTO =5;//免费支付
	 
	 
	 public final static String SHIP_ID_NET ="7";//电信自动
	 public final static String SHIP_ID_PARTNER_ONE ="8";//一级分销商自送
	 public final static String SHIP_ID_PARTNER_TWO ="9";//二级分销商自送
	 
     public static final String GOODS_USAGE_STATE_0 = "0";//有效
	 public static final String GOODS_USAGE_STATE_1 = "1";//无效
	 
	 public static final String SERVICE_TYPE_DEPOST_PAY = "depost_pay";//预存款充值
	 public static final String SERVICE_TYPE_CLOUD_PAY = "cloud_pay";//云卡支付
	 
	 
	 
	 public static final String BANK_BACK_PAY_SUCC = "0000";//银行返回成功
	 public static final String BANK_PAYED_SUCC = "2";//银行支付成功
	 public static final String BANK_PAYED_FAIL = "1";//银行支付失败
	 public static final String BANK_PAYED_EXCEPTION = "12";//银行支付成功
	 public static final String PAY_DEPOSIT = "DEPOSIT"; //预存金充值
	 public static final String BANK_PAYED_AND_BILL_PAYED_SUCC = "4";//银行已付款，且计费冲正成功
	 
	 public static final String BANK_PAYED_AND_BILL_PAYED_FAIL = "3";//银行已付款，且计费冲正失败
	 
	 
	 
	 
	 public static final String PAY_SOURCE_CLOUD_PAY = "001";//云卡网银在线支付 
	 
	 //插入费用日志表默认为未处理，当银行返回成功后，更新状态为已处理，并更新银行订单金额

	 
	 public static final String MASSAGE_CONSTS = "MASSAGE";//成功
	 public static final String CODE_SUCC = "0000";//成功
	 public static final String CODE_FAIL = "-1";//失败
	 
	 
	 //商品发布区域状态：0待审核,1上架,2审核不通过,3下架
	 public final static String AREA_STATE_0="0";//0待审核
	 public final static String AREA_STATE_1="1";//1审核通过
	 public final static String AREA_STATE_2="2";//2审核不通过
	 public final static String AREA_STATE_3="3";//3下架
	 
	 //商品状态
	 public final static Integer GOODS_DISABLED_0=0;//正常列表中
	 public final static Integer GOODS_DISABLED_1=1;//回收站列表中
	 
	 //充值卡资源池状态
	 public final static String CARD_INFO_STATE_0 = "0"; //0可用
	 public final static String CARD_INFO_STATE_1 = "1";//预占
	 public final static String CARD_INFO_STATE_2 = "2";//已用
	 public final static String CARD_INFO_STATE_3 = "3";//失效
	 public final static String CARD_INFO_STATE_4 = "4";// 充值失败 
	 
	 //云卡资源池状态
	 public final static String CLOUD_INFO_STATE_0 = "0"; //0可用
	 public final static String CLOUD_INFO_STATE_1 = "1"; //预占
	 public final static String CLOUD_INFO_STATE_2 = "2"; //已用
	 public final static String CLOUD_INFO_STATE_3 = "3"; //失效
	 
	 //云卡来源 0批量导入，1云卡批开 
	 public final static String CLOUD_FROM_SRC_0 = "0"; //excel or txt
	 public final static String CLOUD_FROM_SRC_1 = "1"; //crm
	 
	 //证件类型
	 public final static String CERTI_TYPE_G = "G";  //外地身份证
	 public final static String CERTI_TYPE_H = "H";  //香港身份证
	 public final static String CERTI_TYPE_A = "A";  //本地身份证
	 public final static String CERTI_TYPE_P = "P";  //其他
	 public final static String CERTI_TYPE_I = "I";  //澳门身份证
	 public final static String CERTI_TYPE_D = "D";  //单位公章证明
	 public final static String CERTI_TYPE_E = "E";  //工商执照
	 public final static String CERTI_TYPE_B = "B";  //军人证
	 public final static String CERTI_TYPE_C = "C";  //户口簿
	 public final static String CERTI_TYPE_F = "F";  //教师证
	 
	 //批量导入数据类型
	 public final static String BATCH_CUST = "cust";  	//客户资料批量反档
	 public final static String BATCH_ORDER = "order";  //订单批量导入
	 
	 public final static String BATCH_ORDER_CONTRACT = "CONTRACT";  //合约机订单导入
	 public final static String BATCH_ORDER_CUSTRETURNRD = "CLOUD";  //云卡资料返档订单导入
	 public final static String BATCH_ORDER_CUSTRETURNRD_LAN = "CLOUDWITHLAN";  //云卡资料返档订单导入
	 
	 public final static String BATCH_OPEN_WALLET = "open_wallet"; //批开钱包导入
	 public final static String BATCH_CHARGE_WALLET = "charge_wallet"; //批充钱包导入
	 public final static String BATCH_CHARGE_BILL = "charge_bill"; //批充话费导入
	 public final static String BATCH_ADJUNCT = "adjunct"; //商品配件导入
	 public final static String BATCH_CLOUD = "cloud";	//云卡批量导入
	 public final static String BATCH_CARD = "card";	//充值卡流量卡批量导入
	 public final static String BATCH_PHONENUM = "phoneNo";	//充值卡流量卡批量导入
	 
	 public final static String BATCH_LOGI="logi_info"; // 物流信息批量导入
	 
	 //外系统订单同步标识
	 public final static String ORDEROUT_FLAG_001 = "001";  	//未同步
	 public final static String ORDEROUT_FLAG_002 = "002";  	//已同步
	 public final static String ORDEROUT_FLAG_003 = "003";  	//数据异常订单
	 
	 //订单表 表名
	 public final static String ORDER_ES_ORDER = "ES_ORDER";	//
	 public final static String ORDER_ES_PARTNER_DESPOST = "ES_PARTNER_DESPOST";	//预存金充值订单
	 
	 //映射类型
	 public final static String FIELD_MAPPING_0 = "0";	//FIELD_NAME映射
	 public final static String FIELD_MAPPING_1 = "1";	//SEC_FIELD_NAME映射
	 
	 //短信队列状态
	 public final static String MSG_QUEUE_STATE_0 = "0";
	 
	 public final static Integer DC_PUBLIC_STYPE_8018 = 8018;
	 public final static Integer DC_PUBLIC_STYPE_8019 = 8019;
	 public final static Integer DC_PUBLIC_STYPE_8119 = 8119;
	 public final static String DC_PUBLIC_8018_PKEY_1 = "stock_warning";
	 public final static String DC_PUBLIC_8018_PKEY_2 = "partner_exp_warning";
	 public final static String DC_PUBLIC_8018_PKEY_3 = "partner_exp";
	 public final static String DC_PUBLIC_8019_PKEY_1 = "partner_audit";
	 public final static String DC_PUBLIC_8119_PKEY_1 = "partner_add";
	 
	 
	 public final static Integer DC_PUBLIC_STYPE_98761 = 98761;//供应商员工权限
	 public final static Integer DC_PUBLIC_STYPE_98762 = 98762;//供应商管理员权限
	 public final static Integer DC_PUBLIC_STYPE_98763 = 98763;//供货商注册未审核权限
	 public final static Integer DC_PUBLIC_STYPE_98764 = 98764;//经销商权限
	 public final static String DC_PUBLIC_98761_PKEY = "supplier_staff";//供应商员工权限
	 public final static String DC_PUBLIC_98762_PKEY = "supplier_admin";//供应商管理员权限
	 public final static String DC_PUBLIC_98763_PKEY = "supplier_no_register";//供货商注册未审核权限
	 public final static String DC_PUBLIC_98764_PKEY = "distributor";//经销商权限
	 public final static String DC_PUBLIC_STYPE_LHJB = "20140604";  //靓号级别
	 
	 
	 //合约受理状态：1成功 \-1失败\0待受理
	 public final static String CONTRACT_STATE_SUCC = "1";
	 public final static String CONTRACT_STATE_FAIL = "-1";
	 public final static String CONTRACT_STATE_WAIT = "0";
	 
	 public final static Integer DC_PUBLIC_STYPE_8025 = 8025;
	 public final static Integer DC_PUBLIC_STYPE_8017 = 8017;
	 public final static Integer DC_PUBLIC_STYPE_8016 = 8016;
	 public final static Integer DC_PUBLIC_STYPE_8007 = 8007;//店铺类型
	 public final static Integer DC_PUBLIC_STYPE_8008 = 8008;//网店量级

	 public final static String DC_PUBLIC_STYLE_8027="8027";//店铺映射店铺类型
	 
	 public final static String DC_PUBLIC_DEF_ROLE = "9999";
	 public final static String DC_PUBLIC_DEF_ACC_SCOPE_LIMIT = "8888"; //上下浮动区域
	 public final static String DC_PUBLIC_DEF_ACC_MAX_COUNT = "88888";//申请号码最大个数
	 
	 
	 //云卡、充值卡
	 public final static String SHIP_STATE_0 = "0"; //CRM云卡商品审核时需要预占
	 public final static String SHIP_STATE_1 = "1"; //CRM云卡调拨处理中
	 public final static String SHIP_STATE_2 = "2"; //CRM云卡调拨处理成功
	 public final static String SHIP_STATE_3 = "3"; //CTRM云卡调拨处理失败
	 public final static String SHIP_STATE_4 = "4"; //待发货
	 public final static String SHIP_STATE_5 = "5"; //已发货
	 
	 //预存金工单类型
	 public final static String BUSI_TYPE_605 = "605";  //电子分销系统缴纳预存金
	 public final static String BUSI_TYPE_606 = "606";  //电子分销系统缴纳预存金回退
	 public final static String SYS_TYPE = "1";
	 public final static String SHIP_STATE_00 = "00"; //调拨二级分销商预占
	 public final static String SHIP_STATE_11 = "11"; //调拨二级分销商处理中
	 public final static String SHIP_STATE_22=  "22"; //调拨二级分销商处理成功
	 
	 
	 
	 //若发布地位**个，则定义为全省发布
	 public final static int LAN_LOCATION = 14;	
	 
	 //是否申请查看上级的卡库存
	 public final static String APPLY_SHOW_PARENT_STOCK = "yes";
	 
	 //是否跳转至商品添加页 
	 public static final String GOODS_ADD_PAGE_ID= "goods_add";
	 
	 //对账预警额设置
//	 public static final int CMP_BILL_ERROR_NUM = 10;			//异常单预警数量
//	 public static final int CMP_BILL_ERROR_MONEY = 100000;		//金额异常预警，单位为分
	 public static final int CMP_CHARGE_ERROR_NUM = 20;			//流量卡、充值卡异常预警数量

	 //失败充值卡扫描处理次数
	 public static final int MAX_DEAL_COUNT = 5;
	 
	 
	 //导入数据时的错误类型
	 public static final String MSG_DUPICATE = "0"; 	//重复数据
	 public static final String MSG_NULL = "1";			//空数据
	 public static final String MSG_WRONG_TYPE = "2";	//拆分txt数据时出错
	 public static final String MSG_WRONG_EXCEL = "3";	//模板出错
	 public static final String MSG_WRONG_GOOD = "4";	//导入商品不匹配
	 
	 public static final String REQUEST_FLAG_00A = "00A"; 	//充值卡、流量卡充值 请求商品有误
	 public static final String REQUEST_FLAG_00S = "00S";	//请求成功
	 
	 /**
 		取值方式为aabbccdd
		aa: 10－集团SGW接入；20－省平台接入；99－其他； 
		bb: 01－IVR；02－SMS；03－WEB；04－WAP；05－USSD；06－CRM；07－第三方；99－其他；
		cc: 01－集团网厅；02－集团掌厅；03－电信客户端；04－爱必灵； 05－集团电子渠道；
		06－集团翼支付客户端；07－支付公司外部合作渠道；99-其它
		dd: 预留，默认填00；
	  */
	 public static final String CARD_ACCESS_TYPE = "20039900";
	 
	 
	 
	 public static final String ITEM_CARD_ACC_COL1_STATE1 = "1";	//号码段调拨号码不足
	 public static final String ITEM_CARD_ACC_COL1_STATE2 = "2";	//号码段调拨号码足
	 
	 public static final String DEF_CUST_PASSWORD = "123";	//客户缺省密码
	 public static final String DEF_CERTI_TYPE_A = "A";	//缺省为身份证
	 
	 
	 
	 public static final String ACCNBR_QUERY_TYPE_00A = "00A";	//查询分销商号码
	 
	 public static final String APPLY_TYPE_NET = "NET";	//电信申请
	 
	 //对账错误类型信息
	 public static final String COMP_BILL_ERROR_00A = "00A"; 		//数据清除、归档异常
	 public static final String COMP_BILL_ERROR_00B = "00B"; 		//获取银行参数异常
	 public static final String COMP_BILL_ERROR_00C = "00C";		//解析银行对账文件异常
	 public static final String COMP_BILL_ERROR_00D = "00D";		//生成对账数据异常
	 public static final String COMP_BILL_ERROR_00E = "00E";		//对账数量异常
	 public static final String COMP_BILL_ERROR_00F = "00F";		//对账金额异常
	 public static final String COMP_BILL_ERROR_00G = "00G";		//对账逻辑异常
	 
	 public static final String IMPORT_STATE_0 = "0";	//导入成功
	 public static final String IMPORT_STATE_1 = "1";	//导入失败
	 
	 //短信发送配置表
	 public static final String COMP_BILL_NBRS = "COMP_BILL_NBRS";					//计费对账失败表
	 public static final String CHARGE_FAIL_NBRS = "CHARGE_FAIL_NBRS";				//定时任务充值处理失败、发短信
	 
	 //es_config_info
	 public static final String SCHEDULER_SERVER_IP_ADDR = "SCHEDULER_SERVER_IP_ADDR"; // 执行定时任务服务器ip
	 public static final String SCHEDULER_SERVER_PORT = "SCHEDULER_SERVER_PORT"; // 定时任务指定端口;
	 public static final String CONTEXT_PATH = "wssmall"; // 
	 
	 public static final int MINTUTE_OF_DAY = 24 * 60;
	 
	 public static final String BIZ_CODE_CARD_STOCK = "cardStock";   // 库存告警提醒
	 
	 //获取应用端口号 用户定时任务配置
	 public static final String PORT_SYS_PROP = "PORT_SYS_PROP"; // System.setPropertie(PORT_SYS_PROP, "port");
	 //订单标准化时使用
	 public static final String PORT_SYS_PROP_ENV = "PORT_SYS_PROP_ENV";
	 
	 public static final String REMOTE_ROP_ADDRESS = "REMOTE_ROP_ADDRESS"; //远程rop调用地址
	 /**
	  * servlet容器信息System.setPropertie(SERVLET_CONTEXT_INFO);
	  */
	 public static final String SERVLET_CONTEXT_INFO = "SERVLET_CONTEXT_INFO";
	 
	 public static final int ADV_STATE_0=0;     //广告待审核
	 public static final int ADV_STATE_1=1;     //广告已发布
	 public static final int ADV_STATE_2=2;     //广告审核不通过
	 
	 public static final String TAG_REMD="201308166124000156";
	 
	 public static final String REL_TYEP_SUPPLIER="supplier";
	 public final static String SUPPLIER_RELTYPE_DEALER="dealer";//关联类型(经销商)
	 public static final String REL_TYEP_SUPPELIER_STAFF="supplier_staff";
	 
	 public static final String SERVICE_TYPE_1="1";
	 public static final String SERVICE_TYPE_2="2";
	 public static final String SERVICE_TYPE_3="3";
	 public static final String SERVICE_TYPE_4="4";
	 
	 
	 public static final String GOODS_RULES_CONFIRM = "confirm";		//确认
	 public static final String GOODS_RULES_PAY = "pay";				//支付
	 public static final String GOODS_RULES_ACCEPT = "accept";			//受理
	 public static final String GOODS_RULES_DELIVERY = "delivery";		//发货
	 public static final String GOODS_RULES_QUERY = "query";//查询
	 public static final String GOODS_RULES_CREATE_ORDER = "createorder";//查询
	 
	 public static final String GOODS_RULES_CONFIRM_NAME = "确认";		
	 public static final String GOODS_RULES_PAY_NAME = "支付";				
	 public static final String GOODS_RULES_ACCEPT_NAME = "受理";			
	 public static final String GOODS_RULES_DELIVERY_NAME = "发货";	
	 public static final String GOODS_RULES_QUERY_NAME ="查询";
	 public static final String GOODS_RULES_CREATE_ORDER_NAME ="生成订单";
	 
	 public static final String GOODS_RULES_ENABLE = "0";				//有效
	 public static final String GOODS_RULES_DISABLE = "1";				//无效 
	 
	 
	 public static final String GOODS_AUDIT_NOT = "00I";		//待审核
	 public static final String GOODS_AUDIT_SUC = "00A";		//审核通过
	 public static final String GOODS_AUDIT_FAIL = "00M";		//审核不通过
	 
	 
	 public static final String OTT_ADV_1 = "322";		//ott广告位位置，大图
	 public static final String OTT_ADV_2 = "323";		//ott广告位位置，小图1
	 public static final String OTT_ADV_3 = "324";		//ott广告位位置，小图2
	 
	 public static final String OTT_SERV_TYPE_1 = "1"; //单次
	 public static final String OTT_SERV_TYPE_2 = "2"; //包月
	 
	 public static final String OTT_TMS_REQ_NO_1 = "1"; //订购
	 public static final String OTT_TMS_REQ_NO_2 = "2"; //取消订购

	 public static final String KEY_SOURCE_FROM = "SOURCE_FROM";	//查询平台来源的key
	 
	 public static final String TYPE_REC_GOODS = "4";				//推荐商品标志位
	 
	 public static final String OTT_CONTENT_ABLE = "1";				//有效
	 public static final String OTT_CONTENT_ENABLE = "0";			//无效
	 
	 
	 public static final String WORK_LOG_STATE_SUCC = "0";	//工作日志，成功
	 public static final String WORK_LOG_STATE_FAIL = "1";	//工作日志，失败
	 
	 
	 public static final String ADMIN_ORG_ID = "07XX";
	 
	 public final static String EXP_BUSS="buss";//业务异常
	 public final static String EXP_RUNTIME="runtime";//运行时异常
	 
	 public static final String LOG_BUS_SUCC = "0";		//工作日志成功状态
	 public static final String LOG_BUS_FAIL = "1";		//工作日志失败状态
	 
	 
	 public static final String CHECK_ACCT_CONFIG_ABLE = "1";	//对账配置有效
	 public static final String CHECK_ACCT_CONFIG_ENANLE = "0";	//无效
	 
	 public static final String ALARM_TYPE_BILL = "1";	//对账告警
	 public static final String ALARM_TYPE_TASK = "2";	//任务告警
		 
	 public static final String ALARM_AMOUNT = "alarm_amount";	//对账账单数量告警
	 public static final String ALARM_MONEY = "alarm_money";	//对账账单金额告警
	 public static final String ALARM_CHECKED = "yes";			//是否需要进行对账
	 
	 public static final String REPLACE_BILL_NUM = "alarm_amount";			//需替换的账单数量
	 public static final String REPLACE_BILL_MONEY = "alarm_money";			//需替换的账单金额
	 public static final String REPLACE_BILL_TYPE = "text_pattern";			//需替换的账单格式
	 public static final String REPLACE_BILL_TIME = "reconciliation_time";	//需替换的当前时间
	 
	 public static final String CHECK_ACCT_TMPL_ABLE = "0";	//告警任务模板有效
	 public static final String CHECK_ACCT_TMPL_ENANLE = "1";	//无效
	 
	 
	 public static final String TASK_MSG_NOT_SEND = "0";	//es_task表 msg_state 未发送
	 public static final String TASK_MGS_SEND = "1";	//es_task表 msg_state 已发送
	 
	 public static final String TASK_CATE_AMOUNT = "0";	//es_task表 task_cate 销售量
	 public static final String TASK_CATE_MONEY = "1";	//es_task表 task_cate 销售金额
	 
	 public static final String TASK_FINISH = "0";		//任务完成
	 public static final String TASK_UNFINISHI = "1";	//任务未完成
	 
	 
	 public static final String TASK_CATE_0 = "0"; //销售量
	 public static final String TASK_CATE_1 = "1"; //销售额
	 
	 
	 public static final Integer NOTICE_STATE_11 = 11;	//公告待审核
	 public static final Integer NOTICE_STATE_12 = 12;	//公告已发布
	 public static final Integer NOTICE_STATE_13 = 13;	//公告审核不通过
	 
	 
	 public static final String article_cat_cacheName ="article_cat";
	 
	 
	 public static final String TERMINAL_CODE = "TERMINAL_CODE";//终端
	 public static final String CONTRACT_CODE = "CONTRACT_CODE";//合约
	 public static final String CONTRACT_OFFER  = "CONTRACT_OFFER";//合约关联计划关系类型rel_type
	 public static final String PRO_REL_GOODS  = "PRO_REL_GOODS";//商品与货品关系类型rel_type
	 public static final String MONTH_FEE = "month_fee";//套餐档次
	 public static final String OFFER_ID = "10002";//套餐类型ID
	 
	 
	 /************** 权限类型常量 ******************/
	 public static final String AUTH_MENU = "menu";	//菜单权限
	 public static final String AUTH_APP = "app";	//app权限
	 public static final String AUTH_BTN = "btn";	//按钮权限
	 public static final String AUTH_DATA = "data";	//数据权限
	 /************** 权限类型常量 ******************/
	 
	 
	 /******************* 推广人模块常量 *************************/
	 public static final String SPREAD_ABLE = "00A";	//可用
	 public static final String SPREAD_ENABLE = "00X";	//不可用
	 
	 
	 public static final String SPREAD_SERVICE_GOODS = "goods";		//业务类型 商品
	 public static final String SPREAD_SERVICE_SERVER = "server";	//业务类型 服务
	 public static final String SPREAD_SERVICE_COMMON = "common";	//业务类型 通用
	 
	 public static final String SPREAD_OBJECT_ADMIN = "admin";		//推荐人类型 系统用户
	 public static final String SPREAD_OBJECT_MEMBER = "member";	//推荐人类型 系统会员
	 public static final String SPREAD_OBJECT_OUTER = "outer";		//推荐人类型 外部人员
	 
	 public static final String QRY_SPREAD_BY_MOBILE = "0";
	 public static final String QRY_SPREAD_BY_ID = "1";
	 
	 
	 /******************* 推广人模块常量  *************************/
	 
	 /** 接口返回编码 */
	 public static final String RESP_CODE_000 = "000";  //接口返回成功
	 public static final String RESP_CODE_0 = "0";  //接口返回成功---zengxianlian
		
	 /** 消息队列状态*/
	 public static final String CO_QUEUE_STATUS_WFS = "WFS";  //未发送
	 public static final String CO_QUEUE_STATUS_FSZ = "FSZ";  //发送中
	 public static final String CO_QUEUE_STATUS_XYCG = "XYCG";  //响应成功
	 public static final String CO_QUEUE_STATUS_XYSB = "XYSB";  //响应失败
	 public static final String CO_QUEUE_STATUS_DDGQ = "DDGQ";  //订单挂起（特殊处理）
	 
	 /** 业务编码 */
	 public static final String SERVICE_CODE_CO_GUIJI_NEW = "CO_GUIJI_NEW";  //新订单系统订单归集
	 public static final String SERVICE_CODE_CO_GUIJI = "CO_GUIJI";  //订单归集
	 public static final String SERVICE_CODE_CO_DINGDAN = "CO_DINGDAN";  //订单同步
	 public static final String SERVICE_CODE_CO_HUOPIN = "CO_HUOPIN";  //货品同步
	 public static final String SERVICE_CODE_CO_SHANGPIN = "CO_SHANGPIN";  //商品同步
	 public static final String SERVICE_CODE_CO_HAOMA = "CO_HAOMA";  //号码同步
	 public static final String SERVICE_CODE_CO_SHANGPIN_KUCUN = "CO_SHANGPIN_KUCUN";  //商品库存同步
	 public static final String SERVICE_CODE_CO_HUOPIN_KUCUN = "CO_HUOPIN_KUCUN";  //货品库存同步
	 public static final String SERVICE_CODE_CO_HUODONG = "CO_HUODONG";  //活动同步
	 public static final String SERVICE_CODE_CO_FENLEI = "CO_FENLEI";  //分类同步
	 public static final String SERVICE_CODE_CO_LEIXING = "CO_LEIXING";  //类型同步
	 public static final String SERVICE_CODE_CO_PINPAI = "CO_PINPAI";  //品牌同步
	 public static final String SERVICE_CODE_CO_XINGHAO = "CO_XINGHAO";  //型号同步
	 
	 
	 /** 动作编码 */
	 public static final String ACTION_CODE_100 = "100";  //页面发布时
	 public static final String ACTION_CODE_200 = "200";  //后台写入（如订单归集时）
	 public static final String ACTION_CODE_A = "A";  //新增
	 public static final String ACTION_CODE_M = "M";  //修改
	 public static final String ACTION_CODE_D = "D";  //删除或停用
	 public static final String ACTION_CODE_B = "B";  //发布
	 
	 /** 库存分配回收动作编码 **/
	 public static final String INVENTORY_ACTION_0 = "0";//出库
	 public static final String INVENTORY_ACTION_1 = "1";//入库
	 public static final String INVENTORY_ACTION_2 = "2";//回收
	 
	 /** 虚拟仓库独享、共享编码 **/
	 public static final String ATTR_INLINE_TYPE_DUXIANG = "0";  //独享
	 public static final String ATTR_INLINE_TYPE_GONGXIANG = "1";  //共享
	 
	 /**
	  * 0订购 1配件2赠品  3补记礼品
	  */
	 public static final String ITEM_TYPE_0 = "0";
	 public static final String ITEM_TYPE_1 = "1";
	 public static final String ITEM_TYPE_2 = "2";
	 public static final String ITEM_TYPE_3 = "3";
	 
	//商品货品 发布状态-- 发布状态:0 未发布； 1 已发布，2发布中，3发布失败
	 public static final Integer PUBLISH_0=0;
	 public static final Integer PUBLISH_1=1;
	 public static final Integer PUBLISH_2=2;
	 public static final Integer PUBLISH_3=3;
	 

	 //货品类型编码
	 public static final String GOODS_TYPE_TERMINAL = "10000";//手机
	 public static final String GOODS_TYPE_CONTRACT = "10001";//合约计划
	 public static final String GOODS_TYPE_OFFER = "10002";//套餐
	 
	 public static final String PRODUCT_TYPE_INTERNET = "10003";//上网卡
	 public static final String PRODUCT_TYPE_FIN_CARD = "10004";//成品卡
	 public static final String PRODUCT_TYPE_BAI_CARD = "10005";//白卡
	 public static final String PRODUCT_TYPE_ADJUNCT = "10006";//配件
	 public static final String PRODUCT_TYPE_LIPIN = "10007";//礼品
	 public static final String PRODUCT_TYPE_ZZYW = "10008";//增值业务(附加产品)
	 public static final String PRODUCT_TYPE_KEXUANBAO = "10009"; //可选包
	 public static final String PRODUCT_TYPE_ZDB = "10010"; //转兑包
	 public static final String PRODUCT_TYPE_NUMBER = "10011"; //号码
	 public static final String PRODUCT_TYPE_YWSL = "10015"; //业务受理
	 
	 //商品类型编码
	 public static final String GOODS_TYPE_NUM_CARD = "20000";//号卡
	 public static final String GOODS_TYPE_WIFI_CARD = "20001";//上网卡
	 public static final String GOODS_TYPE_CONTRACT_MACHINE = "20002";//合约机
	 public static final String GOODS_TYPE_PHONE = "20003";//裸机
	 public static final String GOODS_TYPE_FINISH_CARD = "20004";//成品卡
	 public static final String GOODS_TYPE_ADJUNCT = "20005";//配件
	 
	 
	 //商品货品类别编码
	 public static final String GOODS_CAT_SOCIAL_MACHINE  = "690001000";//社会机
	 public static final String GOODS_CAT_CUSTOM_MACHINE  = "690002000";//定制机
	 
	 public static final String GOODS_CAT_MACHINE_GIVE_FEE = "690301000";//购机送费
	 public static final String GOODS_CAT_FEE_GIVE_MACHINE  = "690302000";//存费送机
	 public static final String GOODS_CAT_FEE_GIVE_FEE  = "690303000";//存费送费
	 public static final String GOODS_CAT_HEYUEHUIJI  = "690307000";//合约惠机A
	 public static final String GOODS_CAT_HEYUEHUIJIB  = "690308000";//合约惠机B
	 
	 
	 public static final String GOODS_CAT_3G_AFTER_FEE  = "'690101000','690102000'";//3G后付费
	 public static final String GOODS_CAT_3G_PRE_FEE  = "690104000";//3G预付费
	 public static final String GOODS_CAT_4G_AFTER_FEE  = "690108000";//4G后付费
	 public static final String GOODS_CAT_2G_3G_FUSE_  = "690105000";//2G/3G融合
	 
	 public static final String GOODS_CAT_ID_1 = "690101000";//3G后付费套餐
	 public static final String GOODS_CAT_ID_2 = "690108000";//4G后付费套餐
	 public static final String GOODS_CAT_ID_3 = "690102000";//iphone套餐
	 public static final String GOODS_CAT_ID_4G_ZYZH = "69010206";  //4G自由组合套餐
	 public static final String GOODS_CAT_ID_DZJQGCFSJ = "69030101";  //商品分类：定制机全国存费送机
	 public static final String GOODS_CAT_ID_DZJQGGJSF = "69030102";  //商品分类：定制机全国购机送费
	 public static final String GOODS_CAT_ID_DZJQGHYHJ = "690301041";  //商品分类：定制机全国合约惠机A
	 public static final String GOODS_CAT_ID_DZJQGHYHJB = "690301042";  //商品分类：定制机全国合约惠机B
	 
	 public static final String GOODS_CAT_ID_SHJQGCFSJ = "69030201";  //商品分类：社会机全国存费送机
	 public static final String GOODS_CAT_ID_SHJQGGJSF = "69030202";  //商品分类：社会机全国购机送费
	 public static final String GOODS_CAT_ID_SHJQGHYHJ = "69030204";  //商品分类：社会机全国合约惠机A
	 public static final String GOODS_CAT_ID_SHJQGHYHJB = "69030205";  //商品分类：社会机全国合约惠机B
	 

	 
	 /***************** 佣金计算常量 *************************/
	 public static final String SPREAD_MOD_AGENCY = "0";	//推荐人模式 分级代办
	 public static final String SPREAD_MOD_SHARE = "1";		//推荐人模式 分享
	 public static final String SPREAD_MOD_GRADE = "2";		//推荐人模式 分级
	 
	 public static final String COMMISSION_AGENCY = "0";		//分级代办模式  只有分级代办的关系可抽取
	 public static final String COMMISSION_DISPOSABLE = "1";	//一次性抽取模式  只有分级的关系可抽取
	 public static final String COMMISSION_REWARD = "2";		//在网奖励模式  只有分级的关系可抽取
	 public static final String COMMISSION_DIVIDED = "3";		//话费分成模式  只有分级的关系可抽取
	 public static final String COMMISSION_SHARE = "4";			//分享模式 只有分享的关系可抽取
	 /***************** 佣金计算常量 *************************/
	 
	 /*************** 广东联通常量 ***************************/
	 public static final String ECS_SOURCE_FROM = "ECS";
	 public static final String ECS_QUERY_TYPE_GOOD = "goods";
	 public static final String ECS_QUERY_TYPE_PRODUCT = "product";
	 public static final String ECS_QUERY_TYPE_MODEL = "model";
	 /*************** 广东联通常量 ***************************/
	 
	 
	 /*************** 江苏移动常量 ***************************/
	 public static final String SOURCE_FROM_JS = "JS";//江苏移动的来源标识
	 /*************** 江苏移动常量 ***************************/
	 /*************** 活动类型 ***************************/
	 public static final String DC_PUBLIC_STYPE_PMT_TYPE = "1443";  //活动类型静态数据编码
	 //public static final String DC_PUBLIC_STYPE_PMT_TYPE = "1443";  //活动类型静态数据编码
	 public static final String PMT_TYPE_DISCOUNT = "001";  //打折
	 public static final String PMT_TYPE_INTEGRAL_DOUBLE = "002";  //积分翻倍
	 public static final String PMT_TYPE_MANZENG = "003";  //满赠
	 public static final String PMT_TYPE_MANJIAN = "004";  //满减
	 public static final String PMT_TYPE_FREIGHT_FREE = "005";  //免运费
	 public static final String PMT_TYPE_ZHIJIANG = "006";  //直降
	 public static final String PMT_TYPE_PRE_SALE = "005";  //预售
	 public static final String PMT_TYPE_GROUP_BUY = "008";  //团购
	 public static final String PMT_TYPE_LIMIT_BUY = "009";  //秒杀
	 public static final String PMT_TYPE_SALES = "010";  //促销
	 public static final String PMT_TYPE_OVERPRICE = "011"; //溢价
	 public static final String PMT_TYPE_REGULARSALES = "012"; //常规促销
	 public static final String PMT_TYPE_MONTHSALES = "013"; //月度主促
	 public static final String PMT_TYPE_YEARSALES = "014";	 //年度大促
			 
	 public static final String PMT_TYPE_GIFT = "200";  //广东联通，赠品规则类型 200
	 
	 /*************** 活动规则种类 ***************************/
	 public static final String PMT_TYPES_ID_ACTIVITY = "activity";  //活动
	 public static final String PMT_TYPES_ID_GIFT = "gift";  //赠品

	 
	 /*************** 号码、活动发布状态 ***************************/
	 public static final Integer PUBLISH_STATE_0 = 0;  //未发布
	 public static final Integer PUBLISH_STATE_1 = 1;  //已发布
	 public static final Integer PUBLISH_STATE_2 = 2;  //发布中
	 public static final Integer PUBLISH_STATE_3 = 3;  //发布可修改
	 
	 /*************** 号码是否为靓号 ***************************/
	 public static final String IS_LUCKY_NUMBER_0="0";//普通号码
	 public static final String IS_LUCKY_NUMBER_1="1";//靓号
	 
	 /*************** 淘宝商城code_id ***************************/
	 public static final String TAOBAO_CODE_ID="10001";//淘宝
	 public static final String XINGSHANGCHENG_CODE_ID="10008";//新商城
	 
	 /********************** 号码状态  **************************/
	 public static final String NUMBER_STATUS_0 = "000";//未使用
	 public static final String NUMBER_STATUS_1 = "111";//已使用
	 public static final String NUMBER_STATUS_A = "001";//已预占
	 public static final String NUMBER_STATUS_X = "00X";//已作废

	 
	 /***************支付日志更新类型(不同类型、调不同更新方法，后续有其他再自己新增)***************************/
	 public static final String PAY_LOG_TYPE_ALL = "1";					//对应 paySucc(String transaction_id)
	 public static final String PAY_LOG_TYPE_PRIMARY_KEY = "2";			//对应 paySucc(PaymentLog paymentLog) 
	 public static final String PAY_LOG_TYPE_PRIMARY_KEY_TYPE = "3";	//对应 paySucc(String transaction_id, String type_code) 
	 /***************支付日志更新类型(不同类型、调不同更新方法 ***************************/
	 
	 /*********************************************************************/
	 //验证码是否校验标识，如果没配置有值，则在登录时做验证码校验；如果配置有值，则不做校验。主要是压力测试时跳过验证码、验证码出问题时屏蔽掉验证码校验这一关---add by Musoon
	 public static final String VALID_CODE_FLAG = "VALID_CODE_FLAG";
	 /*********************************************************************/
	 
	 
	 public static final String COLUMN_CONTENT_STATE_0 = "0";	//待审核
	 public static final String COLUMN_CONTENT_STATE_1 = "1";	//审核通过
	 public static final String COLUMN_CONTENT_STATE_2 = "2";	//审核不通过
	 
	 public static final String MODUAL_STATE_0 = "0";	//待审核
	 public static final String MODUAL_STATE_1 = "1";	//审核通过
	 public static final String MODUAL_STATE_2 = "2";	//审核不通过
	 
	 public static final String CONTENT_TYPE_GOODS = "goods";
	 public static final String CONTENT_TYPE_ADV_0 = "adv_0";
	 public static final String CONTENT_TYPE_ADV_1 = "adv_1";
	 public static final String CONTENT_TYPE_NOTICE = "notice";
	 
	 //大字段存储使用状态
	 public final static Integer DSTORE_DISABLED_0=0;//正常列表中
	 public final static Integer DSTORE_DISABLED_1=1;//回收站列表中
	 
	 //大字段存储状态
	 public static final String DSTORE_STATE_00A = "00A"; 	//正常
	 public static final String DSTORE_STATE_00S = "00S";	//失效
	 
	 //商品批量导入活动类型编码值
	 public static final String ATV_CODE_VALUE_3 = "3"; //号卡
	 public static final String ATV_CODE_VALUE_4 = "4"; //存费送机
	 public static final String ATV_CODE_VALUE_5 = "5"; //购机送费
	 public static final String ATV_CODE_VALUE_6 = "6"; //合约惠机A
	 public static final String ATV_CODE_VALUE_7 = "7"; //合约惠机B
	 
	 public static final String MARKET_ENABLE_0 = "0";
	 public static final String MARKET_ENABLE_1 = "1";
	 
	 //可选包可选元素编码
	 public static final String PACKAGE_ELEMENT_CODE = "package_element_code";
	 
	 //短信发送来源号码
	 public static final String SMS_SEND_NUM="SMS_SEND_NUM";
	 
	 
	 //上传管理中间表数据状态
	 public static final String MID_DATA_STATUS_0 = "0";  //未处理
	 public static final String MID_DATA_STATUS_1 = "1";  //处理成功
	 public static final String MID_DATA_STATUS_2 = "2";  //处理失败
	 public static final String MID_DATA_STATUS_3 = "3";  //处理中
	 
	 //生效方式
	 public static final String ACTIVE_SORT_ALLM = "ALLM";  //当月生效
	 public static final String ACTIVE_SORT_HALF = "HALF";  //半月生效
	 public static final String ACTIVE_SORT_COMM = "COMM";  //次月生效
	 
	 public static final String FIRST_FEE_TYPE_ALLM = "ALLM";  //全月套餐
	 public static final String FIRST_FEE_TYPE_HALF = "HALF";  //套餐减半
	 public static final String FIRST_FEE_TYPE_OTHER = "OTHER";  //套餐包外资费
	 
	 //外部平台状态/订单外部状态
	 public static final String EXT_ORDER_STATUS_00 = "00";   //未知状态
	 public static final String EXT_ORDER_STATUS_01 = "01";   //等待买家付款
	 public static final String EXT_ORDER_STATUS_02 = "02";   //等待商家发货
	 public static final String EXT_ORDER_STATUS_03 = "03";   //商家已发货
	 public static final String EXT_ORDER_STATUS_04 = "04";   //交易成功
	 public static final String EXT_ORDER_STATUS_05 = "05";   //订单取消
	 public static final String EXT_ORDER_STATUS_06 = "06";   //系统暂停订单
	 public static final String EXT_ORDER_STATUS_07 = "07";   //系统打款中
	 public static final String EXT_ORDER_STATUS_08 = "08";   //退款处理中
	 public static final String EXT_ORDER_STATUS_09 = "09";   //交易关闭
	 public static final String EXT_ORDER_STATUS_10 = "10";   //回访完成
	 
	 public static final String LLKJ_WSSMALL_IS_LOGIN_AGENT_YES="yes";//是否可以登录代理商 yes 是 no 不可以
	 public static final String LLKJ_WSSMALL_IS_LOGIN_AGENT_NO="no";
     public static final String LLKJ_AGENT_ROLEID="LLKJ_AGENT_ROLEID";//默认代理商的权限

     public static final String GOODS_CACHE_REFRESH_ALL = "all";
     public static final String GOODS_CACHE_REFRESH_NEW = "new";
     public static final String GOODS_CACHE_REFRESH_CFG = "cfg";
     
     //使用GoodsImageUploadServlet上传图片时临时文件存放目录配置常量【常量配于es_config_info表】
     public static final String GOODS_IMAGE_TEMP_PATH = "GOODS_IMAGE_TEMP_PATH";
     
     public static final String ZTE_CESHI ="ZTE1.0收单测试勿动";
 	public static final String ZTE_CESHI_XJ ="ZTE2.0收单测试勿动";
 	public static final String ZTE_GRAY ="ZTE1.0收单灰度勿动";
 	public static final String ZTE_GRAY_XJ ="ZTE2.0收单灰度勿动";
 	
 	public static final String ZTE_CESH_ENV_TYPE_SERVER ="ecsord_ceshi";
 	public static final String ZTE_CESH_ENV_TYPE_SERVER_XJ ="ecsord2.0_ceshi";
 	public static final String ZTE_ENV_TYPE_SERVER ="ecsord_server";
 	public static final String ZTE_ENV_TYPE_SERVER_XJ ="ecsord_server2.0";
 	public static final String ZTE_ENV_TYPE_STD = "ecsord_std";
 	public static final String ZTE_ENV_BUSI_VERSION = "ecsord1.0";
 	public static final String ZTE_ENV_BUSI_VERSION_XJ = "ecsord2.0";
 	
 	public static final String ECSORD_STD="ecsord_std";
 	public static final String ECSORD_CTN="ecsord_ctn";
 	public static final String ECSORD_ECC="ecsord_ecc";
 	public static final String ECSORD_EXP="ecsord_exp";
 	
 	 public static final String ERROR_FAIL ="-1";
     //队列表报文持久化类型
     public static final String REQ_MSG_PERSISTENT_TYPE = "REQ_MSG_PERSISTENT_TYPE";
     //队列表报文持久化类型 -数据库
     public static final String REQ_MSG_PERSISTENT_TYPE_DB = "REQ_MSG_PERSISTENT_TYPE_DB";
     //队列表报文持久化类型 -elasticsearch
     public static final String REQ_MSG_PERSISTENT_TYPE_ES = "REQ_MSG_PERSISTENT_TYPE_ES";
     
     public static final int REDIS_EXPIRETIME = 999999999;//redis超时时间
     public static final String DESENCRYPT_KEY="abc123.*abc123.*abc123.*abc123.*";
}



