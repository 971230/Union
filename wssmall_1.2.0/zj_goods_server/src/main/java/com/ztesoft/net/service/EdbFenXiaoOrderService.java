package com.ztesoft.net.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.drools.core.util.StringUtils;

import zte.net.iservice.IGoodsService;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.iservice.OuterInf;
import com.ztesoft.net.outter.inf.model.OuterError;


public class EdbFenXiaoOrderService implements OuterInf {
	private static Logger logger = Logger.getLogger(EdbFenXiaoOrderService.class);
	// @Resource
	protected IGoodsService goodServices;
	protected IOutterECSTmplManager outterECSTmplManager;
	
	@Resource
	protected IOrderServiceLocal orderServiceLocal;
	/**
	 * 
	 * URL加密
	 * 
	 * @throws UnsupportedEncodingException
	 */

	public static String encodeUri(String s) {
		String str = "";
		try {
			str = URLEncoder.encode(s, "UTF-8");

		} catch (Exception e) {
			throw new java.lang.RuntimeException("encode error !");
		}
		return str;
	}

	/**
	 * 
	 * md5加密
	 */

	public static String getMD5(String input) {

		String result = null;
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			result = byte2hex(md.digest(input.getBytes("utf-8")));

		} catch (Exception e) {

			throw new java.lang.RuntimeException("sign error !");

		}

		return result;
	}

	/**
	 * 
	 * 新的md5签名，首尾放secret。
	 * 
	 * @param secret
	 *            分配给您的APP_SECRET
	 */

	public static String md5Signature(TreeMap<String, String> params,
			String secret) {

		String result = null;

		StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));

		if (orgin == null)

			return result;

		// orgin.append(secret);

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));

		} catch (Exception e) {

			throw new java.lang.RuntimeException("sign error !");

		}

		return result;

	}

	/**
	 * 
	 * 二行制转字符串
	 */

	private static String byte2hex(byte[] b) {

		StringBuffer hs = new StringBuffer();

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs.append("0").append(stmp);

			else

				hs.append(stmp);

		}

		return hs.toString().toUpperCase();

	}

	/**
	 * 
	 * 添加参数的封装方法
	 */

	private static StringBuffer getBeforeSign(TreeMap<String, String> params,
			StringBuffer orgin) {

		if (params == null)

			return null;

		Map<String, String> treeMap = new TreeMap<String, String>();

		treeMap.putAll(params);

		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {

			String name = iter.next();

			orgin.append(name).append(params.get(name));

		}
		return orgin;

	}

	/** 连接到TOP服务器并获取数据 */

	public static String getResult(String urlStr, String content) {

		URL url = null;

		HttpURLConnection connection = null;

		try {

			url = new URL(urlStr);

			connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);

			connection.setDoInput(true);

			connection.setRequestMethod("POST");

			connection.setUseCaches(false);

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.connect();

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			out.write(content.getBytes("utf-8"));

			out.flush();

			out.close();

			BufferedReader reader =

			new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			StringBuffer buffer = new StringBuffer();

			String line = "";

			while ((line = reader.readLine()) != null) {

				buffer.append(line);

			}

			reader.close();

			return buffer.toString();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {

				connection.disconnect();

			}

		}

		return null;

	}

	// 调用API地址
	protected static String testUrl = "http://112.96.28.238/rest/api.aspx";
	// 申请的appkey
	public static final String appkey = "8be065aa";
	// 申请的secret
	public static final String secret = "1386a3ba2f48443990f197dce3d2a2c1";
	// 申请的token
	public static final String token = "0147e3ffaa4f4572956807d69821f236";
	// 主帐号
	public static final String dbhost = "edb_a70821";
	// 返回格式
	public static final String format = "xml";

	// 获取订单信息
	public String edbTradeGet(String begin_time, String end_time,
			String date_type, String page_no, String page_size) {
		TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
		apiparamsMap.put("dbhost", dbhost);// 添加请求参数——主帐号
		apiparamsMap.put("format", format);// 添加请求参数——返回格式
		apiparamsMap.put("method", "edbTradeGet");// 添加请求参数——接口名称
		apiparamsMap.put("slencry", "0");// 添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）
		apiparamsMap.put("ip", "192.168.60.80");// 添加请求参数——IP地址
		apiparamsMap.put("appkey", appkey);// 添加请求参数——appkey
		apiparamsMap.put("appscret", secret);// 添加请求参数——appscret
		apiparamsMap.put("token", token);// 添加请求参数——token
		apiparamsMap.put("v", "2.0");// 添加请求参数——版本号（目前只提供2.0版本）
		apiparamsMap.put("fields", " ");// 返回字段，如果为空则全部返回
		String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
				.format(new Date());
		apiparamsMap.put("timestamp", timestamp);// 添加请求参数——时间戳

		apiparamsMap.put("date_type", date_type);
		apiparamsMap.put("begin_time", begin_time);
		apiparamsMap.put("end_time", end_time);
		apiparamsMap.put("payment_status", " ");
		apiparamsMap.put("order_status", " ");
		apiparamsMap.put("process_status", " ");
		apiparamsMap.put("store_houseid", " ");
		apiparamsMap.put("shop_id", " ");
		apiparamsMap.put("express_num", " ");
		apiparamsMap.put("express", " ");
		apiparamsMap.put("invoice_is_print", " ");
		apiparamsMap.put("key", " ");
		apiparamsMap.put("invoice_is_open", " ");
		apiparamsMap.put("is_split_suit", " ");
		apiparamsMap.put("is_filterreturngoods", " ");
		apiparamsMap.put("page_no", page_no);
		apiparamsMap.put("page_size", page_size);

		// 获取数字签名
		String sign = md5Signature(apiparamsMap, secret);
		apiparamsMap.put("sign", sign);

		StringBuilder param = new StringBuilder();
		for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			if (e.getKey() != "appscret" && e.getKey() != "token") {
				param.append("&").append(e.getKey()).append("=")
						.append(encodeUri(e.getValue()));
			}
		}

		String PostData = "";
		PostData = param.toString().substring(1);
		logger.info(testUrl + "?" + PostData);
		String result = "";
		result = getResult(testUrl, PostData);
		logger.info(result);
		return result;
	}

	// 获取客户信息
	public static void edbProductBaseInfoGet() {

		TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
		apiparamsMap.put("dbhost", dbhost);// 添加请求参数——主帐号
		apiparamsMap.put("format", format);// 添加请求参数——返回格式
		apiparamsMap.put("method", "edbProductBaseInfoGet");// 添加请求参数——接口名称
		apiparamsMap.put("slencry", "0");// 添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）
		apiparamsMap.put("ip", "10.10.120.186");// 添加请求参数——IP地址
		apiparamsMap.put("appkey", appkey);// 添加请求参数——appkey
		apiparamsMap.put("appscret", secret);// 添加请求参数——appscret
		apiparamsMap.put("token", token);// 添加请求参数——token
		apiparamsMap.put("v", "2.0");// 添加请求参数——版本号（目前只提供2.0版本）
		apiparamsMap.put("fields", " ");// 返回字段，如果为空则全部返回
		String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
				.format(new Date());
		apiparamsMap.put("timestamp", timestamp);// 添加请求参数——时间戳

		apiparamsMap.put("starttime", "2013-03-25 13:36:05");
		apiparamsMap.put("endtime", "2014-03-25 14:29:12");
		apiparamsMap.put("barcode", " ");
		apiparamsMap.put("productname", " ");
		apiparamsMap.put("productnum", " ");
		apiparamsMap.put("is_suit", "0");
		apiparamsMap.put("pagesize", "200");
		apiparamsMap.put("pagenum", "1");
		// 获取数字签名
		String sign = md5Signature(apiparamsMap, secret);
		apiparamsMap.put("sign", sign);
		StringBuilder param = new StringBuilder();
		for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<String, String> e = it.next();
			if (e.getKey() != "appscret" && e.getKey() != "token") {
				param.append("&").append(e.getKey()).append("=")
						.append(encodeUri(e.getValue()));
			}
		}

		String PostData = "";
		PostData = param.toString().substring(1);
		// logger.info(testUrl+"?"+PostData);
		String result = "";
		result = getResult(testUrl, PostData);
		logger.info(result);
	}

	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		System.out
				.println("------------------------edb分销定单获取接口-------------------------------");
		String url = null;

		// String jsonStr = postHttpReq(url);
 		String date_type = String.valueOf(params.get("date_type"));
		String page_no = String.valueOf(params.get("page_no"));
		String page_size = String.valueOf(params.get("page_size"));
		
		String xml = edbTradeGet(start_time, end_time, date_type, page_no,page_size);
//		logger.info("xml>>>>>>>>>>>>>>>"+xml);
//		String xml = "                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  <items>                                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  	<totalResults>2</totalResults>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  	<totalResultsAll>2</totalResultsAll>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  	<Rows>                                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<tid>S140419000233</tid>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<out_tid>621788423172377</out_tid>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<order_channel>直营网店</order_channel>                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<order_from>淘宝</order_from>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<type>正常订单</type>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<status>未确认</status>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<abnormal_status/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<merge_status/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<delivery_status>未发货</delivery_status>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<platform_status>WAIT_BUYER_PAY</platform_status>                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<plat_type>淘宝</plat_type>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<pro_totalfee>63.00</pro_totalfee>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<order_totalfee>63.00</order_totalfee>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<tid_time>2014-04-19 16:22:08</tid_time>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<pay_time/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<get_time>2014-04-19 16:26:41</get_time>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<buyer_message/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<service_remarks>月费=46.00元,机主姓名=刘维全,赠送金额=15元包50分钟+250M,号码预存款=0.00元,返还金额=存63得252，次月起每月到账21元，银行托收每月再送10元,手机号码=18675064551,号码所属市=江门,套餐名称=46A套餐-上网型-联通沃3G,号码所属省=广东,身份证号码=440784199312243617,合约期=12个月,入网当月资费处理方式=收取半月租，套餐量减半</service_remarks>                               "
//				+ "  		<order_provinc_code>440000</order_provinc_code>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<order_city_code>440700</order_city_code>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<order_disfee>0.00</order_disfee>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_adv_sale>0</is_adv_sale>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<plat_distributor_code/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<alipay_id>15602885826                                       </alipay_id>                                                                                                                                                                                                                                                                                                             "
//				+ "  		<pay_mothed>alipay</pay_mothed>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<pay_status>未付款</pay_status>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<order_origfee>63.00</order_origfee>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<order_realfee>63.00</order_realfee>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<buyer_id>liuquan5354</buyer_id>                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<buyer_name>刘维全</buyer_name>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<receiver_name>刘维全</receiver_name>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<receiver_mobile>15602885826</receiver_mobile>                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<phone/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<province>广东省</province>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<city>江门市</city>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<district>鹤山市</district>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<address>广东省 江门市 鹤山市 雅瑶陈山高速路口对面（惠诚物流）(529700)</address>                                                                                                                                                                                                                                                                                                      "
//				+ "  		<post>529700</post>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<provinc_code>440000</provinc_code>                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<city_code>440700</city_code>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<area_code>440784</area_code>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<sending_type>快递</sending_type>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_bill>0</is_bill>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<invoice_title/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<invoice_print_type/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<recommended_name/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<recommended_code/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<recommended_phone/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<development_code/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<development_name/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<file_return_type/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<voucher_id/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<shop_name>广东联通天猫</shop_name>                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<shopid>1</shopid>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<storage_id>1</storage_id>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<reference_price_paid>63.00</reference_price_paid>                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<refund_totalfee/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<discount/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<channel_disfee/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<merchant_disfee/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<commission_fee>0.00</commission_fee>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<point_pay>0</point_pay>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<cost_point>0.00</cost_point>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<point/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<superior_point/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<royalty_fee/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<external_point>0.00</external_point>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<order_creater>edb_a70821</order_creater>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<business_man/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<payment_received_operator/>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<payment_received_time/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<review_orders_operator/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<review_orders_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<finance_review_operator/>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<finance_review_time/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<advance_printer/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<printer/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<print_time/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<is_print>0</is_print>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<adv_distributer/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<adv_distribut_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<distributer/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distribut_time/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_inspection>0</is_inspection>                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<inspecter/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<inspect_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<cancel_operator/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<cancel_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<revoke_cancel_er/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<revoke_cancel_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<packager/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<pack_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<weigh_operator/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<weigh_time/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<book_delivery_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<delivery_operator/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<delivery_time/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<locker/>                                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<lock_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<book_file_time/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<file_operator/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<file_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<finish_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<modity_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<is_promotion>0</is_promotion>                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<promotion_plan/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<out_promotion_detail/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<good_receive_time/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<receive_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<verificaty_time/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<enable_inte_sto_time/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<enable_inte_delivery_time>04 19 2014  4:26PM</enable_inte_delivery_time>                                                                                                                                                                                                                                                                                                             "
//				+ "  		<inner_lable/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distributor_mark/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<system_remarks/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<message/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<message_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<is_stock>0</is_stock>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<related_orders/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<related_orders_type/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<import_mark>未导入</import_mark>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_new_customer>0</is_new_customer>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<distributor_level/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<product_num>1</product_num>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<flag_color/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<is_flag>0</is_flag>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<rate/>                                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<currency/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<customer_id/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distributor_id/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<email/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<express_no/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<express>顺丰</express>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<express_coding>6</express_coding>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<online_express/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<real_income_freight>0.00</real_income_freight>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<real_pay_freight/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<gross_weight_freight/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<net_weight_freight/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<freight_explain/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<total_weight/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<tid_net_weight>0.00</tid_net_weight>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<last_returned_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<deliver_centre/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<deliver_station/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<is_pre_delivery_notice>0</is_pre_delivery_notice>                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<jd_delivery_time/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<invoice_name/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<invoice_type/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<invoice_content/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<invoice_fee/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<tid_item>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  			<Item>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<pro_detail_code>2157</pro_detail_code>                                                                                                                                                                                                                                                                                                                                           "
//				+ "  				<pro_name>广东/广州/深圳联通3g手机卡 3g流量卡 学生卡 电话卡</pro_name>                                                                                                                                                                                                                                                                                                            "
//				+ "  				<pro_type>69010101</pro_type>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<isgifts>0</isgifts>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<pro_num>1</pro_num>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<sell_price>63.0000</sell_price>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<pro_origfee>63.0000</pro_origfee>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<brand_number>GDLT</brand_number>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<brand_name>联通</brand_name>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<color_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<color_name/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<specification_code/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<specificatio_nname/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<model_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<model_name/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<terminal_num/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<phone_num/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<phone_owner_name/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  				<cert_type/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<cert_card_num/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<cert_failure_time/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<cert_address/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<white_cart_type/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<first_payment/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<collection>0</collection>                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  				<out_package_id>20140126132619026824</out_package_id>                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<plan_title/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<out_plan_id_ess/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<out_plan_id_bss/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<reliefpres_flag>0</reliefpres_flag>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<simid/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  				<product_net/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<act_protper/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<phone_deposit/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<famliy_num/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<adjustment_credit/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<superiors_bankcode/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<bank_code/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<bank_account/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<bank_user/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<is_iphone_plan>0</is_iphone_plan>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<is_loves_phone>0</is_loves_phone>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<loves_phone_num/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<is_liang>0</is_liang>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  				<liang_price/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<liang_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<is_stock_phone>0</is_stock_phone>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<ative_type/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<disfee_select/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<barcode>99104722</barcode>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<second_barcode>99104722</second_barcode>                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<sku/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  				<iscombination>0</iscombination>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<send_num/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<refund_num/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<refund_renum/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<inspection_num/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<item_discountfee>0.0000</item_discountfee>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<weight/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<using_state/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<phone_province_code>440000</phone_province_code>                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<phone_city_code/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<tid>S140419000204</tid>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  			</Item>                                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		</tid_item>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  	</Rows>                                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  	<Rows>                                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<tid>S140419000255</tid>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<out_tid>621982645232378</out_tid>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<order_channel>直营网店</order_channel>                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<order_from>淘宝</order_from>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<type>正常订单</type>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<status>未确认</status>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<abnormal_status/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<merge_status/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<delivery_status>未发货</delivery_status>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<platform_status>WAIT_BUYER_PAY</platform_status>                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<plat_type>淘宝</plat_type>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<pro_totalfee>63.00</pro_totalfee>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<order_totalfee>63.00</order_totalfee>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<tid_time>2014-04-19 16:20:19</tid_time>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<pay_time/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<get_time>2014-04-19 16:26:41</get_time>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<buyer_message/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<service_remarks>月费=46.00元,机主姓名=刘维全,赠送金额=15元包50分钟+250M,号码预存款=0.00元,返还金额=存63得252，次月起每月到账21元，银行托收每月再送10元,手机号码=18620877624,号码所属市=广州,套餐名称=46A套餐-上网型-联通沃3G,号码所属省=广东,身份证号码=440784199312243617,合约期=12个月,入网当月资费处理方式=收取半月租，套餐量减半</service_remarks>                               "
//				+ "  		<order_provinc_code>440000</order_provinc_code>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<order_city_code>440100</order_city_code>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<order_disfee>0.00</order_disfee>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_adv_sale>0</is_adv_sale>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<plat_distributor_code/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<alipay_id>15602885826                                       </alipay_id>                                                                                                                                                                                                                                                                                                             "
//				+ "  		<pay_mothed>alipay</pay_mothed>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<pay_status>未付款</pay_status>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<order_origfee>63.00</order_origfee>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<order_realfee>63.00</order_realfee>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<buyer_id>liuquan5354</buyer_id>                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<buyer_name>刘维全</buyer_name>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<receiver_name>刘维全</receiver_name>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<receiver_mobile>15602885826</receiver_mobile>                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<phone/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<province>广东省</province>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<city>江门市</city>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<district>鹤山市</district>                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<address>广东省 江门市 鹤山市 雅瑶陈山高速路口对面（惠诚物流）(529700)</address>                                                                                                                                                                                                                                                                                                      "
//				+ "  		<post>529700</post>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<provinc_code>440000</provinc_code>                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<city_code>440700</city_code>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<area_code>440784</area_code>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<sending_type>快递</sending_type>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_bill>0</is_bill>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<invoice_title/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<invoice_print_type/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<recommended_name/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<recommended_code/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<recommended_phone/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<development_code/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<development_name/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<file_return_type/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<voucher_id/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<shop_name>广东联通天猫</shop_name>                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<shopid>1</shopid>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<storage_id>1</storage_id>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<reference_price_paid>63.00</reference_price_paid>                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<refund_totalfee/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<discount/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<channel_disfee/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<merchant_disfee/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<commission_fee>0.00</commission_fee>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<point_pay>0</point_pay>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<cost_point>0.00</cost_point>                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<point/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<superior_point/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<royalty_fee/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<external_point>0.00</external_point>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<order_creater>edb_a70821</order_creater>                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<business_man/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<payment_received_operator/>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<payment_received_time/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<review_orders_operator/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<review_orders_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<finance_review_operator/>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<finance_review_time/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<advance_printer/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<printer/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<print_time/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<is_print>0</is_print>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<adv_distributer/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<adv_distribut_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<distributer/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distribut_time/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_inspection>0</is_inspection>                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<inspecter/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<inspect_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<cancel_operator/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<cancel_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<revoke_cancel_er/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<revoke_cancel_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<packager/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<pack_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<weigh_operator/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<weigh_time/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<book_delivery_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<delivery_operator/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<delivery_time/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<locker/>                                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		<lock_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<book_file_time/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<file_operator/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  		<file_time/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<finish_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<modity_time/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<is_promotion>0</is_promotion>                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<promotion_plan/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<out_promotion_detail/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<good_receive_time/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<receive_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<verificaty_time/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<enable_inte_sto_time/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<enable_inte_delivery_time>04 19 2014  4:26PM</enable_inte_delivery_time>                                                                                                                                                                                                                                                                                                             "
//				+ "  		<inner_lable/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distributor_mark/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<system_remarks/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<message/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  		<message_time/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<is_stock>0</is_stock>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<related_orders/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<related_orders_type/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  		<import_mark>未导入</import_mark>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<is_new_customer>0</is_new_customer>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<distributor_level/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<product_num>1</product_num>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  		<flag_color/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<is_flag>0</is_flag>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  		<rate/>                                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<currency/>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  		<customer_id/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<distributor_id/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<email/>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  		<express_no/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  		<express>顺丰</express>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<express_coding>6</express_coding>                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<online_express/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<real_income_freight>0.00</real_income_freight>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<real_pay_freight/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<gross_weight_freight/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  		<net_weight_freight/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<freight_explain/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<total_weight/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<tid_net_weight>0.00</tid_net_weight>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<last_returned_time/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  		<deliver_centre/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  		<deliver_station/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<is_pre_delivery_notice>0</is_pre_delivery_notice>                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<jd_delivery_time/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  		<invoice_name/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<invoice_type/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  		<invoice_content/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  		<invoice_fee/>                                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  		<tid_item>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  			<Item>                                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<pro_detail_code>2157</pro_detail_code>                                                                                                                                                                                                                                                                                                                                           "
//				+ "  				<pro_name>广东/广州/深圳联通3g手机卡 3g流量卡 学生卡 电话卡</pro_name>                                                                                                                                                                                                                                                                                                            "
//				+ "  				<pro_type>69010101</pro_type>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<isgifts>0</isgifts>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<pro_num>1</pro_num>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<sell_price>63.0000</sell_price>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<pro_origfee>63.0000</pro_origfee>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<brand_number>GDLT</brand_number>                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<brand_name>联通</brand_name>                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<color_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<color_name/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<specification_code/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<specificatio_nname/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<model_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<model_name/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<terminal_num/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<phone_num/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<phone_owner_name/>                                                                                                                                                                                                                                                                                                                                                               "
//				+ "  				<cert_type/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<cert_card_num/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<cert_failure_time/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<cert_address/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<white_cart_type/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<first_payment/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<collection>0</collection>                                                                                                                                                                                                                                                                                                                                                        "
//				+ "  				<out_package_id>20140126132619026824</out_package_id>                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<plan_title/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<out_plan_id_ess/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<out_plan_id_bss/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<reliefpres_flag>0</reliefpres_flag>                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<simid/>                                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  				<product_net/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<act_protper/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<phone_deposit/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<famliy_num/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<adjustment_credit/>                                                                                                                                                                                                                                                                                                                                                              "
//				+ "  				<superiors_bankcode/>                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  				<bank_code/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<bank_account/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<bank_user/>                                                                                                                                                                                                                                                                                                                                                                      "
//				+ "  				<is_iphone_plan>0</is_iphone_plan>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<is_loves_phone>0</is_loves_phone>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<loves_phone_num/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<is_liang>0</is_liang>                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  				<liang_price/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<liang_code/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<is_stock_phone>0</is_stock_phone>                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<ative_type/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<disfee_select/>                                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<barcode>99104722</barcode>                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<second_barcode>99104722</second_barcode>                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<sku/>                                                                                                                                                                                                                                                                                                                                                                            "
//				+ "  				<iscombination>0</iscombination>                                                                                                                                                                                                                                                                                                                                                  "
//				+ "  				<send_num/>                                                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<refund_num/>                                                                                                                                                                                                                                                                                                                                                                     "
//				+ "  				<refund_renum/>                                                                                                                                                                                                                                                                                                                                                                   "
//				+ "  				<inspection_num/>                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<item_discountfee>0.0000</item_discountfee>                                                                                                                                                                                                                                                                                                                                       "
//				+ "  				<weight/>                                                                                                                                                                                                                                                                                                                                                                         "
//				+ "  				<using_state/>                                                                                                                                                                                                                                                                                                                                                                    "
//				+ "  				<phone_province_code>440000</phone_province_code>                                                                                                                                                                                                                                                                                                                                 "
//				+ "  				<phone_city_code/>                                                                                                                                                                                                                                                                                                                                                                "
//				+ "  				<tid>S140419000205</tid>                                                                                                                                                                                                                                                                                                                                                          "
//				+ "  			</Item>                                                                                                                                                                                                                                                                                                                                                                             "
//				+ "  		</tid_item>                                                                                                                                                                                                                                                                                                                                                                           "
//				+ "  	</Rows>                                                                                                                                                                                                                                                                                                                                                                                 "
//				+ "  </items>                                                                                                                                                                                                                                                                                                                                                                                 ";

		SAXReader saxReader = new SAXReader();
		Document document;
		List<Outer> outer_orders = new ArrayList<Outer>();
		//OutterECSTmplManager outterECSTmplManager = new OutterECSTmplManager();
		try {

			document = saxReader.read(new ByteArrayInputStream(xml
					.getBytes("utf-8")));
			Element e_root = document.getRootElement();
			// logger.info("totalresults : "+e_root.elementText("totalresults"));
			Element e_totalResultsAll = e_root.element("totalResultsAll");
			Integer total_page_no = Integer.valueOf(e_totalResultsAll.getText())/Integer.valueOf(page_size);
			// logger.info("totalresultsall : "+e_totalresults.getText());
			List l_rows = e_root.elements("Rows");

			for (int i = 0; i < l_rows.size(); i++) {
				Outer o = new Outer();
				Element e_rows = (Element) l_rows.get(i);
				logger.info("tid : " + e_rows.elementText("tid"));
				String out_tid = getVal(e_rows.elementText("out_tid"));
				o.setOut_tid(out_tid);
				logger.info("out_tid:"+e_rows.elementText("out_tid"));
				o.setOrder_channel(getVal(e_rows.elementText("order_channel")));
//				o.setOrder_from("淘宝分销");
				String inorder_from = getVal(e_rows.elementText("order_from"));
				String sqlOrderFrom = "Select Count(1) record_num From ES_EDB_ORDER_TYPE a WHERE a.order_from = '"+inorder_from+"'";
				String record_num = orderServiceLocal.queryString(sqlOrderFrom);
				if(!"0".equalsIgnoreCase(record_num)){
					o.setOrder_from(inorder_from);
					o.setType(getVal(e_rows.elementText("type")));
					o.setStatus(getVal(e_rows.elementText("status")));
					o.setAbnormal_status(getVal(e_rows
							.elementText("abnormal_status")));
					o.setMerge_status(getVal(e_rows.elementText("merge_status")));
					o.setDelivery_status(getVal(e_rows
							.elementText("delivery_status")));
					o.setPlatform_status(getVal(e_rows
							.elementText("platform_status")));
					o.setPlat_type(getVal(e_rows.elementText("plat_type")));
					o.setPro_totalfee(getVal(e_rows.elementText("pro_totalfee")));
					// o.setPro_totalfee("100");
					o.setOrder_totalfee(getVal(e_rows.elementText("order_totalfee")));
					o.setTid_time(getVal(e_rows.elementText("tid_time")));
					o.setPay_time(getVal(e_rows.elementText("pay_time")));
					o.setGet_time(getVal(e_rows.elementText("get_time")));
					o.setBuyer_message(getVal(e_rows.elementText("buyer_message")));
					o.setService_remarks(getVal(e_rows
							.elementText("service_remarks")));
					o.setOrder_provinc_code(e_rows
							.elementText("order_provinc_code"));
					o.setOrder_city_code(e_rows.elementText("order_city_code"));
					o.setOrder_disfee(getVal(e_rows.elementText("order_disfee")));
					o.setIs_adv_sale(getVal(e_rows.elementText("is_adv_sale")));
					o.setPlat_distributor_code(getVal(e_rows
							.elementText("plat_distributor_code")));
					o.setAlipay_id(getVal(e_rows.elementText("alipay_id")));
					o.setPay_method(getVal(e_rows.elementText("pay_mothed")));
					o.setPay_status(getVal(e_rows.elementText("pay_status")));
					o.setOrder_origfee(getVal(e_rows.elementText("order_origfee")));

					o.setOrder_realfee(getVal(e_rows.elementText("order_realfee")));
					o.setBuyer_id(getVal(e_rows.elementText("buyer_id")));
					o.setBuyer_name(getVal(e_rows.elementText("buyer_name")));
					o.setReceiver_name(getVal(e_rows.elementText("receiver_name")));
					o.setReceiver_mobile(getVal(e_rows
							.elementText("receiver_mobile")));
					o.setPhone(getVal(e_rows.elementText("phone")));
					o.setProvince(getVal(e_rows.elementText("province")));
					o.setCity(getVal(e_rows.elementText("city")));
					o.setDistrict(getVal(e_rows.elementText("district")));

					o.setAddress(getVal(e_rows.elementText("address")));
					o.setPost(getVal(e_rows.elementText("post")));
					o.setProvinc_code(getVal(e_rows.elementText("provinc_code")));
					o.setCity_code(getVal(e_rows.elementText("city_code")));
					o.setArea_code(getVal(e_rows.elementText("area_code")));
					o.setSending_type(getVal(e_rows.elementText("sending_type")));
					o.setIs_bill(getVal(e_rows.elementText("is_bill")));
					o.setInvoice_title(getVal(e_rows.elementText("invoice_title")));
					o.setInvoice_print_type(getVal(e_rows
							.elementText("invoice_print_type")));
					o.setRecommended_name(getVal(e_rows
							.elementText("recommended_name")));
					o.setRecommended_code(getVal(e_rows
							.elementText("recommended_code")));
					o.setRecommended_phone(getVal(e_rows
							.elementText("recommended_phone")));
					o.setDevelopment_code(getVal(e_rows
							.elementText("development_code")));
					o.setDevelopment_name(getVal(e_rows
							.elementText("development_name")));
					o.setFile_return_type(getVal(e_rows
							.elementText("file_return_type")));

					// o.setVoucher_id(e_rows.elementText("voucher_id"));缺失

					/*
					 * o.setShop_name(e_rows.elementText("shop_name"));
					 * o.setShopid(e_rows.elementText("shopid"));
					 * o.setStorage_id(e_rows.elementText("storage_id"));
					 * o.setReference_price_paid
					 * (e_rows.elementText("reference_price_paid"));
					 * o.setRefund_totalfee(e_rows.elementText("refund_totalfee"));
					 * o.setDiscount(e_rows.elementText("discount"));
					 * o.setChannel_disfee(e_rows.elementText("channel_disfee"));
					 * o.setMerchant_disfee(e_rows.elementText("merchant_disfee")));
					 * o.setCommission_fee(e_rows.elementText("commission_fee"));
					 * o.setPoint_pay(e_rows.elementText("point_pay"));
					 * o.setCost_point(e_rows.elementText("cost_point"));
					 * o.setPoint(e_rows.elementText("point"));
					 * o.setSuperior_point(e_rows.elementText("superior_point"));
					 * o.setRoyalty_fee(e_rows.elementText("royalty_fee"));
					 * o.setExternal_point(e_rows.elementText("external_point"));
					 * o.setOrder_creater(e_rows.elementText("order_creater"));
					 * o.setBusiness_man(e_rows.elementText("business_man"));
					 * o.setPayment_received_operator
					 * (e_rows.elementText("payment_received_operator"));
					 * o.setPayment_received_time
					 * (e_rows.elementText("payment_received_time"));
					 * o.setReview_orders_operator
					 * (e_rows.elementText("review_orders_operator"));
					 * o.setReview_orders_time
					 * (e_rows.elementText("review_orders_time"));
					 * o.setFinance_review_operator
					 * (e_rows.elementText("finance_review_operator"));
					 * o.setFinance_review_time
					 * (e_rows.elementText("finance_review_time"));
					 * o.setAdvance_printer(e_rows.elementText("advance_printer"));
					 * o.setPrinter(e_rows.elementText("printer"));
					 * o.setPrint_time(e_rows.elementText("print_time"));
					 * o.setIs_print(e_rows.elementText("is_print"));
					 * o.setAdv_distributer(e_rows.elementText("adv_distributer"));
					 * o
					 * .setAdv_distribut_time(e_rows.elementText("adv_distribut_time"
					 * )); o.setDistributer(e_rows.elementText("distributer"));
					 * o.setDistribut_time(e_rows.elementText("distribut_time"));
					 * o.setIs_inspection(e_rows.elementText("is_inspection"));
					 * o.setInspecter(e_rows.elementText("inspecter"));
					 * o.setInspect_time(e_rows.elementText("inspect_time"));
					 * o.setCancel_operator(e_rows.elementText("cancel_operator"));
					 * o.setCancel_time(e_rows.elementText("cancel_time"));
					 * o.setRevoke_cancel_er
					 * (e_rows.elementText("revoke_cancel_er"));
					 * o.setRevoke_cancel_time
					 * (e_rows.elementText("revoke_cancel_time"));
					 * o.setPackager(e_rows.elementText("packager"));
					 * o.setPack_time(e_rows.elementText("pack_time"));
					 * o.setWeigh_operator(e_rows.elementText("weigh_operator"));
					 * o.setWeigh_time(e_rows.elementText("weigh_time"));
					 * o.setBook_delivery_time
					 * (e_rows.elementText("book_delivery_time"));
					 * o.setDelivery_operator
					 * (e_rows.elementText("delivery_operator"));
					 * o.setDelivery_time(e_rows.elementText("delivery_time"));
					 * o.setLocker(e_rows.elementText("locker"));
					 * o.setLock_time(e_rows.elementText("lock_time"));
					 * o.setBook_file_time(e_rows.elementText("book_file_time"));
					 * o.setFile_operator(e_rows.elementText("file_operator"));
					 * o.setFile_time(e_rows.elementText("file_time"));
					 * o.setFinish_time(e_rows.elementText("finish_time"));
					 * o.setModity_time(e_rows.elementText("modity_time"));
					 * o.setIs_promotion(e_rows.elementText("is_promotion"));
					 * o.setPromotion_plan(e_rows.elementText("promotion_plan"));
					 * o.setOut_promotion_detail
					 * (e_rows.elementText("out_promotion_detail"));
					 * o.setGood_receive_time
					 * (e_rows.elementText("good_receive_time"));
					 * o.setReceive_time(e_rows.elementText("receive_time"));
					 * o.setVerificaty_time(e_rows.elementText("verificaty_time"));
					 * o.setEnable_inte_sto_time(e_rows.elementText(
					 * "enable_inte_sto_time"));
					 * o.setEnable_inte_delivery_time(e_rows
					 * .elementText("enable_inte_delivery_time"));
					 * o.setInner_lable(e_rows.elementText("inner_lable"));
					 * o.setDistributor_mark
					 * (e_rows.elementText("distributor_mark"));
					 * o.setSystem_remarks(e_rows.elementText("system_remarks"));
					 * o.setMessage(e_rows.elementText("message"));
					 * o.setMessage_time(e_rows.elementText("message_time"));
					 * o.setIs_stock(e_rows.elementText("is_stock"));
					 * o.setRelated_orders(e_rows.elementText("related_orders"));
					 * o.setRelated_orders_type
					 * (e_rows.elementText("related_orders_type"));
					 * o.setImport_mark(e_rows.elementText("import_mark"));
					 * o.setIs_new_customer(e_rows.elementText("is_new_customer"));
					 * o
					 * .setDistributor_level(e_rows.elementText("distributor_level")
					 * ); o.setProduct_num(e_rows.elementText("product_num"));
					 * o.setFlag_color(e_rows.elementText("flag_color"));
					 * o.setIs_flag(e_rows.elementText("is_flag"));
					 * o.setRate(e_rows.elementText("rate"));
					 * o.setCurrency(e_rows.elementText("currency"));
					 * o.setCustomer_id(e_rows.elementText("customer_id"));
					 * o.setDistributor_id(e_rows.elementText("distributor_id"));
					 * o.setEmail(e_rows.elementText("email"));
					 * o.setExpress_no(e_rows.elementText("express_no"));
					 * o.setExpress(e_rows.elementText("express"));
					 * o.setExpress_coding(e_rows.elementText("express_coding"));
					 * o.setOnline_express(e_rows.elementText("online_express"));
					 * o.setReal_income_freight
					 * (e_rows.elementText("real_income_freight"));
					 * o.setReal_pay_freight
					 * (e_rows.elementText("real_pay_freight"));
					 * o.setGross_weight_freight
					 * (e_rows.elementText("gross_weight_freight"));
					 * o.setNet_weight_freight
					 * (e_rows.elementText("net_weight_freight"));
					 * o.setFreight_explain(e_rows.elementText("freight_explain"));
					 * o.setTotal_weight(e_rows.elementText("total_weight"));
					 * o.setTid_net_weight(e_rows.elementText("tid_net_weight"));
					 * o.setLast_returned_time
					 * (e_rows.elementText("last_returned_time"));
					 * o.setDeliver_centre(e_rows.elementText("deliver_centre"));
					 * o.setDeliver_station(e_rows.elementText("deliver_station"));
					 * o.setIs_pre_delivery_notice(e_rows.elementText(
					 * "is_pre_delivery_notice"));
					 * o.setJd_delivery_time(e_rows.elementText
					 * ("jd_delivery_time"));
					 * 
					 * 
					 * 
					 * o.setInvoice_name(e_rows.elementText("invoice_name"));
					 * o.setInvoice_type(e_rows.elementText("invoice_type"));
					 * o.setInvoice_content(e_rows.elementText("invoice_content"));
					 * o.setSetInvoice_fee(e_rows.elementText("invoice_fee"));
					 */

					Element e_tid_item = e_rows.element("tid_item");
					List l_item = e_tid_item.elements("Item");
					List<OuterItem> items = new ArrayList<OuterItem>();
					for (int j = 0; j < l_item.size(); j++) {
						OuterItem item = new OuterItem();
						Element e_item = (Element) l_item.get(j);
						logger.info("out_package_id>>>>>>>>>>>>>>"+e_item.elementText("out_package_id"));
						String pagkage_id = e_item.elementText("out_package_id");
						String outer_sku_id = e_item.elementText("sku");
						String pro_name = e_item.elementText("pro_name");
						Map<String, String> param = new HashMap<String, String>();
						if (!StringUtils.isEmpty(pagkage_id)
								|| !StringUtils.isEmpty(outer_sku_id)) {
							// ZteClient client =
							// ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
							GoodsGetReq req = new GoodsGetReq();
							req.setPackage_id(pagkage_id);
							req.setSn(outer_sku_id);
							GoodsGetResp resp = null;
							try{
								resp = goodServices.getGoods(req);// client.execute(req,
								// GoodsGetResp.class);//goodServices.getGoods(req);//
							}catch(Exception ex){
								OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","goodserror");//noparam  nogoods
								ng.setDeal_flag("-1");
								outterECSTmplManager.insertOuterError(ng);
								logger.info("获取商品信息错误");
								continue;
							}
							
							if ("0".equals(resp.getError_code())) {
								param = resp.getData();
								// item.setPro_detail_code(e_item.elementText("pro_detail_code"));
								item.setPro_name(getVal(e_item.elementText("pro_name")));
								item.setPro_type(getVal(e_item.elementText("pro_type")));
								item.setIsgifts(getVal(e_item.elementText("isgifts")));
								item.setSell_price(Double.valueOf(e_item.elementText("sell_price")));
								item.setPro_origfee(Double.valueOf(e_item.elementText("pro_origfee")));
								item.setBrand_number(getVal(e_item
										.elementText("brand_number")));
								item.setBrand_name(getVal(e_item.elementText("brand_name")));
								item.setColor_code(getVal(e_item.elementText("color_code")));
								item.setColor_name(getVal(e_item.elementText("color_name")));
								item.setSpecification_code(getVal(e_item
										.elementText("specification_code")));
								item.setSpecificatio_nname(getVal(e_item
										.elementText("specificatio_nname")));
								item.setModel_code(getVal(e_item.elementText("model_code")));
								item.setModel_name(getVal(e_item.elementText("model_name")));
								item.setTerminal_num(getVal(e_item
										.elementText("terminal_num")));
								item.setPhone_num(getVal(e_item.elementText("phone_num")));
								item.setPhone_owner_name(getVal(e_item
										.elementText("phone_owner_name")));
								item.setCert_type(getVal(e_item.elementText("cert_type")));
								item.setCert_card_num(getVal(e_item
										.elementText("cert_card_num")));
								item.setCert_failure_time(getVal(e_item
										.elementText("cert_failure_time")));
								item.setCert_address(getVal(e_item
										.elementText("cert_address")));
								item.setWhite_cart_type(getVal(e_item
										.elementText("white_cart_type")));
								item.setFirst_payment(getVal(e_item
										.elementText("first_payment")));
								item.setCollection(getVal(e_item.elementText("collection")));
								item.setOut_package_id(getVal(e_item
										.elementText("out_package_id")));
								item.setPlan_title(getVal(e_item.elementText("plan_title")));
								item.setOut_plan_id_ess(getVal(e_item
										.elementText("out_plan_id_ess")));
								item.setOut_plan_id_bss(getVal(e_item
										.elementText("out_plan_id_bss")));

								item.setReliefpres_flag(getVal(e_item
										.elementText("reliefpres_flag")));
								item.setSimid(getVal(e_item.elementText("simid")));
								item.setProduct_net(getVal(e_item
										.elementText("product_net")));
								item.setAct_protper(getVal(e_item
										.elementText("act_protper")));
								item.setPhone_deposit(getVal(e_item
										.elementText("phone_deposit")));
								item.setFamliy_num(getVal(e_item.elementText("famliy_num")));
								item.setAdjustment_credit(getVal(e_item
										.elementText("adjustment_credit")));
								item.setAdjustment_credit(getVal(e_item
										.elementText("superiors_bankcode")));
								item.setBank_code(getVal(e_item.elementText("bank_code")));
								item.setBank_account(getVal(e_item
										.elementText("bank_account")));
								item.setBank_user(getVal(e_item.elementText("bank_user")));
								item.setIs_iphone_plan(getVal(e_item
										.elementText("is_iphone_plan")));
								item.setIs_loves_phone(getVal(e_item
										.elementText("is_loves_phone")));
								item.setLoves_phone_num(getVal(e_item
										.elementText("loves_phone_num")));
								item.setIs_liang(getVal(e_item.elementText("is_liang")));
								// item.setLiang_price(e_item.elementText("liang_price"));
								item.setLiang_code(getVal(e_item.elementText("liang_code")));
								item.setIs_stock_phone(getVal(e_item
										.elementText("is_stock_phone")));
								item.setAtive_type(getVal(e_item.elementText("ative_type")));

								item.setPro_num(Integer.valueOf(e_item
										.elementText("pro_num")));
								packageGoodsParam(item, param);

								/*
								 * item.setBarcode(e_item.elementText("barcode"));
								 * item.setSecond_barcode
								 * (e_item.elementText("second_barcode"));
								 * item.setSku(e_item.elementText("sku"));
								 * item.setIscombination
								 * (e_item.elementText("iscombination"));
								 * item.setSend_num(e_item.elementText("send_num"));
								 * item.setRefund_num(e_item.elementText("refund_num"));
								 * item.setRefund_renum(e_item.elementText("refund_renum"));
								 * item
								 * .setInspection_num(e_item.elementText("inspection_num"));
								 * item
								 * .setItem_discountfee(e_item.elementText("item_discountfee"
								 * )); item.setWeight(e_item.elementText("weight"));
								 * item.setUsing_state(e_item.elementText("using_state"));
								 * item.setPhone_province_code(e_item.elementText(
								 * "phone_province_code"));
								 * item.setPhone_city_code(e_item.elementText
								 * ("phone_city_code"));
								 */
								items.add(item);
							} else {
								OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","nogoods");//noparam  nogoods
								ng.setDeal_flag("-1");
								outterECSTmplManager.insertOuterError(ng);
								logger.info("电商没有配置商品=====");
							}
						} else {
							// 没有相应的字段查询商品
							OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","noparam");//noparam  nogoods
							ng.setDeal_flag("-1");
							outterECSTmplManager.insertOuterError(ng);
							logger.info("没有商品相关参数======================");
						}

						
					}
					o.setItemList(items);
					outer_orders.add(o);
				}
				
			}
			
			for(int i = (Integer.valueOf(page_no)+1); i <= (total_page_no+1);i++){
				getMore(outer_orders,start_time,end_time,String.valueOf(i),params,order_from);
			}
			
			

//			// for test
//			for (int i = 0; i < outer_orders.size(); i++) {
//				Outer o = (Outer) outer_orders.get(i);
//				// if("3796162478232".equalsIgnoreCase(o.getOut_tid()) ||
//				// "3899077944189".equalsIgnoreCase(o.getOut_tid())
//				// ||"3789453132509".equalsIgnoreCase(o.getOut_tid()) ||
//				// "3190912876372".equalsIgnoreCase(o.getOut_tid())){
//				// logger.info(o.getOut_tid());
//				// }
//				OuterItem item = o.getItemList().get(0);
//				logger.info(item.getPro_type());
//
//			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outer_orders;
	}
	public void getMore(List<Outer> outer_orders,String start_time, String end_time,String page_no,
			Map params, String order_from) throws Exception {



		String date_type = String.valueOf(params.get("date_type"));
		String page_size = String.valueOf(params.get("page_size"));
		String xml = edbTradeGet(start_time, end_time, date_type, page_no,page_size);

		SAXReader saxReader = new SAXReader();
		Document document;

		try {

			document = saxReader.read(new ByteArrayInputStream(xml
					.getBytes("utf-8")));
			Element e_root = document.getRootElement();
			// logger.info("totalresults : "+e_root.elementText("totalresults"));
			Element e_totalresults = e_root.element("totalresultsall");
			// logger.info("totalresultsall : "+e_totalresults.getText());
			List l_rows = e_root.elements("Rows");

			for (int i = 0; i < l_rows.size(); i++) {
				Outer o = new Outer();
				Element e_rows = (Element) l_rows.get(i);
				logger.info("tid : " + e_rows.elementText("tid"));
				String out_tid = getVal(e_rows.elementText("out_tid"));
				o.setOut_tid(out_tid);
				o.setOrder_channel(getVal(e_rows.elementText("order_channel")));
//				o.setOrder_from("edb");
				
				String inorder_from = getVal(e_rows.elementText("order_from"));
				String sqlOrderFrom = "Select Count(1) record_num From ES_EDB_ORDER_TYPE a WHERE a.order_from = '"+inorder_from+"'";
				String record_num = orderServiceLocal.queryString(sqlOrderFrom);
				if(!"0".equalsIgnoreCase(record_num)){
					o.setOrder_from(inorder_from);
					o.setType(getVal(e_rows.elementText("type")));
				o.setStatus(getVal(e_rows.elementText("status")));
				o.setAbnormal_status(getVal(e_rows
						.elementText("abnormal_status")));
				o.setMerge_status(getVal(e_rows.elementText("merge_status")));
				o.setDelivery_status(getVal(e_rows
						.elementText("delivery_status")));
				o.setPlatform_status(getVal(e_rows
						.elementText("platform_status")));
				o.setPlat_type(getVal(e_rows.elementText("plat_type")));
				o.setPro_totalfee(getVal(e_rows.elementText("pro_totalfee")));
				// o.setPro_totalfee("100");
				o.setOrder_totalfee(getVal(e_rows.elementText("order_totalfee")));
				o.setTid_time(getVal(e_rows.elementText("tid_time")));
				o.setPay_time(getVal(e_rows.elementText("pay_time")));
				o.setGet_time(getVal(e_rows.elementText("get_time")));
				o.setBuyer_message(getVal(e_rows.elementText("buyer_message")));
				o.setService_remarks(getVal(e_rows
						.elementText("service_remarks")));
				o.setOrder_provinc_code(e_rows
						.elementText("order_provinc_code"));
				o.setOrder_city_code(e_rows.elementText("order_city_code"));
				o.setOrder_disfee(getVal(e_rows.elementText("order_disfee")));
				o.setIs_adv_sale(getVal(e_rows.elementText("is_adv_sale")));
				o.setPlat_distributor_code(getVal(e_rows
						.elementText("plat_distributor_code")));
				o.setAlipay_id(getVal(e_rows.elementText("alipay_id")));
				o.setPay_method(getVal(e_rows.elementText("pay_mothed")));
				o.setPay_status(getVal(e_rows.elementText("pay_status")));
				o.setOrder_origfee(getVal(e_rows.elementText("order_origfee")));

				o.setOrder_realfee(getVal(e_rows.elementText("order_realfee")));
				o.setBuyer_id(getVal(e_rows.elementText("buyer_id")));
				o.setBuyer_name(getVal(e_rows.elementText("buyer_name")));
				o.setReceiver_name(getVal(e_rows.elementText("receiver_name")));
				o.setReceiver_mobile(getVal(e_rows
						.elementText("receiver_mobile")));
				o.setPhone(getVal(e_rows.elementText("phone")));
				o.setProvince(getVal(e_rows.elementText("province")));
				o.setCity(getVal(e_rows.elementText("city")));
				o.setDistrict(getVal(e_rows.elementText("district")));

				o.setAddress(getVal(e_rows.elementText("address")));
				o.setPost(getVal(e_rows.elementText("post")));
				o.setProvinc_code(getVal(e_rows.elementText("provinc_code")));
				o.setCity_code(getVal(e_rows.elementText("city_code")));
				o.setArea_code(getVal(e_rows.elementText("area_code")));
				o.setSending_type(getVal(e_rows.elementText("sending_type")));
				o.setIs_bill(getVal(e_rows.elementText("is_bill")));
				o.setInvoice_title(getVal(e_rows.elementText("invoice_title")));
				o.setInvoice_print_type(getVal(e_rows
						.elementText("invoice_print_type")));
				o.setRecommended_name(getVal(e_rows
						.elementText("recommended_name")));
				o.setRecommended_code(getVal(e_rows
						.elementText("recommended_code")));
				o.setRecommended_phone(getVal(e_rows
						.elementText("recommended_phone")));
				o.setDevelopment_code(getVal(e_rows
						.elementText("development_code")));
				o.setDevelopment_name(getVal(e_rows
						.elementText("development_name")));
				o.setFile_return_type(getVal(e_rows
						.elementText("file_return_type")));


				Element e_tid_item = e_rows.element("tid_item");
				List l_item = e_tid_item.elements("Item");
				List<OuterItem> items = new ArrayList<OuterItem>();
				for (int j = 0; j < l_item.size(); j++) {
					OuterItem item = new OuterItem();
					Element e_item = (Element) l_item.get(j);
					logger.info("out_package_id>>>>>>>>>>>"+e_item.elementText("out_package_id"));
					String pagkage_id = e_item.elementText("out_package_id");
					String outer_sku_id = e_item.elementText("sku");
					String pro_name = getVal(e_item.elementText("pro_name"));
					Map<String, String> param = new HashMap<String, String>();
					if (!StringUtils.isEmpty(pagkage_id)
							|| !StringUtils.isEmpty(outer_sku_id)) {
						// ZteClient client =
						// ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
						GoodsGetReq req = new GoodsGetReq();
						req.setPackage_id(pagkage_id);
						req.setSn(outer_sku_id);
						GoodsGetResp resp = null;
						
						try{
							resp = goodServices.getGoods(req);// client.execute(req,
						}catch(Exception ex){
							OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","goodserror");//noparam  nogoods
							ng.setDeal_flag("-1");
							outterECSTmplManager.insertOuterError(ng);
							logger.info("获取商品相关参数失败======================");
							continue;
						}
						if ("0".equals(resp.getError_code())) {
							param = resp.getData();
							// item.setPro_detail_code(e_item.elementText("pro_detail_code"));
							item.setPro_name(pro_name);
							item.setPro_type(getVal(e_item.elementText("pro_type")));
							item.setIsgifts(getVal(e_item.elementText("isgifts")));
							// item.setPro_num(e_item.elementText("pro_num"));
							// item.setSell_price(e_item.elementText("sell_price"));
							// item.setPro_origfee(e_item.elementText("pro_origfee"));
							item.setBrand_number(getVal(e_item
									.elementText("brand_number")));
							item.setBrand_name(getVal(e_item.elementText("brand_name")));
							item.setColor_code(getVal(e_item.elementText("color_code")));
							item.setColor_name(getVal(e_item.elementText("color_name")));
							item.setSpecification_code(getVal(e_item
									.elementText("specification_code")));
							item.setSpecificatio_nname(getVal(e_item
									.elementText("specificatio_nname")));
							item.setModel_code(getVal(e_item.elementText("model_code")));
							item.setModel_name(getVal(e_item.elementText("model_name")));
							item.setTerminal_num(getVal(e_item
									.elementText("terminal_num")));
							item.setPhone_num(getVal(e_item.elementText("phone_num")));
							item.setPhone_owner_name(getVal(e_item
									.elementText("phone_owner_name")));
							item.setCert_type(getVal(e_item.elementText("cert_type")));
							item.setCert_card_num(getVal(e_item
									.elementText("cert_card_num")));
							item.setCert_failure_time(getVal(e_item
									.elementText("cert_failure_time")));
							item.setCert_address(getVal(e_item
									.elementText("cert_address")));
							item.setWhite_cart_type(getVal(e_item
									.elementText("white_cart_type")));
							item.setFirst_payment(getVal(e_item
									.elementText("first_payment")));
							item.setCollection(getVal(e_item.elementText("collection")));
							item.setOut_package_id(getVal(e_item
									.elementText("out_package_id")));
							item.setPlan_title(getVal(e_item.elementText("plan_title")));
							item.setOut_plan_id_ess(getVal(e_item
									.elementText("out_plan_id_ess")));
							item.setOut_plan_id_bss(getVal(e_item
									.elementText("out_plan_id_bss")));

							item.setReliefpres_flag(getVal(e_item
									.elementText("reliefpres_flag")));
							item.setSimid(getVal(e_item.elementText("simid")));
							item.setProduct_net(getVal(e_item
									.elementText("product_net")));
							item.setAct_protper(getVal(e_item
									.elementText("act_protper")));
							item.setPhone_deposit(getVal(e_item
									.elementText("phone_deposit")));
							item.setFamliy_num(getVal(e_item.elementText("famliy_num")));
							item.setAdjustment_credit(getVal(e_item
									.elementText("adjustment_credit")));
							item.setAdjustment_credit(getVal(e_item
									.elementText("superiors_bankcode")));
							item.setBank_code(getVal(e_item.elementText("bank_code")));
							item.setBank_account(getVal(e_item
									.elementText("bank_account")));
							item.setBank_user(getVal(e_item.elementText("bank_user")));
							item.setIs_iphone_plan(getVal(e_item
									.elementText("is_iphone_plan")));
							item.setIs_loves_phone(getVal(e_item
									.elementText("is_loves_phone")));
							item.setLoves_phone_num(getVal(e_item
									.elementText("loves_phone_num")));
							item.setIs_liang(getVal(e_item.elementText("is_liang")));
							// item.setLiang_price(e_item.elementText("liang_price"));
							item.setLiang_code(getVal(e_item.elementText("liang_code")));
							item.setIs_stock_phone(getVal(e_item
									.elementText("is_stock_phone")));
							item.setAtive_type(getVal(e_item.elementText("ative_type")));
							item.setOuter_sku_id(getVal(outer_sku_id));

							item.setPro_num(Integer.valueOf(e_item
									.elementText("pro_num")));

							packageGoodsParam(item, param);

							items.add(item);
						} else {
							OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","nogoods");//noparam  nogoods
							ng.setDeal_flag("-1");
							outterECSTmplManager.insertOuterError(ng);
							logger.info("电商没有配置商品=====");
						}
					} else {
						// 没有相应的字段查询商品
						OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, pro_name, order_from, out_tid, "sysdate","noparam");//noparam  nogoods
						ng.setDeal_flag("-1");
						outterECSTmplManager.insertOuterError(ng);
						logger.info("没有商品相关参数======================");
					}
					// item.setPro_detail_code(e_item.elementText("pro_detail_code"));

					
				}
				o.setItemList(items);
				outer_orders.add(o);
					}
			}


		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void packageGoodsParam(OuterItem o, Map<String, String> param) {

		o.setPhone_deposit(StringUtil.isEmpty(param.get("deposit_fee")) ? "0"
				: param.get("deposit_fee"));// 活动预存款 单位元

		String product_id = param.get("product_id");// i.getOuterIid();//String.valueOf(i.getNumIid());
		// product_id = "1";
		logger.info("product_id:=" + product_id);
		o.setProduct_id(product_id);// 产品ID
		o.setGoods_id(param.get("goods_id"));
		/**
		 * 规格数所
		 */
		o.setAct_protper(StringUtil.isEmpty(param.get("package_limit")) ? "0"
				: param.get("package_limit")); // 合约期限 12月、18月、24月、36月、48月
		/**
		 * 规格数所
		 */
		o.setAtive_type(StringUtil.isEmpty(param.get("ative_type")) ? ""
				: param.get("ative_type")); // 字典：5购机送费、4存费送机、3存费送费 --合约类型
		/**
		 * 规格数所
		 */
		o.setBrand_name(param.get("brand_name")); // 品牌
		/**
		 * 规格数所
		 */
		o.setBrand_number(param.get("brand_number")); // 品牌编码
		/**
		 * 规格数所
		 */
		o.setColor_code(StringUtil.isEmpty(param.get("color_code")) ? ""
				: param.get("color_code"));// 颜色编码
		o.setColor_name(StringUtil.isEmpty(param.get("color_name")) ? ""
				: param.get("color_name"));
		/**
		 * 规格数所
		 */
		o.setIs_iphone_plan(param.get("is_iphone_plan"));// 是否iphone套餐 字典：0否，1是
															// 没有
		/**
		 * 规格数所
		 */
		o.setIs_liang(param.get("is_liang"));// 是否靓号 字典：0否，1是 --待确认
		/**
		 * 规格数所
		 */
		o.setIs_loves_phone(StringUtil.isEmpty(param.get("is_loves_phone")) ? "0"
				: param.get("is_loves_phone")); // 是否情侣号码 字典：0否，1是 --默认0
		/**
		 * 规格数所
		 */
		o.setIs_stock_phone(StringUtil.isEmpty(param.get("is_stock_phone")) ? "0"
				: param.get("is_stock_phone"));// 是否库存机 字典：0否，1是 --终端属性
		/**
		 * 规格数所
		 */
		o.setIsgifts(String.valueOf(param.get("isgifts")));// 是否赠品 字典：0否，1是
		/**
		 * 规格数所
		 */
		o.setModel_code(param.get("model_code"));// 机型编码 　---如果要送总部开户，就要对应总部的编码
													// 如果不送总部，就为空。待局方确认
		/**
		 * 规格数所
		 */
		o.setModel_name(param.get("model_name"));// 机型名称

		o.setOut_plan_id_bss(param.get("out_plan_id_bss"));// BSS套餐编码 BSS套餐编码
															// --待局方确认
		o.setOut_plan_id_ess(param.get("out_plan_id_ess"));// 总部套餐编码 总部套餐编码
															// ---如果要送总部开户，就要对应总部的编码
															// 如果不送总部，就为空。待局方确认

		/**
		 * 规格数所
		 */
		o.setPlan_title(param.get("goods_name"));// 套餐名称wt.getPlan_title();
													// 　商品名称
													// param.get("goods_name")

		o.setPro_name(param.get("goods_name"));// 产品名称 　商品名称

		/**
		 * 规格数所
		 */
		o.setPro_type(param.get("product_type"));// 产品类型
		o.setProduct_net(param.get("product_net"));// 产品网别 　2G、3G --填空
		/**
		 * 规格数所
		 */
		o.setSpecificatio_nname(StringUtil.isEmpty(param
				.get("specification_name")) ? "" : param
				.get("specification_name"));// 型号名称
		/**
		 * 规格数所
		 */
		o.setSpecification_code(StringUtil.isEmpty(param
				.get("specification_code")) ? "" : param
				.get("specification_code"));// 型号编码 　---如果要送总部开户，就要对应总部的编码
											// 如果不送总部，就为空。待局方确认
		o.setType_id(param.get("type_id"));
	}
	
	public IGoodsService getGoodServices() {
		return goodServices;
	}

	public void setGoodServices(IGoodsService goodServices) {
		this.goodServices = goodServices;
	}
	
	public IOrderServiceLocal getOrderServicesLocal() {
		return orderServiceLocal;
	}

	public void setOrderServicesLocal(IOrderServiceLocal orderServiceLocal) {
		this.orderServiceLocal = orderServiceLocal;
	}

	public String getVal(String s) {
		// return "'"+s+"'" ;
		String result = s;
		if (s == null || "".equalsIgnoreCase(s))
			result = null;
		return result;
	}
	
	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub
		
	}

	public IOutterECSTmplManager getOutterECSTmplManager() {
		return outterECSTmplManager;
	}

	public void setOutterECSTmplManager(IOutterECSTmplManager outterECSTmplManager) {
		this.outterECSTmplManager = outterECSTmplManager;
	}
}
