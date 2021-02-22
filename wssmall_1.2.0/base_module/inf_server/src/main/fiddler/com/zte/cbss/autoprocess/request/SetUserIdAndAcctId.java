/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.zte.cbss.autoprocess.HCBrowser;
import com.zte.cbss.autoprocess.model.ParameterData;

/**
 * @author ZX
 * CommonSetUserIdAndAcctId.java
 * 设置USERID AND ACCID
 * 2015-1-19
 */
public class SetUserIdAndAcctId {
	
	private static Logger logger = Logger.getLogger(SetUserIdAndAcctId.class);
	private static final String URL = "https://gd.cbss.10010.com/custserv"; //TODO @kuanghb 每一步URL的确定
	private List<NameValuePair> headParams = new ArrayList<NameValuePair>();
	private String serviceUrl;
	private String fee;
	private String noBack;
	private String listener;
	private String staffId;
	private String departId;
	private String subSysCode;
	private String eparchyCode;
	
	/**
     * 信息录入-----跳到----->费用收取--05
     * @return
     * @throws Exception 
     */
    public String nextStep3ToStep4_05(HCBrowser hcb, ParameterData data) throws Exception{
    	//发第5个链接
        List<NameValuePair> postDict = new ArrayList<NameValuePair>();
        postDict.add(new BasicNameValuePair("service", serviceUrl));
        postDict.add(new BasicNameValuePair("TRADE_TYPE_CODE", "tradeType"));
        String paramValue = "{\"SUBSCRIBE_ID\": \""+data.getTradeId()+"\", \"TRADE_ID\": \""+data.getTradeId()+"\", \"PROVINCE_ORDER_ID\": \""+data.getTradeId()+"\"}";
        postDict.add(new BasicNameValuePair("param",paramValue));
        postDict.add(new BasicNameValuePair("fee", fee));
        postDict.add(new BasicNameValuePair("noBack", noBack));
        postDict.add(new BasicNameValuePair("staffId", staffId));
        postDict.add(new BasicNameValuePair("departId", departId));
        postDict.add(new BasicNameValuePair("subSysCode", subSysCode));
        postDict.add(new BasicNameValuePair("eparchyCode", eparchyCode));
        postDict.add(new BasicNameValuePair("listener", listener));
        
        headParams.add(new BasicNameValuePair("Referer", URL));
        String resphtml  = hcb.getUrlRespHtml(URL, headParams, postDict);
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--05: "+ resphtml);

		analyzeParam(resphtml,data);
        
    	logger.debug("【step III -- 4】信息录入-----跳到----->费用收取--05--0001: "+ resphtml);
        return resphtml;
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

	public List<NameValuePair> getHeadParams() {
		return headParams;
	}

	public void setHeadParams(List<NameValuePair> headParams) {
		this.headParams = headParams;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public static String getUrl() {
		return URL;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getSubSysCode() {
		return subSysCode;
	}

	public void setSubSysCode(String subSysCode) {
		this.subSysCode = subSysCode;
	}

	public String getEparchyCode() {
		return eparchyCode;
	}

	public void setEparchyCode(String eparchyCode) {
		this.eparchyCode = eparchyCode;
	}

	public String getNoBack() {
		return noBack;
	}

	public void setNoBack(String noBack) {
		this.noBack = noBack;
	}
    
}
