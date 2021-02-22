package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author song.qi
 * @version
 *
 */
public class ActivityInfoReq4MV implements Serializable {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "活动ID（总部活动ID）", type = "String", isNecessary = "Y", desc = "活动方案ID（客户选定）")
	private String actPlanId;
	@ZteSoftCommentAnnotationParam(name = "终端资源编码，一般是IMEI码", type = "String", isNecessary = "N", desc = "")
	private String resourcesCode;
	@ZteSoftCommentAnnotationParam(name = "是否测试终端 0：测试 1：正式", type = "String", isNecessary = "N", desc = "")
	private String isTest;
	@ZteSoftCommentAnnotationParam(name = "资源费用", type = "String", isNecessary = "N", desc = "")
	private String resourcesFee;
	@ZteSoftCommentAnnotationParam(name = "活动预存费用", type = "String", isNecessary = "N", desc = "")
	private String activityFee;
	@ZteSoftCommentAnnotationParam(name = "活动下自选包", type = "String", isNecessary = "N", desc = "")
	private List<PackageElementVO> packageElement;

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

	public List<PackageElementVO> getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(List<PackageElementVO> packageElement) {
		this.packageElement = packageElement;
	}

}
