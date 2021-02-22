package com.zte.cbss.autoprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.LoginInfo;
import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.autoprocess.model.SimcardInfo;
import com.zte.cbss.autoprocess.model.WriteCardResponse;

/**
 * 模拟登录联通cbss系统，主要目的在于获取登录成功后回写到cookie中的BSS_JSESSIONID值
 * @author tanghaoyang
 *
 */
public class WriteCard {
	private static final Logger log=Logger.getLogger(WriteCard.class);
	
	private static List<NameValuePair> headParams = new ArrayList<NameValuePair>();
	
	private static String phoneNumber;
	static{
		headParams.add(new BasicNameValuePair("Referer","https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin"));
	}
	
	public static void main(String[] args) throws Exception {
		String sessionId="xxx";
		String iccid="89860113851073998958";
		phoneNumber="18575895407";
		
		//writeCard(sessionId,iccid);
//		String html=MyTools.openFile("D:\\写卡页面1.html");
//		analyzeParam1(html,new ParameterData());
		
		ParameterData data =  new ParameterData();
		CustomBill bill = new CustomBill();
		bill.setLiangNumFee("");
		bill.setFee("4800.00");
		bill.setFeeWaivers("2000");
		data.setTradeId("5114070239438023");
		bill.setSerialNumber("18576095054");
		CustomInfo custInfo = new CustomInfo();
		custInfo.setCustName("江雪莲");
		custInfo.setCustId("5114063023337049");
		data.setUserId("");
		data.setAcctId("");
		
		data.setBill(bill);
		data.setCustomInfo(custInfo);
		dealTradeFreeFee_5(null,data);
	}
	
	public static String  writeCard(String sessionId,String iccid) throws Exception{
		WriteCardResponse writeCardResponse=new WriteCardResponse();
		
		try{
			LoginInfo info = new LoginInfo();
			info.setCbssAccount("DSZDGL01");
			info.setCbssPassword("+cfManXzORZEM6m7RxVvIztGm4w=");
			info.setProvinceCode("51");
			HttpLoginClient client = HttpLoginClientPool.add(info);
			if(client != null){
				CustomBill bill = new CustomBill();
				bill.setPsptId("450481198709010055");
				bill.setCustomName("冼宜亮");
				bill.setContact("冼宜亮");
				bill.setContactPhone("18602031660");
				bill.setPsptEndDate("2016-05-08");
				bill.setIdTypeCode("1");
				bill.setPhone("18602031660");
				bill.setPostAddress("广东省广州市天河区");
			    bill.setSerialNumber("18575895164");
			   
	            // 搜索产品名称的key
	            bill.setProductName("自由");
	            // 移动业务选号，电信类型（50:4G移动业务类型）
	            bill.setNetTypeCode("50");
	            // 客户归属业务区
	            bill.setUserCityCode("512039");
	            // 选择的产品编码
	            bill.setSelectedProductId("89002148");
	            // 活动类型
	        //    bill.setActivityType("CFSF001");
	            
	            //活动编码
	    		//bill.setActivityCode("89000100");
	    		//套餐编码
	    		bill.setFirstFeeType("02");
	    		bill.setFee("12000.0");
	    		//bill.setFeeWaivers("1500");
	    		List<String> addServiceCode = new ArrayList<String>();
	    		addServiceCode.add("5574000");
	    		addServiceCode.add("5573000");//addServiceCode.add("5572000");
	    		
	    		bill.setFlowPackage("5509000");
	    		bill.setFlowFirstFeeType("03");
	    		bill.setAddServiceCode(addServiceCode );
	//    		List<String> addServiceCode = new ArrayList<String>();
	//    		
	//    		addServiceCode.add("5574000");addServiceCode.add("5573000");
	//    		//短信业务编码
	//    		bill.setMessageCode("5561000");
	//    		//增值业务集合
	//    		bill.setAddServiceCode(addServiceCode );
	    		
				client.getParam().setDevelopStaffId("5102471614"); //发展人编码
	//			client.getParam().setDevelopDepartId("51b1456"); //渠道编码       51b1456:小米电子渠道—佛山 
	//			client.getParam().setDevelopCityCode("512039");//发展人业务区   
	//			client.getParam().setDevelopEparchyCode("0757"); //发展人区号
	//			client.getParam().setUserCallingArea("51b12z3"); //用户归属区域
				
	    		//usimid赋值
				client.getParam().setIccid(iccid);
	            
	            /**创建客户信息或直接查询得到客户信息,对应首页里面的操作*/
				CreateCustomHandler.emulate(bill, client);
	
				/**活动选择和产品定制*/
	            ChooseProductHandler.chooseFreeProduct(client.getBrowser(), client.getParam());
	            
	            /**信息录入和费用收取*/
	            InformationInput.executeFreeInfomationInput(client.getBrowser(), client.getParam());
	            
	            /**提交订单,完成开户*/
	            Charge.submitFreeOrder(client.getBrowser(), client.getParam());
	            
	            /** 写卡 */
	            WriteCard.openAccountAndWriteFreeCard(client.getBrowser(), client.getParam());
	            
	            /**将业务信息设置到响应实体中*/
	            writeCardResponse.setTelno(client.getParam().getBill().getSerialNumber());
	            writeCardResponse.setResult("OK");
	    		writeCardResponse.setIccid(iccid);
	    		writeCardResponse.setImsi(client.getParam().getSimcardInfo().getImsi());
	    		writeCardResponse.setOptions(client.getParam().getSimcardInfo().getXoption());
	    		
	        }else {
	        	log.warn("登录失败，请检查用户名和密码是否正确.");
	            throw new Exception("登录失败!");
	        }
		}catch(Exception e){
			writeCardResponse.setResult("error");
			writeCardResponse.setErrorCode("100");
			writeCardResponse.setErrorDesc(e.getMessage());
			log.info("开户写卡过程出错",e);
		}
		
		String result =JSONObject.fromObject(writeCardResponse).toString();
		log.info("sessionId="+sessionId+result);
		
		return result;
	}
	
	/**模拟开户写卡
	 * @throws Exception */
	public static ParameterData openAccountAndWriteCard(HCBrowser hcb, ParameterData data) throws Exception {
		
		/**完成号码写卡*/
		log.info("第一步，模拟打开写卡页面");
		openWriteCardPage(hcb,data);
		
		log.info("第二步，模拟通过手机号码查询客户信息");
		queryInfoByPhone(hcb,data);
		
		log.info("第三步，模拟中间的其他请求");
		otherRquest(hcb,data);
		
		log.info("第四步，模拟写卡");
		writeCard(hcb,data);
		
		log.info("第五步，模拟收费");
		dealTradeFee(hcb,data);
		
		log.info("第六步，进入打印页面,完成整个写卡过程");
		printInvoice(hcb,data);
		
		return data;
	}
	
	
	/**模拟自由套餐开户写卡
	 * @throws Exception */
	public static ParameterData openAccountAndWriteFreeCard(HCBrowser hcb, ParameterData data) throws Exception {
		
		/**完成号码写卡*/
		log.info("第一步，模拟打开写卡页面");
		openWriteCardPage(hcb,data);
		
		log.info("第二步，模拟通过手机号码查询客户信息");
		queryInfoByPhone(hcb,data);
		
		log.info("第三步，模拟中间的其他请求");
		otherRquest(hcb,data);
		
		log.info("第四步，模拟写卡");
		writeCard(hcb,data);
		/**
		 * 模拟收费和一体化不同
		 */
		log.info("第五步，模拟收费");
		dealTradeFreeFee(hcb,data);
		
		log.info("第六步，进入打印页面,完成整个写卡过程");
		printInvoice(hcb,data);
		
		return data;
	}

	private static void openWriteCardPage(HCBrowser hcb, ParameterData data) throws Exception {
		String writeCardPage="https://gd.cbss.10010.com/custserv?service=page/personalserv.writesimcard.WriteSimCard&listener=initMobTrade&RIGHT_CODE=csWriteSimCard&LOGIN_RANDOM_CODE="+data.getLOGIN_RANDOM_CODE()+"&LOGIN_CHECK_CODE="+data.getLOGIN_CHECK_CODE()+"&LOGIN_PROVINCE_CODE="+data.getProvinceId()+"&IPASS_LOGIN=null&staffId="+data.getStaffId()+"&departId="+data.getDepartId()+"&subSysCode=BSS&eparchyCode="+data.getEparchyCode();
		String html=hcb.getUrlRespHtml(writeCardPage, headParams, null);
		analyzeParam1(html,data);
	}

	private static void printInvoice(HCBrowser hcb, ParameterData data) {
		log.info("第六步--1:发送订单提交的后续处理请求");
		//打印页面有很多步骤，这里只发了一个最关键的，可能会有问题，先试试
		printInvoice_1(hcb,data);
	}


	private static void dealTradeFee(HCBrowser hcb, ParameterData data) {
		log.info("第五步--1:打开收费页面");
		dealTradeFee_1(hcb,data);
		log.info("第五步--2:打开收费页面");
		dealTradeFee_2(hcb,data);
		log.info("第五步--3:根据号码查询用户信息");
		dealTradeFee_3(hcb,data);
		log.info("第五步--4:常规请求");
		dealTradeFee_4(hcb,data);
		
		log.info("第五步--5:确认收费请求");
		dealTradeFee_5(hcb,data);
		
		log.info("第五步--6:查询号码信息");
		dealTradeFee_6(hcb,data);
		
		log.info("第五步--7:获取号码状态");
		dealTradeFee_7(hcb,data);
		
	}
	
	/**
	 * 自由组合收费
	 * 第二和第五与一体化有区别
	 * @param hcb
	 * @param data
	 */
	private static void dealTradeFreeFee(HCBrowser hcb, ParameterData data) {
		log.info("第五步--1:打开收费页面");
		dealTradeFee_1(hcb,data);
		log.info("第五步--2:打开收费页面");
		dealTradeFreeFee_2(hcb,data);
		log.info("第五步--3:根据号码查询用户信息");
		dealTradeFee_3(hcb,data);
		log.info("第五步--4:常规请求");
		dealTradeFee_4(hcb,data);
		
		log.info("第五步--5:确认收费请求");
		dealTradeFreeFee_5(hcb,data);
		
		log.info("第五步--6:查询号码信息");
		dealTradeFee_6(hcb,data);
		
		log.info("第五步--7:获取号码状态");
		dealTradeFee_7(hcb,data);
		
	}

	private static void printInvoice_1(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/dealAfterOrderSubmit/1"));
		postParams.add(new BasicNameValuePair("tradeId",data.getTradeId()));
		postParams.add(new BasicNameValuePair("tradeTypeCode","0116"));
		postParams.add(new BasicNameValuePair("TRADE_ID_MORE_STR",data.getTradeId()));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER_STR",data.getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR","116"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE_STR","50"));
		postParams.add(new BasicNameValuePair("strNetType","null"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
		log.info(queryHtml);
	}
	

	private static void dealTradeFee_1(HCBrowser hcb, ParameterData data) {
		String url="https://gd.cbss.10010.com/custserv?service=page/personalserv.dealtradefee.DealTradeFee&listener=&staffId="+data.getStaffId()+"&departId="+data.getDepartId()+"&subSysCode=custserv&eparchyCode="+data.getEparchyCode();
		String html = hcb.getUrlRespHtml(url, headParams, null);
	}
	
	private static void dealTradeFee_2(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","direct/1/personalserv.dealtradefee.DealTradeFee/$DirectLink"));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE","tradeType"));
		postParams.add(new BasicNameValuePair("param","{\"SUBSCRIBE_ID\": \""+data.getTradeId()+"\", \"TRADE_ID\": \""+data.getTradeId()
				+"\", \"PROVINCE_ORDER_ID\": \""+data.getTradeId()+"\"}"));
		//TODO 此处费用信息是写死的格式，只包含两项费用信息，feeTypeCode": "1010"和feeTypeCode": "100005",不同业务可能不止这两项而需要动态解析
		//urlDecoder解码后：{"fee": [{"operateType": "1", "feeTypeCode": "1010", "payTag": "0", "tradeId": "5114060735901357", "maxDerateFee": "2000", "feeitemCode": "1010", "feeMode": "0", "oldfee": "2000", "fee": "2000"}, {"operateType": "1", "feeTypeCode": "100005", "payTag": "0", "tradeId": "5114060735901357", "maxDerateFee": "0", "feeitemCode": "100005", "feeMode": "2", "oldfee": "12000", "fee": "12000"}]}
          
		// 自由组合解码后	{"fee": [{"operateType": "1", "feeTypeCode": "1010", "payTag": "0", "tradeId": "5114061937404649", "maxDerateFee": "2000", "feeitemCode": "1010", "feeMode": "0", "oldfee": "2000", "fee": "2000"}]}
		postParams.add(new BasicNameValuePair("fee","%7B%22fee%22:%20%5B%7B%22operateType%22:%20%221%22,%20%22feeTypeCode%22:%20%221010%22,%20%22payTag%22:%20%220%22,%20%22tradeId%22:%20%22"+data.getTradeId()+"%22,%20%22maxDerateFee%22:%20%222000%22,%20%22feeitemCode%22:%20%221010%22,%20%22feeMode%22:%20%220%22,%20%22oldfee%22:%20%222000%22,%20%22fee%22:%20%222000%22%7D,%20%7B%22operateType%22:%20%221%22,%20%22feeTypeCode%22:%20%22100005%22,%20%22payTag%22:%20%220%22,%20%22tradeId%22:%20%"+data.getTradeId()+"%22,%20%22maxDerateFee%22:%20%220%22,%20%22feeitemCode%22:%20%22100005%22,%20%22feeMode%22:%20%222%22,%20%22oldfee%22:%20%2212000%22,%20%22fee%22:%20%2212000%22%7D%5D%7D"));
		postParams.add(new BasicNameValuePair("noBack",""));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	/**
	 * 自由组合
	 * @param hcb
	 * @param data
	 */
	private static void dealTradeFreeFee_2(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","direct/1/personalserv.dealtradefee.DealTradeFee/$DirectLink"));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE","tradeType"));
		postParams.add(new BasicNameValuePair("param","{\"SUBSCRIBE_ID\": \""+data.getTradeId()+"\", \"TRADE_ID\": \""+data.getTradeId()
				+"\", \"PROVINCE_ORDER_ID\": \""+data.getTradeId()+"\"}"));
		//TODO 此处费用信息是写死的格式，只包含两项费用信息，feeTypeCode": "1010"和feeTypeCode": "100005",不同业务可能不止这两项而需要动态解析
		//urlDecoder解码后：{"fee": [{"operateType": "1", "feeTypeCode": "1010", "payTag": "0", "tradeId": "5114060735901357", "maxDerateFee": "2000", "feeitemCode": "1010", "feeMode": "0", "oldfee": "2000", "fee": "2000"}, {"operateType": "1", "feeTypeCode": "100005", "payTag": "0", "tradeId": "5114060735901357", "maxDerateFee": "0", "feeitemCode": "100005", "feeMode": "2", "oldfee": "12000", "fee": "12000"}]}
          
		// 自由组合解码后	{"fee": [{"operateType": "1", "feeTypeCode": "1010", "payTag": "0", "tradeId": "5114061937404649", "maxDerateFee": "2000", "feeitemCode": "1010", "feeMode": "0", "oldfee": "2000", "fee": "2000"}]}
		//2014年7月1号起，最大减免费用maxDerateFee改成0喇亲，没有减免费用咯~想省钱，还是省口气吧……
	    //	postParams.add(new BasicNameValuePair("fee","%7B%22fee%22:%20%5B%7B%22operateType%22:%20%221%22,%20%22feeTypeCode%22:%20%221010%22,%20%22payTag%22:%20%220%22,%20%22tradeId%22:%20%22"+data.getTradeId()+"%22,%20%22maxDerateFee%22:%20%220%22,%20%22feeitemCode%22:%20%221010%22,%20%22feeMode%22:%20%220%22,%20%22oldfee%22:%20%222000%22,%20%22fee%22:%20%222000%22%7D%5D%7D"));
	   //还是得减免
		postParams.add(new BasicNameValuePair("fee","%7B%22fee%22:%20%5B%7B%22operateType%22:%20%221%22,%20%22feeTypeCode%22:%20%221010%22,%20%22payTag%22:%20%220%22,%20%22tradeId%22:%20%22"+data.getTradeId()+"%22,%20%22maxDerateFee%22:%20%222000%22,%20%22feeitemCode%22:%20%221010%22,%20%22feeMode%22:%20%220%22,%20%22oldfee%22:%20%222000%22,%20%22fee%22:%20%222000%22%7D%5D%7D"));

		postParams.add(new BasicNameValuePair("noBack",""));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	private static void dealTradeFee_3(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/bcpCheckBcaInfo/1"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE","50"));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER",data.getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	private static void dealTradeFee_4(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/common.UtilityPage/needChkCust/1"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE",""));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER",""));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE","undefined"));
		postParams.add(new BasicNameValuePair("judge","1"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	private static void dealTradeFee_5(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		//针对卡费减免后所收的金额
		double fee=calcFee(data.getBill().getFeeWaivers());//"2.23";单位是元
		//全部费用算下来的实收金额
		double tradeFee=Double.parseDouble(data.getBill().getFee())/100+fee;//120+fee;//"122.23";单位是元
		
		long money=(long) (tradeFee*100);//是tradeFee乘以100,单位是分
		String cash=tradeFee+"";//和tradeFee一样,单位是元
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1"));
		postParams.add(new BasicNameValuePair("cancelTag","false"));
		postParams.add(new BasicNameValuePair("funcType","0"));
		postParams.add(new BasicNameValuePair("dataType","0"));
		postParams.add(new BasicNameValuePair("tradeMain","[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"TRADE_TYPE\": \"现场写卡\", \"SERIAL_NUMBER\": \""+data.getBill().getSerialNumber()+"\", \"TRADE_FEE\": \""+tradeFee+"\", \"CUST_NAME\": \""+data.getCustomInfo().getCustName()+"\", \"CUST_ID\": \""+data.getCustomInfo().getCustId()+"\", \"USER_ID\": \""+data.getUserId()+"\", \"ACCT_ID\": \""+data.getAcctId()+"\", \"NET_TYPE_CODE\": \"50\", \"TRADE_TYPE_CODE\": \"116\"}]"));
		//TODO  第一个fee节点是卡费，需要减免，第二个fee节点是营业费，可能会根据不同业务变化
		//{"X_TAG": "1", "TRADE_ID": "5114060836058267", "CALCULATE_ID": "", "FEE_MODE": "2", "FEEITEM_CODE": "100005", "OLDFEE": "120.00", "FEE": "120.00", "DERATE_REMARK": "", "PAY_MONEY_CODE": "0", "PAY_TAG": "0", "CALCULATE_TAG": "N", "MODIFY_TAG": "", "TRADE_TYPE_CODE": "116", "NET_TYPE_CODE": "50", "FEEITEM_NAME": "[预存]营业厅收入(营业缴费)_普通预存款(不可清退)"}]
		postParams.add(new BasicNameValuePair("fees","[{\"X_TAG\": \"1\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"0\", \"FEEITEM_CODE\": \"1010\", \"OLDFEE\": \"20.00\", \"FEE\": \""+fee+"\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"116\", \"NET_TYPE_CODE\": \"50\", \"FEEITEM_NAME\": \"[营业费用项]SIM卡/USIM卡费\"}, {\"X_TAG\": \"1\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"2\", \"FEEITEM_CODE\": \"100005\", \"OLDFEE\": \"120.00\", \"FEE\": \"120.00\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"116\", \"NET_TYPE_CODE\": \"50\", \"FEEITEM_NAME\": \"[预存]营业厅收入(营业缴费)_普通预存款(不可清退)\"}]"));
		postParams.add(new BasicNameValuePair("unChargedfees","[]"));
		postParams.add(new BasicNameValuePair("feePayMoney","[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"PAY_MONEY_CODE\": \"0\", \"MONEY\": \""+money+"\"}]"));//TODO 这里有个费用值
		postParams.add(new BasicNameValuePair("feeCheck","[]"));
		postParams.add(new BasicNameValuePair("feePos","[]"));
		postParams.add(new BasicNameValuePair("base","{\"prepayTag\": \"\", \"tradeTypeCode\": \"0116\", \"strisneedprint\": \"0\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}"));
		postParams.add(new BasicNameValuePair("CASH",cash));
		postParams.add(new BasicNameValuePair("SEND_TYPE","0"));
		postParams.add(new BasicNameValuePair("TRADE_ID",data.getTradeId()));
		postParams.add(new BasicNameValuePair("TRADE_ID_MORE_STR",data.getTradeId()));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER_STR",data.getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR","116,"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE_STR","50,"));
		postParams.add(new BasicNameValuePair("DEBUTY_CODE",""));
		postParams.add(new BasicNameValuePair("IS_NEED_WRITE_CARD","false"));
		postParams.add(new BasicNameValuePair("WRAP_TRADE_TYPE","tradeType"));
		postParams.add(new BasicNameValuePair("CUR_TRADE_IDS",""));
		postParams.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES",""));
		postParams.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS",""));
		postParams.add(new BasicNameValuePair("CUR_NET_TYPE_CODES",""));
		postParams.add(new BasicNameValuePair("isAfterFee","2"));//貌似是是否后付费
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	/**
	 * 自由组合
	 * @param hcb
	 * @param data
	 */
	private static void dealTradeFreeFee_5(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		//针对卡费减免后所收的金额
		double shishouFee=calcFee(data.getBill().getFeeWaivers());//"2.23";单位是元
		
		String liangNumFee = data.getBill().getLiangNumFee();
		float liangNumFeeDType;
		//计算靓号费 
		if(liangNumFee==null||"".equals(liangNumFee)){//没有靓号费时
			
			liangNumFeeDType = 0;
		}else{
	
			liangNumFeeDType=Float.parseFloat(liangNumFee)/100;//"2.23";单位是元
		}
		//预存款费用
		Integer fee = (int)(Double.parseDouble(data.getBill().getFee()))/100;//元为单位
		//预存款+靓号费+USIM/SIM卡号费，也就是加20块工本费咯，顶……单位是元
		//预存款+靓号费+USIM/SIM卡号费-减免费
		float tradeFee=(float) (Float.parseFloat(data.getBill().getFee())/100+liangNumFeeDType+shishouFee);//"68.00";
		//以分为单位的总数整数形式
		Integer tradeFeeIntType = (int)tradeFee*100;
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1"));
		postParams.add(new BasicNameValuePair("cancelTag","false"));
		postParams.add(new BasicNameValuePair("funcType","0"));
		postParams.add(new BasicNameValuePair("dataType","0"));
		postParams.add(new BasicNameValuePair("tradeMain","[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"TRADE_TYPE\": \"现场写卡\", \"SERIAL_NUMBER\": \""+data.getBill().getSerialNumber()+"\", \"TRADE_FEE\": \""+tradeFee+"\", \"CUST_NAME\": \""+data.getCustomInfo().getCustName()+"\", \"CUST_ID\": \""+data.getCustomInfo().getCustId()+"\", \"USER_ID\": \""+data.getUserId()+"\", \"ACCT_ID\": \""+data.getAcctId()+"\", \"NET_TYPE_CODE\": \"50\", \"TRADE_TYPE_CODE\": \"116\"}]"));
		//TODO  第一个fee节点是卡费，需要减免，第二个fee节点是营业费，可能会根据不同业务变化
		//{"X_TAG": "1", "TRADE_ID": "5114060836058267", "CALCULATE_ID": "", "FEE_MODE": "2", "FEEITEM_CODE": "100005", "OLDFEE": "120.00", "FEE": "120.00", "DERATE_REMARK": "", "PAY_MONEY_CODE": "0", "PAY_TAG": "0", "CALCULATE_TAG": "N", "MODIFY_TAG": "", "TRADE_TYPE_CODE": "116", "NET_TYPE_CODE": "50", "FEEITEM_NAME": "[预存]营业厅收入(营业缴费)_普通预存款(不可清退)"}]
		postParams.add(new BasicNameValuePair("fees","[{\"X_TAG\": \"1\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"0\", \"FEEITEM_CODE\": \"1010\", \"OLDFEE\": \"20.00\", \"FEE\": \""+shishouFee+"\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"116\", \"NET_TYPE_CODE\": \"50\", \"FEEITEM_NAME\": \"[营业费用项]SIM卡/USIM卡费\"}, {\"X_TAG\": \"1\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"2\", \"FEEITEM_CODE\": \"100000\", \"OLDFEE\": \""+fee+"\", \"FEE\": \""+fee+"\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"Y\", \"MODIFY_TAG\": \"0\", \"TRADE_TYPE_CODE\": \"\", \"NET_TYPE_CODE\": \"\", \"FEEITEM_NAME\": \"[预存款费用]营业厅收入(营业缴费)_普通预存款\"}]"));
		                                                                                                                        //     自由组合    [{"X_TAG": "0", "TRADE_ID": "5114061937404649", "CALCULATE_ID": "", "FEE_MODE": "0", "FEEITEM_CODE": "1010", "OLDFEE": "20.00", "FEE": "20.00", "DERATE_REMARK": "", "PAY_MONEY_CODE": "0", "PAY_TAG": "0", "CALCULATE_TAG": "N", "MODIFY_TAG": "", "TRADE_TYPE_CODE": "116", "NET_TYPE_CODE": "50", "FEEITEM_NAME": "[营业费用项]SIM卡/USIM卡费"}, {"X_TAG": "1", "TRADE_ID": "5114061937404649", "CALCULATE_ID": "", "FEE_MODE": "2", "FEEITEM_CODE": "100000", "OLDFEE": "50", "FEE": "50", "DERATE_REMARK": "", "PAY_MONEY_CODE": "0", "PAY_TAG": "0", "CALCULATE_TAG": "Y", "MODIFY_TAG": "0", "TRADE_TYPE_CODE": "", "NET_TYPE_CODE": "", "FEEITEM_NAME": "[预存款费用]营业厅收入(营业缴费)_普通预存款"}]
		//2014年7月1号起，不收费部分没了……
		postParams.add(new BasicNameValuePair("unChargedfees","[]"));
		//减免20卡费
	    //postParams.add(new BasicNameValuePair("unChargedfees","[{\"X_TAG\": \"0\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"0\", \"FEEITEM_CODE\": \"1010\", \"OLDFEE\": \"20.00\", \"FEE\": \"20\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"116\", \"NET_TYPE_CODE\": \"50\", \"FEEITEM_NAME\": \"[营业费用项]SIM卡/USIM卡费\"}]"));

		//2014年7月1号起，这里需要是预存款+靓号费+卡费了……好贵，贵了20块啊……
		postParams.add(new BasicNameValuePair("feePayMoney","[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"PAY_MONEY_CODE\": \"0\", \"MONEY\": \""+tradeFeeIntType+"\"}]"));//TODO 这里有个费用值
		postParams.add(new BasicNameValuePair("feeCheck","[]"));
		postParams.add(new BasicNameValuePair("feePos","[]"));
		postParams.add(new BasicNameValuePair("base","{\"prepayTag\": \"\", \"tradeTypeCode\": \"0116\", \"strisneedprint\": \"0\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}"));
		postParams.add(new BasicNameValuePair("CASH",String.valueOf(tradeFee)));
		postParams.add(new BasicNameValuePair("SEND_TYPE","0"));
		postParams.add(new BasicNameValuePair("TRADE_ID",data.getTradeId()));
		postParams.add(new BasicNameValuePair("TRADE_ID_MORE_STR",data.getTradeId()));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER_STR",data.getBill().getSerialNumber()+","));
		postParams.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR","116,"));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE_STR","50,"));
		postParams.add(new BasicNameValuePair("DEBUTY_CODE",""));
		postParams.add(new BasicNameValuePair("IS_NEED_WRITE_CARD","false"));
		postParams.add(new BasicNameValuePair("WRAP_TRADE_TYPE","tradeType"));
		postParams.add(new BasicNameValuePair("CUR_TRADE_IDS",""));
		postParams.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES",""));
		postParams.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS",""));
		postParams.add(new BasicNameValuePair("CUR_NET_TYPE_CODES",""));
		postParams.add(new BasicNameValuePair("isAfterFee","2"));//貌似是是否后付费
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	
	private static void dealTradeFee_6(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/resourceMphoneNo/1"));
		postParams.add(new BasicNameValuePair("base",
				"{\"prepayTag\": \"\", \"tradeTypeCode\": \"0116\", \"strisneedprint\": \"0\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}"));
		postParams.add(new BasicNameValuePair("TRADE_ID",data.getTradeId()));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}
	
	private static void dealTradeFee_7(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.dealtradefee.DealTradeFee/archiveMphoneNo/1"));
		postParams.add(new BasicNameValuePair("base",
				"{\"prepayTag\": \"\", \"tradeTypeCode\": \"0116\", \"strisneedprint\": \"0\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \"0050\"}"));
		postParams.add(new BasicNameValuePair("TRADE_ID",data.getTradeId()));
		postParams.add(new BasicNameValuePair("netCode","50"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.dealtradefee.DealTradeFee"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}


	private static void otherRquest(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/common.UtilityPage/getInterfaceElement_first/1"));
		postParams.add(new BasicNameValuePair("contextCode","TRADE_ITEM_116_50_4G00|ShowTradeItem,TRADE_ITEM_116_50|ShowTradeItem,TRADE_ITEM_116|ShowTradeItem,TRADE_ITEM_50_4G00|ShowTradeItem,TRADE_ITEM_50|ShowTradeItem,TRADE_ITEM|ShowTradeItem"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
		log.info(queryHtml);
		
		List<NameValuePair> postParams1 = new ArrayList<NameValuePair>();
		postParams1.add(new BasicNameValuePair("service","swallow/common.UtilityPage/needChkCust/1"));
		postParams1.add(new BasicNameValuePair("SERIAL_NUMBER",data.getBill().getSerialNumber()));
		postParams1.add(new BasicNameValuePair("NET_TYPE_CODE",data.getBill().getNetTypeCode()));
		postParams1.add(new BasicNameValuePair("TRADE_TYPE_CODE","116"));//TODO 不知道此值从哪来，可能需要改成动态
		postParams1.add(new BasicNameValuePair("judge","1"));
		postParams1.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		queryHtml = hcb.getUrlRespHtml(url, headParams, postParams1);
//		log.info(queryHtml);
	}


	private static void writeCard(HCBrowser hcb, ParameterData data) throws Exception {
		/**获取sim卡写卡信息*/
		getSimInfoBcrm(hcb,data);
		
		transmitCardOk(hcb,data);
		
		transmitCardOkToB(hcb,data);
		
		/**提交写卡信息*/
		submitMobTrade(hcb,data);
	}

	private static void submitMobTrade(HCBrowser hcb, ParameterData data) throws Exception {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.writesimcard.WriteSimCard/submitMobTrade/1"));
		postParams.add(new BasicNameValuePair("Base",data.getTradeBase()));
		postParams.add(new BasicNameValuePair("Ext",
				"{\"Common\": {\"ACTOR_NAME\": \"\", \"ACTOR_PHONE\": \"\", \"ACTOR_CERTTYPEID\": \"\", \"ACTOR_CERTNUM\": \"\", \"REMARK\": \"写卡备注\"}, \"TF_B_TRADE_RES_TEMP\": {\"SIM_CARD\": \""+data.getSimcardInfo().getEmptyCardId()+"\", \"IMSI\": \""+data.getSimcardInfo().getImsi()+"\", \"RES_KIND_CODE\": \""+data.getSimcardInfo().getResKindCode()+"\", \"CAPACITY_TYPE_CODE\": \""+data.getSimcardInfo().getCapacityTypeCode()+"\", \"CHECK_SIMCARD\": \"1\"}, \"TRADE_ITEM\": {\"SIM_CARD\": \""+data.getSimcardInfo().getEmptyCardId()+"\", \"R_TRADE_ID\": \""+data.getTradeId()+"\", \"PROCID\": \""+data.getSimcardInfo().getProcid()+"\", \"RemoteTag\": \"1\", \"CARD_DATA\": \""+data.getSimcardInfo().getCardData()+"\"}, \"TRADE_OTHER_INFO\": {\"ITEM\": {\"CHECK_TYPE\": \"0\", \"BLACK_CUST\": \"0\"}}}"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String respXml = hcb.getUrlRespHtml(url, headParams, postParams);
		log.info(respXml);
		
		/**解析返回值中新的tradeId,这个是用于缴费步骤的*/
		analyzesubmitMobTrade(respXml,data);
	}


	private static void analyzesubmitMobTrade(String respXml, ParameterData data) throws Exception {
		try{
			Document doc = DocumentHelper.parseText(respXml);
			Element root = doc.getRootElement();// 获得根节点
			Element eleTradeSubmitOk = root.element("TradeSubmitOk");
			String tradeId=eleTradeSubmitOk.attributeValue("tradeId");
			data.setTradeId(tradeId);
			log.info(tradeId);
		}catch(Exception e){
			log.info("解析xml出错",e);
			throw e;
		}
	}

	private static void transmitCardOk(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.writesimcard.WriteSimCard/transmitCardOk/1"));
		postParams.add(new BasicNameValuePair("param",
				"{\"EMPTY_CARD_ID\": \""+data.getSimcardInfo().getEmptyCardId()+"\", \"IMSI\": \""+data.getSimcardInfo().getImsi()+"\", \"PROCID\": \""+data.getSimcardInfo().getProcid()+"\", \"SIM_STATE_CODE\": \"1\", \"REMARK\": \"90 00\"}"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}

	private static void transmitCardOkToB(HCBrowser hcb, ParameterData data) {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.writesimcard.WriteSimCard/transmitCardOkToB/1"));
		//TODO 需要改成动态值
		postParams.add(new BasicNameValuePair("param","{\"EMPTY_CARD_ID\": \""+data.getIccid()+"\", \"IMSI\": \""+data.getSimcardInfo().getImsi()+"\", \"PROCID\": \""+data.getSimcardInfo().getProcid()+"\", \"SIM_STATE_CODE\": \"1\", \"REMARK\": \"90 00\"}"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
//		log.info(queryHtml);
	}

	private static void getSimInfoBcrm(HCBrowser hcb, ParameterData data) throws Exception {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("service","swallow/personalserv.writesimcard.WriteSimCard/getSimInfoBcrm/1"));
		postParams.add(new BasicNameValuePair("param","{\"SERIAL_NUMBER\": \""+data.getBill().getSerialNumber()+"\", \"SIM_CARD_NO\": \""+data.getIccid()+"\", \"TRADE_TYPE_CODE\": \"116\", \"PRODUCT_ID\": \""+data.getBill().getSelectedProductId()+"\", \"PRE_TAG\": \"\", \"BRAND_CODE\": \"4G00\"}"));
		postParams.add(new BasicNameValuePair("globalPageName","personalserv.writesimcard.WriteSimCard"));
		
		String url="https://gd.cbss.10010.com/custserv";
		String queryHtml = hcb.getUrlRespHtml(url, headParams, postParams);
		
		try{
			/**解析imsi、procid等关健参数*/
			Document doc = DocumentHelper.parseText(queryHtml);
			Element root = doc.getRootElement();// 获得根节点
			Element dataEle=root.element("data");
			
			String resKindCode=dataEle.attributeValue("resKindCode");
			String xoption = dataEle.attributeValue("xoption");
			String emptyCardId=dataEle.attributeValue("emptyCardId");
			String procid=dataEle.attributeValue("procid");
			String imsi=dataEle.attributeValue("imsi");
			String capacityTypeCode=dataEle.attributeValue("capacityTypeCode");
			String cardData=dataEle.attributeValue("cardData");
			
			SimcardInfo simcardInfo=new SimcardInfo();
			simcardInfo.setCapacityTypeCode(capacityTypeCode);
			simcardInfo.setCardData(cardData);
			simcardInfo.setImsi(imsi);
			simcardInfo.setProcid(procid);
			simcardInfo.setEmptyCardId(emptyCardId);
			simcardInfo.setResKindCode(resKindCode);
			simcardInfo.setXoption(xoption);
			
			
			data.setSimcardInfo(simcardInfo);
			
			}catch(Exception e){
				log.info("解析响应的sim卡相关信息出错",e);
				log.info("解析响应的sim卡相关信息出错:\n"+queryHtml);
				throw new Exception("解析响应的sim卡相关信息出错");
			}
	}


	private static void queryInfoByPhone(HCBrowser hcb,ParameterData data) throws Exception {
		/**第一个请求，大概是校验号码的状态*/
		String phone=data.getBill().getSerialNumber();
		String queryPage="https://gd.cbss.10010.com/custserv?service=swallow/pub.chkcust.MainChkCust/queryCustAuth/1";
		queryPage+="?touchId=&serialNumber="+phone+"+&netTypeCode="+data.getBill().getNetTypeCode()+"&rightCode=csWriteSimCard&globalPageName=personalserv.writesimcard.WriteSimCard";
		
		String queryHtml = hcb.getUrlRespHtml(queryPage, headParams, null);
		
		/**第二个请求，根据手机号查询详细信息*/
		String url1="https://gd.cbss.10010.com/custserv";
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			
		postParams.add(new BasicNameValuePair("service","direct/1/personalserv.writesimcard.WriteSimCard/$MobTrade.$Form$0"));
		postParams.add(new BasicNameValuePair("sp","S0"));
		postParams.add(new BasicNameValuePair("Form0","ORDER_MGR,RElA_TRADE_ID,ORDER_TYPE,SUPPORT_TAG,COMM_SHARE_NBR_STRING,AC_INFOS,FORGIFT_USER_ID,QUERY_ACCOUNT_ID,_rightCode,inModeCode,NET_TYPE_CODE,SERIAL_NUMBER,subQueryTrade"));
		postParams.add(new BasicNameValuePair("SUPPORT_TAG",""));
		postParams.add(new BasicNameValuePair("COMM_SHARE_NBR_STRING",""));
		postParams.add(new BasicNameValuePair("AC_INFOS",""));
		postParams.add(new BasicNameValuePair("FORGIFT_USER_ID",""));
		postParams.add(new BasicNameValuePair("QUERY_ACCOUNT_ID",""));
		postParams.add(new BasicNameValuePair("_rightCode","csWriteSimCard"));
		postParams.add(new BasicNameValuePair("_tradeBase",data.getTradeBase()));//TODO 此tradebase可能要解析上一步响应中的值
    	//postParams.add(new BasicNameValuePair("_tradeBase","H4sIAAAAAAAAAFvzloG1fCXD8mqlkCBHF9f4kMgA13hnfxdXJStDQzMdqKiPc2i8n6MvUFApxDk4PrwosyTVObEoJSg1XUlHKTPPNz8l1RmIgfIGQIEg/9AQ13jXAMcgZ49IoNiTvbOfbtwIkvB09wiBGq+UXAw2JzgzF2SUEswuTxegHJwXHOIYEhoMFMnMyyxRqgUAJMlaBK8AAAA="));
		postParams.add(new BasicNameValuePair("inModeCode","0"));
		postParams.add(new BasicNameValuePair("ORDER_MGR",""));
		postParams.add(new BasicNameValuePair("RElA_TRADE_ID",""));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE",""));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER",data.getBill().getSerialNumber()));
		postParams.add(new BasicNameValuePair("subQueryTrade","查询"));
		
		queryHtml = hcb.getUrlRespHtml(url1, headParams, postParams);
		/**解析tradebase和tradeId*/
		analyzeParam(queryHtml, data);
		
//		log.info(queryHtml);
		
	}

	
	/**
     * 根据html解析必要参数
     * @param html html格式字符串
     * @param data 参数数据对象
     * @return 新参数数据对象
     * @throws Exception
     */
    private static ParameterData analyzeParam(String html, ParameterData data) throws Exception {
    	String tradeBase,allInfos;
    	/**解析tradebase值*/
    	String matStr="<input type=\"hidden\" id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^&].*)\"";
		Pattern pat = Pattern.compile(matStr);
		Matcher mat = pat.matcher(html);
		if(mat.find()){
			   tradeBase = mat.group(1);
		}else{
			log.info("未获取到正常dom结构!无法解析tradeBase:\n"+html);
			throw new Exception("未获取到正常dom结构!无法解析tradeBase");
		}
		
		log.warn("写卡（根据手机号查询详细信息）时取得tradeId时的html：\n"+html);
        
        /**解析tradeId的值*/
		matStr="<input type=\"text\" disabled=\"disabled\" id=\"_all_infos\" name=\"_all_infos\" value=\"([^&].*)\" style=\"display:none\"/>";
		pat=Pattern.compile(matStr);
		mat=pat.matcher(html);
		if(mat.find()){
			allInfos=mat.group(1);
	        allInfos = StringEscapeUtils.unescapeHtml(allInfos);
		}else{
			log.info("未获取到正常dom结构!无法解析tradeId:\n"+html);
			 throw new Exception("未获取到正常dom结构!无法解析tradeId");
		}

        /** 获得TRADE_ID */
        JSONObject json = JSONObject.fromObject(allInfos);
        String tradeId = json.getString("R_TRADE_ID");
        if (StringUtils.isBlank(tradeId)) {
        	log.info("未正常获取到TRADE_ID:\n"+html);
            throw new Exception("未正常获取到TRADE_ID!");
        }

        data.setTradeId(tradeId);
        data.setTradeBase(tradeBase);
        
        log.info("写卡（根据手机号查询详细信息）时取得tradeId:"+tradeId);
        
        log.info("写卡（根据手机号查询详细信息）时取得tradeBase:"+tradeBase);
		return data;
	}
    
    
	/**
     * 根据html解析必要参数
     * @param html html格式字符串
     * @param data 参数数据对象
     * @return 新参数数据对象
     * @throws Exception
     */
    private static ParameterData analyzeParam1(String html, ParameterData data) throws Exception {
    	String tradeBase;
    	
    	/**解析tradebase值*/
    	String matStr="<input type=\"hidden\" id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^&].*)\"";
		Pattern pat = Pattern.compile(matStr);
		Matcher mat = pat.matcher(html);
		if(mat.find()){
			   tradeBase = mat.group(1);
		}else{
			log.info("未获取到正常dom结构!无法解析tradeBase:\n"+html);
			throw new Exception("未获取到正常dom结构!无法解析tradeBase");
		}
        
		log.info(data.getTradeBase());
		
        data.setTradeBase(tradeBase);
        
        log.info(tradeBase);
		return data;
	}
    
    /**计算减免后的实收费用*/
    private static double calcFee(String derateFee){
    	double numDerateFee;//减免费用
    	double maxDeratefee=20.00;//实收费用
    	
    	try{
    		numDerateFee=Double.parseDouble(derateFee)/100;
    	}catch(Exception e){
    		//throw new Exception("");
    		log.info("前端传送的减免费用格式有误",e);
    		return maxDeratefee;
    	}
    	
    	return maxDeratefee-((numDerateFee >= 0 && numDerateFee<=maxDeratefee)?numDerateFee:maxDeratefee);
    }
    
  }


