package zte.net.ecsord.params.ems.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmsAssignIds {
	@ZteSoftCommentAnnotationParam(name = "分配的邮件号", type = "EmsAssignId", isNecessary = "N", desc = "分配的邮件号")
	private EmsAssignId assignId;

	public EmsAssignId getAssignId() {
		return assignId;
	}

	public void setAssignId(EmsAssignId assignId) {
		this.assignId = assignId;
	}

	

}
