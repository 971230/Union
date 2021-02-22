package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 合约信息
 * @author sguo
 *
 */
public class Activity implements Serializable{

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ActivityType;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ActivityCode;	

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ActivityName;

	@ZteSoftCommentAnnotationParam(name="附加产品编码",type="String",isNecessary="Y",desc="SubProCode：附加产品编码")
	private String ActProtPer;
	
	@ZteSoftCommentAnnotationParam(name="可选包信息",type="String",isNecessary="Y",desc="Package：可选包信息")
	private List<Package> Package;
	
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

	public List<Package> getPackage() {
		return Package;
	}

	public void setPackage(List<Package> package1) {
		Package = package1;
	}		
	
}
