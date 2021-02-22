/**
 * 
 */
package com.zte.cbss.autoprocess.model.data;

/**
 * @author ZX
 * SPDataSubmit_1.java
 * 2015-1-19
 */
public class SPDataSubmit_4 implements Cloneable {

	private String tradeId;
	private String tradeTypeCode;
	private String TRADE_ID_MORE_STR;
	private String SERIAL_NUMBER_STR;
	private String TRADE_TYPE_CODE_STR;
	private String NET_TYPE_CODE_STR;
	private String strNetType;
	private String globalPageName;
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeTypeCode() {
		return tradeTypeCode;
	}
	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}
	public String getTRADE_ID_MORE_STR() {
		return TRADE_ID_MORE_STR;
	}
	public void setTRADE_ID_MORE_STR(String tRADE_ID_MORE_STR) {
		TRADE_ID_MORE_STR = tRADE_ID_MORE_STR;
	}
	public String getSERIAL_NUMBER_STR() {
		return SERIAL_NUMBER_STR;
	}
	public void setSERIAL_NUMBER_STR(String sERIAL_NUMBER_STR) {
		SERIAL_NUMBER_STR = sERIAL_NUMBER_STR;
	}
	public String getTRADE_TYPE_CODE_STR() {
		return TRADE_TYPE_CODE_STR;
	}
	public void setTRADE_TYPE_CODE_STR(String tRADE_TYPE_CODE_STR) {
		TRADE_TYPE_CODE_STR = tRADE_TYPE_CODE_STR;
	}
	public String getNET_TYPE_CODE_STR() {
		return NET_TYPE_CODE_STR;
	}
	public void setNET_TYPE_CODE_STR(String nET_TYPE_CODE_STR) {
		NET_TYPE_CODE_STR = nET_TYPE_CODE_STR;
	}
	public String getStrNetType() {
		return strNetType;
	}
	public void setStrNetType(String strNetType) {
		this.strNetType = strNetType;
	}
	public String getGlobalPageName() {
		return globalPageName;
	}
	public void setGlobalPageName(String globalPageName) {
		this.globalPageName = globalPageName;
	}
	
}
