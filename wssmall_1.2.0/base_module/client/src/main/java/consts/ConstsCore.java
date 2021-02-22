package consts;


/**
 * 
 * @author wui 静态对象
 * 
 */
public class ConstsCore {
	public static final String ERROR_SUCC ="0";
	public static final String ERROR_FAIL ="-1";
	public static final String ERROR_BUSI_MID ="-2";
	public static final String RULE_ERROR_FAIL ="1";
	public static final String NULL_CART = "1";
	public static final String NOT_LOGIN = "999";
	
	
	public static final String SOURCE_FROM_TAOBAO = "00C"; // 淘宝
	public static final String SOURCE_FROM_FJ = "FJ"; // 福建
	public static final String SOURCE_FROM_WT = "WT"; // 网厅
	public static final String SOURCE_FROM_LLKJ_WT = "LLKJ_WT"; // 连连科技网厅
	public static final String SOURCE_FROM_LLKJ_AGENT = "LLKJ_AGENT"; // 连连科技代理商
	public static final String SOURCE_FROM_LLKJ_ZT = "LLKJ_ZT"; 	//连连科技代掌厅
	public static final String SOURCE_FROM_LLKJ_LBS = "LLKJ_LBS"; 	//连连科技LBS
	public static final String SOURCE_FROM_LLKJ_FT = "LLKJ_FT"; 	//连连科技FT
	public static final String SOURCE_FROM_LLKJ_IVR = "LLKJ_IVR"; 	//连连科技IVR
	public static final String SOURCE_FROM_ECS = "ECS"; 			// 广东联通ECS
	public static final String SOURCE_FROM_SHP = "SHP"; 			// CMS
	public static final String SOURCE_FROM_JSORD = "JS"; 			//江苏移动订单中心
	public static final String SOURCE_FROM_HB = "HB"; // 河北o2o
	
	
	
	public static final String RULE_TYPE_ACCEPT = "accept"; // 受理类规则
	public static final String RULE_TYPE_DELVERY = "delvery"; // 发货类规则delivery
	public static final String RULE_TYPE_CONFIRM = "confirm"; // 确认类规则
	public static final String RULE_TYPE_PAY = "pay"; // 支付类规则
	public static final String RULE_TYPE_USERSTAFF= "userstaff"; // 订单工号规则
	
	
	public static final String RULE_SUCC = "yes"; // 成功
	public static final String RULE_FAIL = "no"; // 失败
	
	
	public static final String ZTE_CLIENT_DUBBO = "dubbo"; // dubbo调用
	public static final String ZTE_CLIENT_REMOTE = "remote"; // 远程调用
	public static final String ZTE_CLIENT_HTTP = "http"; // http调用
	
	public static final String SERVICE_TYPE_SERVICE ="service";
	
	public static final String SERVICE_TYPE_IMPORT = "import";
	
	public static String ALL_MENU = "";
	
	public static String CACHE_SFROM_CACHE = "cache"; //缓存
	public static String CACHE_SFROM_DB = "db"; //数据库
	
	public static String CONSTS_YES = "yes"; //是
	public static String CONSTS_NO = "no"; //否
	
	
	public static String STORE_TYPE_DB = "db"; //是
	public static String STORE_TYPE_JAVA = "java"; //否
	
	public static String IS_DIRTY_YES = "yes"; //是
	
	public static String IS_DIRTY_NO = "no"; //否
	
	public static String IS_ASY_YES = "yes";
	public static String IS_ASY_NO = "no";
	
	public static String QUERY_SQL_CONSTS = "querySql"; 
	
	public static String DB_ACTION_INSERT = "insert";
	public static String DB_ACTION_UPDATE = "update";
	public static String DB_ACTION_DELETE = "del";
	
	
	public static String DYPARAM_PREFIX = "dyparam_";
	
	public static String FIELD_TYPE_INPUT = "input"; //文本框
	public static String FIELD_TYPE_SELECT = "select";// 选择框
	public static String FIELD_TYPE_TEXT = "text";//只读框
	public static String FIELD_TYPE_DATE = "date";//日期类型 
	public static String FIELD_TYPE_CHECKBOX = "checkbox";//复选框
	public static String FIELD_TYPE_RADIO = "radio";//单选框
	public static String FIELD_TYPE_TEXTAREA = "textarea";//复选框
	
	public static String FILED_STYLE_DISABLED ="disabled";
	
	public static String TRACE_ALL = "trace_all";//适用所有环节
	
	
	public static String IS_NULL_Y = "Y";//
	public static String IS_NULL_N = "N";//
	public static String IS_EDIT_Y = "Y";//
	
	public static String DEAL_STR = "deal";//属性需要进单独的处理器处理的字符

	public static String ATTR_ACTION_lOAD = "load";//读取
	public static String ATTR_ACTION_UPDATE = "update";//更新
	
	public static String ATTR_ACTION_LOAD_AND_UPDATE = "load_update";//更新

	public static String TRACE_R = "R";//订单标准化执行方案	 
	public static String TRACE_V = "V";//标准化	 
	public static String TRACE_A = "A";//预处理
	public static String TRACE_B = "B";//客户回访
	public static String TRACE_C = "C";//预拣货
	public static String TRACE_D = "D";//开户
	public static String TRACE_E = "E";//拣货出库
	public static String TRACE_F = "F";//质检稽核
	public static String TRACE_G = "G";//物品包装
	public static String TRACE_H = "H";//发货
	public static String TRACE_I = "I";//收货确认
	public static String TRACE_J = "J";//回单
	public static String TRACE_K = "K";//售后回访
	public static String TRACE_L = "L";//订单归档
	public static String TRACE_M = "M";//退款确认
	public static String TRACE_X = "X";//写卡
	public static String TRACE_Y = "Y";//业务办理
	public static String TRACE_T = "T";//退单处理
	
	public static final String NULL_VAL = "NULL_VAL";
	
	public static final String DC_DC_USER_TYPE ="DC_USER_TYPE";//用户类型dc_sql的dc_name
	public static final String DC_USER_ADMIN_TYPE ="DC_USER_ADMIN_TYPE";//工号类型dc_sql的dc_name
	public static final String PARTNER_USER_ROLE = "partnerUserRole"; //微店分销商在后台的角色编码
	public static final String ADMIN_USER_ID = "1";//超级管理员的userId
	
	public static final String DUBBO_DEFAULT_VERSION = "1.0";
	
	public static final String MACHINE_EVN_CODE_ECSORD = "ecsord";
	
	public static final String MACHINE_EVN_CODE_ECSORD_EXP = "ecsord_exp";
	
	public static final String MACHINE_EVN_CODE_ECSORD_STD = "ecsord_std";
	
	public static final String DECOUPLING_EXEC_D = "d";
	
	public static final String DECOUPLING_EXEC_M = "m";
	
	/**
	 * 异步查询记录总数的标记
	 */
	public static final long PAGE_COUNT_ASY_FLAG = -6122202109863606039L;
	
}