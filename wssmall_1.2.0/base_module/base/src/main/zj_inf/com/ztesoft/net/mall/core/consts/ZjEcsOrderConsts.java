package com.ztesoft.net.mall.core.consts;

import java.util.HashMap;



/**
 *常量定义类
 * 
 * @author wui
 * 状态 0申请、1正常、2冻结、3注销,-1审核部通过
 */
public class ZjEcsOrderConsts { 
	 
	 //商品类型编码
	 public static final String GOODS_TYPE_CONTRACT_MACHINE = "20002";//合约机
	 public static final String GOODS_TYPE_PHONE = "20003";//裸机
	 public static final String GOODS_TYPE_HAOKA = "170801435262003016"; //号卡省内
	 public static final String GOODS_TYPE_JIHUO = "170601900021724272";//非订单中心
	 //接口返回成功标识
	 public static final String BUSI_DEAL_RESULT_0000 = "0000";				//业务处理逻辑成功结果s	

	 //bss业务办理状态
	 public static final String BSS_STATUS_NOT_NEED = "-1";					//无需业务办理
	 public static final String BSS_STATUS_0 = "0";						//未办理
	 public static final String BSS_STATUS_1 = "1";						//已办理未竣工
	 public static final String BSS_STATUS_2 = "2";                     //已办理已竣工
	 public static final String BSS_STATUS_3 = "3";                     //竣工失败
	 
	 
	 //bss业务办理状态				
	 public static final String BSS_STATUS_2_DESC = "BSS已办理已竣工";
	 //核心通用方案id
	 public static final String FA_HUO_PLAN = "107";//发货方案
	 
	 
	 //通用方案id
	 public static final String ORDER_DATA_ARCHIVE_PLAN = "108";		//资料归档
	 public static final String ORDER_ARCHIVE_RULE = "201411251155000324";        //订单归档规则id
	 public static final String ORDER_DATA_ARCHIVE_RULE_AOP = "161651746350001537";        //归档验证(AOP)
	 public static final String ORDER_ARCHIVE_RULE_BSSKL = "160751023120000525";        //订单归档规则id(BSSKL)
	 public static final String ZJ_BSS_OLD_USER_BUSINESS = "162281144490000919";//BSS老用户业务办理方案ID
		
	 public static final String ORDER_WRITE_CARD_NEXT_RULE_06 = "162502044500001657";//人工集中手工生产模式写卡下一步规则

	 public static final String YJH_PLAN_ID = "101"; //预拣货方案ID
	 
	 //规则ID定义	 
	 public static final String DT_ORDER_BACK_RULE_ID = "161442221152000498";//DT卡订单归档
	 /**
	  * 环节常量
	  */
	 public static final String DIC_ORDER_NODE = "DIC_ORDER_NODE";
	 public static final String shipping_type_cache = "shipping_type";
	 public static final String SOURCE_FROM = "source_from";
	 public static final String ARCHIVE_TYPE = "archive_type";
	
	 /************** 权限类型常量 ******************/
	 public static final String AUTH_MENU = "menu";	//菜单权限
	 public static final String AUTH_APP = "app";	//app权限
	 public static final String AUTH_BTN = "btn";	//按钮权限
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
		
	 /** 消息队列状态*/
	 public static final String CO_QUEUE_STATUS_WFS = "WFS";  //未发送
	 public static final String CO_QUEUE_STATUS_FSZ = "FSZ";  //发送中
	 public static final String CO_QUEUE_STATUS_XYCG = "XYCG";  //响应成功
	 public static final String CO_QUEUE_STATUS_XYSB = "XYSB";  //响应失败
	 
	 /** 业务编码 */
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
	 //环节编码
	 public static final String DIC_ORDER_NODE_O = "O";           //订单标准化
	 public static final String DIC_ORDER_NODE_K = "K";           //售后回访
	 public static final String DIC_ORDER_NODE_L = "L";           //订单归档
	 public static final String DIC_ORDER_NODE_M = "M";           //退款确认
	 public static final String DIC_ORDER_NODE_A = "A";           //订单预处理
	 public static final String DIC_ORDER_NODE_B = "B";           //客户回访，订单审核
	 public static final String DIC_ORDER_NODE_C = "C";           //预拣货，配货
	 public static final String DIC_ORDER_NODE_D = "D";           //开户
	 public static final String DIC_ORDER_NODE_E = "E";           //拣货出库
	 public static final String DIC_ORDER_NODE_F = "F";           //质检稽核
	 public static final String DIC_ORDER_NODE_G = "G";           //物品包装
	 public static final String DIC_ORDER_NODE_H = "H";           //发货
	 public static final String DIC_ORDER_NODE_I = "I";           //收货确认
	 public static final String DIC_ORDER_NODE_J = "J";           //回单，资料归档
	 public static final String DIC_ORDER_NODE_X = "X";           //写卡
	 public static final String DIC_ORDER_NODE_Y = "Y";           //业务办理
	 public static final String DIC_ORDER_NODE_T = "T";           //退单处理
	 public static final String DIC_ORDER_NODE_R = "R";           //线上退款
	 public static final String DIC_ORDER_NODE_S = "S";           //线下退款

	 public static final String DIC_ORDER_NODE_O_DESC = "订单标准化";           
	 public static final String DIC_ORDER_NODE_K_DESC = "售后回访";      
	 public static final String DIC_ORDER_NODE_L_DESC = "订单归档";          
	 public static final String DIC_ORDER_NODE_M_DESC = "退款确认";           
	 public static final String DIC_ORDER_NODE_A_DESC = "订单预处理";           
	 public static final String DIC_ORDER_NODE_B_DESC = "订单审核";           
	 public static final String DIC_ORDER_NODE_C_DESC = "配货";           
	 public static final String DIC_ORDER_NODE_D_DESC = "开户";           
	 public static final String DIC_ORDER_NODE_E_DESC = "拣货出库";           
	 public static final String DIC_ORDER_NODE_F_DESC = "质检稽核";           
	 public static final String DIC_ORDER_NODE_G_DESC = "物品包装";           
	 public static final String DIC_ORDER_NODE_H_DESC = "发货";           
	 public static final String DIC_ORDER_NODE_I_DESC = "收货确认";           
	 public static final String DIC_ORDER_NODE_J_DESC = "资料归档";           
	 public static final String DIC_ORDER_NODE_X_DESC = "写卡";           
	 public static final String DIC_ORDER_NODE_Y_DESC = "业务办理";           
	 public static final String DIC_ORDER_NODE_T_DESC = "退单处理";            
	 public static final String DIC_ORDER_NODE_R_DESC = "线上退款";          
	 public static final String DIC_ORDER_NODE_S_DESC = "线下退款";         
	 
	 public static final HashMap DIC_ORDER_NODE_DESC_MAP = new HashMap();
	
	 static {
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_O,DIC_ORDER_NODE_O_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_K,DIC_ORDER_NODE_K_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_L,DIC_ORDER_NODE_L_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_M,DIC_ORDER_NODE_M_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_A,DIC_ORDER_NODE_A_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_B,DIC_ORDER_NODE_B_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_C,DIC_ORDER_NODE_C_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_D,DIC_ORDER_NODE_D_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_E,DIC_ORDER_NODE_E_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_F,DIC_ORDER_NODE_F_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_G,DIC_ORDER_NODE_G_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_H,DIC_ORDER_NODE_H_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_I,DIC_ORDER_NODE_I_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_J,DIC_ORDER_NODE_J_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_X,DIC_ORDER_NODE_X_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_Y,DIC_ORDER_NODE_Y_DESC);
		 DIC_ORDER_NODE_DESC_MAP.put(DIC_ORDER_NODE_T,DIC_ORDER_NODE_T_DESC);
	 }	 
	 
	 //订单状态
	 public static final int DIC_ORDER_STATUS_1 = 1;          //订单生成
	 public static final int DIC_ORDER_STATUS_2 = 2;          //待审核
	 public static final int DIC_ORDER_STATUS_3 = 3;          //待配货
	 public static final int DIC_ORDER_STATUS_4 = 4;          //待开户
	 public static final int DIC_ORDER_STATUS_5 = 5;          //待写卡
	 public static final int DIC_ORDER_STATUS_41 = 42;          //待副卡开户
	 public static final int DIC_ORDER_STATUS_51 = 52;          //待副卡写卡
	 public static final int DIC_ORDER_STATUS_55 = 55;        //待业务办理
	 public static final int DIC_ORDER_STATUS_6 = 6;          //待质检稽核
	 public static final int DIC_ORDER_STATUS_7 = 7;          //待发货
	 public static final int DIC_ORDER_STATUS_72 = 72;          //待取货
	 public static final int DIC_ORDER_STATUS_73 = 73;          //已发货
	 public static final int DIC_ORDER_STATUS_8 = 8;          //待归档
	 public static final int DIC_ORDER_STATUS_82 = 82;          //部分归档
	 public static final int DIC_ORDER_STATUS_83 = 83;          //全部归档
	 public static final int DIC_ORDER_STATUS_91 = 91;          //	待资料归档
	 public static final int DIC_ORDER_STATUS_92 = 92;          //	资料归档
	 public static final int DIC_ORDER_STATUS_10 = 10;          //	交易成功
	 public static final int DIC_ORDER_STATUS_11 = 11;          //	外呼申请
	 public static final int DIC_ORDER_STATUS_12 = 12;          //	退单中
	 public static final int DIC_ORDER_STATUS_13 = 13;          //	交易关闭
	 public static final int DIC_ORDER_STATUS_15 = 15;          //待库管
	 //是否标识
	 public static final String IS_DEFAULT_VALUE = "1";//是
	 public static final String NO_DEFAULT_VALUE = "0";//否
	 
	 //锁定状态
	 public static final String LOCK_STATUS = "1";
	 public static final String UNLOCK_STATUS = "0";   
		
	public static final int ORDER_LOCK_NAMESPACE = 6000;
	public static final int ORDER_LOCK_NAMESPACE_TIMEOUT = 10*24*60*60;

	
	//营业款/实收款稽核状态
	public static final String AUDIT_BUSI_MONEY_STATUS_0 = "0";//未稽核
	public static final String AUDIT_BUSI_MONEY_STATUS_1 = "1";//待稽核
	public static final String AUDIT_BUSI_MONEY_STATUS_2 = "2";//待复核
	public static final String AUDIT_BUSI_MONEY_STATUS_3 = "3";//系统稽核通过
	
	//营业款类型
	public static final String AUDIT_BUSI_DATA_FROM_BSS = "BSS";//bss
	public static final String AUDIT_BUSI_DATA_FROM_CBSS = "CBSS";//cbss
	
	public static final String BATCH_PREHANDLE_EXP = "BATCH_PREHANDLE_EXP";//批量预处理异常处理类型
	
	//总商接口插入定时任务表需要当前时间+xx 秒，避免同时插入时间相同
	
	public static final int ZS_ES_CO_QUEUE_TIME_STATE_SYN= 2;//发货完成
	public static final int ZS_ES_CO_QUEUE_TIME_CHANGE= 4;//信息变更
	public static final int ZS_ES_CO_QUEUE_TIME_NOTIFY_SHIP= 6;//发货完成

	//是否老用户
	public static final String IS_OLD_0 = "0";//新用户
	public static final String IS_OLD_1 = "1";//老用户
	 
	//开户通道
	public static final String ACCOUNT_OPEN_CHANNEL_ZB = "0"; //总部开户
	public static final String ACCOUNT_OPEN_CHANNEL_AOP = "1"; //AOP开户
	public static final String ACCOUNT_OPEN_CHANNEL_BSS = "2"; //BSS开户

	/**
	 * 外系统回传支付结果(码上购支付状态)
	 * */
	/** 外系统回传支付结果(码上购支付状态)    0  :   支付成功*/
	public static final String MA_PAY_SUCCESS = "0";
	/** 外系统回传支付结果(码上购支付状态)   -1  :  支付失败*/
	public static final String MA_PAY_FAIL = "-1"; 

	//码上购协议照片动作
	public static final String AGRT_IMG_ACTION_A = "A"; //新增
	public static final String AGRT_IMG_ACTION_M = "M"; //更新
	
	/** 激活状态 */
	public static final String ACTIVE_FLAG_0 = "0";//0:未激活
	public static final String ACTIVE_FLAG_1 = "1";//1:线下激活失败
	public static final String ACTIVE_FLAG_2 = "2";//2:线下激活成功
	public static final String ACTIVE_FLAG_3 = "3";//3:线上激活成功
	public static final String ACTIVE_FLAG_4 = "4";//4:人工认证中
	public static final String ACTIVE_FLAG_5 = "5";//5:人工认证失败
	//生产模式
	public static final String ORDER_MODEL_01 = "01";					//自动化集中生产模式
	public static final String ORDER_MODEL_02 = "02";					//人工集中生产模式
	public static final String ORDER_MODEL_03 = "03";					//现场生产模式
	public static final String ORDER_MODEL_04 = "04";					//物流生产模式
	public static final String ORDER_MODEL_05 = "05";					//独立生产模式
	public static final String ORDER_MODEL_06 = "06";					//人工集中手工生产模式
	public static final String ORDER_MODEL_07 = "07";					//爬虫手工写卡生产模式
	public static final String ORDER_MODEL_08 = "08";					//爬虫自动写卡生产模式
	public static final String ORDER_MODEL_00 = "00";					//非物流模式，代表01,02,03,05
		 
	
	public static final String GOODS_CAT_ID_DDWK = "69010214";//滴滴王卡
	public static final String GOODS_CAT_ID_TXWK = "69010213";//腾讯王卡
	public static final String GOODS_CAT_ID_MYBK = "69010215";//蚂蚁宝卡
	public static final String GOODS_CAT_ID_BDSK = "69010216";//百度神卡
	/************工号池*****************/
	/**工号池状态  0：有效 */
	public static final String WORKER_POOL_STATE_0 = "0";
	/**工号池状态  1：失效*/
	public static final String WORKER_POOL_STATE_1 = "1";
	//队列开户写卡状态
	public static final String QUEUE_ORDER_OPEN_0 = "0";//未开户
	public static final String QUEUE_ORDER_OPEN_1 = "1";//开户中
	public static final String QUEUE_ORDER_OPEN_2 = "2";//开户完成
	public static final String QUEUE_ORDER_OPEN_3 = "3";//开户异常
	public static final String QUEUE_ORDER_WRITE_0 = "0";//未写卡
	public static final String QUEUE_ORDER_WRITE_1 = "1";//写卡中
	public static final String QUEUE_ORDER_WRITE_2 = "2";//写卡完成
		
	//开户方式
	public static final String OPEN_ACC_ONLINE = "0";//线上开户
	public static final String OPEN_ACC_OFFLINE = "1";//线下开户
	
	//通用方案id
		 public static final String ORDER_TACHE_PAGE_MATCH_DIR = "201410279742000006";	//环节界面匹配目录id
		 public static final String ORDER_MODEL_MATCH_DIR = "201410146717000002";	//模式匹配目录id
		 public static final String ORDER_ORDER_PROC_MATCH_DIR = "201410141607000003";	//订单处理目录id
		 public static final String ORDER_COMMON_RULE_MATCH_DIR = "201411126102000022";	//通用规则目录id
		 public static final String ORDER_ORDER_MANAGER_MATCH_DIR = "201411048347000021";	//订单管理目录id
		 public static final String ORDER_TACHE_MATCH_DIR = "M000";	                //环节目录id
		 public static final String ORDER_RULE_MANAGER_MATCH_DIR = "0";	                //规则管理目录id
		 public static final String ORDER_FLOW_MATCH_DIR = "201410182036000005";	//流程匹配目录id
		 
		 public static final String ACTION_URL_MATCH_PLAN = "201410275030000092";	//环节页面匹配方案id
		 public static final String WORK_FLOW_MATCH_PLAN = "201410185186000091";	//工作流匹配方案id
		 public static final String ORDER_COLLECT_PLAN = "201410091613000142";		//订单归集方案id
		 public static final String ORDER_AUTO_START_PRODUCTION_PLAN = "160721647280000492";		//订单自动启动生产方案id
		 public static final String ORDER_RETURNED_PLAN = "201411046357000122";		//退单申请方案ID	 
		 
		 public static final String ORDER_RETURNED_PLAN_AUTO = "201508144780000133";//退单申请方案ID(AOP+现场交付自动)
		 public static final String ORDER_CANCELRETURNED_PLAN="201411079196000123"; //取消退单方案ID
		 public static final String ORDER_CANCELRETURNED_PLAN_PC="170201541310000067"; //取消退单方案ID[爬虫]
		 public static final String ORDER_PRE_DEAL_PLAN = "100";					//订单预处理方案id
		 public static final String MARKED_EXCEPTION_PLAN = "201411215178000147";	//标记异常方案id
		 public static final String RESTORE_EXCEPTION_PLAN = "201411214349000148";	//恢复异常方案id
		 public static final String ORDER_ARCHIVE_PLAN = "109";		//订单归档方案id
		 public static final String VALIDATE_CARD_PLAN ="201412036823000166";//身份证预校验方案id
		 public static final String TBORDER_RETURNED_PLAN = "201412167961000206";		//同步淘宝退单申请方案ID
		 public static final String WORDER__PRE_DEAL_PLAN = "201501204944000087";		//订单归集方案id
		 public static final String ORDER_ARCHIVE_RULE_AOP = "201506059593000348";        //订单归档规则id(AOP)
		 public static final String ORDER_STATUS_SYN_OLD_SYS_PLAN = "201502265797000104";//订单环节跳转，标记异常，恢复异常通知老系统
		 public static final String CHANGE_OFFLINE_OPENACCOUNT_PLAN = "201503195583000105";//订单转手工开户方案ID
		 public static final String GET_CERT_PHOTO_FROM_ZB =  "201504103627000106";//获取总部证件图片方案ID
		 public static final String GET_CERT_PHOTO_FROM_TB =  "201504221297000121";//获取淘宝证件图片方案ID
		 public static final String MATCHING_IS_AOP = "201506088076000125";//匹配是否走AOP开户方案ID
		 public static final String NUMBER_PRE_OCCUPANCY_AOP = "201506089991000126";//号码资源预占方案ID
		 public static final String NUMBER_OCCUPANCY_AOP = "201506081631000128";//号码资源预定方案ID
		 public static final String NUMBER_OCCUPANCY_BSS = "201509019003000135";//bss号码资源预定方案ID
		 public static final String ORDER_PRE_VALIDATE = "201412102057000186";//预校验[通用规则]
		 public static final String ORDER_PRE_VALIDATE_AOP = "201506045157000124";//订单预校验[AOP]
		 public static final String ORDER_PRE_VALIDATE_BSS = "160721050380000021";//订单预校验[BSS]
		 public static final String NUMBER_RELEASE_AOP = "201506095670000129";//号码资源释放[AOP]
		 public static final String NUMBER_RELEASE_BSS = "201509011042000136";//bss号码资源释放
		 public static final String TERMI_RELEASE_AOP = "201506095304000130";//终端资源释放[AOP]
		 public static final String NUMBER_CHANGE_AOP = "201507229627000132";//号码资源变更[AOP]
		 public static final String NUMBER_CHANGE_BSS = "201509011590000137";//bss号码资源变更
		 public static final String TERMI_PRE_OCCUPANCY_AOP = "150551723260000209";//终端预占方案ID
		 public static final String ORDER_REVERSE_SALE_AOP = "150841047530000096";//订单返销方案ID
		 public static final String MATCH_PRODUCE_CENTER = "160182042480000016";//匹配订单生产中心方案ID
		 public static final String NUMBER_PRE_OCCUPANCY_AOP_FUKA = "160691007240000026";//副卡号码资源预占方案ID
		 public static final String NUMBER_OCCUPANCY_AOP_FUKA = "160691512100000083";//副卡号码资源预定方案ID
		 public static final String NUMBER_OCCUPANCY_BSS_FUKA = "160691512400000095";//bss副卡号码资源预占方案ID
		 public static final String NUMBER_RELEASE_AOP_FUKA = "160691512200000089";//副卡号码释放方案ID
		 public static final String NUMBER_RELEASE_BSS_FUKA = "160691512480000101";//bss号码资源释放方案ID
		 public static final String OLD_USER_BUSINESS = "160201448400000029";//老用户业务办理通用方案ID
		 public static final String BSS_OLD_USER_BUSINESS = "162281144490000919";//BSS老用户业务办理方案ID
		 public static final String PC_BATCH_WRITE_CARD_QUEUE = "170122115230000097";//PC批量写卡匹配队列方案ID
		 public static final String OFFLINE_OPEN_ACCOUNT_PLAN = "162881212530000038";//线下开户通道校验方案ID
		 public static final String ORDER_AUTO_REFUND_PLAN = "170061658410000016";//归集退单订单处理方案ID
		 public static final String MATCH_PHONE_NUM_PARA_ESS = "160911024270000029";//计算ESS预占号码段
		 public static final String MATCH_PHONE_NUM_PARA_BSS = "160911024180000023";//计算BSS预占号码段
		 public static final String SMS_3NET_SEND = "160041034570000157"; // 发送短信方案ID
		 public static final String SMS_3NET_SEND_NEW = "160691755450002671"; // 发送短信（新）方案
		 public static final String SMS_3NET_SEND_NEW_RULE = "160691756280002691"; // 发送短信（新）规则
		 
		 public static final String CUST_ORDER_BIND_RULE = "162641703380000032"; // 客户资料与订单绑定方案ID
		 //模式匹配方案
		 public static final String ORDER_MODEL_MATCH_PLAN_01 = "201410141302000081";		//人工集中生产模式
		 public static final String ORDER_MODEL_MATCH_PLAN_02 = "201410142122000083";		//现场生产模式
		 public static final String ORDER_MODEL_MATCH_PLAN_03 = "201410143595000085";		//物流生产模式
		 public static final String ORDER_MODEL_MATCH_PLAN_04 = "201410148457000082";		//自动化生产模式
		 public static final String ORDER_MODEL_MATCH_PLAN_05 = "201410146764000084";		//独立生产模式
			
		 public static final String OPEN_ACCOUNT_PLAN_ID = "102";//开户方案ID
		 public static final String RULE_PLAN_ID_103 = "103";//写卡方案ID
		 public static final String RULE_PLAN_ID_108 = "108";//回单方案ID
		 
		 public static final String ORDER_STATUS_SYN_OLD_SYS_RULE = "201502269311000110";
		 public static final String ORDER_PRE_INFO_TO_ZB_RULE = "201412316905000022";//信息同步总部通用规则
		 public static final String ORDER_PRE_COMMON_RULE = "201412105383000351";//预检验通用规则
		 public static final String ORDER_PRE_VALIDATE_RULE= "201410255061000160";//预校验规则
		 public static final String ORDER_CANCELRETURNED_RULE="201411078628000218";//取消退单规则	 
		 public static final String ORDER_RETURN_APPLY_RULE = "201411047255000206";//接收退单申请规则
		 public static final String ORDER_RETURN_APPLY_RULE_AUTO = "201508148029000410";//接收退单申请规则(AOP+现场交付自动)
		 public static final String ORDER_RETURN_CONFIRM_RULE = "201411042859000210";//退单确认规则
		 public static final String ORDER_RETURN_CONFIRM_RULE_AUTO = "201508146946000415";//退单确认规则(AOP+现场交付自动)
		 public static final String ORDER_RETURN_ESSREBACK_RULE = "201411044105000212";//ess返销规则
		 public static final String ORDER_RETURN_BSSREBACK_RULE = "201411045465000211";//bss返销规则
		 public static final String ORDER_RETURN_REFUND_RULE = "201411049826000213";// 人工审核下退款规则
		 public static final String ORDER_RETURN_REFUND_RULE_AUTO = "160691537010000547";//自动审核下退款规则
		 //public static final String ORDER_WRITE_CARD_RULE = "201410254337000105";//写卡下一步规则
		 public static final String ORDER_WRITE_CARD_RULE = "201410259425000103";//写卡下一步规则(总商)
		 public static final String ORDER_WRITE_CARD_RULE_AOP = "201506059703000245";//写卡下一步规则(AOP通道)
		 public static final String ORDER_WRITE_CARD_RULE_BSS = "160721500550000597";//写卡下一步规则(BSS通道)
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_01 = "201410259458000112";//自动化生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_02 = "201410259425000103";//人工集中生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_03 = "201410255904000104";//现场生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_04 = "201410252653000102";//物流生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_05 = "201410254337000105";//独立模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_01_AOP = "201506055854000266";//自动化生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_02_AOP = "201506059703000245";//人工集中生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_03_AOP = "201506055688000250";//现场生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_04_AOP = "201506058899000255";//物流生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_05_AOP = "201506055623000267";//独立模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_06_AOP = "162311159400000350";//人工集中手工模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_01_BSSKL = "160721536580001256";//自动化生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_02_BSSKL = "160721500550000597";//人工集中生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_03_BSSKL = "160721513130000800";//现场生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_04_BSSKL = "160721521450000968";//物流生产模式写卡下一步规则
		 public static final String ORDER_WRITE_CARD_NEXT_RULE_05_BSSKL = "160721547290001339";//独立模式写卡下一步规则
		 public static final String ORDER_DIRECT_ARCHIVE_RULE = "201411241092000302";//直接规则
		 public static final String TBORDER_RETURN_APPLY_RULE = "201412163376000416";//同步淘宝退单申请规则
		 public static final String ACCOUNT_CERT_VALI_RULE = "201412038136000331";//身份证校验规则
		 
		 public static final String REFUND_ONLINE="160751659060002273";//线上退款方案
		 public static final String REFUND_OFFLINE="160751659150002279";//线下退款方案
		 
		 public static final String REFUND_ONLINE_NOTICE="160841432310000032";//线上退款通知本地商城
		 public static final String REFUND_OFFLINE_NOTICE="160841433200000060";//线下退款通知本地商城
		 
		 public static final String YJY_NEXT_RULE_ID = "201501206849000066";//预校验下一步规则ID
		 public static final String YJY_NEXT_RULE_ID_AOP = "201506048069000187";//预校验下一步规则ID
		 public static final String YJY_NEXT_RULE_ID_BSSKL = "160721429290000819";//预校验下一步规则ID
		 
		 public static final String YJH_NEXT_RULE_ID_01 = "201410254030000083";//自动化生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_02 = "201410252568000076";//人工集中生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_03 = "201410258619000079";//现场生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_04 = "201410251068000078";//物流生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_05 = "201410254963000084";//独立生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_01_AOP = "201506048025000203";//自动化生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_02_AOP = "201506043699000195";//人工集中生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_03_AOP = "201506043702000198";//现场生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_04_AOP = "201506042453000206";//物流生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_05_AOP = "201506043772000208";//独立生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_01_BSSKL = "160751157240000564";//自动化生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_02_BSSKL = "160751122410000329";//人工集中生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_03_BSSKL = "160751140350000247";//现场生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_04_BSSKL = "160751208260000789";//物流生产模式-预拣货下一步规则ID
		 public static final String YJH_NEXT_RULE_ID_05_BSSKL = "160751215390000951";//独立生产模式-预拣货下一步规则ID

		 public static final String WRITE_CARD_INFO_TO_ZB_02 =  "201411054613000217";//写卡结果通知总部规则ID
		 public static final String WRITE_CARD_INFO_TO_ZB_03 = "201411115543000222";//现场生产模式-写卡结果通知总部
		 public static final String WRITE_CARD_INFO_TO_ZB_04 = "201411122614000230";//物流生产模式-写卡结果通知总部
		 
		 public static final String WRITE_CARD_INFO_TO_ZB_02_AOP =  "201506057839000243";//写卡结果通知总部规则ID
		 public static final String WRITE_CARD_INFO_TO_ZB_03_AOP = "201506054473000248";//现场生产模式-写卡结果通知总部
		 public static final String WRITE_CARD_INFO_TO_ZB_04_AOP = "201506059959000253";//物流生产模式-写卡结果通知总部
		 public static final String WRITE_CARD_INFO_TO_ZB_02_BSSKL =  "160721440070000531";//写卡结果通知总部规则ID
		 public static final String WRITE_CARD_INFO_TO_ZB_03_BSSKL = "160721509480000732";//现场生产模式-写卡结果通知总部
		 public static final String WRITE_CARD_INFO_TO_ZB_04_BSSKL = "160721519090000906";//物流生产模式-写卡结果通知总部
		 
		 public static final String AGENT_DEDUCT_PLAN_ID = "201501095753000083";		 //资金归集扣减方案
		 public static final String AGENT_REFUND_PLAN_ID = "201501097714000084";		 //资金归集返销方案
		 public static final String SYN_ORDER_OLD_SYS = "201502058937000102";			 //同步订单信息到老系统
	//生产模式对应的获取ICCID编码的规则ID
	public static final String ORDER_MODEL_02_ICCID_RULE_ID = "201411124049000226"; //人工集中模式对应的规则ID
	public static final String ORDER_MODEL_03_ICCID_RULE_ID = "201411119130000220"; //现场生产模式对应的规则ID
	public static final String ORDER_MODEL_04_ICCID_RULE_ID = "201411124354000228"; //物流生产模式对应的规则ID

	public static final String ORDER_MODEL_02_ICCID_RULE_ID_AOP = "201506052087000241"; //人工集中模式对应的规则ID
	public static final String ORDER_MODEL_03_ICCID_RULE_ID_AOP = "201506056156000246"; //现场生产模式对应的规则ID
	public static final String ORDER_MODEL_04_ICCID_RULE_ID_AOP = "201506058731000251"; //物流生产模式对应的规则ID
	public static final String ORDER_MODEL_02_ICCID_RULE_ID_BSSKL = "160721438020000493"; //人工集中模式对应的规则ID
	public static final String ORDER_MODEL_03_ICCID_RULE_ID_BSSKL = "160721508160000694"; //现场生产模式对应的规则ID
	public static final String ORDER_MODEL_04_ICCID_RULE_ID_BSSKL = "160721517430000868"; //物流生产模式对应的规则ID
	public static final String ORDER_MODEL_06_ICCID_RULE_ID_BSSKL = "172251641012000634"; //集中写卡生产模式对应的规则ID
	
	
	
	
	
	
	
	/******************************退单状态位开始***********************************/
	 //bss退款状态
	 public static final String BSS_REFUND_STATUS_0 = "0";				//未退款
//	 public static final String BSS_REFUND_STATUS_1 = "1"; 				//已退款
	 public static final String BSS_REFUND_STATUS_OFFLINE_SUCC = "1"; 	//线下退款成功（以前的退款都是线下，改个名字方便识别）
	 public static final String BSS_REFUND_STATUS_OFFLINE_FAIL = "2"; 	//线下退款失败
	 public static final String BSS_REFUND_STATUS_ONLINE_SUCC = "3"; 	//线上退款成功
	 public static final String BSS_REFUND_STATUS_ONLINE_FAIL = "4"; 	//线上退款失败
	 public static final String BSS_REFUND_STATUS_5 = "5"; 	//退款中
	 
	 //退单处理类型
	 public static final String REFUND_DEAL_TYPE_01 = "01";			//正常单
	 public static final String REFUND_DEAL_TYPE_02 = "02";			//退单
	 
	 //Bss返销状态
	 public static final String BSS_CANCEL_STATUS_0 = "0";				//未返销
	 public static final String BSS_CANCEL_STATUS_1 = "1";				//已返销
	 
	 //ess返销状态
	 public static final String ESS_CANCEL_STATUS_0 = "0";				//未返销
	 public static final String ESS_CANCEL_STATUS_1 = "1";				//已返销
	 
	 //退单状态
	 public static final String REFUND_STATUS_00 = "00";			//退单驳回
	 public static final String REFUND_STATUS_01 = "01";			//退单确认通过
	 public static final String REFUND_STATUS_02 = "02";			//BSS返销
	 public static final String REFUND_STATUS_03 = "03";			//ESS返销
	 public static final String REFUND_STATUS_04 = "04";			//已退单
	 public static final String REFUND_STATUS_05 = "05";			//已退单设置
	 public static final String REFUND_STATUS_06 = "06";			//归档
	 public static final String REFUND_STATUS_07 = "07";			//退单结果通知商城
	 public static final String REFUND_STATUS_08 = "08";			//退单申请
	 public static final String REFUND_STATUS_09 = "09";			//退款失败
		 
	 /******************************退单状态位结束***********************************/
	 
	//订单商城来源
		 public static final String ORDER_ORIGIN_ZBWO = "ZBWO";//总部商城
		 public static final String ORDER_ORIGIN_TBFX = "TBFX";//淘宝分销
		 public static final String ORDER_ORIGIN_WO = "WO";//沃商城
		 public static final String ORDER_ORIGIN_PP = "PP";//拍拍商城
		 public static final String ORDER_ORIGIN_JK = "JK";//网盟集团客户
		 public static final String ORDER_ORIGIN_TB = "TB";//淘宝商城
		 public static final String ORDER_ORIGIN_GZTM = "GZTM";//广州天猫
		 public static final String ORDER_ORIGIN_SZPP = "SZPP";//深圳拍拍
		 public static final String ORDER_ORIGIN_FSZY = "FSZY";//佛山自营商城
		 public static final String ORDER_ORIGIN_SZZY = "SZZY";//深圳自营商城
		 public static final String ORDER_ORIGIN_TSC = "TSC";//电话商城
		 public static final String ORDER_ORIGIN_WAP = "WAP";//沃商城WAP
		 public static final String ORDER_ORIGIN_VIP = "VIP";//VIP商城
		 public static final String ORDER_ORIGIN_UHZ = "UHZ";//U惠站
		 public static final String ORDER_ORIGIN_WMTY = "WMTY";//网盟统一接口
		 public static final String ORDER_ORIGIN_WMQT = "WMQT";//网盟其他来源
		 public static final String ORDER_ORIGIN_TEYX = "TEYX";//电话营销
		 public static final String ORDER_ORIGIN_WS = "WS";//微商城
		 public static final String ORDER_ORIGIN_ZXWM = "ZXWM";//线上中小网盟
		 public static final String ORDER_ORIGIN_YYLM = "YYLM";//异业联盟
		 public static final String ORDER_ORIGIN_NH = "NH";//集客农行
		 public static final String ORDER_ORIGIN_WBDR = "WBDR";//外部导入
		 
		 //销售组织
		 public static final String ORDER_FROM_10000 = "10000";//中国联通
		 public static final String ORDER_FROM_10001 = "10001";//淘宝网厅
		 public static final String ORDER_FROM_10002 = "10002";//联通商城
		 public static final String ORDER_FROM_10003 = "10003";//总部商城
		 public static final String ORDER_FROM_100032 = "100032";//总部商城(爬虫)
		 public static final String ORDER_FROM_10004 = "10004";//网盟店铺
		 public static final String ORDER_FROM_10005 = "10005";//拍拍
		 public static final String ORDER_FROM_10006 = "10006";//农行商城
		 public static final String ORDER_FROM_10007 = "10007";//360商城
		 public static final String ORDER_FROM_10008 = "10008";//新商城
		 public static final String ORDER_FROM_10012 = "10012";//淘宝分销
		 public static final String ORDER_FROM_10013 = "10013";//深圳联通商城
		 public static final String ORDER_FROM_10015 = "10015";//电话商城
		 public static final String ORDER_FROM_10030 = "10030";//微商城
		 public static final String ORDER_FROM_10031 = "10031";//沃货架
		 public static final String ORDER_FROM_100312 = "100312";//沃货架2.0
		 public static final String ORDER_FROM_10032 = "10032";//营业厅U惠站
		 public static final String ORDER_FROM_10033 = "10033";//销售联盟
		 public static final String ORDER_FROM_10034 = "10034";//vip商城
		 public static final String ORDER_FROM_10035 = "10035";//电子沃店
		 public static final String ORDER_FROM_10036 = "10036";//沃商城
		 public static final String ORDER_FROM_10037 = "10037";//CPS
		 public static final String ORDER_FROM_10038 = "10038";//异业联盟
		 public static final String ORDER_FROM_10039 = "10039";//百度担保
		 public static final String ORDER_FROM_10040 = "10040";//理财通
		 public static final String ORDER_FROM_10041 = "10041";//
		 public static final String ORDER_FROM_10057 = "10057";//广州天猫
		 public static final String ORDER_FROM_10053 = "10053";//B2B商城
		 public static final String ORDER_FROM_10059 = "10059";//沃云购APP
		 public static final String ORDER_FROM_10060 = "10060";//广东AOP服务平台
		 public static final String ORDER_FROM_10061 = "10061";//华盛B2C
		 public static final String ORDER_FROM_10062 = "10062";//华盛B2B
		 public static final String ORDER_FROM_10065 = "10065";//无纸化平台
		 public static final String ORDER_FROM_10064 = "10064";//BSS上传平台
		 public static final String ORDER_FROM_10066 = "10066";//京东
		 public static final String ORDER_FROM_10067 = "10067";//移宝
		 public static final String ORDER_FROM_10068 = "10068";//苏宁
		 public static final String ORDER_FROM_10069 = "10069";//华为
		 public static final String ORDER_FROM_10070 = "10070";//魅族
		 public static final String ORDER_FROM_10071 = "10071";//一号店
		 public static final String ORDER_FROM_10072 = "10072";//落基伟业
		 public static final String ORDER_FROM_10073 = "10073";//卓端数码
		 public static final String ORDER_FROM_10074 = "10074";//极行速
		 public static final String GOODS_WMS_20001="20001";//WMS 
		//发票打印方式
		 public static final String INVOICE_PRINT_TYPE_MONTH = "2";
		 public static final String INVOICE_PRINT_TYPE_NO = "3";
		 public static final String INVOICE_PRINT_TYPE_ONCE_1 = "1";
		 public static final String INVOICE_PRINT_TYPE_ONCE_2 = "一次性发票";
		 
		 //是否已打印
		 public static final String IS_PRINT_0 = "0";			//0: 未打印
		 public static final String IS_PRINT_1 = "1";			//1: 已打印
		 public static final String IS_PRINT_2 = "2";			//2: 以前打印
		 
		 //总部货品类型【实物】
		 public static final String ZB_PRODUCT_TYPE_SJL = "SJL";
		 public static final String ZB_PRODUCT_TYPE_PJL = "PJL";
		 public static final String ZB_PRODUCT_TYPE_OTH = "OTH";
		 public static final String ZB_PRODUCT_TYPE_SWK = "SWK";
		 public static final String ZB_PRODUCT_TYPE_XJD = "XJD";
		 
		 //总部商品类型
		 public static final String ZB_GOODS_TYPE_ZHKL = "ZHKL";//号卡
		 public static final String ZB_GOODS_TYPE_ZHYL = "ZHYL";//号卡合约
		 public static final String ZB_GOODS_TYPE_ZZDL = "ZZDL";//终端合约 
		 public static final String ZB_GOODS_TYPE_ZSWK = "ZSWK";//上网卡 
		 public static final String ZB_GOODS_TYPE_ZLZD = "ZLZD";//裸终端
		 public static final String ZB_GOODS_TYPE_ZPJL = "ZPJL";//配件 
		 public static final String ZB_GOODS_TYPE_ZYWL = "ZYWL";//业务变更
		//物流类型
		 public static final String LOGIS_NORMAL = "0"; // 正常发货
		 public static final String LOGIS_SUPPLY = "1"; // 补寄
		 public static final String LOGIS_AGAIN = "2"; // 重发
		//物流单打印
		 public static final String IS_COD_YES = "1"; // 代收
		 public static final String IS_COD_NO = "0"; // 非代收
		 public static final String IS_INSUR_YES = "1"; // 保价
		 public static final String IS_INSUR_NO = "0"; // 非保价
		 public static final String IS_SIGNBACK = "1"; // 签回单 
		 public static final String POST_PRINT_SIGN_GOU = "√"; // 打印的钩符号
		 public static final String POST_NUM = "1"; // 默认设置的物流物品件数
		 //物流配送类型
		 public static final String SHIP_TYPE_KD="KD";//快递
		 public static final String SHIP_TYPE_SH="SH";//送货
		 public static final String SHIP_TYPE_ZT="ZT";//自提
		 public static final String SHIP_TYPE_SDS="SDS";//闪电送
		 public static final String SHIP_TYPE_PY="PY";//平邮
		 public static final String SHIP_TYPE_EMS="EMS";//EMS
		 public static final String SHIP_TYPE_ZYPS="ZYPS";//自有配送
		 public static final String SHIP_TYPE_MJBY="MJBY";//卖家包邮
		 public static final String SHIP_TYPE_XNFH="XNFH";//虚拟发货
		 public static final String SHIP_TYPE_XCTH="XCTH";//现场提货
		 public static final String SHIP_TYPE_JJPS="JJPS";//就近配送
		 public static final String SHIP_TYPE_XMPS="XMPS";//小米配送
		 public static final String SHIP_TYPE_WX="WX";//无需配送
		 //配送时间类型
		 public static final String SHIPPING_TIME_HOLIDAY = "HOLIDAY";
		 public static final String SHIPPING_TIME_WORKDAY = "WORKDAY";
		 public static final String SHIPPING_TIME_NOLIMIT = "NOLIMIT";
		 public static final String SHIPPING_TIME_NAME_HOLIDAY = "双休日、节假日送货";
		 public static final String SHIPPING_TIME_NAME_WORKDAY = "工作日，周一到周五工作时间";
		 public static final String SHIPPING_TIME_NAME_NOLIMIT = "不限时间送货";
	     //物流-邮费支付方式
		 public static final String POSTAGE_MODE_JF="1";//寄方付(现结)
		 public static final String POSTAGE_MODE_SF="2";//收方付(现结)
		 public static final String POSTAGE_MODE_DSF="3";//第三方付
		 public static final String POSTAGE_MODE_JZZF="4";//集中总付(月结)
		 public static final String POSTAGE_MODE_JFDSF="5";//寄方付+第三方付
		 //物流公司编码
		 public static final String POST_COMPANY_CODE_ZJS="ZJS";//宅急送
		 //物流公司id
		 public static final String POST_COMPANY_ID_ZJS0001="ZJS0001";//宅急送
		 public static final String POST_COMPANY_ID_ZJS_JZ="ZJS-JZ";//宅急送-生产中心
		 public static final String POST_COMPANY_ID_ZJS_JZ_DS="ZJS-JZ(DS)";//宅急送-生产中心(代收款)
		 //发货公司
		 public static final String POST_COMP_ZGLT="ZGLT";//中国联合网络通讯有限公司
		 public static final String POST_COMP_ZGLTSZ="ZGLTSZ";//中国联合网络通讯有限公司深圳分公司
		 public static final String POST_COMP_SZLT="SZLT";//深圳联通
	     //补寄状态
		 public static final String ITEM_PRINT_STATUS_0="0";//待补寄
		 public static final String ITEM_PRINT_STATUS_1="1";//已打印 
		 public static final String ITEM_PRINT_STATUS_2="2";// 补寄完成
		 //订单查询条件
		 public static final String IS_ORDER_HIS_NO="0";// 是否是历史单-否
		 public static final String IS_ORDER_HIS_YES="1";//是否是历史单-是
		 //订单查询条件(订单状态)
		 public static final String IS_ORDER_HIS_0="0";// 订单状态-在途订单
		 public static final String IS_ORDER_HIS_1="1";// 订单状态-系统归档
		 public static final String IS_ORDER_HIS_2="2";// 订单状态-业务归档
	    //整单补寄状态
		 public static final String ORDER_SUPPLY_STATUS_0="0";//待补寄
		 public static final String ORDER_SUPPLY_STATUS_1="1";// 补寄完成
		 
		 public static final String HIS_TABLE_STR="_HIS";// 历史表结尾
		//订单预警
		 public static final String WARNING_YEL="warning_yel";// 预警颜色-黄
		 public static final String WARNING_ORG="warning_org";//预警颜色-橙
		 public static final String WARNING_RED="warning_red";// 预警颜色-红
		 public static final String WARNING_LIST_URL="/shop/admin/orderWarningAction!searchList.do";// 预警配置列表
		
		 public static final String IS_ORDER_VIEW_YES="1";//是否是订单只查看
		
		 
		 
		 public static final String QUERY_TYPE_SUPPLY="supply"; // 补寄重发
		 

		 //数据模板运行状态【es_outer_execute_tmpl】
		 public static final String RUN_STATUS_00X = "00X";
		 public static final String RUN_STATUS_00A = "00A";
		 
		 public static final String ES_ORDER_ITEMS_APT_PRINTS_STATUS_0 = "0";//0未打印
		 public static final String ES_ORDER_ITEMS_APT_PRINTS_STATUS_1 = "1";//1已打印

		 //字典映射第三方系统编码
		 public static final String OTHER_SYSTEM_ZB = "zb";
		 public static final String ORDER_FROM_TAOBAO = "taobao";
		 
		 public static final String ACTION_TIME_PAGELOAD = "pageload";
		 public static final String ACTION_TIME_PAGESAVE = "pagesave";
		 
		 //按钮类型
		 public static final String BTN_TYPE_SAVE ="save";
		 //受理单打印模式  
		 public static final String RECEIPT_MODE_0 = "0";//套打
		 public static final String RECEIPT_MODE_1 = "1";//白打
		 
		 //平台类型
		 public static final String PLAT_TYPE_10001 = "10001";//淘宝网厅
		 public static final String PLAT_TYPE_10002 = "10002";//联通商城
		 public static final String PLAT_TYPE_10003 = "10003";//总部商城
		 public static final String PLAT_TYPE_10004 = "10004";//网盟店铺
		 public static final String PLAT_TYPE_10005 = "10005";//拍拍
		 public static final String PLAT_TYPE_10006 = "10006";//农行商城
		 public static final String PLAT_TYPE_10007 = "10007";//360商城
		 public static final String PLAT_TYPE_10008 = "10008";//沃云购
		 public static final String PLAT_TYPE_10009 = "10009";//订单系统
		 public static final String PLAT_TYPE_10010 = "10010";//WMS
		 public static final String PLAT_TYPE_10011 = "10011";//商品管理系统
		 public static final String PLAT_TYPE_10012 = "10012";//淘宝分销
		 public static final String PLAT_TYPE_10013 = "10013";//深圳联通商城
		 public static final String PLAT_TYPE_10060 = "10060";//广东AOP服务平台	 
		 public static final String PLAT_TYPE_10061 = "10061";//订单来源平台，华盛
		 public static final String PLAT_TYPE_10065 = "10065";//无纸化平台
		 public static final String PLAT_TYPE_10064 = "10064";//BSS上传平台
		 public static final String PLAT_TYPE_10066 = "10066";//网盟
		 
		 
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
		 
		//证件类型常量值
		 public static final String CERTI_TYPE_SFZ15 = "SFZ15";					//15位身份证
		 public static final String CERTI_TYPE_SFZ18 = "SFZ18";					//18位身份证
		 public static final String CERTI_TYPE_WXZ = "WXZ";					//无需证件
		 
		 //号卡类型
		 public static final String CARD_TYPE_FU = "02";	//副卡
		 public static final String CARD_TYPE_ZHU = "01";	//主卡
		 
		 //退单申请地址
		 public static final String RETURNED_DETAIL_URL = "shop/admin/orderFlowAction!returnedOrd.do";
		 public static final String YCL_URL = "shop/admin/orderFlowAction!preDealOrd.do";
		 public static final String REPLENISH_URL = "shop/admin/orderFlowAction!toReplenish.do";//资料补录页面
		 //补寄重发
		 public static final String SUPPLY_URL = "shop/admin/orderSupplyAction!order_supply.do";
		 public static final String ORDER_VIEW_DETAIL_URL = "shop/admin/orderFlowAction!order_detail_view.do";
		 
		 //归档订单恢复操作
		 public static final String ORDER_RECOVER_URL = "shop/admin/ordAuto!ordRecover.do";
		
		public static final String HIS_ORDER_DETAILS_URL = "shop/admin/orderSupplyAction!order_supply.do";
		//page 界面来源 inf接口来源
		public static final String DEAL_FROM_INF = "inf";
		public static final String DEAL_FROM_PAGE = "page";
	    public static final String TRACE_CODE_H = "H"; //发货
		public static final String TRACE_CODE_H_PLAN_ID = "201501058010000082"; //发货
		
		public static final String QUERY_TYPE_ORDER_LIST = "list";//查看列表 不分页
		
		 //退单原因拼装判断用的
		 public static final String REFUND_APPLY_DESC = "退单原因：";//积压归档
		 public static final String REFUND_CONFIRM_DESC = "退单确认意见：";//积压归档
		 public static final String REFUND_CANCEL_DESC = "取消退单原因：";//积压归档
		 
		 //支付方式
		 public static final String PAY_METHOD_ZFB = "ZFB";//支付宝
		 public static final String PAY_METHOD_QEZF = "QEZF";//全额支付
		 
		 //支付类型
		 public static final String PAY_TYPE_ZXZF = "ZXZF";//在线支付
		 public static final String PAY_TYPE_HDFK = "HDFK";//货到付款
		 public static final String PAY_TYPE_DBZF = "DBZF";//担保免支付
		 public static final String PAY_TYPE_XCZF = "XCZF";//线下现场支付
		 public static final String PAY_TYPE_JFZF = "JFZF";//积分支付
		 
		 //支付状态
		 public static final String PAY_STATUS_NOT_PAY = "0";//未支付
		 public static final String PAY_STATUS_PAYED = "1";//已支付
		 
		 
		 //默认费用0
		 public static final String DEFAULT_FEE_ZERO = "0";
		 public static final String DEFAULT_STR_ONE = "1";
		 public static final String DEFAULT_STR_SERO = "0";
		 public static final String DJYCK_FEE_ITEM_ID = "4001";
		 public static final String NUMYCK_FEE_ITEM_ID = "2001";
		 public static final String OPEN_ACC_FEE_ITEM_ID = "5101";
		 public static final String KF_FEE_ITEM_ID = "1010";//[营业费用]SIM卡/USIM卡费
		 public static final String KF_FEE_ITEM_ID_BSS = "2300";//BSS卡费用项ID
		 
		 public static final String PACKAGE_SALE_DEFAULT = "0";//套包销售标记
		 public static final String PACKAGE_SALE_YES = "1";//套包销售
		 public static final String PACKAGE_SALE_NO = "0";//非套包销售
		 
		//号码等级
		public static final String PHONE_CLASS_ID_1 = "1";//一级靓号
		public static final String PHONE_CLASS_ID_2 = "2";
		public static final String PHONE_CLASS_ID_3 = "3";
		public static final String PHONE_CLASS_ID_4 = "4";
		public static final String PHONE_CLASS_ID_5 = "5"; 
		public static final String PHONE_CLASS_ID_6 = "6";//六级靓号
		public static final String PHONE_CLASS_ID_9 = "9";//普通号码	
		
		//爬虫相关规则id
		//浙江联通所内
//		public static final String CRAWLER_ORDER_ALLOCATION_AUTO="170301520560000129";//总商爬虫订单分配 自动生产模式
//		public static final String CRAWLER_ORDER_ALLOCATION_MANUAL="170301602470000271";//总商爬虫订单分配 手动生产模式
//		
//		public static final String ORDER_MODEL_08_ICCID_RULE_ID_AOP = "170871813130000248"; //总商爬虫对应的获取ICCID  自动生成模式
//		public static final String ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL ="170162059400000026" ;//总商爬虫总商进入开户详情  手动生产模式
//		public static final String ORDER_MODEL_08_ZB_OPEN_ACCOUNT_DETAIL ="170141553590000472" ;//总商爬虫总商进入开户详情  自动生产模式
//		public static final String ORDER_RETURNED_PLAN_PC = "170171938350000378";		//爬虫退单方案-爬虫
//		public static final String ORDER_RETURN_APPLY_RULE_PC = "170201125430000133";//接收退单申请规则[爬虫]
//		public static final String ORDER_RETURN_CONFIRM_RULE_PC = "170201127510000206";//确认退单调总商[爬虫]
//		public static final String ORDER_CANCELRETURNED_RULE_PC="170201542140000093";//取消退单规则【爬虫】	
//		public static final String CRAWLER_ORDER_BACK_RULE_ID = "170191354090000310";//爬虫订单归档
//		public static final String WRITE_CARD_INFO_TO_ZB_07 = "170131109160000263";//爬虫生产模式-写卡结果通知总部，对应写卡完成提交订单
//		public static final String ORDER_MODEL_07_ICCID_RULE_ID = "170131110020000293"; //爬虫生产模式对应的规则ID
//		public static final String ORDER_MODEL_07_ICCID_RULE_ID_AOP ="170141606090000797";//爬虫自动写卡
//		public static final String ORDER_MODEL_07_ZB_WRITE_CARD_DATAS ="170141606090000797";//获取总商写卡数据
		
		 
		//爬虫相关规则id，浙江联通现场
//		public static final String CRAWLER_ORDER_ALLOCATION_AUTO="171201735380000094";//总商爬虫订单分配 自动生产模式
//		public static final String CRAWLER_ORDER_ALLOCATION_MANUAL="171201745170000263";//总商爬虫订单分配 手动生产模式		
//		public static final String ORDER_MODEL_08_ICCID_RULE_ID_AOP = "171201946510000071"; //总商爬虫对应的获取ICCID  自动生成模式
//		public static final String ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL ="171201957330000229" ;//总商爬虫总商进入开户详情  手动生产模式
//		public static final String ORDER_MODEL_08_ZB_OPEN_ACCOUNT_DETAIL ="171201945290000044" ;//总商爬虫总商进入开户详情  自动生产模式
//		public static final String ORDER_RETURNED_PLAN_PC = "170182002062000639";		//爬虫退单方案-爬虫
//		public static final String ORDER_RETURN_APPLY_RULE_PC = "170182003162000690";//接收退单申请规则[爬虫]
//		public static final String ORDER_RETURN_CONFIRM_RULE_PC = "170182008162000876";//确认退单调总商[爬虫]
//		public static final String ORDER_CANCELRETURNED_RULE_PC="170182017562001064";//取消退单规则【爬虫】	
//		public static final String CRAWLER_ORDER_BACK_RULE_ID = "171261046230000024";//爬虫订单归档
//
//		public static final String WRITE_CARD_INFO_TO_ZB_07 = "170701531470000030";//爬虫生产模式-写卡结果通知总部，对应总商写卡成功
//		public static final String ORDER_MODEL_07_ICCID_RULE_ID = "171202014010000482"; //爬虫生产模式对应的规则ID
//		public static final String ORDER_MODEL_07_ICCID_RULE_ID_AOP ="170141606090000797";// 
//		public static final String ORDER_MODEL_07_ZB_WRITE_CARD_DATAS ="171202014510000500";//获取总商写卡数据 
		
		
		//
		public static final String CRAWLER_ORDER_ALLOCATION_AUTO="171201735380000094";//总商爬虫订单分配 自动生产模式
		public static final String CRAWLER_ORDER_ALLOCATION_MANUAL="170912202132000482";//总商爬虫订单分配 手动生产模式		
		public static final String ORDER_MODEL_08_ICCID_RULE_ID_AOP = "171201946510000071"; //总商爬虫对应的获取ICCID  自动生成模式
		public static final String ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL ="170912213592000659" ;//总商爬虫总商进入开户详情  手动生产模式
		public static final String ORDER_MODEL_08_ZB_OPEN_ACCOUNT_DETAIL ="171201945290000044" ;//总商爬虫总商进入开户详情  自动生产模式
		public static final String ORDER_RETURNED_PLAN_PC = "170182002062000639";		//爬虫退单方案-爬虫
		public static final String ORDER_RETURN_APPLY_RULE_PC = "170912243242001220";//接收退单申请规则[爬虫]
		public static final String ORDER_RETURN_CONFIRM_RULE_PC = "170912243582001238";//确认退单调总商[爬虫]
		public static final String ORDER_CANCELRETURNED_RULE_PC="170182017562001064";//取消退单规则【爬虫】	
		public static final String CRAWLER_ORDER_BACK_RULE_ID = "170211123172000624";//爬虫订单归档
																
		public static final String WRITE_CARD_INFO_TO_ZB_07 = "170912226532000861";//爬虫生产模式-写卡结果通知总部，对应总商写卡成功
		public static final String ORDER_MODEL_07_ICCID_RULE_ID = "170912225392000834"; //爬虫生产模式对应的规则ID
		public static final String ORDER_MODEL_07_ICCID_RULE_ID_AOP ="170141606090000797";// 
		public static final String ORDER_MODEL_07_ZB_WRITE_CARD_DATAS ="170912226232000852";//获取总商写卡数据 
		
}
