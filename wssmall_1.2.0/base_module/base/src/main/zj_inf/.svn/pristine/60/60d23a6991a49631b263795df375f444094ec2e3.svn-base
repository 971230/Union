package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ActivityInfoVo implements Serializable{
	@ZteSoftCommentAnnotationParam(name = "活动ID（总部活动ID）", type = "String", isNecessary = "Y", desc = "活动ID（总部活动ID）")
	private String actPlanId;
	@ZteSoftCommentAnnotationParam(name = "产品下附加包及包元素（资费，服务）", type = "PackageChangePackageElementVo", isNecessary = "Y", desc = "packageElement")
	private List<ProdPackElementReqVo> packageElement;
	
	public String getActPlanId() {
		return actPlanId;
	}
	public void setActPlanId(String actPlanId) {
		this.actPlanId = actPlanId;
	}
	public List<ProdPackElementReqVo> getPackageElement() {
		return packageElement;
	}
	public void setPackageElement(List<ProdPackElementReqVo> packageElement) {
		this.packageElement = packageElement;
	}
}
