package com.ztesoft.consts;
/**
 * 商城静态值定义
 *
 * @author Reason.Yea
 * @version Created May 29, 2012
 */
public interface AcceptKey {

    public static final String SYSTEM_OPER_NO = "-1";
    public static final String SYSTEM_OPER_NAME = "system";

    public static final String ORD_OBJ_KEY = "__ORD_OBJ_";
    public static final String ORD_ITEM_KEY = "__ORD_ITEM_OBJ_";

    public static String STATE_1 = "1"; // 有效
    public static String STATE_0 = "0"; // 无效

    //订单处理编码
    public static String ORD_DEAL_SUCC = "0000"; // 成功，其它失败

    public static String ORD_DEAL_UNKOWN = "0001"; // 未知错误
    public static String ORD_DEAL_UNKOWN_MSG = "服务异常，请稍候再试！"; // 未知错误

    public static String ORD_DEAL_1000 = "1000"; // app_code为空
    public static String ORD_DEAL_1000_MSG = "opn_app_code不能为空！"; // app_code为空

    public static String ORD_DEAL_1001 = "1001"; // opn_code不存在
    public static String ORD_DEAL_1001_MSG = "app 公司信息不存在！"; // opn 公司信息不存在

    public static String ORD_DEAL_1002 = "1002"; // acc_nbr 不存在
    public static String ORD_DEAL_1002_MSG = "acc_nbr 不能为空";

    public static String ORD_DEAL_1003 = "1003"; //查询产品实例信息失败
    public static String ORD_DEAL_1003_MSG = "该号码信息失败！"; //查询产品实例信息失败


    public static String ORD_DEAL_1004 = "1004";
    public static String ORD_DEAL_1004_MSG = "对不起，订单项为空，请确认订单信息!";

    public static String ORD_DEAL_1005 = "1005";
    public static String ORD_DEAL_1005_MSG = "第三方订单编号不能为空!";


    public static String ORD_DEAL_1100 = "1100";
    public static String ORD_DEAL_1100_MSG = "plan_id 不能为空！";

    public static String ORD_DEAL_1101 = "1101";
    public static String ORD_DEAL_1101_MSG = "item_type 不能为空！";

    public static String ORD_DEAL_1102 = "1102";
    public static String ORD_DEAL_1102_MSG = "商品可订购数量为空或者超过最大可订购数量！";

    public static String ORD_DEAL_1103 = "1103";
    public static String ORD_DEAL_1103_MSG = "子定订单号不能为空！";

    public static String ORD_DEAL_1104 = "1104";
    public static String ORD_DEAL_1104_MSG = "支付额度超过最大额度限制！";

    public static String ORD_DEAL_1105 = "1105";
    public static String ORD_DEAL_1105_MSG = "此号码不能进行业务受理！";

    public static String ORD_DEAL_1106 = "1106";
    public static String ORD_DEAL_1106_MSG = "不能超出当天的最大支付限额！";

    public static String ORD_DEAL_1107 = "1107";
    public static String ORD_DEAL_1107_MSG = "plan_id无效";
    
    public static String ORD_DEAL_1108 = "1108";  //查询余额相关错误信息

    //短信验证错误码
    public static String ORD_SMS_0000="1000";//成功
    public static String ORD_SMS_2000="2000";//超时
    public static String ORD_SMS_2000_MSG="短信验证码超时!";//超时
    public static String ORD_SMS_2001="2001";//验证码输入错误!
    public static String ORD_SMS_2001_MSG="验证码输入错误!";//验证码输入错误!

    //短信发送
    public final static String SMS_SUCCESS = "0";//成功
    public final static String SMS_FAILED_1 = "1";
    public final static String SMS_FAILED_1_MSG = "接受号码不能为空！";
    public final static String SMS_FAILED_2 = "2";
    public final static String SMS_FAILED_2_MSG = "短信发送异常！";
    public final static String SMS_FAILED_3 = "3";
    public final static String SMS_FAILED_3_MSG = "短信内容不能为空！";
    public final static String SMS_FAILED_4 = "4";
    public final static String SMS_FAILED_4_MSG = "短信接受号码不是有效电信号码！";
    public final static String SMS_FAILED_5 = "5";
    public final static String SMS_FAILED_5_MSG = "目前仅支持验证码的发送！";
    public final static String SMS_FAILED_6 = "6";
    public final static String SMS_FAILED_6_MSG = "号码不是有效电信号码！";


    public final static String DEAL_SUCCESS = "0";
    public final static String DEAL_SUCCESS_MSG = "支付成功";
    public final static String DEAL_FAIL_1 = "1";
    public final static String DEAL_FAIL_1_MSG = "短信验证码不正确";
    public final static String DEAL_FAIL_2 = "2";
    public final static String DEAL_FAIL_2_MSG = "参数错误";
    public final static String DEAL_FAIL_EXCEP = "3";
    public final static String DEAL_FAIL_EXCEP_MSG = "支付异常,请联系10000号";
    //子订单顺序
    public final static String ITEM_INDEX = "ITEM_INDEX";


    public final static String CARD_INDEX_CODE = "card_index";

    public final static String ORDER_ITEM_TYPE = "ITEM_TYPE";
    //订单类型
    public final static String ORDER_TYPE_IM = "IM";//IM号码订购
    public final static String ORDER_TYPE_PACK = "PACK";//可选包订购
    public final static String ORDER_TYPE_PAY = "PAY";	//小额支付订购


    public static final String PAY_TYPE_A1 = "A1";// 立刻支付(支付金额不为0)
    public static final String PAY_TYPE_A2 = "A2";// 立刻支付(支付金额为0)
    public static final String PAY_TYPE_B = "B";// 分期支付
    public static final String PAY_TYPE_A = "A";// 在线支付

    public static final String PAY_TYPE_C = "C";// 无须支付(预定)

    public static final String PAY_TYPE_DESC_B = "分期付款";// 分期支付
    public static final String PAY_TYPE_DESC_A = "在线支付";// 在线支付


    // 订单状态
    public final static String STATE_001 = "001"; // 待付款
    public final static String STATE_002 = "002"; // 已支付
    public final static String STATE_003 = "003"; // 正在办理
    public final static String STATE_004 = "004"; // 待配送
    public final static String STATE_005 = "005"; // 正在发货
    public final static String STATE_006 = "006"; // 已发货
    public final static String STATE_007 = "007"; // 已发货(发票待补)
    public final static String STATE_008 = "008"; // 已失效款
    public final static String STATE_009 = "009"; // 处理失败
    public final static String STATE_010 = "010"; // 办结
//	public final static String STATE_011 = "011"; // 已退款

    public final static String STATE_001_NAME = "待付款";
    public final static String STATE_002_NAME = "已付款";
    public final static String STATE_003_NAME = "正在办理";
    public final static String STATE_004_NAME = "待配送";
    public final static String STATE_005_NAME = "正在发货";
    public final static String STATE_006_NAME = "已发货"; //
    public final static String STATE_007_NAME = "已发货（发票待补）"; //
    public final static String STATE_008_NAME = "已失效款";
    public final static String STATE_009_NAME = "处理失败";
    public final static String STATE_010_NAME = "办结";
//	public final static String STATE_011_NAME = "已退款";


    // 子订单状态
    public final static String ITEM_STATE_001 = "001"; // 待处理
    public final static String ITEM_STATE_002 = "002"; // 已处理
    public final static String ITEM_STATE_003 = "003"; // 退货申请
    public final static String ITEM_STATE_004 = "004"; // 退货物流处理
    public final static String ITEM_STATE_005 = "005"; // 退货订单处理
    public final static String ITEM_STATE_006 = "006"; // 换货申请
    public final static String ITEM_STATE_007 = "007"; // 换货物流处理
    public final static String ITEM_STATE_008 = "008"; // 换货订单处理
    public final static String ITEM_STATE_009 = "009"; // 处理失败
    public final static String ITEM_STATE_010 = "010"; // 办结
//	public final static String ITEM_STATE_011 = "011"; // 已退款

    public final static String REL_STATE_001 = "00B"; // 换货关系
    public final static String REL_STATE_002 = "00A"; // 赠送关系

    public final static String ITEM_STATE_001_NAME = "待处理";
    public final static String ITEM_STATE_002_NAME = "已处理";
    public final static String ITEM_STATE_003_NAME = "退货已申请";
    public final static String ITEM_STATE_004_NAME = "退货物流已回退";
    public final static String ITEM_STATE_005_NAME = "退货订单已回退";
    public final static String ITEM_STATE_006_NAME = "换货申请";
    public final static String ITEM_STATE_007_NAME = "换货物流回退";
    public final static String ITEM_STATE_008_NAME = "换货订单回退";
    public final static String ITEM_STATE_009_NAME = "处理失败";
    public final static String ITEM_STATE_010_NAME = "处理成功";
//	public final static String ITEM_STATE_011_NAME = "已退款成功"; 

    public static final String INVOICE_STATE_A_NAME = "需要";
    public static final String INVOICE_STATE_B_NAME = "不需要";


    public static String ITEM_TYPE_A = "ADD"; // 新增
    public static String ITEM_TYPE_M = "MOD"; // 修改
    public static String ITEM_TYPE_D = "DEL"; // 删除


    public static String SEQU_0 = "0"; // 最新记录

    // 资源号码预占状态
    public static String STATE_ACC_0 = "0"; // 无效
    public static String STATE_ACC_1 = "1"; // 有效
    public static String STATE_ACC_2 = "2"; // 资源预占
    public static String STATE_ACC_3 = "3"; // 已经使用
    // 卡资源状态
    public static String STATE_CARD_0 = "0"; // 可用
    public static String STATE_CARD_1 = "1"; // 预占
    public static String STATE_CARD_2 = "2"; // 已用

    public static String FLOW_TYPE_ORD = "A1"; // 主订单
    public static String FLOW_TYPE_ITEM = "A2"; // 子订单

    public static String FLOW_OP_TYPE_O = "O"; // 流程变更
    public static String FLOW_OP_TYPE_P = "P"; // 字段变更
    
    
    /**
     * ott常量字段
     * */
    public final static String OTT_DEAL_FAIL_0 = "0";
    public final static String OTT_DEAL_FAIL_0_MSG = "订购成功";
    
    public final static String OTT_DEAL_FAIL_1 = "1";
    public final static String OTT_DEAL_FAIL_1_MSG = "参数错误";
    
    public final static String OTT_DEAL_FAIL_2 = "2";
    public final static String OTT_DEAL_FAIL_2_MSG = "接入商信息错误";
    
    public final static String OTT_DEAL_FAIL_3 = "3";
    public final static String OTT_DEAL_FAIL_3_MSG = "用户账户信息错误";
    
    public final static String OTT_DEAL_FAIL_4 = "4";
    public final static String OTT_DEAL_FAIL_4_MSG = "销售品实例错误";
    
    public final static String OTT_DEAL_FAIL_8 = "8";
    
    public final static String OTT_DEAL_FAIL_8_MSG = "订购异常,请联系10000号";
}