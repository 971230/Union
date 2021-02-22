/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 资源信息
 * 
 */
public class TemiResourcesReqVo implements Serializable {

	private String salePrice; // N 销售价格（单位：厘）
	private String cost; // N 成本价格（单位：厘）
	private String cardPrice; // N 卡费（单位：厘）
	private String reservaPrice; // N 预存话费金额（单位：厘）
	private List<ProductActivityInfoVo> productActivityInfo; // 套包对应产品活动信息
	private String resourcesBrandCode; // N 品牌
	private String orgDeviceBrandCode; // N 3GESS维护品牌，当iphone时品牌与上面的一致
	private String resourcesBrandName; // N 终端品牌名称
	private String resourcesModelCode; // N 型号
	private String resourcesModelName; // N 终端型号名称
	private String resourcesSrcCode; // N 终端来源编码
	private String resourcesSrcName; // N 终端来源名称
	private String resourcesSupplyCorp; // N 终端供货商名称
	private String resourcesServiceCorp; // N 终端服务商名称
	private String resourcesColor; // N 终端颜色
	private String machineTypeCode; // N 终端机型编码
	private String machineTypeName; // N 终端机型名称
	private String distributionTag; // N "铺货标志 0非铺货终端  1铺货终端"
	private String resRele; // N 资源归属 01 总部管理资源 02 省分管理资源 03：全部资源 04 社会渠道资源
	private String terminalType; // N 终端类别编码：Iphone：IP 乐phone：LP  智能终端：PP普通定制终端：01 上网卡：04 上网本：05
	private String terminalTSubType; // N 当终端类别为智能终端时，该字段必填，终端子类别编码：入门级：04  普及型：03 中高端：02 明星：01
	private String serviceNumber; // N 终端绑定的服务号码，当终端为打包的预付费产品时必传，如上网卡套包等

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

	public void setProductActivityInfo(
			List<ProductActivityInfoVo> productActivityInfo) {
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

}
