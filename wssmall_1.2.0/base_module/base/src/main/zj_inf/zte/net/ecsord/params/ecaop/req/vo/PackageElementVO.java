package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * @see 通用VO不建议修改该类
 * @author song.qi
 *
 */
public class PackageElementVO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "包编号", type = "String", isNecessary = "Y", desc = "packageId")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name = "元素编号", type = "String", isNecessary = "Y", desc = "elementId")
	private String elementId;
	@ZteSoftCommentAnnotationParam(name = "元素类型 D资费,S服务,A活动，X S服务", type = "String", isNecessary = "Y", desc = "elementType")
	private String elementType;

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

}
