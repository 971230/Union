package com.zte.cbss.autoprocess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.zte.cbss.autoprocess.model.ParameterData;
import com.zte.cbss.convert.ExtGeneration;
import com.zte.cbss.convert.ExtGenerationFree;

/**
 * 信息录入
 * @author kuanghaibo@gmail.com
 *
 */
public class InformationInput {

	
	private static Logger logger = Logger.getLogger(InformationInput.class);
	
	private static final String URL = "https://gd.cbss.10010.com/custserv"; //TODO @kuanghb 每一步URL的确定

    private static List<NameValuePair> headParams = new ArrayList<NameValuePair>();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    static {
        headParams.add(new BasicNameValuePair("Referer", URL));
        logger.info("Referer：" + headParams);
    }
    
    
    /**
     * 开户流程 -- step 3
     * 信息录入 --  【基本信息】选择发展人信息
     * 选择按钮发送的请求
     */
    private static String selectDevelopmentInfoBefore(HCBrowser hcb, ParameterData data){
    	
    	  List<NameValuePair> postDict = new ArrayList<NameValuePair>();
          postDict.add(new BasicNameValuePair("service", "page/common.query.CommonQuery"));
          postDict.add(new BasicNameValuePair("listener", "query"));
          postDict.add(new BasicNameValuePair("CLIENT_WIDTH", "763"));
          postDict.add(new BasicNameValuePair("PRO_SET_NAME", ""));
          postDict.add(new BasicNameValuePair("MODE", "1"));
          postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "4072"));
          postDict.add(new BasicNameValuePair("QUERY_SHEET", "0"));
          postDict.add(new BasicNameValuePair("DEPART_NAME", ""));
          postDict.add(new BasicNameValuePair("STAFF_NAME", ""));
          postDict.add(new BasicNameValuePair("SERIAL_NUMBER", ""));
          postDict.add(new BasicNameValuePair("STAFF_ID", data.getDevelopStaffId()));
          postDict.add(new BasicNameValuePair("PROVINCE_CODE", "51"));
          postDict.add(new BasicNameValuePair("EPARCHY_CODE", data.getEparchyCode()));
          postDict.add(new BasicNameValuePair("DEPART_ID", data.getDepartId()));
          postDict.add(new BasicNameValuePair("RowCount", "20"));
          postDict.add(new BasicNameValuePair("Page", "1"));
          postDict.add(new BasicNameValuePair("staffId", data.getStaffId()));
          postDict.add(new BasicNameValuePair("departId", data.getDepartId()));
          postDict.add(new BasicNameValuePair("subSysCode", "custserv"));
          postDict.add(new BasicNameValuePair("eparchyCode", data.getEparchyCode()));
         
          String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
          logger.debug("【step III -- 1】开户流程 -- 信息录入 -- 基本资料 ---根据发展人编码查询发展人信息 : "+ resphtml);
          
          try {
			analyzeDevelopParam(resphtml,data);
		} catch (Exception e) {
 			e.printStackTrace();
		}
          
          return resphtml;
    }
    
    /**
     *  开户流程 -- step 3
     *  信息录入 --  【基本信息】选择发展人信息
     *  Next
     * @param  
     * @return
     */
    private static String selectDevelopmentInfo(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step III -- 1】开户流程 -- 信息录入 --  选择发展人信息");
    	
    	//@Next --->基本资料 ---到--->群组信息
    	 // POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "page/groupserv.dealgrouprelation.DealGroupRelation"));
        postDict.add(new BasicNameValuePair("listener", "init"));
        postDict.add(new BasicNameValuePair("staffId", data.getStaffId()));
        postDict.add(new BasicNameValuePair("departId", data.getDepartId()));
        postDict.add(new BasicNameValuePair("subSysCode", "custserv"));
        postDict.add(new BasicNameValuePair("eparchyCode", data.getEparchyCode()));
       
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 1】开户流程 -- 信息录入 -- 基本资料 ---到--->群组信息_01 : "+ resphtml);
        return resphtml;
    	
    }
    
    /**
     * 基本资料 ---到--->群组信息
     * @return
     */
    private static String NextDevelopmentInfo(HCBrowser hcb, ParameterData data){
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.UtilityPage/needChkCust/1"));
        postDict.add(new BasicNameValuePair("SERIAL_NUMBER", ""));
        postDict.add(new BasicNameValuePair("NET_TYPE_CODE", ""));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "")); //undefined
        postDict.add(new BasicNameValuePair("judge", "1"));
        postDict.add(new BasicNameValuePair("globalPageName", "groupserv.dealgrouprelation.DealGroupRelation"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 1】开户流程 -- 信息录入 -- 基本资料 ---到--->群组信息_02: "+ resphtml);
        return resphtml;
        
    }
    
    
    /**
     *  开户流程 -- step 3
     *  信息录入 --  选择群组信息
     * @param  
     * @return
     */
    private static String selectGroupInfo(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step III -- 2】开户流程 -- 信息录入 --  选择群组信息");
    	
    	String result = "";
    	
    	return result;
    }
    
    /**
     *  开户流程 -- step 3
     *  信息录入 --  选择邮寄信息
     * @param  
     * @return
     */
    private static String selectMailInfo(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step III -- 3】开户流程 -- 信息录入 --  选择邮寄信息");
    	
    	String result = "";
    	
    	return result;
    }
    
    /**
     *  开户流程 -- step 3
     *  信息录入 --  选择亲情、集团信息
     * @param  
     * @return
     */
    private static String selectFamilyInfo(HCBrowser hcb, ParameterData data) {
    	
    	logger.debug("【step III -- 4】开户流程 -- 信息录入 --  选择亲情、集团信息");
    	
    	String result = "";
    	
    	return result;
    }
    
    /**
     * 信息录入-----跳到----->费用收取
     * @return
     * @throws Exception 
     */
    private static String nextStep3ToStep4(HCBrowser hcb, ParameterData data) throws Exception{
    	//发送了7(5)个链接
    	nextStep3ToStep4_01(hcb,data);//自由没有这个请求
    	nextStep3ToStep4_02(hcb,data);//自由业有这步
    	nextStep3ToStep4_03(hcb,data);//3和5自由是一个请求
    	nextStep3ToStep4_04(hcb,data);//没有请求
    	nextStep3ToStep4_05(hcb,data);//
    	nextStep3ToStep4_06(hcb,data);//6和7自由也一样
    	nextStep3ToStep4_07(hcb,data);//
    	
    	return "";
    }
    /**
     * 自由组合 信息录入-----跳到----->费用收取
     * @param hcb
     * @param data
     * @return
     * @throws Exception
     */
    private static String freeNextStep3ToStep4(HCBrowser hcb, ParameterData data) throws Exception{
    	
    	nextStep3ToStep4_02_02(hcb,data);
    	nextStep3ToStep4_03_05(hcb,data);
    	//3和5自由是一个请求
    	nextStep3ToStep4_04(hcb,data);
    	
    	nextStep3ToStep4_06(hcb,data);
    	nextStep3ToStep4_07(hcb,data);
    	
    	return "";
    }
    /**
     * 信息录入-----跳到----->费用收取--01
     * @return
     */
    private static String nextStep3ToStep4_01(HCBrowser hcb, ParameterData data){
    	//发第一个链接
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.UtilityPage/getSequence/1"));
        postDict.add(new BasicNameValuePair("seqName", "seq_item_id"));
        postDict.add(new BasicNameValuePair("registerName", "seq_item_id"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser")); 
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--01: "+ resphtml);
    	try {
			readSeqIdForXml(resphtml,data);
		} catch (DocumentException e) {
			// TODO kuanghaibo
			e.printStackTrace();
		}
        return resphtml;
    	
    }
    
    /**
     * 信息录入-----跳到----->费用收取--02
     * @return
     */
    private static String nextStep3ToStep4_02(HCBrowser hcb, ParameterData data){
    	//发第2个链接
    	Date now = new Date();
    	String innerEXT = new ExtGeneration().getExt(data);
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.createuser.CreateUser/submitMobTrade/1"));
        postDict.add(new BasicNameValuePair("Base", data.getTradeBase()));
        
        StringBuilder extValue = new StringBuilder();
        extValue.append("{");
        extValue.append("\"Common\": {\"ACTOR_NAME\": \"\", \"ACTOR_PHONE\": \"\", \"ACTOR_CERTTYPEID\": \"\", \"ACTOR_CERTNUM\": \"\", ");
        extValue.append("\"REMARK\": \"\", \"CUST_ID\": \"").append(data.getCustomInfo().getCustId()).append("\", \"USECUST_ID\": \"").append(data.getCustomInfo().getCustId());
        extValue.append("\", \"SERIAL_NUMBER\": \"").append(data.getBill().getSerialNumber()).append("\", \"ACCT_ID\": \"\", \"PAY_MODE_CODE\": \"0\", \"CITY_CODE\": \"");
        extValue.append(data.getBill().getUserCityCode()).append("\", \"PRODUCT_TYPE_CODE\": \"4G000001\", \"CUST_NAME\": \"").append(data.getCustomInfo().getCustName());
        extValue.append("\", \"USER_DIFF_CODE\": \"00\", \"PRODUCT_ID\": \"").append(data.getBill().getSelectedProductId()).append("\", \"BRAND_CODE\": \"4G00\", \"NET_TYPE_CODE\": \"50\"},");
        extValue.append(" \"TF_B_TRADE_ITEM\": {\"ITEM\": [{\"ATTR_CODE\": \"DEVELOP_STAFF_ID\", \"ATTR_VALUE\": \"\"}, {\"ATTR_CODE\": \"DEVELOP_DEPART_ID\", \"ATTR_VALUE\": \"\"}, ");
        extValue.append("{\"ATTR_CODE\": \"nbrSrc\", \"ATTR_VALUE\": \"2\"}, {\"ATTR_CODE\": \"NumberType\", \"ATTR_VALUE\": \"");
        
        if("185".equals((data.getBill().getSerialNumber().substring(0, 3)))){
        	extValue.append("185");
        }else{
        	extValue.append("***");
        }
        extValue.append("\"}");
        /*if(("99999828".equals(data.getBill().getSelectedProductId()) && "2".equals(data.getBill().getIdTypeCode()))
        	|| ("99999827".equals(data.getBill().getSelectedProductId()) && "3".equals(data.getBill().getIdTypeCode()))
        		){//136护照登记用户、166军人登记才有
        	extValue.append(",{\"ATTR_CODE\": \"NumThawPro\", \"ATTR_VALUE\": \"6000\" } ");
        }*/
        extValue.append("]}, ").append(innerEXT.substring(1, innerEXT.length() - 1));
        extValue.append(", \"TF_B_TRADE_USER\": {\"ITEM\": {\"IN_NET_MODE\": \"0\", \"CREDIT_VALUE\": \"0\", \"CREDIT_CLASS\": \"0\", \"X_DATATYPE\": ");
        extValue.append("\"NULL\", \"USER_PASSWD\": \"\", \"USER_TYPE_CODE\": \"0\", \"PREPAY_TAG\": \"1\", \"PRODUCT_TYPE_CODE\": \"4G000001\", \"DEVELOP_DEPART_ID\": \"").append(data.getDevelopDepartId());
        extValue.append("\", \"DEVELOP_EPARCHY_CODE\": \"").append(data.getDevelopEparchyCode()).append("\", \"DEVELOP_STAFF_ID\": \"").append(data.getDevelopStaffId());
        extValue.append("\", \"DEVELOP_CITY_CODE\": \"").append(data.getDevelopCityCode()).append("\"}}, \"TF_B_TRADE_RES\": {\"ITEM\": [{\"RES_TYPE_CODE\": \"0\", \"RES_CODE\": \"");
        extValue.append(data.getBill().getSerialNumber()).append("\", \"MODIFY_TAG\": \"0\", \"X_DATATYPE\": \"NULL\", \"START_DATE\": \"").append(sdf.format(now)).append("\",");
        extValue.append(" \"END_DATE\": \"2050-12-31 23:59:59\"}, {\"RES_TYPE_CODE\": \"1\", \"RES_CODE\": \"89860\", \"RES_INFO1\": \"\", \"RES_INFO5\": \"\", \"MODIFY_TAG\": \"0\", ");
        extValue.append("\"X_DATATYPE\": \"NULL\", \"START_DATE\": \"").append(sdf.format(now)).append("\", \"END_DATE\": \"2050-12-31 23:59:59\"}]}, \"TF_B_TRADE_ACCT\": ");
        extValue.append("{\"ITEM\": {\"PAY_NAME\": \"").append(data.getCustomInfo().getCustName()).append("\", \"PAY_MODE_CODE\": \"0\", \"X_DATATYPE\": \"NULL\", \"CREDIT_VALUE\": \"0\"}}, \"ITEM_INFO\": ");
        extValue.append("{\"GDealerInMet\": \"\", \"NET_CODE\": \"50\", \"INIT_PASSWD\": \"0\", \"USER_TYPE_CODE\": \"0\", \"USER_CALLING_AREA\": \"").append(data.getUserCallingArea());
        extValue.append("\"}, \"TRADE_ITEM\": {\"EXISTS_ACCT\": \"0\", \"REOPEN_TAG\": 2}, \"MY_INFO\": {\"PSPT_TYPE_CODE\": \"").append(data.getBill().getIdTypeCode()).append("\", \"PSPT_ID\": \"").append(data.getBill().getPsptId());
        extValue.append("\", \"SIM_CARD_NO\": \"89860\", \"SERIAL_NUMBER\": \"").append(data.getBill().getSerialNumber()).append("\"} ");
        
        /*if(!"2".equals(data.getBill().getIdTypeCode())){//护照用户的没有该节点
        	extValue.append(",\"TF_B_TRADE_OTHER\": {\"ITEM\": {\"RSRV_VALUE_CODE\": \"ZZFS\", \"RSRV_VALUE\": 2, \"RSRV_STR1\": \"");
        	extValue.append(data.getCustomInfo().getPsptId()).append("\", \"RSRV_STR2\": \"10\", \"MODIFY_TAG\": \"0\", \"RSRV_STR3\": \"-9\", \"RSRV_STR6\": \"1\", ");
        	extValue.append("\"RSRV_STR7\": \"0\", \"START_DATE\": \"").append(sdf.format(now)).append("\", \"END_DATE\": \"2050-12-31\", \"X_DATATYPE\": \"NULL\"}} ");
        }*/
    	extValue.append(",\"TRADE_OTHER_INFO\": {\"ITEM\": {\"CHECK_TYPE\": \"0\", \"BLACK_CUST\": \"0\"}}");
        extValue.append("}");
        
        logger.info(extValue.toString());
        postDict.add(new BasicNameValuePair("Ext", extValue.toString()));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--02: "+ resphtml);
        return resphtml;
    }
    /**
     * 自由组合
     * @param hcb
     * @param data
     * @return
     */
    private static String nextStep3ToStep4_02_02(HCBrowser hcb, ParameterData data){
    	//发第2个链接
    	Date now = new Date();
    	String innerEXT = new ExtGenerationFree().getExt(data);
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/personalserv.createuser.CreateUser/submitMobTrade/1"));
        postDict.add(new BasicNameValuePair("Base", data.getTradeBase()));
        
        StringBuilder extValue = new StringBuilder();
        extValue.append("{");
        extValue.append("\"Common\": {\"ACTOR_NAME\": \"\", \"ACTOR_PHONE\": \"\", \"ACTOR_CERTTYPEID\": \"\", \"ACTOR_CERTNUM\": \"\", ");
        extValue.append("\"REMARK\": \"\", \"CUST_ID\": \"").append(data.getCustomInfo().getCustId()).append("\", \"USECUST_ID\": \"").append(data.getCustomInfo().getCustId());
        extValue.append("\", \"SERIAL_NUMBER\": \"").append(data.getBill().getSerialNumber()).append("\", \"ACCT_ID\": \"\", \"PAY_MODE_CODE\": \"0\", \"CITY_CODE\": \"");
        extValue.append(data.getBill().getUserCityCode()).append("\", \"PRODUCT_TYPE_CODE\": \"4G000001\", \"CUST_NAME\": \"").append(data.getCustomInfo().getCustName());
        extValue.append("\", \"USER_DIFF_CODE\": \"00\", \"PRODUCT_ID\": \"").append(data.getBill().getSelectedProductId()).append("\", \"BRAND_CODE\": \"4G00\", \"NET_TYPE_CODE\": \"50\"},");
        extValue.append(" \"TF_B_TRADE_ITEM\": {\"ITEM\": [{\"ATTR_CODE\": \"DEVELOP_STAFF_ID\", \"ATTR_VALUE\": \"\"}, {\"ATTR_CODE\": \"DEVELOP_DEPART_ID\", \"ATTR_VALUE\": \"\"}, ");
        extValue.append("{\"ATTR_CODE\": \"nbrSrc\", \"ATTR_VALUE\": \"2\"}, {\"ATTR_CODE\": \"NumberType\", \"ATTR_VALUE\": \"");
        
        if("185".equals((data.getBill().getSerialNumber().substring(0, 3)))){
        	extValue.append("185");
        }else{
        	extValue.append("***");
        }
        extValue.append("\"}");
        /*if(("99999828".equals(data.getBill().getSelectedProductId()) && "2".equals(data.getBill().getIdTypeCode()))
        	|| ("99999827".equals(data.getBill().getSelectedProductId()) && "3".equals(data.getBill().getIdTypeCode()))
        		){//136护照登记用户、166军人登记才有
        	extValue.append(",{\"ATTR_CODE\": \"NumThawPro\", \"ATTR_VALUE\": \"6000\" } ");
        }*/
        extValue.append("]}, ").append(innerEXT.substring(1, innerEXT.length() - 1));
        extValue.append(", \"TF_B_TRADE_USER\": {\"ITEM\": {\"IN_NET_MODE\": \"0\", \"CREDIT_VALUE\": \"0\", \"CREDIT_CLASS\": \"0\", \"X_DATATYPE\": ");
        extValue.append("\"NULL\", \"USER_PASSWD\": \"\", \"USER_TYPE_CODE\": \"0\", \"PREPAY_TAG\": \"1\", \"PRODUCT_TYPE_CODE\": \"4G000001\", \"DEVELOP_DEPART_ID\": \"").append(data.getDevelopDepartId());
        extValue.append("\", \"DEVELOP_EPARCHY_CODE\": \"").append(data.getDevelopEparchyCode()).append("\", \"DEVELOP_STAFF_ID\": \"").append(data.getDevelopStaffId());
        extValue.append("\", \"DEVELOP_CITY_CODE\": \"").append(data.getDevelopCityCode()).append("\"}}, \"TF_B_TRADE_RES\": {\"ITEM\": [{\"RES_TYPE_CODE\": \"0\", \"RES_CODE\": \"");
        extValue.append(data.getBill().getSerialNumber()).append("\", \"MODIFY_TAG\": \"0\", \"X_DATATYPE\": \"NULL\", \"START_DATE\": \"").append(sdf.format(now)).append("\",");
        extValue.append(" \"END_DATE\": \"2050-12-31 23:59:59\"}, {\"RES_TYPE_CODE\": \"1\", \"RES_CODE\": \"89860\", \"RES_INFO1\": \"\", \"RES_INFO5\": \"\", \"MODIFY_TAG\": \"0\", ");
        extValue.append("\"X_DATATYPE\": \"NULL\", \"START_DATE\": \"").append(sdf.format(now)).append("\", \"END_DATE\": \"2050-12-31 23:59:59\"}]}, \"TF_B_TRADE_ACCT\": ");
        extValue.append("{\"ITEM\": {\"PAY_NAME\": \"").append(data.getCustomInfo().getCustName()).append("\", \"PAY_MODE_CODE\": \"0\", \"X_DATATYPE\": \"NULL\", \"CREDIT_VALUE\": \"0\"}}, \"ITEM_INFO\": ");
        extValue.append("{\"GDealerInMet\": \"\", \"NET_CODE\": \"50\", \"INIT_PASSWD\": \"0\", \"USER_TYPE_CODE\": \"0\", \"USER_CALLING_AREA\": \"").append(data.getUserCallingArea());
        extValue.append("\"}, \"TRADE_ITEM\": {\"EXISTS_ACCT\": \"0\", \"REOPEN_TAG\": 2}, \"MY_INFO\": {\"PSPT_TYPE_CODE\": \"").append(data.getBill().getIdTypeCode()).append("\", \"PSPT_ID\": \"").append(data.getBill().getPsptId());
        extValue.append("\", \"SIM_CARD_NO\": \"89860\", \"SERIAL_NUMBER\": \"").append(data.getBill().getSerialNumber()).append("\"} ");
        
        /*if(!"2".equals(data.getBill().getIdTypeCode())){//护照用户的没有该节点
        	extValue.append(",\"TF_B_TRADE_OTHER\": {\"ITEM\": {\"RSRV_VALUE_CODE\": \"ZZFS\", \"RSRV_VALUE\": 2, \"RSRV_STR1\": \"");
        	extValue.append(data.getCustomInfo().getPsptId()).append("\", \"RSRV_STR2\": \"10\", \"MODIFY_TAG\": \"0\", \"RSRV_STR3\": \"-9\", \"RSRV_STR6\": \"1\", ");
        	extValue.append("\"RSRV_STR7\": \"0\", \"START_DATE\": \"").append(sdf.format(now)).append("\", \"END_DATE\": \"2050-12-31\", \"X_DATATYPE\": \"NULL\"}} ");
        }*/
    	extValue.append(",\"TRADE_OTHER_INFO\": {\"ITEM\": {\"CHECK_TYPE\": \"0\", \"BLACK_CUST\": \"0\"}}");
        extValue.append("}");
        
        logger.info(extValue.toString());
        postDict.add(new BasicNameValuePair("Ext", extValue.toString()));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.createuser.CreateUser"));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--02: "+ resphtml);
        return resphtml;
    }
        
    /**
     * 信息录入-----跳到----->费用收取--03
     * @return
     */
    private static String nextStep3ToStep4_03(HCBrowser hcb, ParameterData data){
    	//发第3个链接
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "page/personalserv.dealtradefee.DealTradeFee"));
        postDict.add(new BasicNameValuePair("listener", ""));
        postDict.add(new BasicNameValuePair("staffId", data.getStaffId()));
        postDict.add(new BasicNameValuePair("departId", data.getDepartId()));
        postDict.add(new BasicNameValuePair("subSysCode", "custserv"));
        postDict.add(new BasicNameValuePair("eparchyCode", data.getEparchyCode()));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--03: "+ resphtml);
        return resphtml;
    	
    }
    /**
     * 自由组合费用收取
     * 信息录入-----跳到----->费用收取--03
     * @return
     */
    private static String nextStep3ToStep4_03_05(HCBrowser hcb, ParameterData data){
    	//发第3个链接
    	
    	// POST请求参数
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "page/personalserv.dealtradefee.DealTradeFee"));

        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "tradeType"));
        String paramValue = "{\"SUBSCRIBE_ID\": \""+data.getTradeId()+"\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"PROVINCE_ORDER_ID\": \""+data.getTradeId()+"\"}";
        postDict.add(new BasicNameValuePair("param",paramValue));
         //自由组合fee值为空
        postDict.add(new BasicNameValuePair("fee", ""));
        postDict.add(new BasicNameValuePair("noBack", ""));
        
        postDict.add(new BasicNameValuePair("listener", "init"));
        postDict.add(new BasicNameValuePair("staffId", data.getStaffId()));
        postDict.add(new BasicNameValuePair("departId", data.getDepartId()));
        postDict.add(new BasicNameValuePair("subSysCode", "custserv"));
        postDict.add(new BasicNameValuePair("eparchyCode", data.getEparchyCode()));
    	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--03: "+ resphtml);
        return resphtml;
    	
    }
    /**
     * 信息录入-----跳到----->费用收取--04
     * @return
     */
    private static String nextStep3ToStep4_04(HCBrowser hcb, ParameterData data){
    	//发第4个链接
    	//TODO @kuanghb 是否需要发送一次请求
    	//https://gd.cbss.10010.com/app/personalserv/dealtradefee/DealTradeFee.js
    	
    	 return "";
    	
    }
    
    /**
     * 信息录入-----跳到----->费用收取--05
     * @return
     * @throws Exception 
     */
    private static String nextStep3ToStep4_05(HCBrowser hcb, ParameterData data) throws Exception{
    	//发第5个链接
         List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "direct/1/personalserv.dealtradefee.DealTradeFee/$DirectLink"));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "tradeType"));
        String paramValue = "{\"SUBSCRIBE_ID\": \""+data.getTradeId()+"\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"PROVINCE_ORDER_ID\": \""+data.getTradeId()+"\"}";
        postDict.add(new BasicNameValuePair("param",paramValue));
        postDict.add(new BasicNameValuePair("fee", "{\"fee\": [{\"operateType\": \"1\", \"feeTypeCode\": \"100005\", \"payTag\": \"0\", \"tradeId\": \""+data.getTradeId()+"\", \"maxDerateFee\": \"0\", \"feeitemCode\": \"100005\", \"feeMode\": \"2\", \"oldfee\": \"12000\", \"fee\": \"12000\"}]}"));
        postDict.add(new BasicNameValuePair("noBack", ""));
     	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--05: "+ resphtml);

		analyzeParam(resphtml,data);
        
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--05--0001: "+ resphtml);
        return resphtml;
    	
    }
    
    /**
     * 信息录入-----跳到----->费用收取--06
     * @return
     */
    private static String nextStep3ToStep4_06(HCBrowser hcb, ParameterData data){
    	//发第6个链接
     
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", "swallow/common.UtilityPage/needChkCust/1"));
        postDict.add(new BasicNameValuePair("SERIAL_NUMBER", ""));
        postDict.add(new BasicNameValuePair("NET_TYPE_CODE",""));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE",""));
        postDict.add(new BasicNameValuePair("judge","1"));
        postDict.add(new BasicNameValuePair("globalPageName", "personalserv.dealtradefee.DealTradeFee"));
     	
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--06: "+ resphtml);
        return resphtml;
    	
    }
    
    /**
     * 信息录入-----跳到----->费用收取--07
     * @return
     */
    private static String nextStep3ToStep4_07(HCBrowser hcb, ParameterData data){
    	
    	String url ="https://gd.cbss.10010.com/essframe?service=ajaxDirect/1/component.NavMenu/component.NavMenu/javascript/NotifysPart&pagename=component.NavMenu&eventname=queryNotifys&staffId=DSZDGL01&departId=51b12z3&subSysCode=BSS&eparchyCode=0757&partids=NotifysPart&random="+data.getRandom()+"&ajaxSubmitType=get";
    	
    	
    	//发第7个链接
//     
//        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
//        postDict.add(new BasicNameValuePair("service", "ajaxDirect/1/component.NavMenu/component.NavMenu/javascript/NotifysPart"));
//        postDict.add(new BasicNameValuePair("pagename", "component.NavMenu"));
//        postDict.add(new BasicNameValuePair("eventname","queryNotifys"));
//        postDict.add(new BasicNameValuePair("staffId","DSZDGL01"));
//        postDict.add(new BasicNameValuePair("departId","51b12z3"));
//        postDict.add(new BasicNameValuePair("subSysCode","BSS"));
//        postDict.add(new BasicNameValuePair("eparchyCode","0757"));
//        postDict.add(new BasicNameValuePair("partids","NotifysPart"));
//        postDict.add(new BasicNameValuePair("random","2014524124644857"));
//        postDict.add(new BasicNameValuePair("ajaxSubmitType", "get"));
//     	
//        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
        String resphtml  = hcb.getUrlRespHtml(url,"gb2312");
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--07: "+ resphtml);
        return resphtml;
    	
    }


    /**
     * 执行信息录入 
     * @param hcb
     * @param data
     * @return
     * @throws Exception 
     */
    public static ParameterData executeInfomationInput(HCBrowser hcb, ParameterData data) throws Exception {
    	
    	selectDevelopmentInfoBefore(hcb,data);
    	 selectDevelopmentInfo(hcb,data);
    	NextDevelopmentInfo(hcb,data);
    	nextStep3ToStep4(hcb,data);
    	
    	return data;
    }
    /**
     * 
     * @param hcb
     * @param data
     * @return
     * @throws Exception
     */
 public static ParameterData executeFreeInfomationInput(HCBrowser hcb, ParameterData data) throws Exception {
    	
    	selectDevelopmentInfoBefore(hcb,data);
    	 selectDevelopmentInfo(hcb,data);
    	NextDevelopmentInfo(hcb,data);
    	freeNextStep3ToStep4(hcb,data);
    	
    	return data;
    }
    
    /**
     * 根据html解析必要参数
     * @param html  html格式字符串
     * @param data  参数数据对象
     * @return 新参数数据对象
     * @throws Exception 
     */
    private static ParameterData analyzeDevelopParam(String html,ParameterData data) throws Exception{
    	Parser parser;
        NodeList list=null;
        NodeFilter tableFilter = new NodeClassFilter(TableTag.class); 
		try {
			parser =Parser.createParser(html, "gbk");
			list = parser.extractAllNodesThatMatch(tableFilter);
		} catch (ParserException e) {
			logger.info("解析出错",e);
			logger.info("获取发展人信息页面的结构有变，不能正常解析:\n"+html);
			throw new Exception("获取发展人信息页面的结构有变，不能正常解析!");
		}
		if(list==null||list.size()==0){
			logger.info("未获取到正常dom结构:\n"+html);
			throw new Exception("未获取到正常dom结构!");
		}
        TableTag table = (TableTag) list.elementAt(0);
        TableRow [] rows = table.getRows();
        TableRow tr = rows[1];
        TableColumn[] td = tr.getColumns();
 
		/**获得developDepartId*/
		String developDepartId=td[2].toPlainTextString();
		data.setDevelopDepartId(developDepartId.trim());
		
    	return data;
    }
    
    /**
     * 根据html解析必要参数
     * @param html  html格式字符串
     * @param data  参数数据对象
     * @return 新参数数据对象
     * @throws Exception 
     */
    private static ParameterData analyzeParam(String html,ParameterData data) throws Exception{
    	Parser parser;
        NodeList list=null;
       
		NodeFilter trFilter = new TagNameFilter("tr");
        NodeFilter hasAttrFilter = new HasAttributeFilter("rowTag", "tradeMain"); 
        NodeFilter andFilter = new AndFilter(trFilter, hasAttrFilter); 
       
		try {
			parser =Parser.createParser(html, "gbk");
			list= parser.parse(andFilter);
		} catch (ParserException e) {
			logger.info("解析出错",e);
			logger.info("费用信息页面的结构有变，不能正常解析:\n"+html);
			throw new Exception("费用信息页面的结构有变，不能正常解析!");
		}
		if(list==null||list.size()==0){
			logger.info("未获取到正常dom结构:\n"+html);
			throw new Exception("未获取到正常dom结构!");
		}
		Node node=list.elementAt(0);
		NodeList nodeList=node.getChildren();
		NodeFilter tdFilter = new TagNameFilter("td");
		NodeList tdNodeList=nodeList.extractAllNodesThatMatch(tdFilter,true);

		TableColumn row2=(TableColumn)tdNodeList.elementAt(9);
		TableColumn row3=(TableColumn)tdNodeList.elementAt(10);

		/**获得userId和acctId*/
		String userId=row2.getStringText();
		String acctId=row3.getStringText();
		data.setUserId(userId);
		data.setAcctId(acctId);
		
    	return data;
    }
    
    /**
     * 读取Seq_id 
     * @param respXml 返回的xml
     * @return 
     * @throws DocumentException
     */
    public static ParameterData readSeqIdForXml(String respXml,ParameterData data) throws DocumentException{
    	Document document = DocumentHelper.parseText(respXml);
		Element root = document.getRootElement();
		Element infoElement = root.element("seq_item_id");
		String seq_id =infoElement.attributeValue("seq");
		data.setItemId(seq_id);
		return data;
    }
    
    public static void main(String[] args) throws Exception {
    	ParameterData data=new ParameterData();
    	String html = MyTools.openFile("D:\\test01\\test.html");
    	analyzeDevelopParam(html,data);
    	logger.info(data.getDevelopDepartId());
	}
}
