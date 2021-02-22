package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 同步订单信息到WMS系统
 * 商品信息对象
 */
public class NotifyGoodInfoVo implements Serializable{
	
	private static final long serialVersionUID = 5884797023957694931L;
	
	@ZteSoftCommentAnnotationParam(name="货品Id",type="String",isNecessary="Y",desc="货品Id")
	private String detailId;
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="商品Id")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name="货品SKU",type="String",isNecessary="Y",desc="货品SKU（明细编码）")
	private String productCode;
	@ZteSoftCommentAnnotationParam(name="货品明细名称",type="String",isNecessary="Y",desc="货品明细名称")
	private String productName;
	@ZteSoftCommentAnnotationParam(name="货品货主编码",type="String",isNecessary="Y",desc="货品货主编码")
	private String productComp;
	@ZteSoftCommentAnnotationParam(name="订货数量",type="String",isNecessary="Y",desc="订货数量")
	private Integer qty;
	@ZteSoftCommentAnnotationParam(name="发送状态",type="String",isNecessary="Y",desc="发送状态[1：正常、 2：换货]")
	private String detailType;
	@ZteSoftCommentAnnotationParam(name="货品类型",type="String",isNecessary="N",desc="货品类型[10000：手机、 10001：合约计划、 10002：套餐、 " +
			"10003：上网卡硬件、 10004：Sim卡、 10005：白卡（sim卡）（预留）、 10006：配件、 10007：礼品、 " +
			"10008：增值业务(附加产品)（预留）、 10009：附加产品、 10010：转兑包、 10011：号码]")
	private String productType;
	@ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="Y",desc="商品类型[01：手机终端、 02：上网卡、 03：极速卡、 04：套卡、 06：终端+号码]")
	private String packType;
	@ZteSoftCommentAnnotationParam(name="卡类型",type="String",isNecessary="N",desc="卡类型[01：大卡、 02：小卡、 03：NANO卡]")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="N",desc="号码")
	private String number;
	@ZteSoftCommentAnnotationParam(name="商城订单编号",type="String",isNecessary="Y",desc="OrderId：商城订单编号")
	private String OrderId;
	@ZteSoftCommentAnnotationParam(name="华盛商品库位 ",type="String",isNecessary="N",desc="华盛商品库位 ")
	private String productLgort;
	
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		this.OrderId = orderId;
	}

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getPackageId() {
		return this.packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQty() {
		qty = 1;
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getDetailType() {
		detailType = "1";
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPackType() {
		return this.packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProductComp() {
		return productComp;
	}

	public void setProductComp(String productComp) {
		this.productComp = productComp;
	}

	public String getProductLgort() {
		return productLgort;
	}

	public void setProductLgort(String productLgort) {
		this.productLgort = productLgort;
	}
	
}
