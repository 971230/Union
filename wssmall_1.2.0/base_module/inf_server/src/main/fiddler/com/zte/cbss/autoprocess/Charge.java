package com.zte.cbss.autoprocess;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.model.ParameterData;

/**
 * 费用收取
 * @author kuanghaibo@gmail.com
 *
 */
public class Charge {

	public static Logger logger = Logger.getLogger(Charge.class);
	
    private static final String URL = "https://gd.cbss.10010.com/custserv";

    private static List<NameValuePair> headParams = new ArrayList<NameValuePair>();

    static {
        headParams.add(new BasicNameValuePair("Referer", URL));
        logger.info("Referer：" + headParams);
    }
	
	/**
     *  开户流程 -- step 4
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    public static ParameterData submitOrder(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV】开户流程 -- 费用收取 --  提交订单");
    	//发送了4个请求
    	
    	submitOrder_01(hcb,data);
    	submitOrder_02(hcb,data);
    	submitOrder_03(hcb,data);
    	submitOrder_04(hcb,data);
    	
    	return data;
    }
    
    /**
     * 自由组合提交
     * @param hcb
     * @param data
     * @return
     */
public static ParameterData submitFreeOrder(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV】开户流程 -- 费用收取 --  提交订单");
    	//发送了4个请求
    	
    	submitOrder_01(hcb,data);
    	submitFreeOrder_02(hcb,data);
    	submitOrder_03(hcb,data);
    	submitOrder_04(hcb,data);
    	
    	return data;
    }
    /**
     *  开户流程 -- step 4 --01
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    private static String submitOrder_01(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV -- 1】开户流程 -- 费用收取 --  提交订单--->第一个请求");
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/getPayCodeMutex/1"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step IV -- 1】开户流程 -- 费用收取 --  提交订单--->第一个请求 : "+ resphtml);
        return resphtml;
    }
    
    /**
     *  开户流程 -- step 4 --02
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    private static String submitOrder_02(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV -- 1---2 】开户流程 -- 费用收取 --  提交订单--->第2个请求");
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1"));
        postDict.add(new BasicNameValuePair("cancelTag", "false"));
        postDict.add(new BasicNameValuePair("funcType", "0"));
        postDict.add(new BasicNameValuePair("dataType", "0"));
        String tradeValue = "[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"TRADE_TYPE\": \"开户\", \"SERIAL_NUMBER\": \""+data.getBill().getSerialNumber()+"\", \"TRADE_FEE\": \"0.00\", \"CUST_NAME\": \""+data.getCustomInfo().getCustName()+"\", \"CUST_ID\": \""+data.getCustomInfo().getCustId()+"\", \"USER_ID\": \""+data.getUserId()+"\", \"ACCT_ID\": \""+data.getAcctId()+"\", \"NET_TYPE_CODE\": \""+data.getBill().getNetTypeCode()+"\", \"TRADE_TYPE_CODE\": \"10\"}]";
        postDict.add(new BasicNameValuePair("tradeMain", tradeValue));
        String feesValue = "[{\"X_TAG\": \"0\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"2\", \"FEEITEM_CODE\": \"100005\", \"OLDFEE\": \"120.00\", \"FEE\": \"120.00\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"10\", \"NET_TYPE_CODE\": \""+data.getBill().getNetTypeCode()+"\", \"FEEITEM_NAME\": \""+data.getBill().getFeeItemName()+"\"}]";
        postDict.add(new BasicNameValuePair("fees", feesValue));
        String unChargedfeesValue = "[{\"X_TAG\": \"0\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"CALCULATE_ID\": \"\", \"FEE_MODE\": \"2\", \"FEEITEM_CODE\": \"100005\", \"OLDFEE\": \"120.00\", \"FEE\": \"120.00\", \"DERATE_REMARK\": \"\", \"PAY_MONEY_CODE\": \"0\", \"PAY_TAG\": \"0\", \"CALCULATE_TAG\": \"N\", \"MODIFY_TAG\": \"\", \"TRADE_TYPE_CODE\": \"10\", \"NET_TYPE_CODE\": \""+data.getBill().getNetTypeCode()+"\", \"FEEITEM_NAME\": \""+data.getBill().getFeeItemName()+"\"}]";
        postDict.add(new BasicNameValuePair("unChargedfees", unChargedfeesValue));
        postDict.add(new BasicNameValuePair("feePayMoney", "[]"));
        postDict.add(new BasicNameValuePair("feeCheck", "[]"));
        postDict.add(new BasicNameValuePair("feePos", "[]"));
        String baseValue = "{\"prepayTag\": \"\", \"tradeTypeCode\": \"0010\", \"strisneedprint\": \"1\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \""+data.getBill().getNetTypeCode()+"\"}";
        postDict.add(new BasicNameValuePair("base",baseValue));
        postDict.add(new BasicNameValuePair("CASH", "0.00"));
        postDict.add(new BasicNameValuePair("SEND_TYPE", "0"));
        postDict.add(new BasicNameValuePair("TRADE_ID", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("TRADE_ID_MORE_STR", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("SERIAL_NUMBER_STR", ""+data.getBill().getSerialNumber()+","));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "10,"));
        postDict.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
        postDict.add(new BasicNameValuePair("DEBUTY_CODE", ""));
        postDict.add(new BasicNameValuePair("IS_NEED_WRITE_CARD", "true"));
        postDict.add(new BasicNameValuePair("WRAP_TRADE_TYPE", "tradeType"));
        postDict.add(new BasicNameValuePair("CUR_TRADE_IDS", ""));
        postDict.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES", ""));
        postDict.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS", ""));
        postDict.add(new BasicNameValuePair("CUR_NET_TYPE_CODES", ""));
        postDict.add(new BasicNameValuePair("isAfterFee", "2"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step IV -- 2】开户流程 -- 费用收取 --  提交订单--->第2个请求 : "+ resphtml);
        return resphtml;
    }
    
    /**
     *  开户流程 -- step 4 --02
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    private static String submitFreeOrder_02(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV -- 1---2 】开户流程 -- 费用收取 --  提交订单--->第2个请求");
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/continueTradeReg/1"));
        postDict.add(new BasicNameValuePair("cancelTag", "false"));
        postDict.add(new BasicNameValuePair("funcType", "0"));
        postDict.add(new BasicNameValuePair("dataType", "0"));
        String tradeValue = "[{\"TRADE_ID\": \""+data.getTradeId()+"\", \"TRADE_TYPE\": \"开户\", \"SERIAL_NUMBER\": \""+data.getBill().getSerialNumber()+"\", \"TRADE_FEE\": \"0.00\", \"CUST_NAME\": \""+data.getCustomInfo().getCustName()+"\", \"CUST_ID\": \""+data.getCustomInfo().getCustId()+"\", \"USER_ID\": \""+data.getUserId()+"\", \"ACCT_ID\": \""+data.getAcctId()+"\", \"NET_TYPE_CODE\": \""+data.getBill().getNetTypeCode()+"\", \"TRADE_TYPE_CODE\": \"10\"}]";
        postDict.add(new BasicNameValuePair("tradeMain", tradeValue));
        postDict.add(new BasicNameValuePair("fees", "[]"));
        postDict.add(new BasicNameValuePair("unChargedfees", "[]"));
        postDict.add(new BasicNameValuePair("feePayMoney", "[]"));
        postDict.add(new BasicNameValuePair("feeCheck", "[]"));
        postDict.add(new BasicNameValuePair("feePos", "[]"));
        String baseValue = "{\"prepayTag\": \"\", \"tradeTypeCode\": \"0010\", \"strisneedprint\": \"1\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \""+data.getBill().getNetTypeCode()+"\"}";
        postDict.add(new BasicNameValuePair("base",baseValue));
        postDict.add(new BasicNameValuePair("CASH", "0.00"));
        postDict.add(new BasicNameValuePair("SEND_TYPE", "0"));
        postDict.add(new BasicNameValuePair("TRADE_ID", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("TRADE_ID_MORE_STR", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("SERIAL_NUMBER_STR", ""+data.getBill().getSerialNumber()+","));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE_STR", "10,"));
        postDict.add(new BasicNameValuePair("NET_TYPE_CODE_STR", "50,"));
        postDict.add(new BasicNameValuePair("DEBUTY_CODE", ""));
        postDict.add(new BasicNameValuePair("IS_NEED_WRITE_CARD", "true"));
        postDict.add(new BasicNameValuePair("WRAP_TRADE_TYPE", "tradeType"));
        postDict.add(new BasicNameValuePair("CUR_TRADE_IDS", ""));
        postDict.add(new BasicNameValuePair("CUR_TRADE_TYPE_CODES", ""));
        postDict.add(new BasicNameValuePair("CUR_SERIAL_NUMBERS", ""));
        postDict.add(new BasicNameValuePair("CUR_NET_TYPE_CODES", ""));
        postDict.add(new BasicNameValuePair("isAfterFee", ""));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step IV -- 2】开户流程 -- 费用收取 --  提交订单--->第2个请求 : "+ resphtml);
        return resphtml;
    }
    /**
     *  开户流程 -- step 4 --03
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    private static String submitOrder_03(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV -- 1--3】开户流程 -- 费用收取 --  提交订单--->第3个请求");
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/resourceMphoneNo/1"));
        String baseValue = "{\"prepayTag\": \"\", \"tradeTypeCode\": \"0010\", \"strisneedprint\": \"1\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \""+data.getBill().getNetTypeCode()+"\"}";
        postDict.add(new BasicNameValuePair("base", baseValue));
        postDict.add(new BasicNameValuePair("TRADE_ID", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step IV --3】开户流程 -- 费用收取 --  提交订单--->第3个请求 : "+ resphtml);
        return resphtml;
    }
    
    
    /**
     *  开户流程 -- step 4 --01
     *  费用收取 --  提交订单
     * @param  
     * @return
     */
    private static String submitOrder_04(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step IV -- 4--4 】开户流程 -- 费用收取 --  提交订单--->第4个请求");
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.dealtradefee.DealTradeFee/archiveMphoneNo/1"));
        String baseValue = "{\"prepayTag\": \"\", \"tradeTypeCode\": \"0010\", \"strisneedprint\": \"1\", \"serialNumber\": \""+data.getBill().getSerialNumber()+"\", \"tradeReceiptInfo\": \"[{\\\"RECEIPT_INFO5\\\":\\\"\\\",\\\"RECEIPT_INFO2\\\":\\\"\\\",\\\"RECEIPT_INFO1\\\":\\\"\\\",\\\"RECEIPT_INFO4\\\":\\\"\\\",\\\"RECEIPT_INFO3\\\":\\\"\\\"}]\", \"netTypeCode\": \""+data.getBill().getNetTypeCode()+"\"}";
        postDict.add(new BasicNameValuePair("base", baseValue));
        postDict.add(new BasicNameValuePair("TRADE_ID", ""+data.getTradeId()+""));
        postDict.add(new BasicNameValuePair("netCode", "50"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step IV 4--4】开户流程 -- 费用收取 --  提交订单--->第4个请求 : "+ resphtml);
        return resphtml;
    }
}
