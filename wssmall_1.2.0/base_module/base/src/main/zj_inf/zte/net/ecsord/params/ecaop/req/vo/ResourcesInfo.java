/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.DataUtil;

/**
 * @author songqi
 * @see
 */
public class ResourcesInfo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "销售价格 ", type = "String", isNecessary = "Y", desc = "销售价格（单位：厘） ")
	private String salePrice;// 销售价格（单位：厘）
	@ZteSoftCommentAnnotationParam(name = "卡费 ", type = "String", isNecessary = "Y", desc = "卡费（单位：厘） ")
	private String cardPrice;// 卡费（单位：厘）
	@ZteSoftCommentAnnotationParam(name = "预存话费金额 ", type = "String", isNecessary = "Y", desc = "预存话费金额（单位：厘） ")
	private String reservaPrice;// 预存话费金额（单位：厘）
	@ZteSoftCommentAnnotationParam(name = "套包对应产品活动信息", type = "String", isNecessary = "Y", desc = "套包对应产品活动信息")
	private ProductActivityInfo productActivityInfo;// 套包对应产品活动信息
	@ZteSoftCommentAnnotationParam(name = "品牌 ", type = "String", isNecessary = "Y", desc = "品牌")
	private String resourcesBrandCode;// 品牌
	@ZteSoftCommentAnnotationParam(name = "3GESS维护品牌", type = "String", isNecessary = "Y", desc = "3GESS维护品牌，当iphone时品牌与上面的一致 ")
	private String orgDeviceBrandCode;// 3GESS维护品牌，当iphone时品牌与上面的一致
	@ZteSoftCommentAnnotationParam(name = "终端品牌名称 ", type = "String", isNecessary = "Y", desc = "终端品牌名称")
	private String resourcesBrandName;// 终端品牌名称
	@ZteSoftCommentAnnotationParam(name = "型号 ", type = "String", isNecessary = "Y", desc = "型号 ")
	private String resourcesModelCode;// 型号
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "终端型号名称")
	private String resourcesModelName;// 终端型号名称
	@ZteSoftCommentAnnotationParam(name = "终端来源编码", type = "String", isNecessary = "Y", desc = "终端来源编码")
	private String resourcesSrcCode;// 终端来源编码
	@ZteSoftCommentAnnotationParam(name = "终端来源名称 ", type = "String", isNecessary = "Y", desc = "终端来源名称")
	private String resourcesSrcName;// 终端来源名称
	@ZteSoftCommentAnnotationParam(name = "终端供货商名称 ", type = "String", isNecessary = "Y", desc = "终端供货商名称")
	private String resourcesSupplyCorp;// 终端供货商名称
	@ZteSoftCommentAnnotationParam(name = "终端服务商名称", type = "String", isNecessary = "Y", desc = "终端服务商名称 ")
	private String resourcesServiceCorp;// 终端服务商名称
	@ZteSoftCommentAnnotationParam(name = "终端颜色", type = "String", isNecessary = "Y", desc = "终端颜色")
	private String resourcesColor;// 终端颜色
	@ZteSoftCommentAnnotationParam(name = "终端机型编码", type = "String", isNecessary = "Y", desc = "终端机型编码")
	private String machineTypeCode;// 终端机型编码
	@ZteSoftCommentAnnotationParam(name = "终端机型名称", type = "String", isNecessary = "Y", desc = "终端机型名称")
	private String machineTypeName;// 终端机型名称
	@ZteSoftCommentAnnotationParam(name = "终端绑定的服务号码", type = "String", isNecessary = "Y", desc = "终端绑定的服务号码")
	private String serviceNumber;// 终端绑定的服务号码，当终端为打包的预付费产品时必传，如上网卡套包等

	public String getSalePrice() {
		// List<AttrTmResourceInfoBusiRequest> resourcelist =
		// CommonDataFactory.getInstance()
		// .getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		// if (resourcelist.size() > 0) {
		// AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
		// salePrice = resourceInfo.getSale_price();
		// }
		String totalFee = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TOTALFEE);
		if (StringUtil.isEmpty(totalFee)) {
			return "0";
		} else {
			Double price = Double.parseDouble(totalFee);
			salePrice = price + "";
			return salePrice;
		}
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getCardPrice() {
		// List<AttrTmResourceInfoBusiRequest> resourcelist =
		// CommonDataFactory.getInstance()
		// .getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		// if (resourcelist.size() > 0) {
		// AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
		// cardPrice = resourceInfo.getCard_price();
		// }
		String card_Price = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CARD_REAL_FEE);
		if (StringUtil.isEmpty(card_Price)) {
			return "0";
		} else {
			Double price = Double.parseDouble(card_Price) * 10000;
			return price.intValue() + "";
		}
	}

	public void setCardPrice(String cardPrice) {
		this.cardPrice = cardPrice;
	}

	public String getReservaPrice() {
		String phone_deposit = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_DEPOSIT);
		Double price = Double.parseDouble(phone_deposit) * 10000;
		reservaPrice = price.intValue() + "";
		return reservaPrice;
	}

	public void setReservaPrice(String reservaPrice) {
		this.reservaPrice = reservaPrice;
	}

	public ProductActivityInfo getProductActivityInfo() {
		if (null == productActivityInfo) {
			productActivityInfo = new ProductActivityInfo();
		}
		productActivityInfo.setNotNeedReqStrOrderId(notNeedReqStrOrderId);
		return productActivityInfo;
	}

	public void setProductActivityInfo(ProductActivityInfo productActivityInfo) {
		this.productActivityInfo = productActivityInfo;
	}

	public String getResourcesBrandCode() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesBrandCode = resourceInfo.getResources_brand_code();
		}
		return resourcesBrandCode;
	}

	public void setResourcesBrandCode(String resourcesBrandCode) {
		this.resourcesBrandCode = resourcesBrandCode;
	}

	public String getOrgDeviceBrandCode() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			orgDeviceBrandCode = resourceInfo.getOrg_device_brand_code();
		}
		return orgDeviceBrandCode;
	}

	public void setOrgDeviceBrandCode(String orgDeviceBrandCode) {
		this.orgDeviceBrandCode = orgDeviceBrandCode;
	}

	public String getResourcesBrandName() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesBrandName = resourceInfo.getResources_brand_name();
		}
		return resourcesBrandName;
	}

	public void setResourcesBrandName(String resourcesBrandName) {
		this.resourcesBrandName = resourcesBrandName;
	}

	public String getResourcesModelCode() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesModelCode = resourceInfo.getResources_model_code();
		}
		return resourcesModelCode;
	}

	public void setResourcesModelCode(String resourcesModelCode) {
		this.resourcesModelCode = resourcesModelCode;
	}

	public String getResourcesModelName() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesModelName = resourceInfo.getResources_model_name();
		}
		return resourcesModelName;
	}

	public void setResourcesModelName(String resourcesModelName) {
		this.resourcesModelName = resourcesModelName;
	}

	public String getResourcesSrcCode() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesSrcCode = resourceInfo.getResources_src_code();
		}
		return resourcesSrcCode;
	}

	public void setResourcesSrcCode(String resourcesSrcCode) {
		this.resourcesSrcCode = resourcesSrcCode;
	}

	public String getResourcesSrcName() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesSrcName = resourceInfo.getResources_src_name();
		}
		return resourcesSrcName;
	}

	public void setResourcesSrcName(String resourcesSrcName) {
		this.resourcesSrcName = resourcesSrcName;
	}

	public String getResourcesSupplyCorp() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesSupplyCorp = resourceInfo.getResources_supply_corp();
		}
		return resourcesSupplyCorp;
	}

	public void setResourcesSupplyCorp(String resourcesSupplyCorp) {
		this.resourcesSupplyCorp = resourcesSupplyCorp;
	}

	public String getResourcesServiceCorp() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesServiceCorp = resourceInfo.getResources_service_corp();
		}
		return resourcesServiceCorp;
	}

	public void setResourcesServiceCorp(String resourcesServiceCorp) {
		this.resourcesServiceCorp = resourcesServiceCorp;
	}

	public String getResourcesColor() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			resourcesColor = resourceInfo.getResources_color();
		}
		return resourcesColor;
	}

	public void setResourcesColor(String resourcesColor) {
		this.resourcesColor = resourcesColor;
	}

	public String getMachineTypeCode() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			machineTypeCode = resourceInfo.getMachine_type_code();
		}
		return machineTypeCode;
	}

	public void setMachineTypeCode(String machineTypeCode) {
		this.machineTypeCode = machineTypeCode;
	}

	public String getMachineTypeName() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			machineTypeName = resourceInfo.getMachine_type_name();
		}
		return machineTypeName;
	}

	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}

	public String getServiceNumber() {
		List<AttrTmResourceInfoBusiRequest> resourcelist = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests();
		if (resourcelist.size() > 0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			serviceNumber = resourceInfo.getService_number();
		}
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
