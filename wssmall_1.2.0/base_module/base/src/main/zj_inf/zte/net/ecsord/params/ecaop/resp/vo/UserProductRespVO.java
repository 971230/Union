package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UserProductRespVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "Y", desc = "productId")
	private String productId;
	@ZteSoftCommentAnnotationParam(name = "产品模式", type = "String", isNecessary = "Y", desc = "productMode;0：可选产品；1：主产品")
	private String productMode;
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
	
	
}
