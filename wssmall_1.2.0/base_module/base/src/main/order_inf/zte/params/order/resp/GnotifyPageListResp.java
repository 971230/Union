package zte.params.order.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

public class GnotifyPageListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="分页数据",type="String",isNecessary="Y",desc="分页数据",hasChild=true)
	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
