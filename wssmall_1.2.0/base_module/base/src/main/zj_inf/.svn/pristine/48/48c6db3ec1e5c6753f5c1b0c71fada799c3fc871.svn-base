package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author songqi
 * @version AOP
 */
public class FlowProductInfo implements Serializable {

	private static final long serialVersionUID = 3189379820372532636L;
	private String productId;// Y String(20) 产品ID
	private String optType;// N String(2) 00：订购；01：退订
	private List<PackageElement> packageElement;// 产品下附加包及包元素（资费，服务）
	private String enableTag;// Y String(1) 生效方式 1：立即生效 2：次月生效

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public List<PackageElement> getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(List<PackageElement> packageElement) {
		this.packageElement = packageElement;
	}

	public String getEnableTag() {
		return enableTag;
	}

	public void setEnableTag(String enableTag) {
		this.enableTag = enableTag;
	}

}
