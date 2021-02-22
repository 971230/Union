package com.zte.cbss.autoprocess.model.data;

import java.util.ArrayList;
import java.util.List;

public class TF_B_TRADE_PRODUCT_TYPE {

	private List<TF_B_TRADE_PRODUCT_TYPE_ITEM> ITEM;
	
	public TF_B_TRADE_PRODUCT_TYPE(){
		this.ITEM = new ArrayList<TF_B_TRADE_PRODUCT_TYPE_ITEM>();
	}

	public List<TF_B_TRADE_PRODUCT_TYPE_ITEM> getITEM() {
		return ITEM;
	}

	public void setITEM(List<TF_B_TRADE_PRODUCT_TYPE_ITEM> iTEM) {
		ITEM = iTEM;
	}
	
	public static class TF_B_TRADE_PRODUCT_TYPE_ITEM{
		private String PRODUCT_ID = "";
		private String PRODUCT_MODE = "";
		private String START_DATE = "";
		private String END_DATE = "";
		private String MODIFY_TAG = "0";
		private String X_DATATYPE = "";
		private String PRODUCT_TYPE_CODE = "";
		private String USER_ID ;
		public String getPRODUCT_ID() {
			return PRODUCT_ID;
		}
		public void setPRODUCT_ID(String pRODUCT_ID) {
			PRODUCT_ID = pRODUCT_ID;
		}
		public String getPRODUCT_MODE() {
			return PRODUCT_MODE;
		}
		public void setPRODUCT_MODE(String pRODUCT_MODE) {
			PRODUCT_MODE = pRODUCT_MODE;
		}
		public String getSTART_DATE() {
			return START_DATE;
		}
		public void setSTART_DATE(String sTART_DATE) {
			START_DATE = sTART_DATE;
		}
		public String getEND_DATE() {
			return END_DATE;
		}
		public void setEND_DATE(String eND_DATE) {
			END_DATE = eND_DATE;
		}
		public String getMODIFY_TAG() {
			return MODIFY_TAG;
		}
		public void setMODIFY_TAG(String mODIFY_TAG) {
			MODIFY_TAG = mODIFY_TAG;
		}
		public String getX_DATATYPE() {
			return X_DATATYPE;
		}
		public void setX_DATATYPE(String x_DATATYPE) {
			X_DATATYPE = x_DATATYPE;
		}
		public String getPRODUCT_TYPE_CODE() {
			return PRODUCT_TYPE_CODE;
		}
		public void setPRODUCT_TYPE_CODE(String pRODUCT_TYPE_CODE) {
			PRODUCT_TYPE_CODE = pRODUCT_TYPE_CODE;
		}
		public String getUSER_ID() {
			return USER_ID;
		}
		public void setUSER_ID(String uSER_ID) {
			USER_ID = uSER_ID;
		}
		
	}
}
