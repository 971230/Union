package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ActPara;

public class ActivityInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "活动方案ID", type = "String", isNecessary = "Y", desc = "活动方案ID")
	private String actPlanId;

	@ZteSoftCommentAnnotationParam(name = "活动扩展字段", type = "ActPara", isNecessary = "N", desc = "活动扩展字段")
	private List<ActPara> actPara;

	@ZteSoftCommentAnnotationParam(name = "资源唯一标识", type = "String", isNecessary = "N", desc = "资源唯一标识")
	private String resourcesCode;

	@ZteSoftCommentAnnotationParam(name = "预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联", type = "String", isNecessary = "N", desc = "预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联")
	private String resProcId;

	@ZteSoftCommentAnnotationParam(name = "资源类型", type = "String", isNecessary = "N", desc = "资源类型")
	private String resourcesType;

	@ZteSoftCommentAnnotationParam(name = "资源费用", type = "String", isNecessary = "N", desc = "资源费用")
	private String resourcesFee;

	public String getActPlanId() {
		return this.actPlanId;
	}

	public void setActPlanId(String v) {
		this.actPlanId = v;
	}

	public List<ActPara> getActPara() {
		return this.actPara;
	}

	public void setActPara(List<ActPara> v) {
		this.actPara = v;
	}

	public String getResourcesCode() {
		return this.resourcesCode;
	}

	public void setResourcesCode(String v) {
		this.resourcesCode = v;
	}

	public String getResProcId() {
		return this.resProcId;
	}

	public void setResProcId(String v) {
		this.resProcId = v;
	}

	public String getResourcesType() {
		return this.resourcesType;
	}

	public void setResourcesType(String v) {
		this.resourcesType = v;
	}

	public String getResourcesFee() {
		return this.resourcesFee;
	}

	public void setResourcesFee(String v) {
		this.resourcesFee = v;
	}

}
