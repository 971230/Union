package zte.params.process.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-9-26
 */
public class WorkItem implements java.io.Serializable{
	@ZteSoftCommentAnnotationParam(name="环节ID",type="String",isNecessary="N",desc="环节ID")
	private String tacheId;
	@ZteSoftCommentAnnotationParam(name="环节编码",type="String",isNecessary="N",desc="")
	private String tacheCode;	
	@ZteSoftCommentAnnotationParam(name="工作性名称",type="String",isNecessary="N",desc="工作性名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="工作性ID",type="String",isNecessary="N",desc="工作性ID")
	private String workItemId;
	@ZteSoftCommentAnnotationParam(name="活动实例ID",type="String",isNecessary="N",desc="活动实例ID")
	private String activityInstanceId;
	@ZteSoftCommentAnnotationParam(name="工作项开始时间",type="String",isNecessary="N",desc="工作项开始时间")
	private String startedDate;
	@ZteSoftCommentAnnotationParam(name="流程实例ID",type="String",isNecessary="N",desc="流程实例ID")
	private String processInstanceId;
	@ZteSoftCommentAnnotationParam(name="流程实例名称",type="String",isNecessary="N",desc="流程实例名称")
	private String processInstanceName;
	@ZteSoftCommentAnnotationParam(name="工作项的状态",type="String",isNecessary="N",desc="工作项的状态")
	private String state;
	public String getTacheCode() {
		return tacheCode;
	}
	public void setTacheCode(String tacheCode) {
		this.tacheCode = tacheCode;
	}
	public String getTacheId() {
		return tacheId;
	}
	public void setTacheId(String tacheId) {
		this.tacheId = tacheId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	public String getActivityInstanceId() {
		return activityInstanceId;
	}
	public void setActivityInstanceId(String activityInstanceId) {
		this.activityInstanceId = activityInstanceId;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessInstanceName() {
		return processInstanceName;
	}
	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
