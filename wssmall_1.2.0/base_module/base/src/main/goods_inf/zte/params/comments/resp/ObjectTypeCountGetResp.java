package zte.params.comments.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class ObjectTypeCountGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="评论类型统计信息",type="String",isNecessary="Y",desc="objTypeCount：评论类型统计信息")	
	private Map objTypeCount;

	public Map getObjTypeCount() {
		return objTypeCount;
	}

	public void setObjTypeCount(Map objTypeCount) {
		this.objTypeCount = objTypeCount;
	}
}
