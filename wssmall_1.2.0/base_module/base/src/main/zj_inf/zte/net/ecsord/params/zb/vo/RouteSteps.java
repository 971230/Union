package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RouteSteps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2168573284608186085L;

	@ZteSoftCommentAnnotationParam(name = "当前物流状态", type = "String", isNecessary = "Y", desc = "当前物流状态")
	private String AcceptState;
	
	@ZteSoftCommentAnnotationParam(name = "处理时间", type = "String", isNecessary = "Y", desc = "处理时间")
	private String AcceptTime;
	
	@ZteSoftCommentAnnotationParam(name = "AcceptAddress", type = "String", isNecessary = "N", desc = "AcceptAddress")
	private String AcceptAddress;
	@ZteSoftCommentAnnotationParam(name = "AcceptName", type = "String", isNecessary = "N", desc = "AcceptName")
	private String AcceptName;
	@ZteSoftCommentAnnotationParam(name = "Effect", type = "String", isNecessary = "N", desc = "Effect")
	private String Effect;
	public String getAcceptState() {
		return AcceptState;
	}
	public void setAcceptState(String acceptState) {
		AcceptState = acceptState;
	}
	public String getAcceptTime() {
		return AcceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		AcceptTime = acceptTime;
	}
	public String getAcceptAddress() {
		return AcceptAddress;
	}
	public void setAcceptAddress(String acceptAddress) {
		AcceptAddress = acceptAddress;
	}
	public String getAcceptName() {
		return AcceptName;
	}
	public void setAcceptName(String acceptName) {
		AcceptName = acceptName;
	}
	public String getEffect() {
		return Effect;
	}
	public void setEffect(String effect) {
		Effect = effect;
	}

}
