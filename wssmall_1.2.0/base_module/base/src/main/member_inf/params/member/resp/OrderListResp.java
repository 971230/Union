package params.member.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;
import params.ZteResponse;


public class OrderListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="分页器",type="Page",isNecessary="Y",desc="page：订单列表分页查询结果。")
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
