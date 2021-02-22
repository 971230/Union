package zte.net.ecsord.params.ecaop.req.vo;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenActivityInfo {

	@ZteSoftCommentAnnotationParam(name = "活动ID（总部活动ID）", type = "String", isNecessary = "Y", desc = "")
	private String actPlanId;
	@ZteSoftCommentAnnotationParam(name = "终端资源编码，一般是IMEI码", type = "String", isNecessary = "Y", desc = "")
	private String resourcesCode;
	@ZteSoftCommentAnnotationParam(name = "是否测试终端0：测试1：正式", type = "String", isNecessary = "Y", desc = "")
	private String isTest;
	@ZteSoftCommentAnnotationParam(name = "资源费用：厘", type = "String", isNecessary = "Y", desc = "")
	private String resourcesFee;
	@ZteSoftCommentAnnotationParam(name = "活动预存费用：厘", type = "String", isNecessary = "Y", desc = "")
	private String activityFee;
	@ZteSoftCommentAnnotationParam(name = "活动下自选包", type = "String", isNecessary = "Y", desc = "")
	private List<MainViceOpenPackageElement> packageElement;

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

	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}

	public String getResourcesFee() {
		return resourcesFee;
	}

	public void setResourcesFee(String resourcesFee) {
		this.resourcesFee = resourcesFee;
	}

	public String getActivityFee() {
		return activityFee;
	}

	public void setActivityFee(String activityFee) {
		this.activityFee = activityFee;
	}

	public List<MainViceOpenPackageElement> getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(List<MainViceOpenPackageElement> packageElement) {
		this.packageElement = packageElement;
	}

}
