package com.zte.cbss.autoprocess.model.data;

/**
 * @author ZX
 * SPDataSubmit.java
 * 2015-1-17
 */
public class SPDataSubmit implements Cloneable {

	private boolean cancelTag = false;
	private String funcType;
	private String dataType;
	private String tradeMain;
	private String fees;
	private String unChargedfees;
	private String feePayMoney;
	private String feeCheck;
	private String feePos;
	private String base;
	private String CASH;
	private String SEND_TYPE;
	private String TRADE_ID;
	private String TRADE_ID_MORE_STR;
	private String SERIAL_NUMBER_STR;
	private String TRADE_TYPE_CODE_STR;
	private String NET_TYPE_CODE_STR;
	private String DEBUTY_CODE;
	private boolean IS_NEED_WRITE_CARD;
	private String WRAP_TRADE_TYPE;
	private String CUR_TRADE_IDS;
	private String CUR_TRADE_TYPE_CODES;
	private String CUR_SERIAL_NUMBERS;
	private String CUR_NET_TYPE_CODES;
	private String isAfterFee;
	private String globalPageName;
	
	private String referer = "https://gd.cbss.10010.com/custserv";
	
	public boolean isCancelTag() {
		return cancelTag;
	}

	public void setCancelTag(boolean cancelTag) {
		this.cancelTag = cancelTag;
	}

	public boolean isIS_NEED_WRITE_CARD() {
		return IS_NEED_WRITE_CARD;
	}

	public void setIS_NEED_WRITE_CARD(boolean iS_NEED_WRITE_CARD) {
		IS_NEED_WRITE_CARD = iS_NEED_WRITE_CARD;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTradeMain() {
		return tradeMain;
	}

	public void setTradeMain(String tradeMain) {
		this.tradeMain = tradeMain;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getUnChargedfees() {
		return unChargedfees;
	}

	public void setUnChargedfees(String unChargedfees) {
		this.unChargedfees = unChargedfees;
	}

	public String getFeePayMoney() {
		return feePayMoney;
	}

	public void setFeePayMoney(String feePayMoney) {
		this.feePayMoney = feePayMoney;
	}

	public String getFeeCheck() {
		return feeCheck;
	}

	public void setFeeCheck(String feeCheck) {
		this.feeCheck = feeCheck;
	}

	public String getFeePos() {
		return feePos;
	}

	public void setFeePos(String feePos) {
		this.feePos = feePos;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getCASH() {
		return CASH;
	}

	public void setCASH(String cASH) {
		CASH = cASH;
	}

	public String getSEND_TYPE() {
		return SEND_TYPE;
	}

	public void setSEND_TYPE(String sEND_TYPE) {
		SEND_TYPE = sEND_TYPE;
	}

	public String getTRADE_ID() {
		return TRADE_ID;
	}

	public void setTRADE_ID(String tRADE_ID) {
		TRADE_ID = tRADE_ID;
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

	public String getDEBUTY_CODE() {
		return DEBUTY_CODE;
	}

	public void setDEBUTY_CODE(String dEBUTY_CODE) {
		DEBUTY_CODE = dEBUTY_CODE;
	}
	
	public String getWRAP_TRADE_TYPE() {
		return WRAP_TRADE_TYPE;
	}

	public void setWRAP_TRADE_TYPE(String wRAP_TRADE_TYPE) {
		WRAP_TRADE_TYPE = wRAP_TRADE_TYPE;
	}

	public String getCUR_TRADE_IDS() {
		return CUR_TRADE_IDS;
	}

	public void setCUR_TRADE_IDS(String cUR_TRADE_IDS) {
		CUR_TRADE_IDS = cUR_TRADE_IDS;
	}

	public String getCUR_TRADE_TYPE_CODES() {
		return CUR_TRADE_TYPE_CODES;
	}

	public void setCUR_TRADE_TYPE_CODES(String cUR_TRADE_TYPE_CODES) {
		CUR_TRADE_TYPE_CODES = cUR_TRADE_TYPE_CODES;
	}

	public String getCUR_SERIAL_NUMBERS() {
		return CUR_SERIAL_NUMBERS;
	}

	public void setCUR_SERIAL_NUMBERS(String cUR_SERIAL_NUMBERS) {
		CUR_SERIAL_NUMBERS = cUR_SERIAL_NUMBERS;
	}

	public String getCUR_NET_TYPE_CODES() {
		return CUR_NET_TYPE_CODES;
	}

	public void setCUR_NET_TYPE_CODES(String cUR_NET_TYPE_CODES) {
		CUR_NET_TYPE_CODES = cUR_NET_TYPE_CODES;
	}

	public String getIsAfterFee() {
		return isAfterFee;
	}

	public void setIsAfterFee(String isAfterFee) {
		this.isAfterFee = isAfterFee;
	}

	public String getGlobalPageName() {
		return globalPageName;
	}

	public void setGlobalPageName(String globalPageName) {
		this.globalPageName = globalPageName;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
}
