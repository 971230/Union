package params.onlinepay.resp;

import java.util.Map;

import params.ZteResponse;

/**
 * 在线支付跳转银行界面反回参数
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePayResp extends ZteResponse {

	private String transaction_id;
	
	private String bank_url;
	
	private String online_flag;//0在线 1线下
	
	private Map<String,String> resultMap;

	private String formHtml;//与银行交互
	
	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOnline_flag() {
		return online_flag;
	}

	public void setOnline_flag(String online_flag) {
		this.online_flag = online_flag;
	}

	public String getBank_url() {
		return bank_url;
	}

	public void setBank_url(String bank_url) {
		this.bank_url = bank_url;
	}

	private String getStrFromMap(Map map, String key) {
		if ((map == null || map.isEmpty()) && !map.containsKey(key)) {
			return "";
		}
		Object value = map.get(key);
		if (value == null) {
			value = map.get(key.toUpperCase());
		}
		if (value == null) {
			value = map.get(key.toLowerCase());
		}
		if (value == null) {
			value = "";
		}
		return value.toString();
	}
	
	public String getFormHtml() {
		StringBuffer html = new StringBuffer("");
		html.append("	<form method=\"post\" id=\"bankForm\" action=\""+this.bank_url+"\">");
		html.append("		<input type=\"hidden\" name=\"MERCHANTID\" id=\"MERCHANTID\" value=\""+getStrFromMap(resultMap,"MERCHANTID")+"\" >");
		html.append("		<input type=\"hidden\" name=\"SUBMERCHANTID\" id=\"SUBMERCHANTID\" value=\""+getStrFromMap(resultMap,"SUBMERCHANTID")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ORDERSEQ\" id=\"ORDERSEQ\" value=\""+getStrFromMap(resultMap,"ORDERSEQ")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ORDERREQTRANSEQ\" id=\"ORDERREQTRANSEQ\" value=\""+getStrFromMap(resultMap,"ORDERREQTRANSEQ")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ORDERDATE\" id=\"ORDERDATE\" value=\""+getStrFromMap(resultMap,"ORDERDATE")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ORDERAMOUNT\" id=\"ORDERAMOUNT\" value=\""+getStrFromMap(resultMap,"ORDERAMOUNT")+"\" >");
		html.append("		<input type=\"hidden\" name=\"PRODUCTAMOUNT\" id=\"PRODUCTAMOUNT\" value=\""+getStrFromMap(resultMap,"PRODUCTAMOUNT")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ATTACHAMOUNT\" id=\"ATTACHAMOUNT\" value=\""+getStrFromMap(resultMap,"ATTACHAMOUNT")+"\" >");
		html.append("		<input type=\"hidden\" name=\"CURTYPE\" id=\"CURTYPE\" value=\""+getStrFromMap(resultMap,"CURTYPE")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ENCODETYPE\" id=\"ENCODETYPE\" value=\""+getStrFromMap(resultMap,"ENCODETYPE")+"\" >");
		html.append("		<input type=\"hidden\" name=\"MERCHANTURL\" id=\"MERCHANTURL\" value=\""+getStrFromMap(resultMap,"MERCHANTURL")+"\" >");
		html.append("		<input type=\"hidden\" name=\"BACKMERCHANTURL\" id=\"BACKMERCHANTURL\" value=\""+getStrFromMap(resultMap,"BACKMERCHANTURL")+"\" >");
		html.append("		<input type=\"hidden\" name=\"BANKID\" id=\"BANKID\" value=\""+getStrFromMap(resultMap,"BANKID")+"\" >");
		html.append("		<input type=\"hidden\" name=\"ATTACH\" id=\"ATTACH\" value=\""+getStrFromMap(resultMap,"ATTACH")+"\" >");
		html.append("		<input type=\"hidden\" name=\"BUSICODE\" id=\"BUSICODE\" value=\""+getStrFromMap(resultMap,"BUSICODE")+"\" >");
		html.append("		<input type=\"hidden\" name=\"PRODUCTID\" id=\"PRODUCTID\" value=\""+getStrFromMap(resultMap,"PRODUCTID")+"\" >");
		html.append("		<input type=\"hidden\" name=\"TMNUM\" id=\"TMNUM\" value=\""+getStrFromMap(resultMap,"TMNUM")+"\" >");
		html.append("		<input type=\"hidden\" name=\"CUSTOMERID\" id=\"CUSTOMERID\" value=\""+getStrFromMap(resultMap,"CUSTOMERID")+"\" >");
		html.append("		<input type=\"hidden\" name=\"PRODUCTDESC\" id=\"PRODUCTDESC\" value=\""+getStrFromMap(resultMap,"PRODUCTDESC")+"\" >");			
		html.append("		<input type=\"hidden\" name=\"MAC\" id=\"MAC\" value=\""+getStrFromMap(resultMap,"MAC")+"\" >");
		html.append("		<input type=\"hidden\" name=\"CLIENTIP\" id=\"CLIENTIP\" value=\""+getStrFromMap(resultMap,"CLIENTIP")+"\" >");
		html.append("	</form>");
		formHtml = html.toString();
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}
	
}
