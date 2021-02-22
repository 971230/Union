package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ProductInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="productName",type="String",isNecessary="N",desc="productName：开户套餐别名，必须是名称全称")
	private String productName;
	
	@ZteSoftCommentAnnotationParam(name="productId",type="String",isNecessary="Y",desc="productId：开户套餐的ID")
	private String productId;
	
	@ZteSoftCommentAnnotationParam(name="packageInfo",type="String",isNecessary="N",desc="packageInfo：套餐内可选包及元素（资费）")
	private List<PackageInfo> packageInfo;

	public String getProductName() {
		return productName==null?"":productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId==null?"":productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<PackageInfo> getPackageInfo() {
		return packageInfo==null?(new ArrayList<PackageInfo>()):packageInfo;
	}

	public void setPackageInfo(List<PackageInfo> packageInfo) {
		this.packageInfo = packageInfo;
	}
}
