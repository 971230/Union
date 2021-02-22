package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ResourcesRspVo implements Serializable {
	@ZteSoftCommentAnnotationParam(name="资源类型",type="String",isNecessary="Y",desc="resourcesType：资源类型01：USIM卡 02：手机号码 03：移动电话 04：上网卡 05：上网本")
	private String resourcesType;
	@ZteSoftCommentAnnotationParam(name="串号",type="String",isNecessary="Y",desc="resourcesCode：资源唯一标识")
	private String resourcesCode;
	@ZteSoftCommentAnnotationParam(name = "资源变更结果编码", type = "String", isNecessary = "Y", desc = "rscStateCode：资源变更结果编码")
	private String rscStateCode;
	@ZteSoftCommentAnnotationParam(name = "资源状态描述", type = "String", isNecessary = "Y", desc = "rscStateDesc：资源状态描述")
	private String rscStateDesc;	
	@ZteSoftCommentAnnotationParam(name = "销售价格", type = "String", isNecessary = "N", desc = "salePrice：销售价格（单位：厘）")
	private String salePrice;
	@ZteSoftCommentAnnotationParam(name = "成本价格", type = "String", isNecessary = "N", desc = "cost：成本价格（单位：厘）")
	private String cost;
	@ZteSoftCommentAnnotationParam(name = "卡费", type = "String", isNecessary = "N", desc = "cardPrice：卡费（单位：厘）")
	private String cardPrice;
	@ZteSoftCommentAnnotationParam(name = "预存话费金额", type = "String", isNecessary = "N", desc = "reservaPrice：预存话费金额（单位：厘）")
	private String reservaPrice;
	@ZteSoftCommentAnnotationParam(name = "套包对应产品活动信息", type = "List", isNecessary = "N", desc = "productActivityInfo：套包对应产品活动信息")
	private List<ProductActivityInfoVo> productActivityInfo;
	@ZteSoftCommentAnnotationParam(name = "品牌", type = "String", isNecessary = "N", desc = "resourcesBrandCode：品牌")
	private String resourcesBrandCode;
	@ZteSoftCommentAnnotationParam(name = "3GESS维护品牌", type = "String", isNecessary = "N", desc = "orgDeviceBrandCode：3GESS维护品牌，当iphone时品牌与上面的一致")
	private String orgDeviceBrandCode;
	@ZteSoftCommentAnnotationParam(name = "终端品牌名称", type = "String", isNecessary = "N", desc = "resourcesBrandName：终端品牌名称")
	private String resourcesBrandName;
	@ZteSoftCommentAnnotationParam(name = "型号", type = "String", isNecessary = "N", desc = "resourcesModelCode：型号")
	private String resourcesModelCode;
	@ZteSoftCommentAnnotationParam(name = "终端型号名称", type = "String", isNecessary = "N", desc = "resourcesModelName：终端型号名称")
	private String resourcesModelName;
	@ZteSoftCommentAnnotationParam(name = "终端来源编码", type = "String", isNecessary = "N", desc = "resourcesSrcCode：终端来源编码")
	private String resourcesSrcCode;
	@ZteSoftCommentAnnotationParam(name = "终端来源名称", type = "String", isNecessary = "N", desc = "resourcesSrcName：终端来源名称")
	private String resourcesSrcName;
	@ZteSoftCommentAnnotationParam(name = "终端供货商名称", type = "String", isNecessary = "N", desc = "resourcesSupplyCorp：终端供货商名称")
	private String resourcesSupplyCorp;
	@ZteSoftCommentAnnotationParam(name = "终端服务商名称", type = "String", isNecessary = "N", desc = "resourcesServiceCorp：终端服务商名称")
	private String resourcesServiceCorp;
	@ZteSoftCommentAnnotationParam(name = "终端颜色", type = "String", isNecessary = "N", desc = "resourcesColor：终端颜色")
	private String resourcesColor;
	@ZteSoftCommentAnnotationParam(name = "终端机型编码", type = "String", isNecessary = "N", desc = "machineTypeCode：终端机型编码")
	private String machineTypeCode;
	@ZteSoftCommentAnnotationParam(name = "终端机型名称", type = "String", isNecessary = "N", desc = "machineTypeName：终端机型名称")
	private String machineTypeName;
	@ZteSoftCommentAnnotationParam(name = "铺货标志 ", type = "String", isNecessary = "N", desc = "distributionTag：铺货标志  0:非铺货终端  1:铺货终端")
	private String distributionTag;
	@ZteSoftCommentAnnotationParam(name = "资源归属 ", type = "String", isNecessary = "N", desc = "resRele：资源归属  01:总部管理资源  02:省分管理资源  03:全部资源  04:社会渠道资源")
	private String resRele;
	@ZteSoftCommentAnnotationParam(name = "终端机型名称", type = "String", isNecessary = "N", desc = "terminalType：终端类别编码  Iphone：IP 乐phone：LP 智能终端：PP 普通定制终端：01   上网卡：04  上网本：05")
	private String terminalType;
	@ZteSoftCommentAnnotationParam(name = "终端子类别编码", type = "String", isNecessary = "N", desc = "terminalTSubType：当终端类别为智能终端时，该字段必填，终端子类别编码：入门级：04普及型：03中高端：02明星：01")
	private String terminalTSubType;
	@ZteSoftCommentAnnotationParam(name = "终端绑定的服务号码", type = "String", isNecessary = "N", desc = "serviceNumber：终端绑定的服务号码，当终端为打包的预付费产品时必传，如上网卡套包等")
	private String serviceNumber;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="para：保留字段")
	private ParamsVo para;
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
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getCardPrice() {
		return cardPrice;
	}
	public void setCardPrice(String cardPrice) {
		this.cardPrice = cardPrice;
	}
	public String getReservaPrice() {
		return reservaPrice;
	}
	public void setReservaPrice(String reservaPrice) {
		this.reservaPrice = reservaPrice;
	}
	public List<ProductActivityInfoVo> getProductActivityInfo() {
		return productActivityInfo;
	}
	public void setProductActivityInfo(List<ProductActivityInfoVo> productActivityInfo) {
		this.productActivityInfo = productActivityInfo;
	}
	public String getResourcesBrandCode() {
		return resourcesBrandCode;
	}
	public void setResourcesBrandCode(String resourcesBrandCode) {
		this.resourcesBrandCode = resourcesBrandCode;
	}
	public String getOrgDeviceBrandCode() {
		return orgDeviceBrandCode;
	}
	public void setOrgDeviceBrandCode(String orgDeviceBrandCode) {
		this.orgDeviceBrandCode = orgDeviceBrandCode;
	}
	public String getResourcesBrandName() {
		return resourcesBrandName;
	}
	public void setResourcesBrandName(String resourcesBrandName) {
		this.resourcesBrandName = resourcesBrandName;
	}
	public String getResourcesModelCode() {
		return resourcesModelCode;
	}
	public void setResourcesModelCode(String resourcesModelCode) {
		this.resourcesModelCode = resourcesModelCode;
	}
	public String getResourcesModelName() {
		return resourcesModelName;
	}
	public void setResourcesModelName(String resourcesModelName) {
		this.resourcesModelName = resourcesModelName;
	}
	public String getResourcesSrcCode() {
		return resourcesSrcCode;
	}
	public void setResourcesSrcCode(String resourcesSrcCode) {
		this.resourcesSrcCode = resourcesSrcCode;
	}
	public String getResourcesSrcName() {
		return resourcesSrcName;
	}
	public void setResourcesSrcName(String resourcesSrcName) {
		this.resourcesSrcName = resourcesSrcName;
	}
	public String getResourcesSupplyCorp() {
		return resourcesSupplyCorp;
	}
	public void setResourcesSupplyCorp(String resourcesSupplyCorp) {
		this.resourcesSupplyCorp = resourcesSupplyCorp;
	}
	public String getResourcesServiceCorp() {
		return resourcesServiceCorp;
	}
	public void setResourcesServiceCorp(String resourcesServiceCorp) {
		this.resourcesServiceCorp = resourcesServiceCorp;
	}
	public String getResourcesColor() {
		return resourcesColor;
	}
	public void setResourcesColor(String resourcesColor) {
		this.resourcesColor = resourcesColor;
	}
	public String getMachineTypeCode() {
		return machineTypeCode;
	}
	public void setMachineTypeCode(String machineTypeCode) {
		this.machineTypeCode = machineTypeCode;
	}
	
	public String getMachineTypeName() {
		return machineTypeName;
	}
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}
	public String getDistributionTag() {
		return distributionTag;
	}
	public void setDistributionTag(String distributionTag) {
		this.distributionTag = distributionTag;
	}
	public String getResRele() {
		return resRele;
	}
	public void setResRele(String resRele) {
		this.resRele = resRele;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getTerminalTSubType() {
		return terminalTSubType;
	}
	public void setTerminalTSubType(String terminalTSubType) {
		this.terminalTSubType = terminalTSubType;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public ParamsVo getPara() {
		return para;
	}
	public void setPara(ParamsVo para) {
		this.para = para;
	}

}
