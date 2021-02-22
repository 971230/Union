package zte.params.goodscats.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CatPathGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分类路径",type="String",isNecessary="Y",desc="path： 分类路径。")
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
