package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UserActivityRespVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "活动ID（总部活动ID）", type = "String", isNecessary = "Y", desc = "actPlanId")
	private String actPlanId;
	@ZteSoftCommentAnnotationParam(name = "终端资源编码", type = "String", isNecessary = "N", desc = "resourcesCode:终端资源编码，一般是IMEI码")
	private String resourcesCode;
	public String getActPlanId() {
		return actPlanId;
	}
	public void setActPlanId(String actPlanId) {
		this.actPlanId = actPlanId;
	}
	public String getResourcesCode() {
		return resourcesCode;
	}
	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}
	
	
}
