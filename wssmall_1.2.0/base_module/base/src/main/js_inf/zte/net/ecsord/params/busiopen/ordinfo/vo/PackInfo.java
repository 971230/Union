package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 商品包信息
 */
public class PackInfo implements Serializable {

	private static final long serialVersionUID = 6030215507073868389L;

	@ZteSoftCommentAnnotationParam(name = "商品包ID", type = "String", isNecessary = "Y", desc = "商品包ID")
	private String packageId;

	@ZteSoftCommentAnnotationParam(name = "商品包编号", type = "String", isNecessary = "Y", desc = "商品包编号")
	private String prodOfferCode;

	@ZteSoftCommentAnnotationParam(name = "商品包名称", type = "String", isNecessary = "Y", desc = "商品包名称")
	private String prodOfferName;

	@ZteSoftCommentAnnotationParam(name = "商品包类型", type = "String", isNecessary = "Y", desc = "商品包类型")
	private String prodOfferType;

	@ZteSoftCommentAnnotationParam(name = "货品信息", type = "String", isNecessary = "N", desc = "货品信息")
	private List<GoodInfo> goodInfo;

	@ZteSoftCommentAnnotationParam(name = "赠品信息", type = "String", isNecessary = "N", desc = "赠品信息")
	private List<GiftInfo> giftInfo;

	@ZteSoftCommentAnnotationParam(name = "开户信息", type = "String", isNecessary = "N", desc = "开户信息")
	private List<AccountInfo> accountInfo;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProdOfferCode() {
		return prodOfferCode;
	}

	public void setProdOfferCode(String prodOfferCode) {
		this.prodOfferCode = prodOfferCode;
	}

	public String getProdOfferName() {
		return prodOfferName;
	}

	public void setProdOfferName(String prodOfferName) {
		this.prodOfferName = prodOfferName;
	}

	public String getProdOfferType() {
		return prodOfferType;
	}

	public void setProdOfferType(String prodOfferType) {
		this.prodOfferType = prodOfferType;
	}

	public List<GoodInfo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<GoodInfo> goodInfo) {
		this.goodInfo = goodInfo;
	}

	public List<GiftInfo> getGiftInfo() {
		return giftInfo;
	}

	public void setGiftInfo(List<GiftInfo> giftInfo) {
		this.giftInfo = giftInfo;
	}

	public List<AccountInfo> getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(List<AccountInfo> accountInfo) {
		this.accountInfo = accountInfo;
	}

}
