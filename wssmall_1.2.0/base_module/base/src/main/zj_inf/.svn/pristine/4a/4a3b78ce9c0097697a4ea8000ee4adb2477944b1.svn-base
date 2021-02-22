package zte.net.ecsord.params.ecaop.req.vo;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenProductInfo {
	
	@ZteSoftCommentAnnotationParam(name = "产品标识", type = "String", isNecessary = "Y", desc = "")
	private String productId;
	@ZteSoftCommentAnnotationParam(name = "产品模式 1：主产品 2：附加产品", type = "String", isNecessary = "Y", desc = "")
	private String productMode;
	@ZteSoftCommentAnnotationParam(name = "产品下附加包及包元素（资费，服务）", type = "String", isNecessary = "Y", desc = "")
	private List<MainViceOpenPackageElement> packageElement;
	@ZteSoftCommentAnnotationParam(name = "首月付费模式 01：标准资费（免首月月租） 02：全月套餐 03：套餐减半", type = "String", isNecessary = "Y", desc = "")
	private String firstMonBillMode;
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
	public List<MainViceOpenPackageElement> getPackageElement() {
		return packageElement;
	}
	public void setPackageElement(List<MainViceOpenPackageElement> packageElement) {
		this.packageElement = packageElement;
	}
	public String getFirstMonBillMode() {
		return firstMonBillMode;
	}
	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}


}
