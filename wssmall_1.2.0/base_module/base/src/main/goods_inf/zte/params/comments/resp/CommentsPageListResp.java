package zte.params.comments.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

public class CommentsPageListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分页器",type="Page",isNecessary="Y",desc="page：商品评论分页查询结果。")
	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
