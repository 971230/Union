package com.ztesoft.common.consts;


public final class KeyValues {
	
	//申请单类型
	public final static String INFO_STATE_N = "n";//在途单 n
	public final static String INFO_STATE_L = "l";//历史单 l
	public final static String INFO_STATE_U = "u";//在用 u
    public final static String INFO_STATE_A = "A";//查看所有在途和历史的单子
    
    public final static String NEGATIVE_ONE = "-1"; //默认-1型   
    
    public final static String SPLIT_STRING = ","; //逗号分隔符
    
	//业务动作
	public final static String ACTION_TYPE_A = "A"; //动作为新增/添加	
	public final static String ACTION_TYPE_D = "D"; //动作类型为删除/取消	
	public final static String ACTION_TYPE_K = "K"; //动作类型未做修改	
	public final static String ACTION_TYPE_M = "M"; //动作类型为修改类型
	public final static String ACTION_TYPE_C = "C"; //动作类型为复制
	
    //对数据库操作类型
	public final static String OPER_FLAG_A = "A"; //操作类型为新增/添加	
	public final static String OPER_FLAG_D = "D"; //操作类型为删除/取消	
	public final static String OPER_FLAG_K = "K"; //操作类型未做修改	
	public final static String OPER_FLAG_M = "M"; //操作类型为修改类型
	
	//服务的动作类型
	//sql: select action_code,action_name from pm_action
	public final static String SERV_ACTION_TYPE_A = "A"; //服务动作类型为新增	
	public final static String SERV_ACTION_TYPE_D = "D"; //服务动作类型为删除	
	public final static String SERV_ACTION_TYPE_K = "K"; //服务动作类型为移动	
	public final static String SERV_ACTION_TYPE_M = "M"; //服务动作类型为修改
	
    //真假标识
	public final static String IFTRUE_T = "T";	 //条件为TRUE	
	public final static String IFTRUE_F = "F";     //条件为FALSE

    
    //状态
    public static final String STATE_USING      = "00A";//有效
    public static final String STATE_CANCEL     = "00X";//注销
    public static final String STATE_ARCHIVE    = "00H";//归档
    public static final String STATE_ONWAY      = "00N";//在途
    
    
//    
//    /************* 付费模式 ********************/
//	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "后付费", desc = "N1N2； 10～99的顺序数字")
//	public final static String PAYMENT_MODE_CD_A = "12"; // 后付费
//	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "预付费", desc = "N1N2； 10～99的顺序数字")
//	public static final String PAYMENT_MODE_CD_B = "21";  //预付费
//	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "准实时后付费", desc = "N1N2； 10～99的顺序数字")
//	public static final String PAYMENT_MODE_CD_C = "22";  //准实时预付费
//	@FieldJudgeMeta(id = "PRI-0001", code = "PAYMENT_MODE_CD", name = "OCS预付费", desc = "N1N2； 10～99的顺序数字")
//	public static final String PAYMENT_MODE_CD_OCS = "13";  //ocs预付费
	/************* 付费模式 ********************/
	
	
	/*******************销售品状态*************************/
	/**
	 * 销售品状态：有效，请使用本地编码
	 */
	public final static String $PRODOFFER_STATE_EFFECTIVE = "10";//销售品有效：本地编码
	
	/**
	 * 报批状态
	 * 销售品报批时，上传成功给集团，状态改成0000，等集团报批反馈通知后，销售品状态再改回$PRODOFFER_STATE_EFFECTIVE
	 * 主要用以标识改销售品是否已报批成功。
	 */
	public final static String $PRODOFFER_STATE_BP = "0000";
	
	
	/*******************产品状态*************************/
	/**
	 * 产品状态：有效，请使用本地编码
	 */
	public final static String $PRODUCT_STATE_EFFECTIVE = "10";//产品有效：本地编码
	
	
	
	//销售品属性约束类型
	/**
	 * 最大最小值
	 */
	 public static final String ATTR_RELA_TYPE_MINMAX      = "30";//最大最小值
	 /**
	 * 列表值
	 */
	 public static final String ATTR_RELA_TYPE_LIST     = "31";//列表值
	 /**
	  * 指定值
	  */
	 public static final String ATTR_RELA_TYPE_DESIGNATED    = "32";//指定值
	 /**
	  * 默认值
	  */
	 public static final String ATTR_RELA_TYPE_DEFAULT     = "33";//默认值
	 /**
	  * 销售品属性控制产品属性最大最小值
	  */
	 public static final String ATTR_RELA_TYPE_OFFATTR_MINMAX      = "40";//销售品属性控制产品属性最大最小值
	 /**
	  * 销售品属性控制产品属性列表值
	  */
	 public static final String ATTR_RELA_TYPE_OFFATTR_LIST     = "41";//销售品属性控制产品属性列表值
	 /**
	  * 销售属性值同产品属性值
	  */
	 public static final String ATTR_RELA_TYPE_OFFATTR_EQU    = "10";//销售属性值同产品属性值
	 /**
	  * 销售品属性值映射产品属性值
	  */
	 public static final String ATTR_RELA_TYPE_OFFATTR_MAP    = "20";//销售品属性值映射产品属性值
	 
	 //销售口构成成员归属类型
	 /**
	  * 订购者
	  */
	 public static final String OFFER_DETAIL_RELA_TYPE_BUY      = "1";//订购者
	 /**
	  * 参与者
	  */
	 public static final String OFFER_DETAIL_RELA_TYPE_JOIN     = "2";//参与者
	 /**
	  * 约束
	  */
	 public static final String OFFER_DETAIL_RELA_TYPE_RESTRICT    = "3";//约束
	 
	 //销售品构成成员约束类型
	 /**
	  * 缺省默认约束
	  */
	 public static final String OFFER_DETAIL_RULETYPE_SELECTED      = "01";//缺省默认约束
	 /**
	  * 必须强制约束
	  */
	 public static final String OFFER_DETAIL_RULETYPE_MUST     = "02";//必须强制约束
	 /**
	  * 可选约束
	  */
	 public static final String OFFER_DETAIL_RULETYPE_CHOOSE    = "03";//可选约束
	 /**
	  * 禁止选约束
	  */
	 public static final String OFFER_DETAIL_RULETYPE_BAN    = "04";//禁止选约束
	 /**
	  * 拆除销售品时默认取消功能产品
	  */
	 public static final String OFFER_DETAIL_RULETYPE_CANCEL    = "05";//拆除销售品时默认取消功能产品
	 
	 
	 //销售品构成配置界面上，成员树各节点的类型标记
	 /**
	  * 销售品成员树-角色节点
	  */
	 public static final String OFFER_DETAIL_TREECEL_TYPE_ROLE      = "1";//销售品成员树-角色节点
	 public static final String OFFER_DETAIL_TREECEL_TYPE_MPRODUCT     = "2";//销售品成员树-主产品节点
	 public static final String OFFER_DETAIL_TREECEL_TYPE_APRODUCT   = "3";//销售品成员树-附属产品节点
	 

	 //销售品构成成员元素类型
	 /**
	  * 接入产品
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10A      = "10A";
	 /**
	  * 功能产品
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10D      = "10D";
	 /**
	  * 资源产品
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10Z      = "10Z"; 
	 /**
	  * 账户
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10J      = "10J"; 
	 /**
	  * 客户
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10I      = "10I";
	 /**
	  * 外网号码
	  */
	 public static final String OFFER_DETAIL_ELEMENT_TYPE_10V      = "10V";
	 

//	 销售品构成角色包含类型
	 /**
	  * 不可选
	  */
	 public static final String INCLUDE_FLAG_UNELECTABLE = "0";
	 /**
	  * 必选
	  */
	 public static final String INCLUDE_FLAG_MUST = "1";
	 /**
	  * 可选
	  */
	 public static final String INCLUDE_FLAG_OPTIONAL = "2";

//	销售品构成属性约束-关系层次
	 /**
	  * 与成员产品属性
	  */
	public static final String OFFPRODATTRREL_RELA_LEVEL_MAIN = "10";
	/**
	 * 与成员的附属产品属性
	 */
	public static final String OFFPRODATTRREL_RELA_LEVEL_SUB = "11";
	/**
	 * 与成员的资源产品属性
	 */
	public static final String OFFPRODATTRREL_RELA_LEVEL_RESOURCE = "12";

	
	//销售品群组关系-组内关系
		/**
		 * 依赖
		 */
		public static final String OFFER_GROUP_REL_DEPEND = "10L";
		
		/**
		 * 互斥
		 */
		public static final String OFFER_GROUP_REL_MUTEX = "10A";
		
		
//	销售品关系类型
	/**
	 * 绑定
	 */
	public static final String OFFER_REL_BINDING = "10H";
	/**
	 * 依赖
	 */
	public static final String OFFER_REL_DEPEND = "10";
	/**
	 * 互斥
	 */
	public static final String OFFER_REL_MUTEX = "11";
	/**
	 * 升级
	 */
	public static final String OFFER_REL_UPGRADE = "12";
	/**
	 * 替换
	 */
	public static final String OFFER_REL_REPLACE = "13";
	/**
	 * 推荐
	 */
	public static final String OFFER_REL_RECOMMEND = "14";
	/**
	 * 接续
	 */
	public static final String OFFER_REL_CONTINUE = "10F";
	
	
//	销售品关系基于类型
	/**
	 * 基于成员
	 */
	public static final String BASE_ON_MEMBER = "1";
	/**
	 * 基于客户
	 */
	public static final String BASE_ON_CUST = "2";
	
	
//	销售品关系两端销售品类型
	/**
	 * 所有者
	 */
	public static final String OFFER_RELTYPE_OWNER = "0";
	/**
	 * 订购者
	 */
	public static final String OFFER_RELTYPE_ORDER = "1";
	/**
	 * 参与者
	 */
	public static final String OFFER_RELTYPE_PARTICIPANT = "2";
	
//	操作指示
	/**
	 * 警告，但可以继续
	 */
	public static final String OPERATION_FLAG_WARNNING = "1BA";
	/**
	 * 必须终止
	 */
	public static final String OPERATION_FLAG_STOP = "1BB";
	/**
	 * 提示
	 */
	public static final String OPERATION_FLAG_PROMT = "1BC";
	
//	时限类型
	/**
	 * 无限制
	 */
	public static final String TIME_LIMIT_UNLIMITED = "0";
	/**
	 * 相对时间
	 */
	public static final String TIME_LIMIT_RELATIVE = "1";
	/**
	 * 绝对时间
	 */
	public static final String TIME_LIMIT_ABSOLUTE = "2";
	
//	担保类型
	/**
	 * 现金担保
	 */
	public static final String ASSURETYPE_CASH = "01";
	/**
	 * 产品担保
	 */
	public static final String ASSURETYPE_PRODUCT = "02";
	/**
	 * 账户担保
	 */
	public static final String ASSURETYPE_ACCOUNT = "03";
	/**
	 * 客户担保
	 */
	public static final String ASSURETYPE_CUST = "04";
	/**
	 * 电信员工担保
	 */
	public static final String ASSURETYPE_STAFF = "05";

//	货币单位
	/**
	 * 元
	 */
	public static final String YUAN = "0";
	/**
	 * 角
	 */
	public static final String JIAO = "1";
	/**
	 * 分
	 */
	public static final String FEN = "2";
	
//	销售品担保控制类型
	
	/**
	 * 基于绝对时间
	 */
	public static final String CONTROL_TYPE_ABS = "A";
	/**
	 * 基于业务规则
	 */
	public static final String CONTROL_TYPE_RULE = "C";
	/**
	 * 基于相对时间
	 */
	public static final String CONTROL_TYPE_REL = "R";
	/**
	 * 不进行约束
	 */
	public static final String CONTROL_TYPE_NORES = "U";
	
//	担保计算方式
	/**
	 * 不分优惠商品
	 */
	public static final String WARR_CALC_MODE0 = "0";
	/**
	 * 按优惠商品累计
	 */
	public static final String WARR_CALC_MODE1 = "1";
	
//	在途担保规则
	/**
	 * 允许
	 */
	public static final String INC_WARR_RULE_ALLOW = "0";
	/**
	 * 警告
	 */
	public static final String INC_WARR_RULE_WARN = "1";
	/**
	 * 拒绝
	 */
	public static final String INC_WARR_RULE_REJECT = "2";
	
//	生失效时间类型
	/**
	 * 生效时间
	 */
	public static final String TIME_KIND_EFF = "0";
	/**
	 * 失效时间
	 */
	public static final String TIME_KIND_EXP = "1";
	
//	生失效时间指定类型
	/**
	 * 系统指定
	 */
	public static final String SET_TIME_TYPE_SYS = "0";
	/**
	 * 用户指定
	 */
	public static final String SET_TIME_TYPE_USER = "1";
	
//	日期策略
	/**
	 * 竣工时间
	 */
	public static final String TIME_POINT_TYPE_NOW = "0";
	/**
	 * 竣工次月
	 */
	public static final String TIME_POINT_TYPE_NEXTMON = "1";
	/**
	 * 生效时间
	 */
	public static final String TIME_POINT_TYPE_EFFTIME = "2";
	/**
	 * 本帐期
	 */
	public static final String TIME_POINT_TYPE_CURPERIOD = "3";
	/**
	 * 绝对时间
	 */
	public static final String TIME_POINT_TYPE_ABSTIME = "4";
	/**
	 * 指定时间
	 */
	public static final String TIME_POINT_TYPE_APPOINT = "5";
	/**
	 * 根据参数设置时间
	 */
	public static final String TIME_POINT_TYPE_BYPARAM = "6";
	
//	时长单位
	/**
	 * 秒
	 */
	public static final String TIME_UNIT_SS = "SS";
	/**
	 * 日
	 */
	public static final String TIME_UNIT_DD = "DD";
	/**
	 * 月
	 */
	public static final String TIME_UNIT_MM = "MM";
	/**
	 * 账期
	 */
	public static final String TIME_UNIT_PD = "PD";
	/**
	 * 年
	 */
	public static final String TIME_UNIT_YY = "YY";
	
//	动作类型
	/**
	 * 订购
	 */
	public static final String ACTION_TYPE_ORDER = "A";
	/**
	 * 退订
	 */
	public static final String ACTION_TYPE_UNSUB = "D";
	/**
	 * 两个都一样
	 */
	public static final String ACTION_TYPE_BOTH = "M";
	
//	用户类型
	/**
	 * 新用户
	 */
	public static final String USER_FLAG_NEW = "0";
	/**
	 * 老用户
	 */
	public static final String USER_FLAG_OLD = "1";
	/**
	 * 新老用户
	 */
	public static final String USER_FLAG_NEWOLD = "2";
	
	/**
	 * 销售品关系 选择目录
	 */
	public static final String REL_TYPE_CATALOG = "CATALOG";
	
	/**
	 * 销售品关系 选择销售品
	 */
	public static final String REL_TYPE_ITEM = "ITEM";
	
	
	/**
	 * 销售品构成角色
	 */
	public static final String OFFER_PROD_REL_ROLE_TYPE_10A = "10A";
	
	/**
	 * 可选包角色
	 */
	public static final String OFFER_PROD_REL_ROLE_TYPE_10C = "10C";
	
	
	
	/**
	 * 构成角色类型：基础
	 */
	public static final String ROLE_COMP_TYPE_10 = "10";
	
	
	/**
	 * 构成角色类型：加装
	 */
	public static final String ROLE_COMP_TYPE_20 = "20";
	
	/**
	 * 构成角色类型：增值、其他 => 基础增值
	 */
	public static final String ROLE_COMP_TYPE_30 = "30";
	
	/**
	 * 构成角色类型：加装增值
	 */
	public static final String ROLE_COMP_TYPE_31 = "31";
	
	/**
	 * 构成（销售品关系）角色类型：套餐角色
	 */
	public static final String ROLE_COMP_TYPE_80 = "80";
	/**
	 * 构成（销售品关系）角色类型：可选包角色
	 */
	public static final String ROLE_COMP_TYPE_81 = "81";
	/**
	 * 构成（销售品关系）角色类型：促销角色
	 */
	public static final String ROLE_COMP_TYPE_82 = "82";
	
	/**
	 * 构成（销售品关系）角色类型：其他角色
	 */
	public static final String ROLE_COMP_TYPE_83 = "83";
	
	/**
	 * 集团销售品角色：基础增值ROLE_CD
	 */
	public static final String ROLE_CD_BASE_ADD_VALUE = "10600000";
	
	/**
	 * 集团销售品角色：加装增值ROLE_CD
	 */
	public static final String ROLE_CD_INSTALL_ADD_VALUE = "20600000";
	
	
	/***************************** PPM一点配置涉及的各目录**********************************/
	// 销售品服务提供规则目录
	public final static String CATALOG_TYPE_SERVICE_OFFER_CAT = "12S";
	// 其它业务规则目录
	public final static String CATALOG_TYPE_OFFER_SERVICE_REL_RULE_CAT = "12R";
	
	// 销售品服务提供 - 销售业务规则目录 (added by cai.zhengluan@2013/9/12)
	// 根据销售品视图_V1.0-20130604最新集团规范 新增新疆省内配置能力目录
	public final static String CATALOG_TYPE_OFFER_SERVICE_OTHER_RULE_CAT = "12SK";
	
	public final static String CATALOG_TYPE_OFFER_RULE_CAT = "12O";
	// 销售品约束
	public final static String CATALOG_TYPE_OFFER_PROD_SERVICE_REL_CAT = "12Y";
	// 销售品规则分类
	//public final static String CATALOG_TYPE_SERVICE_OFFER_RULE_TYPE_CAT = "12O";
	
	// 12M: 销售品成员构成业务规则(接入类). 例如E9-189的固话不能是E家电话
	public final static String CATALOG_TYPE_OFFER_PROD_REL_RULE_TYPE_CAT = "12M";
	
	// 12P: 销售品成员构成的服务提供业务规则(接入类): 例如E-189的基础固话的订购业务 在协议期内不能办理
	public final static String CATALOG_TYPE_OFFER_PROD_REL_SERVICE_OFFER_RULE_TYPE_CAT = "12P";
	
	// 12Q: 销售品成员构成业务规则(功能类)：例如E-189的基础固话的个人彩铃　每月只能更换两次密码.
	public final static String CATALOG_TYPE_OFFER_FUNC_PROD_REL_RULE_TYPE_CAT = "12Q";
	//12U: 销售品成员服务约束业务规则
	public final static String CATALOG_TYPE_OFFER_PROD_SERV_REL_RULE_TYPE_CAT = "12U";
	
	//	12X: 佣金规则
	public final static String CATALOG_TYPE_OFFER_COMMISSION_RULE_TYPE_CAT = "12X";
	
	// 销售定价模式目录，例如x元每分钟...
	public final static String CATALOG_TYPE_INNER_BILL_MOTHED_CAT = "12Zb";
	
	// 销售定价模式目录，例如x元每分钟...
	public final static String CATALOG_TYPE_OUTTER_BILL_MOTHED_CAT = "10O";
	
	//提醒信息（提醒类别）
	public final static String CATALOG_TYPE_OUTTER_REMIND_COND_CAT = "REMIND_COND";
	
	//提醒信息（提醒类型）
	public final static String CATALOG_TYPE_OUTTER_REMIND_CALSSIC_CAT = "REMIND_CLASSIC";
	
	//提醒信息（提醒频率）
	public final static String CATALOG_TYPE_OUTTER_REMIND_FREQUENCY_CAT = "REMIND_FREQUENCY";
	
	//提醒信息规则目录
	public final static String CATALOG_TYPE_OUTTER_REMIND_RULE_CAT = "12Zf";
	
	
	
	/*****************************处理方式类型**********************************/
	// 销售品类规则
	public static String PROC_MODE_TYPE_OFFER_RULE = "001";
	// 销售品服务类规则
	public static String PROC_MODE_TYPE_OFFER_SERVICE_REL_RULE = "002";
	// 定价类能力-计费策略
	public static String PROC_MODE_TYPE_BILL_METHOD = "003";
	// 佣金计算规则
	public static String PROC_MODE_TYPE_YONG_JIN = "004";
	// 电信结算规则
	public static String PROC_MODE_TYPE_SETTLEMENT_RULE = "005";
	// 销售品可选择在服务提供， 例如目前是订购，退订，和变更
	public static String PROC_MODE_TYPE_SERVICE_OFFERS = "006";
	
	
	/*****************************处理方式用途**********************************/
	// 组合定价的子定价
	public static String USE_TYPE_SUB_PRICE = "001";
	// 组合定价/计费定价的套餐内资费
	public static String USE_TYPE_INNER_BILL_METHOD = "002";
	// 组合定价/计费定价的套餐外资费
	public static String USE_TYPE_OUTTER_BILL_METHOD = "003";
	// 销售品的定价信息
	public static String USE_TYPE_OFFER_PRICE = "004";
	// 销售品的套餐内组合定价信息
	public static String USE_TYPE_OFFER_INNTER_COMP_PRICE = "005";
	// 销售品的超出组合定价信息
	public static String USE_TYPE_OFFER_OUTTER_COMP_PRICE = "006";

	// 销售品的过渡期资费信息
	public static String USE_TYPE_OFFER_PRE_PRICE = "007";
	// 销售品的过渡期资费套餐内组合定价信息
	public static String USE_TYPE_OFFER_PRE_INNER_COMP_PRICE = "008";
	// 销售品的过渡期资费超出组合定价信息
	public static String USE_TYPE_OFFER_PRE_OUTTER_COMP_PRICE = "009";
	
	
	// 过渡期-组合定价/计费定价的套餐内资费
	public static String USE_TYPE_PRE_INNER_BILL_METHOD = "020";
	// 过渡期-组合定价/计费定价的套餐外资费
	public static String USE_TYPE_PRE_OUTTER_BILL_METHOD = "021";
	
	
	//套餐\可选包关键参数配置能力（套餐\可选包报备配置能力）
	public static String USE_TYPE_PACKAGE_PROVINCE_RULE_BILL_METHOD = "040";
	//促销关键参数配置能力（促销报备配置能力）
	public static String USE_TYPE_PROMOTION_PROVINCE_RULE_BILL_METHOD = "041";

	
	
	// 销售品的业务规则
	public static final String USE_TYPE_OFFER_SERVICE_REL_RULES = "010";
	

	// 销售品的规则
	public static String USE_TYPE_OFFER_RULE = "011";

	
	// 销售品成员服务提供业务规则
	public static String USE_TYPE_OFFER_PROD_SERV_RULE = "012";
	

	//013销售品成员构成业务规则
	public static String USE_TYPE_OFFER_PROD_REL_RULE = "013";
	//014销售品成员服务提供业务规则
	public static String USE_TYPE_OFFER_PROD_SERV_REL_RULES = "014";
	
	/***********促销优惠处理方式用途**************/
	//终端补贴
	public static final String USE_TYPE_TERMINAL = "015";
	public static final String PROC_MODE_ID_TERMINAL = "15";
	//终端补贴(首月返还)
	public static final String USE_TYPE_TERMINAL_FIRST = "030";
	public static final String PROC_MODE_ID_TERMINAL_FIRST = "20";

	//话费定额补贴
	public static final String USE_TYPE_QUOTASUBSIDY = "017";
	public static final String PROC_MODE_ID_QUOTASUBSIDY = "17";
	//话费定比补贴
	public static final String USE_TYPE_PERCENTSUBSIDY = "016";
	public static final String PROC_MODE_ID_PERCENTSUBSIDY = "16";

	//存费送费定额补贴
	public static final String USE_TYPE_CFSF_QUOTASUBSIDY = "031";
	public static final String PROC_MODE_ID_CFSF_QUOTASUBSIDY = "21";
	//存费送费定比补贴
	public static final String USE_TYPE_CFSF_PERCENTSUBSIDY = "032";
	public static final String PROC_MODE_ID_CFSF_PERCENTSUBSIDY = "22";

	//充值送费(定额)补贴
	public static final String USE_TYPE_QUOTA_DEPOSITFEE = "033";
	public static final String PROC_MODE_ID_QUOTA_DEPOSITFEE = "23";
	
	//自主融合
	public static final String USE_TYPE_INDEPENDENT_FUSION = "034";
	public static final String PROC_MODE_ID_INDEPENDENT_FUSION = "34";
	//赠送实物
	public static final String USE_TYPE_PRESENTED_OBJECTS = "035";
	public static final String PROC_MODE_ID_PRESENTED_OBJECTS = "35";
	
	//预存补贴
	public static final String USE_TYPE_PRECHUN = "018";
	public static final String PROC_MODE_ID_PRECHUN = "18";
	//其它补贴
	public static final String USE_TYPE_OTHERTIE = "019";
	public static final String PROC_MODE_ID_OTHERTIE = "19";
	
	//政企租机
	public static final String USE_TYPE_GOVERRENTMACHINE="042";
	public static final String PROC_MODE_ID_GOVERRENTMACHINE="42";
	
	//协议促销
	public static final String USE_TYPE_GOVERSALESPROMOTION="043";
	public static final String PROC_MODE_ID_GOVERSALESPROMOTION="43";
	
	// 新疆本地：预存款信息
	public static final String USE_TYPE_XJ_DEPOSIT="051";
	public static final String PROC_MODE_ID_XJ_DEPOSIT = "51";
	
	// 新疆本地：协议信息
	public static final String USE_TYPE_XJ_AGREEMENT="052";
	public static final String PROC_MODE_ID_XJ_ARGREEMENT = "52";
	
	/***********提醒规则处理方式用途**************/
	
	//022销售品提醒规则
	public static String USE_TYPE_OFFER_PROD_REMIND_RULES = "022";
	
	//023销售品提醒规则(提醒类别)
	public static String USE_TYPE_OFFER_PROD_REMIND_COND_RULES = "023";
	
	//024销售品提醒规则(提醒方式)
	public static String USE_TYPE_OFFER_PROD_REMIND_CLASSIC_RULES = "024";
	
	//025销售品提醒规则(提醒时间规则)
	public static String USE_TYPE_OFFER_PROD_REMIND_FREQUENCY_RULES = "025";
	
	//026销售品提醒规则配置能力
	public static String USE_TYPE_OFFER_PROD_REMIND_RULES_ABILITY = "026";
	
	
	/***********生效方式******************/
	/**
	 * 次月生效
	 */
	public static final String EFF_TYPE_NEXTMONTH = "01";
	/**
	 * 当月生效
	 */
	public static final String EFF_TYPE_CRTMONTH = "02";
	/**
	 * 约定时间生效
	 */
	public static final String EFF_TYPE_FIRMDATE = "03";
	/**
	 * 立即生效
	 */
	public static final String EFF_TYPE_IMMEDIATELY = "04";
	
	/***********协议确认方式******************/
	/**
	 * 纸质
	 */
	public static final String CONFIRM_WAY_PAPER = "01";
	/**
	 * 网站
	 */
	public static final String CONFIRM_WAY_WEB = "02";
	/**
	 * 短信
	 */
	public static final String CONFIRM_WAY_MSG = "03";
	/**
	 * 语音
	 */
	public static final String CONFIRM_WAY_VOICE = "04";
	
	/***********协议到期处理规则******************/
	/**
	 * 续订
	 */
	public static final String EXP_RULE_AUTOSUBSCRIBE = "0";
	/**
	 *再次协议续订
	 */
	public static final String EXP_RULE_RESUBSCRIBE = "1";
	/**
	 * 退订
	 */
	public static final String EXP_RULE_UNSUBSCRIBE = "2";
	/**
	 * 注销
	 */
	//public static final String EXP_RULE_CANCEL = "3";//PPM2.8集团0903规范 去掉自动注销
	
	/**********费用类型*************/
	/**
	 * 语音主叫
	 */
	public static final String FEE_TYPE_CALLING = "101000";//语音主叫
	public static final String FEE_TYPE_LOC_CALLING = "101100";//本地主叫
	public static final String FEE_TYPE_LOC_INNERNET_CALLING = "101110";//网内本地主叫
	public static final String FEE_TYPE_LOC_OUTNET_CALLING = "101120";//网外本地主叫
	public static final String FEE_TYPE_LONG_CALLING = "101200";//长途主叫
	public static final String FEE_TYPE_LONG_INNERNET_CALLING = "101210";//长途网内主叫
	public static final String FEE_TYPE_LONG_INNERNET_INNERPROV_CALLING = "101211";//长途网内省内主叫
	public static final String FEE_TYPE_LONG_INNERNET_BETWPROV_CALLING = "101212";//长途网内省间主叫
	public static final String FEE_TYPE_LONG_ORTNET_CALLING = "101220";//长途网外主叫
	public static final String FEE_TYPE_LONG_OUTNET_INNERPROV_CALLING = "101221";//长途网外省内主叫
	public static final String FEE_TYPE_LONG_OUTNET_BETWPROV_CALLING = "101222";//长途网外省间主叫
	public static final String FEE_TYPE_LONG_INNERPROV_CALLING = "101230";//省内长途主叫
	public static final String FEE_TYPE_LONG_BETWPROV_CALLING = "101231";//省间长途主叫
	public static final String FEE_TYPE_ROAM_CALLING = "101300";//漫游主叫
	public static final String FEE_TYPE_ROAM_INNERNET_CALLING = "101310";//漫游网内主叫
	public static final String FEE_TYPE_ROAM_INNERNET_INNERPROV_CALLING = "101311";//漫游网内省内主叫
	public static final String FEE_TYPE_ROAM_INNERNET_BETWPROV_CALLING = "101312";//漫游网内省间主叫
	public static final String FEE_TYPE_ROAM_OUTNET_CALLING = "101320";//漫游网外主叫
	public static final String FEE_TYPE_ROAM_OUTNET_OUTPROV_CALLING = "101321";//漫游网外省内主叫
	public static final String FEE_TYPE_ROAM_OUTNET_BETWPROV_CALLING = "101322";//漫游网外省间主叫
	public static final String FEE_TYPE_ROAM_INNERPROV_CALLING = "101330";//省内漫游主叫
	public static final String FEE_TYPE_ROAM_OUTPROV_CALLING = "101331";//省间漫游主叫
	
	
	/**
	 * 语音被叫
	 */
	public static final String FEE_TYPE_CALLED = "102000";//语音被叫
	public static final String FEE_TYPE_LOC_CALLED = "102100";//本地被叫
	public static final String FEE_TYPE_LOC_INNERNET_CALLED = "102110";//网内本地被叫
	public static final String FEE_TYPE_LOC_OUTNET_CALLED = "102120";//网外本地被叫
	public static final String FEE_TYPE_ROAM_CALLED = "102300";//漫游被叫
	public static final String FEE_TYPE_ROAM_INNERNET_CALLED = "102310";//漫游网内被叫
	public static final String FEE_TYPE_ROAM_INNERNET_INNERPROV_CALLED = "102311";//漫游网内省内被叫
	public static final String FEE_TYPE_ROAM_INNERNET_BETWPROV_CALLED = "102312";//漫游网内省间被叫
	public static final String FEE_TYPE_ROAM_OUTNET_CALLED = "102320";//漫游网外被叫
	public static final String FEE_TYPE_ROAM_OUTNET_INNERPROV_CALLED = "102321";//漫游网外省内被叫
	public static final String FEE_TYPE_ROAM_OUTNET_BETWPROV_CALLED = "102322";//漫游网外省间被叫
	public static final String FEE_TYPE_ROAM_INNERPROV_CALLED = "102330";//省内漫游被叫
	public static final String FEE_TYPE_ROAM_BETWPROV_CALLED = "102331";//省间漫游被叫
	/**
	 * CDMA数据国内
	 */
	public static final String FEE_TYPE_CDMA = "103000";
	/**
	 * CDMA数据省内
	 */
	public static final String FEE_TYPE_CDMA_PROVINCE = "104000";
	/**
	 * 短信使用费
	 */
	public static final String FEE_TYPE_SHORTMSG = "105000";
	/**
	 * 增值使用费
	 */
	public static final String FEE_TYPE_VALUEADDED = "106000";
	/**
	 * 月租费
	 */
	public static final String FEE_TYPE_RENT = "107000";
	/**
	 * 优惠
	 */
	public static final String FEE_TYPE_DISCOUNT = "108000";
	
	/**
	 * 一次性费用
	 */
	public static final String FEE_TYPE_ONE_TIME = "109000";



	
	/**********销售品类型*******************/
//	@FieldJudgeMeta(id = "OFF-0005", code = "DC_OFFER_KIND", name = "基础销售品", desc = "N1N2； 10～99的顺序数字")
//	public final static String PROD_OFFER_OFFER_TYPE_BASE = "10";//基础销售品
//	@FieldJudgeMeta(id = "OFF-0005", code = "DC_OFFER_KIND", name = "套餐销售品", desc = "N1N2； 10～99的顺序数字")
//	public final static String PROD_OFFER_OFFER_TYPE_PACK = "11";//套餐销售品
//	@FieldJudgeMeta(id = "OFF-0005", code = "DC_OFFER_KIND", name = "可选包", desc = "N1N2； 10～99的顺序数字")
//	public final static String PROD_OFFER_OFFER_TYPE_OPT_PACK = "12";//可选包
//	@FieldJudgeMeta(id = "OFF-0005", code = "DC_OFFER_KIND", name = "促销", desc = "N1N2； 10～99的顺序数字")
//	public final static String PROD_OFFER_OFFER_TYPE_PROMOTION = "13";//促销
//	@FieldJudgeMeta(id = "OFF-0005", code = "DC_OFFER_KIND", name = "政企协议", desc = "N1N2； 10～99的顺序数字")
//	public final static String PROD_OFFER_OFFER_TYPE_ARGEE = "14";//政企协议
	/**********销售品类型*******************/


	/***销售品产品关系类型**/
	public static final String ELEMENT_TYPE_ACC_PRODUCT = "10A";
	
	public static final String ELEMENT_TYPE_FUNC_PRODUCT = "10D";
	
	public static final String ELEMENT_TYPE_RES_PRODUCT = "10Z";
	
	
	/**********产品功能分类*******************/
//	@FieldJudgeMeta(id = "PRD-0004", code = "PRODUCT_PRODUCT_CLASSIFICATION", name = "接入类", desc = "采用两级3位编码N1N2 N3；分两级编码：第一级编码N1N2继承产品类型中原子产品编码；第二位编码N3，采用1～9的顺序数字。")
//	public final static String PROD_FUNC_TYPE_ACCESS = "101";//接入类产品
//	@FieldJudgeMeta(id = "PRD-0004", code = "PRODUCT_PRODUCT_CLASSIFICATION", name = "功能类", desc = "采用两级3位编码N1N2 N3；分两级编码：第一级编码N1N2继承产品类型中原子产品编码；第二位编码N3，采用1～9的顺序数字。")
//	public final static String PROD_FUNC_TYPE_FUNC = "102";//功能类产品
//	@FieldJudgeMeta(id = "PRD-0004", code = "PRODUCT_PRODUCT_CLASSIFICATION", name = "内容类", desc = "采用两级3位编码N1N2 N3；分两级编码：第一级编码N1N2继承产品类型中原子产品编码；第二位编码N3，采用1～9的顺序数字。")
//	public final static String PROD_FUNC_TYPE_CONTENT = "103";//内容类产品
//	@FieldJudgeMeta(id = "PRD-0004", code = "PRODUCT_PRODUCT_CLASSIFICATION", name = "资源类", desc = "采用两级3位编码N1N2 N3；分两级编码：第一级编码N1N2继承产品类型中原子产品编码；第二位编码N3，采用1～9的顺序数字。")
//	public final static String PROD_FUNC_TYPE_RESOURCE = "104";//资源类产品
	/**********产品功能分类*******************/


	
	
	
	/*******************************模板实例类型*****************************/
	//销售品申请模板实例
	public static String OFFERAPPLY_TEMPLATE_INST = "10C";  
	//销售品对象模板实例
	public static String PRODOFFER_TEMPLATE_INST = "12C";  
	//产品对象模板实例
	public static String PRODUCT_TEMPLATE_INST = "12A";
	//产品申请模板实例
	public static String PRODUCTAPPLY_TEMPLATE_INST = "10A";  
	public static String SUBPRODUCTAPPLY_TEMPLATE_INST = "10B";   //1.0模板需求,附属产品模板实例
	//目录申请模板实例
	public static String CATALOGAPPLY_TEMPLATE_INST = "10T";   
	//主数据申请模板实例
	public static String ZSJ_TEMPLATE_INST = "10K"; 
	
	//
	
	/***************************子模板类型*************************/
	//销售品关系
	public static String BUSI_TYPE_CProdOfferRel = "CProdOfferRel";
	
	//销售品功能产品
	public static String BUSI_TYPE_CFuncProd = "CFuncProd";

	//销售品可选包
	public static String BUSI_TYPE_COptionalPackage = "COptionalPackage";
	
	//销售品可选包角色
	public static String BUSI_TYPE_CPackageRole = "CPackageRole";
	
	//销售品基本信息
	public static String BUSI_TYPE_OfferBaseInfoList = "OfferBaseInfoList";
	
	//销售品客户群
	public static String BUSI_TYPE_OfferCustGroupList = "OfferCustGroupList";
	
	//接入品产品
	public static String BUSI_TYPE_CAccessProd = "CAccessProd";
	
	//产品成员基本销售品
	public static String BUSI_TYPE_OfferDetailBase ="COfferDetailBase";
	
	//销售品成员角色
	public static String BUSI_TYPE_COfferCompRole = "COfferCompRole";
	
	//销售品服务提供
	public static String BUSI_TYPE_CServiceOffer = "CServiceOffer";
	
	// 销售品对成员产品的属性的最大最小约束配置模板
	public static final String BUSI_TYPE_CMinMaxOfferProdAttrRel = "CMinMaxOfferProdAttrRel";
	
	// 销售品对成员产品的属性的列表值约束配置模板
	public static final String BUSI_TYPE_CListOfferProdAttrRel = "CListOfferProdAttrRel";
	
	//销售品佣金计算模版
	public static final String BUSI_TYPE_CCommissionInfo = "CCommissionInfo";

	//属性约束：最大最小值
	public static final String PRODOFFER_ATTR_MINMAXRELTYPE = "30";
	//属性约束：列表取值
	public static final String PRODOFFER_ATTR_LISTTRELYPE = "31";

	/**
	 * 综合类业务规则
	 */
	public static final String BUSI_TYPE_CComprehsvBusiRule = "CComprehsvBusiRule";
	
	/*
	 * 管理级别manage_grade
	 * 10	集团框架级
	 * 11	集团实例级
	 * 12	省级
	 * 13	本地网级
	 */
	public static final String GROUP_FRAME_MANAGE = "10";
	public static final String GROUP_INSTANCE_MANAGE = "11";
	public static final String PROVINCE_MANAGE = "12";
	public static final String LOCAL_MANAGE = "13";
	
	/*
	 * 管理级别对应的第9、10位的目录编码
	 * 10==00
	 * 11==01
	 * 12==43
	 * 13==43
	 */
	public static final String GROUP_FRAME_MANAGE_CODE = "00";
	public static final String GROUP_INSTANCE_MANAGE_CODE = "01";
	
	/*
	 * 管理部门offer_provider_id
	 * ATTR_VALUE,ATTR_VALUE_NAME
	 * 	5 政企客户部
	 * 	6 公众客户部
	 * 	7 市场部
	 * 	8 创新事业部
	 * 
	 */
	public static final String ENTERPRISE_DEPARTMENT = "5";
	public static final String PUBLIC_DEPARTMENT = "6";
	public static final String MARKET_DEPARTMENT = "7";
	public static final String INNOVATION_DEPARTMENT  = "8";
	
	/*
	 * 客户品牌brand_id
	 * ATTR_VALUE,ATTR_VALUE_NAME
	 *	1000,天翼领航
	 *	1100,天翼e家
	 *	1200,天翼
	 *	1300,政企非品牌
	 *	1400,家庭非品牌
	 *	1500,个人非品牌
	 *	1600,号码百事通
	 */
	public static final String BRAND_BNET = "1000";
	public static final String BRAND_EHOME= "1100";
	public static final String BRAND_ESURFING = "1200";
	public static final String BRAND_NONE_ZQ = "1300";
	public static final String BRAND_NONE_HOME = "1400";
	public static final String BRAND_NONE_PERSION = "1500";
	//public static final String BRAND_NUMBER = "1600";
	
	/*
	 * 归属类别belong_type
	 * ATTR_VALUE,ATTR_VALUE_NAME
	 *	0 品牌套餐
	 *	1 非品牌套餐
	 *	2 基础销售品
	 */
	public static final String BELONG_BRAND = "0";
	public static final String BELONG_NONEBRAND= "1";
	public static final String BELONG_BASIC = "2";
	
	/**
	 * 销售品分类offer_type
	 * ATTR_VALUE,ATTR_VALUE_NAME
	 * 10,基础销售品	
	 * 11,套餐销售品	135
	 * 12,可选包  ch1=7
	 * 13,促销      ch1=8
	 * 
	 */
	public static final String TYPE_BASIC = "10";
	public static final String TYPE_FIX_PACKAGE= "11";
	public static final String TYPE_OPTION_PACKAGE = "12";
	public static final String TYPE_PROMOTION = "13";
	
	
	//销售品收入分摊规则状态：有效
	public static final String OFFER_PROD_INCOMERULE_STATE_1000 = "1000";
	//销售品收入分摊规则状态：无效
	public static final String OFFER_PROD_INCOMERULE_STATE_1100 = "1100";

	//结算周期
	/**
	 * 月
	 */
	public static final String SETTLEMENT_CYCLE_MONTH = "100000";
	/**
	 * 季
	 */
	public static final String SETTLEMENT_CYCLE_QUARTER = "101000";
	/**
	 * 年
	 */
	public static final String SETTLEMENT_CYCLE_YEAR = "102000";
	
	/**********************流程模板**************************/
	/***
	 * 流程模板状态
	 */
    //状态:激活
    public static final String PROCESS_STATE_ACTIVE = "10A";
    //状态：锁定
    public static final String PROCESS_STATE_LOCK = "10B";
    //失效
    public static final String PROCESS_STATE_INACTIVE = "10C";
    //删除
    public static final String PROCESS_STATE_DELETED = "10X";
    
	/***
	 * 流程模板类型
	 */
	public static final String PROCESS_NORMAL="11";//普通流程
	
	public static final String PROCESS_URGENT="12";//紧急流程
	
	public static final String PROCESS_ORG="13";//集团流程
	/******************************************************/
	
	
	/***
	 * 需求单(if_request_order)申请类型
	 */
	public static final String REQUEST_ORDER_PRODUCT="10";//产品申请单
	
	public static final String REQUEST_ORDER_PRODOFFER ="11";//销售品申请单
	
	/**
	 * 接口需求单(if_request_order)状态
	 */
	public static final String IF_REQUEST_ORDER_READY = "1000";//接收中
	public static final String IF_REQUEST_ORDER_SUCCEED = "1001";//接收完成
	public static final String IF_REQUEST_ORDER_CANCEL = "1100";//作废
	public static final String IF_REQUEST_ORDER_HISTORY = "1200";//归档

	/**
	 * 接口需求单明细(if_request_order_detail)状态
	 */
	public static final String IF_REQUEST_ORDER_DETAIL_READY = "1000";//未接收
	public static final String IF_REQUEST_ORDER_DETAIL_SUCCEED = "1001";//已接收
	public static final String IF_REQUEST_ORDER_DETAIL_CANCEL = "1100";//作废
	public static final String IF_REQUEST_ORDER_DETAIL_HISTORY = "1200";//归档
	
	/**
	 * 动态模板直接显示
	 */
    public static final String TEMPLATE_DISPLAY_TYPE_NONE = "none";
    /**
     * 动态模板以PANEL的方式展示
     */
    public static final String TEMPLATE_DISPLAY_TYPE_PANEL = "panel";
    /**
     * 动态模板以tabbox的方式展示，tab水平方向展开
     */
    public static final String TEMPLATE_DISPLAY_TYPE_VERTICAL = "vertical";
    /**
     * 动态模板以tabbox的方式展示，tab竖直方向展开
     */
    public static final String TEMPLATE_DISPLAY_TYPE_HORIZONTAL = "horizontal";
    
    
    /**
     * 管理级别2(管理区域)
     */
    public static final String MANAGE_AREA_NATION = "100";//全国
    public static final String MANAGE_AREA_SOUTH = "101";//南方公司
    public static final String MANAGE_AREA_NORTH = "102";//北方公司
    public static final String MANAGE_AREA_ALL_XJ = "165";//全疆
    public static final String MANAGE_AREA_WLMQ = "991";//乌鲁木齐
    
    public static final String MANAGE_AREA_ALL_HN = "143";//湖南
    
    
    /**
     * 目录类型
     */
    public static final String CATALOG_TYPE_PRODUCT = "12A";//产品目录编码
    public static final String CATALOG_TYPE_PROD_OFFER = "12C";//销售品目录编码
    public static final String CATALOG_TYPE_PACKAGE_OFFER = "12E";//可选包目录编码
    public static final String CATALOG_TYPE_PROMOTION_OFFER = "12F";//促销目录编码
    public static final String CATALOG_TYPE_AGREEMENT = "12G";//政企协议目录编码
    public static final String CATALOG_TYPE_COMMON_PRODUCT = "12H";//普通产品目录编码
    public static final String CATALOG_TYPE_COMMON_OFFER = "12I";//普通销售品目录
    
	/*******************目录编码*************************/
    /**
     * 目录(catalog)标识：产品管理目录；
     */
    public static final String CATALOG_ID_PRODUCT = "100000";//[江西]产品管理目录ID
    /**
     *  目录(catalog)标识：销售品售卖目录；
     */
    public static final String CATALOG_ID_PRODOFFER_SALE = "200001";//[江西]销售品售卖目录

    /**
     * 目录关联（product_catalog_item_element）类型：产品
     */
    public static final String CATALOG_ITEM_ELEMENT_TYPE_PRODUCT = "10A";//产品类型
    /**
     * 目录关联（product_catalog_item_element）类型：销售品
     */
    public static final String CATALOG_ITEM_ELEMENT_TYPE_PRODOFFER = "10C";//销售品品类型
    
	/**
	 * 销售品属性(n_prod_offer_attr)有效状态
	 */
	public static final String $N_PROD_OFFER_ATTR_STATE_EFFECTIVE = "10";
	
	/**********************销售品扩展属性（N_PROD_OFFER_ATTR）************************/
    // PPM2.8规范 参考：全网套餐一点配置与查询分册20130409.doc
	public static final String EXT_PROD_OFFER_ATTR_$_SENSE_RECORD_START_TIME = "OFR10001";//通管局备案开始时间
	public static final String EXT_PROD_OFFER_ATTR_$_SENSE_RECORD_EFFECT_PERIOD = "OFR10002";//通管局备案有效期
	public static final String EXT_PROD_OFFER_ATTR_$_IS_CAMPUS_PROMOTOIN = "OFR10003";//是否校园促销
	public static final String EXT_PROD_OFFER_ATTR_$_VALUE_ADDED_TAX = "OFR10004";//增值税率
	//public static final String EXT_PROD_OFFER_ATTR_$_DISCOUNT_PERCENT = "OFR10005";//折扣比例
	public static final String EXT_PROD_OFFER_ATTR_$_BASICMONTHFEE="OFR10006";//月基本费
	public static final String EXT_PROD_OFFER_ATTR_$_PREFERENTIALLEVEL="20020037";//优惠级别
	public static final String EXT_PROD_OFFER_ATTR_$_SENSE_RECORD_END_TIME = "OFR100022";//通信局备案有效截止日期
	
	/**********************服务提供(SERVICE_OFFER)************************/
	public static final String SERVICE_OFFER_$_OFFERORD_NEWADD = "1";//订购, 销售品新购
	public static final String SERVICE_OFFER_$_OFFERORD_MODIFY = "2826";//修改销售品
	public static final String SERVICE_OFFER_$_OFFERORD_RETURN = "2567";//退订销售品，销售品解体
	public static final String SERVICE_OFFER_$_OFFERORD_CONTINUE= "4444";//续订销售品
	//public static final String SERVICE_OFFER_$_OFFERORD_CANCEL= "4445";//注销销售品
	
    /**********************定价资费类型(PRICING_TYPE)************************/
    /**
     * 套餐内资费
     */
    public static final String INNER_PRICING_TYPE = "10";
    
    /**
     * 套餐内资费
     */
    public static final String OUTTER_PRICING_TYPE = "20";
    
    /**********************关键参数配置能力（报备配置能力）************************/
    /**
     * 套餐\可选包报备配置能力
     */
    public static final String PACKAGE_PROC_MODE_CODE = "35XSPBB001";
    
    /**
     * 促销报备配置能力
     */
    public static final String PROMOTION_PROC_MODE_CODE = "35XSPBB002";
    
    
    /**
     * 接口处理队列表中的处理类型（DEAL_TYPE）
     */
    public static final String DEAL_TYPE_SOURCE = "001";//发送端
    public static final String DEAL_TYPE_TARGET = "002";//接收端
    
    /**
     * 接口处理队列表中的处理状态（DEAL_TYPE）
     */
    public static final String DEAL_QUEUE_STATE_UNDEAL = "1000";//未处理
    public static final String DEAL_QUEUE_STATE_HASDEAL = "1100";//已处理
    public static final String DEAL_QUEUE_STATE_FAILEDEAL = "1200";//处理失败
    
    
    
    /**
     * 申请单模板固定的字段对应的attr_spec.attr_code
     */
    public static String __REQUEST_INST_ITEM_ATTRCODE_NAME = "__REQUEST_INST_NAME";//申请单名称 对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_DEPT = "__REQUEST_INST_SBDW";//上报单位 对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_REQUESTDATE = "__REQUEST_INST_REQUEST_DATE";//申请时间 对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_COMPLETEDATE = "__REQUEST_INST_EXPE_FINISHED_TIME";//预计完成时间 对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_FINISHEDATE = "__REQUEST_INST_FINISHED_TIME";//完成时间 对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_DESC = "__REQUEST_INST_DESC";//需求单描述对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_CONT_NAME= "__REQUEST_INST_CONT_NAME";//联系人名称对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_CONT_TELE = "__REQUEST_INST_CONT_TELE";//联系人电话对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_ATTACHMENT = "__REQUEST_INST_ATTACHMENT";//相关附件对应的attr_spec的attr_code
	public static String __REQUEST_INST_ITEM_ATTRCODE_ATTACHMENT_DESC = "__REQUEST_INST_ATTACHMENT_DESC";//附件说明对应的attr_spec的attr_code
	
	
	public static String INTERFACE_CODE_PROD_OFFER_BP = "PROD_OFFER_BP";//销售品报批接口
    
}
