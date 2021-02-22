package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ResourcesInfo implements Serializable{
	@ZteSoftCommentAnnotationParam(name = "0:不修改；1：修改", type = "String", isNecessary = "Y", desc = "keyChangeTag：0:不修改；1：修改")
	private String keyChangeTag;
	
	@ZteSoftCommentAnnotationParam(name = "原资源预占关键字", type = "String", isNecessary = "N", desc = "oldKey：原资源预占关键字")
	private String oldKey;
	
	@ZteSoftCommentAnnotationParam(name = "资源预占关键字类型", type = "String", isNecessary = "Y", desc = "proKeyMode：资源预占关键字类型")
	private String proKeyMode;
	
	@ZteSoftCommentAnnotationParam(name = "资源预占关键字", type = "String", isNecessary = "Y", desc = "proKey：资源预占关键字")
	private String proKey;
	
	@ZteSoftCommentAnnotationParam(name = "组别编码", type = "String", isNecessary = "N", desc = "groupKey：组别编码")
	private String groupKey;
	
	@ZteSoftCommentAnnotationParam(name = "资源类型", type = "String", isNecessary = "Y", desc = "resourcesType：资源类型")
	private String resourcesType;
	
	@ZteSoftCommentAnnotationParam(name = "套包销售标记:0－非套包销售；1－套包销售", type = "String", isNecessary = "N", desc = "packageTag：套包销售标记:0－非套包销售；1－套包销售")
	private String packageTag;
	
	@ZteSoftCommentAnnotationParam(name = "资源唯一标识", type = "String", isNecessary = "Y", desc = "resourcesCode：资源唯一标识")
	private String resourcesCode;
	
	@ZteSoftCommentAnnotationParam(name = "原资源唯一标识", type = "String", isNecessary = "N", desc = "oldResourcesCode：原资源唯一标识")
	private String oldResourcesCode;
	
	@ZteSoftCommentAnnotationParam(name = "号码状态标识", type = "String", isNecessary = "Y", desc = "occupiedFlag：号码状态标识")
	private String occupiedFlag;
	
	@ZteSoftCommentAnnotationParam(name = "0：号码不变更；1：号码变更", type = "String", isNecessary = "Y", desc = "snChangeTag：0：号码不变更；1：号码变更")
	private String snChangeTag;
	
	@ZteSoftCommentAnnotationParam(name = "0：不延长预定时间；1：延长预定时间；目前仅用于北六延长号码未付费预定时间", type = "String", isNecessary = "N", desc = "delayOccupiedFlag：0：不延长预定时间；1：延长预定时间；目前仅用于北六延长号码未付费预定时间")
	private String delayOccupiedFlag;
	
	@ZteSoftCommentAnnotationParam(name = "释放时间", type = "String", isNecessary = "N", desc = "occupiedTime：yyyymmddhh24miss。OccupiedFlag:1，2，3的时候必填")
	private String occupiedTime;
	
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "N", desc = "custName：客户名称")
	private String custName;
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "certType：证件类型")
	private String certType;
	
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "certNum：证件号码")
	private String certNum;
	
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "N", desc = "contactNum：联系电话")
	private String contactNum;
	
	@ZteSoftCommentAnnotationParam(name = "0不校验;1身份信息校验", type = "String", isNecessary = "N", desc = "preOrderTag：0不校验;1身份信息校验 ")
	private String preOrderTag;
	
	@ZteSoftCommentAnnotationParam(name = "套餐编码", type = "String", isNecessary = "N", desc = "productId：套餐编码")
	private String productId;
	
	@ZteSoftCommentAnnotationParam(name = "受理渠道标识", type = "String", isNecessary = "N", desc = "acceptChannelTag：受理渠道标识")
	private String acceptChannelTag;
	
	@ZteSoftCommentAnnotationParam(name = "发展人判断标识", type = "String", isNecessary = "N", desc = "developPersonTag：发展人判断标识")
	private String developPersonTag;
	
	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "N", desc = "recomPersonId：发展人标识")
	private String recomPersonId;
	
	@ZteSoftCommentAnnotationParam(name = "发展人渠道标识", type = "String", isNecessary = "N", desc = "recomDeparId：发展人渠道标识")
	private String recomDeparId;
	
	@ZteSoftCommentAnnotationParam(name = "备注信息", type = "String", isNecessary = "N", desc = "remark：备注信息")
	private String remark;

	public String getKeyChangeTag() {
		return keyChangeTag;
	}

	public void setKeyChangeTag(String keyChangeTag) {
		this.keyChangeTag = keyChangeTag;
	}

	public String getOldKey() {
		return oldKey;
	}

	public void setOldKey(String oldKey) {
		this.oldKey = oldKey;
		if(oldKey==""){
			this.oldKey=null;
		}
	}

	public String getProKeyMode() {
		return proKeyMode;
	}

	public void setProKeyMode(String proKeyMode) {
		this.proKeyMode = proKeyMode;
	}

	public String getProKey() {
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
		if(groupKey==""){
			this.groupKey=null;
		}
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

	public String getOldResourcesCode() {
		return oldResourcesCode;
	}

	public void setOldResourcesCode(String oldResourcesCode) {
		this.oldResourcesCode = oldResourcesCode;
	}

	public String getOccupiedFlag() {
		return occupiedFlag;
	}

	public void setOccupiedFlag(String occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}

	public String getSnChangeTag() {
		return snChangeTag;
	}

	public void setSnChangeTag(String snChangeTag) {
		this.snChangeTag = snChangeTag;
	}

	public String getDelayOccupiedFlag() {
		return delayOccupiedFlag;
	}

	public void setDelayOccupiedFlag(String delayOccupiedFlag) {
		this.delayOccupiedFlag = delayOccupiedFlag;
	}

	public String getOccupiedTime() {
		return occupiedTime;
	}

	public void setOccupiedTime(String occupiedTime) {
		this.occupiedTime = occupiedTime;
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

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
		if(contactNum==""){
			this.contactNum=null;
		}
	}

	public String getPreOrderTag() {
		return preOrderTag;
	}

	public void setPreOrderTag(String preOrderTag) {
		this.preOrderTag = preOrderTag;
		if(preOrderTag==""){
			this.preOrderTag=null;
		}
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
		if(productId==""){
			this.productId=null;
		}
	}

	public String getAcceptChannelTag() {
		return acceptChannelTag;
	}

	public void setAcceptChannelTag(String acceptChannelTag) {
		this.acceptChannelTag = acceptChannelTag;
		if(acceptChannelTag==""){
			this.acceptChannelTag=null;
		}
	}

	public String getDevelopPersonTag() {
		return developPersonTag;
	}

	public void setDevelopPersonTag(String developPersonTag) {
		this.developPersonTag = developPersonTag;
	}

	public String getRecomPersonId() {
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
		if(recomPersonId==""){
			this.recomPersonId=null;
		}
	}

	public String getRecomDeparId() {
		return recomDeparId;
	}

	public void setRecomDeparId(String recomDeparId) {
		this.recomDeparId = recomDeparId;
		if(recomDeparId==""){
			this.recomDeparId=null;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		if(remark==""){
			this.remark=null;
		}
	}

	public String getPackageTag() {
		return packageTag;
	}

	public void setPackageTag(String packageTag) {
		this.packageTag = packageTag;
		if(packageTag==""){
			this.packageTag=null;
		}
	}
	

}
