package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ActivityInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="activityId",type="String",isNecessary="Y",desc="activityId：开户套餐的ID")
	private String activityId;
	
	@ZteSoftCommentAnnotationParam(name="productType",type="String",isNecessary="Y",desc="productType：活动类型，编码参考活动类型编码表 （编码）;690301000,购机送费;690302000存费送机;690303000存费送费;690304000存费送积分;690305000存费送礼品;690306000存费送流量;690307000合约惠机A;690308000合约惠机B")
	private String productType;

	@ZteSoftCommentAnnotationParam(name="deviceImei",type="String",isNecessary="N",desc="deviceImei：终端串号")
	private String deviceImei;
	
	@ZteSoftCommentAnnotationParam(name="packageInfo",type="String",isNecessary="N",desc="packageInfo：活动内可选包及元素（资费）")
	private List<PackageInfo> packageInfo;

	public String getActivityId() {
		return activityId==null?"":activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getProductType() {
		return productType==null?"":productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDeviceImei() {
		return deviceImei==null?"":deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public List<PackageInfo> getPackageInfo() {
		return packageInfo==null?(new ArrayList<PackageInfo>()):packageInfo;
	}

	public void setPackageInfo(List<PackageInfo> packageInfo) {
		this.packageInfo = packageInfo;
	}
}
