package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

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
	
}
