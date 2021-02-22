package com.zte.cbss.autoprocess.model.data;

import java.util.ArrayList;
import java.util.List;

public class TF_B_TRADE_PRODUCT {

	private List<TF_B_TRADE_PRODUCT_ITEM> ITEM;
	
	public TF_B_TRADE_PRODUCT(){
		this.ITEM = new ArrayList<TF_B_TRADE_PRODUCT_ITEM>();
	}

	public List<TF_B_TRADE_PRODUCT_ITEM> getITEM() {
		return ITEM;
	}

	public void setITEM(List<TF_B_TRADE_PRODUCT_ITEM> iTEM) {
		ITEM = iTEM;
	}
	
	public static class TF_B_TRADE_PRODUCT_ITEM{
		private String PRODUCT_ID = "";
		private String PRODUCT_MODE = "";
		private String START_DATE = "";
		private String END_DATE = "";
		private String MODIFY_TAG = "0";
		private String USER_ID_A = "-1";
		private String ITEM_ID = "";
		private String BRAND_CODE = "-1";
		private String X_DATATYPE = "";
		
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
		public String getUSER_ID_A() {
			return USER_ID_A;
		}
		public void setUSER_ID_A(String uSER_ID_A) {
			USER_ID_A = uSER_ID_A;
		}
		public String getITEM_ID() {
			return ITEM_ID;
		}
		public void setITEM_ID(String iTEM_ID) {
			ITEM_ID = iTEM_ID;
		}
		public String getBRAND_CODE() {
			return BRAND_CODE;
		}
		public void setBRAND_CODE(String bRAND_CODE) {
			BRAND_CODE = bRAND_CODE;
		}
		public String getX_DATATYPE() {
			return X_DATATYPE;
		}
		public void setX_DATATYPE(String x_DATATYPE) {
			X_DATATYPE = x_DATATYPE;
		}
		
	}
}
