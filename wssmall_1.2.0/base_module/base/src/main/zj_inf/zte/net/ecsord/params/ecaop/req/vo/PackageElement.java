package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author songqi
 * @version AOP
 */
public class PackageElement implements Serializable {

	private static final long serialVersionUID = 3189379820372532636L;
	private String packageId;// Y 包编号
	private String elementId;// Y String(20) 元素编号
	private String elementType;// Y String(2) 元素类型 D资费,S服务,A活动，X S服务

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

}
