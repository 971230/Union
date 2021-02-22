package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 产品下附加包及包元素（资费，服务）
 * 
 */
public class ProdPackElementReqVo implements Serializable {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "包编号", type = "String", isNecessary = "Y", desc = "packageId")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name = "元素编号", type = "String", isNecessary = "Y", desc = "elementId")
	private String elementId;
	@ZteSoftCommentAnnotationParam(name = "元素类型 D资费,S服务,A活动，X S服务", type = "String", isNecessary = "Y", desc = "elementType")
	private String elementType;
	@ZteSoftCommentAnnotationParam(name = "0：订购；1：退订", type = "String", isNecessary = "Y", desc = "modType")
	private String modType;
	@ZteSoftCommentAnnotationParam(name = "服务资费属性", type = "String", isNecessary = "Y", desc = "")
	private List<ServiceAttrReqVo> serviceAttr;

	public String getPackageId() {
		if (StringUtils.isBlank(packageId)) {
			return null;
		}
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getElementId() {
		if (StringUtils.isBlank(elementId)) {
			return null;
		}
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementType() {
		if (StringUtils.isBlank(elementType)) {
			return null;
		}
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getModType() {
		if (StringUtils.isBlank(modType)) {
			return null;
		}
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

	public List<ServiceAttrReqVo> getServiceAttr() {
		if (serviceAttr == null || serviceAttr.size() == 0) {
			return null;
		}
		return serviceAttr;
	}

	public void setServiceAttr(List<ServiceAttrReqVo> serviceAttr) {
		this.serviceAttr = serviceAttr;
	}

}
