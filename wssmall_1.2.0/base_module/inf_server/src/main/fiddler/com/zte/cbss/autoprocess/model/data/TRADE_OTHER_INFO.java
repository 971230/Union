package com.zte.cbss.autoprocess.model.data;



public class TRADE_OTHER_INFO {

	private TRADE_OTHER_INFO_ITEM ITEM;

	public TRADE_OTHER_INFO_ITEM getITEM() {
		return ITEM;
	}

	public void setITEM(TRADE_OTHER_INFO_ITEM iTEM) {
		ITEM = iTEM;
	}
	public static class TRADE_OTHER_INFO_ITEM {

		private String CHECK_TYPE;

		private String BLACK_CUST;

		public String getCHECK_TYPE() {
			return CHECK_TYPE;
		}

		public void setCHECK_TYPE(String cHECK_TYPE) {
			CHECK_TYPE = cHECK_TYPE;
		}

		public String getBLACK_CUST() {
			return BLACK_CUST;
		}

		public void setBLACK_CUST(String bLACK_CUST) {
			BLACK_CUST = bLACK_CUST;
		}

	}

}
