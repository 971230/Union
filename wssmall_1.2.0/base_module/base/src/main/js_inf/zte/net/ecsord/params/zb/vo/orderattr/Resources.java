package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Resources implements Serializable{

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesBrand;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesModel;

	@ZteSoftCommentAnnotationParam(name="机型编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesTypeId;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesColor;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesCode;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ResourcesFittings;

	public String getResourcesBrand() {
		return ResourcesBrand;
	}

	public void setResourcesBrand(String resourcesBrand) {
		ResourcesBrand = resourcesBrand;
	}

	public String getResourcesModel() {
		return ResourcesModel;
	}

	public void setResourcesModel(String resourcesModel) {
		ResourcesModel = resourcesModel;
	}

	public String getResourcesTypeId() {
		return ResourcesTypeId;
	}

	public void setResourcesTypeId(String resourcesTypeId) {
		ResourcesTypeId = resourcesTypeId;
	}

	public String getResourcesColor() {
		return ResourcesColor;
	}

	public void setResourcesColor(String resourcesColor) {
		ResourcesColor = resourcesColor;
	}

	public String getResourcesCode() {
		return ResourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		ResourcesCode = resourcesCode;
	}

	public String getResourcesFittings() {
		return ResourcesFittings;
	}

	public void setResourcesFittings(String resourcesFittings) {
		ResourcesFittings = resourcesFittings;
	}
	
}
