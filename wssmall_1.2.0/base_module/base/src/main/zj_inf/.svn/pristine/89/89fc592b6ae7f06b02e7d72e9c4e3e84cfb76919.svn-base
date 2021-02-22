package zte.net.ecsord.common;


import java.util.HashMap;
import java.util.Map;


public class LocalCrawlerUtil {
	public static final String ZB_PROVINCE_CODE="36";
	public static final String ZB_DEFAULT_ACCOUNT_CODE="ZJ0003";
	public static final String ZB_DEFAULT_ACCOUNT_PWD="Hlw2016";
	public static final String ZB_DEFAULT_ACCOUNT_ID="41242686";
	public static final String ZB_DEFAULT_ACCOUNT_NAME="智能订单处理-浙江";
	public static final String PROVINCE_CODE = "330000";
	public static final String PROVINCE_NAME = "浙江省";
	public static final String LOCAL_MALL_CHANNEL_MARK = "13";
	public static final String RECEIVE_SYSTEM = "10011";
	//订单来源数据字典映射
	public static final Map<String,String> ORDERSRC = new HashMap<String, String>(9);
	//城市编码
	public static final Map<String,String> AREAID = new HashMap<String, String>(12);
	//城市编码反转
	public static final Map<String,String> AREAIDBACK = new HashMap<String, String>(12);
	//支付类型
	public static final Map<String,String> PAYTYPE = new HashMap<String, String>(2);
	//支付类型反转
	public static final Map<String,String> PAYTYPEBACK = new HashMap<String, String>(2);
	//支付状态
	public static final Map<String, String> PAYSTATUS = new HashMap<String, String>(2);
	//配送方式
	public static final Map<String, String> DELIVERYTYPE = new HashMap<String, String>(2);
	//订单类型
	public static final Map<String, String> ORDERTYPE = new HashMap<String, String>(19);
	//卡类型
	public static final Map<String, String> CARDTYPE = new HashMap<String, String>(3);
	//卡类型反转
	public static final Map<String, String> CARDTYPEBACK = new HashMap<String, String>(3);
	//活动类型
	public static final Map<String, String> ACTIVITYTYPE = new HashMap<String, String>(7);
	//证件类型
	public static final Map<String, String> CUSTCARDTYPE = new HashMap<String, String>(25);
	//证件类型反转
	public static final Map<String, String> CUSTCARDTYPEBACK = new HashMap<String, String>(25);
	//支付方式
	public static final Map<String,String> PAYWAY = new HashMap<String, String>(9);
	//配送时间类型 
	public static final Map<String, String> DELIVERYTIMETYPE = new HashMap<String, String>(2);
	//首月资费方式
	public static final HashMap<String, String> FIRST_FEE_TYPE = new HashMap<String, String>();
	//入网类型
	public static final HashMap<String, String> USER_TYPE = new HashMap<String, String>();
	//接入类型 
	public static final HashMap<String, String> ACC_TYPE = new HashMap<String, String>();
	//支付机构 
	public static final HashMap<String, String> PAY_PROVIDER = new HashMap<String, String>();
	//支付渠道
	public static final HashMap<String, String> PAY_CHANNEL = new HashMap<String, String>();
	//合约类型
	public static final HashMap<String, String> ACTIVITY_TYPE = new HashMap<String, String>();
	//订单状态-订单系统
	public static final HashMap<Integer, String> ORDER_STATUS = new HashMap<Integer, String>();
	//订单状态-总商
	public static final HashMap<String, Integer> ORDER_STATUS_ZB = new HashMap<String, Integer>();
	//环节规则映射
	public static final HashMap<Integer, String> STATUS_RULE_MAP = new HashMap<Integer, String>();
	//退单退款方案
	public static final HashMap<Integer, String> ORDER_PLAN = new HashMap<Integer, String>();
	static{
		ORDER_STATUS.put(1, "订单审核");
		ORDER_STATUS.put(2, "分配处理");
		ORDER_STATUS.put(3, "外呼确认");
		ORDER_STATUS.put(4, "开户处理");
		ORDER_STATUS.put(5, "发货处理");
		ORDER_STATUS.put(6, "归档操作");
		ORDER_STATUS.put(7, "归档完成");		
		ORDER_STATUS.put(8, "申请退单");
		ORDER_STATUS.put(9, "退款申请");
		ORDER_STATUS.put(10, "退单通过");
		ORDER_STATUS.put(11, "退款通过");
		

		ORDER_STATUS_ZB.put("订单审核", 1);
		ORDER_STATUS_ZB.put("分配处理", 2);
		ORDER_STATUS_ZB.put("外呼确认", 3);
		ORDER_STATUS_ZB.put("开户处理", 4);
		ORDER_STATUS_ZB.put("发货处理", 5);
		ORDER_STATUS_ZB.put("归档操作", 6);
		ORDER_STATUS_ZB.put("归档完成", 7);		
		ORDER_STATUS_ZB.put("申请退单", 8);
		ORDER_STATUS_ZB.put("退款申请", 9);
		ORDER_STATUS_ZB.put("退单通过", 10);
		ORDER_STATUS_ZB.put("退款通过", 11);
		
		STATUS_RULE_MAP.put(1, "170061652000000385");//审核
		STATUS_RULE_MAP.put(2, "170061732550000998");//分配
		STATUS_RULE_MAP.put(3, "170061733500001007");//外呼
		STATUS_RULE_MAP.put(4, "170061702270000557");//开户
		STATUS_RULE_MAP.put(5, "170061719110000767");//发货
		STATUS_RULE_MAP.put(6, "170061726490000889");//归档		
		STATUS_RULE_MAP.put(8, "170121735110000028");//申请退单
		STATUS_RULE_MAP.put(9, "170301033400000032");//退款申请
		STATUS_RULE_MAP.put(10, "170121737230000047");//退单确认
		STATUS_RULE_MAP.put(11, "170301034150000041");//退款确认
		
		
		ORDER_PLAN.put(8, "170121727080000108");//退单方案
		ORDER_PLAN.put(9, "170300956550000073");//退款方案
		ORDER_PLAN.put(10, "170121727080000108");//退单方案
		ORDER_PLAN.put(11, "170300956550000073");//退款方案
		
		
		//订单来源数据字典映射     key 订单中心编码 value 为订单中心中文名称
		ORDERSRC.put("10001","淘宝商城/淘宝网厅");//淘宝商城/淘宝网厅
		ORDERSRC.put("10005","拍拍");//拍拍
		ORDERSRC.put("10002","联通商城");//联通商城
		ORDERSRC.put("10035","电子沃店");//电子沃店
		ORDERSRC.put("10063","微信接口");//微信接口
		ORDERSRC.put("10064","手机商城");//手机商城
		ORDERSRC.put("10065","推广联盟");//推广联盟
		ORDERSRC.put("10066","挑号网");//挑号网
		ORDERSRC.put("10067","外围接入");//外围接入
		ORDERSRC.put("中国联通商城", "EMALL");
		ORDERSRC.put("网上商城", "EMALL");
		ORDERSRC.put("联通商城", "EMALL");
		ORDERSRC.put("中国联通手机商城", "MOBILE");
		ORDERSRC.put("手机商城", "MOBILE");
		ORDERSRC.put("电子沃店", "CCS");
		ORDERSRC.put("华盛O2O", "O2O");
		ORDERSRC.put("中国联通天猫店", "TMALL");
		ORDERSRC.put("天猫店", "TMALL");
		ORDERSRC.put("淘宝", "TAOBAO");
		ORDERSRC.put("淘宝商城", "TAOBAO");
		ORDERSRC.put("淘宝网厅", "TAOBAO");
		ORDERSRC.put("注销系统", "SSHS");
		ORDERSRC.put("当当商城", "DANGDANG");
		ORDERSRC.put("拍拍商城", "PAIPAI");
		ORDERSRC.put("拍拍", "PAIPAI");
		ORDERSRC.put("营业厅微厅", "BOMO");
		ORDERSRC.put("英文商城", "ENG");
		ORDERSRC.put("英文手机商城", "MENG");
		
		//城市编码数据字典     key 地市编码 value 地市名称
		AREAID.put("330100", "杭州市");
		AREAID.put("330500", "湖州市");
		AREAID.put("330400", "嘉兴市");
		AREAID.put("330700", "金华市");
		AREAID.put("331100", "丽水市");
		AREAID.put("330200", "宁波市");	
		AREAID.put("330600", "绍兴市");
		AREAID.put("331000", "台州市");
		AREAID.put("330300", "温州市");
		AREAID.put("330900", "舟山市");
		AREAID.put("330800", "衢州市");
		AREAID.put("330000", "浙江");
		//支付类型 key 订单中心编码  value 订单中心中文名称
		PAYTYPE.put("ZXZF","在线支付");//在线支付
		PAYTYPE.put("HDFK","货到付款");//货到付款
		//支付状态 key 订单中心编码 value 订单中心中文名称
		PAYSTATUS.put("0","未支付");//未支付
		PAYSTATUS.put("1","已支付");//已支付
		//订单类型 key 订单中心编码 value 订单中心中文名称
		ORDERTYPE.put("Z0","合约购机");//合约购机
		ORDERTYPE.put("Z1","套餐3G手机卡");//套餐3G手机卡
		ORDERTYPE.put("Z8","3G无线上网卡");//3G无线上网卡
		ORDERTYPE.put("Z23","流量充值卡");//流量充值卡
		ORDERTYPE.put("Z24","话费充值");//话费充值
		ORDERTYPE.put("Z25","天猫券");//天猫券
		ORDERTYPE.put("Z19","余额宝购机");//余额宝购机
		ORDERTYPE.put("Z21","全国预付费上网卡");//全国预付费上网卡
		ORDERTYPE.put("Z22","省份预付费上网卡");//省份预付费上网卡
		ORDERTYPE.put("Z26","配件");//配件
		ORDERTYPE.put("Z5","2G号码");//2G号码
		ORDERTYPE.put("Z15","3G预付费号码");//3G预付费号码
		ORDERTYPE.put("Z16","3G后付费号码");//3G后付费号码
		ORDERTYPE.put("Z11","预付费合约购机");//预付费合约购机
		ORDERTYPE.put("Z12","后付费合约购机");//后付费合约购机
		ORDERTYPE.put("Z27","裸机");//裸机
		ORDERTYPE.put("Z13","非定制机合约购机");//非定制机合约购机
		ORDERTYPE.put("Z6","4G号码");//4G号码
		ORDERTYPE.put("Z28","存量用户活动受理订单");//存量用户活动受理订单
		//卡类型 key 订单中心编码  value 订单中心中文名称
		CARDTYPE.put("NM","普卡");//普卡
		CARDTYPE.put("MC","微卡");//微卡
		CARDTYPE.put("NN","纳诺卡");//纳诺卡
		//活动类型 key 订单中心编码 value 订单中心中文名称
		ACTIVITYTYPE.put("690301000","购手机入网送话费");//购手机入网送话费
		ACTIVITYTYPE.put("690302000","预存话费送手机");//预存话费送手机
		ACTIVITYTYPE.put("690303000","存费送费");//存费送费
		ACTIVITYTYPE.put("690310000","存费送短信");//存费送短信
		ACTIVITYTYPE.put("690306000","存费送流量");//存费送流量
		ACTIVITYTYPE.put("690310000","存费送语音");//存费送语音
		//ACTIVITYTYPE.put("999", "");//其他
		//证件类型 key 订单中心编码 value 订单中心中文名称
		CUSTCARDTYPE.put("SFZ15","15位身份证");//15位身份证
		CUSTCARDTYPE.put("SFZ18","18位身份证");//18位身份证
		CUSTCARDTYPE.put("HZB","护照");//护照
		CUSTCARDTYPE.put("HKB","户口本");//户口本
		CUSTCARDTYPE.put("JUZ","军官证");//军官证
		CUSTCARDTYPE.put("JGZ","警官证");//警官证
		CUSTCARDTYPE.put("GOT","港澳居民来往内地通行证");//港澳居民来往内地通行证
		CUSTCARDTYPE.put("TWT","台湾居民来往大陆通行证");//台湾居民来往大陆通行证
		CUSTCARDTYPE.put("JSZ","驾驶证");//驾驶证
		CUSTCARDTYPE.put("GZZ","工作证");//工作证
		CUSTCARDTYPE.put("XSZ","学生证");//学生证
		CUSTCARDTYPE.put("ZZZ","暂住证");//暂住证
		CUSTCARDTYPE.put("JSX","单位介绍信");//单位介绍信
		CUSTCARDTYPE.put("YYZ","营业执照");//营业执照
		CUSTCARDTYPE.put("QT","其它");//其它
		CUSTCARDTYPE.put("XWQY","小微企业客户证件");//小微企业客户证件
		CUSTCARDTYPE.put("JTBH","集团编号");//集团编号
		CUSTCARDTYPE.put("GSDJ","工商注册登记证");//工商注册登记证
		CUSTCARDTYPE.put("ZZJG","组织机构代码证");//组织机构代码证
		CUSTCARDTYPE.put("YKH","预开户编号");//预开户编号
		CUSTCARDTYPE.put("WJSFZ","武警身份证");//武警身份证
		CUSTCARDTYPE.put("SYDW","事业单位法人代表证");//事业单位法人代表证
		CUSTCARDTYPE.put("ZH","照会");//照会
		CUSTCARDTYPE.put("SHTT","社会团体法人证书");//社会团体法人证书
		CUSTCARDTYPE.put("DWS","待完善资料");//待完善资料
		
		
		//城市编码数据字典     key 地市名称 value 地市编码
		AREAIDBACK.put("杭州市", "330100");
		AREAIDBACK.put("湖州市", "330500");
		AREAIDBACK.put("嘉兴市", "330400");
		AREAIDBACK.put("金华市", "330700");
		AREAIDBACK.put("丽水市", "331100");
		AREAIDBACK.put("宁波市", "330200");	
		AREAIDBACK.put("绍兴市", "330600");
		AREAIDBACK.put("台州市", "331000");
		AREAIDBACK.put("温州市", "330300");
		AREAIDBACK.put("舟山市", "330900");
		AREAIDBACK.put("衢州市", "330800");
		AREAIDBACK.put("杭州", "330100");
		AREAIDBACK.put("湖州", "330500");
		AREAIDBACK.put("嘉兴", "330400");
		AREAIDBACK.put("金华", "330700");
		AREAIDBACK.put("丽水", "331100");
		AREAIDBACK.put("宁波", "330200");	
		AREAIDBACK.put("绍兴", "330600");
		AREAIDBACK.put("台州", "331000");
		AREAIDBACK.put("温州", "330300");
		AREAIDBACK.put("舟山", "330900");
		AREAIDBACK.put("衢州", "330800");
		AREAIDBACK.put("浙江", "330000");
		
		//证件类型 key 订单中心中文名称 value 订单中心编码
		CUSTCARDTYPEBACK.put("15位身份证","SFZ15");//15位身份证
		CUSTCARDTYPEBACK.put("18位身份证","SFZ18");//18位身份证
		CUSTCARDTYPEBACK.put("护照","HZB");//护照
		CUSTCARDTYPEBACK.put("户口本","HKB");//户口本
		CUSTCARDTYPEBACK.put("军官证","JUZ");//军官证
		CUSTCARDTYPEBACK.put("警官证","JGZ");//警官证
		CUSTCARDTYPEBACK.put("港澳居民来往内地通行证","GOT");//港澳居民来往内地通行证
		CUSTCARDTYPEBACK.put("台湾居民来往大陆通行证","TWT");//台湾居民来往大陆通行证
		CUSTCARDTYPEBACK.put("驾驶证","JSZ");//驾驶证
		CUSTCARDTYPEBACK.put("工作证","GZZ");//工作证
		CUSTCARDTYPEBACK.put("学生证","XSZ");//学生证
		CUSTCARDTYPEBACK.put("暂住证","ZZZ");//暂住证
		CUSTCARDTYPEBACK.put("单位介绍信","JSX");//单位介绍信
		CUSTCARDTYPEBACK.put("营业执照","YYZ");//营业执照
		CUSTCARDTYPEBACK.put("其它","QT");//其它
		CUSTCARDTYPEBACK.put("小微企业客户证件","XWQY");//小微企业客户证件
		CUSTCARDTYPEBACK.put("集团编号","JTBH");//集团编号
		CUSTCARDTYPEBACK.put("工商注册登记证","GSDJ");//工商注册登记证
		CUSTCARDTYPEBACK.put("组织机构代码证","ZZJG");//组织机构代码证
		CUSTCARDTYPEBACK.put("预开户编号","YKH");//预开户编号
		CUSTCARDTYPEBACK.put("武警身份证","WJSFZ");//武警身份证
		CUSTCARDTYPEBACK.put("事业单位法人代表证","SYDW");//事业单位法人代表证
		CUSTCARDTYPEBACK.put("照会","ZH");//照会
		CUSTCARDTYPEBACK.put("社会团体法人证书","SHTT");//社会团体法人证书
		CUSTCARDTYPEBACK.put("待完善资料","DWS");//待完善资料
		
		PAYTYPEBACK.put("在线支付","ZXZF");//在线支付
		PAYTYPEBACK.put("货到付款","HDFK");//货到付款
		PAYTYPEBACK.put("担保免支付", "DBZF");//担保免支付
		PAYTYPEBACK.put("线下现场支付", "XCZF");//线下现场支付
		PAYTYPEBACK.put("积分支付", "JFZF");//积分支付
		PAYTYPEBACK.put("代理商免支付", "DLZF");
		
		PAYWAY.put("支付宝", "ZFB");
		PAYWAY.put("全额支付", "QEZF");
		PAYWAY.put("分期付款", "FQFK");
		PAYWAY.put("现金支付", "XJZF");
		PAYWAY.put("POS机刷卡", "POSC");
		PAYWAY.put("信用卡分期付款", "XYFQ");
		PAYWAY.put("支票支付", "ZPZF");
		PAYWAY.put("建行信用卡分期付款", "JXFQ");
		PAYWAY.put("招行信用卡分期付款", "ZXFQ");
		PAYWAY.put("中国银行信用卡分期", "ZGFQ");
		PAYWAY.put("银联预付卡", "YLYF");
		PAYWAY.put("资和信预付费卡", "ZHXC");
		PAYWAY.put("易事通商卡", "YSTS");
		PAYWAY.put("全额担保", "QEDB");
		PAYWAY.put("部分担保", "BFDB");
		PAYWAY.put("资金归集", "ZJGJ");
		PAYWAY.put("资金批扣", "ZJPK");
		
		//配送方式 key 订单中心中文名称 value 订单中心编码
		DELIVERYTYPE.put("快递","KD");//快递
		DELIVERYTYPE.put("自提","ZT");//自提
		DELIVERYTYPE.put("送货","SH");
		DELIVERYTYPE.put("闪电送","SDS");
		DELIVERYTYPE.put("平邮","PY");
		DELIVERYTYPE.put("EMS","EMS");
		DELIVERYTYPE.put("自有配送","ZYPS");
		DELIVERYTYPE.put("卖家包邮","MJBY");
		DELIVERYTYPE.put("虚拟发货","XNFH");
		DELIVERYTYPE.put("现场提货","XCTH");
		DELIVERYTYPE.put("就近配送","JJPS");
		DELIVERYTYPE.put("小米配送","XMPS");
		DELIVERYTYPE.put("无需配送","NO");
		DELIVERYTYPE.put("不需要配送","NO");
		DELIVERYTYPE.put("无配送","NO");
		
		DELIVERYTIMETYPE.put("不限时间送货", "NOLIMIT");
		DELIVERYTIMETYPE.put("双休日、节假日送货", "HOLIDAY");
		DELIVERYTIMETYPE.put("只双休日送货", "HOLIDAY");
		DELIVERYTIMETYPE.put("只节假日送货", "HOLIDAY");
		DELIVERYTIMETYPE.put("只有双休日、节假日送货", "HOLIDAY");
		DELIVERYTIMETYPE.put("工作日，周一到周五工作时间", "WORKDAY");
		DELIVERYTIMETYPE.put("只工作日送货", "WORKDAY");
		DELIVERYTIMETYPE.put("周一到周五工作时间", "WORKDAY");
		
		FIRST_FEE_TYPE.put("全月套餐", "ALLM");
		FIRST_FEE_TYPE.put("套餐减半", "HALF");
		FIRST_FEE_TYPE.put("套餐包外资费", "COMM");
		
		USER_TYPE.put("新用户", "NEW");
		USER_TYPE.put("老用户", "OLD");
		USER_TYPE.put("1", "NEW");
		USER_TYPE.put("2", "OLD");
		
		//卡类型 key 订单中心中文名称  value 订单中心编码
		CARDTYPEBACK.put("普卡","NM");//普卡
		CARDTYPEBACK.put("微卡","MC");//微卡
		CARDTYPEBACK.put("纳诺卡","NN");//纳诺卡
		CARDTYPEBACK.put("nano卡","NN");
		CARDTYPEBACK.put("三合一卡","NM");
		
		//订单接入类型
		ACC_TYPE.put("自然订单", "SELF");
		ACC_TYPE.put("推广联盟", "LEAGUE");
		ACC_TYPE.put("码上购", "O2M");
		ACC_TYPE.put("码上购推广", "O2M");
		ACC_TYPE.put("空中入网", "AIR");
		ACC_TYPE.put("沃联盟", "WLM");
		ACC_TYPE.put("电子沃店", "WOMA");
		ACC_TYPE.put("亿起发", "EMAR");
		ACC_TYPE.put("淘宝", "SELF");
		ACC_TYPE.put("注销系统", "SELF");
		ACC_TYPE.put("网上商城订单", "ENG");
		ACC_TYPE.put("手机商城订单", "MENG");
		
		PAY_PROVIDER.put("易宝", "01");
		PAY_PROVIDER.put("银联（总部MINI）", "02");
		PAY_PROVIDER.put("银联", "02");
		PAY_PROVIDER.put("支付宝", "03");
		PAY_PROVIDER.put("环迅", "04");
		PAY_PROVIDER.put("财付通", "05");
		PAY_PROVIDER.put("现金", "06");
		PAY_PROVIDER.put("现金（自助终端）", "06");
		PAY_PROVIDER.put("银联（吉林MINI）", "08");
		PAY_PROVIDER.put("银联（自助终端）", "09");
		PAY_PROVIDER.put("第三方直充（成就信飞预存）", "10");
		PAY_PROVIDER.put("第三方直充", "10");
		PAY_PROVIDER.put("易宝直充", "11");
		PAY_PROVIDER.put("MINIPOS", "12");
		PAY_PROVIDER.put("招行", "13");
		PAY_PROVIDER.put("沃易付（快捷交费）", "14");
		PAY_PROVIDER.put("沃易付", "14");
		PAY_PROVIDER.put("沃易付网银网关", "15");
		PAY_PROVIDER.put("快钱", "18");
		PAY_PROVIDER.put("银联(快捷支付)", "21");
		PAY_PROVIDER.put("百度理财", "100");
		PAY_PROVIDER.put("沃财富", "101");
		
		PAY_CHANNEL.put("中国银行企业版", "BOC-B2B");
		PAY_CHANNEL.put("浦发银行企业版", "SPDB-B2B");
		PAY_CHANNEL.put("中国工商银行", "ICBC");
		PAY_CHANNEL.put("中国农业银行", "ABC");
		PAY_CHANNEL.put("招商银行", "CMBCHINA");
		PAY_CHANNEL.put("建设银行", "CCB");
		PAY_CHANNEL.put("中国银行", "BOC");
		PAY_CHANNEL.put("交通银行", "BOCO");
		PAY_CHANNEL.put("兴业银行", "CIB");
		PAY_CHANNEL.put("光大银行", "CEB");
		PAY_CHANNEL.put("易宝自有帐户1", "YeePay01");
		PAY_CHANNEL.put("易宝自有帐户2", "YeePay02");
		PAY_CHANNEL.put("易宝自有帐户3", "YeePay03");
		PAY_CHANNEL.put("上海浦东发展银行", "SPDB");
		PAY_CHANNEL.put("支付宝自有帐户1", "AliPay01");
		PAY_CHANNEL.put("支付宝自有帐户2", "AliPay02");
		PAY_CHANNEL.put("支付宝自有帐户3", "AliPay03");
		PAY_CHANNEL.put("银联自有帐户1", "UnionPay01");
		PAY_CHANNEL.put("银联自有帐户2", "UnionPay02");
		PAY_CHANNEL.put("银联自有帐户3", "UnionPay03");
		PAY_CHANNEL.put("华夏银行", "HXB");
		PAY_CHANNEL.put("中国民生银行", "CMBC");
		PAY_CHANNEL.put("中信实业银行", "ECITIC");
		PAY_CHANNEL.put("中国邮政", "POST");
		PAY_CHANNEL.put("平安银行", "PAB");
		PAY_CHANNEL.put("广东发展银行", "GDB");
		PAY_CHANNEL.put("上海农村商业银行", "SHRCB");
		PAY_CHANNEL.put("顺德农村信用社", "SDE");
		PAY_CHANNEL.put("广州农村信用社", "GNXS");
		PAY_CHANNEL.put("广州市商业银行", "GZCB");
		PAY_CHANNEL.put("浙商银行", "CZB");
		PAY_CHANNEL.put("重庆银行", "CQB");
		PAY_CHANNEL.put("渤海银行", "CBHB");
		PAY_CHANNEL.put("南京银行", "NJCB");
		PAY_CHANNEL.put("北京银行", "BCCB");
		PAY_CHANNEL.put("深圳发展银行", "SDB");
		PAY_CHANNEL.put("南海农村信用社", "NHB");
		PAY_CHANNEL.put("宁波银行", "NBCB");
		PAY_CHANNEL.put("厦门商业银行", "XMCCB");
		PAY_CHANNEL.put("中国信合", "CNRCU");
		PAY_CHANNEL.put("浙江稠州商业银行", "CZCB");
		PAY_CHANNEL.put("杭州银行", "HZB");
		PAY_CHANNEL.put("财付通余额", "TenPay01");
		PAY_CHANNEL.put("财付通一点通", "TenPay02");
		PAY_CHANNEL.put("中国工商银行企业版", "ICBC-B2B");
		PAY_CHANNEL.put("中国农业银行企业版", "ABC-B2B");
		PAY_CHANNEL.put("招商银行企业版", "CMBCHINA-B2B");
		PAY_CHANNEL.put("中国建设银行企业版", "CCB-B2B");
		PAY_CHANNEL.put("光大银行企业版", "CEB-B2B");
		PAY_CHANNEL.put("深圳发展银行企业版", "SDB-B2B");
		PAY_CHANNEL.put("上海银行", "SHB");
		PAY_CHANNEL.put("北京农村商业银行", "BJRCB");
		PAY_CHANNEL.put("东亚银行", "HKBEA");
		PAY_CHANNEL.put("IPS账户支付", "InterPay01");
		PAY_CHANNEL.put("卡通", "AliPay04");
		PAY_CHANNEL.put("WAP支付宝余额", "alipaywap");
		PAY_CHANNEL.put("WAP财付通余额", "tenpaywap");
		PAY_CHANNEL.put("建设银行信用卡", "CCB-CREDITCARD");
		PAY_CHANNEL.put("招商银行信用卡", "CMBCHINA-CREDITCARD");
		PAY_CHANNEL.put("中信银行信用卡", "ECITIC-CREDITCARD");
		PAY_CHANNEL.put("工商银行信用卡", "ICBC-CREDITCARD");
		PAY_CHANNEL.put("工商银行信用卡支付宝快捷支付", "CREDITCARD_QICBC");
		PAY_CHANNEL.put("中国农业银行信用卡支付宝快捷支付", "CREDITCARD_QABC");
		PAY_CHANNEL.put("中国银行信用卡支付宝快捷支付", "CREDITCARD_QBOC");
		PAY_CHANNEL.put("建设银行信用卡支付宝快捷支付", "CREDITCARD_QCCB");
		PAY_CHANNEL.put("招商银行信用卡支付宝快捷支付", "CREDITCARD_QCMB");
		PAY_CHANNEL.put("北京银行信用卡支付宝快捷支付", "CREDITCARD_QBJBANK");
		PAY_CHANNEL.put("华夏银行信用卡支付宝快捷支付", "CREDITCARD_QHXBANK");
		PAY_CHANNEL.put("光大银行信用卡支付宝快捷支付", "CREDITCARD_QCEB");
		PAY_CHANNEL.put("中国民生银行信用卡支付宝快捷支付", "CREDITCARD_QCMBC");
		PAY_CHANNEL.put("广东发展银行信用卡支付宝快捷支付", "CREDITCARD_QGDB");
		PAY_CHANNEL.put("兴业银行信用卡支付宝快捷支付", "CREDITCARD_QCIB");
		PAY_CHANNEL.put("深圳发展银行信用卡支付宝快捷支付", "CREDITCARD_QSDB");
		PAY_CHANNEL.put("平安银行信用卡支付宝快捷支付", "CREDITCARD_QSPABANK");
		PAY_CHANNEL.put("宁波银行信用卡支付宝快捷支付", "CREDITCARD_QNBBANK");
		PAY_CHANNEL.put("上海农村商业银行信用卡支付宝快捷支付", "CREDITCARD_QSHRCB");
		PAY_CHANNEL.put("江苏银行信用卡支付宝快捷支付", "CREDITCARD_QJSBANK");
		PAY_CHANNEL.put("杭州银行信用卡支付宝快捷支付", "CREDITCARD_QHZCB");
		PAY_CHANNEL.put("宁夏银行信用卡支付宝快捷支付", "CREDITCARD_QNXBANK");
		PAY_CHANNEL.put("大连银行信用卡支付宝快捷支付", "CREDITCARD_QDLB");
		PAY_CHANNEL.put("东莞银行信用卡支付宝快捷支付", "CREDITCARD_QBOD");
		PAY_CHANNEL.put("南京银行信用卡支付宝快捷支付", "CREDITCARD_QNJCB");
		PAY_CHANNEL.put("徽商银行信用卡支付宝快捷支付", "CREDITCARD_QHSBANK");
		PAY_CHANNEL.put("河北银行信用卡支付宝快捷支付", "CREDITCARD_QBHB");
		PAY_CHANNEL.put("天津银行信用卡支付宝快捷支付", "CREDITCARD_QTCCB");
		PAY_CHANNEL.put("吴江农村商业银行信用卡支付宝快捷支付", "CREDITCARD_QWJRCB");
		PAY_CHANNEL.put("重庆农村商业银行信用卡支付宝快捷支付", "CREDITCARD_QCRCBANK");
		PAY_CHANNEL.put("上饶银行信用卡支付宝快捷支付", "CREDITCARD_QSRBANK");
		PAY_CHANNEL.put("浙江稠州商业银行信用卡支付宝快捷支付", "CREDITCARD_QCZCB");
		PAY_CHANNEL.put("中国农业银行支付宝借记卡快捷支付", "DEBITCARD_ABC");
		PAY_CHANNEL.put("中国工商银行支付宝借记卡快捷支付", "DEBITCARD_ICBC");
		PAY_CHANNEL.put("中信银行支付宝借记卡快捷支付", "DEBITCARD_CITIC");
		PAY_CHANNEL.put("光大银行支付宝借记卡快捷支付", "DEBITCARD_CEB");
		PAY_CHANNEL.put("深圳发展银行支付宝借记卡快捷支付", "DEBITCARD_SDB");
		PAY_CHANNEL.put("晋商银行支付宝借记卡快捷支付", "DEBITCARD_JSB");
		PAY_CHANNEL.put("九江银行支付宝借记卡快捷支付", "DEBITCARD_JJBANK");
		PAY_CHANNEL.put("吉林省农村信用社支付宝借记卡快捷支付", "DEBITCARD_JLRCU");
		PAY_CHANNEL.put("太仓农村商业银行支付宝借记卡快捷支付", "DEBITCARD_TCRCB");
		PAY_CHANNEL.put("河北银行支付宝借记卡快捷支付", "DEBITCARD_BHB");
		PAY_CHANNEL.put("建设银行支付宝信用卡快捷支付", "CREDITCARD_CCB");
		PAY_CHANNEL.put("广东发展银行支付宝信用卡快捷支付", "CREDITCARD_GDB");
		PAY_CHANNEL.put("工商银行支付宝信用卡快捷支付", "CREDITCARD_ICBC");
		PAY_CHANNEL.put("中国民生银行支付宝信用卡快捷支付", "CREDITCARD_CMBC");
		PAY_CHANNEL.put("兴业银行支付宝信用卡快捷支付", "CREDITCARD_CIB");
		PAY_CHANNEL.put("中国银行支付宝信用卡快捷支付", "CREDITCARD_BOC");
		PAY_CHANNEL.put("中国农业银行支付宝信用卡快捷支付", "CREDITCARD_ABC");
		PAY_CHANNEL.put("深圳发展银行支付宝信用卡快捷支付", "CREDITCARD_SDB");
		PAY_CHANNEL.put("平安银行支付宝信用卡快捷支付", "CREDITCARD_SPABANK");
		PAY_CHANNEL.put("华夏银行支付宝信用卡快捷支付", "CREDITCARD_HXBANK");
		PAY_CHANNEL.put("北京银行支付宝信用卡快捷支付", "CREDITCARD_BJBANK");
		PAY_CHANNEL.put("江苏银行支付宝信用卡快捷支付", "CREDITCARD_JSBANK");
		PAY_CHANNEL.put("河北银行支付宝信用卡快捷支付", "CREDITCARD_BHB");
		PAY_CHANNEL.put("天津银行支付宝信用卡快捷支付", "CREDITCARD_TCCB");
		PAY_CHANNEL.put("沃易付快捷", "WOYF02");
		PAY_CHANNEL.put("中行分期付款(3期)", "INSTALL_BOC_3");
		PAY_CHANNEL.put("中行分期付款(6期)", "INSTALL_BOC_6");
		PAY_CHANNEL.put("中行分期付款(12期)", "INSTALL_BOC_12");
		PAY_CHANNEL.put("中行分期付款(24期)", "INSTALL_BOC_24");
		PAY_CHANNEL.put("工行分期付款(3期)", "INSTALL_ICBC_3");
		PAY_CHANNEL.put("工行分期付款(6期)", "INSTALL_ICBC_6");
		PAY_CHANNEL.put("工行分期付款(12期)", "INSTALL_ICBC_12");
		PAY_CHANNEL.put("工行分期付款(24期)", "INSTALL_ICBC_24");
		PAY_CHANNEL.put("农业分期付款(3期)", "INSTALL_ABC_3");
		PAY_CHANNEL.put("农业分期付款(6期)", "INSTALL_ABC_6");
		PAY_CHANNEL.put("农业分期付款(12期)", "INSTALL_ABC_12");
		PAY_CHANNEL.put("农业分期付款(24期)", "INSTALL_ABC_24");
		PAY_CHANNEL.put("建行3期", "INSTALL_CCB_3");
		PAY_CHANNEL.put("建行6期", "INSTALL_CCB_6");
		PAY_CHANNEL.put("建行12期", "INSTALL_CCB_12");
		PAY_CHANNEL.put("建行24期", "INSTALL_CCB_24");
		PAY_CHANNEL.put("沃易付账户余额", "WOYF01");
		PAY_CHANNEL.put("中国工商银行支付宝借记卡快捷支付", "DEBITCARD_QICBC");
		PAY_CHANNEL.put("中国建设银行支付宝借记卡快捷支付", "DEBITCARD_QCCB");
		PAY_CHANNEL.put("中国农业银行支付宝借记卡快捷支付", "DEBITCARD_QABC");
		PAY_CHANNEL.put("中国银行支付宝借记卡快捷支付", "DEBITCARD_QBOC");
		PAY_CHANNEL.put("中国光大银行支付宝借记卡快捷支付", "DEBITCARD_QCEB");
		PAY_CHANNEL.put("深圳发展银行支付宝借记卡快捷支付", "DEBITCARD_QSDB");
		PAY_CHANNEL.put("银联信用卡快捷支付", "CREDITCARD_UNIONPAY");
		PAY_CHANNEL.put("银联借记卡快捷支付", "DEBITCARD_UNIONPAY");
		PAY_CHANNEL.put("支付宝信用卡快捷支付", "CREDITCARD_ALIPAY");
		PAY_CHANNEL.put("支付宝借记卡快捷支付", "DEBITCARD_ALIPAY");
		PAY_CHANNEL.put("手厅沃支付", "WOYFWAP");
		PAY_CHANNEL.put("微信支付", "wxapp");
		PAY_CHANNEL.put("支付宝客户端", "alipayApp");
//		PAY_CHANNEL.put("光大银行", "CREDITCARD_CEB");
//		PAY_CHANNEL.put("中信银行", "CREDITCARD_CITIC");
//		PAY_CHANNEL.put("招商银行", "CREDITCARD_CMB");
		PAY_CHANNEL.put("财付通信用卡快捷", "CREDITCARD_TENPAY");
		PAY_CHANNEL.put("沃支付信用卡快捷", "CREDITCARD_WOYF");
//		PAY_CHANNEL.put("北京银行", "DEBITCARD_BJBANK");
//		PAY_CHANNEL.put("建设银行", "DEBITCARD_CCB");
//		PAY_CHANNEL.put("招商银行", "DEBITCARD_CMB");
//		PAY_CHANNEL.put("广发银行", "DEBITCARD_GDB");
		PAY_CHANNEL.put("财付通借记卡快捷", "DEBITCARD_TENPAY");
		PAY_CHANNEL.put("沃支付借记卡快捷", "DEBITCARD_WOYF");
		PAY_CHANNEL.put("拉卡拉", "LAKALA");
		PAY_CHANNEL.put("支付宝扫码", "SCAN_ALIPAY");
		PAY_CHANNEL.put("支付宝扫码账户", "SCAN_ALIPAY_ACCOUNT");
		PAY_CHANNEL.put("支付宝扫码信用卡", "SCAN_ALIPAY_CREDIT");
		PAY_CHANNEL.put("支付宝扫码借记卡", "SCAN_ALIPAY_DEBIT");

		ACTIVITY_TYPE.put("购手机送话费", "GJSF");
		ACTIVITY_TYPE.put("预存话费送手机", "CFSJ");
		ACTIVITY_TYPE.put("存话费送话费", "CFSF");
		ACTIVITY_TYPE.put("存话费送业务", "CFSY");
		ACTIVITY_TYPE.put("合约惠机A模式", "HYHJ");
		ACTIVITY_TYPE.put("合约惠机B模式", "HYHB");
		ACTIVITY_TYPE.put("合约惠机C模式", "HYHC");
		ACTIVITY_TYPE.put("存费送费", "CFSF");
		ACTIVITY_TYPE.put("存费送业务", "CFSY");
		ACTIVITY_TYPE.put("购机送费", "GJSF");
		ACTIVITY_TYPE.put("", "");
		ACTIVITY_TYPE.put("", "");
		ACTIVITY_TYPE.put("", "");
	}
}
