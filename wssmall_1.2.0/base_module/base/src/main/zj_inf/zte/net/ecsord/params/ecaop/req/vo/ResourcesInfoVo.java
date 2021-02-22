/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-04
 *
 */
public class ResourcesInfoVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="资源类型",type="String",isNecessary="Y",desc="resourcesType：资源类型   01：USIM卡 02：手机号码 03：移动电话 04：上网卡 05：上网本")
	private String resourcesType;
	@ZteSoftCommentAnnotationParam(name="串号",type="String",isNecessary="Y",desc="resourcesCode：资源唯一标识")
	private String resourcesCode;
	@ZteSoftCommentAnnotationParam(name="预占标识",type="String",isNecessary="N",desc="occupiedFlag：预占标识")
	private String occupiedFlag;
	@ZteSoftCommentAnnotationParam(name="预占标识为1时此字段必填",type="String",isNecessary="N",desc="occupiedTime：预占标识为1时此字段必填 0 不预占 1 预占 2 预订 3 释放")
	private String occupiedTime;
	@ZteSoftCommentAnnotationParam(name="编码类型",type="String",isNecessary="Y",desc="resCodeType：编码类型 01 IMEI 02 ICCID 03 SN手机号码")
	private String resCodeType;
	@ZteSoftCommentAnnotationParam(name="客户名称",type="String",isNecessary="N",desc="custName：客户名称")
	private String custName;
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="N",desc="certType：证件类型")
	private String certType;
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="N",desc="certNum：证件号码")
	private String certNum;
	@ZteSoftCommentAnnotationParam(name = "调拨标识", type = "String", isNecessary = "N", desc = "allocationFlag：0 调拨 1 不调拨")
	private String allocationFlag;
	@ZteSoftCommentAnnotationParam(name = "合约类型", type = "String", isNecessary = "Y", desc = "activeType：合约类型：01 预存话费送手机、02 购手机送话费、03 合约惠机、04 无合约")
	protected String activeType;
	@ZteSoftCommentAnnotationParam(name = "是否自备", type = "String", isNecessary = "Y", desc = "isSelf：是否自备 1：否 2：是")
	protected String isSelf;
	
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public String getAllocationFlag() {
		return allocationFlag;
	}
	public void setAllocationFlag(String allocationFlag) {
		this.allocationFlag = allocationFlag;
	}
	public String getResCodeType() {
		return resCodeType;
	}
	public void setResCodeType(String resCodeType) {
		this.resCodeType = resCodeType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
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
	public String getOccupiedFlag() {
		return occupiedFlag;
	}
	public void setOccupiedFlag(String occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}
	public String getOccupiedTime() {
		return occupiedTime;
	}
	public void setOccupiedTime(String occupiedTime) {
		this.occupiedTime = occupiedTime;
	}
	public String getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	
}
