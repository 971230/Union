package test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.phw.eop.api.ApiException;
import org.phw.eop.api.EopClient;
import org.phw.eop.api.EopReq;
import org.phw.eop.api.EopRsp;

import com.ztesoft.common.util.StringUtils;

public class TestServelet {
	
//2.0退单地址
//	public static String strIp = "10.123.189.154";
//	public static String strPort = "7001";
	//退单申请【状态通知接口】2.0
//	public static String servlet = "/integrationweb/integration/ordersStatus.sync";//新商城推送订单入口
	
	
	//测试环境
//	public static String strIp = "10.123.99.69";
//	public static String strPort = "30001";
//	public static String strPort = "10004";
	
//接口机模拟总部发送订单
//	public static String strIp = "10.123.178.43";
//	public static String strPort = "7004";	
//	public static String servlet = "/service/interface/receiveStatus_ZB.srv";

	//生产
	public static String strIp = "10.123.99.69";
	public static String strPort = "10004";
	
	//本机
//	public static String strIp = "127.0.0.1";
//	public static String strPort = "8080";
	//总部
//	public static String strIp = "eop.mall.10010.com/phw-eop";
//	public static String strPort = "";
//	public static String action = "periphery.deliverynotify";
//	public static String servlet = "/servlet/InformationServerServlet";//新商城推送订单入口
//	public static String servlet = "/servlet/ZBMallOrderServlet";//总部推送订单入口
//	public static String servlet = "";//
//	public static String servlet = "/servlet/CommonServlet?reqType=wyg_syn_operator_id";//商城同步工号
	public static String servlet = "/servlet/CommonServlet?reqType=jkzf_inf";//接口转发
//	public static String servlet = "/servlet/CommonServlet?reqType=wyg_syn_order_status";//沃云购-订单状态同步

	//沃云购-订单状态同步
//	public static String json = "{\"ActiveNo\":\"12ce4c6c-96d3-451d-a2c3-981617b4901c\",\"apiMethodName\":\"com.zte.unicomService.wyg.chargebackApply\",\"appKey\":\"wssmall_ecs\",\"appSecret\":\"\",\"asy\":\"no\",\"base_co_id\":\"\",\"base_order_id\":\"\",\"changeFiels\":[],\"db_action\":\"\",\"dyn_field\":null,\"format\":\"json\",\"inf_log_id\":\"10298088\",\"is_dirty\":\"no\",\"locale\":\"zh_CN\",\"md5\":\"xsc-order-status-syn\",\"method\":\"\",\"mqTopic\":\"\",\"need_query\":\"yes\",\"orderSource\":\"WCS\",\"orderState\":\"05\",\"outOrderId\":\"WCSV2S15102215061256679343\",\"remote_type\":\"dubbo\",\"reqId\":\"xsc-wyg-valid\",\"reqType\":\"wyg_syn_order_status\",\"ropRequestContext\":null,\"serverUrl\":\"\",\"sessionId\":\"\",\"sign\":\"\",\"sourceFrom\":\"ECS\",\"textParams\":{},\"userSessionId\":\"7b6dc26918594333976bde4983ad3d17\",\"version\":\"1.0\"}";


	//新商城入库报文
//	public static String json = "{\"order_req\":{\"source_system\":\"100312\",\"bss_operator\":\"aatest\",\"oss_operator\":\"bbtest\",\"receive_system\":\"10011\",\"serial_no\":\"97f80dc9-6d03-40bc-8db5-a826451f0df5\",\"time\":\"20150929064730\",\"order_id\":\"WCS1509290646350test\",\"order_city\":\"440100\",\"development_code\":\"5100000999\",\"order_type\":\"1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"platform_status\":\"WAIT_SELLER_SEND_GOODS\",\"reference_name\":\"vp1\",\"create_time\":\"20150929064635\",\"order_amount\":240000,\"order_heavy\":\"0\",\"order_disacount\":0,\"pay_money\":240000,\"source_from_system\":\"10008\",\"source_from\":\"10034\",\"ship_area\":\"\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440100\",\"alipay_id\":\"无账号\",\"uid\":\"510000\",\"is_deal\":\"0\",\"pay_time\":\"20150929064727\",\"payment_serial_no\":\"2015092900001000440070274203\",\"payment_code\":\"03\",\"payment_name\":\"支付宝\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"channel_id\":\"vip\",\"chanel_name\":\"老用户\",\"channel_mark\":\"13\",\"ssyh\":\"vip\",\"points_user\":\"18620860806\",\"order_comment\":\"赠品信息：WO+视频定向流量-限106及以上套餐,电影通兑卡（内含2D电影票2张）,赠3G省内闲时流量-6个月-限106以上套餐；渠道标记：自营渠道\",\"is_examine_card\":\"0\",\"shipping_type\":\"XJ\",\"shipping_time\":\"NOLIMIT\",\"ship_city\":\"440600\",\"ship_country\":\"440604\",\"ship_addr\":\"华远东路77号1座401\",\"ship_tel\":\"18620860806\",\"ship_phone\":\"18620860806\",\"name\":\"黄志刚\",\"ship_name\":\"zte测试\",\"uname\":\"黄志刚\",\"abnormal_status\":\"正常\",\"delivery_status\":\"未发货\",\"shipping_amount\":0,\"n_shipping_amount\":0,\"shipping_quick\":\"01\",\"is_to4g\":\"1\",\"order_list\":[{\"activity_list\":[{\"activity_code\":\"201505225437004193\",\"activity_id\":14339,\"gift_list\":[{\"gift_id\":\"5869090400020150108002304\",\"gift_num\":1,\"is_process\":\"0\"},{\"gift_id\":\"5869090100020150113002331\",\"gift_num\":1,\"is_process\":\"0\"},{\"gift_id\":\"5869090200020150813004390\",\"gift_num\":1,\"is_process\":\"0\"}]}],\"prod_offer_code\":\"596901020520141202023437\",\"prod_offer_name\":\"136元[vip商城]【沃4G】预存240元送480元话费12月\",\"prod_offer_type\":\"20000\",\"offer_disacount_price\":0,\"offer_coupon_price\":240000,\"prod_offer_num\":1,\"prod_offer_heavy\":\"0\",\"certi_type\":\"SFZ18\",\"cust_type\":\"GRKH\",\"cust_name\":\"黄志刚\",\"certi_num\":\"440602197205291534\",\"certi_address\":\"广东省佛山市禅城区同华西一路1号之二802房\",\"invoice_print_type\":\"3\",\"offer_price\":240000,\"good_no_deposit\":\"0\",\"offer_eff_type\":\"ALLM\",\"package_sale\":\"NO\",\"is_change\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18620860806\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"1\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"sex\",\"param_name\":\"性别\",\"param_value_code\":\"\",\"param_value\":\"M\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\",\"param_value_code\":\"\",\"param_value\":\"广东省佛山市禅城区同华西一路1号之二802房\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"hehe\",\"param_value\":\"none\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}";
	//商城工号同步

//	public static String json = "{\"reqId\":\"wyg2d0\",\"reqType\":\"wyg_syn_operator_id\",\"source_system\":\"100132\",\"receive_system\":\"10009\",\"serial_no\":\"272c570a-62ac-4e07-8a79-97224d9be55d\",\"time\":\"20151013175420\",\"StaffInfo\":[{\"StaffCode\":\"GZHZ0712\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"GZHZ0712\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DG013666\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DG013666\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"CZFZ3GJQ\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"CZFZ3GJQ\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"STHJ0021\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"STHJ0021\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"STHJ0014\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"STHJ0014\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"QYJTYF17\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"QYJTYF17\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"ZSBSB612\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"ZSBSB612\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"JMFWA014\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"JMFWA014\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"YF010087\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"YF010087\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"ZSBS5306\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"ZSBS5306\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"NH944856\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"NH944856\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"SZBA1019\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"SZBA1019\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"SZBA1018\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"SZBA1018\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"HZXN6182\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"HZXN6182\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ018\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ018\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ075\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ075\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ017\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ017\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ016\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ016\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ078\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ078\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ015\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ015\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ013\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ013\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ012\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ012\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ023\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ023\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"TJDGJ011\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"TJDGJ011\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH11\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH11\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH10\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH10\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH09\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH09\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH08\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH08\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH07\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH07\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH06\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH06\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH05\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH05\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH04\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH04\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH03\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH03\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH02\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH02\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ076\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ076\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ074\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ074\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ070\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ070\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ069\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ069\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ068\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ068\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ066\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ066\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ071\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ071\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGYJKH01\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGYJKH01\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ073\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ073\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ072\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ072\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGHLW001\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGHLW001\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ055\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ055\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ054\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ054\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ053\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ053\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ052\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ052\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ051\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ051\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ050\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ050\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ065\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ065\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ064\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ064\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ063\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ063\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ062\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ062\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ061\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ061\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ060\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ060\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"HLWDG679\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"HLWDG679\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ058\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ058\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ057\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ057\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ056\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ056\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"HLWDG001\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"HLWDG001\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ045\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ045\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ044\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ044\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ043\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ043\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ042\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ042\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ041\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ041\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ046\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ046\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DSZFSL01\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DSZFSL01\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"KFWBB007\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"KFWBB007\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZFSCGH1\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZFSCGH1\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ029\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ029\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ028\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ028\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ027\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ027\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ026\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ026\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DGZYJZ01\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DGZYJZ01\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ040\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ040\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ039\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ039\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ038\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ038\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ037\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ037\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ036\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ036\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ035\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ035\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ025\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ025\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ024\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ024\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ023\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ023\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ022\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ022\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ021\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ021\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ020\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ020\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ018\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ018\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ017\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ017\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ015\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ015\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ014\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ014\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ012\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ012\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ011\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ011\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ009\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ009\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ008\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ008\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ007\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ007\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ006\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ006\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ005\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ005\",\"Province\":\"51\",\"City\":\"518\"}]},{\"StaffCode\":\"DZDGJ004\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"DZDGJ004\",\"Province\":\"51\",\"City\":\"518\"}]}]}";

//	public static String json = "{\"reqId\":\"xsc-wyg-valid\",\"reqType\":\"wyg_syn_operator_id\",\"serial_no\":\"201402191000022222\",\"time\":\"20140219094811\",\"source_system\":\"10011\",\"receive_system\":\"10008\",\"StaffInfo\":[{\"BelongDesc\":\"什么鬼\",\"updateStaff\":\"呵呵\",\"StaffCode\":\"HLWxx543\",\"CodeState\":\"10\",\"CBSSInfo\":[{\"CBSSStaff\":\"HLWxx679\",\"Province\":\"51\",\"City\":\"518\"},{\"CBSSStaff\":\"hahaha\",\"Province\":\"51\",\"City\":\"510\"}]}]}";
//	public static String json = "{\"order_req\":{\"source_system\":\"10008\",\"receive_system\":\"10011\",\"serial_no\":\"97f80dc9-6d03-40bc-8db5-a826451f0df4\",\"time\":\"20150929064730\",\"order_id\":\"WCS15092906463501223197\",\"order_city\":\"440100\",\"development_code\":\"5100000999\",\"order_type\":\"1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"platform_status\":\"WAIT_SELLER_SEND_GOODS\",\"reference_name\":\"vp1\",\"create_time\":\"20150929064635\",\"order_amount\":240000,\"order_heavy\":\"0\",\"order_disacount\":0,\"pay_money\":240000,\"source_from_system\":\"10008\",\"source_from\":\"10034\",\"ship_area\":\"\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440100\",\"alipay_id\":\"无账号\",\"uid\":\"510000\",\"is_deal\":\"0\",\"pay_time\":\"20150929064727\",\"payment_serial_no\":\"2015092900001000440070274203\",\"payment_code\":\"03\",\"payment_name\":\"支付宝\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"channel_id\":\"vip\",\"chanel_name\":\"老用户\",\"channel_mark\":\"13\",\"ssyh\":\"vip\",\"points_user\":\"18620860806\",\"order_comment\":\"赠品信息：WO+视频定向流量-限106及以上套餐,电影通兑卡（内含2D电影票2张）,赠3G省内闲时流量-6个月-限106以上套餐；渠道标记：自营渠道\",\"is_examine_card\":\"0\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_city\":\"440600\",\"ship_country\":\"440604\",\"ship_addr\":\"华远东路77号1座401\",\"ship_tel\":\"18620860806\",\"ship_phone\":\"18620860806\",\"name\":\"黄志刚\",\"ship_name\":\"黄志刚\",\"uname\":\"黄志刚\",\"abnormal_status\":\"正常\",\"delivery_status\":\"未发货\",\"shipping_amount\":0,\"n_shipping_amount\":0,\"shipping_quick\":\"01\",\"is_to4g\":\"1\",\"order_list\":[{\"activity_list\":[{\"activity_code\":\"201505225437004193\",\"activity_id\":14339,\"gift_list\":[{\"gift_id\":\"5869090400020150108002304\",\"gift_num\":1,\"is_process\":\"0\"},{\"gift_id\":\"5869090100020150113002331\",\"gift_num\":1,\"is_process\":\"0\"},{\"gift_id\":\"5869090200020150813004390\",\"gift_num\":1,\"is_process\":\"0\"}]}],\"prod_offer_code\":\"596901020520141202023437\",\"prod_offer_name\":\"136元[vip商城]【沃4G】预存240元送480元话费12月\",\"prod_offer_type\":\"20000\",\"offer_disacount_price\":0,\"offer_coupon_price\":240000,\"prod_offer_num\":1,\"prod_offer_heavy\":\"0\",\"certi_type\":\"SFZ18\",\"cust_type\":\"GRKH\",\"cust_name\":\"黄志刚\",\"certi_num\":\"440602197205291534\",\"certi_address\":\"广东省佛山市禅城区同华西一路1号之二802房\",\"invoice_print_type\":\"3\",\"offer_price\":240000,\"good_no_deposit\":\"0\",\"offer_eff_type\":\"ALLM\",\"package_sale\":\"NO\",\"is_change\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18620860806\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"1\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"sex\",\"param_name\":\"性别\",\"param_value_code\":\"\",\"param_value\":\"M\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\",\"param_value_code\":\"\",\"param_value\":\"广东省佛山市禅城区同华西一路1号之二802房\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_nam\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}";
	
	//客户资料校验三户返回
	public static String json = "{\"inf_params\": {\"busi_params\": {\"channelId\": \"51b12z3\",\"channelType\": \"1030100\",\"city\": \"530\",\"district\": \"0\",\"infoList\": \"CUST|USER|ACCT\",\"operatorId\": \"HLWFS679\",\"province\": \"51\",\"serialNumber\": \"18588767712\",\"serviceClassCode\": \"0001\",\"tradeTypeCode\": \"9999\"},\"inf_mark\": \"shfh\"},\"receive_system\": \"10011\",\"reqId\": \"xsc-wyg-valid\",\"reqType\": \"jkzf_inf\",\"serial_no\": \"2f0ead28-bcd4-429e-bb77-42cf3d0be56b\",\"source_system\": \"10008\",\"time\": \"20151015143004\"}";

	
	
	//通知商城退单确认
//	public static String json = 
//			"{\"OrderDesc\":\"\",\"OutnotNeedReqStrOrderId\":\"WCSV215100109521649019043\",\"Logistics\":\"SF-FYZQYF\",\"SimICCID\":\"89860115851044669041\",\"OrderState\":\"09\",\"OrderSource\":\"100312\",\"TerminalNo\":\"\",\"ActiveNo\":\"EM2015911939146535\",\"LogisticsNo\":\"\",\"OutOrderId\":\"WCSV215100109521649019043\"}";
//	public static String json = "";
	
	private static DefaultHttpClient httpclient = null;
	
	public static void main(String[] args) {

		try {
			System.out.println("发送报文:\n" + json);
//			String remsg = channelchangezb(json,"http://"+strIp+((null==strPort||"".equals(strPort))?"":(":"+strPort))+servlet,action);
			String remsg = channelchange2(json,"http://"+strIp+((null==strPort||"".equals(strPort))?"":(":"+strPort))+servlet);//新商城推送订单,servelet接口

//			String remsg = httpGet(json,"http://"+strIp+((null==strPort||"".equals(strPort))?"":(":"+strPort))+servlet);//总部推送订单
			
			System.out.println("返回报文：\n" + remsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        Map<String, String> parameters = new HashMap<String, String>();  
//
//		String url = "http://"+strIp+":"+strPort+"/servlet/InformationServerServlet";
//        parameters.put("name", "sarin");  
//        String result = "";
////        result = sendGet(url,parameters);  
//        result = sendPost(url,parameters);  
//        System.out.println(result);  
	}

	
	/**
	 * 
	 * */
	 public static String httpGet(String jsonStr, String path) throws Exception{
		 		 
         // 得到请求报文的二进制数据
         byte[] data = jsonStr.getBytes("UTF-8");
         java.net.URL url = new java.net.URL(path);
         //openConnection() 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接
         java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();// 打开一个连接
         conn.setRequestMethod("POST");// 设置post方式请求
         conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒  JDK1.5以上才有此方法
         conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒 JDK1.5以上才有此方法
         // 打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true
         conn.setDoOutput(true);
         // 这里只设置内容类型与内容长度的头字段根据传送内容决定
         // 内容类型Content-Type:
         // application/x-www-form-urlencoded、text/xml;charset=GBK
         // 内容长度Content-Length: 38
//         conn.setRequestProperty("Content-Type", "text/xml;charset=GBK");
         conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//         conn.setRequestProperty("Content-Length", String.valueOf(data.length));
         OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
         BufferedOutputStream out = new BufferedOutputStream(outStream);
         out.write(data);
         // 把二进制数据写入是输出流
//         outStream.write(data);
         // 内存中的数据刷入
         out.flush();
         //关闭流
         out.close();
 

         // 获取应答输入流
         InputStream inputStream = conn.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

         StringBuffer buf = new StringBuffer();
         String line = null;
         while ((line = reader.readLine()) != null) {
             buf.append(line);
             buf.append("\r\n");
         }
         // 获取应答内容
         String msg = buf.toString();

         // 关闭流
         reader.close();

         // 关闭输入流
         inputStream.close();
         return msg;
     }
	
	/**
	       * 发送请求到http服务然后接收返回报文
	      * @param jsonStr 请求的json格式的字符串
	      * @param path   请求的http服务的路径
	      * @return 返回请求的响应信息
	      * @throws IOException 
	      */
	     public static String channelchangezb(String jsonStr, String path, String action)
	             throws IOException {

	 		String appCode = "6375BB381D7649A5A38108AB4CC746D0";
	 		String signKey = "372817d8-6e54-49c4-9d95-29b31142902e";

				EopClient client = new EopClient(path, appCode, signKey);

				EopReq eopReq = new EopReq(action);
				eopReq.put("REQ_STR", jsonStr);
				
				
				EopRsp rsp;
				String s ="";
				try {
					rsp = client.execute(eopReq);
					s = rsp.toString();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return s;
	    	 }
	     
	/**
	       * 发送请求到http服务然后接收返回报文
	      * @param jsonStr 请求的json格式的字符串
	      * @param path   请求的http服务的路径
	      * @return 返回请求的响应信息
	      * @throws IOException 
	      */
	     public static String channelchange2(String jsonStr, String path)
	             throws IOException {
	         // 得到请求报文的二进制数据
	         byte[] data = jsonStr.getBytes();
	         java.net.URL url = new java.net.URL(path);
	         //openConnection() 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接
	         java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();// 打开一个连接
	         conn.setRequestMethod("POST");// 设置post方式请求
	         conn.setConnectTimeout(5 * 1000);// 设置连接超时时间为5秒  JDK1.5以上才有此方法
	         conn.setReadTimeout(20 * 1000);// 设置读取超时时间为20秒 JDK1.5以上才有此方法
	         // 打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true
	         conn.setDoOutput(true);
	         // 这里只设置内容类型与内容长度的头字段根据传送内容决定
	         // 内容类型Content-Type:
	         // application/x-www-form-urlencoded、text/xml;charset=GBK
	         // 内容长度Content-Length: 38
	         conn.setRequestProperty("Content-Type", "text/xml;charset=GBK");
	         conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	         OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
	         // 把二进制数据写入是输出流
	         outStream.write(data);
	         // 内存中的数据刷入
	         outStream.flush();
	         //关闭流
	         outStream.close();
	 
	         String msg = "";// 保存调用http服务后的响应信息
	         // 如果请求响应码是200，则表示成功
	         if (conn.getResponseCode() == 200) {
	             // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱码
	             BufferedReader in = new BufferedReader(new InputStreamReader(
	                      conn.getInputStream(), "UTF-8"));//返回从此打开的连接读取的输入流

	             msg = in.readLine();
	             in.close();
	         }
	         conn.disconnect();// 断开连接
	         return msg;
	     }
	public static void invoke(){
		String serverUrl = "http://"+strIp+":"+strPort+"/servlet/InformationServerServlet";
		
		String action = "POST";

		EopReq eopReq = new EopReq(action);
		
		
		String param_name = "";
		String param_value = "{\"activeNo\":\"EM20141026334293418\"}";//报文
		param_value = StringUtils.capitalized(param_value);

		eopReq.put(param_name, param_value);

		
		EopRsp rsp = null;

		try{
			EopClient client = new EopClient(serverUrl, "", "");
			rsp = client.execute(eopReq); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void haha(String[] args) {//发起请求、先写发送（out）、然后等待度(in)

		try{

			String url = "http://"+strIp+":"+strPort+"/servlet/InformationServerServlet";

			String json = "json={\"a\":\"a\"}";
			url = url + "?" + json;


			DefaultHttpClient httpClient = getHttpClientDefault();

			HttpResponse httpResponse = null;
			HttpRequestBase uriRequest = null;
			uriRequest = new HttpGet(url);
			httpResponse = httpClient.execute(uriRequest);




		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static DefaultHttpClient getHttpClientDefault() {
		if (null != httpclient) {
			return httpclient;
		}
		int timeoutConnection = 23 * 1000;
		int timeoutSocket = 28 * 1000;
		int BUFFER_SIZE = 8192 * 20;

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);

		HttpClientParams.setRedirecting(httpParameters, true);
		HttpClientParams.setCookiePolicy(httpParameters,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager(),
				httpParameters);
		return httpclient;
	}

    /** 
         * 发送GET请求 
         *  
         * @param url 
         *            目的地址 
         * @param parameters 
         *            请求参数，Map类型。 
         * @return 远程响应结果 
         */  
        public static String sendGet(String url, Map<String, String> parameters) {  
            String result = "";// 返回的结果  
            BufferedReader in = null;// 读取响应输入流  
            StringBuffer sb = new StringBuffer();// 存储参数  
            String params = "";// 编码之后的参数  
            try {  
                // 编码请求参数  
                if (parameters.size() == 1) {  
                    for (String name : parameters.keySet()) {  
                        sb.append(name).append("=").append(  
                                java.net.URLEncoder.encode(parameters.get(name),  
                                        "UTF-8"));  
                    }  
                    params = sb.toString();  
                } else {  
                    for (String name : parameters.keySet()) {  
                        sb.append(name).append("=").append(  
                                java.net.URLEncoder.encode(parameters.get(name),  
                                        "UTF-8")).append("&");  
                    }  
                    String temp_params = sb.toString();  
                    params = temp_params.substring(0, temp_params.length() - 1);  
                }  
                String full_url = url + "?" + params;  
                System.out.println(full_url);  
                // 创建URL对象  
                java.net.URL connURL = new java.net.URL(full_url);  
                // 打开URL连接  
                java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                        .openConnection();  
                // 设置通用属性  
                httpConn.setRequestProperty("Accept", "*/*");  
                httpConn.setRequestProperty("Connection", "Keep-Alive");  
                httpConn.setRequestProperty("User-Agent",  
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
                // 建立实际的连接  
                httpConn.connect();  
                // 响应头部获取  
                Map<String, List<String>> headers = httpConn.getHeaderFields();  
                // 遍历所有的响应头字段  
                for (String key : headers.keySet()) {  
                    System.out.println(key + "\t：\t" + headers.get(key));  
                }  
                // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
                in = new BufferedReader(new InputStreamReader(httpConn  
                        .getInputStream(), "UTF-8"));  
                String line;  
                // 读取返回的内容  
                while ((line = in.readLine()) != null) {  
                    result += line;  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    if (in != null) {  
                        in.close();  
                    }  
                } catch (IOException ex) {  
                    ex.printStackTrace();  
                }  
            }  
            return result;  
        }  
        /** 
         * 发送POST请求 
         *  
         * @param url 
         *            目的地址 
         * @param parameters 
         *            请求参数，Map类型。 
         * @return 远程响应结果 
         */  
        public static String sendPost(String url, Map<String, String> parameters) {
            String result = "";// 返回的结果  
            BufferedReader in = null;// 读取响应输入流  
            PrintWriter out = null;  
            StringBuffer sb = new StringBuffer();// 处理请求参数  
            String params = "";// 编码之后的参数  
            params = "{\"json\":\"haha\"}";
            try {  
                // 编码请求参数  
//                if (parameters.size() == 1) {  
//                    for (String name : parameters.keySet()) {  
//                        sb.append(name).append("=").append(  
//                                java.net.URLEncoder.encode(parameters.get(name),  
//                                        "UTF-8"));  
//                    }  
//                    params = sb.toString();  
//                } else {  
//                    for (String name : parameters.keySet()) {  
//                        sb.append(name).append("=").append(  
//                                java.net.URLEncoder.encode(parameters.get(name),  
//                                        "UTF-8")).append("&");  
//                    }  
//                    String temp_params = sb.toString();  
//                    params = temp_params.substring(0, temp_params.length() - 1);  
//                }  
                // 创建URL对象  
                java.net.URL connURL = new java.net.URL(url);  
                // 打开URL连接  
                java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                        .openConnection();  
                // 设置通用属性  
                httpConn.setRequestProperty("Accept", "*/*");  
                httpConn.setRequestProperty("Connection", "Keep-Alive");  
                httpConn.setRequestProperty("User-Agent",  
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
                // 设置POST方式  
                httpConn.setDoInput(true);  
                httpConn.setDoOutput(true);  
                // 获取HttpURLConnection对象对应的输出流  
                out = new PrintWriter(httpConn.getOutputStream());  
                // 发送请求参数  
                out.write(params);  
                // flush输出流的缓冲  
                out.flush();  
                // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
                in = new BufferedReader(new InputStreamReader(httpConn  
                        .getInputStream(), "UTF-8"));  
                String line;  
                // 读取返回的内容  
                while ((line = in.readLine()) != null) {  
                    result += line;  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    if (out != null) {  
                        out.close();  
                    }  
                    if (in != null) {  
                        in.close();  
                    }  
                } catch (IOException ex) {  
                    ex.printStackTrace();  
                }  
            }  
            return result;  
        } 
}
