package zte.params.orderctn.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class HSOrderCtnB2BResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String TYPE;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "返回信息描述")
	private String MESSAGE;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "仓储商单号")
	private String WMSOrderID;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "预留字段1")
	private String RESERVE1;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "预留字段2")
	private String RESERVE2;
	
	
	
	public String getTYPE() {
		return TYPE;
	}



	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}



	public String getMESSAGE() {
		return MESSAGE;
	}



	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}



	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return "RETURN";
	}



	public String getWMSOrderID() {
		return WMSOrderID;
	}



	public void setWMSOrderID(String wMSOrderID) {
		WMSOrderID = wMSOrderID;
	}



	public String getRESERVE1() {
		return RESERVE1;
	}



	public void setRESERVE1(String rESERVE1) {
		RESERVE1 = rESERVE1;
	}



	public String getRESERVE2() {
		return RESERVE2;
	}



	public void setRESERVE2(String rESERVE2) {
		RESERVE2 = rESERVE2;
	}
	
}
