package params.member.resp;

import com.ztesoft.net.framework.database.Page;
import params.ZteResponse;

public class CommentListResp extends ZteResponse {

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
