package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UserInfoRespVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "电信业务类别", type = "String", isNecessary = "N", desc = "firstMonBillMode")
	private String firstMonBillMode;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的产品信息", type = "String", isNecessary = "N", desc = "product")
	private List<UserProductRespVO> product;
	@ZteSoftCommentAnnotationParam(name = "开户时活动信息", type = "String", isNecessary = "N", desc = "activityInfo")
	private List<UserActivityRespVO> activityInfo;
	public String getFirstMonBillMode() {
		return firstMonBillMode;
	}
	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}
	public List<UserProductRespVO> getProduct() {
		return product;
	}
	public void setProduct(List<UserProductRespVO> product) {
		this.product = product;
	}
	public List<UserActivityRespVO> getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(List<UserActivityRespVO> activityInfo) {
		this.activityInfo = activityInfo;
	}
	
}
