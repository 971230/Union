package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author song.qi
 * @version 2018年5月21日
 * @see
 * 
 */
public class ProductInfo4MV implements Serializable {

	private static final long serialVersionUID = 5113929029447566819L;
	@ZteSoftCommentAnnotationParam(name = "产品模式0：可选产品1：主产品（加入主卡同时变更主产品则传1）不传默认为0", type = "String", isNecessary = "Y", desc = "")
	private String productMode;
	@ZteSoftCommentAnnotationParam(name = "首月付费模式 01：标准资费（免首月月租） 02：全月套餐 03：套餐减半该字段不传时默认为02", type = "String", isNecessary = "Y", desc = "")
	private String firstMonBillMode;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "Y", desc = "productId")
	private String productId;
	@ZteSoftCommentAnnotationParam(name = "产品下附加包及包元素（资费，服务）", type = "PackageChangePackageElementVo", isNecessary = "Y", desc = "")
	private List<PackageElementVO> packageElement;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductMode() {
		return productMode;
	}

	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}

	public String getFirstMonBillMode() {
		return firstMonBillMode;
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}

	public List<PackageElementVO> getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(List<PackageElementVO> packageElement) {
		this.packageElement = packageElement;
	}

}
