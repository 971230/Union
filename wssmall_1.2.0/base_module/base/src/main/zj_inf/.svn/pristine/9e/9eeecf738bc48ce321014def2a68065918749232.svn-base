package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AttachedInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="flowId",type="String",isNecessary="N",desc="flowId：附加产品名")
	private String flowId;
	
//	@ZteSoftCommentAnnotationParam(name="flowPackageId",type="String",isNecessary="N",desc="附加产品的ID,如:10元3G闲时省内流量包的ID.")
//	private String flowPackageId;
	
	@ZteSoftCommentAnnotationParam(name="packageInfo",type="String",isNecessary="N",desc="packageInfo：附加产品内可选包及元素（资费）")
	private List<PackageInfo> packageInfo;

	public String getFlowId() {
		return flowId==null?"":flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public List<PackageInfo> getPackageInfo() {
		return packageInfo==null?(new ArrayList<PackageInfo>()):packageInfo;
	}

	public void setPackageInfo(List<PackageInfo> packageInfo) {
		this.packageInfo = packageInfo;
	}

//	public String getFlowPackageId() {
//		return flowPackageId==null?"":flowPackageId;
//	}
//
//	public void setFlowPackageId(String flowPackageId) {
//		this.flowPackageId = flowPackageId;
//	}
}
