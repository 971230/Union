/**
 * 
 */
package zte.net.ecsord.params.zb.vo.orderattr;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-12-28
 * @see 合约信息
 *
 */
public class ActivityInfo {

	@ZteSoftCommentAnnotationParam(name = "合约类型", type = "String", isNecessary = "N", desc = "ActivityType：合约类型")
	private String ActivityType;
	@ZteSoftCommentAnnotationParam(name = "合约编码", type = "String", isNecessary = "N", desc = "ActivityCode：合约编码")
	private String ActivityCode;
	@ZteSoftCommentAnnotationParam(name = "活动下自选包", type = "String", isNecessary = "N", desc = "Package：活动下自选包")
	private List<ActivityInfoPackage> Package;
	@ZteSoftCommentAnnotationParam(name = "合约名称", type = "String", isNecessary = "N", desc = "ActivityName：合约名称")
	private String ActivityName;
	@ZteSoftCommentAnnotationParam(name = "合约期限", type = "String", isNecessary = "N", desc = "ActProtPer：合约期限")
	private String ActProtPer;
	
	public String getActivityType() {
		return ActivityType;
	}
	public void setActivityType(String activityType) {
		ActivityType = activityType;
	}
	public String getActivityCode() {
		return ActivityCode;
	}
	public void setActivityCode(String activityCode) {
		ActivityCode = activityCode;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public String getActProtPer() {
		return ActProtPer;
	}
	public void setActProtPer(String actProtPer) {
		ActProtPer = actProtPer;
	}
	public List<ActivityInfoPackage> getPackage() {
		return Package;
	}
	public void setPackage(List<ActivityInfoPackage> package1) {
		Package = package1;
	}
	
}
