/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 产品下附加包及包元素（资费，服务）
 * 
 */
public class OpenDealApplyPackageElementVo implements Serializable {
	
	private String packageId;// Y 包编号
	private String elementId; // Y 元素编号
	private String elementType; // Y 元素类型

	public String getPackageId() {
		if (StringUtils.isBlank(packageId)) return null;
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getElementId() {
		if (StringUtils.isBlank(elementId)) return null;
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementType() {
		if (StringUtils.isBlank(elementType)) return null;
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

}
