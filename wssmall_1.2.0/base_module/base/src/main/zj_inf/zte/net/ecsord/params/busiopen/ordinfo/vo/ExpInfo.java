package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 异常信息
 */
public class ExpInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821339864337373317L;

	@ZteSoftCommentAnnotationParam(name = "是否需要回访客户：0无需回访，1需要回访", type = "String", isNecessary = "N", desc = "是否需要回访客户：0无需回访，1需要回访")
	private String callBack;
	
	@ZteSoftCommentAnnotationParam(name = "异常分类：1.人工异常 3.自动化异常）", type = "String", isNecessary = "N", desc = "异常分类：1.人工异常 3.自动化异常）")
	private String abnormalType;
	
	@ZteSoftCommentAnnotationParam(name = "异常类型", type = "String", isNecessary = "N", desc = "异常类型")
	private String excType;
	
	@ZteSoftCommentAnnotationParam(name = "异常描述", type = "String", isNecessary = "N", desc = "异常描述")
	private String excDesc;
	
	@ZteSoftCommentAnnotationParam(name = "异常发生(标记)时间", type = "String", isNecessary = "N", desc = "异常发生(标记)时间")
	private String excTime;
	
	@ZteSoftCommentAnnotationParam(name = "异常是否已处理：0.未处理；1.已处理", type = "String", isNecessary = "N", desc = "异常是否已处理：0.未处理；1.已处理")
	private String excDeal;

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getExcType() {
		return excType;
	}

	public void setExcType(String excType) {
		this.excType = excType;
	}

	public String getExcDesc() {
		return excDesc;
	}

	public void setExcDesc(String excDesc) {
		this.excDesc = excDesc;
	}

	public String getExcTime() {
		return excTime;
	}

	public void setExcTime(String excTime) {
		this.excTime = excTime;
	}

	public String getExcDeal() {
		return excDeal;
	}

	public void setExcDeal(String excDeal) {
		this.excDeal = excDeal;
	}
	
}
