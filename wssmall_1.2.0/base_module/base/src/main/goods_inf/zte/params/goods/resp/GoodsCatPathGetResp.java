package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsCatPathGetResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="类别id列",type="String",isNecessary="Y",desc="catPath:类别id列")
	public String catPath;

	public String getCatPath() {
		return catPath;
	}

	public void setCatPath(String catPath) {
		this.catPath = catPath;
	}


}
