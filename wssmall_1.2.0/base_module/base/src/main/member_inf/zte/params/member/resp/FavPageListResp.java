package zte.params.member.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

public class FavPageListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="收藏夹分页数据",type="String",isNecessary="N",desc="收藏夹分页数据")
	private Page favPage;

	public Page getFavPage() {
		return favPage;
	}

	public void setFavPage(Page favPage) {
		this.favPage = favPage;
	}
	
}
