package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ResourecsRsp implements Serializable{
	@ZteSoftCommentAnnotationParam(name = "资源类型", type = "String", isNecessary = "Y", desc = "resourcesType：资源类型")
	private String resourcesType;
	
	@ZteSoftCommentAnnotationParam(name = "资源唯一标识", type = "String", isNecessary = "Y", desc = "resourcesCode：资源唯一标识")
	private String resourcesCode;

	@ZteSoftCommentAnnotationParam(name = "资源变更结果编码", type = "String", isNecessary = "Y", desc = "rscStateCode：资源变更结果编码")
	private String rscStateCode;
	
	@ZteSoftCommentAnnotationParam(name = "资源状态描述", type = "String", isNecessary = "Y", desc = "rscStateDesc：资源状态描述")
	private String rscStateDesc;
	
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "numId：号码")
	private String numId;
	
	@ZteSoftCommentAnnotationParam(name = "号码说明", type = "String", isNecessary = "N", desc = "numMemo：号码说明")
	private String numMemo;
	
	@ZteSoftCommentAnnotationParam(name = "靓号预存单位分", type = "String", isNecessary = "N", desc = "numPreFee：靓号预存单位分")
	private String numPreFee;
	
	@ZteSoftCommentAnnotationParam(name = "靓号级别", type = "String", isNecessary = "N", desc = "numLevel：靓号级别")
	private String numLevel;
	
	@ZteSoftCommentAnnotationParam(name = "号码约束产品", type = "String", isNecessary = "N", desc = "numProduct：号码约束产品")
	private String numProduct;
	
	@ZteSoftCommentAnnotationParam(name = "卡号", type = "String", isNecessary = "N", desc = "simId：卡号")
	private String simId;
	
	@ZteSoftCommentAnnotationParam(name = "关联号码", type = "String", isNecessary = "N", desc = "numRelNum：关联号码")
	private String numRelNum;

	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	public String getResourcesCode() {
		return resourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}

	public String getRscStateCode() {
		return rscStateCode;
	}

	public void setRscStateCode(String rscStateCode) {
		this.rscStateCode = rscStateCode;
	}

	public String getRscStateDesc() {
		return rscStateDesc;
	}

	public void setRscStateDesc(String rscStateDesc) {
		this.rscStateDesc = rscStateDesc;
	}

	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getNumMemo() {
		return numMemo;
	}

	public void setNumMemo(String numMemo) {
		this.numMemo = numMemo;
	}

	public String getNumPreFee() {
		return numPreFee;
	}

	public void setNumPreFee(String numPreFee) {
		this.numPreFee = numPreFee;
	}

	public String getNumLevel() {
		return numLevel;
	}

	public void setNumLevel(String numLevel) {
		this.numLevel = numLevel;
	}

	public String getNumProduct() {
		return numProduct;
	}

	public void setNumProduct(String numProduct) {
		this.numProduct = numProduct;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getNumRelNum() {
		return numRelNum;
	}

	public void setNumRelNum(String numRelNum) {
		this.numRelNum = numRelNum;
	}
	

}
