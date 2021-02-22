package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainProductInfoVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5673828219247400094L;
	@ZteSoftCommentAnnotationParam(name = "产品编码", type = "String", isNecessary = "N", desc = "ProductID")
	private String ProductID;
	@ZteSoftCommentAnnotationParam(name = "产品名称", type = "String", isNecessary = "N", desc = "ProductName")
	private String ProductName ;
	@ZteSoftCommentAnnotationParam(name = "生效时间YYYYMMDDHHMMSS", type = "String", isNecessary = "N", desc = "ActiveTime")
	private String ActiveTime ;
	@ZteSoftCommentAnnotationParam(name = "失效时间YYYYMMDDHHMMSS", type = "String", isNecessary = "N", desc = "UnactiveTime")
	private String UnactiveTime ;
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getActiveTime() {
		return ActiveTime;
	}
	public void setActiveTime(String activeTime) {
		ActiveTime = activeTime;
	}
	public String getUnactiveTime() {
		return UnactiveTime;
	}
	public void setUnactiveTime(String unactiveTime) {
		UnactiveTime = unactiveTime;
	}
	
}
