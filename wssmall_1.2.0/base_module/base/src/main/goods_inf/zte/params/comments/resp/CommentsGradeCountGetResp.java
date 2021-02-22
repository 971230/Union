package zte.params.comments.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CommentsGradeCountGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="评论等级数量",type="String",isNecessary="Y",desc="grade：评论等级数量")	
	private Map grade;

	public Map getGrade() {
		return grade;
	}

	public void setGrade(Map grade) {
		this.grade = grade;
	}
}
