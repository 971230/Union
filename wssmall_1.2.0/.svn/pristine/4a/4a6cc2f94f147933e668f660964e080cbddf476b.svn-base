/**
 * 
 */
package com.zte.cbss.autoprocess.request;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.HCBrowser;
import com.zte.cbss.autoprocess.WriteCard;
import com.zte.cbss.autoprocess.model.ParameterData;

/**
 * @author ZX
 * SPBuy_2.java
 * 2015-1-19
 */
public class SPBuy_2 {
	
	private static final Logger log=Logger.getLogger(WriteCard.class);
	
	private static List<NameValuePair> headParams = new ArrayList<NameValuePair>();
	
	static{
		headParams.add(new BasicNameValuePair("Referer","https://gd.cbss.10010.com/essframe?service=page/LoginProxy&login_type=redirectLogin"));
	}
	
	public String qryTradeBase_2(HCBrowser hcb,ParameterData data, 
			String tradeBase, String phone) throws Exception {
		String base = "";
		String url = "https://gd.cbss.10010.com/custserv";
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("service", "direct/1/personalserv.platformtrade.SpTrade/$MobTrade.$Form$0"));
		postParams.add(new BasicNameValuePair("sp", "S0"));
		postParams.add(new BasicNameValuePair("Form0", "ORDER_MGR,RElA_TRADE_ID,ORDER_TYPE,SUPPORT_TAG,COMM_SHARE_NBR_STRING,AC_INFOS,FORGIFT_USER_ID,QUERY_ACCOUNT_ID,_rightCode,inModeCode,NET_TYPE_CODE,SERIAL_NUMBER,subQueryTrade"));
		postParams.add(new BasicNameValuePair("SUPPORT_TAG", ""));
		postParams.add(new BasicNameValuePair("COMM_SHARE_NBR_STRING", ""));
		postParams.add(new BasicNameValuePair("AC_INFOS", ""));
		postParams.add(new BasicNameValuePair("FORGIFT_USER_ID", ""));
		postParams.add(new BasicNameValuePair("QUERY_ACCOUNT_ID", ""));
		postParams.add(new BasicNameValuePair("_rightCode", "csSpTrade"));
		postParams.add(new BasicNameValuePair("_tradeBase", tradeBase));
		postParams.add(new BasicNameValuePair("inModeCode", "0"));
		postParams.add(new BasicNameValuePair("ORDER_MGR", ""));
		postParams.add(new BasicNameValuePair("RElA_TRADE_ID", ""));
		postParams.add(new BasicNameValuePair("ORDER_TYPE", ""));
		postParams.add(new BasicNameValuePair("NET_TYPE_CODE", "50"));
		postParams.add(new BasicNameValuePair("SERIAL_NUMBER", phone));
		postParams.add(new BasicNameValuePair("subQueryTrade", "��ѯ"));
		String html = hcb.getUrlRespHtml(url, postParams, null);
		String matStr="<input type=\"hidden\" id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^&].*)\"";
		Pattern pat = Pattern.compile(matStr);
		Matcher mat = pat.matcher(html);
		if(mat.find()){
			   base = mat.group(1);
		}else{
			log.info("未获取到正常dom结构!无法解析tradeBase:\n"+html);
			throw new Exception("未获取到正常dom结构!无法解析tradeBase");
		}
		return base;
	}
	
}
