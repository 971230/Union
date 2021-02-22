package zte.net.ecsord.params.ecaop.resp.vo;

import java.util.List;

public class AccessInfoList {
	private String ACCESS_TYPE;
	private String ACCESS_TYPE_NAME;
	private List<ExchInfo> EXCH_LIST ;
	private String IP_TYPE;
	private String MAX_RATE;
	public String getACCESS_TYPE() {
		return ACCESS_TYPE;
	}
	public void setACCESS_TYPE(String aCCESS_TYPE) {
		ACCESS_TYPE = aCCESS_TYPE;
	}
	public String getACCESS_TYPE_NAME() {
		return ACCESS_TYPE_NAME;
	}
	public void setACCESS_TYPE_NAME(String aCCESS_TYPE_NAME) {
		ACCESS_TYPE_NAME = aCCESS_TYPE_NAME;
	}
	public List<ExchInfo> getEXCH_LIST() {
		return EXCH_LIST;
	}
	public void setEXCH_LIST(List<ExchInfo> eXCH_LIST) {
		EXCH_LIST = eXCH_LIST;
	}
	public String getIP_TYPE() {
		return IP_TYPE;
	}
	public void setIP_TYPE(String iP_TYPE) {
		IP_TYPE = iP_TYPE;
	}
	public String getMAX_RATE() {
		return MAX_RATE;
	}
	public void setMAX_RATE(String mAX_RATE) {
		MAX_RATE = mAX_RATE;
	}
	
}
