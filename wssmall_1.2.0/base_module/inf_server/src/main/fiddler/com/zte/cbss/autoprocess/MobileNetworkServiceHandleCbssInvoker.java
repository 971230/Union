package com.zte.cbss.autoprocess;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;
import zte.net.ecsord.params.bss.resp.MobileNetworkServiceHandleResp;

import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.request.MobileProductPage1_1Request;
import com.ztesoft.inf.communication.client.AbsCbssInvoker;

/**
 * 4G省内闲时流量包附加产品 [附加产品]
 */
public class MobileNetworkServiceHandleCbssInvoker extends AbsCbssInvoker<MobileNetworkServiceHandleReq, MobileNetworkServiceHandleResp> {

	private final static Logger logger = Logger.getLogger(MobileNetworkServiceHandleCbssInvoker.class);

	private final static List<NameValuePair> custservHeadParams = new ArrayList<NameValuePair>();

	private final static List<NameValuePair> redirectLoginHeadParams = new ArrayList<NameValuePair>();

	private final static String custservURL = "https://gd.cbss.10010.com/custserv";

	static {
		custservHeadParams.add(new BasicNameValuePair("Referer", "https://gd.cbss.10010.com/custserv"));
		redirectLoginHeadParams.add(new BasicNameValuePair("Referer", "https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin"));
	}

	@Override
	public MobileNetworkServiceHandleResp businessProcess(MobileNetworkServiceHandleReq zteRequest, HttpLoginClient client, CustomInfo customInfo) throws Exception {
		
//		HttpLoginClient client = (HttpLoginClient) zteRequest.getHttpLoginclient();
		
		MobileNetworkServiceHandleResp resp = new MobileNetworkServiceHandleResp();
		// 第一步:获取基础base参数-->模拟打开[移网产品/服务变更 ]页面.
		getBase(client);
		logger.debug("第一步:获取基础base参数-->模拟打开[移网产品/服务变更 ]页面.成功");

		// 第二步:获取完整base参数-->模拟点击[移网产品/服务变更 ]页面中查询按钮.
		getFullBase(client);
		logger.debug("第二步:获取完整base参数-->模拟点击[移网产品/服务变更 ]页面中查询按钮.成功");

		// 第三步:提交移网产品服务办理-->模拟[移网产品/服务变更 ]页面中确定按钮.
		String xmlStr = new MobileProductPage1_1Request(client).send(zteRequest);
		analyzeXml(xmlStr, client);
		logger.debug("第三步:提交移网产品服务办理-->模拟[移网产品/服务变更 ]页面中确定按钮.成功");

		// 第四步:跳转到[提交订单]页面
		jump2SubmitPage(client);
		logger.debug("第四步:跳转到[提交订单]页面.成功");

		// [提交订单页面]初始化请求1
		mobileProductPage2_1Request(client);
		logger.debug("[提交订单页面]初始化请求1.成功");
		// [提交订单页面]初始化请求2
		mobileProductPage2_2Request(client);
		logger.debug("提交订单页面]初始化请求2.成功");

		// [提交订单页面]点击提交订单按钮请求1
		mobileProductPageClickSubmit3_1Request(client);
		logger.debug("[提交订单页面]点击提交订单按钮请求1.成功");
		// [提交订单页面]点击提交订单按钮请求2
		mobileProductPageClickSubmit3_2Request(client);
		logger.debug("[提交订单页面]点击提交订单按钮请求2.成功");
		// [提交订单页面]点击提交订单按钮请求3
		mobileProductPageClickSubmit3_3Request(client);
		logger.debug("[提交订单页面]点击提交订单按钮请求3.成功");
		// [提交订单页面]点击提交订单按钮请求4
		mobileProductPageClickSubmit3_4Request(client);
		logger.debug("[提交订单页面]点击提交订单按钮请求4.成功");
		// [提交订单页面]点击提交订单按钮请求5
		mobileProductPageClickSubmit3_5Request(client);
		logger.debug("[提交订单页面]点击提交订单按钮请求5.成功");
		return resp;
	}

	/**
	 * 提交订单页面]点击提交订单按钮请求5
	 * 
	 * @param client
	 */
	private void mobileProductPageClickSubmit3_5Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/dealAfterOrderSubmit/1"));
		postParams.add(new BasicNameValuePair("tradeId", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("tradeTypeCode", "0120"));
		postParams.add(new BasicNameValuePair("TRADE_ID_MORE_STR", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER_STR", client.getParam().getBill().getSerialNumber() + ","));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "120,"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
		postParams.add(new BasicNameValuePair("strNetType", "0050"));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPageClickSubmit3_5Request返回" + queryHtml);
	}

	/**
	 * [提交订单页面]点击提交订单按钮请求4
	 * 
	 * @param client
	 */
	private void mobileProductPageClickSubmit3_4Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/archiveMphoneNo/1"));
		postParams.add(new BasicNameValuePair("CASH", "0.00"));
		String base = "{\"prepayTag\": \"1\", \"tradeTypeCode\": \"0120\", \"strisneedprint\": \"1\", \"serialNumber\": \""
				+ client.getParam().getBill().getSerialNumber()
				+ "\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}";
		postParams.add(new BasicNameValuePair("base", base));
		postParams.add(new BasicNameValuePair("TRADE_ID", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
		postParams.add(new BasicNameValuePair("netCode", "50"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPageClickSubmit3_4Request返回" + queryHtml);
	}

	/**
	 * [提交订单页面]点击提交订单按钮请求3
	 * 
	 * @param client
	 */
	public void mobileProductPageClickSubmit3_3Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/resourceMphoneNo/1"));
		postParams.add(new BasicNameValuePair("CASH", "0.00"));
		String base = "{\"prepayTag\": \"1\", \"tradeTypeCode\": \"0120\", \"strisneedprint\": \"1\", \"serialNumber\": \""
				+ client.getParam().getBill().getSerialNumber()
				+ "\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}";
		postParams.add(new BasicNameValuePair("base", base));
		postParams.add(new BasicNameValuePair("TRADE_ID", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPageClickSubmit3_3Request返回:" + queryHtml);

	}

	/**
	 * [提交订单页面]点击提交订单按钮请求2
	 * 
	 * @param client
	 */
	public void mobileProductPageClickSubmit3_2Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1"));
		postParams.add(new BasicNameValuePair("cancelTag", "false"));
		postParams.add(new BasicNameValuePair("funcType", "0"));
		postParams.add(new BasicNameValuePair("dataType", "0"));
		String tradeMain = "[{\"TRADE_ID\": \"" + client.getParam().getTradeId() + "\", \"TRADE_TYPE\": \"移网产品/服务变更\", \"SERIAL_NUMBER\": \""
				+ client.getParam().getBill().getSerialNumber() + "\", \"TRADE_FEE\": \"0.00\", \"CUST_NAME\": \""+client.getParam().getCustomInfo().getCustName()+"\", \"CUST_ID\": \""
				+ client.getParam().getCustomInfo().getCustId() + "\", \"USER_ID\": \"" + client.getParam().getUserId() + "\", \"ACCT_ID\": \"" + client.getParam().getUserId()
				+ "\", \"NET_TYPE_CODE\": \"50\", \"TRADE_TYPE_CODE\": \"120\"}]";
		postParams.add(new BasicNameValuePair("tradeMain", tradeMain));
		postParams.add(new BasicNameValuePair("fees", "[]"));
		postParams.add(new BasicNameValuePair("unChargedfees", "[]"));
		postParams.add(new BasicNameValuePair("feePayMoney", "[]"));
		postParams.add(new BasicNameValuePair("feeCheck", "[]"));
		postParams.add(new BasicNameValuePair("feePos", "[]"));
		String base = "{\"prepayTag\": \"1\", \"tradeTypeCode\": \"0120\", \"strisneedprint\": \"1\", \"serialNumber\": \""
				+ client.getParam().getBill().getSerialNumber()
				+ "\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}";
		postParams.add(new BasicNameValuePair("base", base));
		postParams.add(new BasicNameValuePair("CASH", "0.00"));
		postParams.add(new BasicNameValuePair("SEND_TYPE", "0"));
		postParams.add(new BasicNameValuePair("TRADE_ID", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("TRADE_ID_MORE_STR", client.getParam().getTradeId()));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER_STR", client.getParam().getBill().getSerialNumber() + ","));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "120,"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
		postParams.add(new BasicNameValuePair("DEBUTY_CODE", ""));
		postParams.add(new BasicNameValuePair("IS_NEED_WRITE_CARD", "false"));
		postParams.add(new BasicNameValuePair("WRAP_TRADE_TYPE", "tradeType"));
		postParams.add(new BasicNameValuePair("CUR_TRADE_IDS", ""));
		postParams.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES", ""));
		postParams.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS", ""));
		postParams.add(new BasicNameValuePair("CUR_NET_TYPE_CODES", ""));
		postParams.add(new BasicNameValuePair("isAfterFee", ""));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPageClickSubmit3_2Request返回:" + queryHtml);
	}

	/**
	 * [提交订单页面]点击提交订单按钮请求1
	 * 
	 * @param client
	 */
	public void mobileProductPageClickSubmit3_1Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/getPayCodeMutex/1"));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPageClickSubmit3_1Request返回:" + queryHtml);
	}

	/**
	 * [提交订单页面]初始化请求2
	 * 
	 * @param client
	 */
	private void mobileProductPage2_2Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/common.UtilityPage/needChkCust/1"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE", ""));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER", ""));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE", "undefined"));
		postParams.add(new BasicNameValuePair("judge", "1"));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPage2_2Request返回:" + queryHtml);
	}

	/**
	 * [提交订单页面]初始化请求1
	 * 
	 * @param client
	 */
	public void mobileProductPage2_1Request(HttpLoginClient client) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/bcpCheckBcaInfo/1"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE", "50"));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER", client.getParam().getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));

		String queryHtml = client.getBrowser().getUrlRespHtml(custservURL, redirectLoginHeadParams, postParams);
		logger.debug("mobileProductPage2_1Request返回" + queryHtml);
	}

	/**
	 * 第四步:跳转到[提交订单]页面
	 * 
	 * @param client
	 * @throws Exception
	 */
	public void jump2SubmitPage(HttpLoginClient client) throws Exception {
		List<NameValuePair> postDict = new ArrayList<NameValuePair>();
		postDict.add(new BasicNameValuePair("service", "page/personalserv.dealtradefee.DealTradeFee"));
		postDict.add(new BasicNameValuePair("listener", "init"));
		postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "tradeType"));
		String param = "{\"SUBSCRIBE_ID\": \"" + client.getParam().getTradeId() + "\", \"TRADE_ID\": \"" + client.getParam().getTradeId() + "\", \"PROVINCE_ORDER_ID\": \""
				+ client.getParam().getTradeId() + "\"}";
		postDict.add(new BasicNameValuePair("param", param));
		postDict.add(new BasicNameValuePair("fee", ""));
		postDict.add(new BasicNameValuePair("noBack", ""));
		postDict.add(new BasicNameValuePair("staffId", client.getParam().getStaffId()));
		postDict.add(new BasicNameValuePair("departId", client.getParam().getDepartId()));
		postDict.add(new BasicNameValuePair("subSysCode", "custserv"));
		postDict.add(new BasicNameValuePair("eparchyCode", client.getParam().getEparchyCode()));

		String resphtml = client.getBrowser().getUrlRespHtml(custservURL, custservHeadParams, postDict);
		// 设置必要参数
		analyzeParam(resphtml, client);
	}

	/**
	 * 解析返回html参数 tradeId,tradeTypeCode,netTypeCode
	 * 
	 * @param xmlStr
	 * @param client
	 * @throws Exception
	 */
	public static void analyzeXml(String xmlStr, HttpLoginClient client) throws Exception {
		SAXReader readerDom = new SAXReader();
		Document doc = readerDom.read(new StringReader(xmlStr));
		Element EradeSubmitOkElement = doc.getRootElement().element("TradeSubmitOk");
		if (null == EradeSubmitOkElement) {
			Element message = doc.getRootElement().element("message");
			message.element("data").attributeValue("message");
			throw new Exception(message.element("data").attributeValue("message"));
		} else {
			String tradeId = EradeSubmitOkElement.attributeValue("tradeId");
			// String tradeTypeCode =
			// EradeSubmitOkElement.element("TradeData").attributeValue("tradeTypeCode");
			String netTypeCode = EradeSubmitOkElement.element("TradeData").attributeValue("netTypeCode");
			client.getParam().setTradeId(tradeId);
			client.getParam().getBill().setNetTypeCode(netTypeCode);
		}

	}

	/**
	 * 第二步:获取完整base参数-->模拟点击[移网产品/服务变更 ]页面中查询按钮.
	 * 
	 * @param client
	 * @throws Exception
	 */
	public void getFullBase(HttpLoginClient client) throws Exception {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "direct/1/personalserv.changeelement.ChangeElement/$MobTrade.$Form$0"));
		postParams.add(new BasicNameValuePair("sp", "S0"));
		final String Form0 = "ORDER_MGR,RElA_TRADE_ID,ORDER_TYPE,SUPPORT_TAG,COMM_SHARE_NBR_STRING,AC_INFOS,FORGIFT_USER_ID,QUERY_ACCOUNT_ID,_rightCode,inModeCode,NET_TYPE_CODE,SERIAL_NUMBER,subQueryTrade";
		postParams.add(new BasicNameValuePair("Form0", Form0));
		postParams.add(new BasicNameValuePair("SUPPORT_TAG", ""));
		postParams.add(new BasicNameValuePair("COMM_SHARE_NBR_STRING", ""));
		postParams.add(new BasicNameValuePair("AC_INFOS", ""));
		postParams.add(new BasicNameValuePair("FORGIFT_USER_ID", ""));
		postParams.add(new BasicNameValuePair("QUERY_ACCOUNT_ID", ""));
		postParams.add(new BasicNameValuePair("_rightCode", "csChangeServiceTrade"));
		postParams.add(new BasicNameValuePair("_tradeBase", client.getParam().getTradeBase()));
		postParams.add(new BasicNameValuePair("inModeCode", "0"));
		postParams.add(new BasicNameValuePair("ORDER_MGR", ""));
		postParams.add(new BasicNameValuePair("RElA_TRADE_ID", ""));
		postParams.add(new BasicNameValuePair("ORDER_TYPE", ""));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE", "50"));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER", client.getParam().getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("subQueryTrade", "查询"));
		String resphtml = client.getBrowser().getUrlRespHtml(custservURL, custservHeadParams, postParams);
		// 解析返回html获取页面第一次生成tradBase
		analyzeParamGetBase(resphtml, client.getParam(), true);
	}

	/**
	 * 第一步:获取基础base参数-->模拟打开[移网产品/服务变更 ]页面
	 * 
	 * @param client
	 * @throws Exception
	 */
	public void getBase(HttpLoginClient client) throws Exception {
		List<NameValuePair> getParams = new ArrayList<NameValuePair>();
		getParams.add(new BasicNameValuePair("service", "page/personalserv.changeelement.ChangeElement"));
		getParams.add(new BasicNameValuePair("listener", "initMobTrade"));
		getParams.add(new BasicNameValuePair("RIGHT_CODE", "csChangeServiceTrade"));
		getParams.add(new BasicNameValuePair("LOGIN_RANDOM_CODE", client.getParam().getLOGIN_RANDOM_CODE()));
		getParams.add(new BasicNameValuePair("LOGIN_CHECK_CODE", client.getParam().getLOGIN_CHECK_CODE()));
		getParams.add(new BasicNameValuePair("LOGIN_PROVINCE_CODE", client.getParam().getProvinceId()));
		getParams.add(new BasicNameValuePair("IPASS_LOGIN", "null"));
		getParams.add(new BasicNameValuePair("staffId", client.getParam().getStaffId()));
		getParams.add(new BasicNameValuePair("departId", client.getParam().getDepartId()));
		getParams.add(new BasicNameValuePair("subSysCode", "BSS"));
		getParams.add(new BasicNameValuePair("eparchyCode", client.getParam().getEparchyCode()));

		String resphtml = client.getBrowser().getUrlRespHtml(custservURL, custservHeadParams, getParams);
		// 解析返回html获取页面第一次生成tradBase
		analyzeParamGetBase(resphtml, client.getParam(), false);

	}

	/**
	 * 解析返回html参数 tradBase,userInfo
	 * 
	 * @param html
	 * @param data
	 * @param isUserInfo
	 *            是否解析userInfo信息
	 * @throws Exception
	 */
	private static void analyzeParamGetBase(String html, ParameterData data, boolean isUserInfo) throws Exception {
		final String tradeBase;
		final String userInfoJson;
		// 解析tradebase值
		String matStrByTradeBase = "<input type=\"hidden\" id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^&].*)\"";
		Pattern patBytradebase = Pattern.compile(matStrByTradeBase);
		Matcher matBytradebase = patBytradebase.matcher(html);

		if (matBytradebase.find()) {
			tradeBase = matBytradebase.group(1);
		} else {
			logger.info("未获取到正常dom结构!无法解析tradeBase:\n" + html);
			throw new Exception("未获取到正常dom结构!无法解析tradeBase");
		}

		/**
		 * 解析号码用户信息-->包含信息:
		 * {"custId":"5114122313144638","groupUserId":"","productTypeCode"
		 * :"4G000001",
		 * ,"acctId":"5115011363175648","userId":"5115011368520341",
		 * "productChgFlg":"-2","serialnumber":"18689302832",
		 * "grpId":"","productId":"99999829"}
		 */
		if (isUserInfo) {
			String matStrByUserInfo = "<input type=\"hidden\" id=\"_infos\" name=\"_infos\" value=\"([^&].*)\"";
			Pattern patByUserInfo = Pattern.compile(matStrByUserInfo);
			Matcher matByUserInfo = patByUserInfo.matcher(html);

			if (matByUserInfo.find()) {
				userInfoJson = matByUserInfo.group(1).replaceAll("&quot;", "\"");
				JSONObject jsonObject = JSONObject.fromObject(JSONObject.fromObject(userInfoJson));
				data.setUserId(jsonObject.getString("userId"));
				data.setAcctId(jsonObject.getString("acctId"));
			} else {
				logger.info("未获取到正常dom结构!无法解析userInfo:\n" + html);
				throw new Exception("未获取到正常dom结构!无法解析userInfo");
			}
		}

		data.setTradeBase(tradeBase);
		logger.debug("移网产品服务变更时取得tradeBase:" + tradeBase);
	}

	private static void analyzeParam(String html, HttpLoginClient client) throws Exception {
		Parser parser;
		NodeList list = null;
		NodeFilter trFilter = new TagNameFilter("tr");
		NodeFilter hasAttrFilter = new HasAttributeFilter("rowTag", "tradeMain");
		NodeFilter andFilter = new AndFilter(trFilter, hasAttrFilter);
		try {
			parser = Parser.createParser(html, "gbk");
			list = parser.parse(andFilter);
		} catch (ParserException e) {
			logger.info("费用信息页面的结构有变，不能正常解析:\n" + html);
			throw new Exception("费用信息页面的结构有变，不能正常解析!");
		}
		if (list == null || list.size() == 0) {
			logger.info("未获取到正常dom结构:\n" + html);
			throw new Exception("未获取到正常dom结构!");
		}
		Node node = list.elementAt(0);
		NodeList nodeList = node.getChildren();
		NodeFilter tdFilter = new TagNameFilter("td");
		NodeList tdNodeList = nodeList.extractAllNodesThatMatch(tdFilter, true);
		// 9:CUST_ID 10:USER_ID 11:ACCT_ID 12:NET_TYPE_CODE 13:TRADE_TYPE_CODE
		TableColumn row9 = (TableColumn) tdNodeList.elementAt(9);
		TableColumn row10 = (TableColumn) tdNodeList.elementAt(10);
		TableColumn row11 = (TableColumn) tdNodeList.elementAt(11);
		TableColumn row12 = (TableColumn) tdNodeList.elementAt(12);
		TableColumn row13 = (TableColumn) tdNodeList.elementAt(13);

		String custId = row9.getStringText();
		String userId = row10.getStringText();
		String acctId = row11.getStringText();
		String netTypeCode = row12.getStringText();
		String tradeTypeCode = row13.getStringText();

		client.getParam().setUserId(userId);
		client.getParam().setAcctId(acctId);
	}

}
